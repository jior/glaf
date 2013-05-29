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

package com.glaf.base.modules.sys.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glaf.base.modules.sys.model.WorkCalendar;
import com.glaf.base.modules.sys.service.WorkCalendarService;
import com.glaf.base.utils.ParamUtil;

@Controller("/rs/sys/workCalendar")
@Path("/rs/sys/workCalendar")
public class WorkCalendarResource {
	protected static final Log logger = LogFactory
			.getLog(WorkCalendarResource.class);

	private WorkCalendarService workCalendarService;

	/**
	 * 创建记录
	 * 
	 * @param year
	 * @param month
	 * @param day
	 */
	@ResponseBody
	@Path("createData")
	@POST
	public void createData(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		int year = ParamUtil.getIntParameter(request, "year", 0);
		int month = ParamUtil.getIntParameter(request, "month", 0);
		int day = ParamUtil.getIntParameter(request, "day", 0);
		WorkCalendar calendar = workCalendarService.find(year, month, day);
		if (calendar == null) {
			calendar = new WorkCalendar();
			calendar.setFreeYear(year);
			calendar.setFreeMonth(month);
			calendar.setFreeDay(day);
			workCalendarService.create(calendar);
		}
	}

	/**
	 * 删除记录
	 * 
	 * @param year
	 * @param month
	 * @param day
	 */
	@ResponseBody
	@Path("deleteData")
	@POST
	public void deleteData(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		int year = ParamUtil.getIntParameter(request, "year", 0);
		int month = ParamUtil.getIntParameter(request, "month", 0);
		int day = ParamUtil.getIntParameter(request, "day", 0);
		WorkCalendar calendar = workCalendarService.find(year, month, day);
		if (calendar != null) {
			workCalendarService.delete(calendar.getId());
		}
	}

	@javax.annotation.Resource
	public void setWorkCalendarService(WorkCalendarService workCalendarService) {
		this.workCalendarService = workCalendarService;
	}

}