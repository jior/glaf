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
package com.glaf.oa.travelpersonnel.web.springmvc;

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

import com.glaf.oa.travelpersonnel.model.*;
import com.glaf.oa.travelpersonnel.query.*;
import com.glaf.oa.travelpersonnel.service.*;

@Controller("/oa/travelpersonnel")
@RequestMapping("/oa/travelpersonnel")
public class TravelpersonnelController {
	protected static final Log logger = LogFactory
			.getLog(TravelpersonnelController.class);

	protected TravelpersonnelService travelpersonnelService;

	public TravelpersonnelController() {

	}

	@ResponseBody
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, ModelMap modelMap) {
		Long personnelid = RequestUtils.getLong(request, "personnelid");
		String personnelids = request.getParameter("personnelids");
		if (StringUtils.isNotEmpty(personnelids)) {
			StringTokenizer token = new StringTokenizer(personnelids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Travelpersonnel travelpersonnel = travelpersonnelService
							.getTravelpersonnel(Long.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					if (travelpersonnel != null) {
						// travelpersonnel.setDeleteFlag(1);
						travelpersonnelService.deleteById(Long.valueOf(x));
					}
				}
			}
		} else if (personnelid != null) {
			Travelpersonnel travelpersonnel = travelpersonnelService
					.getTravelpersonnel(personnelid);
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (travelpersonnel != null) {
				// travelpersonnel.setDeleteFlag(1);
				travelpersonnelService.deleteById(personnelid);
			}
		}
	}

	@ResponseBody
	@RequestMapping("/detail")
	public byte[] detail(HttpServletRequest request) throws IOException {
		Travelpersonnel travelpersonnel = travelpersonnelService
				.getTravelpersonnel(RequestUtils
						.getLong(request, "personnelid"));

		JSONObject rowJSON = travelpersonnel.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Travelpersonnel travelpersonnel = travelpersonnelService
				.getTravelpersonnel(RequestUtils
						.getLong(request, "personnelid"));
		if (travelpersonnel != null) {
			request.setAttribute("travelpersonnel", travelpersonnel);
			JSONObject rowJSON = travelpersonnel.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("travelpersonnel.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/oa/travelpersonnel/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TravelpersonnelQuery query = new TravelpersonnelQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);

		if (request.getParameter("travelid") != null
				&& !request.getParameter("travelid").equals("")) {
			query.setTravelid(Long.parseLong(request.getParameter("travelid")));
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
		int total = travelpersonnelService
				.getTravelpersonnelCountByQueryCriteria(query);
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

			List<Travelpersonnel> list = travelpersonnelService
					.getTravelpersonnelsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Travelpersonnel travelpersonnel : list) {
					JSONObject rowJSON = travelpersonnel.toJsonObject();
					rowJSON.put("id", travelpersonnel.getPersonnelid());
					rowJSON.put("travelpersonnelId",
							travelpersonnel.getPersonnelid());
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

		return new ModelAndView("/oa/travelpersonnel/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("travelpersonnel.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/oa/travelpersonnel/query", modelMap);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Travelpersonnel travelpersonnel = new Travelpersonnel();
		Tools.populate(travelpersonnel, params);

		travelpersonnel.setTravelid(RequestUtils.getLong(request, "travelid"));
		travelpersonnel.setDept(request.getParameter("dept"));
		travelpersonnel.setPersonnel(request.getParameter("personnel"));
		travelpersonnel.setRemark(request.getParameter("remark"));
		if (RequestUtils.getLong(request, "personnelid") == 0L
				|| request.getParameter("personnelid") == null) {
			travelpersonnel.setPersonnelid(0L);
			travelpersonnel.setCreateDate(new Date());
			travelpersonnel.setCreateBy(actorId);
		} else {
			travelpersonnel.setPersonnelid(RequestUtils.getLong(request,
					"personnelid"));
			travelpersonnel.setUpdateDate(new Date());
			travelpersonnel.setUpdateBy(actorId);
		}

		travelpersonnelService.save(travelpersonnel);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveTravelpersonnel")
	public byte[] saveTravelpersonnel(HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Travelpersonnel travelpersonnel = new Travelpersonnel();
		try {
			Tools.populate(travelpersonnel, params);
			travelpersonnel.setTravelid(RequestUtils.getLong(request,
					"travelid"));
			travelpersonnel.setDept(request.getParameter("dept"));
			travelpersonnel.setPersonnel(request.getParameter("personnel"));
			travelpersonnel.setRemark(request.getParameter("remark"));
			this.travelpersonnelService.save(travelpersonnel);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setTravelpersonnelService(
			TravelpersonnelService travelpersonnelService) {
		this.travelpersonnelService = travelpersonnelService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Travelpersonnel travelpersonnel = travelpersonnelService
				.getTravelpersonnel(RequestUtils
						.getLong(request, "personnelid"));

		travelpersonnel.setTravelid(RequestUtils.getLong(request, "travelid"));
		travelpersonnel.setDept(request.getParameter("dept"));
		travelpersonnel.setPersonnel(request.getParameter("personnel"));
		travelpersonnel.setRemark(request.getParameter("remark"));

		travelpersonnelService.save(travelpersonnel);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Travelpersonnel travelpersonnel = travelpersonnelService
				.getTravelpersonnel(RequestUtils
						.getLong(request, "personnelid"));
		request.setAttribute("travelpersonnel", travelpersonnel);
		JSONObject rowJSON = travelpersonnel.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("travelpersonnel.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/oa/travelpersonnel/view");
	}

}