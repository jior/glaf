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
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.def.AssignmentHandler;
import org.jbpm.taskmgmt.def.Swimlane;
import org.jbpm.taskmgmt.exe.Assignable;
import org.jpage.actor.Actor;
import org.jpage.jbpm.context.JbpmContextFactory;
import org.jpage.jbpm.service.ActorManager;

/**
 * 任务分配说明： 根据优先级顺序，设置任务的参与者<br>
 * 1、直接从外部获取的动态参与者ID <br>
 * 2、流程任务预定义的参与者<br>
 * 3、流程任务中Swimlane定义的参与者<br>
 * 4、流程定义文件中expression定义的参与者
 * 
 */
public class GeneralAssignment implements AssignmentHandler {
	private static final Log logger = LogFactory
			.getLog(GeneralAssignment.class);

	private static final long serialVersionUID = 1L;

	private ActorManager actorManager;

	private AssignableHelper helper;

	/**
	 * 参与者表达式 user(joy) 表示一个用户 users(joy,jior,huangcw)表示三个用户
	 * role(SystemAdministrator)表示一个角色参与者
	 */
	private String expression;

	/**
	 * 动态设置的参与者的参数名，环境变量可以通过contextInstance.getVariable()取得
	 * 例如：contextInstance.getVariable("SendDocAuditor");
	 */
	private String dynamicActors;

	public GeneralAssignment() {

	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getDynamicActors() {
		return dynamicActors;
	}

	public void setDynamicActors(String dynamicActors) {
		this.dynamicActors = dynamicActors;
	}

	public void assign(Assignable assignable, ExecutionContext ctx) {
		logger.debug("-------------------------------------------------------");
		logger.debug("-------------------GeneralAssignment-------------------");
		logger.debug("-------------------------------------------------------");
		logger.debug("expression:" + expression);

		helper = new AssignableHelper();
		actorManager = (ActorManager) JbpmContextFactory
				.getBean("actorManager");

		/**
		 * 1、直接从外部获取的动态参与者ID
		 */
		ContextInstance contextInstance = ctx.getContextInstance();
		if (StringUtils.isNotBlank(dynamicActors)) {
			String actorId = (String) contextInstance
					.getVariable(dynamicActors);
			if (StringUtils.isNotBlank(actorId)) {
				logger.debug("外部输入的actors:" + actorId);
				helper.setActors(assignable, actorId);
				return;
			}
		}

		String processName = contextInstance.getProcessInstance()
				.getProcessDefinition().getName();
		String taskName = ctx.getTask().getName();

		/**
		 * 2、流程任务预定义的参与者
		 */
		String roleId = processName + "-" + taskName;
		List actors = actorManager.getActors(ctx.getJbpmContext(), roleId);
		logger.debug("roleId:" + roleId);
		logger.debug("预定义的任务参与者:" + actors);
		if (actors != null && actors.size() > 0) {
			if (actors.size() == 1) {
				Actor actor = (Actor) actors.get(0);
				assignable.setActorId(actor.getActorId());
				return;
			}
			int i = 0;
			String[] users = new String[actors.size()];
			Iterator iterator = actors.iterator();
			while (iterator.hasNext()) {
				Actor actor = (Actor) iterator.next();
				users[i++] = actor.getActorId();
			}
			assignable.setPooledActors(users);
			return;
		}

		/**
		 * 3、流程任务中Swimlane定义的参与者
		 */
		Swimlane swimlane = ctx.getTask().getSwimlane();
		if (swimlane != null) {
			roleId = swimlane.getName();
			actors = actorManager.getActors(ctx.getJbpmContext(), roleId);
			logger.debug("获取任务定义的角色。");
			logger.debug(">>roleId:" + roleId);
			logger.debug("任务定义的角色:" + actors);
			if (actors != null && actors.size() > 0) {
				if (actors.size() == 1) {
					Actor actor = (Actor) actors.get(0);
					assignable.setActorId(actor.getActorId());
					return;
				}
				int i = 0;
				String[] users = new String[actors.size()];
				Iterator iterator = actors.iterator();
				while (iterator.hasNext()) {
					Actor actor = (Actor) iterator.next();
					users[i++] = actor.getActorId();
				}
				assignable.setPooledActors(users);
				return;
			}
		}

		Map params = new HashMap();
		ProcessInstance processInstance = ctx.getProcessInstance();
		String processInstanceId = String.valueOf(processInstance.getId());
		params.put("processInstanceId", processInstanceId);

		Map varMap = contextInstance.getVariables();
		if (varMap != null && varMap.size() > 0) {
			Iterator iterator = varMap.keySet().iterator();
			while (iterator.hasNext()) {
				String variableName = (String) iterator.next();
				if (params.get(variableName) == null) {
					Object value = contextInstance.getVariable(variableName);
					params.put(variableName, value);
				}
			}
		}

		/**
		 * 4、流程定义文件中expression定义的参与者
		 */
		if (StringUtils.isNotBlank(expression)) {
			Set actorIds = helper.getActorIds(ctx.getJbpmContext(), expression,
					params);
			helper.setActors(assignable, actorIds);
		}
	}

}
