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

package com.glaf.core.context;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.startup.BootstrapManager;
import com.glaf.core.util.Constants;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.QuartzUtils;

public class StartupListener extends ContextLoaderListener implements
		ServletContextListener {

	protected static Configuration conf = BaseConfiguration.create();

	private static class SchedulerRunner extends Thread {
		public void run() {
			try {
				Thread.sleep(60000);// 60秒后执行
				QuartzUtils.startup();
				logger.info("系统调度已经成功启动。");
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			}
		}
	}

	private static final Log logger = LogFactory.getLog(StartupListener.class);

	public void beforeContextInitialized(ServletContext context) {
		BootstrapManager.getInstance().startup(context);
	}

	public void contextInitialized(ServletContextEvent event) {
		logger.info("initializing servlet context......");
		ServletContext context = event.getServletContext();
		String root = context.getRealPath("/");
		com.glaf.core.context.ApplicationContext.setAppPath(root);
		com.glaf.core.context.ApplicationContext.setContextPath(event
				.getServletContext().getContextPath());
		System.setProperty("webapp.root", SystemProperties.getAppPath());
		if (DBConnectionFactory.checkConnection()) {
			this.beforeContextInitialized(context);
			super.contextInitialized(event);
			this.setupContext(context);
		} else {
			logger.error("数据库连接错误，请检查配置。");
		}
		if (SystemProperties.getDeploymentSystemName() != null) {
			String deploymentSystemName = SystemProperties
					.getDeploymentSystemName();
			String path = SystemProperties.getConfigRootPath()
					+ Constants.DEPLOYMENT_JDBC_PATH + deploymentSystemName
					+ "/jdbc";
			try {
				FileUtils.mkdirs(path);
			} catch (IOException ex) {
			}
			String log_path = SystemProperties.getConfigRootPath() + "/logs/"
					+ deploymentSystemName;
			try {
				FileUtils.mkdirs(log_path);
			} catch (IOException ex) {
			}
		}
	}

	public void setupContext(ServletContext context) {
		ApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(context);
		if (ctx != null) {
			logger.info("设置应用环境上下文......");
			ContextFactory.setContext(ctx);
			if (conf.getBoolean("scheduler.enabled", true)
					&& ContextFactory.hasBean("scheduler")) {
				SchedulerRunner runner = new SchedulerRunner();
				runner.start();
			}
		}
	}
}