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

package com.glaf.dts.web.rest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glaf.core.domain.*;
import com.glaf.core.query.QueryDefinitionQuery;
import com.glaf.core.query.TableDefinitionQuery;
import com.glaf.core.service.IQueryDefinitionService;
import com.glaf.core.service.ITableDefinitionService;

import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.Tools;
import com.glaf.dts.transform.MxTransformManager;
import com.glaf.dts.util.Constants;

@Controller("/rs/dts/query")
@Path("/rs/dts/query")
public class MxQueryResource {
	private static Log logger = LogFactory.getLog(MxQueryResource.class);

	@Resource
	protected ITableDefinitionService tableDefinitionService;

	@Resource
	protected IQueryDefinitionService queryDefinitionService;

	@POST
	@Path("/delete")
	public void delete(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String queryId = request.getParameter("queryId");
		if (queryDefinitionService.hasChildren(queryId)) {
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		}
		queryDefinitionService.deleteById(queryId);
	}

	@POST
	@Path("/delete/{queryId}")
	public void delete(@PathParam("queryId") String queryId,
			@Context UriInfo uriInfo) {
		if (queryDefinitionService.hasChildren(queryId)) {
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		}
		queryDefinitionService.deleteById(queryId);
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] list(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		QueryDefinitionQuery query = new QueryDefinitionQuery();
		Tools.populate(query, params);
		List<QueryDefinition> queries = queryDefinitionService.list(query);
		ObjectNode responseJSON = new ObjectMapper().createObjectNode();

		ArrayNode arrayJSON = new ObjectMapper().createArrayNode();

		for (QueryDefinition q : queries) {
			ObjectNode json = q.toObjectNode();
			arrayJSON.add(json);
		}

		responseJSON.put("data", arrayJSON);
		responseJSON.put("rows", arrayJSON);
		responseJSON.put("total", queries.size());
		responseJSON.put("totalCount", queries.size());
		responseJSON.put("totalRecords", queries.size());

		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}

	@POST
	@Path("/saveQuery")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveQuery(@Context HttpServletRequest request) {
		String queryId = request.getParameter("queryId");
		QueryDefinition query = null;

		if (StringUtils.isNotEmpty(queryId)) {
			query = queryDefinitionService.getQueryDefinition(queryId);
		}

		if (query == null) {
			query = new QueryDefinition();
		}

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Tools.populate(query, params);

		logger.debug("sql:" + query.getSql());

		logger.debug("targetTableName:" + query.getTargetTableName());

		logger.debug("parentId:" + query.getParentId());

		MxTransformManager manager = new MxTransformManager();
		if (StringUtils.isNotEmpty(query.getSql())) {

			if (!DBUtils.isLegalQuerySql(query.getSql())) {
				return ResponseUtils.responseJsonResult(false,
						"SQL查询非法，包含不合法指令！");
			}
			if (StringUtils.containsIgnoreCase(query.getSql(), "UserInfo")
					|| StringUtils
							.containsIgnoreCase(query.getSql(), "MX_USER")) {
				return ResponseUtils.responseJsonResult(false,
						"SQL查询非法，不允许访问用户信息表！");
			}

			if (StringUtils.isNotEmpty(query.getTargetTableName())) {
				String tableName = query.getTargetTableName();
				tableName = tableName.toLowerCase();
				if (tableName.length() > 26) {
					return ResponseUtils.responseJsonResult(false,
							"目标表长度不能超过26个字符！");
				}
				if (StringUtils.startsWith(tableName, "mx_")
						|| StringUtils.startsWith(tableName, "sys_")
						|| StringUtils.startsWith(tableName, "jbpm_")
						|| StringUtils.startsWith(tableName, "act_")) {
					return ResponseUtils.responseJsonResult(false, "目标表不正确！");
				}
			}

			TableDefinition newTable = null;
			try {
				newTable = manager.toTableDefinition(query);
			} catch (Exception ex) {
				ex.printStackTrace();
				return ResponseUtils
						.responseJsonResult(false, "查询失败，SQL语句不正确！");
			}

			if (StringUtils.isNotEmpty(query.getTargetTableName())) {
				newTable.setTableName(query.getTargetTableName());
				TableDefinition table = tableDefinitionService
						.getTableDefinition(query.getTargetTableName());
				if (table == null) {
					table = newTable;
				} else {
					if (newTable != null && newTable.getColumns() != null) {
						for (ColumnDefinition column : newTable.getColumns()) {
							if (!table.getColumns().contains(column)) {
								table.addColumn(column);
							}
						}
					}
				}
				tableDefinitionService.save(table);
			}

			if (newTable != null && newTable.getColumns() != null) {

			}
			query.setType(Constants.DTS_TASK_TYPE);
			queryDefinitionService.save(query);
		}

		return ResponseUtils.responseJsonResult(true);
	}

	public void setQueryDefinitionService(
			IQueryDefinitionService queryDefinitionService) {
		this.queryDefinitionService = queryDefinitionService;
	}

	public void setTableDefinitionService(
			ITableDefinitionService tableDefinitionService) {
		this.tableDefinitionService = tableDefinitionService;
	}

	@GET
	@POST
	@Path("/view/{queryId}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@PathParam("queryId") String queryId,
			@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableDefinitionQuery query = new TableDefinitionQuery();
		Tools.populate(query, params);
		QueryDefinition q = queryDefinitionService.getQueryDefinition(queryId);
		ObjectNode responseJSON = q.toObjectNode();
		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}

}