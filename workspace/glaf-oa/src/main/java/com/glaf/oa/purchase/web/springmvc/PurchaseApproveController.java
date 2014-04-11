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
import java.util.ArrayList;
import java.util.Collection;
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
import com.glaf.oa.purchase.model.Purchase;
import com.glaf.oa.purchase.query.PurchaseQuery;
import com.glaf.oa.purchase.service.PurchaseService;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.User;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.Tools;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.ProcessContext;
import com.glaf.jbpm.datafield.DataField;

@Controller("/oa/purchaseApprove")
@RequestMapping("/oa/purchaseApprove")
public class PurchaseApproveController {
	protected static final Log logger = LogFactory
			.getLog(PurchaseApproveController.class);

	protected PurchaseService purchaseService;

	protected SysDepartmentService sysDepartmentService;

	protected SysUserService sysUserService;

	public PurchaseApproveController() {

	}

	@RequestMapping("/approve")
	public ModelAndView approve(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Purchase purchase = purchaseService.getPurchase(RequestUtils.getLong(
				request, "purchaseId"));

		request.setAttribute("purchase", purchase);
		JSONObject rowJSON = purchase.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		return new ModelAndView("/oa/purchase/approve_edit", modelMap);
	}

	/**
	 * 审核动作
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/approveData")
	public ModelAndView approveData(HttpServletRequest request,
			ModelMap modelMap) {
		try {
			String purchaseIds = request.getParameter("purchaseIds");
			if (StringUtils.isNotEmpty(purchaseIds)) {// purchaseids不为null时
				StringTokenizer token = new StringTokenizer(purchaseIds, ",");
				while (token.hasMoreTokens()) {
					String x = token.nextToken();
					if (StringUtils.isNotEmpty(x)) {
						Purchase purchase = purchaseService.getPurchase(Long
								.parseLong(x));
						String isAgree = RequestUtils.getString(request,
								"isAgree");
						if (purchase.getProcessinstanceid() != null
								&& purchase.getProcessinstanceid() > 0) {
							if ("isAgree".equals(isAgree)) {
								completeTask(purchase, 0, request);
							} else {
								completeTask(purchase, 1, request);
							}
						}
					}
				}
				return this.list(request, modelMap);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			ModelAndView mav = new ModelAndView();
			mav.addObject("message", "审核异常。");
			return mav;
		}
		return null;
	}

	/**
	 * 工作流审批
	 * 
	 * @param purchase
	 * @param flag
	 *            0同意 1不同意
	 * @param request
	 * @return
	 */
	private boolean completeTask(Purchase purchase, int flag,
			HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		String processName = "PurchaseProcess";

		// 获取登录用户部门
		long deptId01 = user.getDeptId();
		// 根据用户部门id 获取整个部门的对象（GZ01）
		SysDepartment curdept = sysDepartmentService.findById(deptId01);

		// 根据部门CODE(例如GZ01)截取前2位 作为地区
		String curAreadeptCode = curdept.getCode().substring(0, 2);
		// 根据code 获取 地区部门对象（GZ06）行政
		SysDepartment HRdept = sysDepartmentService.findByCode(curAreadeptCode
				+ "06");
		// 根据code 获取 地区部门对象（GZ）
		SysDepartment curAreadept = sysDepartmentService
				.findByCode(curAreadeptCode);

		// 获取集团部门对象（JT）
		SysDepartment sysdeptMem = sysDepartmentService.findByCode("JT");

		ProcessContext ctx = new ProcessContext();
		ctx.setRowId(purchase.getPurchaseid());// 表id
		ctx.setActorId(actorId);// 用户审批者
		ctx.setProcessName(processName);// 流程名称
		String opinion = request.getParameter("approveOpinion");
		ctx.setOpinion(opinion);// 审批意见

		Collection<DataField> dataFields = new ArrayList<DataField>();// 参数

		DataField dataField = new DataField();
		dataField.setName("isAgree");// 是否通过审批
		if (flag == 0) {
			dataField.setValue("true");
		} else {
			dataField.setValue("false");
		}
		dataFields.add(dataField);

		// 用户销售部门（如GZ01）
		DataField datafield1 = new DataField();
		datafield1.setName("deptId01");
		datafield1.setValue(user.getDeptId());
		dataFields.add(datafield1);

		// 用户地区部门（如GZ06）行政部门
		DataField datafield4 = new DataField();
		datafield4.setName("deptId02");
		datafield4.setValue(HRdept.getId());
		dataFields.add(datafield4);

		// 用户地区部门（如GZ）
		DataField datafield5 = new DataField();
		datafield5.setName("deptId03");
		datafield5.setValue(curAreadept.getId());
		dataFields.add(datafield5);

		// 集团(JT)
		DataField datafield2 = new DataField();
		datafield2.setName("deptId04");
		datafield2.setValue(sysdeptMem.getId());
		dataFields.add(datafield2);

		DataField datafield3 = new DataField();
		datafield3.setName("rowId");
		datafield3.setValue(purchase.getPurchaseid());
		dataFields.add(datafield3);

		ctx.setDataFields(dataFields);

		Long processInstanceId;
		boolean isOK = false;

		if (purchase.getProcessinstanceid() != null
				&& purchase.getWfstatus() != 9999
				&& purchase.getWfstatus() != null) {
			processInstanceId = purchase.getProcessinstanceid();
			ctx.setProcessInstanceId(processInstanceId);
			isOK = ProcessContainer.getContainer().completeTask(ctx);
			logger.info("workflowing .......  ");
		} else {
			processInstanceId = ProcessContainer.getContainer().startProcess(
					ctx);
			logger.info("processInstanceId=" + processInstanceId);
			isOK = true;
			logger.info("workflow start");
		}
		return isOK;

	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		PurchaseQuery query = new PurchaseQuery();
		Tools.populate(query, params);
		query.setActorId(loginContext.getActorId());// 只能查找自己
		query.setLoginContext(loginContext);
		/**
		 * 查询条件
		 */
		query.setPurchasenoLike(ParamUtils.getString(params, "purchaseNoLike"));
		query.setAppuser(ParamUtils.getString(params, "appuser"));
		query.setAppdateGreaterThanOrEqual(ParamUtils.getDate(params,
				"appdateGreaterThanOrEqual"));
		query.setAppdateLessThanOrEqual(ParamUtils.getDate(params,
				"appdateLessThanOrEqual"));

		query.setArea(ParamUtils.getString(params, "area"));
		query.setCompany(ParamUtils.getString(params, "company"));
		query.setDept(ParamUtils.getString(params, "dept"));
		// 查询status 0为全部 1为未审批 2为已审批 默认显示未审批的单
		query.setStatus(ParamUtils.getIntValue(params, "status"));
		if (StringUtils.isEmpty(request.getParameter("status"))) {
			query.setStatus(1);
		}
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
		int total = purchaseService
				.getPurchaseApproveCountByQueryCriteria(query);
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

			List<Purchase> list = purchaseService
					.getPurchasesApproveByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Purchase purchase : list) {
					JSONObject rowJSON = purchase.toJsonObject();
					rowJSON.put("id", purchase.getPurchaseid());
					rowJSON.put("purchaseId", purchase.getPurchaseid());
					rowJSON.put("startIndex", ++start);
					rowJSON.put("wdStatusFlag", purchase.getWdStatusFlag());
					String areaname = BaseDataManager.getInstance()
							.getStringValue(purchase.getArea(), "area");
					rowJSON.put("areaname", areaname);
					String companyname = BaseDataManager.getInstance()
							.getStringValue(purchase.getCompany(),
									purchase.getArea());
					rowJSON.put("companyname", companyname);
					String appusername = BaseDataManager.getInstance()
							.getStringValue(purchase.getAppuser(), "SYS_USERS");
					rowJSON.put("appusername", appusername);
					String deptname = BaseDataManager.getInstance()
							.getStringValue(purchase.getDept(), "SYS_DEPTS");
					rowJSON.put("deptname", deptname);
					if (query.getStatus() == 2) {
						rowJSON.put("wdStatusFlag", "wdStatusFlag");
					}
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

		return new ModelAndView("/oa/purchase/approve_list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("purchase.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/oa/purchase/query", modelMap);
	}

	@javax.annotation.Resource
	public void setPurchaseService(PurchaseService purchaseService) {
		this.purchaseService = purchaseService;
	}

	@javax.annotation.Resource
	public void setSysDepartmentService(
			SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
	}

	@javax.annotation.Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Purchase purchase = purchaseService.getPurchase(RequestUtils.getLong(
				request, "purchaseid"));

		purchase.setPurchaseno(request.getParameter("purchaseno"));
		purchase.setArea(request.getParameter("area"));
		purchase.setCompany(request.getParameter("company"));
		purchase.setDept(request.getParameter("dept"));
		purchase.setPost(request.getParameter("post"));
		purchase.setAppuser(request.getParameter("appuser"));
		purchase.setAppdate(RequestUtils.getDate(request, "appdate"));
		purchase.setPurchasesum(RequestUtils.getDouble(request, "purchasesum"));
		purchase.setStatus(RequestUtils.getInt(request, "status"));
		purchase.setProcessname(request.getParameter("processname"));
		purchase.setProcessinstanceid(RequestUtils.getLong(request,
				"processinstanceid"));
		purchase.setWfstatus(RequestUtils.getLong(request, "wfstatus"));
		purchase.setCreateBy(request.getParameter("createby"));
		purchase.setCreateDate(RequestUtils.getDate(request, "createdate"));
		purchase.setUpdateDate(RequestUtils.getDate(request, "updatedate"));
		purchase.setUpdateBy(request.getParameter("updateby"));

		purchaseService.save(purchase);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Purchase purchase = purchaseService.getPurchase(RequestUtils.getLong(
				request, "purchaseid"));
		request.setAttribute("purchase", purchase);
		JSONObject rowJSON = purchase.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("purchase.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/oa/purchase/view");
	}
}