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

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.def.AssignmentHandler;
import org.jbpm.taskmgmt.exe.Assignable;
import org.jpage.actor.Actor;
import org.jpage.jbpm.config.ObjectFactory;
import org.jpage.jbpm.context.JbpmContextFactory;
import org.jpage.jbpm.service.ActorManager;
import org.jpage.jbpm.util.Constant;

public class PooledRoleAssignment implements AssignmentHandler {

	private static final Log logger = LogFactory
			.getLog(PooledRoleAssignment.class);

	private static final long serialVersionUID = 1L;

	/**
	 * 任务分配的角色列表，多个角色之间以","隔开
	 */
	private String roleIds;

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

	private AssignableHelper helper;

	private ActorManager actorManager;

	public PooledRoleAssignment() {

	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
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

	public void assign(Assignable assignable, ExecutionContext ctx)
			throws Exception {
		logger.debug("--------------------------------------------------------");
		logger.debug("-------------PooledActorsAssignmentHandler--------------");
		logger.debug("--------------------------------------------------------");

		helper = new AssignableHelper();
		actorManager = (ActorManager) JbpmContextFactory
				.getBean("actorManager");

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

		Set actorIds = new HashSet();

		if (StringUtils.isNotBlank(roleIds)) {
			logger.debug("roleIds:" + roleIds);
			Set roles = new HashSet();
			StringTokenizer token = new StringTokenizer(roleIds, ",");
			while (token.hasMoreTokens()) {
				String elem = token.nextToken();
				if (StringUtils.isNotBlank(elem)) {
					roles.add(elem);
				}
			}

			List actors = actorManager.getActors(ctx.getJbpmContext(), roles);
			if (actors != null && actors.size() > 0) {
				Iterator iterator = actors.iterator();
				while (iterator.hasNext()) {
					Actor actor = (Actor) iterator.next();
					actorIds.add(actor.getActorId());
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

		logger.debug("预定义的任务参与者:" + actorIds);

		if (actorIds.size() > 0) {
			int i = 0;
			String[] pooledActorIds = new String[actorIds.size()];
			Iterator iterator = actorIds.iterator();
			while (iterator.hasNext()) {
				pooledActorIds[i++] = (String) iterator.next();
			}
			assignable.setPooledActors(pooledActorIds);
		}

	}

	public static void main(String[] args) {
		StringTokenizer token = new StringTokenizer("role001,role003", ",");
		while (token.hasMoreTokens()) {
			String elem = token.nextToken();
			if (StringUtils.isNotBlank(elem)) {
				logger.debug(elem);
			}
		}
	}

}
