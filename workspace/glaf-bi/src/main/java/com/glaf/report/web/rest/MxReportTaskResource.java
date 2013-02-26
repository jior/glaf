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
import org.apache.commons.lang.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.QuartzUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;

import com.glaf.report.domain.ReportTask;
import com.glaf.report.query.ReportTaskQuery;
import com.glaf.report.service.IReportTaskService;

@Controller("/rs/bi/reportTask")
@Path("/rs/bi/reportTask")
public class MxReportTaskResource {
	protected static final Log logger = LogFactory
			.getLog(MxReportTaskResource.class);

	@javax.annotation.Resource
	protected IReportTaskService reportTaskService;

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
				// reportTaskService.deleteByIds(ids);
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
				// reportTaskService.deleteByIds(ids);
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
		String reportTaskId = request.getParameter("reportTaskId");
		if (StringUtils.isEmpty(reportTaskId)) {
			reportTaskId = request.getParameter("id");
		}
		if (reportTaskId != null) {
			reportTaskService.deleteById(reportTaskId);
			return ResponseUtils.responseJsonResult(true, "É¾³ý³É¹¦£¡");
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}

	@POST
	@Path("/delete/{reportTaskId}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteById(@PathParam("reportTaskId") String reportTaskId,
			@Context UriInfo uriInfo) {
		if (reportTaskId != null) {
			reportTaskService.deleteById(reportTaskId);
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
		ReportTaskQuery query = new ReportTaskQuery();
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
		int total = reportTaskService.getReportTaskCountByQueryCriteria(query);
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
			List<ReportTask> list = reportTaskService
					.getReportTasksByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				ArrayNode rowsJSON = new ObjectMapper().createArrayNode();
				if ("yui".equals(gridType)) {
					responseJSON.put("records", rowsJSON);
				} else {
					responseJSON.put("rows", rowsJSON);
				}

				// int sortNo = 0;
				for (ReportTask reportTask : list) {
					// sortNo++;
					ObjectNode node = reportTask.toObjectNode();
					node.put("sortNo", ++start);
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
	public byte[] saveReportTask(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String reportTaskId = request.getParameter("reportTaskId");
		if (StringUtils.isEmpty(reportTaskId)) {
			reportTaskId = request.getParameter("id");
		}
		ReportTask reportTask = null;
		if (StringUtils.isNotEmpty(reportTaskId)) {
			reportTask = reportTaskService.getReportTask(reportTaskId);
		}

		if (reportTask == null) {
			reportTask = new ReportTask();
		}

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Tools.populate(reportTask, params);
		logger.debug(params);

		try {
			this.reportTaskService.save(reportTask);

			String taskId = reportTask.getId();

			if (StringUtils.equals(reportTask.getEnableFlag(), "1")) {
				QuartzUtils.stop(taskId);
				QuartzUtils.restart(taskId);
			} else {
				QuartzUtils.stop(taskId);
			}

			return ResponseUtils.responseJsonResult(true, "±£´æ³É¹¦£¡");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false, "±£´æÊ§°Ü£¡");
	}

	public void setReportTaskService(IReportTaskService reportTaskService) {
		this.reportTaskService = reportTaskService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String reportTaskId = request.getParameter("reportTaskId");
		ReportTask reportTask = null;
		if (StringUtils.isNotEmpty(reportTaskId)) {
			reportTask = reportTaskService.getReportTask(reportTaskId);
		}
		ObjectNode responseJSON = new ObjectMapper().createObjectNode();
		if (reportTask != null) {
			// Map<String, UserProfile> userMap =
			// MxIdentityFactory.getUserProfileMap();
			responseJSON = reportTask.toObjectNode();
		}
		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}
}
