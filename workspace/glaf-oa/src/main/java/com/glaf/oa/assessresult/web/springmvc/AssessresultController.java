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
package com.glaf.oa.assessresult.web.springmvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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
import com.glaf.oa.assessquestion.model.Assessquestion;
import com.glaf.oa.assessquestion.service.AssessquestionService;
import com.glaf.oa.assessresult.model.Assessresult;
import com.glaf.oa.assessresult.model.JobEvaluation;
import com.glaf.oa.assessresult.query.AssessresultQuery;
import com.glaf.oa.assessresult.service.AssessresultService;
import com.glaf.oa.assessscore.model.Assessscore;
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

@Controller("/oa/assessresult")
@RequestMapping("/oa/assessresult")
public class AssessresultController {
	protected static final Log logger = LogFactory
			.getLog(AssessresultController.class);

	protected AssessresultService assessresultService;

	protected AssessquestionService assessquestionService;

	protected AssessscoreService assessscoreService;

	public AssessresultController() {

	}

	/**
	 * 岗位考核查询列表
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/assessresultJson")
	@ResponseBody
	public byte[] assessresultJson(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		AssessresultQuery query = new AssessresultQuery();
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
		int areaRole = Integer.parseInt(request.getParameter("areaRole"));
		if (areaRole == 0) {
			query.setEvaluation(loginContext.getActorId());
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
		int total = assessresultService
				.getAssessresultCountByQueryCriteria(query);
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

			List<Assessresult> list = assessresultService
					.getAssessresultsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Assessresult assessresult : list) {
					JSONObject rowJSON = assessresult.toJsonObject();
					// rowJSON.put("id", assessresult.getId());
					// rowJSON.put("assessresultId", assessresult.getId());
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

	@ResponseBody
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Long resultid = RequestUtils.getLong(request, "resultid");
		String resultids = request.getParameter("resultids");
		if (StringUtils.isNotEmpty(resultids)) {
			StringTokenizer token = new StringTokenizer(resultids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Assessresult assessresult = assessresultService
							.getAssessresult(Long.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					if (assessresult != null
							&& (StringUtils.equals(assessresult.getCreateBy(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						// assessresult.setDeleteFlag(1);
						assessresultService.save(assessresult);
					}
				}
			}
		} else if (resultid != null) {
			Assessresult assessresult = assessresultService
					.getAssessresult(Long.valueOf(resultid));
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (assessresult != null
					&& (StringUtils.equals(assessresult.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				// assessresult.setDeleteFlag(1);
				assessresultService.save(assessresult);
			}
		}
	}

	@ResponseBody
	@RequestMapping("/detail")
	public byte[] detail(HttpServletRequest request) throws IOException {
		// RequestUtils.setRequestParameterToAttribute(request);
		// Map<String, Object> params = RequestUtils.getParameterMap(request);
		Assessresult assessresult = assessresultService
				.getAssessresult(RequestUtils.getLong(request, "resultid"));

		JSONObject rowJSON = assessresult.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		Assessresult assessresult = assessresultService
				.getAssessresult(RequestUtils.getLong(request, "resultid"));
		if (assessresult != null) {
			request.setAttribute("assessresult", assessresult);
			JSONObject rowJSON = assessresult.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}

		boolean canUpdate = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {

		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (assessresult != null) {
				canUpdate = true;
			}
		}

		request.setAttribute("canUpdate", canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("assessresult.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/oa/assessresult/edit", modelMap);
	}

	@RequestMapping("/jobEvaluate")
	public ModelAndView jobEvaluate(HttpServletRequest request,
			ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		AssessresultQuery query = new AssessresultQuery();
		query.setQustionid(RequestUtils.getLong(request, "questionId"));
		List<JobEvaluation> jobEvaluations = assessresultService
				.listForJob(query);
		Assessquestion assessquestion = assessquestionService
				.getAssessquestion(query.getQustionid());

		// 合并单元格参数
		Map<String, List<JobEvaluation>> map = new HashMap<String, List<JobEvaluation>>();
		List<String> dictorList = new ArrayList<String>();
		List<JobEvaluation> list = null;
		Double allScore = 0D;
		for (JobEvaluation jobEvaluation : jobEvaluations) {
			if (!dictorList.contains(jobEvaluation.getDictoryName())) {
				dictorList.add(jobEvaluation.getDictoryName());
			}
			allScore += jobEvaluation.getStandard();
			if (map.containsKey(jobEvaluation.getTreeName())) {
				list = map.get(jobEvaluation.getTreeName());
				list.add(jobEvaluation);
			} else {
				list = new ArrayList<JobEvaluation>();
				list.add(jobEvaluation);
				map.put(jobEvaluation.getTreeName(), list);
			}
		}

		int oneIndex = 0;
		int twoIndex = 0;
		JSONArray rowsJSON = new JSONArray();
		JSONArray rowsJSONForTwo = new JSONArray();
		Set<Entry<String, List<JobEvaluation>>> set = map.entrySet();
		Iterator<Entry<String, List<JobEvaluation>>> it = set.iterator();
		while (it.hasNext()) {
			Map.Entry<String, List<JobEvaluation>> entryMap = (Map.Entry<String, List<JobEvaluation>>) it
					.next();
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("index", oneIndex);
			jsonObj.put("rowspan", entryMap.getValue().size());
			rowsJSON.add(jsonObj);

			// 第2列合并
			Map<String, List<JobEvaluation>> subMap = new HashMap<String, List<JobEvaluation>>();
			List<JobEvaluation> subList = entryMap.getValue();
			for (JobEvaluation jobEvaluation : subList) {
				if (subMap.containsKey(jobEvaluation.getDictoryName())) {
					List<JobEvaluation> jobList = subMap.get(jobEvaluation
							.getDictoryName());
					jobList.add(jobEvaluation);
				} else {
					List<JobEvaluation> jobList = new ArrayList<JobEvaluation>();
					jobList.add(jobEvaluation);
					subMap.put(jobEvaluation.getDictoryName(), jobList);
				}
			}
			for (int i = 0; i < dictorList.size(); i++) {
				List<JobEvaluation> ub = subMap.get(dictorList.get(i));
				if (ub == null || ub.size() == 0) {
					continue;
				}
				JSONObject subJsonObj = new JSONObject();
				subJsonObj.put("subindex", twoIndex);
				twoIndex += ub.size();
				subJsonObj.put("subrowspan", ub.size());
				rowsJSONForTwo.add(subJsonObj);
			}

			oneIndex += entryMap.getValue().size();
		}

		Assessresult assessresult = new Assessresult();
		assessresult.setEvaluation(actorId);
		request.setAttribute("assessquestion", assessquestion);
		request.setAttribute("allScore", allScore);
		request.setAttribute("assessresult", assessresult);
		request.setAttribute("contentLength", jobEvaluations.size());
		request.setAttribute("mergeCells", rowsJSON);
		request.setAttribute("subMergeCells", rowsJSONForTwo);
		return new ModelAndView("/oa/assessresult/jobEvaluation");
	}

	@RequestMapping("/jobEvaluationView")
	public ModelAndView jobEvaluateView(HttpServletRequest request,
			ModelMap modelMap) {
		AssessresultQuery query = new AssessresultQuery();
		query.setQustionid(RequestUtils.getLong(request, "questionId"));
		List<JobEvaluation> jobEvaluations = assessresultService
				.listForJob(query);
		Assessquestion assessquestion = assessquestionService
				.getAssessquestion(query.getQustionid());

		Map<String, List<JobEvaluation>> map = new HashMap<String, List<JobEvaluation>>();
		List<String> dictorList = new ArrayList<String>();
		List<JobEvaluation> list = null;
		Double allScore = 0D;
		for (JobEvaluation jobEvaluation : jobEvaluations) {
			if (!dictorList.contains(jobEvaluation.getDictoryName())) {
				dictorList.add(jobEvaluation.getDictoryName());
			}
			allScore += jobEvaluation.getStandard();
			if (map.containsKey(jobEvaluation.getTreeName())) {
				list = map.get(jobEvaluation.getTreeName());
				list.add(jobEvaluation);
			} else {
				list = new ArrayList<JobEvaluation>();
				list.add(jobEvaluation);
				map.put(jobEvaluation.getTreeName(), list);
			}
		}

		int oneIndex = 0;
		int twoIndex = 0;
		JSONArray rowsJSON = new JSONArray();
		JSONArray rowsJSONForTwo = new JSONArray();
		Set<Entry<String, List<JobEvaluation>>> set = map.entrySet();
		Iterator<Entry<String, List<JobEvaluation>>> it = set.iterator();
		while (it.hasNext()) {
			Map.Entry<String, List<JobEvaluation>> entryMap = (Map.Entry<String, List<JobEvaluation>>) it
					.next();
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("index", oneIndex);
			jsonObj.put("rowspan", entryMap.getValue().size());
			rowsJSON.add(jsonObj);

			// 第2列合并
			Map<String, List<JobEvaluation>> subMap = new HashMap<String, List<JobEvaluation>>();
			List<JobEvaluation> subList = entryMap.getValue();
			for (JobEvaluation jobEvaluation : subList) {
				if (subMap.containsKey(jobEvaluation.getDictoryName())) {
					List<JobEvaluation> jobList = subMap.get(jobEvaluation
							.getDictoryName());
					jobList.add(jobEvaluation);
				} else {
					List<JobEvaluation> jobList = new ArrayList<JobEvaluation>();
					jobList.add(jobEvaluation);
					subMap.put(jobEvaluation.getDictoryName(), jobList);
				}
			}

			for (int i = 0; i < dictorList.size(); i++) {
				List<JobEvaluation> ub = subMap.get(dictorList.get(i));
				if (ub == null || ub.size() == 0) {
					continue;
				}
				JSONObject subJsonObj = new JSONObject();
				subJsonObj.put("subindex", twoIndex);
				twoIndex += ub.size();
				subJsonObj.put("subrowspan", ub.size());
				rowsJSONForTwo.add(subJsonObj);
			}

			oneIndex += entryMap.getValue().size();
		}

		request.setAttribute("allScore", allScore);
		request.setAttribute("assessquestion", assessquestion);
		request.setAttribute("mergeCells", rowsJSON);
		request.setAttribute("subMergeCells", rowsJSONForTwo);
		return new ModelAndView("/oa/assessresult/jobEvaluationView");
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		AssessresultQuery query = new AssessresultQuery();
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
		int total = assessresultService
				.getAssessresultCountByQueryCriteria(query);
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

			List<Assessresult> list = assessresultService
					.getAssessresultsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Assessresult assessresult : list) {
					JSONObject rowJSON = assessresult.toJsonObject();
					// rowJSON.put("id", assessresult.getId());
					// rowJSON.put("assessresultId", assessresult.getId());
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

	/**
	 * 考核结果得分查看
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/jsonForResultView")
	@ResponseBody
	public byte[] jsonForResultView(HttpServletRequest request,
			ModelMap modelMap) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		AssessresultQuery query = new AssessresultQuery();
		Tools.populate(query, params);
		query.setResultid(RequestUtils.getLong(request, "resultid"));

		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "easyui";
		}
		int start = 0;

		JSONObject result = new JSONObject();

		List<JobEvaluation> list = assessresultService.listForResultView(query);

		if (list != null && !list.isEmpty()) {
			JSONArray rowsJSON = new JSONArray();

			result.put("rows", rowsJSON);

			for (JobEvaluation jobEvaluation : list) {
				JSONObject rowJSON = jobEvaluation.toJsonObject();
				// rowJSON.put("id", assessresult.getId());
				// rowJSON.put("assessresultId", assessresult.getId());
				rowJSON.put("startIndex", ++start);
				rowsJSON.add(rowJSON);
			}

		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/jsonForView")
	@ResponseBody
	public byte[] jsonForView(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		AssessresultQuery query = new AssessresultQuery();
		Tools.populate(query, params);
		query.setQustionid(RequestUtils.getLong(request, "questionId"));

		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "easyui";
		}
		int start = 0;

		JSONObject result = new JSONObject();

		List<JobEvaluation> list = assessresultService.listForJob(query);

		if (list != null && !list.isEmpty()) {
			JSONArray rowsJSON = new JSONArray();

			result.put("rows", rowsJSON);

			for (JobEvaluation jobEvaluation : list) {
				JSONObject rowJSON = jobEvaluation.toJsonObject();

				rowJSON.put("startIndex", ++start);
				rowsJSON.add(rowJSON);
			}

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

		return new ModelAndView("/oa/assessresult/list", modelMap);
	}

	/**
	 * 显示岗们考核查询列表
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		return new ModelAndView("/oa/assessresult/assessresultList", modelMap);
	}

	/**
	 * 考核结果查看
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/resultView")
	public ModelAndView resultView(HttpServletRequest request, ModelMap modelMap) {
		AssessresultQuery query = new AssessresultQuery();
		query.setResultid(RequestUtils.getLong(request, "resultid"));

		List<JobEvaluation> jobEvaluations = assessresultService
				.listForResultView(query);
		// Assessquestion assessquestion =
		// assessquestionService.getAssessquestion(query.getQustionid());
		Assessresult assessresult = assessresultService.getAssessresult(query
				.getResultid());
		// 合并单元格参数
		Map<String, List<JobEvaluation>> map = new HashMap<String, List<JobEvaluation>>();
		List<String> dictorList = new ArrayList<String>();
		List<JobEvaluation> list = null;
		Double allScore = 0D;
		for (JobEvaluation jobEvaluation : jobEvaluations) {
			if (!dictorList.contains(jobEvaluation.getDictoryName())) {
				dictorList.add(jobEvaluation.getDictoryName());
			}
			allScore += jobEvaluation.getStandard();
			if (map.containsKey(jobEvaluation.getTreeName())) {
				list = map.get(jobEvaluation.getTreeName());
				list.add(jobEvaluation);
			} else {
				list = new ArrayList<JobEvaluation>();
				list.add(jobEvaluation);
				map.put(jobEvaluation.getTreeName(), list);
			}
		}

		int oneIndex = 0;
		int twoIndex = 0;
		JSONArray rowsJSON = new JSONArray();
		JSONArray rowsJSONForTwo = new JSONArray();
		Set<Entry<String, List<JobEvaluation>>> set = map.entrySet();
		Iterator<Entry<String, List<JobEvaluation>>> it = set.iterator();
		while (it.hasNext()) {
			Map.Entry<String, List<JobEvaluation>> entryMap = (Map.Entry<String, List<JobEvaluation>>) it
					.next();
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("index", oneIndex);
			jsonObj.put("rowspan", entryMap.getValue().size());
			rowsJSON.add(jsonObj);

			// 第2列合并
			Map<String, List<JobEvaluation>> subMap = new HashMap<String, List<JobEvaluation>>();
			List<JobEvaluation> subList = entryMap.getValue();
			for (JobEvaluation jobEvaluation : subList) {
				if (subMap.containsKey(jobEvaluation.getDictoryName())) {
					List<JobEvaluation> jobList = subMap.get(jobEvaluation
							.getDictoryName());
					jobList.add(jobEvaluation);
				} else {
					List<JobEvaluation> jobList = new ArrayList<JobEvaluation>();
					jobList.add(jobEvaluation);
					subMap.put(jobEvaluation.getDictoryName(), jobList);
				}
			}
			for (int i = 0; i < dictorList.size(); i++) {
				List<JobEvaluation> ub = subMap.get(dictorList.get(i));
				if (ub == null || ub.size() == 0) {
					continue;
				}
				JSONObject subJsonObj = new JSONObject();
				subJsonObj.put("subindex", twoIndex);
				twoIndex += ub.size();
				subJsonObj.put("subrowspan", ub.size());
				rowsJSONForTwo.add(subJsonObj);
			}

			oneIndex += entryMap.getValue().size();
		}

		request.setAttribute("allScore", allScore);
		request.setAttribute("assessresult", assessresult);
		request.setAttribute("contentLength", jobEvaluations.size());
		request.setAttribute("mergeCells", rowsJSON);
		request.setAttribute("subMergeCells", rowsJSONForTwo);
		return new ModelAndView("/oa/assessresult/result_view");
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Assessresult assessresult = new Assessresult();
		Tools.populate(assessresult, params);

		assessresult.setQustionid(RequestUtils.getLong(request, "qustionid"));
		assessresult.setArea(RequestUtils.getString(request, "area"));
		assessresult.setCompany(request.getParameter("company"));
		assessresult.setDept(request.getParameter("dept"));
		assessresult.setPost(request.getParameter("post"));
		assessresult.setYear(RequestUtils.getInt(request, "year"));
		assessresult.setSeason(RequestUtils.getInt(request, "season"));
		assessresult.setMonth(RequestUtils.getInt(request, "month"));
		assessresult.setBeevaluation(request.getParameter("beevaluation"));
		assessresult.setEvaluation(request.getParameter("evaluation"));
		assessresult.setRewardsum(RequestUtils.getDouble(request, "rewardsum"));
		assessresult.setScore(RequestUtils.getDouble(request, "score"));
		assessresult.setCreateBy(request.getParameter("createBy"));
		assessresult.setCreateDate(RequestUtils.getDate(request, "createDate"));
		assessresult.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		assessresult.setUpdateBy(request.getParameter("updateBy"));

		assessresult.setCreateBy(actorId);

		assessresultService.save(assessresult);

		return this.list(request, modelMap);
	}

	/**
	 * 保存
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveAssessresult")
	public byte[] saveAssessresult(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Assessresult assessresult = new Assessresult();
		try {
			Tools.populate(assessresult, params);
			assessresult.setQustionid(RequestUtils.getLong(request,
					"questionId"));
			assessresult.setTitle(request.getParameter("title"));
			assessresult.setArea(RequestUtils.getString(request, "area"));
			assessresult.setCompany(request.getParameter("company"));
			assessresult.setDept(request.getParameter("dept"));
			assessresult.setPost(request.getParameter("post"));
			assessresult.setYear(RequestUtils.getInt(request, "year"));
			assessresult.setSeason(RequestUtils.getInt(request, "season"));
			assessresult.setMonth(RequestUtils.getInt(request, "month"));
			assessresult.setBeevaluation(request.getParameter("beevaluation"));
			assessresult.setEvaluation(request.getParameter("evaluation"));
			assessresult.setComment(request.getParameter("comment"));
			// assessresult.setRewardsum(RequestUtils.getDouble(request,"rewardsum"));
			assessresult.setScore(RequestUtils.getDouble(request, "score"));
			assessresult.setCreateDate(new Date());
			assessresult.setUpdateDate(new Date());
			assessresult.setUpdateBy(actorId);
			assessresult.setCreateBy(actorId);
			this.assessresultService.save(assessresult);

			int datagridLength = RequestUtils.getInt(request, "datagridLength");
			for (int i = 0; i < datagridLength; i++) {
				Assessscore assessscore = new Assessscore();
				assessscore.setResultid(assessresult.getResultid());// 考核结果id
				assessscore.setContentid(RequestUtils.getLong(request,
						"contentid_" + i));// 考核内容id
				assessscore.setScore(RequestUtils
						.getLong(request, "score_" + i));// 得分
				assessscore.setReason(RequestUtils.getString(request, "reason_"
						+ i));// 扣分原因
				assessscoreService.save(assessscore);
			}

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setAssessquestionService(
			AssessquestionService assessquestionService) {
		this.assessquestionService = assessquestionService;
	}

	@javax.annotation.Resource
	public void setAssessresultService(AssessresultService assessresultService) {
		this.assessresultService = assessresultService;
	}

	@javax.annotation.Resource
	public void setAssessscoreService(AssessscoreService assessscoreService) {
		this.assessscoreService = assessscoreService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Assessresult assessresult = assessresultService
				.getAssessresult(RequestUtils.getLong(request, "resultid"));

		assessresult.setQustionid(RequestUtils.getLong(request, "qustionid"));
		assessresult.setArea(RequestUtils.getString(request, "area"));
		assessresult.setCompany(request.getParameter("company"));
		assessresult.setDept(request.getParameter("dept"));
		assessresult.setPost(request.getParameter("post"));
		assessresult.setYear(RequestUtils.getInt(request, "year"));
		assessresult.setSeason(RequestUtils.getInt(request, "season"));
		assessresult.setMonth(RequestUtils.getInt(request, "month"));
		assessresult.setBeevaluation(request.getParameter("beevaluation"));
		assessresult.setEvaluation(request.getParameter("evaluation"));
		assessresult.setRewardsum(RequestUtils.getDouble(request, "rewardsum"));
		assessresult.setScore(RequestUtils.getDouble(request, "score"));
		assessresult.setCreateBy(request.getParameter("createBy"));
		assessresult.setCreateDate(RequestUtils.getDate(request, "createDate"));
		assessresult.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		assessresult.setUpdateBy(request.getParameter("updateBy"));

		assessresultService.save(assessresult);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {

		return new ModelAndView("/oa/assessresult/assessresultList");
	}

}