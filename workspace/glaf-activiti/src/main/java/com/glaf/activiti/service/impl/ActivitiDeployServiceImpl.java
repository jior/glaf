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

package com.glaf.activiti.service.impl;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.activiti.service.ActivitiDeployService;

@Service("activitiDeployService")
@Transactional
public class ActivitiDeployServiceImpl implements ActivitiDeployService {

	private RepositoryService repositoryService;

	public Deployment addClasspathResource(String resource) {
		DeploymentBuilder builder = repositoryService.createDeployment();
		builder.addClasspathResource(resource);
		Deployment deployment = builder.deploy();
		return deployment;
	}

	public Deployment addInputStream(String resourceName,
			InputStream inputStream) {
		DeploymentBuilder builder = repositoryService.createDeployment();
		builder.addInputStream(resourceName, inputStream);
		Deployment deployment = builder.deploy();
		return deployment;
	}

	public Deployment addZipInputStream(ZipInputStream zipInputStream) {
		DeploymentBuilder builder = repositoryService.createDeployment();
		builder.addZipInputStream(zipInputStream);
		Deployment deployment = builder.deploy();
		return deployment;
	}

	@javax.annotation.Resource
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

}