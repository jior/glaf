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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.Scheduler;
import com.glaf.core.domain.SchedulerEntity;
import com.glaf.core.service.ISysSchedulerService;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.QuartzUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.Tools;
import com.glaf.dts.util.Constants;

@Controller("/rs/dts/scheduler")
@Path("/rs/dts/scheduler")
public class MxSchedulerResource {
	protected final static Log logger = LogFactory
			.getLog(MxSchedulerResource.class);

	protected ISysSchedulerService sysSchedulerService;

	public MxSchedulerResource() {

	}

	@POST
	@Path("/delete/{taskId}")
	public void delete(@PathParam("taskId") String taskId,
			@Context UriInfo uriInfo) {
		if (StringUtils.isNotEmpty(taskId)) {
			Scheduler scheduler = sysSchedulerService
					.getSchedulerByTaskId(taskId);
			if (scheduler != null
					&& StringUtils.equals(scheduler.getTaskType(),
							Constants.DTS_TASK_TYPE)) {
				sysSchedulerService.deleteScheduler(taskId);
			}
		}
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] list(@Context HttpServletRequest request) {
		List<Scheduler> list = sysSchedulerService
				.getSchedulers(Constants.DTS_TASK_TYPE);
		ObjectNode responseJSON = new ObjectMapper().createObjectNode();

		ArrayNode schedulersJSON = new ObjectMapper().createArrayNode();
		responseJSON.put("schedulers", schedulersJSON);

		for (Scheduler scheduler : list) {
			ObjectNode schedulerJSON = new ObjectMapper().createObjectNode();
			schedulerJSON.put("taskId", scheduler.getTaskId());
			schedulerJSON.put("taskName", scheduler.getTaskName());
			schedulerJSON.put("title", scheduler.getTitle());
			schedulerJSON.put("content", scheduler.getContent());
			schedulerJSON.put("locked", scheduler.getLocked());
			schedulerJSON.put("expression", scheduler.getExpression());
			schedulerJSON.put("jobClass", scheduler.getJobClass());
			schedulerJSON.put("priority", scheduler.getPriority());
			schedulerJSON.put("repeatCount", scheduler.getRepeatCount());
			schedulerJSON.put("repeatInterval", scheduler.getRepeatInterval());
			schedulerJSON.put("startDelay", scheduler.getStartDelay());
			schedulerJSON.put("threadSize", scheduler.getThreadSize());
			schedulerJSON.put("createBy", scheduler.getCreateBy());
			schedulerJSON.put("createDate",
					DateUtils.getDateTime(scheduler.getCreateDate()));
			schedulerJSON.put("startDate",
					DateUtils.getDateTime(scheduler.getStartDate()));
			schedulerJSON.put("endDate",
					DateUtils.getDateTime(scheduler.getEndDate()));
			schedulersJSON.add(schedulerJSON);
		}

		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}

	@POST
	@Path("/locked/{taskId}/{locked}")
	public void locked(@PathParam("taskId") String taskId,
			@PathParam("locked") int locked, @Context UriInfo uriInfo) {
		if (StringUtils.isNotEmpty(taskId)) {
			Scheduler scheduler = sysSchedulerService
					.getSchedulerByTaskId(taskId);
			if (scheduler != null
					&& StringUtils.equals(scheduler.getTaskType(),
							Constants.DTS_TASK_TYPE)) {
				sysSchedulerService.locked(taskId, locked);
				if (scheduler.getLocked() == 1) {
					QuartzUtils.stop(taskId);
				}
			}
		}
	}

	@POST
	@Path("/save")
	public void save(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		logger.debug("----------------save scheduler-------------- ");
		try {
			Scheduler scheduler = new SchedulerEntity();
			Map<String, Object> params = RequestUtils.getParameterMap(request);
			logger.debug("params:" + params);
			Tools.populate(scheduler, params);
			logger.debug("rpt:" + scheduler.getRepeatInterval());
			if (scheduler.getRepeatInterval() <= 0) {
				throw new WebApplicationException(
						Response.Status.INTERNAL_SERVER_ERROR);
			}

			if (StringUtils.isNotEmpty(scheduler.getJobClass())) {
				try {
					Class<?> clazz = Class.forName(scheduler.getJobClass());
					if (!Job.class.isAssignableFrom(clazz)) {
						throw new IllegalArgumentException(
								"Job class must implement the org.quartz.Job interface.");
					}
				} catch (Exception ex) {
					logger.debug(ex);
					throw new WebApplicationException(
							Response.Status.INTERNAL_SERVER_ERROR);
				}
			}

			Scheduler model = null;
			if (StringUtils.isNotEmpty(scheduler.getTaskId())) {
				model = sysSchedulerService.getSchedulerByTaskId(scheduler
						.getTaskId());
				if (model != null
						&& StringUtils.equals(model.getTaskType(),
								Constants.DTS_TASK_TYPE)) {
					QuartzUtils.stop(scheduler.getTaskId());
					sysSchedulerService.deleteScheduler(model.getTaskId());
				}
			}

			logger.debug("save scheduler ");
			scheduler.setTaskType(Constants.DTS_TASK_TYPE);
			sysSchedulerService.save(scheduler);

			if (scheduler.isSchedulerAutoStartup()) {
				QuartzUtils.restart(scheduler.getTaskId());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@javax.annotation.Resource
	public void setSysSchedulerService(ISysSchedulerService sysSchedulerService) {
		this.sysSchedulerService = sysSchedulerService;
	}

	@POST
	@Path("/startup/{taskId}/{startup}")
	public void startup(@PathParam("taskId") String taskId,
			@PathParam("startup") String startup, @Context UriInfo uriInfo) {
		Scheduler scheduler = null;
		logger.debug("startup: " + startup);
		if (StringUtils.isNotEmpty(taskId)) {
			scheduler = sysSchedulerService.getSchedulerByTaskId(taskId);
			if (scheduler != null) {

				if (StringUtils.equals(startup, "true")) {
					QuartzUtils.stop(taskId);
					QuartzUtils.restart(taskId);
				} else {
					QuartzUtils.stop(taskId);
				}
			}
		}
	}

	@GET
	@POST
	@Path("/view/{queryId}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@PathParam("taskId") String taskId,
			@Context UriInfo uriInfo) {
		Scheduler scheduler = null;
		if (StringUtils.isNotEmpty(taskId)) {
			scheduler = sysSchedulerService.getSchedulerByTaskId(taskId);
		}
		ObjectNode responseJSON = new ObjectMapper().createObjectNode();
		if (scheduler != null) {
			responseJSON.put("taskId", scheduler.getTaskId());
			responseJSON.put("taskName", scheduler.getTaskName());
			responseJSON.put("title", scheduler.getTitle());
			responseJSON.put("content", scheduler.getContent());
			responseJSON.put("locked", scheduler.getLocked());
			responseJSON.put("expression", scheduler.getExpression());
			responseJSON.put("jobClass", scheduler.getJobClass());
			responseJSON.put("priority", scheduler.getPriority());
			responseJSON.put("repeatCount", scheduler.getRepeatCount());
			responseJSON.put("repeatInterval", scheduler.getRepeatInterval());
			responseJSON.put("startDelay", scheduler.getStartDelay());
			responseJSON.put("threadSize", scheduler.getThreadSize());
			responseJSON.put("createBy", scheduler.getCreateBy());
			responseJSON.put("createDate",
					DateUtils.getDateTime(scheduler.getCreateDate()));
			responseJSON.put("startDate",
					DateUtils.getDateTime(scheduler.getStartDate()));
			responseJSON.put("endDate",
					DateUtils.getDateTime(scheduler.getEndDate()));
		}
		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}

}