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


package org.jpage.jbpm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.db.GraphSession;
import org.jpage.actor.User;
import org.jpage.jbpm.config.ObjectFactory;
import org.jpage.jbpm.context.Context;
import org.jpage.jbpm.model.DeployInstance;
import org.jpage.jbpm.service.ServiceManager;
import org.jpage.util.RequestUtil;

public class ProcessDefinitionServlet extends HttpServlet {
	private final static Log logger = LogFactory
			.getLog(ProcessDefinitionServlet.class);
	private JbpmConfiguration jbpmConfiguration = JbpmConfiguration
			.getInstance();
	private static final long serialVersionUID = 1L;

	private ServiceManager serviceManager;

	public void init() {
		logger.debug("ProcessDefinitionServlet init...");
	}

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Context.create();
		User user = (User) request.getSession().getAttribute(
				org.jpage.util.Constant.LOGIN_USER);
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Expires", "-1");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0L);
		request.setCharacterEncoding("UTF-8");

		PrintWriter writer = response.getWriter();
		if (user == null) {
			String login_url = ObjectFactory.getLoginUrl();
			response.sendRedirect(login_url);
			return;
		}

		String actionType = request.getParameter("actionType");

		if (actionType == null) {
			actionType = "findAllProcessDefinitions";
		}

		JbpmContext jbpmContext = null;
		
		GraphSession graphSession = null;

		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			jbpmContext.setActorId(user.getActorId());

			RequestUtil.setRequestParameterToAttribute(request);

			graphSession = jbpmContext.getGraphSession();
			if (actionType.equalsIgnoreCase("findAllProcessDefinitions")) {
				List processDefinitions = graphSession
						.findAllProcessDefinitions();
				request.setAttribute("processDefinitions", processDefinitions);
			}

			serviceManager = (ServiceManager) org.jpage.jbpm.context.JbpmContextFactory
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
					deployInstanceMap.put(deployInstance
							.getProcessDefinitionId(), deployInstance);
				}
			}

			request.setAttribute("deployInstanceMap", deployInstanceMap);

			String forward = request.getParameter("forward");
			if (StringUtils.isNotBlank(forward)) {
				request.getRequestDispatcher(forward)
						.forward(request, response);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			writer.write("<font color=\"red\" size=\"3\">应用程序处理发生错误！</font>");
			writer.flush();
			writer.close();
		} finally {
			Context.close(jbpmContext);
		}
	}

}
