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


package org.jpage.jbpm.mail;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 
import org.jbpm.JbpmContext;
import org.jpage.jbpm.context.Context;
import org.jpage.jbpm.context.JbpmContextFactory;
import org.jpage.jbpm.model.MessageInstance;
import org.jpage.jbpm.service.PersistenceManager;
import org.jpage.jbpm.service.ProcessContainer;

public class MailCallbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 

	private final static Log logger = LogFactory
			.getLog(MailCallbackServlet.class);

	private PersistenceManager persistenceManager;

	public void init() {
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doProcess(request, response);
	}

	public void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/html;charset=UTF-8");
		String messageId = request.getParameter("messageId");
		JbpmContext jbpmContext = null;
		try {
			persistenceManager = (PersistenceManager) JbpmContextFactory
					.getBean("persistenceManager");

			if (StringUtils.isNotBlank(messageId)) {
				jbpmContext = ProcessContainer.getContainer().createJbpmContext();
				MessageInstance messageInstance = (MessageInstance) persistenceManager
						.getPersistObject(jbpmContext, MessageInstance.class,
								messageId);
				if (messageInstance != null) {
					messageInstance.setLastViewDate(new java.util.Date());
					messageInstance.setLastViewIP(request.getRemoteHost());
					persistenceManager.update(jbpmContext, messageInstance);
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
		} finally {
			Context.close(jbpmContext);
		}
	}

}