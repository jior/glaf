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
package com.glaf.oa.travelfee.web.springmvc;

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

import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;

import com.glaf.oa.travelfee.model.*;
import com.glaf.oa.travelfee.query.*;
import com.glaf.oa.travelfee.service.*;

@Controller("/oa/travelfee")
@RequestMapping("/oa/travelfee")
public class TravelfeeController {
	protected static final Log logger = LogFactory
			.getLog(TravelfeeController.class);

	protected TravelfeeService travelfeeService;

	public TravelfeeController() {

	}

	@ResponseBody
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, ModelMap modelMap) {

		Long feeid = RequestUtils.getLong(request, "feeid");
		String feeids = request.getParameter("feeids");
		if (StringUtils.isNotEmpty(feeids)) {
			StringTokenizer token = new StringTokenizer(feeids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Travelfee travelfee = travelfeeService.getTravelfee(Long
							.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					if (travelfee != null) {
						// travelfee.setDeleteFlag(1);
						travelfeeService.deleteById(Long.valueOf(x));
					}
				}
			}
		} else if (feeid != null) {
			Travelfee travelfee = travelfeeService.getTravelfee(Long
					.valueOf(feeid));
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (travelfee != null) {
				// travelfee.setDeleteFlag(1);
				travelfeeService.deleteById(feeid);
			}
		}
	}

	@ResponseBody
	@RequestMapping("/detail")
	public byte[] detail(HttpServletRequest request) throws IOException {
		// RequestUtils.setRequestParameterToAttribute(request);
		// Map<String, Object> params = RequestUtils.getParameterMap(request);
		Travelfee travelfee = travelfeeService.getTravelfee(RequestUtils
				.getLong(request, "feeid"));

		JSONObject rowJSON = travelfee.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		Travelfee travelfee = travelfeeService.getTravelfee(RequestUtils
				.getLong(request, "feeid"));
		if (travelfee != null) {
			request.setAttribute("travelfee", travelfee);
			JSONObject rowJSON = travelfee.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("travelfee.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/oa/travelfee/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TravelfeeQuery query = new TravelfeeQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);

		if (request.getParameter("travelid") != null
				&& !request.getParameter("travelid").equals("")) {
			query.setTravelid(Long.parseLong(request.getParameter("travelid")));
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

		double feesumAccount = 0;

		JSONObject result = new JSONObject();
		int total = travelfeeService.getTravelfeeCountByQueryCriteria(query);
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

			List<Travelfee> list = travelfeeService
					.getTravelfeesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Travelfee travelfee : list) {
					JSONObject rowJSON = travelfee.toJsonObject();
					rowJSON.put("id", travelfee.getFeeid());
					rowJSON.put("travelfeeId", travelfee.getFeeid());
					rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
					feesumAccount = feesumAccount + travelfee.getFeesum();
				}

			}
			result.put("feesumAccount", feesumAccount);
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
			result.put("feesumAccount", feesumAccount);
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

		return new ModelAndView("/oa/travelfee/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("travelfee.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/oa/travelfee/query", modelMap);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Travelfee travelfee = new Travelfee();
		Tools.populate(travelfee, params);

		travelfee.setTravelid(RequestUtils.getLong(request, "travelid"));
		travelfee.setFeename(request.getParameter("feename"));
		travelfee.setFeesum(RequestUtils.getDouble(request, "feesum"));
		travelfee.setRemark(request.getParameter("remark"));

		if (RequestUtils.getLong(request, "feeid") == 0L
				|| request.getParameter("feeid") == null) {
			travelfee.setFeeid(0L);
			travelfee.setCreateDate(new Date());
			travelfee.setCreateBy(actorId);
		} else {
			travelfee.setFeeid(RequestUtils.getLong(request, "feeid"));
			travelfee.setUpdateDate(new Date());
			travelfee.setUpdateBy(actorId);
		}

		travelfeeService.save(travelfee);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveTravelfee")
	public byte[] saveTravelfee(HttpServletRequest request) {

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Travelfee travelfee = new Travelfee();
		try {
			Tools.populate(travelfee, params);
			travelfee.setTravelid(RequestUtils.getLong(request, "travelid"));
			travelfee.setFeename(request.getParameter("feename"));
			travelfee.setFeesum(RequestUtils.getDouble(request, "feesum"));
			travelfee.setRemark(request.getParameter("remark"));
			this.travelfeeService.save(travelfee);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setTravelfeeService(TravelfeeService travelfeeService) {
		this.travelfeeService = travelfeeService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Travelfee travelfee = travelfeeService.getTravelfee(RequestUtils
				.getLong(request, "feeid"));

		travelfee.setTravelid(RequestUtils.getLong(request, "travelid"));
		travelfee.setFeename(request.getParameter("feename"));
		travelfee.setFeesum(RequestUtils.getDouble(request, "feesum"));
		travelfee.setRemark(request.getParameter("remark"));

		travelfeeService.save(travelfee);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Travelfee travelfee = travelfeeService.getTravelfee(RequestUtils
				.getLong(request, "feeid"));
		request.setAttribute("travelfee", travelfee);
		JSONObject rowJSON = travelfee.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("travelfee.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/oa/travelfee/view");
	}

}