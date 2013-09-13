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
package com.glaf.oa.salescontract.web.springmvc;

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
import com.glaf.oa.salescontract.model.Salescontract;
import com.glaf.oa.salescontract.query.SalescontractQuery;
import com.glaf.oa.salescontract.service.SalescontractService;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysUserService;
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

@Controller("/oa/salescontract")
@RequestMapping("/oa/salescontract")
public class SalescontractController {
	protected static final Log logger = LogFactory
			.getLog(SalescontractController.class);

	protected SalescontractService salescontractService;

	protected SysDepartmentService sysDepartmentService;

	protected SysUserService sysUserService;

	public SalescontractController() {

	}

	private boolean completeTask(Salescontract salescontract, int flag,
			HttpServletRequest request) {
		String processName = "SalesContractProcess";
		ProcessContext ctx = new ProcessContext();
		ctx.setRowId(salescontract.getId());
		// 获取申请人
		User user = sysUserService.findByAccount(salescontract.getAppuser());
		// 获取操作者
		User opUser = RequestUtils.getUser(request);
		if (salescontract.getWfstatus() == -5555) {
			ctx.setActorId(salescontract.getAppuser());
		} else {
			ctx.setActorId(opUser.getActorId());
		}

		ctx.setProcessName(processName);
		String opinion = request.getParameter("approveOpinion");
		ctx.setOpinion(opinion);// 审批意见
		/**
		 * 工作流控制参数
		 */
		Collection<DataField> datafields = new ArrayList<DataField>();
		// 获取 部门节点
		long deptId02 = user.getDeptId();
		SysDepartment curdept = sysDepartmentService.findById(deptId02);
		// 获取当地部门节点
		String curAreadeptCode = curdept.getCode().substring(0, 2);
		SysDepartment curAreadept = sysDepartmentService
				.findByCode(curAreadeptCode);

		SysDepartment sysdeptMemSale = sysDepartmentService.findByCode("JT01");

		// 获取当地行政部门
		SysDepartment admindept = sysDepartmentService
				.findByCode(curAreadeptCode + "06");
		DataField datafield1 = new DataField();
		datafield1.setName("deptId01");
		datafield1.setValue(admindept.getId());
		DataField datafield2 = new DataField();
		datafield2.setName("deptId02");
		datafield2.setValue(deptId02);
		DataField datafield3 = new DataField();
		datafield3.setName("deptId03");
		datafield3.setValue(curAreadept.getId());
		DataField datafield4 = new DataField();
		datafield4.setName("deptId04");
		datafield4.setValue(sysdeptMemSale.getId());
		DataField datafield5 = new DataField();
		datafield5.setName("isAgree");
		DataField datafield6 = new DataField();
		datafield6.setName("rowId");
		datafield6.setValue(salescontract.getId());
		if (flag == 0) {
			datafield5.setValue("true");
		} else {
			datafield5.setValue("false");
		}
		datafields.add(datafield1);
		datafields.add(datafield2);
		datafields.add(datafield3);
		datafields.add(datafield4);
		datafields.add(datafield5);
		datafields.add(datafield6);
		ctx.setDataFields(datafields);
		boolean isOK = false;
		if (salescontract.getProcessinstanceid() != null
				&& salescontract.getWfstatus() != 9999
				&& salescontract.getWfstatus() != null) {
			ctx.setProcessInstanceId((new Double(salescontract
					.getProcessinstanceid()).longValue()));

			isOK = ProcessContainer.getContainer().completeTask(ctx);
			logger.info("workflow 中");
		} else {
			long processInstanceId = ProcessContainer.getContainer()
					.startProcess(ctx);
			logger.info("processInstanceId=" + processInstanceId);
			if (processInstanceId != 0) {
				isOK = true;
				logger.info("workflow start");
			}
		}
		return isOK;
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
					Salescontract salescontract = salescontractService
							.getSalescontract(Long.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					if (salescontract != null
							&& (StringUtils.equals(salescontract.getCreateBy(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						if (salescontract.getProcessinstanceid() != 0D
								&& salescontract.getProcessinstanceid() != null) {
							ProcessContext ctx = new ProcessContext();
							ctx.setActorId(loginContext.getActorId());
							ctx.setProcessInstanceId(new Double(salescontract
									.getProcessinstanceid()).longValue());
							ProcessContainer.getContainer().abortProcess(ctx);
						}
						salescontractService.deleteById(salescontract.getId());
					}
				}
			}
		} else if (id != null) {
			Salescontract salescontract = salescontractService
					.getSalescontract(Long.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (salescontract != null
					&& (StringUtils.equals(salescontract.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				if (salescontract.getProcessinstanceid() != null
						&& 0D != salescontract.getProcessinstanceid()) {
					ProcessContext ctx = new ProcessContext();
					ctx.setActorId(loginContext.getActorId());
					ctx.setProcessInstanceId(new Double(salescontract
							.getProcessinstanceid()).longValue());
					ProcessContainer.getContainer().abortProcess(ctx);
				}
				salescontractService.deleteById(salescontract.getId());
			}
		}
	}

	@ResponseBody
	@RequestMapping("/detail")
	public byte[] detail(HttpServletRequest request) throws IOException {
		// RequestUtils.setRequestParameterToAttribute(request);
		// Map<String, Object> params = RequestUtils.getParameterMap(request);
		Salescontract salescontract = salescontractService
				.getSalescontract(RequestUtils.getLong(request, "id"));

		JSONObject rowJSON = salescontract.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Salescontract salescontract = salescontractService
				.getSalescontract(RequestUtils.getLong(request, "id"));
		if (salescontract != null) {
			request.setAttribute("salescontract", salescontract);
			JSONObject rowJSON = salescontract.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
			// 用户名处理
			String appusername = BaseDataManager.getInstance().getStringValue(
					salescontract.getAppuser(), "SYS_USERS");
			request.setAttribute("appusername", appusername);

		} else {
			// 获取 部门节点
			long deptId01 = user.getDeptId();
			SysDepartment curdept = sysDepartmentService.findById(deptId01);
			// 获取当地部门节点
			String curAreadeptCode = curdept.getCode().substring(0, 2);
			request.setAttribute("area", curAreadeptCode);
			salescontract = new Salescontract();
			salescontract.setArea(curAreadeptCode);
			salescontract.setDept(curdept.getCode());
			salescontract.setAppuser(user.getActorId());
			salescontract.setHeadship(RequestUtil.getLoginUser(request)
					.getHeadship());
			salescontract.setOptionalsum(0.00);

			salescontract.setStatus(-1);
			salescontract.setCreateBy(actorId);
			salescontract.setAppdate(new Date());
			salescontract.setCreatedate(new Date());
			salescontractService.save(salescontract);
			request.setAttribute("salescontract", salescontract);
			JSONObject rowJSON = salescontract.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}

		boolean canUpdate = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {

		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (salescontract != null) {
				if (salescontract.getStatus() == 0
						|| salescontract.getStatus() == -1) {
					canUpdate = true;
				}
			}
		}

		request.setAttribute("canUpdate", canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("salescontract.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/oa/salesContract/salesContractedit", modelMap);
	}

	@RequestMapping("/getReviewSalesContract")
	@ResponseBody
	public byte[] getReviewSalesContract(HttpServletRequest request,
			ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SalescontractQuery query = new SalescontractQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setStatusGreaterThanOrEqual(0);// 查询状态大于0的记录，-1为无效
		List<String> actorIds = new ArrayList<String>();

		// 由于页面输入的日期没有时分秒，查找范围的时候获取不到最后一天的记录,人为设置
		if (null != query.getAppdateLessThanOrEqual()) {
			Date appdateLessThanOrEqual = query.getAppdateLessThanOrEqual();
			// appdateLessThanOrEqual.setHours(23);
			// appdateLessThanOrEqual.setMinutes(59);
			// appdateLessThanOrEqual.setSeconds(59);
			query.setAppdateLessThanOrEqual(appdateLessThanOrEqual);
		}

		/**
		 * 此处业务逻辑需自行调整
		 */
		if (null == query.getWorkedProcessFlag()
				|| "".equals(query.getWorkedProcessFlag())) {
			query.setWorkedProcessFlag("WD");
		}
		if (!loginContext.isSystemAdministrator()) {
			query.setActorId(loginContext.getActorId());
			actorIds.add(loginContext.getActorId());
			actorIds.addAll(ProcessContainer.getContainer().getAgentIds(
					loginContext.getActorId()));
			query.setActorIds(actorIds);
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
		int total = salescontractService
				.getReviewSalescontractCountByQueryCriteria(query);
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

			List<Salescontract> list = salescontractService
					.getReviewSalescontractsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				result.put("rows", rowsJSON);
				for (Salescontract salescontract : list) {
					String companyname = salescontract.getCompany();
					String appuser = salescontract.getAppuser();
					try {
						String companyname_CN = BaseDataManager.getInstance()
								.getStringValue(salescontract.getCompanyname(),
										salescontract.getArea());
						salescontract.setCompanyname(companyname_CN);
						// 用户名处理
						String appusername = BaseDataManager.getInstance()
								.getStringValue(salescontract.getAppuser(),
										"SYS_USERS");
						salescontract.setAppuser(appusername);
					} catch (Exception e) {
						salescontract.setAppuser(appuser);
						salescontract.setCompany(companyname);
						logger.error(e.getMessage());
					}
					JSONObject rowJSON = salescontract.toJsonObject();
					rowJSON.put("id", salescontract.getId());
					rowJSON.put("salescontractId", salescontract.getId());
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
	@RequestMapping("/init")
	public ModelAndView init(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String x_query = request.getParameter("x_query");
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
		return new ModelAndView("/oa/salesContract/search_salesContract",
				modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SalescontractQuery query = new SalescontractQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setStatusGreaterThanOrEqual(0);// 查询状态大于0的记录，-1为无效
		User user = loginContext.getUser();
		long deptId01 = user.getDeptId();
		SysDepartment curdept = sysDepartmentService.findById(deptId01);

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
				query.setCreateby(actorId);
			}
		} else if ("0".equals(areaRole)) {
			query.setArea(curdept.getCode().substring(0, 2));
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
			limit = 15;
		}

		JSONObject result = new JSONObject();
		int total = salescontractService
				.getSalescontractCountByQueryCriteria(query);
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

			List<Salescontract> list = salescontractService
					.getSalescontractsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				result.put("rows", rowsJSON);
				for (Salescontract salescontract : list) {
					String companyname = salescontract.getCompany();
					String appuser = salescontract.getAppuser();
					try {
						String companyname_CN = BaseDataManager.getInstance()
								.getStringValue(salescontract.getCompanyname(),
										salescontract.getArea());
						salescontract.setCompanyname(companyname_CN);
						// 用户名处理
						String appusername = BaseDataManager.getInstance()
								.getStringValue(salescontract.getAppuser(),
										"SYS_USERS");
						salescontract.setAppuser(appusername);
					} catch (Exception e) {
						salescontract.setAppuser(appuser);
						salescontract.setCompany(companyname);
						logger.error(e.getMessage());
					}
					JSONObject rowJSON = salescontract.toJsonObject();
					rowJSON.put("id", salescontract.getId());
					rowJSON.put("salescontractId", salescontract.getId());
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
		return new ModelAndView("/oa/salesContract/salesContractlist", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("salescontract.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/oa/salescontract/query", modelMap);
	}

	@RequestMapping("/review")
	public ModelAndView review(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Salescontract salescontract = salescontractService
				.getSalescontract(RequestUtils.getLong(request, "id"));
		if (salescontract != null) {
			request.setAttribute("salescontract", salescontract);
			JSONObject rowJSON = salescontract.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
			// 用户名处理
			String appusername = BaseDataManager.getInstance().getStringValue(
					salescontract.getAppuser(), "SYS_USERS");
			request.setAttribute("appusername", appusername);
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
			if (salescontract != null) {
				if (salescontract.getStatus() == 0
						|| salescontract.getStatus() == -1) {
					canUpdate = true;
				}
			}
		}

		request.setAttribute("canUpdate", canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("salescontract.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/oa/salesContract/salesContractReview",
				modelMap);
	}

	@ResponseBody
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
		return new ModelAndView("/oa/salesContract/salesContractReviewlist",
				modelMap);
	}

	@ResponseBody
	@RequestMapping("/rollbackData")
	public byte[] rollbackData(HttpServletRequest request, ModelMap modelMap) {
		Long id = RequestUtils.getLong(request, "id");
		String ids = request.getParameter("ids");
		boolean returnFlag = false;
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Salescontract salescontract = salescontractService
							.getSalescontract(Long.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */

					if (salescontract.getStatus() == 1) {
						if (null != salescontract.getProcessinstanceid()
								&& 0 != salescontract.getProcessinstanceid()) {
							returnFlag = completeTask(salescontract, 1, request);
						} else {
							returnFlag = startProcess(salescontract, request);
						}
					}
				}
			}
		} else if (id != null) {
			Salescontract salescontract = salescontractService
					.getSalescontract(Long.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */

			if (salescontract.getStatus() == 0
					|| salescontract.getStatus() == 3
					|| salescontract.getStatus() == 1) {
				if (salescontract.getStatus() == 1) {
					if (null != salescontract.getProcessinstanceid()
							&& 0 != salescontract.getProcessinstanceid()) {
						returnFlag = completeTask(salescontract, 1, request);
					} else {
						returnFlag = startProcess(salescontract, request);
					}
				}
			}
		}
		if (returnFlag) {
			return ResponseUtils.responseJsonResult(true);
		} else {
			return ResponseUtils.responseJsonResult(false);
		}
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Salescontract salescontract = new Salescontract();
		Tools.populate(salescontract, params);

		salescontract.setContactname(request.getParameter("contactname"));
		salescontract.setProjrctname(request.getParameter("projrctname"));
		salescontract.setCompanyname(request.getParameter("companyname"));
		salescontract.setSupplisername(request.getParameter("supplisername"));
		salescontract.setCurrency(request.getParameter("currency"));
		salescontract.setContractsum(RequestUtils.getDouble(request,
				"contractsum"));
		salescontract.setPaytype(RequestUtils.getInt(request, "paytype"));
		salescontract.setRemarks(request.getParameter("remarks"));
		salescontract.setAttachment(request.getParameter("attachment"));
		salescontract.setStatus(0);
		salescontract.setAppuser(request.getParameter("appuser"));
		salescontract.setContractno(request.getParameter("contractno"));

		salescontract.setOptionalsum(RequestUtils.getDouble(request,
				"optionalsum"));
		salescontract.setFirstpay(RequestUtils.getDouble(request, "firstpay"));
		salescontract.setLastpay(RequestUtils.getDouble(request, "lastpay"));

		salescontract.setDiscount(RequestUtils.getDouble(request, "discount"));
		salescontract.setDeliverydate(RequestUtils.getDate(request,
				"deliverydate"));
		salescontract.setSales(request.getParameter("sales"));
		salescontract.setContractsales(request.getParameter("contractsales"));
		salescontract.setGiftsum(RequestUtils.getDouble(request, "giftsum"));
		salescontract.setGiftremark(request.getParameter("giftremark"));
		salescontract.setRemark(request.getParameter("remark"));
		salescontract.setHeadship(request.getParameter("headship"));
		salescontract.setDept(request.getParameter("dept"));
		// salescontract.setArea(RequestUtils.getLong(request, "area"));
		salescontract.setCompany(request.getParameter("company"));
		salescontract.setUpdatedate(new Date());
		salescontract.setUpdateby(actorId);

		// 处理地区
		salescontract.setArea(request.getParameter("area"));
		salescontractService.save(salescontract);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveSalescontract")
	public byte[] saveSalescontract(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Salescontract salescontract = new Salescontract();
		Tools.populate(salescontract, params);
		try {
			salescontract.setContactname(request.getParameter("contactname"));
			salescontract.setProjrctname(request.getParameter("projrctname"));
			salescontract.setCompanyname(request.getParameter("companyname"));
			salescontract.setSupplisername(request
					.getParameter("supplisername"));
			salescontract.setCurrency(request.getParameter("currency"));
			salescontract.setContractsum(RequestUtils.getDouble(request,
					"contractsum"));
			salescontract.setPaytype(RequestUtils.getInt(request, "paytype"));
			salescontract.setRemarks(request.getParameter("remarks"));
			salescontract.setAttachment(request.getParameter("attachment"));
			salescontract.setStatus(0);
			salescontract.setAppuser(request.getParameter("appuser"));
			salescontract.setContractno(request.getParameter("contractno"));

			salescontract.setOptionalsum(RequestUtils.getDouble(request,
					"optionalsum"));
			salescontract.setFirstpay(RequestUtils.getDouble(request,
					"firstpay"));
			salescontract
					.setLastpay(RequestUtils.getDouble(request, "lastpay"));

			salescontract.setDiscount(RequestUtils.getDouble(request,
					"discount"));
			salescontract.setDeliverydate(RequestUtils.getDate(request,
					"deliverydate"));
			salescontract.setSales(request.getParameter("sales"));
			salescontract.setContractsales(request
					.getParameter("contractsales"));
			salescontract
					.setGiftsum(RequestUtils.getDouble(request, "giftsum"));
			salescontract.setGiftremark(request.getParameter("giftremark"));
			salescontract.setRemark(request.getParameter("remark"));
			salescontract.setHeadship(request.getParameter("headship"));
			salescontract.setDept(request.getParameter("dept"));
			// salescontract.setArea(RequestUtils.getLong(request, "area"));
			salescontract.setCompany(request.getParameter("company"));
			salescontract.setUpdatedate(new Date());
			salescontract.setUpdateby(actorId);

			// 处理地区
			salescontract.setArea(request.getParameter("area"));
			salescontractService.save(salescontract);
			boolean returnFlag = false;
			salescontract = salescontractService.getSalescontract(salescontract
					.getId());
			if (null != salescontract.getProcessinstanceid()
					&& 0 != salescontract.getProcessinstanceid()) {
				if (salescontract.getWfstatus() == -5555) {
					salescontract.setStatus(1);
					salescontractService.save(salescontract);

				}
				returnFlag = completeTask(salescontract, 0, request);
			} else {
				returnFlag = startProcess(salescontract, request);
			}
			if (returnFlag) {
				return ResponseUtils.responseJsonResult(true);
			} else {
				return ResponseUtils.responseJsonResult(false);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setSalescontractService(
			SalescontractService salescontractService) {
		this.salescontractService = salescontractService;
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

	private boolean startProcess(Salescontract salescontract,
			HttpServletRequest request) {
		String processName = "SalesContractProcess";
		ProcessContext ctx = new ProcessContext();
		ctx.setRowId(salescontract.getId());
		User user = sysUserService.findByAccount(salescontract.getAppuser());
		String actorId = user.getActorId();
		ctx.setActorId(actorId);
		ctx.setProcessName(processName);
		/**
		 * 工作流控制参数
		 */
		Collection<DataField> datafields = new ArrayList<DataField>();
		// 获取 部门节点
		long deptId02 = user.getDeptId();
		SysDepartment curdept = sysDepartmentService.findById(deptId02);
		// 获取当地部门节点
		String curAreadeptCode = curdept.getCode().substring(0, 2);
		SysDepartment curAreadept = sysDepartmentService
				.findByCode(curAreadeptCode);

		SysDepartment sysdeptMemSale = sysDepartmentService.findByCode("JT01");

		// 获取当地行政部门
		SysDepartment admindept = sysDepartmentService
				.findByCode(curAreadeptCode + "06");
		DataField datafield1 = new DataField();
		datafield1.setName("deptId01");
		datafield1.setValue(admindept.getId());
		DataField datafield2 = new DataField();
		datafield2.setName("deptId02");
		datafield2.setValue(deptId02);
		DataField datafield3 = new DataField();
		datafield3.setName("deptId03");
		datafield3.setValue(curAreadept.getId());
		DataField datafield4 = new DataField();
		datafield4.setName("deptId04");
		datafield4.setValue(sysdeptMemSale.getId());
		DataField datafield6 = new DataField();
		datafield6.setName("rowId");
		datafield6.setValue(salescontract.getId());

		datafields.add(datafield1);
		datafields.add(datafield2);
		datafields.add(datafield3);
		datafields.add(datafield4);
		datafields.add(datafield6);
		ctx.setDataFields(datafields);
		boolean isOK = false;
		if (salescontract.getProcessinstanceid() != null
				&& salescontract.getWfstatus() != 9999
				&& salescontract.getWfstatus() != null) {
			isOK = ProcessContainer.getContainer().completeTask(ctx);
			logger.info("workflow 中");
		} else {
			long processInstanceId = ProcessContainer.getContainer()
					.startProcess(ctx);
			logger.info("processInstanceId=" + processInstanceId);
			if (processInstanceId != 0) {
				isOK = true;
				logger.info("workflow start");
			}
		}
		return isOK;
	}

	@ResponseBody
	@RequestMapping("/submit")
	public byte[] submit(HttpServletRequest request, ModelMap modelMap) {
		Long id = RequestUtils.getLong(request, "id");
		String ids = request.getParameter("ids");
		boolean returnFlag = false;
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Salescontract salescontract = salescontractService
							.getSalescontract(Long.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */

					if (salescontract != null) {
						if (null != salescontract.getProcessinstanceid()
								&& 0 != salescontract.getProcessinstanceid()) {

							if (salescontract.getWfstatus() == -5555) {
								salescontract.setStatus(1);
								salescontractService.save(salescontract);

							}

							returnFlag = completeTask(salescontract, 0, request);
						} else {
							returnFlag = startProcess(salescontract, request);
						}
					}
				}
			}
		} else if (id != null) {
			Salescontract salescontract = salescontractService
					.getSalescontract(Long.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (salescontract != null) {
				if (null != salescontract.getProcessinstanceid()
						&& 0 != salescontract.getProcessinstanceid()) {
					if (salescontract.getWfstatus() == -5555) {
						salescontract.setStatus(1);
						salescontractService.save(salescontract);

					}
					returnFlag = completeTask(salescontract, 0, request);
				} else {
					returnFlag = startProcess(salescontract, request);
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

		Salescontract salescontract = salescontractService
				.getSalescontract(RequestUtils.getLong(request, "id"));

		salescontract.setContactname(request.getParameter("contactname"));
		salescontract.setProjrctname(request.getParameter("projrctname"));
		salescontract.setCompanyname(request.getParameter("companyname"));
		salescontract.setSupplisername(request.getParameter("supplisername"));
		salescontract.setCurrency(request.getParameter("currency"));
		salescontract.setContractsum(RequestUtils.getDouble(request,
				"contractsum"));
		salescontract.setPaytype(RequestUtils.getInt(request, "paytype"));
		salescontract.setRemarks(request.getParameter("remarks"));
		salescontract.setAttachment(request.getParameter("attachment"));
		salescontract.setStatus(RequestUtils.getInt(request, "status"));
		salescontract.setAppuser(request.getParameter("appuser"));
		salescontract.setAppdate(RequestUtils.getDate(request, "appdate"));
		salescontract.setContractno(request.getParameter("contractno"));
		salescontract.setProcessname(request.getParameter("processname"));
		salescontract.setProcessinstanceid(RequestUtils.getDouble(request,
				"processinstanceid"));
		salescontract.setWfstatus(RequestUtils.getDouble(request, "wfstatus"));

		salescontract.setOptionalsum(RequestUtils.getDouble(request,
				"optionalsum"));
		salescontract.setFirstpay(RequestUtils.getDouble(request, "firstpay"));
		salescontract.setLastpay(RequestUtils.getDouble(request, "lastpay"));

		salescontract.setDiscount(RequestUtils.getDouble(request, "discount"));
		salescontract.setDeliverydate(RequestUtils.getDate(request,
				"deliverydate"));
		salescontract.setSales(request.getParameter("sales"));
		salescontract.setContractsales(request.getParameter("contractsales"));
		salescontract.setGiftsum(RequestUtils.getDouble(request, "giftsum"));
		salescontract.setGiftremark(request.getParameter("giftremark"));
		salescontract.setRemark(request.getParameter("remark"));
		salescontract.setArea(request.getParameter("area"));
		salescontract.setCompany(request.getParameter("company"));
		salescontract.setCreateBy(request.getParameter("createby"));
		salescontract
				.setCreatedate(RequestUtils.getDate(request, "createdate"));
		salescontract
				.setUpdatedate(RequestUtils.getDate(request, "updatedate"));
		salescontract.setUpdateby(request.getParameter("updateby"));

		salescontractService.save(salescontract);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Salescontract salescontract = salescontractService
				.getSalescontract(RequestUtils.getLong(request, "id"));
		request.setAttribute("salescontract", salescontract);
		JSONObject rowJSON = salescontract.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("salescontract.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/oa/salescontract/view");
	}
}