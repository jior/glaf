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
package com.glaf.oa.budget.web.springmvc;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
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
import com.glaf.oa.budget.model.Budget;
import com.glaf.oa.budget.query.BudgetQuery;
import com.glaf.oa.budget.service.BudgetService;

import com.glaf.core.security.LoginContext;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.Tools;

@Controller("/oa/budgetView")
@RequestMapping({ "/oa/budgetView" })
public class BudgetViewController {
	protected static final Log logger = LogFactory
			.getLog(BudgetViewController.class);

	protected BudgetService budgetService;

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		BudgetQuery query = new BudgetQuery();
		Tools.populate(query, params);
		query.setLoginContext(loginContext);

		query.setStatus(Integer.valueOf(2));

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
			limit = 20;
		}

		JSONObject result = new JSONObject();
		int total = this.budgetService.getBudgetCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", Integer.valueOf(total));
			result.put("totalCount", Integer.valueOf(total));
			result.put("totalRecords", Integer.valueOf(total));
			result.put("start", Integer.valueOf(start));
			result.put("startIndex", Integer.valueOf(start));
			result.put("limit", Integer.valueOf(limit));
			result.put("pageSize", Integer.valueOf(limit));

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortOrder(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			List<Budget> list = this.budgetService.getBudgetsByQueryCriteria(
					start, limit, query);

			if ((list != null) && (!list.isEmpty())) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);
				for (Budget budget : list) {
					JSONObject rowJSON = budget.toJsonObject();
					rowJSON.put("id", budget.getBudgetid());
					rowJSON.put("budgetId", budget.getBudgetid());
					start++;
					rowJSON.put("startIndex", Integer.valueOf(start));
					rowsJSON.add(rowJSON);
				}
			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", Integer.valueOf(total));
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

		return new ModelAndView("/oa/common/budget_view", modelMap);
	}

	@Resource
	public void setBudgetService(BudgetService budgetService) {
		this.budgetService = budgetService;
	}
}