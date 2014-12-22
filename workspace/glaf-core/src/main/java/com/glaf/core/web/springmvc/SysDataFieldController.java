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

import com.alibaba.fastjson.*;
import com.glaf.core.base.DataRequest;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.*;
import com.glaf.core.query.SysDataItemQuery;
import com.glaf.core.security.*;
import com.glaf.core.util.*;
import com.glaf.core.domain.*;
import com.glaf.core.service.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/system/datafield")
@RequestMapping("/system/datafield")
public class SysDataFieldController {
	protected static final Log logger = LogFactory
			.getLog(SysDataFieldController.class);

	protected ISysDataTableService sysDataTableService;

	protected ISysDataItemService sysDataItemService;

	public SysDataFieldController() {

	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String id = RequestUtils.getString(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					SysDataField sysDataField = sysDataTableService
							.getDataFieldById(String.valueOf(x));

					if (sysDataField != null
							&& (StringUtils.equals(sysDataField.getCreateBy(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						sysDataTableService.deleteDataFieldById(x);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			SysDataField sysDataField = sysDataTableService
					.getDataFieldById(String.valueOf(id));

			if (sysDataField != null
					&& (StringUtils.equals(sysDataField.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				sysDataTableService.deleteDataFieldById(id);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		SysDataField sysDataField = sysDataTableService
				.getDataFieldById(request.getParameter("id"));
		if (sysDataField != null) {
			request.setAttribute("sysDataField", sysDataField);
		}

		SysDataTable sysDataTable = null;
		if (request.getParameter("datatableId") != null) {
			sysDataTable = sysDataTableService.getDataTableById(request
					.getParameter("datatableId"));
			if (sysDataTable != null) {
				request.setAttribute("sysDataTable", sysDataTable);
			}
		}
		
		SysDataItemQuery query = new SysDataItemQuery();
		query.locked(0);
		List<SysDataItem> dataItems = sysDataItemService.list(query);
		request.setAttribute("dataItems", dataItems);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("sysDataField.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/datafield/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		String tableName = request.getParameter("tableName");
		SysDataTable sysDataTable = null;
		if (request.getParameter("datatableId") != null) {
			sysDataTable = sysDataTableService.getDataTableById(request
					.getParameter("datatableId"));
			if (sysDataTable != null) {
				tableName = sysDataTable.getTablename();
			}
		}

		JSONObject result = new JSONObject();
		int total = sysDataTableService.getDataFieldCountByTablename(tableName);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", 0);
			result.put("startIndex", 0);
			result.put("limit", 1000);
			result.put("pageSize", 1000);

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<SysDataField> list = sysDataTableService
					.getDataFieldsByTablename(tableName);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				int start = 0;

				for (SysDataField sysDataField : list) {
					JSONObject rowJSON = sysDataField.toJsonObject();
					rowJSON.put("id", sysDataField.getId());
					rowJSON.put("datafieldId", sysDataField.getId());
					rowJSON.put("startIndex", ++start);
					if (userMap.get(sysDataField.getCreateBy()) != null) {
						rowJSON.put("createByName",
								userMap.get(sysDataField.getCreateBy())
										.getName());
					}
					if (userMap.get(sysDataField.getUpdateBy()) != null) {
						rowJSON.put("updateByName",
								userMap.get(sysDataField.getUpdateBy())
										.getName());
					}
					rowsJSON.add(rowJSON);
				}
				result.put("rows", rowsJSON);
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

		SysDataTable sysDataTable = null;
		if (request.getParameter("datatableId") != null) {
			sysDataTable = sysDataTableService.getDataTableById(request
					.getParameter("datatableId"));
			if (sysDataTable != null) {
				request.setAttribute("sysDataTable", sysDataTable);
			}
		}

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

		return new ModelAndView("/modules/sys/datafield/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("sysDataField.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/sys/datafield/query", modelMap);
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		String tableName = request.getParameter("tableName");
		SysDataTable sysDataTable = null;
		if (request.getParameter("datatableId") != null) {
			sysDataTable = sysDataTableService.getDataTableById(request
					.getParameter("datatableId"));
			if (sysDataTable != null) {
				tableName = sysDataTable.getTablename();
			}
		}
		JSONObject result = new JSONObject();
		int total = sysDataTableService.getDataFieldCountByTablename(tableName);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", 0);
			result.put("startIndex", 0);
			result.put("limit", 1000);
			result.put("pageSize", 1000);

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<SysDataField> list = sysDataTableService
					.getDataFieldsByTablename(tableName);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				int start = 0;

				for (SysDataField sysDataField : list) {
					JSONObject rowJSON = sysDataField.toJsonObject();
					rowJSON.put("id", sysDataField.getId());
					rowJSON.put("datafieldId", sysDataField.getId());
					rowJSON.put("startIndex", ++start);
					if (userMap.get(sysDataField.getCreateBy()) != null) {
						rowJSON.put("createByName",
								userMap.get(sysDataField.getCreateBy())
										.getName());
					}
					if (userMap.get(sysDataField.getUpdateBy()) != null) {
						rowJSON.put("updateByName",
								userMap.get(sysDataField.getUpdateBy())
										.getName());
					}
					rowsJSON.add(rowJSON);
				}
				result.put("rows", rowsJSON);
			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		SysDataField sysDataField = new SysDataField();
		Tools.populate(sysDataField, params);

		sysDataField.setServiceKey(request.getParameter("serviceKey"));
		sysDataField.setTablename(request.getParameter("tablename"));
		sysDataField.setColumnName(request.getParameter("columnName"));
		sysDataField.setName(request.getParameter("name"));
		sysDataField.setTitle(request.getParameter("title"));
		sysDataField.setFrmType(request.getParameter("frmType"));
		sysDataField.setDataType(request.getParameter("dataType"));
		sysDataField.setLength(RequestUtils.getInt(request, "length"));
		sysDataField.setListWeigth(RequestUtils.getInt(request, "listWeigth"));
		sysDataField.setPrimaryKey(request.getParameter("primaryKey"));
		sysDataField.setSystemFlag(request.getParameter("systemFlag"));
		sysDataField.setInputType(request.getParameter("inputType"));
		sysDataField
				.setDisplayType(RequestUtils.getInt(request, "displayType"));
		sysDataField.setImportType(RequestUtils.getInt(request, "importType"));
		sysDataField.setFormatter(request.getParameter("formatter"));
		sysDataField.setSearchable(request.getParameter("searchable"));
		sysDataField.setEditable(request.getParameter("editable"));
		sysDataField.setUpdatable(request.getParameter("updatable"));
		sysDataField.setFormula(request.getParameter("formula"));
		sysDataField.setMask(request.getParameter("mask"));
		sysDataField.setQueryId(request.getParameter("queryId"));
		sysDataField.setValueField(request.getParameter("valueField"));
		sysDataField.setTextField(request.getParameter("textField"));
		sysDataField.setValidType(request.getParameter("validType"));
		sysDataField.setRequired(request.getParameter("required"));
		sysDataField.setInitValue(request.getParameter("initValue"));
		sysDataField.setDefaultValue(request.getParameter("defaultValue"));
		sysDataField
				.setValueExpression(request.getParameter("valueExpression"));
		sysDataField.setMaxValue(RequestUtils.getDouble(request, "maxValue"));
		sysDataField.setMinValue(RequestUtils.getDouble(request, "minValue"));
		sysDataField.setStepValue(RequestUtils.getDouble(request, "stepValue"));
		sysDataField.setPlaceholder(request.getParameter("placeholder"));
		sysDataField.setSortable(request.getParameter("sortable"));
		sysDataField.setOrdinal(RequestUtils.getInt(request, "ordinal"));
		sysDataField.setDataItemId(RequestUtils.getLong(request, "dataItemId"));
		sysDataField.setCreateBy(actorId);

		sysDataTableService.saveDataField(sysDataField);

		return this.list(request, modelMap);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody SysDataField saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		SysDataField sysDataField = new SysDataField();
		try {
			Tools.populate(sysDataField, model);
			sysDataField.setServiceKey(ParamUtils
					.getString(model, "serviceKey"));
			sysDataField.setTablename(ParamUtils.getString(model, "tablename"));
			sysDataField.setColumnName(ParamUtils
					.getString(model, "columnName"));
			sysDataField.setName(ParamUtils.getString(model, "name"));
			sysDataField.setTitle(ParamUtils.getString(model, "title"));
			sysDataField.setFrmType(ParamUtils.getString(model, "frmType"));
			sysDataField.setDataType(ParamUtils.getString(model, "dataType"));
			sysDataField.setLength(ParamUtils.getInt(model, "length"));
			sysDataField.setListWeigth(ParamUtils.getInt(model, "listWeigth"));
			sysDataField.setPrimaryKey(ParamUtils
					.getString(model, "primaryKey"));
			sysDataField.setSystemFlag(ParamUtils
					.getString(model, "systemFlag"));
			sysDataField.setInputType(ParamUtils.getString(model, "inputType"));
			sysDataField
					.setDisplayType(ParamUtils.getInt(model, "displayType"));
			sysDataField.setImportType(ParamUtils.getInt(model, "importType"));
			sysDataField.setFormatter(ParamUtils.getString(model, "formatter"));
			sysDataField.setSearchable(ParamUtils
					.getString(model, "searchable"));
			sysDataField.setEditable(ParamUtils.getString(model, "editable"));
			sysDataField.setUpdatable(ParamUtils.getString(model, "updatable"));
			sysDataField.setFormula(ParamUtils.getString(model, "formula"));
			sysDataField.setMask(ParamUtils.getString(model, "mask"));
			sysDataField.setQueryId(ParamUtils.getString(model, "queryId"));
			sysDataField.setValueField(ParamUtils
					.getString(model, "valueField"));
			sysDataField.setTextField(ParamUtils.getString(model, "textField"));
			sysDataField.setValidType(ParamUtils.getString(model, "validType"));
			sysDataField.setRequired(ParamUtils.getString(model, "required"));
			sysDataField.setInitValue(ParamUtils.getString(model, "initValue"));
			sysDataField.setDefaultValue(ParamUtils.getString(model,
					"defaultValue"));
			sysDataField.setValueExpression(ParamUtils.getString(model,
					"valueExpression"));
			sysDataField.setMaxValue(RequestUtils
					.getDouble(request, "maxValue"));
			sysDataField.setMinValue(RequestUtils
					.getDouble(request, "minValue"));
			sysDataField.setStepValue(RequestUtils.getDouble(request,
					"stepValue"));
			sysDataField.setPlaceholder(request.getParameter("placeholder"));
			sysDataField.setSortable(ParamUtils.getString(model, "sortable"));
			sysDataField.setOrdinal(ParamUtils.getInt(model, "ordinal"));
			sysDataField.setDataItemId(RequestUtils.getLong(request,
					"dataItemId"));
			sysDataField.setUpdateBy(actorId);
			sysDataField.setCreateBy(actorId);
			this.sysDataTableService.saveDataField(sysDataField);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return sysDataField;
	}

	@ResponseBody
	@RequestMapping("/saveSysDataField")
	public byte[] saveSysDataField(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug(params);
		SysDataField sysDataField = new SysDataField();
		try {
			Tools.populate(sysDataField, params);
			sysDataField.setServiceKey(request.getParameter("serviceKey"));
			sysDataField.setTablename(request.getParameter("tablename"));
			sysDataField.setColumnName(request.getParameter("columnName"));
			sysDataField.setName(request.getParameter("name"));
			sysDataField.setTitle(request.getParameter("title"));
			sysDataField.setFrmType(request.getParameter("frmType"));
			sysDataField.setDataType(request.getParameter("dataType"));
			sysDataField.setLength(RequestUtils.getInt(request, "length"));
			sysDataField.setListWeigth(RequestUtils.getInt(request,
					"listWeigth"));
			sysDataField.setPrimaryKey(request.getParameter("primaryKey"));
			sysDataField.setSystemFlag(request.getParameter("systemFlag"));
			sysDataField.setInputType(request.getParameter("inputType"));
			sysDataField.setDisplayType(RequestUtils.getInt(request,
					"displayType"));
			sysDataField.setImportType(RequestUtils.getInt(request,
					"importType"));
			sysDataField.setFormatter(request.getParameter("formatter"));
			sysDataField.setSearchable(request.getParameter("searchable"));
			sysDataField.setEditable(request.getParameter("editable"));
			sysDataField.setUpdatable(request.getParameter("updatable"));
			sysDataField.setFormula(request.getParameter("formula"));
			sysDataField.setMask(request.getParameter("mask"));
			sysDataField.setQueryId(request.getParameter("queryId"));
			sysDataField.setValueField(request.getParameter("valueField"));
			sysDataField.setTextField(request.getParameter("textField"));
			sysDataField.setValidType(request.getParameter("validType"));
			sysDataField.setRequired(request.getParameter("required"));
			sysDataField.setInitValue(request.getParameter("initValue"));
			sysDataField.setDefaultValue(request.getParameter("defaultValue"));
			sysDataField.setValueExpression(request
					.getParameter("valueExpression"));
			sysDataField.setMaxValue(RequestUtils
					.getDouble(request, "maxValue"));
			sysDataField.setMinValue(RequestUtils
					.getDouble(request, "minValue"));
			sysDataField.setStepValue(RequestUtils.getDouble(request,
					"stepValue"));
			sysDataField.setPlaceholder(request.getParameter("placeholder"));
			sysDataField.setSortable(request.getParameter("sortable"));
			sysDataField.setOrdinal(RequestUtils.getInt(request, "ordinal"));
			sysDataField.setDataItemId(RequestUtils.getLong(request,
					"dataItemId"));
			sysDataField.setUpdateBy(actorId);
			sysDataField.setCreateBy(actorId);
			this.sysDataTableService.saveDataField(sysDataField);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setSysDataTableService(ISysDataTableService sysDataTableService) {
		this.sysDataTableService = sysDataTableService;
	}

	@javax.annotation.Resource
	public void setSysDataItemService(ISysDataItemService sysDataItemService) {
		this.sysDataItemService = sysDataItemService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		SysDataField sysDataField = sysDataTableService
				.getDataFieldById(request.getParameter("id"));

		Tools.populate(sysDataField, params);

		sysDataField.setServiceKey(request.getParameter("serviceKey"));
		sysDataField.setTablename(request.getParameter("tablename"));
		sysDataField.setColumnName(request.getParameter("columnName"));
		sysDataField.setName(request.getParameter("name"));
		sysDataField.setTitle(request.getParameter("title"));
		sysDataField.setFrmType(request.getParameter("frmType"));
		sysDataField.setDataType(request.getParameter("dataType"));
		sysDataField.setLength(RequestUtils.getInt(request, "length"));
		sysDataField.setListWeigth(RequestUtils.getInt(request, "listWeigth"));
		sysDataField.setPrimaryKey(request.getParameter("primaryKey"));
		sysDataField.setSystemFlag(request.getParameter("systemFlag"));
		sysDataField.setInputType(request.getParameter("inputType"));
		sysDataField
				.setDisplayType(RequestUtils.getInt(request, "displayType"));
		sysDataField.setImportType(RequestUtils.getInt(request, "importType"));
		sysDataField.setFormatter(request.getParameter("formatter"));
		sysDataField.setSearchable(request.getParameter("searchable"));
		sysDataField.setEditable(request.getParameter("editable"));
		sysDataField.setUpdatable(request.getParameter("updatable"));
		sysDataField.setFormula(request.getParameter("formula"));
		sysDataField.setMask(request.getParameter("mask"));
		sysDataField.setQueryId(request.getParameter("queryId"));
		sysDataField.setValueField(request.getParameter("valueField"));
		sysDataField.setTextField(request.getParameter("textField"));
		sysDataField.setValidType(request.getParameter("validType"));
		sysDataField.setRequired(request.getParameter("required"));
		sysDataField.setInitValue(request.getParameter("initValue"));
		sysDataField.setDefaultValue(request.getParameter("defaultValue"));
		sysDataField
				.setValueExpression(request.getParameter("valueExpression"));
		sysDataField.setMaxValue(RequestUtils.getDouble(request, "maxValue"));
		sysDataField.setMinValue(RequestUtils.getDouble(request, "minValue"));
		sysDataField.setStepValue(RequestUtils.getDouble(request, "stepValue"));
		sysDataField.setPlaceholder(request.getParameter("placeholder"));
		sysDataField.setSortable(request.getParameter("sortable"));
		sysDataField.setOrdinal(RequestUtils.getInt(request, "ordinal"));
		sysDataField.setDataItemId(RequestUtils.getLong(request, "dataItemId"));
		sysDataField.setUpdateBy(user.getActorId());
		sysDataTableService.saveDataField(sysDataField);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		SysDataField sysDataField = sysDataTableService
				.getDataFieldById(request.getParameter("id"));
		request.setAttribute("sysDataField", sysDataField);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("sysDataField.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/modules/sys/datafield/view");
	}

}
