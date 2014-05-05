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
package com.glaf.oa.assesssort.web.springmvc;

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
import com.glaf.oa.assesssort.model.Assesssort;
import com.glaf.oa.assesssort.model.AssesssortType;
import com.glaf.oa.assesssort.query.AssesssortQuery;
import com.glaf.oa.assesssort.service.AssesssortService;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.User;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;

@Controller("/oa/assesssort")
@RequestMapping("/oa/assesssort")
public class AssesssortController {

	protected static final Log logger = LogFactory
			.getLog(AssesssortController.class);

	protected AssesssortService assesssortService;

	public AssesssortController() {
		
	}

	@ResponseBody
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Long assesssortid = RequestUtils.getLong(request, "assesssortid");
		String assesssortids = request.getParameter("assesssortids");
		if (StringUtils.isNotEmpty(assesssortids)) {
			StringTokenizer token = new StringTokenizer(assesssortids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Assesssort assesssort = assesssortService
							.getAssesssort(Long.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					if (assesssort != null
							&& (StringUtils.equals(assesssort.getCreateBy(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						// assesssort.setDeleteFlag(1);
						assesssortService.save(assesssort);
					}
				}
			}
		} else if (assesssortid != null) {
			Assesssort assesssort = assesssortService.getAssesssort(Long
					.valueOf(assesssortid));
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (assesssort != null
					&& (StringUtils.equals(assesssort.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				// assesssort.setDeleteFlag(1);
				assesssortService.save(assesssort);
			}
		}
	}

	@ResponseBody
	@RequestMapping("/detail")
	public byte[] detail(HttpServletRequest request) throws IOException {
		// RequestUtils.setRequestParameterToAttribute(request);
		// Map<String, Object> params = RequestUtils.getParameterMap(request);
		Assesssort assesssort = assesssortService.getAssesssort(RequestUtils
				.getLong(request, "assesssortid"));

		JSONObject rowJSON = assesssort.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		Assesssort assesssort = assesssortService.getAssesssort(RequestUtils
				.getLong(request, "assesssortid"));
		if (assesssort != null) {
			request.setAttribute("assesssort", assesssort);
			JSONObject rowJSON = assesssort.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}

		boolean canUpdate = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {

		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (assesssort != null) {
				canUpdate = true;
			}
		}

		request.setAttribute("canUpdate", canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("assesssort.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/oa/assesssort/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {

		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		AssesssortQuery query = new AssesssortQuery();
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
		int total = assesssortService.getAssesssortCountByQueryCriteria(query);
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

			List<Assesssort> list = assesssortService
					.getAssesssortsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Assesssort assesssort : list) {
					JSONObject rowJSON = assesssort.toJsonObject();
					// rowJSON.put("id", assesssort.getId());
					// rowJSON.put("assesssortId", assesssort.getId());
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

		return new ModelAndView("/oa/assesssort/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {

		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("assesssort.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/oa/assesssort/query", modelMap);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Assesssort assesssort = new Assesssort();
		Tools.populate(assesssort, params);

		assesssort.setQustionid(RequestUtils.getLong(request, "qustionid"));
		assesssort.setSortid(RequestUtils.getLong(request, "sortid"));
		assesssort.setCreateBy(request.getParameter("createBy"));
		assesssort.setCreateDate(RequestUtils.getDate(request, "createDate"));
		assesssort.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		assesssort.setUpdateBy(request.getParameter("updateBy"));

		assesssort.setCreateBy(actorId);

		assesssortService.save(assesssort);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveAssesssort")
	public byte[] saveAssesssort(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Assesssort assesssort = new Assesssort();
		try {
			Tools.populate(assesssort, params);
			assesssort.setQustionid(RequestUtils.getLong(request, "qustionid"));
			assesssort.setSortid(RequestUtils.getLong(request, "sortid"));
			assesssort.setCreateBy(request.getParameter("createBy"));
			assesssort.setCreateDate(RequestUtils
					.getDate(request, "createDate"));
			assesssort.setUpdateDate(RequestUtils
					.getDate(request, "updateDate"));
			assesssort.setUpdateBy(request.getParameter("updateBy"));
			assesssort.setCreateBy(actorId);
			this.assesssortService.save(assesssort);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	/**
	 * 选择指标类型
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/selectAssessType")
	public ModelAndView selectAssessType(HttpServletRequest request,
			ModelMap modelMap) {
		List<AssesssortType> assessSortTypeList = assesssortService
				.getAssesssortsType("ASSESS_CLASS");
		modelMap.put("assessSortTypeList", assessSortTypeList);
		Object questionId = request.getParameter("questionId");
		modelMap.put("questionId", questionId);

		return new ModelAndView("/oa/assesssort/selectAssessinfo", modelMap);
	}

	@javax.annotation.Resource
	public void setAssesssortService(AssesssortService assesssortService) {

		this.assesssortService = assesssortService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Assesssort assesssort = assesssortService.getAssesssort(RequestUtils
				.getLong(request, "assesssortid"));

		assesssort.setQustionid(RequestUtils.getLong(request, "qustionid"));
		assesssort.setSortid(RequestUtils.getLong(request, "sortid"));
		assesssort.setCreateBy(request.getParameter("createBy"));
		assesssort.setCreateDate(RequestUtils.getDate(request, "createDate"));
		assesssort.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		assesssort.setUpdateBy(request.getParameter("updateBy"));

		assesssortService.save(assesssort);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		Assesssort assesssort = assesssortService.getAssesssort(RequestUtils
				.getLong(request, "assesssortid"));
		request.setAttribute("assesssort", assesssort);
		JSONObject rowJSON = assesssort.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("assesssort.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/oa/assesssort/view");
	}

}