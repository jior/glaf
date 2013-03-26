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

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysUserRoleService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.modules.utils.BaseUtil;
import com.glaf.base.utils.ParamUtil;
import com.glaf.base.utils.RequestUtil;
import com.glaf.base.utils.WebUtil;
import com.glaf.core.res.MessageUtils;
import com.glaf.core.res.ViewMessage;
import com.glaf.core.res.ViewMessages;

@Controller("/sys/sysUserRole")
@RequestMapping("/sys/sysUserRole.do")
public class SysUserRoleController {
	private static final Log logger = LogFactory
			.getLog(SysUserRoleController.class);

	@javax.annotation.Resource
	private SysUserRoleService sysUserRoleService;

	@javax.annotation.Resource
	private SysUserService sysUserService;

	public void setSysUserRoleService(SysUserRoleService sysUserRoleService) {
		this.sysUserRoleService = sysUserRoleService;
		logger.info("setSysUserRoleService");
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
		logger.info("setSysUserService");
	}

	/**
	 * 显示授权页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showMain")
	public ModelAndView showMain(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		SysUser user = RequestUtil.getLoginUser(request);
		request.setAttribute("available",
				sysUserRoleService.getUnAuthorizedUser(user));
		request.setAttribute("unavailable",
				sysUserRoleService.getAuthorizedUser(user));

		// 显示列表页面
		return new ModelAndView("/modules/sys/userRole/authorize_list",
				modelMap);
	}

	/**
	 * 显示授权页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showUsers")
	public ModelAndView showUsers(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		Map<String, String> filter = WebUtil.getQueryMap(request);
		request.setAttribute("pager",
				sysUserRoleService.getAllAuthorizedUser(filter));
		// 显示列表页面
		return new ModelAndView("/modules/sys/userRole/authorize_users",
				modelMap);
	}

	/**
	 * 显示授权页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showSysAuth")
	public ModelAndView showSysAuth(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		long userId = ParamUtil.getLongParameter(request, "id", 0);
		SysUser user = (SysUser) sysUserService.findById(userId);
		if (user == null) {
			user = RequestUtil.getLoginUser(request);
		}

		request.setAttribute("user", user);
		request.setAttribute("authorizedUser",
				sysUserRoleService.getAuthorizedUser(user));
		request.setAttribute("processList",
				sysUserRoleService.getProcessByUser(user));

		// 显示列表页面
		return new ModelAndView("/modules/sys/userRole/authorize_panel",
				modelMap);
	}

	/**
	 * 显示授权页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showUserSysAuth")
	public ModelAndView showUserSysAuth(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		long userId = ParamUtil.getLongParameter(request, "id", 0);
		SysUser user = (SysUser) sysUserService.findById(userId);
		if (user == null) {
			user = RequestUtil.getLoginUser(request);
		}

		request.setAttribute("user", user);
		request.setAttribute("authorizedUser",
				sysUserRoleService.getAuthorizedUser(user));

		// 显示列表页面
		return new ModelAndView("/modules/sys/userRole/authorizeUser_panel",
				modelMap);
	}

	/**
	 * 保存用户授权
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=saveUserSysAuth")
	public ModelAndView saveUserSysAuth(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		long fromUserId = ParamUtil.getLongParameter(request, "uid", 0);
		long[] userIds = ParamUtil.getLongParameterValues(request, "userIds");

		SysUser user = sysUserService.findById(fromUserId);
		SysUser rootUser = sysUserService.findByAccount("root");// 管理员

		String msgStr = user.getName() + "[" + user.getAccount()
				+ "]的受权列表如下:<br><br>";
		ViewMessages messages = new ViewMessages();
		if (fromUserId != 0 && userIds.length > 0) {
			// 取得授权列表
			List userList = sysUserRoleService.getAuthorizedUser(user);
			logger.info("userList.size()=>" + userList.size());

			authorStart: for (int i = 0; i < userIds.length; i++) {
				SysUser sysUser = sysUserService.findById(userIds[i]);

				String startDate = ParamUtil.getParameter(request, "startDate_"
						+ userIds[i], "");
				String endDate = ParamUtil.getParameter(request, "endDate_"
						+ userIds[i], "");

				if (!startDate.equals("") && !endDate.equals("")) {
					for (int j = 0; j < userList.size(); j++) {
						Object[] bean = (Object[]) userList.get(j);
						SysUser authorUser = (SysUser) bean[0];
						if (authorUser.getId() == sysUser.getId()) {// 已授权
							msgStr = msgStr + "&nbsp;&nbsp;&nbsp;&nbsp;修改授权=>"
									+ sysUser.getName() + "["
									+ sysUser.getAccount() + "]&nbsp;&nbsp;"
									+ startDate + "至" + endDate + "<br>";
							logger.info(msgStr);
							userList.remove(j);

							continue authorStart;
						}
					}

					msgStr = msgStr + "&nbsp;&nbsp;&nbsp;&nbsp;添加授权=>"
							+ sysUser.getName() + "[" + sysUser.getAccount()
							+ "]&nbsp;&nbsp;" + startDate + "至" + endDate
							+ "<br>";
					logger.info(msgStr);
				} else {
					messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
							sysUser.getName() + "[" + sysUser.getAccount()
									+ "]" + "sys.author_dateErr"));

					return new ModelAndView("show_msg", modelMap);
				}
			}

			for (int i = 0; i < userList.size(); i++) {
				Object[] bean = (Object[]) userList.get(i);
				SysUser authorUser = (SysUser) bean[0];
				Date aStartDate = (Date) bean[1];
				Date aEndDate = (Date) bean[2];
				msgStr = msgStr + "&nbsp;&nbsp;&nbsp;&nbsp;取消授权=>"
						+ authorUser.getName() + "[" + authorUser.getAccount()
						+ "]&nbsp;&nbsp;" + BaseUtil.dateToString(aStartDate)
						+ "至" + BaseUtil.dateToString(aEndDate) + "<br>";
				logger.info(msgStr);
			}
		} else {
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"sys.author_msgErr"));

			return new ModelAndView("show_msg");
		}

		if (sendMail(user, rootUser, "授权书", msgStr)) {
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"sys.author_success"));
		} else {
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"sys.author_failure"));
		}

		MessageUtils.addMessages(request, messages);
		request.setAttribute("refresh", "false");
		// 显示列表页面
		return new ModelAndView("show_msg", modelMap);
	}

	/**
	 * 向管理员发送授权电邮
	 * 
	 * @param fromUser
	 * @param toUser
	 * @param msgStr
	 * @return
	 */
	private boolean sendMail(SysUser fromUser, SysUser toUser, String title,
			String msgStr) {
		boolean rst = true;

		String subject = title;
		String context = msgStr;
		logger.info(fromUser.getEmail() + "--" + toUser.getEmail() + "--"
				+ subject + "--" + context);

		return rst;
	}

}