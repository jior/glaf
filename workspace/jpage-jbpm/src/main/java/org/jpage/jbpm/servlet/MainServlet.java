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
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jpage.actor.User;
import org.jpage.context.ApplicationContext;
import org.jpage.jbpm.config.ObjectFactory;
import org.jpage.jbpm.context.Context;
import org.jpage.jbpm.service.ProcessContainer;
import org.jpage.jbpm.util.ResourceType;
import org.jpage.util.RequestUtil;
import org.jpage.util.Tools;

public class MainServlet extends HttpServlet {

	private final static Log logger = LogFactory.getLog(MainServlet.class);
	 
	private final static String newline = System.getProperty("line.separator");

	private static final long serialVersionUID = 1L;

	public void init() {
		logger.debug("MainServlet init...");
		String appPath = this.getServletContext().getRealPath("");
		if (!(appPath.endsWith("/") || appPath.endsWith("\\"))) {
			if (appPath.indexOf("/") != -1) {
				appPath = appPath + "/";
			} else {
				appPath = appPath + "\\";
			}
		}

		logger.debug("root path:" + appPath);
		ApplicationContext.setAppPath(appPath);

	}

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		logger.debug("MainServlet start process............................");
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
			logger.warn("not login......");
			String login_url = ObjectFactory.getLoginUrl();
			response.sendRedirect(login_url);
			return;
		}

		request.getSession().removeAttribute(ResourceType.TASK_FORWARD_PAGE);

		Map params = Tools.getParameters(request);
		RequestUtil.setRequestParameterToAttribute(request);
		request.setAttribute(org.jpage.jbpm.util.Constant.CONTEXT_MAP, params);

		String businessKey = request.getParameter("businessKey");
		String businessValue = request.getParameter("businessValue");

		if (StringUtils.isNotBlank(businessKey)
				&& StringUtils.isNotBlank(businessValue)) {
			request.getSession().setAttribute("businessKey", businessKey);
			request.getSession().setAttribute("businessValue", businessValue);
		}

		long taskInstanceId = Tools.getLong(params, "taskInstanceId");
		String taskName = null;
		String processName = null;

		JbpmContext jbpmContext = null;

		TaskInstance taskInstance = null;
		ProcessDefinition processDefinition = null;
		long processInstanceId = -1;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			jbpmContext.setActorId(user.getActorId());

			if (taskInstanceId > 0) {
				taskInstance = jbpmContext.loadTaskInstance(taskInstanceId);
			}

			if (taskInstance == null) {
				request.setAttribute(
						org.jpage.jbpm.util.Constant.APPLICATION_EXCEPTION_MESSAGE,
						"不存在该流程实例!");
				request.getRequestDispatcher(
						"/WEB-INF/pages/workflow/error.jsp").forward(request,
						response);
				return;
			}

			ProcessInstance processInstance = taskInstance.getToken()
					.getProcessInstance();
			processDefinition = processInstance.getProcessDefinition();
			processName = processDefinition.getName();

			processInstanceId = processInstance.getId();
			request.setAttribute("processInstanceId",
					String.valueOf(processInstanceId));
			request.setAttribute("processName", processName);
			request.setAttribute("processInstance", processInstance);
			request.setAttribute("processDefinition", processDefinition);

			request.setAttribute("taskInstanceId",
					String.valueOf(taskInstanceId));
			request.setAttribute("taskInstance", taskInstance);

			request.setAttribute("transitions",
					taskInstance.getAvailableTransitions());

			/**
			 * 获取任务
			 */
			Task task = taskInstance.getTask();
			if (task != null) {
				taskName = task.getName();
				request.setAttribute("task", task);
				request.setAttribute("taskName", taskName);
				logger.debug("task name:" + task.getName());
				logger.debug("task description:" + task.getDescription());
			}

			// 加载页头信息
			loadFormHeader(request, response);

			// 加载页脚信息
			loadFormFooter(request, response);

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("***********************ERROR***********************");
			logger.error(ex);
			logger.error("-----------------------params----------------------");
			logger.error(params);
			writer.write("<font color=\"red\" size=\"3\">服务器内部错误，请与系统管理员联系！</font>");
			writer.flush();
			writer.close();
		} finally {
			Context.close(jbpmContext);
		}
	}

	private void loadFormHeader(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.getRequestDispatcher("/WEB-INF/pages/workflow/header.jsp")
				.include(request, response);
		Map contextMap = (Map) request
				.getAttribute(org.jpage.jbpm.util.Constant.CONTEXT_MAP);
		if (contextMap != null) {
			StringBuffer buffer = new StringBuffer();
			Iterator iterator = contextMap.keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				String value = (String) contextMap.get(key);
				if (!key.equalsIgnoreCase("actionType")
						&& StringUtils.isNotBlank(value)) {
					value = RequestUtil.encodeString(value);
					buffer.append(
							"<input type=\"hidden\" name=\"process_request_")
							.append(key).append("\" value=\"").append(value)
							.append("\">");
					buffer.append(newline);
				}
			}
			PrintWriter writer = response.getWriter();
			writer.write(buffer.toString());
			writer.flush();
		}
	}

	private void loadFormFooter(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.getRequestDispatcher("/WEB-INF/pages/workflow/footer.jsp")
				.include(request, response);
	}

}
