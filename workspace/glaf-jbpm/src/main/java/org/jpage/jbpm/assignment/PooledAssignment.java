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

package org.jpage.jbpm.assignment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.node.TaskNode;
import org.jbpm.taskmgmt.def.AssignmentHandler;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.Assignable;
import org.jpage.actor.Actor;
import org.jpage.actor.User;
import org.jpage.jbpm.config.ObjectFactory;
import org.jpage.jbpm.context.JbpmContextFactory;
import org.jpage.jbpm.service.ActorManager;
import org.jpage.jbpm.util.Constant;

/**
 * 任务分配说明： 根据优先级顺序，设置任务的参与者<br>
 * 1、直接从外部获取的动态参与者ID <br>
 * 2、流程任务预定义的参与者<br>
 * 3、流程任务中Swimlane定义的参与者<br>
 * 4、流程定义文件中expression定义的参与者
 * 
 */
public class PooledAssignment implements AssignmentHandler {
	private static final Log logger = LogFactory.getLog(PooledAssignment.class);

	private static final long serialVersionUID = 1L;

	private String taskName;

	/**
	 * 动态设置的参与者的参数名，环境变量可以通过contextInstance.getVariable()取得
	 * 例如：contextInstance.getVariable("SendDocAuditor");
	 */
	private String dynamicActors;

	/**
	 * 如果不能获取任务参与者是否离开本节点（任务节点）
	 */
	private String leaveNodeIfActorNotAvailable;

	private String transitionName;

	private ActorManager actorManager;

	public PooledAssignment() {

	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getDynamicActors() {
		return dynamicActors;
	}

	public void setDynamicActors(String dynamicActors) {
		this.dynamicActors = dynamicActors;
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

	public void assign(Assignable assignable, ExecutionContext ctx) {
		logger.debug("-------------------------------------------------------");
		logger.debug("------------------PooledAssignment---------------------");
		logger.debug("-------------------------------------------------------");

		Task task = null;

		actorManager = (ActorManager) JbpmContextFactory
				.getBean("actorManager");

		if (StringUtils.isNotBlank(taskName)) {
			Node node = ctx.getNode();
			if (node instanceof TaskNode) {
				TaskNode taskNode = (TaskNode) node;
				task = taskNode.getTask(taskName);
			}
		}

		if (task == null) {
			task = ctx.getTask();
			taskName = task.getName();
		}

		if (task != null) {
			ContextInstance contextInstance = ctx.getContextInstance();
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

			ProcessInstance processInstance = contextInstance
					.getProcessInstance();
			String processName = processInstance.getProcessDefinition()
					.getName();

			String roleId = processName + "-" + taskName;
			List actors = actorManager.getActors(ctx.getJbpmContext(), roleId);
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

			if (actorIds.size() == 0) {

				if (StringUtils.isNotBlank(leaveNodeIfActorNotAvailable)) {
					if (StringUtils
							.equals("true", leaveNodeIfActorNotAvailable)) {

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
						StringTokenizer st2 = new StringTokenizer(
								defaultActors, ",");
						while (st2.hasMoreTokens()) {
							String elem = st2.nextToken();
							if (StringUtils.isNotBlank(elem)) {
								actorIds.add(elem);
							}
						}
					}
				}
			}

			logger.debug("actorIds size:" + actorIds.size());

			if (actorIds.size() == 1) {
				String actorId = (String) actorIds.get(0);
				assignable.setActorId(actorId);
				logger.debug("actorId:" + actorId);
			} else if (actorIds.size() > 1) {
				int i = 0;
				String[] array = new String[actorIds.size()];
				Iterator iterator = actorIds.iterator();
				while (iterator.hasNext()) {
					String actorId = (String) iterator.next();
					array[i++] = actorId;
					logger.debug("pooed actorId:" + actorId);
				}
				assignable.setPooledActors(array);
			}

		}
	}

}
