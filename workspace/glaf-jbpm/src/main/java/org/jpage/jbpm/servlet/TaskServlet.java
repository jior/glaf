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

package org.jpage.jbpm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jpage.actor.User;
import org.jpage.jbpm.config.ObjectFactory;
import org.jpage.jbpm.context.Context;
import org.jpage.jbpm.service.ServiceManager;
import org.jpage.util.QueryTool;
import org.jpage.util.RequestUtil;
import org.jpage.util.Tools;

public class TaskServlet extends HttpServlet {
	private final static Log logger = LogFactory.getLog(TaskServlet.class);
	private JbpmConfiguration jbpmConfiguration = JbpmConfiguration
			.getInstance();
	private static final long serialVersionUID = 1L;

	private static final int MAX_TASK_SIZE = 200;

	private ServiceManager serviceManager;

	public void init() {
		logger.debug("TaskServlet init...");
		Context.create();
		serviceManager = (ServiceManager) org.jpage.jbpm.context.JbpmContextFactory
				.getBean("serviceManager");
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			List processes = jbpmContext.getGraphSession()
					.findLatestProcessDefinitions();
			if (processes != null && processes.size() > 0) {
				logger.debug("总共流程数:" + processes.size());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			Context.close(jbpmContext);
		}
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
		User user = new User();
		user.setActorId(actorId);

		JbpmContext jbpmContext = null;
		GraphSession graphSession = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			graphSession = jbpmContext.getGraphSession();

			List processDefinitions = graphSession
					.findLatestProcessDefinitions();

			List taskInstances = jbpmContext.getTaskList(actorId);

			Set processInstanceIds = new HashSet();

			if (taskInstances != null && taskInstances.size() > 0) {
				Iterator iterator = taskInstances.iterator();
				while (iterator.hasNext()) {
					if (processInstanceIds.size() > MAX_TASK_SIZE) {
						break;
					}
					TaskInstance ti = (TaskInstance) iterator.next();
					if (ti.getToken() == null) {
						continue;
					}
					processInstanceIds.add(String.valueOf(ti.getToken()
							.getProcessInstance().getId()));
				}
			}

			if (processInstanceIds.size() < MAX_TASK_SIZE) {
				List actorIds = new ArrayList();
				actorIds.add(actorId);
				List pooledTaskInstances = jbpmContext
						.getGroupTaskList(actorIds);
				if (pooledTaskInstances != null
						&& pooledTaskInstances.size() > 0) {
					Iterator iterator = pooledTaskInstances.iterator();
					while (iterator.hasNext()) {
						if (processInstanceIds.size() > MAX_TASK_SIZE) {
							break;
						}
						TaskInstance ti = (TaskInstance) iterator.next();
						if (ti.getToken() == null) {
							continue;
						}
						ti.getToken().getProcessInstance().getId();
						processInstanceIds.add(String.valueOf(ti.getToken()
								.getProcessInstance().getId()));
					}
					request.setAttribute("pooled-tasks", pooledTaskInstances);
				}
			}

		 
			request.setAttribute("tasks", taskInstances);

			request.setAttribute("processDefinitions", processDefinitions);
			 
			RequestUtil.setRequestParameterToAttribute(request);
			Map params = Tools.getParameters(request);

			String forward = request.getParameter("forward");
			if (StringUtils.isNotBlank(forward) && forward.length() > 5) {
				forward = QueryTool.replaceTextParas(forward, params);
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
