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

package com.glaf.activiti.container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.identity.Agent;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.util.StringTools;

import com.glaf.activiti.model.ProcessContext;
import com.glaf.activiti.model.TaskItem;
import com.glaf.activiti.service.ActivitiProcessQueryService;
import com.glaf.activiti.service.ActivitiProcessService;
import com.glaf.activiti.service.ActivitiTaskQueryService;

public class ProcessContainer {

	protected static final Log logger = LogFactory
			.getLog(ProcessContainer.class);

	private static ConcurrentMap<String, Object> cache = new ConcurrentHashMap<String, Object>();

	protected static final int MAX_PROCESS_INSTANCE_QTY = 1000;

	protected static final int MAX_TASK_INSTANCE_QTY = 1000;

	private static final ProcessContainer container = new ProcessContainer();

	protected static ActivitiProcessQueryService activitiProcessQueryService;

	protected static ActivitiTaskQueryService activitiTaskQueryService;

	protected static ActivitiProcessService activitiProcessService;

	public static ActivitiProcessQueryService getActivitiProcessQueryService() {
		return activitiProcessQueryService;
	}

	public static ActivitiProcessService getActivitiProcessService() {
		return activitiProcessService;
	}

	public static ActivitiTaskQueryService getActivitiTaskQueryService() {
		return activitiTaskQueryService;
	}

	public final static ProcessContainer getContainer() {

		return container;
	}

	private ProcessContainer() {
		activitiProcessService = ContextFactory
				.getBean(ActivitiProcessService.class);
		activitiTaskQueryService = ContextFactory
				.getBean(ActivitiTaskQueryService.class);
		activitiProcessQueryService = ContextFactory
				.getBean(ActivitiProcessQueryService.class);
	}

	/**
	 * �������
	 * 
	 * @param actorId
	 * @param params
	 * @return
	 */
	public boolean completeTask(ProcessContext ctx) {
		// ȷ��ÿ�����̶�ÿ��ҵ�񵥾���ͬһʱ��ֻ������һ��ʵ��
		String cacheKey = "x_";
		if (StringUtils.isNotEmpty(ctx.getProcessInstanceId())) {
			cacheKey += "pid_" + ctx.getProcessInstanceId();
		} else if (StringUtils.isNotEmpty(ctx.getTaskId())) {
			cacheKey += "tid_" + ctx.getTaskId();
		}
		boolean isCompleteOK = false;
		try {
			if (cache.get(cacheKey) == null) {
				cache.put(cacheKey, "1");
				Collection<String> agentIds = this
						.getAgentIds(ctx.getActorId());
				ctx.setAgentIds(agentIds);
				activitiProcessService.completeTask(ctx);
				isCompleteOK = true;
			}
		} catch (Exception ex) {
			isCompleteOK = false;
			logger.debug(ex);
			throw new RuntimeException(ex);
		} finally {
			cache.remove(cacheKey);
		}
		return isCompleteOK;
	}

	protected TaskItem convert(Task task) {
		TaskItem item = new TaskItem();
		item.setId(task.getId());
		item.setPriority(task.getPriority());
		item.setActorId(task.getAssignee());
		item.setCreateTime(task.getCreateTime());
		item.setDuedate(task.getDueDate());
		item.setTaskInstanceId(task.getId());
		item.setTaskName(task.getName());
		item.setTaskDescription(task.getDescription());
		item.setTaskDefinitionKey(task.getTaskDefinitionKey());
		item.setProcessDefinitionId(task.getProcessDefinitionId());
		item.setOwner(task.getOwner());
		item.setExecutionId(task.getExecutionId());
		item.setProcessInstanceId(task.getProcessInstanceId());
		item.setParentTaskId(task.getParentTaskId());
		return item;
	}

	/**
	 * ��������
	 * 
	 * @param actorId
	 * @param rows
	 * @return
	 */
	public List<TaskItem> filter(String actorId, List<TaskItem> rows) {
		List<Agent> agents = this.getAgents(actorId);
		logger.debug(agents);
		List<TaskItem> taskItems = new ArrayList<TaskItem>();
		if (rows != null && rows.size() > 0) {
			Iterator<TaskItem> iter = rows.iterator();
			while (iter.hasNext()) {
				TaskItem item = iter.next();
				// logger.debug(item.getProcessDescription() + "\t"
				// + item.getTaskDescription() + "\t" + item.getActorId());
				/**
				 * ����������˵������ֱ�Ӵ���
				 */
				if (StringUtils.equals(actorId, item.getActorId())) {
					taskItems.add(item);
				} else if (StringUtils.contains(item.getActorId(), actorId)) {
					List<String> actorIds = StringTools
							.split(item.getActorId());
					if (actorIds != null && actorIds.contains(actorId)) {
						taskItems.add(item);
					}
				} else {
					if (agents != null && agents.size() > 0) {
						Iterator<Agent> it = agents.iterator();
						while (it.hasNext()) {
							Agent agent = it.next();
							if (!agent.isValid()) {
								continue;
							}
							/**
							 * �ж��Ƿ�Ϊĳ�������˵�����
							 */
							if (!StringUtils.equals(item.getActorId(),
									agent.getAssignFrom())) {
								continue;
							}
							switch (agent.getAgentType()) {
							case 0:// ȫ�ִ���
								taskItems.add(item);
								break;
							case 1:// ���̴���
								if (StringUtils.equalsIgnoreCase(
										agent.getProcessName(),
										item.getProcessName())) {
									taskItems.add(item);
								}
								break;
							case 2:// ָ�������������
								if (StringUtils.equalsIgnoreCase(
										agent.getProcessName(),
										item.getProcessName())
										&& StringUtils.equalsIgnoreCase(
												agent.getTaskName(),
												item.getTaskName())) {
									taskItems.add(item);
								}
								break;
							default:
								break;
							}
						}
					}
				}
			}
		}
		return taskItems;
	}

	/**
	 * ��������
	 * 
	 * @param actorId
	 * @param rows
	 * @return
	 */
	public TaskItem filterOne(String actorId, TaskItem item) {
		List<Agent> agents = this.getAgents(actorId);
		// logger.debug(agents);
		// logger.debug(item.getProcessDescription() + "\t"
		// + item.getTaskDescription() + "\t" + item.getActorId());
		TaskItem taskItem = null;
		/**
		 * ����������˵������ֱ�Ӵ���
		 */
		if (StringUtils.equals(actorId, item.getActorId())) {
			taskItem = item;
		} else if (StringUtils.contains(item.getActorId(), actorId)) {
			List<String> actorIds = StringTools.split(item.getActorId());
			if (actorIds != null && actorIds.contains(actorId)) {
				taskItem = item;
			}
		} else {
			if (agents != null && agents.size() > 0) {
				Iterator<Agent> it = agents.iterator();
				while (it.hasNext()) {
					Agent agent = it.next();
					if (!agent.isValid()) {
						continue;
					}
					/**
					 * �ж��Ƿ�Ϊĳ�������˵�����
					 */
					if (!StringUtils.equals(item.getActorId(),
							agent.getAssignFrom())) {
						continue;
					}
					switch (agent.getAgentType()) {
					case 0:// ȫ�ִ���
						taskItem = item;
						break;
					case 1:// ���̴���
						if (StringUtils.equalsIgnoreCase(
								agent.getProcessName(), item.getProcessName())) {
							taskItem = item;
						}
						break;
					case 2:// ָ�������������
						if (StringUtils.equalsIgnoreCase(
								agent.getProcessName(), item.getProcessName())
								&& StringUtils
										.equalsIgnoreCase(agent.getTaskName(),
												item.getTaskName())) {
							taskItem = item;
						}
						break;
					default:
						break;
					}
				}
			}
		}

		return taskItem;
	}

	public Collection<String> getActivityNames(String processInstanceId) {
		Collection<String> names = new ArrayList<String>();
		try {

		} catch (Exception ex) {
			logger.debug(ex);
			throw new RuntimeException(ex);
		}
		return names;
	}

	/**
	 * ��ȡί���˱�ż���
	 * 
	 * @param assignTo
	 *            �����˱��
	 * @return
	 */
	public List<String> getAgentIds(String assignTo) {
		List<String> agentIds = IdentityFactory.getAgentIds(assignTo);
		return agentIds;
	}

	/**
	 * ��ȡί���˶��󼯺�
	 * 
	 * @param assignTo
	 *            �����˱��
	 * @return
	 */
	public List<Agent> getAgents(String assignTo) {
		List<Agent> agents = IdentityFactory.getAgents(assignTo);
		return agents;
	}

	/**
	 * ��ȡȫ���û��Ĵ�������������Ϣϵͳ�Ĵ߰졣
	 * 
	 * @return
	 */
	public List<TaskItem> getAllTaskItems() {
		List<TaskItem> taskItems = new ArrayList<TaskItem>();
		try {
			List<Task> tasks = activitiTaskQueryService.getAllTasks();
			if (tasks != null && !tasks.isEmpty()) {
				for (Task task : tasks) {
					taskItems.add(this.convert(task));
				}
			}
		} catch (Exception ex) {
			logger.debug(ex);
			throw new RuntimeException(ex);
		}

		logger.debug(" all tasks processInstanceId size:" + taskItems.size());

		return taskItems;
	}

	/**
	 * ��ȡĳ������ʵ�����е����񣨰����Ѿ����������ͻ�δ���������
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public List<TaskItem> getAllTaskItems(String processInstanceId) {
		List<TaskItem> taskItems = new ArrayList<TaskItem>();
		try {
			List<Task> tasks = activitiTaskQueryService
					.getAllTasks(processInstanceId);
			if (tasks != null && !tasks.isEmpty()) {
				for (Task task : tasks) {
					taskItems.add(this.convert(task));
				}
			}
		} catch (Exception ex) {
			logger.debug(ex);
			throw new RuntimeException(ex);
		}
		return taskItems;
	}

	/**
	 * ��ȡȫ�����µ����̶���
	 * 
	 * @return
	 */
	public List<ProcessDefinition> getLatestProcessDefinitions() {
		try {
			return activitiProcessQueryService.getAllLatestProcessDefinitions();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * �������̶����Ż�ȡ���̶���
	 * 
	 * @param processDefinitionId
	 * @return
	 */
	public ProcessDefinition getProcessDefinition(String processDefinitionId) {
		try {
			return activitiProcessQueryService
					.getProcessDefinition(processDefinitionId);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * ��������ʵ����Ż�ȡ����ʵ��
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public ProcessInstance getProcessInstance(String processInstanceId) {
		try {
			return activitiProcessQueryService
					.getProcessInstance(processInstanceId);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * �����������ƻ�ȡ����ʵ��
	 * 
	 * @param processDefinitionKey
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public List<ProcessInstance> getProcessInstances(
			String processDefinitionKey, int start, int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("processDefinitionKey", processDefinitionKey);
		try {
			return activitiProcessQueryService.getProcessInstances(start,
					pageSize, paramMap);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * ��ȡĳЩ�û������������ʵ�����
	 * 
	 * @param actorIds
	 * @return
	 */
	public List<String> getRunningProcessInstanceIds(Collection<String> actorIds) {
		List<String> processInstanceIds = new ArrayList<String>();
		List<TaskItem> taskItems = this.getTaskItems(actorIds);
		if (taskItems != null && taskItems.size() > 0) {
			Iterator<TaskItem> iterator = taskItems.iterator();
			while (iterator.hasNext()) {
				TaskItem taskItem = iterator.next();
				if (!processInstanceIds.contains(taskItem
						.getProcessInstanceId())) {
					processInstanceIds.add(taskItem.getProcessInstanceId());
				}
			}
		}

		logger.debug(actorIds + " running processInstanceId size:"
				+ processInstanceIds.size());

		return processInstanceIds;
	}

	/**
	 * ��ȡĳ���û������������ʵ�����
	 * 
	 * @param actorId
	 * @return
	 */
	public List<String> getRunningProcessInstanceIds(String actorId) {
		List<String> processInstanceIds = new ArrayList<String>();
		List<TaskItem> taskItems = this.getTaskItems(actorId);
		if (taskItems != null && taskItems.size() > 0) {
			Iterator<TaskItem> iterator = taskItems.iterator();
			while (iterator.hasNext()) {
				TaskItem taskItem = iterator.next();
				if (!processInstanceIds.contains(taskItem
						.getProcessInstanceId())) {
					processInstanceIds.add(taskItem.getProcessInstanceId());
				}
			}
		}
		logger.debug(actorId + " task size:" + taskItems.size());
		return processInstanceIds;
	}

	/**
	 * ��ȡĳ������ָ���û����������ʵ�����
	 * 
	 * @param actorId
	 * @return
	 */
	public List<String> getRunningProcessInstanceIds(
			String processDefinitionKey, String actorId) {
		List<String> processInstanceIds = new ArrayList<String>();
		List<TaskItem> taskItems = this.getRunningTaskItems(
				processDefinitionKey, actorId);
		if (taskItems != null && taskItems.size() > 0) {
			Iterator<TaskItem> iterator = taskItems.iterator();
			while (iterator.hasNext()) {
				TaskItem taskItem = iterator.next();
				if (!processInstanceIds.contains(taskItem
						.getProcessInstanceId())) {
					processInstanceIds.add(taskItem.getProcessInstanceId());
				}
			}
		}
		logger.debug(actorId + " task size:" + taskItems.size());
		return processInstanceIds;
	}

	/**
	 * ��ȡĳ���������������ʵ�����
	 * 
	 * @param processDefinitionKey
	 * @return
	 */
	public List<String> getRunningProcessInstanceIdsByName(
			String processDefinitionKey) {
		List<String> processInstanceIds = new ArrayList<String>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("processDefinitionKey", processDefinitionKey);
		try {
			List<ProcessInstance> rows = activitiProcessQueryService
					.getProcessInstances(1, MAX_PROCESS_INSTANCE_QTY, paramMap);
			for (ProcessInstance p : rows) {
				if (!processInstanceIds.contains(p.getProcessInstanceId())) {
					processInstanceIds.add(p.getProcessInstanceId());
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return processInstanceIds;
	}

	/**
	 * ��ȡĳЩ�û�������ʵ�����
	 * 
	 * @param actorIds
	 * @return
	 */
	public List<String> getRunningTaskInstanceIds(Collection<String> actorIds) {
		List<String> taskIds = new ArrayList<String>();
		List<TaskItem> taskItems = this.getTaskItems(actorIds);
		if (taskItems != null && taskItems.size() > 0) {
			Iterator<TaskItem> iterator = taskItems.iterator();
			while (iterator.hasNext()) {
				TaskItem taskItem = iterator.next();
				if (!taskIds.contains(taskItem.getTaskInstanceId())) {
					taskIds.add(taskItem.getTaskInstanceId());
				}
			}
		}

		logger.debug(actorIds + " running taskId size:" + taskIds.size());

		return taskIds;
	}

	/**
	 * ��ȡĳ���û�������ʵ�����
	 * 
	 * @param actorId
	 * @return
	 */
	public List<String> getRunningTaskInstanceIds(String actorId) {
		List<String> taskIds = new ArrayList<String>();
		List<TaskItem> taskItems = this.getTaskItems(actorId);
		if (taskItems != null && taskItems.size() > 0) {
			Iterator<TaskItem> iterator = taskItems.iterator();
			while (iterator.hasNext()) {
				TaskItem taskItem = iterator.next();
				if (!taskIds.contains(taskItem.getTaskInstanceId())) {
					taskIds.add(taskItem.getTaskInstanceId());
				}
			}
		}

		logger.debug(actorId + " running taskId size:" + taskIds.size());

		return taskIds;
	}

	/**
	 * ��ȡĳ�����̵���������ʵ�����
	 * 
	 * @param processName
	 * @return
	 */
	public List<String> getRunningTaskInstanceIdsByName(String processName) {
		List<String> taskIds = new ArrayList<String>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("processDefinitionKey", processName);
		try {
			List<ProcessInstance> rows = activitiProcessQueryService
					.getProcessInstances(1, MAX_PROCESS_INSTANCE_QTY, paramMap);
			for (ProcessInstance p : rows) {
				paramMap.put("processInstanceId", p.getProcessInstanceId());
				List<Task> tasks = activitiTaskQueryService.getTasks(0,
						MAX_TASK_INSTANCE_QTY, paramMap);
				for (Task task : tasks) {
					if (!taskIds.contains(task.getId())) {
						taskIds.add(task.getId());
					}
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return taskIds;
	}

	/**
	 * ��ȡĳ���û�ĳ�����̵�����ʵ�����
	 * 
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public List<String> getRunningTaskInstanceIdsByName(String processName,
			String actorId) {
		List<String> taskIds = new ArrayList<String>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("processDefinitionKey", processName);
		try {
			List<ProcessInstance> rows = activitiProcessQueryService
					.getProcessInstances(1, MAX_PROCESS_INSTANCE_QTY, paramMap);
			for (ProcessInstance p : rows) {
				paramMap.put("assignee", actorId);
				paramMap.put("processInstanceId", p.getProcessInstanceId());
				List<Task> tasks = activitiTaskQueryService.getTasks(0,
						MAX_TASK_INSTANCE_QTY, paramMap);
				for (Task task : tasks) {
					if (!taskIds.contains(task.getId())) {
						taskIds.add(task.getId());
					}
				}

				paramMap.remove("assignee");
				paramMap.put("candidateUser", actorId);
				tasks = activitiTaskQueryService.getTasks(0,
						MAX_TASK_INSTANCE_QTY, paramMap);
				for (Task task : tasks) {
					if (!taskIds.contains(task.getId())) {
						taskIds.add(task.getId());
					}
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return taskIds;
	}

	/**
	 * ��ȡĳ������ָ���û��Ĵ�������
	 * 
	 * @param processDefinitionKey
	 * @param actorId
	 * 
	 * @return
	 */
	public List<TaskItem> getRunningTaskItems(String processDefinitionKey,
			String actorId) {
		List<TaskItem> taskItems = new ArrayList<TaskItem>();
		Collection<String> agentIds = this.getAgentIds(actorId);
		Collection<String> actorIds = new ArrayList<String>();
		actorIds.add(actorId);
		if (agentIds != null && agentIds.size() > 0) {
			actorIds.addAll(agentIds);
		}

		for (String userId : actorIds) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("assignee", userId);
			paramMap.put("processDefinitionKey", processDefinitionKey);
			List<Task> tasks = activitiTaskQueryService.getTasks(0,
					MAX_TASK_INSTANCE_QTY, paramMap);
			if (tasks != null && !tasks.isEmpty()) {
				for (Task task : tasks) {
					taskItems.add(this.convert(task));
				}
			}
		}

		for (String userId : actorIds) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("candidateUser", userId);
			paramMap.put("processDefinitionKey", processDefinitionKey);
			List<Task> tasks = activitiTaskQueryService.getTasks(0,
					MAX_TASK_INSTANCE_QTY, paramMap);
			if (tasks != null && !tasks.isEmpty()) {
				for (Task task : tasks) {
					taskItems.add(this.convert(task));
				}
			}
		}

		taskItems = this.filter(actorId, taskItems);

		return taskItems;
	}

	/**
	 * ��ȡĳ���û����������������ʵ�����
	 * 
	 * @param actorId
	 * @return �����������ʵ����ŵļ���
	 */
	public List<String> getTaskInstanceIds(String actorId) {
		List<String> taskIds = new ArrayList<String>();
		try {
			// ��ȡ�����˵�����ʵ��
			List<String> agentIds = this.getAgentIds(actorId);

			List<String> actorIds = new ArrayList<String>();
			actorIds.add(actorId);

			if (agentIds.size() > 0) {
				actorIds.addAll(agentIds);
			}

			Map<String, Object> paramMap = new HashMap<String, Object>();

			for (String userId : actorIds) {
				paramMap.put("assignee", userId);

				List<Task> tasks = activitiTaskQueryService.getTasks(0,
						MAX_TASK_INSTANCE_QTY, paramMap);
				for (Task task : tasks) {
					if (!taskIds.contains(task.getId())) {
						taskIds.add(task.getId());
					}
				}

				paramMap.remove("assignee");
				paramMap.put("candidateUser", actorId);
				tasks = activitiTaskQueryService.getTasks(0,
						MAX_TASK_INSTANCE_QTY, paramMap);
				for (Task task : tasks) {
					if (!taskIds.contains(task.getId())) {
						taskIds.add(task.getId());
					}
				}
			}

		} catch (Exception ex) {
			logger.debug(ex);
			throw new RuntimeException(ex);
		} finally {

		}

		logger.debug(actorId + " running taskId size:" + taskIds.size());

		return taskIds;
	}

	/**
	 * ��������ʵ����Ż�ȡ����
	 * 
	 * @param taskId
	 * @param actorId
	 * @return
	 */
	public TaskItem getTaskItem(String taskId, String actorId) {
		TaskItem taskItem = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("taskId", taskId);
			paramMap.put("assignee", actorId);
			Task task = null;
			List<Task> tasks = activitiTaskQueryService.getTasks(0,
					MAX_TASK_INSTANCE_QTY, paramMap);
			if (tasks != null && !tasks.isEmpty()) {
				task = tasks.get(0);
			} else {
				paramMap.put("candidateUser", actorId);
				tasks = activitiTaskQueryService.getTasks(0,
						MAX_TASK_INSTANCE_QTY, paramMap);
				if (tasks != null && !tasks.isEmpty()) {
					task = tasks.get(0);
				}
			}

			if (task != null) {
				taskItem = this.convert(task);
			}

		} catch (Exception ex) {
			logger.debug(ex);
			throw new RuntimeException(ex);
		} finally {

		}
		return taskItem;
	}

	/**
	 * ��������ʵ����Ż�ȡ����
	 * 
	 * @param processInstanceId
	 * @param actorId
	 * @return
	 */
	public TaskItem getTaskItemByProcessInstanceId(String processInstanceId,
			String actorId) {
		TaskItem taskItem = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("processInstanceId", processInstanceId);
			paramMap.put("assignee", actorId);
			logger.debug("->paramMap:" + paramMap);
			Task task = null;
			List<Task> tasks = activitiTaskQueryService.getTasks(0,
					MAX_TASK_INSTANCE_QTY, paramMap);
			logger.debug("tasks:" + tasks);
			if (tasks != null && !tasks.isEmpty()) {
				task = tasks.get(0);
			} else {
				paramMap.put("candidateUser", actorId);
				tasks = activitiTaskQueryService.getTasks(0,
						MAX_TASK_INSTANCE_QTY, paramMap);
				if (tasks != null && !tasks.isEmpty()) {
					task = tasks.get(0);
				}
			}

			if (task != null) {
				taskItem = this.convert(task);
			}

		} catch (Exception ex) {
			logger.debug(ex);
			throw new RuntimeException(ex);
		} finally {

		}
		return taskItem;
	}

	/**
	 * ��ȡ��������ߵĴ�������
	 * 
	 * @param actorIds
	 * @return
	 */
	public List<TaskItem> getTaskItems(Collection<String> actorIds) {
		List<TaskItem> taskItems = new ArrayList<TaskItem>();
		for (String actorId : actorIds) {
			List<Task> tasks = activitiTaskQueryService.getUserTasks(actorId);
			if (tasks != null && !tasks.isEmpty()) {
				for (Task task : tasks) {
					taskItems.add(this.convert(task));
				}
			}
		}
		return taskItems;
	}

	/**
	 * ��ȡ�û��������б�
	 * 
	 * @param actorId
	 * @return
	 */
	public List<TaskItem> getTaskItems(String actorId) {
		List<TaskItem> taskItems = new ArrayList<TaskItem>();
		Collection<String> agentIds = this.getAgentIds(actorId);

		Collection<String> actorIds = new ArrayList<String>();
		actorIds.add(actorId);
		if (agentIds != null && agentIds.size() > 0) {
			actorIds.addAll(agentIds);
		}

		for (String userId : actorIds) {
			List<Task> tasks = activitiTaskQueryService.getUserTasks(userId);
			if (tasks != null && !tasks.isEmpty()) {
				for (Task task : tasks) {
					taskItems.add(this.convert(task));
				}
			}
		}

		taskItems = this.filter(actorId, taskItems);

		return taskItems;
	}

	/**
	 * ��ȡĳ������ָ���û��Ĵ�������
	 * 
	 * @param actorId
	 * @param processInstanceId
	 * @return
	 */
	public List<TaskItem> getTaskItems(String actorId, String processInstanceId) {
		List<TaskItem> taskItems = new ArrayList<TaskItem>();
		Collection<String> agentIds = this.getAgentIds(actorId);
		Collection<String> actorIds = new ArrayList<String>();
		actorIds.add(actorId);
		if (agentIds != null && agentIds.size() > 0) {
			actorIds.addAll(agentIds);
		}

		for (String userId : actorIds) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("assignee", userId);
			paramMap.put("processInstanceId", processInstanceId);
			List<Task> tasks = activitiTaskQueryService.getTasks(0,
					MAX_TASK_INSTANCE_QTY, paramMap);
			if (tasks != null && !tasks.isEmpty()) {
				for (Task task : tasks) {
					taskItems.add(this.convert(task));
				}
			}
		}

		for (String userId : actorIds) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("candidateUser", userId);
			paramMap.put("processInstanceId", processInstanceId);
			List<Task> tasks = activitiTaskQueryService.getTasks(0,
					MAX_TASK_INSTANCE_QTY, paramMap);
			if (tasks != null && !tasks.isEmpty()) {
				for (Task task : tasks) {
					taskItems.add(this.convert(task));
				}
			}
		}

		taskItems = this.filter(actorId, taskItems);

		return taskItems;
	}

	/**
	 * ��������ʵ����Ż�ȡ�û�������ʵ��
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public List<TaskItem> getTaskItemsByProcessInstanceId(
			String processInstanceId) {
		List<TaskItem> taskItems = new ArrayList<TaskItem>();

		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("processInstanceId", processInstanceId);
		List<Task> tasks = activitiTaskQueryService.getTasks(0,
				MAX_TASK_INSTANCE_QTY, paramMap);
		if (tasks != null && !tasks.isEmpty()) {
			for (Task task : tasks) {
				taskItems.add(this.convert(task));
			}
		}

		return taskItems;
	}

	/**
	 * ��������ʵ����Ż�ȡ�û�������ʵ��
	 * 
	 * @param processInstanceIds
	 * @return
	 */
	public List<TaskItem> getTaskItemsByProcessInstanceIds(
			Collection<String> processInstanceIds) {
		List<TaskItem> taskItems = new ArrayList<TaskItem>();

		for (String pid : processInstanceIds) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("processInstanceId", pid);
			List<Task> tasks = activitiTaskQueryService.getTasks(0,
					MAX_TASK_INSTANCE_QTY, paramMap);
			if (tasks != null && !tasks.isEmpty()) {
				for (Task task : tasks) {
					taskItems.add(this.convert(task));
				}
			}
		}

		return taskItems;
	}

	public Collection<String> getTransitionNames(String taskId) {
		Collection<String> transitions = new ArrayList<String>();
		try {

		} catch (Exception ex) {
			logger.debug(ex);
			throw new RuntimeException(ex);
		} finally {

		}
		return transitions;
	}

	public Collection<String> getWorkedProcessInstanceIds(String processName,
			String actorId) {
		List<String> processInstanceIds = activitiTaskQueryService
				.getWorkedProcessInstanceIds(processName, actorId);
		logger.debug("worded processInstanceIds:" + processInstanceIds);
		return processInstanceIds;
	}

	/**
	 * ��������
	 * 
	 * @param ctx
	 *            ����������
	 * 
	 * @return
	 */
	public String startProcess(ProcessContext ctx) {
		// ȷ��ÿ�����̶�ÿ��ҵ�񵥾���ͬһʱ��ֻ������һ��ʵ��
		String cacheKey = ctx.getProcessName() + "_" + ctx.getBusinessKey();
		String processInstanceId = null;
		try {
			if (cache.get(cacheKey) == null) {
				cache.put(cacheKey, "1");
				processInstanceId = activitiProcessService.startProcess(ctx);
			}
		} catch (Exception ex) {
			if (logger.isDebugEnabled()) {
				ex.printStackTrace();
				logger.debug(ex);
			}
			throw new RuntimeException(ex);
		} finally {
			cache.remove(cacheKey);
		}
		return processInstanceId;
	}

}