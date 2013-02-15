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
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmContext;
import org.jbpm.taskmgmt.exe.Assignable;
import org.jpage.actor.Actor;
import org.jpage.actor.User;
import org.jpage.jbpm.context.JbpmContextFactory;
import org.jpage.jbpm.el.DefaultExpressionEvaluator;
import org.jpage.jbpm.service.ActorManager;

public class AssignableHelper {
	private static final Log logger = LogFactory.getLog(AssignableHelper.class);

	private ActorManager actorManager;

	public AssignableHelper() {
		actorManager = (ActorManager) JbpmContextFactory
				.getBean("actorManager");
	}

	public void setActors(Assignable assignable, String actorId) {
		if (StringUtils.isBlank(actorId)) {
			throw new RuntimeException(" actorId is null ");
		}
		if (actorId.indexOf(",") > 0) {
			Set actorIds = new HashSet();
			StringTokenizer token = new StringTokenizer(actorId, ",");
			while (token.hasMoreTokens()) {
				String elem = token.nextToken();
				if (StringUtils.isNotBlank(elem)) {
					actorIds.add(elem);
				}
			}
			if (actorIds.size() > 0) {
				int i = 0;
				String[] users = new String[actorIds.size()];
				Iterator iterator = actorIds.iterator();
				while (iterator.hasNext()) {
					users[i++] = (String) iterator.next();
				}
				assignable.setPooledActors(users);
			}
		} else {
			assignable.setActorId(actorId);
		}
	}

	public void setActors(Assignable assignable, Set actorIds) {
		if (actorIds != null && actorIds.size() > 0) {
			if (actorIds.size() == 1) {
				assignable.setActorId((String) actorIds.iterator().next());
			} else {
				int i = 0;
				String[] users = new String[actorIds.size()];
				Iterator iterator = actorIds.iterator();
				while (iterator.hasNext()) {
					users[i++] = (String) iterator.next();
				}
				assignable.setPooledActors(users);
			}
		} else {
			throw new RuntimeException(" actorIds is null ");
		}
	}

	public Set getActorIds(JbpmContext jbpmContext, String expression,
			Map params) {
		Set actorIds = new HashSet();
		/**
		 * 流程定义文件中expression定义的参与者
		 */
		if (StringUtils.isNotBlank(expression)) {
			expression = expression.trim();
			if (logger.isDebugEnabled()) {
				logger.debug("获取流程定义文件中定义的参与者表达式。");
				logger.debug(">>expression:" + expression);
			}
			if (expression.startsWith("user(") && expression.endsWith(")")) {
				// 单个用户
				String user = expression.substring(5, expression.length() - 1);
				if (StringUtils.isNotBlank(user)) {
					actorIds.add(user);
					if (logger.isDebugEnabled()) {
						logger.debug("取得单个用户 ：" + user);
					}
				}
			} else if (expression.startsWith("users(")
					&& expression.endsWith(")")) {
				// 多个用户
				String users = expression.substring(6, expression.length() - 1);
				if (StringUtils.isNotBlank(users)) {
					StringTokenizer token = new StringTokenizer(users, ",");
					while (token.hasMoreTokens()) {
						String elem = token.nextToken();
						if (StringUtils.isNotBlank(elem)) {
							actorIds.add(elem);
						}
					}
				}
			} else if (expression.startsWith("role(")
					&& expression.endsWith(")")) {
				// 角色
				String roleId = expression
						.substring(5, expression.length() - 1);
				List actors = actorManager.getActors(jbpmContext, roleId);
				if (logger.isDebugEnabled()) {
					logger.debug(">>role name:" + roleId);
					logger.debug(">>actors:" + actors);
				}
				if (actors != null && actors.size() > 0) {
					Iterator iterator = actors.iterator();
					while (iterator.hasNext()) {
						Actor actor = (Actor) iterator.next();
						actorIds.add(actor.getActorId());
					}
				}
			} else {
				// 参与者表达式
				String expr = expression.trim();
				if ((expr.startsWith("#{") && expr.endsWith("}"))
						|| (expr.startsWith("${") && expr.endsWith("}"))) {
					Object obj = DefaultExpressionEvaluator.evaluate(expr,
							params);
					if (obj != null) {
						String actorId = this.populateActorId(obj);
						if (StringUtils.isNotBlank(actorId)) {
							if (logger.isDebugEnabled()) {
								logger.debug("通过表达式计算的actorId:" + actorId);
							}
							actorIds.add(actorId);
						}
					}
				}
			}
		}
		return actorIds;
	}

	public String populateActorId(Object obj) {
		String actorId = null;
		if (obj instanceof String) {
			actorId = (String) obj;
		} else if (obj instanceof org.jpage.actor.Actor) {
			Actor actor = (Actor) obj;
			actorId = actor.getActorId();
		} else if (obj instanceof org.jpage.actor.User) {
			User user = (User) obj;
			actorId = user.getActorId();
		} else {
			throw new RuntimeException(
					"actor object must instanceof String, org.jpage.actor.Actor or org.jpage.actor.User");
		}
		return actorId;
	}

}
