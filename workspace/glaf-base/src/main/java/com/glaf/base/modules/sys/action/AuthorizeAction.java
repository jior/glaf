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

package com.glaf.base.modules.sys.action;

import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.jpage.util.DigestUtil;
import org.springframework.web.struts.DispatchActionSupport;

import com.glaf.base.callback.CallbackProperties;
import com.glaf.base.callback.LoginCallback;
import com.glaf.base.listener.UserOnlineListener;
import com.glaf.base.modules.Constants;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.AuthorizeService;
import com.glaf.base.modules.sys.service.SysApplicationService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.modules.utils.ContextUtil;
import com.glaf.base.utils.ClassUtil;
import com.glaf.base.utils.ParamUtil;
import com.glaf.base.utils.RequestUtil;

public class AuthorizeAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(AuthorizeAction.class);

	private SysApplicationService sysApplicationService;

	private AuthorizeService authorizeService;

	private SysTreeService sysTreeService;

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
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward login(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		saveToken(request);

		ActionMessages messages = new ActionMessages();
		// 获取参数
		String account = ParamUtil.getParameter(request, "account");
		String password = ParamUtil.getParameter(request, "password");
		String pwd = DigestUtil.digestString(password, "MD5");

		logger.debug(account + " start login........................");

		// 用户登陆，返回系统用户对象
		SysUser bean = authorizeService.login(account, pwd);
		if (bean == null) {
			// 用户对象为空或失效，显示错误信息
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"authorize.login_failure"));
			addMessages(request, messages);
			return mapping.findForward("show_login");
		} else {
			String loginIp = UserOnlineListener.findUser(bean.getId());
			logger.info("login ip:" + loginIp);
			if (loginIp != null && !loginIp.equals(request.getRemoteAddr())) {// 用户已在其他机器登陆
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"authorize.login_failure2"));
				addMessages(request, messages);
				return mapping.findForward("show_login");
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
							callback.afterLogin(bean, request, response);
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

			RequestUtil.setLoginUser(request, bean);

			// 保存session对象，跳转到后台主页面
			request.getSession().setAttribute(SysConstants.MENU, menus);

			if (bean.getAccountType() == 1) {// 供应商用户
				return mapping.findForward("show_sp_frame");
			} else {
				return mapping.findForward("show_frame");
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
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward logout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 登出系统，清除session对象
		request.getSession().removeAttribute(SysConstants.LOGIN);
		request.getSession().removeAttribute(SysConstants.MENU);
		return mapping.findForward("show_login");
	}

	/**
	 * 准备登录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward prepareLogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 显示登陆页面
		return mapping.findForward("show_login");
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
	 * @throws Exception
	 */
	public ActionForward showMenu(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUser user = RequestUtil.getLoginUser(request);
		SysTree parent = sysTreeService.getSysTreeByCode(Constants.TREE_APP);
		List list = sysApplicationService
				.getAccessAppList(parent.getId(), user);
		request.setAttribute("list", list);
		return mapping.findForward("show_menu");
	}

	/**
	 * 显示子菜单
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showSubMenu(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SysUser user = RequestUtil.getLoginUser(request);
		long parent = ParamUtil.getIntParameter(request, "parent", 0);
		List list = sysApplicationService.getAccessAppList(parent, user);
		request.setAttribute("list", list);
		return mapping.findForward("show_submenu");
	}
}