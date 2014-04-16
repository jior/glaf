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

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.core.config.ViewProperties;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.Tools;
import com.glaf.report.domain.Report;
import com.glaf.report.domain.ReportTask;
import com.glaf.report.query.ReportQuery;
import com.glaf.report.service.IReportService;
import com.glaf.report.service.IReportTaskService;

@Controller("/bi/reportTask")
@RequestMapping("/bi/reportTask")
public class MxReportTaskController {
	protected static final Log logger = LogFactory
			.getLog(MxReportTaskController.class);

	protected IReportService reportService;

	protected IReportTaskService reportTaskService;

	public MxReportTaskController() {

	}

	@RequestMapping("/chooseReport")
	public ModelAndView chooseReport(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String rowId = ParamUtils.getString(params, "rowId");
		ReportQuery query = new ReportQuery();
		List<Report> list = reportService.list(query);
		request.setAttribute("unselecteds", list);
		ReportTask reportTask = null;
		if (StringUtils.isNotEmpty(rowId)) {
			reportTask = reportTaskService.getReportTask(rowId);
			request.setAttribute("reportTask", reportTask);
			if (StringUtils.isNotEmpty(reportTask.getReportIds())) {
				StringBuffer sb01 = new StringBuffer();
				StringBuffer sb02 = new StringBuffer();
				List<String> selecteds = new java.util.ArrayList<String>();
				for (Report r : list) {
					if (StringUtils.contains(reportTask.getReportIds(),
							r.getId())) {
						selecteds.add(r.getId());
						sb01.append(r.getId()).append(",");
						sb02.append(r.getSubject()).append(",");
					}
				}
				if (sb01.toString().endsWith(",")) {
					sb01.delete(sb01.length() - 1, sb01.length());
				}
				if (sb02.toString().endsWith(",")) {
					sb02.delete(sb02.length() - 1, sb02.length());
				}
				request.setAttribute("selecteds", selecteds);
				request.setAttribute("reportIds", sb01.toString());

				request.setAttribute("reportNames", sb02.toString());
			}
		}

		String x_view = ViewProperties.getString("reportTask.chooseReport");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/bi/reportTask/chooseReport", modelMap);
	}

	@RequestMapping("/delete")
	public ModelAndView delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String reportTaskId = ParamUtils.getString(params, "reportTaskId");
		String reportTaskIds = request.getParameter("reportTaskIds");
		if (StringUtils.isNotEmpty(reportTaskIds)) {
			StringTokenizer token = new StringTokenizer(reportTaskIds, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					ReportTask reportTask = reportTaskService.getReportTask(x);
					if (reportTask != null
							&& StringUtils.equals(reportTask.getCreateBy(),
									loginContext.getActorId())) {
						// reportTask.setDeleteFlag(1);
						reportTaskService.save(reportTask);
					}
				}
			}
		} else if (StringUtils.isNotEmpty(reportTaskId)) {
			ReportTask reportTask = reportTaskService
					.getReportTask(reportTaskId);
			if (reportTask != null
					&& StringUtils.equals(reportTask.getCreateBy(),
							loginContext.getActorId())) {
				// reportTask.setDeleteFlag(1);
				reportTaskService.save(reportTask);
			}
		}

		return this.list(request, modelMap);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String reportTaskId = ParamUtils.getString(params, "reportTaskId");
		ReportTask reportTask = null;
		if (StringUtils.isNotEmpty(reportTaskId)) {
			reportTask = reportTaskService.getReportTask(reportTaskId);
			request.setAttribute("reportTask", reportTask);
			if (StringUtils.isNotEmpty(reportTask.getReportIds())) {
				StringBuffer sb01 = new StringBuffer();
				StringBuffer sb02 = new StringBuffer();
				List<String> selecteds = new java.util.ArrayList<String>();
				ReportQuery query = new ReportQuery();
				List<Report> list = reportService.list(query);
				for (Report r : list) {
					if (StringUtils.contains(reportTask.getReportIds(),
							r.getId())) {
						selecteds.add(r.getId());
						sb01.append(r.getId()).append(",");
						sb02.append(r.getSubject()).append(",");
					}
				}
				if (sb01.toString().endsWith(",")) {
					sb01.delete(sb01.length() - 1, sb01.length());
				}
				if (sb02.toString().endsWith(",")) {
					sb02.delete(sb02.length() - 1, sb02.length());
				}
				request.setAttribute("selecteds", selecteds);
				request.setAttribute("reportIds", sb01.toString());

				request.setAttribute("reportNames", sb02.toString());
			}
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("reportTask.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/bi/reportTask/edit", modelMap);
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

		String x_view = ViewProperties.getString("reportTask.list");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/bi/reportTask/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("reportTask.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/bi/reportTask/query", modelMap);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String actorId = loginContext.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.put("actorId", actorId);
		params.put("createBy", actorId);
		params.remove("status");
		params.remove("wfStatus");
		ReportTask reportTask = new ReportTask();
		Tools.populate(reportTask, params);
		// reportTask.setCreateBy(actorId);

		reportTaskService.save(reportTask);

		return this.list(request, modelMap);
	}

	@javax.annotation.Resource
	public void setReportService(IReportService reportService) {
		this.reportService = reportService;
	}

	@javax.annotation.Resource
	public void setReportTaskService(IReportTaskService reportTaskService) {
		this.reportTaskService = reportTaskService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");
		String reportTaskId = ParamUtils.getString(params, "reportTaskId");
		ReportTask reportTask = null;
		if (StringUtils.isNotEmpty(reportTaskId)) {
			reportTask = reportTaskService.getReportTask(reportTaskId);
		}

		if (reportTask != null
				&& StringUtils.equals(reportTask.getCreateBy(),
						loginContext.getActorId())) {
			// if (reportTask.getStatus() == 0
			// || reportTask.getStatus() == -1) {
			Tools.populate(reportTask, params);
			reportTaskService.save(reportTask);
			// }
		}

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String reportTaskId = ParamUtils.getString(params, "reportTaskId");
		ReportTask reportTask = null;
		if (StringUtils.isNotEmpty(reportTaskId)) {
			reportTask = reportTaskService.getReportTask(reportTaskId);
			request.setAttribute("reportTask", reportTask);
			Map<String, Object> dataMap = Tools.getDataMap(reportTask);
			String x_json = JsonUtils.encode(dataMap);
			x_json = RequestUtils.encodeString(x_json);
			request.setAttribute("x_json", x_json);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("reportTask.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/bi/reportTask/view");
	}

}
