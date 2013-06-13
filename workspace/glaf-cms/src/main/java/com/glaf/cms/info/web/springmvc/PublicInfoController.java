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

package com.glaf.cms.info.web.springmvc;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import com.alibaba.fastjson.*;

import com.glaf.core.base.DataFile;
import com.glaf.core.base.TreeModel;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.User;

import com.glaf.core.security.IdentityFactory;
import com.glaf.core.service.IBlobService;
import com.glaf.core.service.ISystemParamService;
import com.glaf.core.service.ITreeModelService;
import com.glaf.core.util.*;

import com.glaf.cms.info.model.*;
import com.glaf.cms.info.query.*;
import com.glaf.cms.info.service.*;

@Controller("/public/info")
@RequestMapping("/public/info")
public class PublicInfoController {
	protected static final Log logger = LogFactory
			.getLog(PublicInfoController.class);

	protected IBlobService blobService;

	protected PublicInfoService publicInfoService;

	protected ISystemParamService systemParamService;

	protected ITreeModelService treeModelService;

	public PublicInfoController() {

	}

	@ResponseBody
	@RequestMapping("/detail")
	public byte[] detail(HttpServletRequest request) throws IOException {
		// RequestUtils.setRequestParameterToAttribute(request);
		// Map<String, Object> params = RequestUtils.getParameterMap(request);
		PublicInfo publicInfo = publicInfoService.getPublicInfo(request
				.getParameter("id"));
		try {
			publicInfo.setViewCount(publicInfo.getViewCount() + 1);
			publicInfoService.save(publicInfo);
		} catch (Exception ex) {
			logger.error(ex);
		}

		JSONObject rowJSON = publicInfo.toJsonObject();

		List<DataFile> dataFiles = blobService.getBlobList(publicInfo.getId());
		if (dataFiles != null && !dataFiles.isEmpty()) {
			JSONArray array = new JSONArray();
			if (dataFiles != null && !dataFiles.isEmpty()) {
				for (DataFile model : dataFiles) {
					JSONObject jsonObject = model.toJsonObject();
					array.add(jsonObject);
				}
			}
			rowJSON.put("files", array);
		}

		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/indexList")
	public ModelAndView indexList(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String serviceKey = request.getParameter("serviceKey");
		if (StringUtils.isNotEmpty(serviceKey)) {
			TreeModel treeModel = treeModelService
					.getTreeModelByCode(serviceKey);
			request.setAttribute("treeModel", treeModel);

			Map<String, Object> params = RequestUtils.getParameterMap(request);
			PublicInfoQuery query = new PublicInfoQuery();
			Tools.populate(query, params);
			query.setPublishFlag(1);
			query.serviceKey(serviceKey);

			List<PublicInfo> rows = publicInfoService.list(query);
			request.setAttribute("rows", rows);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/cms/info/indexList", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		RequestUtils.setRequestParameterToAttribute(request);

		String serviceKey = request.getParameter("serviceKey");
		Long nodeId = RequestUtils.getLong(request, "nodeId");

		Map<Long, TreeModel> treeModelMap = new HashMap<Long, TreeModel>();
		TreeModel treeModel = null;
		if (StringUtils.isNotEmpty(serviceKey)) {
			treeModel = treeModelService.getTreeModelByCode(serviceKey);
			request.setAttribute("treeModel", treeModel);
			List<TreeModel> treeModels = treeModelService
					.getChildrenTreeModels(treeModel.getId());
			if (treeModels != null && !treeModels.isEmpty()) {
				for (TreeModel t : treeModels) {
					treeModelMap.put(t.getId(), t);
				}
			}
		}

		if (nodeId > 0) {
			treeModel = treeModelService.getTreeModel(nodeId);
			request.setAttribute("treeModel", treeModel);
		}

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		PublicInfoQuery query = new PublicInfoQuery();
		Tools.populate(query, params);
		query.setPublishFlag(1);

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
		int total = publicInfoService.getPublicInfoCountByQueryCriteria(query);
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

			List<PublicInfo> list = publicInfoService
					.getPublicInfosByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (PublicInfo publicInfo : list) {
					Long nid = publicInfo.getNodeId();
					TreeModel tree = treeModelMap.get(nid);
					JSONObject rowJSON = publicInfo.toJsonObject();
					rowJSON.put("id", publicInfo.getId());
					rowJSON.put("publicInfoId", publicInfo.getId());
					rowJSON.put("startIndex", ++start);
					if (tree != null) {
						rowJSON.put("categoryName", tree.getName());
					}
					rowsJSON.add(rowJSON);
				}

			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
		}
		return result.toJSONString().getBytes("UTF-8");
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

		String serviceKey = request.getParameter("serviceKey");
		if (StringUtils.isNotEmpty(serviceKey)) {
			TreeModel treeModel = treeModelService
					.getTreeModelByCode(serviceKey);
			request.setAttribute("treeModel", treeModel);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/cms/info/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("publicInfo.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/cms/info/query", modelMap);
	}

	@javax.annotation.Resource
	public void setBlobService(IBlobService blobService) {
		this.blobService = blobService;
	}

	@javax.annotation.Resource
	public void setPublicInfoService(PublicInfoService publicInfoService) {
		this.publicInfoService = publicInfoService;
	}

	@javax.annotation.Resource
	public void setSystemParamService(ISystemParamService systemParamService) {
		this.systemParamService = systemParamService;
	}

	@javax.annotation.Resource
	public void setTreeModelService(ITreeModelService treeModelService) {
		this.treeModelService = treeModelService;
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		PublicInfo publicInfo = publicInfoService.getPublicInfo(request
				.getParameter("id"));
		if (publicInfo != null) {
			request.setAttribute("publicInfo", publicInfo);
			User user = IdentityFactory.getUser(publicInfo.getCreateBy());
			if (user != null) {
				publicInfo.setCreateByName(user.getName());
			}
			try {
				publicInfoService.updateViewCount(publicInfo.getId());
			} catch (Exception ex) {
				logger.error(ex);
			}

			JSONObject rowJSON = publicInfo.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());

			List<DataFile> dataFiles = blobService.getBlobList(publicInfo
					.getId());
			request.setAttribute("dataFiles", dataFiles);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("publicInfo.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/cms/info/view");
	}

}
