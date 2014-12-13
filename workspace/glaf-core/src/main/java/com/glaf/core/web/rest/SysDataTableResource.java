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
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;
import com.glaf.core.domain.SysDataTable;
import com.glaf.core.domain.util.SysDataTableDomainFactory;
import com.glaf.core.query.SysDataTableQuery;
import com.glaf.core.service.ISysDataTableService;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/system/datatable")
public class SysDataTableResource {
	protected static final Log logger = LogFactory
			.getLog(SysDataTableResource.class);

	protected ISysDataTableService sysDataTableService;

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysDataTableQuery query = new SysDataTableQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		SysDataTableDomainFactory.processDataRequest(dataRequest);

		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "kendoui";
		}
		int start = 0;
		int limit = PageResult.DEFAULT_PAGE_SIZE;

		int pageNo = dataRequest.getPage();
		limit = dataRequest.getPageSize();

		start = (pageNo - 1) * limit;

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = sysDataTableService.getDataTableCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

			String orderName = null;
			String order = null;

			if (dataRequest.getSort() != null
					&& !dataRequest.getSort().isEmpty()) {
				SortDescriptor sort = dataRequest.getSort().get(0);
				orderName = sort.getField();
				order = sort.getDir();
				logger.debug("orderName:" + orderName);
				logger.debug("order:" + order);
			}

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortColumn(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<SysDataTable> list = sysDataTableService
					.getDataTablesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SysDataTable sysDataTable : list) {
					JSONObject rowJSON = sysDataTable.toJsonObject();
					rowJSON.put("id", sysDataTable.getId());
					rowJSON.put("datatableId", sysDataTable.getId());
					rowJSON.put("startIndex", ++start);
					if (userMap.get(sysDataTable.getCreateBy()) != null) {
						rowJSON.put("createByName",
								userMap.get(sysDataTable.getCreateBy())
										.getName());
					}
					if (userMap.get(sysDataTable.getUpdateBy()) != null) {
						rowJSON.put("updateByName",
								userMap.get(sysDataTable.getUpdateBy())
										.getName());
					}
					rowsJSON.add(rowJSON);
				}

			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysDataTableQuery query = new SysDataTableQuery();
		Tools.populate(query, params);

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
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = sysDataTableService.getDataTableCountByQueryCriteria(query);
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

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<SysDataTable> list = sysDataTableService
					.getDataTablesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SysDataTable sysDataTable : list) {
					JSONObject rowJSON = sysDataTable.toJsonObject();
					rowJSON.put("id", sysDataTable.getId());
					rowJSON.put("datatableId", sysDataTable.getId());
					rowJSON.put("startIndex", ++start);
					if (userMap.get(sysDataTable.getCreateBy()) != null) {
						rowJSON.put("createByName",
								userMap.get(sysDataTable.getCreateBy())
										.getName());
					}
					if (userMap.get(sysDataTable.getUpdateBy()) != null) {
						rowJSON.put("updateByName",
								userMap.get(sysDataTable.getUpdateBy())
										.getName());
					}
					rowsJSON.add(rowJSON);
				}

			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@POST
	@Path("/saveSysDataTable")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveSysDataTable(@Context HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysDataTable sysDataTable = new SysDataTable();
		try {
			Tools.populate(sysDataTable, params);

			sysDataTable.setServiceKey(request.getParameter("serviceKey"));
			sysDataTable.setTablename(request.getParameter("tablename"));
			sysDataTable.setTitle(request.getParameter("title"));
			sysDataTable.setType(RequestUtils.getInt(request, "type"));
			sysDataTable.setMaxUser(RequestUtils.getInt(request, "maxUser"));
			sysDataTable.setMaxSys(RequestUtils.getInt(request, "maxSys"));
			sysDataTable.setCreateBy(loginContext.getActorId());
			sysDataTable.setCreateTime(RequestUtils.getDate(request,
					"createTime"));
			sysDataTable.setUpdateTime(RequestUtils.getDate(request,
					"updateTime"));
			sysDataTable.setUpdateBy(loginContext.getActorId());
			sysDataTable.setContent(request.getParameter("content"));
			sysDataTable.setIsSubTable(request.getParameter("isSubTable"));

			this.sysDataTableService.saveDataTable(sysDataTable);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setISysDataTableService(ISysDataTableService sysDataTableService) {
		this.sysDataTableService = sysDataTableService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		SysDataTable sysDataTable = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			sysDataTable = sysDataTableService.getDataTableById(request
					.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (sysDataTable != null) {
			result = sysDataTable.toJsonObject();
			Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", sysDataTable.getId());
			result.put("datatableId", sysDataTable.getId());
			if (userMap.get(sysDataTable.getCreateBy()) != null) {
				result.put("createByName",
						userMap.get(sysDataTable.getCreateBy()).getName());
			}
			if (userMap.get(sysDataTable.getUpdateBy()) != null) {
				result.put("updateByName",
						userMap.get(sysDataTable.getUpdateBy()).getName());
			}
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
