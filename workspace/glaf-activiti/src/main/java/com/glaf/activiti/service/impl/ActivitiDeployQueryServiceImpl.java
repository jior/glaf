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
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.activiti.service.ActivitiDeployQueryService;

@Service("activitiDeployQueryService")
@Transactional(readOnly = true)
public class ActivitiDeployQueryServiceImpl implements
		ActivitiDeployQueryService {

	private RepositoryService repositoryService;

	public Deployment getDeployment(String deploymentId) {
		DeploymentQuery query = repositoryService.createDeploymentQuery();
		query.deploymentId(deploymentId);
		return query.singleResult();
	}

	public long getDeploymentCount(DeploymentQuery query) {
		return query.count();
	}

	public long getDeploymentCount(Map<String, Object> paramMap) {
		DeploymentQuery query = repositoryService.createDeploymentQuery();
		if (paramMap != null && paramMap.size() > 0) {
			if (paramMap.get("deploymentId") != null) {
				Object deploymentId = paramMap.get("deploymentId");
				query.deploymentId(deploymentId.toString());
			}

			if (paramMap.get("name") != null) {
				Object name = paramMap.get("name");
				query.deploymentName(name.toString());
			}

			if (paramMap.get("nameLike") != null) {
				Object nameLike = paramMap.get("nameLike");
				query.deploymentNameLike(nameLike.toString());
			}
		}

		return query.count();
	}

	public List<String> getDeploymentResourceNames(String deploymentId) {
		return repositoryService.getDeploymentResourceNames(deploymentId);
	}

	public List<Deployment> getDeployments() {
		DeploymentQuery query = repositoryService.createDeploymentQuery();
		return query.list();
	}

	public List<Deployment> getDeployments(int firstResult, int maxResults,
			DeploymentQuery query) {
		if (firstResult >= 0 && maxResults > 0) {
			query.listPage(firstResult, maxResults);
		}
		return query.list();
	}

	public List<Deployment> getDeployments(int firstResult, int maxResults,
			Map<String, Object> paramMap) {
		DeploymentQuery query = repositoryService.createDeploymentQuery();
		if (paramMap != null && paramMap.size() > 0) {
			if (paramMap.get("deploymentId") != null) {
				Object deploymentId = paramMap.get("deploymentId");
				query.deploymentId(deploymentId.toString());
			}

			if (paramMap.get("id") != null) {
				Object id = paramMap.get("id");
				query.deploymentId(id.toString());
			}

			if (paramMap.get("name") != null) {
				Object name = paramMap.get("name");
				query.deploymentName(name.toString());
			}

			if (paramMap.get("nameLike") != null) {
				Object nameLike = paramMap.get("nameLike");
				query.deploymentNameLike(nameLike.toString());
			}

			if (paramMap.get("orderByDeploymentId") != null) {
				query.orderByDeploymentId();
			}

			if (paramMap.get("orderByDeploymentName") != null) {
				query.orderByDeploymentName();
			}

			if (paramMap.get("orderByDeploymenTime") != null) {
				query.orderByDeploymenTime();
			}

			if (paramMap.get("orderById") != null) {
				query.orderByDeploymentId();
			}

			if (paramMap.get("orderByName") != null) {
				query.orderByDeploymentName();
			}

			if (paramMap.get("orderByTime") != null) {
				query.orderByDeploymenTime();
			}

			if (paramMap.get("orderAsc") != null) {
				query.asc();
			}

			if (paramMap.get("orderDesc") != null) {
				query.desc();
			}
		}
		if (firstResult >= 0 && maxResults > 0) {
			query.listPage(firstResult, maxResults);
		}
		return query.list();
	}

	public InputStream getResourceAsStream(String deploymentId,
			String resourceName) {
		return repositoryService
				.getResourceAsStream(deploymentId, resourceName);
	}

	@javax.annotation.Resource
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

}