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
package com.glaf.oa.leave.web.springmvc;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.*;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.others.service.AttachmentService;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.utils.RequestUtil;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.ProcessContext;
import com.glaf.jbpm.datafield.DataField;
import com.glaf.oa.leave.model.*;
import com.glaf.oa.leave.query.*;
import com.glaf.oa.leave.service.*;

@Controller("/oa/leave")
@RequestMapping("/oa/leave")
public class LeaveController {
	protected static final Log logger = LogFactory
			.getLog(LeaveController.class);

	protected LeaveService leaveService;

	private AttachmentService attachmentService;

	public LeaveController() {

	}

	@javax.annotation.Resource
	public void setLeaveService(LeaveService leaveService) {
		this.leaveService = leaveService;
	}

	@javax.annotation.Resource
	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	@ResponseBody
	@RequestMapping("/saveLeave")
	public byte[] saveLeave(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Leave leave = new Leave();
		try {
			Tools.populate(leave, params);
			leave.setLeaveid(RequestUtils.getLong(request, "leaveid"));
			leave.setArea(request.getParameter("area"));
			leave.setCompany(request.getParameter("company"));
			leave.setDept(request.getParameter("dept"));
			leave.setAppuser(request.getParameter("appuser"));
			leave.setPost(request.getParameter("post"));
			leave.setAppdate(RequestUtils.getDate(request, "appdate"));
			leave.setType(RequestUtils.getInt(request, "type"));
			leave.setStartdate(RequestUtils.getDate(request, "startdate"));
			leave.setStarttime(RequestUtils.getInt(request, "starttime"));
			leave.setEnddate(RequestUtils.getDate(request, "enddate"));
			leave.setEndtime(RequestUtils.getInt(request, "endtime"));
			leave.setLeavesum(RequestUtils.getDouble(request, "leavesum"));
			leave.setContent(request.getParameter("content"));
			leave.setRemark(request.getParameter("remark"));
			if (RequestUtils.getLong(request, "leaveid") == 0L) {
				leave.setLeaveid(0L);
				leave.setStatus(0);
				leave.setCreateDate(new Date());
				leave.setCreateBy(actorId);
			} else {
				leave.setLeaveid(RequestUtils.getLong(request, "leaveid"));
				leave.setUpdateDate(new Date());
				leave.setUpdateBy(actorId);
			}
			this.leaveService.save(leave);

			attachmentService.updateByReferTypeAndCreateId(leave.getLeaveid(),
					13, user.getId());

			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("statusCode", Integer.valueOf(200));
			jsonMap.put("message", ViewProperties.getString("res_op_ok"));
			jsonMap.put("id", leave.getLeaveid());
			JSONObject object = new JSONObject(jsonMap);
			return object.toString().getBytes("UTF-8");

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, ModelMap modelMap) {
		Long leaveid = RequestUtils.getLong(request, "leaveid");
		String leaveids = request.getParameter("leaveids");
		if (StringUtils.isNotEmpty(leaveids)) {
			StringTokenizer token = new StringTokenizer(leaveids, ",");
			while (token.hasMoreTokens()) {
				Long x = Long.parseLong(token.nextToken());
				if (x != 0L) {
					Leave leave = leaveService.getLeave(x);
					/**
					 * 此处业务逻辑需自行调整
					 */
					if (leave != null) {
						// leave.setDeleteFlag(1);
						if (leave.getStatus() == 0 || leave.getStatus() == 3) {
							leaveService.deleteById(x);
							if (leave.getProcessinstanceid() != null
									&& leave.getProcessinstanceid() != 0L) {
								ProcessContext ctx = new ProcessContext();
								ctx.setProcessInstanceId(leave
										.getProcessinstanceid());
								ProcessContainer.getContainer().abortProcess(
										ctx);
							}
						}

					}
				}
			}
		} else if (leaveid != null) {
			Leave leave = leaveService.getLeave(leaveid);
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (leave != null) {
				if (leave.getStatus() == 0 || leave.getStatus() == 3) {
					leaveService.deleteById(leaveid);
					if (leave.getProcessinstanceid() != null
							&& leave.getProcessinstanceid() != 0L) {
						ProcessContext ctx = new ProcessContext();
						ctx.setProcessInstanceId(leave.getProcessinstanceid());
						ProcessContainer.getContainer().abortProcess(ctx);
					}
				}
			}
		}
	}

	@ResponseBody
	@RequestMapping("/detail")
	public byte[] detail(HttpServletRequest request) throws IOException {
		// RequestUtils.setRequestParameterToAttribute(request);
		// Map<String, Object> params = RequestUtils.getParameterMap(request);
		Leave leave = leaveService.getLeave(RequestUtils.getLong(request,
				"leaveid"));

		JSONObject rowJSON = leave.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/addOrEdit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		RequestUtils.setRequestParameterToAttribute(request);
		Long leaveid = RequestUtils.getLong(request, "leaveid");
		Leave leave = leaveService.getLeave(leaveid);
		if (leave != null) {
			request.setAttribute("leave", leave);
			request.setAttribute("appdate",
					DateUtils.getDate(leave.getAppdate()));
			request.setAttribute("startdate",
					DateUtils.getDate(leave.getStartdate()));
			request.setAttribute("enddate",
					DateUtils.getDate(leave.getEnddate()));
			JSONObject rowJSON = leave.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		} else {
			leave = new Leave();
			SysDepartment sysDepartment = BaseDataManager.getInstance()
					.getSysDepartmentService().findById(user.getDeptId());
			String areaCode = "";
			if (sysDepartment.getCode() != null
					&& sysDepartment.getCode().length() >= 2) {
				areaCode = sysDepartment.getCode().substring(0, 2);
			}
			leave.setLeaveid(0L);
			leave.setArea(areaCode);
			leave.setDept(sysDepartment.getCode());
			leave.setAppuser(user.getActorId());
			leave.setPost(RequestUtil.getLoginUser(request).getHeadship());
			leave.setAppdate(new Date());
			request.setAttribute("leave", leave);
			request.setAttribute("appdate",
					DateUtils.getDate(leave.getAppdate()));
			JSONObject rowJSON = leave.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}

		return new ModelAndView("/oa/leave/leave_edit", modelMap);
	}

	@RequestMapping("/getLeave")
	@ResponseBody
	public byte[] getLeave(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		RequestUtils.setRequestParameterToAttribute(request);
		Long leaveid = RequestUtils.getLong(request, "leaveid");
		Leave leave = leaveService.getLeave(leaveid);
		JSONObject rowJSON = leave.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Leave leave = leaveService.getLeave(RequestUtils.getLong(request,
				"leaveid"));
		request.setAttribute("leave", leave);
		JSONObject rowJSON = leave.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("leave.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/oa/leave/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("leave.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/oa/leave/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		LeaveQuery query = new LeaveQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setCreateByAndApply(loginContext.getActorId());

		if (request.getParameter("rstatus") != null
				&& !request.getParameter("rstatus").equals("")) {
			query.setStatus(RequestUtils.getInteger(request, "rstatus"));
			query.setAppuser(loginContext.getActorId());
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
		int total = leaveService.getLeaveCountByQueryCriteria(query);
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

			List<Leave> list = leaveService.getLeavesByQueryCriteria(start,
					limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Leave leave : list) {
					JSONObject rowJSON = new JSONObject();

					rowJSON.put("leaveid", leave.getLeaveid());
					rowJSON.put("_leaveid_", leave.getLeaveid());
					rowJSON.put("_oleaveid_", leave.getLeaveid());
					if (leave.getArea() != null
							&& BaseDataManager.getInstance().getValue(
									leave.getArea(), "area") != null) {
						rowJSON.put("area", BaseDataManager.getInstance()
								.getValue(leave.getArea(), "area").getName());
					}
					if (leave.getCompany() != null) {
						rowJSON.put("company", leave.getCompany());
					}
					if (leave.getDept() != null) {
						rowJSON.put("dept", BaseDataManager.getInstance()
								.getStringValue(leave.getDept(), "SYS_DEPTS"));
					}
					if (leave.getAppuser() != null) {
						rowJSON.put(
								"appuser",
								BaseDataManager.getInstance().getStringValue(
										leave.getAppuser(), "SYS_USERS"));
					}
					if (leave.getPost() != null) {
						rowJSON.put("post", leave.getPost());
					}
					if (leave.getAppdate() != null) {
						rowJSON.put("appdate",
								DateUtils.getDate(leave.getAppdate()));
						rowJSON.put("appdate_date",
								DateUtils.getDate(leave.getAppdate()));
						rowJSON.put("appdate_datetime",
								DateUtils.getDateTime(leave.getAppdate()));
					}
					rowJSON.put(
							"type",
							BaseDataManager.getInstance()
									.getValue(leave.getType() + "", "QJLX")
									.getName());
					if (leave.getContent() != null) {
						rowJSON.put("content", leave.getContent());
					}
					if (leave.getStartdate() != null) {
						rowJSON.put("startdate",
								DateUtils.getDate(leave.getStartdate()));
						rowJSON.put("startdate_date",
								DateUtils.getDate(leave.getStartdate()));
						rowJSON.put("startdate_datetime",
								DateUtils.getDateTime(leave.getStartdate()));
					}
					rowJSON.put("starttime", leave.getStarttime());
					if (leave.getEnddate() != null) {
						rowJSON.put("enddate",
								DateUtils.getDate(leave.getEnddate()));
						rowJSON.put("enddate_date",
								DateUtils.getDate(leave.getEnddate()));
						rowJSON.put("enddate_datetime",
								DateUtils.getDateTime(leave.getEnddate()));
					}
					rowJSON.put("endtime", leave.getEndtime());
					rowJSON.put("leavesum", leave.getLeavesum() * 8);
					if (leave.getRemark() != null) {
						rowJSON.put("remark", leave.getRemark());
					}
					rowJSON.put("status", BaseDataManager.getInstance()
							.getValue(leave.getStatus() + "", "followStatus")
							.getName());
					if (leave.getProcessname() != null) {
						rowJSON.put("processname", leave.getProcessname());
					}
					rowJSON.put("processinstanceid",
							leave.getProcessinstanceid());
					rowJSON.put("wfstatus", leave.getWfstatus());

					rowJSON.put("leaveid", leave.getLeaveid());
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

		if (request.getParameter("status") != null
				&& !request.getParameter("status").equals("")) {
			request.setAttribute("status", request.getParameter("status"));
		}

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

		return new ModelAndView("/oa/leave/leave_list", modelMap);
	}

	@ResponseBody
	@RequestMapping("/submit")
	public byte[] submit(HttpServletRequest request, ModelMap modelMap) {
		boolean returnFlag = false;
		Long leaveid = RequestUtils.getLong(request, "leaveid");
		if (leaveid != 0L) {
			Leave leave = leaveService.getLeave(leaveid);
			if (leave.getStatus() == 0 || leave.getStatus() == 3) {
				returnFlag = startProcess(leave, request);
			}
		}
		if (returnFlag) {
			return ResponseUtils.responseJsonResult(true);
		} else {
			return ResponseUtils.responseJsonResult(false);
		}

	}

	private boolean completeTask(Leave leave, int flag,
			HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		String processName = "LeaveProcess";

		ProcessContext ctx = new ProcessContext();
		ctx.setRowId(leave.getLeaveid());
		ctx.setActorId(actorId);
		ctx.setProcessName(processName);
		String opinion = request.getParameter("approveOpinion");
		ctx.setOpinion(opinion);// 审批意见

		/**
		 * 工作流控制参数
		 */
		Collection<DataField> datafields = new ArrayList<DataField>();

		DataField datafield1 = new DataField();
		datafield1.setName("rowId");
		datafield1.setValue(leave.getLeaveid());

		// 集团人力资源部
		SysDepartment sysDepartment1 = BaseDataManager.getInstance()
				.getSysDepartmentService().findByCode("JT03");
		DataField datafield2 = new DataField();
		datafield2.setName("deptId01");
		datafield2.setValue(sysDepartment1.getId());

		// 用户职位属性，是否是部门经理以下
		SysUser syuser = BaseDataManager.getInstance().getSysUserService()
				.findByAccount(leave.getAppuser());
		int heapShipType = Integer.parseInt(BaseDataManager.getInstance()
				.getValue(syuser.getHeadship(), "UserHeadship").getValue());
		DataField datafield3 = new DataField();
		datafield3.setName("heapShipType");
		datafield3.setValue(heapShipType);

		// 部门经理
		DataField datafield4 = new DataField();
		datafield4.setName("deptId02");
		datafield4.setValue(syuser.getDeptId());

		// 集团领导（集团副总）
		SysDepartment sysdepartMem2 = BaseDataManager.getInstance()
				.getSysDepartmentService().findByCode("JT");
		DataField datafield5 = new DataField();
		datafield5.setName("deptId03");
		datafield5.setValue(sysdepartMem2.getId());

		// 行政财务
		SysDepartment sysdepartMemTemp = BaseDataManager.getInstance()
				.getSysDepartmentService().findById(syuser.getDeptId());
		SysDepartment sysdepartMem3 = BaseDataManager.getInstance()
				.getSysDepartmentService()
				.findByCode(sysdepartMemTemp.getCode().substring(0, 2) + "06");
		DataField datafield6 = new DataField();
		datafield6.setName("deptId04");
		datafield6.setValue(sysdepartMem3.getId());

		DataField datafield7 = new DataField();
		datafield7.setName("isAgree");
		if (flag == 0) {
			datafield7.setValue("true");
		} else {
			datafield7.setValue("false");
		}

		datafields.add(datafield1);
		datafields.add(datafield2);
		datafields.add(datafield3);
		datafields.add(datafield4);
		datafields.add(datafield5);
		datafields.add(datafield6);
		datafields.add(datafield7);
		ctx.setDataFields(datafields);

		Long processInstanceId = null;
		boolean isOK = false;

		if (leave.getProcessinstanceid() != null && leave.getWfstatus() != 9999
				&& leave.getWfstatus() != null) {
			processInstanceId = leave.getProcessinstanceid();
			ctx.setProcessInstanceId(processInstanceId);
			isOK = ProcessContainer.getContainer().completeTask(ctx);
			logger.info("workflow 中");
		} else {
			processInstanceId = ProcessContainer.getContainer().startProcess(
					ctx);
			logger.info("processInstanceId=" + processInstanceId);

			isOK = true;
			logger.info("workflow start");

		}
		return isOK;

	}

	private boolean startProcess(Leave leave, HttpServletRequest request) {
		String processName = "LeaveProcess";

		ProcessContext ctx = new ProcessContext();
		ctx.setRowId(leave.getLeaveid());
		ctx.setActorId(leave.getAppuser());
		ctx.setProcessName(processName);
		/**
		 * 工作流控制参数
		 */
		Collection<DataField> datafields = new ArrayList<DataField>();

		DataField datafield1 = new DataField();
		datafield1.setName("rowId");
		datafield1.setValue(leave.getLeaveid());

		// 集团人力资源部
		SysDepartment sysDepartment1 = BaseDataManager.getInstance()
				.getSysDepartmentService().findByCode("JT03");
		DataField datafield2 = new DataField();
		datafield2.setName("deptId01");
		datafield2.setValue(sysDepartment1.getId());

		// 用户职位属性，是否是部门经理以下
		SysUser syuser = BaseDataManager.getInstance().getSysUserService()
				.findByAccount(leave.getAppuser());
		int heapShipType = Integer.parseInt(BaseDataManager.getInstance()
				.getValue(syuser.getHeadship(), "UserHeadship").getValue());
		DataField datafield3 = new DataField();
		datafield3.setName("heapShipType");
		datafield3.setValue(heapShipType);

		// 部门经理
		DataField datafield4 = new DataField();
		datafield4.setName("deptId02");
		datafield4.setValue(syuser.getDeptId());

		// 集团领导（集团副总）
		SysDepartment sysdepartMem2 = BaseDataManager.getInstance()
				.getSysDepartmentService().findByCode("JT");
		DataField datafield5 = new DataField();
		datafield5.setName("deptId03");
		datafield5.setValue(sysdepartMem2.getId());

		// 行政财务
		SysDepartment sysdepartMemTemp = BaseDataManager.getInstance()
				.getSysDepartmentService().findById(syuser.getDeptId());
		SysDepartment sysdepartMem3 = BaseDataManager.getInstance()
				.getSysDepartmentService()
				.findByCode(sysdepartMemTemp.getCode().substring(0, 2) + "06");
		DataField datafield6 = new DataField();
		datafield6.setName("deptId04");
		datafield6.setValue(sysdepartMem3.getId());

		DataField datafield7 = new DataField();
		datafield7.setName("isAgree");
		datafield7.setValue("true");

		datafields.add(datafield1);
		datafields.add(datafield2);
		datafields.add(datafield3);
		datafields.add(datafield4);
		datafields.add(datafield5);
		datafields.add(datafield6);
		datafields.add(datafield7);
		ctx.setDataFields(datafields);

		Long processInstanceId = null;
		boolean isOK = false;

		if (leave.getProcessinstanceid() != null && leave.getWfstatus() != 9999
				&& leave.getWfstatus() != null) {
			processInstanceId = leave.getProcessinstanceid();
			ctx.setProcessInstanceId(processInstanceId);
			isOK = ProcessContainer.getContainer().completeTask(ctx);
			logger.info("workflow 中");
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
	@RequestMapping("/rollbackData")
	public byte[] rollbackData(HttpServletRequest request, ModelMap modelMap) {
		boolean returnFlag = false;
		Long leaveid = RequestUtils.getLong(request, "leaveid");
		if (leaveid != 0L) {
			Leave leave = leaveService.getLeave(leaveid);
			// 添加提交业务逻辑
			if (leave.getStatus() == 1) {
				if (leave.getProcessinstanceid() != null) {
					returnFlag = completeTask(leave, 1, request);
				} else {
					returnFlag = startProcess(leave, request);
				}
			}
		}

		if (returnFlag) {
			return ResponseUtils.responseJsonResult(true);
		} else {
			return ResponseUtils.responseJsonResult(false);
		}

	}

	@RequestMapping("/getReviewLeave")
	@ResponseBody
	public byte[] getReviewLeave(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		LeaveQuery query = new LeaveQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setLoginContext(loginContext);
		List<String> actorIds = new ArrayList<String>();

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
		int total = leaveService.getReviewLeaveCountByQueryCriteria(query);
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

			List<Leave> list = leaveService.getReviewLeavesByQueryCriteria(
					start, limit, query);
			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);
				for (Leave leave : list) {
					JSONObject rowJSON = new JSONObject();

					if ("WD".equals(query.getWorkedProcessFlag())) {
						rowJSON.put("strstauts", "未审核");
					} else if ("PD".equals(query.getWorkedProcessFlag())) {
						rowJSON.put("strstauts", "已审核");
					}

					rowJSON.put("leaveid", leave.getLeaveid());
					rowJSON.put("_leaveid_", leave.getLeaveid());
					rowJSON.put("_oleaveid_", leave.getLeaveid());
					if (leave.getArea() != null) {
						rowJSON.put("area", BaseDataManager.getInstance()
								.getValue(leave.getArea(), "area").getName());
					}
					if (leave.getCompany() != null) {
						rowJSON.put("company", BaseDataManager.getInstance()
								.getValue(leave.getCompany(), leave.getArea())
								.getName());
					}
					if (leave.getDept() != null) {
						rowJSON.put("dept", BaseDataManager.getInstance()
								.getStringValue(leave.getDept(), "SYS_DEPTS"));
					}
					if (leave.getAppuser() != null) {
						rowJSON.put(
								"appuser",
								BaseDataManager.getInstance().getStringValue(
										leave.getAppuser(), "SYS_USERS"));
					}
					if (leave.getPost() != null) {
						rowJSON.put("post", leave.getPost());
					}
					if (leave.getAppdate() != null) {
						rowJSON.put("appdate",
								DateUtils.getDate(leave.getAppdate()));
						rowJSON.put("appdate_date",
								DateUtils.getDate(leave.getAppdate()));
						rowJSON.put("appdate_datetime",
								DateUtils.getDateTime(leave.getAppdate()));
					}
					rowJSON.put(
							"type",
							BaseDataManager.getInstance()
									.getValue(leave.getType() + "", "QJLX")
									.getName());
					if (leave.getContent() != null) {
						rowJSON.put("content", leave.getContent());
					}
					if (leave.getStartdate() != null) {
						rowJSON.put("startdate",
								DateUtils.getDate(leave.getStartdate()));
						rowJSON.put("startdate_date",
								DateUtils.getDate(leave.getStartdate()));
						rowJSON.put("startdate_datetime",
								DateUtils.getDateTime(leave.getStartdate()));
					}
					rowJSON.put("starttime", leave.getStarttime());
					if (leave.getEnddate() != null) {
						rowJSON.put("enddate",
								DateUtils.getDate(leave.getEnddate()));
						rowJSON.put("enddate_date",
								DateUtils.getDate(leave.getEnddate()));
						rowJSON.put("enddate_datetime",
								DateUtils.getDateTime(leave.getEnddate()));
					}
					rowJSON.put("endtime", leave.getEndtime());
					rowJSON.put("leavesum", leave.getLeavesum() * 8);
					if (leave.getRemark() != null) {
						rowJSON.put("remark", leave.getRemark());
					}
					rowJSON.put("status", BaseDataManager.getInstance()
							.getValue(leave.getStatus() + "", "followStatus")
							.getName());
					if (leave.getProcessname() != null) {
						rowJSON.put("processname", leave.getProcessname());
					}
					rowJSON.put("processinstanceid",
							leave.getProcessinstanceid());
					rowJSON.put("wfstatus", leave.getWfstatus());

					rowJSON.put("leaveid", leave.getLeaveid());
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
	@RequestMapping("/submitData")
	public byte[] submitData(HttpServletRequest request, ModelMap modelMap) {
		Long leaveid = RequestUtils.getLong(request, "leaveid");
		String leaveids = request.getParameter("leaveids");
		boolean returnFlag = true;

		int passFlag = 0;
		if (request.getParameter("passFlag") != null
				&& request.getParameter("passFlag").equals("1")) {
			passFlag = 1;
		}
		if (StringUtils.isNotEmpty(leaveids)) {
			StringTokenizer token = new StringTokenizer(leaveids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Leave leave = leaveService.getLeave(Long.valueOf(x));
					// 添加提交业务逻辑
					if (leave.getStatus() == 1) {
						if (leave.getProcessinstanceid() != null
								&& leave.getProcessinstanceid() != 0L) {
							returnFlag = returnFlag
									&& completeTask(leave, passFlag, request);
						} else {
							returnFlag = returnFlag
									&& startProcess(leave, request);
						}
					}
				}
			}
		} else if (leaveid != null) {
			Leave leave = leaveService.getLeave(Long.valueOf(leaveid));
			// 添加提交业务逻辑
			if (leave.getStatus() == 1) {
				if (leave.getProcessinstanceid() != null
						&& leave.getProcessinstanceid() != 0) {
					returnFlag = completeTask(leave, 0, request);
				} else {
					returnFlag = startProcess(leave, request);
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
	@RequestMapping("/leaveReviewList")
	public ModelAndView leaveReviewList(HttpServletRequest request,
			ModelMap modelMap) {
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
		return new ModelAndView("/oa/leave/leave_review_list", modelMap);
	}

	@RequestMapping("/review")
	public ModelAndView review(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		Leave leave = leaveService.getLeave(RequestUtils.getLong(request,
				"leaveid"));
		if (RequestUtils.getBoolean(request, "lookover")) {
			request.setAttribute("lookover", true);
		} else {
			request.setAttribute("lookover", false);
		}

		if (leave != null) {
			request.setAttribute("leave", leave);
			request.setAttribute("appdate",
					DateUtils.getDate(leave.getAppdate()));
			request.setAttribute("startdate",
					DateUtils.getDate(leave.getStartdate()));
			request.setAttribute("enddate",
					DateUtils.getDate(leave.getEnddate()));
			JSONObject rowJSON = leave.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}
		return new ModelAndView("/oa/leave/leave_review", modelMap);
	}

	@RequestMapping("/searchList")
	public ModelAndView searchList(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String x_query = request.getParameter("x_query");
		request.setAttribute("areaRole", request.getAttribute("areaRole"));
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

		return new ModelAndView("/oa/leave/leave_search_list", modelMap);
	}

	@RequestMapping("/searchJson")
	@ResponseBody
	public byte[] searchJson(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		User user = RequestUtils.getUser(request);
		String areaRole = "";
		if (request.getParameter("areaRole") != null) {
			areaRole = request.getParameter("areaRole");
		}
		LeaveQuery query = new LeaveQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);

		if (areaRole.equals("1")) {
			// 查询所有，不设置参数
		} else {
			SysDepartment sysDepartment = BaseDataManager.getInstance()
					.getSysDepartmentService().findById(user.getDeptId());
			String areaCode = "";
			if (sysDepartment.getCode() != null
					&& sysDepartment.getCode().length() >= 2) {
				areaCode = sysDepartment.getCode().substring(0, 2);
			}
			if (ParamUtils.getString(params, "area") == null
					|| ParamUtils.getString(params, "area").equals("")
					|| ParamUtils.getString(params, "area").equals(areaCode)) {
				query.setArea(areaCode);
			} else {
				query.setArea("null");
			}
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
		int total = leaveService.getLeaveCountByQueryCriteria(query);
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

			List<Leave> list = leaveService.getLeavesByQueryCriteria(start,
					limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);
				for (Leave leave : list) {
					JSONObject rowJSON = new JSONObject();

					rowJSON.put("leaveid", leave.getLeaveid());
					rowJSON.put("_leaveid_", leave.getLeaveid());
					rowJSON.put("_oleaveid_", leave.getLeaveid());
					if (leave.getArea() != null) {
						rowJSON.put("area", BaseDataManager.getInstance()
								.getValue(leave.getArea(), "area").getName());
					}
					if (leave.getCompany() != null) {
						rowJSON.put("company", BaseDataManager.getInstance()
								.getValue(leave.getCompany(), leave.getArea())
								.getName());
					}
					if (leave.getDept() != null) {
						rowJSON.put("dept", BaseDataManager.getInstance()
								.getStringValue(leave.getDept(), "SYS_DEPTS"));
					}
					if (leave.getAppuser() != null) {
						rowJSON.put(
								"appuser",
								BaseDataManager.getInstance().getStringValue(
										leave.getAppuser(), "SYS_USERS"));
					}
					if (leave.getPost() != null) {
						rowJSON.put("post", leave.getPost());
					}
					if (leave.getAppdate() != null) {
						rowJSON.put("appdate",
								DateUtils.getDate(leave.getAppdate()));
						rowJSON.put("appdate_date",
								DateUtils.getDate(leave.getAppdate()));
						rowJSON.put("appdate_datetime",
								DateUtils.getDateTime(leave.getAppdate()));
					}
					rowJSON.put(
							"type",
							BaseDataManager.getInstance()
									.getValue(leave.getType() + "", "QJLX")
									.getName());
					if (leave.getContent() != null) {
						rowJSON.put("content", leave.getContent());
					}
					if (leave.getStartdate() != null) {
						rowJSON.put("startdate",
								DateUtils.getDate(leave.getStartdate()));
						rowJSON.put("startdate_date",
								DateUtils.getDate(leave.getStartdate()));
						rowJSON.put("startdate_datetime",
								DateUtils.getDateTime(leave.getStartdate()));
					}
					rowJSON.put("starttime", leave.getStarttime());
					if (leave.getEnddate() != null) {
						rowJSON.put("enddate",
								DateUtils.getDate(leave.getEnddate()));
						rowJSON.put("enddate_date",
								DateUtils.getDate(leave.getEnddate()));
						rowJSON.put("enddate_datetime",
								DateUtils.getDateTime(leave.getEnddate()));
					}
					rowJSON.put("endtime", leave.getEndtime());
					rowJSON.put("leavesum", leave.getLeavesum() * 8);
					if (leave.getRemark() != null) {
						rowJSON.put("remark", leave.getRemark());
					}
					rowJSON.put("status", BaseDataManager.getInstance()
							.getValue(leave.getStatus() + "", "followStatus")
							.getName());
					if (leave.getProcessname() != null) {
						rowJSON.put("processname", leave.getProcessname());
					}
					rowJSON.put("processinstanceid",
							leave.getProcessinstanceid());
					rowJSON.put("wfstatus", leave.getWfstatus());

					rowJSON.put("leaveid", leave.getLeaveid());
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

}