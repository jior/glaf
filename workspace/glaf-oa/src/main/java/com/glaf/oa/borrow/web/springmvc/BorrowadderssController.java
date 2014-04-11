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
import com.glaf.oa.borrow.model.Borrowadderss;
import com.glaf.oa.borrow.query.BorrowadderssQuery;
import com.glaf.oa.borrow.service.BorrowadderssService;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.User;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;

@Controller("/oa/borrowadderss")
@RequestMapping("/oa/borrowadderss")
public class BorrowadderssController {
	protected static final Log logger = LogFactory
			.getLog(BorrowadderssController.class);

	protected BorrowadderssService borrowadderssService;

	public BorrowadderssController() {

	}

	@ResponseBody
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Long addressid = RequestUtils.getLong(request, "addressid");
		String addressids = request.getParameter("addressids");
		if (StringUtils.isNotEmpty(addressids)) {
			StringTokenizer token = new StringTokenizer(addressids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Borrowadderss borrowadderss = borrowadderssService
							.getBorrowadderss(Long.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					if (borrowadderss != null
							&& (StringUtils.equals(borrowadderss.getCreateBy(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						borrowadderssService.save(borrowadderss);
					}
				}
			}
		} else if (addressid != null) {
			Borrowadderss borrowadderss = borrowadderssService
					.getBorrowadderss(Long.valueOf(addressid));
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (borrowadderss != null
					&& (StringUtils.equals(borrowadderss.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				borrowadderssService.save(borrowadderss);
			}
		}
	}

	@ResponseBody
	@RequestMapping("/detail")
	public byte[] detail(HttpServletRequest request) throws IOException {
		// RequestUtils.setRequestParameterToAttribute(request);
		// Map<String, Object> params = RequestUtils.getParameterMap(request);
		Borrowadderss borrowadderss = borrowadderssService
				.getBorrowadderss(RequestUtils.getLong(request, "addressid"));

		JSONObject rowJSON = borrowadderss.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		Borrowadderss borrowadderss = borrowadderssService
				.getBorrowadderss(RequestUtils.getLong(request, "addressid"));
		if (borrowadderss != null) {
			request.setAttribute("borrowadderss", borrowadderss);
			JSONObject rowJSON = borrowadderss.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}

		boolean canUpdate = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {

		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (borrowadderss != null) {
			}
		}

		request.setAttribute("canUpdate", canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("borrowadderss.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/oa/borrowadderss/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		BorrowadderssQuery query = new BorrowadderssQuery();
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
		int total = borrowadderssService
				.getBorrowadderssCountByQueryCriteria(query);
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

			List<Borrowadderss> list = borrowadderssService
					.getBorrowaddersssByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Borrowadderss borrowadderss : list) {
					JSONObject rowJSON = borrowadderss.toJsonObject();
					rowJSON.put("id", borrowadderss.getAddressid());
					rowJSON.put("borrowadderssId", borrowadderss.getAddressid());
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

		return new ModelAndView("/oa/borrowadderss/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("borrowadderss.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/oa/borrowadderss/query", modelMap);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Borrowadderss borrowadderss = new Borrowadderss();
		Tools.populate(borrowadderss, params);

		borrowadderss.setBorrowid(RequestUtils.getLong(request, "borrowid"));
		borrowadderss.setStart(request.getParameter("start"));
		borrowadderss.setReach(request.getParameter("reach"));
		borrowadderss.setRemark(request.getParameter("remark"));
		borrowadderss.setCreateBy(request.getParameter("createBy"));
		borrowadderss
				.setCreateDate(RequestUtils.getDate(request, "createDate"));
		borrowadderss
				.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		borrowadderss.setUpdateBy(request.getParameter("updateBy"));

		borrowadderss.setCreateBy(actorId);

		borrowadderssService.save(borrowadderss);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveBorrowadderss")
	public byte[] saveBorrowadderss(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Borrowadderss borrowadderss = new Borrowadderss();
		try {
			Tools.populate(borrowadderss, params);
			borrowadderss
					.setBorrowid(RequestUtils.getLong(request, "borrowid"));
			borrowadderss.setStart(request.getParameter("start"));
			borrowadderss.setReach(request.getParameter("reach"));
			borrowadderss.setRemark(request.getParameter("remark"));
			borrowadderss.setCreateBy(request.getParameter("createBy"));
			borrowadderss.setCreateDate(RequestUtils.getDate(request,
					"createDate"));
			borrowadderss.setUpdateDate(RequestUtils.getDate(request,
					"updateDate"));
			borrowadderss.setUpdateBy(request.getParameter("updateBy"));
			borrowadderss.setCreateBy(actorId);
			this.borrowadderssService.save(borrowadderss);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setBorrowadderssService(
			BorrowadderssService borrowadderssService) {
		this.borrowadderssService = borrowadderssService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Borrowadderss borrowadderss = borrowadderssService
				.getBorrowadderss(RequestUtils.getLong(request, "addressid"));

		borrowadderss.setBorrowid(RequestUtils.getLong(request, "borrowid"));
		borrowadderss.setStart(request.getParameter("start"));
		borrowadderss.setReach(request.getParameter("reach"));
		borrowadderss.setRemark(request.getParameter("remark"));
		borrowadderss.setCreateBy(request.getParameter("createBy"));
		borrowadderss
				.setCreateDate(RequestUtils.getDate(request, "createDate"));
		borrowadderss
				.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		borrowadderss.setUpdateBy(request.getParameter("updateBy"));

		borrowadderssService.save(borrowadderss);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		Borrowadderss borrowadderss = borrowadderssService
				.getBorrowadderss(RequestUtils.getLong(request, "addressid"));
		request.setAttribute("borrowadderss", borrowadderss);
		JSONObject rowJSON = borrowadderss.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("borrowadderss.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/oa/borrowadderss/view");
	}

}