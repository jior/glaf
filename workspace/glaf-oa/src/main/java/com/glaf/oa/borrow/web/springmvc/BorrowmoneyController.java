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
package com.glaf.oa.borrow.web.springmvc;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.oa.borrow.model.Borrowmoney;
import com.glaf.oa.borrow.query.BorrowmoneyQuery;
import com.glaf.oa.borrow.service.BorrowmoneyService;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.User;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;

@Controller("/oa/borrowmoney")
@RequestMapping("/oa/borrowmoney")
public class BorrowmoneyController {
	protected static final Log logger = LogFactory
			.getLog(BorrowmoneyController.class);

	protected BorrowmoneyService borrowmoneyService;

	public BorrowmoneyController() {

	}

	@ResponseBody
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Long borrowmoneyid = RequestUtils.getLong(request, "borrowmoneyid");
		String borrowmoneyids = request.getParameter("borrowmoneyids");
		if (StringUtils.isNotEmpty(borrowmoneyids)) {
			StringTokenizer token = new StringTokenizer(borrowmoneyids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Borrowmoney borrowmoney = borrowmoneyService
							.getBorrowmoney(Long.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					if (borrowmoney != null
							&& (StringUtils.equals(borrowmoney.getCreateBy(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						borrowmoneyService.save(borrowmoney);
					}
				}
			}
		} else if (borrowmoneyid != null) {
			Borrowmoney borrowmoney = borrowmoneyService.getBorrowmoney(Long
					.valueOf(borrowmoneyid));
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (borrowmoney != null
					&& (StringUtils.equals(borrowmoney.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				borrowmoneyService.save(borrowmoney);
			}
		}
	}

	@ResponseBody
	@RequestMapping("/detail")
	public byte[] detail(HttpServletRequest request) throws IOException {
		// RequestUtils.setRequestParameterToAttribute(request);
		// Map<String, Object> params = RequestUtils.getParameterMap(request);
		Borrowmoney borrowmoney = borrowmoneyService
				.getBorrowmoney(RequestUtils.getLong(request, "borrowmoneyid"));

		JSONObject rowJSON = borrowmoney.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		Borrowmoney borrowmoney = borrowmoneyService
				.getBorrowmoney(RequestUtils.getLong(request, "borrowmoneyid"));
		if (borrowmoney != null) {
			request.setAttribute("borrowmoney", borrowmoney);
			JSONObject rowJSON = borrowmoney.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}

		boolean canUpdate = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {

		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (borrowmoney != null) {
			}
		}

		request.setAttribute("canUpdate", canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("borrowmoney.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/oa/borrowmoney/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		BorrowmoneyQuery query = new BorrowmoneyQuery();
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
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = borrowmoneyService
				.getBorrowmoneyCountByQueryCriteria(query);
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

			List<Borrowmoney> list = borrowmoneyService
					.getBorrowmoneysByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Borrowmoney borrowmoney : list) {
					JSONObject rowJSON = borrowmoney.toJsonObject();
					rowJSON.put("id", borrowmoney.getBorrowmoneyid());
					rowJSON.put("borrowmoneyId", borrowmoney.getBorrowmoneyid());
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

		return new ModelAndView("/oa/borrowmoney/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("borrowmoney.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/oa/borrowmoney/query", modelMap);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Borrowmoney borrowmoney = new Borrowmoney();
		Tools.populate(borrowmoney, params);

		borrowmoney.setBorrowid(RequestUtils.getLong(request, "borrowid"));
		borrowmoney.setFeename(request.getParameter("feename"));
		borrowmoney.setFeesum(RequestUtils.getDouble(request, "feesum"));
		borrowmoney.setRemark(request.getParameter("remark"));
		borrowmoney.setCreateBy(request.getParameter("createBy"));
		borrowmoney.setCreateDate(RequestUtils.getDate(request, "createDate"));
		borrowmoney.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		borrowmoney.setUpdateBy(request.getParameter("updateBy"));

		borrowmoney.setCreateBy(actorId);

		borrowmoneyService.save(borrowmoney);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveBorrowmoney")
	public byte[] saveBorrowmoney(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Borrowmoney borrowmoney = new Borrowmoney();
		try {
			Tools.populate(borrowmoney, params);
			borrowmoney.setBorrowid(RequestUtils.getLong(request, "borrowid"));
			borrowmoney.setFeename(request.getParameter("feename"));
			borrowmoney.setFeesum(RequestUtils.getDouble(request, "feesum"));
			borrowmoney.setRemark(request.getParameter("remark"));
			borrowmoney.setCreateBy(request.getParameter("createBy"));
			borrowmoney.setCreateDate(RequestUtils.getDate(request,
					"createDate"));
			borrowmoney.setUpdateDate(RequestUtils.getDate(request,
					"updateDate"));
			borrowmoney.setUpdateBy(request.getParameter("updateBy"));
			borrowmoney.setCreateBy(actorId);
			this.borrowmoneyService.save(borrowmoney);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setBorrowmoneyService(BorrowmoneyService borrowmoneyService) {
		this.borrowmoneyService = borrowmoneyService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Borrowmoney borrowmoney = borrowmoneyService
				.getBorrowmoney(RequestUtils.getLong(request, "borrowmoneyid"));

		borrowmoney.setBorrowid(RequestUtils.getLong(request, "borrowid"));
		borrowmoney.setFeename(request.getParameter("feename"));
		borrowmoney.setFeesum(RequestUtils.getDouble(request, "feesum"));
		borrowmoney.setRemark(request.getParameter("remark"));
		borrowmoney.setCreateBy(request.getParameter("createBy"));
		borrowmoney.setCreateDate(RequestUtils.getDate(request, "createDate"));
		borrowmoney.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		borrowmoney.setUpdateBy(request.getParameter("updateBy"));

		borrowmoneyService.save(borrowmoney);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		Borrowmoney borrowmoney = borrowmoneyService
				.getBorrowmoney(RequestUtils.getLong(request, "borrowmoneyid"));
		request.setAttribute("borrowmoney", borrowmoney);
		JSONObject rowJSON = borrowmoney.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("borrowmoney.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/oa/borrowmoney/view");
	}

}