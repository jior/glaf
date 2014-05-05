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
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.glaf.activiti.service.ActivitiDeployQueryService;
import com.glaf.activiti.service.ActivitiDeployService;
import com.glaf.activiti.service.ActivitiProcessQueryService;
import com.glaf.activiti.service.ActivitiProcessService;
import com.glaf.activiti.service.ActivitiTaskQueryService;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.util.Paging;
import com.glaf.core.util.RequestUtils;

@Controller("/activiti/process")
@RequestMapping("/activiti/process")
public class ActivitiProcessController {
	protected final static Log logger = LogFactory
			.getLog(ActivitiProcessController.class);

	protected ActivitiDeployService activitiDeployService;

	protected ActivitiDeployQueryService activitiDeployQueryService;

	protected ActivitiProcessService activitiProcessService;

	protected ActivitiProcessQueryService activitiProcessQueryService;

	protected ActivitiTaskQueryService activitiTaskQueryService;

	@RequestMapping("/definition")
	public String processDefinition(
			@RequestParam("processDefinitionId") String processDefinitionId,
			Model model) {
		ProcessDefinition processDefinition = activitiProcessQueryService
				.getProcessDefinition(processDefinitionId);
		model.addAttribute("processDefinition", processDefinition);

		String view = ViewProperties.getString("activiti.process.definition");
		if (StringUtils.isNotEmpty(view)) {
			return view;
		}

		return "/activiti/process/definition";
	}

	@RequestMapping("/processDefinitionJson")
	@ResponseBody
	public byte[] processDefinitionJson(HttpServletRequest request, Model model)
			throws IOException {
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		int start = RequestUtils.getInt(request, "startIndex", 0);
		int limit = RequestUtils.getInt(request, "results",
				Paging.DEFAULT_PAGE_SIZE);

		long total = activitiProcessQueryService
				.getProcessDefinitionCount(paramMap);
		List<ProcessDefinition> rows = activitiProcessQueryService
				.getProcessDefinitions(start, limit, paramMap);

		List<Object> list = new java.util.ArrayList<Object>();

		for (ProcessDefinition processDefinition : rows) {
			Map<String, Object> rowMap = new java.util.HashMap<String, Object>();
			rowMap.put("id", processDefinition.getId());
			rowMap.put("processDefinitionId", processDefinition.getId());
			rowMap.put("deploymentId", processDefinition.getDeploymentId());
			rowMap.put("key", processDefinition.getKey());
			rowMap.put("name", processDefinition.getName());
			rowMap.put("category", processDefinition.getCategory());
			rowMap.put("version", processDefinition.getVersion());
			rowMap.put("resourceName", processDefinition.getResourceName());
			rowMap.put("diagramResourceName",
					processDefinition.getDiagramResourceName());
			rowMap.put("hasStartFormKey", processDefinition.hasStartFormKey());

			list.add(rowMap);
		}

		Map<String, Object> pageInfo = new java.util.HashMap<String, Object>();
		// 当前页数设置
		pageInfo.put("startIndex", start);

		// 每页记录数
		pageInfo.put("pageSize", limit);

		// 总数据量设置
		pageInfo.put("totalRecords", total);
		pageInfo.put("records", list);

		JSONObject object = new JSONObject(pageInfo);

		return object.toString().getBytes("UTF-8");
	}

	@RequestMapping("/processDefinitions")
	public String processDefinitions(HttpServletRequest request, Model model) {
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		Set<Entry<String, Object>> entrySet = paramMap.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String name = entry.getKey();
			Object value = entry.getValue();
			if (value != null) {
				model.addAttribute(name, value);
			}
		}

		List<Deployment> deployments = activitiDeployQueryService
				.getDeployments();
		model.addAttribute("deployments", deployments);

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return jx_view;
		}

		String view = ViewProperties
				.getString("activiti.process.processDefinitions");
		if (StringUtils.isNotEmpty(view)) {
			return view;
		}

		return "/activiti/process/processDefinitions";
	}

	@RequestMapping("/processInstanceJson")
	@ResponseBody
	public byte[] processInstanceJson(HttpServletRequest request, Model model)
			throws IOException {
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		int start = RequestUtils.getInt(request, "startIndex", 0);
		int limit = RequestUtils.getInt(request, "results",
				Paging.DEFAULT_PAGE_SIZE);

		String deploymentId = request.getParameter("deploymentId");
		String processDefinitionId = request
				.getParameter("processDefinitionId");
		ProcessDefinition processDefinition = null;
		if (StringUtils.isNotEmpty(deploymentId)) {
			processDefinition = activitiProcessQueryService
					.getProcessDefinitionByDeploymentId(deploymentId);
		} else if (StringUtils.isNotEmpty(processDefinitionId)) {
			processDefinition = activitiProcessQueryService
					.getProcessDefinition(processDefinitionId);
		}
		if (processDefinition != null) {
			model.addAttribute("processDefinition", processDefinition);
			paramMap.put("deploymentId", processDefinition.getDeploymentId());
			paramMap.put("processDefinitionId", processDefinition.getId());
		}

		logger.debug("paramMap:" + paramMap);

		long total = activitiProcessQueryService
				.getProcessInstanceCount(paramMap);
		List<ProcessInstance> rows = activitiProcessQueryService
				.getProcessInstances(start, limit, paramMap);

		List<Object> list = new java.util.ArrayList<Object>();

		for (ProcessInstance processInstance : rows) {
			Map<String, Object> rowMap = new java.util.HashMap<String, Object>();
			rowMap.put("id", processInstance.getId());
			rowMap.put("processInstanceId", processInstance.getId());
			rowMap.put("businessKey", processInstance.getBusinessKey());
			rowMap.put("isEnded", processInstance.isEnded());
			rowMap.put("processDefinitionId",
					processInstance.getProcessDefinitionId());
			list.add(rowMap);
		}

		Map<String, Object> pageInfo = new java.util.HashMap<String, Object>();
		// 当前页数设置
		pageInfo.put("startIndex", start);

		// 每页记录数
		pageInfo.put("pageSize", limit);

		// 总数据量设置
		pageInfo.put("totalRecords", total);
		pageInfo.put("records", list);

		JSONObject object = new JSONObject(pageInfo);

		return object.toString().getBytes("UTF-8");
	}

	@RequestMapping("/processInstances")
	public String processInstances(HttpServletRequest request, Model model) {
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);

		Set<Entry<String, Object>> entrySet = paramMap.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String name = entry.getKey();
			Object value = entry.getValue();
			if (value != null) {
				model.addAttribute(name, value);
			}
		}

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return jx_view;
		}

		String view = ViewProperties
				.getString("activiti.process.processInstances");
		if (StringUtils.isNotEmpty(view)) {
			return view;
		}

		return "/activiti/process/processInstances";
	}

	@javax.annotation.Resource
	public void setActivitiDeployService(
			ActivitiDeployService activitiDeployService) {
		this.activitiDeployService = activitiDeployService;
	}

	@javax.annotation.Resource
	public void setActivitiDeployQueryService(
			ActivitiDeployQueryService activitiDeployQueryService) {
		this.activitiDeployQueryService = activitiDeployQueryService;
	}

	@javax.annotation.Resource
	public void setActivitiProcessQueryService(
			ActivitiProcessQueryService activitiProcessQueryService) {
		this.activitiProcessQueryService = activitiProcessQueryService;
	}

	@javax.annotation.Resource
	public void setActivitiProcessService(
			ActivitiProcessService activitiProcessService) {
		this.activitiProcessService = activitiProcessService;
	}

	@javax.annotation.Resource
	public void setActivitiTaskQueryService(
			ActivitiTaskQueryService activitiTaskQueryService) {
		this.activitiTaskQueryService = activitiTaskQueryService;
	}

}