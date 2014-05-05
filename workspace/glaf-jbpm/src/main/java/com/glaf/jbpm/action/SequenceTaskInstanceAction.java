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

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.graph.node.TaskNode;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;

import com.glaf.core.util.StringTools;
import com.glaf.jbpm.el.DefaultExpressionEvaluator;
import com.glaf.jbpm.util.Constant;

/**
 * 顺序任务实例处理器<br/>
 * 假设是A节点里的task02任务，假设三个经理分别是X,Y,Z 假设当前设置的优先级是Y,Z,X。<br/>
 * 首先把A的create-tasks="false", 然后加node-enter事件<br/>
 * 在事件里先创建第一个关于task02的taskInstance,在创建任务时可以读出这个taskInstance的所有执行人 即Y,Z,X。<br/>
 * 最初只创建Y的taskInstance， 此时只有Y可以审批任务task02。 <br/>
 * 当Y审批完之后 在task02上加一个task-end事件， 然后创建Z的taskInstance，依次类推。<br/>
 * 
 */
public class SequenceTaskInstanceAction implements ActionHandler {

	private static final long serialVersionUID = 1L;

	/**
	 * 通过判断条件
	 */
	protected String expression;

	/**
	 * 动态设置的参与者的参数名，环境变量可以通过contextInstance.getVariable()取得
	 * 例如：contextInstance.getVariable("DeptAuditor");
	 */
	protected String dynamicActors;

	/**
	 * 任务名称
	 */
	protected String taskName;

	/**
	 * 转移路径的名称
	 */
	protected String transitionName;

	/**
	 * 如果有一个不通过则离开本节点（一票否决还是由最后审批人决定）
	 */
	protected boolean leaveNodeIfActorNotAgree;

	/**
	 * 如果不能获取任务参与者是否离开本节点（任务节点）
	 */
	protected boolean leaveNodeIfActorNotAvailable;

	public void execute(ExecutionContext ctx) throws Exception {
		boolean isAgree = true;

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
						isAgree = b.booleanValue();
					}
				}
			}
		}

		/**
		 * 假如有一个人审核不通过并且设置了一票否决
		 */
		if ((!isAgree) && leaveNodeIfActorNotAgree) {
			if (StringUtils.isNotEmpty(transitionName)) {
				ctx.leaveNode(transitionName);
			} else {
				ctx.leaveNode();
			}
			return;
		}

		boolean hasActors = false;

		List<String> actorIds = null;

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

		if (StringUtils.isNotEmpty(dynamicActors)) {
			String rowIds = (String) contextInstance.getVariable(dynamicActors);
			if (StringUtils.isNotEmpty(rowIds)) {
				actorIds = StringTools.split(rowIds);
			}
		}

		if (actorIds != null) {
			if (actorIds.size() > 0) {
				String actorId = actorIds.get(0);
				Token token = ctx.getToken();
				TaskMgmtInstance tmi = ctx.getTaskMgmtInstance();
				TaskInstance taskInstance = tmi.createTaskInstance(task, token);
				taskInstance.setActorId(actorId);
				taskInstance.setCreate(new Date());
				taskInstance.setSignalling(task.isSignalling());
				actorIds.remove(0);
				hasActors = true;
			}

			if (actorIds.size() > 0) {
				StringBuilder buffer = new StringBuilder();
				String delim = "";
				for (String element : actorIds) {
					buffer.append(delim);
					buffer.append(element);
					delim = ",";
				}
				contextInstance.setVariable(dynamicActors, buffer.toString());
			}
		}

		if (!hasActors) {
			if (leaveNodeIfActorNotAvailable) {
				contextInstance.setVariable(Constant.IS_AGREE, "true");
				if (StringUtils.isNotEmpty(transitionName)) {
					ctx.leaveNode(transitionName);
				} else {
					ctx.leaveNode();
				}
			}
		}
	}

	public String getDynamicActors() {
		return dynamicActors;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getTransitionName() {
		return transitionName;
	}

	public boolean isLeaveNodeIfActorNotAgree() {
		return leaveNodeIfActorNotAgree;
	}

	public boolean isLeaveNodeIfActorNotAvailable() {
		return leaveNodeIfActorNotAvailable;
	}

	public void setDynamicActors(String dynamicActors) {
		this.dynamicActors = dynamicActors;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public void setLeaveNodeIfActorNotAgree(boolean leaveNodeIfActorNotAgree) {
		this.leaveNodeIfActorNotAgree = leaveNodeIfActorNotAgree;
	}

	public void setLeaveNodeIfActorNotAvailable(
			boolean leaveNodeIfActorNotAvailable) {
		this.leaveNodeIfActorNotAvailable = leaveNodeIfActorNotAvailable;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setTransitionName(String transitionName) {
		this.transitionName = transitionName;
	}

}