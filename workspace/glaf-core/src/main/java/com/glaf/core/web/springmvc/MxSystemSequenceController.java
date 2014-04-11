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

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.core.config.ViewProperties;
import com.glaf.core.id.Dbid;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;

@Controller("/sys/sequence")
@RequestMapping("/sys/sequence")
public class MxSystemSequenceController {

	protected static final Log logger = LogFactory
			.getLog(MxSystemSequenceController.class);

	protected ITableDataService tableDataService;

	@RequestMapping
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		List<Dbid> rows = tableDataService.getAllDbids();
		request.setAttribute("rows", rows);

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = ViewProperties.getString("sys_sequence.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/sys/sequence/edit");
	}

	@ResponseBody
	@RequestMapping("/save")
	public byte[] save(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug("params:" + params);

		List<Dbid> rows = tableDataService.getAllDbids();
		if (rows != null && !rows.isEmpty()) {
			for (Dbid dbid : rows) {
				String value = request.getParameter(dbid.getName());
				if (StringUtils.isNotEmpty(value)
						&& StringUtils.isNumeric(value)) {
					dbid.setValue(value);
				}
			}
			tableDataService.updateAllDbids(rows);
			return ResponseUtils.responseJsonResult(true);
		}

		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping("/saveAll")
	public ModelAndView saveAll(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		List<Dbid> rows = tableDataService.getAllDbids();
		if (rows != null && !rows.isEmpty()) {
			for (Dbid dbid : rows) {
				String value = request.getParameter(dbid.getName());
				if (StringUtils.isNotEmpty(value)
						&& StringUtils.isNumeric(value)) {
					dbid.setValue(value);
				}
			}
			tableDataService.updateAllDbids(rows);
		}

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = ViewProperties.getString("sys_sequence.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return this.edit(request, modelMap);
	}

	@javax.annotation.Resource
	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

}