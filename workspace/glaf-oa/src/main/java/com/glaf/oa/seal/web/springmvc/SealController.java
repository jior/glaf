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
package com.glaf.oa.seal.web.springmvc;

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
import com.glaf.oa.seal.model.Seal;
import com.glaf.oa.seal.query.SealQuery;
import com.glaf.oa.seal.service.SealService;
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

@Controller("/oa/seal")
@RequestMapping("/oa/seal")
public class SealController {
	protected static final Log logger = LogFactory.getLog(SealController.class);

	protected SealService sealService;

	protected AttachmentService attachmentService;

	protected SysDepartmentService sysDepartmentService;

	protected SysUserService sysUserService;

	public SealController() {

	}

	private boolean completeTask(Seal seal, int flag, HttpServletRequest request) {
		String processName = "Sealprocess";
		ProcessContext ctx = new ProcessContext();
		ctx.setRowId(seal.getSealid());
		// 获取申请人
		User user = sysUserService.findByAccount(seal.getAppuser());

		// 获取操作者
		User opUser = RequestUtils.getUser(request);
		if (seal.getWfstatus() == -5555) {
			ctx.setActorId(seal.getAppuser());
		} else {
			ctx.setActorId(opUser.getActorId());
		}

		ctx.setProcessName(processName);
		String opinion = request.getParameter("approveOpinion");
		if (opinion != null && !"".equals(opinion)) {
			ctx.setOpinion(opinion);
		}
		/**
		 * 工作流控制参数
		 */
		Collection<DataField> datafields = new ArrayList<DataField>();
		// 获取 部门节点
		long deptId02 = user.getDeptId();
		SysDepartment curdept = sysDepartmentService.findById(deptId02);
		// 获取当地部门节点
		String curAreadeptCode = curdept.getCode().substring(0, 2);
		SysDepartment curAreadept = sysDepartmentService
				.findByCode(curAreadeptCode);
		// 获取集团节点
		SysDepartment sysdeptMem = sysDepartmentService.findByCode("JT");

		// 获取当地行政部门
		SysDepartment admindept = sysDepartmentService
				.findByCode(curAreadeptCode + "06");
		DataField datafield1 = new DataField();
		datafield1.setName("deptId01");
		datafield1.setValue(admindept.getId());
		DataField datafield2 = new DataField();
		datafield2.setName("deptId02");
		datafield2.setValue(curAreadept.getId());
		DataField datafield3 = new DataField();
		datafield3.setName("isContractAndMoneyLess");
		if ("CON".equals(seal.getSealtype()) && seal.getMoney() < 500000) {
			datafield3.setValue("true");
		} else {
			datafield3.setValue("false");
		}
		datafield2.setValue(curAreadept.getId());
		DataField datafield4 = new DataField();
		datafield4.setName("deptId04");
		datafield4.setValue(sysdeptMem.getId());
		DataField datafield5 = new DataField();
		datafield5.setName("isAgree");
		DataField datafield6 = new DataField();
		datafield6.setName("rowId");
		datafield6.setValue(seal.getSealid());
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
		ctx.setDataFields(datafields);
		boolean isOK = false;
		if (seal.getProcessinstanceid() != null && seal.getWfstatus() != 9999
				&& seal.getWfstatus() != null) {
			ctx.setProcessInstanceId((new Double(seal.getProcessinstanceid())
					.longValue()));

			isOK = ProcessContainer.getContainer().completeTask(ctx);
			logger.info("workflow 中");
		} else {
			long processInstanceId = ProcessContainer.getContainer()
					.startProcess(ctx);
			logger.info("processInstanceId=" + processInstanceId);
			if (processInstanceId != 0) {
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
		Long sealid = RequestUtils.getLong(request, "sealid");
		String sealids = request.getParameter("sealids");
		if (StringUtils.isNotEmpty(sealids)) {
			StringTokenizer token = new StringTokenizer(sealids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Seal seal = sealService.getSeal(Long.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					if (seal != null
							&& (StringUtils.equals(seal.getCreateBy(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						if (seal.getProcessinstanceid() != null
								&& 0d != seal.getProcessinstanceid()) {
							ProcessContext ctx = new ProcessContext();
							ctx.setActorId(loginContext.getActorId());
							ctx.setProcessInstanceId(Long.valueOf(seal
									.getProcessinstanceid().longValue()));
							ProcessContainer.getContainer().abortProcess(ctx);
						}
						sealService.deleteById(seal.getSealid());
					}
				}
			}
		} else if (sealid != null) {
			Seal seal = sealService.getSeal(Long.valueOf(sealid));
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (seal != null
					&& (StringUtils.equals(seal.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				if (seal.getProcessinstanceid() != null
						&& 0d != seal.getProcessinstanceid()) {
					ProcessContext ctx = new ProcessContext();
					ctx.setActorId(loginContext.getActorId());
					ctx.setProcessInstanceId(Long.valueOf(seal
							.getProcessinstanceid().longValue()));
					ProcessContainer.getContainer().abortProcess(ctx);
				}
				sealService.deleteById(seal.getSealid());
			}
		}
	}

	@ResponseBody
	@RequestMapping("/detail")
	public byte[] detail(HttpServletRequest request) throws IOException {
		// RequestUtils.setRequestParameterToAttribute(request);
		// Map<String, Object> params = RequestUtils.getParameterMap(request);
		Seal seal = sealService
				.getSeal(RequestUtils.getLong(request, "sealid"));

		JSONObject rowJSON = seal.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		Seal seal = sealService
				.getSeal(RequestUtils.getLong(request, "sealid"));
		if (seal != null) {
			request.setAttribute("seal", seal);
			JSONObject rowJSON = seal.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		} else {
			seal = new Seal();
			long deptId01 = user.getDeptId();
			SysDepartment curdept = sysDepartmentService.findById(deptId01);
			// 获取当地部门节点
			String curAreadeptCode = curdept.getCode().substring(0, 2);

			seal.setArea(curAreadeptCode);
			seal.setDept(curdept.getCode());
			seal.setAppuser(user.getActorId());
			seal.setPost(RequestUtil.getLoginUser(request).getHeadship());
			request.setAttribute("seal", seal);
			JSONObject rowJSON = seal.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}

		boolean canUpdate = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {

		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (seal != null) {
				if (seal.getStatus() == 0 || seal.getStatus() == -1) {
					canUpdate = true;
				}
			}
		}

		request.setAttribute("canUpdate", canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("seal.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		if (seal != null) {
			String appusername = BaseDataManager.getInstance().getStringValue(
					seal.getAppuser(), "SYS_USERS");
			request.setAttribute("appusername", appusername);
		}

		return new ModelAndView("/oa/seal/sealedit", modelMap);
	}

	@RequestMapping("/getReviewSeal")
	@ResponseBody
	public byte[] getReviewSeal(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SealQuery query = new SealQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
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
		 * 此处业务逻辑需自行调整
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
			limit = 15;
		}

		JSONObject result = new JSONObject();
		int total = sealService.getReviewSealCountByQueryCriteria(query);
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

			List<Seal> list = sealService.getReviewSealsByQueryCriteria(start,
					limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Seal seal : list) {
					String companyname = seal.getCompany();
					String appuser = seal.getAppuser();
					try {
						String companyname_CN = BaseDataManager.getInstance()
								.getStringValue(seal.getCompany(),
										seal.getArea());
						seal.setCompany(companyname_CN);
						// 用户名处理
						String appusername = BaseDataManager.getInstance()
								.getStringValue(seal.getAppuser(), "SYS_USERS");
						seal.setAppuser(appusername);
					} catch (Exception e) {
						logger.error(e.getMessage());
						seal.setAppuser(appuser);
						seal.setCompany(companyname);
					}
					// 印章类型
					String[] sealtypes = seal.getSealtype().split(",");
					String sealtypes_CH = "";
					for (int i = 0; i < sealtypes.length; i++) {
						String sealtype_ch = BaseDataManager.getInstance()
								.getStringValue(sealtypes[i], "seal");
						sealtypes_CH += sealtype_ch;
						if (i != sealtypes.length - 1) {
							sealtypes_CH += ",";
						}
					}
					seal.setSealtype(sealtypes_CH);
					JSONObject rowJSON = seal.toJsonObject();
					rowJSON.put("sealId", seal.getSealid());
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
		return new ModelAndView("/oa/seal/search_seallist", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SealQuery query = new SealQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);

		User user = loginContext.getUser();
		long deptId01 = user.getDeptId();
		SysDepartment curdept = sysDepartmentService.findById(deptId01);

		// 由于页面输入的日期没有时分秒，查找范围的时候获取不到最后一天的记录,人为设置
		if (null != query.getAppdateLessThanOrEqual()) {
			Date appdateLessThanOrEqual = query.getAppdateLessThanOrEqual();
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
		int total = sealService.getSealCountByQueryCriteria(query);
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

			List<Seal> list = sealService.getSealsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Seal seal : list) {
					String companyname = seal.getCompany();
					String appuser = seal.getAppuser();
					try {
						String companyname_CN = BaseDataManager.getInstance()
								.getStringValue(seal.getCompany(),
										seal.getArea());
						seal.setCompany(companyname_CN);
						// 用户名处理
						String appusername = BaseDataManager.getInstance()
								.getStringValue(seal.getAppuser(), "SYS_USERS");
						seal.setAppuser(appusername);
					} catch (Exception e) {
						logger.error(e.getMessage());
						seal.setAppuser(appuser);
						seal.setCompany(companyname);
					}
					// 印章类型
					String[] sealtypes = seal.getSealtype().split(",");
					String sealtypes_CH = "";
					for (int i = 0; i < sealtypes.length; i++) {
						String sealtype_ch = BaseDataManager.getInstance()
								.getStringValue(sealtypes[i], "seal");
						sealtypes_CH += sealtype_ch;
						if (i != sealtypes.length - 1) {
							sealtypes_CH += ",";
						}
					}
					seal.setSealtype(sealtypes_CH);
					JSONObject rowJSON = seal.toJsonObject();
					rowJSON.put("sealId", seal.getSealid());
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
		return new ModelAndView("/oa/seal/seallist", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("seal.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/oa/seal/query", modelMap);
	}

	@RequestMapping("/review")
	public ModelAndView review(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		Seal seal = sealService
				.getSeal(RequestUtils.getLong(request, "sealid"));
		if (seal != null) {
			request.setAttribute("seal", seal);
			JSONObject rowJSON = seal.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}

		boolean canUpdate = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {

		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (seal != null) {
				if (seal.getStatus() == 0 || seal.getStatus() == -1) {
					canUpdate = true;
				}
			}
		}

		if (RequestUtils.getBoolean(request, "lookover")) {
			request.setAttribute("lookover", true);
		} else {
			request.setAttribute("lookover", false);
		}

		request.setAttribute("canUpdate", canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("seal.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		String appusername = BaseDataManager.getInstance().getStringValue(
				seal.getAppuser(), "SYS_USERS");
		request.setAttribute("appusername", appusername);

		return new ModelAndView("/oa/seal/sealReview", modelMap);
	}

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
		return new ModelAndView("/oa/seal/sealReviewlist", modelMap);
	}

	@ResponseBody
	@RequestMapping("/rollback")
	public byte[] rollback(HttpServletRequest request, ModelMap modelMap) {
		Long sealid = RequestUtils.getLong(request, "sealid");
		String sealids = request.getParameter("sealids");
		boolean returnFlag = false;
		if (StringUtils.isNotEmpty(sealids)) {
			StringTokenizer token = new StringTokenizer(sealids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Seal seal = sealService.getSeal(Long.valueOf(x));
					if (seal != null) {
						if (null != seal.getProcessinstanceid()
								&& 0 != seal.getProcessinstanceid()) {
							returnFlag = completeTask(seal, 1, request);
						} else {
							returnFlag = startProcess(seal, request);
						}
					}
				}
			}
		} else if (sealid != null) {
			Seal seal = sealService.getSeal(Long.valueOf(sealid));

			if (seal != null) {
				if (null != seal.getProcessinstanceid()
						&& 0 != seal.getProcessinstanceid()) {
					returnFlag = completeTask(seal, 1, request);
				} else {
					returnFlag = startProcess(seal, request);
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
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");
		Seal seal = new Seal();
		Tools.populate(seal, params);
		seal.setArea(request.getParameter("area"));
		seal.setCompany(request.getParameter("company"));
		seal.setDept(request.getParameter("dept"));
		seal.setPost(request.getParameter("post"));
		seal.setAppuser(request.getParameter("appuser"));
		seal.setSealtype(request.getParameter("sealtypes"));
		seal.setSupplier(request.getParameter("supplier"));
		seal.setContent(request.getParameter("content"));
		seal.setNum(RequestUtils.getInt(request, "num"));
		seal.setRemark(request.getParameter("remark"));
		seal.setMoney(RequestUtils.getDouble(request, "money"));

		// 新增处理
		if (null == seal.getSealid()) {
			seal.setCreateBy(actorId);
			seal.setCreateDate(new Date());
			seal.setAppdate(new Date());
			seal.setStatus(0);
		}
		// 修改处理
		else {
			seal.setUpdateBy(user.getActorId());
			seal.setUpdateDate(new Date());
			seal.setStatus(0);
		}
		long flag = seal.getSealid() == null ? 0 : seal.getSealid();
		sealService.save(seal);
		if (flag == 0) {
			this.attachmentService.updateByReferTypeAndCreateId(
					seal.getSealid(), 4, user.getId());
		}
		boolean returnFlag = false;
		seal = sealService.getSeal(seal.getSealid());
		if (null != seal.getProcessinstanceid()
				&& 0 != seal.getProcessinstanceid()) {
			if (seal.getWfstatus() == -5555) {
				seal.setStatus(1);
				sealService.save(seal);
			}
			returnFlag = completeTask(seal, 0, request);
		} else {
			returnFlag = startProcess(seal, request);
		}
		if (returnFlag) {
			return ResponseUtils.responseJsonResult(true);
		} else {
			return ResponseUtils.responseJsonResult(false);
		}
	}

	@ResponseBody
	@RequestMapping("/saveSeal")
	public byte[] saveSeal(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Seal seal = new Seal();
		try {
			Tools.populate(seal, params);
			seal.setArea(request.getParameter("area"));
			seal.setCompany(request.getParameter("company"));
			seal.setDept(request.getParameter("dept"));
			seal.setPost(request.getParameter("post"));
			seal.setAppuser(request.getParameter("appuser"));
			seal.setSealtype(request.getParameter("sealtypes"));
			seal.setSupplier(request.getParameter("supplier"));
			seal.setContent(request.getParameter("content"));
			seal.setNum(RequestUtils.getInt(request, "num"));
			seal.setRemark(request.getParameter("remark"));
			seal.setMoney(RequestUtils.getDouble(request, "money"));
			// 新增处理
			if (null == seal.getSealid()) {
				seal.setCreateBy(actorId);
				seal.setCreateDate(new Date());
				seal.setAppdate(new Date());
				seal.setStatus(0);
			}
			// 修改处理
			else {
				seal.setUpdateBy(user.getActorId());
				seal.setUpdateDate(new Date());
				seal.setStatus(0);
			}
			long flag = seal.getSealid() == null ? 0 : seal.getSealid();
			sealService.save(seal);
			if (flag == 0) {
				this.attachmentService.updateByReferTypeAndCreateId(
						seal.getSealid(), 4, user.getId());
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
	public void setSealService(SealService sealService) {
		this.sealService = sealService;
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

	private boolean startProcess(Seal seal, HttpServletRequest request) {
		String processName = "Sealprocess";
		ProcessContext ctx = new ProcessContext();
		ctx.setRowId(seal.getSealid());
		User user = sysUserService.findByAccount(seal.getAppuser());
		String actorId = user.getActorId();
		ctx.setActorId(actorId);
		ctx.setProcessName(processName);
		/**
		 * 工作流控制参数
		 */
		Collection<DataField> datafields = new ArrayList<DataField>();
		// 获取 部门节点
		long deptId02 = user.getDeptId();
		SysDepartment curdept = sysDepartmentService.findById(deptId02);
		// 获取当地部门节点
		String curAreadeptCode = curdept.getCode().substring(0, 2);
		SysDepartment curAreadept = sysDepartmentService
				.findByCode(curAreadeptCode);
		// 获取集团节点
		SysDepartment sysdeptMem = sysDepartmentService.findByCode("JT");

		// 获取当地行政部门
		SysDepartment admindept = sysDepartmentService
				.findByCode(curAreadeptCode + "06");
		DataField datafield1 = new DataField();
		datafield1.setName("deptId01");
		datafield1.setValue(admindept.getId());
		DataField datafield2 = new DataField();
		datafield2.setName("deptId02");
		datafield2.setValue(curAreadept.getId());
		DataField datafield3 = new DataField();
		datafield3.setName("isContractAndMoneyLess");
		if ("CON".equals(seal.getSealtype()) && seal.getMoney() < 500000) {
			datafield3.setValue("true");
		} else {
			datafield3.setValue("false");
		}
		DataField datafield4 = new DataField();
		datafield4.setName("deptId04");
		datafield4.setValue(sysdeptMem.getId());

		DataField datafield6 = new DataField();
		datafield6.setName("rowId");
		datafield6.setValue(seal.getSealid());

		datafields.add(datafield1);
		datafields.add(datafield2);
		datafields.add(datafield3);
		datafields.add(datafield4);
		datafields.add(datafield6);
		ctx.setDataFields(datafields);
		boolean isOK = false;
		if (seal.getProcessinstanceid() != null && seal.getWfstatus() != 9999
				&& seal.getWfstatus() != null) {
			isOK = ProcessContainer.getContainer().completeTask(ctx);
			logger.info("workflow 中");
		} else {
			long processInstanceId = ProcessContainer.getContainer()
					.startProcess(ctx);
			logger.info("processInstanceId=" + processInstanceId);
			if (processInstanceId != 0) {
				isOK = true;
				logger.info("workflow start");
			}
		}
		return isOK;
	}

	@ResponseBody
	@RequestMapping("/submit")
	public byte[] submit(HttpServletRequest request, ModelMap modelMap) {
		Long sealid = RequestUtils.getLong(request, "sealid");
		String sealids = request.getParameter("sealids");
		boolean returnFlag = false;
		if (StringUtils.isNotEmpty(sealids)) {
			StringTokenizer token = new StringTokenizer(sealids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Seal seal = sealService.getSeal(Long.valueOf(x));
					if (seal != null) {
						if (null != seal.getProcessinstanceid()
								&& 0 != seal.getProcessinstanceid()) {
							if (seal.getWfstatus() == -5555) {
								seal.setStatus(1);
								sealService.save(seal);

							}
							returnFlag = completeTask(seal, 0, request);
						} else {
							returnFlag = startProcess(seal, request);
						}
					}
				}
			}
		} else if (sealid != null) {
			Seal seal = sealService.getSeal(Long.valueOf(sealid));
			/**
			 * 此处业务逻辑需自行调整
			 */

			if (seal != null) {
				if (null != seal.getProcessinstanceid()
						&& 0 != seal.getProcessinstanceid()) {
					if (seal.getWfstatus() == -5555) {
						seal.setStatus(1);
						sealService.save(seal);

					}
					returnFlag = completeTask(seal, 0, request);
				} else {
					returnFlag = startProcess(seal, request);
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

		Seal seal = sealService
				.getSeal(RequestUtils.getLong(request, "sealid"));

		seal.setArea(request.getParameter("area"));
		seal.setCompany(request.getParameter("company"));
		seal.setDept(request.getParameter("dept"));
		seal.setPost(request.getParameter("post"));
		seal.setAppuser(request.getParameter("appuser"));
		seal.setSealtype(request.getParameter("sealtype"));
		seal.setSupplier(request.getParameter("supplier"));
		seal.setContent(request.getParameter("content"));
		seal.setNum(RequestUtils.getInt(request, "num"));
		seal.setRemark(request.getParameter("remark"));
		seal.setAttachment(request.getParameter("attachment"));
		seal.setAppdate(RequestUtils.getDate(request, "appdate"));
		seal.setStatus(RequestUtils.getInt(request, "status"));
		seal.setProcessname(request.getParameter("processname"));
		seal.setWfstatus(RequestUtils.getDouble(request, "wfstatus"));
		seal.setCreateBy(request.getParameter("createBy"));
		seal.setCreateDate(RequestUtils.getDate(request, "createDate"));
		seal.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		seal.setUpdateBy(request.getParameter("updateBy"));

		sealService.save(seal);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Seal seal = sealService
				.getSeal(RequestUtils.getLong(request, "sealid"));
		request.setAttribute("seal", seal);
		JSONObject rowJSON = seal.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("seal.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/oa/seal/view");
	}
}