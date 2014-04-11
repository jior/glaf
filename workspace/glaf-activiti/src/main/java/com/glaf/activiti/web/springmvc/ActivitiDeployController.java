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
import java.util.zip.ZipInputStream;

import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.glaf.activiti.service.ActivitiDeployQueryService;
import com.glaf.activiti.service.ActivitiDeployService;
import com.glaf.activiti.service.ActivitiProcessQueryService;
import com.glaf.activiti.util.ProcessUtils;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.IOUtils;

@Controller("/activiti/deploy")
@RequestMapping("/activiti/deploy")
public class ActivitiDeployController {

	protected final static Log logger = LogFactory
			.getLog(ActivitiDeployController.class);

	protected ActivitiDeployService activitiDeployService;

	protected ActivitiDeployQueryService activitiDeployQueryService;

	protected ActivitiProcessQueryService activitiProcessQueryService;

	/**
	 * 
	 * @param model
	 * @param mFile
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(Model model,
			@RequestParam("file") MultipartFile mFile) throws IOException {
		if (!mFile.isEmpty()) {
			String deploymentId = null;
			ProcessDefinition processDefinition = null;
			if (mFile.getOriginalFilename().endsWith(".zip")
					|| mFile.getOriginalFilename().endsWith(".jar")) {
				ZipInputStream zipInputStream = null;
				try {
					zipInputStream = new ZipInputStream(mFile.getInputStream());
					deploymentId = activitiDeployService.addZipInputStream(
							zipInputStream).getId();
				} finally {
					IOUtils.closeStream(zipInputStream);
				}
			} else {
				String resourceName = FileUtils.getFilename(mFile
						.getOriginalFilename());
				deploymentId = activitiDeployService.addInputStream(
						resourceName, mFile.getInputStream()).getId();
			}
			if (StringUtils.isNotEmpty(deploymentId)) {
				logger.debug("deploymentId:" + deploymentId);
				processDefinition = activitiProcessQueryService
						.getProcessDefinitionByDeploymentId(deploymentId);
				if (processDefinition != null) {
					model.addAttribute("processDefinition", processDefinition);
					model.addAttribute("deploymentId",
							processDefinition.getDeploymentId());
					String resourceName = processDefinition
							.getDiagramResourceName();
					if (resourceName != null) {
						ProcessUtils.saveProcessImageToFileSystem(processDefinition);
						String path = "/deploy/bpmn/"+ProcessUtils.getImagePath(processDefinition);
						model.addAttribute("path", path);
						return "/activiti/deploy/showImage";
					}
				}
			}
		}

		String view = ViewProperties.getString("activiti.deploy");
		if (StringUtils.isNotEmpty(view)) {
			return view;
		}

		return "/activiti/deploy/deploy";
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

	@RequestMapping(method = RequestMethod.GET)
	public String showDeploy(Model model) {
		String view = ViewProperties.getString("activiti.showDeploy");
		if (StringUtils.isNotEmpty(view)) {
			return view;
		}
		return "/activiti/deploy/showDeploy";
	}

}