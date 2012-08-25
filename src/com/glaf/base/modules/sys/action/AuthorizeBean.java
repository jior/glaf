package com.glaf.base.modules.sys.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.context.ContextFactory;

import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.AuthorizeService;
import com.glaf.base.modules.sys.service.SysApplicationService;
import com.glaf.base.modules.utils.ContextUtil;
import com.glaf.base.utils.RequestUtil;

public class AuthorizeBean {
	private static final Log logger = LogFactory.getLog(AuthorizeBean.class);

	private SysApplicationService sysApplicationService;

	private AuthorizeService authorizeService;

	public AuthorizeBean() {
		authorizeService = ContextFactory.getBean("authorizeService");
		sysApplicationService = ContextFactory.getBean("sysApplicationService");
	}

	/**
	 * 登录
	 * 
	 * @param request
	 */
	public void login(String account, HttpServletRequest request) {

		logger.debug(account + " start login........................");

		// 用户登陆，返回系统用户对象
		SysUser bean = authorizeService.login(account);
		if (bean != null) {

			// 登录成功，修改最近一次登录时间
			Map map = new HashMap();
			map.put("lastLoginIP", request.getRemoteAddr());
			map.put("lastLoginTime", new Date());
			map.put("account", bean.getAccount());
			// sysUserService.updatexy();

			ContextUtil.put(bean.getAccount(), bean);// 传入全局变量

			RequestUtil.setLoginUser(request, bean);

			// 保存session对象，跳转到后台主页面
			request.getSession().setAttribute(SysConstants.MENU,
					sysApplicationService.getMenu(3, bean));

		}
	}

}
