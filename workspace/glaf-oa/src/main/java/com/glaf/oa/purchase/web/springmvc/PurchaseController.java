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
import com.glaf.oa.purchase.model.Purchase;
import com.glaf.oa.purchase.query.PurchaseQuery;
import com.glaf.oa.purchase.service.PurchaseService;
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

@Controller("/oa/purchase")
@RequestMapping("/oa/purchase")
public class PurchaseController {
	protected static final Log logger = LogFactory
			.getLog(PurchaseController.class);

	protected PurchaseService purchaseService;

	protected SysDepartmentService sysDepartmentService;

	protected SysUserService sysUserService;

	protected AttachmentService attachmentService;

	public PurchaseController() {

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

		String processName = "PurchaseProcess";
		User appUser = BaseDataManager.getInstance().getSysUserService()
				.findByAccount(purchase.getAppuser());

		// 根据用户部门id 获取整个部门的对象（GZ01）
		SysDepartment curdept = sysDepartmentService.findById(appUser
				.getDeptId());

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

		// 用户销售部门（如GZ01）部门经理审核
		DataField datafield1 = new DataField();
		datafield1.setName("deptId01");
		datafield1.setValue(appUser.getDeptId());
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

	@ResponseBody
	@RequestMapping("/delete")
	public ModelAndView delete(HttpServletRequest request, ModelMap modelMap) {
		String purchaseids = request.getParameter("purchaseIds");
		if (StringUtils.isNotEmpty(purchaseids)) {// purchaseids不为null时
			StringTokenizer token = new StringTokenizer(purchaseids, ",");
			List<Long> ids = new ArrayList<Long>();
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					ids.add(Long.valueOf(x));
				}
			}
			try {
				purchaseService.delete(ids);
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

		Purchase purchase = purchaseService.getPurchase(RequestUtils.getLong(
				request, "purchaseid"));

		JSONObject rowJSON = purchase.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Purchase purchase = purchaseService.getPurchase(RequestUtils.getLong(
				request, "purchaseId"));
		if (purchase == null) {
			purchase = new Purchase();
			purchase.setStatus(-1);// 无效
			SysDepartment sysDepartment = BaseDataManager.getInstance()
					.getSysDepartmentService().findById(user.getDeptId());
			String areaCode = "";
			if (sysDepartment.getCode() != null
					&& sysDepartment.getCode().length() >= 2) {
				areaCode = sysDepartment.getCode().substring(0, 2);
			}
			purchase.setArea(areaCode);// 地区
			purchase.setDept(sysDepartment.getCode());// 部门
			purchase.setPost(RequestUtil.getLoginUser(request).getHeadship());// 职位
			purchase.setAppuser(actorId);
			purchase.setAppdate(new Date());
			purchase.setCreateBy(actorId);
			purchaseService.save(purchase);
		}
		request.setAttribute("purchase", purchase);
		JSONObject rowJSON = purchase.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		boolean canUpdate = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {

		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (purchase != null) {
				if (purchase.getStatus() == 0 || purchase.getStatus() == -1) {
					canUpdate = true;
				}
			}
		}

		request.setAttribute("canUpdate", canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("purchase.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/oa/purchase/edit", modelMap);
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
		query.setAppdateGreaterThanOrEqual(ParamUtils.getDate(params,
				"appdateGreaterThanOrEqual"));
		query.setAppdateLessThanOrEqual(ParamUtils.getDate(params,
				"appdateLessThanOrEqual"));
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
		int total = purchaseService.getPurchaseCountByQueryCriteria(query);
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

			List<Purchase> list = purchaseService.getPurchasesByQueryCriteria(
					start, limit, query);
			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);
				for (Purchase purchase : list) {
					JSONObject rowJSON = purchase.toJsonObject();
					rowJSON.put("id", purchase.getPurchaseid());
					rowJSON.put("purchaseId", purchase.getPurchaseid());
					rowJSON.put("startIndex", ++start);

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

		return new ModelAndView("/oa/purchase/list", modelMap);
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

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		Purchase purchase = new Purchase();
		Tools.populate(purchase, params);
		purchase.setPurchaseid(RequestUtils.getLong(request, "purchaseid"));
		purchase.setPurchaseno(request.getParameter("purchaseno"));
		purchase.setArea(request.getParameter("area"));
		purchase.setCompany(request.getParameter("company"));
		purchase.setDept(request.getParameter("dept"));
		purchase.setPost(request.getParameter("post"));
		purchase.setAppuser(request.getParameter("appuser"));
		purchase.setAppdate(RequestUtils.getDate(request, "appdate"));
		purchase.setPurchasesum(RequestUtils.getDouble(request, "purchasesum"));
		purchase.setStatus(0);
		purchase.setCreateBy(request.getParameter("createBy") == null ? actorId
				: request.getParameter("createBy"));
		purchase.setCreateDate(RequestUtils.getDate(request, "createDate") == null ? new Date()
				: RequestUtils.getDate(request, "createDate"));
		purchase.setUpdateDate(new Date());
		purchase.setUpdateBy(actorId);
		try {
			purchaseService.save(purchase);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			ModelAndView mav = new ModelAndView();
			mav.addObject("message", "保存错误。");
			return mav;
		}
		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/savePurchase")
	public byte[] savePurchase(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Purchase purchase = new Purchase();
		try {
			Tools.populate(purchase, params);
			purchase.setPurchaseno(request.getParameter("purchaseno"));
			purchase.setArea(request.getParameter("area"));
			purchase.setCompany(request.getParameter("company"));
			purchase.setDept(request.getParameter("dept"));
			purchase.setPost(request.getParameter("post"));
			purchase.setAppuser(request.getParameter("appuser"));
			purchase.setAppdate(RequestUtils.getDate(request, "appdate"));
			purchase.setPurchasesum(RequestUtils.getDouble(request,
					"purchasesum"));
			purchase.setStatus(RequestUtils.getInt(request, "status"));
			purchase.setProcessname(request.getParameter("processname"));
			purchase.setProcessinstanceid(RequestUtils.getLong(request,
					"processinstanceid"));
			purchase.setWfstatus(RequestUtils.getLong(request, "wfstatus"));
			purchase.setCreateBy(request.getParameter("createBy"));
			purchase.setCreateDate(RequestUtils.getDate(request, "createDate"));
			purchase.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
			purchase.setUpdateBy(request.getParameter("updateBy"));
			purchase.setCreateBy(actorId);
			this.purchaseService.save(purchase);

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

	/**
	 * 启动工作流
	 * 
	 * @param contract
	 * @param request
	 * @return
	 */
	private boolean startProcess(Purchase purchase, HttpServletRequest request) {
		String processName = "PurchaseProcess";

		// 获取登录用户部门
		User appUser = BaseDataManager.getInstance().getSysUserService()
				.findByAccount(purchase.getAppuser());

		// 根据用户部门id 获取整个部门的对象（GZ01）
		SysDepartment curdept = sysDepartmentService.findById(appUser
				.getDeptId());

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
		ctx.setActorId(appUser.getActorId());// 用户审批者
		ctx.setProcessName(processName);// 流程名称

		Collection<DataField> dataFields = new ArrayList<DataField>();// 参数

		// 用户销售部门（如GZ01）
		DataField datafield1 = new DataField();
		datafield1.setName("deptId01");
		datafield1.setValue(appUser.getDeptId());
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

		Long processInstanceId = null;
		boolean isOK = false;

		if (purchase.getProcessinstanceid() != null
				&& purchase.getWfstatus() != 9999
				&& purchase.getWfstatus() != null) {
			processInstanceId = purchase.getProcessinstanceid();
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
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Purchase purchase = new Purchase();
		try {
			Tools.populate(purchase, params);
			purchase.setPurchaseid(RequestUtils.getLong(request, "purchaseid"));
			purchase.setPurchaseno(request.getParameter("purchaseno"));// 采购编号
			purchase.setArea(request.getParameter("area"));// 地区
			purchase.setCompany(request.getParameter("company"));// 单位
			purchase.setDept(request.getParameter("dept"));// 部门
			purchase.setPost(request.getParameter("post"));// 职位
			purchase.setAppuser(request.getParameter("appuser"));// 申请人
			purchase.setAppdate(RequestUtils.getDate(request, "appdate"));// 申请日期
			purchase.setPurchasesum(RequestUtils.getDouble(request,
					"purchasesum"));// 总金额
			purchase.setStatus(1);// 状态 已提交
			purchase.setCreateBy(request.getParameter("createby") == null ? null
					: request.getParameter("createby"));
			purchase.setCreateDate(RequestUtils.getDate(request, "createdate") == null ? null
					: RequestUtils.getDate(request, "createdate"));
			purchase.setUpdateBy(request.getParameter("updateby"));
			purchase.setUpdateDate(RequestUtils.getDate(request, "updatedate"));

			purchaseService.save(purchase);

			// 状态为 提交 进入工作流程
			if (purchase.getStatus() == 1) {
				if (purchase.getProcessinstanceid() != null
						&& purchase.getProcessinstanceid() > 0) {
					completeTask(purchase, 0, request);
				} else {
					startProcess(purchase, request);
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
				request, "purchaseId"));
		request.setAttribute("purchase", purchase);
		JSONObject rowJSON = purchase.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		return new ModelAndView("/oa/purchase/view");
	}
}