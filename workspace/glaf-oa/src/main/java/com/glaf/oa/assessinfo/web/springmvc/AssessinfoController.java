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
package com.glaf.oa.assessinfo.web.springmvc;

import java.io.IOException;
import java.util.Date;
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
import com.glaf.oa.assessinfo.model.Assessinfo;
import com.glaf.oa.assessinfo.query.AssessinfoQuery;
import com.glaf.oa.assessinfo.service.AssessinfoService;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.User;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;

@Controller("/oa/assessinfo")
@RequestMapping("/oa/assessinfo")
public class AssessinfoController {
	protected static final Log logger = LogFactory
			.getLog(AssessinfoController.class);

	protected AssessinfoService assessinfoService;

	public AssessinfoController() {

	}

	@ResponseBody
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Long indexid = RequestUtils.getLong(request, "indexid");
		String indexids = request.getParameter("indexids");
		if (StringUtils.isNotEmpty(indexids)) {
			StringTokenizer token = new StringTokenizer(indexids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					assessinfoService.deleteById(Long.valueOf(x));
				}
			}
		} else if (indexid != null) {
			Assessinfo assessinfo = assessinfoService.getAssessinfo(Long
					.valueOf(indexid));
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (assessinfo != null
					&& (StringUtils.equals(assessinfo.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				// assessinfo.setDeleteFlag(1);
				assessinfoService.save(assessinfo);
			}
		}
	}

	@ResponseBody
	@RequestMapping("/detail")
	public byte[] detail(HttpServletRequest request) throws IOException {
		// RequestUtils.setRequestParameterToAttribute(request);
		// Map<String, Object> params = RequestUtils.getParameterMap(request);
		Assessinfo assessinfo = assessinfoService.getAssessinfo(RequestUtils
				.getLong(request, "indexid"));

		JSONObject rowJSON = assessinfo.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Assessinfo assessinfo = assessinfoService.getAssessinfo(RequestUtils
				.getLong(request, "indexid"));
		if (assessinfo != null) {
			request.setAttribute("assessinfo", assessinfo);
			JSONObject rowJSON = assessinfo.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}

		boolean canUpdate = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {

		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (assessinfo != null) {
				canUpdate = true;
			}
		}

		request.setAttribute("canUpdate", canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("assessinfo.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/oa/assessinfo/assessinfoEdit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		AssessinfoQuery query = new AssessinfoQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		// query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		/**
		 * 此处业务逻辑需自行调整
		 */
		// if(!loginContext.isSystemAdministrator()){
		// String actorId = loginContext.getActorId();
		// query.createBy(actorId);
		// }
		query.setNameLike(request.getParameter("nameLike"));

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
		int total = assessinfoService.getAssessinfoCountByQueryCriteria(query);
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

			List<Assessinfo> list = assessinfoService
					.getAssessinfosByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				result.put("rows", rowsJSON);

				for (Assessinfo assessinfo : list) {
					JSONObject rowJSON = assessinfo.toJsonObject();
					// rowJSON.put("id", assessinfo.getIndexid());
					// rowJSON.put("assessinfoId", assessinfo.get);
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

		return new ModelAndView("/oa/assessinfo/assessinfoList", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("assessinfo.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/oa/assessinfo/assessinfoList", modelMap);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Assessinfo assessinfo = new Assessinfo();
		Tools.populate(assessinfo, params);

		assessinfo.setName(request.getParameter("name"));
		assessinfo.setStandard(RequestUtils.getDouble(request, "standard"));
		assessinfo.setIseffective(RequestUtils.getInt(request, "iseffective"));
		assessinfo.setCreateBy(request.getParameter("createBy"));
		assessinfo.setCreateDate(RequestUtils.getDate(request, "createDate"));
		assessinfo.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		assessinfo.setUpdateBy(request.getParameter("updateBy"));

		assessinfo.setCreateBy(actorId);

		assessinfoService.save(assessinfo);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveAssessinfo")
	public byte[] saveAssessinfo(HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Assessinfo assessinfo = new Assessinfo();
		try {
			Tools.populate(assessinfo, params);
			assessinfo.setName(request.getParameter("name"));
			assessinfo.setBasis(RequestUtils.getParameter(request, "basis"));
			assessinfo.setStandard(RequestUtils.getDouble(request, "standard"));
			assessinfo.setIseffective(RequestUtils.getInt(request,
					"iseffective"));
			assessinfo.setCreateDate(new Date());
			assessinfo.setUpdateDate(new Date());
			this.assessinfoService.save(assessinfo);
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	/**
	 * 选择指标
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/selectAssesInfo")
	@ResponseBody
	public byte[] selectAssesInfo(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		if (loginContext == null) {
			return "请重新登录".getBytes("utf-8");
		}
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		AssessinfoQuery query = new AssessinfoQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setLoginContext(loginContext);
		query.setNameLike(request.getParameter("nameLike"));

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
		int total = assessinfoService.getAssessinfoCountByQueryCriteria(query);
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

			List<Assessinfo> list = assessinfoService
					.getAssessinfosByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Assessinfo assessinfo : list) {
					JSONObject rowJSON = assessinfo.toJsonObject();
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

	@javax.annotation.Resource
	public void setAssessinfoService(AssessinfoService assessinfoService) {
		this.assessinfoService = assessinfoService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");
		Assessinfo assessinfo = assessinfoService.getAssessinfo(RequestUtils
				.getLong(request, "indexid"));
		assessinfo.setName(request.getParameter("name"));
		assessinfo.setBasis(request.getParameter("basis"));
		assessinfo.setStandard(RequestUtils.getDouble(request, "standard"));
		assessinfo.setIseffective(RequestUtils.getInt(request, "iseffective"));
		// assessinfo.setCreateBy(request.getParameter("createBy"));
		assessinfo.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		assessinfoService.save(assessinfo);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("assessinfo.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/oa/assessinfo/assessinfoList");
	}

}