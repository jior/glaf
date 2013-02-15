/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
	 * 删除消息实例
	 * 
	 * @param processInstanceId
	 */
	public void deleteMessageInstances(JbpmContext jbpmContext,
			String processInstanceId);

	/**
	 * 删除流程实例，同时删除该流程实例的状态实例,消息实例和数据实例
	 * 
	 * @param processInstanceId
	 */
	public void deleteProcessInstance(JbpmContext jbpmContext,
			String processInstanceId);

	/**
	 * 获取任务池中的任务实例
	 * 
	 * @param actorId
	 * @param maxResults
	 * @return
	 */
	public List findPooledTaskInstances(JbpmContext jbpmContext,
			String actorId, int maxResults);

	/**
	 * 获取任务实例
	 * 
	 * @param actorId
	 * @param maxResults
	 * @return
	 */
	public List findTaskInstances(JbpmContext jbpmContext, String actorId,
			int maxResults);

	/**
	 * 根据条件获取参与者
	 * 
	 * @param queryExecutor
	 * @return
	 */
	public Set getActorIds(JbpmContext jbpmContext, Executor queryExecutor);

	/**
	 * 获取某个流程某些角色的用户ID
	 * 
	 * @param roleIds
	 * @return
	 */
	public List getActors(JbpmContext jbpmContext, Collection roleIds);

	/**
	 * 获取某个流程某个任务的用户
	 * 
	 * @param roleId
	 * @return
	 */
	public List getActors(JbpmContext jbpmContext, String roleId);

	/**
	 * 获取最新部署的流程定义实例
	 * 
	 * @param processName
	 * @return
	 */
	public DeployInstance getMaxDeployInstance(JbpmContext jbpmContext,
			String processName);

	/**
	 * 获取某个流程最新状态的实例数据
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public StateInstance getMaxStateInstance(JbpmContext jbpmContext,
			String processInstanceId);

	/**
	 * 获取下一个ID
	 * 
	 * @param objectId
	 * @param increment
	 * @return
	 */
	public Long getNextId(JbpmContext jbpmContext, String objectId,
			int increment);

	/**
	 * 获取用户Map。其中key是角色ID，value是org.jpage.actor.Actor对象。
	 * 
	 * @return
	 */
	public Map getRoleMap(JbpmContext jbpmContext, Executor queryExecutor);

	/**
	 * 获取状态实例
	 * 
	 * @param stateInstanceId
	 * @return
	 */
	public StateInstance getStateInstance(JbpmContext jbpmContext,
			String stateInstanceId);

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
	 * 
	 * @param jbpmContext
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public List getStateInstances(JbpmContext jbpmContext, String processName,
			String actorId);

	/**
	 * 获取用户Map。其中key是用户帐号，value是org.jpage.actor.User对象。
	 * 
	 * @return
	 */
	public Map getUserMap(JbpmContext jbpmContext, Executor queryExecutor);

	/**
	 * 保存某个流程任务的参与者
	 * 
	 * @param roleId
	 * @param actors
	 */
	public void saveActors(JbpmContext jbpmContext, String roleId, List actors);

	/**
	 * 保存代理人
	 * 
	 * @param jbpmContext
	 * @param actorId
	 * @param agents
	 */
	public void saveAgents(JbpmContext jbpmContext, String actorId,
			List agents, String objectValue);

}
