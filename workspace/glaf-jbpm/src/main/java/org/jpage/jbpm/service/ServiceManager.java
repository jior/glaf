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


package org.jpage.jbpm.service;

import java.sql.Connection;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jbpm.JbpmContext;
import org.jpage.core.query.paging.Page;
 
import org.jpage.jbpm.model.DeployInstance;
 
import org.jpage.jbpm.model.StateInstance;
import org.jpage.persistence.Executor;

public interface ServiceManager {

	 
	/**
	 * ���沿������̶���ʵ��
	 * 
	 * @param deployInstance
	 * @return
	 */
	public void saveDeployInstance(JbpmContext jbpmContext,
			DeployInstance deployInstance);

  
	 
	/**
	 * ��������ʵ����Ż�ȡ�û�������ʵ��
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public List getProcessTaskItems(JbpmContext jbpmContext,
			String processInstanceId);

	/**
	 * ����״̬ʵ��
	 * 
	 * @param stateInstance
	 */
	public void saveStateInstance(JbpmContext jbpmContext,
			StateInstance stateInstance);

	/**
	 * ����״̬ʵ��
	 * 
	 * @param stateInstance
	 */
	public void updateStateInstance(JbpmContext jbpmContext, StateInstance stateInstance);

	/**
	 * ��ȡ���²�������̶���ʵ��
	 * 
	 * @param processName
	 * @return
	 */
	public DeployInstance getMaxDeployInstance(JbpmContext jbpmContext,
			String processName);

 
	/**
	 * ��ȡ״̬ʵ��
	 * 
	 * @param stateInstanceId
	 * @return
	 */
	public StateInstance getStateInstance(JbpmContext jbpmContext,
			String stateInstanceId);

	/**
	 * ��ȡĳ����������״̬��ʵ������
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public StateInstance getMaxStateInstance(JbpmContext jbpmContext,
			String processInstanceId);

	 
 
   

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
	 * @param jbpmContext
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public List getStateInstances(JbpmContext jbpmContext, String processName, String actorId);

 
	/**
	 * ��ȡȫ�����̲���ʵ��
	 * 
	 * @return
	 */
	public List getDeployInstances(JbpmContext jbpmContext);


	/**
	 * ִ�ж����SQL���
	 * 
	 * @param con
	 * @param executors
	 */
	public void execute(Connection con, List executors);

	/**
	 * ִ�ж����SQL���
	 * 
	 * @param con
	 * @param executor
	 * @param params
	 */
	public void execute(Connection con, Executor executor, Map params);

	 

	/**
	 * ��ȡһҳ״̬ʵ������
	 * 
	 * @param currPageNo
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public Page getPageStateInstance(JbpmContext jbpmContext, int currPageNo,
			int pageSize, Map params);

	/**
	 * ��ȡһҳ��Ϣʵ������
	 * 
	 * @param currPageNo
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public Page getPageMessageInstance(JbpmContext jbpmContext, int currPageNo,
			int pageSize, Map params);

	/**
	 * ��������ʵ����ź������Ż�ȡ������Ĵ�����
	 * 
	 * @param processInstanceId
	 * @param taskId
	 * @return
	 */
	public Set getActorIds(JbpmContext jbpmContext, String processInstanceId,
			String taskId);

}
