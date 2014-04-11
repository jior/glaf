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
package com.glaf.oa.budget.web.springmvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
import com.glaf.oa.budget.model.Budget;
import com.glaf.oa.budget.query.BudgetQuery;
import com.glaf.oa.budget.service.BudgetService;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.model.BaseDataInfo;
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

@Controller("/oa/budget")
@RequestMapping("/oa/budget")
public class BudgetController {
	protected static final Log logger = LogFactory
			.getLog(BudgetController.class);

	protected BudgetService budgetService;

	protected SysDepartmentService sysDepartmentService;

	public BudgetController() {

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
	private boolean completeTask(Budget budget, int flag,
			HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		String processName = "Budgetprocess";
		User appUser = BaseDataManager.getInstance().getSysUserService()
				.findByAccount(budget.getAppuser());

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
		ctx.setRowId(budget.getBudgetid());// 表id
		if (budget.getWfstatus() == -5555) {
			ctx.setActorId(budget.getAppuser());
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
		datafield3.setValue(budget.getBudgetid());
		dataFields.add(datafield3);

		ctx.setDataFields(dataFields);

		Long processInstanceId;
		boolean isOK = false;

		if (budget.getProcessinstanceid() != null
				&& budget.getWfstatus() != 9999 && budget.getWfstatus() != null) {
			processInstanceId = new Double(budget.getProcessinstanceid())
					.longValue();
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
		Long budgetid = RequestUtils.getLong(request, "budgetid");
		String budgetids = request.getParameter("budgetids");
		if (StringUtils.isNotEmpty(budgetids)) {
			StringTokenizer token = new StringTokenizer(budgetids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Budget budget = budgetService.getBudget(Long.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					if (budget != null
							&& (StringUtils.equals(budget.getCreateBy(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						if (null != budget.getProcessinstanceid()
								&& budget.getProcessinstanceid() != 0d) {
							ProcessContext ctx = new ProcessContext();
							ctx.setActorId(loginContext.getActorId());
							ctx.setProcessInstanceId(Long.valueOf(budget
									.getProcessinstanceid().longValue()));
							ProcessContainer.getContainer().abortProcess(ctx);
						}
						budgetService.deleteById((budget.getBudgetid()));
					}
				}
			}
		} else if (budgetid != null) {
			Budget budget = budgetService.getBudget(Long.valueOf(budgetid));
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (budget != null
					&& (StringUtils.equals(budget.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				if (null != budget.getProcessinstanceid()
						&& budget.getProcessinstanceid() != 0d) {
					ProcessContext ctx = new ProcessContext();
					ctx.setActorId(loginContext.getActorId());
					ctx.setProcessInstanceId(Long.valueOf(budget
							.getProcessinstanceid().longValue()));
					ProcessContainer.getContainer().abortProcess(ctx);
				}
				budgetService.deleteById((budget.getBudgetid()));
			}
		}
	}

	@ResponseBody
	@RequestMapping("/detail")
	public byte[] detail(HttpServletRequest request) throws IOException {
		// RequestUtils.setRequestParameterToAttribute(request);
		// Map<String, Object> params = RequestUtils.getParameterMap(request);
		Budget budget = budgetService.getBudget(RequestUtils.getLong(request,
				"budgetid"));

		JSONObject rowJSON = budget.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		Budget budget = budgetService.getBudget(RequestUtils.getLong(request,
				"budgetid"));
		List<BaseDataInfo> brandlist = BaseDataManager.getInstance()
				.getDataList("Brand");
		BaseDataInfo brand1 = null;
		BaseDataInfo brand2 = null;
		for (BaseDataInfo info : brandlist) {
			if ("1".equals(info.getValue())) {
				request.setAttribute("brand1", info.getName());
				brand1 = info;
			} else if ("2".equals(info.getValue())) {
				request.setAttribute("brand2", info.getName());
				brand2 = info;
			}
		}
		if (budget != null) {
			request.setAttribute("budget", budget);
			JSONObject rowJSON = budget.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
			// 用户名处理
			String appusername = BaseDataManager.getInstance().getStringValue(
					budget.getAppuser(), "SYS_USERS");
			request.setAttribute("appusername", appusername);
			// 增加品牌检索
			// 增加品牌检索

			if (budget != null) {
				if ("".equals(budget.getBrands1().trim())
						&& "".equals(budget.getBrands2().trim())) {
					request.setAttribute("Brands", "MUL");
				} else if (!"".equals(budget.getBrands1().trim())
						&& "".equals(budget.getBrands2().trim())) {
					request.setAttribute("Brands", brand1.getCode());
				} else if (!"".equals(budget.getBrands2().trim())
						&& "".equals(budget.getBrands1().trim())) {
					request.setAttribute("Brands", brand2.getCode());
				} else if (!"".equals(budget.getBrands2().trim())
						&& !"".equals(budget.getBrands1().trim())) {
					request.setAttribute("Brands", "MUL");
				}
			}
		} else {
			// 获取 部门节点
			budget = new Budget();

			long deptId01 = user.getDeptId();
			SysDepartment curdept = sysDepartmentService.findById(deptId01);
			// 获取当地部门节点
			String curAreadeptCode = curdept.getCode().substring(0, 2);

			request.setAttribute("area", curAreadeptCode);
			budget = new Budget();
			budget.setArea(curAreadeptCode);
			budget.setStatus(-1);
			budget.setDept(curdept.getCode());
			budget.setAppuser(user.getActorId());
			budget.setPost(RequestUtil.getLoginUser(request).getHeadship());
			budget.setCreateBy(actorId);
			budget.setAppdate(new Date());
			budget.setCreateDate(new Date());
			budgetService.save(budget);
			request.setAttribute("budget", budget);
			JSONObject rowJSON = budget.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}
		boolean canUpdate = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {

		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (budget != null) {
				if (budget.getStatus() == 0 || budget.getStatus() == -1) {
					canUpdate = true;
				}
			}
		}

		request.setAttribute("canUpdate", canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("budget.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/oa/budget/budgetedit", modelMap);
	}

	@RequestMapping("/getReviewBudget")
	@ResponseBody
	public byte[] getReviewBudget(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		BudgetQuery query = new BudgetQuery();
		Tools.populate(query, params);
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

		if (null == query.getWorkedProcessFlag()
				|| "".equals(query.getWorkedProcessFlag())) {
			query.setWorkedProcessFlag("WD");
		}
		query.setActorId(loginContext.getActorId());
		actorIds.add(loginContext.getActorId());
		actorIds.addAll(ProcessContainer.getContainer().getAgentIds(
				loginContext.getActorId()));
		query.setActorIds(actorIds);

		/**
		 * 此处业务逻辑需自行调整
		 */
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
		int total = budgetService.getReviewBudgetCountByQueryCriteria(query);
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

			List<Budget> list = budgetService.getReviewBudgetsByQueryCriteria(
					start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Budget budget : list) {
					String area = budget.getArea();
					String companyname = budget.getCompany();
					String appuser = budget.getAppuser();
					String deptname = budget.getDept();
					try {
						String area_CN = BaseDataManager.getInstance()
								.getStringValue(budget.getArea(), "area");
						String companyname_CN = BaseDataManager.getInstance()
								.getStringValue(budget.getCompany(),
										budget.getArea());
						budget.setCompany(companyname_CN);
						budget.setArea(area_CN);
						// 用户名处理
						String appusername = BaseDataManager.getInstance()
								.getStringValue(budget.getAppuser(),
										"SYS_USERS");
						budget.setAppuser(appusername);
						// 部门名称处理
						String dept = BaseDataManager.getInstance()
								.getStringValue(budget.getDept(), "SYS_DEPTS");
						budget.setDept(dept);
					} catch (Exception e) {
						budget.setAppuser(appuser);
						budget.setArea(area);
						budget.setCompany(companyname);
						budget.setDept(deptname);
						logger.error(e.getMessage());
					}
					JSONObject rowJSON = budget.toJsonObject();
					rowJSON.put("id", budget.getBudgetid());
					rowJSON.put("budgetId", budget.getBudgetid());
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

		return new ModelAndView("/oa/budget/search_budgetlist", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		BudgetQuery query = new BudgetQuery();
		Tools.populate(query, params);
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
				query.createBy(actorId);
			}
		} else if ("0".equals(areaRole)) {
			query.setArea(curdept.getCode().substring(0, 2));
			query.setStatusGreaterThanOrEqual(1);
		} else if ("1".equals(areaRole)) {
			query.setStatusGreaterThanOrEqual(1);
		}
		/**
		 * 此处业务逻辑需自行调整
		 */

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
		int total = budgetService.getBudgetCountByQueryCriteria(query);
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

			List<Budget> list = budgetService.getBudgetsByQueryCriteria(start,
					limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Budget budget : list) {
					String area = budget.getArea();
					String companyname = budget.getCompany();
					String appuser = budget.getAppuser();
					String deptname = budget.getDept();
					try {
						String area_CN = BaseDataManager.getInstance()
								.getStringValue(budget.getArea(), "area");
						String companyname_CN = BaseDataManager.getInstance()
								.getStringValue(budget.getCompany(),
										budget.getArea());
						budget.setCompany(companyname_CN);
						budget.setArea(area_CN);
						// 用户名处理
						String appusername = BaseDataManager.getInstance()
								.getStringValue(budget.getAppuser(),
										"SYS_USERS");
						budget.setAppuser(appusername);
						// 部门名称处理
						String dept = BaseDataManager.getInstance()
								.getStringValue(budget.getDept(), "SYS_DEPTS");
						budget.setDept(dept);
					} catch (Exception e) {
						budget.setAppuser(appuser);
						budget.setArea(area);
						budget.setCompany(companyname);
						budget.setDept(deptname);
						logger.error(e.getMessage());
					}
					JSONObject rowJSON = budget.toJsonObject();
					rowJSON.put("id", budget.getBudgetid());
					rowJSON.put("budgetId", budget.getBudgetid());
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

	@RequestMapping("/jsonTo")
	@ResponseBody
	public byte[] jsonTo(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		BudgetQuery query = new BudgetQuery();
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
		int total = budgetService.getBudgetCountByQueryCriteria(query);
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

			List<Budget> list = budgetService.getBudgetsByQueryCriteria(start,
					limit, query);
			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				result.put("rows", rowsJSON);
				for (Budget budget : list) {
					String area_CN = BaseDataManager.getInstance()
							.getStringValue(budget.getArea(), "area");
					budget.setArea(area_CN);
					String companyname_CN = BaseDataManager.getInstance()
							.getStringValue(budget.getCompany(),
									budget.getArea());
					budget.setCompany(companyname_CN);
					// 用户名处理
					String appusername = BaseDataManager.getInstance()
							.getStringValue(budget.getAppuser(), "SYS_USERS");
					budget.setAppuser(appusername);
					JSONObject rowJSON = budget.toJsonObject();
					rowJSON.put("id", budget.getBudgetid());
					rowJSON.put("budgetId", budget.getBudgetid());
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

		return new ModelAndView("/oa/budget/budgetlist", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("budget.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/oa/budget/query", modelMap);
	}

	@RequestMapping("/review")
	public ModelAndView review(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		Budget budget = budgetService.getBudget(RequestUtils.getLong(request,
				"budgetid"));
		if (budget != null) {
			request.setAttribute("budget", budget);
			JSONObject rowJSON = budget.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
			// 用户名处理
			String appusername = BaseDataManager.getInstance().getStringValue(
					budget.getAppuser(), "SYS_USERS");
			request.setAttribute("appusername", appusername);
			// 增加品牌检索
			// 增加品牌检索
			List<BaseDataInfo> brandlist = BaseDataManager.getInstance()
					.getDataList("Brand");
			BaseDataInfo brand1 = null;
			BaseDataInfo brand2 = null;
			for (BaseDataInfo info : brandlist) {
				if ("1".equals(info.getValue())) {
					request.setAttribute("brand1", info.getName());
					brand1 = info;
				} else if ("2".equals(info.getValue())) {
					request.setAttribute("brand2", info.getName());
					brand2 = info;
				}
			}
			if (budget != null) {
				if ("".equals(budget.getBrands1().trim())
						&& "".equals(budget.getBrands2().trim())) {
					request.setAttribute("Brands", "MUL");
				} else if (!"".equals(budget.getBrands1().trim())
						&& "".equals(budget.getBrands2().trim())) {
					request.setAttribute("Brands", brand1.getCode());
				} else if (!"".equals(budget.getBrands2().trim())
						&& "".equals(budget.getBrands1().trim())) {
					request.setAttribute("Brands", brand2.getCode());
				} else if (!"".equals(budget.getBrands2().trim())
						&& !"".equals(budget.getBrands1().trim())) {
					request.setAttribute("Brands", "MUL");
				}
			}
		} else {
			// 获取 部门节点
			long deptId01 = user.getDeptId();
			SysDepartment curdept = sysDepartmentService.findById(deptId01);
			// 获取当地部门节点
			String curAreadeptCode = curdept.getCode().substring(0, 2);

			request.setAttribute("area", curAreadeptCode);
			budget = new Budget();
			budget.setArea(curAreadeptCode);
			budget.setStatus(-1);
			budget.setCreateBy(actorId);
			budget.setAppdate(new Date());
			budget.setCreateDate(new Date());
			budgetService.save(budget);
			request.setAttribute("budget", budget);
		}
		boolean canUpdate = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {

		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (budget != null) {
				if (budget.getStatus() == 0 || budget.getStatus() == -1) {
					canUpdate = true;
				}
			}
		}

		request.setAttribute("canUpdate", canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("budget.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/oa/budget/budgetReview", modelMap);
	}

	@RequestMapping("/reviewList")
	@ResponseBody
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
		return new ModelAndView("/oa/budget/budgetReviewlist", modelMap);
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
		Long budgetid = RequestUtils.getLong(request, "budgetid");
		String budgetids = request.getParameter("budgetids");
		boolean returnFlag = false;
		if (StringUtils.isNotEmpty(budgetids)) {
			StringTokenizer token = new StringTokenizer(budgetids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Budget budget = budgetService.getBudget(Long.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					if (budget != null) {
						if (null != budget.getProcessinstanceid()
								&& 0 != budget.getProcessinstanceid()) {
							returnFlag = completeTask(budget, 1, request);
						} else {
							returnFlag = startProcess(budget, request);
						}
					}
				}
			}
		} else if (budgetid != null) {
			Budget budget = budgetService.getBudget(Long.valueOf(budgetid));
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (budget != null) {
				if (null != budget.getProcessinstanceid()
						&& 0 != budget.getProcessinstanceid()) {
					returnFlag = completeTask(budget, 1, request);
				} else {
					returnFlag = startProcess(budget, request);
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

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Budget budget = new Budget();
		try {
			Tools.populate(budget, params);
			budget.setBudgetno(request.getParameter("budgetno"));
			budget.setArea(request.getParameter("area"));
			budget.setCompany(request.getParameter("company"));
			budget.setDept(request.getParameter("dept"));
			budget.setPost(request.getParameter("post"));
			budget.setAppuser(request.getParameter("appuser"));
			budget.setAppdate(RequestUtils.getDate(request, "appdate"));
			budget.setProname(request.getParameter("proname"));
			budget.setBudgetsum(RequestUtils.getDouble(request, "budgetsum"));
			budget.setCurrency(request.getParameter("currency"));
			budget.setPaymentmodel(RequestUtils.getInt(request, "paymentmodel"));
			budget.setPaymenttype(RequestUtils.getInt(request, "paymenttype"));
			budget.setPaymentdate(RequestUtils.getDate(request, "paymentdate1"));
			budget.setSupname(request.getParameter("supname"));
			budget.setSupaccount(request.getParameter("supaccount"));
			budget.setSupbank(request.getParameter("supbank"));
			budget.setSupaddress(request.getParameter("supaddress"));
			budget.setUpdateBy(user.getActorId());
			budget.setUpdateDate(new Date());
			budget.setStatus(0);
			// 增加品牌处理
			List<BaseDataInfo> brandlist = BaseDataManager.getInstance()
					.getDataList("Brand");
			BaseDataInfo brand1 = null;
			BaseDataInfo brand2 = null;
			for (BaseDataInfo info : brandlist) {
				if ("1".equals(info.getValue())) {
					request.setAttribute("brand1", info.getName());
					brand1 = info;
				} else if ("2".equals(info.getValue())) {
					request.setAttribute("brand2", info.getName());
					brand2 = info;
				}
			}
			if (brand1.getCode().equals(request.getParameter("Brands"))) {
				budget.setBrands1(brand1.getCode());
				budget.setBrands1account(100d);
				budget.setBrands2(" ");
				budget.setBrands2account(0d);
			} else if (brand2.getCode().equals(request.getParameter("Brands"))) {
				budget.setBrands2(brand2.getCode());
				budget.setBrands2account(100d);
				budget.setBrands1(" ");
				budget.setBrands1account(0d);
			} else if ("MUL".equals(request.getParameter("Brands"))) {
				budget.setBrands1(brand1.getCode());
				budget.setBrands2(brand2.getCode());
				budget.setBrands1account(RequestUtils.getDouble(request,
						"brands1account"));
				budget.setBrands2account(RequestUtils.getDouble(request,
						"brands2account"));
			}
			this.budgetService.save(budget);

			budget = budgetService.getBudget(budget.getBudgetid());
			boolean returnFlag = false;
			if (null != budget.getProcessinstanceid()
					&& 0 != budget.getProcessinstanceid()) {
				if (budget.getWfstatus() == -5555) {
					budget.setStatus(1);
					budgetService.save(budget);
				}
				returnFlag = completeTask(budget, 0, request);
			} else {
				returnFlag = startProcess(budget, request);
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

	@ResponseBody
	@RequestMapping("/saveBudget")
	public byte[] saveBudget(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Budget budget = new Budget();
		try {
			Tools.populate(budget, params);
			budget.setBudgetno(request.getParameter("budgetno"));
			budget.setArea(request.getParameter("area"));
			budget.setCompany(request.getParameter("company"));
			budget.setDept(request.getParameter("dept"));
			budget.setPost(request.getParameter("post"));
			budget.setAppuser(request.getParameter("appuser"));
			budget.setAppdate(RequestUtils.getDate(request, "appdate"));
			budget.setProname(request.getParameter("proname"));
			budget.setBudgetsum(RequestUtils.getDouble(request, "budgetsum"));
			budget.setCurrency(request.getParameter("currency"));
			budget.setPaymentmodel(RequestUtils.getInt(request, "paymentmodel"));
			budget.setPaymenttype(RequestUtils.getInt(request, "paymenttype"));
			budget.setPaymentdate(RequestUtils.getDate(request, "paymentdate1"));
			budget.setSupname(request.getParameter("supname"));
			budget.setSupaccount(request.getParameter("supaccount"));
			budget.setSupbank(request.getParameter("supbank"));
			budget.setSupaddress(request.getParameter("supaddress"));
			budget.setUpdateBy(user.getActorId());
			budget.setUpdateDate(new Date());
			budget.setStatus(0);
			// 增加品牌处理
			List<BaseDataInfo> brandlist = BaseDataManager.getInstance()
					.getDataList("Brand");
			BaseDataInfo brand1 = null;
			BaseDataInfo brand2 = null;
			for (BaseDataInfo info : brandlist) {
				if ("1".equals(info.getValue())) {
					request.setAttribute("brand1", info.getName());
					brand1 = info;
				} else if ("2".equals(info.getValue())) {
					request.setAttribute("brand2", info.getName());
					brand2 = info;
				}
			}
			if (brand1.getCode().equals(request.getParameter("Brands"))) {
				budget.setBrands1(brand1.getCode());
				budget.setBrands1account(100d);
				budget.setBrands2(" ");
				budget.setBrands2account(0d);
			} else if (brand2.getCode().equals(request.getParameter("Brands"))) {
				budget.setBrands2(brand2.getCode());
				budget.setBrands2account(100d);
				budget.setBrands1(" ");
				budget.setBrands1account(0d);
			} else if ("MUL".equals(request.getParameter("Brands"))) {
				budget.setBrands1(brand1.getCode());
				budget.setBrands2(brand2.getCode());
				budget.setBrands1account(RequestUtils.getDouble(request,
						"brands1account"));
				budget.setBrands2account(RequestUtils.getDouble(request,
						"brands2account"));
			}
			this.budgetService.save(budget);
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setBudgetService(BudgetService budgetService) {
		this.budgetService = budgetService;
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
	private boolean startProcess(Budget budget, HttpServletRequest request) {

		String processName = "Budgetprocess";

		// 获取登录用户部门
		User appUser = BaseDataManager.getInstance().getSysUserService()
				.findByAccount(budget.getAppuser());

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
		ctx.setRowId(budget.getBudgetid());// 表id
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
		datafield3.setValue(budget.getBudgetid());
		dataFields.add(datafield3);

		// DataField dataField = new DataField();
		// dataField.setName("isAgree");// 是否通过审批
		// dataField.setValue("true");
		// dataFields.add(dataField);

		ctx.setDataFields(dataFields);

		Long processInstanceId = null;
		boolean isOK = false;

		if (budget.getProcessinstanceid() != null
				&& budget.getWfstatus() != 9999 && budget.getWfstatus() != null) {
			processInstanceId = new Double(budget.getProcessinstanceid())
					.longValue();
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

		Long budgetid = RequestUtils.getLong(request, "budgetid");
		String budgetids = request.getParameter("budgetids");
		boolean returnFlag = false;
		if (StringUtils.isNotEmpty(budgetids)) {
			StringTokenizer token = new StringTokenizer(budgetids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Budget budget = budgetService.getBudget(Long.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					if (budget != null) {
						if (null != budget.getProcessinstanceid()
								&& 0 != budget.getProcessinstanceid()) {
							if (budget.getWfstatus() == -5555) {
								budget.setStatus(1);
								budgetService.save(budget);

							}
							returnFlag = completeTask(budget, 0, request);
						} else {
							returnFlag = startProcess(budget, request);
						}
					}
				}
			}
		} else if (budgetid != null) {
			Budget budget = budgetService.getBudget(Long.valueOf(budgetid));
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (budget != null) {
				if (null != budget.getProcessinstanceid()
						&& 0 != budget.getProcessinstanceid()) {
					if (budget.getWfstatus() == -5555) {
						budget.setStatus(1);
						budgetService.save(budget);

					}
					returnFlag = completeTask(budget, 0, request);
				} else {
					returnFlag = startProcess(budget, request);
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

		Budget budget = budgetService.getBudget(RequestUtils.getLong(request,
				"budgetid"));

		budget.setBudgetno(request.getParameter("budgetno"));
		budget.setArea(request.getParameter("area"));
		budget.setCompany(request.getParameter("company"));
		budget.setDept(request.getParameter("dept"));
		budget.setPost(request.getParameter("post"));
		budget.setAppuser(request.getParameter("appuser"));
		budget.setAppdate(RequestUtils.getDate(request, "appdate"));
		budget.setProname(request.getParameter("proname"));
		budget.setBudgetsum(RequestUtils.getDouble(request, "budgetsum"));
		budget.setCurrency(request.getParameter("currency"));
		budget.setPaymentmodel(RequestUtils.getInt(request, "paymentmodel"));
		budget.setPaymenttype(RequestUtils.getInt(request, "paymenttype"));
		budget.setSupname(request.getParameter("supname"));
		budget.setSupaccount(request.getParameter("supaccount"));
		budget.setSupbank(request.getParameter("supbank"));
		budget.setSupaddress(request.getParameter("supaddress"));
		budget.setAttachment(request.getParameter("attachment"));
		budget.setStatus(RequestUtils.getInt(request, "status"));
		budget.setProcessname(request.getParameter("processname"));
		budget.setProcessinstanceid(RequestUtils.getDouble(request,
				"processinstanceid"));
		budget.setWfstatus(RequestUtils.getDouble(request, "wfstatus"));
		budget.setBrands1(request.getParameter("brands1"));
		budget.setBrands1account(RequestUtils.getDouble(request,
				"brands1account"));
		budget.setBrands2(request.getParameter("brands2"));
		budget.setBrands2account(RequestUtils.getDouble(request,
				"brands2account"));
		budget.setBrands3(request.getParameter("brands3"));
		budget.setBrands3account(RequestUtils.getDouble(request,
				"brands3account"));
		budget.setPaymentdate(RequestUtils.getDate(request, "paymentdate1"));
		budget.setRemark(request.getParameter("remark"));
		budget.setCreateBy(request.getParameter("createBy"));
		budget.setCreateDate(RequestUtils.getDate(request, "createDate"));
		budget.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		budget.setUpdateBy(request.getParameter("updateBy"));

		budgetService.save(budget);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Budget budget = budgetService.getBudget(RequestUtils.getLong(request,
				"budgetid"));
		request.setAttribute("budget", budget);
		JSONObject rowJSON = budget.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("budget.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/oa/budget/view");
	}

}