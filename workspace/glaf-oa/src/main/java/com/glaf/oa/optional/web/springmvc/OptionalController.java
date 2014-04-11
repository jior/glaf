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
package com.glaf.oa.optional.web.springmvc;

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

import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;

import com.glaf.oa.optional.model.*;
import com.glaf.oa.optional.query.*;
import com.glaf.oa.optional.service.*;

@Controller("/oa/optional")
@RequestMapping("/oa/optional")
public class OptionalController {
	protected static final Log logger = LogFactory
			.getLog(OptionalController.class);

	protected OptionalService optionalService;

	public OptionalController() {

	}

	@ResponseBody
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Integer optionalId = RequestUtils.getInteger(request, "optionalId");
		String optionalIds = request.getParameter("optionalIds");
		if (StringUtils.isNotEmpty(optionalIds)) {
			StringTokenizer token = new StringTokenizer(optionalIds, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Optional optional = optionalService.getOptional(Integer
							.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					optionalService.deleteById(Integer.valueOf(x));
					if (optional != null
							&& (StringUtils.equals(optional.getCreateBy(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						optionalService.save(optional);
					}
				}
			}
		} else if (optionalId != null) {
			Optional optional = optionalService.getOptional(Integer
					.valueOf(optionalId));
			/**
			 * 此处业务逻辑需自行调整
			 */

			optionalService.deleteById(Integer.valueOf(optionalId));
			if (optional != null
					&& (StringUtils.equals(optional.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				optionalService.save(optional);
			}
		}
	}

	@ResponseBody
	@RequestMapping("/detail")
	public byte[] detail(HttpServletRequest request) throws IOException {
		// RequestUtils.setRequestParameterToAttribute(request);
		// Map<String, Object> params = RequestUtils.getParameterMap(request);
		Optional optional = optionalService.getOptional(RequestUtils.getInt(
				request, "optionalId"));

		JSONObject rowJSON = optional.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		Optional optional = optionalService.getOptional(RequestUtils.getInt(
				request, "optionalId"));
		if (optional != null) {
			request.setAttribute("optional", optional);
			JSONObject rowJSON = optional.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}

		boolean canUpdate = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {

		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (optional != null) {
				canUpdate = true;
			}
		}

		request.setAttribute("canUpdate", canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("optional.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/oa/optional/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		OptionalQuery query = new OptionalQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		/**
		 * 此处业务逻辑需自行调整
		 */
		if (!loginContext.isSystemAdministrator()) {

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
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = optionalService.getOptionalCountByQueryCriteria(query);
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

			List<Optional> list = optionalService.getOptionalsByQueryCriteria(
					start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Optional optional : list) {
					JSONObject rowJSON = optional.toJsonObject();
					rowJSON.put("id", optional.getId());
					rowJSON.put("optionalId", optional.getOptionalId());
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
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		return new ModelAndView("/oa/optional/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("optional.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/oa/optional/query", modelMap);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Optional optional = new Optional();
		Tools.populate(optional, params);
		optional.setId(RequestUtils.getInt(request, "id"));
		optional.setCode(request.getParameter("code"));
		optional.setPrice(RequestUtils.getDouble(request, "price"));
		optional.setRemark(request.getParameter("remark"));
		optional.setCreateDate(new Date());

		optional.setCreateBy(actorId);

		optionalService.save(optional);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveOptional")
	public byte[] saveOptional(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Optional optional = new Optional();
		try {
			Tools.populate(optional, params);
			optional.setId(RequestUtils.getInt(request, "id"));
			optional.setCode(request.getParameter("code"));
			optional.setPrice(RequestUtils.getDouble(request, "price"));
			optional.setRemark(request.getParameter("remark"));
			optional.setCreateBy(request.getParameter("createBy"));
			optional.setCreateDate(RequestUtils.getDate(request, "createDate"));
			optional.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
			optional.setUpdateBy(request.getParameter("updateBy"));
			optional.setCreateBy(actorId);
			this.optionalService.save(optional);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setOptionalService(OptionalService optionalService) {
		this.optionalService = optionalService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Optional optional = optionalService.getOptional(RequestUtils.getInt(
				request, "optionalId"));

		optional.setId(RequestUtils.getInt(request, "id"));
		optional.setCode(request.getParameter("code"));
		optional.setPrice(RequestUtils.getDouble(request, "price"));
		optional.setRemark(request.getParameter("remark"));
		optional.setCreateBy(request.getParameter("createBy"));
		optional.setCreateDate(RequestUtils.getDate(request, "createDate"));
		optional.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		optional.setUpdateBy(request.getParameter("updateBy"));

		optionalService.save(optional);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Optional optional = optionalService.getOptional(RequestUtils.getInt(
				request, "optionalId"));
		request.setAttribute("optional", optional);
		JSONObject rowJSON = optional.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("optional.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/oa/optional/view");
	}

}