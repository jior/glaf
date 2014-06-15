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

package com.glaf.activiti.web.springmvc;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.activiti.service.ActivitiDeployQueryService;
import com.glaf.activiti.service.ActivitiDeployService;
import com.glaf.activiti.service.ActivitiProcessQueryService;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;

@Controller("/activiti/tree")
@RequestMapping("/activiti/tree")
public class ActivitiTreeController {

	protected final static Log logger = LogFactory
			.getLog(ActivitiTreeController.class);

	protected ActivitiDeployService activitiDeployService;

	protected ActivitiDeployQueryService activitiDeployQueryService;

	protected ActivitiProcessQueryService activitiProcessQueryService;

	@ResponseBody
	@RequestMapping("/historyProcessInstances")
	public byte[] historyProcessInstances(HttpServletRequest request) {
		ObjectNode responseJSON = new ObjectMapper().createObjectNode();
		ArrayNode arrayJSON = new ObjectMapper().createArrayNode();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.put("orderByProcessInstanceStartTime", "1");
		params.put("orderDesc", "1");

		logger.debug("params:" + params);
		int pageNo = ParamUtils.getInt(params, "page");
		int limit = ParamUtils.getInt(params, "rows");
		if (limit <= 0) {
			limit = 15;
		}
		if (pageNo <= 0) {
			pageNo = 1;
		}
		int start = (pageNo - 1) * limit;
		long total = activitiProcessQueryService
				.getHistoricProcessInstanceCount(params);
		responseJSON.put("start", start);
		responseJSON.put("limit", limit);
		if (total > 0) {
			responseJSON.put("total", total);
			List<HistoricProcessInstance> list = activitiProcessQueryService
					.getHistoricProcessInstances(start, limit, params);
			if (list != null && !list.isEmpty()) {
				for (HistoricProcessInstance processInstance : list) {
					ObjectNode row = new ObjectMapper().createObjectNode();
					row.put("sortNo", ++start);
					row.put("id", processInstance.getId());
					row.put("processInstanceId", processInstance.getId());
					row.put("processDefinitionId",
							processInstance.getProcessDefinitionId());
					row.put("businessKey", processInstance.getBusinessKey());
					row.put("startTime", DateUtils.getDateTime(processInstance
							.getStartTime()));
					row.put("endTime",
							DateUtils.getDateTime(processInstance.getEndTime()));
					row.put("startUserId", processInstance.getStartUserId());
					row.put("startDate",
							DateUtils.getDate(processInstance.getStartTime()));
					row.put("endDate",
							DateUtils.getDate(processInstance.getEndTime()));
					row.put("startDateTime", DateUtils
							.getDateTime(processInstance.getStartTime()));
					row.put("endDateTime",
							DateUtils.getDateTime(processInstance.getEndTime()));
					row.put("durationInMillis",
							processInstance.getDurationInMillis());
					row.put("processDefinitionId",
							processInstance.getProcessDefinitionId());
					arrayJSON.add(row);
				}
				responseJSON.set("rows", arrayJSON);
			}
		} else {
			responseJSON.set("rows", arrayJSON);
		}
		try {
			// logger.debug(responseJSON.toString());
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}

	@ResponseBody
	@RequestMapping("/json")
	public byte[] json(HttpServletRequest request) {
		ArrayNode responseJSON = new ObjectMapper().createArrayNode();
		List<ProcessDefinition> processDefinitions = activitiProcessQueryService
				.getAllLatestProcessDefinitions();
		if (processDefinitions != null && !processDefinitions.isEmpty()) {
			for (ProcessDefinition processDefinition : processDefinitions) {
				ObjectNode row = new ObjectMapper().createObjectNode();
				row.put("category", processDefinition.getCategory());
				row.put("deploymentId", processDefinition.getDeploymentId());
				row.put("description", processDefinition.getDescription());
				row.put("id", processDefinition.getId());
				row.put("key", processDefinition.getKey());
				row.put("name", processDefinition.getName());
				row.put("text", processDefinition.getName());
				row.put("version", processDefinition.getVersion());
				row.put("leaf", Boolean.valueOf(false));
				row.put("iconSkin", "process_folder");
				row.put("nlevel", 0);
				responseJSON.add(row);
				List<ProcessDefinition> list = activitiProcessQueryService
						.getProcessDefinitions(processDefinition.getKey());
				if (list != null && !list.isEmpty()) {
					ArrayNode arrayJSON = new ObjectMapper().createArrayNode();
					for (ProcessDefinition pd : list) {
						ObjectNode o = new ObjectMapper().createObjectNode();
						o.put("category", pd.getCategory());
						o.put("deploymentId", pd.getDeploymentId());
						o.put("description", pd.getDescription());
						o.put("id", pd.getId());
						o.put("key", pd.getKey());
						o.put("name", pd.getName() + " V" + pd.getVersion());
						o.put("text", pd.getName() + " V" + pd.getVersion());
						o.put("version", pd.getVersion());
						o.put("leaf", Boolean.valueOf(true));
						o.put("iconSkin", "process_leaf");
						o.put("nlevel", 1);
						arrayJSON.add(o);
					}
					row.set("children", arrayJSON);
				}
			}
		}
		try {
			// logger.debug(responseJSON.toString());
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}

	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/main")
	public ModelAndView main(HttpServletRequest request, Model model) {
		RequestUtils.setRequestParameterToAttribute(request);
		return new ModelAndView("/activiti/tree/main");
	}

	@ResponseBody
	@RequestMapping("/processInstances")
	public byte[] processInstances(HttpServletRequest request) {
		ObjectNode responseJSON = new ObjectMapper().createObjectNode();
		ArrayNode arrayJSON = new ObjectMapper().createArrayNode();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug("params:" + params);
		int pageNo = ParamUtils.getInt(params, "page");
		int limit = ParamUtils.getInt(params, "rows");
		if (limit <= 0) {
			limit = 15;
		}
		if (pageNo <= 0) {
			pageNo = 1;
		}
		int start = (pageNo - 1) * limit;
		long total = activitiProcessQueryService
				.getProcessInstanceCount(params);
		responseJSON.put("start", start);
		responseJSON.put("limit", limit);
		if (total > 0) {
			responseJSON.put("total", total);
			List<ProcessInstance> list = activitiProcessQueryService
					.getProcessInstances(start, limit, params);
			if (list != null && !list.isEmpty()) {
				for (ProcessInstance processInstance : list) {
					ObjectNode row = new ObjectMapper().createObjectNode();
					row.put("sortNo", ++start);
					row.put("id", processInstance.getId());
					row.put("processInstanceId", processInstance.getId());
					row.put("businessKey", processInstance.getBusinessKey());
					row.put("isEnded", processInstance.isEnded());
					row.put("processDefinitionId",
							processInstance.getProcessDefinitionId());
					arrayJSON.add(row);
				}
				responseJSON.set("rows", arrayJSON);
			}
		} else {
			responseJSON.set("rows", arrayJSON);
		}
		try {
			// logger.debug(responseJSON.toString());
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}

	@javax.annotation.Resource
	public void setActivitiDeployQueryService(
			ActivitiDeployQueryService activitiDeployQueryService) {
		this.activitiDeployQueryService = activitiDeployQueryService;
	}

	@javax.annotation.Resource
	public void setActivitiDeployService(
			ActivitiDeployService activitiDeployService) {
		this.activitiDeployService = activitiDeployService;
	}

	@javax.annotation.Resource
	public void setActivitiProcessQueryService(
			ActivitiProcessQueryService activitiProcessQueryService) {
		this.activitiProcessQueryService = activitiProcessQueryService;
	}

}