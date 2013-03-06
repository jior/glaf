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
	 * Ϊָ��������Ӻ�ѡ��
	 * 
	 * @param taskId
	 * @param groupId
	 */
	void addCandidateGroup(String taskId, String groupId);

	/**
	 * Ϊָ��������Ӻ�ѡ�û�
	 * 
	 * @param taskId
	 * @param actorId
	 */
	void addCandidateUser(String taskId, String actorId);

	/**
	 * Ϊָ�����������
	 * 
	 * @param taskId
	 * @param groupId
	 * @param identityLinkType
	 */
	void addGroupIdentityLink(String taskId, String groupId,
			String identityLinkType);

	/**
	 * Ϊָ����������û�
	 * 
	 * @param taskId
	 * @param userId
	 * @param identityLinkType
	 */
	void addUserIdentityLink(String taskId, String userId,
			String identityLinkType);

	/**
	 * ���������ָ����
	 * 
	 * @param taskId
	 *            ������
	 * @param actorId
	 *            �û����
	 */
	void claimTask(String taskId, String actorId);

	/**
	 * �������
	 * 
	 * @param ctx
	 */
	void completeTask(ProcessContext ctx);

	/**
	 * �������
	 * 
	 * @param taskId
	 *            ������
	 */
	void completeTask(String taskId);

	/**
	 * �������
	 * 
	 * @param taskId
	 *            ������
	 * @param variables
	 *            ������
	 */
	void completeTask(String taskId, Map<String, Object> variables);

	/**
	 * �������
	 * 
	 * @param actorId
	 *            �û����
	 * @param taskId
	 *            ������
	 * 
	 * @param variables
	 *            ������
	 */
	void completeTask(String actorId, String taskId,
			Map<String, Object> variables);

	/**
	 * ɾ������ʵ��
	 * 
	 * @param processInstanceId
	 *            ����ʵ�����
	 */
	void deleteProcessInstance(String processInstanceId, String deleteReason);

	/**
	 * ɾ������ʵ��
	 * 
	 * @param taskId
	 *            ����ʵ�����
	 */
	void deleteTask(String taskId);

	/**
	 * ɾ������ʵ��
	 * 
	 * @param taskIds
	 *            ����ʵ����ż���
	 */
	void deleteTasks(List<String> taskIds);

	/**
	 * ����ָ����������
	 * 
	 * @param taskId
	 * @param actorId
	 */
	void setAssignee(String taskId, String actorId);

	/**
	 * �����������ȼ�
	 * 
	 * @param taskId
	 * @param priority
	 */
	void setPriority(String taskId, int priority);

	/**
	 * ��ִ���ź�
	 * 
	 * @param executionId
	 */
	void signal(String executionId);

	/**
	 * ��������
	 * 
	 * @param ctx
	 *            ����������
	 * 
	 * @return
	 */
	String startProcess(ProcessContext ctx);

	/**
	 * ��������
	 * 
	 * @param actorId
	 *            ������
	 * @param processDefinitionKey
	 *            ���̶�����
	 * @return
	 */
	ProcessInstance startProcessInstanceByKey(String actorId,
			String processDefinitionKey);

	/**
	 * 
	 * @param actorId
	 *            ������
	 * @param processDefinitionKey
	 *            ���̶�����
	 * @param variables
	 *            ������
	 * @return
	 */
	ProcessInstance startProcessInstanceByKey(String actorId,
			String processDefinitionKey, Map<String, Object> variables);

	/**
	 * 
	 * @param actorId
	 *            ������
	 * @param processDefinitionKey
	 *            ���̶�����
	 * @param businessKey
	 *            ҵ������
	 * @return
	 */
	ProcessInstance startProcessInstanceByKey(String actorId,
			String processDefinitionKey, String businessKey);

	/**
	 * 
	 * @param actorId
	 *            ������
	 * @param processDefinitionKey
	 *            ���̶�����
	 * @param businessKey
	 *            ҵ������
	 * @param variables
	 *            ������
	 * @return
	 */
	ProcessInstance startProcessInstanceByKey(String actorId,
			String processDefinitionKey, String businessKey,
			Map<String, Object> variables);

}