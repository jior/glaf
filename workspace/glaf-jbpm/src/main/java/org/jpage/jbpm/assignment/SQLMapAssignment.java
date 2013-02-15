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


package org.jpage.jbpm.assignment;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.jbpm.JbpmContext;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.def.AssignmentHandler;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.Assignable;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jpage.jbpm.context.JbpmContextFactory;
import org.jpage.jbpm.el.DefaultExpressionEvaluator;
import org.jpage.jbpm.ibatis.MutableSQLMapContainer;
import org.jpage.jbpm.instantiation.CustomFieldInstantiator;
import org.jpage.jbpm.service.ActorManager;
import org.jpage.jbpm.service.ServiceManager;
import org.jpage.jbpm.util.Constant;
import org.jpage.util.Tools;

public class SQLMapAssignment implements AssignmentHandler {
	private static final Log logger = LogFactory.getLog(SQLMapAssignment.class);

	private static final long serialVersionUID = 1L;

	private ActorManager actorManager;

	private ServiceManager serviceManager;

	private AssignableHelper helper;

	private String expression;

	private String fallback;

	private String deptId;

	private String roleId;

	private String queryId;

	private String objectId;

	private String objectValue;

	private Map variables;

	private Element elements;

	/**
	 * 动态设置的参与者的参数名，环境变量可以通过contextInstance.getVariable()取得
	 * 例如：contextInstance.getVariable("SendDocAuditor");
	 */
	private String dynamicActors;

	public SQLMapAssignment() {

	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getFallback() {
		return fallback;
	}

	public void setFallback(String fallback) {
		this.fallback = fallback;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getDynamicActors() {
		return dynamicActors;
	}

	public void setDynamicActors(String dynamicActors) {
		this.dynamicActors = dynamicActors;
	}

	public Map getVariables() {
		return variables;
	}

	public void setVariables(Map variables) {
		this.variables = variables;
	}

	public Element getElements() {
		return elements;
	}

	public void setElements(Element elements) {
		this.elements = elements;
	}

	public void assign(Assignable assignable, ExecutionContext ctx) {
		logger.debug("-------------------------------------------------------");
		logger.debug("----------------SQLMapAssignment-----------------------");
		logger.debug("-------------------------------------------------------");

		helper = new AssignableHelper();
		actorManager = (ActorManager) JbpmContextFactory
				.getBean("actorManager");
		serviceManager = (ServiceManager) JbpmContextFactory
				.getBean("serviceManager");

		/**
		 * 直接从外部获取的动态参与者ID
		 */

		ContextInstance contextInstance = ctx.getContextInstance();
		if (StringUtils.isNotBlank(dynamicActors)) {
			String actorId = (String) contextInstance
					.getVariable(dynamicActors);
			logger.debug("dynamicActors:" + dynamicActors);
			logger.debug("actorId:" + actorId);
			if (StringUtils.isNotBlank(actorId)) {
				logger.debug("外部输入的actors:" + actorId);
				helper.setActors(assignable, actorId);
				return;
			}
		}

		Map params = new HashMap();

		JbpmContext jbpmContext = ctx.getJbpmContext();
		ProcessInstance processInstance = ctx.getProcessInstance();
		String processInstanceId = String.valueOf(processInstance.getId());
		params.put("processInstanceId", processInstanceId);
		String rowId = (String) contextInstance.getVariable(Constant.ROW_ID);
		String process_starter = (String) contextInstance
				.getVariable(Constant.PROCESS_START_ACTORID);
		String latestActorId = (String) contextInstance
				.getVariable(Constant.PROCESS_LATEST_ACTORID);

		/**
		 * 如果表达式是流程启动者，则从环境变量中获取流程启动者
		 */
		if (StringUtils.isNotBlank(expression)) {
			if (StringUtils.equals(expression,
					Constant.PROCESS_STARTER_EXPRESSION)) {
				String actorId = (String) contextInstance
						.getVariable(Constant.PROCESS_START_ACTORID);
				if (StringUtils.isNotEmpty(actorId)) {
					assignable.setActorId(actorId);
					return;
				}
			}
		}

		if (variables != null && variables.size() > 0) {
			Iterator iterator = variables.keySet().iterator();
			while (iterator.hasNext()) {
				Object key = iterator.next();
				Object value = variables.get(key);
				if (key != null && value != null) {
					params.put(key, value);
				}
			}
		}

		if (elements != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("elements:" + elements.asXML());
			}
			Object obj = CustomFieldInstantiator.getValue(Map.class, elements);
			if (obj instanceof Map) {
				Map paramMap = (Map) obj;
				Iterator iterator = paramMap.keySet().iterator();
				while (iterator.hasNext()) {
					Object key = iterator.next();
					Object value = paramMap.get(key);
					if (key != null && value != null) {
						params.put(key, value);
					}
				}
			}
		}

		/**
		 * 从数据库中根据查询条件查找参与者
		 */
		if (StringUtils.isNotBlank(queryId)) {
			if (logger.isDebugEnabled()) {
				logger.debug("queryId:" + queryId);
			}
			params.put("roleId", roleId);
			params.put("objectId", objectId);
			params.put("objectValue", objectValue);

			if (StringUtils.isNotBlank(deptId)) {
				String tmp = deptId;
				Object value = deptId;
				if (tmp.startsWith("#{") && tmp.endsWith("}")) {
					value = DefaultExpressionEvaluator.evaluate(tmp, params);
				} else if (tmp.startsWith("#P{") && tmp.endsWith("}")) {
					tmp = Tools.replaceIgnoreCase(tmp, "#P{", "");
					tmp = Tools.replaceIgnoreCase(tmp, "}", "");
					value = contextInstance.getVariable(tmp);
				}
				params.put("deptId", value);
			}

			if (StringUtils.isNotBlank(roleId)) {
				String tmp = roleId;
				Object value = roleId;
				if (tmp.startsWith("#{") && tmp.endsWith("}")) {
					value = DefaultExpressionEvaluator.evaluate(tmp, params);
				} else if (tmp.startsWith("#P{") && tmp.endsWith("}")) {
					tmp = Tools.replaceIgnoreCase(tmp, "#P{", "");
					tmp = Tools.replaceIgnoreCase(tmp, "}", "");
					value = contextInstance.getVariable(tmp);
				}
				params.put("roleId", value);
			}

			if (StringUtils.isNotBlank(objectId)) {
				String tmp = objectId;
				Object value = objectId;
				if (tmp.startsWith("#{") && tmp.endsWith("}")) {
					value = DefaultExpressionEvaluator.evaluate(tmp, params);
				} else if (tmp.startsWith("#P{") && tmp.endsWith("}")) {
					tmp = Tools.replaceIgnoreCase(tmp, "#P{", "");
					tmp = Tools.replaceIgnoreCase(tmp, "}", "");
					value = contextInstance.getVariable(tmp);
				}
				params.put("objectId", value);
			}

			if (StringUtils.isNotBlank(objectValue)) {
				String tmp = objectValue;
				Object value = objectValue;
				if (tmp.startsWith("#{") && tmp.endsWith("}")) {
					value = DefaultExpressionEvaluator.evaluate(tmp, params);
				} else if (tmp.startsWith("#P{") && tmp.endsWith("}")) {
					tmp = Tools.replaceIgnoreCase(tmp, "#P{", "");
					tmp = Tools.replaceIgnoreCase(tmp, "}", "");
					value = contextInstance.getVariable(tmp);
				}
				params.put("objectValue", value);
			}

			Map varMap = contextInstance.getVariables();
			if (varMap != null && varMap.size() > 0) {
				Iterator iterator = varMap.keySet().iterator();
				while (iterator.hasNext()) {
					String variableName = (String) iterator.next();
					if (params.get(variableName) == null) {
						Object value = contextInstance
								.getVariable(variableName);
						params.put(variableName, value);
					}
				}
			}

			Task task = ctx.getTask();
			TaskInstance taskInstance = ctx.getTaskInstance();
			if (taskInstance != null) {
				params.put("taskInstanceId", String.valueOf(taskInstance
						.getId()));
				params.put("taskInstanceName", taskInstance.getName());
			}

			java.util.Date now = new java.util.Date();

			Iterator iterator008 = params.keySet().iterator();
			while (iterator008.hasNext()) {
				Object key = iterator008.next();
				Object value = params.get(key);
				if (key != null && value != null) {
					if (value instanceof String) {
						String tmp = (String) value;
						if (StringUtils.isNotBlank(tmp)) {
							if (tmp.equals("#{processInstanceId}")) {
								value = String.valueOf(contextInstance
										.getProcessInstance().getId());
							} else if (tmp.equals("#{taskInstanceId}")) {
								if (taskInstance != null) {
									value = String
											.valueOf(taskInstance.getId());
								}
							} else if (tmp.equals("#{taskName}")) {
								if (task != null) {
									value = task.getName();
								} else {
									if (ctx.getTaskInstance() != null) {
										value = ctx.getTaskInstance().getName();
									}
								}
							} else if (tmp.equals("now()")) {
								value = new java.sql.Date(now.getTime());
							} else if (tmp.equals("date()")) {
								value = new java.sql.Date(now.getTime());
							} else if (tmp.equals("time()")) {
								value = new java.sql.Time(now.getTime());
							} else if (tmp.equals("timestamp()")) {
								value = new java.sql.Timestamp(now.getTime());
							} else if (tmp.equals("dateTime()")) {
								value = new java.sql.Timestamp(now.getTime());
							} else if (tmp.equals("currentTimeMillis()")) {
								value = new Long(System.currentTimeMillis());
							} else if (tmp
									.equals(Constant.PROCESS_STARTER_EXPRESSION)) {
								value = process_starter;
							} else if (tmp
									.equals(Constant.PROCESS_LATESTER_EXPRESSION)) {
								value = latestActorId;
							} else if (tmp.equals("#{rowId}")) {
								value = rowId;
							} else if (tmp.startsWith("#P{")
									&& tmp.endsWith("}")) {
								tmp = Tools.replaceIgnoreCase(tmp, "#P{", "");
								tmp = Tools.replaceIgnoreCase(tmp, "}", "");
								value = contextInstance.getVariable(tmp);
							} else if (tmp.startsWith("#{")
									&& tmp.endsWith("}")) {
								value = DefaultExpressionEvaluator.evaluate(
										tmp, params);
							}

							params.put(key, value);

						}
					}
				}
			}

			List actors = MutableSQLMapContainer.getContainer().query(
					jbpmContext, queryId, params);

			if (logger.isDebugEnabled()) {
				logger.debug("---->params:" + params);
				logger.debug("---->actorIds:" + actors);
			}

			if (actors != null && actors.size() > 0) {
				if (actors.size() == 1) {
					Object obj = actors.get(0);
					assignable.setActorId(helper.populateActorId(obj));
					return;
				}
				int i = 0;
				String[] pooledActorIds = new String[actors.size()];
				Iterator iterator = actors.iterator();
				while (iterator.hasNext()) {
					Object obj = iterator.next();
					pooledActorIds[i++] = helper.populateActorId(obj);
				}
				assignable.setPooledActors(pooledActorIds);
				return;
			}
		}

		/**
		 * 流程任务预定义的参与者
		 */
		String processName = contextInstance.getProcessInstance()
				.getProcessDefinition().getName();
		String taskName = ctx.getTask().getName();
		String roleId = processName + "-" + taskName;
		List actors = actorManager.getActors(jbpmContext, roleId);
		if (logger.isDebugEnabled()) {
			logger.debug("roleId:" + roleId);
			logger.debug("预定义的任务参与者:" + actors);
		}
		if (actors != null && actors.size() > 0) {
			if (actors.size() == 1) {
				Object obj = actors.get(0);
				assignable.setActorId(helper.populateActorId(obj));
				return;
			}
			int i = 0;
			String[] users = new String[actors.size()];
			Iterator iterator = actors.iterator();
			while (iterator.hasNext()) {
				Object obj = iterator.next();
				users[i++] = helper.populateActorId(obj);
			}
			assignable.setPooledActors(users);
			return;
		}

		/**
		 * 流程定义文件中expression定义的参与者
		 */
		if (StringUtils.isNotBlank(expression)) {
			if (logger.isDebugEnabled()) {
				logger.debug("expression:" + expression);
			}
			Set actorIds = helper.getActorIds(jbpmContext, expression, params);
			helper.setActors(assignable, actorIds);
		}

	}

}
