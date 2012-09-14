package com.glaf.base.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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

	private String errorUrl = "/error/error_jump.htm";

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

			/**
			 * 未登录或不是系统管理员，不允许访问系统管理地址
			 */
			if ((user == null) || (!user.isSystemAdmin())) {
				String uri = request.getRequestURI();
				logger.debug("request uri:" + uri);
				if (StringUtils.contains(uri, "/sys/role.do")
						|| StringUtils.contains(uri, "/sys/department.do")
						|| StringUtils.contains(uri, "/sys/application.do")
						|| StringUtils.contains(uri, "/sys/dictory.do")
						|| StringUtils.contains(uri, "/sys/scheduler.do")
						|| StringUtils.contains(uri, "/sys/function.do")
						|| StringUtils.contains(uri, "/sys/todo.do")
						|| StringUtils.contains(uri, "others/workCalendar.do")) {
					response.sendRedirect(request.getContextPath() + errorUrl);
					return;
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
