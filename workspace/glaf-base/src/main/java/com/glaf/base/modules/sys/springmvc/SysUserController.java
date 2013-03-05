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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.base.modules.Constants;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysDeptRole;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysDeptRoleService;
import com.glaf.base.modules.sys.service.SysRoleService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.core.res.MessageUtils;
import com.glaf.core.res.ViewMessage;
import com.glaf.core.res.ViewMessages;
import com.glaf.core.util.PageResult;
import com.glaf.base.utils.ParamUtil;
import com.glaf.base.utils.RequestUtil;
import com.glaf.core.security.DigestUtil;

@Controller("/sys/user")
@RequestMapping("/sys/user.do")
public class SysUserController {
	private static final Log logger = LogFactory
			.getLog(SysUserController.class);

	@javax.annotation.Resource
	private SysUserService sysUserService;

	@javax.annotation.Resource
	private SysDeptRoleService sysDeptRoleService;

	@javax.annotation.Resource
	private SysTreeService sysTreeService;

	@javax.annotation.Resource
	private SysDepartmentService sysDepartmentService;

	@javax.annotation.Resource
	private SysRoleService sysRoleService;

	/**
	 * 增加角色用户
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=addRoleUser")
	public ModelAndView addRoleUser(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		logger.debug("---------addRoleUser---------------------------");
		RequestUtil.setRequestParameterToAttribute(request);
		int deptId = ParamUtil.getIntParameter(request, "deptId", 0);
		int roleId = ParamUtil.getIntParameter(request, "roleId", 0);
		SysDeptRole deptRole = sysDeptRoleService.find(deptId, roleId);
		boolean success = false;
		if (deptRole == null) {
			deptRole = new SysDeptRole();
			deptRole.setDeptId(deptId);
			deptRole.setDept(sysDepartmentService.findById(deptId));
			deptRole.setRole(sysRoleService.findById(roleId));
			sysDeptRoleService.create(deptRole);
		}
		if (deptRole != null) {
			Set<SysUser> users = deptRole.getUsers();

			long[] userIds = ParamUtil.getLongParameterValues(request, "id");
			for (int i = 0; i < userIds.length; i++) {
				SysUser user = sysUserService.findById(userIds[i]);
				if (user != null) {
					logger.info(user.getName());
					users.add(user);
				}
			}
			deptRole.setUsers(users);
			success = sysDeptRoleService.update(deptRole);
		}

		ViewMessages messages = new ViewMessages();
		if (success) {
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"user.add_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"user.add_failure"));
		}
		MessageUtils.addMessages(request, messages);

		return new ModelAndView("show_msg", modelMap);
	}

	/**
	 * 批量删除信息
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=batchDelete")
	public ModelAndView batchDelete(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		boolean ret = true;
		long[] id = ParamUtil.getLongParameterValues(request, "id");
		ret = sysUserService.deleteAll(id);
		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"user.delete_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"user.delete_failure"));
		}
		MessageUtils.addMessages(request, messages);
		return new ModelAndView("show_msg2", modelMap);
	}

	/**
	 * 删除角色用户
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=delRoleUser")
	public ModelAndView delRoleUser(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		int deptId = ParamUtil.getIntParameter(request, "deptId", 0);
		int roleId = ParamUtil.getIntParameter(request, "roleId", 0);
		SysDeptRole deptRole = sysDeptRoleService.find(deptId, roleId);
		Set users = deptRole.getUsers();

		long[] userIds = ParamUtil.getLongParameterValues(request, "id");
		for (int i = 0; i < userIds.length; i++) {
			SysUser user = sysUserService.findById(userIds[i]);
			if (user != null) {
				logger.info(user.getName());
				users.remove(user);
			}
		}
		deptRole.setUsers(users);

		ViewMessages messages = new ViewMessages();
		if (sysDeptRoleService.update(deptRole)) {
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"user.delete_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"user.delete_failure"));
		}
		MessageUtils.addMessages(request, messages);

		return new ModelAndView("show_msg2", modelMap);
	}

	/**
	 * 得到部门下所有部门列表
	 * 
	 * @param list
	 * @param parentId
	 */
	public void getAllSysDepartmentList(List list, int parentId) {
		List temp = new ArrayList();
		temp = this.sysDepartmentService.getSysDepartmentList(parentId);
		if (temp != null && temp.size() != 0) {
			for (int i = 0; i < temp.size(); i++) {
				SysDepartment element = (SysDepartment) temp.get(i);
				getAllSysDepartmentList(list, (int) element.getId());
			}
			list.addAll(temp);
		}
	}

	/**
	 * 得到本部门下所属角色的人 如果没有角色，则得到本部门里所有人
	 * 
	 * @param set
	 * @param deptId
	 * @param code
	 */
	public void getRoleUser(Set set, long deptId, String code) {
		if (!"".equals(code)) {
			Set temp = sysDeptRoleService.findRoleUser(deptId, "R011");
			set.addAll(temp);
		} else {
			List list = sysUserService.getSysUserList((int) deptId);
			set.addAll(list);
		}
	}

	/**
	 * 显示增加页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=prepareAdd")
	public ModelAndView prepareAdd(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		// RequestUtil.setRequestParameterToAttribute(request);
		return new ModelAndView("/modules/sys/user/user_add", modelMap);
	}

	/**
	 * 显示修改页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=prepareModify")
	public ModelAndView prepareModify(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		long id = ParamUtil.getLongParameter(request, "id", 0);
		SysUser bean = sysUserService.findById(id);
		request.setAttribute("bean", bean);

		SysTree parent = sysTreeService.getSysTreeByCode(Constants.TREE_DEPT);
		List list = new ArrayList();
		parent.setDeep(0);
		list.add(parent);
		sysTreeService.getSysTree(list, (int) parent.getId(), 1);
		request.setAttribute("parent", list);

		return new ModelAndView("/modules/sys/user/user_modify", modelMap);
	}

	/**
	 * 显示修改页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=prepareModifyInfo")
	public ModelAndView prepareModifyInfo(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		SysUser user = RequestUtil.getLoginUser(request);
		SysUser bean = sysUserService.findByAccount(user.getAccount());
		request.setAttribute("bean", bean);

		return new ModelAndView("/modules/sys/user/user_change_info", modelMap);
	}

	/**
	 * 显示修改页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=prepareModifyPwd")
	public ModelAndView prepareModifyPwd(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		long id = ParamUtil.getLongParameter(request, "id", 0);
		SysUser bean = sysUserService.findById(id);
		request.setAttribute("bean", bean);

		SysTree parent = sysTreeService.getSysTreeByCode(Constants.TREE_DEPT);
		List list = new ArrayList();
		parent.setDeep(0);
		list.add(parent);
		sysTreeService.getSysTree(list, (int) parent.getId(), 1);
		request.setAttribute("parent", list);

		return new ModelAndView("/modules/sys/user/user_modify_pwd", modelMap);
	}

	/**
	 * 显示重置密码页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=prepareResetPwd")
	public ModelAndView prepareResetPwd(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		long id = ParamUtil.getLongParameter(request, "id", 0);
		SysUser bean = sysUserService.findById(id);
		request.setAttribute("bean", bean);

		return new ModelAndView("/modules/sys/user/user_reset_pwd", modelMap);
	}

	/**
	 * 重置用户密码
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=resetPwd")
	public ModelAndView resetPwd(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		SysUser login = RequestUtil.getLoginUser(request);
		boolean ret = false;

		if (login.isSystemAdmin()) {
			logger.debug(login.getAccount() + " is system admin");
		}

		if (login.isDepartmentAdmin()) {
			logger.debug(login.getAccount() + " is dept admin");
		}

		if (login.isDepartmentAdmin() || login.isSystemAdmin()) {

			long id = ParamUtil.getIntParameter(request, "id", 0);
			SysUser bean = sysUserService.findById(id);

			String newPwd = ParamUtil.getParameter(request, "newPwd");
			if (bean != null && StringUtils.isNotEmpty(newPwd)) {
				try {
					bean.setPassword(DigestUtil.digestString(newPwd, "MD5"));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				ret = sysUserService.update(bean);
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

	/**
	 * 提交增加信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=saveAdd")
	public ModelAndView saveAdd(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		SysUser bean = new SysUser();
		SysDepartment department = sysDepartmentService.findById(ParamUtil
				.getIntParameter(request, "parent", 0));
		bean.setDepartment(department);
		bean.setCode(ParamUtil.getParameter(request, "code"));
		bean.setAccount(bean.getCode());
		bean.setName(ParamUtil.getParameter(request, "name"));
		// bean.setPassword(ParamUtil.getParameter(request, "password"));
		String password = ParamUtil.getParameter(request, "password");
		try {
			String pwd = DigestUtil.digestString(password, "MD5");
			bean.setPassword(pwd);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		bean.setSuperiorIds(ParamUtil.getParameter(request, "superiorIds"));
		bean.setGender(ParamUtil.getIntParameter(request, "gender", 0));
		bean.setMobile(ParamUtil.getParameter(request, "mobile"));
		bean.setEmail(ParamUtil.getParameter(request, "email"));
		bean.setTelephone(ParamUtil.getParameter(request, "telephone"));
		bean.setBlocked(ParamUtil.getIntParameter(request, "blocked", 0));
		bean.setHeadship(ParamUtil.getParameter(request, "headship"));
		bean.setUserType(ParamUtil.getIntParameter(request, "userType", 0));
		bean.setEvection(0);
		bean.setCreateTime(new Date());
		bean.setLastLoginTime(new Date());

		int ret = 0;
		if (sysUserService.findByAccount(bean.getAccount()) == null) {
			if (sysUserService.create(bean))
				ret = 2;
		} else {// 帐号存在
			ret = 1;
		}

		ViewMessages messages = new ViewMessages();
		if (ret == 2) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"user.add_success"));
		} else if (ret == 1) {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"user.existed"));
		} else {
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"user.add_failure"));
		}
		MessageUtils.addMessages(request, messages);

		// 显示列表页面
		return new ModelAndView("show_msg", modelMap);
	}

	/**
	 * 提交修改信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=saveModify")
	public ModelAndView saveModify(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
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
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=saveModifyInfo")
	public ModelAndView saveModifyInfo(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		SysUser bean = RequestUtil.getLoginUser(request);
		boolean ret = false;
		if (bean != null) {
			// bean.setPassword(ParamUtil.getParameter(request, "password"));
			// bean.setPassword(CryptUtil.EnCryptPassword(ParamUtil.getParameter(request,
			// "password")));
			SysUser user = sysUserService.findById(bean.getId());
			user.setMobile(ParamUtil.getParameter(request, "mobile"));
			user.setEmail(ParamUtil.getParameter(request, "email"));
			user.setTelephone(ParamUtil.getParameter(request, "telephone"));
			ret = sysUserService.update(user);
		}

		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			RequestUtil.setLoginUser(request, bean);
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
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=savePwd")
	public ModelAndView savePwd(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
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

	/**
	 * 查询获取用户列表
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=selectSysUser")
	public ModelAndView selectSysUser(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = Constants.PAGE_SIZE;
		int deptId = ParamUtil.getIntParameter(request, "deptId", 0);
		int multDate = ParamUtil.getIntParameter(request, "multDate", 0);
		String name = ParamUtil.getParameter(request, "fullName", null);
		PageResult pager = sysUserService.getSysUserList(deptId, name, pageNo,
				pageSize);
		request.setAttribute("pager", pager);
		request.setAttribute("multDate", new Integer(multDate));

		return new ModelAndView("/modules/sys/user/user_select", modelMap);
	}

	/**
	 * 查询获取特定部门用户列表
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=selectSysUserByDept")
	public ModelAndView selectSysUserByDept(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = Constants.PAGE_SIZE;
		int deptId = ParamUtil.getIntParameter(request, "deptId", 0);
		String name = ParamUtil.getParameter(request, "fullName", null);

		SysDepartment sysDepartment = sysDepartmentService.findById(deptId);
		request.setAttribute("sysDepartment", sysDepartment);
		PageResult pager = null;
		if (name != null && !"".equals(name)) {
			pager = sysUserService.getSysUserList(deptId, name, pageNo,
					pageSize);
		} else {
			pager = sysUserService.getSysUserList(deptId, pageNo, pageSize);
		}
		request.setAttribute("pager", pager);

		return new ModelAndView("/modules/sys/user/userByDept_list", modelMap);
	}

	/**
	 * 设置用户角色
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=setRole")
	public ModelAndView setRole(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		ViewMessages messages = new ViewMessages();
		long userId = ParamUtil.getIntParameter(request, "user_id", 0);
		SysUser user = sysUserService.findById(userId);// 查找用户对象
		if (user != null) {// 用户存在
			long[] id = ParamUtil.getLongParameterValues(request, "id");// 获取页面参数
			if (id != null) {
				Set delRoles = new HashSet();
				Set oldRoles = user.getRoles();
				Set newRoles = new HashSet();
				for (int i = 0; i < id.length; i++) {
					SysDeptRole role = sysDeptRoleService.findById(id[i]);// 查找角色对象
					if (role != null) {
						newRoles.add(role);// 加入到角色列表
					}
				}
				delRoles.addAll(oldRoles);
				oldRoles.retainAll(newRoles);// 公共权限
				delRoles.removeAll(newRoles);// 待删除的权限
				newRoles.removeAll(oldRoles);// 待增加的权限

				if (sysUserService.updateRole(user, delRoles, newRoles)) {// 授权成功
					messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
							"user.role_success"));
				} else {// 保存失败
					messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
							"user.role_failure"));
				}
			}
		}
		MessageUtils.addMessages(request, messages);
		return new ModelAndView("show_msg", modelMap);
	}

	public void setSysDepartmentService(
			SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
		logger.info("setSysDepartmentService");
	}

	public void setSysDeptRoleService(SysDeptRoleService sysDeptRoleService) {
		this.sysDeptRoleService = sysDeptRoleService;
		logger.info("setSysDeptRoleService");
	}

	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
		logger.info("setSysRoleService");
	}

	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
		logger.info("setSysTreeService");
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
		logger.info("setSysUserService");
	}

	/**
	 * 显示部门下所有人
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showDeptUsers")
	public ModelAndView showDeptUsers(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		List list = new ArrayList();
		Set set = new HashSet();
		// 6:
		long deptId = ParamUtil.getLongParameter(request, "dept", 5);
		String roleCode = ParamUtil.getParameter(request, "code", "");
		SysDepartment node = this.sysDepartmentService.findById(deptId);
		if (node != null) {
			list.add(node);
			this.getAllSysDepartmentList(list, (int) node.getId());
		} else {
			this.getAllSysDepartmentList(list, (int) deptId);
		}
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			SysDepartment element = (SysDepartment) iter.next();
			this.getRoleUser(set, element.getId(), roleCode);
		}
		request.setAttribute("user", set);
		return new ModelAndView("/modules/sys/user/duty_select", modelMap);
	}

	/**
	 * 显示框架页面
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showFrame")
	public ModelAndView showFrame(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		SysTree bean = sysTreeService.getSysTreeByCode(Constants.TREE_DEPT);
		request.setAttribute("parent", bean.getId() + "");
		return new ModelAndView("/modules/sys/user/user_frame", modelMap);
	}

	/**
	 * 显示所有列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showList")
	public ModelAndView showList(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		int deptId = ParamUtil.getIntParameter(request, "parent", 0);
		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = ParamUtil.getIntParameter(request, "page_size",
				Constants.PAGE_SIZE);
		PageResult pager = sysUserService.getSysUserList(deptId, pageNo,
				pageSize);
		request.setAttribute("department",
				sysDepartmentService.findById(deptId));
		request.setAttribute("pager", pager);

		SysDepartment dept = sysDepartmentService.findById(deptId);
		List list = new ArrayList();
		sysDepartmentService.findNestingDepartment(list, dept);
		request.setAttribute("nav", list);

		return new ModelAndView("/modules/sys/user/user_list", modelMap);
	}

	/**
	 * 显示所有列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showPasswordList")
	public ModelAndView showPasswordList(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		String userName = ParamUtil.getParameter(request, "userName");
		String account = ParamUtil.getParameter(request, "account");
		int deptId = ParamUtil.getIntParameter(request, "deptId", 0);
		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = ParamUtil.getIntParameter(request, "page_size", 20);

		PageResult pager = sysUserService.getSysUserList(deptId, userName,
				account, pageNo, pageSize);
		request.setAttribute("pager", pager);
		return new ModelAndView("/modules/sys/user/user_password_list",
				modelMap);
	}

	/**
	 * 显示角色
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showRole")
	public ModelAndView showRole(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		long id = ParamUtil.getIntParameter(request, "user_id", 0);
		SysUser user = sysUserService.findById(id);
		request.setAttribute("user", user);
		request.setAttribute("list",
				sysDeptRoleService.getRoleList(user.getDepartment().getId()));
		// 显示列表页面
		return new ModelAndView("/modules/sys/user/user_role", modelMap);
	}

	/**
	 * 显示角色用户列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showRoleUser")
	public ModelAndView showRoleUser(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		int deptId = ParamUtil.getIntParameter(request, "deptId", 0);
		long roleId = ParamUtil.getLongParameter(request, "roleId", 0);

		// 部门信息
		SysDepartment dept = sysDepartmentService.findById(deptId);
		List list = new ArrayList();
		sysDepartmentService.findNestingDepartment(list, dept);
		request.setAttribute("nav", list);

		// 角色
		SysRole role = sysRoleService.findById(roleId);
		request.setAttribute("role", role.getName());

		Set users = sysDeptRoleService.findRoleUser(deptId, role.getCode());
		request.setAttribute("list", users);

		return new ModelAndView("/modules/sys/user/deptRole_user", modelMap);
	}

	/**
	 * 显示角色用户列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showSelUser")
	public ModelAndView showSelUser(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		int deptId = ParamUtil.getIntParameter(request, "deptId2", 0);
		if (deptId != 0) {
			request.setAttribute("list", sysUserService.getSysUserList(deptId));
		}

		return new ModelAndView("/modules/sys/user/dept_user_sel", modelMap);
	}

	/**
	 * 增加角色用户
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showUser")
	public ModelAndView showUser(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		long userId = ParamUtil.getLongParameter(request, "userId", 0);
		SysUser user = sysUserService.findById(userId);
		request.setAttribute("user", user);

		return new ModelAndView("/modules/sys/user/user_info", modelMap);
	}
}