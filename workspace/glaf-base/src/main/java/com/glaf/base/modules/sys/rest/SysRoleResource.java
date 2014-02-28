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
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.query.SysRoleQuery;
import com.glaf.base.modules.sys.service.SysRoleService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.utils.ParamUtil;
import com.glaf.core.base.BaseTree;
import com.glaf.core.base.TreeModel;
import com.glaf.core.res.MessageUtils;
import com.glaf.core.res.ViewMessage;
import com.glaf.core.res.ViewMessages;
import com.glaf.core.tree.helper.TreeHelper;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;

@Controller("/rs/sys/role")
@Path("/rs/sys/role")
// /rs/sys/rolebatchDelete?rowIds=181819
public class SysRoleResource {
	private static final Log logger = LogFactory.getLog(SysRoleResource.class);

	protected SysRoleService sysRoleService;

	protected SysTreeService sysTreeService;

	protected SysUserService sysUserService;

	/**
	 * 批量删除信息
	 * 
	 * @param request
	 * @param uriInfo
	 * @return
	 */
	@GET
	@POST
	@Path("batchDelete")
	@Produces(MediaType.TEXT_PLAIN)
	public ModelAndView batchDelete(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		RequestUtils.setRequestParameterToAttribute(request);
		boolean ret = false;
		String objectIds = request.getParameter("rowIds");
		List<Long> list = StringTools.splitToLong(objectIds);
		if (list != null && !list.isEmpty()) {
			int index = 0;
			long[] ids = new long[list.size()];
			for (Long x : list) {
				ids[index++] = x;
			}
			try {
				if (ids != null && ids.length > 0) {
					ret = sysRoleService.deleteAll(ids);
					ret = true;
				}
			} catch (Exception ex) {
				logger.error(ex);
				ret = false;
			}
		}

		ViewMessages messages = new ViewMessages();
		if (ret) {// 成功
			request.setAttribute("statusCode", 200);
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"role.delete_success"));
		} else {// 失败
			request.setAttribute("statusCode", 500);
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"role.delete_failure"));
		}
		MessageUtils.addMessages(request, messages);

		// 显示列表页面
		return new ModelAndView("show_json_msg");
	}

	@GET
	@POST
	@Path("detail")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] detail(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) throws IOException {
		long id = RequestUtils.getLong(request, "id");
		if (id > 0) {
			SysRole role = sysRoleService.findById(id);
			if (role != null) {
				return role.toJsonObject().toJSONString().getBytes("UTF-8");
			}
		}
		return null;
	}

	@GET
	@POST
	@Path("json")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] json(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysRoleQuery query = new SysRoleQuery();
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
			limit = Paging.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = sysRoleService.getSysRoleCountByQueryCriteria(query);
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

			List<SysRole> list = sysRoleService.getSysRolesByQueryCriteria(
					start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				result.put("rows", rowsJSON);
				for (SysRole sysRole : list) {
					JSONObject rowJSON = sysRole.toJsonObject();
					rowJSON.put("id", sysRole.getId());
					rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
				}
			}
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("roleUsersJson")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] roleUsersJson(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) throws IOException {
		JSONObject result = new JSONObject();
		String roleCode = request.getParameter("roleCode");
		List<SysUser> roleUsers = sysUserService
				.getSysUsersByRoleCode(roleCode);
		Collection<String> userIds = new HashSet<String>();
		if (roleUsers != null && !roleUsers.isEmpty()) {
			for (SysUser u : roleUsers) {
				userIds.add(u.getAccount());
			}
		}
		List<SysUser> users = sysUserService.getSysUserWithDeptList();
		SysTree root = sysTreeService.getSysTreeByCode(SysConstants.TREE_DEPT);
		if (root != null && users != null) {
			logger.debug(root.toJsonObject().toJSONString());
			logger.debug("users size:" + users.size());
			List<TreeModel> treeModels = new java.util.concurrent.CopyOnWriteArrayList<TreeModel>();
			// treeModels.add(root);
			List<SysTree> trees = sysTreeService.getAllSysTreeListForDept(
					(int) root.getId(), 0);
			if (trees != null && !trees.isEmpty()) {
				logger.debug("dept tree size:" + trees.size());
				Map<Long, SysTree> treeMap = new java.util.concurrent.ConcurrentHashMap<Long, SysTree>();
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
								treeModel.setIcon(request.getContextPath()
										+ "/icons/icons/user.gif");
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
			// logger.debug(jsonArray.toJSONString());
			return jsonArray.toJSONString().getBytes("UTF-8");
		}
		return result.toString().getBytes("UTF-8");
	}

	/**
	 * 提交增加信息
	 * 
	 * @param request
	 * @param uriInfo
	 * @return
	 */
	@POST
	@Path("saveAdd")
	@Produces(MediaType.TEXT_PLAIN)
	public ModelAndView saveAdd(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		RequestUtils.setRequestParameterToAttribute(request);
		boolean ret = false;
		try {
			if (sysRoleService.findByCode(ParamUtil.getParameter(request,
					"code")) == null) {
				SysRole bean = new SysRole();
				bean.setName(ParamUtil.getParameter(request, "name"));
				bean.setDesc(ParamUtil.getParameter(request, "desc"));
				bean.setCode(ParamUtil.getParameter(request, "code"));
				bean.setCreateBy(RequestUtils.getActorId(request));
				bean.setUpdateBy(RequestUtils.getActorId(request));
				ret = sysRoleService.create(bean);
			}
		} catch (Exception ex) {
			logger.error(ex);
			ret = false;
		}

		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			request.setAttribute("statusCode", 200);
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"role.add_success"));
		} else {// 保存失败
			request.setAttribute("statusCode", 500);
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"role.add_failure"));
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
	@POST
	@Path("saveModify")
	@Produces(MediaType.TEXT_PLAIN)
	public ModelAndView saveModify(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		RequestUtils.setRequestParameterToAttribute(request);
		boolean ret = false;
		try {
			long id = ParamUtil.getIntParameter(request, "id", 0);
			SysRole bean = sysRoleService.findById(id);
			if (bean != null) {
				bean.setName(ParamUtil.getParameter(request, "name"));
				bean.setDesc(ParamUtil.getParameter(request, "desc"));
				bean.setCode(ParamUtil.getParameter(request, "code"));
				bean.setUpdateBy(RequestUtils.getActorId(request));
			}
			ret = sysRoleService.update(bean);
		} catch (Exception ex) {
			logger.error(ex);
			ret = false;
		}
		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			request.setAttribute("statusCode", 200);
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"role.modify_success"));
		} else {// 保存失败
			request.setAttribute("statusCode", 500);
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"role.modify_failure"));
		}
		MessageUtils.addMessages(request, messages);
		// 显示列表页面
		return new ModelAndView("show_json_msg");
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

	@POST
	@Path("sort")
	@ResponseBody
	public void sort(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		long id = ParamUtil.getIntParameter(request, "id", 0);
		int operate = ParamUtil.getIntParameter(request, "operate", 0);
		logger.info("id:" + id + ",operate:" + operate);
		sysRoleService.sort(sysRoleService.findById(id), operate);
	}
}