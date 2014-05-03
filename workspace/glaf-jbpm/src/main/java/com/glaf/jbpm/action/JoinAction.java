/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.jbpm.action;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.graph.node.Join;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;

import com.glaf.core.util.StringTools;
import com.glaf.jbpm.el.DefaultExpressionEvaluator;

public class JoinAction implements ActionHandler {
	private final transient Log logger = LogFactory.getLog(JoinAction.class);
	private static final long serialVersionUID = 1L;

	protected String expression;

	/**
	 * 包含的任务名称，多个任务之间用半角的逗号隔开
	 */
	protected String includes;

	/**
	 * 排除的任务名称，多个任务之间用半角的逗号隔开
	 */
	protected String excludes;

	protected String nodeType;

	protected boolean visible;

	protected boolean selected;

	public void execute(ExecutionContext ctx) throws Exception {
		logger.debug("------------------------------------------------------");
		logger.debug("----------------JoinAction----------------------------");
		logger.debug("------------------------------------------------------");

		boolean executable = true;

		Map<String, Object> params = new java.util.HashMap<String, Object>();

		ContextInstance contextInstance = ctx.getContextInstance();
		Map<String, Object> variables = contextInstance.getVariables();
		if (variables != null && variables.size() > 0) {
			Iterator<String> iterator = variables.keySet().iterator();
			while (iterator.hasNext()) {
				String variableName = iterator.next();
				if (params.get(variableName) == null) {
					Object value = contextInstance.getVariable(variableName);
					params.put(variableName, value);
				}
			}
		}

		if (StringUtils.isNotEmpty(expression)) {
			if (expression.startsWith("#{") && expression.endsWith("}")) {
				Object value = DefaultExpressionEvaluator.evaluate(expression,
						params);
				if (value != null) {
					if (value instanceof Boolean) {
						Boolean b = (Boolean) value;
						executable = b.booleanValue();
					}
				}
			}
		}

		if (!executable) {
			logger.debug("表达式计算后取值为false，不执行后续动作。");
			return;
		}

		Join join = (Join) ctx.getNode();
		join.setDiscriminator(true);

		Token root = ctx.getToken().getParent();
		Collection<Token> childTokenList = root.getChildren().values();
		TaskMgmtInstance tms = ctx.getTaskMgmtInstance();
		if (StringUtils.isNotEmpty(includes)) {
			List<String> rows = StringTools.split(includes, ",");
			Iterator<Token> iter = childTokenList.iterator();
			while (iter.hasNext()) {
				Token childToken = iter.next();
				Collection<TaskInstance> taskInstances = tms
						.getUnfinishedTasks(childToken);
				Iterator<TaskInstance> it = taskInstances.iterator();
				while (it.hasNext()) {
					TaskInstance taskInstance = it.next();
					if (rows.contains(taskInstance.getName())) {
						if (taskInstance.isBlocking()) {
							taskInstance.setBlocking(false);
						}
						if (taskInstance.isSignalling()) {
							taskInstance.setSignalling(false);
						}
						taskInstance.cancel();
					}
				}
			}
		} else {
			List<String> rows = StringTools.split(excludes, ",");
			Iterator<Token> iter = childTokenList.iterator();
			while (iter.hasNext()) {
				Token childToken = iter.next();
				Collection<TaskInstance> taskInstances = tms
						.getUnfinishedTasks(childToken);
				Iterator<TaskInstance> it = taskInstances.iterator();
				while (it.hasNext()) {
					TaskInstance taskInstance = it.next();
					if (!(rows != null && rows.contains(taskInstance.getName()))) {
						if (taskInstance.isBlocking()) {
							taskInstance.setBlocking(false);
						}
						if (taskInstance.isSignalling()) {
							taskInstance.setSignalling(false);
						}
						taskInstance.cancel();
					}
				}
			}
		}
	}

	public String getExpression() {
		return expression;
	}

	public String getNodeType() {
		return nodeType;
	}

	public boolean isSelected() {
		return selected;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}