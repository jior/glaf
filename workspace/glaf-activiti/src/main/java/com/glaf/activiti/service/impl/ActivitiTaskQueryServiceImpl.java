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

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.activiti.model.TaskItem;
import com.glaf.activiti.service.ActivitiTaskQueryService;
import com.glaf.core.util.DateUtils;

@Service("activitiTaskQueryService")
@Transactional(readOnly = true)
public class ActivitiTaskQueryServiceImpl implements ActivitiTaskQueryService {

	protected HistoryService historyService;

	protected RepositoryService repositoryService;

	protected RuntimeService runtimeService;

	protected TaskService taskService;

	public List<Task> getAllTasks() {
		List<Task> tasks = new java.util.ArrayList<Task>();
		List<Task> rows = taskService.createTaskQuery().list();
		for (Task task : rows) {
			tasks.add(task);
		}
		return tasks;
	}

	public List<Task> getAllTasks(String processInstanceId) {
		List<Task> tasks = new java.util.ArrayList<Task>();
		List<Task> rows = taskService.createTaskQuery()
				.processInstanceId(processInstanceId).list();
		for (Task task : rows) {
			tasks.add(task);
		}
		return tasks;
	}

	public List<Task> getAssigneeTasks(String processInstanceId) {
		List<Task> tasks = new java.util.ArrayList<Task>();
		List<Task> rows = taskService.createTaskQuery()
				.processInstanceId(processInstanceId).list();
		for (Task task : rows) {
			if (task.getAssignee() != null) {
				tasks.add(task);
			}
		}
		return tasks;
	}

	public List<HistoricTaskInstance> getHistoricTaskInstances(
			String processInstanceId) {
		return historyService.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId).list();
	}

	public List<TaskItem> getHistoryTasks(String processInstanceId) {
		List<TaskItem> taskItems = new java.util.ArrayList<TaskItem>();
		List<HistoricTaskInstance> tasks = historyService
				.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId).list();
		if (tasks != null && !tasks.isEmpty()) {
			for (HistoricTaskInstance t : tasks) {
				if (t.getEndTime() != null) {
					TaskItem ti = new TaskItem();
					ti.setTaskName(t.getName());
					ti.setActivityName(t.getTaskDefinitionKey());
					ti.setId(t.getId());
					ti.setTaskDefinitionKey(t.getTaskDefinitionKey());
					ti.setCreateTime(t.getStartTime());
					ti.setStartTime(t.getStartTime());
					ti.setEndTime(t.getEndTime());
					ti.setExecutionId(t.getExecutionId());
					ti.setTaskDescription(t.getDescription());
					ti.setActorId(t.getAssignee());
					ti.setTaskInstanceId(t.getId());
					ti.setDuration(t.getDurationInMillis());
					taskItems.add(ti);
				}
			}
		}
		return taskItems;
	}

	public Task getTask(String taskId) {
		TaskQuery query = taskService.createTaskQuery();
		query.taskId(taskId);
		return query.singleResult();
	}

	public long getTaskCount(Map<String, Object> paramMap) {
		TaskQuery query = taskService.createTaskQuery();
		if (paramMap != null && paramMap.size() > 0) {
			if (paramMap.get("taskId") != null) {
				Object taskId = paramMap.get("taskId");
				query.taskId(taskId.toString());
			}

			if (paramMap.get("nameLike") != null) {
				Object nameLike = paramMap.get("nameLike");
				query.taskNameLike("%" + nameLike.toString() + "%");
			}

			if (paramMap.get("descriptionLike") != null) {
				Object descriptionLike = paramMap.get("descriptionLike");
				query.taskDescriptionLike("%" + descriptionLike.toString()
						+ "%");
			}

			if (paramMap.get("priority") != null) {
				Object priority = (Object) paramMap.get("priority");
				if (priority instanceof Integer) {
					query.taskPriority((Integer) priority);
				} else {
					query.taskPriority(Integer.parseInt(priority.toString()));
				}
			}

			if (paramMap.get("minPriority") != null) {
				Object minPriority = (Object) paramMap.get("minPriority");
				if (minPriority instanceof Integer) {
					query.taskMinPriority((Integer) minPriority);
				} else {
					query.taskMinPriority(Integer.parseInt(minPriority
							.toString()));
				}
			}

			if (paramMap.get("maxPriority") != null) {
				Object maxPriority = (Object) paramMap.get("maxPriority");
				if (maxPriority instanceof Integer) {
					query.taskMaxPriority((Integer) maxPriority);
				} else {
					query.taskMaxPriority(Integer.parseInt(maxPriority
							.toString()));
				}
			}

			if (paramMap.get("assignee") != null) {
				Object assignee = paramMap.get("assignee");
				query.taskAssignee(assignee.toString());
			}

			if (paramMap.get("owner") != null) {
				Object owner = paramMap.get("owner");
				query.taskOwner(owner.toString());
			}

			if (paramMap.get("taskUnassigned") != null) {
				query.taskUnassigned();
			}

			if (paramMap.get("candidateUser") != null) {
				Object candidateUser = paramMap.get("candidateUser");
				query.taskCandidateUser(candidateUser.toString());
			}

			if (paramMap.get("candidateGroup") != null) {
				Object candidateGroup = paramMap.get("candidateGroup");
				query.taskCandidateGroup(candidateGroup.toString());
			}

			if (paramMap.get("processInstanceId") != null) {
				Object processInstanceId = paramMap.get("processInstanceId");
				query.processInstanceId(processInstanceId.toString());
			}

			if (paramMap.get("executionId") != null) {
				Object executionId = paramMap.get("executionId");
				query.executionId(executionId.toString());
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

			if (paramMap.get("processDefinitionName") != null) {
				Object processDefinitionName = paramMap
						.get("processDefinitionName");
				query.processDefinitionName(processDefinitionName.toString());
			}

			if (paramMap.get("createTime") != null) {
				Object createTime = (Object) paramMap.get("createTime");
				if (createTime instanceof Date) {
					query.taskCreatedOn((Date) createTime);
				} else {
					Date date = DateUtils.toDate(createTime.toString());
					query.taskCreatedOn(date);
				}
			}

			if (paramMap.get("createTimeBefore") != null) {
				Object createTimeBefore = (Object) paramMap
						.get("createTimeBefore");
				if (createTimeBefore instanceof Date) {
					query.taskCreatedBefore((Date) createTimeBefore);
				} else {
					Date date = DateUtils.toDate(createTimeBefore.toString());
					query.taskCreatedBefore(date);
				}
			}

			if (paramMap.get("createTimeAfter") != null) {
				Object createTimeAfter = (Object) paramMap
						.get("createTimeAfter");
				if (createTimeAfter instanceof Date) {
					query.taskCreatedAfter((Date) createTimeAfter);
				} else {
					Date date = DateUtils.toDate(createTimeAfter.toString());
					query.taskCreatedAfter(date);
				}
			}

			if (paramMap.get("dueDate") != null) {
				Object dueDate = (Object) paramMap.get("dueDate");
				if (dueDate instanceof Date) {
					query.dueDate((Date) dueDate);
				} else {
					Date date = DateUtils.toDate(dueDate.toString());
					query.dueDate(date);
				}
			}

			if (paramMap.get("dueDateBefore") != null) {
				Object dueDateBefore = (Object) paramMap.get("dueDateBefore");
				if (dueDateBefore instanceof Date) {
					query.dueBefore((Date) dueDateBefore);
				} else {
					Date date = DateUtils.toDate(dueDateBefore.toString());
					query.dueBefore(date);
				}
			}

			if (paramMap.get("dueDateAfter") != null) {
				Object dueDateAfter = (Object) paramMap.get("dueDateAfter");
				if (dueDateAfter instanceof Date) {
					query.dueAfter((Date) dueDateAfter);
				} else {
					Date date = DateUtils.toDate(dueDateAfter.toString());
					query.dueAfter(date);
				}
			}
		}

		return query.count();
	}

	public long getTaskCount(TaskQuery query) {
		return query.count();
	}

	public List<IdentityLink> getTaskIdentityLinks(String taskId) {
		return taskService.getIdentityLinksForTask(taskId);
	}

	public List<TaskItem> getTaskItems(String processInstanceId) {
		TaskQuery query = taskService.createTaskQuery();
		query.processInstanceId(processInstanceId).orderByTaskCreateTime()
				.desc();
		List<Task> tasks = query.list();
		List<TaskItem> taskItems = new java.util.ArrayList<TaskItem>();
		for (Task task : tasks) {
			if (task.getAssignee() != null) {
				TaskItem taskItem = new TaskItem();
				taskItem.setActivityName(task.getName());
				taskItem.setActorId(task.getAssignee());
				taskItem.setCreateTime(task.getCreateTime());
				taskItem.setId(task.getId());
				taskItem.setPriority(task.getPriority());
				taskItem.setTaskName(task.getName());
				taskItem.setTaskInstanceId(task.getId());
				taskItem.setTaskDescription(task.getDescription());
				taskItem.setTaskDefinitionKey(task.getTaskDefinitionKey());
				taskItem.setProcessDefinitionId(task.getProcessDefinitionId());
				taskItems.add(taskItem);
			} else {
				List<IdentityLink> list = taskService
						.getIdentityLinksForTask(task.getId());
				for (IdentityLink link : list) {
					if (link.getUserId() != null) {
						TaskItem taskItem = new TaskItem();
						taskItem.setActivityName(task.getName());
						taskItem.setActorId(link.getUserId());
						taskItem.setCreateTime(task.getCreateTime());
						taskItem.setId(task.getId());
						taskItem.setPriority(task.getPriority());
						taskItem.setIdentityLinkType(link.getType());
						taskItem.setTaskName(task.getName());
						taskItem.setTaskInstanceId(task.getId());
						taskItem.setTaskDescription(task.getDescription());
						taskItem.setTaskDefinitionKey(task
								.getTaskDefinitionKey());
						taskItem.setProcessDefinitionId(task
								.getProcessDefinitionId());
						taskItems.add(taskItem);
					} else {
						TaskItem taskItem = new TaskItem();
						taskItem.setActivityName(task.getName());
						taskItem.setGroupId(link.getGroupId());
						taskItem.setCreateTime(task.getCreateTime());
						taskItem.setId(task.getId());
						taskItem.setPriority(task.getPriority());
						taskItem.setIdentityLinkType(link.getType());
						taskItem.setTaskName(task.getName());
						taskItem.setTaskInstanceId(task.getId());
						taskItem.setTaskDescription(task.getDescription());
						taskItem.setTaskDefinitionKey(task
								.getTaskDefinitionKey());
						taskItem.setProcessDefinitionId(task
								.getProcessDefinitionId());
						taskItems.add(taskItem);
					}
				}
			}
		}
		return taskItems;
	}

	public List<Task> getTasks(int firstResult, int maxResults,
			Map<String, Object> paramMap) {
		TaskQuery query = taskService.createTaskQuery();
		if (paramMap != null && paramMap.size() > 0) {

			if (paramMap.get("taskId") != null) {
				Object taskId = paramMap.get("taskId");
				query.taskId(taskId.toString());
			}

			if (paramMap.get("nameLike") != null) {
				Object nameLike = paramMap.get("nameLike");
				query.taskNameLike("%" + nameLike.toString() + "%");
			}

			if (paramMap.get("descriptionLike") != null) {
				Object descriptionLike = paramMap.get("descriptionLike");
				query.taskDescriptionLike("%" + descriptionLike.toString()
						+ "%");
			}

			if (paramMap.get("priority") != null) {
				Object priority = (Object) paramMap.get("priority");
				if (priority instanceof Integer) {
					query.taskPriority((Integer) priority);
				} else {
					query.taskPriority(Integer.parseInt(priority.toString()));
				}
			}

			if (paramMap.get("minPriority") != null) {
				Object minPriority = (Object) paramMap.get("minPriority");
				if (minPriority instanceof Integer) {
					query.taskMinPriority((Integer) minPriority);
				} else {
					query.taskMinPriority(Integer.parseInt(minPriority
							.toString()));
				}
			}

			if (paramMap.get("maxPriority") != null) {
				Object maxPriority = (Object) paramMap.get("maxPriority");
				if (maxPriority instanceof Integer) {
					query.taskMaxPriority((Integer) maxPriority);
				} else {
					query.taskMaxPriority(Integer.parseInt(maxPriority
							.toString()));
				}
			}

			if (paramMap.get("assignee") != null) {
				Object assignee = paramMap.get("assignee");
				query.taskAssignee(assignee.toString());
			}

			if (paramMap.get("owner") != null) {
				Object owner = paramMap.get("owner");
				query.taskOwner(owner.toString());
			}

			if (paramMap.get("taskUnassigned") != null) {
				query.taskUnassigned();
			}

			if (paramMap.get("candidateUser") != null) {
				Object candidateUser = paramMap.get("candidateUser");
				query.taskCandidateUser(candidateUser.toString());
			}

			if (paramMap.get("candidateGroup") != null) {
				Object candidateGroup = paramMap.get("candidateGroup");
				query.taskCandidateGroup(candidateGroup.toString());
			}

			if (paramMap.get("processInstanceId") != null) {
				Object processInstanceId = paramMap.get("processInstanceId");
				query.processInstanceId(processInstanceId.toString());
			}

			if (paramMap.get("executionId") != null) {
				Object executionId = paramMap.get("executionId");
				query.executionId(executionId.toString());
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

			if (paramMap.get("processDefinitionName") != null) {
				Object processDefinitionName = paramMap
						.get("processDefinitionName");
				query.processDefinitionName(processDefinitionName.toString());
			}

			if (paramMap.get("createTime") != null) {
				Object createTime = (Object) paramMap.get("createTime");
				if (createTime instanceof Date) {
					query.taskCreatedOn((Date) createTime);
				} else {
					Date date = DateUtils.toDate(createTime.toString());
					query.taskCreatedOn(date);
				}
			}

			if (paramMap.get("createTimeBefore") != null) {
				Object createTimeBefore = (Object) paramMap
						.get("createTimeBefore");
				if (createTimeBefore instanceof Date) {
					query.taskCreatedBefore((Date) createTimeBefore);
				} else {
					Date date = DateUtils.toDate(createTimeBefore.toString());
					query.taskCreatedBefore(date);
				}
			}

			if (paramMap.get("createTimeAfter") != null) {
				Object createTimeAfter = (Object) paramMap
						.get("createTimeAfter");
				if (createTimeAfter instanceof Date) {
					query.taskCreatedAfter((Date) createTimeAfter);
				} else {
					Date date = DateUtils.toDate(createTimeAfter.toString());
					query.taskCreatedAfter(date);
				}
			}

			if (paramMap.get("dueDate") != null) {
				Object dueDate = (Object) paramMap.get("dueDate");
				if (dueDate instanceof Date) {
					query.dueDate((Date) dueDate);
				} else {
					Date date = DateUtils.toDate(dueDate.toString());
					query.dueDate(date);
				}
			}

			if (paramMap.get("dueDateBefore") != null) {
				Object dueDateBefore = (Object) paramMap.get("dueDateBefore");
				if (dueDateBefore instanceof Date) {
					query.dueBefore((Date) dueDateBefore);
				} else {
					Date date = DateUtils.toDate(dueDateBefore.toString());
					query.dueBefore(date);
				}
			}

			if (paramMap.get("dueDateAfter") != null) {
				Object dueDateAfter = (Object) paramMap.get("dueDateAfter");
				if (dueDateAfter instanceof Date) {
					query.dueAfter((Date) dueDateAfter);
				} else {
					Date date = DateUtils.toDate(dueDateAfter.toString());
					query.dueAfter(date);
				}
			}

			if (paramMap.get("orderByTaskName") != null) {
				query.orderByTaskName();
			}

			if (paramMap.get("orderByExecutionId") != null) {
				query.orderByExecutionId();
			}

			if (paramMap.get("orderByProcessInstanceId") != null) {
				query.orderByProcessInstanceId();
			}

			if (paramMap.get("orderByTaskAssignee") != null) {
				query.orderByTaskAssignee();
			}

			if (paramMap.get("orderByTaskCreateTime") != null) {
				query.orderByTaskCreateTime();
			}

			if (paramMap.get("orderByTaskDescription") != null) {
				query.orderByTaskDescription();
			}

			if (paramMap.get("orderByTaskId") != null) {
				query.orderByTaskId();
			}

			if (paramMap.get("orderByTaskPriority") != null) {
				query.orderByTaskPriority();
			}

			if (paramMap.get("orderAsc") != null) {
				query.asc();
			}

			if (paramMap.get("orderDesc") != null) {
				query.desc();
			}

		} else {
			query.orderByTaskCreateTime();
			query.desc();
		}

		if (firstResult >= 0 && maxResults > 0) {
			return query.listPage(firstResult, maxResults);
		}

		return query.list();
	}

	public List<Task> getTasks(int firstResult, int maxResults, TaskQuery query) {
		if (firstResult >= 0 && maxResults > 0) {
			return query.listPage(firstResult, maxResults);
		}

		return query.list();
	}

	public List<Task> getTasks(String processInstanceId) {
		TaskQuery query = taskService.createTaskQuery();
		query.processInstanceId(processInstanceId);
		query.orderByTaskCreateTime();
		query.desc();
		return query.list();
	}

	/**
	 * 获取用户的代办任务（包含个人及组的任务）
	 * 
	 * @param actorId
	 *            用户编号
	 * @return
	 */
	public List<Task> getUserTasks(String actorId) {
		List<Task> tasks = new java.util.ArrayList<Task>();
		TaskQuery query01 = taskService.createTaskQuery();
		query01.taskAssignee(actorId);
		List<Task> tasks01 = query01.list();
		if (tasks01 != null && tasks01.size() > 0) {
			for (Task task : tasks01) {
				tasks.add(task);
			}
		}

		TaskQuery query02 = taskService.createTaskQuery();
		query02.taskCandidateUser(actorId);
		List<Task> tasks02 = query02.list();
		if (tasks02 != null && tasks02.size() > 0) {
			for (Task task : tasks02) {
				tasks.add(task);
			}
		}

		return tasks;
	}

	public List<Task> getUserTasks(String processInstanceId, String actorId) {
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

		TaskQuery query02 = taskService.createTaskQuery();
		query02.taskCandidateUser(actorId);
		query02.processInstanceId(processInstanceId);
		List<Task> tasks02 = query02.list();
		if (tasks02 != null && tasks02.size() > 0) {
			for (Task task : tasks02) {
				tasks.add(task);
			}
		}

		return tasks;
	}

	public List<String> getWorkedProcessInstanceIds(String processName,
			String actorId) {
		List<String> processInstanceIds = new java.util.ArrayList<String>();
		HistoricTaskInstanceQuery query = historyService
				.createHistoricTaskInstanceQuery();
		query.processDefinitionKey(processName);
		query.taskAssignee(actorId);
		List<HistoricTaskInstance> list = query.list();
		if (list != null && !list.isEmpty()) {
			for (HistoricTaskInstance t : list) {
				processInstanceIds.add(t.getProcessInstanceId());
			}
		}

		query = historyService.createHistoricTaskInstanceQuery();
		query.processDefinitionKey(processName);
		query.taskOwner(actorId);
		list = query.list();
		if (list != null && !list.isEmpty()) {
			for (HistoricTaskInstance t : list) {
				processInstanceIds.add(t.getProcessInstanceId());
			}
		}

		return processInstanceIds;
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