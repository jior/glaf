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
