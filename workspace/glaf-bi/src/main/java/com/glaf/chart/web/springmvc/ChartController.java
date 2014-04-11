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

package com.glaf.chart.web.springmvc;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.jfree.chart.JFreeChart;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.chart.domain.Chart;
import com.glaf.chart.gen.ChartFactory;
import com.glaf.chart.gen.ChartGen;
import com.glaf.chart.query.ChartQuery;
import com.glaf.chart.service.IChartService;
import com.glaf.chart.util.ChartUtils;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.QueryDefinition;
import com.glaf.core.query.QueryDefinitionQuery;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IQueryDefinitionService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.LogUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;

@Controller("/bi/chart")
@RequestMapping("/bi/chart")
public class ChartController {
	protected static final Log logger = LogFactory
			.getLog(ChartController.class);

	protected IChartService chartService;

	protected IQueryDefinitionService queryDefinitionService;

	public ChartController() {

	}

	@ResponseBody
	@RequestMapping("/chart")
	public byte[] chart(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String rowId = ParamUtils.getString(params, "chartId");
		String name = ParamUtils.getString(params, "name");
		Chart chart = null;
		if (StringUtils.isNotEmpty(rowId)) {
			chart = chartService.getChart(rowId);
		} else if (StringUtils.isNotEmpty(name)) {
			chart = chartService.getChartByName(name);
		}
		if (chart != null) {
			chart = chartService
					.getChartAndFetchDataById(chart.getId(), params);
			logger.debug("chart rows size:" + chart.getColumns().size());
			ChartGen chartGen = ChartFactory.getChartGen(chart.getChartType());
			if (chartGen != null) {
				JFreeChart jchart = chartGen.createChart(chart);
				return ChartUtils.createChart(chart, jchart);
			}
		}

		return null;
	}

	@RequestMapping("/chartTree")
	public ModelAndView chartTree(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String x_view = ViewProperties.getString("chart.chartTree");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/bi/chart/chart_tree", modelMap);
	}

	@RequestMapping("/chooseQuery")
	public ModelAndView chooseQuery(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String rowId = ParamUtils.getString(params, "chartId");
		QueryDefinitionQuery query = new QueryDefinitionQuery();
		List<QueryDefinition> list = queryDefinitionService.list(query);
		request.setAttribute("unselecteds", list);
		Chart chart = null;
		if (StringUtils.isNotEmpty(rowId)) {
			chart = chartService.getChart(rowId);
			request.setAttribute("chart", chart);
			if (StringUtils.isNotEmpty(chart.getQueryIds())) {
				StringBuffer sb01 = new StringBuffer();
				StringBuffer sb02 = new StringBuffer();
				List<String> selecteds = new java.util.concurrent.CopyOnWriteArrayList<String>();
				for (QueryDefinition q : list) {
					if (StringUtils.contains(chart.getQueryIds(), q.getId())) {
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
		}

		String x_view = ViewProperties.getString("chart.chooseQuery");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/bi/chart/chooseQuery", modelMap);
	}

	@RequestMapping("/delete")
	public ModelAndView delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext securityContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String rowId = ParamUtils.getString(params, "chartId");
		String rowIds = request.getParameter("chartIds");
		if (StringUtils.isNotEmpty(rowIds)) {
			StringTokenizer token = new StringTokenizer(rowIds, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					chartService.deleteById(x);
				}
			}
		} else if (StringUtils.isNotEmpty(rowId)) {
			Chart chart = chartService.getChart(rowId);
			if (chart != null
					&& StringUtils.equals(chart.getCreateBy(),
							securityContext.getActorId())) {
				chartService.deleteById(chart.getId());
			}
		}

		return this.list(request, modelMap);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String rowId = ParamUtils.getString(params, "chartId");
		Chart chart = null;
		if (StringUtils.isNotEmpty(rowId)) {
			chart = chartService.getChart(rowId);
			request.setAttribute("chart", chart);
			if (StringUtils.isNotEmpty(chart.getQueryIds())) {
				List<String> queryIds = StringTools.split(chart.getQueryIds());
				StringBuffer sb01 = new StringBuffer();
				StringBuffer sb02 = new StringBuffer();
				for (String queryId : queryIds) {
					QueryDefinition queryDefinition = queryDefinitionService
							.getQueryDefinition(queryId);
					if (queryDefinition != null) {
						sb01.append(queryDefinition.getId()).append(",");
						sb02.append(queryDefinition.getTitle()).append("[")
								.append(queryDefinition.getId()).append("],");
					}
				}
				if (sb01.toString().endsWith(",")) {
					sb01.delete(sb01.length() - 1, sb01.length());
				}
				if (sb02.toString().endsWith(",")) {
					sb02.delete(sb02.length() - 1, sb02.length());
				}
				request.setAttribute("queryIds", sb01.toString());
				request.setAttribute("queryNames", sb02.toString());
			}
		}

		String x_view = ViewProperties.getString("chart.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/bi/chart/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ChartQuery query = new ChartQuery();
		Tools.populate(query, params);

		Long nodeId = RequestUtils.getLong(request, "nodeId");
		if (nodeId != null && nodeId > 0) {
			query.nodeId(nodeId);
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
		int total = chartService.getChartCountByQueryCriteria(query);
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

			List<Chart> list = chartService.getChartsByQueryCriteria(start,
					limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Chart chart : list) {
					JSONObject rowJSON = chart.toJsonObject();
					rowsJSON.add(rowJSON);
				}

			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
		}
		LogUtils.debug(result.toJSONString());
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

		return new ModelAndView("/bi/chart/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("chart.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/bi/chart/query", modelMap);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		LoginContext securityContext = RequestUtils.getLoginContext(request);
		String actorId = securityContext.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		Chart chart = new Chart();
		Tools.populate(chart, params);
		chart.setCreateBy(actorId);

		String querySQL = request.getParameter("querySQL");
		if (StringUtils.isNotEmpty(querySQL)) {
			if (!DBUtils.isLegalQuerySql(querySQL)) {
				throw new RuntimeException("SQL查询不合法！");
			}
		}

		chartService.save(chart);

		return this.list(request, modelMap);
	}

	@javax.annotation.Resource
	public void setChartService(IChartService chartService) {
		this.chartService = chartService;
	}

	@javax.annotation.Resource
	public void setQueryDefinitionService(
			IQueryDefinitionService queryDefinitionService) {
		this.queryDefinitionService = queryDefinitionService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		String rowId = ParamUtils.getString(params, "chartId");
		Chart chart = null;
		if (StringUtils.isNotEmpty(rowId)) {
			chart = chartService.getChart(rowId);
		}

		if (chart != null) {
			Tools.populate(chart, params);

			String querySQL = request.getParameter("querySQL");
			if (StringUtils.isNotEmpty(querySQL)) {
				if (!DBUtils.isLegalQuerySql(querySQL)) {
					throw new RuntimeException("SQL查询不合法！");
				}
			}

			chartService.save(chart);
		}

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String rowId = ParamUtils.getString(params, "chartId");
		Chart chart = null;
		if (StringUtils.isNotEmpty(rowId)) {
			chart = chartService.getChart(rowId);
			request.setAttribute("chart", chart);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("chart.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/bi/chart/view");
	}

}