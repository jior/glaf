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

import java.util.ArrayList;
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
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;
import org.jpage.core.cache.CacheFactory;
import org.jpage.jbpm.el.DefaultExpressionEvaluator;

public class FinishProcessAction implements ActionHandler {
	private static final Log logger = LogFactory
			.getLog(FinishProcessAction.class);

	private static final long serialVersionUID = 1L;

	private String description;

	private String expression;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public void execute(ExecutionContext ctx) throws Exception {
		logger.debug("---------------------------------------------------");
		logger.debug("---------------FinishProcessAction-----------------");
		logger.debug("---------------------------------------------------");
		ProcessInstance processInstance = ctx.getProcessInstance();

		boolean executable = true;

		Map params = new HashMap();

		ContextInstance contextInstance = ctx.getContextInstance();
		Map variables = contextInstance.getVariables();
		if (variables != null && variables.size() > 0) {
			Iterator iterator = variables.keySet().iterator();
			while (iterator.hasNext()) {
				String variableName = (String) iterator.next();
				if (params.get(variableName) == null) {
					Object value = contextInstance.getVariable(variableName);
					params.put(variableName, value);
				}
			}
		}

		if (StringUtils.isNotBlank(expression)) {
			if (expression.startsWith("#{") && expression.endsWith("}")) {
				if (logger.isDebugEnabled()) {
					logger.debug("expression->" + expression);
					logger.debug("params->" + params);
				}
				Object value = DefaultExpressionEvaluator.evaluate(expression,
						params);
				if (value != null) {
					if (value instanceof Boolean) {
						Boolean b = (Boolean) value;
						executable = b.booleanValue();
						logger.debug("executable->" + executable);
					}
				}
			}
		}

		if (!executable) {
			logger.debug("在表达式计算后取值为false，不执行后续动作。");
			return;
		}

		Token token = ctx.getToken();
		TaskMgmtInstance tmi = processInstance.getTaskMgmtInstance();
		if (tmi != null && token != null) {
			Collection taskInstances = tmi.getTaskInstances();
			Collection unfinishedTasks = new ArrayList();
			if (taskInstances != null) {
				Iterator iter = taskInstances.iterator();
				while (iter.hasNext()) {
					TaskInstance task = (TaskInstance) iter.next();
					if ((!task.hasEnded())) {
						unfinishedTasks.add(task);
					}
				}
			}

			if (unfinishedTasks.size() > 0) {
				logger.debug("unfinishedTasks size:" + unfinishedTasks.size());
				TaskInstance currTaskInstance = ctx.getTaskInstance();
				StringBuffer buffer = new StringBuffer();
				Iterator iter = unfinishedTasks.iterator();
				while (iter.hasNext()) {
					TaskInstance taskInstance = (TaskInstance) iter.next();

					if (currTaskInstance != null) {
						if (currTaskInstance.getId() == taskInstance.getId()) {
							continue;
						}
					}

					try {
						if (CacheFactory.get(String.valueOf(taskInstance
								.getId())) == null) {
							taskInstance.setSignalling(false);
							taskInstance.end();
							CacheFactory.put(
									String.valueOf(taskInstance.getId()), "1");
							buffer.append("\n取消任务实例 编号: ")
									.append(taskInstance.getId())
									.append("\t名称:")
									.append(taskInstance.getName())
									.append("\t描述:")
									.append(taskInstance.getDescription());
							CacheFactory.put("leave_and_cancel_"
									+ processInstance.getId(), "1");
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

				logger.debug(buffer.toString());

			}
		}

		processInstance.end();

	}

}
