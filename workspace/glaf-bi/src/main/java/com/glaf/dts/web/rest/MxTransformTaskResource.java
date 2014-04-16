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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glaf.core.service.IQueryDefinitionService;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.dts.business.MxTransformScheduler;
import com.glaf.dts.domain.TransformTask;
import com.glaf.dts.query.TransformTaskQuery;
import com.glaf.dts.service.ITransformTaskService;
import com.glaf.dts.transform.MxTransformThread;

@Controller("/rs/dts/transform/task")
@Path("/rs/dts/transform/task")
public class MxTransformTaskResource {

	protected ITableDefinitionService tableDefinitionService;

	protected IQueryDefinitionService queryDefinitionService;

	protected ITransformTaskService transformTaskService;

	@POST
	@Path("/delete ")
	public void delete(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String queryId = request.getParameter("queryId");
		if (StringUtils.isNotEmpty(queryId)) {
			transformTaskService.deleteByQueryId(queryId);
		}
	}

	@POST
	@Path("/delete/{queryId}")
	public void delete(@PathParam("queryId") String queryId,
			@Context UriInfo uriInfo) {
		if (StringUtils.isNotEmpty(queryId)) {
			transformTaskService.deleteByQueryId(queryId);
		}
	}

	@POST
	@Path("/retryAll/{queryId}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public void retryAll(@PathParam("queryId") String queryId,
			@Context UriInfo uriInfo) {
		if (queryId != null) {
			TransformTaskQuery query = new TransformTaskQuery();
			query.queryId(queryId);
			query.statusNotEqual(9);
			List<TransformTask> tasks = transformTaskService.list(query);
			if (tasks != null && !tasks.isEmpty()) {
				List<String> taskIds = new java.util.ArrayList<String>();
				for (TransformTask task : tasks) {
					if (task.getStatus() != 9) {
						if (!taskIds.contains(task.getId())) {
							taskIds.add(task.getId());
						}
					}
				}
				if (taskIds.size() > 0) {
					MxTransformScheduler schedule = new MxTransformScheduler(
							taskIds);
					try {
						schedule.run();
					} catch (Exception ex) {

					}
				}
			}
		}

	}

	@POST
	@Path("/retry/{taskId}")
	public void retry(@PathParam("taskId") String taskId,
			@Context UriInfo uriInfo) {
		if (taskId != null) {
			MxTransformThread thread = new MxTransformThread(taskId);
			TransformTask task = transformTaskService.getTransformTask(taskId);
			task.setRetryTimes(0);
			transformTaskService.save(task);
			thread.run();
		}
	}

	@javax.annotation.Resource
	public void setQueryDefinitionService(
			IQueryDefinitionService queryDefinitionService) {
		this.queryDefinitionService = queryDefinitionService;
	}

	@javax.annotation.Resource
	public void setTableDefinitionService(
			ITableDefinitionService tableDefinitionService) {
		this.tableDefinitionService = tableDefinitionService;
	}

	@javax.annotation.Resource
	public void setTransformTaskService(
			ITransformTaskService transformTaskService) {
		this.transformTaskService = transformTaskService;
	}

}