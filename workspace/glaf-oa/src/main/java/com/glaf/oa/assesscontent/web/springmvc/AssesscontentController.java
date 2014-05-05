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
package com.glaf.oa.assesscontent.web.springmvc;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.oa.assesscontent.model.Assesscontent;
import com.glaf.oa.assesscontent.query.AssesscontentQuery;
import com.glaf.oa.assesscontent.service.AssesscontentService;
import com.glaf.oa.assessinfo.model.Assessinfo;
import com.glaf.oa.assessinfo.service.AssessinfoService;
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

@Controller("/oa/assesscontent")
@RequestMapping("/oa/assesscontent")
public class AssesscontentController {

	protected static final Log logger = LogFactory
			.getLog(AssesscontentController.class);

	protected AssesscontentService assesscontentService;

	protected AssessinfoService assessinfoService;

	protected AssesssortService assesssortService;

	public AssesscontentController() {

	}

	/**
	 * 删除指标
	 * 
	 * @param request
	 * @param modelMap
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public ModelAndView delete(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		// JSONObject rowJSON = new JSONObject();

		// LoginContext loginContext = RequestUtils.getLoginContext( request );
		// Map<String, Object> params = RequestUtils.getParameterMap( request );
		Long contentid = null;
		String scontentId = request.getParameter("contentid");
		if (scontentId == null) {
			modelMap.addAttribute("message", "删除失败");
			return new ModelAndView("/oa/assessquestion/makeAssessIndex",
					modelMap);
		}
		contentid = Long.valueOf(scontentId);
		try {
			assesscontentService.deleteById(contentid);

		} catch (DataIntegrityViolationException de) {
			de.printStackTrace();
			logger.error("AssesscontentController.class,method=delete,已考核不能删除="
					+ de.getMessage());
			modelMap.addAttribute("message", "已考核，不能删除。");
			return new ModelAndView("/oa/assessquestion/makeAssessIndex",
					modelMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AssesscontentController.class,method=delete,删除异常="
					+ e.getMessage());
			modelMap.addAttribute("message", "删除异常");
			return new ModelAndView("/oa/assessquestion/makeAssessIndex",
					modelMap);
		}
		return new ModelAndView("/oa/assessquestion/makeAssessIndex", modelMap);

	}

	/**
	 * 删除指标类型
	 * 
	 * @param request
	 * @param modelMap
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping("/deleteType")
	public ModelAndView deleteType(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		Long assesssortid = null;
		String assesssortId = request.getParameter("assesssortid");
		if (assesssortId == null) {
			modelMap.addAttribute("message", "删除失败");
			return new ModelAndView("/oa/assessquestion/makeAssessIndex",
					modelMap);
		}
		assesssortid = Long.valueOf(assesssortId);
		try {
			assesscontentService.deleteByParentId(assesssortid);
			assesssortService.deleteById(assesssortid);
		} catch (DataIntegrityViolationException de) {
			de.printStackTrace();
			logger.error("AssesscontentController.class,method=deleteType,已考核不能删除="
					+ de.getMessage());
			modelMap.addAttribute("message", "已考核，不能删除。");
			return new ModelAndView("/oa/assessquestion/makeAssessIndex",
					modelMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AssesscontentController.class,method=deleteType,删除异常="
					+ e.getMessage());
			modelMap.addAttribute("message", "删除异常");
			return new ModelAndView("/oa/assessquestion/makeAssessIndex",
					modelMap);
		}
		return new ModelAndView("/oa/assessquestion/makeAssessIndex", modelMap);

	}

	@ResponseBody
	@RequestMapping("/detail")
	public byte[] detail(HttpServletRequest request) throws IOException {

		// RequestUtils.setRequestParameterToAttribute(request);
		// Map<String, Object> params = RequestUtils.getParameterMap(request);
		Assesscontent assesscontent = assesscontentService
				.getAssesscontent(RequestUtils.getLong(request, "contentid"));

		JSONObject rowJSON = assesscontent.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Assesscontent assesscontent = assesscontentService
				.getAssesscontent(RequestUtils.getLong(request, "contentid"));
		if (assesscontent != null) {
			request.setAttribute("assesscontent", assesscontent);
			JSONObject rowJSON = assesscontent.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}

		boolean canUpdate = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {

		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (assesscontent != null) {
				// if (assesscontent.getStatus() == 0 ||
				// assesscontent.getStatus() == -1) {
				canUpdate = true;
				// }
			}
		}

		request.setAttribute("canUpdate", canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("assesscontent.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/oa/assesscontent/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {

		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		AssesscontentQuery query = new AssesscontentQuery();
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
		int total = assesscontentService
				.getAssesscontentCountByQueryCriteria(query);
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

			List<Assesscontent> list = assesscontentService
					.getAssesscontentsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Assesscontent assesscontent : list) {
					JSONObject rowJSON = assesscontent.toJsonObject();
					// rowJSON.put("id", assesscontent.getId());
					// rowJSON.put("assesscontentId", assesscontent.getId());
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

		return new ModelAndView("/oa/assesscontent/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("assesscontent.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/oa/assesscontent/query", modelMap);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {

		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Assesscontent assesscontent = new Assesscontent();
		Tools.populate(assesscontent, params);

		assesscontent.setSortid(RequestUtils.getLong(request, "sortid"));
		assesscontent.setName(request.getParameter("name"));
		assesscontent.setStandard(RequestUtils.getDouble(request, "standard"));
		assesscontent.setCreateBy(request.getParameter("createBy"));
		assesscontent
				.setCreateDate(RequestUtils.getDate(request, "createDate"));
		assesscontent
				.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		assesscontent.setUpdateBy(request.getParameter("updateBy"));

		assesscontent.setCreateBy(actorId);

		assesscontentService.save(assesscontent);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveAssesscontent")
	public byte[] saveAssesscontent(HttpServletRequest request) {

		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Assesscontent assesscontent = new Assesscontent();
		try {
			Tools.populate(assesscontent, params);
			assesscontent.setSortid(RequestUtils.getLong(request, "sortid"));
			assesscontent.setName(request.getParameter("name"));
			assesscontent.setStandard(RequestUtils.getDouble(request,
					"standard"));
			assesscontent.setCreateBy(request.getParameter("createBy"));
			assesscontent.setCreateDate(RequestUtils.getDate(request,
					"createDate"));
			assesscontent.setUpdateDate(RequestUtils.getDate(request,
					"updateDate"));
			assesscontent.setUpdateBy(request.getParameter("updateBy"));
			assesscontent.setCreateBy(actorId);
			this.assesscontentService.save(assesscontent);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	// 保存指标
	@RequestMapping("/saveAssessContent")
	@ResponseBody
	public byte[] saveAssessContent(HttpServletRequest request,
			ModelMap modelMap) throws IOException {
		String indexIds = RequestUtils.getString(request, "indexIds");
		boolean rstFlag = false;
		int assessSortId = RequestUtils.getInt(request, "assessSortId");
		if (StringUtils.isNotEmpty(indexIds)) {
			StringTokenizer token = new StringTokenizer(indexIds, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Assessinfo assessinfo = assessinfoService
							.getAssessinfo(Long.valueOf(x));
					rstFlag = false;
					if (assessinfo != null) {
						Assesscontent assesscontent = new Assesscontent();
						assesscontent.setSortid((long) assessSortId);
						assesscontent.setName(assessinfo.getName());
						assesscontent.setBasis(assessinfo.getBasis());
						assesscontent.setStandard(assessinfo.getStandard());
						assesscontent.setCreateDate(new Date());
						// 指标ID
						assesscontent.setAssessId(assessinfo.getIndexid());
						assesscontent.setAssessInfo(assessinfo);
						assesscontentService.save(assesscontent);
						rstFlag = true;
					}
				}
			}
		}

		return ResponseUtils.responseJsonResult(rstFlag);
	}

	/**
	 * 选择指标查询页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/selectAssessContent")
	public ModelAndView selectAssessContent(HttpServletRequest request,
			ModelMap modelMap) {

		// LoginContext loginContext = RequestUtils.getLoginContext( request );

		// AssessinfoQuery assessinfoQuery = new AssessinfoQuery();
		// assessinfoQuery.setActorId( loginContext.getActorId() );

		// List<Assessinfo> assessInfoList = assessinfoService.list(
		// assessinfoQuery );
		// modelMap.addAttribute( "assessInfoList", assessInfoList );
		String assessSortId = request.getParameter("assessSortId");
		modelMap.addAttribute("assessSortId", assessSortId);
		return new ModelAndView("/oa/assessinfo/selectAssessContent", modelMap);
	}

	@javax.annotation.Resource
	public void setAssesscontentService(
			AssesscontentService assesscontentService) {

		this.assesscontentService = assesscontentService;
	}

	@javax.annotation.Resource
	public void setAssessinfoService(AssessinfoService assessinfoService) {

		this.assessinfoService = assessinfoService;
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

		Assesscontent assesscontent = assesscontentService
				.getAssesscontent(RequestUtils.getLong(request, "contentid"));

		assesscontent.setSortid(RequestUtils.getLong(request, "sortid"));
		assesscontent.setName(request.getParameter("name"));
		assesscontent.setStandard(RequestUtils.getDouble(request, "standard"));
		assesscontent.setCreateBy(request.getParameter("createBy"));
		assesscontent
				.setCreateDate(RequestUtils.getDate(request, "createDate"));
		assesscontent
				.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		assesscontent.setUpdateBy(request.getParameter("updateBy"));

		assesscontentService.save(assesscontent);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Assesscontent assesscontent = assesscontentService
				.getAssesscontent(RequestUtils.getLong(request, "contentid"));
		request.setAttribute("assesscontent", assesscontent);
		JSONObject rowJSON = assesscontent.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("assesscontent.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/oa/assesscontent/view");
	}

}