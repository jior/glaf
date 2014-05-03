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
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmException;
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

import com.glaf.core.identity.User;
import com.glaf.core.util.LogUtils;
import com.glaf.core.util.StringTools;
import com.glaf.jbpm.db.mybatis2.SqlMapContainer;
import com.glaf.jbpm.el.DefaultExpressionEvaluator;
import com.glaf.jbpm.util.Constant;

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
public class SqlMapMultiTaskInstanceAction implements ActionHandler {
	private static final Log logger = LogFactory
			.getLog(SqlMapMultiTaskInstanceAction.class);

	private static final long serialVersionUID = 1L;

	/**
	 * 描述
	 */
	protected String description;

	/**
	 * 如果表达式不为空，则其计算结果必须为true才执行任务分派。
	 */
	protected String expression;

	/**
	 * 部门编号或表达式
	 */
	protected String deptId;

	/**
	 * 角色集合 <br>
	 * <roleIds><br>
	 * <element>R001</element><br>
	 * <element>R002</element><br>
	 * </roleIds><br>
	 */
	protected List<Object> roleIds;

	/**
	 * SqlMap查询语句编号
	 */
	protected String queryId;

	/**
	 * 转移路径的名称
	 */
	protected String transitionName;

	/**
	 * 任务名称
	 */
	protected String taskName;

	/**
	 * 如果多个审批者只要有一个通过就可以的，设置该值为true
	 */
	protected boolean isPooled;

	/**
	 * 如果不能获取任务参与者是否离开本节点（任务节点）
	 */
	protected boolean leaveNodeIfActorNotAvailable;

	public SqlMapMultiTaskInstanceAction() {

	}

	public void execute(ExecutionContext ctx) {
		logger.debug("-------------------------------------------------------");
		logger.debug("---------------MxMyBatisMultiTaskInstanceAction--------");
		logger.debug("-------------------------------------------------------");

		boolean executable = true;

		Map<String, Object> params = new java.util.HashMap<String, Object>();

		ProcessInstance processInstance = ctx.getProcessInstance();
		params.put("processInstanceId", processInstance.getId());
		ContextInstance contextInstance = ctx.getContextInstance();
		Map<String, Object> variables = contextInstance.getVariables();
		if (variables != null && variables.size() > 0) {
			Set<Entry<String, Object>> entrySet = variables.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String name = entry.getKey();
				Object value = entry.getValue();
				if (name != null && value != null && params.get(name) == null) {
					params.put(name, value);
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

		boolean exists = false;

		/**
		 * 从数据库中根据查询条件查找参与者
		 */
		if (StringUtils.isNotEmpty(queryId)) {
			if (LogUtils.isDebug()) {
				logger.debug("queryId:" + queryId);
			}

			if (StringUtils.isNotEmpty(deptId)) {
				String tmp = deptId;
				Object value = deptId;
				if (tmp.startsWith("#{") && tmp.endsWith("}")) {
					value = DefaultExpressionEvaluator.evaluate(tmp, params);
				} else if (tmp.startsWith("#P{") && tmp.endsWith("}")) {
					tmp = StringTools.replaceIgnoreCase(tmp, "#P{", "");
					tmp = StringTools.replaceIgnoreCase(tmp, "}", "");
					value = contextInstance.getVariable(tmp);
				}
				params.put("deptId", value);
			}

			if (roleIds != null && roleIds.size() > 0) {

				Task task = null;

				if (StringUtils.isNotEmpty(taskName)) {
					Node node = ctx.getNode();
					if (node instanceof TaskNode) {
						TaskNode taskNode = (TaskNode) node;
						task = taskNode.getTask(taskName);
					}
				}

				if (task == null) {
					task = ctx.getTask();
				}

				if (task == null) {
					throw new JbpmException(" task is null");
				}

				Token token = ctx.getToken();
				TaskMgmtInstance tmi = ctx.getTaskMgmtInstance();

				for (Object roleId : roleIds) {

					params.put("roleId", roleId);

					List<String> actorIds = new java.util.ArrayList<String>();

					SqlMapContainer container = SqlMapContainer.getContainer();

					Collection<?> actors = container.getList(ctx
							.getJbpmContext().getConnection(), queryId, params);

					if (actors != null && actors.size() > 0) {
						Iterator<?> iterator = actors.iterator();
						while (iterator.hasNext()) {
							Object object = iterator.next();
							String actorId = null;
							if (object instanceof String) {
								actorId = (String) object;
							} else if (object instanceof User) {
								User user = (User) object;
								actorId = user.getActorId();
							}
							if (actorId != null) {
								actorIds.add(actorId);
							}
						}
					}

					String startActorId = (String) contextInstance
							.getVariable(Constant.PROCESS_STARTERID);
					actorIds.remove(startActorId);

					if (LogUtils.isDebug()) {
						logger.debug("actorIds:" + actorIds);
					}

					if (actorIds.size() > 0) {
						exists = true;
						if (isPooled) {
							TaskInstance taskInstance = tmi.createTaskInstance(
									task, token);
							taskInstance.setCreate(new Date());
							if (task != null) {
								taskInstance.setSignalling(task.isSignalling());
							}
							if (actorIds.size() == 1) {
								String actorId = actorIds.iterator().next();
								taskInstance.setActorId(actorId);
							} else {
								int i = 0;
								String[] pooledIds = new String[actorIds.size()];
								Iterator<String> iterator2 = actorIds
										.iterator();
								while (iterator2.hasNext()) {
									pooledIds[i++] = iterator2.next();
								}
								taskInstance.setPooledActors(pooledIds);
							}
						} else {
							Iterator<String> iterator = actorIds.iterator();
							while (iterator.hasNext()) {
								String actorId = iterator.next();
								TaskInstance taskInstance = tmi
										.createTaskInstance(task, token);
								taskInstance.setActorId(actorId);
								taskInstance.setCreate(new Date());
								if (task != null) {
									taskInstance.setSignalling(task
											.isSignalling());
								}
							}
						}
					}
				}
			}

			if (!exists && leaveNodeIfActorNotAvailable) {
				contextInstance.setVariable(Constant.IS_AGREE, "true");
				if (StringUtils.isNotEmpty(transitionName)) {
					ctx.leaveNode(transitionName);
				} else {
					ctx.leaveNode();
				}
				return;
			}
		}
	}

	public String getDeptId() {
		return deptId;
	}

	public String getDescription() {
		return description;
	}

	public String getExpression() {
		return expression;
	}

	public String getQueryId() {
		return queryId;
	}

	public List<Object> getRoleIds() {
		return roleIds;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getTransitionName() {
		return transitionName;
	}

	public boolean isLeaveNodeIfActorNotAvailable() {
		return leaveNodeIfActorNotAvailable;
	}

	public boolean isPooled() {
		return isPooled;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public void setLeaveNodeIfActorNotAvailable(
			boolean leaveNodeIfActorNotAvailable) {
		this.leaveNodeIfActorNotAvailable = leaveNodeIfActorNotAvailable;
	}

	public void setPooled(boolean isPooled) {
		this.isPooled = isPooled;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public void setRoleIds(List<Object> roleIds) {
		this.roleIds = roleIds;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setTransitionName(String transitionName) {
		this.transitionName = transitionName;
	}
}