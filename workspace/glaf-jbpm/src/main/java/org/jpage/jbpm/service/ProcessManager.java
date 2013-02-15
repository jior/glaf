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

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jbpm.JbpmContext;
import org.jpage.core.query.paging.Page;
import org.jpage.jbpm.context.ProcessContext;
import org.jpage.jbpm.datafield.DataField;
import org.jpage.jbpm.model.TaskItem;
import org.jpage.persistence.Executor;

public interface ProcessManager {

	/**
	 * ��������
	 * 
	 * @param ctx
	 * @return
	 */
	public String startProcess(ProcessContext ctx);

	/**
	 * �������
	 * 
	 * @param ctx
	 */
	public void completeTask(ProcessContext ctx);

	/**
	 * ��ֹ����
	 * 
	 * @param ctx
	 * @return
	 */
	public void abortProcess(ProcessContext ctx);

	/**
	 * Ϊ�û���������
	 * 
	 * @param taskInstanceId
	 * @param actorIds
	 */
	public void createTaskInstances(JbpmContext jbpmContext,
			String taskInstanceId, Set actorIds);

	/**
	 * ���·�������
	 * 
	 * @param taskInstanceId
	 * @param actorIds
	 */
	public void reassignTask(JbpmContext jbpmContext, String taskInstanceId,
			Set actorIds);

	/**
	 * ���·�������
	 * 
	 * @param taskInstanceId
	 * @param actorIds
	 */
	public void reassignTask(JbpmContext jbpmContext, String taskInstanceId,
			Set actorIds, Collection<DataField> dataFields);

	/**
	 * ���·�������
	 * 
	 * @param processInstanceId
	 * @param previousActorId
	 * @param actorIds
	 */
	public void reassignTask(JbpmContext jbpmContext, String processInstanceId,
			String previousActorId, Set actorIds);

	/**
	 * ���·�������
	 * 
	 * @param processInstanceId
	 * @param previousActorId
	 * @param actorIds
	 */
	public void reassignTask(JbpmContext jbpmContext, String processInstanceId,
			String previousActorId, Set actorIds,
			Collection<DataField> dataFields);

	/**
	 * �������д���������ǰ�Ĳ����߸���Ϊ�µĲ�����
	 * 
	 * @param jbpmContext
	 * @param previousActorId
	 * @param nowActorId
	 */
	public void reassignAllTasks(JbpmContext jbpmContext,
			String previousActorId, String nowActorId);

	/**
	 * ���·�������
	 * 
	 * @param processInstanceId
	 * @param taskName
	 * @param actorIds
	 */
	public void reassignTaskByTaskName(JbpmContext jbpmContext,
			String processInstanceId, String taskName, Set actorIds);

	/**
	 * ���·�������
	 * 
	 * @param processInstanceId
	 * @param taskName
	 * @param actorIds
	 */
	public void reassignTaskByTaskName(JbpmContext jbpmContext,
			String processInstanceId, String taskName, Set actorIds,
			Collection<DataField> dataFields);

	/**
	 * ����������ʵ��
	 * 
	 * @param processInstanceId
	 * @param taskName
	 * @param actorIds
	 */
	public void createTaskInstances(JbpmContext jbpmContext,
			String processInstanceId, String taskName, Set actorIds);

	/**
	 * ����������ʵ��
	 * 
	 * @param processInstanceId
	 * @param taskName
	 * @param actorIds
	 */
	public void createTaskInstances(JbpmContext jbpmContext,
			String processInstanceId, String taskName, Set actorIds,
			Collection<DataField> dataFields);

	/**
	 * ��ȡȫ���û��Ĵ�������������Ϣϵͳ�Ĵ߰졣
	 * 
	 * @param jbpmContext
	 * @return
	 */
	public List getAllTaskItems(JbpmContext jbpmContext);

	/**
	 * ��ȡ�û��������б�
	 * 
	 * @param actorId
	 * @return
	 */
	public List getTaskItems(JbpmContext jbpmContext, String actorId);

	/**
	 * ��ȡ��������ߵĴ�������
	 * 
	 * @param jbpmContext
	 * @param actorIds
	 * @return
	 */
	public List getTaskItems(JbpmContext jbpmContext, Collection actorIds);

	/**
	 * ��������ʵ����ź��û���Ż�ȡ�û�������ʵ�����
	 * 
	 * @param actorId
	 * @param processInstanceId
	 * @return
	 */
	public List getTaskItems(JbpmContext jbpmContext, String actorId,
			String processInstanceId);

	/**
	 * ��ȡĳ������ʵ���������б�
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public List getTaskItemsByProcessInstanceId(JbpmContext jbpmContext,
			String processInstanceId);

	/**
	 * ��ȡĳ������ʵ���������б�
	 * 
	 * @param processInstanceId
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
	 * ��ȡ�û��������б�
	 * 
	 * @param actorId
	 * @return
	 */
	public List getWorkItems(JbpmContext jbpmContext, String actorId);

	/**
	 * ��ȡĳ���������°汾��ȫ������ʵ��
	 * 
	 * @param processName
	 * @return
	 */
	public List getLatestProcessInstances(JbpmContext jbpmContext,
			String processName);

	/**
	 * ��������ʵ����Ż�ȡ����
	 * 
	 * @param jbpmContext
	 * @param taskInstanceId
	 * @return
	 */
	public TaskItem getTaskItem(JbpmContext jbpmContext, String taskInstanceId);

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
	 * ��ȡĳ�����̶�������񼯺ϡ� ����key����������(String)��value��org.jbpm.taskmgmt.def.Task����
	 * 
	 * @param processDefinitionId
	 * @return
	 */
	public Map getTasks(JbpmContext jbpmContext, long processDefinitionId);

	/**
	 * ��ȡĳ�����̶�������񼯺ϡ� ����key����������(String)��value��org.jbpm.taskmgmt.def.Task����
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public Map getTasks(JbpmContext jbpmContext, String processInstanceId);

	/**
	 * ��ȡĳ�����̶�������񼯺ϡ� ����key����������(String)��value��org.jbpm.taskmgmt.def.Task����
	 * 
	 * @param processName
	 * @return
	 */
	public Map getMaxVersionTasks(JbpmContext jbpmContext, String processName);

	/**
	 * ��ȡȫ�����̶���ʵ��
	 * 
	 * @return
	 */
	public List getAllProcessDefinitions(JbpmContext jbpmContext);

	/**
	 * ��ҳ��ѯ
	 * 
	 * @param currPageNo
	 * @param pageSize
	 * @param countExecutor
	 * @param queryExecutor
	 * @return
	 */
	public Page getPage(JbpmContext jbpmContext, int currPageNo, int pageSize,
			Executor countExecutor, Executor queryExecutor);

	/**
	 * ��ȡһҳ�Ѿ���ɵ�����ʵ��
	 * 
	 * @param currPageNo
	 * @param pageSize
	 * @param processDefinitionId
	 * @return
	 */
	public Page getPageFinishedProcessInstances(JbpmContext jbpmContext,
			int currPageNo, int pageSize, long processDefinitionId);

	/**
	 * ��ȡһҳ���������е�����ʵ��
	 * 
	 * @param currPageNo
	 * @param pageSize
	 * @param processDefinitionId
	 * @return
	 */
	public Page getPageRunningProcessInstances(JbpmContext jbpmContext,
			int currPageNo, int pageSize, long processDefinitionId);

	/**
	 * ��ȡĳ�������ߵ�����ʵ��
	 * 
	 * @param processName
	 * @return
	 */
	public List getTaskInstances(JbpmContext jbpmContext, String actorId);

	/**
	 * ɾ�����̶���ʵ��
	 * 
	 * @param processDefinitionId
	 */
	public void deleteProcessDefinition(JbpmContext jbpmContext,
			String processDefinitionId);

	/**
	 * ɾ��maxResults���Ѿ���ɵĲ�����finishDate��ǰ������ʵ��
	 * 
	 * @param processDefinitionId
	 * @param maxResults
	 * @param finishDate
	 */
	public void deleteFinishedProcessInstances(JbpmContext jbpmContext,
			String processDefinitionId, int maxResults, Date finishDate);

	/**
	 * ɾ������ʵ��
	 * 
	 * @param processInstanceId
	 */
	public void deleteProcessInstance(JbpmContext jbpmContext,
			String processInstanceId);

	/**
	 * ����ĳ�����̣����̹���ʱ������Ͷ�ʱ����ͣ��
	 * 
	 * @param processInstanceId
	 */
	public void suspend(JbpmContext jbpmContext, String processInstanceId);

	/**
	 * �ָ���������̣�����Ͷ�ʱ�����¿�ʼ��
	 * 
	 * @param processInstanceId
	 */
	public void resume(JbpmContext jbpmContext, String processInstanceId);

}
