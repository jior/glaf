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

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.utils.ParamUtil;
import com.glaf.base.utils.RequestUtil;
import com.glaf.core.base.Scheduler;
import com.glaf.core.domain.SchedulerEntity;
import com.glaf.core.res.MessageUtils;
import com.glaf.core.res.ViewMessage;
import com.glaf.core.res.ViewMessages;
import com.glaf.core.service.ISysSchedulerService;
import com.glaf.core.util.QuartzUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.Tools;

@Controller("/rs/sys/scheduler")
@Path("/rs/sys/scheduler")
public class SysSchedulerResource {
	protected final static Log logger = LogFactory
			.getLog(SysSchedulerResource.class);

	protected ISysSchedulerService sysSchedulerService;

	public SysSchedulerResource() {

	}

	@Path("locked")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public ModelAndView locked(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		RequestUtils.setRequestParameterToAttribute(request);
		String taskId = request.getParameter("taskId");
		int locked = 0;
		boolean ret = false;

		Scheduler scheduler = null;
		if (StringUtils.isNotEmpty(taskId)) {
			scheduler = sysSchedulerService.getSchedulerByTaskId(taskId);
			if (scheduler != null) {
				sysSchedulerService.locked(taskId, locked);
				if (scheduler.getLocked() == 1) {
					QuartzUtils.stop(taskId);
				}
				ret = true;
			}
		}
		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"scheduler.modify_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"scheduler.modify_failure"));
		}
		MessageUtils.addMessages(request, messages);

		return new ModelAndView("show_json_msg");
	}

	@Path("saveModify")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public ModelAndView saveModify(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		RequestUtils.setRequestParameterToAttribute(request);
		Scheduler scheduler = new SchedulerEntity();
		boolean ret = false;
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug(params);
		try {
			Tools.populate(scheduler, params);
			Date startDate = ParamUtil.getDate(params, "startDate");
			Date endDate = ParamUtil.getDate(params, "endDate");
			scheduler.setStartDate(startDate);
			scheduler.setEndDate(endDate);
			if (StringUtils.isNotEmpty(scheduler.getJobClass())) {
				Class<?> clazz = Class.forName(scheduler.getJobClass());
				Object object = clazz.newInstance();
				if (!(object instanceof org.quartz.Job)) {

				}
			}

			SysUser user = RequestUtil.getLoginUser(request);
			String actorId = user.getAccount();
			scheduler.setCreateBy(actorId);
			sysSchedulerService.save(scheduler);
			ret = true;
		} catch (Exception ex) {
			logger.debug(ex);
			ret = false;
		}

		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"scheduler.modify_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"scheduler.modify_failure"));
		}
		MessageUtils.addMessages(request, messages);

		return new ModelAndView("show_json_msg");
	}

	@javax.annotation.Resource
	public void setSysSchedulerService(ISysSchedulerService sysSchedulerService) {
		this.sysSchedulerService = sysSchedulerService;
	}

	@Path("startup")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public ModelAndView startup(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		RequestUtils.setRequestParameterToAttribute(request);
		boolean ret = false;
		String taskId = request.getParameter("taskId");
		String startup = request.getParameter("startup");
		Scheduler scheduler = null;
		if (StringUtils.isNotEmpty(taskId)) {
			try {
				scheduler = sysSchedulerService.getSchedulerByTaskId(taskId);
				if (scheduler != null) {
					if (StringUtils.equals(startup, "1")) {
						QuartzUtils.stop(taskId);
						QuartzUtils.restart(taskId);
					} else {
						QuartzUtils.stop(taskId);
					}
					ret = true;
				}
			} catch (Exception ex) {
				logger.debug(ex);
				ret = false;
			}
		}

		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"scheduler.modify_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"scheduler.modify_failure"));
		}
		MessageUtils.addMessages(request, messages);

		return new ModelAndView("show_json_msg");
	}

}