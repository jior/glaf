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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.glaf.core.base.BaseTree;
import com.glaf.core.base.TreeModel;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.tree.helper.TreeHelper;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.StringTools;

import com.glaf.base.modules.sys.SysConstants;

import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.model.SysUser;

import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysRoleService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.service.SysUserService;

@Controller("/base/identityChoose")
@RequestMapping("/base/identityChoose.do")
public class IdentityChooseController {
	private static final Log logger = LogFactory
			.getLog(IdentityChooseController.class);

	protected SysDepartmentService sysDepartmentService;

	protected SysTreeService sysTreeService;

	protected SysUserService sysUserService;

	protected SysRoleService sysRoleService;

	/**
	 * 显示选择部门页面
	 * 
	 * @param modelMap
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=chooseDepts")
	public ModelAndView chooseDepts(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String x_view = ViewProperties.getString("identityChoose.chooseDepts");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/base/choose/choose_depts", modelMap);
	}

	/**
	 * 显示选择角色页面
	 * 
	 * @param modelMap
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=chooseRoles")
	public ModelAndView chooseRoles(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		
		List<SysRole>  roles = sysRoleService.getSysRoleList();
		String selecteds = request.getParameter("selecteds");
		request.setAttribute("roles", roles);
		request.setAttribute("selecteds", StringTools.split(selecteds));

		String x_view = ViewProperties.getString("identityChoose.chooseRoles");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/base/choose/choose_roles", modelMap);
	}

	/**
	 * 显示选择部门页面
	 * 
	 * @param modelMap
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=chooseTrees")
	public ModelAndView chooseTrees(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String x_view = ViewProperties.getString("identityChoose.chooseTrees");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/base/choose/choose_trees", modelMap);
	}

	/**
	 * 显示选择用户页面
	 * 
	 * @param modelMap
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=chooseUsers")
	public ModelAndView chooseUsers(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String x_view = ViewProperties.getString("identityChoose.chooseUsers");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/base/choose/choose_users", modelMap);
	}

	@RequestMapping(params = "method=deptJson")
	@ResponseBody
	public byte[] deptJson(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		JSONObject result = new JSONObject();
		String selecteds = request.getParameter("selecteds");
		List<String> deptIds = StringTools.split(selecteds);
		SysTree root = sysTreeService.getSysTreeByCode(SysConstants.TREE_DEPT);
		if (root != null) {
			logger.debug(root.toJsonObject().toJSONString());

			List<TreeModel> treeModels = new ArrayList<TreeModel>();
			// treeModels.add(root);
			List<SysTree> trees = sysTreeService.getAllSysTreeListForDept(
					(int) root.getId(), 0);
			if (trees != null && !trees.isEmpty()) {
				logger.debug("dept tree size:" + trees.size());
				Map<Long, SysTree> treeMap = new HashMap<Long, SysTree>();
				for (SysTree tree : trees) {
					SysDepartment dept = tree.getDepartment();
					treeMap.put(dept.getId(), tree);
				}

				for (SysTree tree : trees) {
					if (deptIds.contains(String.valueOf(tree.getId()))) {
						tree.setChecked(true);
					}
					treeModels.add(tree);
				}
			}
			logger.debug("treeModels:" + treeModels.size());
			TreeHelper treeHelper = new TreeHelper();
			JSONArray jsonArray = treeHelper.getTreeJSONArray(treeModels);
			return jsonArray.toJSONString().getBytes("UTF-8");
		}
		return result.toString().getBytes("UTF-8");
	}

	@RequestMapping(params = "method=json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		JSONObject result = new JSONObject();
		String selecteds = request.getParameter("selecteds");
		List<String> userIds = StringTools.split(selecteds);
		List<SysUser> users = sysUserService.getSysUserWithDeptList();
		SysTree root = sysTreeService.getSysTreeByCode(SysConstants.TREE_DEPT);
		if (root != null && users != null) {
			logger.debug(root.toJsonObject().toJSONString());
			logger.debug("users size:" + users.size());
			List<TreeModel> treeModels = new ArrayList<TreeModel>();
			// treeModels.add(root);
			List<SysTree> trees = sysTreeService.getAllSysTreeListForDept(
					(int) root.getId(), 0);
			if (trees != null && !trees.isEmpty()) {
				logger.debug("dept tree size:" + trees.size());
				Map<Long, SysTree> treeMap = new HashMap<Long, SysTree>();
				for (SysTree tree : trees) {
					SysDepartment dept = tree.getDepartment();
					treeMap.put(dept.getId(), tree);
				}
				long ts = System.currentTimeMillis();
				for (SysTree tree : trees) {
					treeModels.add(tree);
					SysDepartment dept = tree.getDepartment();
					if (dept != null && dept.getId() > 0) {
						for (SysUser user : users) {
							SysTree t = treeMap.get(Long.valueOf(user
									.getDeptId()));
							if (dept.getId() == user.getDeptId() && t != null) {
								TreeModel treeModel = new BaseTree();
								treeModel.setParentId(t.getId());
								treeModel.setId(ts++);
								treeModel.setCode(user.getAccount());
								treeModel.setName(user.getAccount() + " "
										+ user.getName());
								treeModel.setIconCls("icon-user");
								if (userIds != null
										&& userIds.contains(user.getAccount())) {
									treeModel.setChecked(true);
								}
								treeModels.add(treeModel);
							}
						}
					}
				}
			}
			logger.debug("treeModels:" + treeModels.size());
			TreeHelper treeHelper = new TreeHelper();
			JSONArray jsonArray = treeHelper.getTreeJSONArray(treeModels);
			return jsonArray.toJSONString().getBytes("UTF-8");
		}
		return result.toString().getBytes("UTF-8");
	}

	@javax.annotation.Resource
	public void setSysDepartmentService(
			SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
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
	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
	}

	@RequestMapping(params = "method=treeJson")
	@ResponseBody
	public byte[] treeJson(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		JSONObject result = new JSONObject();
		Long parentId = RequestUtils.getLong(request, "parentId");
		String selecteds = request.getParameter("selecteds");
		List<String> deptIds = StringTools.split(selecteds);
		SysTree root = sysTreeService.findById(parentId);
		if (root != null) {
			logger.debug(root.toJsonObject().toJSONString());

			List<TreeModel> treeModels = new ArrayList<TreeModel>();
			// treeModels.add(root);
			List<SysTree> trees = sysTreeService.getAllSysTreeListForDept(
					(int) root.getId(), 0);
			if (trees != null && !trees.isEmpty()) {
				logger.debug("dept tree size:" + trees.size());
				Map<Long, SysTree> treeMap = new HashMap<Long, SysTree>();
				for (SysTree tree : trees) {
					SysDepartment dept = tree.getDepartment();
					treeMap.put(dept.getId(), tree);
				}

				for (SysTree tree : trees) {
					if (deptIds.contains(String.valueOf(tree.getId()))) {
						tree.setChecked(true);
					}
					treeModels.add(tree);
				}
			}
			logger.debug("treeModels:" + treeModels.size());
			TreeHelper treeHelper = new TreeHelper();
			JSONArray jsonArray = treeHelper.getTreeJSONArray(treeModels);
			return jsonArray.toJSONString().getBytes("UTF-8");
		}
		return result.toString().getBytes("UTF-8");
	}

	@RequestMapping(params = "method=userJson")
	@ResponseBody
	public byte[] userJson(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		JSONObject result = new JSONObject();
		String selecteds = request.getParameter("selecteds");
		List<String> userIds = StringTools.split(selecteds);
		List<SysUser> users = sysUserService.getSysUserWithDeptList();
		SysTree root = sysTreeService.getSysTreeByCode(SysConstants.TREE_DEPT);
		if (root != null && users != null) {
			logger.debug(root.toJsonObject().toJSONString());
			logger.debug("users size:" + users.size());
			List<TreeModel> treeModels = new ArrayList<TreeModel>();
			// treeModels.add(root);
			List<SysTree> trees = sysTreeService.getAllSysTreeListForDept(
					(int) root.getId(), 0);
			if (trees != null && !trees.isEmpty()) {
				logger.debug("dept tree size:" + trees.size());
				Map<Long, SysTree> treeMap = new HashMap<Long, SysTree>();
				for (SysTree tree : trees) {
					SysDepartment dept = tree.getDepartment();
					treeMap.put(dept.getId(), tree);
				}
				long ts = System.currentTimeMillis();
				for (SysTree tree : trees) {
					treeModels.add(tree);
					SysDepartment dept = tree.getDepartment();
					if (dept != null && dept.getId() > 0) {
						for (SysUser user : users) {
							SysTree t = treeMap.get(Long.valueOf(user
									.getDeptId()));
							if (dept.getId() == user.getDeptId() && t != null) {
								TreeModel treeModel = new BaseTree();
								Map<String, Object> dataMap = new HashMap<String, Object>();
								dataMap.put("actorId", user.getAccount());
								treeModel.setDataMap(dataMap);
								treeModel.setParentId(t.getId());
								treeModel.setId(ts++);
								treeModel.setCode(user.getAccount());
								treeModel.setName(user.getAccount() + " "
										+ user.getName());
								treeModel.setIconCls("icon-user");
								treeModel.setIcon(request.getContextPath()+"/icons/icons/user.gif");
								if (userIds != null
										&& userIds.contains(user.getAccount())) {
									treeModel.setChecked(true);
								}
								treeModels.add(treeModel);
							}
						}
					}
				}
			}
			logger.debug("treeModels:" + treeModels.size());
			TreeHelper treeHelper = new TreeHelper();
			JSONArray jsonArray = treeHelper.getTreeJSONArray(treeModels);
			logger.debug(jsonArray.toJSONString());
			return jsonArray.toJSONString().getBytes("UTF-8");
		}
		return result.toString().getBytes("UTF-8");
	}

}