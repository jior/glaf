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

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.SysDataField;
import com.glaf.core.domain.SysDataItem;
import com.glaf.core.domain.SysDataTable;
import com.glaf.core.service.ISysDataItemService;
import com.glaf.core.service.ISysDataTableService;
import com.glaf.core.util.RequestUtils;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/data/table")
@RequestMapping("/data/table")
public class DataTableController {
	protected static final Log logger = LogFactory
			.getLog(DataTableController.class);

	protected ISysDataTableService sysDataTableService;

	protected ISysDataItemService sysDataItemService;

	public DataTableController() {

	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String tableName = request.getParameter("tableName");
		String businessKey = request.getParameter("id");
		SysDataTable sysDataTable = null;

		if (tableName != null) {
			if (businessKey != null) {
				sysDataTable = sysDataTableService.getDataTableWithData(
						tableName, businessKey);
				if (sysDataTable != null) {
					Collections.sort(sysDataTable.getFields());
					for (SysDataField field : sysDataTable.getFields()) {
						if (field.getDataItemId() != null
								&& field.getDataItemId() > 0) {
							SysDataItem item = sysDataItemService
									.getSysDataItemById(field.getDataItemId());
							field.setDataItem(item);
							logger.debug(item.getTextField()+"->"+item.getValueField());
						}
					}
					request.setAttribute("sysDataTable", sysDataTable);
				}
			} else {
				sysDataTable = sysDataTableService
						.getDataTableByName(tableName);
				if (sysDataTable != null) {
					Collections.sort(sysDataTable.getFields());
					for (SysDataField field : sysDataTable.getFields()) {
						if (field.getDataItemId() != null
								&& field.getDataItemId() > 0) {
							SysDataItem item = sysDataItemService
									.getSysDataItemById(field.getDataItemId());
							field.setDataItem(item);
							logger.debug(item.getTextField()+"->"+item.getValueField());
						}
					}

					request.setAttribute("sysDataTable", sysDataTable);
				}
			}
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("dataTable.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/datatable/data_edit", modelMap);
	}

	@RequestMapping
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		SysDataTable sysDataTable = sysDataTableService
				.getDataTableById(request.getParameter("datatableId"));
		if (sysDataTable != null) {
			Collections.sort(sysDataTable.getFields());
			request.setAttribute("sysDataTable", sysDataTable);
			StringBuffer buffer = new StringBuffer();
			for (SysDataField field : sysDataTable.getFields()) {
				buffer.append("\n                      ");
				buffer.append("\"").append(field.getName()).append("\"")
						.append(":{");
				if (StringUtils
						.equalsIgnoreCase(field.getDataType(), "Integer")) {
					buffer.append("\"type\": \"number\"");
				} else if (StringUtils.equalsIgnoreCase(field.getDataType(),
						"Long")) {
					buffer.append("\"type\": \"number\"");
				} else if (StringUtils.equalsIgnoreCase(field.getDataType(),
						"Double")) {
					buffer.append("\"type\": \"number\"");
				} else if (StringUtils.equalsIgnoreCase(field.getDataType(),
						"Date")) {
					buffer.append("\"type\": \"date\"");
					buffer.append(",\"format\": \"{0: yyyy-MM-dd}\"");
				} else {
					buffer.append("\"type\": \"string\"");
				}
				buffer.append("},");
			}
			buffer.append("\n\"                      startIndex\": {").append(
					"\"type\": \"number\"}");
			request.setAttribute("fields_buffer", buffer.toString());
		}

		String requestURI = request.getRequestURI();
		if (request.getQueryString() != null) {
			request.setAttribute(
					"fromUrl",
					RequestUtils.encodeURL(requestURI + "?"
							+ request.getQueryString()));
		} else {
			request.setAttribute("fromUrl", RequestUtils.encodeURL(requestURI));
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/modules/sys/datatable/data_list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("dataTable.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/sys/datatable/data_query", modelMap);
	}

	@javax.annotation.Resource
	public void setSysDataTableService(ISysDataTableService sysDataTableService) {
		this.sysDataTableService = sysDataTableService;
	}

	@javax.annotation.Resource
	public void setSysDataItemService(ISysDataItemService sysDataItemService) {
		this.sysDataItemService = sysDataItemService;
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		SysDataTable sysDataTable = sysDataTableService
				.getDataTableById(request.getParameter("datatableId"));
		request.setAttribute("sysDataTable", sysDataTable);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("dataTable.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/modules/sys/datatable/data_view");
	}

}
