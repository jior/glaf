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

package com.glaf.core.web.rest;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.*;
import com.glaf.core.base.DataRequest;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;
import com.glaf.core.domain.SysDataField;
import com.glaf.core.service.ISysDataTableService;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/system/datafield")
public class SysDataFieldResource {
	protected static final Log logger = LogFactory
			.getLog(SysDataFieldResource.class);

	protected ISysDataTableService sysDataTableService;

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		String tableName = request.getParameter("tableName");
		JSONObject result = new JSONObject();
		int total = sysDataTableService.getDataFieldCountByTable(tableName);
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
					.getDataFieldsByTable(tableName);

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

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request)
			throws IOException {
		sysDataTableService.deleteDataField(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		String tableName = request.getParameter("tableName");
		JSONObject result = new JSONObject();
		int total = sysDataTableService.getDataFieldCountByTable(tableName);
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
					.getDataFieldsByTable(tableName);

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

	@POST
	@Path("/saveSysDataField")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveSysDataField(@Context HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
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
			sysDataField.setSortable(request.getParameter("sortable"));
			sysDataField.setOrdinal(RequestUtils.getInt(request, "ordinal"));
			sysDataField.setCreateTime(RequestUtils.getDate(request,
					"createTime"));
			sysDataField.setCreateBy(loginContext.getActorId());
			sysDataField.setUpdateTime(RequestUtils.getDate(request,
					"updateTime"));
			sysDataField.setUpdateBy(loginContext.getActorId());

			this.sysDataTableService.saveDataField(sysDataField);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setSysDataTableService(ISysDataTableService sysDataTableService) {
		this.sysDataTableService = sysDataTableService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		SysDataField sysDataField = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			sysDataField = sysDataTableService.getDataField(request
					.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (sysDataField != null) {
			result = sysDataField.toJsonObject();
			Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", sysDataField.getId());
			result.put("datafieldId", sysDataField.getId());
			if (userMap.get(sysDataField.getCreateBy()) != null) {
				result.put("createByName",
						userMap.get(sysDataField.getCreateBy()).getName());
			}
			if (userMap.get(sysDataField.getUpdateBy()) != null) {
				result.put("updateByName",
						userMap.get(sysDataField.getUpdateBy()).getName());
			}
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
