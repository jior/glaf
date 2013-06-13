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

package com.glaf.cms.info.web.rest;

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
import org.apache.commons.lang.StringUtils;
import com.alibaba.fastjson.*;

import com.glaf.core.util.*;

import com.glaf.cms.info.model.PublicInfo;
import com.glaf.cms.info.query.PublicInfoQuery;
import com.glaf.cms.info.service.PublicInfoService;

@Controller
@Path("/rs/apps/publicInfo")
public class PublicInfoResourceRest {
	protected static final Log logger = LogFactory
			.getLog(PublicInfoResourceRest.class);

	protected PublicInfoService publicInfoService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				publicInfoService.deleteByIds(ids);
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
		publicInfoService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		PublicInfoQuery query = new PublicInfoQuery();
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
		int total = publicInfoService.getPublicInfoCountByQueryCriteria(query);
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

			List<PublicInfo> list = publicInfoService
					.getPublicInfosByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (PublicInfo publicInfo : list) {
					JSONObject rowJSON = publicInfo.toJsonObject();
					rowJSON.put("id", publicInfo.getId());
					rowJSON.put("publicInfoId", publicInfo.getId());
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

	@GET
	@POST
	@Path("/json")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] json(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		PublicInfoQuery query = new PublicInfoQuery();
		Tools.populate(query, params);
		query.setPublishFlag(1);
		query.serviceKey(request.getParameter("serviceKey"));

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
		int total = publicInfoService.getPublicInfoCountByQueryCriteria(query);
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

			List<PublicInfo> list = publicInfoService
					.getPublicInfosByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				result.put("rows", rowsJSON);
				for (PublicInfo publicInfo : list) {
					JSONObject rowJSON = publicInfo.toJsonObject();
					rowJSON.put("id", publicInfo.getId());
					rowJSON.put("publicInfoId", publicInfo.getId());
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
	@Path("/savePublicInfo")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] savePublicInfo(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		PublicInfo publicInfo = new PublicInfo();
		try {
			Tools.populate(publicInfo, params);

			publicInfo.setWfStatus(RequestUtils.getInt(request, "wfStatus"));
			publicInfo.setOriginalFlag(RequestUtils.getInt(request,
					"originalFlag"));
			publicInfo.setStartDate(RequestUtils.getDate(request, "startDate"));
			publicInfo.setServiceKey(request.getParameter("serviceKey"));
			publicInfo.setTag(request.getParameter("tag"));
			publicInfo.setSubject(request.getParameter("subject"));
			publicInfo.setLink(request.getParameter("link"));
			publicInfo.setProcessName(request.getParameter("processName"));
			publicInfo.setSortNo(RequestUtils.getInt(request, "sortNo"));
			publicInfo.setEndDate(RequestUtils.getDate(request, "endDate"));
			publicInfo.setRefererUrl(request.getParameter("refererUrl"));
			publicInfo.setCreateBy(request.getParameter("createBy"));
			publicInfo.setPublishFlag(RequestUtils.getInt(request,
					"publishFlag"));
			publicInfo.setAuthor(request.getParameter("author"));
			publicInfo.setNodeId(RequestUtils.getLong(request, "nodeId"));
			publicInfo.setProcessInstanceId(RequestUtils.getLong(request,
					"processInstanceId"));
			publicInfo.setName(request.getParameter("name"));
			publicInfo.setCreateDate(RequestUtils
					.getDate(request, "createDate"));
			publicInfo
					.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
			publicInfo.setCommentFlag(RequestUtils.getInt(request,
					"commentFlag"));
			publicInfo.setUpdateBy(request.getParameter("updateBy"));
			publicInfo.setFallbackFlag(request.getParameter("fallbackFlag"));
			publicInfo.setKeywords(request.getParameter("keywords"));
			publicInfo.setStatus(RequestUtils.getInt(request, "status"));
			publicInfo.setDigg(RequestUtils.getInt(request, "digg"));
			publicInfo.setBury(RequestUtils.getInt(request, "bury"));
			publicInfo.setCommentCount(RequestUtils.getInt(request,
					"commentCount"));
			publicInfo.setUpdateDate(RequestUtils
					.getDate(request, "updateDate"));
			publicInfo.setContent(request.getParameter("content"));
			publicInfo.setUnitName(request.getParameter("unitName"));
			publicInfo.setViewCount(RequestUtils.getInt(request, "viewCount"));

			this.publicInfoService.save(publicInfo);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setPublicInfoService(PublicInfoService publicInfoService) {
		this.publicInfoService = publicInfoService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		PublicInfo publicInfo = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			publicInfo = publicInfoService.getPublicInfo(request
					.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (publicInfo != null) {
			result = publicInfo.toJsonObject();
			result.put("id", publicInfo.getId());
			result.put("publicInfoId", publicInfo.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
