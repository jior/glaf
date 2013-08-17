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
package com.glaf.oa.overtime.web.springmvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
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
import com.glaf.oa.overtime.model.Overtime;
import com.glaf.oa.overtime.query.OvertimeQuery;
import com.glaf.oa.overtime.service.OvertimeService;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.others.service.AttachmentService;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.utils.RequestUtil;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.User;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.ProcessContext;
import com.glaf.jbpm.datafield.DataField;

@Controller("/oa/overtime")
@RequestMapping("/oa/overtime")
public class OvertimeController {
	protected static final Log logger = LogFactory
			.getLog(OvertimeController.class);

	protected OvertimeService overtimeService;

	private SysDepartmentService sysDepartmentService;

	private SysUserService sysUserService;

	private AttachmentService attachmentService;

	public OvertimeController() {

	}

	@javax.annotation.Resource
	public void setOvertimeService(OvertimeService overtimeService) {
		this.overtimeService = overtimeService;
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
	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	@ResponseBody
	@RequestMapping("/saveOvertime")
	public byte[] saveOvertime(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Overtime overtime = new Overtime();
		try {
			Tools.populate(overtime, params);
			overtime.setArea(request.getParameter("area"));
			overtime.setCompany(request.getParameter("company"));
			overtime.setDept(request.getParameter("dept"));
			overtime.setAppuser(request.getParameter("appuser"));
			overtime.setPost(request.getParameter("post"));
			overtime.setAppdate(RequestUtils.getDate(request, "appdate"));
			overtime.setType(RequestUtils.getInt(request, "type"));
			overtime.setStartdate(RequestUtils.getDate(request, "startdate"));
			overtime.setStarttime(RequestUtils.getInt(request, "starttime"));
			overtime.setEnddate(RequestUtils.getDate(request, "enddate"));
			overtime.setEndtime(RequestUtils.getInt(request, "endtime"));
			overtime.setOvertimesum(RequestUtils.getDouble(request,
					"overtimesum"));
			overtime.setContent(request.getParameter("content"));
			overtime.setRemark(request.getParameter("remark"));
			if (RequestUtils.getLong(request, "id") == 0L) {
				overtime.setId(0L);
				overtime.setStatus(0);
				overtime.setCreateDate(new Date());
				overtime.setCreateBy(actorId);
			} else {
				overtime.setId(RequestUtils.getLong(request, "id"));
				overtime.setUpdateDate(new Date());
				overtime.setUpdateBy(actorId);
			}

			this.overtimeService.save(overtime);

			attachmentService.updateByReferTypeAndCreateId(overtime.getId(),
					12, user.getId());

			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("statusCode", Integer.valueOf(200));
			jsonMap.put("message", ViewProperties.getString("res_op_ok"));
			jsonMap.put("id", overtime.getId());
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
		Long id = RequestUtils.getLong(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Overtime overtime = overtimeService.getOvertime(Long
							.parseLong(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					if (overtime != null) {
						// overtime.setDeleteFlag(1);
						if (overtime.getStatus() == 0
								|| overtime.getStatus() == 3) {
							overtimeService.deleteById(Long.parseLong(x));
							if (overtime.getProcessinstanceid() != null
									&& overtime.getProcessinstanceid() != 0L) {
								ProcessContext ctx = new ProcessContext();
								ctx.setProcessInstanceId(overtime
										.getProcessinstanceid());
								ProcessContainer.getContainer().abortProcess(
										ctx);
							}
						}

					}
				}
			}
		} else if (id != null) {
			Overtime overtime = overtimeService.getOvertime(id);
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (overtime != null) {
				// overtime.setDeleteFlag(1);
				if (overtime.getStatus() == 0 || overtime.getStatus() == 3) {
					overtimeService.deleteById(id);
					if (overtime.getProcessinstanceid() != null
							&& overtime.getProcessinstanceid() != 0L) {
						ProcessContext ctx = new ProcessContext();
						ctx.setProcessInstanceId(overtime
								.getProcessinstanceid());
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
		Long id = RequestUtils.getLong(request, "id");
		Overtime overtime = overtimeService.getOvertime(id);

		JSONObject rowJSON = overtime.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/addOrEdit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		RequestUtils.setRequestParameterToAttribute(request);
		Long id = RequestUtils.getLong(request, "id");
		Overtime overtime = overtimeService.getOvertime(id);
		if (overtime != null) {
			request.setAttribute("overtime", overtime);
			request.setAttribute("appdate",
					DateUtils.getDate(overtime.getAppdate()));
			request.setAttribute("startdate",
					DateUtils.getDate(overtime.getStartdate()));
			request.setAttribute("enddate",
					DateUtils.getDate(overtime.getEnddate()));
			JSONObject rowJSON = overtime.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		} else {
			overtime = new Overtime();
			SysDepartment sysDepartment = BaseDataManager.getInstance()
					.getSysDepartmentService().findById(user.getDeptId());
			String areaCode = "";
			if (sysDepartment.getCode() != null
					&& sysDepartment.getCode().length() >= 2) {
				areaCode = sysDepartment.getCode().substring(0, 2);
			}
			overtime.setId(0L);
			overtime.setArea(areaCode);
			overtime.setDept(sysDepartment.getCode());
			overtime.setAppuser(user.getActorId());
			overtime.setPost(RequestUtil.getLoginUser(request).getHeadship());
			request.setAttribute("overtime", overtime);
			overtime.setAppdate(new Date());
			request.setAttribute("appdate",
					DateUtils.getDate(overtime.getAppdate()));
			JSONObject rowJSON = overtime.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}

		return new ModelAndView("/oa/overtime/overtime_edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Long id = RequestUtils.getLong(request, "id");
		Overtime overtime = overtimeService.getOvertime(id);
		request.setAttribute("overtime", overtime);
		JSONObject rowJSON = overtime.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("overtime.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/oa/overtime/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("overtime.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/oa/overtime/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		OvertimeQuery query = new OvertimeQuery();
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
		int total = overtimeService.getOvertimeCountByQueryCriteria(query);
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

			List<Overtime> list = overtimeService.getOvertimesByQueryCriteria(
					start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Overtime overtime : list) {
					JSONObject rowJSON = new JSONObject();

					rowJSON.put("id", overtime.getId());
					rowJSON.put("_id_", overtime.getId());
					rowJSON.put("_oid_", overtime.getId());
					rowJSON.put("overtimeid", overtime.getOvertimeid());
					if (overtime.getArea() != null) {
						rowJSON.put("area", BaseDataManager.getInstance()
								.getValue(overtime.getArea(), "eara").getName());
					}
					if (overtime.getCompany() != null) {
						rowJSON.put("company", overtime.getCompany());
					}
					if (overtime.getDept() != null) {
						rowJSON.put(
								"dept",
								BaseDataManager.getInstance().getStringValue(
										overtime.getDept(), "SYS_DEPTS"));
					}
					if (overtime.getAppuser() != null) {

						rowJSON.put(
								"appuser",
								BaseDataManager.getInstance().getStringValue(
										overtime.getAppuser(), "SYS_USERS"));
					}
					if (overtime.getPost() != null) {
						rowJSON.put("post", overtime.getPost());
					}
					if (overtime.getAppdate() != null) {
						rowJSON.put("appdate",
								DateUtils.getDate(overtime.getAppdate()));
						rowJSON.put("appdate_date",
								DateUtils.getDate(overtime.getAppdate()));
						rowJSON.put("appdate_datetime",
								DateUtils.getDateTime(overtime.getAppdate()));
					}
					rowJSON.put(
							"type",
							BaseDataManager.getInstance()
									.getValue(overtime.getType() + "", "JBLX")
									.getName());
					if (overtime.getContent() != null) {
						rowJSON.put("content", overtime.getContent());
					}
					if (overtime.getStartdate() != null) {
						rowJSON.put("startdate",
								DateUtils.getDate(overtime.getStartdate()));
						rowJSON.put("startdate_date",
								DateUtils.getDate(overtime.getStartdate()));
						rowJSON.put("startdate_datetime",
								DateUtils.getDateTime(overtime.getStartdate()));
					}
					rowJSON.put("starttime", overtime.getStarttime());
					if (overtime.getEnddate() != null) {
						rowJSON.put("enddate",
								DateUtils.getDate(overtime.getEnddate()));
						rowJSON.put("enddate_date",
								DateUtils.getDate(overtime.getEnddate()));
						rowJSON.put("enddate_datetime",
								DateUtils.getDateTime(overtime.getEnddate()));
					}
					rowJSON.put("endtime", overtime.getEndtime());
					rowJSON.put("overtimesum", overtime.getOvertimesum() * 8);
					if (overtime.getRemark() != null) {
						rowJSON.put("remark", overtime.getRemark());
					}
					rowJSON.put(
							"status",
							BaseDataManager
									.getInstance()
									.getValue(overtime.getStatus() + "",
											"followStatus").getName());
					if (overtime.getProcessname() != null) {
						rowJSON.put("processname", overtime.getProcessname());
					}
					rowJSON.put("processinstanceid",
							overtime.getProcessinstanceid());
					rowJSON.put("wfstatus", overtime.getWfstatus());
					if (overtime.getCreateDate() != null) {
						rowJSON.put("createDate",
								DateUtils.getDate(overtime.getCreateDate()));
						rowJSON.put("createDate_date",
								DateUtils.getDate(overtime.getCreateDate()));
						rowJSON.put("createDate_datetime",
								DateUtils.getDateTime(overtime.getCreateDate()));
					}
					if (overtime.getUpdateDate() != null) {
						rowJSON.put("updateDate",
								DateUtils.getDate(overtime.getUpdateDate()));
						rowJSON.put("updateDate_date",
								DateUtils.getDate(overtime.getUpdateDate()));
						rowJSON.put("updateDate_datetime",
								DateUtils.getDateTime(overtime.getUpdateDate()));
					}

					rowJSON.put("id", overtime.getId());
					rowJSON.put("overtimeId", overtime.getId());
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

	@RequestMapping("/getOvertime")
	@ResponseBody
	public byte[] getOvertime(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		RequestUtils.setRequestParameterToAttribute(request);
		Long id = RequestUtils.getLong(request, "id");
		Overtime overtime = overtimeService.getOvertime(id);
		JSONObject rowJSON = overtime.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
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

		return new ModelAndView("/oa/overtime/overtime_list", modelMap);
	}

	@ResponseBody
	@RequestMapping("/submit")
	public byte[] submit(HttpServletRequest request, ModelMap modelMap) {
		boolean returnFlag = false;
		Long id = RequestUtils.getLong(request, "id");
		if (id != 0L) {
			Overtime overtime = overtimeService.getOvertime(id);
			// 添加提交业务逻辑
			if (overtime.getStatus() == 0 || overtime.getStatus() == 3) {
				returnFlag = startProcess(overtime, request);
			}
		}
		if (returnFlag) {
			return ResponseUtils.responseJsonResult(true);
		} else {
			return ResponseUtils.responseJsonResult(false);
		}
	}

	private boolean completeTask(Overtime overtime, int flag,
			HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		String processName = "OvertimeProcess";

		SysUser sysUser = BaseDataManager.getInstance().getSysUserService()
				.findByAccount(overtime.getAppuser());
		ProcessContext ctx = new ProcessContext();
		ctx.setRowId(overtime.getId());
		ctx.setActorId(actorId);
		ctx.setProcessName(processName);
		String opinion = request.getParameter("approveOpinion");
		ctx.setOpinion(opinion);// 审批意见
		/**
		 * 工作流控制参数
		 */

		Collection<DataField> datafields = new ArrayList<DataField>();
		// 获取 部门节点
		long deptId01 = sysUser.getDeptId();
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
		String userId = "";
		List<SysUser> list = sysUserService.getSuperiors(overtime.getAppuser());
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
		datafield1.setName("userId");
		datafield1.setValue(userId);

		DataField datafield2 = new DataField();
		datafield2.setName("deptId02");
		datafield2.setValue(curAreadept.getId());

		DataField datafield3 = new DataField();
		datafield3.setName("deptId03");
		datafield3.setValue(sysJtdept.getId());

		DataField datafield4 = new DataField();
		datafield4.setName("deptId04");
		datafield4.setValue(sysdeptMem.getId());

		DataField datafield5 = new DataField();
		datafield5.setName("rowId");
		datafield5.setValue(overtime.getId());

		DataField datafield6 = new DataField();
		datafield6.setName("isAgree");
		if (flag == 0) {
			datafield6.setValue("true");
		} else {
			datafield6.setValue("false");
		}

		datafields.add(datafield1);
		datafields.add(datafield2);
		datafields.add(datafield3);
		datafields.add(datafield4);
		datafields.add(datafield5);
		datafields.add(datafield6);
		ctx.setDataFields(datafields);

		Long processInstanceId = null;
		boolean isOK = false;

		if (overtime.getProcessinstanceid() != null
				&& overtime.getWfstatus() != 9999
				&& overtime.getWfstatus() != null) {
			processInstanceId = overtime.getProcessinstanceid();
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

	private boolean startProcess(Overtime overtime, HttpServletRequest request) {

		String processName = "OvertimeProcess";

		SysUser sysUser = BaseDataManager.getInstance().getSysUserService()
				.findByAccount(overtime.getAppuser());
		ProcessContext ctx = new ProcessContext();
		ctx.setRowId(overtime.getId());
		ctx.setActorId(overtime.getAppuser());
		ctx.setProcessName(processName);
		/**
		 * 工作流控制参数
		 */

		Collection<DataField> datafields = new ArrayList<DataField>();
		// 获取 部门节点
		long deptId01 = sysUser.getDeptId();
		SysDepartment curdept = sysDepartmentService.findById(deptId01);

		// 获取当地部门节点
		String curAreadeptCode = curdept.getCode().substring(0, 2);
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
		List<SysUser> list = sysUserService.getSuperiors(overtime.getAppuser());
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
		datafield1.setName("userId");
		datafield1.setValue(userId);

		DataField datafield2 = new DataField();
		datafield2.setName("deptId02");
		datafield2.setValue(curAreadept.getId());

		DataField datafield3 = new DataField();
		datafield3.setName("deptId03");
		datafield3.setValue(sysJtdept.getId());

		DataField datafield4 = new DataField();
		datafield4.setName("deptId04");
		datafield4.setValue(sysdeptMem.getId());

		DataField datafield6 = new DataField();
		datafield6.setName("rowId");
		datafield6.setValue(overtime.getId());

		DataField datafield7 = new DataField();
		datafield7.setName("isAgree");
		datafield7.setValue("true");

		datafields.add(datafield1);
		datafields.add(datafield2);
		datafields.add(datafield3);
		datafields.add(datafield4);
		// datafields.add(datafield5);
		datafields.add(datafield6);
		datafields.add(datafield7);
		ctx.setDataFields(datafields);

		Long processInstanceId = null;
		boolean isOK = false;

		if (overtime.getProcessinstanceid() != null
				&& overtime.getWfstatus() != 9999
				&& overtime.getWfstatus() != null) {
			processInstanceId = overtime.getProcessinstanceid();
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
		Long id = RequestUtils.getLong(request, "id");
		if (id != 0L) {
			Overtime overtime = overtimeService.getOvertime(id);
			// 添加提交业务逻辑
			if (overtime.getStatus() == 1) {
				if (overtime.getProcessinstanceid() != null) {
					returnFlag = completeTask(overtime, 1, request);
				} else {
					returnFlag = startProcess(overtime, request);
				}
			}
		}

		if (returnFlag) {
			return ResponseUtils.responseJsonResult(true);
		} else {
			return ResponseUtils.responseJsonResult(false);
		}

	}

	@RequestMapping("/getReviewOvertime")
	@ResponseBody
	public byte[] getReviewOvertime(HttpServletRequest request,
			ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		OvertimeQuery query = new OvertimeQuery();
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
		int total = overtimeService
				.getReviewOvertimeCountByQueryCriteria(query);
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

			List<Overtime> list = overtimeService
					.getReviewOvertimesByQueryCriteria(start, limit, query);
			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);
				for (Overtime overtime : list) {
					JSONObject rowJSON = new JSONObject();

					if ("WD".equals(query.getWorkedProcessFlag())) {
						rowJSON.put("strstauts", "未审核");
					} else if ("PD".equals(query.getWorkedProcessFlag())) {
						rowJSON.put("strstauts", "已审核");
					}

					rowJSON.put("id", overtime.getId());
					rowJSON.put("_id_", overtime.getId());
					rowJSON.put("_oid_", overtime.getId());
					rowJSON.put("overtimeid", overtime.getOvertimeid());
					if (overtime.getArea() != null) {
						rowJSON.put("area", BaseDataManager.getInstance()
								.getValue(overtime.getArea(), "eara").getName());
					}
					if (overtime.getCompany() != null) {
						rowJSON.put(
								"company",
								BaseDataManager
										.getInstance()
										.getValue(overtime.getCompany(),
												overtime.getArea()).getName());
					}
					if (overtime.getDept() != null) {
						rowJSON.put(
								"dept",
								BaseDataManager.getInstance().getStringValue(
										overtime.getDept(), "SYS_DEPTS"));
					}
					if (overtime.getAppuser() != null) {

						rowJSON.put(
								"appuser",
								BaseDataManager.getInstance().getStringValue(
										overtime.getAppuser(), "SYS_USERS"));
					}
					if (overtime.getPost() != null) {
						rowJSON.put("post", overtime.getPost());
					}
					if (overtime.getAppdate() != null) {
						rowJSON.put("appdate",
								DateUtils.getDate(overtime.getAppdate()));
						rowJSON.put("appdate_date",
								DateUtils.getDate(overtime.getAppdate()));
						rowJSON.put("appdate_datetime",
								DateUtils.getDateTime(overtime.getAppdate()));
					}
					rowJSON.put(
							"type",
							BaseDataManager.getInstance()
									.getValue(overtime.getType() + "", "JBLX")
									.getName());
					if (overtime.getContent() != null) {
						rowJSON.put("content", overtime.getContent());
					}
					if (overtime.getStartdate() != null) {
						rowJSON.put("startdate",
								DateUtils.getDate(overtime.getStartdate()));
						rowJSON.put("startdate_date",
								DateUtils.getDate(overtime.getStartdate()));
						rowJSON.put("startdate_datetime",
								DateUtils.getDateTime(overtime.getStartdate()));
					}
					rowJSON.put("starttime", overtime.getStarttime());
					if (overtime.getEnddate() != null) {
						rowJSON.put("enddate",
								DateUtils.getDate(overtime.getEnddate()));
						rowJSON.put("enddate_date",
								DateUtils.getDate(overtime.getEnddate()));
						rowJSON.put("enddate_datetime",
								DateUtils.getDateTime(overtime.getEnddate()));
					}
					rowJSON.put("endtime", overtime.getEndtime());
					rowJSON.put("overtimesum", overtime.getOvertimesum() * 8);
					if (overtime.getRemark() != null) {
						rowJSON.put("remark", overtime.getRemark());
					}
					rowJSON.put(
							"status",
							BaseDataManager
									.getInstance()
									.getValue(overtime.getStatus() + "",
											"followStatus").getName());
					if (overtime.getProcessname() != null) {
						rowJSON.put("processname", overtime.getProcessname());
					}
					rowJSON.put("processinstanceid",
							overtime.getProcessinstanceid());
					rowJSON.put("wfstatus", overtime.getWfstatus());
					if (overtime.getCreateDate() != null) {
						rowJSON.put("createDate",
								DateUtils.getDate(overtime.getCreateDate()));
						rowJSON.put("createDate_date",
								DateUtils.getDate(overtime.getCreateDate()));
						rowJSON.put("createDate_datetime",
								DateUtils.getDateTime(overtime.getCreateDate()));
					}
					if (overtime.getUpdateDate() != null) {
						rowJSON.put("updateDate",
								DateUtils.getDate(overtime.getUpdateDate()));
						rowJSON.put("updateDate_date",
								DateUtils.getDate(overtime.getUpdateDate()));
						rowJSON.put("updateDate_datetime",
								DateUtils.getDateTime(overtime.getUpdateDate()));
					}

					rowJSON.put("id", overtime.getId());
					rowJSON.put("overtimeId", overtime.getId());
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
		Long id = RequestUtils.getLong(request, "id");
		String ids = request.getParameter("ids");
		boolean returnFlag = true;
		int passFlag = 0;
		if (request.getParameter("passFlag") != null
				&& request.getParameter("passFlag").equals("1")) {
			passFlag = 1;
		}
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Overtime overtime = overtimeService.getOvertime(Long
							.valueOf(x));
					// 添加提交业务逻辑
					if (overtime.getStatus() == 1) {
						if (overtime.getProcessinstanceid() != null
								&& overtime.getProcessinstanceid() != 0L) {
							returnFlag = returnFlag
									&& completeTask(overtime, passFlag, request);
						} else {
							returnFlag = returnFlag
									&& startProcess(overtime, request);
						}
					}
				}
			}
		} else if (id != null) {
			Overtime overtime = overtimeService.getOvertime(Long.valueOf(id));
			// 添加提交业务逻辑
			if (overtime.getStatus() == 1) {
				if (overtime.getProcessinstanceid() != null
						&& overtime.getProcessinstanceid() != 0) {
					returnFlag = completeTask(overtime, 0, request);
				} else {
					returnFlag = startProcess(overtime, request);
				}
			}
		}
		if (returnFlag) {
			return ResponseUtils.responseJsonResult(true);
		} else {
			return ResponseUtils.responseJsonResult(false);
		}
	}

	@RequestMapping("/review")
	public ModelAndView review(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		Overtime overtime = overtimeService.getOvertime(RequestUtils.getLong(
				request, "id"));
		if (RequestUtils.getBoolean(request, "lookover")) {
			request.setAttribute("lookover", true);
		} else {
			request.setAttribute("lookover", false);
		}

		if (overtime != null) {
			request.setAttribute("overtime", overtime);
			request.setAttribute("appdate",
					DateUtils.getDate(overtime.getAppdate()));
			request.setAttribute("startdate",
					DateUtils.getDate(overtime.getStartdate()));
			request.setAttribute("enddate",
					DateUtils.getDate(overtime.getEnddate()));
			JSONObject rowJSON = overtime.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}
		return new ModelAndView("/oa/overtime/overtime_review", modelMap);
	}

	@ResponseBody
	@RequestMapping("/overtimeReviewList")
	public ModelAndView overtimeReviewList(HttpServletRequest request,
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
		return new ModelAndView("/oa/overtime/overtime_review_list", modelMap);
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

		return new ModelAndView("/oa/overtime/overtime_search_list", modelMap);
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

		OvertimeQuery query = new OvertimeQuery();
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
		int total = overtimeService.getOvertimeCountByQueryCriteria(query);
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

			List<Overtime> list = overtimeService.getOvertimesByQueryCriteria(
					start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Overtime overtime : list) {
					JSONObject rowJSON = new JSONObject();

					rowJSON.put("id", overtime.getId());
					rowJSON.put("_id_", overtime.getId());
					rowJSON.put("_oid_", overtime.getId());
					rowJSON.put("overtimeid", overtime.getOvertimeid());
					if (overtime.getArea() != null) {
						rowJSON.put("area", BaseDataManager.getInstance()
								.getValue(overtime.getArea(), "eara").getName());
					}
					if (overtime.getCompany() != null) {
						rowJSON.put(
								"company",
								BaseDataManager
										.getInstance()
										.getValue(overtime.getCompany(),
												overtime.getArea()).getName());
					}
					if (overtime.getDept() != null) {
						rowJSON.put(
								"dept",
								BaseDataManager.getInstance().getStringValue(
										overtime.getDept(), "SYS_DEPTS"));
					}
					if (overtime.getAppuser() != null) {

						rowJSON.put(
								"appuser",
								BaseDataManager.getInstance().getStringValue(
										overtime.getAppuser(), "SYS_USERS"));
					}
					if (overtime.getPost() != null) {
						rowJSON.put("post", overtime.getPost());
					}
					if (overtime.getAppdate() != null) {
						rowJSON.put("appdate",
								DateUtils.getDate(overtime.getAppdate()));
						rowJSON.put("appdate_date",
								DateUtils.getDate(overtime.getAppdate()));
						rowJSON.put("appdate_datetime",
								DateUtils.getDateTime(overtime.getAppdate()));
					}
					rowJSON.put(
							"type",
							BaseDataManager.getInstance()
									.getValue(overtime.getType() + "", "JBLX")
									.getName());
					if (overtime.getContent() != null) {
						rowJSON.put("content", overtime.getContent());
					}
					if (overtime.getStartdate() != null) {
						rowJSON.put("startdate",
								DateUtils.getDate(overtime.getStartdate()));
						rowJSON.put("startdate_date",
								DateUtils.getDate(overtime.getStartdate()));
						rowJSON.put("startdate_datetime",
								DateUtils.getDateTime(overtime.getStartdate()));
					}
					rowJSON.put("starttime", overtime.getStarttime());
					if (overtime.getEnddate() != null) {
						rowJSON.put("enddate",
								DateUtils.getDate(overtime.getEnddate()));
						rowJSON.put("enddate_date",
								DateUtils.getDate(overtime.getEnddate()));
						rowJSON.put("enddate_datetime",
								DateUtils.getDateTime(overtime.getEnddate()));
					}
					rowJSON.put("endtime", overtime.getEndtime());
					rowJSON.put("overtimesum", overtime.getOvertimesum() * 8);
					if (overtime.getRemark() != null) {
						rowJSON.put("remark", overtime.getRemark());
					}
					rowJSON.put(
							"status",
							BaseDataManager
									.getInstance()
									.getValue(overtime.getStatus() + "",
											"followStatus").getName());
					if (overtime.getProcessname() != null) {
						rowJSON.put("processname", overtime.getProcessname());
					}
					rowJSON.put("processinstanceid",
							overtime.getProcessinstanceid());
					rowJSON.put("wfstatus", overtime.getWfstatus());
					if (overtime.getCreateDate() != null) {
						rowJSON.put("createDate",
								DateUtils.getDate(overtime.getCreateDate()));
						rowJSON.put("createDate_date",
								DateUtils.getDate(overtime.getCreateDate()));
						rowJSON.put("createDate_datetime",
								DateUtils.getDateTime(overtime.getCreateDate()));
					}
					if (overtime.getUpdateDate() != null) {
						rowJSON.put("updateDate",
								DateUtils.getDate(overtime.getUpdateDate()));
						rowJSON.put("updateDate_date",
								DateUtils.getDate(overtime.getUpdateDate()));
						rowJSON.put("updateDate_datetime",
								DateUtils.getDateTime(overtime.getUpdateDate()));
					}

					rowJSON.put("id", overtime.getId());
					rowJSON.put("overtimeId", overtime.getId());
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