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

package com.glaf.jbpm.web.rest;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.LogUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.ProcessContext;
import com.glaf.jbpm.datafield.DataField;
import com.glaf.jbpm.model.TaskItem;

@Controller("/rs/jbpm")
@Path("/rs/jbpm")
public class MxJbpmResource {
	protected final static Log logger = LogFactory.getLog(MxJbpmResource.class);

	@GET
	@POST
	@Path("startProcess")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	@ResponseBody
	public byte[] startProcess(@Context HttpServletRequest request) {
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		Map<String, Object> jsonResultMap = new java.util.HashMap<String, Object>();
		logger.debug(paramMap);
		String json = RequestUtils.getStringValue(request, "json");
		String rowId = RequestUtils.getStringValue(request, "rowId");
		String processName = RequestUtils
				.getStringValue(request, "processName");
		String actorId = RequestUtils.getActorId(request);
		ProcessContainer container = ProcessContainer.getContainer();
		Long processInstanceId = null;

		if (StringUtils.isNotEmpty(rowId)
				&& StringUtils.isNotEmpty(processName)
				&& StringUtils.isNotEmpty(actorId)) {
			ProcessContext ctx = new ProcessContext();
			ctx.setRowId(rowId);
			ctx.setActorId(actorId);
			ctx.setTitle("单据编号：" + rowId);
			ctx.setProcessName(processName);

			/**
			 * 流程控制参数为json格式的字符串.<br>
			 * 例如：{money:100000,day:5,pass:true,deptId:"123", roleId:"R001"}
			 */
			if (StringUtils.isNotEmpty(json)) {
				Map<String, Object> jsonMap = JsonUtils.decode(json);
				if (jsonMap != null && jsonMap.size() > 0) {
					Set<Entry<String, Object>> entrySet = jsonMap.entrySet();
					for (Entry<String, Object> entry : entrySet) {
						String name = entry.getKey();
						Object value = entry.getValue();
						if (name != null && value != null) {
							if (name != null && value != null) {
								DataField dataField = new DataField();
								dataField.setName(name);
								dataField.setValue(value);
								ctx.addDataField(dataField);
							}
						}
					}
				}
			}

			try {
				processInstanceId = container.startProcess(ctx);
				if (processInstanceId != null) {
					jsonResultMap.put("statusCode", 200);
					jsonResultMap.put("success", "true");
					jsonResultMap.put("data",
							ViewProperties.getString("res_submit_ok"));
					jsonResultMap.put("message",
							ViewProperties.getString("res_submit_ok"));
				}
				logger.debug("processInstanceId:" + processInstanceId);
			} catch (Exception ex) {
				jsonResultMap.put("statusCode", 500);
				jsonResultMap.put("success", "false");
				jsonResultMap.put("data",
						ViewProperties.getString("res_submit_error"));
				jsonResultMap.put("message",
						ViewProperties.getString("res_submit_error"));
				if (LogUtils.isDebug()) {
					ex.printStackTrace();
					logger.debug(ex);
				}
			}
		}

		JSONObject jsonObject = new JSONObject(jsonResultMap);

		try {
			return jsonObject.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return jsonObject.toString().getBytes();
		}
	}

	@GET
	@POST
	@Path("completeTask")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	@ResponseBody
	public byte[] completeTask(@Context HttpServletRequest request) {
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		Map<String, Object> jsonResultMap = new java.util.HashMap<String, Object>();
		logger.debug(paramMap);
		String json = RequestUtils.getStringValue(request, "json");
		Long processInstanceId = ParamUtils.getLong(paramMap,
				"processInstanceId");
		Long taskInstanceId = ParamUtils.getLong(paramMap, "taskInstanceId");
		String transitionName = RequestUtils.getStringValue(request,
				"transitionName");
		String jumpToNode = RequestUtils.getStringValue(request, "jumpToNode");
		String isAgree = RequestUtils.getStringValue(request, "isAgree");
		String opinion = RequestUtils.getStringValue(request, "opinion");
		logger.debug("json:" + json);
		logger.debug("processInstanceId:" + processInstanceId);
		logger.debug("taskInstanceId:" + taskInstanceId);
		logger.debug("transitionName:" + transitionName);
		logger.debug("jumpToNode:" + jumpToNode);
		logger.debug("isAgree:" + isAgree);
		logger.debug("opinion:" + opinion);
		String actorId = RequestUtils.getActorId(request);
		ProcessContainer container = ProcessContainer.getContainer();
		boolean canSubmit = false;
		boolean isOK = false;
		TaskItem taskItem = null;
		if ("0".equals(isAgree)) {
			isAgree = "false";
		} else if ("1".equals(isAgree)) {
			isAgree = "true";
		}

		if (taskInstanceId != null && StringUtils.isNotEmpty(actorId)) {
			/**
			 * 根据任务实例编号和用户编号获取待办任务
			 */
			taskItem = container.getTaskItem(taskInstanceId, actorId);
			if (taskItem != null) {
				if (processInstanceId != null) {
					canSubmit = true;
				}
			}
		} else if (processInstanceId != null && StringUtils.isNotEmpty(actorId)) {
			/**
			 * 根据流程实例编号和用户编号获取待办任务
			 */
			taskItem = container.getMinTaskItem(actorId, processInstanceId);
			if (taskItem != null) {
				canSubmit = true;

			}
		}

		/**
		 * 如果存在待办任务并且可以提交审核
		 */
		if (canSubmit && taskItem != null) {
			ProcessContext ctx = new ProcessContext();
			/**
			 * 流程控制参数为json格式的字符串.<br>
			 * 例如：{money:100000,day:5,pass:true,deptId:"123", roleId:"R001"}
			 */
			if (StringUtils.isNotEmpty(json)) {
				Map<String, Object> jsonMap = JsonUtils.decode(json);
				if (jsonMap != null && jsonMap.size() > 0) {
					Set<Entry<String, Object>> entrySet = jsonMap.entrySet();
					for (Entry<String, Object> entry : entrySet) {
						String name = entry.getKey();
						Object value = entry.getValue();
						if (name != null && value != null) {
							DataField dataField = new DataField();
							dataField.setName(name);
							dataField.setValue(value);
							ctx.addDataField(dataField);
						}
					}
				}
			}

			DataField dataField = new DataField();
			dataField.setName("isAgree");
			dataField.setValue(isAgree);
			ctx.addDataField(dataField);

			ctx.setActorId(actorId);
			ctx.setOpinion(opinion);
			ctx.setProcessInstanceId(taskItem.getProcessInstanceId());
			ctx.setTaskInstanceId(taskItem.getTaskInstanceId());
			ctx.setJumpToNode(jumpToNode);
			ctx.setTransitionName(transitionName);

			try {
				isOK = container.completeTask(ctx);
				request.setAttribute("isOK", Boolean.valueOf(isOK));
				if (isOK) {
					jsonResultMap.put("statusCode", 200);
					jsonResultMap.put("success", "true");
					jsonResultMap.put("data",
							ViewProperties.getString("res_submit_ok"));
					jsonResultMap.put("message",
							ViewProperties.getString("res_submit_ok"));
				} else {
					jsonResultMap.put("statusCode", 500);
					jsonResultMap.put("success", "false");
					jsonResultMap.put("data",
							ViewProperties.getString("res_submit_error"));
					jsonResultMap.put("message",
							ViewProperties.getString("res_submit_error"));
				}
			} catch (Exception ex) {
				jsonResultMap.put("statusCode", 500);
				jsonResultMap.put("success", "false");
				jsonResultMap.put("data",
						ViewProperties.getString("res_submit_error"));
				jsonResultMap.put("message",
						ViewProperties.getString("res_submit_error"));
				if (LogUtils.isDebug()) {
					ex.printStackTrace();
					logger.debug(ex);
				}
			}
		}
		JSONObject jsonObject = new JSONObject(jsonResultMap);

		try {
			return jsonObject.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return jsonObject.toString().getBytes();
		}
	}

}