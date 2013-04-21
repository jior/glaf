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

package com.glaf.base.modules.sys.rest;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysUserRoleService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.utils.ParamUtil;
import com.glaf.core.res.MessageUtils;
import com.glaf.core.res.ViewMessage;
import com.glaf.core.res.ViewMessages;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.RequestUtils;

@Controller("/rs/sys/sysUserRole")
@Path("/rs/sys/sysUserRole")
public class SysUserRoleResource {
	private static final Log logger = LogFactory
			.getLog(SysUserRoleResource.class);

 
	private SysUserRoleService sysUserRoleService;

 
	private SysUserService sysUserService;

	@ResponseBody
	@Path("addRole")
	@POST
	public void addRole(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		long fromUserId = RequestUtils.getLong(request, "fromUserId");
		long toUserId = RequestUtils.getLong(request, "toUserId");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		int mark = RequestUtils.getInt(request, "mark");
		String processNames = request.getParameter("processNames");
		String processDescriptions = request
				.getParameter("processDescriptions");
		if (!sysUserRoleService.isAuthorized(fromUserId, toUserId)) {// 已授权
			sysUserRoleService.addRole(fromUserId, toUserId, startDate,
					endDate, mark, processNames, processDescriptions);
		}
	}

	@ResponseBody
	@Path("addRoleUser")
	@POST
	public void addRoleUser(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		long fromUserId = RequestUtils.getLong(request, "fromUserId");
		String toUserIds = request.getParameter("toUserIds");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		int mark = RequestUtils.getInt(request, "mark");
		String processNames = request.getParameter("processNames");
		String processDescriptions = request
				.getParameter("processDescriptions");
		String[] ids = toUserIds.split(",");
		for (int i = 0; i < ids.length; i++) {
			long toUserId = Long.parseLong(ids[i]);
			if (!sysUserRoleService.isAuthorized(fromUserId, toUserId)) {// 已授权
				sysUserRoleService.addRole(fromUserId, toUserId, startDate,
						endDate, mark, processNames, processDescriptions);
			}
		}
	}

	@ResponseBody
	@Path("removeRole")
	@POST
	public void removeRole(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		long fromUserId = RequestUtils.getLong(request, "fromUserId");
		long toUserId = RequestUtils.getLong(request, "toUserId");
		sysUserRoleService.removeRole(fromUserId, toUserId);
	}

	@ResponseBody
	@Path("removeRoleUser")
	@POST
	public void removeRoleUser(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		long fromUserId = RequestUtils.getLong(request, "fromUserId");
		String toUserIds = request.getParameter("toUserIds");
		logger.info("toUserIds:" + toUserIds);
		String[] ids = toUserIds.split(",");
		for (int i = 0; i < ids.length; i++) {
			long toUserId = Long.parseLong(ids[i]);
			sysUserRoleService.removeRole(fromUserId, toUserId);
		}
	}

	/**
	 * 保存用户授权
	 * 
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Path("saveUserSysAuth")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public ModelAndView saveUserSysAuth(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		RequestUtils.setRequestParameterToAttribute(request);
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

					return new ModelAndView("show_json_msg");
				}
			}

			for (int i = 0; i < userList.size(); i++) {
				Object[] bean = (Object[]) userList.get(i);
				SysUser authorUser = (SysUser) bean[0];
				Date aStartDate = (Date) bean[1];
				Date aEndDate = (Date) bean[2];
				msgStr = msgStr + "&nbsp;&nbsp;&nbsp;&nbsp;取消授权=>"
						+ authorUser.getName() + "[" + authorUser.getAccount()
						+ "]&nbsp;&nbsp;" + DateUtils.getDateTime(aStartDate)
						+ "至" + DateUtils.getDateTime(aEndDate) + "<br>";
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
		return new ModelAndView("show_json_msg");
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

	@javax.annotation.Resource
	public void setSysUserRoleService(SysUserRoleService sysUserRoleService) {
		this.sysUserRoleService = sysUserRoleService;
		logger.info("setSysUserRoleService");
	}

	@javax.annotation.Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
		logger.info("setSysUserService");
	}

}