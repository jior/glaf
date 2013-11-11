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
import java.util.List;
import java.util.Map;

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
import com.glaf.base.modules.Constants;
import com.glaf.base.modules.sys.model.SysApplication;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.query.SysApplicationQuery;
import com.glaf.base.modules.sys.service.SysApplicationService;
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

@Controller("/sys/application")
@RequestMapping("/sys/application.do")
public class SysApplicationController {
	private static final Log logger = LogFactory
			.getLog(SysApplicationController.class);

	private SysApplicationService sysApplicationService;

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
		boolean ret = true;
		long[] id = ParamUtil.getLongParameterValues(request, "id");
		ret = sysApplicationService.deleteAll(id);

		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"application.delete_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"application.delete_failure"));
		}
		MessageUtils.addMessages(request, messages);
		return new ModelAndView("show_msg2", modelMap);
	}

	/**
	 * 显示修改页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		// RequestUtils.setRequestParameterToAttribute(request);
		long id = ParamUtil.getIntParameter(request, "id", 0);
		if (id > 0) {
			SysApplication bean = sysApplicationService.findById(id);
			request.setAttribute("bean", bean);
		}

		SysTree parent = sysTreeService.getSysTreeByCode(Constants.TREE_APP);
		List<SysTree> list = new ArrayList<SysTree>();
		parent.setDeep(0);
		list.add(parent);
		sysTreeService.getSysTree(list, (int) parent.getId(), 1);
		request.setAttribute("parent", list);

		String x_view = ViewProperties.getString("application.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/app/edit", modelMap);
	}

	@RequestMapping(params = "method=json")
	@ResponseBody
	public byte[] json(HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysApplicationQuery query = new SysApplicationQuery();
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
		int total = sysApplicationService
				.getSysApplicationCountByQueryCriteria(query);
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

			List<SysApplication> list = sysApplicationService
					.getSysApplicationsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SysApplication sysApplication : list) {
					JSONObject rowJSON = sysApplication.toJsonObject();
					rowJSON.put("id", sysApplication.getId());
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
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("application.list");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/app/list", modelMap);
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
		// RequestUtils.setRequestParameterToAttribute(request);

		String x_view = ViewProperties.getString("application.prepareAdd");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/app/app_add", modelMap);
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
		// RequestUtils.setRequestParameterToAttribute(request);
		long id = ParamUtil.getIntParameter(request, "id", 0);
		SysApplication bean = sysApplicationService.findById(id);
		request.setAttribute("bean", bean);

		SysTree parent = sysTreeService.getSysTreeByCode(Constants.TREE_APP);
		List<SysTree> list = new ArrayList<SysTree>();
		parent.setDeep(0);
		list.add(parent);
		sysTreeService.getSysTree(list, (int) parent.getId(), 1);
		request.setAttribute("parent", list);

		String x_view = ViewProperties.getString("application.prepareModify");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/app/app_modify", modelMap);
	}

	@ResponseBody
	@RequestMapping(params = "method=save")
	public byte[] save(HttpServletRequest request, ModelMap modelMap) {
		boolean ret = false;
		long id = ParamUtil.getIntParameter(request, "id", 0);
		SysApplication bean = sysApplicationService.findById(id);
		if (bean != null) {
			bean.setName(ParamUtil.getParameter(request, "name"));
			bean.setCode(ParamUtil.getParameter(request, "code"));
			bean.setDesc(ParamUtil.getParameter(request, "desc"));
			bean.setUrl(ParamUtil.getParameter(request, "url"));
			bean.setShowMenu(ParamUtil.getIntParameter(request, "showMenu", 0));
			bean.setUpdateBy(RequestUtils.getActorId(request));
			bean.setLocked(ParamUtil.getIntParameter(request, "locked", 0));

			SysTree node = bean.getNode();
			node.setName(bean.getName());
			node.setCode(bean.getCode());
			node.setDesc(bean.getName());
			node.setParentId(ParamUtil.getLongParameter(request, "parent", 0));
			bean.setNode(node);
			try {
				ret = sysApplicationService.update(bean);
			} catch (Exception ex) {
				ret = false;
				logger.error(ex);
			}
		} else {
			bean = new SysApplication();
			bean.setName(ParamUtil.getParameter(request, "name"));
			bean.setCode(ParamUtil.getParameter(request, "code"));
			bean.setDesc(ParamUtil.getParameter(request, "desc"));
			bean.setUrl(ParamUtil.getParameter(request, "url"));
			bean.setShowMenu(ParamUtil.getIntParameter(request, "showMenu", 0));
			bean.setCreateBy(RequestUtils.getActorId(request));
			SysTree node = new SysTree();
			node.setName(bean.getName());
			node.setDesc(bean.getName());
			node.setCode(bean.getCode());
			node.setCreateBy(RequestUtils.getActorId(request));
			node.setParentId((long) ParamUtil.getIntParameter(request,
					"parent", 0));
			bean.setNode(node);
			try {
				ret = sysApplicationService.create(bean);
			} catch (Exception ex) {
				ret = false;
				logger.error(ex);
			}
		}

		if (ret) {
			ResponseUtils.responseResult(true);
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
		SysApplication bean = new SysApplication();
		bean.setName(ParamUtil.getParameter(request, "name"));
		bean.setCode(ParamUtil.getParameter(request, "code"));
		bean.setDesc(ParamUtil.getParameter(request, "desc"));
		bean.setUrl(ParamUtil.getParameter(request, "url"));
		bean.setShowMenu(ParamUtil.getIntParameter(request, "showMenu", 0));
		bean.setCreateBy(RequestUtils.getActorId(request));
		SysTree node = new SysTree();
		node.setName(bean.getName());
		node.setDesc(bean.getName());
		node.setCode(bean.getCode());
		node.setCreateBy(RequestUtils.getActorId(request));
		node.setParentId((long) ParamUtil.getIntParameter(request, "parent", 0));
		bean.setNode(node);

		boolean ret = sysApplicationService.create(bean);
		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"application.add_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"application.add_failure"));
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
		SysApplication bean = sysApplicationService.findById(id);
		if (bean != null) {
			bean.setName(ParamUtil.getParameter(request, "name"));
			bean.setCode(ParamUtil.getParameter(request, "code"));
			bean.setDesc(ParamUtil.getParameter(request, "desc"));
			bean.setUrl(ParamUtil.getParameter(request, "url"));
			bean.setShowMenu(ParamUtil.getIntParameter(request, "showMenu", 0));
			bean.setUpdateBy(RequestUtils.getActorId(request));
			bean.setLocked(ParamUtil.getIntParameter(request, "locked", 0));

			SysTree node = bean.getNode();
			node.setName(bean.getName());
			node.setCode(bean.getCode());
			node.setDesc(bean.getName());
			node.setParentId(ParamUtil.getLongParameter(request, "parent", 0));
			bean.setNode(node);
		}
		boolean ret = false;
		try {
			ret = sysApplicationService.update(bean);
		} catch (Exception ex) {
			ret = false;
			logger.error(ex);
		}
		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"application.modify_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"application.modify_failure"));
		}
		MessageUtils.addMessages(request, messages);
		return new ModelAndView("show_msg", modelMap);
	}

	@javax.annotation.Resource
	public void setSysApplicationService(
			SysApplicationService sysApplicationService) {
		this.sysApplicationService = sysApplicationService;

	}

	@javax.annotation.Resource
	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;

	}

	/**
	 * 显示框架页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=showBase")
	public ModelAndView showBase(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String x_view = ViewProperties.getString("application.showBase");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/sys/app/basedata_frame", modelMap);
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
		SysTree bean = sysTreeService.getSysTreeByCode(Constants.TREE_APP);
		request.setAttribute("parent", bean.getId() + "");
		String x_view = ViewProperties.getString("application.showFrame");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/sys/app/app_frame", modelMap);
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
		PageResult pager = sysApplicationService.getApplicationList(parent,
				pageNo, pageSize);
		request.setAttribute("pager", pager);

		String x_view = ViewProperties.getString("application.showList");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		// 显示列表页面
		return new ModelAndView("/modules/sys/app/app_list", modelMap);
	}

	/**
	 * 显示二级栏目导航菜单
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=showNavMenu")
	public ModelAndView showNavMenu(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		int parent = ParamUtil.getIntParameter(request, "parent", 0);
		List<SysTree> list = new ArrayList<SysTree>();
		sysTreeService.getSysTree(list, parent, 0);
		request.setAttribute("list", list);

		String x_view = ViewProperties.getString("application.showNavMenu");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/app/navmenu", modelMap);
	}

	@RequestMapping(params = "method=showPermission")
	public ModelAndView showPermission(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String x_view = ViewProperties.getString("application.showPermission");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/app/permission_frame", modelMap);
	}

	@ResponseBody
	@RequestMapping(params = "method=sort")
	public void sort(@RequestParam(value = "parent") int parent,
			@RequestParam(value = "id") int id,
			@RequestParam(value = "operate") int operate) {
		logger.info("parent:" + parent + "; id:" + id + "; operate:" + operate);
		SysApplication bean = sysApplicationService.findById(id);
		sysApplicationService.sort(parent, bean, operate);
	}
}