package com.glaf.base.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;

import com.glaf.base.modules.sys.action.AuthorizeBean;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.utils.Authentication;
import com.glaf.base.utils.RequestUtil;

public class StrutsActionServlet extends ActionServlet {
	protected static final Log logger = LogFactory
			.getLog(StrutsActionServlet.class);

	private static final long serialVersionUID = 1L;

	private static String dispatcherAuth = "false";

	protected void process(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		try {
			String actorId = RequestUtil.getActorId(request);
			if (actorId != null) {
				logger.debug("login actorId:" + actorId);
				Authentication.setAuthenticatedAccount(actorId);
			}

			SysUser user = RequestUtil.getLoginUser(request);
			if (user != null) {
				Authentication.setAuthenticatedUser(user);
			} else {
				if (actorId != null) {
					AuthorizeBean bean = new AuthorizeBean();
					user = bean.getUser(actorId);
					if (user != null) {
						Authentication.setAuthenticatedUser(user);
					}
				}
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

		super.process(request, response);
	}

}
