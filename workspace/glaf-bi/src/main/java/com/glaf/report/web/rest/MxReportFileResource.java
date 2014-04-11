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
package com.glaf.report.web.rest;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.WebApplicationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;

import com.glaf.report.domain.ReportFile;
import com.glaf.report.query.ReportFileQuery;
import com.glaf.report.service.IReportFileService;

@Controller("/rs/bi/reportFile")
@Path("/rs/bi/reportFile")
public class MxReportFileResource {
	protected static final Log logger = LogFactory
			.getLog(MxReportFileResource.class);

	 
	protected IReportFileService reportFileService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteAll(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String rowIds = request.getParameter("rowIds");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				// reportFileService.deleteByIds(ids);
				return ResponseUtils.responseJsonResult(true, "É¾³ý³É¹¦£¡");
			}
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return ResponseUtils.responseJsonResult(false, "É¾³ýÊ§°Ü£¡");
	}

	@POST
	@Path("/deleteAll/{rowIds}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteAll(@PathParam("rowIds") String rowIds,
			@Context UriInfo uriInfo) {
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				// reportFileService.deleteByIds(ids);
				return ResponseUtils.responseJsonResult(true, "É¾³ý³É¹¦£¡");
			}
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return ResponseUtils.responseJsonResult(false, "É¾³ýÊ§°Ü£¡");
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteById(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String reportFileId = request.getParameter("reportFileId");
		if (StringUtils.isEmpty(reportFileId)) {
			reportFileId = request.getParameter("id");
		}
		if (reportFileId != null) {
			reportFileService.deleteById(reportFileId);
			return ResponseUtils.responseJsonResult(true, "É¾³ý³É¹¦£¡");
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}

	@POST
	@Path("/delete/{reportFileId}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteById(@PathParam("reportFileId") String reportFileId,
			@Context UriInfo uriInfo) {
		if (reportFileId != null) {
			reportFileService.deleteById(reportFileId);
			return ResponseUtils.responseJsonResult(true, "É¾³ý³É¹¦£¡");
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] list(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ReportFileQuery query = new ReportFileQuery();
		Tools.populate(query, params);

		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "easyui";
		}
		int start = 0;
		int limit = 10;
		String orderName = null;
		String order = null;
		if ("easyui".equals(gridType)) {
			int pageNo = ParamUtils.getInt(params, "page");
			limit = ParamUtils.getInt(params, "rows");
			start = (pageNo - 1) * limit;
			orderName = ParamUtils.getString(params, "sort");
			order = ParamUtils.getString(params, "order");
		} else if ("extjs".equals(gridType)) {
			start = ParamUtils.getInt(params, "start");
			limit = ParamUtils.getInt(params, "limit");
			orderName = ParamUtils.getString(params, "sort");
			order = ParamUtils.getString(params, "dir");
		} else if ("yui".equals(gridType)) {
			start = ParamUtils.getInt(params, "startIndex");
			limit = ParamUtils.getInt(params, "results");
			orderName = ParamUtils.getString(params, "sort");
			order = ParamUtils.getString(params, "dir");
		}

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = Paging.DEFAULT_PAGE_SIZE;
		}

		ObjectNode responseJSON = new ObjectMapper().createObjectNode();
		int total = reportFileService.getReportFileCountByQueryCriteria(query);
		if (total > 0) {
			responseJSON.put("total", total);
			responseJSON.put("totalCount", total);
			responseJSON.put("totalRecords", total);
			responseJSON.put("start", start);
			responseJSON.put("startIndex", start);
			responseJSON.put("limit", limit);
			responseJSON.put("pageSize", limit);

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortOrder(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder("desc");
				}
			}

			// Map<String, UserProfile> userMap =
			// MxIdentityFactory.getUserProfileMap();
			List<ReportFile> list = reportFileService
					.getReportFilesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				ArrayNode rowsJSON = new ObjectMapper().createArrayNode();
				if ("yui".equals(gridType)) {
					responseJSON.put("records", rowsJSON);
				} else {
					responseJSON.put("rows", rowsJSON);
				}

				for (ReportFile reportFile : list) {
					ObjectNode node = reportFile.toObjectNode();
					node.put("sortNo", ++start);
					node.put("startIndex", start);
					rowsJSON.add(node);
				}

			}
		}
		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}

	@POST
	@Path("/save")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveReportFile(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String reportFileId = request.getParameter("reportFileId");
		if (StringUtils.isEmpty(reportFileId)) {
			reportFileId = request.getParameter("id");
		}
		ReportFile reportFile = null;
		if (StringUtils.isNotEmpty(reportFileId)) {
			reportFile = reportFileService.getReportFile(reportFileId);
		}

		if (reportFile == null) {
			reportFile = new ReportFile();
		}

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Tools.populate(reportFile, params);

		try {
			this.reportFileService.save(reportFile);
			return ResponseUtils.responseJsonResult(true, "±£´æ³É¹¦£¡");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false, "±£´æÊ§°Ü£¡");
	}

	@javax.annotation.Resource
	public void setReportFileService(IReportFileService reportFileService) {
		this.reportFileService = reportFileService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String reportFileId = request.getParameter("reportFileId");
		ReportFile reportFile = null;
		if (StringUtils.isNotEmpty(reportFileId)) {
			reportFile = reportFileService.getReportFile(reportFileId);
		}
		ObjectNode responseJSON = new ObjectMapper().createObjectNode();
		if (reportFile != null) {
			// Map<String, UserProfile> userMap =
			// MxIdentityFactory.getUserProfileMap();
			responseJSON = reportFile.toObjectNode();
		}
		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}
}
