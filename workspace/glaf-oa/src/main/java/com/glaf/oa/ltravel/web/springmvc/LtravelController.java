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
package com.glaf.oa.ltravel.web.springmvc;

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
import com.glaf.base.utils.RequestUtil;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.ProcessContext;
import com.glaf.jbpm.datafield.DataField;
import com.glaf.oa.ltravel.model.*;
import com.glaf.oa.ltravel.query.*;
import com.glaf.oa.ltravel.service.*;

@Controller("/oa/ltravel")
@RequestMapping("/oa/ltravel")
public class LtravelController {
	protected static final Log logger = LogFactory
			.getLog(LtravelController.class);

	protected LtravelService ltravelService;

	private AttachmentService attachmentService;

	public LtravelController() {

	}

	@javax.annotation.Resource
	public void setLtravelService(LtravelService ltravelService) {
		this.ltravelService = ltravelService;
	}

	@javax.annotation.Resource
	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	@ResponseBody
	@RequestMapping("/saveLtravel")
	public byte[] saveLtravel(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Ltravel ltravel = new Ltravel();
		try {
			Tools.populate(ltravel, params);
			ltravel.setTravelid(RequestUtils.getLong(request, "travelid"));
			ltravel.setArea(request.getParameter("area"));
			ltravel.setCompany(request.getParameter("company"));
			ltravel.setDept(request.getParameter("dept"));
			ltravel.setPost(request.getParameter("post"));
			ltravel.setAppuser(request.getParameter("appuser"));
			ltravel.setAppdate(RequestUtils.getDate(request, "appdate"));
			ltravel.setTraveladdress(request.getParameter("traveladdress"));
			ltravel.setStartdate(RequestUtils.getDate(request, "startdate"));
			ltravel.setEnddate(RequestUtils.getDate(request, "enddate"));
			ltravel.setTravelnum(RequestUtils.getDouble(request, "travelnum"));
			ltravel.setContent(request.getParameter("content"));
			if (RequestUtils.getLong(request, "travelid") == 0L) {
				ltravel.setTravelid(0L);
				ltravel.setStatus(0);
				ltravel.setCreateDate(new Date());
				ltravel.setCreateBy(actorId);
			} else {
				ltravel.setTravelid(RequestUtils.getLong(request, "travelid"));
				ltravel.setUpdateDate(new Date());
				ltravel.setUpdateBy(actorId);
			}
			this.ltravelService.save(ltravel);

			attachmentService.updateByReferTypeAndCreateId(
					ltravel.getTravelid(), 8, user.getId());

			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("statusCode", Integer.valueOf(200));
			jsonMap.put("message", ViewProperties.getString("res_op_ok"));
			jsonMap.put("id", ltravel.getTravelid());
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
		Long travelid = RequestUtils.getLong(request, "travelid");
		String travelids = request.getParameter("travelids");
		if (StringUtils.isNotEmpty(travelids)) {
			StringTokenizer token = new StringTokenizer(travelids, ",");
			while (token.hasMoreTokens()) {
				Long x = Long.parseLong(token.nextToken());
				if (x != 0L) {
					Ltravel ltravel = ltravelService.getLtravel(x);
					/**
					 * 此处业务逻辑需自行调整
					 */
					if (ltravel != null) {
						// ltravel.setDeleteFlag(1);
						if (ltravel.getStatus() == 0
								|| ltravel.getStatus() == 3) {
							ltravelService.deleteById(x);
							if (ltravel.getProcessinstanceid() != null
									&& ltravel.getProcessinstanceid() != 0L) {
								ProcessContext ctx = new ProcessContext();
								ctx.setProcessInstanceId(ltravel
										.getProcessinstanceid());
								ProcessContainer.getContainer().abortProcess(
										ctx);
							}
						}

					}
				}
			}
		} else if (travelid != 0L) {
			Ltravel ltravel = ltravelService.getLtravel(travelid);
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (ltravel != null) {
				// ltravel.setDeleteFlag(1);
				if (ltravel.getStatus() == 0 || ltravel.getStatus() == 3) {
					ltravelService.deleteById(travelid);
					if (ltravel.getProcessinstanceid() != null
							&& ltravel.getProcessinstanceid() != 0L) {
						ProcessContext ctx = new ProcessContext();
						ctx.setProcessInstanceId(ltravel.getProcessinstanceid());
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
		Ltravel ltravel = ltravelService.getLtravel(RequestUtils.getLong(
				request, "travelid"));

		JSONObject rowJSON = ltravel.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/addOrEdit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		RequestUtils.setRequestParameterToAttribute(request);
		Long travelid = RequestUtils.getLong(request, "travelid");
		Ltravel ltravel = ltravelService.getLtravel(travelid);
		if (ltravel != null) {
			request.setAttribute("ltravel", ltravel);
			request.setAttribute("appdate",
					DateUtils.getDate(ltravel.getAppdate()));
			request.setAttribute("startdate",
					DateUtils.getDate(ltravel.getStartdate()));
			request.setAttribute("enddate",
					DateUtils.getDate(ltravel.getEnddate()));
			JSONObject rowJSON = ltravel.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		} else {
			ltravel = new Ltravel();
			SysDepartment sysDepartment = BaseDataManager.getInstance()
					.getSysDepartmentService().findById(user.getDeptId());
			String areaCode = "";
			if (sysDepartment.getCode() != null
					&& sysDepartment.getCode().length() >= 2) {
				areaCode = sysDepartment.getCode().substring(0, 2);
			}
			ltravel.setTravelid(0L);
			ltravel.setArea(areaCode);
			ltravel.setDept(sysDepartment.getCode());
			ltravel.setAppuser(user.getActorId());
			ltravel.setPost(RequestUtil.getLoginUser(request).getHeadship());
			ltravel.setAppdate(new Date());
			request.setAttribute("ltravel", ltravel);
			request.setAttribute("appdate",
					DateUtils.getDate(ltravel.getAppdate()));
			JSONObject rowJSON = ltravel.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}

		return new ModelAndView("/oa/ltravel/ltravel_edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Ltravel ltravel = ltravelService.getLtravel(RequestUtils.getLong(
				request, "travelid"));
		request.setAttribute("ltravel", ltravel);
		JSONObject rowJSON = ltravel.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("ltravel.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/oa/ltravel/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("ltravel.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/oa/ltravel/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		LtravelQuery query = new LtravelQuery();
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
		int total = ltravelService.getLtravelCountByQueryCriteria(query);
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

			List<Ltravel> list = ltravelService.getLtravelsByQueryCriteria(
					start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Ltravel ltravel : list) {
					JSONObject rowJSON = new JSONObject();

					rowJSON.put("travelid", ltravel.getTravelid());
					rowJSON.put("_travelid_", ltravel.getTravelid());
					rowJSON.put("_otravelid_", ltravel.getTravelid());
					rowJSON.put("area", ltravel.getArea());
					if (ltravel.getCompany() != null) {
						rowJSON.put(
								"company",
								BaseDataManager
										.getInstance()
										.getValue(ltravel.getCompany(),
												ltravel.getArea()).getName());
					}
					if (ltravel.getDept() != null) {
						rowJSON.put("dept", BaseDataManager.getInstance()
								.getStringValue(ltravel.getDept(), "SYS_DEPTS"));
					}
					if (ltravel.getPost() != null) {
						rowJSON.put("post", ltravel.getPost());
					}
					if (ltravel.getAppuser() != null) {
						rowJSON.put(
								"appuser",
								BaseDataManager.getInstance().getStringValue(
										ltravel.getAppuser(), "SYS_USERS"));
					}
					if (ltravel.getAppdate() != null) {
						rowJSON.put("appdate",
								DateUtils.getDate(ltravel.getAppdate()));
						rowJSON.put("appdate_date",
								DateUtils.getDate(ltravel.getAppdate()));
						rowJSON.put("appdate_datetime",
								DateUtils.getDateTime(ltravel.getAppdate()));
					}
					if (ltravel.getTraveladdress() != null) {
						rowJSON.put("traveladdress", ltravel.getTraveladdress());
					}
					if (ltravel.getStartdate() != null) {
						rowJSON.put("startdate",
								DateUtils.getDate(ltravel.getStartdate()));
						rowJSON.put("startdate_date",
								DateUtils.getDate(ltravel.getStartdate()));
						rowJSON.put("startdate_datetime",
								DateUtils.getDateTime(ltravel.getStartdate()));
					}
					if (ltravel.getEnddate() != null) {
						rowJSON.put("enddate",
								DateUtils.getDate(ltravel.getEnddate()));
						rowJSON.put("enddate_date",
								DateUtils.getDate(ltravel.getEnddate()));
						rowJSON.put("enddate_datetime",
								DateUtils.getDateTime(ltravel.getEnddate()));
					}
					if (ltravel.getContent() != null) {
						rowJSON.put("content", ltravel.getContent());
					}
					if (ltravel.getAttachment() != null) {
						rowJSON.put("attachment", ltravel.getAttachment());
					}
					rowJSON.put("status", BaseDataManager.getInstance()
							.getValue(ltravel.getStatus() + "", "followStatus")
							.getName());
					rowJSON.put("travelnum", ltravel.getTravelnum());
					if (ltravel.getProcessname() != null) {
						rowJSON.put("processname", ltravel.getProcessname());
					}
					rowJSON.put("processinstanceid",
							ltravel.getProcessinstanceid());
					rowJSON.put("wfstatus", ltravel.getWfstatus());
					if (ltravel.getCreateDate() != null) {
						rowJSON.put("createDate",
								DateUtils.getDate(ltravel.getCreateDate()));
						rowJSON.put("createDate_date",
								DateUtils.getDate(ltravel.getCreateDate()));
						rowJSON.put("createDate_datetime",
								DateUtils.getDateTime(ltravel.getCreateDate()));
					}
					if (ltravel.getUpdateDate() != null) {
						rowJSON.put("updateDate",
								DateUtils.getDate(ltravel.getUpdateDate()));
						rowJSON.put("updateDate_date",
								DateUtils.getDate(ltravel.getUpdateDate()));
						rowJSON.put("updateDate_datetime",
								DateUtils.getDateTime(ltravel.getUpdateDate()));
					}
					if (ltravel.getCreateBy() != null) {
						rowJSON.put("createBy", ltravel.getCreateBy());
					}
					if (ltravel.getUpdateBy() != null) {
						rowJSON.put("updateBy", ltravel.getUpdateBy());
					}

					rowJSON.put("travelid", ltravel.getTravelid());
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

		return new ModelAndView("/oa/ltravel/ltravel_list", modelMap);
	}

	@RequestMapping("/getLtravel")
	@ResponseBody
	public byte[] getLtravel(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		RequestUtils.setRequestParameterToAttribute(request);
		Long travelid = RequestUtils.getLong(request, "travelid");
		Ltravel ltravel = ltravelService.getLtravel(travelid);
		JSONObject rowJSON = ltravel.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
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
					Ltravel ltravel = ltravelService
							.getLtravel(Long.valueOf(x));
					// 添加提交业务逻辑
					if (ltravel.getStatus() == 1) {
						if (ltravel.getProcessinstanceid() != null
								&& ltravel.getProcessinstanceid() != 0L) {
							returnFlag = returnFlag
									&& completeTask(ltravel, passFlag, request);
						} else {
							returnFlag = returnFlag
									&& startProcess(ltravel, request);
						}
					}
				}
			}

		} else if (travelid != null) {
			Ltravel ltravel = ltravelService.getLtravel(travelid);
			// 添加提交业务逻辑
			if (ltravel.getStatus() == 1) {
				if (ltravel.getProcessinstanceid() != null
						&& ltravel.getProcessinstanceid() != 0) {
					returnFlag = completeTask(ltravel, 0, request);
				} else {
					returnFlag = startProcess(ltravel, request);
				}
			}
		}
		if (returnFlag) {
			return ResponseUtils.responseJsonResult(true);
		} else {
			return ResponseUtils.responseJsonResult(false);
		}
	}

	private boolean completeTask(Ltravel ltravel, int flag,
			HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		String processName = "LtravelProcess";

		ProcessContext ctx = new ProcessContext();
		ctx.setRowId(ltravel.getTravelid());
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
		datafield1.setValue(ltravel.getTravelid());

		// 集团副总
		SysDepartment sysdepartMem1 = BaseDataManager.getInstance()
				.getSysDepartmentService().findByCode("JT");
		DataField datafield2 = new DataField();
		datafield2.setName("deptId01");
		datafield2.setValue(sysdepartMem1.getId());

		// 执行董事
		DataField datafield3 = new DataField();
		datafield3.setName("deptId02");
		datafield3.setValue(sysdepartMem1.getId());

		// 集团HR
		SysDepartment sysDepartment2 = BaseDataManager.getInstance()
				.getSysDepartmentService().findByCode("JT03");
		DataField datafield4 = new DataField();
		datafield4.setName("deptId03");
		datafield4.setValue(sysDepartment2.getId());

		DataField datafield5 = new DataField();
		datafield5.setName("isAgree");
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
		ctx.setDataFields(datafields);

		Long processInstanceId = null;
		boolean isOK = false;

		if (ltravel.getProcessinstanceid() != null
				&& ltravel.getWfstatus() != 9999
				&& ltravel.getWfstatus() != null) {
			processInstanceId = ltravel.getProcessinstanceid();
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

	private boolean startProcess(Ltravel ltravel, HttpServletRequest request) {
		String processName = "LtravelProcess";

		ProcessContext ctx = new ProcessContext();
		ctx.setRowId(ltravel.getTravelid());
		ctx.setActorId(ltravel.getAppuser());
		ctx.setProcessName(processName);
		/**
		 * 工作流控制参数
		 */
		Collection<DataField> datafields = new ArrayList<DataField>();

		DataField datafield1 = new DataField();
		datafield1.setName("rowId");
		datafield1.setValue(ltravel.getTravelid());

		// 集团副总
		SysDepartment sysdepartMem1 = BaseDataManager.getInstance()
				.getSysDepartmentService().findByCode("JT");
		DataField datafield2 = new DataField();
		datafield2.setName("deptId01");
		datafield2.setValue(sysdepartMem1.getId());

		// 执行董事
		DataField datafield3 = new DataField();
		datafield3.setName("deptId02");
		datafield3.setValue(sysdepartMem1.getId());

		// 集团HR
		SysDepartment sysDepartment2 = BaseDataManager.getInstance()
				.getSysDepartmentService().findByCode("JT03");
		DataField datafield4 = new DataField();
		datafield4.setName("deptId03");
		datafield4.setValue(sysDepartment2.getId());

		DataField datafield5 = new DataField();
		datafield5.setName("isAgree");
		datafield5.setValue("true");

		datafields.add(datafield1);
		datafields.add(datafield2);
		datafields.add(datafield3);
		datafields.add(datafield4);
		datafields.add(datafield5);
		ctx.setDataFields(datafields);

		Long processInstanceId = null;
		boolean isOK = false;

		if (ltravel.getProcessinstanceid() != null
				&& ltravel.getWfstatus() != 9999
				&& ltravel.getWfstatus() != null) {
			processInstanceId = ltravel.getProcessinstanceid();
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
		Long travelid = RequestUtils.getLong(request, "travelid");
		if (travelid != 0L) {
			Ltravel ltravel = ltravelService.getLtravel(travelid);
			// 添加提交业务逻辑
			if (ltravel.getStatus() == 1) {
				if (ltravel.getProcessinstanceid() != null) {
					returnFlag = completeTask(ltravel, 1, request);
				} else {
					returnFlag = startProcess(ltravel, request);
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
	@RequestMapping("/ltravelReviewList")
	public ModelAndView ltravelReviewList(HttpServletRequest request,
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
		return new ModelAndView("/oa/ltravel/ltravel_review_list", modelMap);
	}

	@RequestMapping("/review")
	public ModelAndView review(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		Ltravel ltravel = ltravelService.getLtravel(RequestUtils.getLong(
				request, "travelid"));
		if (RequestUtils.getBoolean(request, "lookover")) {
			request.setAttribute("lookover", true);
		} else {
			request.setAttribute("lookover", false);
		}

		if (ltravel != null) {
			request.setAttribute("ltravel", ltravel);
			request.setAttribute("appdate",
					DateUtils.getDate(ltravel.getAppdate()));
			request.setAttribute("startdate",
					DateUtils.getDate(ltravel.getStartdate()));
			request.setAttribute("enddate",
					DateUtils.getDate(ltravel.getEnddate()));
			JSONObject rowJSON = ltravel.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}
		return new ModelAndView("/oa/ltravel/ltravel_review", modelMap);
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

		return new ModelAndView("/oa/ltravel/ltravel_search_list", modelMap);
	}

	@RequestMapping("/getReviewLtravel")
	@ResponseBody
	public byte[] getReviewLtravel(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		LtravelQuery query = new LtravelQuery();
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

		int total = ltravelService.getReviewLtravelCountByQueryCriteria(query);
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

			List<Ltravel> list = ltravelService
					.getReviewLtravelsByQueryCriteria(start, limit, query);
			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);
				for (Ltravel ltravel : list) {
					JSONObject rowJSON = new JSONObject();

					if ("WD".equals(query.getWorkedProcessFlag())) {
						rowJSON.put("strstauts", "未审核");
					} else if ("PD".equals(query.getWorkedProcessFlag())) {
						rowJSON.put("strstauts", "已审核");
					}

					rowJSON.put("travelid", ltravel.getTravelid());
					rowJSON.put("_travelid_", ltravel.getTravelid());
					rowJSON.put("_otravelid_", ltravel.getTravelid());
					rowJSON.put(
							"area",
							BaseDataManager.getInstance()
									.getValue(ltravel.getArea(), "eara")
									.getName());
					if (ltravel.getCompany() != null) {
						rowJSON.put(
								"company",
								BaseDataManager
										.getInstance()
										.getValue(ltravel.getCompany(),
												ltravel.getArea()).getName());
					}
					if (ltravel.getDept() != null) {
						rowJSON.put("dept", BaseDataManager.getInstance()
								.getStringValue(ltravel.getDept(), "SYS_DEPTS"));
					}
					if (ltravel.getPost() != null) {
						rowJSON.put("post", ltravel.getPost());
					}
					if (ltravel.getAppuser() != null) {
						rowJSON.put(
								"appuser",
								BaseDataManager.getInstance().getStringValue(
										ltravel.getAppuser(), "SYS_USERS"));
					}
					if (ltravel.getAppdate() != null) {
						rowJSON.put("appdate",
								DateUtils.getDate(ltravel.getAppdate()));
						rowJSON.put("appdate_date",
								DateUtils.getDate(ltravel.getAppdate()));
						rowJSON.put("appdate_datetime",
								DateUtils.getDateTime(ltravel.getAppdate()));
					}
					if (ltravel.getTraveladdress() != null) {
						rowJSON.put("traveladdress", ltravel.getTraveladdress());
					}
					if (ltravel.getStartdate() != null) {
						rowJSON.put("startdate",
								DateUtils.getDate(ltravel.getStartdate()));
						rowJSON.put("startdate_date",
								DateUtils.getDate(ltravel.getStartdate()));
						rowJSON.put("startdate_datetime",
								DateUtils.getDateTime(ltravel.getStartdate()));
					}
					if (ltravel.getEnddate() != null) {
						rowJSON.put("enddate",
								DateUtils.getDate(ltravel.getEnddate()));
						rowJSON.put("enddate_date",
								DateUtils.getDate(ltravel.getEnddate()));
						rowJSON.put("enddate_datetime",
								DateUtils.getDateTime(ltravel.getEnddate()));
					}
					if (ltravel.getContent() != null) {
						rowJSON.put("content", ltravel.getContent());
					}
					if (ltravel.getAttachment() != null) {
						rowJSON.put("attachment", ltravel.getAttachment());
					}
					rowJSON.put("status", BaseDataManager.getInstance()
							.getValue(ltravel.getStatus() + "", "followStatus")
							.getName());
					rowJSON.put("travelnum", ltravel.getTravelnum());
					if (ltravel.getProcessname() != null) {
						rowJSON.put("processname", ltravel.getProcessname());
					}
					rowJSON.put("processinstanceid",
							ltravel.getProcessinstanceid());
					rowJSON.put("wfstatus", ltravel.getWfstatus());
					if (ltravel.getCreateDate() != null) {
						rowJSON.put("createDate",
								DateUtils.getDate(ltravel.getCreateDate()));
						rowJSON.put("createDate_date",
								DateUtils.getDate(ltravel.getCreateDate()));
						rowJSON.put("createDate_datetime",
								DateUtils.getDateTime(ltravel.getCreateDate()));
					}
					if (ltravel.getUpdateDate() != null) {
						rowJSON.put("updateDate",
								DateUtils.getDate(ltravel.getUpdateDate()));
						rowJSON.put("updateDate_date",
								DateUtils.getDate(ltravel.getUpdateDate()));
						rowJSON.put("updateDate_datetime",
								DateUtils.getDateTime(ltravel.getUpdateDate()));
					}
					if (ltravel.getCreateBy() != null) {
						rowJSON.put("createBy", ltravel.getCreateBy());
					}
					if (ltravel.getUpdateBy() != null) {
						rowJSON.put("updateBy", ltravel.getUpdateBy());
					}

					rowJSON.put("travelid", ltravel.getTravelid());
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
		LtravelQuery query = new LtravelQuery();
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
		int total = ltravelService.getLtravelCountByQueryCriteria(query);
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

			List<Ltravel> list = ltravelService.getLtravelsByQueryCriteria(
					start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Ltravel ltravel : list) {
					JSONObject rowJSON = new JSONObject();

					rowJSON.put("travelid", ltravel.getTravelid());
					rowJSON.put("_travelid_", ltravel.getTravelid());
					rowJSON.put("_otravelid_", ltravel.getTravelid());
					rowJSON.put(
							"area",
							BaseDataManager.getInstance()
									.getValue(ltravel.getArea(), "eara")
									.getName());
					if (ltravel.getCompany() != null) {
						rowJSON.put(
								"company",
								BaseDataManager
										.getInstance()
										.getValue(ltravel.getCompany(),
												ltravel.getArea()).getName());
					}
					if (ltravel.getDept() != null) {
						rowJSON.put("dept", BaseDataManager.getInstance()
								.getStringValue(ltravel.getDept(), "SYS_DEPTS"));
					}
					if (ltravel.getPost() != null) {
						rowJSON.put("post", ltravel.getPost());
					}
					if (ltravel.getAppuser() != null) {
						rowJSON.put(
								"appuser",
								BaseDataManager.getInstance().getStringValue(
										ltravel.getAppuser(), "SYS_USERS"));
					}
					if (ltravel.getAppdate() != null) {
						rowJSON.put("appdate",
								DateUtils.getDate(ltravel.getAppdate()));
						rowJSON.put("appdate_date",
								DateUtils.getDate(ltravel.getAppdate()));
						rowJSON.put("appdate_datetime",
								DateUtils.getDateTime(ltravel.getAppdate()));
					}
					if (ltravel.getTraveladdress() != null) {
						rowJSON.put("traveladdress", ltravel.getTraveladdress());
					}
					if (ltravel.getStartdate() != null) {
						rowJSON.put("startdate",
								DateUtils.getDate(ltravel.getStartdate()));
						rowJSON.put("startdate_date",
								DateUtils.getDate(ltravel.getStartdate()));
						rowJSON.put("startdate_datetime",
								DateUtils.getDateTime(ltravel.getStartdate()));
					}
					if (ltravel.getEnddate() != null) {
						rowJSON.put("enddate",
								DateUtils.getDate(ltravel.getEnddate()));
						rowJSON.put("enddate_date",
								DateUtils.getDate(ltravel.getEnddate()));
						rowJSON.put("enddate_datetime",
								DateUtils.getDateTime(ltravel.getEnddate()));
					}
					if (ltravel.getContent() != null) {
						rowJSON.put("content", ltravel.getContent());
					}
					if (ltravel.getAttachment() != null) {
						rowJSON.put("attachment", ltravel.getAttachment());
					}
					rowJSON.put("status", BaseDataManager.getInstance()
							.getValue(ltravel.getStatus() + "", "followStatus")
							.getName());
					rowJSON.put("travelnum", ltravel.getTravelnum());
					if (ltravel.getProcessname() != null) {
						rowJSON.put("processname", ltravel.getProcessname());
					}
					rowJSON.put("processinstanceid",
							ltravel.getProcessinstanceid());
					rowJSON.put("wfstatus", ltravel.getWfstatus());
					if (ltravel.getCreateDate() != null) {
						rowJSON.put("createDate",
								DateUtils.getDate(ltravel.getCreateDate()));
						rowJSON.put("createDate_date",
								DateUtils.getDate(ltravel.getCreateDate()));
						rowJSON.put("createDate_datetime",
								DateUtils.getDateTime(ltravel.getCreateDate()));
					}
					if (ltravel.getUpdateDate() != null) {
						rowJSON.put("updateDate",
								DateUtils.getDate(ltravel.getUpdateDate()));
						rowJSON.put("updateDate_date",
								DateUtils.getDate(ltravel.getUpdateDate()));
						rowJSON.put("updateDate_datetime",
								DateUtils.getDateTime(ltravel.getUpdateDate()));
					}
					if (ltravel.getCreateBy() != null) {
						rowJSON.put("createBy", ltravel.getCreateBy());
					}
					if (ltravel.getUpdateBy() != null) {
						rowJSON.put("updateBy", ltravel.getUpdateBy());
					}

					rowJSON.put("travelid", ltravel.getTravelid());
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
	@RequestMapping("/submit")
	public byte[] submit(HttpServletRequest request, ModelMap modelMap) {
		boolean returnFlag = false;
		Long travelid = RequestUtils.getLong(request, "travelid");
		if (travelid != 0L) {
			Ltravel ltravel = ltravelService.getLtravel(travelid);
			if (ltravel.getStatus() == 0 || ltravel.getStatus() == 3) {
				returnFlag = startProcess(ltravel, request);
			}
		}
		if (returnFlag) {
			return ResponseUtils.responseJsonResult(true);
		} else {
			return ResponseUtils.responseJsonResult(false);
		}

	}

}