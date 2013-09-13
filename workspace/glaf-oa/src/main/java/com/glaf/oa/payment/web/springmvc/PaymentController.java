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
package com.glaf.oa.payment.web.springmvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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

import com.glaf.oa.payment.model.Payment;
import com.glaf.oa.payment.query.PaymentQuery;
import com.glaf.oa.payment.service.PaymentService;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.others.service.AttachmentService;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.utils.RequestUtil;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.User;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.ProcessContext;
import com.glaf.jbpm.datafield.DataField;

@Controller("/oa/payment")
@RequestMapping("/oa/payment")
public class PaymentController {
	protected static final Log logger = LogFactory
			.getLog(PaymentController.class);

	protected PaymentService paymentService;

	protected SysDepartmentService sysDepartmentService;

	protected AttachmentService attachmentService;

	public PaymentController() {

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
	private boolean completeTask(Payment payment, int flag,
			HttpServletRequest request) {

		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		String processName = "PaymentProcess";
		User appUser = BaseDataManager.getInstance().getSysUserService()
				.findByAccount(payment.getAppuser());

		// 根据用户部门id 获取整个部门的对象（GZ01）
		SysDepartment curdept = sysDepartmentService.findById(appUser
				.getDeptId());

		// 根据部门CODE(例如GZ01)截取前2位 作为地区
		String curAreadeptCode = curdept.getCode().substring(0, 2);
		String endOfCode = "";
		if (curdept.getCode().length() == 4) {
			endOfCode = curdept.getCode().substring(2);
		}

		// 根据code 获取 地区部门对象（GZ06）行政
		SysDepartment HRdept = sysDepartmentService.findByCode(curAreadeptCode
				+ "06");
		// 根据code 获取 地区部门对象（GZ）
		SysDepartment curAreadept = sysDepartmentService
				.findByCode(curAreadeptCode);

		// 获取集团部门对象（JT）
		SysDepartment sysdeptMem = sysDepartmentService.findByCode("JT");

		// 获取集团部门部门对象（JTxx）
		SysDepartment sysdeptMemDept = sysDepartmentService.findByCode("JT"
				+ endOfCode);

		ProcessContext ctx = new ProcessContext();
		ctx.setRowId(payment.getPaymentid());// 表id

		if (payment.getWfstatus() == -5555) {
			ctx.setActorId(payment.getAppuser());
		} else {
			ctx.setActorId(actorId);// 用户审批者
		}

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

		// 会计 （XX06）
		DataField datafield1 = new DataField();
		datafield1.setName("deptId01");
		datafield1.setValue(HRdept.getId());
		dataFields.add(datafield1);

		// 部门主管/经理
		DataField datafield4 = new DataField();
		datafield4.setName("deptId02");
		datafield4.setValue(appUser.getDeptId());
		dataFields.add(datafield4);

		// 用户地区部门（如GZ）
		DataField datafield5 = new DataField();
		datafield5.setName("deptId03");
		datafield5.setValue(curAreadept.getId());
		dataFields.add(datafield5);

		// 集团部门(JTxx)
		DataField datafield2 = new DataField();
		datafield2.setName("deptId04");
		datafield2.setValue(sysdeptMemDept.getId());
		dataFields.add(datafield2);

		// 集团(JT)
		DataField datafield6 = new DataField();
		datafield6.setName("deptId05");
		datafield6.setValue(sysdeptMem.getId());
		dataFields.add(datafield6);

		DataField datafield3 = new DataField();
		datafield3.setName("rowId");
		datafield3.setValue(payment.getPaymentid());
		dataFields.add(datafield3);

		ctx.setDataFields(dataFields);

		Long processInstanceId;
		boolean isOK = false;

		if (payment.getProcessinstanceid() != null
				&& payment.getWfstatus() != 9999
				&& payment.getWfstatus() != null) {
			processInstanceId = payment.getProcessinstanceid();
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

	@ResponseBody
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Long paymentid = RequestUtils.getLong(request, "paymentid");
		String paymentids = request.getParameter("paymentids");
		if (StringUtils.isNotEmpty(paymentids)) {
			StringTokenizer token = new StringTokenizer(paymentids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Payment payment = paymentService
							.getPayment(Long.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					if (payment != null
							&& (StringUtils.equals(payment.getCreateBy(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						if (payment.getProcessinstanceid() != null
								&& payment.getProcessinstanceid() != 0L) {
							ProcessContext ctx = new ProcessContext();
							ctx.setActorId(loginContext.getActorId());
							ctx.setProcessInstanceId(payment
									.getProcessinstanceid());
							ProcessContainer.getContainer().abortProcess(ctx);
						}

						paymentService.deleteById(payment.getPaymentid());
					}
				}
			}
		} else if (paymentid != null) {
			Payment payment = paymentService
					.getPayment(Long.valueOf(paymentid));
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (payment != null
					&& (StringUtils.equals(payment.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				if (payment.getProcessinstanceid() != null
						&& payment.getProcessinstanceid() != 0L) {
					ProcessContext ctx = new ProcessContext();
					ctx.setActorId(loginContext.getActorId());
					ctx.setProcessInstanceId(payment.getProcessinstanceid());
					ProcessContainer.getContainer().abortProcess(ctx);
				}
				paymentService.deleteById(payment.getPaymentid());
			}
		}
	}

	@ResponseBody
	@RequestMapping("/detail")
	public byte[] detail(HttpServletRequest request) throws IOException {
		// RequestUtils.setRequestParameterToAttribute(request);
		// Map<String, Object> params = RequestUtils.getParameterMap(request);
		Payment payment = paymentService.getPayment(RequestUtils.getLong(
				request, "paymentid"));

		JSONObject rowJSON = payment.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Payment payment = paymentService.getPayment(RequestUtils.getLong(
				request, "paymentid"));
		if (payment != null) {
			// 用户名处理
			String appusername = BaseDataManager.getInstance().getStringValue(
					payment.getAppuser(), "SYS_USERS");
			request.setAttribute("appusername", appusername);
			request.setAttribute("payment", payment);
			JSONObject rowJSON = payment.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		} else {
			payment = new Payment();
			long deptId01 = user.getDeptId();
			SysDepartment curdept = sysDepartmentService.findById(deptId01);
			// 获取当地部门节点
			String curAreadeptCode = curdept.getCode().substring(0, 2);

			payment.setArea(curAreadeptCode);
			payment.setDept(curdept.getCode());
			payment.setAppuser(user.getActorId());
			payment.setPost(RequestUtil.getLoginUser(request).getHeadship());
			request.setAttribute("payment", payment);
			JSONObject rowJSON = payment.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}

		boolean canUpdate = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {

		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (payment != null) {
				if (payment.getStatus() == 0 || payment.getStatus() == -1) {
					canUpdate = true;
				}
			}
		}

		request.setAttribute("canUpdate", canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("payment.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/oa/payment/payment_edit", modelMap);
	}

	@RequestMapping("/getReviewList")
	@ResponseBody
	public byte[] getReviewList(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		PaymentQuery query = new PaymentQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		List<String> actorIds = new ArrayList<String>();

		/**
		 * 此处业务逻辑需自行调整
		 */
		// 由于页面输入的日期没有时分秒，查找范围的时候获取不到最后一天的记录,人为设置
		if (null != query.getAppdateLessThanOrEqual()) {
			Date appdateLessThanOrEqual = query.getAppdateLessThanOrEqual();
			// appdateLessThanOrEqual.setHours(23);
			// appdateLessThanOrEqual.setMinutes(59);
			// appdateLessThanOrEqual.setSeconds(59);
			query.setAppdateLessThanOrEqual(appdateLessThanOrEqual);
		}

		if (null == query.getWorkedProcessFlag()
				|| "".equals(query.getWorkedProcessFlag())) {
			query.setWorkedProcessFlag("WD");
		}
		query.setActorId(loginContext.getActorId());
		actorIds.add(loginContext.getActorId());
		actorIds.addAll(ProcessContainer.getContainer().getAgentIds(
				loginContext.getActorId()));
		query.setActorIds(actorIds);

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
		int total = paymentService.getReviewPaymentCountByQueryCriteria(query);
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

			List<Payment> list = paymentService
					.getReviewPaymentsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Payment payment : list) {
					String area = payment.getArea();
					String companyname = payment.getCompany();
					String appuser = payment.getAppuser();
					String deptname = payment.getDept();
					try {
						String area_CN = BaseDataManager.getInstance()
								.getStringValue(payment.getArea(), "area");
						String companyname_CN = BaseDataManager.getInstance()
								.getStringValue(payment.getCompany(),
										payment.getArea());
						payment.setCompany(companyname_CN);
						payment.setArea(area_CN);
						// 用户名处理
						String appusername = BaseDataManager.getInstance()
								.getStringValue(payment.getAppuser(),
										"SYS_USERS");
						payment.setAppuser(appusername);
						// 部门名称处理
						String dept = BaseDataManager.getInstance()
								.getStringValue(payment.getDept(), "SYS_DEPTS");
						payment.setDept(dept);
					} catch (Exception e) {
						payment.setAppuser(appuser);
						payment.setArea(area);
						payment.setCompany(companyname);
						payment.setDept(deptname);
						logger.error(e.getMessage());
					}
					JSONObject rowJSON = payment.toJsonObject();
					rowJSON.put("id", payment.getPaymentid());
					rowJSON.put("paymentId", payment.getPaymentid());
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

	@RequestMapping("/init")
	public ModelAndView init(HttpServletRequest request, ModelMap modelMap) {
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

		String areaRole = request.getParameter("areaRole");
		if ("0".equals(areaRole)) {
			User user = RequestUtils.getUser(request);
			long curdeptid = user.getDeptId();
			SysDepartment curdept = sysDepartmentService.findById(curdeptid);
			if (null != curdept) {
				request.setAttribute("area", curdept.getCode().substring(0, 2));
				request.setAttribute("areaRole", areaRole);
			}
		} else {
			request.setAttribute("areaRole", areaRole);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/oa/payment/search_payment_list", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		PaymentQuery query = new PaymentQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);

		User user = loginContext.getUser();
		long deptId01 = user.getDeptId();
		SysDepartment curdept = sysDepartmentService.findById(deptId01);
		/**
		 * 此处业务逻辑需自行调整
		 */
		// 由于页面输入的日期没有时分秒，查找范围的时候获取不到最后一天的记录,人为设置
		if (null != query.getAppdateLessThanOrEqual()) {
			Date appdateLessThanOrEqual = query.getAppdateLessThanOrEqual();
			// appdateLessThanOrEqual.setHours(23);
			// appdateLessThanOrEqual.setMinutes(59);
			// appdateLessThanOrEqual.setSeconds(59);
			query.setAppdateLessThanOrEqual(appdateLessThanOrEqual);
		}

		String rstatus = request.getParameter("rstatus");
		if (!StringUtils.isEmpty(rstatus) && !"null".equals(rstatus)) {
			query.setStatus(Integer.parseInt(rstatus));
		}
		String status = request.getParameter("status");
		if (!StringUtils.isEmpty(status)) {
			query.setStatus(Integer.parseInt(status));
		}

		String areaRole = request.getParameter("areaRole");
		if (null == areaRole || "".equals(areaRole)) {
			if (!loginContext.isSystemAdministrator()) {
				String actorId = loginContext.getActorId();
				query.createBy(actorId);
			}
		} else if ("0".equals(areaRole)) {
			query.setArea(curdept.getCode().substring(0, 2));
			query.setStatusGreaterThanOrEqual(1);
		} else if ("1".equals(areaRole)) {
			query.setStatusGreaterThanOrEqual(1);
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
		int total = paymentService.getPaymentCountByQueryCriteria(query);
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

			List<Payment> list = paymentService.getPaymentsByQueryCriteria(
					start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Payment payment : list) {
					String area = payment.getArea();
					String companyname = payment.getCompany();
					String appuser = payment.getAppuser();
					String deptname = payment.getDept();
					try {
						String area_CN = BaseDataManager.getInstance()
								.getStringValue(payment.getArea(), "area");
						String companyname_CN = BaseDataManager.getInstance()
								.getStringValue(payment.getCompany(),
										payment.getArea());
						payment.setCompany(companyname_CN);
						payment.setArea(area_CN);
						// 用户名处理
						String appusername = BaseDataManager.getInstance()
								.getStringValue(payment.getAppuser(),
										"SYS_USERS");
						payment.setAppuser(appusername);
						// 部门名称处理
						String dept = BaseDataManager.getInstance()
								.getStringValue(payment.getDept(), "SYS_DEPTS");
						payment.setDept(dept);
					} catch (Exception e) {
						payment.setAppuser(appuser);
						payment.setArea(area);
						payment.setCompany(companyname);
						payment.setDept(deptname);
						logger.error(e.getMessage());
					}

					JSONObject rowJSON = payment.toJsonObject();
					rowJSON.put("id", payment.getPaymentid());
					rowJSON.put("paymentId", payment.getPaymentid());
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

		return new ModelAndView("/oa/payment/payment_list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("payment.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/oa/payment/query", modelMap);
	}

	@RequestMapping("/review")
	public ModelAndView review(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Payment payment = paymentService.getPayment(RequestUtils.getLong(
				request, "paymentid"));
		if (payment != null) {
			// 用户名处理
			String appusername = BaseDataManager.getInstance().getStringValue(
					payment.getAppuser(), "SYS_USERS");
			request.setAttribute("appusername", appusername);
			request.setAttribute("payment", payment);
			JSONObject rowJSON = payment.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}

		if (RequestUtils.getBoolean(request, "lookover")) {
			request.setAttribute("lookover", true);
		} else {
			request.setAttribute("lookover", false);
		}
		boolean canUpdate = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {

		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (payment != null) {
				if (payment.getStatus() == 0 || payment.getStatus() == -1) {
					canUpdate = true;
				}
			}
		}

		request.setAttribute("canUpdate", canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("payment.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/oa/payment/payment_review", modelMap);
	}

	@RequestMapping("/reviewList")
	public ModelAndView reviewList(HttpServletRequest request, ModelMap modelMap) {
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

		return new ModelAndView("/oa/payment/payment_reviewlist", modelMap);
	}

	/**
	 * 流程打回
	 * 
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/rollback")
	public byte[] rollback(HttpServletRequest request, ModelMap modelMap) {
		Long paymentid = RequestUtils.getLong(request, "paymentid");
		String paymentids = request.getParameter("paymentids");
		boolean returnFlag = false;
		if (StringUtils.isNotEmpty(paymentids)) {
			StringTokenizer token = new StringTokenizer(paymentids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Payment payment = paymentService
							.getPayment(Long.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					if (payment != null) {
						if (null != payment.getProcessinstanceid()
								&& 0 != payment.getProcessinstanceid()) {
							returnFlag = completeTask(payment, 1, request);
						}
					}
				}
			}
		} else if (paymentid != null) {
			Payment payment = paymentService
					.getPayment(Long.valueOf(paymentid));
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (payment != null) {
				if (null != payment.getProcessinstanceid()
						&& 0 != payment.getProcessinstanceid()) {
					returnFlag = completeTask(payment, 1, request);
				}
			}
		}
		if (returnFlag) {
			return ResponseUtils.responseJsonResult(true);
		} else {
			return ResponseUtils.responseJsonResult(false);
		}
	}

	@ResponseBody
	@RequestMapping("/save")
	public byte[] save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		try {
			params.remove("status");
			params.remove("wfStatus");

			Payment payment = new Payment();
			Tools.populate(payment, params);

			payment.setArea(request.getParameter("area"));
			payment.setPost(request.getParameter("post"));
			payment.setCompany(request.getParameter("company"));
			payment.setDept(request.getParameter("dept"));
			payment.setCertificateno(request.getParameter("certificateno"));
			payment.setReceiptno(request.getParameter("receiptno"));
			payment.setAppuser(request.getParameter("appuser"));
			payment.setAppdate(RequestUtils.getDate(request, "appdate"));
			payment.setMaturitydate(RequestUtils.getDate(request,
					"maturitydate"));
			payment.setAppsum(RequestUtils.getDouble(request, "appsum"));
			payment.setCurrency(request.getParameter("currency"));
			payment.setBudgetno(request.getParameter("budgetno"));
			payment.setUse(request.getParameter("use"));
			payment.setSupname(request.getParameter("supname"));
			payment.setSupbank(request.getParameter("supbank"));
			payment.setSupaccount(request.getParameter("supaccount"));
			payment.setSupaddress(request.getParameter("supaddress"));
			payment.setSubject(request.getParameter("subject"));
			payment.setCheckno(request.getParameter("checkno"));
			payment.setRemark(request.getParameter("remark"));
			if (null != payment.getPaymentid()) {
				payment.setUpdateDate(new Date());
				payment.setUpdateBy(actorId);
			} else {
				payment.setCreateBy(actorId);
				payment.setAppdate(new Date());
				payment.setCreateDate(new Date());
			}
			payment.setStatus(0);
			long flag = payment.getPaymentid() == null ? 0 : payment
					.getPaymentid();
			this.paymentService.save(payment);
			if (flag == 0) {
				this.attachmentService.updateByReferTypeAndCreateId(
						payment.getPaymentid(), 6, user.getId());
			}
			boolean returnFlag = false;
			payment = paymentService.getPayment(payment.getPaymentid());
			if (null != payment.getProcessinstanceid()
					&& 0 != payment.getProcessinstanceid()) {
				if (payment.getWfstatus() == -5555) {
					payment.setStatus(1);
					paymentService.save(payment);

				}
				returnFlag = completeTask(payment, 0, request);
			} else {
				returnFlag = startProcess(payment, request);
			}
			if (returnFlag) {
				return ResponseUtils.responseJsonResult(true);
			} else {
				return ResponseUtils.responseJsonResult(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@ResponseBody
	@RequestMapping("/savePayment")
	public byte[] savePayment(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Payment payment = new Payment();
		try {
			Tools.populate(payment, params);
			payment.setArea(request.getParameter("area"));
			payment.setPost(request.getParameter("post"));
			payment.setCompany(request.getParameter("company"));
			payment.setDept(request.getParameter("dept"));
			payment.setCertificateno(request.getParameter("certificateno"));
			payment.setReceiptno(request.getParameter("receiptno"));
			payment.setAppuser(request.getParameter("appuser"));
			payment.setMaturitydate(RequestUtils.getDate(request,
					"maturitydate"));
			payment.setAppsum(RequestUtils.getDouble(request, "appsum"));
			payment.setCurrency(request.getParameter("currency"));
			payment.setBudgetno(request.getParameter("budgetno"));
			payment.setUse(request.getParameter("use"));
			payment.setSupname(request.getParameter("supname"));
			payment.setSupbank(request.getParameter("supbank"));
			payment.setSupaccount(request.getParameter("supaccount"));
			payment.setSupaddress(request.getParameter("supaddress"));
			payment.setSubject(request.getParameter("subject"));
			payment.setCheckno(request.getParameter("checkno"));
			payment.setRemark(request.getParameter("remark"));
			if (null != payment.getPaymentid()) {
				payment.setUpdateDate(new Date());
				payment.setUpdateBy(actorId);
			} else {
				payment.setCreateBy(actorId);
				payment.setAppdate(new Date());
				payment.setCreateDate(new Date());
			}
			payment.setStatus(0);
			long flag = payment.getPaymentid() == null ? 0 : payment
					.getPaymentid();
			this.paymentService.save(payment);
			if (flag == 0) {
				this.attachmentService.updateByReferTypeAndCreateId(
						payment.getPaymentid(), 6, user.getId());
			}
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	@javax.annotation.Resource
	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@javax.annotation.Resource
	public void setSysDepartmentService(
			SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
	}

	/**
	 * 启动工作流
	 * 
	 * @param budget
	 * @param request
	 * @return
	 */
	private boolean startProcess(Payment payment, HttpServletRequest request) {
		String processName = "PaymentProcess";

		// 获取登录用户部门
		User appUser = BaseDataManager.getInstance().getSysUserService()
				.findByAccount(payment.getAppuser());

		// 根据用户部门id 获取整个部门的对象（GZ01）
		SysDepartment curdept = sysDepartmentService.findById(appUser
				.getDeptId());

		// 根据部门CODE(例如GZ01)截取前2位 作为地区
		String curAreadeptCode = curdept.getCode().substring(0, 2);

		String endOfCode = "";
		if (curdept.getCode().length() == 4) {
			endOfCode = curdept.getCode().substring(2);
		}

		// 根据code 获取 地区部门对象（GZ06）行政
		SysDepartment HRdept = sysDepartmentService.findByCode(curAreadeptCode
				+ "06");
		// 根据code 获取 地区部门对象（GZ）
		SysDepartment curAreadept = sysDepartmentService
				.findByCode(curAreadeptCode);

		// 获取集团部门对象（JT）
		SysDepartment sysdeptMem = sysDepartmentService.findByCode("JT");

		// 获取集团部门部门对象（JTxx）
		SysDepartment sysdeptMemDept = sysDepartmentService.findByCode("JT"
				+ endOfCode);

		ProcessContext ctx = new ProcessContext();
		ctx.setRowId(payment.getPaymentid());// 表id
		ctx.setActorId(appUser.getActorId());// 用户审批者
		ctx.setProcessName(processName);// 流程名称

		Collection<DataField> dataFields = new ArrayList<DataField>();// 参数

		// 会计 （XX06）
		DataField datafield1 = new DataField();
		datafield1.setName("deptId01");
		datafield1.setValue(HRdept.getId());
		dataFields.add(datafield1);

		// 部门主管/经理
		DataField datafield4 = new DataField();
		datafield4.setName("deptId02");
		datafield4.setValue(appUser.getDeptId());
		dataFields.add(datafield4);

		// 用户地区部门（如GZ）
		DataField datafield5 = new DataField();
		datafield5.setName("deptId03");
		datafield5.setValue(curAreadept.getId());
		dataFields.add(datafield5);

		// 集团部门(JTxx)
		DataField datafield2 = new DataField();
		datafield2.setName("deptId04");
		datafield2.setValue(sysdeptMemDept.getId());
		dataFields.add(datafield2);

		// 集团(JT)
		DataField datafield6 = new DataField();
		datafield6.setName("deptId05");
		datafield6.setValue(sysdeptMem.getId());
		dataFields.add(datafield6);

		DataField datafield3 = new DataField();
		datafield3.setName("rowId");
		datafield3.setValue(payment.getPaymentid());
		dataFields.add(datafield3);

		// DataField dataField = new DataField();
		// dataField.setName("isAgree");// 是否通过审批
		// dataField.setValue("true");
		// dataFields.add(dataField);

		ctx.setDataFields(dataFields);

		Long processInstanceId = null;
		boolean isOK = false;

		if (payment.getProcessinstanceid() != null
				&& payment.getWfstatus() != 9999
				&& payment.getWfstatus() != null) {
			processInstanceId = payment.getProcessinstanceid();
			ctx.setProcessInstanceId(processInstanceId);
			isOK = ProcessContainer.getContainer().completeTask(ctx);
			logger.info("workflowing .......");
		} else {
			processInstanceId = ProcessContainer.getContainer().startProcess(
					ctx);
			logger.info("processInstanceId=" + processInstanceId);
			isOK = true;
			logger.info("workflow start");
		}
		return isOK;
	}

	/**
	 * 列表用户提交流程及流程审批
	 * 
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/submit")
	public byte[] submit(HttpServletRequest request, ModelMap modelMap) {
		Long paymentid = RequestUtils.getLong(request, "paymentid");
		String paymentids = request.getParameter("paymentids");
		boolean returnFlag = false;
		if (StringUtils.isNotEmpty(paymentids)) {
			StringTokenizer token = new StringTokenizer(paymentids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Payment payment = paymentService
							.getPayment(Long.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					if (payment != null) {
						if (null != payment.getProcessinstanceid()
								&& 0 != payment.getProcessinstanceid()) {
							if (payment.getWfstatus() == -5555) {
								payment.setStatus(1);
								paymentService.save(payment);

							}
							returnFlag = completeTask(payment, 0, request);
						} else {
							returnFlag = startProcess(payment, request);
						}
					}
				}
			}
		} else if (paymentid != null) {
			Payment payment = paymentService
					.getPayment(Long.valueOf(paymentid));
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (payment != null) {
				if (null != payment.getProcessinstanceid()
						&& 0 != payment.getProcessinstanceid()) {
					if (payment.getWfstatus() == -5555) {
						payment.setStatus(1);
						paymentService.save(payment);

					}
					returnFlag = completeTask(payment, 0, request);
				} else {
					returnFlag = startProcess(payment, request);
				}
			}
		}
		if (returnFlag) {
			return ResponseUtils.responseJsonResult(true);
		} else {
			return ResponseUtils.responseJsonResult(false);
		}
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Payment payment = paymentService.getPayment(RequestUtils.getLong(
				request, "paymentid"));

		payment.setArea(request.getParameter("area"));
		payment.setPost(request.getParameter("post"));
		payment.setCompany(request.getParameter("company"));
		payment.setDept(request.getParameter("dept"));
		payment.setCertificateno(request.getParameter("certificateno"));
		payment.setReceiptno(request.getParameter("receiptno"));
		payment.setAppuser(request.getParameter("appuser"));
		payment.setAppdate(RequestUtils.getDate(request, "appdate"));
		payment.setMaturitydate(RequestUtils.getDate(request, "maturitydate"));
		payment.setAppsum(RequestUtils.getDouble(request, "appsum"));
		payment.setCurrency(request.getParameter("currency"));
		payment.setBudgetno(request.getParameter("budgetno"));
		payment.setUse(request.getParameter("use"));
		payment.setSupname(request.getParameter("supname"));
		payment.setSupbank(request.getParameter("supbank"));
		payment.setSupaccount(request.getParameter("supaccount"));
		payment.setSupaddress(request.getParameter("supaddress"));
		payment.setSubject(request.getParameter("subject"));
		payment.setCheckno(request.getParameter("checkno"));
		payment.setRemark(request.getParameter("remark"));
		payment.setStatus(RequestUtils.getInt(request, "status"));
		payment.setProcessname(request.getParameter("processname"));
		payment.setProcessinstanceid(RequestUtils.getLong(request,
				"processinstanceid"));
		payment.setWfstatus(RequestUtils.getDouble(request, "wfstatus"));
		payment.setCreateBy(request.getParameter("createBy"));
		payment.setCreateDate(RequestUtils.getDate(request, "createDate"));
		payment.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		payment.setUpdateBy(request.getParameter("updateBy"));

		paymentService.save(payment);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Payment payment = paymentService.getPayment(RequestUtils.getLong(
				request, "paymentid"));
		request.setAttribute("payment", payment);
		JSONObject rowJSON = payment.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("payment.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/oa/payment/view");
	}
}