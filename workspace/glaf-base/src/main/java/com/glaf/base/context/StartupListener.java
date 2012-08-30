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
