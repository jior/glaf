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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.TreeModel;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.res.MessageUtils;
import com.glaf.core.res.ViewMessage;
import com.glaf.core.res.ViewMessages;
import com.glaf.core.security.DigestUtil;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.service.ITreeModelService;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.base.modules.Constants;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.Dictory;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysDeptRole;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.query.SysUserQuery;
import com.glaf.base.modules.sys.service.DictoryService;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysDeptRoleService;
import com.glaf.base.modules.sys.service.SysRoleService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.utils.ParamUtil;

@Controller("/branch/user")
@RequestMapping("/branch/user.do")
public class BranchUserController {
	private static final Log logger = LogFactory
			.getLog(BranchUserController.class);

	protected DictoryService dictoryService;

	protected SysDepartmentService sysDepartmentService;

	protected SysDeptRoleService sysDeptRoleService;

	protected SysRoleService sysRoleService;

	protected SysTreeService sysTreeService;

	protected SysUserService sysUserService;

	protected ITableDataService tableDataService;

	protected ITreeModelService treeModelService;

	/**
	 * 增加角色用户
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=addRoleUser")
	public ModelAndView addRoleUser(HttpServletRequest request,
			ModelMap modelMap) {
		logger.debug("---------addRoleUser---------------------------");
		RequestUtils.setRequestParameterToAttribute(request);
		int deptId = ParamUtil.getIntParameter(request, "deptId", 0);
		int roleId = ParamUtil.getIntParameter(request, "roleId", 0);
		boolean success = false;
		String actorId = RequestUtils.getActorId(request);
		List<Long> nodeIds = new ArrayList<Long>();
		nodeIds.add(-1L);
		SysUserQuery qx = new SysUserQuery();
		qx.setAccount(actorId);
		qx.setRoleCode(SysConstants.BRANCH_ADMIN);
		List<SysTree> subTrees = sysTreeService.getRoleUserTrees(qx);
		if (subTrees != null && !subTrees.isEmpty()) {
			for (SysTree tree : subTrees) {
				List<TreeModel> children = treeModelService
						.getChildrenTreeModels(tree.getId());
				if (children != null && !children.isEmpty()) {
					for (TreeModel child : children) {
						if (!nodeIds.contains(child.getId())) {
							nodeIds.add(child.getId());
						}
					}
				}
			}
		}

		SysDepartment department = sysDepartmentService.findById(deptId);
		if (department != null) {
			SysTree tree = sysTreeService.findById(department.getNodeId());
			if (tree != null && nodeIds.contains(tree.getId())) {
				SysDeptRole deptRole = sysDeptRoleService.find(deptId, roleId);
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
					long[] userIds = ParamUtil.getLongParameterValues(request,
							"id");
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
			}
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

	@RequestMapping(params = "method=deptUsers")
	public ModelAndView deptUsers(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtils
					.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtils.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}

		String x_view = ViewProperties.getString("branch.user.deptUsers");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/modules/branch/user/deptUsers", modelMap);
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

	@RequestMapping(params = "method=json")
	@ResponseBody
	public byte[] json(HttpServletRequest request) throws IOException {
		Long deptId = ParamUtil.getLongParameter(request, "parent", 0);
		Long nodeId = RequestUtils.getLong(request, "nodeId");
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysUserQuery query = new SysUserQuery();
		Tools.populate(query, params);
		if (nodeId > 0) {
			SysDepartment dept = sysDepartmentService
					.getSysDepartmentByNodeId(nodeId);
			if (dept != null) {
				query.deptId(dept.getId());
			}
		} else if (deptId > 0) {
			query.deptId(deptId);
		} else {
			query.deptId(-1L);
		}

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
			limit = Paging.DEFAULT_PAGE_SIZE;
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
		} else {
			result.put("total", total);
			result.put("totalCount", total);
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
		}
		return result.toString().getBytes("UTF-8");
	}

	@RequestMapping
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtils
					.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtils.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}

		String x_view = ViewProperties.getString("branch.user.list");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/modules/branch/user/list", modelMap);
	}

	/**
	 * 显示增加页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=prepareAdd")
	public ModelAndView prepareAdd(HttpServletRequest request, ModelMap modelMap) {

		List<Dictory> dictories = dictoryService
				.getDictoryList(SysConstants.USER_HEADSHIP);
		modelMap.put("dictories", dictories);

		String x_view = ViewProperties.getString("branch.user.prepareAdd");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/branch/user/user_add", modelMap);
	}

	/**
	 * 显示修改页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=prepareModify")
	public ModelAndView prepareModify(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		long id = ParamUtil.getLongParameter(request, "id", 0);
		SysUser bean = sysUserService.findById(id);
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

		List<Dictory> dictories = dictoryService
				.getDictoryList(SysConstants.USER_HEADSHIP);
		modelMap.put("dictories", dictories);

		SysTree parent = sysTreeService.getSysTreeByCode(Constants.TREE_DEPT);
		List<SysTree> list = new ArrayList<SysTree>();
		parent.setDeep(0);
		list.add(parent);
		sysTreeService.getSysTree(list, (int) parent.getId(), 1);
		request.setAttribute("parent", list);

		String x_view = ViewProperties.getString("branch.user.prepareModify");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/branch/user/user_modify", modelMap);
	}

	/**
	 * 显示重置密码页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=prepareResetPwd")
	public ModelAndView prepareResetPwd(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		long id = ParamUtil.getLongParameter(request, "id", 0);
		SysUser bean = sysUserService.findById(id);
		request.setAttribute("bean", bean);

		String x_view = ViewProperties.getString("user.prepareResetPwd");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/branch/user/user_reset_pwd", modelMap);
	}

	/**
	 * 重置用户密码
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=resetPwd")
	public ModelAndView resetPwd(HttpServletRequest request, ModelMap modelMap) {
		boolean ret = false;
		String actorId = RequestUtils.getActorId(request);
		List<Long> nodeIds = new ArrayList<Long>();
		nodeIds.add(-1L);
		SysUserQuery qx = new SysUserQuery();
		qx.setAccount(actorId);
		qx.setRoleCode(SysConstants.BRANCH_ADMIN);
		List<SysTree> subTrees = sysTreeService.getRoleUserTrees(qx);
		if (subTrees != null && !subTrees.isEmpty()) {
			for (SysTree tree : subTrees) {
				List<TreeModel> children = treeModelService
						.getChildrenTreeModels(tree.getId());
				if (children != null && !children.isEmpty()) {
					for (TreeModel child : children) {
						if (!nodeIds.contains(child.getId())) {
							nodeIds.add(child.getId());
						}
					}
				}
			}
		}

		long id = ParamUtil.getIntParameter(request, "id", 0);
		SysUser bean = sysUserService.findById(id);

		/**
		 * 系统管理员的密码不允许重置
		 */
		if (bean != null && !bean.isSystemAdministrator()) {
			/**
			 * 保修改的用户所属部门是分级管理员管辖的部门
			 */
			SysDepartment department = sysDepartmentService.findById(bean
					.getDeptId());
			if (department != null) {
				SysTree tree = sysTreeService.findById(department.getNodeId());
				if (tree != null && nodeIds.contains(tree.getId())) {
					String newPwd = ParamUtil.getParameter(request, "newPwd");
					if (bean != null && StringUtils.isNotEmpty(newPwd)) {
						try {
							bean.setPassword(DigestUtil.digestString(newPwd,
									"MD5"));
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						bean.setUpdateBy(bean.getAccount());
						ret = sysUserService.update(bean);
					}
				}
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
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=saveAdd")
	public ModelAndView saveAdd(HttpServletRequest request, ModelMap modelMap) {
		SysUser bean = new SysUser();
		String actorId = RequestUtils.getActorId(request);
		List<Long> nodeIds = new ArrayList<Long>();
		nodeIds.add(-1L);
		SysUserQuery qx = new SysUserQuery();
		qx.setAccount(actorId);
		qx.setRoleCode(SysConstants.BRANCH_ADMIN);
		List<SysTree> subTrees = sysTreeService.getRoleUserTrees(qx);
		if (subTrees != null && !subTrees.isEmpty()) {
			for (SysTree tree : subTrees) {
				List<TreeModel> children = treeModelService
						.getChildrenTreeModels(tree.getId());
				if (children != null && !children.isEmpty()) {
					for (TreeModel child : children) {
						if (!nodeIds.contains(child.getId())) {
							nodeIds.add(child.getId());
						}
					}
				}
			}
		}

		int ret = 0;
		SysDepartment department = null;
		long nodeId = RequestUtils.getLong(request, "nodeId");
		if (nodeId > 0) {
			department = sysDepartmentService.getSysDepartmentByNodeId(nodeId);
			bean.setDepartment(department);
		} else {
			department = sysDepartmentService.findById(ParamUtil
					.getIntParameter(request, "parent", 0));
			bean.setDepartment(department);
		}
		/**
		 * 保证添加的用户所属部门是分级管理员管辖的部门
		 */
		if (department != null) {
			SysTree tree = sysTreeService.findById(department.getNodeId());
			if (tree != null && nodeIds.contains(tree.getId())) {
				bean.setDeptId(department.getId());
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
				bean.setSuperiorIds(ParamUtil.getParameter(request,
						"superiorIds"));
				bean.setGender(ParamUtil.getIntParameter(request, "gender", 0));
				bean.setMobile(ParamUtil.getParameter(request, "mobile"));
				bean.setEmail(ParamUtil.getParameter(request, "email"));
				bean.setTelephone(ParamUtil.getParameter(request, "telephone"));
				bean.setBlocked(ParamUtil
						.getIntParameter(request, "blocked", 0));
				bean.setHeadship(ParamUtil.getParameter(request, "headship"));
				bean.setUserType(ParamUtil.getIntParameter(request, "userType",
						0));
				bean.setEvection(0);
				bean.setCreateTime(new Date());
				bean.setLastLoginTime(new Date());
				bean.setCreateBy(RequestUtils.getActorId(request));
				bean.setUpdateBy(RequestUtils.getActorId(request));

				if (sysUserService.findByAccount(bean.getAccount()) == null) {
					if (sysUserService.create(bean))
						ret = 2;
				} else {// 帐号存在
					ret = 1;
				}
			}
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
			String actorId = RequestUtils.getActorId(request);
			List<Long> nodeIds = new ArrayList<Long>();
			nodeIds.add(-1L);
			SysUserQuery qx = new SysUserQuery();
			qx.setAccount(actorId);
			qx.setRoleCode(SysConstants.BRANCH_ADMIN);
			List<SysTree> subTrees = sysTreeService.getRoleUserTrees(qx);
			if (subTrees != null && !subTrees.isEmpty()) {
				for (SysTree tree : subTrees) {
					List<TreeModel> children = treeModelService
							.getChildrenTreeModels(tree.getId());
					if (children != null && !children.isEmpty()) {
						for (TreeModel child : children) {
							if (!nodeIds.contains(child.getId())) {
								nodeIds.add(child.getId());
							}
						}
					}
				}
			}

			/**
			 * 保证添加的用户所属部门是分级管理员管辖的部门
			 */
			if (department != null) {
				SysTree tree = sysTreeService.findById(department.getNodeId());
				if (tree != null && nodeIds.contains(tree.getId())) {
					bean.setDepartment(department);
					bean.setName(ParamUtil.getParameter(request, "name"));
					bean.setSuperiorIds(ParamUtil.getParameter(request,
							"superiorIds"));
					bean.setGender(ParamUtil.getIntParameter(request, "gender",
							0));
					bean.setMobile(ParamUtil.getParameter(request, "mobile"));
					bean.setEmail(ParamUtil.getParameter(request, "email"));
					bean.setTelephone(ParamUtil.getParameter(request,
							"telephone"));
					bean.setEvection(ParamUtil.getIntParameter(request,
							"evection", 0));
					bean.setBlocked(ParamUtil.getIntParameter(request,
							"blocked", 0));
					bean.setHeadship(ParamUtil
							.getParameter(request, "headship"));
					bean.setUserType(ParamUtil.getIntParameter(request,
							"userType", 0));
					bean.setUpdateBy(RequestUtils.getActorId(request));
					ret = sysUserService.update(bean);
				}
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
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=selectSysUser")
	public ModelAndView selectSysUser(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = Constants.PAGE_SIZE;
		int deptId = ParamUtil.getIntParameter(request, "deptId", 0);
		int multDate = ParamUtil.getIntParameter(request, "multDate", 0);
		String name = ParamUtil.getParameter(request, "fullName", null);
		PageResult pager = sysUserService.getSysUserList(deptId, name, pageNo,
				pageSize);
		request.setAttribute("pager", pager);
		request.setAttribute("multDate", new Integer(multDate));

		String x_view = ViewProperties.getString("branch.user.selectSysUser");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/branch/user/user_select", modelMap);
	}

	/**
	 * 查询获取特定部门用户列表
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=selectSysUserByDept")
	public ModelAndView selectSysUserByDept(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
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

		String x_view = ViewProperties
				.getString("branch.user.selectSysUserByDept");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/branch/user/userByDept_list",
				modelMap);
	}

	@javax.annotation.Resource
	public void setDictoryService(DictoryService dictoryService) {
		this.dictoryService = dictoryService;
	}

	/**
	 * 设置用户角色
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=setRole")
	public ModelAndView setRole(HttpServletRequest request, ModelMap modelMap) {
		logger.debug(RequestUtils.getParameterMap(request));
		ViewMessages messages = new ViewMessages();
		long userId = ParamUtil.getIntParameter(request, "user_id", 0);
		SysUser user = sysUserService.findById(userId);// 查找用户对象
		if (user != null && user.getDeptId() > 0) {// 用户存在
			String actorId = RequestUtils.getActorId(request);
			List<Long> nodeIds = new ArrayList<Long>();
			nodeIds.add(-1L);
			SysUserQuery qx = new SysUserQuery();
			qx.setAccount(actorId);
			qx.setRoleCode(SysConstants.BRANCH_ADMIN);
			List<SysTree> subTrees = sysTreeService.getRoleUserTrees(qx);
			if (subTrees != null && !subTrees.isEmpty()) {
				for (SysTree tree : subTrees) {
					List<TreeModel> children = treeModelService
							.getChildrenTreeModels(tree.getId());
					if (children != null && !children.isEmpty()) {
						for (TreeModel child : children) {
							if (!nodeIds.contains(child.getId())) {
								nodeIds.add(child.getId());
							}
						}
					}
				}
			}

			SysDepartment department = sysDepartmentService.findById(user
					.getDeptId());
			/**
			 * 保证添加的用户所属部门是分级管理员管辖的部门
			 */
			if (department != null && department.getNodeId() > 0) {
				SysTree tree = sysTreeService.findById(department.getNodeId());
				if (tree != null && nodeIds.contains(tree.getId())) {
					long[] id = ParamUtil.getLongParameterValues(request, "id");// 获取页面参数
					if (id != null) {
						Set<SysDeptRole> delRoles = new HashSet<SysDeptRole>();
						Set<SysDeptRole> oldRoles = user.getRoles();
						Set<SysDeptRole> newRoles = new HashSet<SysDeptRole>();
						for (int i = 0; i < id.length; i++) {
							logger.debug("id[" + i + "]=" + id[i]);
							SysDeptRole role = sysDeptRoleService
									.findById(id[i]);// 查找角色对象
							if (role != null) {
								newRoles.add(role);// 加入到角色列表
							}
						}

						oldRoles.retainAll(newRoles);// 公共权限
						delRoles.removeAll(newRoles);// 待删除的权限
						newRoles.removeAll(oldRoles);// 待增加的权限
						user.setUpdateBy(RequestUtils.getActorId(request));

						if (sysUserService.updateRole(user, delRoles, newRoles)) {// 授权成功
							messages.add(ViewMessages.GLOBAL_MESSAGE,
									new ViewMessage("user.role_success"));
						} else {// 保存失败
							messages.add(ViewMessages.GLOBAL_MESSAGE,
									new ViewMessage("user.role_failure"));
						}
					}
				}
			}
		}
		MessageUtils.addMessages(request, messages);
		return new ModelAndView("show_msg", modelMap);
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

	@javax.annotation.Resource
	public void setTreeModelService(ITreeModelService treeModelService) {
		this.treeModelService = treeModelService;

	}

	/**
	 * 显示部门下所有人
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=showDeptUsers")
	public ModelAndView showDeptUsers(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		List<SysDepartment> list = new ArrayList<SysDepartment>();
		Set<SysUser> set = new HashSet<SysUser>();
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
		for (Iterator<SysDepartment> iter = list.iterator(); iter.hasNext();) {
			SysDepartment element = (SysDepartment) iter.next();
			this.getRoleUser(set, element.getId(), roleCode);
		}
		request.setAttribute("user", set);

		String x_view = ViewProperties.getString("branch.user.showDeptUsers");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/branch/user/duty_select", modelMap);
	}

	/**
	 * 显示所有列表
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=showList")
	public ModelAndView showList(HttpServletRequest request, ModelMap modelMap) {
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
		List<SysDepartment> list = new ArrayList<SysDepartment>();
		sysDepartmentService.findNestingDepartment(list, dept);
		request.setAttribute("nav", list);

		String x_view = ViewProperties.getString("branch.user.showList");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/branch/user/user_list", modelMap);
	}

	/**
	 * 显示角色
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=showRole")
	public ModelAndView showRole(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		long id = ParamUtil.getIntParameter(request, "user_id", 0);
		SysUser bean = sysUserService.findById(id);
		SysUser user = sysUserService.findByAccountWithAll(bean.getAccount());
		request.setAttribute("user", user);
		request.setAttribute("list",
				sysDeptRoleService.getRoleList(user.getDepartment().getId()));

		String x_view = ViewProperties.getString("branch.user.showRole");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		// 显示列表页面
		return new ModelAndView("/modules/branch/user/user_role", modelMap);
	}

	/**
	 * 显示角色用户列表
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=showRoleUser")
	public ModelAndView showRoleUser(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		int deptId = ParamUtil.getIntParameter(request, "deptId", 0);
		long roleId = ParamUtil.getLongParameter(request, "roleId", 0);

		// 部门信息
		SysDepartment dept = sysDepartmentService.findById(deptId);
		List<SysDepartment> list = new ArrayList<SysDepartment>();
		sysDepartmentService.findNestingDepartment(list, dept);
		request.setAttribute("nav", list);

		// 角色
		SysRole role = sysRoleService.findById(roleId);
		request.setAttribute("role", role.getName());

		Set<?> users = sysDeptRoleService.findRoleUser(deptId, role.getCode());
		request.setAttribute("list", users);

		String x_view = ViewProperties.getString("branch.user.showRoleUser");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/branch/user/deptRole_user", modelMap);
	}

	/**
	 * 显示角色用户列表
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=showSelUser")
	public ModelAndView showSelUser(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		int deptId = ParamUtil.getIntParameter(request, "deptId2", 0);
		if (deptId != 0) {
			request.setAttribute("list", sysUserService.getSysUserList(deptId));
		}

		String x_view = ViewProperties.getString("branch.user.showSelUser");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/branch/user/dept_user_sel", modelMap);
	}

	/**
	 * 显示用户信息
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=showUser")
	public ModelAndView showUser(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		long userId = ParamUtil.getLongParameter(request, "userId", 0);
		SysUser user = sysUserService.findById(userId);
		user.setPassword(null);
		request.setAttribute("user", user);

		String x_view = ViewProperties.getString("branch.user.showUser");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/branch/user/user_info", modelMap);
	}
}