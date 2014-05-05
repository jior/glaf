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
package com.glaf.oa.paymentplan.web.springmvc;

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
import com.glaf.core.security.*;
import com.glaf.core.util.*;

import com.glaf.oa.paymentplan.model.*;
import com.glaf.oa.paymentplan.query.*;
import com.glaf.oa.paymentplan.service.*;

@Controller("/oa/paymentplan")
@RequestMapping("/oa/paymentplan")
public class PaymentplanController {
	protected static final Log logger = LogFactory
			.getLog(PaymentplanController.class);

	protected PaymentplanService paymentplanService;

	public PaymentplanController() {

	}

	@ResponseBody
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, ModelMap modelMap) {
		Long planid = RequestUtils.getLong(request, "planid");
		String planids = request.getParameter("planids");
		if (StringUtils.isNotEmpty(planids)) {
			StringTokenizer token = new StringTokenizer(planids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Paymentplan paymentplan = paymentplanService
							.getPaymentplan(Long.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					if (paymentplan != null) {
						paymentplanService
								.deleteById((paymentplan.getPlanid()));
					}
				}
			}
		} else if (planid != null) {
			Paymentplan paymentplan = paymentplanService.getPaymentplan(Long
					.valueOf(planid));
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (paymentplan != null) {
				paymentplanService.deleteById((paymentplan.getPlanid()));
			}
		}
	}

	@ResponseBody
	@RequestMapping("/detail")
	public byte[] detail(HttpServletRequest request) throws IOException {
		// RequestUtils.setRequestParameterToAttribute(request);
		// Map<String, Object> params = RequestUtils.getParameterMap(request);
		Paymentplan paymentplan = paymentplanService
				.getPaymentplan(RequestUtils.getLong(request, "planid"));

		JSONObject rowJSON = paymentplan.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		Paymentplan paymentplan = paymentplanService
				.getPaymentplan(RequestUtils.getLong(request, "planid"));
		if (paymentplan != null) {
			request.setAttribute("paymentplan", paymentplan);
			JSONObject rowJSON = paymentplan.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}

		boolean canUpdate = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {

		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (paymentplan != null) {
			}
		}

		request.setAttribute("canUpdate", canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("paymentplan.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/oa/paymentplan/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		PaymentplanQuery query = new PaymentplanQuery();
		Tools.populate(query, params);
		long budgetid = Long.valueOf(request.getParameter("budgetid"));
		query.setBudgetid(budgetid);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		/**
		 * 此处业务逻辑需自行调整
		 */

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
		int total = paymentplanService
				.getPaymentplanCountByQueryCriteria(query);
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

			List<Paymentplan> list = paymentplanService
					.getPaymentplansByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Paymentplan paymentplan : list) {
					JSONObject rowJSON = paymentplan.toJsonObject();
					rowJSON.put("id", paymentplan.getPlanid());
					rowJSON.put("paymentplanId", paymentplan.getPlanid());
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

		return new ModelAndView("/oa/paymentplan/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("paymentplan.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/oa/paymentplan/query", modelMap);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Paymentplan paymentplan = new Paymentplan();
		Tools.populate(paymentplan, params);

		paymentplan.setBudgetid(RequestUtils.getLong(request, "budgetid"));
		paymentplan
				.setPaymemtsum(RequestUtils.getDouble(request, "paymemtsum"));
		paymentplan
				.setPaymentdate(RequestUtils.getDate(request, "paymentdate"));
		paymentplan.setSequence(RequestUtils.getInt(request, "sequence"));

		paymentplanService.save(paymentplan);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/savePaymentplan")
	public byte[] savePaymentplan(HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Paymentplan paymentplan = new Paymentplan();
		try {
			Tools.populate(paymentplan, params);
			paymentplan.setBudgetid(RequestUtils.getLong(request, "budgetid"));
			paymentplan.setPaymemtsum(RequestUtils.getDouble(request,
					"paymemtsum"));
			paymentplan.setPaymentdate(RequestUtils.getDate(request,
					"paymentdate"));
			paymentplan.setSequence(RequestUtils.getInt(request, "sequence"));
			this.paymentplanService.save(paymentplan);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setPaymentplanService(PaymentplanService paymentplanService) {
		this.paymentplanService = paymentplanService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Paymentplan paymentplan = paymentplanService
				.getPaymentplan(RequestUtils.getLong(request, "planid"));

		paymentplan.setBudgetid(RequestUtils.getLong(request, "budgetid"));
		paymentplan
				.setPaymemtsum(RequestUtils.getDouble(request, "paymemtsum"));
		paymentplan
				.setPaymentdate(RequestUtils.getDate(request, "paymentdate"));
		paymentplan.setSequence(RequestUtils.getInt(request, "sequence"));

		paymentplanService.save(paymentplan);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Paymentplan paymentplan = paymentplanService
				.getPaymentplan(RequestUtils.getLong(request, "planid"));
		request.setAttribute("paymentplan", paymentplan);
		JSONObject rowJSON = paymentplan.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("paymentplan.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/oa/paymentplan/view");
	}

}