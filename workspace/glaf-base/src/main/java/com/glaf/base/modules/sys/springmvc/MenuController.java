package com.glaf.base.modules.sys.springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.glaf.base.modules.sys.model.SysApplication;
import com.glaf.base.modules.sys.service.SysApplicationService;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.RequestUtils;

@Controller("menu")
@RequestMapping("/menu.do")
public class MenuController {

	@javax.annotation.Resource
	private SysApplicationService sysApplicationService;

	public void setSysApplicationService(
			SysApplicationService sysApplicationService) {
		this.sysApplicationService = sysApplicationService;
	}

	@RequestMapping("/jump")
	public void jump(HttpServletRequest request, HttpServletResponse response) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		if (loginContext == null) {
			try {
				response.sendRedirect(request.getContextPath()
						+ ViewProperties.getString("loginUrl"));
				return;
			} catch (Exception ex) {
			}
		}
		String menuId = request.getParameter("menuId");
		if (menuId != null) {
			menuId = RequestUtils.decodeString(menuId);
		}
		if (menuId != null && StringUtils.isNumeric(menuId)) {
			SysApplication app = sysApplicationService.findById(Long
					.parseLong(menuId));
			if (app != null) {
				boolean accessable = false;
				if (loginContext.isSystemAdministrator()) {
					accessable = true;
				} else {

				}
				if (accessable) {
					try {
						String url = app.getUrl();
						if (url != null) {
							if (!(url.toLowerCase().startsWith("http://") || url
									.toLowerCase().startsWith("https://"))) {
								if (url.startsWith("/")) {
									url = request.getContextPath() + url;
								} else {
									url = request.getContextPath() + "/" + url;
								}
							}
							if (url.indexOf("?") != -1) {
								url = url + "&time="
										+ System.currentTimeMillis();
							} else {
								url = url + "?time="
										+ System.currentTimeMillis();
							}
							response.sendRedirect(url);
						} else {
							return;
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		try {
			request.getRequestDispatcher("/WEB-INF/views/404.jsp").forward(
					request, response);
		} catch (Exception e) {
		}
	}

}
