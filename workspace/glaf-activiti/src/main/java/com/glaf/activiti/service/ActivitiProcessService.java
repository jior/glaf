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

import org.activiti.engine.runtime.ProcessInstance;

import com.glaf.activiti.model.ProcessContext;

public interface ActivitiProcessService {

	/**
	 * 为指定任务添加候选组
	 * 
	 * @param taskId
	 * @param groupId
	 */
	void addCandidateGroup(String taskId, String groupId);

	/**
	 * 为指定任务添加候选用户
	 * 
	 * @param taskId
	 * @param actorId
	 */
	void addCandidateUser(String taskId, String actorId);

	/**
	 * 为指定任务添加组
	 * 
	 * @param taskId
	 * @param groupId
	 * @param identityLinkType
	 */
	void addGroupIdentityLink(String taskId, String groupId,
			String identityLinkType);

	/**
	 * 为指定任务添加用户
	 * 
	 * @param taskId
	 * @param userId
	 * @param identityLinkType
	 */
	void addUserIdentityLink(String taskId, String userId,
			String identityLinkType);

	/**
	 * 取回任务
	 * 
	 * @param taskId
	 * @return
	 */
	byte[] callback(String taskId);

	/**
	 * 分配任务给指定人
	 * 
	 * @param taskId
	 *            任务编号
	 * @param actorId
	 *            用户编号
	 */
	void claimTask(String taskId, String actorId);

	/**
	 * 完成任务
	 * 
	 * @param ctx
	 */
	void completeTask(ProcessContext ctx);

	/**
	 * 完成任务
	 * 
	 * @param taskId
	 *            任务编号
	 */
	void completeTask(String taskId);

	/**
	 * 完成任务
	 * 
	 * @param taskId
	 *            任务编号
	 * @param variables
	 *            变量集
	 */
	void completeTask(String taskId, Map<String, Object> variables);

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
	void completeTask(String actorId, String taskId,
			Map<String, Object> variables);

	/**
	 * 删除流程实例
	 * 
	 * @param processInstanceId
	 *            流程实例编号
	 */
	void deleteProcessInstance(String processInstanceId, String deleteReason);

	/**
	 * 删除任务实例
	 * 
	 * @param taskId
	 *            任务实例编号
	 */
	void deleteTask(String taskId);

	/**
	 * 删除任务实例
	 * 
	 * @param taskIds
	 *            任务实例编号集合
	 */
	void deleteTasks(List<String> taskIds);

	/**
	 * 驳回任务
	 * 
	 * @param taskId
	 * @return
	 */
	byte[] rollback(String taskId);

	/**
	 * 重新指派任务处理人
	 * 
	 * @param taskId
	 * @param actorId
	 */
	void setAssignee(String taskId, String actorId);

	/**
	 * 设置任务优先级
	 * 
	 * @param taskId
	 * @param priority
	 */
	void setPriority(String taskId, int priority);

	/**
	 * 发执行信号
	 * 
	 * @param executionId
	 */
	void signal(String executionId);

	/**
	 * 启动流程
	 * 
	 * @param ctx
	 *            流程上下文
	 * 
	 * @return
	 */
	String startProcess(ProcessContext ctx);

	/**
	 * 启动流程
	 * 
	 * @param actorId
	 *            参与者
	 * @param processDefinitionKey
	 *            流程定义名
	 * @return
	 */
	ProcessInstance startProcessInstanceByKey(String actorId,
			String processDefinitionKey);

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
	ProcessInstance startProcessInstanceByKey(String actorId,
			String processDefinitionKey, Map<String, Object> variables);

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
	ProcessInstance startProcessInstanceByKey(String actorId,
			String processDefinitionKey, String businessKey);

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
	ProcessInstance startProcessInstanceByKey(String actorId,
			String processDefinitionKey, String businessKey,
			Map<String, Object> variables);

}