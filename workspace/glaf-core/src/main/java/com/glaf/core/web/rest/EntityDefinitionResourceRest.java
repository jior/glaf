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
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.domain.EntityDefinition;
import com.glaf.core.query.EntityDefinitionQuery;
import com.glaf.core.service.EntityDefinitionService;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;

@Controller
@Path("/rs/system/entity")
public class EntityDefinitionResourceRest {
	protected static final Log logger = LogFactory
			.getLog(EntityDefinitionResourceRest.class);

	protected EntityDefinitionService entityDefinitionService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("rowIds");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				entityDefinitionService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteById(@Context HttpServletRequest request)
			throws IOException {
		entityDefinitionService.deleteById(request.getParameter("rowId"));
		return ResponseUtils.responseJsonResult(true);
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EntityDefinitionQuery query = new EntityDefinitionQuery();
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
		int total = entityDefinitionService
				.getEntityDefinitionCountByQueryCriteria(query);
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

			List<EntityDefinition> list = entityDefinitionService
					.getEntityDefinitionsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (EntityDefinition entityDefinition : list) {
					JSONObject rowJSON = entityDefinition.toJsonObject();
					rowJSON.put("id", entityDefinition.getId());
					rowJSON.put("entityDefinitionId", entityDefinition.getId());
					rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
				}

			}
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@POST
	@Path("/saveEntityDefinition")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveEntityDefinition(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EntityDefinition entityDefinition = new EntityDefinition();
		try {
			Tools.populate(entityDefinition, params);

			entityDefinition.setName(request.getParameter("name"));
			entityDefinition.setType(request.getParameter("type"));
			entityDefinition.setTitle(request.getParameter("title"));
			entityDefinition.setTablename(request.getParameter("tablename"));
			entityDefinition.setParseType(request.getParameter("parseType"));
			entityDefinition.setPrimaryKey(request.getParameter("primaryKey"));
			entityDefinition.setFilePrefix(request.getParameter("filePrefix"));
			entityDefinition.setStopWord(request.getParameter("stopWord"));
			entityDefinition.setJavaType(request.getParameter("javaType"));
			entityDefinition.setAggregationKeys(request
					.getParameter("aggregationKeys"));
			entityDefinition.setStartRow(RequestUtils.getInt(request,
					"startRow"));
			entityDefinition.setInsertOnly(request.getParameter("insertOnly"));
			entityDefinition
					.setFileContent(request.getParameter("fileContent"));
			entityDefinition.setCreateDate(RequestUtils.getDate(request,
					"createDate"));
			entityDefinition.setCreateBy(request.getParameter("createBy"));
			entityDefinition.setUpdateBy(request.getParameter("updateBy"));
			entityDefinition.setUpdateDate(RequestUtils.getDate(request,
					"updateDate"));

			this.entityDefinitionService.save(entityDefinition);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setEntityDefinitionService(
			EntityDefinitionService entityDefinitionService) {
		this.entityDefinitionService = entityDefinitionService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		EntityDefinition entityDefinition = null;
		if (StringUtils.isNotEmpty(request.getParameter("rowId"))) {
			entityDefinition = entityDefinitionService
					.getEntityDefinition(request.getParameter("rowId"));
		}
		JSONObject result = new JSONObject();
		if (entityDefinition != null) {
			result = entityDefinition.toJsonObject();
			result.put("id", entityDefinition.getId());
			result.put("entityDefinitionId", entityDefinition.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
