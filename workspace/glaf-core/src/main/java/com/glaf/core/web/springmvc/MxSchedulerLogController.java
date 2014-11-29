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
package com.glaf.core.web.springmvc;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.*;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;
import com.glaf.core.base.Scheduler;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.*;
import com.glaf.core.query.*;
import com.glaf.core.service.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/system/schedulerLog")
@RequestMapping("/system/schedulerLog")
public class MxSchedulerLogController {
	protected static final Log logger = LogFactory
			.getLog(MxSchedulerLogController.class);

	protected ISysSchedulerService sysSchedulerService;

	protected ISchedulerLogService schedulerLogService;

	public MxSchedulerLogController() {

	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		logger.debug(RequestUtils.getParameterMap(request));
		String id = RequestUtils.getString(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					SchedulerLog schedulerLog = schedulerLogService
							.getSchedulerLog(String.valueOf(x));
					if (schedulerLog != null
							&& (StringUtils.equals(schedulerLog.getCreateBy(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						schedulerLogService.deleteById(schedulerLog.getId());
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			SchedulerLog schedulerLog = schedulerLogService
					.getSchedulerLog(String.valueOf(id));
			if (schedulerLog != null
					&& (StringUtils.equals(schedulerLog.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				schedulerLogService.deleteById(schedulerLog.getId());
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@ResponseBody
	@RequestMapping("/deleteAll")
	public byte[] deleteAll(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);

		String taskId = RequestUtils.getString(request, "taskId");

		if (StringUtils.isNotEmpty(taskId)) {
			Scheduler scheduler = sysSchedulerService
					.getSchedulerByTaskId(taskId);
			if (scheduler != null
					&& (StringUtils.equals(scheduler.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				schedulerLogService.deleteSchedulerLogByTaskId(taskId);
				return ResponseUtils.responseResult(true);
			}
		}

		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug("query params:"+params);
		SchedulerLogQuery query = new SchedulerLogQuery();
		Tools.populate(query, params);
		if (ParamUtils.getDate(params, "startDate") != null) {
			query.setStartDateGreaterThanOrEqual(ParamUtils.getDate(params,
					"startDate"));
		}
		if (ParamUtils.getDate(params, "endDate") != null) {
			query.setEndDateLessThanOrEqual(ParamUtils.getDate(params,
					"endDate"));
		}
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		if (StringUtils.isNotEmpty(request.getParameter("runStatus"))) {
			query.setStatus(ParamUtils.getIntValue(params, "runStatus"));
		}
		/**
		 * 此处业务逻辑需自行调整
		 */
		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
		}

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
			limit = Paging.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = schedulerLogService
				.getSchedulerLogCountByQueryCriteria(query);
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

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<SchedulerLog> list = schedulerLogService
					.getSchedulerLogsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SchedulerLog schedulerLog : list) {
					JSONObject rowJSON = schedulerLog.toJsonObject();
					if (userMap.get(schedulerLog.getCreateBy()) != null) {
						rowJSON.put("createUserName",
								userMap.get(schedulerLog.getCreateBy())
										.getName());
					} else {
						rowJSON.put("createUserName",
								schedulerLog.getCreateBy());
					}
					rowJSON.put("id", schedulerLog.getId());
					rowJSON.put("rowId", schedulerLog.getId());
					rowJSON.put("schedulerLogId", schedulerLog.getId());
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

	@RequestMapping
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtils
					.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtils.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}

		Scheduler scheduler = sysSchedulerService.getSchedulerByTaskId(request
				.getParameter("taskId"));
		if (scheduler != null) {
			request.setAttribute("scheduler", scheduler);
		}

		String requestURI = request.getRequestURI();
		if (request.getQueryString() != null) {
			request.setAttribute(
					"fromUrl",
					RequestUtils.encodeURL(requestURI + "?"
							+ request.getQueryString()));
		} else {
			request.setAttribute("fromUrl", RequestUtils.encodeURL(requestURI));
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/modules/sys/scheduler/loglist", modelMap);
	}

	@javax.annotation.Resource
	public void setSchedulerLogService(ISchedulerLogService schedulerLogService) {
		this.schedulerLogService = schedulerLogService;
	}

	@javax.annotation.Resource
	public void setSysSchedulerService(ISysSchedulerService sysSchedulerService) {
		this.sysSchedulerService = sysSchedulerService;
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		SchedulerLog schedulerLog = schedulerLogService.getSchedulerLog(request
				.getParameter("id"));
		request.setAttribute("schedulerLog", schedulerLog);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("scheduler.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/modules/sys/scheduler/viewLog");
	}

}
