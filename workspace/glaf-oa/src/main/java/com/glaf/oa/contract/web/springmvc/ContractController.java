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
package com.glaf.oa.contract.web.springmvc;

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
import com.glaf.oa.contract.model.Contract;
import com.glaf.oa.contract.query.ContractQuery;
import com.glaf.oa.contract.service.ContractService;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.others.service.AttachmentService;
import com.glaf.base.modules.sys.model.BaseDataInfo;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysUser;
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
import com.glaf.report.service.IReportService;

@Controller("/oa/contract")
@RequestMapping("/oa/contract")
public class ContractController {
	protected static final Log logger = LogFactory
			.getLog(ContractController.class);

	protected ContractService contractService;

	protected AttachmentService attachmentService;

	protected SysDepartmentService sysDepartmentService;

	protected SysUserService sysUserService;

	protected IReportService reportService;

	public ContractController() {

	}

	private boolean completeTask(Contract contract, int flag,
			HttpServletRequest request) {

		User user = sysUserService.findByAccount(RequestUtils
				.getActorId(request));
		String actorId = user.getActorId();
		String processName = "Contractprocess";

		ProcessContext ctx = new ProcessContext();
		ctx.setRowId(contract.getId());
		if (contract.getWfstatus() == -5555) {
			ctx.setActorId(contract.getAppuser());
		} else {
			ctx.setActorId(actorId);
		}
		ctx.setProcessName(processName);
		String opinion = request.getParameter("approveOpinion");
		ctx.setOpinion(opinion);// 审批意见
		/**
		 * 工作流控制参数
		 */
		Collection<DataField> datafields = new ArrayList<DataField>();
		// 获取 部门节点
		long deptId01 = user.getDeptId();
		SysDepartment curdept = sysDepartmentService.findById(deptId01);
		// 获取当地部门节点
		String curAreadeptCode = curdept.getCode().substring(0, 2);
		SysDepartment curAreadept = sysDepartmentService
				.findByCode(curAreadeptCode);
		// 获取集团节点
		SysDepartment sysdeptMem = sysDepartmentService.findByCode("JT");
		// 获取集团部门节点
		String jtdeptCode = "";
		if (curdept.getCode().length() >= 4) {
			jtdeptCode = "JT" + curdept.getCode().substring(2, 4);
		}
		SysDepartment sysJtdept = sysDepartmentService.findByCode(jtdeptCode);
		String useId = "";
		String userId = null;
		List<SysUser> list = sysUserService.getSuperiors(user.getActorId());
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				SysUser use = list.get(i);
				useId = useId + use.getActorId() + ",";
				if (i == list.size() - 1) {
					userId = useId.substring(0, useId.length() - 1);
				}
			}
		}
		DataField datafield1 = new DataField();
		datafield1.setName("deptId02");
		datafield1.setValue(curAreadept.getId());
		DataField datafield2 = new DataField();
		datafield2.setName("deptId04");
		datafield2.setValue(sysdeptMem.getId());
		DataField datafield3 = new DataField();
		datafield3.setName("userId");
		datafield3.setValue(userId);
		DataField datafield4 = new DataField();
		datafield4.setName("monthAccount");
		datafield4.setValue(Double.valueOf(contract.getContractsum()));
		DataField datafield5 = new DataField();
		datafield5.setName("isAgree");
		DataField datafield6 = new DataField();
		datafield6.setName("rowId");
		datafield6.setValue(contract.getId());
		DataField datafield7 = new DataField();
		datafield7.setName("deptId03");
		if (null != sysJtdept) {
			datafield7.setValue(sysJtdept.getId());
		} else {
			datafield7.setValue("");
		}
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
		datafields.add(datafield7);
		ctx.setDataFields(datafields);

		String processInstanceId = null;
		boolean isOK = false;

		if (contract.getProcessinstanceid() != null
				&& contract.getWfstatus() != 9999
				&& contract.getWfstatus() != null) {
			processInstanceId = contract.getProcessinstanceid();
			ctx.setProcessInstanceId(Long.parseLong(processInstanceId));
			isOK = ProcessContainer.getContainer().completeTask(ctx);
			logger.info("workflow 中");
		} else {
			processInstanceId = ProcessContainer.getContainer()
					.startProcess(ctx).toString();
			logger.info("processInstanceId=" + processInstanceId);
			if (!"".equals(processInstanceId) && processInstanceId != null) {
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
					Contract contract = contractService.getContract(Long
							.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					if (contract != null
							&& (StringUtils.equals(contract.getCreateBy(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						if (null != contract.getProcessinstanceid()) {
							ProcessContext ctx = new ProcessContext();
							ctx.setActorId(loginContext.getActorId());
							ctx.setProcessInstanceId(Long.valueOf(contract
									.getProcessinstanceid()));
							ProcessContainer.getContainer().abortProcess(ctx);
						}
						contractService.deleteById(contract.getId());
					}
				}
			}
		} else if (id != null) {
			Contract contract = contractService.getContract(Long.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (contract != null
					&& (StringUtils.equals(contract.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				if (null != contract.getProcessinstanceid()) {
					ProcessContext ctx = new ProcessContext();
					ctx.setActorId(loginContext.getActorId());
					ctx.setProcessInstanceId(Long.valueOf(contract
							.getProcessinstanceid()));
					ProcessContainer.getContainer().abortProcess(ctx);
				}
				contractService.deleteById(contract.getId());
			}
		}
	}

	@ResponseBody
	@RequestMapping("/detail")
	public byte[] detail(HttpServletRequest request) throws IOException {
		// RequestUtils.setRequestParameterToAttribute(request);
		// Map<String, Object> params = RequestUtils.getParameterMap(request);
		Contract contract = contractService.getContract(RequestUtils.getLong(
				request, "id"));

		JSONObject rowJSON = contract.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Contract contract = contractService.getContract(RequestUtils.getLong(
				request, "id"));
		if (contract != null) {
			request.setAttribute("contract", contract);
			JSONObject rowJSON = contract.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}

		boolean canUpdate = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {

		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (contract != null) {
				if (contract.getStatus() == 0 || contract.getStatus() == -1) {
					canUpdate = true;
				}
			}
		}

		request.setAttribute("canUpdate", canUpdate);

		if (contract != null) {

		} else {
			contract = new Contract();
			long deptId01 = user.getDeptId();
			SysDepartment curdept = sysDepartmentService.findById(deptId01);
			// 获取当地部门节点
			String curAreadeptCode = curdept.getCode().substring(0, 2);

			contract.setArea(curAreadeptCode);
			contract.setDept(curdept.getCode());
			contract.setAppuser(user.getActorId());
			contract.setPost(RequestUtil.getLoginUser(request).getHeadship());
			request.setAttribute("contract", contract);
			JSONObject rowJSON = contract.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("contract.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/oa/contract/edit", modelMap);
	}

	@RequestMapping("/getReviewContract")
	@ResponseBody
	public byte[] getReviewContract(HttpServletRequest request,
			ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ContractQuery query = new ContractQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setLoginContext(loginContext);
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
		 * 页面初始化默认是待审核状态
		 */
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
		int total = contractService
				.getReviewContractCountByQueryCriteria(query);
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

			List<Contract> list = contractService
					.getReviewContractsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Contract contract : list) {
					String area = contract.getArea();
					String companyname = contract.getCompanyname();
					String appuser = contract.getAppuser();
					try {
						String area_CN = BaseDataManager.getInstance()
								.getStringValue(contract.getArea(), "area");
						String companyname_CN = BaseDataManager.getInstance()
								.getStringValue(contract.getCompanyname(),
										contract.getArea());
						contract.setCompanyname(companyname_CN);
						contract.setArea(area_CN);
						// 用户名处理
						String appusername = BaseDataManager.getInstance()
								.getStringValue(contract.getAppuser(),
										"SYS_USERS");
						contract.setAppuser(appusername);
					} catch (Exception e) {
						contract.setArea(area);
						contract.setAppuser(appuser);
						contract.setCompanyname(companyname);
						logger.error(e.getMessage());
					}
					JSONObject rowJSON = contract.toJsonObject();
					rowJSON.put("id", contract.getId());
					rowJSON.put("contractId", contract.getId());
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

		return new ModelAndView("/oa/contract/search_list", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ContractQuery query = new ContractQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());

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
		query.setLoginContext(loginContext);

		/**
		 * 此处业务逻辑需自行调整
		 */

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
		int total = contractService.getContractCountByQueryCriteria(query);
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

			List<Contract> list = contractService.getContractsByQueryCriteria(
					start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Contract contract : list) {
					String area = contract.getArea();
					String companyname = contract.getCompanyname();
					String appuser = contract.getAppuser();
					try {
						String area_CN = BaseDataManager.getInstance()
								.getStringValue(contract.getArea(), "area");
						String companyname_CN = BaseDataManager.getInstance()
								.getStringValue(contract.getCompanyname(),
										contract.getArea());
						contract.setCompanyname(companyname_CN);
						contract.setArea(area_CN);
						// 用户名处理
						String appusername = BaseDataManager.getInstance()
								.getStringValue(contract.getAppuser(),
										"SYS_USERS");
						contract.setAppuser(appusername);
					} catch (Exception e) {
						contract.setArea(area);
						contract.setAppuser(appuser);
						contract.setCompanyname(companyname);
						logger.error(e.getMessage());
					}
					JSONObject rowJSON = contract.toJsonObject();
					rowJSON.put("id", contract.getId());
					rowJSON.put("contractId", contract.getId());
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

		return new ModelAndView("/oa/contract/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("contract.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/oa/contract/query", modelMap);
	}

	// review
	@RequestMapping("/review")
	public ModelAndView review(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		Contract contract = contractService.getContract(RequestUtils.getLong(
				request, "id"));
		if (RequestUtils.getBoolean(request, "lookover")) {
			request.setAttribute("lookover", true);
		} else {
			request.setAttribute("lookover", false);
		}

		if (contract != null) {
			request.setAttribute("contract", contract);
			JSONObject rowJSON = contract.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}

		boolean canUpdate = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {

		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (contract != null) {
				if (contract.getStatus() == 0 || contract.getStatus() == -1) {
					canUpdate = true;
				}
			}
		}

		request.setAttribute("canUpdate", canUpdate);

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
		if (contract != null) {
			if ("".equals(contract.getBrands1().trim())
					&& "".equals(contract.getBrands2().trim())) {
				request.setAttribute("Brands", "MUL");
			} else if (!"".equals(contract.getBrands1().trim())
					&& "".equals(contract.getBrands2().trim())) {
				request.setAttribute("Brands", brand1.getCode());
			} else if (!"".equals(contract.getBrands2().trim())
					&& "".equals(contract.getBrands1().trim())) {
				request.setAttribute("Brands", brand2.getCode());
			} else if (!"".equals(contract.getBrands2().trim())
					&& !"".equals(contract.getBrands1().trim())) {
				request.setAttribute("Brands", "MUL");
			}
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("contract.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/oa/contract/review", modelMap);
	}

	// reviewList
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
		return new ModelAndView("/oa/contract/reviewlist", modelMap);
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
					Contract contract = contractService.getContract(Long
							.valueOf(x));
					// 添加提交业务逻辑
					if (contract.getStatus() == 1) {
						if (contract.getProcessinstanceid() != null
								&& contract.getProcessinstanceid().trim()
										.length() > 0) {
							returnFlag = completeTask(contract, 1, request);
						} else {
							returnFlag = startProcess(contract, request);
						}
					}
				}
			}
		} else if (id != null) {
			Contract contract = contractService.getContract(Long.valueOf(id));
			// 添加提交业务逻辑
			if (contract.getStatus() == 1) {
				if (contract.getProcessinstanceid() != null
						&& contract.getProcessinstanceid().trim().length() > 0) {
					returnFlag = completeTask(contract, 1, request);
				} else {
					returnFlag = startProcess(contract, request);
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
		try {
			User user = RequestUtils.getUser(request);
			String actorId = user.getActorId();
			Map<String, Object> params = RequestUtils.getParameterMap(request);
			params.remove("status");
			params.remove("wfStatus");
			Contract contract = new Contract();
			Tools.populate(contract, params);
			contract.setContactname(request.getParameter("contactname"));
			contract.setProjrctname(request.getParameter("projrctname"));
			contract.setCompanyname(request.getParameter("companyname"));
			contract.setSupplisername(request.getParameter("supplisername"));
			contract.setCurrency(request.getParameter("currency"));
			contract.setContractsum(RequestUtils.getDouble(request,
					"contractsum"));
			contract.setPaytype(RequestUtils.getInt(request, "paytype"));
			contract.setRemarks(request.getParameter("remarks"));
			contract.setAttachment(request.getParameter("attachment"));
			contract.setStatus(RequestUtils.getInt(request, "status"));
			contract.setAppuser(request.getParameter("appuser"));
			contract.setAppdate(RequestUtils.getDate(request, "appdate"));
			contract.setContractno(request.getParameter("contractno"));
			contract.setArea(request.getParameter("area"));
			contract.setDept(request.getParameter("dept"));
			contract.setPost(request.getParameter("post"));
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
				contract.setBrands1(brand1.getCode());
				contract.setBrands1account(100d);
				contract.setBrands2(" ");
				contract.setBrands2account(0d);
			} else if (brand2.getCode().equals(request.getParameter("Brands"))) {
				contract.setBrands2(brand2.getCode());
				contract.setBrands2account(100d);
				contract.setBrands1(" ");
				contract.setBrands1account(0d);
			} else if ("MUL".equals(request.getParameter("Brands"))) {
				contract.setBrands1(brand1.getCode());
				contract.setBrands2(brand2.getCode());
				contract.setBrands1account(RequestUtils.getDouble(request,
						"brands1account"));
				contract.setBrands2account(RequestUtils.getDouble(request,
						"brands2account"));
			}

			// 新增处理
			if (null == contract.getId()) {
				contract.setAppdate(new Date());
				contract.setStatus(0);
			}
			// 修改处理
			else {
				contract.setUpdateBy(user.getActorId());
				contract.setUpdateDate(new Date());
				contract.setStatus(0);
			}
			long flag = contract.getId() == null ? 0 : contract.getId();
			contract.setCreateBy(actorId);
			this.contractService.save(contract);
			if (flag == 0) {
				this.attachmentService.updateByReferTypeAndCreateId(
						contract.getId(), 2, user.getId());
			}
			boolean isok = false;
			contract = contractService.getContract(contract.getId());
			if (null != contract.getProcessinstanceid()) {
				contract.setStatus(1);
				this.contractService.save(contract);
				isok = this.completeTask(contract, 0, request);
			} else {
				isok = this.startProcess(contract, request);
			}
			if (isok) {
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
	@RequestMapping("/saveContract")
	public byte[] saveContract(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Contract contract = new Contract();
		try {
			Tools.populate(contract, params);
			contract.setContactname(request.getParameter("contactname"));
			contract.setProjrctname(request.getParameter("projrctname"));
			contract.setCompanyname(request.getParameter("companyname"));
			contract.setSupplisername(request.getParameter("supplisername"));
			contract.setCurrency(request.getParameter("currency"));
			contract.setContractsum(RequestUtils.getDouble(request,
					"contractsum"));
			contract.setPaytype(RequestUtils.getInt(request, "paytype"));
			contract.setRemarks(request.getParameter("remarks"));
			contract.setAttachment(request.getParameter("attachment"));
			contract.setStatus(RequestUtils.getInt(request, "status"));
			contract.setAppusername(request.getParameter("appusername"));
			contract.setContractno(request.getParameter("contractno"));
			contract.setArea(request.getParameter("area"));
			contract.setDept(request.getParameter("dept"));
			contract.setPost(request.getParameter("post"));
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
				contract.setBrands1(brand1.getCode());
				contract.setBrands1account(100d);
				contract.setBrands2(" ");
				contract.setBrands2account(0d);
			} else if (brand2.getCode().equals(request.getParameter("Brands"))) {
				contract.setBrands2(brand2.getCode());
				contract.setBrands2account(100d);
				contract.setBrands1(" ");
				contract.setBrands1account(0d);
			} else if ("MUL".equals(request.getParameter("Brands"))) {
				contract.setBrands1(brand1.getCode());
				contract.setBrands2(brand2.getCode());
				contract.setBrands1account(RequestUtils.getDouble(request,
						"brands1account"));
				contract.setBrands2account(RequestUtils.getDouble(request,
						"brands2account"));
			}

			// 新增处理
			if (null == contract.getId()) {
				contract.setAppdate(new Date());
				contract.setStatus(0);
			}
			// 修改处理
			else {
				contract.setUpdateBy(user.getActorId());
				contract.setUpdateDate(new Date());
				contract.setStatus(0);
			}
			long flag = contract.getId() == null ? 0 : contract.getId();
			contract.setCreateBy(actorId);
			this.contractService.save(contract);

			if (flag == 0) {
				this.attachmentService.updateByReferTypeAndCreateId(
						contract.getId(), 2, user.getId());
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
	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}

	@javax.annotation.Resource
	public void setReportService(IReportService reportService) {
		this.reportService = reportService;
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

	private boolean startProcess(Contract contract, HttpServletRequest request) {
		User user = sysUserService.findByAccount(contract.getAppuser());

		String processName = "Contractprocess";

		ProcessContext ctx = new ProcessContext();
		ctx.setRowId(contract.getId());
		ctx.setActorId(contract.getAppuser());
		ctx.setProcessName(processName);
		/**
		 * 工作流控制参数
		 */
		Collection<DataField> datafields = new ArrayList<DataField>();
		// 获取 部门节点
		long deptId01 = user.getDeptId();
		SysDepartment curdept = sysDepartmentService.findById(deptId01);
		// 获取当地部门节点
		String curAreadeptCode = curdept.getCode().substring(0, 2);
		// 地区
		SysDepartment curAreadept = sysDepartmentService
				.findByCode(curAreadeptCode);
		// 获取集团节点
		SysDepartment sysdeptMem = sysDepartmentService.findByCode("JT");
		// 获取集团部门节点
		String jtdeptCode = null;
		if (curdept.getCode().length() >= 4) {
			jtdeptCode = "JT" + curdept.getCode().substring(2, 4);
		}
		SysDepartment sysJtdept = sysDepartmentService.findByCode(jtdeptCode);

		String useId = "";
		String userId = "";
		List<SysUser> list = sysUserService.getSuperiors(contract.getAppuser());
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				SysUser use = list.get(i);
				useId = useId + use.getActorId() + ",";
				if (i == list.size() - 1) {
					userId = useId.substring(0, useId.length() - 1);
				}
			}
		}
		DataField datafield1 = new DataField();
		datafield1.setName("deptId02");
		datafield1.setValue(curAreadept.getId());
		DataField datafield2 = new DataField();
		datafield2.setName("deptId04");
		datafield2.setValue(sysdeptMem.getId());
		DataField datafield3 = new DataField();
		datafield3.setName("userId");
		datafield3.setValue(userId);
		DataField datafield4 = new DataField();
		datafield4.setName("monthAccount");
		datafield4.setValue(Double.valueOf(contract.getContractsum()));
		DataField datafield6 = new DataField();
		datafield6.setName("rowId");
		datafield6.setValue(contract.getId());
		DataField datafield7 = new DataField();
		datafield7.setName("deptId03");
		datafield7.setValue(sysJtdept.getId());

		datafields.add(datafield1);
		datafields.add(datafield2);
		datafields.add(datafield3);
		datafields.add(datafield4);
		datafields.add(datafield6);
		datafields.add(datafield7);
		ctx.setDataFields(datafields);

		String processInstanceId = null;
		boolean isOK = false;

		if (contract.getProcessinstanceid() != null
				&& contract.getWfstatus() != 9999
				&& contract.getWfstatus() != null) {
			processInstanceId = contract.getProcessinstanceid();
			ctx.setProcessInstanceId(Long.parseLong(processInstanceId));
			isOK = ProcessContainer.getContainer().completeTask(ctx);
			logger.info("workflow 中");
		} else {
			processInstanceId = ProcessContainer.getContainer()
					.startProcess(ctx).toString();
			logger.info("processInstanceId=" + processInstanceId);
			if (!"".equals(processInstanceId) && processInstanceId != null) {
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
					Contract contract = contractService.getContract(Long
							.valueOf(x));
					// 添加提交业务逻辑
					if (contract.getStatus() == 0 || contract.getStatus() == 3) {
						if (contract.getProcessinstanceid() != null
								&& contract.getProcessinstanceid().trim()
										.length() > 0) {
							contract.setStatus(1);
							contractService.save(contract);
							returnFlag = completeTask(contract, 0, request);
						} else {
							returnFlag = startProcess(contract, request);
						}
					}
				}
			}
		} else if (id != null) {
			Contract contract = contractService.getContract(Long.valueOf(id));
			// 添加提交业务逻辑
			if (contract.getStatus() == 0 || contract.getStatus() == 3) {
				if (contract.getProcessinstanceid() != null
						&& contract.getProcessinstanceid().trim().length() > 0) {
					returnFlag = completeTask(contract, 0, request);
				} else {
					returnFlag = startProcess(contract, request);
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
	@RequestMapping("/submitData")
	public byte[] submitData(HttpServletRequest request, ModelMap modelMap) {
		Long id = RequestUtils.getLong(request, "id");
		String ids = request.getParameter("ids");
		boolean returnFlag = true;
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Contract contract = contractService.getContract(Long
							.valueOf(x));
					// 添加提交业务逻辑
					if (contract.getStatus() == 1) {
						if (contract.getProcessinstanceid() != null
								&& contract.getProcessinstanceid().trim()
										.length() > 0) {
							returnFlag = returnFlag
									&& completeTask(contract, 0, request);
						} else {
							returnFlag = returnFlag
									&& startProcess(contract, request);
						}
					}
				}
			}
		} else if (id != null) {
			Contract contract = contractService.getContract(Long.valueOf(id));
			// 添加提交业务逻辑
			if (contract.getStatus() == 1) {
				if (contract.getProcessinstanceid() != null
						&& contract.getProcessinstanceid().trim().length() > 0) {
					returnFlag = completeTask(contract, 0, request);
				} else {
					returnFlag = startProcess(contract, request);
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

		Contract contract = contractService.getContract(RequestUtils.getLong(
				request, "id"));

		contract.setContactname(request.getParameter("contactname"));
		contract.setProjrctname(request.getParameter("projrctname"));
		contract.setCompanyname(request.getParameter("companyname"));
		contract.setSupplisername(request.getParameter("supplisername"));
		contract.setCurrency(request.getParameter("currency"));
		contract.setContractsum(RequestUtils.getDouble(request, "contractsum"));
		contract.setPaytype(RequestUtils.getInt(request, "paytype"));
		contract.setRemarks(request.getParameter("remarks"));
		contract.setAttachment(request.getParameter("attachment"));
		contract.setStatus(RequestUtils.getInt(request, "status"));
		contract.setAppuser(request.getParameter("appuser"));
		contract.setAppdate(RequestUtils.getDate(request, "appdate"));
		contract.setContractno(request.getParameter("contractno"));
		contract.setProcessname(request.getParameter("processname"));
		contract.setProcessinstanceid(request.getParameter("processinstanceid"));
		contract.setWfstatus(RequestUtils.getDouble(request, "wfstatus"));
		contract.setAppusername(request.getParameter("appusername"));
		contract.setCreateBy(request.getParameter("createBy"));
		contract.setCreateDate(RequestUtils.getDate(request, "createDate"));
		contract.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		contract.setUpdateBy(request.getParameter("updateBy"));

		contract.setBrands1(request.getParameter("brands1"));
		contract.setBrands1account(RequestUtils.getDouble(request,
				"brands1account"));
		contract.setBrands2(request.getParameter("brands2"));
		contract.setBrands2account(RequestUtils.getDouble(request,
				"brands2account"));
		contract.setBrands3(request.getParameter("brands3"));
		contract.setBrands3account(RequestUtils.getDouble(request,
				"brands3account"));

		contractService.save(contract);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		Contract contract = contractService.getContract(RequestUtils.getLong(
				request, "id"));
		request.setAttribute("contract", contract);
		JSONObject rowJSON = contract.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("contract.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/oa/contract/view");
	}

}