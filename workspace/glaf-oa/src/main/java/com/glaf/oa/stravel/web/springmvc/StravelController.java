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
package com.glaf.oa.stravel.web.springmvc;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import com.glaf.oa.stravel.model.*;
import com.glaf.oa.stravel.query.*;
import com.glaf.oa.stravel.service.*;
import com.glaf.oa.traveladdress.model.Traveladdress;
import com.glaf.oa.traveladdress.query.TraveladdressQuery;
import com.glaf.oa.traveladdress.service.TraveladdressService;
import com.glaf.oa.travelfee.model.Travelfee;
import com.glaf.oa.travelfee.query.TravelfeeQuery;
import com.glaf.oa.travelfee.service.TravelfeeService;
import com.glaf.oa.travelpersonnel.model.Travelpersonnel;
import com.glaf.oa.travelpersonnel.query.TravelpersonnelQuery;
import com.glaf.oa.travelpersonnel.service.TravelpersonnelService;

@Controller("/oa/stravel")
@RequestMapping("/oa/stravel")
public class StravelController {
	protected static final Log logger = LogFactory
			.getLog(StravelController.class);

	protected StravelService stravelService;

	protected AttachmentService attachmentService;

	protected TraveladdressService traveladdressService;

	protected TravelfeeService travelfeeService;

	protected TravelpersonnelService travelpersonnelService;

	public StravelController() {

	}

	private boolean completeTask(Stravel stravel, int flag,
			HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		String processName = "StravelProcess";

		ProcessContext ctx = new ProcessContext();
		ctx.setRowId(stravel.getTravelid());
		ctx.setActorId(actorId);
		ctx.setProcessName(processName);
		String opinion = request.getParameter("approveOpinion");
		ctx.setOpinion(opinion);// 审批意见

		/**
		 * 工作流控制参数
		 */
		SysUser sysUser = BaseDataManager.getInstance().getSysUserService()
				.findByAccount(stravel.getAppuser());
		Collection<DataField> datafields = new ArrayList<DataField>();

		DataField datafield1 = new DataField();
		datafield1.setName("rowId");
		datafield1.setValue(stravel.getTravelid());

		// 直属上级
		String useId = "";
		String userId = "";
		List<SysUser> list = BaseDataManager.getInstance().getSysUserService()
				.getSuperiors(stravel.getAppuser());
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				SysUser use = list.get(i);
				useId = useId + use.getActorId() + ",";
				if (i == list.size() - 1) {
					userId = useId.substring(0, useId.length() - 1);
				}
			}
		}

		DataField datafield2 = new DataField();
		datafield2.setName("userId");
		datafield2.setValue(userId);

		// 部门经理
		DataField datafield3 = new DataField();
		datafield3.setName("deptId02");
		datafield3.setValue(sysUser.getDeptId());

		// 是否是集团员工
		SysDepartment curdept = BaseDataManager.getInstance()
				.getSysDepartmentService().findById(sysUser.getDeptId());
		DataField datafield4 = new DataField();
		datafield4.setName("area");
		datafield4.setValue(curdept.getCode().substring(0, 2));

		// 当地总经理
		SysDepartment curAreadept = BaseDataManager.getInstance()
				.getSysDepartmentService()
				.findByCode(curdept.getCode().substring(0, 2));
		DataField datafield5 = new DataField();
		datafield5.setName("deptId03");
		datafield5.setValue(curAreadept.getId());

		// 集团副总
		SysDepartment sysdepartMem1 = BaseDataManager.getInstance()
				.getSysDepartmentService().findByCode("JT");
		DataField datafield6 = new DataField();
		datafield6.setName("deptId04");
		datafield6.setValue(sysdepartMem1.getId());

		// 集团HR
		SysDepartment sysDepartment2 = BaseDataManager.getInstance()
				.getSysDepartmentService().findByCode("JT03");
		DataField datafield7 = new DataField();
		datafield7.setName("deptId05");
		datafield7.setValue(sysDepartment2.getId());

		// 行政财务
		SysDepartment sysdepartMem3 = BaseDataManager.getInstance()
				.getSysDepartmentService()
				.findByCode(curdept.getCode().substring(0, 2) + "06");
		DataField datafield8 = new DataField();
		datafield8.setName("deptId06");
		datafield8.setValue(sysdepartMem3.getId());

		DataField datafield9 = new DataField();
		datafield9.setName("isAgree");
		if (flag == 0) {
			datafield9.setValue("true");
		} else {
			datafield9.setValue("false");
		}

		datafields.add(datafield1);
		datafields.add(datafield2);
		datafields.add(datafield3);
		datafields.add(datafield4);
		datafields.add(datafield5);
		datafields.add(datafield6);
		datafields.add(datafield7);
		datafields.add(datafield8);
		datafields.add(datafield9);
		ctx.setDataFields(datafields);

		Long processInstanceId = null;
		boolean isOK = false;

		if (stravel.getProcessinstanceid() != null
				&& stravel.getWfstatus() != 9999
				&& stravel.getWfstatus() != null) {
			processInstanceId = stravel.getProcessinstanceid();
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
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, ModelMap modelMap) {
		Long travelid = RequestUtils.getLong(request, "travelid");
		String travelids = request.getParameter("travelids");
		if (StringUtils.isNotEmpty(travelids)) {
			StringTokenizer token = new StringTokenizer(travelids, ",");
			while (token.hasMoreTokens()) {
				Long x = Long.parseLong(token.nextToken());
				if (x != 0L) {
					Stravel stravel = stravelService.getStravel(x);
					/**
					 * 此处业务逻辑需自行调整
					 */
					if (stravel != null) {
						// stravel.setDeleteFlag(1);
						if (stravel.getStatus() == 0
								|| stravel.getStatus() == 3) {
							travelpersonnelService.deleteByTravelid(x);
							traveladdressService.deleteByTravelid(x);
							travelfeeService.deleteByTravelid(x);
							stravelService.deleteById(x);
							if (stravel.getProcessinstanceid() != null
									&& stravel.getProcessinstanceid() != 0L) {
								ProcessContext ctx = new ProcessContext();
								ctx.setProcessInstanceId(stravel
										.getProcessinstanceid());
								ProcessContainer.getContainer().abortProcess(
										ctx);
							}
						}

					}
				}
			}
		} else if (travelid != null) {
			Stravel stravel = stravelService.getStravel(Long.valueOf(travelid));
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (stravel != null) {
				// stravel.setDeleteFlag(1);
				if (stravel.getStatus() == 0 || stravel.getStatus() == 3) {
					travelpersonnelService.deleteByTravelid(travelid);
					traveladdressService.deleteByTravelid(travelid);
					travelfeeService.deleteByTravelid(travelid);
					stravelService.deleteById(travelid);
					if (stravel.getProcessinstanceid() != null
							&& stravel.getProcessinstanceid() != 0L) {
						ProcessContext ctx = new ProcessContext();
						ctx.setProcessInstanceId(stravel.getProcessinstanceid());
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
		Stravel stravel = stravelService.getStravel(RequestUtils.getLong(
				request, "travelid"));

		JSONObject rowJSON = stravel.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/addOrEdit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		RequestUtils.setRequestParameterToAttribute(request);
		Long travelid = RequestUtils.getLong(request, "travelid");
		Stravel stravel = stravelService.getStravel(travelid);
		if (stravel != null) {
			request.setAttribute("stravel", stravel);
			request.setAttribute("appdate",
					DateUtils.getDate(stravel.getAppdate()));
			request.setAttribute("startdate",
					DateUtils.getDate(stravel.getStartdate()));
			request.setAttribute("enddate",
					DateUtils.getDate(stravel.getEnddate()));
			JSONObject rowJSON = stravel.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		} else {
			// 新增创建一张单
			stravel = new Stravel();
			SysDepartment sysDepartment = BaseDataManager.getInstance()
					.getSysDepartmentService().findById(user.getDeptId());
			String areaCode = "";
			if (sysDepartment.getCode() != null
					&& sysDepartment.getCode().length() >= 2) {
				areaCode = sysDepartment.getCode().substring(0, 2);
			}
			stravel.setTravelid(0L);
			stravel.setArea(areaCode);
			stravel.setDept(sysDepartment.getCode());
			stravel.setAppuser(user.getActorId());
			stravel.setPost(RequestUtil.getLoginUser(request).getHeadship());
			stravel.setAppdate(new Date());
			stravel.setTraveltype(0);

			stravel.setStatus(-1);
			stravel.setCreateDate(new Date());
			stravel.setCreateBy(actorId);

			stravelService.save(stravel);

			stravel.setTravelid(stravel.getTravelid());

			request.setAttribute("stravel", stravel);
			request.setAttribute("appdate",
					DateUtils.getDate(stravel.getAppdate()));
			JSONObject rowJSON = stravel.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}

		return new ModelAndView("/oa/stravel/stravel_edit", modelMap);
	}

	@RequestMapping("/getReviewStravel")
	@ResponseBody
	public byte[] getReviewStravel(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		StravelQuery query = new StravelQuery();
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

		int total = stravelService.getReviewStravelCountByQueryCriteria(query);
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

			List<Stravel> list = stravelService
					.getReviewStravelsByQueryCriteria(start, limit, query);
			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);
				for (Stravel stravel : list) {
					JSONObject rowJSON = new JSONObject();

					if ("WD".equals(query.getWorkedProcessFlag())) {
						rowJSON.put("strstauts", "未审核");
					} else if ("PD".equals(query.getWorkedProcessFlag())) {
						rowJSON.put("strstauts", "已审核");
					}

					rowJSON.put("travelid", stravel.getTravelid());
					rowJSON.put("_travelid_", stravel.getTravelid());
					rowJSON.put(
							"area",
							BaseDataManager.getInstance()
									.getValue(stravel.getArea(), "area")
									.getName());
					if (stravel.getCompany() != null) {
						rowJSON.put(
								"company",
								BaseDataManager
										.getInstance()
										.getValue(stravel.getCompany(),
												stravel.getArea()).getName());
					}
					if (stravel.getDept() != null) {
						rowJSON.put("dept", BaseDataManager.getInstance()
								.getStringValue(stravel.getDept(), "SYS_DEPTS"));
					}
					if (stravel.getPost() != null) {
						rowJSON.put("post", stravel.getPost());
					}
					if (stravel.getAppuser() != null) {
						rowJSON.put(
								"appuser",
								BaseDataManager.getInstance().getStringValue(
										stravel.getAppuser(), "SYS_USERS"));
					}
					if (stravel.getAppdate() != null) {
						rowJSON.put("appdate",
								DateUtils.getDate(stravel.getAppdate()));
						rowJSON.put("appdate_date",
								DateUtils.getDate(stravel.getAppdate()));
						rowJSON.put("appdate_datetime",
								DateUtils.getDateTime(stravel.getAppdate()));
					}
					if (stravel.getStartdate() != null) {
						rowJSON.put("startdate",
								DateUtils.getDate(stravel.getStartdate()));
						rowJSON.put("startdate_date",
								DateUtils.getDate(stravel.getStartdate()));
						rowJSON.put("startdate_datetime",
								DateUtils.getDateTime(stravel.getStartdate()));
					}
					if (stravel.getEnddate() != null) {
						rowJSON.put("enddate",
								DateUtils.getDate(stravel.getEnddate()));
						rowJSON.put("enddate_date",
								DateUtils.getDate(stravel.getEnddate()));
						rowJSON.put("enddate_datetime",
								DateUtils.getDateTime(stravel.getEnddate()));
					}
					rowJSON.put("travelsum", stravel.getTravelsum());
					if (stravel.getContent() != null) {
						rowJSON.put("content", stravel.getContent());
					}
					rowJSON.put("traveltype", stravel.getTraveltype());
					if (stravel.getOthers() != null) {
						rowJSON.put("others", stravel.getOthers());
					}
					rowJSON.put("status", BaseDataManager.getInstance()
							.getValue(stravel.getStatus() + "", "followStatus")
							.getName());
					if (stravel.getProcessname() != null) {
						rowJSON.put("processname", stravel.getProcessname());
					}
					rowJSON.put("processinstanceid",
							stravel.getProcessinstanceid());
					rowJSON.put("wfstatus", stravel.getWfstatus());
					if (stravel.getCreateDate() != null) {
						rowJSON.put("createDate",
								DateUtils.getDate(stravel.getCreateDate()));
						rowJSON.put("createDate_date",
								DateUtils.getDate(stravel.getCreateDate()));
						rowJSON.put("createDate_datetime",
								DateUtils.getDateTime(stravel.getCreateDate()));
					}
					if (stravel.getUpdateDate() != null) {
						rowJSON.put("updateDate",
								DateUtils.getDate(stravel.getUpdateDate()));
						rowJSON.put("updateDate_date",
								DateUtils.getDate(stravel.getUpdateDate()));
						rowJSON.put("updateDate_datetime",
								DateUtils.getDateTime(stravel.getUpdateDate()));
					}
					if (stravel.getCreateBy() != null) {
						rowJSON.put("createBy", stravel.getCreateBy());
					}
					if (stravel.getUpdateBy() != null) {
						rowJSON.put("updateBy", stravel.getUpdateBy());
					}

					rowJSON.put("feesumAccount", stravel.getFeesumAccount());

					rowJSON.put("travelid", stravel.getTravelid());
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

	@RequestMapping("/getStravel")
	@ResponseBody
	public byte[] getStravel(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		RequestUtils.setRequestParameterToAttribute(request);
		Long travelid = RequestUtils.getLong(request, "travelid");
		Stravel stravel = stravelService.getStravel(travelid);
		JSONObject rowJSON = stravel.toJsonObject();

		TravelpersonnelQuery personnelQuery = new TravelpersonnelQuery();
		personnelQuery.setTravelid(travelid);
		List<Travelpersonnel> travelpersonnelList = travelpersonnelService
				.list(personnelQuery);
		rowJSON.put("Travelpersonnel", travelpersonnelList.size());

		TraveladdressQuery addressQuery = new TraveladdressQuery();
		addressQuery.setTravelid(travelid);
		List<Traveladdress> traveladdressList = traveladdressService
				.list(addressQuery);
		rowJSON.put("Traveladdress", traveladdressList.size());

		TravelfeeQuery feeQuery = new TravelfeeQuery();
		feeQuery.setTravelid(travelid);
		List<Travelfee> travelfeeList = travelfeeService.list(feeQuery);
		rowJSON.put("Travelfee", travelfeeList.size());

		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		StravelQuery query = new StravelQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setCreateByAndApply(loginContext.getActorId());
		query.setStatusGreaterThanOrEqual(0);

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
		int total = stravelService.getStravelCountByQueryCriteria(query);
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

			List<Stravel> list = stravelService.getStravelsByQueryCriteria(
					start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Stravel stravel : list) {
					JSONObject rowJSON = new JSONObject();

					rowJSON.put("travelid", stravel.getTravelid());
					rowJSON.put("_travelid_", stravel.getTravelid());
					if (stravel.getArea() != null) {
						rowJSON.put(
								"area",
								BaseDataManager.getInstance().getValue(
										stravel.getArea(), "area") != null ? BaseDataManager
										.getInstance()
										.getValue(stravel.getArea(), "area")
										.getName()
										: "");
					}
					if (stravel.getCompany() != null
							&& stravel.getArea() != null) {
						rowJSON.put(
								"company",
								BaseDataManager.getInstance()
										.getValue(stravel.getCompany(),
												stravel.getArea()) != null ? BaseDataManager
										.getInstance()
										.getValue(stravel.getCompany(),
												stravel.getArea()).getName()
										: "");
					}
					if (stravel.getDept() != null) {
						rowJSON.put("dept", BaseDataManager.getInstance()
								.getStringValue(stravel.getDept(), "SYS_DEPTS"));
					}
					if (stravel.getPost() != null) {
						rowJSON.put("post", stravel.getPost());
					}
					if (stravel.getAppuser() != null) {
						rowJSON.put(
								"appuser",
								BaseDataManager.getInstance().getStringValue(
										stravel.getAppuser(), "SYS_USERS"));
					}
					if (stravel.getAppdate() != null) {
						rowJSON.put("appdate",
								DateUtils.getDate(stravel.getAppdate()));
						rowJSON.put("appdate_date",
								DateUtils.getDate(stravel.getAppdate()));
						rowJSON.put("appdate_datetime",
								DateUtils.getDateTime(stravel.getAppdate()));
					}
					if (stravel.getStartdate() != null) {
						rowJSON.put("startdate",
								DateUtils.getDate(stravel.getStartdate()));
						rowJSON.put("startdate_date",
								DateUtils.getDate(stravel.getStartdate()));
						rowJSON.put("startdate_datetime",
								DateUtils.getDateTime(stravel.getStartdate()));
					}
					if (stravel.getEnddate() != null) {
						rowJSON.put("enddate",
								DateUtils.getDate(stravel.getEnddate()));
						rowJSON.put("enddate_date",
								DateUtils.getDate(stravel.getEnddate()));
						rowJSON.put("enddate_datetime",
								DateUtils.getDateTime(stravel.getEnddate()));
					}
					rowJSON.put("travelsum", stravel.getTravelsum());
					if (stravel.getContent() != null) {
						rowJSON.put("content", stravel.getContent());
					}
					rowJSON.put("traveltype", stravel.getTraveltype());
					if (stravel.getOthers() != null) {
						rowJSON.put("others", stravel.getOthers());
					}
					rowJSON.put("status", BaseDataManager.getInstance()
							.getValue(stravel.getStatus() + "", "followStatus")
							.getName());
					if (stravel.getProcessname() != null) {
						rowJSON.put("processname", stravel.getProcessname());
					}
					rowJSON.put("processinstanceid",
							stravel.getProcessinstanceid());
					rowJSON.put("wfstatus", stravel.getWfstatus());
					if (stravel.getCreateDate() != null) {
						rowJSON.put("createDate",
								DateUtils.getDate(stravel.getCreateDate()));
						rowJSON.put("createDate_date",
								DateUtils.getDate(stravel.getCreateDate()));
						rowJSON.put("createDate_datetime",
								DateUtils.getDateTime(stravel.getCreateDate()));
					}
					if (stravel.getUpdateDate() != null) {
						rowJSON.put("updateDate",
								DateUtils.getDate(stravel.getUpdateDate()));
						rowJSON.put("updateDate_date",
								DateUtils.getDate(stravel.getUpdateDate()));
						rowJSON.put("updateDate_datetime",
								DateUtils.getDateTime(stravel.getUpdateDate()));
					}
					if (stravel.getCreateBy() != null) {
						rowJSON.put("createBy", stravel.getCreateBy());
					}
					if (stravel.getUpdateBy() != null) {
						rowJSON.put("updateBy", stravel.getUpdateBy());
					}

					rowJSON.put("id", stravel.getTravelid());
					rowJSON.put("stravelId", stravel.getTravelid());
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

		return new ModelAndView("/oa/stravel/stravel_list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("stravel.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/oa/stravel/query", modelMap);
	}

	@RequestMapping("/review")
	public ModelAndView review(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		Stravel stravel = stravelService.getStravel(RequestUtils.getLong(
				request, "travelid"));
		if (RequestUtils.getBoolean(request, "lookover")) {
			request.setAttribute("lookover", true);
		} else {
			request.setAttribute("lookover", false);
		}

		if (stravel != null) {
			request.setAttribute("stravel", stravel);
			request.setAttribute("appdate",
					DateUtils.getDate(stravel.getAppdate()));
			request.setAttribute("startdate",
					DateUtils.getDate(stravel.getStartdate()));
			request.setAttribute("enddate",
					DateUtils.getDate(stravel.getEnddate()));
			JSONObject rowJSON = stravel.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}
		return new ModelAndView("/oa/stravel/stravel_review", modelMap);
	}

	@ResponseBody
	@RequestMapping("/rollbackData")
	public byte[] rollbackData(HttpServletRequest request, ModelMap modelMap) {
		boolean returnFlag = false;
		Long travelid = RequestUtils.getLong(request, "travelid");
		if (travelid != 0L) {
			Stravel stravel = stravelService.getStravel(travelid);
			// 添加提交业务逻辑
			if (stravel.getStatus() == 1) {
				if (stravel.getProcessinstanceid() != null) {
					returnFlag = completeTask(stravel, 1, request);
				} else {
					returnFlag = startProcess(stravel, request);
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
	@RequestMapping("/saveStravel")
	public byte[] saveStravel(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Stravel stravel = new Stravel();
		try {
			Tools.populate(stravel, params);
			stravel.setTravelid(RequestUtils.getLong(request, "travelid"));
			stravel.setArea(request.getParameter("area"));
			stravel.setCompany(request.getParameter("company"));
			stravel.setDept(request.getParameter("dept"));
			stravel.setPost(request.getParameter("post"));
			stravel.setAppuser(request.getParameter("appuser"));
			stravel.setAppdate(RequestUtils.getDate(request, "appdate"));
			stravel.setStartdate(RequestUtils.getDate(request, "startdate"));
			stravel.setEnddate(RequestUtils.getDate(request, "enddate"));
			stravel.setTravelsum(RequestUtils.getDouble(request, "travelsum"));
			stravel.setTraveltype(RequestUtils.getInt(request, "traveltype"));
			stravel.setContent(request.getParameter("content"));
			stravel.setOthers(request.getParameter("others"));
			if (RequestUtils.getLong(request, "travelid") == 0L) {
				stravel.setTravelid(0L);
				stravel.setStatus(0);
				stravel.setCreateDate(new Date());
				stravel.setCreateBy(actorId);
			} else {
				if (this.stravelService.getStravel(
						RequestUtils.getLong(request, "travelid")).getStatus() == -1) {
					stravel.setStatus(0);
				}
				stravel.setTravelid(RequestUtils.getLong(request, "travelid"));
				stravel.setUpdateDate(new Date());
				stravel.setUpdateBy(actorId);
			}
			this.stravelService.save(stravel);

			attachmentService.updateByReferTypeAndCreateId(
					stravel.getTravelid(), 8, user.getId());

			JSONObject object;
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("statusCode", Integer.valueOf(200));
			jsonMap.put("message", ViewProperties.getString("res_op_ok"));
			jsonMap.put("id", stravel.getTravelid());
			object = new JSONObject(jsonMap);
			return object.toString().getBytes("UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
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
		StravelQuery query = new StravelQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setStatusGreaterThanOrEqual(0);

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
		int total = stravelService.getStravelCountByQueryCriteria(query);
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

			List<Stravel> list = stravelService.getStravelsByQueryCriteria(
					start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Stravel stravel : list) {
					JSONObject rowJSON = new JSONObject();

					rowJSON.put("travelid", stravel.getTravelid());
					rowJSON.put("_travelid_", stravel.getTravelid());
					rowJSON.put(
							"area",
							BaseDataManager.getInstance().getValue(
									stravel.getArea(), "area") != null ? BaseDataManager
									.getInstance()
									.getValue(stravel.getArea(), "area")
									.getName()
									: "");
					if (stravel.getCompany() != null
							&& stravel.getArea() != null) {
						rowJSON.put(
								"company",
								BaseDataManager.getInstance()
										.getValue(stravel.getCompany(),
												stravel.getArea()) != null ? BaseDataManager
										.getInstance()
										.getValue(stravel.getCompany(),
												stravel.getArea()).getName()
										: "");
					}
					if (stravel.getDept() != null) {
						rowJSON.put("dept", BaseDataManager.getInstance()
								.getStringValue(stravel.getDept(), "SYS_DEPTS"));
					}
					if (stravel.getPost() != null) {
						rowJSON.put("post", stravel.getPost());
					}
					if (stravel.getAppuser() != null) {
						rowJSON.put(
								"appuser",
								BaseDataManager.getInstance().getStringValue(
										stravel.getAppuser(), "SYS_USERS"));
					}
					if (stravel.getAppdate() != null) {
						rowJSON.put("appdate",
								DateUtils.getDate(stravel.getAppdate()));
						rowJSON.put("appdate_date",
								DateUtils.getDate(stravel.getAppdate()));
						rowJSON.put("appdate_datetime",
								DateUtils.getDateTime(stravel.getAppdate()));
					}
					if (stravel.getStartdate() != null) {
						rowJSON.put("startdate",
								DateUtils.getDate(stravel.getStartdate()));
						rowJSON.put("startdate_date",
								DateUtils.getDate(stravel.getStartdate()));
						rowJSON.put("startdate_datetime",
								DateUtils.getDateTime(stravel.getStartdate()));
					}
					if (stravel.getEnddate() != null) {
						rowJSON.put("enddate",
								DateUtils.getDate(stravel.getEnddate()));
						rowJSON.put("enddate_date",
								DateUtils.getDate(stravel.getEnddate()));
						rowJSON.put("enddate_datetime",
								DateUtils.getDateTime(stravel.getEnddate()));
					}
					rowJSON.put("travelsum", stravel.getTravelsum());
					if (stravel.getContent() != null) {
						rowJSON.put("content", stravel.getContent());
					}
					rowJSON.put("traveltype", stravel.getTraveltype());
					if (stravel.getOthers() != null) {
						rowJSON.put("others", stravel.getOthers());
					}
					rowJSON.put("status", BaseDataManager.getInstance()
							.getValue(stravel.getStatus() + "", "followStatus")
							.getName());
					if (stravel.getProcessname() != null) {
						rowJSON.put("processname", stravel.getProcessname());
					}
					rowJSON.put("processinstanceid",
							stravel.getProcessinstanceid());
					rowJSON.put("wfstatus", stravel.getWfstatus());
					if (stravel.getCreateDate() != null) {
						rowJSON.put("createDate",
								DateUtils.getDate(stravel.getCreateDate()));
						rowJSON.put("createDate_date",
								DateUtils.getDate(stravel.getCreateDate()));
						rowJSON.put("createDate_datetime",
								DateUtils.getDateTime(stravel.getCreateDate()));
					}
					if (stravel.getUpdateDate() != null) {
						rowJSON.put("updateDate",
								DateUtils.getDate(stravel.getUpdateDate()));
						rowJSON.put("updateDate_date",
								DateUtils.getDate(stravel.getUpdateDate()));
						rowJSON.put("updateDate_datetime",
								DateUtils.getDateTime(stravel.getUpdateDate()));
					}
					if (stravel.getCreateBy() != null) {
						rowJSON.put("createBy", stravel.getCreateBy());
					}
					if (stravel.getUpdateBy() != null) {
						rowJSON.put("updateBy", stravel.getUpdateBy());
					}

					rowJSON.put("feesumAccount", stravel.getFeesumAccount());

					rowJSON.put("travelid", stravel.getTravelid());
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

		return new ModelAndView("/oa/stravel/stravel_search_list", modelMap);
	}

	@javax.annotation.Resource
	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	@javax.annotation.Resource
	public void setStravelService(StravelService stravelService) {
		this.stravelService = stravelService;
	}

	@javax.annotation.Resource
	public void setTraveladdressService(
			TraveladdressService traveladdressService) {
		this.traveladdressService = traveladdressService;
	}

	@javax.annotation.Resource
	public void setTravelfeeService(TravelfeeService travelfeeService) {
		this.travelfeeService = travelfeeService;
	}

	@javax.annotation.Resource
	public void setTravelpersonnelService(
			TravelpersonnelService travelpersonnelService) {
		this.travelpersonnelService = travelpersonnelService;
	}

	private boolean startProcess(Stravel stravel, HttpServletRequest request) {
		String processName = "StravelProcess";

		ProcessContext ctx = new ProcessContext();
		ctx.setRowId(stravel.getTravelid());
		ctx.setActorId(stravel.getAppuser());
		ctx.setProcessName(processName);
		/**
		 * 工作流控制参数
		 */
		SysUser sysUser = BaseDataManager.getInstance().getSysUserService()
				.findByAccount(stravel.getAppuser());
		Collection<DataField> datafields = new ArrayList<DataField>();

		DataField datafield1 = new DataField();
		datafield1.setName("rowId");
		datafield1.setValue(stravel.getTravelid());

		// 直属上级
		String useId = "";
		String userId = "";
		List<SysUser> list = BaseDataManager.getInstance().getSysUserService()
				.getSuperiors(stravel.getAppuser());
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				SysUser use = list.get(i);
				useId = useId + use.getActorId() + ",";
				if (i == list.size() - 1) {
					userId = useId.substring(0, useId.length() - 1);
				}
			}
		}

		DataField datafield2 = new DataField();
		datafield2.setName("userId");
		datafield2.setValue(userId);

		// 部门经理
		DataField datafield3 = new DataField();
		datafield3.setName("deptId02");
		datafield3.setValue(sysUser.getDeptId());

		// 是否是集团员工
		SysDepartment curdept = BaseDataManager.getInstance()
				.getSysDepartmentService().findById(sysUser.getDeptId());
		DataField datafield4 = new DataField();
		datafield4.setName("area");
		datafield4.setValue(curdept.getCode().substring(0, 2));

		// 当地总经理
		SysDepartment curAreadept = BaseDataManager.getInstance()
				.getSysDepartmentService()
				.findByCode(curdept.getCode().substring(0, 2));
		DataField datafield5 = new DataField();
		datafield5.setName("deptId03");
		datafield5.setValue(curAreadept.getId());

		// 集团副总
		SysDepartment sysdepartMem1 = BaseDataManager.getInstance()
				.getSysDepartmentService().findByCode("JT");
		DataField datafield6 = new DataField();
		datafield6.setName("deptId04");
		datafield6.setValue(sysdepartMem1.getId());

		// 集团HR
		SysDepartment sysDepartment2 = BaseDataManager.getInstance()
				.getSysDepartmentService().findByCode("JT03");
		DataField datafield7 = new DataField();
		datafield7.setName("deptId05");
		datafield7.setValue(sysDepartment2.getId());

		// 行政财务
		SysDepartment sysdepartMem3 = BaseDataManager.getInstance()
				.getSysDepartmentService()
				.findByCode(curdept.getCode().substring(0, 2) + "06");
		DataField datafield8 = new DataField();
		datafield8.setName("deptId06");
		datafield8.setValue(sysdepartMem3.getId());

		DataField datafield9 = new DataField();
		datafield9.setName("isAgree");
		datafield9.setValue("true");

		datafields.add(datafield1);
		datafields.add(datafield2);
		datafields.add(datafield3);
		datafields.add(datafield4);
		datafields.add(datafield5);
		datafields.add(datafield6);
		datafields.add(datafield7);
		datafields.add(datafield8);
		datafields.add(datafield9);
		ctx.setDataFields(datafields);

		Long processInstanceId = null;
		boolean isOK = false;

		if (stravel.getProcessinstanceid() != null
				&& stravel.getWfstatus() != 9999
				&& stravel.getWfstatus() != null) {
			processInstanceId = stravel.getProcessinstanceid();
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
	@RequestMapping("/stravelReviewList")
	public ModelAndView stravelReviewList(HttpServletRequest request,
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
		return new ModelAndView("/oa/stravel/stravel_review_list", modelMap);
	}

	@ResponseBody
	@RequestMapping("/submit")
	public byte[] submit(HttpServletRequest request, ModelMap modelMap) {
		boolean returnFlag = false;
		Long travelid = RequestUtils.getLong(request, "travelid");
		if (travelid != 0L) {
			Stravel stravel = stravelService.getStravel(travelid);
			if (stravel.getStatus() == 0 || stravel.getStatus() == 3) {
				returnFlag = startProcess(stravel, request);
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
		Long travelid = RequestUtils.getLong(request, "travelid");
		String travelids = request.getParameter("travelids");
		boolean returnFlag = true;

		int passFlag = 0;
		if (request.getParameter("passFlag") != null
				&& request.getParameter("passFlag").equals("1")) {
			passFlag = 1;
		}
		if (StringUtils.isNotEmpty(travelids)) {
			StringTokenizer token = new StringTokenizer(travelids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Stravel stravel = stravelService
							.getStravel(Long.valueOf(x));
					// 添加提交业务逻辑
					if (stravel.getStatus() == 1) {
						if (stravel.getProcessinstanceid() != null
								&& stravel.getProcessinstanceid() != 0L) {
							returnFlag = returnFlag
									&& completeTask(stravel, passFlag, request);
						} else {
							returnFlag = returnFlag
									&& startProcess(stravel, request);
						}
					}
				}
			}

		} else if (travelid != null) {
			Stravel stravel = stravelService.getStravel(travelid);
			// 添加提交业务逻辑
			if (stravel.getStatus() == 1) {
				if (stravel.getProcessinstanceid() != null
						&& stravel.getProcessinstanceid() != 0) {
					returnFlag = completeTask(stravel, 0, request);
				} else {
					returnFlag = startProcess(stravel, request);
				}
			}
		}
		if (returnFlag) {
			return ResponseUtils.responseJsonResult(true);
		} else {
			return ResponseUtils.responseJsonResult(false);
		}
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Stravel stravel = stravelService.getStravel(RequestUtils.getLong(
				request, "travelid"));
		request.setAttribute("stravel", stravel);
		JSONObject rowJSON = stravel.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("stravel.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/oa/stravel/view");
	}

}