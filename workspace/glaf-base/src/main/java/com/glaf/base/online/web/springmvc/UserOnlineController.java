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

package com.glaf.base.online.web.springmvc;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.*;
import com.glaf.core.domain.SystemProperty;
import com.glaf.core.security.*;
import com.glaf.core.service.ISystemPropertyService;
import com.glaf.core.util.*;

import com.glaf.base.online.domain.*;
import com.glaf.base.online.query.*;
import com.glaf.base.online.service.*;

@Controller("/public/online")
@RequestMapping("/public/online")
public class UserOnlineController {
	protected static final Log logger = LogFactory
			.getLog(UserOnlineController.class);

	protected UserOnlineService userOnlineService;

	protected ISystemPropertyService systemPropertyService;

	public UserOnlineController() {

	}

	@RequestMapping("/doKickOut")
	@ResponseBody
	public void doKickOut(HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		if (loginContext.isSystemAdministrator()) {
			String actorId = request.getParameter("actorId");
			if (!(StringUtils.equals(actorId, "admin") || StringUtils.equals(
					actorId, "root"))) {
				try {
					userOnlineService.logout(actorId);
				} catch (Exception ex) {
				}
			}
		}
	}

	@RequestMapping("/doRemain")
	@ResponseBody
	public void doRemain(HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		try {
			userOnlineService.remain(loginContext.getActorId());
		} catch (Exception ex) {
		}
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		UserOnlineQuery query = new UserOnlineQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setSearchWord(request.getParameter("searchWord"));

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
		int total = userOnlineService.getUserOnlineCountByQueryCriteria(query);
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

			List<UserOnline> list = userOnlineService
					.getUserOnlinesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (UserOnline userOnline : list) {
					JSONObject rowJSON = userOnline.toJsonObject();
					rowJSON.put("id", userOnline.getId());
					rowJSON.put("userOnlineId", userOnline.getId());
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

		return new ModelAndView("/modules/sys/online/list", modelMap);
	}

	@RequestMapping("/remain")
	public ModelAndView remain(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		int timeoutSeconds = 300;

		SystemProperty p = systemPropertyService.getSystemProperty("SYS",
				"login_time_check");
		if (p != null && p.getValue() != null
				&& StringUtils.isNumeric(p.getValue())) {
			timeoutSeconds = Integer.parseInt(p.getValue());
		}

		if (timeoutSeconds > 3600) {
			timeoutSeconds = 3600;
		}

		timeoutSeconds = timeoutSeconds - 60;
		if (timeoutSeconds < 60) {
			timeoutSeconds = 60;
		}
		request.setAttribute("timeoutSeconds", timeoutSeconds);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/modules/sys/online/remain", modelMap);
	}

	@javax.annotation.Resource
	public void setSystemPropertyService(
			ISystemPropertyService systemPropertyService) {
		this.systemPropertyService = systemPropertyService;
	}

	@javax.annotation.Resource
	public void setUserOnlineService(UserOnlineService userOnlineService) {
		this.userOnlineService = userOnlineService;
	}

}
