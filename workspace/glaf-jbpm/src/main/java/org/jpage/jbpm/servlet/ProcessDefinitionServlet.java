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
