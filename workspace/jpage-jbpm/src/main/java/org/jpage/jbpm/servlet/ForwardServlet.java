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
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jpage.actor.User;
import org.jpage.jbpm.config.ObjectFactory;
import org.jpage.jbpm.context.Context;
import org.jpage.util.RequestUtil;
import org.jpage.util.Tools;

public class ForwardServlet extends HttpServlet {
	private final static Log logger = LogFactory.getLog(ForwardServlet.class);
	private JbpmConfiguration jbpmConfiguration = JbpmConfiguration
			.getInstance();
	private static final long serialVersionUID = 1L;

	public void init() {
		logger.debug("ForwardServlet init...");
	}

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Context.create();
		String actorId = (String) request.getSession().getAttribute(
				org.jpage.util.Constant.LOGIN_USER_USERNAME);
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Expires", "-1");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0L);
		request.setCharacterEncoding("UTF-8");

		PrintWriter writer = response.getWriter();
		if (StringUtils.isBlank(actorId)) {
			String login_url = ObjectFactory.getLoginUrl();
			response.sendRedirect(login_url);
			return;
		}

		Map params = Tools.getParameters(request);

		User user = new User();
		user.setActorId(actorId);

		long processDefinitionId = Tools.getId(params, "processDefinitionId");
		long processInstanceId = Tools.getId(params, "processInstanceId");
		long taskInstanceId = Tools.getId(params, "taskInstanceId");

		JbpmContext jbpmContext = null;

		GraphSession graphSession = null;

		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			jbpmContext.setActorId(actorId);
			graphSession = jbpmContext.getGraphSession();

			if (processDefinitionId > 0) {
				ProcessDefinition processDefinition = graphSession
						.loadProcessDefinition(processDefinitionId);
				if (processDefinition != null) {
					logger.debug("process definition name : "
							+ processDefinition.getName());
					logger.debug("process definition version : "
							+ processDefinition.getVersion());
					request
							.setAttribute("processDefinition",
									processDefinition);
				}
			}

			if (processInstanceId > 0) {
				ProcessInstance processInstance = jbpmContext
						.loadProcessInstance(processInstanceId);
				if (processInstance != null) {
					request.setAttribute("processInstance", processInstance);
				}
			}

			if (taskInstanceId > 0) {
				TaskInstance taskInstance = jbpmContext
						.loadTaskInstance(taskInstanceId);
				if (taskInstance != null) {
					request.setAttribute("taskInstance", taskInstance);
				}
			}

			RequestUtil.setRequestParameterToAttribute(request);

			String goToPage = request.getParameter("goToPage");
			if (StringUtils.isNotBlank(goToPage)) {
				request.getRequestDispatcher(goToPage).forward(request,
						response);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			writer.write("<font color=\"red\" size=\"3\">Ӧ�ó�������������</font>");
			writer.flush();
			writer.close();
		} finally {
			Context.close(jbpmContext);
		}

	}

}