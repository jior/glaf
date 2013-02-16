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
	 * 保存部署的流程定义实例
	 * 
	 * @param deployInstance
	 * @return
	 */
	public void saveDeployInstance(JbpmContext jbpmContext,
			DeployInstance deployInstance);

  
	 
	/**
	 * 根据流程实例编号获取用户的任务实例
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public List getProcessTaskItems(JbpmContext jbpmContext,
			String processInstanceId);

	/**
	 * 保存状态实例
	 * 
	 * @param stateInstance
	 */
	public void saveStateInstance(JbpmContext jbpmContext,
			StateInstance stateInstance);

	/**
	 * 保存状态实例
	 * 
	 * @param stateInstance
	 */
	public void updateStateInstance(JbpmContext jbpmContext, StateInstance stateInstance);

	/**
	 * 获取最新部署的流程定义实例
	 * 
	 * @param processName
	 * @return
	 */
	public DeployInstance getMaxDeployInstance(JbpmContext jbpmContext,
			String processName);

 
	/**
	 * 获取状态实例
	 * 
	 * @param stateInstanceId
	 * @return
	 */
	public StateInstance getStateInstance(JbpmContext jbpmContext,
			String stateInstanceId);

	/**
	 * 获取某个流程最新状态的实例数据
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public StateInstance getMaxStateInstance(JbpmContext jbpmContext,
			String processInstanceId);

	 
 
   

	/**
	 * 获取状态实例
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public List getStateInstances(JbpmContext jbpmContext,
			String processInstanceId);
	
	
	/**
	 * 获取状态实例
	 * @param jbpmContext
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public List getStateInstances(JbpmContext jbpmContext, String processName, String actorId);

 
	/**
	 * 获取全部流程部署实例
	 * 
	 * @return
	 */
	public List getDeployInstances(JbpmContext jbpmContext);


	/**
	 * 执行定义的SQL语句
	 * 
	 * @param con
	 * @param executors
	 */
	public void execute(Connection con, List executors);

	/**
	 * 执行定义的SQL语句
	 * 
	 * @param con
	 * @param executor
	 * @param params
	 */
	public void execute(Connection con, Executor executor, Map params);

	 

	/**
	 * 获取一页状态实例对象
	 * 
	 * @param currPageNo
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public Page getPageStateInstance(JbpmContext jbpmContext, int currPageNo,
			int pageSize, Map params);

	/**
	 * 获取一页消息实例对象
	 * 
	 * @param currPageNo
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public Page getPageMessageInstance(JbpmContext jbpmContext, int currPageNo,
			int pageSize, Map params);

	/**
	 * 根据流程实例编号和任务编号获取该任务的处理者
	 * 
	 * @param processInstanceId
	 * @param taskId
	 * @return
	 */
	public Set getActorIds(JbpmContext jbpmContext, String processInstanceId,
			String taskId);

}
