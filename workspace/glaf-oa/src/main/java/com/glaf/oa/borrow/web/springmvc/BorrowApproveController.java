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

@Controller("/oa/borrowApprove")
@RequestMapping("/oa/borrowApprove")
public class BorrowApproveController {
	protected static final Log logger = LogFactory
			.getLog(BorrowApproveController.class);

	protected BorrowService borrowService;

	protected BorrowadderssService borrowadderssService;

	protected BorrowmoneyService borrowmoneyService;

	protected SysDepartmentService sysDepartmentService;

	@RequestMapping("/approve")
	public ModelAndView approve(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		Long borrowid = RequestUtils.getLong(request, "borrowids");
		Borrow borrow = borrowService.getBorrow(borrowid);
		List<Borrowmoney> moneylist = borrowmoneyService
				.getBorrowmoneyByParentId(borrowid);
		List<Borrowadderss> addresslist = borrowadderssService
				.getBorrowadderssByParentId(borrowid);
		Double totelPrice = 0D;
		for (Borrowmoney borrowmoney : moneylist) {
			totelPrice += borrowmoney.getFeesum();
		}
		request.setAttribute("addresslist", addresslist);
		request.setAttribute("moneylist", moneylist);
		request.setAttribute("totelPrice", totelPrice);
		request.setAttribute("borrow", borrow);
		JSONObject rowJSON = borrow.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());
		return new ModelAndView("/oa/borrow/approve_edit", modelMap);
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
			String borrowids = request.getParameter("borrowids");
			if (StringUtils.isNotEmpty(borrowids)) {//
				StringTokenizer token = new StringTokenizer(borrowids, ",");
				while (token.hasMoreTokens()) {
					String x = token.nextToken();
					if (StringUtils.isNotEmpty(x)) {
						Borrow borrow = borrowService.getBorrow(Long
								.parseLong(x));
						String isAgree = RequestUtils.getString(request,
								"isAgree");
						if (borrow.getProcessinstanceid() != null
								&& borrow.getProcessinstanceid() > 0) {
							if ("isAgree".equals(isAgree)) {
								completeTask(borrow, 0, request);
							} else {
								completeTask(borrow, 1, request);
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
	private boolean completeTask(Borrow borrow, int flag,
			HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
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
		query.setActorId(loginContext.getActorId());

		// 查询status 0为全部 1为未审批 2为已审批 默认显示未审批的单
		query.setStatus(ParamUtils.getIntValue(params, "status"));
		if (StringUtils.isEmpty(request.getParameter("status"))) {
			query.setStatus(1);
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
		int total = borrowService.getBorrowApproveCountByQueryCriteria(query);
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

			List<Borrow> list = borrowService.getBorrowsApproveByQueryCriteria(
					start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Borrow borrow : list) {
					JSONObject rowJSON = borrow.toJsonObject();
					rowJSON.put("id", borrow.getBorrowid());
					rowJSON.put("borrowId", borrow.getBorrowid());
					rowJSON.put("startIndex", ++start);
					rowJSON.put("wdStatusFlag", borrow.getWdStatusFlag());
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

		return new ModelAndView("/oa/borrow/approve_list", modelMap);
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
}