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

package com.glaf.cms.info.web.springmvc;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmContext;
import org.jbpm.db.GraphSession;
import org.jbpm.graph.def.ProcessDefinition;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import com.alibaba.fastjson.*;

import com.glaf.core.base.DataFile;
import com.glaf.core.base.TreeModel;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.SystemParam;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.service.IBlobService;
import com.glaf.core.service.ISystemParamService;
import com.glaf.core.service.ITreeModelService;
import com.glaf.core.util.*;

import com.glaf.cms.info.model.*;
import com.glaf.cms.info.query.*;
import com.glaf.cms.info.service.*;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.Context;
import com.glaf.jbpm.context.ProcessContext;
import com.glaf.jbpm.datafield.DataField;
import com.glaf.jbpm.model.ActivityInstance;
import com.glaf.jbpm.model.TaskItem;

@Controller("/cms/info")
@RequestMapping("/cms/info")
public class PublicInfoMgmtController {
	protected static final Log logger = LogFactory
			.getLog(PublicInfoMgmtController.class);

	protected IBlobService blobService;

	protected PublicInfoService publicInfoService;

	protected ISystemParamService systemParamService;

	protected ITreeModelService treeModelService;

	public PublicInfoMgmtController() {

	}

	@RequestMapping("/completeTask")
	@ResponseBody
	public byte[] completeTask(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug(params);

		PublicInfo publicInfo = publicInfoService.getPublicInfo(request
				.getParameter("id"));
		if (publicInfo != null && publicInfo.getProcessInstanceId() != null
				&& publicInfo.getWfStatus() != 9999) {
			TaskItem taskItem = ProcessContainer.getContainer().getMinTaskItem(
					actorId, publicInfo.getProcessInstanceId());
			if (taskItem != null) {
				String route = request.getParameter("route");
				String isAgree = request.getParameter("isAgree");
				String opinion = request.getParameter("opinion");
				ProcessContext ctx = new ProcessContext();
				Collection<DataField> datafields = new java.util.concurrent.CopyOnWriteArrayList<DataField>();
				if (StringUtils.isNotEmpty(isAgree)) {
					DataField datafield = new DataField();
					datafield.setName("isAgree");
					datafield.setValue(isAgree);
					datafields.add(datafield);
				}
				if (StringUtils.isNotEmpty(route)) {
					DataField datafield = new DataField();
					datafield.setName("route");
					datafield.setValue(route);
					datafields.add(datafield);
				}
				ctx.setActorId(actorId);
				ctx.setOpinion(opinion);
				ctx.setDataFields(datafields);
				ctx.setTaskInstanceId(taskItem.getTaskInstanceId());
				ctx.setProcessInstanceId(publicInfo.getProcessInstanceId());
				try {
					boolean isOK = ProcessContainer.getContainer()
							.completeTask(ctx);
					if (isOK) {
						return ResponseUtils.responseJsonResult(true);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error(ex);
				}
			}
		}

		return ResponseUtils.responseJsonResult(false);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String id = ParamUtils.getString(params, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					PublicInfo publicInfo = publicInfoService.getPublicInfo(x);
					/**
					 * 此处业务逻辑需自行调整
					 */
					if (publicInfo != null
							&& (StringUtils.equals(publicInfo.getCreateBy(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						publicInfoService.deleteById(publicInfo.getId());
					}
				}
			}
		} else if (StringUtils.isNotEmpty(id)) {
			PublicInfo publicInfo = publicInfoService.getPublicInfo(id);
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (publicInfo != null
					&& (StringUtils.equals(publicInfo.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				publicInfoService.deleteById(publicInfo.getId());
			}
		}
	}

	@ResponseBody
	@RequestMapping("/detail")
	public byte[] detail(HttpServletRequest request) throws IOException {
		// RequestUtils.setRequestParameterToAttribute(request);
		// Map<String, Object> params = RequestUtils.getParameterMap(request);
		PublicInfo publicInfo = publicInfoService.getPublicInfo(request
				.getParameter("id"));

		JSONObject rowJSON = publicInfo.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		String serviceKey = request.getParameter("serviceKey");
		Long nodeId = RequestUtils.getLong(request, "nodeId");

		PublicInfo publicInfo = publicInfoService.getPublicInfo(request
				.getParameter("id"));

		if (publicInfo != null) {
			request.setAttribute("publicInfo", publicInfo);
			JSONObject rowJSON = publicInfo.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
			serviceKey = publicInfo.getServiceKey();
			nodeId = publicInfo.getNodeId();
		}

		if (StringUtils.isNotEmpty(serviceKey)) {
			TreeModel treeModel = treeModelService
					.getTreeModelByCode(serviceKey);
			request.setAttribute("treeModel", treeModel);
		}

		if (nodeId > 0) {
			TreeModel treeModel = treeModelService.getTreeModel(nodeId);
			request.setAttribute("treeModel", treeModel);
		}

		boolean canUpdate = false;
		boolean canSubmit = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {
			if (publicInfo != null && publicInfo.getProcessInstanceId() != null) {
				ProcessContainer container = ProcessContainer.getContainer();
				Collection<Long> processInstanceIds = container
						.getRunningProcessInstanceIds(actorId);
				if (processInstanceIds.contains(publicInfo
						.getProcessInstanceId())) {
					canSubmit = true;
				}
				if (publicInfo.getStatus() == 0 || publicInfo.getStatus() == -1) {
					canUpdate = true;
				}
				TaskItem taskItem = container.getMinTaskItem(actorId,
						publicInfo.getProcessInstanceId());
				if (taskItem != null) {
					request.setAttribute("taskItem", taskItem);
				}
				List<ActivityInstance> stepInstances = container
						.getActivityInstances(publicInfo.getProcessInstanceId());
				request.setAttribute("stepInstances", stepInstances);
				request.setAttribute("stateInstances", stepInstances);
			} else {
				canSubmit = true;
				canUpdate = true;
			}
		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (publicInfo != null) {
				if (publicInfo.getStatus() == 0 || publicInfo.getStatus() == -1) {
					canUpdate = true;
				}
			}
		}

		if (publicInfo != null) {
			List<DataFile> dataFiles = blobService.getBlobList(publicInfo
					.getId());
			request.setAttribute("dataFiles", dataFiles);
		}

		request.setAttribute("canSubmit", canSubmit);
		request.setAttribute("canUpdate", canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("publicInfo.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/cms/info/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		User user = RequestUtils.getUser(request);
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		RequestUtils.setRequestParameterToAttribute(request);

		String serviceKey = request.getParameter("serviceKey");
		Long nodeId = RequestUtils.getLong(request, "nodeId");

		Map<Long, TreeModel> treeModelMap = new java.util.concurrent.ConcurrentHashMap<Long, TreeModel>();
		TreeModel treeModel = null;
		if (StringUtils.isNotEmpty(serviceKey)) {
			treeModel = treeModelService.getTreeModelByCode(serviceKey);
			request.setAttribute("treeModel", treeModel);
			List<TreeModel> treeModels = treeModelService
					.getChildrenTreeModels(treeModel.getId());
			if (treeModels != null && !treeModels.isEmpty()) {
				for (TreeModel t : treeModels) {
					treeModelMap.put(t.getId(), t);
				}
			}
		}

		if (nodeId > 0) {
			treeModel = treeModelService.getTreeModel(nodeId);
			request.setAttribute("treeModel", treeModel);
		}

		String processName = null;

		SystemParam param = systemParamService.getSystemParam(serviceKey,
				treeModel.getCode(), "processName");
		if (param != null) {
			processName = param.getStringVal();
		}

		String workedProcessFlag = request.getParameter("workedProcessFlag");
		if (StringUtils.isEmpty(workedProcessFlag)) {
			workedProcessFlag = "x";
		}

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		PublicInfoQuery query = new PublicInfoQuery();
		Tools.populate(query, params);
		query.setWorkedProcessFlag(workedProcessFlag);
		List<String> appActorIds = new java.util.concurrent.CopyOnWriteArrayList<String>();
		appActorIds.addAll(loginContext.getAgents());
		appActorIds.add(loginContext.getActorId());
		query.setAppActorIds(appActorIds);

		ProcessContainer container = ProcessContainer.getContainer();
		if (StringUtils.equals(workedProcessFlag, "PD")) {
			List<Long> processInstanceIds = container
					.getRunningProcessInstanceIdsByName(processName,
							user.getActorId());
			if (processInstanceIds != null && processInstanceIds.size() > 0) {
				query.processInstanceIds(processInstanceIds);
			} else {
				processInstanceIds = new java.util.concurrent.CopyOnWriteArrayList<Long>();
				processInstanceIds.add(0L);
				query.processInstanceIds(processInstanceIds);
			}
		} else if (StringUtils.equals(workedProcessFlag, "END")) {
			query.setStatus(50);
			query.setWfStatus(9999);
			List<Long> processInstanceIds = container
					.getFinishedProcessInstanceIds(processName,
							user.getActorId());
			if (processInstanceIds != null && processInstanceIds.size() > 0) {
				// query.processInstanceIds(processInstanceIds);
			} else {
				processInstanceIds = new java.util.concurrent.CopyOnWriteArrayList<Long>();
				processInstanceIds.add(0L);
				query.processInstanceIds(processInstanceIds);
			}
		} else if (StringUtils.equals(workedProcessFlag, "FB")) {
			query.setStatus(-1);
			List<Long> processInstanceIds = container
					.getFinishedProcessInstanceIds(processName,
							user.getActorId());
			if (processInstanceIds != null && processInstanceIds.size() > 0) {
				// query.processInstanceIds(processInstanceIds);
			} else {
				processInstanceIds = new java.util.concurrent.CopyOnWriteArrayList<Long>();
				processInstanceIds.add(0L);
				query.processInstanceIds(processInstanceIds);
			}
		} else if (StringUtils.equals(workedProcessFlag, "WD")) {
			List<Long> processInstanceIds = container
					.getFinishedProcessInstanceIds(processName,
							user.getActorId());
			if (processInstanceIds != null && processInstanceIds.size() > 0) {
				// query.processInstanceIds(processInstanceIds);
			} else {
				processInstanceIds = new java.util.concurrent.CopyOnWriteArrayList<Long>();
				processInstanceIds.add(0L);
				query.processInstanceIds(processInstanceIds);
			}
		} else if (StringUtils.equals(workedProcessFlag, "DF")) {
			query.setWorkedProcessFlag(null);
			query.setStatus(0);
			// 如果不是系统管理员或信息审核员，只能看自己发布的信息
			if (!(loginContext.isSystemAdministrator() || loginContext
					.hasPermission("Auditor", "and"))) {
				query.setCreateBy(loginContext.getActorId());
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
		int total = publicInfoService.getPublicInfoCountByQueryCriteria(query);
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

			List<PublicInfo> list = publicInfoService
					.getPublicInfosByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (PublicInfo publicInfo : list) {
					JSONObject rowJSON = publicInfo.toJsonObject();
					rowJSON.put("id", publicInfo.getId());
					rowJSON.put("publicInfoId", publicInfo.getId());
					rowJSON.put("startIndex", ++start);
					Long nid = publicInfo.getNodeId();
					TreeModel tree = treeModelMap.get(nid);
					if (tree != null) {
						rowJSON.put("categoryName", tree.getName());
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

		String serviceKey = request.getParameter("serviceKey");
		if (StringUtils.isNotEmpty(serviceKey)) {
			TreeModel treeModel = treeModelService
					.getTreeModelByCode(serviceKey);
			request.setAttribute("treeModel", treeModel);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/cms/info/list", modelMap);
	}

	@RequestMapping("/props")
	public ModelAndView props(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String serviceKey = request.getParameter("serviceKey");
		Long nodeId = RequestUtils.getLong(request, "nodeId");

		if (StringUtils.isNotEmpty(serviceKey)) {
			TreeModel treeModel = treeModelService
					.getTreeModelByCode(serviceKey);
			request.setAttribute("treeModel", treeModel);
		}

		if (nodeId > 0) {
			TreeModel treeModel = treeModelService.getTreeModel(nodeId);
			request.setAttribute("treeModel", treeModel);
		}

		JbpmContext jbpmContext = null;
		GraphSession graphSession = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			if (jbpmContext != null) {
				graphSession = jbpmContext.getGraphSession();
				List<ProcessDefinition> processDefinitions = graphSession
						.findLatestProcessDefinitions();
				modelMap.put("processDefinitions", processDefinitions);
			}
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			Context.close(jbpmContext);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("publicInfo.props");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/cms/info/props", modelMap);
	}

	@ResponseBody
	@RequestMapping("/publish")
	public byte[] publish(HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		/**
		 * 只有系统管理员及信息审核员才能发布信息
		 */
		if (loginContext.isSystemAdministrator()
				|| loginContext.hasPermission("Auditor", "and")) {
			String id = request.getParameter("id");
			int publishFlag = RequestUtils.getInt(request, "publishFlag");
			PublicInfo publicInfo = null;
			try {
				if (StringUtils.isNotEmpty(id)) {
					publicInfo = publicInfoService.getPublicInfo(id);
					publicInfo.setPublishFlag(publishFlag);
					publicInfoService.save(publicInfo);
					return ResponseUtils.responseJsonResult(true);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			}
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("publicInfo.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/cms/info/query", modelMap);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String id = request.getParameter("id");
		PublicInfo publicInfo = new PublicInfo();
		boolean exists = false;
		try {
			if (StringUtils.isNotEmpty(id)) {
				publicInfo = publicInfoService.getPublicInfo(id);
				exists = true;
			}
			if (publicInfo == null) {
				publicInfo = new PublicInfo();
			}
			Tools.populate(publicInfo, params);

			publicInfo.setOriginalFlag(RequestUtils.getInt(request,
					"originalFlag"));
			publicInfo.setStartDate(RequestUtils.getDate(request, "startDate"));
			publicInfo.setTag(request.getParameter("tag"));
			publicInfo.setSubject(request.getParameter("subject"));
			publicInfo.setLink(request.getParameter("link"));
			publicInfo.setEndDate(RequestUtils.getDate(request, "endDate"));
			publicInfo.setRefererUrl(request.getParameter("refererUrl"));
			publicInfo.setAuthor(request.getParameter("author"));
			publicInfo.setName(request.getParameter("name"));
			publicInfo.setCommentFlag(RequestUtils.getInt(request,
					"commentFlag"));
			publicInfo.setUpdateBy(actorId);
			publicInfo.setKeywords(request.getParameter("keywords"));
			publicInfo.setContent(request.getParameter("content"));
			publicInfo.setUnitName(request.getParameter("unitName"));

			if (!exists) {
				publicInfo.setNodeId(RequestUtils.getLong(request, "nodeId"));
				publicInfo.setServiceKey(request.getParameter("serviceKey"));
				publicInfo.setCreateBy(actorId);
			}
			publicInfoService.save(publicInfo);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/savePublicInfo")
	public byte[] savePublicInfo(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String id = request.getParameter("id");
		PublicInfo publicInfo = new PublicInfo();
		boolean exists = false;
		try {
			if (StringUtils.isNotEmpty(id)) {
				publicInfo = publicInfoService.getPublicInfo(id);
				exists = true;
			}
			if (publicInfo == null) {
				publicInfo = new PublicInfo();
			}
			Tools.populate(publicInfo, params);

			publicInfo.setOriginalFlag(RequestUtils.getInt(request,
					"originalFlag"));
			publicInfo.setStartDate(RequestUtils.getDate(request, "startDate"));
			publicInfo.setTag(request.getParameter("tag"));
			publicInfo.setSubject(request.getParameter("subject"));
			publicInfo.setLink(request.getParameter("link"));
			publicInfo.setEndDate(RequestUtils.getDate(request, "endDate"));
			publicInfo.setRefererUrl(request.getParameter("refererUrl"));
			publicInfo.setAuthor(request.getParameter("author"));
			publicInfo.setName(request.getParameter("name"));
			publicInfo.setCommentFlag(RequestUtils.getInt(request,
					"commentFlag"));
			publicInfo.setUpdateBy(actorId);
			publicInfo.setKeywords(request.getParameter("keywords"));
			publicInfo.setContent(request.getParameter("content"));
			publicInfo.setUnitName(request.getParameter("unitName"));

			if (!exists) {
				publicInfo.setNodeId(RequestUtils.getLong(request, "nodeId"));
				publicInfo.setServiceKey(request.getParameter("serviceKey"));
				publicInfo.setCreateBy(actorId);
			}

			publicInfoService.save(publicInfo);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setBlobService(IBlobService blobService) {
		this.blobService = blobService;
	}

	@javax.annotation.Resource
	public void setPublicInfoService(PublicInfoService publicInfoService) {
		this.publicInfoService = publicInfoService;
	}

	@javax.annotation.Resource
	public void setSystemParamService(ISystemParamService systemParamService) {
		this.systemParamService = systemParamService;
	}

	@javax.annotation.Resource
	public void setTreeModelService(ITreeModelService treeModelService) {
		this.treeModelService = treeModelService;
	}

	@RequestMapping("/startProcess")
	@ResponseBody
	public byte[] startProcess(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		PublicInfo publicInfo = publicInfoService.getPublicInfo(request
				.getParameter("id"));
		if (publicInfo != null) {

			String serviceKey = publicInfo.getServiceKey();
			Long nodeId = publicInfo.getNodeId();

			TreeModel treeModel = null;
			String processName = null;

			if (nodeId > 0) {
				treeModel = treeModelService.getTreeModel(nodeId);
				request.setAttribute("treeModel", treeModel);
				SystemParam param = systemParamService.getSystemParam(
						serviceKey, treeModel.getCode(), "processName");
				if (param != null) {
					processName = param.getStringVal();
				}
			}

			if (StringUtils.isEmpty(processName)) {
				treeModel = treeModelService.getTreeModelByCode(serviceKey);
				request.setAttribute("treeModel", treeModel);
				SystemParam param = systemParamService.getSystemParam(
						serviceKey, treeModel.getCode(), "processName");
				if (param != null) {
					processName = param.getStringVal();
				}
			}

           if (StringUtils.isNotEmpty(processName)) {
				ProcessContext ctx = new ProcessContext();
				ctx.setRowId(publicInfo.getId());
				ctx.setActorId(actorId);
				ctx.setTitle(ViewProperties.getString("res_id")
						+ publicInfo.getId());
				ctx.setProcessName(processName);
				DataField dataField = new DataField();
				dataField.setName("tableName");
				dataField.setValue("CMS_PUBLICINFO");
				ctx.addDataField(dataField);
				try {
					Long processInstanceId = ProcessContainer.getContainer()
							.startProcess(ctx);
					if (processInstanceId != null && processInstanceId > 0) {
						return ResponseUtils.responseJsonResult(true);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error(ex);
				}
			} else {
				publicInfo.setPublishFlag(1);
				publicInfoService.save(publicInfo);
				return ResponseUtils.responseJsonResult(true);
			}
		}

		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		PublicInfo publicInfo = publicInfoService.getPublicInfo(request
				.getParameter("id"));

		publicInfo
				.setOriginalFlag(RequestUtils.getInt(request, "originalFlag"));
		publicInfo.setStartDate(RequestUtils.getDate(request, "startDate"));
		publicInfo.setServiceKey(request.getParameter("serviceKey"));
		publicInfo.setTag(request.getParameter("tag"));
		publicInfo.setSubject(request.getParameter("subject"));
		publicInfo.setLink(request.getParameter("link"));
		publicInfo.setEndDate(RequestUtils.getDate(request, "endDate"));
		publicInfo.setRefererUrl(request.getParameter("refererUrl"));
		publicInfo.setAuthor(request.getParameter("author"));
		publicInfo.setNodeId(RequestUtils.getLong(request, "nodeId"));
		publicInfo.setName(request.getParameter("name"));
		publicInfo.setCommentFlag(RequestUtils.getInt(request, "commentFlag"));
		publicInfo.setUpdateBy(actorId);
		publicInfo.setKeywords(request.getParameter("keywords"));
		publicInfo.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		publicInfo.setContent(request.getParameter("content"));
		publicInfo.setUnitName(request.getParameter("unitName"));

		publicInfoService.save(publicInfo);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		PublicInfo publicInfo = publicInfoService.getPublicInfo(request
				.getParameter("id"));
		request.setAttribute("publicInfo", publicInfo);

		JSONObject rowJSON = publicInfo.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		if (publicInfo.getProcessInstanceId() != null) {
			ProcessContainer container = ProcessContainer.getContainer();
			List<ActivityInstance> stepInstances = container
					.getActivityInstances(publicInfo.getProcessInstanceId());
			request.setAttribute("stepInstances", stepInstances);
			request.setAttribute("stateInstances", stepInstances);
		}

		List<DataFile> dataFiles = blobService.getBlobList(publicInfo.getId());
		request.setAttribute("dataFiles", dataFiles);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("publicInfo.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/cms/info/view");
	}

}
