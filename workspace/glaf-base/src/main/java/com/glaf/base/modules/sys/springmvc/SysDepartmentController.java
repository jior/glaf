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
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.glaf.base.modules.Constants;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.Dictory;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.query.SysDepartmentQuery;
import com.glaf.base.modules.sys.service.DictoryService;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysDeptRoleService;
import com.glaf.base.modules.sys.service.SysRoleService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.util.SysRoleJsonFactory;
import com.glaf.base.utils.ParamUtil;
import com.glaf.core.base.TreeModel;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.res.MessageUtils;
import com.glaf.core.res.ViewMessage;
import com.glaf.core.res.ViewMessages;
import com.glaf.core.tree.helper.JacksonTreeHelper;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;

@Controller("/sys/department")
@RequestMapping("/sys/department.do")
public class SysDepartmentController {
	private static final Log logger = LogFactory
			.getLog(SysDepartmentController.class);

	protected DictoryService dictoryService;

	protected SysDepartmentService sysDepartmentService;

	protected SysDeptRoleService sysDeptRoleService;

	protected SysRoleService sysRoleService;

	protected SysTreeService sysTreeService;

	/**
	 * 批量删除信息
	 * 
	 * 
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=batchDelete")
	public ModelAndView batchDelete(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		boolean ret = true;
		long[] id = ParamUtil.getLongParameterValues(request, "id");
		ret = sysDepartmentService.deleteAll(id);

		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"department.delete_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"department.delete_failure"));
		}
		MessageUtils.addMessages(request, messages);

		// 显示列表页面
		return new ModelAndView("show_msg2", modelMap);
	}

	@ResponseBody
	@RequestMapping(params = "method=deptRolesJson")
	public byte[] deptRolesJson(HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug(params);

		long nodeId = RequestUtils.getLong(request, "nodeId", -1);
		SysDepartmentQuery query = new SysDepartmentQuery();
		query.nodeId(nodeId);
		List<SysRole> roles = sysRoleService.getSysRolesOfDepts(query);

		for (SysRole role : roles) {
			role.setNodeId(nodeId);
		}

		JSONArray array = SysRoleJsonFactory.listToArray(roles);

		logger.debug(array.toString());
		try {
			return array.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return array.toString().getBytes();
		}
	}

	/**
	 * 显示编辑页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		long id = ParamUtil.getIntParameter(request, "id", 0);
		SysDepartment bean = sysDepartmentService.getSysDepartment(id);
		request.setAttribute("bean", bean);

		SysTree parent = sysTreeService.getSysTreeByCode(Constants.TREE_DEPT);
		List<SysTree> list = new java.util.ArrayList<SysTree>();
		parent.setDeep(0);
		list.add(parent);
		sysTreeService.getSysTree(list, (int) parent.getId(), 1);
		request.setAttribute("parent", list);

		List<Dictory> dictories = dictoryService
				.getDictoryList(SysConstants.DEPT_LEVEL);
		modelMap.put("dictories", dictories);

		String x_view = ViewProperties.getString("department.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		// 显示列表页面
		return new ModelAndView("/modules/sys/dept/edit", modelMap);
	}

	/**
	 * 显示下级部门节点
	 * 
	 * 
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=getSubDept")
	public ModelAndView getSubDept(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		int id = ParamUtil.getIntParameter(request, "id", 0);
		int status = ParamUtil.getIntParameter(request, "status", -1);
		List<SysTree> list = sysTreeService.getSysTreeListForDept(id, status);
		request.setAttribute("list", list);

		String x_view = ViewProperties.getString("department.getSubDept");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/dept/subdept_list", modelMap);
	}

	/**
	 * 显示下级所有部门节点,包括无效的部门
	 * 
	 * 
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=getSubDeptAll")
	public ModelAndView getSubDeptAll(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		int id = ParamUtil.getIntParameter(request, "id", 0);
		List<SysTree> list = sysTreeService.getSysTreeList(id);
		request.setAttribute("list", list);

		String x_view = ViewProperties.getString("department.getSubDeptAll");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/dept/subdeptall_list", modelMap);
	}

	@RequestMapping(params = "method=json")
	@ResponseBody
	public byte[] json(HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysDepartmentQuery query = new SysDepartmentQuery();
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
		int total = sysDepartmentService
				.getSysDepartmentCountByQueryCriteria(query);
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

			List<SysDepartment> list = sysDepartmentService
					.getSysDepartmentsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SysDepartment sysDepartment : list) {
					JSONObject rowJSON = sysDepartment.toJsonObject();
					rowJSON.put("id", sysDepartment.getId());
					rowJSON.put("sysDepartmentId", sysDepartment.getId());
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

		String x_view = ViewProperties.getString("department.list");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/modules/sys/dept/list", modelMap);
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
		RequestUtils.setRequestParameterToAttribute(request);

		List<Dictory> dictories = dictoryService
				.getDictoryList(SysConstants.DEPT_LEVEL);
		modelMap.put("dictories", dictories);

		String x_view = ViewProperties.getString("department.prepareAdd");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/dept/dept_add", modelMap);
	}

	/**
	 * 显示修改页面
	 * 
	 * 
	 * @param form
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=prepareModify")
	public ModelAndView prepareModify(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		long id = ParamUtil.getIntParameter(request, "id", 0);
		SysDepartment bean = sysDepartmentService.getSysDepartment(id);
		request.setAttribute("bean", bean);

		SysTree parent = sysTreeService.getSysTreeByCode(Constants.TREE_DEPT);
		List<SysTree> list = new java.util.concurrent.CopyOnWriteArrayList<SysTree>();
		parent.setDeep(0);
		list.add(parent);
		sysTreeService.getSysTree(list, (int) parent.getId(), 1);
		request.setAttribute("parent", list);

		List<Dictory> dictories = dictoryService
				.getDictoryList(SysConstants.DEPT_LEVEL);
		modelMap.put("dictories", dictories);

		String x_view = ViewProperties.getString("department.prepareModify");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		// 显示列表页面
		return new ModelAndView("/modules/sys/dept/dept_modify", modelMap);
	}

	@ResponseBody
	@RequestMapping(params = "method=save")
	public byte[] save(HttpServletRequest request, ModelMap modelMap) {
		long id = ParamUtil.getIntParameter(request, "id", 0);
		SysDepartment bean = sysDepartmentService.getSysDepartment(id);
		boolean ret = false;
		if (bean != null) {
			bean.setUpdateBy(RequestUtils.getActorId(request));
			bean.setName(ParamUtil.getParameter(request, "name"));
			bean.setDesc(ParamUtil.getParameter(request, "desc"));
			bean.setCode(ParamUtil.getParameter(request, "code"));
			bean.setCode2(ParamUtil.getParameter(request, "code2"));
			bean.setNo(ParamUtil.getParameter(request, "no"));
			bean.setStatus(ParamUtil.getIntParameter(request, "status", 0));
			bean.setLevel(RequestUtils.getInt(request, "level"));

			long parentId = ParamUtil.getLongParameter(request, "parent", 0);
			if (parentId == 0) {
				SysTree root = sysTreeService
						.getSysTreeByCode(Constants.TREE_DEPT);
				parentId = root.getId();
			}
			SysTree node = bean.getNode();
			node.setUpdateBy(RequestUtils.getActorId(request));
			node.setName(bean.getName());
			node.setParentId(parentId);
			bean.setNode(node);
			try {
				ret = sysDepartmentService.update(bean);
			} catch (Exception ex) {
				ret = false;
				logger.error(ex);
			}
		} else {
			bean = new SysDepartment();
			bean.setName(ParamUtil.getParameter(request, "name"));
			bean.setDesc(ParamUtil.getParameter(request, "desc"));
			bean.setCode(ParamUtil.getParameter(request, "code"));
			bean.setCode2(ParamUtil.getParameter(request, "code2"));
			bean.setNo(ParamUtil.getParameter(request, "no"));
			bean.setLevel(RequestUtils.getInt(request, "level"));
			bean.setCreateTime(new Date());

			long parentId = ParamUtil.getLongParameter(request, "parent", 0);
			if (parentId == 0) {
				SysTree root = sysTreeService
						.getSysTreeByCode(Constants.TREE_DEPT);
				parentId = root.getId();
			}
			SysTree node = new SysTree();
			node.setCreateBy(RequestUtils.getActorId(request));
			node.setName(bean.getName());
			node.setDesc(bean.getName());
			node.setCode(bean.getCode());
			node.setParentId(parentId);
			bean.setNode(node);
			bean.setCreateBy(RequestUtils.getActorId(request));
			try {
				ret = sysDepartmentService.create(bean);
			} catch (Exception ex) {
				ret = false;
				logger.error(ex);
			}
		}

		if (ret) {
			return ResponseUtils.responseResult(true);
		}

		return ResponseUtils.responseResult(false);
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
		// 增加部门时，同时要增加对应节点
		SysDepartment bean = new SysDepartment();
		bean.setName(ParamUtil.getParameter(request, "name"));
		bean.setDesc(ParamUtil.getParameter(request, "desc"));
		bean.setCode(ParamUtil.getParameter(request, "code"));
		bean.setCode2(ParamUtil.getParameter(request, "code2"));
		bean.setNo(ParamUtil.getParameter(request, "no"));
		bean.setLevel(RequestUtils.getInt(request, "level"));
		bean.setCreateTime(new Date());

		long parentId = ParamUtil.getLongParameter(request, "parent", 0);
		if (parentId == 0) {
			SysTree root = sysTreeService.getSysTreeByCode(Constants.TREE_DEPT);
			parentId = root.getId();
		}

		SysTree node = new SysTree();
		node.setCreateBy(RequestUtils.getActorId(request));
		node.setName(bean.getName());
		node.setDesc(bean.getName());
		node.setCode(bean.getCode());
		node.setParentId(parentId);
		bean.setNode(node);
		bean.setCreateBy(RequestUtils.getActorId(request));
		boolean ret = sysDepartmentService.create(bean);

		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"department.add_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"department.add_failure"));
		}
		MessageUtils.addMessages(request, messages);

		return new ModelAndView("show_msg", modelMap);
	}

	/**
	 * 提交修改信息
	 * 
	 * 
	 * @param form
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=saveModify")
	public ModelAndView saveModify(HttpServletRequest request, ModelMap modelMap) {
		long id = ParamUtil.getIntParameter(request, "id", 0);
		SysDepartment bean = sysDepartmentService.getSysDepartment(id);
		boolean ret = false;
		if (bean != null) {
			bean.setUpdateBy(RequestUtils.getActorId(request));
			bean.setName(ParamUtil.getParameter(request, "name"));
			bean.setDesc(ParamUtil.getParameter(request, "desc"));
			bean.setCode(ParamUtil.getParameter(request, "code"));
			bean.setCode2(ParamUtil.getParameter(request, "code2"));
			bean.setNo(ParamUtil.getParameter(request, "no"));
			bean.setStatus(ParamUtil.getIntParameter(request, "status", 0));
			bean.setLevel(RequestUtils.getInt(request, "level"));

			long parentId = ParamUtil.getLongParameter(request, "parent", 0);
			if (parentId == 0) {
				SysTree root = sysTreeService
						.getSysTreeByCode(Constants.TREE_DEPT);
				parentId = root.getId();
			}

			SysTree node = bean.getNode();
			node.setUpdateBy(RequestUtils.getActorId(request));
			node.setName(bean.getName());
			node.setParentId(parentId);
			bean.setNode(node);
			try {
				ret = sysDepartmentService.update(bean);
			} catch (Exception ex) {
				ret = false;
				logger.error(ex);
			}
		}
		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"department.modify_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"department.modify_failure"));
		}
		MessageUtils.addMessages(request, messages);

		// 显示列表页面
		return new ModelAndView("show_msg", modelMap);
	}

	@javax.annotation.Resource
	public void setDictoryService(DictoryService dictoryService) {
		this.dictoryService = dictoryService;
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

	/**
	 * 显示所有部门的菜单选择页面
	 * 
	 * 
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=showDeptAllSelect")
	public ModelAndView showDeptAllSelect(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		ModelAndView forward = new ModelAndView(
				"/modules/sys/dept/dept_select_all", modelMap);
		int parent = ParamUtil.getIntParameter(request, "parent",
				SysConstants.TREE_ROOT);
		request.setAttribute("parent", sysTreeService.findById(parent));
		String url = ParamUtil.getParameter(request, "url");
		request.setAttribute("url", url);

		String x_view = ViewProperties
				.getString("department.showDeptAllSelect");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		// 显示列表页面
		return forward;
	}

	/**
	 * 显示所有列表
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=showDeptRoleUsers")
	public ModelAndView showDeptRoleUsers(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		long nodeId = ParamUtil.getIntParameter(request, "nodeId", 0);
		String roleCode = request.getParameter("roleCode");

		SysDepartment dept = sysDepartmentService
				.getSysDepartmentByNodeId(nodeId);
		if (dept != null) {
			Set<SysUser> users = sysDeptRoleService.findRoleUser(dept.getId(),
					roleCode);
			request.setAttribute("department", dept);
			request.setAttribute("users", users);
		}

		String x_view = ViewProperties
				.getString("department.showDeptRoleUsers");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/dept/dept_role_user", modelMap);
	}

	/**
	 * 显示菜单选择页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=showDeptSelect")
	public ModelAndView showDeptSelect(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		ModelAndView forward = new ModelAndView(
				"/modules/sys/dept/dept_select", modelMap);
		int type = ParamUtil.getIntParameter(request, "type", 1);
		if (type == 2) {
			forward = new ModelAndView("/modules/sys/dept/dept_select2",
					modelMap);
		} else if (type == 3) {
			forward = new ModelAndView("/modules/sys/dept/dept_select3",
					modelMap);
		}

		String x_view = ViewProperties
				.getString("department.showDeptSelect.type");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		int parent = ParamUtil.getIntParameter(request, "parent",
				SysConstants.TREE_ROOT);
		request.setAttribute("parent", sysTreeService.findById(parent));
		String url = ParamUtil.getParameter(request, "url");
		request.setAttribute("url", url);

		// 显示列表页面
		return forward;
	}

	/**
	 * 显示框架页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=showFrame")
	public ModelAndView showFrame(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		SysTree bean = sysTreeService.getSysTreeByCode(Constants.TREE_DEPT);
		request.setAttribute("parent", bean.getId() + "");

		String x_view = ViewProperties.getString("department.showFrame");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		// 显示列表页面
		return new ModelAndView("/modules/sys/dept/dept_frame", modelMap);
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
		RequestUtils.setRequestParameterToAttribute(request);
		int parent = ParamUtil.getIntParameter(request, "parent", 0);
		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = ParamUtil.getIntParameter(request, "page_size",
				Constants.PAGE_SIZE);
		PageResult pager = sysDepartmentService.getSysDepartmentList(parent,
				pageNo, pageSize);
		request.setAttribute("pager", pager);

		SysTree treeNode = sysTreeService.findById(parent);
		SysDepartment dept = treeNode.getDepartment();
		List<SysDepartment> list = new java.util.concurrent.CopyOnWriteArrayList<SysDepartment>();
		sysDepartmentService.findNestingDepartment(list, dept);
		request.setAttribute("nav", list);

		String x_view = ViewProperties.getString("department.showList");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/dept/dept_list", modelMap);
	}

	@ResponseBody
	@RequestMapping(params = "method=sort")
	public void sort(@RequestParam(value = "parent") int parent,
			@RequestParam(value = "id") int id,
			@RequestParam(value = "operate") int operate) {
		logger.info("parent:" + parent + "; id:" + id + "; operate:" + operate);
		SysDepartment bean = sysDepartmentService.findById(id);
		sysDepartmentService.sort(parent, bean, operate);
	}

	@RequestMapping(params = "method=treegrid")
	public ModelAndView treegrid(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String x_view = ViewProperties.getString("department.treegrid");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/modules/sys/dept/treegrid", modelMap);
	}

	@ResponseBody
	@RequestMapping(params = "method=treegridJson")
	public byte[] treegridJson(HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug(params);

		long parentId = RequestUtils.getLong(request, "parentId", -1);

		List<SysTree> trees = sysTreeService.getSysTreeListForDept(parentId, 0);
		List<SysDepartment> depts = sysDepartmentService
				.getSysDepartmentList(parentId);

		ArrayNode responseJSON = new ObjectMapper().createArrayNode();

		List<TreeModel> treeModels = new java.util.concurrent.CopyOnWriteArrayList<TreeModel>();

		if (trees != null && !trees.isEmpty() && depts != null
				&& !depts.isEmpty()) {
			Map<Long, SysDepartment> deptMap = new java.util.concurrent.ConcurrentHashMap<Long, SysDepartment>();
			for (SysDepartment dept : depts) {
				deptMap.put(dept.getNodeId(), dept);
			}
			for (SysTree tree : trees) {
				SysDepartment dept = deptMap.get(tree.getId());
				if (dept != null) {
					Map<String, Object> dataMap = tree.getDataMap();
					if (dataMap == null) {
						dataMap = new java.util.concurrent.ConcurrentHashMap<String, Object>();
					}
					dataMap.put("deptId", dept.getId());
					dataMap.put("deptName", dept.getName());
					dataMap.put("deptCode", dept.getCode());
					dataMap.put("deptCode2", dept.getCode2());
					dataMap.put("deptNo", dept.getNo());
					dataMap.put("deptDesc", dept.getDesc());
					dataMap.put("deptFincode", dept.getFincode());
					dataMap.put("deptLevel", dept.getLevel());
					tree.setDataMap(dataMap);
				}
				treeModels.add(tree);
			}
		}

		JacksonTreeHelper treeHelper = new JacksonTreeHelper();
		responseJSON = treeHelper.getTreeArrayNode(treeModels);
		logger.debug(responseJSON.toString());
		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}
}