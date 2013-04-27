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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysDeptRole;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.query.SysUserQuery;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysDeptRoleService;
import com.glaf.base.modules.sys.service.SysRoleService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.utils.ParamUtil;
import com.glaf.base.utils.RequestUtil;
import com.glaf.core.cache.CacheUtils;
import com.glaf.core.res.MessageUtils;
import com.glaf.core.res.ViewMessage;
import com.glaf.core.res.ViewMessages;
import com.glaf.core.security.DigestUtil;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.Tools;

@Controller("/rs/sys/user")
@Path("/rs/sys/user")
public class SysUserResource {
	private static final Log logger = LogFactory.getLog(SysUserResource.class);

	protected SysDepartmentService sysDepartmentService;

	protected SysDeptRoleService sysDeptRoleService;

	protected SysRoleService sysRoleService;

	protected SysTreeService sysTreeService;

	protected SysUserService sysUserService;

	protected ITableDataService tableDataService;

	/**
	 * 增加角色用户
	 * 
	 * @param request
	 * @param uriInfo
	 * @return
	 */
	@Path("addRoleUser")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public ModelAndView addRoleUser(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		logger.debug("---------addRoleUser---------------------------");
		RequestUtils.setRequestParameterToAttribute(request);
		int deptId = ParamUtil.getIntParameter(request, "deptId", 0);
		int roleId = ParamUtil.getIntParameter(request, "roleId", 0);
		SysDeptRole deptRole = sysDeptRoleService.find(deptId, roleId);
		boolean success = false;
		if (deptRole == null) {
			deptRole = new SysDeptRole();
			deptRole.setDeptId(deptId);
			deptRole.setDept(sysDepartmentService.findById(deptId));
			deptRole.setSysRoleId(roleId);
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

		return new ModelAndView("show_json_msg");
	}

	/**
	 * 批量删除信息
	 * 
	 * @param request
	 * @param uriInfo
	 * @return
	 */
	@Path("batchDelete")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public ModelAndView batchDelete(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		RequestUtils.setRequestParameterToAttribute(request);
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
		return new ModelAndView("show_json_msg");
	}

	/**
	 * 删除角色用户
	 * 
	 * @param request
	 * @param uriInfo
	 * @return
	 */
	@Path("delRoleUser")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public ModelAndView delRoleUser(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		RequestUtils.setRequestParameterToAttribute(request);
		int deptId = ParamUtil.getIntParameter(request, "deptId", 0);
		int roleId = ParamUtil.getIntParameter(request, "roleId", 0);
		SysDeptRole deptRole = sysDeptRoleService.find(deptId, roleId);
		boolean sucess = false;
		try {
			long[] userIds = ParamUtil.getLongParameterValues(request, "id");
			sysUserService.deleteRoleUsers(deptRole, userIds);
			sucess = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			sucess = false;
		}

		ViewMessages messages = new ViewMessages();
		if (sucess) {
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"user.delete_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"user.delete_failure"));
		}
		MessageUtils.addMessages(request, messages);

		return new ModelAndView("show_json_msg");
	}

	/**
	 * 得到部门下所有部门列表
	 * 
	 * @param list
	 * @param parentId
	 */
	public void getAllSysDepartmentList(List<SysDepartment> list, int parentId) {
		List<SysDepartment> temp = new ArrayList<SysDepartment>();
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
	public void getRoleUser(Set<SysUser> set, long deptId, String code) {
		if (!"".equals(code)) {
			Set<SysUser> temp = sysDeptRoleService.findRoleUser(deptId, "R011");
			set.addAll(temp);
		} else {
			List<SysUser> list = sysUserService.getSysUserList((int) deptId);
			set.addAll(list);
		}
	}

	@GET
	@POST
	@Path("json")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	@ResponseBody
	public byte[] json(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysUserQuery query = new SysUserQuery();
		Tools.populate(query, params);

		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "easyui";
		}
		int start = 0;
		int limit = 10;
		String orderName = null;
		String order = null;

		int pageNo = ParamUtils.getInt(params, "page");
		limit = ParamUtils.getInt(params, "rows");
		start = (pageNo - 1) * limit;
		orderName = ParamUtils.getString(params, "sortName");
		order = ParamUtils.getString(params, "sortOrder");

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = sysUserService.getSysUserCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortOrder(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			List<SysUser> list = sysUserService.getSysUsersByQueryCriteria(
					start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SysUser sysUser : list) {
					JSONObject rowJSON = sysUser.toJsonObject();
					rowJSON.put("id", sysUser.getId());
					rowJSON.put("actorId", sysUser.getAccount());
					rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
				}

			}
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 重置用户密码
	 * 
	 * @param request
	 * @param uriInfo
	 * @return
	 */
	@Path("resetPwd")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public ModelAndView resetPwd(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		RequestUtils.setRequestParameterToAttribute(request);
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
				bean.setUpdateBy(bean.getAccount());
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
		return new ModelAndView("show_json_msg");
	}

	/**
	 * 提交增加信息
	 * 
	 * @param request
	 * @param uriInfo
	 * @return
	 */
	@Path("saveAdd")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public ModelAndView saveAdd(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		RequestUtils.setRequestParameterToAttribute(request);
		SysUser bean = new SysUser();
		SysDepartment department = sysDepartmentService.findById(ParamUtil
				.getIntParameter(request, "parent", 0));
		bean.setDepartment(department);
		bean.setCode(ParamUtil.getParameter(request, "code"));
		bean.setAccount(bean.getCode());
		bean.setName(ParamUtil.getParameter(request, "name"));

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
		bean.setCreateBy(bean.getAccount());
		bean.setUpdateBy(bean.getAccount());

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
		return new ModelAndView("show_json_msg");
	}

	/**
	 * 提交修改信息
	 * 
	 * @param request
	 * @param uriInfo
	 * @return
	 */
	@Path("saveModify")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public ModelAndView saveModify(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		RequestUtils.setRequestParameterToAttribute(request);
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
			bean.setUpdateBy(bean.getAccount());
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
		return new ModelAndView("show_json_msg");
	}

	/**
	 * 提交修改信息
	 * 
	 * @param request
	 * @param uriInfo
	 * @return
	 */
	@Path("saveModifyInfo")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public ModelAndView saveModifyInfo(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		RequestUtils.setRequestParameterToAttribute(request);
		SysUser bean = RequestUtil.getLoginUser(request);
		boolean ret = false;
		if (bean != null) {
			SysUser user = sysUserService.findById(bean.getId());
			user.setMobile(ParamUtil.getParameter(request, "mobile"));
			user.setEmail(ParamUtil.getParameter(request, "email"));
			user.setTelephone(ParamUtil.getParameter(request, "telephone"));
			user.setUpdateBy(bean.getAccount());
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
		return new ModelAndView("show_json_msg");
	}

	/**
	 * 修改用户密码
	 * 
	 * @param request
	 * @param uriInfo
	 * @return
	 */
	@Path("savePwd")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public ModelAndView savePwd(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		RequestUtils.setRequestParameterToAttribute(request);
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
					user.setUpdateBy(bean.getAccount());
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
		return new ModelAndView("show_json_msg");
	}

	/**
	 * 设置用户角色
	 * 
	 * @param request
	 * @param uriInfo
	 * @return
	 */
	@Path("setRole")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public ModelAndView setRole(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		RequestUtils.setRequestParameterToAttribute(request);
		logger.debug(RequestUtils.getParameterMap(request));
		ViewMessages messages = new ViewMessages();
		long userId = ParamUtil.getIntParameter(request, "user_id", 0);
		SysUser user = sysUserService.findById(userId);// 查找用户对象

		if (user != null) {// 用户存在
			long[] id = ParamUtil.getLongParameterValues(request, "id");// 获取页面参数
			if (id != null) {
				Set<SysDeptRole> delRoles = new HashSet<SysDeptRole>();
				Set<SysDeptRole> oldRoles = user.getRoles();
				Set<SysDeptRole> newRoles = new HashSet<SysDeptRole>();
				for (int i = 0; i < id.length; i++) {
					logger.debug("id[" + i + "]=" + id[i]);
					SysDeptRole role = sysDeptRoleService.findById(id[i]);// 查找角色对象
					if (role != null) {
						newRoles.add(role);// 加入到角色列表
					}
				}

				oldRoles.retainAll(newRoles);// 公共权限
				delRoles.removeAll(newRoles);// 待删除的权限
				newRoles.removeAll(oldRoles);// 待增加的权限
				user.setUpdateBy(RequestUtils.getActorId(request));

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
		return new ModelAndView("show_json_msg");
	}

	@javax.annotation.Resource
	public void setSysDepartmentService(
			SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
		logger.info("setSysDepartmentService");
	}

	@javax.annotation.Resource
	public void setSysDeptRoleService(SysDeptRoleService sysDeptRoleService) {
		this.sysDeptRoleService = sysDeptRoleService;
		logger.info("setSysDeptRoleService");
	}

	@javax.annotation.Resource
	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
		logger.info("setSysRoleService");
	}

	@javax.annotation.Resource
	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
		logger.info("setSysTreeService");
	}

	@javax.annotation.Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
		logger.info("setSysUserService");
	}

	@javax.annotation.Resource
	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

}