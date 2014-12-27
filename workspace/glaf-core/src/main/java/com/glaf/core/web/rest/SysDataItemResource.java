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
import com.glaf.core.domain.SysDataItem;
import com.glaf.core.domain.util.SysDataItemDomainFactory;
import com.glaf.core.query.SysDataItemQuery;
import com.glaf.core.service.ISysDataItemService;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/system/dataitem")
public class SysDataItemResource {
	protected static final Log logger = LogFactory
			.getLog(SysDataItemResource.class);

	protected ISysDataItemService sysDataItemService;

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysDataItemQuery query = new SysDataItemQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		SysDataItemDomainFactory.processDataRequest(dataRequest);

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
		int total = sysDataItemService
				.getSysDataItemCountByQueryCriteria(query);
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
			List<SysDataItem> list = sysDataItemService
					.getSysDataItemsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SysDataItem sysDataItem : list) {
					JSONObject rowJSON = sysDataItem.toJsonObject();
					rowJSON.put("id", sysDataItem.getId());
					rowJSON.put("sysDataItemId", sysDataItem.getId());
					rowJSON.put("startIndex", ++start);
					if (userMap.get(sysDataItem.getCreateBy()) != null) {
						result.put("createByName",
								userMap.get(sysDataItem.getCreateBy())
										.getName());
					}
					if (userMap.get(sysDataItem.getUpdateBy()) != null) {
						result.put("updateByName",
								userMap.get(sysDataItem.getUpdateBy())
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
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request)
			throws IOException {
		sysDataItemService.deleteById(RequestUtils.getLong(request, "id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysDataItemQuery query = new SysDataItemQuery();
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
		int total = sysDataItemService
				.getSysDataItemCountByQueryCriteria(query);
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
			List<SysDataItem> list = sysDataItemService
					.getSysDataItemsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SysDataItem sysDataItem : list) {
					JSONObject rowJSON = sysDataItem.toJsonObject();
					rowJSON.put("id", sysDataItem.getId());
					rowJSON.put("sysDataItemId", sysDataItem.getId());
					rowJSON.put("startIndex", ++start);
					if (userMap.get(sysDataItem.getCreateBy()) != null) {
						result.put("createByName",
								userMap.get(sysDataItem.getCreateBy())
										.getName());
					}
					if (userMap.get(sysDataItem.getUpdateBy()) != null) {
						result.put("updateByName",
								userMap.get(sysDataItem.getUpdateBy())
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
	@Path("/saveSysDataItem")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveSysDataItem(@Context HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysDataItem sysDataItem = new SysDataItem();
		try {
			Tools.populate(sysDataItem, params);

			sysDataItem.setName(request.getParameter("name"));
			sysDataItem.setTitle(request.getParameter("title"));
			sysDataItem.setQueryId(request.getParameter("queryId"));
			sysDataItem.setQuerySQL(request.getParameter("querySQL"));
			sysDataItem.setParameter(request.getParameter("parameter"));
			sysDataItem.setTextField(request.getParameter("textField"));
			sysDataItem.setValueField(request.getParameter("valueField"));
			sysDataItem.setTreeIdField(request.getParameter("treeIdField"));
			sysDataItem.setTreeParentIdField(request
					.getParameter("treeParentIdField"));
			sysDataItem.setTreeTreeIdField(request
					.getParameter("treeTreeIdField"));
			sysDataItem.setTreeNameField(request.getParameter("treeNameField"));
			sysDataItem.setTreeListNoField(request
					.getParameter("treeListNoField"));
			sysDataItem.setUrl(request.getParameter("url"));
			sysDataItem.setCacheFlag(request.getParameter("cacheFlag"));
			sysDataItem.setCreateBy(actorId);
			sysDataItem.setUpdateBy(actorId);
			sysDataItem.setLocked(RequestUtils.getInt(request, "locked"));

			if (!DBUtils.isLegalQuerySql(sysDataItem.getQuerySQL())) {
				throw new RuntimeException(" SQL statement illegal ");
			}

			this.sysDataItemService.save(sysDataItem);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setSysDataItemService(ISysDataItemService sysDataItemService) {
		this.sysDataItemService = sysDataItemService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		SysDataItem sysDataItem = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			sysDataItem = sysDataItemService.getSysDataItemById(RequestUtils
					.getLong(request, "id"));
		}
		JSONObject result = new JSONObject();
		if (sysDataItem != null) {
			result = sysDataItem.toJsonObject();
			Map<String, User> userMap = IdentityFactory.getUserMap();
			if (userMap.get(sysDataItem.getCreateBy()) != null) {
				result.put("createByName",
						userMap.get(sysDataItem.getCreateBy()).getName());
			}
			if (userMap.get(sysDataItem.getUpdateBy()) != null) {
				result.put("updateByName",
						userMap.get(sysDataItem.getUpdateBy()).getName());
			}
			result.put("id", sysDataItem.getId());
			result.put("sysDataItemId", sysDataItem.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
