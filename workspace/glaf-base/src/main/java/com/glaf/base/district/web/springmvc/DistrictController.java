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

package com.glaf.base.district.web.springmvc;

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
import com.glaf.core.base.BaseTree;
import com.glaf.core.base.TreeModel;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.tree.helper.TreeHelper;
import com.glaf.core.util.*;
import com.glaf.base.district.domain.*;
import com.glaf.base.district.query.*;
import com.glaf.base.district.service.*;

@Controller("/sys/district")
@RequestMapping("/sys/district")
public class DistrictController {
	protected static final Log logger = LogFactory
			.getLog(DistrictController.class);

	protected DistrictService districtService;

	public DistrictController() {

	}

	@ResponseBody
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Long id = RequestUtils.getLong(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					DistrictEntity district = districtService.getDistrict(Long
							.valueOf(x));
					if (district != null
							&& (StringUtils.equals(district.getCreateBy(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						districtService.deleteById(district.getId());
					}
				}
			}
		} else if (id != null) {
			DistrictEntity district = districtService.getDistrict(Long
					.valueOf(id));
			if (district != null
					&& (StringUtils.equals(district.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				districtService.deleteById(district.getId());
			}
		}
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		RequestUtils.setRequestParameterToAttribute(request);

		DistrictEntity district = districtService.getDistrict(RequestUtils
				.getLong(request, "id"));
		if (district != null
				&& (StringUtils.equals(district.getCreateBy(),
						loginContext.getActorId()) || loginContext
						.isSystemAdministrator())) {
			request.setAttribute("district", district);
		}

		Long parentId = RequestUtils.getLong(request, "parentId", 0);
		List<DistrictEntity> districts = districtService
				.getDistrictList(parentId);
		if (district != null) {
			List<DistrictEntity> children = districtService
					.getDistrictList(district.getId());
			if (districts != null && !districts.isEmpty()) {
				if (children != null && !children.isEmpty()) {
					districts.removeAll(children);
				}
				districts.remove(district);
			}
		}

		request.setAttribute("districts", districts);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("district.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/district/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug("params:"+params);
		DistrictQuery query = new DistrictQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		String actorId = loginContext.getActorId();
		query.createBy(actorId);

		Long parentId = RequestUtils.getLong(request, "parentId", 0);
		query.parentId(parentId);

		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "easyui";
		}
		int start = 0;
		int limit = 15;
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
		int total = districtService.getDistrictCountByQueryCriteria(query);
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

			List<DistrictEntity> list = districtService
					.getDistrictsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DistrictEntity district : list) {
					JSONObject rowJSON = district.toJsonObject();
					rowJSON.put("id", district.getId());
					rowJSON.put("pId", district.getParentId());
					rowJSON.put("districtId", district.getId());
					rowJSON.put("districtId", district.getId());
					rowJSON.put("startIndex", ++start);
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

		String requestURI = request.getRequestURI();
		logger.debug("requestURI:" + requestURI);
		logger.debug("queryString:" + request.getQueryString());
		request.setAttribute(
				"fromUrl",
				RequestUtils.encodeURL(requestURI + "?"
						+ request.getQueryString()));

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/modules/sys/district/list", modelMap);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DistrictEntity district = new DistrictEntity();
		Tools.populate(district, params);
		district.setParentId(RequestUtils.getLong(request, "parentId"));
		district.setSortNo(RequestUtils.getInt(request, "sortNo"));
		district.setName(request.getParameter("name"));
		district.setCode(request.getParameter("code"));
		district.setCreateBy(actorId);
		districtService.save(district);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveDistrict")
	public byte[] saveDistrict(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DistrictEntity district = new DistrictEntity();
		try {
			Tools.populate(district, params);
			district.setParentId(RequestUtils.getLong(request, "parentId"));
			district.setSortNo(RequestUtils.getInt(request, "sortNo"));
			district.setName(request.getParameter("name"));
			district.setCode(request.getParameter("code"));
			district.setCreateBy(actorId);
			this.districtService.save(district);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setDistrictService(DistrictService districtService) {
		this.districtService = districtService;
	}

	@ResponseBody
	@RequestMapping("/treeJson")
	public byte[] treeJson(HttpServletRequest request) throws IOException {
		JSONArray array = new JSONArray();
		Long parentId = RequestUtils.getLong(request, "parentId", 0);
		List<DistrictEntity> districts = null;
		if (parentId != null) {
			districts = districtService.getDistrictList(parentId);
		}
		if (districts != null && !districts.isEmpty()) {
			Map<Long, TreeModel> treeMap = new HashMap<Long, TreeModel>();
			List<TreeModel> treeModels = new ArrayList<TreeModel>();
			List<Long> districtIds = new ArrayList<Long>();
			for (DistrictEntity district : districts) {
				TreeModel tree = new BaseTree();
				tree.setId(district.getId());
				tree.setParentId(district.getParentId());
				tree.setCode(district.getCode());
				tree.setName(district.getName());
				tree.setSortNo(district.getSortNo());
				tree.setIconCls("tree_folder");
				treeModels.add(tree);
				districtIds.add(district.getId());
				treeMap.put(district.getId(), tree);
			}
			logger.debug("treeModels:" + treeModels.size());
			TreeHelper treeHelper = new TreeHelper();
			JSONArray jsonArray = treeHelper.getTreeJSONArray(treeModels);
			for(int i=0,len = jsonArray.size();i<len;i++){
				JSONObject json = jsonArray.getJSONObject(i);
				json.put("isParent", true);
			}
			logger.debug(jsonArray.toJSONString());
			return jsonArray.toJSONString().getBytes("UTF-8");
		}
		return array.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/view/{id}")
	public ModelAndView view(@PathVariable("id") Long id,
			HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		DistrictEntity district = districtService.getDistrict(id);
		request.setAttribute("district", district);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("district.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/modules/sys/district/view");
	}

}
