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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.SysData;
import com.glaf.core.identity.Role;
import com.glaf.core.identity.User;
import com.glaf.core.query.SysDataQuery;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.SysDataService;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.SysDataLogTableUtils;
import com.glaf.core.util.Tools;

@Controller("/sys/data/service")
@RequestMapping("/sys/data/service")
public class MxSysDataController {
	protected static final Log logger = LogFactory
			.getLog(MxSysDataController.class);

	protected SysDataService sysDataService;

	public MxSysDataController() {

	}

	@ResponseBody
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String id = RequestUtils.getString(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					SysData sysData = sysDataService.getSysData(String
							.valueOf(x));
					if (sysData != null
							&& (StringUtils.equals(sysData.getCreateBy(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						sysData.setLocked(1);
						sysDataService.save(sysData);
					}
				}
			}
		} else if (id != null) {
			SysData sysData = sysDataService.getSysData(String.valueOf(id));

			if (sysData != null
					&& (StringUtils.equals(sysData.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				sysData.setLocked(1);
				sysDataService.save(sysData);
			}
		}
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		SysData sysData = sysDataService.getSysData(request.getParameter("id"));
		if (sysData != null) {
			request.setAttribute("sysData", sysData);

			List<String> perms = StringTools.split(sysData.getPerms());
			StringBuffer buffer = new StringBuffer();
			List<Role> roles = IdentityFactory.getRoles();

			if (roles != null && !roles.isEmpty()) {
				for (Role role : roles) {
					if (perms.contains(String.valueOf(role.getId()))
							|| perms.contains(role.getCode())) {
						buffer.append(role.getName()).append("[")
								.append(role.getCode()).append("] ");
						buffer.append(FileUtils.newline);
					}
				}
			}

			request.setAttribute("x_role_names", buffer.toString());

		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("sysData.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/data/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysDataQuery query = new SysDataQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
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
		int total = sysDataService.getSysDataCountByQueryCriteria(query);
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

			List<SysData> list = sysDataService.getSysDatasByQueryCriteria(
					start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SysData sysData : list) {
					JSONObject rowJSON = sysData.toJsonObject();
					rowJSON.put("id", sysData.getId());
					rowJSON.put("rowId", sysData.getId());
					rowJSON.put("sysDataId", sysData.getId());
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

		return new ModelAndView("/modules/sys/data/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("sysData.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/sys/data/query", modelMap);
	}

	@RequestMapping("/reload")
	@ResponseBody
	public byte[] reload(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		String root = SystemProperties.getConfigRootPath();
		String config_path = root + "/conf/templates/xml";
		sysDataService.reload(config_path);
		Date date = DateUtils.getDateAfter(new Date(), 0);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int daysOfMonth = DateUtils.getYearMonthDays(year, month + 1);

		calendar.set(year, month, daysOfMonth);

		int begin = getYearMonthDay(date);
		int end = getYearMonthDay(calendar.getTime());

		for (int i = begin; i <= end; i++) {
			try {
				SysDataLogTableUtils.createTable("SYS_DATA_LOG_" + i);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	private int getYearMonthDay(Date date) {
		String returnStr = null;
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
		returnStr = f.format(date);
		return Integer.parseInt(returnStr);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		SysData sysData = new SysData();
		Tools.populate(sysData, params);

		String perm = request.getParameter("perms");
		List<String> perms = StringTools.split(perm);
		StringBuffer buffer = new StringBuffer();
		List<Role> roles = IdentityFactory.getRoles();
		if (roles != null && !roles.isEmpty()) {
			for (Role role : roles) {
				if (perms.contains(String.valueOf(role.getId()))
						|| perms.contains(role.getCode())) {
					buffer.append(role.getCode()).append(",");
				}
			}
		}

		sysData.setTitle(request.getParameter("title"));
		sysData.setDescription(request.getParameter("description"));
		sysData.setPath(request.getParameter("path"));
		sysData.setPerms(buffer.toString());
		sysData.setAddressPerms(request.getParameter("addressPerms"));
		sysData.setType(request.getParameter("type"));
		sysData.setLocked(RequestUtils.getInt(request, "locked"));
		sysData.setCreateBy(actorId);
		sysData.setUpdateBy(actorId);

		sysDataService.save(sysData);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveSysData")
	public byte[] saveSysData(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		String perm = request.getParameter("perms");
		List<String> perms = StringTools.split(perm);
		StringBuffer buffer = new StringBuffer();
		List<Role> roles = IdentityFactory.getRoles();
		if (roles != null && !roles.isEmpty()) {
			for (Role role : roles) {
				if (perms.contains(String.valueOf(role.getId()))
						|| perms.contains(role.getCode())) {
					buffer.append(role.getCode()).append(",");
				}
			}
		}

		SysData sysData = new SysData();
		try {
			Tools.populate(sysData, params);
			sysData.setTitle(request.getParameter("title"));
			sysData.setDescription(request.getParameter("description"));
			sysData.setPath(request.getParameter("path"));
			sysData.setPerms(buffer.toString());
			sysData.setAddressPerms(request.getParameter("addressPerms"));
			sysData.setType(request.getParameter("type"));
			sysData.setLocked(RequestUtils.getInt(request, "locked"));
			sysData.setCreateBy(actorId);
			sysData.setUpdateBy(actorId);
			this.sysDataService.save(sysData);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setSysDataService(SysDataService sysDataService) {
		this.sysDataService = sysDataService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();

		SysData sysData = sysDataService.getSysData(request.getParameter("id"));

		String perm = request.getParameter("perms");
		List<String> perms = StringTools.split(perm);
		StringBuffer buffer = new StringBuffer();
		List<Role> roles = IdentityFactory.getRoles();
		if (roles != null && !roles.isEmpty()) {
			for (Role role : roles) {
				if (perms.contains(String.valueOf(role.getId()))
						|| perms.contains(role.getCode())) {
					buffer.append(role.getCode()).append(",");
				}
			}
		}

		sysData.setTitle(request.getParameter("title"));
		sysData.setDescription(request.getParameter("description"));
		sysData.setPath(request.getParameter("path"));
		sysData.setPerms(perm.toString());
		sysData.setAddressPerms(request.getParameter("addressPerms"));
		sysData.setType(request.getParameter("type"));
		sysData.setLocked(RequestUtils.getInt(request, "locked"));
		sysData.setCreateBy(actorId);
		sysData.setUpdateBy(actorId);

		sysDataService.save(sysData);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		SysData sysData = sysDataService.getSysData(request.getParameter("id"));
		request.setAttribute("sysData", sysData);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("sysData.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/modules/sys/data/view");
	}

}
