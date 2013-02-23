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
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.graph.node.TaskNode;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;

import com.glaf.jbpm.util.Constant;

public class MxDynamicTaskInstances implements ActionHandler {
	private static final Log logger = LogFactory
			.getLog(MxDynamicTaskInstances.class);

	private static final long serialVersionUID = 1L;

	/**
	 * 动态设置的参与者的参数名，环境变量可以通过contextInstance.getVariable()取得
	 * 例如：contextInstance.getVariable("SendDocAuditor");
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
	 * 如果不能获取任务参与者是否离开本节点（任务节点）
	 */
	protected boolean leaveNodeIfActorNotAvailable;

	public MxDynamicTaskInstances() {

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

	public String getTransitionName() {
		return transitionName;
	}

	public void setTransitionName(String transitionName) {
		this.transitionName = transitionName;
	}

	public boolean isLeaveNodeIfActorNotAvailable() {
		return leaveNodeIfActorNotAvailable;
	}

	public void setLeaveNodeIfActorNotAvailable(
			boolean leaveNodeIfActorNotAvailable) {
		this.leaveNodeIfActorNotAvailable = leaveNodeIfActorNotAvailable;
	}

	public void execute(ExecutionContext ctx) {
		logger.debug("-------------------------------------------------------");
		logger.debug("---------------DynamicTaskInstances--------------------");
		logger.debug("-------------------------------------------------------");

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

		boolean hasActors = false;

		ContextInstance contextInstance = ctx.getContextInstance();

		if (StringUtils.isNotEmpty(dynamicActors)) {
			Token token = ctx.getToken();
			TaskMgmtInstance tmi = ctx.getTaskMgmtInstance();
			String actorIdxy = (String) contextInstance
					.getVariable(dynamicActors);
			if (StringUtils.isNotEmpty(actorIdxy)) {
				StringTokenizer st2 = new StringTokenizer(actorIdxy, ",");
				while (st2.hasMoreTokens()) {
					String actorId = st2.nextToken();
					if (StringUtils.isNotEmpty(actorId)) {
						TaskInstance taskInstance = tmi.createTaskInstance(
								task, token);
						taskInstance.setActorId(actorId);
						taskInstance.setCreate(new Date());
						taskInstance.setSignalling(task.isSignalling());
						hasActors = true;
					}
				}
			}
		}

		// 如果没有任务参与者，判断是否可以离开本节点。
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
}