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

import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.base.listener.UserOnlineListener;
import com.glaf.base.modules.Constants;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.SysApplication;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.AuthorizeService;
import com.glaf.base.modules.sys.service.SysApplicationService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.modules.utils.ContextUtil;
import com.glaf.core.res.MessageUtils;
import com.glaf.core.res.ViewMessage;
import com.glaf.core.res.ViewMessages;
import com.glaf.base.utils.ClassUtil;
import com.glaf.base.utils.ParamUtil;
import com.glaf.base.utils.RequestUtil;
import com.glaf.core.security.DigestUtil;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.web.callback.CallbackProperties;
import com.glaf.core.web.callback.LoginCallback;

@Controller("/sys/authorize")
@RequestMapping("/sys/authorize.do")
public class AuthorizeController {
	private static final Log logger = LogFactory
			.getLog(AuthorizeController.class);

	@javax.annotation.Resource
	private SysApplicationService sysApplicationService;

	@javax.annotation.Resource
	private AuthorizeService authorizeService;

	@javax.annotation.Resource
	private SysTreeService sysTreeService;

	@javax.annotation.Resource
	private SysUserService sysUserService;

	public SysUserService getSysUserService() {
		return sysUserService;
	}

	/**
	 * 登录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=login")
	public ModelAndView login(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		HttpSession session = request.getSession(true);
		ViewMessages messages = new ViewMessages();
		// 获取参数
		String account = ParamUtil.getParameter(request, "account");
		String password = ParamUtil.getParameter(request, "password");
		String pwd = password;
		try {
			pwd = DigestUtil.digestString(password, "MD5");
		} catch (Exception ex) {
		}

		logger.debug(account + " start login........................");

		// 用户登陆，返回系统用户对象
		SysUser bean = authorizeService.login(account, pwd);
		if (bean == null) {
			// 用户对象为空或失效，显示错误信息
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"authorize.login_failure"));
			MessageUtils.addMessages(request, messages);
			return new ModelAndView("/modules/login", modelMap);
		} else {
			String loginIp = UserOnlineListener.findUser(bean.getId());
			logger.info("login ip:" + loginIp);
			if (loginIp != null && !loginIp.equals(request.getRemoteAddr())) {// 用户已在其他机器登陆
				messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
						"authorize.login_failure2"));
				MessageUtils.addMessages(request, messages);
				return new ModelAndView("/modules/login", modelMap);
			}

			Properties props = CallbackProperties.getProperties();
			if (props != null && props.keys().hasMoreElements()) {
				Enumeration<?> e = props.keys();
				while (e.hasMoreElements()) {
					String className = (String) e.nextElement();
					try {
						Object obj = ClassUtil.instantiateObject(className);
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

			// 登录成功，修改最近一次登录时间

			String menus = sysApplicationService.getMenu(3, bean);
			bean.setMenus(menus);

			ContextUtil.put(bean.getAccount(), bean);// 传入全局变量

			if (session != null) {
				RequestUtil.setLoginUser(request, bean);
				RequestUtils.setLoginUser(request, response, "GLAF",
						bean.getAccount());

				// 保存session对象，跳转到后台主页面
				// session.setAttribute(SysConstants.MENU, menus);
			}

			request.setAttribute(SysConstants.MENU, menus);

			if (bean.getAccountType() == 1) {// 供应商用户
				return new ModelAndView("/modules/spframe", modelMap);
			} else {
				return new ModelAndView("/modules/frame", modelMap);
			}
		}
	}

	/**
	 * 登出
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=logout")
	public ModelAndView logout(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		// 登出系统，清除session对象
		request.getSession().removeAttribute(SysConstants.LOGIN);
		request.getSession().removeAttribute(SysConstants.MENU);
		return new ModelAndView("/modules/login", modelMap);
	}

	/**
	 * 准备登录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=prepareLogin")
	public ModelAndView prepareLogin(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		// 显示登陆页面
		return new ModelAndView("/modules/login", modelMap);
	}

	public void setAuthorizeService(AuthorizeService authorizeService) {
		this.authorizeService = authorizeService;
		logger.info("setAuthorizeService");
	}

	public void setSysApplicationService(
			SysApplicationService sysApplicationService) {
		this.sysApplicationService = sysApplicationService;
		logger.info("setSysApplicationService");
	}

	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
		logger.info("setSysTreeService");
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	/**
	 * 显示菜单
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showMenu")
	public ModelAndView showMenu(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		SysUser user = RequestUtil.getLoginUser(request);
		SysTree parent = sysTreeService.getSysTreeByCode(Constants.TREE_APP);
		List<SysApplication> list = sysApplicationService.getAccessAppList(
				parent.getId(), user);
		request.setAttribute("list", list);
		return new ModelAndView("/modules/menu", modelMap);
	}

	/**
	 * 显示子菜单
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showSubMenu")
	public ModelAndView showSubMenu(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		SysUser user = RequestUtil.getLoginUser(request);
		long parent = ParamUtil.getIntParameter(request, "parent", 0);
		List<SysApplication> list = sysApplicationService.getAccessAppList(
				parent, user);
		request.setAttribute("list", list);
		return new ModelAndView("/modules/sub_menu", modelMap);
	}
}