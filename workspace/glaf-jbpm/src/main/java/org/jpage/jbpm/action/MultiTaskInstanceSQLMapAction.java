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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

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
import org.jbpm.taskmgmt.exe.PooledActor;
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

/**
 * 动态任务产生注意事项：需要将task-node节点的create-tasks的属性改成create-tasks="false" 例如：
 * <task-node name="采购部部长审批" create-tasks="false"><br>
 * <event type="node-enter"><br>
 * <action ref-name="taskinstance_role07x"/><br>
 * </event><br>
 * <task name="task07x" description="采购部部长审批" ></task><br>
 * <transition name="tr410" to="采购部部长审批通过？"></transition><br>
 * </task-node><br>
 */
public class MultiTaskInstanceSQLMapAction implements ActionHandler {
	private static final Log logger = LogFactory
			.getLog(MultiTaskInstanceSQLMapAction.class);

	private static final long serialVersionUID = 1L;

	private String description;

	private String expression;

	private String deptId;

	private String roleId;

	private String queryId;

	private String objectId;

	private String objectValue;

	private Element elements;

	/**
	 * 动态设置的参与者的参数名，环境变量可以通过contextInstance.getVariable()取得
	 * 例如：contextInstance.getVariable("SendDocAuditor");
	 */
	private String dynamicActors;

	private String taskName;

	private String signalling;

	/**
	 * 如果不能获取任务参与者是否离开本节点（任务节点）
	 */
	private String leaveNodeIfActorNotAvailable;

	private String transitionName;

	/**
	 * 排斥的任务名称，多个任务名称之间用“,”隔开
	 */
	private String excludeTaskNames;

	/**
	 * 排斥的表达式，只有当表达式成立才排斥那些任务
	 */
	private String excludeExpression;

	public MultiTaskInstanceSQLMapAction() {

	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public Element getElements() {
		return elements;
	}

	public void setElements(Element elements) {
		this.elements = elements;
	}

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

	public String getExcludeExpression() {
		return excludeExpression;
	}

	public void setExcludeExpression(String excludeExpression) {
		this.excludeExpression = excludeExpression;
	}

	public String getExcludeTaskNames() {
		return excludeTaskNames;
	}

	public void setExcludeTaskNames(String excludeTaskNames) {
		this.excludeTaskNames = excludeTaskNames;
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

	public void execute(ExecutionContext ctx) {
		logger.debug("-------------------------------------------------------");
		logger.debug("------------MultiTaskInstanceSQLMapAction--------------");
		logger.debug("-------------------------------------------------------");

		boolean executable = true;

		Map params = new HashMap();

		ProcessInstance processInstance = ctx.getProcessInstance();
		String processInstanceId = String.valueOf(processInstance.getId());
		params.put("processInstanceId", processInstanceId);
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

		String rowId = (String) contextInstance.getVariable(Constant.ROW_ID);
		String process_starter = (String) contextInstance
				.getVariable(Constant.PROCESS_START_ACTORID);
		String latestActorId = (String) contextInstance
				.getVariable(Constant.PROCESS_LATEST_ACTORID);

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

							params.put(key, value);

						}
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

			if (logger.isDebugEnabled()) {
				logger.debug("params:" + params);
			}

			Collection actors = MutableSQLMapContainer.getContainer().query(
					ctx.getJbpmContext(), queryId, params);

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

		if (logger.isDebugEnabled()) {
			logger.debug("actorIds:" + actorIds);
		}

		if (actorIds.size() == 0) {

			if (StringUtils.isNotBlank(leaveNodeIfActorNotAvailable)) {
				if (StringUtils.equals("true", leaveNodeIfActorNotAvailable)) {

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
					StringTokenizer st2 = new StringTokenizer(defaultActors,
							",");
					while (st2.hasMoreTokens()) {
						String elem = st2.nextToken();
						if (StringUtils.isNotBlank(elem)) {
							actorIds.add(elem);
						}
					}
				}
			}
		}

		TaskMgmtInstance tmi = ctx.getTaskMgmtInstance();

		if (actorIds.size() > 0 && StringUtils.isNotBlank(excludeTaskNames)) {
			boolean exclude = true;
			if (StringUtils.isNotBlank(excludeExpression)) {
				if (excludeExpression.startsWith("#{")
						&& excludeExpression.endsWith("}")) {
					if (logger.isDebugEnabled()) {
						logger.debug("excludeExpression->" + excludeExpression);
						logger.debug("params->" + params);
					}
					Object value = DefaultExpressionEvaluator.evaluate(
							excludeExpression, params);
					if (value != null) {
						if (value instanceof Boolean) {
							Boolean b = (Boolean) value;
							exclude = b.booleanValue();
							if (logger.isDebugEnabled()) {
								logger.debug("排斥表达式执行结果->" + exclude);
							}
						}
					}
				}
				if (!exclude) {
					logger.debug("排斥的表达式取值为false，不执行排斥动作。");
				}
			}

			// 检查排斥的任务名称
			Collection taskInstances = tmi.getTaskInstances();
			if (exclude && taskInstances != null) {
				Iterator iter = taskInstances.iterator();
				while (iter.hasNext()) {
					TaskInstance taskInstance = (TaskInstance) iter.next();
					if (excludeTaskNames.indexOf(taskInstance.getName()) != -1) {
						if (StringUtils.isNotBlank(taskInstance.getActorId())) {
							actorIds.remove(taskInstance.getActorId());
						} else {
							Set pooledActors = taskInstance.getPooledActors();
							if (pooledActors != null) {
								Iterator iterator = pooledActors.iterator();
								while (iterator.hasNext()) {
									PooledActor pooledActor = (PooledActor) iterator
											.next();
									if (StringUtils.isNotBlank(pooledActor
											.getActorId())) {
										actorIds.remove(pooledActor
												.getActorId());
									}
								}
							}
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

			StringBuffer buffer = new StringBuffer();

			Iterator iterator = actorIds.iterator();
			while (iterator.hasNext()) {
				String actorId = (String) iterator.next();
				TaskInstance taskInstance = tmi.createTaskInstance(task, token);
				taskInstance.setActorId(actorId);
				taskInstance.setCreate(new Date());
				if (StringUtils.equals(signalling, "false")) {
					taskInstance.setSignalling(false);
				} else {
					taskInstance.setSignalling(true);
				}
				buffer.append("\n正在为用户【").append(taskInstance.getActorId())
						.append("】创建任务,").append("id=")
						.append(taskInstance.getId()).append(",name=")
						.append(taskInstance.getName()).append(",signalling=")
						.append(taskInstance.isSignalling());
			}

			logger.debug(buffer.toString());

		}

	}

}
