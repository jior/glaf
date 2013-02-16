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
import java.util.Set;

import org.jbpm.JbpmContext;

public interface TaskDAO {

	/**
	 * ��ȡȫ���û��Ĵ�������������Ϣϵͳ�Ĵ߰졣
	 * 
	 * @param jbpmContext
	 * @return
	 */
	public List getAllTaskItems(JbpmContext jbpmContext);

	/**
	 * ��ȡ�û��Ĵ�������
	 * 
	 * @param actorId
	 * @return
	 */
	public List getTaskItems(JbpmContext jbpmContext, String actorId);
	
	/**
	 * ��������ʵ����Ż�ȡ�û�������ʵ��
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public List getProcessTaskItems(JbpmContext jbpmContext,
			String processInstanceId);

	/**
	 * ��ȡ��������ߵĴ�������
	 * 
	 * @param jbpmContext
	 * @param actorIds
	 * @return
	 */
	public List getTaskItems(JbpmContext jbpmContext, Collection actorIds);

	/**
	 * ��������ʵ����Ż�ȡ�û�������ʵ��
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public List getTaskItemsByProcessInstanceId(JbpmContext jbpmContext,
			String processInstanceId);

	/**
	 * ��������ʵ����Ż�ȡ�û�������ʵ��
	 * 
	 * @param processInstanceIds
	 * @return
	 */
	public List getTaskItemsByProcessInstanceIds(JbpmContext jbpmContext,
			Collection processInstanceIds);
	
	/**
	 * ��ȡĳ���������а汾�Ĵ�������
	 * 
	 * @param jbpmContext
	 * @param processName
	 * @return
	 */
	public List getTaskItemsByProcessName(JbpmContext jbpmContext,
			String processName);

	/**
	 * ��ȡĳ���û�ĳ���������а汾�Ĵ�������
	 * 
	 * @param jbpmContext
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public List getTaskItemsByProcessName(JbpmContext jbpmContext,
			String processName, String actorId);

	/**
	 * ��ȡĳ���û�ĳЩ�������а汾�Ĵ�������
	 * 
	 * @param jbpmContext
	 * @param processNames
	 * @param actorId
	 * @return
	 */
	public List getTaskItemsByProcessName(JbpmContext jbpmContext,
			Collection processNames, String actorId);

	/**
	 * ��ȡĳЩ�û�ĳ���������а汾�Ĵ�������
	 * 
	 * @param jbpmContext
	 * @param processName
	 * @param actorIds
	 * @return
	 */
	public List getTaskItemsByProcessName(JbpmContext jbpmContext,
			String processName, Collection actorIds);

	/**
	 * ��ȡĳЩ�û�ĳЩ�������а汾�Ĵ�������
	 * 
	 * @param jbpmContext
	 * @param processNames
	 * @param actorIds
	 * @return
	 */
	public List getTaskItemsByProcessName(JbpmContext jbpmContext,
			Collection processNames, Collection actorIds);

	/**
	 * ��������ʵ����ź��û���Ż�ȡ�û�������ʵ��
	 * 
	 * @param actorId
	 * @param processInstanceId
	 * @return
	 */
	public List getTaskItems(JbpmContext jbpmContext, String actorId,
			String processInstanceId);

	/**
	 * ��������ʵ����ź��û���Ż�ȡ�û�������ʵ��
	 * 
	 * @param actorIds
	 * @param processInstanceId
	 * @return
	 */
	public List getTaskItems(JbpmContext jbpmContext, Collection actorIds,
			String processInstanceId);

	/**
	 * ��ȡ�û��Ѿ������������
	 * 
	 * @param actorId
	 * @return
	 */
	public List getFinishedTaskItems(JbpmContext jbpmContext, String actorId);

	/**
	 * ��ȡ�û��Ѿ������������
	 * 
	 * @param actorIds
	 * @return
	 */
	public List getFinishedTaskItems(JbpmContext jbpmContext,
			Collection actorIds);

	/**
	 * ��ȡ�û��Ѿ������������
	 * 
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public List getFinishedTaskItems(JbpmContext jbpmContext,
			String processName, String actorId);

	/**
	 * ��ȡ�û��Ѿ������������
	 * 
	 * @param processName
	 * @param actorIds
	 * @return
	 */
	public List getFinishedTaskItems(JbpmContext jbpmContext,
			String processName, Collection actorIds);

	/**
	 * ��ȡ�û��Ѿ������������ʵ�����
	 * 
	 * @param jbpmContext
	 * @param actorIds
	 * @return
	 */
	public Collection getFinishedProcessInstanceIds(JbpmContext jbpmContext,
			Collection actorIds);

	/**
	 * ��ȡĳ���������Ƶ�����ʵ��
	 * 
	 * @param jbpmContext
	 * @param processName
	 * @return
	 */
	public Collection getProcessInstanceIds(JbpmContext jbpmContext,
			String processName);

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
