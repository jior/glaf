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

package com.glaf.base.modules.sys.springmvc;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.base.config.BaseConfiguration;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.AuthorizeService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.online.domain.UserOnline;
import com.glaf.base.online.service.UserOnlineService;
import com.glaf.base.utils.ContextUtil;
import com.glaf.base.utils.ParamUtil;
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.Environment;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.domain.SystemProperty;
import com.glaf.core.res.MessageUtils;
import com.glaf.core.res.ViewMessage;
import com.glaf.core.res.ViewMessages;
import com.glaf.core.security.DigestUtil;
import com.glaf.core.service.ISystemPropertyService;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.Constants;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.web.callback.CallbackProperties;
import com.glaf.core.web.callback.LoginCallback;
import com.glaf.shiro.ShiroSecurity;

@Controller("/mx/login")
@RequestMapping("/login")
public class MxLoginController {
	private static final Log logger = LogFactory
			.getLog(MxLoginController.class);

	private static Configuration conf = BaseConfiguration.create();

	private AuthorizeService authorizeService;

	private SysUserService sysUserService;

	private UserOnlineService userOnlineService;

	private ISystemPropertyService systemPropertyService;

	/**
	 * 登录
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/doLogin")
	public ModelAndView doLogin(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		HttpSession session = request.getSession(false);
		if (session == null) {
			return new ModelAndView("/modules/login", modelMap);
		}

		if (StringUtils.isNotEmpty(request.getParameter("systemName"))) {
			Environment
					.setCurrentSystemName(request.getParameter("systemName"));
		} else {
			Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
		}

		ViewMessages messages = new ViewMessages();
		// 获取参数
		String account = ParamUtil.getParameter(request, "x");
		String password = ParamUtil.getParameter(request, "y");

		String rand = (String) session.getAttribute("x_y");
		if (rand != null) {
			password = StringTools.replace(password, rand, "");
		}
		String pwd = password;
		try {
			pwd = DigestUtil.digestString(password, "MD5");
		} catch (Exception ex) {
		}

		logger.debug(account + " start login........................");

		// 用户登陆，返回系统用户对象
		SysUser bean = authorizeService.authorize(account, pwd);
		if (bean == null) {
			// 用户对象为空或失效，显示错误信息
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"authorize.login_failure"));
			MessageUtils.addMessages(request, messages);
			return new ModelAndView("/modules/login", modelMap);
		}

		String ipAddr = RequestUtils.getIPAddress(request);

		if (!(StringUtils.equals(ipAddr, "localhost")
				|| StringUtils.equals(ipAddr, "127.0.0.1")
				|| StringUtils.equals(account, "root") || StringUtils.equals(
				account, "admin"))) {
			SystemProperty p = systemPropertyService.getSystemProperty("SYS",
					"login_limit");
			SystemProperty pt = systemPropertyService.getSystemProperty("SYS",
					"login_time_check");
			int timeoutSeconds = 300;

			if (pt != null && pt.getValue() != null
					&& StringUtils.isNumeric(pt.getValue())) {
				timeoutSeconds = Integer.parseInt(pt.getValue());
			}
			if (timeoutSeconds < 300) {
				timeoutSeconds = 300;
			}
			if (timeoutSeconds > 3600) {
				timeoutSeconds = 3600;
			}

			/**
			 * 检测是否限制一个用户只能在一个地方登录
			 */
			if (p != null && StringUtils.equals(p.getValue(), "true")) {
				logger.debug("#################3#########################");
				String loginIP = null;
				UserOnline userOnline = userOnlineService
						.getUserOnline(account);
				boolean timeout = false;
				if (userOnline != null) {
					loginIP = userOnline.getLoginIP();
					if (userOnline.getCheckDateMs() != null
							&& System.currentTimeMillis()
									- userOnline.getCheckDateMs() > timeoutSeconds * 1000) {
						timeout = true;// 超时，说明登录已经过期
					}
					if (userOnline.getLoginDate() != null
							&& System.currentTimeMillis()
									- userOnline.getLoginDate().getTime() > timeoutSeconds * 1000) {
						timeout = true;// 超时，说明登录已经过期
					}
				}
				logger.info("timeout:" + timeout);
				logger.info("login IP:" + loginIP);
				if (!timeout) {// 超时，说明登录已经过期，不用判断是否已经登录了
					if (loginIP != null
							&& !(StringUtils.equals(ipAddr, loginIP))) {// 用户已在其他机器登陆
						messages.add(ViewMessages.GLOBAL_MESSAGE,
								new ViewMessage("authorize.login_failure2"));
						MessageUtils.addMessages(request, messages);
						logger.debug("用户已经在其他地方登录。");
						return new ModelAndView("/modules/login", modelMap);
					}
				}
			}
		}

		Properties props = CallbackProperties.getProperties();
		if (props != null && props.keys().hasMoreElements()) {
			Enumeration<?> e = props.keys();
			while (e.hasMoreElements()) {
				String className = (String) e.nextElement();
				try {
					Object obj = ClassUtils.instantiateObject(className);
					if (obj instanceof LoginCallback) {
						LoginCallback callback = (LoginCallback) obj;
						callback.afterLogin(bean.getAccount(), request,
								response);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error(ex);
				}
			}
		}

		if (bean.getLoginCount() != null) {
			bean.setLoginCount(bean.getLoginCount() + 1);
		} else {
			bean.setLoginCount(1);
		}

		// 登录成功，修改最近一次登录时间
		bean.setLastLoginDate(new Date());
		sysUserService.updateUser(bean);

		ContextUtil.put(bean.getAccount(), bean);// 传入全局变量

		RequestUtils.setLoginUser(request, response, "default",
				bean.getAccount());

		try {
			UserOnline online = new UserOnline();
			online.setActorId(bean.getActorId());
			online.setName(bean.getName());
			online.setCheckDate(new Date());
			online.setLoginDate(new Date());
			online.setLoginIP(ipAddr);
			online.setSessionId(session.getId());
			userOnlineService.login(online);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}

		if (bean.getAccountType() == 1) {// 供应商用户
			return new ModelAndView("/modules/sp_main", modelMap);
		} else if (bean.getAccountType() == 2) {// 微信用户
			return new ModelAndView("/modules/wx_main", modelMap);
		} else {
			return new ModelAndView("/modules/main", modelMap);
		}

	}

	/**
	 * 登录
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request,
			HttpServletResponse response) {
		String ip = RequestUtils.getIPAddress(request);
		/**
		 * 允许从指定的机器上通过用户名密码登录
		 */
		if (StringUtils.contains(conf.get("login.allow.ip", "127.0.0.1"), ip)
				|| StringUtils.contains(
						SystemConfig.getString("login.allow.ip", "127.0.0.1"),
						ip)) {
			String actorId = request.getParameter("x");
			String password = request.getParameter("y");
			HttpSession session = request.getSession(true);
			java.util.Random random = new java.util.Random();
			String rand = Math.abs(random.nextInt(999999))
					+ com.glaf.core.util.UUID32.getUUID()
					+ Math.abs(random.nextInt(999999));
			session = request.getSession(true);
			if (session != null) {
				session.setAttribute("x_y", rand);
			}
			String url = request.getContextPath() + "/mx/login/doLogin?x="
					+ actorId + "&y=" + rand + password;
			try {
				response.sendRedirect(url);
				return null;
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return new ModelAndView("/modules/login");
	}

	/**
	 * 注销
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String actorId = RequestUtils.getActorId(request);
		// 退出系统，清除session对象
		request.getSession().removeAttribute(SysConstants.LOGIN);
		request.getSession().removeAttribute(SysConstants.MENU);
		try {
			userOnlineService.logout(actorId);
			String cacheKey = Constants.LOGIN_USER_CACHE + actorId;
			CacheFactory.remove(cacheKey);
			cacheKey = Constants.USER_CACHE + actorId;
			CacheFactory.remove(cacheKey);
			ShiroSecurity.logout();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ModelAndView("/modules/login", modelMap);
	}

	/**
	 * 准备登录
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping
	public ModelAndView prepareLogin(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		// 显示登陆页面
		return new ModelAndView("/modules/login", modelMap);
	}

	@javax.annotation.Resource
	public void setAuthorizeService(AuthorizeService authorizeService) {
		this.authorizeService = authorizeService;
	}

	@javax.annotation.Resource
	public void setSystemPropertyService(
			ISystemPropertyService systemPropertyService) {
		this.systemPropertyService = systemPropertyService;
	}

	@javax.annotation.Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	@javax.annotation.Resource
	public void setUserOnlineService(UserOnlineService userOnlineService) {
		this.userOnlineService = userOnlineService;
	}

}