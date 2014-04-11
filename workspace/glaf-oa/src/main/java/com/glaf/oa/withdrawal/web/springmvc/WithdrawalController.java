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
import com.glaf.oa.withdrawal.model.Withdrawal;
import com.glaf.oa.withdrawal.query.WithdrawalQuery;
import com.glaf.oa.withdrawal.service.WithdrawalService;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.others.service.AttachmentService;
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

@Controller("/oa/withdrawal")
@RequestMapping("/oa/withdrawal")
public class WithdrawalController {
	protected static final Log logger = LogFactory
			.getLog(WithdrawalController.class);

	protected WithdrawalService withdrawalService;

	protected SysDepartmentService sysDepartmentService;

	protected SysUserService sysUserService;

	protected AttachmentService attachmentService;

	public WithdrawalController() {

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
		ctx.setActorId(appUser.getActorId());// 用户审批者
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

	@ResponseBody
	@RequestMapping("/delete")
	public ModelAndView delete(HttpServletRequest request, ModelMap modelMap) {
		String withdrawalids = request.getParameter("withdrawalids");
		if (StringUtils.isNotEmpty(withdrawalids)) {
			StringTokenizer token = new StringTokenizer(withdrawalids, ",");
			try {
				while (token.hasMoreTokens()) {
					String x = token.nextToken();
					if (StringUtils.isNotEmpty(x)) {
						Withdrawal withdrawal = withdrawalService
								.getWithdrawal(Long.valueOf(x));
						if (withdrawal.getProcessinstanceid() != null) {
							ProcessContext ctx = new ProcessContext();
							ctx.setProcessInstanceId(withdrawal
									.getProcessinstanceid());
							ProcessContainer.getContainer().abortProcess(ctx);
						}

						withdrawalService.deleteById(Long.valueOf(x));
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex.getMessage());
				ModelAndView mav = new ModelAndView();
				mav.addObject("message", "删除失败。");
				return mav;
			}
		}
		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/detail")
	public byte[] detail(HttpServletRequest request) throws IOException {

		Withdrawal withdrawal = withdrawalService.getWithdrawal(RequestUtils
				.getLong(request, "withdrawalid"));

		JSONObject rowJSON = withdrawal.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Withdrawal withdrawal = withdrawalService.getWithdrawal(RequestUtils
				.getLong(request, "withdrawalid"));
		if (withdrawal == null) {
			withdrawal = new Withdrawal();
			withdrawal.setStatus(-1);// 无效
			SysDepartment sysDepartment = BaseDataManager.getInstance()
					.getSysDepartmentService().findById(user.getDeptId());
			String areaCode = "";
			if (sysDepartment.getCode() != null
					&& sysDepartment.getCode().length() >= 2) {
				areaCode = sysDepartment.getCode().substring(0, 2);
			}
			withdrawal.setAppuser(actorId);
			withdrawal.setArea(areaCode);// 地区
			withdrawal.setDept(sysDepartment.getCode());// 部门
			withdrawal.setPost(RequestUtil.getLoginUser(request).getHeadship());// 职位
			withdrawal.setAppdate(new Date());
			withdrawal.setCreateBy(actorId);
			withdrawal.setCreateDate(new Date());
			withdrawalService.save(withdrawal);
		}
		if (withdrawal != null) {
			if (withdrawal.getBrands1() != null
					&& withdrawal.getBrands2() != null) {
				withdrawal.setBrand("MUL");
			} else if (withdrawal.getBrands1() != null
					&& withdrawal.getBrands2() == null) {
				withdrawal.setBrand("FLL");
			} else if (withdrawal.getBrands1() == null
					&& withdrawal.getBrands2() != null) {
				withdrawal.setBrand("MSLD");
			}
			request.setAttribute("withdrawal", withdrawal);
			JSONObject rowJSON = withdrawal.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}

		boolean canUpdate = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {

		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (withdrawal != null) {
				if (withdrawal.getStatus() == 0 || withdrawal.getStatus() == -1) {
					canUpdate = true;
				}
			}
		}

		request.setAttribute("canUpdate", canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("withdrawal.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/oa/withdrawal/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		WithdrawalQuery query = new WithdrawalQuery();
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
		String rstatus = request.getParameter("rstatus");
		if (!StringUtils.isEmpty(rstatus) && !"null".equals(rstatus)) {
			query.setStatus(Integer.parseInt(rstatus));
		}
		String status = request.getParameter("status");
		if (!StringUtils.isEmpty(status)) {
			query.setStatus(Integer.parseInt(status));
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
		int total = withdrawalService.getWithdrawalCountByQueryCriteria(query);
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
					.getWithdrawalsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Withdrawal withdrawal : list) {
					JSONObject rowJSON = withdrawal.toJsonObject();
					rowJSON.put("id", withdrawal.getWithdrawalid());
					rowJSON.put("withdrawalId", withdrawal.getWithdrawalid());
					rowJSON.put("startIndex", ++start);
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

		return new ModelAndView("/oa/withdrawal/list", modelMap);
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

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Withdrawal withdrawal = new Withdrawal();
		Tools.populate(withdrawal, params);
		withdrawal.setWithdrawalid(RequestUtils
				.getLong(request, "withdrawalid"));
		withdrawal.setArea(request.getParameter("area"));
		withdrawal.setCompany(request.getParameter("company"));
		withdrawal.setDept(request.getParameter("dept"));
		withdrawal.setPost(request.getParameter("post"));
		withdrawal.setAppuser(request.getParameter("appuser"));
		withdrawal.setAppdate(RequestUtils.getDate(request, "appdate"));
		withdrawal.setAppsum(RequestUtils.getDouble(request, "appsum"));
		withdrawal.setContent(request.getParameter("content"));
		withdrawal.setRemark(request.getParameter("remark"));
		withdrawal.setStatus(0);// 0为保存
		if ("MUL".equals(request.getParameter("brands"))) {// 选择为多品牌时
			withdrawal.setBrands1("FLL");
			withdrawal.setBrands1account(RequestUtils.getDouble(request,
					"brands1account"));
			withdrawal.setBrands2("MSLD");
			withdrawal.setBrands2account(RequestUtils.getDouble(request,
					"brands2account"));
		} else if ("FLL".equals(request.getParameter("brands"))) {
			withdrawal.setBrands1("FLL");
			withdrawal.setBrands1account(100D);
			withdrawal.setBrands2(null);
			withdrawal.setBrands2account(null);
		} else if ("MSLD".equals(request.getParameter("brands"))) {
			withdrawal.setBrands1(null);
			withdrawal.setBrands1account(null);
			withdrawal.setBrands2("MSLD");
			withdrawal.setBrands2account(100D);
		}
		withdrawal.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		withdrawal.setUpdateBy(actorId);

		withdrawalService.save(withdrawal);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveWithdrawal")
	public byte[] saveWithdrawal(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Withdrawal withdrawal = new Withdrawal();
		try {
			Tools.populate(withdrawal, params);
			withdrawal.setArea(request.getParameter("area"));
			withdrawal.setCompany(request.getParameter("company"));
			withdrawal.setDept(request.getParameter("dept"));
			withdrawal.setPost(request.getParameter("post"));
			withdrawal.setAppuser(request.getParameter("appuser"));
			withdrawal.setAppdate(RequestUtils.getDate(request, "appdate"));
			withdrawal.setAppsum(RequestUtils.getDouble(request, "appsum"));
			withdrawal.setContent(request.getParameter("content"));
			withdrawal.setRemark(request.getParameter("remark"));
			withdrawal.setStatus(RequestUtils.getInt(request, "status"));
			withdrawal.setProcessname(request.getParameter("processname"));
			withdrawal.setProcessinstanceid(RequestUtils.getLong(request,
					"processinstanceid"));
			withdrawal.setWfstatus(RequestUtils.getLong(request, "wfstatus"));
			withdrawal.setBrands1(request.getParameter("brands1"));
			withdrawal.setBrands1account(RequestUtils.getDouble(request,
					"brands1account"));
			withdrawal.setBrands2(request.getParameter("brands2"));
			withdrawal.setBrands2account(RequestUtils.getDouble(request,
					"brands2account"));
			withdrawal.setBrands3(request.getParameter("brands3"));
			withdrawal.setBrands3account(RequestUtils.getDouble(request,
					"brands3account"));
			withdrawal.setCreateBy(request.getParameter("createBy"));
			withdrawal.setCreateDate(RequestUtils
					.getDate(request, "createDate"));
			withdrawal.setUpdateDate(RequestUtils
					.getDate(request, "updateDate"));
			withdrawal.setUpdateBy(request.getParameter("updateBy"));
			withdrawal.setCreateBy(actorId);
			this.withdrawalService.save(withdrawal);

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
	public void setSysDepartmentService(
			SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
	}

	@javax.annotation.Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	@javax.annotation.Resource
	public void setWithdrawalService(WithdrawalService withdrawalService) {
		this.withdrawalService = withdrawalService;
	}

	/**
	 * 启动工作流
	 * 
	 * @param contract
	 * @param request
	 * @return
	 */
	private boolean startProcess(Withdrawal withdrawal,
			HttpServletRequest request) {
		// User user = RequestUtils.getUser(request);
		// String actorId = user.getActorId();
		String processName = "Withdrawalprocess";

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
		ctx.setActorId(appUser.getActorId());// 用户审批者
		ctx.setProcessName(processName);// 流程名称
		String opinion = request.getParameter("approveOpinion");
		ctx.setOpinion(opinion);// 审批意见

		Collection<DataField> dataFields = new ArrayList<DataField>();// 参数

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

		// DataField dataField = new DataField();
		// dataField.setName("isAgree");// 是否通过审批
		// dataField.setValue("true");
		// dataFields.add(dataField);

		ctx.setDataFields(dataFields);

		Long processInstanceId = null;
		boolean isOK = false;

		if (withdrawal.getProcessinstanceid() != null
				&& withdrawal.getWfstatus() != 9999
				&& withdrawal.getWfstatus() != null) {
			processInstanceId = withdrawal.getProcessinstanceid();
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

	@RequestMapping("/submit")
	public ModelAndView submit(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Withdrawal withdrawal = new Withdrawal();
		try {
			Tools.populate(withdrawal, params);
			withdrawal.setWithdrawalid(RequestUtils.getLong(request,
					"withdrawalid"));
			withdrawal.setArea(request.getParameter("area"));
			withdrawal.setCompany(request.getParameter("company"));
			withdrawal.setDept(request.getParameter("dept"));
			withdrawal.setPost(request.getParameter("post"));
			withdrawal.setAppuser(request.getParameter("appuser"));
			withdrawal.setAppdate(RequestUtils.getDate(request, "appdate"));
			withdrawal.setAppsum(RequestUtils.getDouble(request, "appsum"));
			withdrawal.setContent(request.getParameter("content"));
			withdrawal.setRemark(request.getParameter("remark"));
			withdrawal.setStatus(1);// 0为保存
			if ("MUL".equals(request.getParameter("brands"))) {// 选择为多品牌时
				withdrawal.setBrands1("FLL");
				withdrawal.setBrands1account(RequestUtils.getDouble(request,
						"brands1account"));
				withdrawal.setBrands2("MSLD");
				withdrawal.setBrands2account(RequestUtils.getDouble(request,
						"brands2account"));
			} else if ("FLL".equals(request.getParameter("brands"))) {
				withdrawal.setBrands1("FLL");
				withdrawal.setBrands1account(100D);
				withdrawal.setBrands2(" ");
				withdrawal.setBrands2account(0D);
			} else if ("MSLD".equals(request.getParameter("brands"))) {
				withdrawal.setBrands1(" ");
				withdrawal.setBrands1account(0D);
				withdrawal.setBrands2("MSLD");
				withdrawal.setBrands2account(100D);
			}
			withdrawal.setUpdateDate(new Date());
			withdrawal.setUpdateBy(actorId);

			withdrawalService.save(withdrawal);

			// 状态为 提交 进入工作流程
			if (withdrawal.getStatus() == 1) {
				if (withdrawal.getProcessinstanceid() != null
						&& withdrawal.getProcessinstanceid() > 0) {
					completeTask(withdrawal, 0, request);
				} else {
					startProcess(withdrawal, request);
				}
			}

			return this.list(request, modelMap);

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			ModelAndView mav = new ModelAndView();
			mav.addObject("message", "提交失败。");
			return mav;
		}
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Withdrawal withdrawal = withdrawalService.getWithdrawal(RequestUtils
				.getLong(request, "withdrawalid"));

		withdrawal.setArea(request.getParameter("area"));
		withdrawal.setCompany(request.getParameter("company"));
		withdrawal.setDept(request.getParameter("dept"));
		withdrawal.setPost(request.getParameter("post"));
		withdrawal.setAppuser(request.getParameter("appuser"));
		withdrawal.setAppdate(RequestUtils.getDate(request, "appdate"));
		withdrawal.setAppsum(RequestUtils.getDouble(request, "appsum"));
		withdrawal.setContent(request.getParameter("content"));
		withdrawal.setRemark(request.getParameter("remark"));
		withdrawal.setStatus(RequestUtils.getInt(request, "status"));
		withdrawal.setProcessname(request.getParameter("processname"));
		withdrawal.setProcessinstanceid(RequestUtils.getLong(request,
				"processinstanceid"));
		withdrawal.setWfstatus(RequestUtils.getLong(request, "wfstatus"));
		withdrawal.setBrands1(request.getParameter("brands1"));
		withdrawal.setBrands1account(RequestUtils.getDouble(request,
				"brands1account"));
		withdrawal.setBrands2(request.getParameter("brands2"));
		withdrawal.setBrands2account(RequestUtils.getDouble(request,
				"brands2account"));
		withdrawal.setBrands3(request.getParameter("brands3"));
		withdrawal.setBrands3account(RequestUtils.getDouble(request,
				"brands3account"));
		withdrawal.setCreateBy(request.getParameter("createBy"));
		withdrawal.setCreateDate(RequestUtils.getDate(request, "createDate"));
		withdrawal.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		withdrawal.setUpdateBy(request.getParameter("updateBy"));

		withdrawalService.save(withdrawal);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Withdrawal withdrawal = withdrawalService.getWithdrawal(RequestUtils
				.getLong(request, "withdrawalid"));
		if (withdrawal != null) {
			if (withdrawal.getBrands1() != null
					&& withdrawal.getBrands2() != null) {
				withdrawal.setBrand("MUL");
			} else if (withdrawal.getBrands1() != null
					&& withdrawal.getBrands2() == null) {
				withdrawal.setBrand("FLL");
			} else if (withdrawal.getBrands1() == null
					&& withdrawal.getBrands2() != null) {
				withdrawal.setBrand("MSLD");
			}
			request.setAttribute("withdrawal", withdrawal);
			JSONObject rowJSON = withdrawal.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}

		return new ModelAndView("/oa/withdrawal/view", modelMap);
	}
}