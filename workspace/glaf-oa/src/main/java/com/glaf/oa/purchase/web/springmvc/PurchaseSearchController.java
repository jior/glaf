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
package com.glaf.oa.purchase.web.springmvc;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
import com.glaf.oa.purchase.model.Purchase;
import com.glaf.oa.purchase.query.PurchaseQuery;
import com.glaf.oa.purchase.service.PurchaseService;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.service.SysDepartmentService;

import com.glaf.core.identity.User;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.Tools;

@Controller("/oa/purchaseSearch")
@RequestMapping("/oa/purchaseSearch")
public class PurchaseSearchController {
	protected static final Log logger = LogFactory
			.getLog(PurchaseSearchController.class);

	protected PurchaseService purchaseService;

	protected SysDepartmentService sysDepartmentService;

	public PurchaseSearchController() {

	}

	@RequestMapping("/init")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		RequestUtils.setRequestParameterToAttribute(request);

		Purchase purchase = new Purchase();
		int areaRole = Integer.parseInt(request.getParameter("areaRole"));
		if (areaRole == 0) {
			// 根据用户部门id 获取整个部门的对象（GZ01）
			SysDepartment curdept = sysDepartmentService.findById(user
					.getDeptId());
			// 根据部门CODE(例如GZ01)截取前2位 作为地区
			String curAreadeptCode = curdept.getCode().substring(0, 2);
			purchase.setArea(curAreadeptCode);
		}

		request.setAttribute("purchase", purchase);
		JSONObject rowJSON = purchase.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		return new ModelAndView("/oa/purchase/search_list", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		PurchaseQuery query = new PurchaseQuery();
		Tools.populate(query, params);
		query.setActorId(loginContext.getActorId());// 只能查找自己
		query.setLoginContext(loginContext);

		User user = RequestUtils.getUser(request);
		/**
		 * 查询条件
		 */
		int areaRole = Integer.parseInt(request.getParameter("areaRole"));
		if (areaRole == 0) {
			// 根据用户部门id 获取整个部门的对象（GZ01）
			SysDepartment curdept = sysDepartmentService.findById(user
					.getDeptId());
			// 根据部门CODE(例如GZ01)截取前2位 作为地区
			String curAreadeptCode = curdept.getCode().substring(0, 2);
			query.setArea(curAreadeptCode);
		} else {
			query.setArea(ParamUtils.getString(params, "area"));
		}
		query.setPurchasenoLike(ParamUtils.getString(params, "purchaseNoLike"));
		query.setAppdateGreaterThanOrEqual(ParamUtils.getDate(params,
				"appdateGreaterThanOrEqual"));
		query.setAppdateLessThanOrEqual(ParamUtils.getDate(params,
				"appdateLessThanOrEqual"));
		query.setCompany(ParamUtils.getString(params, "company"));
		query.setDept(ParamUtils.getString(params, "dept"));
		query.setStatusGreaterThanOrEqual(1);

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
		int total = purchaseService.getPurchaseCountByQueryCriteria(query);
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

			List<Purchase> list = purchaseService.getPurchasesByQueryCriteria(
					start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Purchase purchase : list) {
					JSONObject rowJSON = purchase.toJsonObject();
					rowJSON.put("id", purchase.getPurchaseid());
					rowJSON.put("purchaseId", purchase.getPurchaseid());
					rowJSON.put("startIndex", ++start);
					String areaname = BaseDataManager.getInstance()
							.getStringValue(purchase.getArea(), "area");
					rowJSON.put("areaname", areaname);
					String companyname = BaseDataManager.getInstance()
							.getStringValue(purchase.getCompany(),
									purchase.getArea());
					rowJSON.put("companyname", companyname);
					String appusername = BaseDataManager.getInstance()
							.getStringValue(purchase.getAppuser(), "SYS_USERS");
					rowJSON.put("appusername", appusername);
					String deptname = BaseDataManager.getInstance()
							.getStringValue(purchase.getDept(), "SYS_DEPTS");
					rowJSON.put("deptname", deptname);
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

		return new ModelAndView("/oa/purchase/search_list", modelMap);
	}

	@javax.annotation.Resource
	public void setPurchaseService(PurchaseService purchaseService) {
		this.purchaseService = purchaseService;
	}

	@javax.annotation.Resource
	public void setSysDepartmentService(
			SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
	}

}