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
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.query.SysTreeQuery;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.utils.ParamUtil;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.res.MessageUtils;
import com.glaf.core.res.ViewMessage;
import com.glaf.core.res.ViewMessages;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;

@Controller("/base/tree")
@RequestMapping("/base/tree.do")
public class TreeController {
	private static final Log logger = LogFactory.getLog(TreeController.class);

	private SysTreeService sysTreeService;

	/**
	 * 批量删除信息
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=batchDelete")
	public ModelAndView batchDelete(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		ViewMessages messages = new ViewMessages();
		messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
				"tree.delete_failure"));
		MessageUtils.addMessages(request, messages);
		return new ModelAndView("show_msg2", modelMap);
	}

	/**
	 * 显示下级节点
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=getSubTree")
	public ModelAndView getSubTree(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		int id = ParamUtil.getIntParameter(request, "id", 0);
		List<SysTree> list = sysTreeService.getSysTreeList(id);
		Collections.sort(list);
		request.setAttribute("list", list);

		String x_view = ViewProperties.getString("baseTree.getSubTree");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/base/tree/subtree_list", modelMap);
	}

	@RequestMapping(params = "method=json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysTreeQuery query = new SysTreeQuery();
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
		int total = sysTreeService.getSysTreeCountByQueryCriteria(query);
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

			List<SysTree> list = sysTreeService.getSysTreesByQueryCriteria(
					start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SysTree sysTree : list) {
					JSONObject rowJSON = sysTree.toJsonObject();
					rowJSON.put("id", sysTree.getId());
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

		String x_view = ViewProperties.getString("baseTree.list");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/modules/base/tree/list", modelMap);
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
		request.setAttribute("contextPath", request.getContextPath());

		String x_view = ViewProperties.getString("baseTree.prepareAdd");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/base/tree/tree_add", modelMap);
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
		long id = ParamUtil.getIntParameter(request, "id", 0);
		SysTree bean = sysTreeService.findById(id);
		if (bean != null && bean.getParentId() > 0) {
			SysTree parent = sysTreeService.findById(bean.getParentId());
			bean.setParent(parent);
		}
		request.setAttribute("bean", bean);
		List<SysTree> list = new java.util.ArrayList<SysTree>();
		sysTreeService.getSysTree(list, 0, 0);
		request.setAttribute("parent", list);

		String x_view = ViewProperties.getString("baseTree.prepareModify");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/base/tree/tree_modify", modelMap);
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
		SysTree bean = new SysTree();
		bean.setParentId(ParamUtil.getIntParameter(request, "parent", 0));
		bean.setName(ParamUtil.getParameter(request, "name"));
		bean.setDesc(ParamUtil.getParameter(request, "desc"));
		bean.setCode(ParamUtil.getParameter(request, "code"));
		boolean ret = sysTreeService.create(bean);
		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"tree.add_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"tree.add_failure"));
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
	@RequestMapping(params = "method=saveModify")
	public ModelAndView saveModify(HttpServletRequest request, ModelMap modelMap) {
		long id = ParamUtil.getIntParameter(request, "id", 0);
		SysTree bean = sysTreeService.findById(id);
		if (bean != null) {
			bean.setParentId(ParamUtil.getIntParameter(request, "parent", 0));
			bean.setName(ParamUtil.getParameter(request, "name"));
			bean.setDesc(ParamUtil.getParameter(request, "desc"));
			bean.setCode(ParamUtil.getParameter(request, "code"));
		}
		boolean ret = false;
		try {
			ret = sysTreeService.update(bean);
		} catch (Exception ex) {
			ret = false;
			logger.error(ex);
		}
		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"tree.modify_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"tree.modify_failure"));
		}
		MessageUtils.addMessages(request, messages);
		// 显示列表页面
		return new ModelAndView("show_msg", modelMap);
	}

	@javax.annotation.Resource
	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;

	}

	/**
	 * 显示左边菜单
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=showLeft")
	public ModelAndView showLeft(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		ModelAndView forward = new ModelAndView("/modules/base/tree/tree_left",
				modelMap);
		int parent = ParamUtil.getIntParameter(request, "parent",
				SysConstants.TREE_ROOT);
		request.setAttribute("parent", sysTreeService.findById(parent));
		String url = ParamUtil.getParameter(request, "url");

		String x_view = ViewProperties.getString("baseTree.showLeft");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		request.setAttribute("url", url);

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
	@RequestMapping(params = "method=showList")
	public ModelAndView showList(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		int parentId = ParamUtil.getIntParameter(request, "parent", 0);
		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = ParamUtil.getIntParameter(request, "page_size",
				Constants.PAGE_SIZE);
		if (parentId > 0) {
			SysTree parent = sysTreeService.findById(parentId);
			request.setAttribute("parent", parent);
		}
		PageResult pager = sysTreeService.getSysTreeList(parentId, pageNo,
				pageSize);
		request.setAttribute("pager", pager);

		String x_view = ViewProperties.getString("baseTree.showList");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/base/tree/tree_list", modelMap);
	}

	/**
	 * 显示主页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=showMain")
	public ModelAndView showMain(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String x_view = ViewProperties.getString("baseTree.showMain");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/base/tree/tree_frame", modelMap);
	}

	@RequestMapping(params = "method=showTop")
	public ModelAndView showTop(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String x_view = ViewProperties.getString("baseTree.showTop");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/base/tree/tree_top", modelMap);
	}

	@ResponseBody
	@RequestMapping(params = "method=sort")
	public byte[] sort(@RequestParam(value = "parent") int parent,
			@RequestParam(value = "id") int id,
			@RequestParam(value = "operate") int operate) {
		logger.info("parent:" + parent + "; id:" + id + "; operate:" + operate);
		try {
			SysTree bean = sysTreeService.findById(id);
			sysTreeService.sort(parent, bean, operate);
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}
}