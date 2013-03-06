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

package com.glaf.activiti.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;

public interface ActivitiDeployQueryService {

	Deployment getDeployment(String deploymentId);

	long getDeploymentCount(Map<String, Object> paramMap);

	long getDeploymentCount(DeploymentQuery query);

	List<String> getDeploymentResourceNames(String deploymentId);

	List<Deployment> getDeployments();

	List<Deployment> getDeployments(int firstResult, int maxResults,
			Map<String, Object> paramMap);

	List<Deployment> getDeployments(int firstResult, int maxResults,
			DeploymentQuery query);

	InputStream getResourceAsStream(String deploymentId, String resourceName);

}