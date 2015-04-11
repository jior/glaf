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
import com.glaf.core.domain.Database;
import com.glaf.core.query.DatabaseQuery;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;

/**
 * 
 * Rest响应类
 * 
 */

@Controller("/rs/sys/database")
@Path("/rs/sys/database")
public class DatabaseResourceRest {
	protected static final Log logger = LogFactory
			.getLog(DatabaseResourceRest.class);

	protected IDatabaseService databaseService;

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteById(@Context HttpServletRequest request)
			throws IOException {
		long databaseId = RequestUtils.getLong(request, "id");
		if (databaseId > 0) {
			databaseService.deleteById(databaseId);
			// sqlResultService.deleteByDatabaseId(databaseId);
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DatabaseQuery query = new DatabaseQuery();
		Tools.populate(query, params);

		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);

		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
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
		int total = databaseService.getDatabaseCountByQueryCriteria(query);
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

			List<Database> list = databaseService.getDatabasesByQueryCriteria(
					start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Database repository : list) {
					JSONObject rowJSON = repository.toJsonObject();
					rowJSON.put("id", repository.getId());
					rowJSON.put("databaseId", repository.getId());
					rowJSON.put("startIndex", ++start);
					rowJSON.remove("key");
					rowJSON.remove("user");
					rowJSON.remove("password");
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
	@Path("/saveDB")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveDB(@Context HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Database database = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			database = databaseService.getDatabaseById(RequestUtils.getLong(
					request, "id"));
		}
		if (database == null) {
			database = new Database();
			database.setCreateBy(loginContext.getActorId());
		}

		String user = request.getParameter("user");
		String password = request.getParameter("password");
		database.setUser(user);
		database.setPassword(password);
		database.setTitle(request.getParameter("title"));
		database.setCode(request.getParameter("code"));
		database.setNodeId(RequestUtils.getLong(request, "nodeId"));
		database.setHost(request.getParameter("host"));
		database.setPort(RequestUtils.getInt(request, "port"));
		database.setName(request.getParameter("name"));
		database.setType(request.getParameter("type"));
		database.setLevel(RequestUtils.getInt(request, "level"));
		database.setPriority(RequestUtils.getInt(request, "priority"));
		database.setOperation(RequestUtils.getInt(request, "operation"));
		database.setDbname(request.getParameter("dbname"));
		database.setProviderClass(request.getParameter("providerClass"));
		database.setActive(request.getParameter("active"));
		database.setUpdateBy(loginContext.getActorId());
		if (StringUtils.isEmpty(database.getType())) {
			database.setType("sqlserver");
		}
		try {
			this.databaseService.save(database);
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		Database database = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			database = databaseService.getDatabaseById(RequestUtils.getLong(
					request, "id"));
		}
		JSONObject result = new JSONObject();
		if (database != null) {
			result = database.toJsonObject();
			result.put("id", database.getId());
			result.put("databaseId", database.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
