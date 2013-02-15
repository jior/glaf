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

package org.jpage.jbpm.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jpage.actor.User;
import org.jpage.core.query.paging.Page;
import org.jpage.jbpm.context.Context;
import org.jpage.jbpm.service.MessageManager;
import org.jpage.jbpm.util.Constant;
import org.jpage.util.RequestUtil;
import org.springframework.web.servlet.ModelAndView;
 

public class MessageController implements org.springframework.web.servlet.mvc.Controller {
	private final static Log logger = LogFactory.getLog(ActorController.class);

	private JbpmConfiguration jbpmConfiguration = JbpmConfiguration
			.getInstance();

	private MessageManager messageManager;

	public MessageController() {
		messageManager = (MessageManager) org.jpage.jbpm.context.JbpmContextFactory
				.getBean("messageManager");
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
			method = "messageList";
		}

		return messageList(request, response);
	}

	public ModelAndView messageList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map params = RequestUtil.getQueryParams(request);
		int currPageNo = 1;
		String temp = request.getParameter(Page.PAGENO_PARAMNAME);
		if ((temp != null) && (temp.length() > 0)) {
			try {
				currPageNo = Integer.parseInt(temp);
			} catch (NumberFormatException e) {
			}
		}
		Page jpage = null;
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			jpage = messageManager.getPageMessageInstances(jbpmContext,
					currPageNo, -1, params);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			return new ModelAndView("error");
		} finally {
			Context.close(jbpmContext);
		}

		RequestUtil.setRequestParameterToAttribute(request);
		request.setAttribute("jpage", jpage);

		String view = request.getParameter("view");
		if (StringUtils.isNotBlank(view)) {
			return new ModelAndView(view);
		}

		return new ModelAndView("/message/list");
	}

}