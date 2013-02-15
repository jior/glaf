/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jpage.jbpm.action;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.graph.node.TaskNode;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;
import org.jpage.actor.Actor;
import org.jpage.actor.User;
import org.jpage.jbpm.config.ObjectFactory;
import org.jpage.jbpm.el.DefaultExpressionEvaluator;
import org.jpage.jbpm.ibatis.MutableSQLMapContainer;
import org.jpage.jbpm.instantiation.CustomFieldInstantiator;
import org.jpage.jbpm.util.Constant;
import org.jpage.util.Tools;

public class PooledTaskInstanceSQLMapXYAction implements ActionHandler {
	private static final Log logger = LogFactory
			.getLog(PooledTaskInstanceSQLMapXYAction.class);

	private static final long serialVersionUID = 1L;

	private String deptId;

	private String roleId;

	private String queryId;

	/**
	 * 部门集合 <br>
	 * <deptIds><br>
	 * <element>310</element><br>
	 * <element>303</element><br>
	 * </deptIds><br>
	 */
	private List deptIds;

	/**
	 * 角色集合 <br>
	 * <roleIds><br>
	 * <element>R001</element><br>
	 * <element>R002</element><br>
	 * </roleIds><br>
	 */
	private List roleIds;

	/**
	 * 查询集合 <br>
	 * <queryIds><br>
	 * <element>tms_getDeptRoleUsers</element><br>
	 * <element>tms_getCategoryChargeUsers</element><br>
	 * </queryIds><br>
	 */
	private List queryIds;

	/**
	 * JSON 表达式,用于查询的参数,如sqlmap的查询参数
	 */
	private String json;

	private String objectId;

	private String objectValue;

	private String signalling;

	private String taskName;

	private Element elements;

	/**
	 * 动态设置的参与者的参数名，环境变量可以通过contextInstance.getVariable()取得
	 * 例如：contextInstance.getVariable("SendDocAuditor");
	 */
	private String dynamicActors;

	/**
	 * 如果不能获取任务参与者是否离开本节点（任务节点）
	 */
	private String leaveNodeIfActorNotAvailable;

	private String transitionName;

	public PooledTaskInstanceSQLMapXYAction() {

	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
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

	public String getSignalling() {
		return signalling;
	}

	public void setSignalling(String signalling) {
		this.signalling = signalling;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getDynamicActors() {
		return dynamicActors;
	}

	public void setDynamicActors(String dynamicActors) {
		this.dynamicActors = dynamicActors;
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

	public Element getElements() {
		return elements;
	}

	public void setElements(Element elements) {
		this.elements = elements;
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

	public List getQueryIds() {
		return queryIds;
	}

	public void setQueryIds(List queryIds) {
		this.queryIds = queryIds;
	}

	public void execute(ExecutionContext ctx) throws Exception {
		logger.debug("-------------------------------------------------------");
		logger.debug("-----------PooledTaskInstanceSQLMapXYAction------------");
		logger.debug("-------------------------------------------------------");

		Set actorIds = new HashSet();
		ContextInstance contextInstance = ctx.getContextInstance();

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

		if (StringUtils.isNotEmpty(json) && json.startsWith("{")
				&& json.endsWith("}")) {
			Map paramMap = org.jpage.util.JSONTools.decode(json);
			if (paramMap != null && paramMap.size() > 0) {
				params.putAll(paramMap);
			}
		}

		/**
		 * 从数据库中根据查询条件查找参与者
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

			String rowId = (String) contextInstance
					.getVariable(Constant.ROW_ID);
			String process_starter = (String) contextInstance
					.getVariable(Constant.PROCESS_START_ACTORID);
			String latestActorId = (String) contextInstance
					.getVariable(Constant.PROCESS_LATEST_ACTORID);

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
									value = String
											.valueOf(taskInstance.getId());
								}
							} else if (tmp.equals("#{taskName}")) {
								Task task = ctx.getTask();
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
						}
						params.put(key, value);
					}
				}
			}

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
					Collection actorsXY = MutableSQLMapContainer.getContainer()
							.query(ctx.getJbpmContext(), queryId, params);
					if (CollectionUtils.isNotEmpty(actorsXY)) {
						actors.addAll(actorsXY);
					}
				}
			}

			if (actors != null && actors.size() > 0) {
				logger.debug("actors size:" + actors.size());
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
					if (StringUtils.isNotBlank(actorId)) {
						actorIds.add(actorId);
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
									+ "->不能获取任务参与者，离开当前节点。");
						} else {
							if (ctx.getToken() != null) {
								logger.debug(ctx.getToken().getName()
										+ "->不能获取任务参与者，离开当前节点。");
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
				}

				Token token = ctx.getToken();
				TaskMgmtInstance tmi = ctx.getTaskMgmtInstance();
				TaskInstance taskInstance = tmi.createTaskInstance(task, token);
				taskInstance.setCreate(new Date());
				if (StringUtils.equals(signalling, "false")) {
					taskInstance.setSignalling(false);
				} else {
					taskInstance.setSignalling(true);
				}

				if (actorIds.size() == 1) {
					String actorId = (String) actorIds.iterator().next();
					taskInstance.setActorId(actorId);
				} else {
					int i = 0;
					String[] pooledIds = new String[actorIds.size()];
					Iterator iterator2 = actorIds.iterator();
					while (iterator2.hasNext()) {
						pooledIds[i++] = (String) iterator2.next();
					}
					taskInstance.setPooledActors(pooledIds);
				}
			}

		}
	}

}
