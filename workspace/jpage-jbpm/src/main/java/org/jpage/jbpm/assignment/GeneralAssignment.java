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
 * �������˵���� �������ȼ�˳����������Ĳ�����<br>
 * 1��ֱ�Ӵ��ⲿ��ȡ�Ķ�̬������ID <br>
 * 2����������Ԥ����Ĳ�����<br>
 * 3������������Swimlane����Ĳ�����<br>
 * 4�����̶����ļ���expression����Ĳ�����
 * 
 */
public class GeneralAssignment implements AssignmentHandler {
	private static final Log logger = LogFactory
			.getLog(GeneralAssignment.class);

	private static final long serialVersionUID = 1L;

	private ActorManager actorManager;

	private AssignableHelper helper;

	/**
	 * �����߱���ʽ user(joy) ��ʾһ���û� users(joy,jior,huangcw)��ʾ�����û�
	 * role(SystemAdministrator)��ʾһ����ɫ������
	 */
	private String expression;

	/**
	 * ��̬���õĲ����ߵĲ�������������������ͨ��contextInstance.getVariable()ȡ��
	 * ���磺contextInstance.getVariable("SendDocAuditor");
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
		 * 1��ֱ�Ӵ��ⲿ��ȡ�Ķ�̬������ID
		 */
		ContextInstance contextInstance = ctx.getContextInstance();
		if (StringUtils.isNotBlank(dynamicActors)) {
			String actorId = (String) contextInstance
					.getVariable(dynamicActors);
			if (StringUtils.isNotBlank(actorId)) {
				logger.debug("�ⲿ�����actors:" + actorId);
				helper.setActors(assignable, actorId);
				return;
			}
		}

		String processName = contextInstance.getProcessInstance()
				.getProcessDefinition().getName();
		String taskName = ctx.getTask().getName();

		/**
		 * 2����������Ԥ����Ĳ�����
		 */
		String roleId = processName + "-" + taskName;
		List actors = actorManager.getActors(ctx.getJbpmContext(), roleId);
		logger.debug("roleId:" + roleId);
		logger.debug("Ԥ��������������:" + actors);
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
		 * 3������������Swimlane����Ĳ�����
		 */
		Swimlane swimlane = ctx.getTask().getSwimlane();
		if (swimlane != null) {
			roleId = swimlane.getName();
			actors = actorManager.getActors(ctx.getJbpmContext(), roleId);
			logger.debug("��ȡ������Ľ�ɫ��");
			logger.debug(">>roleId:" + roleId);
			logger.debug("������Ľ�ɫ:" + actors);
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
		 * 4�����̶����ļ���expression����Ĳ�����
		 */
		if (StringUtils.isNotBlank(expression)) {
			Set actorIds = helper.getActorIds(ctx.getJbpmContext(), expression,
					params);
			helper.setActors(assignable, actorIds);
		}
	}

}