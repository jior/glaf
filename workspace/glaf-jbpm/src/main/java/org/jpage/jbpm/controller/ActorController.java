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


package org.jpage.jbpm.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jpage.actor.Actor;
import org.jpage.actor.User;
import org.jpage.jbpm.context.Context;
import org.jpage.jbpm.context.JbpmContextFactory;
import org.jpage.jbpm.model.AgentInstance;
import org.jpage.jbpm.service.ActorManager;
import org.jpage.jbpm.util.Constant;
import org.jpage.util.RequestUtil;
import org.jpage.util.Tools;
import org.jpage.util.UUID32;
import org.springframework.web.servlet.ModelAndView;

public class ActorController implements
		org.springframework.web.servlet.mvc.Controller {
	private final static Log logger = LogFactory.getLog(ActorController.class);

	private JbpmConfiguration jbpmConfiguration = JbpmConfiguration
			.getInstance();

	private ActorManager actorManager;

	public ActorController() {
		actorManager = (ActorManager) JbpmContextFactory
				.getBean("actorManager");
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = (User) request.getSession().getAttribute(
				org.jpage.util.Constant.LOGIN_USER);
		if (user == null) {
			request.setAttribute(Constant.APPLICATION_EXCEPTION_MESSAGE,
					"您没有登录或会话已经超时，请重新登录！");
			return new ModelAndView("error");
		}

		String method = request.getParameter("method");
		if (method == null) {
			method = "actorList";
		}
		try {

			if (method.equalsIgnoreCase("save")) {
				return save(request, response);
			} else if (method.equalsIgnoreCase("saveAgents")) {
				return saveAgents(request, response);
			} else if (method.equalsIgnoreCase("chooseUser")) {
				return chooseUser(request, response);
			} else if (method.equalsIgnoreCase("chooseAgents")) {
				return chooseAgents(request, response);
			} else if (method.equalsIgnoreCase("actorList")) {
				return actorList(request, response);
			} else if (method.equalsIgnoreCase("agentList")) {
				return agentList(request, response);
			}

			return actorList(request, response);

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			return new ModelAndView("error");
		}
	}

	public ModelAndView chooseUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			String roleId = request.getParameter("roleId");
			List actorIds = actorManager.getActorIds(jbpmContext, roleId);
			StringBuffer selected = new StringBuffer();
			StringBuffer noselected = new StringBuffer();

			Map userMap = actorManager.getUserMap(jbpmContext);
			if (userMap != null) {
				Iterator iterator = userMap.keySet().iterator();
				while (iterator.hasNext()) {
					String userId = (String) iterator.next();
					User user = (User) userMap.get(userId);
					if (actorIds != null && actorIds.contains(userId)) {
						selected.append("<option value=\"")
								.append(user.getActorId()).append("\">")
								.append(user.getActorId()).append(" [")
								.append(user.getName()).append("]</option>");
					} else {
						noselected.append("<option value=\"")
								.append(user.getActorId()).append("\">")
								.append(user.getActorId()).append(" [")
								.append(user.getName()).append("]</option>");
					}
				}
			}

			RequestUtil.setRequestParameterToAttribute(request);

			request.setAttribute("selectedScript", selected.toString());
			request.setAttribute("noselectedScript", noselected.toString());

			return new ModelAndView("chooseUser");
		} catch (Exception ex) {
			logger.error(ex);
			return new ModelAndView("error");
		} finally {
			Context.close(jbpmContext);
		}
	}

	public ModelAndView chooseAgents(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String actorId = request.getParameter("actorId");
		String objectValue = request.getParameter("objectValue");
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			List agentIds = null;
			if (StringUtils.isNotBlank(objectValue)) {
				Map params = new HashMap();
				params.put("objectId", "agent");
				params.put("actorId", actorId);
				params.put("objectValue", objectValue);
				agentIds = actorManager.getAgentIds(jbpmContext, params);
			} else {
				agentIds = actorManager.getAgentIds(jbpmContext, actorId);
			}

			StringBuffer selected = new StringBuffer();
			StringBuffer noselected = new StringBuffer();

			Map userMap = actorManager.getUserMap(jbpmContext);
			if (userMap != null) {
				Iterator iterator = userMap.keySet().iterator();
				while (iterator.hasNext()) {
					String userId = (String) iterator.next();
					User user = (User) userMap.get(userId);
					if (StringUtils.equals(actorId, user.getActorId())) {
						continue;
					}
					if (agentIds != null && agentIds.contains(userId)) {
						selected.append("<option value=\"")
								.append(user.getActorId()).append("\">")
								.append(user.getActorId()).append(" [")
								.append(user.getName()).append("]</option>");
					} else {
						noselected.append("<option value=\"")
								.append(user.getActorId()).append("\">")
								.append(user.getActorId()).append(" [")
								.append(user.getName()).append("]</option>");
					}
				}
			}

			RequestUtil.setRequestParameterToAttribute(request);

			request.setAttribute("selectedScript", selected.toString());
			request.setAttribute("noselectedScript", noselected.toString());

			return new ModelAndView("chooseAgents");
		} catch (Exception ex) {
			logger.error(ex);
			return new ModelAndView("error");
		} finally {
			Context.close(jbpmContext);
		}
	}

	public ModelAndView save(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String objectIds = request.getParameter("objectIds");
		List userIds = Tools.splitString(objectIds, ";");
		String roleId = request.getParameter("roleId");
		String objectId = request.getParameter("objectId");
		String objectValue = request.getParameter("objectValue");
		List actors = new ArrayList();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			Map userMap = actorManager.getUserMap(jbpmContext);
			if (userMap != null) {
				if (userIds != null && userIds.size() > 0) {
					Iterator iterator = userIds.iterator();
					while (iterator.hasNext()) {
						String userId = (String) iterator.next();
						if (userMap.get(userId) != null) {
							User user = (User) userMap.get(userId);
							String id = UUID32.getUUID();
							Actor actor = new Actor();
							actor.setId(id);
							actor.setActorId(userId);
							actor.setName(user.getName());
							actor.setObjectId(objectId);
							actor.setObjectValue(objectValue);
							actors.add(actor);
						}
					}
				}
			}
			logger.debug("userIds:" + userIds);
			// logger.debug("userMap:" + userMap.size());
			logger.debug("保存参与者:" + actors.size());
			actorManager.saveActors(jbpmContext, roleId, actors);
		} catch (Exception ex) {
			logger.error(ex);
			return new ModelAndView("error");
		} finally {
			Context.close(jbpmContext);
		}
		return actorList(request, response);

	}

	public ModelAndView saveAgents(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String actorId = request.getParameter("actorId");
		String objectId = request.getParameter("objectId");
		String objectValue = request.getParameter("objectValue");
		String objectIds = request.getParameter("objectIds");
		List agentIds = Tools.splitString(objectIds, ";");
		List agents = new ArrayList();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			Map userMap = actorManager.getUserMap(jbpmContext);
			if (userMap != null) {
				if (agentIds != null && agentIds.size() > 0) {
					Iterator iterator = agentIds.iterator();
					while (iterator.hasNext()) {
						String agentId = (String) iterator.next();
						if (userMap.get(agentId) != null) {
							String id = UUID32.getUUID();
							AgentInstance agent = new AgentInstance();
							agent.setId(id);
							agent.setAgentId(agentId);
							agent.setObjectId(objectId);
							agent.setObjectValue(objectValue);
							agents.add(agent);
						}
					}
				}
			}
			logger.debug("agentIds:" + agentIds);
			// logger.debug("userMap:" + userMap.size());
			logger.debug("保存代理人:" + agents.size());
			actorManager.saveAgents(jbpmContext, actorId, agents, objectValue);
		} catch (Exception ex) {
			logger.error(ex);
			return new ModelAndView("error");
		} finally {
			Context.close(jbpmContext);
		}
		return agentList(request, response);
	}

	public ModelAndView actorList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String roleId = request.getParameter("roleId");
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			List actors = actorManager.getActors(jbpmContext, roleId);
			List users = new ArrayList();
			Map userMap = actorManager.getUserMap(jbpmContext);
			RequestUtil.setRequestParameterToAttribute(request);
			if (actors != null && actors.size() > 0) {
				Iterator iterator = actors.iterator();
				while (iterator.hasNext()) {
					Actor actor = (Actor) iterator.next();
					User user = (User) userMap.get(actor.getActorId());
					users.add(user);
				}
			}
			request.setAttribute("users", users);
			request.setAttribute("userMap", userMap);
			return new ModelAndView("actorList");
		} catch (Exception ex) {
			logger.error(ex);
			return new ModelAndView("error");
		} finally {
			Context.close(jbpmContext);
		}
	}

	public ModelAndView agentList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String objectValue = request.getParameter("objectValue");
		Map params = new HashMap();

		params.put("objectId", "agent");
		params.put("objectValue", "jbpm");

		if (StringUtils.isNotBlank(objectValue)) {
			params.put("objectValue", objectValue);
		}

		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();

			Map userMap = actorManager.getUserMap(jbpmContext);

			List agents = actorManager.getAgents(jbpmContext, params);

			Collection users = new ArrayList();
			if (userMap != null && userMap.size() > 0) {
				Iterator iterator = userMap.values().iterator();
				while (iterator.hasNext()) {
					User user = (User) iterator.next();
					Map properties = new HashMap();
					if (agents != null && agents.size() > 0) {
						Iterator iter = agents.iterator();
						while (iter.hasNext()) {
							AgentInstance agent = (AgentInstance) iter.next();
							if (StringUtils.equals(agent.getActorId(),
									user.getActorId())) {
								User u = (User) userMap.get(agent.getAgentId());
								properties.put(agent.getAgentId(), u);
							}
						}
					}
					user.setProperties(properties);
					users.add(user);
				}
			}

			RequestUtil.setRequestParameterToAttribute(request);
			request.setAttribute("users", users);
			request.setAttribute("agents", agents);
			request.setAttribute("userMap", userMap);
			return new ModelAndView("agentList");
		} catch (Exception ex) {
			logger.error(ex);
			return new ModelAndView("error");
		} finally {
			Context.close(jbpmContext);
		}
	}

}