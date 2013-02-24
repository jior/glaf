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
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glaf.activiti.service.ActivitiDeployQueryService;
import com.glaf.activiti.service.ActivitiDeployService;
import com.glaf.activiti.service.ActivitiProcessQueryService;
import com.glaf.activiti.service.ActivitiTaskQueryService;

@Controller("/activiti/image")
@RequestMapping("/activiti/image")
public class ActivitiProcessImageController {
	protected final static Log logger = LogFactory
			.getLog(ActivitiProcessImageController.class);
	@Autowired
	protected ActivitiDeployService activitiDeployService;

	@Autowired
	protected ActivitiDeployQueryService activitiDeployQueryService;

	@Autowired
	protected ActivitiProcessQueryService activitiProcessQueryService;

	@Autowired
	protected ActivitiTaskQueryService activitiTaskQueryService;

	@RequestMapping("/image")
	@ResponseBody
	public byte[] image(@RequestParam("deploymentId") String deploymentId)
			throws IOException {
		ProcessDefinition processDefinition = null;
		logger.debug("deploymentId:" + deploymentId);
		if (StringUtils.isNotEmpty(deploymentId)) {
			processDefinition = activitiProcessQueryService
					.getProcessDefinitionByDeploymentId(deploymentId);
		}
		if (processDefinition != null) {
			String resourceName = processDefinition.getDiagramResourceName();
			if (resourceName != null) {
				InputStream inputStream = activitiDeployQueryService
						.getResourceAsStream(
								processDefinition.getDeploymentId(),
								resourceName);
				if (inputStream != null) {
					byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);
					return bytes;
				}
			}
		}

		return null;
	}

	public void setActivitiDeployService(
			ActivitiDeployService activitiDeployService) {
		this.activitiDeployService = activitiDeployService;
	}

	public void setActivitiDeployQueryService(
			ActivitiDeployQueryService activitiDeployQueryService) {
		this.activitiDeployQueryService = activitiDeployQueryService;
	}

	public void setActivitiProcessQueryService(
			ActivitiProcessQueryService activitiProcessQueryService) {
		this.activitiProcessQueryService = activitiProcessQueryService;
	}

	public void setActivitiTaskQueryService(
			ActivitiTaskQueryService activitiTaskQueryService) {
		this.activitiTaskQueryService = activitiTaskQueryService;
	}

	@RequestMapping
	@ResponseBody
	public byte[] showImage(HttpServletRequest request) throws IOException {
		String processDefinitionId = request
				.getParameter("processDefinitionId");
		String processInstanceId = request.getParameter("processInstanceId");
		ProcessDefinition processDefinition = null;
		logger.debug("processDefinitionId:" + processDefinitionId);
		if (StringUtils.isNotEmpty(processDefinitionId)) {
			processDefinition = activitiProcessQueryService
					.getProcessDefinition(processDefinitionId);
		} else if (StringUtils.isNotEmpty(processInstanceId)) {
			processDefinition = activitiProcessQueryService
					.getProcessDefinitionByProcessInstanceId(processInstanceId);
		}
		if (processDefinition != null) {
			String resourceName = processDefinition.getDiagramResourceName();
			if (resourceName != null) {
				InputStream inputStream = activitiDeployQueryService
						.getResourceAsStream(
								processDefinition.getDeploymentId(),
								resourceName);
				if (inputStream != null) {
					byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);
					return bytes;
				}
			}
		}

		return null;
	}
}