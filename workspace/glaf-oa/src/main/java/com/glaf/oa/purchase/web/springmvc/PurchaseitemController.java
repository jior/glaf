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
package com.glaf.oa.purchase.web.springmvc;

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

import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.User;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;

import com.glaf.oa.purchase.model.Purchaseitem;
import com.glaf.oa.purchase.query.PurchaseitemQuery;
import com.glaf.oa.purchase.service.PurchaseitemService;

@Controller("/oa/purchaseitem")
@RequestMapping("/oa/purchaseitem")
public class PurchaseitemController {
	protected static final Log logger = LogFactory
			.getLog(PurchaseitemController.class);

	protected PurchaseitemService purchaseitemService;

	public PurchaseitemController() {

	}

	@ResponseBody
	@RequestMapping("/delete")
	public ModelAndView delete(HttpServletRequest request, ModelMap modelMap) {
		String purchaseitemids = request.getParameter("purchaseitemIds");
		String purchaseid = request.getParameter("purchaseId");
		try {
			if (StringUtils.isNotEmpty(purchaseitemids)) {
				StringTokenizer token = new StringTokenizer(purchaseitemids,
						",");
				while (token.hasMoreTokens()) {
					String x = token.nextToken();
					if (StringUtils.isNotEmpty(x)) {
						purchaseitemService.deleteById(Long.parseLong(x),
								Long.parseLong(purchaseid));
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			ModelAndView mav = new ModelAndView();
			mav.addObject("message", "删除失败。");
			return mav;
		}
		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/detail")
	public byte[] detail(HttpServletRequest request) throws IOException {
		// RequestUtils.setRequestParameterToAttribute(request);
		// Map<String, Object> params = RequestUtils.getParameterMap(request);
		Purchaseitem purchaseitem = purchaseitemService
				.getPurchaseitem(RequestUtils
						.getLong(request, "purchaseitemid"));

		JSONObject rowJSON = purchaseitem.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Purchaseitem purchaseitem = purchaseitemService
				.getPurchaseitem(RequestUtils
						.getLong(request, "purchaseitemid"));
		if (purchaseitem != null) {
			request.setAttribute("purchaseitem", purchaseitem);
			JSONObject rowJSON = purchaseitem.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}

		boolean canUpdate = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {

		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (purchaseitem != null) {

			}
		}

		request.setAttribute("canUpdate", canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("purchaseitem.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/oa/purchaseitem/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		PurchaseitemQuery query = new PurchaseitemQuery();
		Tools.populate(query, params);
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

		int startIndex = 0;
		JSONObject result = new JSONObject();
		Long purchaseId = Long.parseLong(request.getParameter("purchaseId")
				.toString());
		List<Purchaseitem> list = purchaseitemService
				.getPurchaseitemByParentId(purchaseId);
		if (list != null && !list.isEmpty()) {
			JSONArray rowsJSON = new JSONArray();

			result.put("rows", rowsJSON);
			for (Purchaseitem purchaseitem : list) {
				JSONObject rowJSON = purchaseitem.toJsonObject();
				rowJSON.put("id", purchaseitem.getPurchaseitemid());
				rowJSON.put("purchaseitemId", purchaseitem.getPurchaseitemid());
				rowJSON.put("startIndex", ++startIndex);
				rowJSON.put("remark", purchaseitem.getRemark());
				rowJSON.put("totalprice", purchaseitem.getQuantity()
						* purchaseitem.getReferenceprice());
				rowsJSON.add(rowJSON);
			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", 0);
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

		return new ModelAndView("/oa/purchaseitem/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("purchaseitem.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/oa/purchaseitem/query", modelMap);
	}

	/**
	 * 保存
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		Purchaseitem purchaseitem = new Purchaseitem();
		Tools.populate(purchaseitem, params);

		purchaseitem.setPurchaseid(RequestUtils.getLong(request,
				"purchaseitemid"));
		purchaseitem.setPurchaseid(RequestUtils.getLong(request, "purchaseId"));
		purchaseitem.setContent(request.getParameter("content"));
		purchaseitem.setSpecification(request.getParameter("specification"));
		purchaseitem.setQuantity(RequestUtils.getDouble(request, "quantity"));
		purchaseitem.setReferenceprice(RequestUtils.getDouble(request,
				"referenceprice"));
		purchaseitem.setCreateBy(request.getParameter("createBy"));
		purchaseitem.setCreateDate(RequestUtils.getDate(request, "createDate"));
		purchaseitem.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		purchaseitem.setUpdateBy(request.getParameter("updateBy"));
		purchaseitem.setCreateBy(actorId);
		try {
			purchaseitemService.save(purchaseitem);

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			ModelAndView mav = new ModelAndView();
			mav.addObject("message", "保存失败。");
			return mav;
		}

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/savePurchaseitem")
	public byte[] savePurchaseitem(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Purchaseitem purchaseitem = new Purchaseitem();
		try {
			Tools.populate(purchaseitem, params);
			purchaseitem.setPurchaseid(RequestUtils.getLong(request,
					"purchaseid"));
			purchaseitem.setContent(request.getParameter("content"));
			purchaseitem
					.setSpecification(request.getParameter("specification"));
			purchaseitem.setQuantity(RequestUtils
					.getDouble(request, "quantity"));
			purchaseitem.setReferenceprice(RequestUtils.getDouble(request,
					"referenceprice"));
			purchaseitem.setCreateBy(request.getParameter("createBy"));
			purchaseitem.setCreateDate(RequestUtils.getDate(request,
					"createDate"));
			purchaseitem.setUpdateDate(RequestUtils.getDate(request,
					"updateDate"));
			purchaseitem.setUpdateBy(request.getParameter("updateBy"));
			purchaseitem.setCreateBy(actorId);
			this.purchaseitemService.save(purchaseitem);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setPurchaseitemService(PurchaseitemService purchaseitemService) {
		this.purchaseitemService = purchaseitemService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Purchaseitem purchaseitem = purchaseitemService
				.getPurchaseitem(RequestUtils
						.getLong(request, "purchaseitemid"));

		purchaseitem.setPurchaseid(RequestUtils.getLong(request, "purchaseid"));
		purchaseitem.setContent(request.getParameter("content"));
		purchaseitem.setSpecification(request.getParameter("specification"));
		purchaseitem.setQuantity(RequestUtils.getDouble(request, "quantity"));
		purchaseitem.setReferenceprice(RequestUtils.getDouble(request,
				"referenceprice"));
		purchaseitem.setCreateBy(request.getParameter("createBy"));
		purchaseitem.setCreateDate(RequestUtils.getDate(request, "createDate"));
		purchaseitem.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		purchaseitem.setUpdateBy(request.getParameter("updateBy"));

		purchaseitemService.save(purchaseitem);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Purchaseitem purchaseitem = purchaseitemService
				.getPurchaseitem(RequestUtils
						.getLong(request, "purchaseitemid"));
		request.setAttribute("purchaseitem", purchaseitem);
		JSONObject rowJSON = purchaseitem.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("purchaseitem.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/oa/purchaseitem/view");
	}

}