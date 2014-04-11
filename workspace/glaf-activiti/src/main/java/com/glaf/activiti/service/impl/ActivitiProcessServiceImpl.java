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

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.activiti.model.DataField;
import com.glaf.activiti.model.ProcessContext;
import com.glaf.activiti.service.ActivitiProcessService;
import com.glaf.core.util.Constants;
import com.glaf.core.util.UUID32;

@Service("activitiProcessService")
@Transactional
public class ActivitiProcessServiceImpl implements ActivitiProcessService {

	protected RepositoryService repositoryService;

	protected RuntimeService runtimeService;

	protected TaskService taskService;

	protected HistoryService historyService;

	protected IdentityService identityService;

	public ActivitiProcessServiceImpl() {

	}

	/**
	 * 为指定任务添加候选组
	 * 
	 * @param taskId
	 * @param groupId
	 */
	public void addCandidateGroup(String taskId, String groupId) {
		taskService.addCandidateGroup(taskId, groupId);
	}

	/**
	 * 为指定任务添加候选用户
	 * 
	 * @param taskId
	 * @param actorId
	 */
	public void addCandidateUser(String taskId, String actorId) {
		taskService.addCandidateUser(taskId, actorId);
	}

	/**
	 * 为指定任务添加组
	 * 
	 * @param taskId
	 * @param groupId
	 * @param identityLinkType
	 */
	public void addGroupIdentityLink(String taskId, String groupId,
			String identityLinkType) {
		taskService.addGroupIdentityLink(taskId, groupId, identityLinkType);
	}

	/**
	 * 为指定任务添加用户
	 * 
	 * @param taskId
	 * @param userId
	 * @param identityLinkType
	 */
	public void addUserIdentityLink(String taskId, String userId,
			String identityLinkType) {
		taskService.addUserIdentityLink(taskId, userId, identityLinkType);
	}

	/**
	 * 分配任务给指定人
	 * 
	 * @param taskId
	 *            任务编号
	 * @param actorId
	 *            用户编号
	 */
	public void claimTask(String taskId, String actorId) {
		taskService.claim(taskId, actorId);
	}

	protected List<Task> getUserTasks(String processInstanceId, String actorId) {
		List<Task> tasks = new java.util.concurrent.CopyOnWriteArrayList<Task>();
		TaskQuery query01 = taskService.createTaskQuery();
		query01.processInstanceId(processInstanceId);
		query01.taskAssignee(actorId);
		List<Task> tasks01 = query01.list();
		if (tasks01 != null && tasks01.size() > 0) {
			for (Task task : tasks01) {
				tasks.add(task);
			}
		}

		return tasks;
	}

	/**
	 * 完成任务
	 * 
	 * @param ctx
	 */
	public void completeTask(ProcessContext ctx) {
		String actorId = ctx.getActorId();
		String taskId = ctx.getTaskId();
		Map<String, Object> variables = ctx.getVariables();
		if (variables == null) {
			variables = new java.util.concurrent.ConcurrentHashMap<String, Object>();
		}

		if (ctx.getOutcome() != null) {
			variables.put(Constants.OUTCOME, ctx.getOutcome());
		}

		if (ctx.getDataFields() != null && !ctx.getDataFields().isEmpty()) {
			Collection<DataField> dataFields = ctx.getDataFields();
			for (DataField dataField : dataFields) {
				variables.put(dataField.getName(), dataField.getValue());
			}
		}

		variables.put(Constants.BUSINESS_KEY, UUID32.getUUID());

		TaskQuery query = taskService.createTaskQuery();
		Task task = null;
		if (taskId != null) {
			task = query.taskId(taskId).singleResult();
		} else {
			List<Task> tasks = this.getUserTasks(ctx.getProcessInstanceId(),
					actorId);
			if (tasks != null && !tasks.isEmpty()) {
				task = tasks.get(0);
			}
		}

		if (task != null) {
			taskId = task.getId();
			if (task.getAssignee() == null) {
				taskService.claim(taskId, actorId);
			}
			try {
				identityService.setAuthenticatedUserId(actorId);

				taskService.complete(taskId, variables);

			} finally {
				identityService.setAuthenticatedUserId(null);
			}
		}
	}

	/**
	 * 完成任务
	 * 
	 * @param taskId
	 *            任务编号
	 */
	public void completeTask(String taskId) {
		taskService.complete(taskId);
	}

	/**
	 * 完成任务
	 * 
	 * @param taskId
	 *            任务编号
	 * @param variables
	 *            变量集
	 */
	public void completeTask(String taskId, Map<String, Object> variables) {
		variables.remove(Constants.BUSINESS_KEY);
		taskService.complete(taskId, variables);
	}

	/**
	 * 完成任务
	 * 
	 * @param actorId
	 *            用户编号
	 * @param taskId
	 *            任务编号
	 * 
	 * @param variables
	 *            变量集
	 */
	public void completeTask(String actorId, String taskId,
			Map<String, Object> variables) {
		if (StringUtils.isNotEmpty(taskId) && StringUtils.isNotEmpty(actorId)) {
			TaskQuery query = taskService.createTaskQuery();
			Task task = query.taskId(taskId).singleResult();

			if (task != null) {
				if (task.getAssignee() == null) {
					taskService.claim(taskId, actorId);
				}
				variables.remove(Constants.BUSINESS_KEY);
				try {
					identityService.setAuthenticatedUserId(actorId);

					taskService.complete(taskId, variables);

				} finally {
					identityService.setAuthenticatedUserId(null);
				}
			}
		}
	}

	/**
	 * 删除流程实例
	 * 
	 * @param processInstanceId
	 *            流程实例编号
	 */
	public void deleteProcessInstance(String processInstanceId,
			String deleteReason) {
		runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
	}

	/**
	 * 删除任务实例
	 * 
	 * @param taskId
	 *            任务实例编号
	 */
	public void deleteTask(String taskId) {
		taskService.deleteTask(taskId);
	}

	/**
	 * 删除任务实例
	 * 
	 * @param taskIds
	 *            任务实例编号集合
	 */
	public void deleteTasks(List<String> taskIds) {
		taskService.deleteTasks(taskIds);
	}

	/**
	 * 重新指派任务处理人
	 * 
	 * @param taskId
	 * @param actorId
	 */
	public void setAssignee(String taskId, String actorId) {
		taskService.setAssignee(taskId, actorId);
	}

	@javax.annotation.Resource
	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	@javax.annotation.Resource
	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}

	/**
	 * 设置任务优先级
	 * 
	 * @param taskId
	 * @param priority
	 */
	public void setPriority(String taskId, int priority) {
		taskService.setPriority(taskId, priority);
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

	/**
	 * 发执行信号
	 * 
	 * @param executionId
	 */
	public void signal(String executionId) {
		runtimeService.signal(executionId);
	}

	/**
	 * 启动流程
	 * 
	 * @param ctx
	 *            流程上下文
	 * 
	 * @return
	 */
	public String startProcess(ProcessContext ctx) {
		String actorId = ctx.getActorId();
		String processDefinitionKey = ctx.getProcessName();
		String businessKey = ctx.getBusinessKey();
		Map<String, Object> variables = ctx.getVariables();
		if (variables == null) {
			variables = new java.util.concurrent.ConcurrentHashMap<String, Object>();
		}
		variables.put(Constants.PROCESS_STARTER, actorId);
		variables.remove(Constants.BUSINESS_KEY);

		if (ctx.getDataFields() != null && !ctx.getDataFields().isEmpty()) {
			Collection<DataField> dataFields = ctx.getDataFields();
			for (DataField dataField : dataFields) {
				variables.put(dataField.getName(), dataField.getValue());
			}
		}

		try {
			identityService.setAuthenticatedUserId(actorId);

			ProcessInstance processInstance = runtimeService
					.startProcessInstanceByKey(processDefinitionKey,
							businessKey, variables);

			return processInstance.getProcessInstanceId();
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
	}

	/**
	 * 启动流程
	 * 
	 * @param actorId
	 *            参与者
	 * @param processDefinitionKey
	 *            流程定义名
	 * @return
	 */
	public ProcessInstance startProcessInstanceByKey(String actorId,
			String processDefinitionKey) {
		Map<String, Object> variables = new java.util.concurrent.ConcurrentHashMap<String, Object>();
		variables.put(Constants.PROCESS_STARTER, actorId);

		try {
			identityService.setAuthenticatedUserId(actorId);

			ProcessInstance processInstance = runtimeService
					.startProcessInstanceByKey(processDefinitionKey, variables);
			return processInstance;
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
	}

	/**
	 * 
	 * @param actorId
	 *            参与者
	 * @param processDefinitionKey
	 *            流程定义名
	 * @param variables
	 *            变量集
	 * @return
	 */
	public ProcessInstance startProcessInstanceByKey(String actorId,
			String processDefinitionKey, Map<String, Object> variables) {
		if (variables == null) {
			variables = new java.util.concurrent.ConcurrentHashMap<String, Object>();
		}
		variables.put(Constants.PROCESS_STARTER, actorId);

		try {
			identityService.setAuthenticatedUserId(actorId);

			ProcessInstance processInstance = runtimeService
					.startProcessInstanceByKey(processDefinitionKey, variables);
			return processInstance;
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
	}

	/**
	 * 
	 * @param actorId
	 *            参与者
	 * @param processDefinitionKey
	 *            流程定义名
	 * @param businessKey
	 *            业务主键
	 * @return
	 */
	public ProcessInstance startProcessInstanceByKey(String actorId,
			String processDefinitionKey, String businessKey) {
		Map<String, Object> variables = new java.util.concurrent.ConcurrentHashMap<String, Object>();
		variables.put(Constants.PROCESS_STARTER, actorId);

		try {
			identityService.setAuthenticatedUserId(actorId);

			ProcessInstance processInstance = runtimeService
					.startProcessInstanceByKey(processDefinitionKey,
							businessKey);

			return processInstance;
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
	}

	/**
	 * 
	 * @param actorId
	 *            参与者
	 * @param processDefinitionKey
	 *            流程定义名
	 * @param businessKey
	 *            业务主键
	 * @param variables
	 *            变量集
	 * @return
	 */
	public ProcessInstance startProcessInstanceByKey(String actorId,
			String processDefinitionKey, String businessKey,
			Map<String, Object> variables) {
		if (variables == null) {
			variables = new java.util.concurrent.ConcurrentHashMap<String, Object>();
		}
		variables.put(Constants.PROCESS_STARTER, actorId);

		try {
			identityService.setAuthenticatedUserId(actorId);

			ProcessInstance processInstance = runtimeService
					.startProcessInstanceByKey(processDefinitionKey,
							businessKey, variables);

			return processInstance;
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
	}

}