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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jbpm.JbpmContext;
import org.jpage.actor.User;
import org.jpage.persistence.Executor;

public interface ActorManager {

	/**
	 * ��ȡ�û�
	 * 
	 * @param actorId
	 * @return
	 */
	public User getUser(JbpmContext jbpmContext, String actorId);

	/**
	 * ����������ȡ������
	 * 
	 * @param queryExecutor
	 * @return
	 */
	public Set getActorIds(JbpmContext jbpmContext, Executor queryExecutor);

	/**
	 * ��ȡ�û�Map������key���û��ʺţ�value��org.jpage.actor.User����
	 * 
	 * @return
	 */
	public Map getUserMap(JbpmContext jbpmContext);

	/**
	 * ����ĳ����������Ĳ�����
	 * 
	 * @param roleId
	 * @param roleUsers
	 */
	public void saveActors(JbpmContext jbpmContext, String roleId,
			List roleUsers);

	/**
	 * ���������
	 * 
	 * @param jbpmContext
	 * @param actorId
	 * @param agents
	 */
	public void saveAgents(JbpmContext jbpmContext, String actorId,
			List agents, String objectValue);

	/**
	 * ��ȡĳ������ĳ������Ĳ�����
	 * 
	 * @param roleId
	 * @return
	 */
	public List getActors(JbpmContext jbpmContext, String roleId);

	/**
	 * ��ȡĳ������ĳЩ��ɫ���û�ID
	 * 
	 * @param roleIds
	 * @return
	 */
	public List getActors(JbpmContext jbpmContext, Collection roleIds);

	/**
	 * ��ȡĳ������ĳ������Ĳ�����
	 * 
	 * @param roleId
	 * @return
	 */
	public List getActorIds(JbpmContext jbpmContext, String roleId);

	/**
	 * ��ȡĳ���û��Ĵ������б�
	 * 
	 * @param jbpmContext
	 * @param actorId
	 * @return
	 */
	public List getAgentIds(JbpmContext jbpmContext, String actorId);

	/**
	 * ��ȡĳ���û���ĳ�����̵Ĵ������б�
	 * 
	 * @param jbpmContext
	 * @param actorId
	 * @param processName
	 * @return
	 */
	public List getAgentIds(JbpmContext jbpmContext, String actorId,
			String processName);

	/**
	 * ���ݲ�ѯ������ȡ������
	 * 
	 * @param jbpmContext
	 * @param params
	 * @return
	 */
	public List getActors(JbpmContext jbpmContext, Map params);

	/**
	 * ���ݲ�ѯ������ȡ������
	 * 
	 * @param jbpmContext
	 * @param paramMap
	 * @return
	 */
	public List getAgents(JbpmContext jbpmContext, Map paramMap);

	/**
	 * ���ݲ�ѯ������ȡ������
	 * 
	 * @param jbpmContext
	 * @param paramMap
	 * @return
	 */
	public List getAgentIds(JbpmContext jbpmContext, Map paramMap);

 

}
