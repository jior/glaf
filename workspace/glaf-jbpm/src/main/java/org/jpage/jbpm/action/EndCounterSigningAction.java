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


package org.jpage.jbpm.action;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;
import org.jpage.core.cache.CacheFactory;
import org.jpage.jbpm.el.DefaultExpressionEvaluator;

public class EndCounterSigningAction implements ActionHandler {

	private static final Log logger = LogFactory
			.getLog(EndCounterSigningAction.class);

	private static final long serialVersionUID = 1L;

	/**
	 * �Ƿ��ź�
	 */
	private boolean signal;

	private String expression;

	private String taskName;

	private String transitionName;

	protected String nodeType;

	protected boolean visible;

	protected boolean selected;

	public void execute(ExecutionContext ctx) throws Exception {
		logger.debug("------------------------------------------------------");
		logger.debug("----------------EndCounterSigningAction---------------");
		logger.debug("------------------------------------------------------");

		boolean executable = true;

		Map<String, Object> params = new HashMap<String, Object>();

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
			logger.debug("����ʽ�����ȡֵΪfalse����ִ�к���������");
			return;
		}

		TaskMgmtInstance tmi = ctx.getTaskMgmtInstance();
		TaskInstance x = ctx.getTaskInstance();
		String actorId = null;
		if (x != null) {
			actorId = x.getActorId();
		}
		Collection<TaskInstance> c = tmi.getTaskInstances();
		for (Iterator<TaskInstance> it = c.iterator(); it.hasNext();) {
			TaskInstance taskInstance = it.next();
			if (!taskInstance.hasEnded()) {
				if ((actorId != null && actorId.equals(taskInstance
						.getActorId()))) {
					if (CacheFactory.get(String.valueOf(taskInstance.getId())) == null) {
						CacheFactory.put(String.valueOf(taskInstance.getId()),
								"1");
					}
				} else {
					if (CacheFactory.get(String.valueOf(taskInstance.getId())) == null) {
						CacheFactory.put(String.valueOf(taskInstance.getId()),
								"1");
						if (StringUtils.isNotEmpty(taskName)) {
							if (!StringUtils.equals(taskName,
									taskInstance.getName())) {
								continue;
							}
						}
						if (StringUtils.isNotEmpty(transitionName)) {
							taskInstance.setSignalling(false);
							taskInstance.end(transitionName);
						} else {
							taskInstance.setSignalling(false);
							taskInstance.end();
						}
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

	public String getTaskName() {
		return taskName;
	}

	public String getTransitionName() {
		return transitionName;
	}

	public boolean isSelected() {
		return selected;
	}

	public boolean isSignal() {
		return signal;
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

	public void setSignal(boolean signal) {
		this.signal = signal;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setTransitionName(String transitionName) {
		this.transitionName = transitionName;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}