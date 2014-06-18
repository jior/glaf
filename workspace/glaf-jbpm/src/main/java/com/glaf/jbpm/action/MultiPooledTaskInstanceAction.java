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
 * 动态任务产生注意事项：需要将task-node节点的create-tasks的属性改成create-tasks="false" 例如：
 * <task-node name="设备管理员验收" create-tasks="false" end-tasks="true"><br>
 * <event type="node-enter"><br>
 * <action ref-name="multiPooledTaskInstanceAction"/><br>
 * </event><br>
 * <task name="taskxyz" description="使用部门部长审批" ></task><br>
 * <transition name="tr005" to="设备管理员验收通过？"></transition><br>
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

	/**
	 * 动态设置的参与者的参数名，环境变量可以通过contextInstance.getVariable()取得
	 * 例如：contextInstance.getVariable("SendDocAuditor");
	 */
	protected String dynamicActors;

	/**
	 * 如果不能获取任务参与者是否离开本节点（任务节点）
	 */
	protected boolean leaveNodeIfActorNotAvailable;

	/**
	 * 转移路径的名称
	 */
	protected String transitionName;

	/**
	 * 任务名称
	 */
	protected String taskName;

	/**
	 * 是否发送邮件
	 */
	protected String sendMail;

	/**
	 * 邮件标题
	 */
	protected String subject;

	/**
	 * 邮件内容
	 */
	protected String content;

	/**
	 * 任务内容
	 */
	protected String taskContent;

	/**
	 * 邮件模板编号
	 */
	protected String templateId;

	public MultiPooledTaskInstanceAction() {

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
								if (StringUtils.isNotEmpty(sendMail)
										&& StringUtils.equals(sendMail, "true")) {
									MailBean mailBean = new MailBean();
									mailBean.setContent(content);
									mailBean.setSubject(subject);
									mailBean.setTaskContent(taskContent);
									mailBean.setTaskName(taskName);
									mailBean.setTemplateId(templateId);
									mailBean.execute(ctx, actorIds);
								}
							}
						}
					}
				}
			}
		}

		// 如果没有任务参与者，判断是否可以离开本节点。
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

	public void setContent(String content) {
		this.content = content;
	}

	public void setDynamicActors(String dynamicActors) {
		this.dynamicActors = dynamicActors;
	}

	public void setLeaveNodeIfActorNotAvailable(
			boolean leaveNodeIfActorNotAvailable) {
		this.leaveNodeIfActorNotAvailable = leaveNodeIfActorNotAvailable;
	}

	public void setSendMail(String sendMail) {
		this.sendMail = sendMail;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setTaskContent(String taskContent) {
		this.taskContent = taskContent;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public void setTransitionName(String transitionName) {
		this.transitionName = transitionName;
	}

}