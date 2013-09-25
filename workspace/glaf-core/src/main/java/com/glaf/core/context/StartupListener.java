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

import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.glaf.core.config.DataSourceConfig;
import com.glaf.core.startup.Bootstrap;
import com.glaf.core.startup.BootstrapProperties;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.QuartzUtils;

public class StartupListener extends ContextLoaderListener implements
		ServletContextListener {

	private static final Log logger = LogFactory.getLog(StartupListener.class);

	public void beforeContextInitialized(ServletContext context) {
		Properties props = BootstrapProperties.getProperties();
		if (props != null && props.keys().hasMoreElements()) {
			Enumeration<?> e = props.keys();
			while (e.hasMoreElements()) {
				String className = (String) e.nextElement();
				String value = props.getProperty(className);
				try {
					Object obj = ClassUtils.instantiateObject(className);
					if (obj instanceof Bootstrap) {
						Bootstrap bootstrap = (Bootstrap) obj;
						bootstrap.startup(context, value);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error(ex);
				}
			}
		}
	}

	public void contextInitialized(ServletContextEvent event) {
		logger.info("initializing servlet context......");
		ServletContext context = event.getServletContext();
		String root = context.getRealPath("/");
		com.glaf.core.context.ApplicationContext.setAppPath(root);
		com.glaf.core.context.ApplicationContext.setContextPath(event
				.getServletContext().getContextPath());
		if (DataSourceConfig.checkConnection()) {
			DataSourceConfig.initDatabaseType();
			this.beforeContextInitialized(context);
			super.contextInitialized(event);
			this.setupContext(context);
		} else {
			logger.error("数据库连接错误，请检查配置。");
		}
	}

	public void setupContext(ServletContext context) {
		ApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(context);
		if (ctx != null) {
			logger.info("设置应用环境上下文......");
			ContextFactory.setContext(ctx);
			if (ContextFactory.hasBean("scheduler")) {
				SchedulerRunner runner = new SchedulerRunner();
				runner.start();
			}
		}
	}

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
}