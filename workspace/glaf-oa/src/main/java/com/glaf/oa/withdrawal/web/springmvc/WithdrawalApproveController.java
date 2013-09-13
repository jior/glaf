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
package com.glaf.oa.withdrawal.web.springmvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

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

import com.glaf.oa.withdrawal.model.Withdrawal;
import com.glaf.oa.withdrawal.query.WithdrawalQuery;
import com.glaf.oa.withdrawal.service.WithdrawalService;
import com.glaf.base.modules.BaseDataManager;

import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.service.SysDepartmentService;

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

@Controller("/oa/withdrawalApprove")
@RequestMapping("/oa/withdrawalApprove")
public class WithdrawalApproveController {
	protected static final Log logger = LogFactory
			.getLog(WithdrawalApproveController.class);

	protected WithdrawalService withdrawalService;

	private SysDepartmentService sysDepartmentService;

	public WithdrawalApproveController() {

	}

	@RequestMapping("/approve")
	public ModelAndView approve(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Withdrawal withdrawal = withdrawalService.getWithdrawal(RequestUtils
				.getLong(request, "withdrawalids"));

		if (withdrawal.getBrands1() != null && withdrawal.getBrands2() != null) {
			withdrawal.setBrand("MUL");
		} else if (withdrawal.getBrands1() != null
				&& withdrawal.getBrands2() == null) {

		} else if (withdrawal.getBrands1() == null
				&& withdrawal.getBrands2() != null) {

		}

		request.setAttribute("withdrawal", withdrawal);
		JSONObject rowJSON = withdrawal.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		return new ModelAndView("/oa/withdrawal/approve_edit", modelMap);
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
			String withdrawalids = request.getParameter("withdrawalids");
			if (StringUtils.isNotEmpty(withdrawalids)) {//
				StringTokenizer token = new StringTokenizer(withdrawalids, ",");
				while (token.hasMoreTokens()) {
					String x = token.nextToken();
					if (StringUtils.isNotEmpty(x)) {
						Withdrawal withdrawal = withdrawalService
								.getWithdrawal(Long.parseLong(x));
						String isAgree = RequestUtils.getString(request,
								"isAgree");
						if (withdrawal.getProcessinstanceid() != null
								&& withdrawal.getProcessinstanceid() > 0) {
							if ("isAgree".equals(isAgree)) {
								completeTask(withdrawal, 0, request);
							} else {
								completeTask(withdrawal, 1, request);
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
	private boolean completeTask(Withdrawal withdrawal, int flag,
			HttpServletRequest request) {

		String processName = "Withdrawalprocess";

		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();

		// 获取登录用户部门
		User appUser = BaseDataManager.getInstance().getSysUserService()
				.findByAccount(withdrawal.getAppuser());

		// 根据用户部门id 获取整个部门的对象（GZ01）
		SysDepartment curdept = sysDepartmentService.findById(appUser
				.getDeptId());

		// 根据部门CODE(例如GZ01)截取前2位 作为地区
		String curAreadeptCode = curdept.getCode().substring(0, 2);

		// 根据code 获取 地区部门对象（GZ06）行政财务部
		SysDepartment HRdept = sysDepartmentService.findByCode(curAreadeptCode
				+ "06");

		// 根据code 获取 地区部门对象（GZ）
		SysDepartment curAreadept = sysDepartmentService
				.findByCode(curAreadeptCode);

		// 获取集团部门对象（JT）
		SysDepartment sysdeptMem = sysDepartmentService.findByCode("JT");

		// 行政总监部门
		SysDepartment sysJtdept = sysDepartmentService.findByCode("JT06");

		ProcessContext ctx = new ProcessContext();
		ctx.setRowId(withdrawal.getWithdrawalid());// 表id
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

		// 财务担当
		DataField datafield1 = new DataField();
		datafield1.setName("deptId01");
		datafield1.setValue(HRdept.getId());
		dataFields.add(datafield1);

		// 当地总经理
		DataField datafield4 = new DataField();
		datafield4.setName("deptId02");
		datafield4.setValue(curAreadept.getId());
		dataFields.add(datafield4);

		// 行政总监
		DataField datafield5 = new DataField();
		datafield5.setName("deptId03");
		datafield5.setValue(sysJtdept.getId());
		dataFields.add(datafield5);

		// 集团(JT)
		DataField datafield2 = new DataField();
		datafield2.setName("deptId04");
		datafield2.setValue(sysdeptMem.getId());
		dataFields.add(datafield2);

		DataField datafield3 = new DataField();
		datafield3.setName("rowId");
		datafield3.setValue(withdrawal.getWithdrawalid());
		dataFields.add(datafield3);

		ctx.setDataFields(dataFields);

		Long processInstanceId;
		boolean isOK = false;

		if (withdrawal.getProcessinstanceid() != null
				&& withdrawal.getWfstatus() != 9999
				&& withdrawal.getWfstatus() != null) {
			processInstanceId = withdrawal.getProcessinstanceid();
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
		WithdrawalQuery query = new WithdrawalQuery();
		Tools.populate(query, params);

		// 查询status 0为全部 1为未审批 2为已审批 默认显示未审批的单
		query.setStatus(ParamUtils.getIntValue(params, "status"));
		if (StringUtils.isEmpty(request.getParameter("status"))) {
			query.setStatus(1);
		}
		query.setActorId(loginContext.getActorId());

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
		int total = withdrawalService
				.getWithdrawalApproveCountByQueryCriteria(query);
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

			List<Withdrawal> list = withdrawalService
					.getWithdrawalsApproveByQueryCriteria(start, limit, query);
			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Withdrawal withdrawal : list) {
					JSONObject rowJSON = withdrawal.toJsonObject();
					rowJSON.put("id", withdrawal.getWithdrawalid());
					rowJSON.put("withdrawalId", withdrawal.getWithdrawalid());
					rowJSON.put("startIndex", ++start);
					rowJSON.put("wdStatusFlag", withdrawal.getWdStatusFlag());
					String areaname = BaseDataManager.getInstance()
							.getStringValue(withdrawal.getArea(), "area");
					rowJSON.put("areaname", areaname);
					String companyname = BaseDataManager.getInstance()
							.getStringValue(withdrawal.getCompany(),
									withdrawal.getArea());
					rowJSON.put("companyname", companyname);
					String appusername = BaseDataManager.getInstance()
							.getStringValue(withdrawal.getAppuser(),
									"SYS_USERS");
					rowJSON.put("appusername", appusername);
					String deptname = BaseDataManager.getInstance()
							.getStringValue(withdrawal.getDept(), "SYS_DEPTS");
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

		return new ModelAndView("/oa/withdrawal/approve_list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("withdrawal.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/oa/withdrawal/query", modelMap);
	}

	@javax.annotation.Resource
	public void setSysDepartmentService(
			SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
	}

	@javax.annotation.Resource
	public void setWithdrawalService(WithdrawalService withdrawalService) {
		this.withdrawalService = withdrawalService;
	}
}