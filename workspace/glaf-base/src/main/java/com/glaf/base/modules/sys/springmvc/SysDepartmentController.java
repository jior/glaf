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
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.glaf.base.modules.Constants;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.query.SysDepartmentQuery;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.utils.ParamUtil;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.res.MessageUtils;
import com.glaf.core.res.ViewMessage;
import com.glaf.core.res.ViewMessages;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.Tools;

@Controller("/sys/department")
@RequestMapping("/sys/department.do")
public class SysDepartmentController {
	private static final Log logger = LogFactory
			.getLog(SysDepartmentController.class);

	@javax.annotation.Resource
	private SysDepartmentService sysDepartmentService;

	@javax.annotation.Resource
	private SysTreeService sysTreeService;

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

	/**
	 * 显示下级部门节点
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=getSubDept")
	public ModelAndView getSubDept(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
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
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=getSubDeptAll")
	public ModelAndView getSubDeptAll(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
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
			limit = PageResult.DEFAULT_PAGE_SIZE;
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

					rowsJSON.add(rowJSON);
				}

			}
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
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=prepareAdd")
	public ModelAndView prepareAdd(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtils.setRequestParameterToAttribute(request);

		String x_view = ViewProperties.getString("department.prepareAdd");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/dept/dept_add", modelMap);
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
		RequestUtils.setRequestParameterToAttribute(request);
		long id = ParamUtil.getIntParameter(request, "id", 0);
		SysDepartment bean = sysDepartmentService.findById(id);
		request.setAttribute("bean", bean);

		SysTree parent = sysTreeService.getSysTreeByCode(Constants.TREE_DEPT);
		List<SysTree> list = new ArrayList<SysTree>();
		parent.setDeep(0);
		list.add(parent);
		sysTreeService.getSysTree(list, (int) parent.getId(), 1);
		request.setAttribute("parent", list);

		String x_view = ViewProperties.getString("department.prepareModify");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		// 显示列表页面
		return new ModelAndView("/modules/sys/dept/dept_modify", modelMap);
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
		RequestUtils.setRequestParameterToAttribute(request);
		// 增加部门时，同时要增加对应节点
		SysDepartment bean = new SysDepartment();
		bean.setName(ParamUtil.getParameter(request, "name"));
		bean.setDesc(ParamUtil.getParameter(request, "desc"));
		bean.setCode(ParamUtil.getParameter(request, "code"));
		bean.setCode2(ParamUtil.getParameter(request, "code2"));
		bean.setNo(ParamUtil.getParameter(request, "no"));
		bean.setCreateTime(new Date());

		SysTree node = new SysTree();
		node.setCreateBy(RequestUtils.getActorId(request));
		node.setName(bean.getName());
		node.setDesc(bean.getName());
		node.setCode(bean.getCode());
		node.setParentId((long) ParamUtil.getIntParameter(request, "parent", 0));
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
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=saveModify")
	public ModelAndView saveModify(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		long id = ParamUtil.getIntParameter(request, "id", 0);
		SysDepartment bean = sysDepartmentService.findById(id);
		boolean ret = false;
		if (bean != null) {
			bean.setUpdateBy(RequestUtils.getActorId(request));
			bean.setName(ParamUtil.getParameter(request, "name"));
			bean.setDesc(ParamUtil.getParameter(request, "desc"));
			bean.setCode(ParamUtil.getParameter(request, "code"));
			bean.setCode2(ParamUtil.getParameter(request, "code2"));
			bean.setNo(ParamUtil.getParameter(request, "no"));
			bean.setStatus(ParamUtil.getIntParameter(request, "status", 0));
			SysTree node = bean.getNode();
			node.setUpdateBy(RequestUtils.getActorId(request));
			node.setName(bean.getName());
			node.setParentId((long) ParamUtil.getIntParameter(request,
					"parent", 0));
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

	public void setSysDepartmentService(
			SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
		logger.info("setSysDepartmentService");
	}

	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
		logger.info("setSysTreeService");
	}

	/**
	 * 显示所有部门的菜单选择页面
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showDeptAllSelect")
	public ModelAndView showDeptAllSelect(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
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
	 * 显示菜单选择页面
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showDeptSelect")
	public ModelAndView showDeptSelect(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
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
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showFrame")
	public ModelAndView showFrame(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
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
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=showList")
	public ModelAndView showList(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
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
		List<SysDepartment> list = new ArrayList<SysDepartment>();
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
}