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

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.jpage.actor.Actor;
import org.jpage.actor.User;
import org.jpage.jbpm.config.ObjectFactory;
import org.jpage.jbpm.context.JbpmContextFactory;
import org.jpage.jbpm.service.ActorManager;
import org.jpage.jbpm.util.Constant;

public class PooledTaskInstanceAction implements ActionHandler {
	private static final Log logger = LogFactory
			.getLog(PooledTaskInstanceAction.class);

	private static final long serialVersionUID = 1L;

	/**
	 * 动态设置的参与者的参数名，环境变量可以通过contextInstance.getVariable()取得
	 * 例如：contextInstance.getVariable("SendDocAuditor");
	 */
	private String dynamicActors;

	private String taskName;

	private String signalling;

	/**
	 * 如果不能获取任务参与者是否离开本节点（任务节点）
	 */
	private String leaveNodeIfActorNotAvailable;

	private String transitionName;

	private ActorManager actorManager;

	public PooledTaskInstanceAction() {

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

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
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

	public void execute(ExecutionContext ctx) {
		logger.debug("-------------------------------------------------------");
		logger.debug("-----------------PooledTaskInstanceAction---------------");
		logger.debug("-------------------------------------------------------");

		actorManager = (ActorManager) JbpmContextFactory
				.getBean("actorManager");

		Task task = ctx.getTask();

		if (StringUtils.isBlank(taskName)) {
			if (task != null) {
				taskName = task.getName();
			}
		}

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

		ProcessInstance processInstance = contextInstance.getProcessInstance();
		String processName = processInstance.getProcessDefinition().getName();

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
				if (StringUtils.equals("true", leaveNodeIfActorNotAvailable)) {

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
					StringTokenizer st2 = new StringTokenizer(defaultActors,
							",");
					while (st2.hasMoreTokens()) {
						String elem = st2.nextToken();
						if (StringUtils.isNotBlank(elem)) {
							actorIds.add(elem);
						}
					}
				}
			}
		}

		if (actorIds.size() > 0) {

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

			Token token = ctx.getToken();
			TaskMgmtInstance tmi = ctx.getTaskMgmtInstance();
			TaskInstance taskInstance = tmi.createTaskInstance(task, token);
			taskInstance.setCreate(new Date());
			if (StringUtils.equals(signalling, "false")) {
				taskInstance.setSignalling(false);
			} else {
				taskInstance.setSignalling(true);
			}

			if (actorIds.size() == 1) {
				String actorId = (String) actorIds.iterator().next();
				taskInstance.setActorId(actorId);
			} else {
				int i = 0;
				String[] pooledIds = new String[actorIds.size()];
				Iterator iterator2 = actorIds.iterator();
				while (iterator2.hasNext()) {
					pooledIds[i++] = (String) iterator2.next();
				}
				taskInstance.setPooledActors(pooledIds);
			}
		}

	}

}
