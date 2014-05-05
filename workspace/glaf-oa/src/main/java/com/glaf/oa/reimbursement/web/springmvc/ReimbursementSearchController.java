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
package com.glaf.oa.reimbursement.web.springmvc;

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

import com.glaf.oa.reimbursement.model.Reimbursement;
import com.glaf.oa.reimbursement.query.ReimbursementQuery;
import com.glaf.oa.reimbursement.service.ReimbursementService;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.service.SysDepartmentService;

import com.glaf.core.identity.User;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.Tools;

@Controller("/oa/reimbursementSearch")
@RequestMapping("/oa/reimbursementSearch")
public class ReimbursementSearchController {
	protected static final Log logger = LogFactory
			.getLog(ReimbursementSearchController.class);

	protected ReimbursementService reimbursementService;

	protected SysDepartmentService sysDepartmentService;

	public ReimbursementSearchController() {

	}

	@RequestMapping("/init")
	public ModelAndView init(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		RequestUtils.setRequestParameterToAttribute(request);

		Reimbursement reimbursement = new Reimbursement();
		int areaRole = Integer.parseInt(request.getParameter("areaRole"));
		if (areaRole == 0) {
			// 根据用户部门id 获取整个部门的对象（GZ01）
			SysDepartment curdept = sysDepartmentService.findById(user
					.getDeptId());
			// 根据部门CODE(例如GZ01)截取前2位 作为地区
			String curAreadeptCode = curdept.getCode().substring(0, 2);
			reimbursement.setArea(curAreadeptCode);
		}

		request.setAttribute("reimbursement", reimbursement);
		JSONObject rowJSON = reimbursement.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		return new ModelAndView("/oa/reimbursement/search_list", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ReimbursementQuery query = new ReimbursementQuery();
		Tools.populate(query, params);
		/**
		 * 此处业务逻辑需自行调整
		 */

		int areaRole = Integer.parseInt(request.getParameter("areaRole"));
		User user = RequestUtils.getUser(request);
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
		int total = reimbursementService
				.getReimbursementCountByQueryCriteria(query);
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

			List<Reimbursement> list = reimbursementService
					.getReimbursementsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Reimbursement reimbursement : list) {
					JSONObject rowJSON = reimbursement.toJsonObject();
					rowJSON.put("id", reimbursement.getReimbursementid());
					rowJSON.put("reimbursementId",
							reimbursement.getReimbursementid());
					rowJSON.put("startIndex", ++start);
					String areaname = BaseDataManager.getInstance()
							.getStringValue(reimbursement.getArea(), "area");
					rowJSON.put("areaname", areaname);
					String companyname = BaseDataManager.getInstance()
							.getStringValue(reimbursement.getCompany(),
									reimbursement.getArea());
					rowJSON.put("companyname", companyname);
					String appusername = BaseDataManager.getInstance()
							.getStringValue(reimbursement.getAppuser(),
									"SYS_USERS");
					rowJSON.put("appusername", appusername);
					String deptname = BaseDataManager.getInstance()
							.getStringValue(reimbursement.getDept(),
									"SYS_DEPTS");
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

		return new ModelAndView("/oa/reimbursement/search_list", modelMap);
	}

	@javax.annotation.Resource
	public void setReimbursementService(
			ReimbursementService reimbursementService) {
		this.reimbursementService = reimbursementService;
	}

	@javax.annotation.Resource
	public void setSysDepartmentService(
			SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
	}
}