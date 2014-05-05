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

import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;

import com.glaf.activiti.model.TaskItem;

public interface ActivitiTaskQueryService {

	List<Task> getAllTasks();

	List<Task> getAllTasks(String processInstanceId);

	List<Task> getAssigneeTasks(String processInstanceId);

	List<HistoricTaskInstance> getHistoricTaskInstances(String processInstanceId);

	List<TaskItem> getHistoryTasks(String processInstanceId);

	Task getTask(String taskId);

	long getTaskCount(Map<String, Object> paramMap);

	long getTaskCount(TaskQuery query);

	List<IdentityLink> getTaskIdentityLinks(String taskId);

	List<TaskItem> getTaskItems(String processInstanceId);

	List<Task> getTasks(int firstResult, int maxResults,
			Map<String, Object> paramMap);

	List<Task> getTasks(int firstResult, int maxResults, TaskQuery query);

	List<Task> getTasks(String processInstanceId);

	/**
	 * 获取用户的待办任务（包含个人及组的任务）
	 * 
	 * @param actorId
	 *            用户编号
	 * @return
	 */
	List<Task> getUserTasks(String actorId);

	/**
	 * 获取某个流程实例用户的待办任务（包含个人及组的任务）
	 * 
	 * @param actorId
	 *            用户编号
	 * @return
	 */
	List<Task> getUserTasks(String processInstanceId, String actorId);

	List<String> getWorkedProcessInstanceIds(String processName, String actorId);

}