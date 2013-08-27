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

package com.glaf.report.web.springmvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.chart.domain.Chart;
import com.glaf.chart.query.ChartQuery;
import com.glaf.chart.service.IChartService;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.QueryDefinition;
import com.glaf.core.query.QueryDefinitionQuery;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IQueryDefinitionService;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.LogUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.report.config.ReportConfig;
import com.glaf.report.domain.Report;
import com.glaf.report.gen.ReportFactory;
import com.glaf.report.jxls.JxlsReportContainer;
import com.glaf.report.query.ReportQuery;
import com.glaf.report.service.IReportService;

@Controller("/bi/report")
@RequestMapping("/bi/report")
public class MxReportController {

	protected IReportService reportService;

	protected IChartService chartService;

	protected IQueryDefinitionService queryDefinitionService;

	@RequestMapping("/chooseChart")
	public ModelAndView chooseChart(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String rowId = ParamUtils.getString(params, "reportId");
		ChartQuery query = new ChartQuery();
		List<Chart> list = chartService.list(query);
		request.setAttribute("unselecteds", list);
		Report report = null;
		if (StringUtils.isNotEmpty(rowId)) {
			report = reportService.getReport(rowId);
			request.setAttribute("report", report);
			if (StringUtils.isNotEmpty(report.getChartIds())) {
				StringBuffer sb01 = new StringBuffer();
				StringBuffer sb02 = new StringBuffer();
				List<String> selecteds = new ArrayList<String>();
				List<String> chartIds = StringTools.split(report.getChartIds());
				for (Chart c : list) {
					if (chartIds.contains(c.getId())) {
						selecteds.add(c.getId());
						sb01.append(c.getId()).append(",");
						sb02.append(c.getSubject()).append(",");
					}
				}
				if (sb01.toString().endsWith(",")) {
					sb01.delete(sb01.length() - 1, sb01.length());
				}
				if (sb02.toString().endsWith(",")) {
					sb02.delete(sb02.length() - 1, sb02.length());
				}
				request.setAttribute("selecteds", selecteds);
				request.setAttribute("chartIds", sb01.toString());

				request.setAttribute("chartNames", sb02.toString());
			}

			if (StringUtils.isNotEmpty(report.getQueryIds())) {
				List<String> queryIds = StringTools.split(report.getQueryIds());
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

		String x_view = ViewProperties.getString("report.chooseChart");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/bi/report/chooseChart", modelMap);
	}

	@RequestMapping("/chooseQuery")
	public ModelAndView chooseQuery(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String rowId = ParamUtils.getString(params, "reportId");
		QueryDefinitionQuery query = new QueryDefinitionQuery();
		List<QueryDefinition> list = queryDefinitionService.list(query);
		request.setAttribute("unselecteds", list);
		Report report = null;
		if (StringUtils.isNotEmpty(rowId)) {
			report = reportService.getReport(rowId);
			request.setAttribute("report", report);
			if (StringUtils.isNotEmpty(report.getQueryIds())) {
				StringBuffer sb01 = new StringBuffer();
				StringBuffer sb02 = new StringBuffer();
				List<String> selecteds = new ArrayList<String>();
				for (QueryDefinition q : list) {
					if (StringUtils.contains(report.getQueryIds(), q.getId())) {
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

		String x_view = ViewProperties.getString("report.chooseQuery");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/bi/report/chooseQuery", modelMap);
	}

	@RequestMapping("/createReport")
	public void createReport(HttpServletRequest request,
			HttpServletResponse response) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String reportId = ParamUtils.getString(params, "reportId");
		Report report = null;
		if (StringUtils.isNotEmpty(reportId)) {
			report = reportService.getReport(reportId);
			if (report != null) {
				String filename = report.getSubject() + "."
						+ report.getReportFormat();
				try {
					byte[] bytes = ReportFactory.createReportStream(report,
							params);
					if (bytes != null) {
						String destFileName = ReportConfig
								.getReportDestFileName(report);
						FileUtils.save(destFileName, bytes);
						ResponseUtils.download(request, response, bytes,
								filename);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	@RequestMapping("/delete")
	public ModelAndView delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext securityContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String rowId = ParamUtils.getString(params, "reportId");
		String rowIds = request.getParameter("reportIds");
		if (StringUtils.isNotEmpty(rowIds)) {
			StringTokenizer token = new StringTokenizer(rowIds, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Report report = reportService.getReport(x);
					if (report != null
							&& StringUtils.equals(report.getCreateBy(),
									securityContext.getActorId())) {
						reportService.deleteById(report.getId());
					}
				}
			}
		} else if (StringUtils.isNotEmpty(rowId)) {
			Report report = reportService.getReport(rowId);
			if (report != null
					&& StringUtils.equals(report.getCreateBy(),
							securityContext.getActorId())) {
				reportService.deleteById(report.getId());
			}
		}

		return this.list(request, modelMap);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String rowId = ParamUtils.getString(params, "reportId");
		Report report = null;
		if (StringUtils.isNotEmpty(rowId)) {
			report = reportService.getReport(rowId);
			request.setAttribute("report", report);
			if (StringUtils.isNotEmpty(report.getChartIds())) {
				StringBuffer sb01 = new StringBuffer();
				StringBuffer sb02 = new StringBuffer();
				List<Chart> selecteds = new ArrayList<Chart>();
				ChartQuery query = new ChartQuery();
				List<Chart> list = chartService.list(query);
				request.setAttribute("unselecteds", list);
				for (Chart c : list) {
					if (StringUtils.contains(report.getChartIds(), c.getId())) {
						selecteds.add(c);
						sb01.append(c.getId()).append(",");
						sb02.append(c.getSubject()).append(",");
					}
				}
				if (sb01.toString().endsWith(",")) {
					sb01.delete(sb01.length() - 1, sb01.length());
				}
				if (sb02.toString().endsWith(",")) {
					sb02.delete(sb02.length() - 1, sb02.length());
				}
				request.setAttribute("selecteds", selecteds);
				request.setAttribute("chartIds", sb01.toString());

				request.setAttribute("chartNames", sb02.toString());
			}
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("report.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/bi/report/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ReportQuery query = new ReportQuery();
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
		int total = reportService.getReportCountByQueryCriteria(query);
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

			List<Report> list = reportService.getReportsByQueryCriteria(start,
					limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Report report : list) {
					JSONObject rowJSON = report.toJsonObject();
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

		return new ModelAndView("/bi/report/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("report.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/bi/report/query", modelMap);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		LoginContext securityContext = RequestUtils.getLoginContext(request);
		String actorId = securityContext.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");
		Report report = new Report();
		Tools.populate(report, params);
		report.setCreateBy(actorId);

		reportService.save(report);

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

	@javax.annotation.Resource
	public void setReportService(IReportService reportService) {
		this.reportService = reportService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");
		String rowId = ParamUtils.getString(params, "reportId");
		Report report = null;
		if (StringUtils.isNotEmpty(rowId)) {
			report = reportService.getReport(rowId);
		}

		Tools.populate(report, params);
		reportService.save(report);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String rowId = ParamUtils.getString(params, "reportId");
		Report report = null;
		if (StringUtils.isNotEmpty(rowId)) {
			report = reportService.getReport(rowId);
			request.setAttribute("report", report);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("report.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/bi/report/view");
	}

	@ResponseBody
	@RequestMapping("/xls")
	public void xls(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String reportId = request.getParameter("reportId");
		if (StringUtils.isNotEmpty(reportId)) {
			LoginContext loginContext = RequestUtils.getLoginContext(request);

			Map<String, Object> params = RequestUtils.getParameterMap(request);

			java.util.Enumeration<?> e = request.getAttributeNames();
			while (e.hasMoreElements()) {
				String name = (String) e.nextElement();
				params.put(name, request.getAttribute(name));
			}

			Workbook wb = JxlsReportContainer.getContainer().execute(reportId,
					loginContext.getActorId(), params);
			if (wb != null) {
				String contentDisposition = "attachment;filename=\"export.xls\"";
				response.setHeader("Content-Transfer-Encoding", "base64");
				response.setHeader("Content-Disposition", contentDisposition);
				response.setContentType("application/octet-stream");
				java.io.OutputStream outputStream = response.getOutputStream();
				wb.write(outputStream);
				outputStream.flush();
				outputStream.close();
				outputStream = null;
				wb = null;
			}
		}
	}

}