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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.base.modules.Constants;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysDeptRoleService;
import com.glaf.base.modules.sys.service.SysRoleService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.utils.ParamUtil;
import com.glaf.base.utils.RequestUtil;
import com.glaf.core.cache.CacheUtils;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.res.MessageUtils;
import com.glaf.core.res.ViewMessage;
import com.glaf.core.res.ViewMessages;
import com.glaf.core.security.DigestUtil;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;

@Controller("/identity/user")
@RequestMapping("/identity/user.do")
public class UserController {
	protected static final Log logger = LogFactory.getLog(UserController.class);

	protected SysDepartmentService sysDepartmentService;

	protected SysDeptRoleService sysDeptRoleService;

	protected SysRoleService sysRoleService;

	protected SysTreeService sysTreeService;

	protected SysUserService sysUserService;

	protected ITableDataService tableDataService;

	/**
	 * 显示修改页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=prepareModifyInfo")
	public ModelAndView prepareModifyInfo(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		SysUser user = RequestUtil.getLoginUser(request);
		SysUser bean = sysUserService.findByAccount(user.getAccount());
		request.setAttribute("bean", bean);

		if (bean != null && StringUtils.isNotEmpty(bean.getSuperiorIds())) {
			List<String> userIds = StringTools.split(bean.getSuperiorIds());
			StringBuffer buffer = new StringBuffer();
			if (userIds != null && !userIds.isEmpty()) {
				for (String userId : userIds) {
					SysUser u = sysUserService.findByAccount(userId);
					if (u != null) {
						buffer.append(u.getName()).append("[")
								.append(u.getAccount()).append("] ");
					}
				}
				request.setAttribute("x_users_name", buffer.toString());
			}
		}

		String x_view = ViewProperties
				.getString("identity.user.prepareModifyInfo");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/identity/user/user_change_info",
				modelMap);
	}

	/**
	 * 显示修改页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=prepareModifyPwd")
	public ModelAndView prepareModifyPwd(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		SysUser bean = RequestUtil.getLoginUser(request);
		request.setAttribute("bean", bean);

		SysTree parent = sysTreeService.getSysTreeByCode(Constants.TREE_DEPT);
		List<SysTree> list = new ArrayList<SysTree>();
		parent.setDeep(0);
		list.add(parent);
		sysTreeService.getSysTree(list, (int) parent.getId(), 1);
		request.setAttribute("parent", list);

		String x_view = ViewProperties
				.getString("identity.user.prepareModifyPwd");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/identity/user/user_modify_pwd",
				modelMap);
	}

	/**
	 * 提交修改信息
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=saveModify")
	public ModelAndView saveModify(HttpServletRequest request, ModelMap modelMap) {
		long id = ParamUtil.getIntParameter(request, "id", 0);
		SysUser bean = sysUserService.findById(id);
		boolean ret = false;
		if (bean != null) {
			SysDepartment department = sysDepartmentService.findById(ParamUtil
					.getIntParameter(request, "parent", 0));
			bean.setDepartment(department);
			bean.setName(ParamUtil.getParameter(request, "name"));
			bean.setSuperiorIds(ParamUtil.getParameter(request, "superiorIds"));
			bean.setGender(ParamUtil.getIntParameter(request, "gender", 0));
			bean.setMobile(ParamUtil.getParameter(request, "mobile"));
			bean.setEmail(ParamUtil.getParameter(request, "email"));
			bean.setTelephone(ParamUtil.getParameter(request, "telephone"));
			bean.setEvection(ParamUtil.getIntParameter(request, "evection", 0));
			bean.setBlocked(ParamUtil.getIntParameter(request, "blocked", 0));
			bean.setHeadship(ParamUtil.getParameter(request, "headship"));
			bean.setUserType(ParamUtil.getIntParameter(request, "userType", 0));
			bean.setUpdateBy(RequestUtils.getActorId(request));
			ret = sysUserService.update(bean);
		}

		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"user.modify_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"user.modify_failure"));
		}
		MessageUtils.addMessages(request, messages);
		return new ModelAndView("show_msg", modelMap);
	}

	/**
	 * 提交修改信息
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=saveModifyInfo")
	public ModelAndView saveModifyInfo(HttpServletRequest request,
			ModelMap modelMap) {
		SysUser bean = RequestUtil.getLoginUser(request);
		boolean ret = false;
		if (bean != null) {
			SysUser user = sysUserService.findById(bean.getId());
			user.setMobile(ParamUtil.getParameter(request, "mobile"));
			user.setEmail(ParamUtil.getParameter(request, "email"));
			user.setTelephone(ParamUtil.getParameter(request, "telephone"));
			user.setUpdateBy(RequestUtils.getActorId(request));
			ret = sysUserService.update(user);
			CacheUtils.clearUserCache(user.getAccount());
		}

		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"user.modify_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"user.modify_failure"));
		}
		MessageUtils.addMessages(request, messages);
		return new ModelAndView("show_msg", modelMap);
	}

	/**
	 * 修改用户密码
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=savePwd")
	public ModelAndView savePwd(HttpServletRequest request, ModelMap modelMap) {
		SysUser bean = RequestUtil.getLoginUser(request);
		boolean ret = false;
		String oldPwd = ParamUtil.getParameter(request, "oldPwd");
		String newPwd = ParamUtil.getParameter(request, "newPwd");
		if (bean != null && StringUtils.isNotEmpty(oldPwd)
				&& StringUtils.isNotEmpty(newPwd)) {
			SysUser user = sysUserService.findById(bean.getId());
			try {
				String encPwd = DigestUtil.digestString(oldPwd, "MD5");
				if (StringUtils.equals(encPwd, user.getPassword())) {
					user.setPassword(DigestUtil.digestString(newPwd, "MD5"));
					user.setUpdateBy(RequestUtils.getActorId(request));
					user.setLastChangePasswordDate(new Date());
					user.setIsChangePassword(2);
					ret = sysUserService.update(user);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"user.modify_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"user.modify_failure"));
		}
		MessageUtils.addMessages(request, messages);
		return new ModelAndView("show_msg", modelMap);
	}

	@RequestMapping(params = "method=savePwdLogin")
	@ResponseBody
	public byte[] savePwdLogin(HttpServletRequest request, ModelMap modelMap) {
		SysUser bean = RequestUtil.getLoginUser(request);
		boolean ret = false;
		String oldPwd = ParamUtil.getParameter(request, "oldPwd");
		String newPwd = ParamUtil.getParameter(request, "newPwd");
		if (bean != null && StringUtils.isNotEmpty(oldPwd)
				&& StringUtils.isNotEmpty(newPwd)) {
			SysUser user = sysUserService.findById(bean.getId());
			try {
				String encPwd = DigestUtil.digestString(oldPwd, "MD5");
				if (StringUtils.equals(encPwd, user.getPassword())) {
					user.setPassword(DigestUtil.digestString(newPwd, "MD5"));
					user.setUpdateBy(RequestUtils.getActorId(request));
					user.setLastChangePasswordDate(new Date());
					user.setIsChangePassword(2);
					ret = sysUserService.updateUser(user);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if (ret) {// 保存成功
			return ResponseUtils.responseJsonResult(true, "保存成功!");
		} else {// 保存失败
			return ResponseUtils.responseJsonResult(true, "保存失败!");
		}
	}

	@javax.annotation.Resource
	public void setSysDepartmentService(
			SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;

	}

	@javax.annotation.Resource
	public void setSysDeptRoleService(SysDeptRoleService sysDeptRoleService) {
		this.sysDeptRoleService = sysDeptRoleService;

	}

	@javax.annotation.Resource
	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;

	}

	@javax.annotation.Resource
	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;

	}

	@javax.annotation.Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;

	}

	@javax.annotation.Resource
	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

}