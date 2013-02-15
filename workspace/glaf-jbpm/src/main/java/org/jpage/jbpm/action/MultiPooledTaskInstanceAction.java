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

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
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
import org.jpage.jbpm.util.Constant;

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
 * class="org.jpage.jbpm.action.MultiPooledTaskInstanceAction"><br>
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
	private String dynamicActors;

	private String signalling;

	/**
	 * ������ܻ�ȡ����������Ƿ��뿪���ڵ㣨����ڵ㣩
	 */
	private String leaveNodeIfActorNotAvailable;

	private String taskName;

	private String transitionName;

	public MultiPooledTaskInstanceAction() {

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

	public String getLeaveNodeIfActorNotAvailable() {
		return leaveNodeIfActorNotAvailable;
	}

	public void setLeaveNodeIfActorNotAvailable(
			String leaveNodeIfActorNotAvailable) {
		this.leaveNodeIfActorNotAvailable = leaveNodeIfActorNotAvailable;
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

	public void execute(ExecutionContext ctx) {
		logger.debug("-------------------------------------------------------");
		logger.debug("--------------MultiPooledTaskInstanceAction------------");
		logger.debug("-------------------------------------------------------");

		Task task = ctx.getTask();

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

		boolean hasTaskActor = false;
		Token token = ctx.getToken();
		TaskMgmtInstance tmi = ctx.getTaskMgmtInstance();
		ContextInstance contextInstance = ctx.getContextInstance();

		if (StringUtils.isNotBlank(dynamicActors)) {
			String actorIdxy = (String) contextInstance
					.getVariable(dynamicActors);
			if (StringUtils.isNotBlank(actorIdxy)) {
				StringTokenizer st2 = new StringTokenizer(actorIdxy, ";");
				while (st2.hasMoreTokens()) {
					String elem2 = st2.nextToken();
					if (StringUtils.isNotBlank(elem2)) {
						elem2 = elem2.trim();
						if (elem2.startsWith("{") && elem2.endsWith("}")) {
							elem2 = elem2.substring(elem2.indexOf("{") + 1,
									elem2.indexOf("}"));
							Set actorIds = new HashSet();
							StringTokenizer st4 = new StringTokenizer(elem2,
									",");
							while (st4.hasMoreTokens()) {
								String elem4 = st4.nextToken();
								elem4 = elem4.trim();
								if (elem4.length() > 0) {
									actorIds.add(elem4);
								}
							}
							if (actorIds.size() > 0) {
								TaskInstance taskInstance = tmi
										.createTaskInstance(task, token);
								taskInstance.setCreate(new Date());
								if (StringUtils.equals(signalling, "false")) {
									taskInstance.setSignalling(false);
								} else {
									taskInstance.setSignalling(true);
								}

								hasTaskActor = true;

								if (actorIds.size() == 1) {
									String actorId = (String) actorIds
											.iterator().next();
									taskInstance.setActorId(actorId);
								} else {
									int i = 0;
									String[] pooledIds = new String[actorIds
											.size()];
									Iterator iterator2 = actorIds.iterator();
									while (iterator2.hasNext()) {
										pooledIds[i++] = (String) iterator2
												.next();
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
			if (StringUtils.equals("true", leaveNodeIfActorNotAvailable)) {
				contextInstance.setVariable(Constant.IS_AGREE, "true");
				if (StringUtils.isNotBlank(transitionName)) {
					ctx.leaveNode(transitionName);
				} else {
					ctx.leaveNode();
				}
				if (ctx.getNode() != null) {
					logger.info(ctx.getNode().getName() + "->���ܻ�ȡ��������ߣ��뿪��ǰ�ڵ㡣");
				} else {
					if (ctx.getToken() != null) {
						logger.debug(ctx.getToken().getName()
								+ "->���ܻ�ȡ��������ߣ��뿪��ǰ�ڵ㡣");
					}
				}
				return;
			}
		}

	}

	public static void main(String[] args) {
		String actorIdxy = "{joy,sam};{pp,qq};{kit,cora};{eyb2000,huangcw}";
		StringTokenizer st2 = new StringTokenizer(actorIdxy, ";");
		while (st2.hasMoreTokens()) {
			String elem2 = st2.nextToken();
			if (StringUtils.isNotBlank(elem2)) {
				elem2 = elem2.trim();
				if (elem2.startsWith("{") && elem2.endsWith("}")) {
					elem2 = elem2.substring(elem2.indexOf("{") + 1,
							elem2.indexOf("}"));
					Set actorIds = new HashSet();
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
