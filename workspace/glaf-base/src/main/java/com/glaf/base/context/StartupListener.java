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

package com.glaf.base.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.glaf.base.modules.sys.service.SchedulerService;

public class StartupListener extends ContextLoaderListener implements
		ServletContextListener {

	private static final Log logger = LogFactory.getLog(StartupListener.class);

	public void beforeContextInitialized(ServletContext context) {

	}

	public void contextInitialized(ServletContextEvent event) {
		logger.info("initializing servlet context......");
		ServletContext context = event.getServletContext();
		String root = context.getRealPath("/");
		com.glaf.base.context.ApplicationContext.setAppPath(root);
		com.glaf.base.context.ApplicationContext.setContextPath(event
				.getServletContext().getContextPath());

		this.beforeContextInitialized(context);
		super.contextInitialized(event);
		this.setupContext(context);

	}

	public void setupContext(ServletContext context) {
		ApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(context);
		if (ctx != null) {
			logger.info("设置应用环境上下文......");
			ContextFactory.setContext(ctx);
		}
		try {
			if (ContextFactory.hasBean("schedulerService")) {
				SchedulerService schedulerService = (SchedulerService) ContextFactory
						.getBean("schedulerService");
				if (ContextFactory.hasBean("scheduler")) {
					// schedulerService.startup();
					logger.info("成功启动系统调动服务.");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}