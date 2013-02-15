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
	 * 获取用户
	 * 
	 * @param actorId
	 * @return
	 */
	public User getUser(JbpmContext jbpmContext, String actorId);

	/**
	 * 根据条件获取参与者
	 * 
	 * @param queryExecutor
	 * @return
	 */
	public Set getActorIds(JbpmContext jbpmContext, Executor queryExecutor);

	/**
	 * 获取用户Map。其中key是用户帐号，value是org.jpage.actor.User对象。
	 * 
	 * @return
	 */
	public Map getUserMap(JbpmContext jbpmContext);

	/**
	 * 保存某个流程任务的参与者
	 * 
	 * @param roleId
	 * @param roleUsers
	 */
	public void saveActors(JbpmContext jbpmContext, String roleId,
			List roleUsers);

	/**
	 * 保存代理人
	 * 
	 * @param jbpmContext
	 * @param actorId
	 * @param agents
	 */
	public void saveAgents(JbpmContext jbpmContext, String actorId,
			List agents, String objectValue);

	/**
	 * 获取某个流程某个任务的参与者
	 * 
	 * @param roleId
	 * @return
	 */
	public List getActors(JbpmContext jbpmContext, String roleId);

	/**
	 * 获取某个流程某些角色的用户ID
	 * 
	 * @param roleIds
	 * @return
	 */
	public List getActors(JbpmContext jbpmContext, Collection roleIds);

	/**
	 * 获取某个流程某个任务的参与者
	 * 
	 * @param roleId
	 * @return
	 */
	public List getActorIds(JbpmContext jbpmContext, String roleId);

	/**
	 * 获取某个用户的代理人列表
	 * 
	 * @param jbpmContext
	 * @param actorId
	 * @return
	 */
	public List getAgentIds(JbpmContext jbpmContext, String actorId);

	/**
	 * 获取某个用户的某个流程的代理人列表
	 * 
	 * @param jbpmContext
	 * @param actorId
	 * @param processName
	 * @return
	 */
	public List getAgentIds(JbpmContext jbpmContext, String actorId,
			String processName);

	/**
	 * 根据查询条件获取参与者
	 * 
	 * @param jbpmContext
	 * @param params
	 * @return
	 */
	public List getActors(JbpmContext jbpmContext, Map params);

	/**
	 * 根据查询条件获取代理人
	 * 
	 * @param jbpmContext
	 * @param paramMap
	 * @return
	 */
	public List getAgents(JbpmContext jbpmContext, Map paramMap);

	/**
	 * 根据查询条件获取代理人
	 * 
	 * @param jbpmContext
	 * @param paramMap
	 * @return
	 */
	public List getAgentIds(JbpmContext jbpmContext, Map paramMap);

 

}
