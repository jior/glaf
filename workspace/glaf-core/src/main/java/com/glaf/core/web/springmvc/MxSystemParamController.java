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

package com.glaf.core.web.springmvc;

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
import com.glaf.core.domain.InputDefinition;
import com.glaf.core.domain.SystemParam;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.ISystemParamService;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.Tools;

@Controller("/sys/param")
@RequestMapping("/sys/param")
public class MxSystemParamController {
	protected static final Log logger = LogFactory
			.getLog(MxSystemParamController.class);

	protected ISystemParamService systemParamService;

	public MxSystemParamController() {

	}

	@RequestMapping("/delete")
	public ModelAndView delete(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String systemParamId = ParamUtils.getString(params, "systemParamId");
		String systemParamIds = request.getParameter("systemParamIds");
		if (StringUtils.isNotEmpty(systemParamIds)) {
			StringTokenizer token = new StringTokenizer(systemParamIds, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					SystemParam systemParam = systemParamService
							.getSystemParam(x);
					if (systemParam != null) {
						// systemParam.setDeleteFlag(1);
						systemParamService.save(systemParam);
					}
				}
			}
		} else if (StringUtils.isNotEmpty(systemParamId)) {
			SystemParam systemParam = systemParamService
					.getSystemParam(systemParamId);
			if (systemParam != null) {
				// systemParam.setDeleteFlag(1);
				systemParamService.save(systemParam);
			}
		}

		return this.list(request, modelMap);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String serviceKey = request.getParameter("serviceKey");
		String businessKey = request.getParameter("businessKey");

		systemParamService.createSystemParams(serviceKey, businessKey);

		List<InputDefinition> rows = systemParamService
				.getInputDefinitions(serviceKey);
		request.setAttribute("rows", rows);

		List<SystemParam> params = systemParamService.getSystemParams(
				serviceKey, businessKey);
		request.setAttribute("params", params);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("system_param.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/param/edit", modelMap);
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

		String x_view = ViewProperties.getString("system_param.list");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/param/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("system_param.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/sys/param/query", modelMap);
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
		SystemParam systemParam = new SystemParam();
		Tools.populate(systemParam, params);
		// systemParam.setCreateBy(actorId);

		systemParamService.save(systemParam);

		return this.list(request, modelMap);
	}

	@javax.annotation.Resource
	public void setSystemParamService(ISystemParamService systemParamService) {
		this.systemParamService = systemParamService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");
		String systemParamId = ParamUtils.getString(params, "systemParamId");
		SystemParam systemParam = null;
		if (StringUtils.isNotEmpty(systemParamId)) {
			systemParam = systemParamService.getSystemParam(systemParamId);
		}

		if (systemParam != null) {

			Tools.populate(systemParam, params);
			systemParamService.save(systemParam);

		}

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String systemParamId = ParamUtils.getString(params, "systemParamId");
		SystemParam systemParam = null;
		if (StringUtils.isNotEmpty(systemParamId)) {
			systemParam = systemParamService.getSystemParam(systemParamId);
			request.setAttribute("systemParam", systemParam);

		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("system_param.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/modules/sys/param/view");
	}

}
