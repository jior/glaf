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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmContext;
import org.jbpm.db.GraphSession;
import org.jbpm.graph.def.ProcessDefinition;
import org.jpage.actor.User;
import org.jpage.jbpm.context.Context;
import org.jpage.jbpm.context.JbpmContextFactory;
import org.jpage.jbpm.model.DeployInstance;
import org.jpage.jbpm.service.ProcessContainer;
import org.jpage.jbpm.service.ServiceManager;
import org.jpage.jbpm.util.Constant;
import org.jpage.util.RequestUtil;
import org.springframework.web.servlet.ModelAndView;

public class ProcessController implements
		org.springframework.web.servlet.mvc.Controller {
	protected final static Log logger = LogFactory
			.getLog(ProcessController.class);

	public ProcessController() {

	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = (User) request.getSession().getAttribute(
				org.jpage.util.Constant.LOGIN_USER);
		if (user == null) {
			request.setAttribute(Constant.APPLICATION_EXCEPTION_MESSAGE,
					"Not Login!");
			return new ModelAndView("error");
		}

		if (!user.isAdmin()) {
			request.setAttribute(Constant.APPLICATION_EXCEPTION_MESSAGE,
					"Access denied!");
			return new ModelAndView("error");
		}

		RequestUtil.setRequestParameterToAttribute(request);

		String method = request.getParameter("method");
		if (method == null) {
			method = "processes";
		}
		try {

			if (method.equalsIgnoreCase("processes")) {
				return processes(request);
			} else if (method.equalsIgnoreCase("query")) {
				return query(request);
			}

			return processes(request);

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			return new ModelAndView("error");
		}
	}

	public ModelAndView processes(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(
				org.jpage.util.Constant.LOGIN_USER);
		if (user == null) {
			request.setAttribute(Constant.APPLICATION_EXCEPTION_MESSAGE,
					"Not Login!");
			return new ModelAndView("error");
		}

		JbpmContext jbpmContext = null;

		GraphSession graphSession = null;

		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			jbpmContext.setActorId(user.getActorId());

			RequestUtil.setRequestParameterToAttribute(request);

			graphSession = jbpmContext.getGraphSession();

			List processDefinitions = graphSession.findAllProcessDefinitions();
			request.setAttribute("processDefinitions", processDefinitions);

			ServiceManager serviceManager = (ServiceManager) JbpmContextFactory
					.getBean("serviceManager");
			List deployInstances = serviceManager
					.getDeployInstances(jbpmContext);

			request.setAttribute("deployInstances", deployInstances);

			Map deployInstanceMap = new HashMap();

			Iterator iterator = deployInstances.iterator();
			while (iterator.hasNext()) {
				DeployInstance deployInstance = (DeployInstance) iterator
						.next();
				if (deployInstance.getProcessDefinitionId() != null) {
					deployInstanceMap.put(
							deployInstance.getProcessDefinitionId(),
							deployInstance);
				}
			}

			request.setAttribute("deployInstanceMap", deployInstanceMap);
		} catch (Exception ex) {
			logger.debug(ex);
		} finally {
			Context.close(jbpmContext);
		}

		return new ModelAndView("processes");
	}

	public ModelAndView query(HttpServletRequest request) {
		GraphSession graphSession = null;
		JbpmContext jbpmContext = null;
		try {

			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			graphSession = jbpmContext.getGraphSession();

			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			List<ProcessDefinition> processDefinitions = graphSession
					.findAllProcessDefinitions();
			request.setAttribute("processDefinitions", processDefinitions);

		} catch (Exception ex) {
			logger.debug(ex);
		} finally {
			Context.close(jbpmContext);
		}

		return new ModelAndView("query");
	}

}