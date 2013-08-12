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
package com.glaf.oa.assessscore.web.springmvc;

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
import com.glaf.oa.assessscore.model.Assessscore;
import com.glaf.oa.assessscore.query.AssessscoreQuery;
import com.glaf.oa.assessscore.service.AssessscoreService;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.User;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;

@Controller("/oa/assessscore")
@RequestMapping("/oa/assessscore")
public class AssessscoreController {

	protected static final Log logger = LogFactory
			.getLog(AssessscoreController.class);

	protected AssessscoreService assessscoreService;

	public AssessscoreController() {

	}

	@javax.annotation.Resource
	public void setAssessscoreService(AssessscoreService assessscoreService) {
		this.assessscoreService = assessscoreService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Assessscore assessscore = new Assessscore();
		Tools.populate(assessscore, params);

		assessscore.setContentid(RequestUtils.getLong(request, "contentid"));
		assessscore.setResultid(RequestUtils.getLong(request, "resultid"));
		assessscore.setScore(RequestUtils.getLong(request, "score"));
		assessscore.setCreateBy(request.getParameter("createBy"));
		assessscore.setCreateDate(RequestUtils.getDate(request, "createDate"));
		assessscore.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		assessscore.setUpdateBy(request.getParameter("updateBy"));

		assessscore.setCreateBy(actorId);

		assessscoreService.save(assessscore);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveAssessscore")
	public byte[] saveAssessscore(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Assessscore assessscore = new Assessscore();
		try {
			Tools.populate(assessscore, params);
			assessscore
					.setContentid(RequestUtils.getLong(request, "contentid"));
			assessscore.setResultid(RequestUtils.getLong(request, "resultid"));
			assessscore.setScore(RequestUtils.getLong(request, "score"));
			assessscore.setCreateBy(request.getParameter("createBy"));
			assessscore.setCreateDate(RequestUtils.getDate(request,
					"createDate"));
			assessscore.setUpdateDate(RequestUtils.getDate(request,
					"updateDate"));
			assessscore.setUpdateBy(request.getParameter("updateBy"));
			assessscore.setCreateBy(actorId);
			this.assessscoreService.save(assessscore);

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

		Assessscore assessscore = assessscoreService
				.getAssessscore(RequestUtils.getLong(request, "scoreid"));

		assessscore.setContentid(RequestUtils.getLong(request, "contentid"));
		assessscore.setResultid(RequestUtils.getLong(request, "resultid"));
		assessscore.setScore(RequestUtils.getLong(request, "score"));
		assessscore.setCreateBy(request.getParameter("createBy"));
		assessscore.setCreateDate(RequestUtils.getDate(request, "createDate"));
		assessscore.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		assessscore.setUpdateBy(request.getParameter("updateBy"));

		assessscoreService.save(assessscore);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Long scoreid = RequestUtils.getLong(request, "scoreid");
		String scoreids = request.getParameter("scoreids");
		if (StringUtils.isNotEmpty(scoreids)) {
			StringTokenizer token = new StringTokenizer(scoreids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Assessscore assessscore = assessscoreService
							.getAssessscore(Long.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					if (assessscore != null
							&& (StringUtils.equals(assessscore.getCreateBy(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						// assessscore.setDeleteFlag(1);
						assessscoreService.save(assessscore);
					}
				}
			}
		} else if (scoreid != null) {
			Assessscore assessscore = assessscoreService.getAssessscore(Long
					.valueOf(scoreid));
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (assessscore != null
					&& (StringUtils.equals(assessscore.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				// assessscore.setDeleteFlag(1);
				assessscoreService.save(assessscore);
			}
		}
	}

	@ResponseBody
	@RequestMapping("/detail")
	public byte[] detail(HttpServletRequest request) throws IOException {
		Assessscore assessscore = assessscoreService
				.getAssessscore(RequestUtils.getLong(request, "scoreid"));

		JSONObject rowJSON = assessscore.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		Assessscore assessscore = assessscoreService
				.getAssessscore(RequestUtils.getLong(request, "scoreid"));
		if (assessscore != null) {
			request.setAttribute("assessscore", assessscore);
			JSONObject rowJSON = assessscore.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}

		boolean canUpdate = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {

		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (assessscore != null) {
				canUpdate = true;
			}
		}

		request.setAttribute("canUpdate", canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("assessscore.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/oa/assessscore/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		Assessscore assessscore = assessscoreService
				.getAssessscore(RequestUtils.getLong(request, "scoreid"));
		request.setAttribute("assessscore", assessscore);
		JSONObject rowJSON = assessscore.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("assessscore.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/oa/assessscore/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("assessscore.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/oa/assessscore/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		AssessscoreQuery query = new AssessscoreQuery();
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
		int total = assessscoreService
				.getAssessscoreCountByQueryCriteria(query);
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

			List<Assessscore> list = assessscoreService
					.getAssessscoresByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Assessscore assessscore : list) {
					JSONObject rowJSON = assessscore.toJsonObject();
					// rowJSON.put("id", assessscore.getId());
					// rowJSON.put("assessscoreId", assessscore.getId());
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

		return new ModelAndView("/oa/assessscore/list", modelMap);
	}

}