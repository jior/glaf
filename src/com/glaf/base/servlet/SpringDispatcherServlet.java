package com.glaf.base.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.utils.Authentication;
import com.glaf.base.utils.RequestUtil;

public class SpringDispatcherServlet extends DispatcherServlet {

	protected static final Log logger = LogFactory
			.getLog(SpringDispatcherServlet.class);

	private static final long serialVersionUID = 1L;

	private static String dispatcherAuth = "false";

	public SpringDispatcherServlet() {
		super();
	}

	@Override
	protected void doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String actorId = RequestUtil.getActorId(request);
			if (actorId != null) {
				// logger.debug("actorId:" + actorId);
				Authentication.setAuthenticatedAccount(actorId);
			}

			SysUser user = RequestUtil.getLoginUser(request);
			if (user != null) {
				Authentication.setAuthenticatedUser(user);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// logger.debug("dispatcher required auth:" + dispatcherAuth);

		/**
		 * 如果需要认证
		 */
		if ("true".equals(dispatcherAuth)) {

		}

		super.doService(request, response);
	}

	@Override
	protected WebApplicationContext initWebApplicationContext() {
		WebApplicationContext wac = super.initWebApplicationContext();
		return wac;
	}

}
