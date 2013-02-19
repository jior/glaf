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

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 
import org.jbpm.JbpmContext;
import org.jpage.actor.User;
import org.jpage.jbpm.config.ObjectFactory;
import org.jpage.jbpm.context.Context;
import org.jpage.jbpm.context.ProcessContext;
import org.jpage.jbpm.datafield.DataField;
import org.jpage.jbpm.service.ProcessContainer;
import org.jpage.jbpm.service.ProcessManager;
import org.jpage.jbpm.util.ProcessActionType;
import org.jpage.jbpm.util.ResourceType;
import org.jpage.util.QueryTool;
import org.jpage.util.RequestUtil;
import org.jpage.util.Tools;
 
public class MainController extends HttpServlet {
	private final static Log logger = LogFactory.getLog(MainController.class);

	 
	private final static String sp = System.getProperty("file.separator");

	private static final long serialVersionUID = 1L;

	private ProcessManager processManager;

	public void init() {
		logger.debug("MainController init...");
		processManager = (ProcessManager) org.jpage.jbpm.context.JbpmContextFactory
				.getBean("processManager");
	}

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		logger.debug("MainController start process........................");
		Context.create();
		User user = (User) request.getSession().getAttribute(
				org.jpage.util.Constant.LOGIN_USER);
		response.setContentType("text/html;charset=GBK");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Expires", "-1");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0L);
		request.setCharacterEncoding("GBK");

		PrintWriter writer = response.getWriter();

		if (user == null) {
			logger.debug("not login...");
			String login_url = ObjectFactory.getLoginUrl();
			response.sendRedirect(login_url);
			return;
		}

		ProcessContext ctx = new ProcessContext();
		Map params = Tools.getParameters(request);
		Enumeration enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			if (key.startsWith(org.jpage.jbpm.util.Constant.PROCESS_DATAFIELD_PREFIX)) {
				Object value = (Object) params.get(key);
				String paramName = key.replaceAll(
						org.jpage.jbpm.util.Constant.PROCESS_DATAFIELD_PREFIX,
						"");
				params.put(paramName, value);
				DataField dataField = new DataField();
				dataField.setName(paramName);
				dataField.setValue(value);
				ctx.getDataFields().add(dataField);
			}
		}

		params.put(org.jpage.jbpm.util.Constant.PROCESS_OPINION, request
				.getParameter(org.jpage.jbpm.util.Constant.PROCESS_OPINION));
		logger.debug(params.get(org.jpage.jbpm.util.Constant.PROCESS_OPINION));

		enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String name = (String) enumeration.nextElement();
			String value = request.getParameter(name);
			if (name.startsWith(ResourceType.GRID_PARAMETERS_TAG)) {
				String row = request.getParameter(name + ":rows");
				if (StringUtils.isNumeric(row)) {
					List sqlParams = new ArrayList();
					int count = Integer.parseInt(row);
					for (int i = 0; i < count; i++) {
						Map rowMap = new HashMap();
						StringTokenizer token = new StringTokenizer(value, ",");
						while (token.hasMoreTokens()) {
							String elem = token.nextToken();
							String sqlParamName = ResourceType.GRID_PARAMETER_TAG
									+ elem + "_" + i;
							String sqlParamValue = request
									.getParameter(sqlParamName);
							rowMap.put(elem, sqlParamValue);
						}
						logger.debug("grid row:" + rowMap);
						sqlParams.add(rowMap);
					}
					name = name
							.replaceAll(ResourceType.GRID_PARAMETERS_TAG, "");
					logger.debug("grid name:" + name);

				}
			}
		}

		ctx.setActorId(user.getActorId());
		ctx.setContextMap(params);

		String actionType = (String) request.getParameter("actionType");
		int processActionType = ProcessActionType.getActionType(actionType);
		long processDefinitionId = Tools.getId(params, "processDefinitionId");
		String taskForwardPage = (String) request.getSession().getAttribute(
				ResourceType.TASK_FORWARD_PAGE);
		logger.debug("nextStepId:"
				+ params.get(org.jpage.jbpm.util.Constant.NEXT_STEP_ID));
		logger.debug("actionType:" + actionType);
		logger.debug("params:" + params);

		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			jbpmContext.setActorId(user.getActorId());

			switch (processActionType) {
			case ProcessActionType.START_PROCESS_TYPE:
				// 启动流程
				processManager.startProcess(ctx);
				break;
			case ProcessActionType.COMPLETE_TASK_TYPE:
				// 保存并且完成任务
				processManager.completeTask(ctx);
				break;
			default:
				break;
			}

			request.getSession()
					.removeAttribute(
							org.jpage.jbpm.util.Constant.SESSION_PROCESS_ATTACHMENT_KEY);

			if (StringUtils.isNotBlank(taskForwardPage)) {
				taskForwardPage = RequestUtil.decodeURL(taskForwardPage);
				taskForwardPage = QueryTool.replaceTextParas(taskForwardPage,
						params);
				request.getRequestDispatcher(taskForwardPage).forward(request,
						response);
				return;
			}

			StringBuffer buffer = new StringBuffer();
			buffer.append(
					"/WEB-INF/pages/workflow/taskForward.jsp?processDefinitionId=")
					.append(processDefinitionId);
			request.getRequestDispatcher(buffer.toString()).forward(request,
					response);

		} catch (Exception ex) {
			ex.printStackTrace();
			writer.write("<font color=\"red\" size=\"3\">应用程序处理发生错误！</font>");
			writer.flush();
			writer.close();
		} finally {
			Context.close(jbpmContext);
		}
	}

	protected String getYearMonthDay() {
		DateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
		String s = simpledateformat.format(new Date());
		String s1 = s.substring(0, 4);
		String s2 = s.substring(5, 7);
		String s3 = s.substring(8, 10);
		return s1 + sp + s2 + sp + s3;
	}

}
