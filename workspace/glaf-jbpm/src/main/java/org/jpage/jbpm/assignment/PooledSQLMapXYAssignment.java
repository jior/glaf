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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.node.TaskNode;
import org.jbpm.taskmgmt.def.AssignmentHandler;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.Assignable;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jpage.actor.Actor;
import org.jpage.actor.User;
import org.jpage.jbpm.config.ObjectFactory;
import org.jpage.jbpm.el.DefaultExpressionEvaluator;
import org.jpage.jbpm.ibatis.MutableSQLMapContainer;
import org.jpage.jbpm.instantiation.CustomFieldInstantiator;
import org.jpage.jbpm.util.Constant;
import org.jpage.util.Tools;

/**
 * �������˵���� �������ȼ�˳����������Ĳ�����<br>
 * 1��ֱ�Ӵ��ⲿ��ȡ�Ķ�̬������ID <br>
 * 2����������Ԥ����Ĳ�����<br>
 * 3������������Swimlane����Ĳ�����<br>
 * 4�����̶����ļ���expression����Ĳ�����
 * 
 */
public class PooledSQLMapXYAssignment implements AssignmentHandler {
	private static final Log logger = LogFactory
			.getLog(PooledSQLMapXYAssignment.class);

	private static final long serialVersionUID = 1L;

	private String deptId;

	private String roleId;

	private String queryId;

	/**
	 * ���ż��� <br>
	 * <deptIds><br>
	 * <element>310</element><br>
	 * <element>303</element><br>
	 * </deptIds><br>
	 */
	private List deptIds;

	/**
	 * ��ɫ���� <br>
	 * <roleIds><br>
	 * <element>R001</element><br>
	 * <element>R002</element><br>
	 * </roleIds><br>
	 */
	private List roleIds;

	/**
	 * ��ѯ���� <br>
	 * <queryIds><br>
	 * <element>tms_getDeptRoleUsers</element><br>
	 * <element>tms_getCategoryChargeUsers</element><br>
	 * </queryIds><br>
	 */
	private List queryIds;

	/**
	 * JSON ���ʽ,���ڲ�ѯ�Ĳ���,��sqlmap�Ĳ�ѯ����
	 */
	private String json;

	private String objectId;

	private String objectValue;

	private Element elements;

	/**
	 * ��̬���õĲ����ߵĲ�������������������ͨ��contextInstance.getVariable()ȡ��
	 * ���磺contextInstance.getVariable("SendDocAuditor");
	 */
	private String dynamicActors;

	private String taskName;

	/**
	 * ������ܻ�ȡ����������Ƿ��뿪���ڵ㣨����ڵ㣩
	 */
	private String leaveNodeIfActorNotAvailable;

	private String transitionName;

	public PooledSQLMapXYAssignment() {

	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public List getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(List deptIds) {
		this.deptIds = deptIds;
	}

	public List getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List roleIds) {
		this.roleIds = roleIds;
	}

	public Element getElements() {
		return elements;
	}

	public void setElements(Element elements) {
		this.elements = elements;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
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

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public List getQueryIds() {
		return queryIds;
	}

	public void setQueryIds(List queryIds) {
		this.queryIds = queryIds;
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

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getLeaveNodeIfActorNotAvailable() {
		return leaveNodeIfActorNotAvailable;
	}

	public void setLeaveNodeIfActorNotAvailable(
			String leaveNodeIfActorNotAvailable) {
		this.leaveNodeIfActorNotAvailable = leaveNodeIfActorNotAvailable;
	}

	public String getTransitionName() {
		return transitionName;
	}

	public void setTransitionName(String transitionName) {
		this.transitionName = transitionName;
	}

	public void assign(Assignable assignable, ExecutionContext ctx) {
		logger.debug("-------------------------------------------------------");
		logger.debug("--------------PooledSQLMapXYAssignment-----------------");
		logger.debug("-------------------------------------------------------");

		Task task = null;

		if (StringUtils.isNotBlank(taskName)) {
			Node node = ctx.getNode();
			if (node instanceof TaskNode) {
				TaskNode taskNode = (TaskNode) node;
				task = taskNode.getTask(taskName);
			}
		}

		if (task == null) {
			task = ctx.getTask();
			taskName = task.getName();
		}

		if (task != null) {
			ContextInstance contextInstance = ctx.getContextInstance();
			List actorIds = new ArrayList();
			if (StringUtils.isNotBlank(dynamicActors)) {
				String actorId = (String) contextInstance
						.getVariable(dynamicActors);
				if (StringUtils.isNotBlank(actorId)) {
					StringTokenizer st2 = new StringTokenizer(actorId, ",");
					while (st2.hasMoreTokens()) {
						String elem = st2.nextToken();
						if (StringUtils.isNotBlank(elem)) {
							actorIds.add(elem);
						}
					}
				}
			}

			Map params = new HashMap();
			ProcessInstance processInstance = ctx.getProcessInstance();
			String processInstanceId = String.valueOf(processInstance.getId());
			params.put("processInstanceId", processInstanceId);
			String rowId = (String) contextInstance
					.getVariable(Constant.ROW_ID);
			String process_starter = (String) contextInstance
					.getVariable(Constant.PROCESS_START_ACTORID);
			String latestActorId = (String) contextInstance
					.getVariable(Constant.PROCESS_LATEST_ACTORID);

			if (elements != null) {
				if (logger.isDebugEnabled()) {
					logger.debug("elements:" + elements.asXML());
				}
				Object obj = CustomFieldInstantiator.getValue(Map.class,
						elements);
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

			if (StringUtils.isNotEmpty(json) && json.startsWith("{")
					&& json.endsWith("}")) {
				Map paramMap = org.jpage.util.JSONTools.decode(json);
				if (paramMap != null && paramMap.size() > 0) {
					params.putAll(paramMap);
				}
			}

			/**
			 * �����ݿ��и��ݲ�ѯ�������Ҳ�����
			 */
			if (StringUtils.isNotBlank(queryId)
					|| CollectionUtils.isNotEmpty(queryIds)) {
				if (logger.isDebugEnabled()) {
					logger.debug("queryId:" + queryId);
				}
				params.put("roleId", roleId);
				params.put("objectId", objectId);
				params.put("objectValue", objectValue);

				Map variables = contextInstance.getVariables();
				if (variables != null && variables.size() > 0) {
					Iterator iterator = variables.keySet().iterator();
					while (iterator.hasNext()) {
						String variableName = (String) iterator.next();
						if (params.get(variableName) == null) {
							Object value = contextInstance
									.getVariable(variableName);
							params.put(variableName, value);
						}
					}
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
									TaskInstance taskInstance = ctx
											.getTaskInstance();
									if (taskInstance != null) {
										value = String.valueOf(taskInstance
												.getId());
									}
								} else if (tmp.equals("#{taskName}")) {
									if (task != null) {
										value = task.getName();
									} else {
										if (ctx.getTaskInstance() != null) {
											value = ctx.getTaskInstance()
													.getName();
										}
									}
								} else if (tmp.equals("now()")) {
									value = new java.sql.Date(now.getTime());
								} else if (tmp.equals("date()")) {
									value = new java.sql.Date(now.getTime());
								} else if (tmp.equals("time()")) {
									value = new java.sql.Time(now.getTime());
								} else if (tmp.equals("timestamp()")) {
									value = new java.sql.Timestamp(now
											.getTime());
								} else if (tmp.equals("dateTime()")) {
									value = new java.sql.Timestamp(now
											.getTime());
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
									tmp = Tools.replaceIgnoreCase(tmp, "#P{",
											"");
									tmp = Tools.replaceIgnoreCase(tmp, "}", "");
									value = contextInstance.getVariable(tmp);
								} else if (tmp.startsWith("#{")
										&& tmp.endsWith("}")) {
									value = DefaultExpressionEvaluator
											.evaluate(tmp, params);
								}

								params.put(key, value);

							}
						}
					}
				}

				if (StringUtils.isNotBlank(deptId)) {
					String tmp = deptId;
					Object value = deptId;
					if (tmp.startsWith("#{") && tmp.endsWith("}")) {
						value = DefaultExpressionEvaluator
								.evaluate(tmp, params);
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
						value = DefaultExpressionEvaluator
								.evaluate(tmp, params);
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
						value = DefaultExpressionEvaluator
								.evaluate(tmp, params);
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
						value = DefaultExpressionEvaluator
								.evaluate(tmp, params);
					} else if (tmp.startsWith("#P{") && tmp.endsWith("}")) {
						tmp = Tools.replaceIgnoreCase(tmp, "#P{", "");
						tmp = Tools.replaceIgnoreCase(tmp, "}", "");
						value = contextInstance.getVariable(tmp);
					}
					params.put("objectValue", value);
				}

				if (CollectionUtils.isNotEmpty(deptIds)) {
					params.put("deptIds", deptIds);
				}

				if (CollectionUtils.isNotEmpty(roleIds)) {
					params.put("roleIds", roleIds);
				}

				if (logger.isDebugEnabled()) {
					logger.debug("params:" + params);
					logger.debug("variables:" + contextInstance.getVariables());
				}

				Collection actors = new HashSet();

				if (StringUtils.isNotBlank(queryId)) {
					actors = MutableSQLMapContainer.getContainer().query(
							ctx.getJbpmContext(), queryId, params);
				} else {
					Iterator iterator = queryIds.iterator();
					while (iterator.hasNext()) {
						queryId = (String) iterator.next();
						Collection actorsXY = MutableSQLMapContainer
								.getContainer().query(ctx.getJbpmContext(),
										queryId, params);
						if (CollectionUtils.isNotEmpty(actorsXY)) {
							actors.addAll(actorsXY);
						}
					}
				}

				if (actors != null && actors.size() > 0) {
					Iterator iterator = actors.iterator();
					while (iterator.hasNext()) {
						Object obj = iterator.next();
						String actorId = null;
						if (obj instanceof String) {
							actorId = (String) obj;
						} else if (obj instanceof org.jpage.actor.Actor) {
							Actor actor = (Actor) obj;
							actorId = actor.getActorId();
						} else if (obj instanceof org.jpage.actor.User) {
							User user = (User) obj;
							actorId = user.getActorId();
						}
						if (actorId != null) {
							actorIds.add(actorId);
						}
					}
				}

			}

			if (actorIds.size() == 0) {

				if (StringUtils.isNotBlank(leaveNodeIfActorNotAvailable)) {
					if (StringUtils
							.equals("true", leaveNodeIfActorNotAvailable)) {

						contextInstance.setVariable(Constant.IS_AGREE, "true");

						if (StringUtils.isNotBlank(transitionName)) {
							ctx.leaveNode(transitionName);
						} else {
							ctx.leaveNode();
						}
						if (ctx.getNode() != null) {
							logger.debug(ctx.getNode().getName()
									+ "->���ܻ�ȡ��������ߣ��뿪��ǰ�ڵ㡣");
						} else {
							if (ctx.getToken() != null) {
								logger.debug(ctx.getToken().getName()
										+ "->���ܻ�ȡ��������ߣ��뿪��ǰ�ڵ㡣");
							}
						}
						return;
					}
				}

				if (ObjectFactory.isDefaultActorEnable()) {
					String defaultActors = ObjectFactory.getDefaultActors();
					if (StringUtils.isNotBlank(defaultActors)) {
						StringTokenizer st2 = new StringTokenizer(
								defaultActors, ",");
						while (st2.hasMoreTokens()) {
							String elem = st2.nextToken();
							if (StringUtils.isNotBlank(elem)) {
								actorIds.add(elem);
							}
						}
					}
				}
			}

			if (actorIds.size() > 0) {

				logger.debug("actorIds size:" + actorIds.size());

				if (actorIds.size() == 1) {
					String actorId = (String) actorIds.get(0);
					assignable.setActorId(actorId);
					logger.debug("actorId:" + actorId);
				} else if (actorIds.size() > 1) {
					int i = 0;
					String[] array = new String[actorIds.size()];
					Iterator iterator = actorIds.iterator();
					while (iterator.hasNext()) {
						String actorId = (String) iterator.next();
						array[i++] = actorId;
						logger.debug("pooed actorId:" + actorId);
					}
					assignable.setPooledActors(array);
				}

			}

		}
	}

}
