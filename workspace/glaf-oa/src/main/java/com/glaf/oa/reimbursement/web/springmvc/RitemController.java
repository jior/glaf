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
import com.glaf.oa.reimbursement.model.Ritem;
import com.glaf.oa.reimbursement.query.RitemQuery;
import com.glaf.oa.reimbursement.service.RitemService;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.User;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;

@Controller("/oa/ritem")
@RequestMapping("/oa/ritem")
public class RitemController {
	protected static final Log logger = LogFactory
			.getLog(RitemController.class);

	protected RitemService ritemService;

	public RitemController() {

	}

	@javax.annotation.Resource
	public void setRitemService(RitemService ritemService) {
		this.ritemService = ritemService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Ritem ritem = new Ritem();
		Tools.populate(ritem, params);

		ritem.setReimbursementid(RequestUtils.getLong(request,
				"reimbursementid"));
		ritem.setFeetype(RequestUtils.getInt(request, "feetype"));
		ritem.setFeedate(RequestUtils.getDate(request, "feedate"));
		ritem.setSubject(request.getParameter("subject"));
		ritem.setCurrency(request.getParameter("currency"));
		ritem.setItemsum(RequestUtils.getDouble(request, "itemsum"));
		ritem.setExrate(RequestUtils.getDouble(request, "exrate"));
		ritem.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		ritem.setUpdateBy(request.getParameter("updateBy"));

		ritem.setCreateBy(actorId);

		ritemService.save(ritem);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveRitem")
	public byte[] saveRitem(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Ritem ritem = new Ritem();
		try {
			Tools.populate(ritem, params);
			ritem.setReimbursementid(RequestUtils.getLong(request,
					"reimbursementid"));
			ritem.setFeetype(RequestUtils.getInt(request, "feetype"));
			ritem.setFeedate(RequestUtils.getDate(request, "feedate"));
			ritem.setSubject(request.getParameter("subject"));
			ritem.setCurrency(request.getParameter("currency"));
			ritem.setItemsum(RequestUtils.getDouble(request, "itemsum"));
			ritem.setExrate(RequestUtils.getDouble(request, "exrate"));
			ritem.setCreateBy(request.getParameter("createBy"));
			ritem.setCreateDate(RequestUtils.getDate(request, "createDate"));
			ritem.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
			ritem.setUpdateBy(request.getParameter("updateBy"));
			ritem.setCreateBy(actorId);
			this.ritemService.save(ritem);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Ritem ritem = ritemService.getRitem(RequestUtils.getLong(request,
				"ritemid"));

		ritem.setReimbursementid(RequestUtils.getLong(request,
				"reimbursementid"));
		ritem.setFeetype(RequestUtils.getInt(request, "feetype"));
		ritem.setFeedate(RequestUtils.getDate(request, "feedate"));
		ritem.setSubject(request.getParameter("subject"));
		ritem.setCurrency(request.getParameter("currency"));
		ritem.setItemsum(RequestUtils.getDouble(request, "itemsum"));
		ritem.setExrate(RequestUtils.getDouble(request, "exrate"));
		ritem.setCreateBy(request.getParameter("createBy"));
		ritem.setCreateDate(RequestUtils.getDate(request, "createDate"));
		ritem.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		ritem.setUpdateBy(request.getParameter("updateBy"));

		ritemService.save(ritem);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, ModelMap modelMap) {
		String ritemids = request.getParameter("ritemids");
		String reimbursementid = request.getParameter("reimbursementid");
		if (StringUtils.isNotEmpty(ritemids)) {
			StringTokenizer token = new StringTokenizer(ritemids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					ritemService.deleteById(Long.valueOf(x),
							Long.valueOf(reimbursementid));
				}
			}
		}
	}

	@ResponseBody
	@RequestMapping("/detail")
	public byte[] detail(HttpServletRequest request) throws IOException {
		// RequestUtils.setRequestParameterToAttribute(request);
		// Map<String, Object> params = RequestUtils.getParameterMap(request);
		Ritem ritem = ritemService.getRitem(RequestUtils.getLong(request,
				"ritemid"));

		JSONObject rowJSON = ritem.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Ritem ritem = ritemService.getRitem(RequestUtils.getLong(request,
				"ritemid"));
		if (ritem != null) {
			request.setAttribute("ritem", ritem);
			JSONObject rowJSON = ritem.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}

		boolean canUpdate = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {

		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (ritem != null) {
			}
		}

		request.setAttribute("canUpdate", canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("ritem.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/oa/ritem/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Ritem ritem = ritemService.getRitem(RequestUtils.getLong(request,
				"ritemid"));
		request.setAttribute("ritem", ritem);
		JSONObject rowJSON = ritem.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("ritem.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/oa/ritem/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("ritem.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/oa/ritem/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		RitemQuery query = new RitemQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);

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

		query.setReimbursementid(Long.valueOf(request
				.getParameter("reimbursementid")));

		JSONObject result = new JSONObject();
		int total = ritemService.getRitemCountByQueryCriteria(query);
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

			List<Ritem> list = ritemService.getRitemsByQueryCriteria(start,
					limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Ritem ritem : list) {
					JSONObject rowJSON = ritem.toJsonObject();
					rowJSON.put("id", ritem.getRitemid());
					rowJSON.put("ritemId", ritem.getRitemid());
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

		return new ModelAndView("/oa/ritem/list", modelMap);
	}

}