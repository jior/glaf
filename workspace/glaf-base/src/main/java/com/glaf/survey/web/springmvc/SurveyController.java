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

package com.glaf.survey.web.springmvc;

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
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;
import com.glaf.survey.domain.*;
import com.glaf.survey.query.*;
import com.glaf.survey.service.*;

@Controller("/base/survey")
@RequestMapping("/base/survey")
public class SurveyController {
	protected static final Log logger = LogFactory
			.getLog(SurveyController.class);

	protected SurveyService surveyService;

	public SurveyController() {

	}

	@RequestMapping("/choose")
	public ModelAndView choose(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("survey.choose");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/survey/choose_survey", modelMap);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Long id = RequestUtils.getLong(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Survey survey = surveyService.getSurvey(Long.valueOf(x));
					if (survey != null
							&& (StringUtils.equals(survey.getCreateBy(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						surveyService.deleteById(survey.getId());
					}
				}
			}
		} else if (id != null) {
			Survey survey = surveyService.getSurvey(Long.valueOf(id));
			if (survey != null
					&& (StringUtils.equals(survey.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				surveyService.deleteById(survey.getId());
			}
		}
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);

		RequestUtils.setRequestParameterToAttribute(request);

		Survey survey = surveyService.getSurvey(RequestUtils.getLong(request,
				"id"));
		if (survey != null
				&& (StringUtils.equals(survey.getCreateBy(),
						loginContext.getActorId()) || loginContext
						.isSystemAdministrator())) {
			request.setAttribute("survey", survey);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("survey.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/survey/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SurveyQuery query = new SurveyQuery();
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
			limit = Paging.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = surveyService.getSurveyCountByQueryCriteria(query);
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

			List<Survey> list = surveyService.getSurveysByQueryCriteria(start,
					limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Survey survey : list) {
					JSONObject rowJSON = survey.toJsonObject();
					rowJSON.put("id", survey.getId());
					rowJSON.put("surveyId", survey.getId());
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

		String requestURI = request.getRequestURI();
		logger.debug("requestURI:" + requestURI);
		logger.debug("queryString:" + request.getQueryString());
		request.setAttribute(
				"fromUrl",
				RequestUtils.encodeURL(requestURI + "?"
						+ request.getQueryString()));

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/modules/survey/list", modelMap);
	}

	@RequestMapping("/query/{accountId}")
	public ModelAndView query(@PathVariable("accountId") Long accountId,
			HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("survey.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/survey/query", modelMap);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		Survey survey = new Survey();
		Tools.populate(survey, params);

		survey.setTitle(request.getParameter("title"));
		survey.setContent(request.getParameter("content"));
		survey.setIcon(request.getParameter("icon"));
		survey.setKeywords(request.getParameter("keywords"));
		survey.setStatus(RequestUtils.getInt(request, "status"));
		survey.setShowIconFlag(RequestUtils.getInt(request, "showIconFlag"));
		survey.setSignFlag(RequestUtils.getInt(request, "signFlag"));
		survey.setMultiFlag(RequestUtils.getInt(request, "multiFlag"));
		survey.setLimitFlag(RequestUtils.getInt(request, "limitFlag"));
		survey.setLimitTimeInterval(RequestUtils.getInt(request,
				"limitTimeInterval"));
		survey.setResultFlag(RequestUtils.getInt(request, "resultFlag"));
		survey.setStartDate(RequestUtils.getDate(request, "startDate"));
		survey.setEndDate(RequestUtils.getDate(request, "endDate"));
		survey.setRelationIds(request.getParameter("relationIds"));
		survey.setCreateBy(actorId);

		surveyService.save(survey);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveSurvey")
	public byte[] saveSurvey(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug("params:" + params);
		Survey survey = new Survey();
		try {
			Tools.populate(survey, params);

			survey.setTitle(request.getParameter("title"));
			survey.setContent(request.getParameter("content"));
			survey.setIcon(request.getParameter("icon"));
			survey.setKeywords(request.getParameter("keywords"));
			survey.setStatus(RequestUtils.getInt(request, "status"));
			survey.setShowIconFlag(RequestUtils.getInt(request, "showIconFlag"));
			survey.setSignFlag(RequestUtils.getInt(request, "signFlag"));
			survey.setMultiFlag(RequestUtils.getInt(request, "multiFlag"));
			survey.setLimitFlag(RequestUtils.getInt(request, "limitFlag"));
			survey.setLimitTimeInterval(RequestUtils.getInt(request,
					"limitTimeInterval"));
			survey.setResultFlag(RequestUtils.getInt(request, "resultFlag"));
			survey.setStartDate(RequestUtils.getDate(request, "startDate"));
			survey.setEndDate(RequestUtils.getDate(request, "endDate"));
			survey.setRelationIds(request.getParameter("relationIds"));
			survey.setCreateBy(actorId);

			Map<Integer, SurveyItem> dataMap = new java.util.HashMap<Integer, SurveyItem>();
			String[] titleArray = request.getParameterValues("item_title");
			if (titleArray != null && titleArray.length > 0) {
				int index = 0;
				for (String t : titleArray) {
					SurveyItem item = new SurveyItem();
					item.setName(t);
					item.setValue(String.valueOf(index));
					survey.addItem(item);
					dataMap.put(index, item);
					index++;
				}
			}

			String[] sortArray = request.getParameterValues("item_sort");
			if (sortArray != null && sortArray.length > 0) {
				int index = 0;
				for (String sort : sortArray) {
					SurveyItem item = dataMap.get(index++);
					if (item != null && StringUtils.isNotEmpty(sort)
							&& StringUtils.isNumeric(sort)) {
						item.setSort(Integer.parseInt(sort));
					}
				}
			}

			this.surveyService.save(survey);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setSurveyService(SurveyService surveyService) {
		this.surveyService = surveyService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);

		Survey survey = surveyService.getSurvey(RequestUtils.getLong(request,
				"id"));

		if (survey != null
				&& (StringUtils.equals(survey.getCreateBy(),
						loginContext.getActorId()) || loginContext
						.isSystemAdministrator())) {
			survey.setTitle(request.getParameter("title"));
			survey.setContent(request.getParameter("content"));
			survey.setIcon(request.getParameter("icon"));
			survey.setKeywords(request.getParameter("keywords"));
			survey.setStatus(RequestUtils.getInt(request, "status"));
			survey.setShowIconFlag(RequestUtils.getInt(request, "showIconFlag"));
			survey.setSignFlag(RequestUtils.getInt(request, "signFlag"));
			survey.setMultiFlag(RequestUtils.getInt(request, "multiFlag"));
			survey.setLimitFlag(RequestUtils.getInt(request, "limitFlag"));
			survey.setLimitTimeInterval(RequestUtils.getInt(request,
					"limitTimeInterval"));
			survey.setResultFlag(RequestUtils.getInt(request, "resultFlag"));
			survey.setStartDate(RequestUtils.getDate(request, "startDate"));
			survey.setEndDate(RequestUtils.getDate(request, "endDate"));
			survey.setRelationIds(request.getParameter("relationIds"));
			surveyService.save(survey);
		}

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		RequestUtils.setRequestParameterToAttribute(request);
		Survey survey = surveyService.getSurvey(RequestUtils.getLong(request,
				"id"));
		if (survey != null
				&& (StringUtils.equals(survey.getCreateBy(),
						loginContext.getActorId()) || loginContext
						.isSystemAdministrator())) {
			request.setAttribute("survey", survey);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("survey.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/modules/survey/view");
	}

}
