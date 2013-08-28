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

package com.glaf.dts.web.springmvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.QueryDefinition;
import com.glaf.core.query.QueryDefinitionQuery;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IQueryDefinitionService;
import com.glaf.core.util.LogUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.Tools;

@Controller("/dts/query")
@RequestMapping("/dts/query")
public class MxDtsQueryController {

	protected IQueryDefinitionService queryDefinitionService;

	@RequestMapping("/chooseQuery")
	public ModelAndView chooseQuery(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String elementValue = request.getParameter("elementValue");
		QueryDefinitionQuery query = new QueryDefinitionQuery();
		query.setOrderBy(" E.TARGETTABLENAME_ asc, E.TITLE_ asc ");
		List<QueryDefinition> list = queryDefinitionService.list(query);
		request.setAttribute("unselecteds", list);

		if (StringUtils.isNotEmpty(elementValue)) {
			StringBuffer sb01 = new StringBuffer();
			StringBuffer sb02 = new StringBuffer();
			List<String> selecteds = new ArrayList<String>();
			for (QueryDefinition q : list) {
				if (StringUtils.contains(elementValue, q.getId())) {
					selecteds.add(q.getId());
					sb01.append(q.getId()).append(",");
					sb02.append(q.getName()).append(",");
				}
			}
			if (sb01.toString().endsWith(",")) {
				sb01.delete(sb01.length() - 1, sb01.length());
			}
			if (sb02.toString().endsWith(",")) {
				sb02.delete(sb02.length() - 1, sb02.length());
			}
			request.setAttribute("selecteds", selecteds);
			request.setAttribute("queryIds", sb01.toString());
			request.setAttribute("queryNames", sb02.toString());
		}

		String x_view = ViewProperties.getString("dts.chooseQuery");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/bi/dts/query/chooseQuery", modelMap);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		String jx_view = request.getParameter("jx_view");

		RequestUtils.setRequestParameterToAttribute(request);

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = ViewProperties.getString("dts_query.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/bi/dts/query/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		
		QueryDefinitionQuery query = new QueryDefinitionQuery();
		Tools.populate(query, params);
		query.type(com.glaf.dts.util.Constants.DTS_TASK_TYPE);

		Long nodeId = RequestUtils.getLong(request, "nodeId");
		if (nodeId != null && nodeId > 0) {
			query.nodeId(nodeId);
		}

		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		/**
		 * 此处业务逻辑需自行调整
		 */
		if (!loginContext.isSystemAdministrator()) {
			//String actorId = loginContext.getActorId();
			//query.createBy(actorId);
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
		int total = queryDefinitionService
				.getQueryDefinitionCountByQueryCriteria(query);
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

			List<QueryDefinition> list = queryDefinitionService
					.getQueryDefinitionsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				result.put("rows", rowsJSON);
				for (QueryDefinition queryDefinition : list) {
					JSONObject rowJSON = queryDefinition.toJsonObject();
					rowJSON.put("id", queryDefinition.getId());
					rowJSON.put("queryId", queryDefinition.getId());
					rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
				}

			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
		}
		LogUtils.debug(result.toJSONString());
		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {
		String jx_view = request.getParameter("jx_view");
		RequestUtils.setRequestParameterToAttribute(request);
		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = ViewProperties.getString("dts_query.list");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/bi/dts/query/list", modelMap);
	}

	@javax.annotation.Resource
	public void setQueryDefinitionService(
			IQueryDefinitionService queryDefinitionService) {
		this.queryDefinitionService = queryDefinitionService;
	}

	@RequestMapping("/sqleditor")
	public ModelAndView sqleditor(HttpServletRequest request, ModelMap modelMap) {
		String jx_view = request.getParameter("jx_view");
		RequestUtils.setRequestParameterToAttribute(request);
		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = ViewProperties.getString("dts_query.sqleditor");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/bi/dts/query/sqleditor", modelMap);
	}

}