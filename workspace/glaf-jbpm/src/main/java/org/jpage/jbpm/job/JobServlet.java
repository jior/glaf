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


package org.jpage.jbpm.job;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpage.jbpm.context.JbpmContextFactory;
import org.quartz.Scheduler;

public class JobServlet extends HttpServlet {
	private final static Log logger = LogFactory.getLog(JobServlet.class);

	private static final long serialVersionUID = 1L;

	public void init() {
		logger.debug("JobServlet init...");
		try {
			String loadSchedulerOnSetup = this
					.getInitParameter("loadSchedulerOnSetup");
			if (StringUtils.equalsIgnoreCase(loadSchedulerOnSetup, "true")) {
				logger.debug("正在加载调度引擎...");
				Scheduler scheduler = (Scheduler) JbpmContextFactory
						.getBean("scheduler");
				scheduler.start();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			logger.error("加载调度引擎出错:" + ex.getMessage());
		}
	}

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

	}

}
