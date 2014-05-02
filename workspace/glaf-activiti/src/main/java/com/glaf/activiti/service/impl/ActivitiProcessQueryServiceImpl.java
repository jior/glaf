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

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricDetailQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.activiti.model.ActivityInfo;
import com.glaf.activiti.model.ProcessDefinitionInfo;
import com.glaf.activiti.model.ProcessInstanceInfo;
import com.glaf.activiti.model.UserTask;
import com.glaf.activiti.service.ActivitiProcessQueryService;
import com.glaf.activiti.xml.BpmnXmlReader;

@Service("activitiProcessQueryService")
@Transactional(readOnly = true)
public class ActivitiProcessQueryServiceImpl implements
		ActivitiProcessQueryService {
	protected static final Log logger = LogFactory
			.getLog(ActivitiProcessQueryService.class);

	protected RepositoryService repositoryService;

	protected RuntimeService runtimeService;

	protected TaskService taskService;

	protected HistoryService historyService;

	public List<ProcessDefinition> getAllLatestProcessDefinitions() {
		ProcessDefinitionQuery query = repositoryService
				.createProcessDefinitionQuery();
		query.latestVersion();
		return query.list();
	}

	public HistoricActivityInstance getHistoricActivityInstance(
			String activityInstanceId) {
		HistoricActivityInstanceQuery query = historyService
				.createHistoricActivityInstanceQuery();
		query.activityInstanceId(activityInstanceId);
		return query.singleResult();
	}

	public long getHistoricActivityInstanceCount(
			HistoricActivityInstanceQuery query) {
		return query.count();
	}

	public long getHistoricActivityInstanceCount(Map<String, Object> paramMap) {
		HistoricActivityInstanceQuery query = historyService
				.createHistoricActivityInstanceQuery();
		if (paramMap != null && paramMap.size() > 0) {
			if (paramMap.get("activityInstanceId") != null) {
				Object activityInstanceId = paramMap.get("activityInstanceId");
				query.activityInstanceId(activityInstanceId.toString());
			}

			if (paramMap.get("processInstanceId") != null) {
				Object processInstanceId = paramMap.get("processInstanceId");
				query.processInstanceId(processInstanceId.toString());
			}

			if (paramMap.get("processDefinitionId") != null) {
				Object processDefinitionId = paramMap
						.get("processDefinitionId");
				query.processDefinitionId(processDefinitionId.toString());
			}

			if (paramMap.get("executionId") != null) {
				Object executionId = paramMap.get("executionId");
				query.executionId(executionId.toString());
			}

			if (paramMap.get("activityId") != null) {
				Object activityId = paramMap.get("activityId");
				query.activityId(activityId.toString());
			}

			if (paramMap.get("activityName") != null) {
				Object activityName = paramMap.get("activityName");
				query.activityName(activityName.toString());
			}

			if (paramMap.get("activityType") != null) {
				Object activityType = paramMap.get("activityType");
				query.activityType(activityType.toString());
			}

			if (paramMap.get("assignee") != null) {
				Object assignee = paramMap.get("assignee");
				query.taskAssignee(assignee.toString());
			}

			if (paramMap.get("finished") != null) {
				query.finished();
			}

			if (paramMap.get("unfinished") != null) {
				query.unfinished();
			}

		}

		return query.count();
	}

	public List<HistoricActivityInstance> getHistoricActivityInstances(
			int firstResult, int maxResults, HistoricActivityInstanceQuery query) {
		if (firstResult >= 0 && maxResults > 0) {
			return query.listPage(firstResult, maxResults);
		}

		return query.list();
	}

	public List<HistoricActivityInstance> getHistoricActivityInstances(
			int firstResult, int maxResults, Map<String, Object> paramMap) {
		HistoricActivityInstanceQuery query = historyService
				.createHistoricActivityInstanceQuery();
		if (paramMap != null && paramMap.size() > 0) {
			if (paramMap.get("processInstanceId") != null) {
				Object processInstanceId = paramMap.get("processInstanceId");
				query.processInstanceId(processInstanceId.toString());
			}

			if (paramMap.get("processDefinitionId") != null) {
				Object processDefinitionId = paramMap
						.get("processDefinitionId");
				query.processDefinitionId(processDefinitionId.toString());
			}

			if (paramMap.get("executionId") != null) {
				Object executionId = paramMap.get("executionId");
				query.executionId(executionId.toString());
			}

			if (paramMap.get("activityId") != null) {
				Object activityId = paramMap.get("activityId");
				query.activityId(activityId.toString());
			}

			if (paramMap.get("activityName") != null) {
				Object activityName = paramMap.get("activityName");
				query.activityName(activityName.toString());
			}

			if (paramMap.get("activityType") != null) {
				Object activityType = paramMap.get("activityType");
				query.activityType(activityType.toString());
			}

			if (paramMap.get("assignee") != null) {
				Object assignee = paramMap.get("assignee");
				query.taskAssignee(assignee.toString());
			}

			if (paramMap.get("orderByProcessInstanceId") != null) {
				query.orderByProcessInstanceId();
			}

			if (paramMap.get("orderByExecutionId") != null) {
				query.orderByExecutionId();
			}

			if (paramMap.get("orderByActivityId") != null) {
				query.orderByActivityId();
			}

			if (paramMap.get("orderByActivityName") != null) {
				query.orderByActivityName();
			}

			if (paramMap.get("orderByActivityType") != null) {
				query.orderByActivityType();
			}

			if (paramMap.get("orderByHistoricActivityInstanceStartTime") != null) {
				query.orderByHistoricActivityInstanceStartTime();
			}

			if (paramMap.get("orderByHistoricActivityInstanceEndTime") != null) {
				query.orderByHistoricActivityInstanceEndTime();
			}

			if (paramMap.get("orderByHistoricActivityInstanceDuration") != null) {
				query.orderByHistoricActivityInstanceDuration();
			}

			if (paramMap.get("orderAsc") != null) {
				query.asc();
			}

			if (paramMap.get("orderDesc") != null) {
				query.desc();
			}

		} else {
			query.orderByHistoricActivityInstanceStartTime();
			query.asc();
		}

		if (firstResult >= 0 && maxResults > 0) {
			return query.listPage(firstResult, maxResults);
		}

		return query.list();
	}

	public List<HistoricActivityInstance> getHistoricActivityInstances(
			String processInstanceId) {
		HistoricActivityInstanceQuery query = historyService
				.createHistoricActivityInstanceQuery();
		query.processInstanceId(processInstanceId);
		query.orderByHistoricActivityInstanceStartTime();
		query.asc();
		return query.list();
	}

	public long getHistoricDetailCount(HistoricDetailQuery query) {
		return query.count();
	}

	public long getHistoricDetailCount(Map<String, Object> paramMap) {
		HistoricDetailQuery query = historyService.createHistoricDetailQuery();
		if (paramMap != null && paramMap.size() > 0) {
			if (paramMap.get("processInstanceId") != null) {
				Object processInstanceId = paramMap.get("processInstanceId");
				query.processInstanceId(processInstanceId.toString());
			}

			if (paramMap.get("activityInstanceId") != null) {
				Object activityInstanceId = paramMap.get("activityInstanceId");
				query.activityInstanceId(activityInstanceId.toString());
			}

		}

		return query.count();
	}

	public List<HistoricDetail> getHistoricDetails(int firstResult,
			int maxResults, HistoricDetailQuery query) {
		if (firstResult >= 0 && maxResults > 0) {
			return query.listPage(firstResult, maxResults);
		}

		return query.list();
	}

	public List<HistoricDetail> getHistoricDetails(int firstResult,
			int maxResults, Map<String, Object> paramMap) {
		HistoricDetailQuery query = historyService.createHistoricDetailQuery();
		if (paramMap != null && paramMap.size() > 0) {
			if (paramMap.get("processInstanceId") != null) {
				Object processInstanceId = paramMap.get("processInstanceId");
				query.processInstanceId(processInstanceId.toString());
			}

			if (paramMap.get("activityInstanceId") != null) {
				Object activityInstanceId = paramMap.get("activityInstanceId");
				query.activityInstanceId(activityInstanceId.toString());
			}

			if (paramMap.get("orderByProcessInstanceId") != null) {
				query.orderByProcessInstanceId();
			}

			if (paramMap.get("orderByVariableName") != null) {
				query.orderByVariableName();
			}

			if (paramMap.get("orderByFormPropertyId") != null) {
				query.orderByFormPropertyId();
			}

			if (paramMap.get("orderByVariableType") != null) {
				query.orderByVariableType();
			}

			if (paramMap.get("orderByVariableRevision") != null) {
				query.orderByVariableRevision();
			}

			if (paramMap.get("orderByTime") != null) {
				query.orderByTime();
			}

			if (paramMap.get("orderAsc") != null) {
				query.asc();
			}

			if (paramMap.get("orderDesc") != null) {
				query.desc();
			}

		} else {
			query.orderByTime();
			query.asc();
		}

		if (firstResult >= 0 && maxResults > 0) {
			return query.listPage(firstResult, maxResults);
		}

		return query.list();
	}

	public List<HistoricDetail> getHistoricDetails(String taskId) {
		return null;
	}

	public HistoricProcessInstance getHistoricProcessInstance(
			String processInstanceId) {
		HistoricProcessInstanceQuery query = historyService
				.createHistoricProcessInstanceQuery();
		query.processInstanceId(processInstanceId);
		return query.singleResult();
	}

	public long getHistoricProcessInstanceCount(
			HistoricProcessInstanceQuery query) {
		return query.count();
	}

	public long getHistoricProcessInstanceCount(Map<String, Object> paramMap) {
		HistoricProcessInstanceQuery query = historyService
				.createHistoricProcessInstanceQuery();
		if (paramMap != null && paramMap.size() > 0) {
			if (paramMap.get("processInstanceId") != null) {
				Object processInstanceId = paramMap.get("processInstanceId");
				query.processInstanceId(processInstanceId.toString());
			}

			if (paramMap.get("processDefinitionId") != null) {
				Object processDefinitionId = paramMap
						.get("processDefinitionId");
				query.processDefinitionId(processDefinitionId.toString());
			}

			if (paramMap.get("businessKey") != null) {
				Object businessKey = paramMap.get("businessKey");
				query.processInstanceBusinessKey(businessKey.toString());
			}

			if (paramMap.get("finished") != null) {
				query.finished();
			}

			if (paramMap.get("unfinished") != null) {
				query.unfinished();
			}

		}

		return query.count();
	}

	public List<HistoricProcessInstance> getHistoricProcessInstances(
			int firstResult, int maxResults, HistoricProcessInstanceQuery query) {
		if (firstResult >= 0 && maxResults > 0) {
			return query.listPage(firstResult, maxResults);
		}

		return query.list();
	}

	public List<HistoricProcessInstance> getHistoricProcessInstances(
			int firstResult, int maxResults, Map<String, Object> paramMap) {
		HistoricProcessInstanceQuery query = historyService
				.createHistoricProcessInstanceQuery();
		if (paramMap != null && paramMap.size() > 0) {
			if (paramMap.get("processInstanceId") != null) {
				Object processInstanceId = paramMap.get("processInstanceId");
				query.processInstanceId(processInstanceId.toString());
			}

			if (paramMap.get("processDefinitionId") != null) {
				Object processDefinitionId = paramMap
						.get("processDefinitionId");
				query.processDefinitionId(processDefinitionId.toString());
			}

			if (paramMap.get("businessKey") != null) {
				Object businessKey = paramMap.get("businessKey");
				query.processInstanceBusinessKey(businessKey.toString());
			}

			if (paramMap.get("finished") != null) {
				query.finished();
			}

			if (paramMap.get("unfinished") != null) {
				query.unfinished();
			}

			if (paramMap.get("orderByProcessInstanceId") != null) {
				query.orderByProcessInstanceId();
			}

			if (paramMap.get("orderByProcessDefinitionId") != null) {
				query.orderByProcessDefinitionId();
			}

			if (paramMap.get("orderByProcessInstanceBusinessKey") != null) {
				query.orderByProcessInstanceBusinessKey();
			}

			if (paramMap.get("orderByProcessInstanceStartTime") != null) {
				query.orderByProcessInstanceStartTime();
			}

			if (paramMap.get("orderByProcessInstanceEndTime") != null) {
				query.orderByProcessInstanceEndTime();
			}

			if (paramMap.get("orderByProcessInstanceDuration") != null) {
				query.orderByProcessInstanceDuration();
			}

			if (paramMap.get("orderAsc") != null) {
				query.asc();
			}

			if (paramMap.get("orderDesc") != null) {
				query.desc();
			}

		} else {
			query.orderByProcessInstanceStartTime().desc();
		}

		if (firstResult >= 0 && maxResults > 0) {
			return query.listPage(firstResult, maxResults);
		}

		return query.list();
	}

	public ProcessDefinitionInfo getLatestProcessDefinitionByKey(
			String processDefinitionKey) {
		ProcessDefinitionInfo processDefinitionInfo = new ProcessDefinitionInfo();
		ProcessDefinitionQuery query = repositoryService
				.createProcessDefinitionQuery();
		List<ProcessDefinition> list = query
				.processDefinitionKey(processDefinitionKey).latestVersion()
				.orderByProcessDefinitionVersion().desc().list();
		ProcessDefinitionEntity processDefinitionImpl = null;
		if (list != null && !list.isEmpty()) {
			processDefinitionImpl = (ProcessDefinitionEntity) list.get(0);
		}

		if (processDefinitionImpl != null) {
			processDefinitionInfo.setProcessDefinition(processDefinitionImpl);
			String deploymentId = processDefinitionImpl.getDeploymentId();
			String resourceName = processDefinitionImpl.getResourceName();
			InputStream inputStream = repositoryService.getResourceAsStream(
					deploymentId, resourceName);
			if (inputStream != null) {
				BpmnXmlReader reader = new BpmnXmlReader();
				Element root = reader.getRootElement(inputStream);
				List<ActivityInfo> activities = reader.read(root,
						processDefinitionKey);
				processDefinitionInfo.setActivities(activities);
				List<UserTask> userTasks = reader.readUserTasks(root,
						processDefinitionKey);
				processDefinitionInfo.setUserTasks(userTasks);
			}
		}
		return processDefinitionInfo;
	}

	public List<ProcessDefinition> getLatestProcessDefinitions(int firstResult,
			int maxResults, Map<String, Object> paramMap) {
		ProcessDefinitionQuery query = repositoryService
				.createProcessDefinitionQuery();
		if (paramMap != null && paramMap.size() > 0) {
			if (paramMap.get("deploymentId") != null) {
				Object deploymentId = paramMap.get("deploymentId");
				query.deploymentId(deploymentId.toString());
			}

			if (paramMap.get("name") != null) {
				Object name = paramMap.get("name");
				query.processDefinitionName(name.toString());
			}

			if (paramMap.get("nameLike") != null) {
				Object nameLike = paramMap.get("nameLike");
				query.processDefinitionNameLike(nameLike.toString());
			}

			if (paramMap.get("key") != null) {
				Object key = paramMap.get("key");
				query.processDefinitionKey(key.toString());
			}

			if (paramMap.get("keyLike") != null) {
				Object keyLike = paramMap.get("keyLike");
				query.processDefinitionKeyLike(keyLike.toString());
			}

			if (paramMap.get("version") != null) {
				Object version = (Object) paramMap.get("version");
				if (version instanceof Integer) {
					query.processDefinitionVersion((Integer) version);
				} else {
					query.processDefinitionVersion(Integer.parseInt(version
							.toString()));
				}
			}

			if (paramMap.get("category") != null) {
				Object category = paramMap.get("category");
				query.processDefinitionCategory(category.toString());
			}

			if (paramMap.get("categoryLike") != null) {
				Object categoryLike = paramMap.get("categoryLike");
				query.processDefinitionCategoryLike(categoryLike.toString());
			}

			if (paramMap.get("orderByDeploymentId") != null) {
				query.orderByDeploymentId();
			}

			if (paramMap.get("orderByProcessDefinitionCategory") != null) {
				query.orderByProcessDefinitionCategory();
			}

			if (paramMap.get("orderByProcessDefinitionId") != null) {
				query.orderByProcessDefinitionId();
			}

			if (paramMap.get("orderByProcessDefinitionKey") != null) {
				query.orderByProcessDefinitionKey();
			}

			if (paramMap.get("orderByProcessDefinitionName") != null) {
				query.orderByProcessDefinitionName();
			}

			if (paramMap.get("orderByProcessDefinitionVersion") != null) {
				query.orderByProcessDefinitionVersion();
			}

			if (paramMap.get("orderAsc") != null) {
				query.asc();
			}

			if (paramMap.get("orderDesc") != null) {
				query.desc();
			}

		} else {
			query.orderByProcessDefinitionName().asc();
		}

		query.latestVersion();

		if (firstResult >= 0 && maxResults > 0) {
			return query.listPage(firstResult, maxResults);
		}

		return query.list();
	}

	public ProcessDefinition getProcessDefinition(String processDefinitionId) {
		ProcessDefinition processDefinition = null;
		ProcessDefinitionQuery query = repositoryService
				.createProcessDefinitionQuery();
		query.processDefinitionId(processDefinitionId);
		processDefinition = query.singleResult();
		return processDefinition;
	}

	public ProcessDefinition getProcessDefinitionByDeploymentId(
			String deploymentId) {
		ProcessDefinition processDefinition = null;
		ProcessDefinitionQuery query = repositoryService
				.createProcessDefinitionQuery();
		query.deploymentId(deploymentId).orderByProcessDefinitionVersion()
				.desc();
		List<ProcessDefinition> rows = query.list();
		if (rows != null && rows.size() > 0) {
			processDefinition = rows.get(0);
		}
		return processDefinition;
	}

	public ProcessDefinition getProcessDefinitionByProcessInstanceId(
			String processInstanceId) {
		ProcessInstance processInstance = this
				.getProcessInstance(processInstanceId);
		if (processInstance != null) {
			String processDefinitionId = processInstance
					.getProcessDefinitionId();
			return this.getProcessDefinition(processDefinitionId);
		} else {
			HistoricProcessInstance hpi = this
					.getHistoricProcessInstance(processInstanceId);
			if (hpi != null) {
				String processDefinitionId = hpi.getProcessDefinitionId();
				return this.getProcessDefinition(processDefinitionId);
			}
		}
		return null;
	}

	public long getProcessDefinitionCount(Map<String, Object> paramMap) {
		ProcessDefinitionQuery query = repositoryService
				.createProcessDefinitionQuery();
		if (paramMap != null && paramMap.size() > 0) {
			if (paramMap.get("deploymentId") != null) {
				Object deploymentId = paramMap.get("deploymentId");
				query.deploymentId(deploymentId.toString());
			}

			if (paramMap.get("name") != null) {
				Object name = paramMap.get("name");
				query.processDefinitionName(name.toString());
			}

			if (paramMap.get("nameLike") != null) {
				Object nameLike = paramMap.get("nameLike");
				query.processDefinitionNameLike(nameLike.toString());
			}

			if (paramMap.get("key") != null) {
				Object key = paramMap.get("key");
				query.processDefinitionKey(key.toString());
			}

			if (paramMap.get("keyLike") != null) {
				Object keyLike = paramMap.get("keyLike");
				query.processDefinitionKeyLike(keyLike.toString());
			}

			if (paramMap.get("version") != null) {
				Object version = (Object) paramMap.get("version");
				if (version instanceof Integer) {
					query.processDefinitionVersion((Integer) version);
				} else {
					query.processDefinitionVersion(Integer.parseInt(version
							.toString()));
				}
			}

			if (paramMap.get("category") != null) {
				Object category = paramMap.get("category");
				query.processDefinitionCategory(category.toString());
			}

			if (paramMap.get("categoryLike") != null) {
				Object categoryLike = paramMap.get("categoryLike");
				query.processDefinitionCategoryLike(categoryLike.toString());
			}

		}

		return query.count();
	}

	public long getProcessDefinitionCount(ProcessDefinitionQuery query) {
		return query.count();
	}

	public List<ProcessDefinition> getProcessDefinitions(int firstResult,
			int maxResults, Map<String, Object> paramMap) {
		ProcessDefinitionQuery query = repositoryService
				.createProcessDefinitionQuery();
		if (paramMap != null && paramMap.size() > 0) {
			if (paramMap.get("deploymentId") != null) {
				Object deploymentId = paramMap.get("deploymentId");
				query.deploymentId(deploymentId.toString());
			}

			if (paramMap.get("name") != null) {
				Object name = paramMap.get("name");
				query.processDefinitionName(name.toString());
			}

			if (paramMap.get("nameLike") != null) {
				Object nameLike = paramMap.get("nameLike");
				query.processDefinitionNameLike(nameLike.toString());
			}

			if (paramMap.get("key") != null) {
				Object key = paramMap.get("key");
				query.processDefinitionKey(key.toString());
			}

			if (paramMap.get("keyLike") != null) {
				Object keyLike = paramMap.get("keyLike");
				query.processDefinitionKeyLike(keyLike.toString());
			}

			if (paramMap.get("version") != null) {
				Object version = (Object) paramMap.get("version");
				if (version instanceof Integer) {
					query.processDefinitionVersion((Integer) version);
				} else {
					query.processDefinitionVersion(Integer.parseInt(version
							.toString()));
				}
			}

			if (paramMap.get("category") != null) {
				Object category = paramMap.get("category");
				query.processDefinitionCategory(category.toString());
			}

			if (paramMap.get("categoryLike") != null) {
				Object categoryLike = paramMap.get("categoryLike");
				query.processDefinitionCategoryLike(categoryLike.toString());
			}

			if (paramMap.get("orderByDeploymentId") != null) {
				query.orderByDeploymentId();
			}

			if (paramMap.get("orderByProcessDefinitionCategory") != null) {
				query.orderByProcessDefinitionCategory();
			}

			if (paramMap.get("orderByProcessDefinitionId") != null) {
				query.orderByProcessDefinitionId();
			}

			if (paramMap.get("orderByProcessDefinitionKey") != null) {
				query.orderByProcessDefinitionKey();
			}

			if (paramMap.get("orderByProcessDefinitionName") != null) {
				query.orderByProcessDefinitionName();
			}

			if (paramMap.get("orderByProcessDefinitionVersion") != null) {
				query.orderByProcessDefinitionVersion();
			}

			if (paramMap.get("orderAsc") != null) {
				query.asc();
			}

			if (paramMap.get("orderDesc") != null) {
				query.desc();
			}

		} else {
			query.orderByProcessDefinitionName();
			query.asc();
		}

		if (firstResult >= 0 && maxResults > 0) {
			return query.listPage(firstResult, maxResults);
		}

		return query.list();
	}

	public List<ProcessDefinition> getProcessDefinitions(int firstResult,
			int maxResults, ProcessDefinitionQuery query) {
		if (firstResult >= 0 && maxResults > 0) {
			return query.listPage(firstResult, maxResults);
		}

		return query.list();
	}

	public List<ProcessDefinition> getProcessDefinitions(String processDefKey) {
		ProcessDefinitionQuery query = repositoryService
				.createProcessDefinitionQuery();
		query.processDefinitionKey(processDefKey);
		query.orderByProcessDefinitionVersion().asc();
		return query.list();
	}

	public ProcessInstance getProcessInstance(String processInstanceId) {
		ProcessInstanceQuery query = runtimeService
				.createProcessInstanceQuery();
		query.processInstanceId(processInstanceId);
		return query.singleResult();
	}

	public long getProcessInstanceCount(Map<String, Object> paramMap) {
		ProcessInstanceQuery query = runtimeService
				.createProcessInstanceQuery();
		if (paramMap != null && paramMap.size() > 0) {
			if (paramMap.get("processInstanceId") != null) {
				Object processInstanceId = paramMap.get("processInstanceId");
				query.processInstanceId(processInstanceId.toString());
			}

			if (paramMap.get("processDefinitionId") != null) {
				Object processDefinitionId = paramMap
						.get("processDefinitionId");
				query.processDefinitionId(processDefinitionId.toString());
			}

			if (paramMap.get("processDefinitionKey") != null) {
				Object processDefinitionKey = paramMap
						.get("processDefinitionKey");
				query.processDefinitionKey(processDefinitionKey.toString());
			}

			if (paramMap.get("businessKey") != null) {
				Object businessKey = paramMap.get("businessKey");
				query.processInstanceBusinessKey(businessKey.toString());
			}

			if (paramMap.get("superProcessInstanceId") != null) {
				Object superProcessInstanceId = paramMap
						.get("superProcessInstanceId");
				query.superProcessInstanceId(superProcessInstanceId.toString());
			}

			if (paramMap.get("subProcessInstanceId") != null) {
				Object subProcessInstanceId = paramMap
						.get("subProcessInstanceId");
				query.subProcessInstanceId(subProcessInstanceId.toString());
			}

		}

		return query.count();
	}

	public long getProcessInstanceCount(ProcessInstanceQuery query) {
		return query.count();
	}

	public ProcessInstanceInfo getProcessInstanceInfo(String processInstanceId) {
		ProcessInstance processInstance = this
				.getProcessInstance(processInstanceId);
		if (processInstance != null) {
			ProcessInstanceInfo info = new ProcessInstanceInfo();
			info.setProcessInstance(processInstance);
			ProcessDefinition processDefinition = this
					.getProcessDefinitionByProcessInstanceId(processInstanceId);
			info.setProcessDefinition(processDefinition);
			HistoricProcessInstance hpi = this
					.getHistoricProcessInstance(processInstanceId);
			info.setHistoricProcessInstance(hpi);
			ProcessDefinitionInfo pdi = this
					.getLatestProcessDefinitionByKey(processDefinition.getKey());

			List<ActivityInfo> activities = pdi.getActivities();

			if (activities != null && !activities.isEmpty()) {
				logger.debug("activities:" + activities.size());
				Map<String, ActivityInfo> actMap = new java.util.HashMap<String, ActivityInfo>();
				for (ActivityInfo act : activities) {
					actMap.put(act.getActivityId(), act);
				}
				List<HistoricActivityInstance> historicActivityInstances = this
						.getHistoricActivityInstances(processInstanceId);
				for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
					ActivityInfo actInfo = new ActivityInfo();
					actInfo.setActivityInstance(historicActivityInstance);
					ActivityInfo act = actMap.get(historicActivityInstance
							.getActivityId());
					if (act != null) {
						actInfo.setCoordinates(act.getCoordinates());
						if (historicActivityInstance.getEndTime() != null) {
							info.addProcessedActivityInfo(actInfo);
						} else {
							info.addActiveActivityInfo(actInfo);
						}
					}
				}
			}
			return info;
		}
		return null;
	}

	public List<ProcessInstance> getProcessInstances(int firstResult,
			int maxResults, Map<String, Object> paramMap) {
		ProcessInstanceQuery query = runtimeService
				.createProcessInstanceQuery();
		if (paramMap != null && paramMap.size() > 0) {
			if (paramMap.get("processInstanceId") != null) {
				Object processInstanceId = paramMap.get("processInstanceId");
				query.processInstanceId(processInstanceId.toString());
			}

			if (paramMap.get("processDefinitionId") != null) {
				Object processDefinitionId = paramMap
						.get("processDefinitionId");
				query.processDefinitionId(processDefinitionId.toString());
			}

			if (paramMap.get("processDefinitionKey") != null) {
				Object processDefinitionKey = paramMap
						.get("processDefinitionKey");
				query.processDefinitionKey(processDefinitionKey.toString());
			}

			if (paramMap.get("businessKey") != null) {
				Object businessKey = paramMap.get("businessKey");
				query.processInstanceBusinessKey(businessKey.toString());
			}

			if (paramMap.get("superProcessInstanceId") != null) {
				Object superProcessInstanceId = paramMap
						.get("superProcessInstanceId");
				query.superProcessInstanceId(superProcessInstanceId.toString());
			}

			if (paramMap.get("subProcessInstanceId") != null) {
				Object subProcessInstanceId = paramMap
						.get("subProcessInstanceId");
				query.subProcessInstanceId(subProcessInstanceId.toString());
			}

			if (paramMap.get("orderByProcessInstanceId") != null) {
				query.orderByProcessInstanceId();
			}

			if (paramMap.get("orderByProcessDefinitionKey") != null) {
				query.orderByProcessDefinitionKey();
			}

			if (paramMap.get("orderByProcessDefinitionId") != null) {
				query.orderByProcessDefinitionId();
			}

			if (paramMap.get("orderAsc") != null) {
				query.asc();
			}

			if (paramMap.get("orderDesc") != null) {
				query.desc();
			}

		} else {

		}

		if (firstResult >= 0 && maxResults > 0) {
			return query.listPage(firstResult, maxResults);
		}

		return query.list();
	}

	public List<ProcessInstance> getProcessInstances(int firstResult,
			int maxResults, ProcessInstanceQuery query) {
		if (firstResult >= 0 && maxResults > 0) {
			return query.listPage(firstResult, maxResults);
		}

		return query.list();
	}

	@javax.annotation.Resource
	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	@javax.annotation.Resource
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	@javax.annotation.Resource
	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	@javax.annotation.Resource
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

}