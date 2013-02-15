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
