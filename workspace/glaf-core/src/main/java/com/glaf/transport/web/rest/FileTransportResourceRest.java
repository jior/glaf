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

package com.glaf.transport.web.rest;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.*;

import com.glaf.core.util.*;

import com.glaf.transport.domain.FileTransport;
import com.glaf.transport.query.FileTransportQuery;
import com.glaf.transport.service.FileTransportService;

/**
 * 
 * Rest响应类
 * 
 */

@Controller
@Path("/rs/system/transport")
public class FileTransportResourceRest {
	protected static final Log logger = LogFactory
			.getLog(FileTransportResourceRest.class);

	protected FileTransportService fileTransportService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
			List<Long> ids = StringTools.splitToLong(rowIds);
			if (ids != null && !ids.isEmpty()) {
				fileTransportService.deleteByIds(ids);
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
		fileTransportService.deleteById(RequestUtils.getLong(request, "id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FileTransportQuery query = new FileTransportQuery();
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
		int total = fileTransportService
				.getFileTransportCountByQueryCriteria(query);
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

			List<FileTransport> list = fileTransportService
					.getFileTransportsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FileTransport fileTransport : list) {
					JSONObject rowJSON = fileTransport.toJsonObject();
					rowJSON.put("id", fileTransport.getId());
					rowJSON.put("transportId", fileTransport.getId());
					rowJSON.put("startIndex", ++start);
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
	@Path("/saveTransport")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveTransport(@Context HttpServletRequest request) {
		FileTransport fileTransport = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			fileTransport = fileTransportService.getFileTransport(RequestUtils
					.getLong(request, "id"));
		}
		if (fileTransport == null) {
			fileTransport = new FileTransport();
		}

		String user = request.getParameter("user");
		String password = request.getParameter("password");
		fileTransport.setUser(user);
		fileTransport.setPassword(password);
		fileTransport.setTitle(request.getParameter("title"));
		fileTransport.setCode(request.getParameter("code"));
		fileTransport.setNodeId(RequestUtils.getLong(request, "nodeId"));
		fileTransport.setHost(request.getParameter("host"));
		fileTransport.setPort(RequestUtils.getInt(request, "port"));
		fileTransport.setPath(request.getParameter("path"));
		fileTransport.setType(request.getParameter("type"));
		fileTransport.setActive(request.getParameter("active"));
		try {

			this.fileTransportService.save(fileTransport);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setFileTransportService(
			FileTransportService fileTransportService) {
		this.fileTransportService = fileTransportService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		FileTransport fileTransport = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			fileTransport = fileTransportService.getFileTransport(RequestUtils
					.getLong(request, "id"));
		}
		JSONObject result = new JSONObject();
		if (fileTransport != null) {
			result = fileTransport.toJsonObject();
			result.put("id", fileTransport.getId());
			result.put("transportId", fileTransport.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
