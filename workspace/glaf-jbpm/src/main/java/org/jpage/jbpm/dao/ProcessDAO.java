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
