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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 
import org.jbpm.JbpmContext;
import org.jpage.actor.User;
import org.jpage.core.query.paging.Page;
import org.jpage.jbpm.context.Context;
import org.jpage.jbpm.service.MessageManager;
import org.jpage.jbpm.service.ProcessContainer;
import org.jpage.jbpm.util.Constant;
import org.jpage.util.RequestUtil;
import org.springframework.web.servlet.ModelAndView;
 

public class MessageController implements org.springframework.web.servlet.mvc.Controller {
	private final static Log logger = LogFactory.getLog(ActorController.class);

	 

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
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
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