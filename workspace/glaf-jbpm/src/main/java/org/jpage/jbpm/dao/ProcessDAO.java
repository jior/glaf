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


package org.jpage.jbpm.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jbpm.JbpmContext;

import org.jpage.jbpm.model.DeployInstance;
import org.jpage.jbpm.model.StateInstance;
import org.jpage.persistence.Executor;

public interface ProcessDAO {

	/**
	 * ɾ����Ϣʵ��
	 * 
	 * @param processInstanceId
	 */
	public void deleteMessageInstances(JbpmContext jbpmContext,
			String processInstanceId);

	/**
	 * ɾ������ʵ����ͬʱɾ��������ʵ����״̬ʵ��,��Ϣʵ��������ʵ��
	 * 
	 * @param processInstanceId
	 */
	public void deleteProcessInstance(JbpmContext jbpmContext,
			String processInstanceId);

	/**
	 * ��ȡ������е�����ʵ��
	 * 
	 * @param actorId
	 * @param maxResults
	 * @return
	 */
	public List findPooledTaskInstances(JbpmContext jbpmContext,
			String actorId, int maxResults);

	/**
	 * ��ȡ����ʵ��
	 * 
	 * @param actorId
	 * @param maxResults
	 * @return
	 */
	public List findTaskInstances(JbpmContext jbpmContext, String actorId,
			int maxResults);

	/**
	 * ����������ȡ������
	 * 
	 * @param queryExecutor
	 * @return
	 */
	public Set getActorIds(JbpmContext jbpmContext, Executor queryExecutor);

	/**
	 * ��ȡĳ������ĳЩ��ɫ���û�ID
	 * 
	 * @param roleIds
	 * @return
	 */
	public List getActors(JbpmContext jbpmContext, Collection roleIds);

	/**
	 * ��ȡĳ������ĳ��������û�
	 * 
	 * @param roleId
	 * @return
	 */
	public List getActors(JbpmContext jbpmContext, String roleId);

	/**
	 * ��ȡ���²�������̶���ʵ��
	 * 
	 * @param processName
	 * @return
	 */
	public DeployInstance getMaxDeployInstance(JbpmContext jbpmContext,
			String processName);

	/**
	 * ��ȡĳ����������״̬��ʵ������
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public StateInstance getMaxStateInstance(JbpmContext jbpmContext,
			String processInstanceId);

	/**
	 * ��ȡ��һ��ID
	 * 
	 * @param objectId
	 * @param increment
	 * @return
	 */
	public Long getNextId(JbpmContext jbpmContext, String objectId,
			int increment);

	/**
	 * ��ȡ�û�Map������key�ǽ�ɫID��value��org.jpage.actor.Actor����
	 * 
	 * @return
	 */
	public Map getRoleMap(JbpmContext jbpmContext, Executor queryExecutor);

	/**
	 * ��ȡ״̬ʵ��
	 * 
	 * @param stateInstanceId
	 * @return
	 */
	public StateInstance getStateInstance(JbpmContext jbpmContext,
			String stateInstanceId);

	/**
	 * ��ȡ״̬ʵ��
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public List getStateInstances(JbpmContext jbpmContext,
			String processInstanceId);

	/**
	 * ��ȡ״̬ʵ��
	 * 
	 * @param jbpmContext
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public List getStateInstances(JbpmContext jbpmContext, String processName,
			String actorId);

	/**
	 * ��ȡ�û�Map������key���û��ʺţ�value��org.jpage.actor.User����
	 * 
	 * @return
	 */
	public Map getUserMap(JbpmContext jbpmContext, Executor queryExecutor);

	/**
	 * ����ĳ����������Ĳ�����
	 * 
	 * @param roleId
	 * @param actors
	 */
	public void saveActors(JbpmContext jbpmContext, String roleId, List actors);

	/**
	 * ���������
	 * 
	 * @param jbpmContext
	 * @param actorId
	 * @param agents
	 */
	public void saveAgents(JbpmContext jbpmContext, String actorId,
			List agents, String objectValue);

}
