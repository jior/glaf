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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
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
import com.glaf.core.util.ResponseUtils;
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
	 * 取回任务
	 * 
	 * @param taskId
	 * @return
	 */
	public byte[] callback(String taskId) {
		Map<String, Object> variables = null;
		try {
			// 取得当前任务
			HistoricTaskInstance historicTaskInstance = historyService
					.createHistoricTaskInstanceQuery().taskId(taskId)
					.singleResult();
			// 取得流程实例
			ProcessInstance processInstance = runtimeService
					.createProcessInstanceQuery()
					.processInstanceId(
							historicTaskInstance.getProcessInstanceId())
					.singleResult();
			if (processInstance == null) {
				return ResponseUtils.responseJsonResult(false, "流程已经结束");
			}
			variables = processInstance.getProcessVariables();
			// 取得流程定义
			ProcessDefinitionEntity definition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
					.getDeployedProcessDefinition(historicTaskInstance
							.getProcessDefinitionId());
			if (definition == null) {
				return ResponseUtils.responseJsonResult(false, "流程定义不存在");
			}
			// 取得下一步活动
			ActivityImpl currActivity = ((ProcessDefinitionImpl) definition)
					.findActivity(historicTaskInstance.getTaskDefinitionKey());
			List<PvmTransition> nextTransitionList = currActivity
					.getOutgoingTransitions();
			for (PvmTransition nextTransition : nextTransitionList) {
				PvmActivity nextActivity = nextTransition.getDestination();
				List<HistoricTaskInstance> completeTasks = historyService
						.createHistoricTaskInstanceQuery()
						.processInstanceId(processInstance.getId())
						.taskDefinitionKey(nextActivity.getId()).finished()
						.list();
				int finished = completeTasks.size();
				if (finished > 0) {
					return ResponseUtils.responseJsonResult(false,
							"存在已经完成的活动，流程不能取回");
				}
				List<Task> nextTasks = taskService.createTaskQuery()
						.processInstanceId(processInstance.getId())
						.taskDefinitionKey(nextActivity.getId()).list();
				for (Task nextTask : nextTasks) {
					// 取活动，清除活动方向
					List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
					List<PvmTransition> pvmTransitionList = nextActivity
							.getOutgoingTransitions();
					for (PvmTransition pvmTransition : pvmTransitionList) {
						oriPvmTransitionList.add(pvmTransition);
					}
					pvmTransitionList.clear();
					// 建立新方向
					ActivityImpl nextActivityImpl = ((ProcessDefinitionImpl) definition)
							.findActivity(nextTask.getTaskDefinitionKey());
					TransitionImpl newTransition = nextActivityImpl
							.createOutgoingTransition();
					newTransition.setDestination(currActivity);
					// 完成任务
					taskService.complete(nextTask.getId(), variables);
					historyService.deleteHistoricTaskInstance(nextTask.getId());
					// 恢复方向
					currActivity.getIncomingTransitions().remove(newTransition);
					List<PvmTransition> pvmTList = nextActivity
							.getOutgoingTransitions();
					pvmTList.clear();
					for (PvmTransition pvmTransition : oriPvmTransitionList) {
						pvmTransitionList.add(pvmTransition);
					}
				}
			}
			historyService.deleteHistoricTaskInstance(historicTaskInstance
					.getId());

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
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
			variables = new java.util.HashMap<String, Object>();
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

	protected List<Task> getUserTasks(String processInstanceId, String actorId) {
		List<Task> tasks = new java.util.ArrayList<Task>();
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
	 * 驳回任务
	 * 
	 * @param taskId
	 * @return
	 */
	public byte[] rollback(String taskId) {
		Map<String, Object> variables = null;
		try {
			// 取得当前任务
			HistoricTaskInstance historicTaskInstance = historyService
					.createHistoricTaskInstanceQuery().taskId(taskId)
					.singleResult();
			// 取得流程实例
			ProcessInstance processInstance = runtimeService
					.createProcessInstanceQuery()
					.processInstanceId(
							historicTaskInstance.getProcessInstanceId())
					.singleResult();
			if (processInstance == null) {
				return ResponseUtils.responseJsonResult(false, "流程已经结束");
			}
			variables = processInstance.getProcessVariables();
			// 取得流程定义
			ProcessDefinitionEntity definition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
					.getDeployedProcessDefinition(historicTaskInstance
							.getProcessDefinitionId());
			if (definition == null) {
				return ResponseUtils.responseJsonResult(false, "流程定义不存在");
			}
			// 取得上一步活动
			ActivityImpl activity = ((ProcessDefinitionImpl) definition)
					.findActivity(historicTaskInstance.getTaskDefinitionKey());
			List<PvmTransition> nextTransitionList = activity
					.getIncomingTransitions();
			// 清除当前活动的出口
			List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
			List<PvmTransition> pvmTransitionList = activity
					.getOutgoingTransitions();
			for (PvmTransition pvmTransition : pvmTransitionList) {
				oriPvmTransitionList.add(pvmTransition);
			}
			pvmTransitionList.clear();

			// 建立新出口
			List<TransitionImpl> newTransitions = new ArrayList<TransitionImpl>();
			for (PvmTransition nextTransition : nextTransitionList) {
				PvmActivity nextActivity = nextTransition.getSource();
				ActivityImpl nextActivityImpl = ((ProcessDefinitionImpl) definition)
						.findActivity(nextActivity.getId());
				TransitionImpl newTransition = activity
						.createOutgoingTransition();
				newTransition.setDestination(nextActivityImpl);
				newTransitions.add(newTransition);
			}
			// 完成任务
			List<Task> tasks = taskService
					.createTaskQuery()
					.processInstanceId(processInstance.getId())
					.taskDefinitionKey(
							historicTaskInstance.getTaskDefinitionKey()).list();
			for (Task task : tasks) {
				taskService.complete(task.getId(), variables);
				historyService.deleteHistoricTaskInstance(task.getId());
			}
			// 恢复方向
			for (TransitionImpl transitionImpl : newTransitions) {
				activity.getOutgoingTransitions().remove(transitionImpl);
			}
			for (PvmTransition pvmTransition : oriPvmTransitionList) {
				pvmTransitionList.add(pvmTransition);
			}

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
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
			variables = new java.util.HashMap<String, Object>();
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
		Map<String, Object> variables = new java.util.HashMap<String, Object>();
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
			variables = new java.util.HashMap<String, Object>();
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
		Map<String, Object> variables = new java.util.HashMap<String, Object>();
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
			variables = new java.util.HashMap<String, Object>();
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