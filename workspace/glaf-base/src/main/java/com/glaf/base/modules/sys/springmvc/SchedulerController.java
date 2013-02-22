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

package com.glaf.base.modules.sys.springmvc;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

 

import com.glaf.base.modules.sys.form.SchedulerFormBean;
import com.glaf.base.modules.sys.model.Scheduler;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SchedulerService;

import com.glaf.base.utils.ParamUtil;
import com.glaf.base.utils.QuartzUtils;
import com.glaf.base.utils.RequestUtil;

@Controller("/sys/scheduler")
@RequestMapping("/sys/scheduler.do")
public class SchedulerController {
	protected final static Log logger = LogFactory
			.getLog(SchedulerController.class);

	@javax.annotation.Resource
	protected SchedulerService schedulerService;

	public SchedulerController() {

	}

	@RequestMapping(params = "method=locked")
	public ModelAndView locked(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		String taskId = request.getParameter("taskId");
		int locked = 0;
		Scheduler scheduler = null;
		if (StringUtils.isNotEmpty(taskId)) {
			scheduler = schedulerService.getSchedulerById(taskId);
			if (scheduler != null) {
				schedulerService.locked(taskId, locked);
				if (scheduler.getLocked() == 1) {
					QuartzUtils.stop(taskId);
				}
			}
		}
		return this.showList(modelMap, request, response);
	}

	@RequestMapping(params = "method=saveModify")
	@SuppressWarnings("rawtypes")
	public ModelAndView saveModify(ModelMap modelMap,
			SchedulerFormBean schedulerForm, HttpServletRequest request,
			HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		Scheduler scheduler = new Scheduler();

		Map params = RequestUtil.getParameterMap(request);
		logger.debug(params);
		try {
			PropertyUtils.copyProperties(scheduler, schedulerForm);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Date startDate = ParamUtil.getDate(params, "startDate");
		Date endDate = ParamUtil.getDate(params, "endDate");
		scheduler.setStartDate(startDate);
		scheduler.setEndDate(endDate);
		if (StringUtils.isNotEmpty(scheduler.getJobClass())) {
			try {
				Class<?> clazz = Class.forName(scheduler.getJobClass());
				Object object = clazz.newInstance();
				if (!(object instanceof org.quartz.Job)) {

				}
			} catch (Exception ex) {
				logger.debug(ex);
			}
		}

		SysUser user = RequestUtil.getLoginUser(request);
		String actorId = user.getAccount();
		scheduler.setCreateBy(actorId);
		schedulerService.save(scheduler);

		return this.showList(modelMap, request, response);
	}

	public void setSchedulerService(SchedulerService schedulerService) {
		this.schedulerService = schedulerService;
	}

	@RequestMapping(params = "method=showList")
	public ModelAndView showList(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		SysUser user = RequestUtil.getLoginUser(request);
		String actorId = user.getAccount();
		List<Scheduler> list = schedulerService.getUserSchedulers(actorId);
		request.setAttribute("schedulers", list);
		return new ModelAndView("/modules/sys/scheduler/scheduler_list",
				modelMap);
	}

	@RequestMapping(params = "method=showModify")
	public ModelAndView showModify(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		String taskId = request.getParameter("taskId");
		Scheduler scheduler = null;
		if (StringUtils.isNotEmpty(taskId)) {
			scheduler = schedulerService.getSchedulerById(taskId);
		}
		request.setAttribute("scheduler", scheduler);

		return new ModelAndView("/modules/sys/scheduler/scheduler_modify",
				modelMap);
	}

	@RequestMapping(params = "method=startup")
	public ModelAndView startup(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		RequestUtil.setRequestParameterToAttribute(request);
		String taskId = request.getParameter("taskId");
		String startup = request.getParameter("startup");
		Scheduler scheduler = null;
		if (StringUtils.isNotEmpty(taskId)) {
			scheduler = schedulerService.getSchedulerById(taskId);
			if (scheduler != null) {
				if (StringUtils.equals(startup, "1")) {
					QuartzUtils.stop(taskId);
					QuartzUtils.restart(taskId);
				} else {
					QuartzUtils.stop(taskId);
				}
			}
		}
		return this.showList(modelMap, request, response);
	}

}