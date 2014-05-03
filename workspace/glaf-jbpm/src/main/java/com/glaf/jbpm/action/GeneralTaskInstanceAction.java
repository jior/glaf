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
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmException;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.graph.node.TaskNode;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;

import com.glaf.jbpm.config.JbpmObjectFactory;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.util.Constant;

/**
 * �������˵���� �������ȼ�˳����������Ĳ�����<br>
 * 1��ֱ�Ӵ��ⲿ��ȡ�Ķ�̬������ID <br>
 * 2����������Ԥ����Ĳ�����<br>
 * 
 */
public class GeneralTaskInstanceAction implements ActionHandler {
	private static final Log logger = LogFactory
			.getLog(GeneralTaskInstanceAction.class);

	private static final long serialVersionUID = 1L;

	/**
	 * ��̬���õĲ����ߵĲ�������������������ͨ��contextInstance.getVariable()ȡ��
	 * ���磺contextInstance.getVariable("SendDocAuditor");
	 */
	String dynamicActors;

	/**
	 * ��ɫ���
	 */
	String roleId;

	/**
	 * ת��·��������
	 */
	String transitionName;

	/**
	 * ��������
	 */
	String taskName;

	/**
	 * ������������ֻҪ��һ��ͨ���Ϳ��Եģ����ø�ֵΪtrue
	 */
	boolean isPooled;

	/**
	 * ������ܻ�ȡ����������Ƿ��뿪���ڵ㣨����ڵ㣩
	 */
	boolean leaveNodeIfActorNotAvailable;

	public GeneralTaskInstanceAction() {

	}

	public String getDynamicActors() {
		return dynamicActors;
	}

	public void setDynamicActors(String dynamicActors) {
		this.dynamicActors = dynamicActors;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getTransitionName() {
		return transitionName;
	}

	public void setTransitionName(String transitionName) {
		this.transitionName = transitionName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public boolean isPooled() {
		return isPooled;
	}

	public void setPooled(boolean isPooled) {
		this.isPooled = isPooled;
	}

	public boolean isLeaveNodeIfActorNotAvailable() {
		return leaveNodeIfActorNotAvailable;
	}

	public void setLeaveNodeIfActorNotAvailable(
			boolean leaveNodeIfActorNotAvailable) {
		this.leaveNodeIfActorNotAvailable = leaveNodeIfActorNotAvailable;
	}

	public void execute(ExecutionContext ctx) throws Exception {
		logger.debug("-------------------------------------------------------");
		logger.debug("--------------GeneralTaskInstanceAction----------------");
		logger.debug("-------------------------------------------------------");

		ContextInstance contextInstance = ctx.getContextInstance();

		List<String> actorIds = new java.util.ArrayList<String>();

		/**
		 * 1��ֱ�Ӵ��ⲿ��ȡ�Ķ�̬������ID
		 */
		if (StringUtils.isNotEmpty(dynamicActors)) {
			String actorId = (String) contextInstance
					.getVariable(dynamicActors);
			if (StringUtils.isNotEmpty(actorId)) {
				StringTokenizer st2 = new StringTokenizer(actorId, ",");
				while (st2.hasMoreTokens()) {
					String elem = st2.nextToken();
					if (StringUtils.isNotEmpty(elem)) {
						actorIds.add(elem);
					}
				}
			}
		}

		String processName = contextInstance.getProcessInstance()
				.getProcessDefinition().getName();

		/**
		 * 2����������Ԥ����Ĳ�����
		 */

		if (StringUtils.isEmpty(roleId)) {
			roleId = processName + "_" + taskName;
		}

		Map<String, Object> paramMap = new java.util.HashMap<String, Object>();

		paramMap.put("roleId", roleId);

		List<String> x_actorIds = ProcessContainer.getContainer()
				.getMembershipActorIds(paramMap);

		if (x_actorIds != null && x_actorIds.size() > 0) {
			actorIds.addAll(x_actorIds);
		}

		if (actorIds.size() == 0 && JbpmObjectFactory.isDefaultActorEnable()) {
			String defaultActors = JbpmObjectFactory.getDefaultActors();
			if (StringUtils.isNotEmpty(defaultActors)) {
				StringTokenizer st2 = new StringTokenizer(defaultActors, ",");
				while (st2.hasMoreTokens()) {
					String elem = st2.nextToken();
					if (StringUtils.isNotEmpty(elem)) {
						actorIds.add(elem);
					}
				}
			}
		}

		if (actorIds.size() > 0) {

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

			Token token = ctx.getToken();
			TaskMgmtInstance tmi = ctx.getTaskMgmtInstance();

			if (actorIds.size() == 1) {
				String actorId = actorIds.get(0);
				TaskInstance taskInstance = tmi.createTaskInstance(task, token);
				taskInstance.setActorId(actorId);
				taskInstance.setCreate(new Date());
				taskInstance.setSignalling(task.isSignalling());
			} else if (actorIds.size() > 1) {
				if (!isPooled) {
					Iterator<String> iterator = actorIds.iterator();
					while (iterator.hasNext()) {
						String actorId = iterator.next();
						TaskInstance taskInstance = tmi.createTaskInstance(
								task, token);
						taskInstance.setActorId(actorId);
						taskInstance.setCreate(new Date());
						taskInstance.setSignalling(task.isSignalling());
					}
				} else {
					int i = 0;
					String[] pooledIds = new String[actorIds.size()];
					Iterator<String> iterator2 = actorIds.iterator();
					while (iterator2.hasNext()) {
						pooledIds[i++] = iterator2.next();
					}
					TaskInstance taskInstance = tmi.createTaskInstance(task,
							token);
					taskInstance.setCreate(new Date());
					taskInstance.setSignalling(task.isSignalling());
					taskInstance.setPooledActors(pooledIds);
				}
			}
		} else {
			if (leaveNodeIfActorNotAvailable) {
				contextInstance.setVariable(Constant.IS_AGREE, "true");
				if (StringUtils.isNotEmpty(transitionName)) {
					ctx.leaveNode(transitionName);
				} else {
					ctx.leaveNode();
				}
				return;
			}
			throw new JbpmException(" actorId is null ");
		}
	}

}