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
package com.glaf.oa.borrow.web.springmvc;

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
import com.glaf.oa.borrow.model.Borrow;
import com.glaf.oa.borrow.model.Borrowadderss;
import com.glaf.oa.borrow.model.Borrowmoney;
import com.glaf.oa.borrow.query.BorrowQuery;
import com.glaf.oa.borrow.service.BorrowService;
import com.glaf.oa.borrow.service.BorrowadderssService;
import com.glaf.oa.borrow.service.BorrowmoneyService;
import com.glaf.base.modules.BaseDataManager;
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

@Controller("/oa/borrow")
@RequestMapping("/oa/borrow")
public class BorrowController {
	protected static final Log logger = LogFactory
			.getLog(BorrowController.class);

	protected BorrowService borrowService;

	protected BorrowadderssService borrowadderssService;

	protected BorrowmoneyService borrowmoneyService;

	protected SysDepartmentService sysDepartmentService;

	/**
	 * 工作流审批
	 * 
	 * @param purchase
	 * @param flag
	 *            0同意 1不同意
	 * @param request
	 * @return
	 */
	private boolean completeTask(Borrow borrow, int flag,
			HttpServletRequest request) {
		String processName = "Borrowprocess";

		User appUser = BaseDataManager.getInstance().getSysUserService()
				.findByAccount(borrow.getAppuser());

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
		ctx.setRowId(borrow.getBorrowid());// 表id
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
		datafield3.setValue(borrow.getBorrowid());
		dataFields.add(datafield3);

		ctx.setDataFields(dataFields);

		Long processInstanceId;
		boolean isOK = false;

		if (borrow.getProcessinstanceid() != null
				&& borrow.getWfstatus() != 9999 && borrow.getWfstatus() != null) {
			processInstanceId = borrow.getProcessinstanceid();
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
		String borrowids = request.getParameter("borrowids");
		if (StringUtils.isNotEmpty(borrowids)) {
			StringTokenizer token = new StringTokenizer(borrowids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Borrow borrow = borrowService.getBorrow(Long.valueOf(x));
					if (borrow.getProcessinstanceid() != null) {
						ProcessContext ctx = new ProcessContext();
						ctx.setProcessInstanceId(borrow.getProcessinstanceid());
						ProcessContainer.getContainer().abortProcess(ctx);
					}
					borrowService.deleteById(Long.valueOf(x));
				}
			}
		}
	}

	@ResponseBody
	@RequestMapping("/detail")
	public byte[] detail(HttpServletRequest request) throws IOException {
		// RequestUtils.setRequestParameterToAttribute(request);
		// Map<String, Object> params = RequestUtils.getParameterMap(request);
		Borrow borrow = borrowService.getBorrow(RequestUtils.getLong(request,
				"borrowid"));

		JSONObject rowJSON = borrow.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		Borrow borrow = borrowService.getBorrow(RequestUtils.getLong(request,
				"borrowid"));
		if (borrow != null) {
			request.setAttribute("borrow", borrow);
			JSONObject rowJSON = borrow.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}

		boolean canUpdate = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {

		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (borrow != null) {
				if (borrow.getStatus() == 0 || borrow.getStatus() == -1) {
					canUpdate = true;
				}
			}
		}

		request.setAttribute("canUpdate", canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("borrow.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/oa/borrow/edit", modelMap);
	}

	@RequestMapping("/init")
	public ModelAndView init(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Long borrowid = RequestUtils.getLong(request, "borrowid");

		// borrow
		Borrow borrow = borrowService.getBorrow(borrowid);
		if (borrow == null) {
			borrow = new Borrow();
			borrow.setStatus(-1);// 无效
			SysDepartment sysDepartment = BaseDataManager.getInstance()
					.getSysDepartmentService().findById(user.getDeptId());
			String areaCode = "";
			if (sysDepartment.getCode() != null
					&& sysDepartment.getCode().length() >= 2) {
				areaCode = sysDepartment.getCode().substring(0, 2);
			}
			borrow.setAppuser(actorId);
			borrow.setArea(areaCode);// 地区
			borrow.setDept(sysDepartment.getCode());// 部门
			borrow.setPost(RequestUtil.getLoginUser(request).getHeadship());// 职位
			borrow.setAppdate(new Date());
			borrow.setCreateBy(actorId);
			borrow.setCreateDate(new Date());
			borrowService.save(borrow);
		}
		Tools.populate(borrow, params);

		// address
		List<Borrowadderss> addresslist = borrowadderssService
				.getBorrowadderssByParentId(borrowid);
		if (addresslist == null || addresslist.size() == 0) {
			addresslist = new ArrayList<Borrowadderss>();
			Borrowadderss borrowadderss = new Borrowadderss();
			addresslist.add(borrowadderss);
		}

		// money
		Double totelPrice = 0D;
		List<Borrowmoney> moneylist = borrowmoneyService
				.getBorrowmoneyByParentId(borrowid);
		if (moneylist == null || moneylist.size() == 0) {
			Borrowmoney borrowmoney1 = new Borrowmoney();
			borrowmoney1.setFeename("1");
			Borrowmoney borrowmoney2 = new Borrowmoney();
			borrowmoney2.setFeename("2");
			Borrowmoney borrowmoney3 = new Borrowmoney();
			borrowmoney3.setFeename("3");
			Borrowmoney borrowmoney4 = new Borrowmoney();
			borrowmoney4.setFeename("0");
			moneylist = new ArrayList<Borrowmoney>();
			moneylist.add(borrowmoney1);
			moneylist.add(borrowmoney2);
			moneylist.add(borrowmoney3);
			moneylist.add(borrowmoney4);
		} else {
			for (Borrowmoney borrowmoney : moneylist) {
				totelPrice += borrowmoney.getFeesum();
			}
		}

		request.setAttribute("borrow", borrow);
		request.setAttribute("addresslist", addresslist);
		request.setAttribute("moneylist", moneylist);
		request.setAttribute("totelPrice", totelPrice);

		return new ModelAndView("/oa/borrow/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		BorrowQuery query = new BorrowQuery();
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

		query.setStatusGreaterThanOrEqual(0);

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
		int total = borrowService.getBorrowCountByQueryCriteria(query);
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

			List<Borrow> list = borrowService.getBorrowsByQueryCriteria(start,
					limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Borrow borrow : list) {
					JSONObject rowJSON = borrow.toJsonObject();
					rowJSON.put("id", borrow.getBorrowid());
					rowJSON.put("borrowId", borrow.getBorrowid());
					rowJSON.put("startIndex", ++start);
					String areaname = BaseDataManager.getInstance()
							.getStringValue(borrow.getArea(), "area");
					rowJSON.put("areaname", areaname);
					String companyname = BaseDataManager.getInstance()
							.getStringValue(borrow.getCompany(),
									borrow.getArea());
					rowJSON.put("companyname", companyname);
					String appusername = BaseDataManager.getInstance()
							.getStringValue(borrow.getAppuser(), "SYS_USERS");
					rowJSON.put("appusername", appusername);
					String deptname = BaseDataManager.getInstance()
							.getStringValue(borrow.getDept(), "SYS_DEPTS");
					rowJSON.put("deptname", deptname);
					String contentname = BaseDataManager.getInstance()
							.getStringValue(borrow.getContent(), "content");
					rowJSON.put("contentname", contentname);
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

		return new ModelAndView("/oa/borrow/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("borrow.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/oa/borrow/query", modelMap);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Long borrowid = RequestUtils.getLong(request, "borrowid");
		// borrow
		Borrow borrow = new Borrow();
		Tools.populate(borrow, params);
		borrow.setArea(request.getParameter("area"));
		borrow.setCompany(request.getParameter("company"));
		borrow.setDept(request.getParameter("dept"));
		borrow.setAppuser(request.getParameter("appuser"));
		borrow.setAppdate(RequestUtils.getDate(request, "appdate"));
		borrow.setPost(request.getParameter("post"));
		borrow.setBorrowNo(request.getParameter("borrowNo"));
		borrow.setContent(request.getParameter("content"));
		if (!"0".equals(request.getParameter("content"))) {
			borrow.setStartdate(RequestUtils.getDate(request, "startdate"));
			borrow.setEnddate(RequestUtils.getDate(request, "enddate"));
			borrow.setDaynum(RequestUtils.getInt(request, "daynum"));
		} else {
			borrow.setStartdate(null);
			borrow.setEnddate(null);
			borrow.setDaynum(null);
		}
		borrow.setDetails(request.getParameter("details"));
		borrow.setAppsum(RequestUtils.getDouble(request, "appsum"));
		borrow.setStatus(0);
		borrow.setUpdateDate(new Date());
		borrow.setUpdateBy(actorId);

		borrowService.save(borrow);
		// address
		List<Borrowadderss> addresss = borrowadderssService
				.getBorrowadderssByParentId(borrowid);
		List<Long> addressIds = new ArrayList<Long>();
		addressIds.add(0L);
		for (Borrowadderss borrowadderss : addresss) {
			addressIds.add(borrowadderss.getAddressid());
		}
		if (!"0".equals(request.getParameter("content"))) {
			for (int i = 0; i < 10; i++) {
				if (request.getParameter("start_" + i) != null
						&& !"".equals(request.getParameter("start_" + i))) {
					Long addressid = RequestUtils.getLong(request, "addressid_"
							+ i);
					if (addressIds.contains(addressid)) {
						addressIds.remove(addressid);
					}
					Borrowadderss borrowadderss = new Borrowadderss();
					borrowadderss.setAddressid(addressid == 0 ? null
							: addressid);
					borrowadderss.setStart(request.getParameter("start_" + i));
					borrowadderss.setReach(request.getParameter("reach_" + i));
					borrowadderss.setBorrowid(borrowid);
					borrowadderssService.save(borrowadderss);
				}
			}
			for (Long addressId : addressIds) {
				borrowadderssService.deleteById(addressId);
			}
		} else {
			// 根据主表id删除所有记录
			borrowadderssService.deleteByParentId(borrowid);
		}

		// money
		List<Borrowmoney> moneys = borrowmoneyService
				.getBorrowmoneyByParentId(borrowid);
		List<Long> moneyIds = new ArrayList<Long>();
		for (Borrowmoney borrowmoney : moneys) {
			moneyIds.add(borrowmoney.getBorrowmoneyid());
		}
		for (int i = 0; i < 10; i++) {
			if (request.getParameter("feesum_" + i) != null
					&& !"".equals(request.getParameter("feesum_" + i))) {
				Long borrowmoneyid = RequestUtils.getLong(request,
						"borrowmoneyid_" + i);
				if (moneyIds.contains(borrowmoneyid)) {
					moneyIds.remove(borrowmoneyid);
				}
				Borrowmoney borrowmoney = new Borrowmoney();
				borrowmoney.setBorrowmoneyid(borrowmoneyid == 0 ? null
						: borrowmoneyid);
				borrowmoney.setFeename(request.getParameter("feename_" + i));
				borrowmoney.setFeesum(RequestUtils.getDouble(request, "feesum_"
						+ i));
				borrowmoney.setBorrowid(borrowid);
				borrowmoneyService.save(borrowmoney);
			}
		}
		for (Long moneyId : moneyIds) {
			borrowmoneyService.deleteById(moneyId);
		}

		return new ModelAndView("/oa/borrow/edit", modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveBorrow")
	public byte[] saveBorrow(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Borrow borrow = new Borrow();
		try {
			Tools.populate(borrow, params);
			borrow.setArea(request.getParameter("area"));
			borrow.setCompany(request.getParameter("company"));
			borrow.setDept(request.getParameter("dept"));
			borrow.setAppuser(request.getParameter("appuser"));
			borrow.setAppdate(RequestUtils.getDate(request, "appdate"));
			borrow.setPost(request.getParameter("post"));
			borrow.setContent(request.getParameter("content"));
			borrow.setStartdate(RequestUtils.getDate(request, "startdate"));
			borrow.setEnddate(RequestUtils.getDate(request, "enddate"));
			borrow.setDaynum(RequestUtils.getInt(request, "daynum"));
			borrow.setDetails(request.getParameter("details"));
			borrow.setAppsum(RequestUtils.getDouble(request, "appsum"));
			borrow.setStatus(RequestUtils.getInt(request, "status"));
			borrow.setProcessname(request.getParameter("processname"));
			borrow.setProcessinstanceid(RequestUtils.getLong(request,
					"processinstanceid"));
			borrow.setWfstatus(RequestUtils.getLong(request, "wfstatus"));
			borrow.setCreateBy(request.getParameter("createBy"));
			borrow.setCreateDate(RequestUtils.getDate(request, "createDate"));
			borrow.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
			borrow.setUpdateBy(request.getParameter("updateBy"));
			borrow.setCreateBy(actorId);
			this.borrowService.save(borrow);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setBorrowadderssService(
			BorrowadderssService borrowadderssService) {
		this.borrowadderssService = borrowadderssService;
	}

	@javax.annotation.Resource
	public void setBorrowmoneyService(BorrowmoneyService borrowmoneyService) {
		this.borrowmoneyService = borrowmoneyService;
	}

	@javax.annotation.Resource
	public void setBorrowService(BorrowService borrowService) {
		this.borrowService = borrowService;
	}

	@javax.annotation.Resource
	public void setSysDepartmentService(
			SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
	}

	/**
	 * 启动工作流
	 * 
	 * @param contract
	 * @param request
	 * @return
	 */
	private boolean startProcess(Borrow borrow, HttpServletRequest request) {
		String processName = "Borrowprocess";

		// 获取登录用户部门
		User appUser = BaseDataManager.getInstance().getSysUserService()
				.findByAccount(borrow.getAppuser());

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
		ctx.setRowId(borrow.getBorrowid());// 表id
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
		datafield3.setValue(borrow.getBorrowid());
		dataFields.add(datafield3);

		// DataField dataField = new DataField();
		// dataField.setName("isAgree");// 是否通过审批
		// dataField.setValue("true");
		// dataFields.add(dataField);

		ctx.setDataFields(dataFields);

		Long processInstanceId = null;
		boolean isOK = false;

		if (borrow.getProcessinstanceid() != null
				&& borrow.getWfstatus() != 9999 && borrow.getWfstatus() != null) {
			processInstanceId = borrow.getProcessinstanceid();
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
		Long borrowid = RequestUtils.getLong(request, "borrowid");
		// borrow
		Borrow borrow = new Borrow();
		try {
			Tools.populate(borrow, params);
			borrow.setArea(request.getParameter("area"));
			borrow.setCompany(request.getParameter("company"));
			borrow.setDept(request.getParameter("dept"));
			borrow.setAppuser(request.getParameter("appuser"));
			borrow.setAppdate(RequestUtils.getDate(request, "appdate"));
			borrow.setPost(request.getParameter("post"));
			borrow.setBorrowNo(request.getParameter("borrowNo"));
			borrow.setContent(request.getParameter("content"));
			if (!"0".equals(request.getParameter("content"))) {
				borrow.setStartdate(RequestUtils.getDate(request, "startdate"));
				borrow.setEnddate(RequestUtils.getDate(request, "enddate"));
				borrow.setDaynum(RequestUtils.getInt(request, "daynum"));
			} else {
				borrow.setStartdate(null);
				borrow.setEnddate(null);
				borrow.setDaynum(null);
			}
			borrow.setDetails(request.getParameter("details"));
			borrow.setAppsum(RequestUtils.getDouble(request, "appsum"));
			borrow.setStatus(1);
			borrow.setUpdateDate(new Date());
			borrow.setUpdateBy(actorId);

			borrowService.save(borrow);
			// address
			List<Borrowadderss> addresss = borrowadderssService
					.getBorrowadderssByParentId(borrowid);
			List<Long> addressIds = new ArrayList<Long>();
			addressIds.add(0L);
			for (Borrowadderss borrowadderss : addresss) {
				addressIds.add(borrowadderss.getAddressid());
			}
			if (!"0".equals(request.getParameter("content"))) {
				for (int i = 0; i < 10; i++) {
					if (request.getParameter("start_" + i) != null
							&& !"".equals(request.getParameter("start_" + i))) {
						Long addressid = RequestUtils.getLong(request,
								"addressid_" + i);
						if (addressIds.contains(addressid)) {
							addressIds.remove(addressid);
						}
						Borrowadderss borrowadderss = new Borrowadderss();
						borrowadderss.setAddressid(addressid == 0 ? null
								: addressid);
						borrowadderss.setStart(request.getParameter("start_"
								+ i));
						borrowadderss.setReach(request.getParameter("reach_"
								+ i));
						borrowadderss.setBorrowid(borrowid);
						borrowadderssService.save(borrowadderss);
					}
				}
				for (Long addressId : addressIds) {
					borrowadderssService.deleteById(addressId);
				}
			} else {
				// 根据主表id删除所有记录
				borrowadderssService.deleteByParentId(borrowid);
			}

			// money
			List<Borrowmoney> moneys = borrowmoneyService
					.getBorrowmoneyByParentId(borrowid);
			List<Long> moneyIds = new ArrayList<Long>();
			for (Borrowmoney borrowmoney : moneys) {
				moneyIds.add(borrowmoney.getBorrowmoneyid());
			}
			for (int i = 0; i < 10; i++) {
				if (request.getParameter("feesum_" + i) != null
						&& !"".equals(request.getParameter("feesum_" + i))) {
					Long borrowmoneyid = RequestUtils.getLong(request,
							"borrowmoneyid_" + i);
					if (moneyIds.contains(borrowmoneyid)) {
						moneyIds.remove(borrowmoneyid);
					}
					Borrowmoney borrowmoney = new Borrowmoney();
					borrowmoney.setBorrowmoneyid(borrowmoneyid == 0 ? null
							: borrowmoneyid);
					borrowmoney
							.setFeename(request.getParameter("feename_" + i));
					borrowmoney.setFeesum(RequestUtils.getDouble(request,
							"feesum_" + i));
					borrowmoney.setBorrowid(borrowid);
					borrowmoneyService.save(borrowmoney);
				}
			}
			for (Long moneyId : moneyIds) {
				borrowmoneyService.deleteById(moneyId);
			}

			// 状态为 提交 进入工作流程
			if (borrow.getStatus() == 1) {
				if (borrow.getProcessinstanceid() != null
						&& borrow.getProcessinstanceid() > 0) {
					completeTask(borrow, 0, request);
				} else {
					startProcess(borrow, request);
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

	@RequestMapping("/submit2")
	public ModelAndView submit2(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Long borrowid = RequestUtils.getLong(request, "borrowid");
		// borrow
		Borrow borrow = new Borrow();
		try {
			Tools.populate(borrow, params);
			borrow.setBorrowid(borrowid);
			borrow.setStatus(1);
			borrow.setUpdateDate(new Date());
			borrow.setUpdateBy(actorId);

			borrowService.save(borrow);

			// 状态为 提交 进入工作流程
			if (borrow.getStatus() == 1) {
				if (borrow.getProcessinstanceid() != null
						&& borrow.getProcessinstanceid() > 0) {
					completeTask(borrow, 0, request);
				} else {
					startProcess(borrow, request);
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

		Borrow borrow = borrowService.getBorrow(RequestUtils.getLong(request,
				"borrowid"));

		borrow.setArea(request.getParameter("area"));
		borrow.setCompany(request.getParameter("company"));
		borrow.setDept(request.getParameter("dept"));
		borrow.setAppuser(request.getParameter("appuser"));
		borrow.setAppdate(RequestUtils.getDate(request, "appdate"));
		borrow.setPost(request.getParameter("post"));
		borrow.setContent(request.getParameter("content"));
		borrow.setStartdate(RequestUtils.getDate(request, "startdate"));
		borrow.setEnddate(RequestUtils.getDate(request, "enddate"));
		borrow.setDaynum(RequestUtils.getInt(request, "daynum"));
		borrow.setDetails(request.getParameter("details"));
		borrow.setAppsum(RequestUtils.getDouble(request, "appsum"));
		borrow.setStatus(RequestUtils.getInt(request, "status"));
		borrow.setProcessname(request.getParameter("processname"));
		borrow.setProcessinstanceid(RequestUtils.getLong(request,
				"processinstanceid"));
		borrow.setWfstatus(RequestUtils.getLong(request, "wfstatus"));
		borrow.setCreateBy(request.getParameter("createBy"));
		borrow.setCreateDate(RequestUtils.getDate(request, "createDate"));
		borrow.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		borrow.setUpdateBy(request.getParameter("updateBy"));

		borrowService.save(borrow);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Long borrowid = RequestUtils.getLong(request, "borrowid");

		// borrow
		Borrow borrow = borrowService.getBorrow(borrowid);
		if (borrow == null) {
			borrow = new Borrow();
			borrow.setStatus(-1);// 无效
			borrow.setAppdate(new Date());
			borrow.setCreateBy(actorId);
			borrow.setCreateDate(new Date());
			borrowService.save(borrow);
		}
		Tools.populate(borrow, params);

		// address
		List<Borrowadderss> addresslist = borrowadderssService
				.getBorrowadderssByParentId(borrowid);
		if (addresslist == null || addresslist.size() == 0) {
			addresslist = new ArrayList<Borrowadderss>();
			Borrowadderss borrowadderss = new Borrowadderss();
			addresslist.add(borrowadderss);
		}

		// money
		Double totelPrice = 0D;
		List<Borrowmoney> moneylist = borrowmoneyService
				.getBorrowmoneyByParentId(borrowid);
		if (moneylist == null || moneylist.size() == 0) {
			Borrowmoney borrowmoney1 = new Borrowmoney();
			borrowmoney1.setFeename("1");
			Borrowmoney borrowmoney2 = new Borrowmoney();
			borrowmoney2.setFeename("2");
			Borrowmoney borrowmoney3 = new Borrowmoney();
			borrowmoney3.setFeename("3");
			Borrowmoney borrowmoney4 = new Borrowmoney();
			borrowmoney4.setFeename("0");
			moneylist = new ArrayList<Borrowmoney>();
			moneylist.add(borrowmoney1);
			moneylist.add(borrowmoney2);
			moneylist.add(borrowmoney3);
			moneylist.add(borrowmoney4);
		} else {
			for (Borrowmoney borrowmoney : moneylist) {
				totelPrice += borrowmoney.getFeesum();
			}
		}

		request.setAttribute("borrow", borrow);
		request.setAttribute("addresslist", addresslist);
		request.setAttribute("moneylist", moneylist);
		request.setAttribute("totelPrice", totelPrice);

		return new ModelAndView("/oa/borrow/view", modelMap);
	}
}