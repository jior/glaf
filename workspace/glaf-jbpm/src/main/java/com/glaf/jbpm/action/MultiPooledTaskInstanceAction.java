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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
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

/**
 * ��̬�������ע�������Ҫ��task-node�ڵ��create-tasks�����Ըĳ�create-tasks="false" ���磺
 * <task-node name="�豸����Ա����" create-tasks="false" end-tasks="true"><br>
 * <event type="node-enter"><br>
 * <action ref-name="multiPooledTaskInstanceAction"/><br>
 * </event><br>
 * <task name="taskxyz" description="ʹ�ò��Ų�������" ></task><br>
 * <transition name="tr005" to="�豸����Ա����ͨ����"></transition><br>
 * </task-node><br>
 */

/**
 * <action name="multiPooledTaskInstanceAction"
 * class="com.glaf.jbpm.action.MultiPooledTaskInstanceAction"><br>
 * <leaveNodeIfActorNotAvailable>true</leaveNodeIfActorNotAvailable><br>
 * <dynamicActors>role_sbgyl</dynamicActors><br>
 * <taskName>taskxyz</taskName><br>
 * </action>
 */

public class MultiPooledTaskInstanceAction implements ActionHandler {
	private static final Log logger = LogFactory
			.getLog(MultiPooledTaskInstanceAction.class);

	private static final long serialVersionUID = 1L;

	/**
	 * ��̬���õĲ����ߵĲ�������������������ͨ��contextInstance.getVariable()ȡ��
	 * ���磺contextInstance.getVariable("SendDocAuditor");
	 */
	String dynamicActors;

	/**
	 * ������ܻ�ȡ����������Ƿ��뿪���ڵ㣨����ڵ㣩
	 */
	boolean leaveNodeIfActorNotAvailable;

	/**
	 * ת��·��������
	 */
	String transitionName;

	/**
	 * ��������
	 */
	String taskName;

	public MultiPooledTaskInstanceAction() {

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
		logger.debug("--------------MultiPooledTaskInstanceAction------------");
		logger.debug("-------------------------------------------------------");

		Task task = ctx.getTask();

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

		boolean hasTaskActor = false;
		Token token = ctx.getToken();
		TaskMgmtInstance tmi = ctx.getTaskMgmtInstance();
		ContextInstance contextInstance = ctx.getContextInstance();

		if (StringUtils.isNotEmpty(dynamicActors)) {
			String actorIdxy = (String) contextInstance
					.getVariable(dynamicActors);
			if (StringUtils.isNotEmpty(actorIdxy)) {
				StringTokenizer st2 = new StringTokenizer(actorIdxy, ",");
				while (st2.hasMoreTokens()) {
					String elem2 = st2.nextToken();
					if (StringUtils.isNotEmpty(elem2)) {
						elem2 = elem2.trim();
						if ((elem2.length() > 0 && elem2.charAt(0) == '{')
								&& elem2.endsWith("}")) {
							elem2 = elem2.substring(elem2.indexOf("{") + 1,
									elem2.indexOf("}"));
							Set<String> actorIds = new HashSet<String>();
							StringTokenizer st4 = new StringTokenizer(elem2,
									",");
							while (st4.hasMoreTokens()) {
								String elem4 = st4.nextToken();
								elem4 = elem4.trim();
								if (elem4.length() > 0) {
									actorIds.add(elem4);
								}
							}
							
							String startActorId = (String) contextInstance
									.getVariable(Constant.PROCESS_STARTERID);
							actorIds.remove(startActorId);
							
							if (actorIds.size() > 0) {
								TaskInstance taskInstance = tmi
										.createTaskInstance(task, token);
								taskInstance.setCreate(new Date());
								taskInstance.setSignalling(task.isSignalling());

								hasTaskActor = true;

								if (actorIds.size() == 1) {
									String actorId = actorIds.iterator().next();
									taskInstance.setActorId(actorId);
								} else {
									int i = 0;
									String[] pooledIds = new String[actorIds
											.size()];
									Iterator<String> iterator2 = actorIds
											.iterator();
									while (iterator2.hasNext()) {
										pooledIds[i++] = iterator2.next();
									}
									taskInstance.setPooledActors(pooledIds);
								}
							}
						}
					}
				}
			}
		}

		// ���û����������ߣ��ж��Ƿ�����뿪���ڵ㡣
		if (!hasTaskActor) {
			if (leaveNodeIfActorNotAvailable) {
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

	public static void main(String[] args) throws Exception {
		String actorIdxy = "{joy,sam},{pp,qq},{kit,cora},{eyb2000,huangcw}";
		StringTokenizer st2 = new StringTokenizer(actorIdxy, ";");
		while (st2.hasMoreTokens()) {
			String elem2 = st2.nextToken();
			if (StringUtils.isNotEmpty(elem2)) {
				elem2 = elem2.trim();
				if ((elem2.length() > 0 && elem2.charAt(0) == '{')
						&& elem2.endsWith("}")) {
					elem2 = elem2.substring(elem2.indexOf("{") + 1,
							elem2.indexOf("}"));
					Set<String> actorIds = new HashSet<String>();
					StringTokenizer st4 = new StringTokenizer(elem2, ",");
					while (st4.hasMoreTokens()) {
						String elem4 = st4.nextToken();
						elem4 = elem4.trim();
						if (elem4.length() > 0) {
							actorIds.add(elem4);
						}
					}
					System.out.println(actorIds);
				}
			}
		}
	}

}