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

import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.glaf.report.domain.ReportFile;
import com.glaf.report.service.IReportFileService;

@Controller("/bi/reportFile")
@RequestMapping("/bi/reportFile")
public class MxReportFileController {
	protected static final Log logger = LogFactory
			.getLog(MxReportFileController.class);

 
	protected IReportFileService reportFileService;

	public MxReportFileController() {

	}

	@RequestMapping("/delete")
	public ModelAndView delete(HttpServletRequest request, ModelMap modelMap) {

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String reportFileId = ParamUtils.getString(params, "reportFileId");
		String reportFileIds = request.getParameter("reportFileIds");
		if (StringUtils.isNotEmpty(reportFileIds)) {
			StringTokenizer token = new StringTokenizer(reportFileIds, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					ReportFile reportFile = reportFileService.getReportFile(x);
					if (reportFile != null) {

						reportFileService.save(reportFile);
					}
				}
			}
		} else if (StringUtils.isNotEmpty(reportFileId)) {
			ReportFile reportFile = reportFileService
					.getReportFile(reportFileId);
			if (reportFile != null) {

				reportFileService.save(reportFile);
			}
		}

		return this.list(request, modelMap);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String reportFileId = ParamUtils.getString(params, "reportFileId");
		ReportFile reportFile = null;
		if (StringUtils.isNotEmpty(reportFileId)) {
			reportFile = reportFileService.getReportFile(reportFileId);
			request.setAttribute("reportFile", reportFile);
			Map<String, Object> dataMap = Tools.getDataMap(reportFile);
			String x_json = JsonUtils.encode(dataMap);
			x_json = RequestUtils.encodeString(x_json);
			request.setAttribute("x_json", x_json);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("reportFile.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/bi/reportFile/edit", modelMap);
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

		String x_view = ViewProperties.getString("reportFile.list");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/bi/reportFile/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("reportFile.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/bi/reportFile/query", modelMap);
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
		ReportFile reportFile = new ReportFile();
		Tools.populate(reportFile, params);
		// reportFile.setCreateBy(actorId);

		reportFileService.save(reportFile);

		return this.list(request, modelMap);
	}

	@javax.annotation.Resource
	public void setReportFileService(IReportFileService reportFileService) {
		this.reportFileService = reportFileService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");
		String reportFileId = ParamUtils.getString(params, "reportFileId");
		ReportFile reportFile = null;
		if (StringUtils.isNotEmpty(reportFileId)) {
			reportFile = reportFileService.getReportFile(reportFileId);
		}

		if (reportFile != null) {

			Tools.populate(reportFile, params);
			reportFileService.save(reportFile);

		}

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String reportFileId = ParamUtils.getString(params, "reportFileId");
		ReportFile reportFile = null;
		if (StringUtils.isNotEmpty(reportFileId)) {
			reportFile = reportFileService.getReportFile(reportFileId);
			request.setAttribute("reportFile", reportFile);
			Map<String, Object> dataMap = Tools.getDataMap(reportFile);
			String x_json = JsonUtils.encode(dataMap);
			x_json = RequestUtils.encodeString(x_json);
			request.setAttribute("x_json", x_json);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("reportFile.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/bi/reportFile/view");
	}

}
