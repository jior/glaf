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
	 * 启动流程
	 * 
	 * @param ctx
	 * @return
	 */
	public String startProcess(ProcessContext ctx);

	/**
	 * 完成任务
	 * 
	 * @param ctx
	 */
	public void completeTask(ProcessContext ctx);

	/**
	 * 中止流程
	 * 
	 * @param ctx
	 * @return
	 */
	public void abortProcess(ProcessContext ctx);

	/**
	 * 为用户产生任务
	 * 
	 * @param taskInstanceId
	 * @param actorIds
	 */
	public void createTaskInstances(JbpmContext jbpmContext,
			String taskInstanceId, Set actorIds);

	/**
	 * 重新分配任务
	 * 
	 * @param taskInstanceId
	 * @param actorIds
	 */
	public void reassignTask(JbpmContext jbpmContext, String taskInstanceId,
			Set actorIds);

	/**
	 * 重新分配任务
	 * 
	 * @param taskInstanceId
	 * @param actorIds
	 */
	public void reassignTask(JbpmContext jbpmContext, String taskInstanceId,
			Set actorIds, Collection<DataField> dataFields);

	/**
	 * 重新分配任务
	 * 
	 * @param processInstanceId
	 * @param previousActorId
	 * @param actorIds
	 */
	public void reassignTask(JbpmContext jbpmContext, String processInstanceId,
			String previousActorId, Set actorIds);

	/**
	 * 重新分配任务
	 * 
	 * @param processInstanceId
	 * @param previousActorId
	 * @param actorIds
	 */
	public void reassignTask(JbpmContext jbpmContext, String processInstanceId,
			String previousActorId, Set actorIds,
			Collection<DataField> dataFields);

	/**
	 * 将流程中待办任务以前的参与者更改为新的参与者
	 * 
	 * @param jbpmContext
	 * @param previousActorId
	 * @param nowActorId
	 */
	public void reassignAllTasks(JbpmContext jbpmContext,
			String previousActorId, String nowActorId);

	/**
	 * 重新分配任务
	 * 
	 * @param processInstanceId
	 * @param taskName
	 * @param actorIds
	 */
	public void reassignTaskByTaskName(JbpmContext jbpmContext,
			String processInstanceId, String taskName, Set actorIds);

	/**
	 * 重新分配任务
	 * 
	 * @param processInstanceId
	 * @param taskName
	 * @param actorIds
	 */
	public void reassignTaskByTaskName(JbpmContext jbpmContext,
			String processInstanceId, String taskName, Set actorIds,
			Collection<DataField> dataFields);

	/**
	 * 创建新任务实例
	 * 
	 * @param processInstanceId
	 * @param taskName
	 * @param actorIds
	 */
	public void createTaskInstances(JbpmContext jbpmContext,
			String processInstanceId, String taskName, Set actorIds);

	/**
	 * 创建新任务实例
	 * 
	 * @param processInstanceId
	 * @param taskName
	 * @param actorIds
	 */
	public void createTaskInstances(JbpmContext jbpmContext,
			String processInstanceId, String taskName, Set actorIds,
			Collection<DataField> dataFields);

	/**
	 * 获取全部用户的待办任务，用于消息系统的催办。
	 * 
	 * @param jbpmContext
	 * @return
	 */
	public List getAllTaskItems(JbpmContext jbpmContext);

	/**
	 * 获取用户的任务列表
	 * 
	 * @param actorId
	 * @return
	 */
	public List getTaskItems(JbpmContext jbpmContext, String actorId);

	/**
	 * 获取多个参与者的待办任务
	 * 
	 * @param jbpmContext
	 * @param actorIds
	 * @return
	 */
	public List getTaskItems(JbpmContext jbpmContext, Collection actorIds);

	/**
	 * 根据流程实例编号和用户编号获取用户的任务实例编号
	 * 
	 * @param actorId
	 * @param processInstanceId
	 * @return
	 */
	public List getTaskItems(JbpmContext jbpmContext, String actorId,
			String processInstanceId);

	/**
	 * 获取某个流程实例的任务列表
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public List getTaskItemsByProcessInstanceId(JbpmContext jbpmContext,
			String processInstanceId);

	/**
	 * 获取某个流程实例的任务列表
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public List getTaskItemsByProcessInstanceIds(JbpmContext jbpmContext,
			Collection processInstanceIds);

	/**
	 * 获取某个流程所有版本的待办任务
	 * 
	 * @param jbpmContext
	 * @param processName
	 * @return
	 */
	public List getTaskItemsByProcessName(JbpmContext jbpmContext,
			String processName);

	/**
	 * 获取某个用户某个流程所有版本的待办任务
	 * 
	 * @param jbpmContext
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public List getTaskItemsByProcessName(JbpmContext jbpmContext,
			String processName, String actorId);

	/**
	 * 获取某个用户某些流程所有版本的待办任务
	 * 
	 * @param jbpmContext
	 * @param processNames
	 * @param actorId
	 * @return
	 */
	public List getTaskItemsByProcessName(JbpmContext jbpmContext,
			Collection processNames, String actorId);

	/**
	 * 获取某些用户某个流程所有版本的待办任务
	 * 
	 * @param jbpmContext
	 * @param processName
	 * @param actorIds
	 * @return
	 */
	public List getTaskItemsByProcessName(JbpmContext jbpmContext,
			String processName, Collection actorIds);

	/**
	 * 获取某些用户某些流程所有版本的待办任务
	 * 
	 * @param jbpmContext
	 * @param processNames
	 * @param actorIds
	 * @return
	 */
	public List getTaskItemsByProcessName(JbpmContext jbpmContext,
			Collection processNames, Collection actorIds);

	/**
	 * 获取用户的任务列表
	 * 
	 * @param actorId
	 * @return
	 */
	public List getWorkItems(JbpmContext jbpmContext, String actorId);

	/**
	 * 获取某个流程最新版本的全部流程实例
	 * 
	 * @param processName
	 * @return
	 */
	public List getLatestProcessInstances(JbpmContext jbpmContext,
			String processName);

	/**
	 * 根据任务实例编号获取任务
	 * 
	 * @param jbpmContext
	 * @param taskInstanceId
	 * @return
	 */
	public TaskItem getTaskItem(JbpmContext jbpmContext, String taskInstanceId);

	/**
	 * 获取用户已经处理过的任务
	 * 
	 * @param actorId
	 * @return
	 */
	public List getFinishedTaskItems(JbpmContext jbpmContext, String actorId);

	/**
	 * 获取用户已经处理过的任务
	 * 
	 * @param actorIds
	 * @return
	 */
	public List getFinishedTaskItems(JbpmContext jbpmContext,
			Collection actorIds);

	/**
	 * 获取用户已经处理过的任务
	 * 
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public List getFinishedTaskItems(JbpmContext jbpmContext,
			String processName, String actorId);

	/**
	 * 获取用户已经处理过的任务
	 * 
	 * @param processName
	 * @param actorIds
	 * @return
	 */
	public List getFinishedTaskItems(JbpmContext jbpmContext,
			String processName, Collection actorIds);

	/**
	 * 获取用户已经处理过的流程实例编号
	 * 
	 * @param jbpmContext
	 * @param actorIds
	 * @return
	 */
	public Collection getFinishedProcessInstanceIds(JbpmContext jbpmContext,
			Collection actorIds);

	/**
	 * 获取某个流程名称的流程实例
	 * 
	 * @param jbpmContext
	 * @param processName
	 * @return
	 */
	public Collection getProcessInstanceIds(JbpmContext jbpmContext,
			String processName);

	/**
	 * 获取某个流程定义的任务集合。 其中key是任务名称(String)，value是org.jbpm.taskmgmt.def.Task对象。
	 * 
	 * @param processDefinitionId
	 * @return
	 */
	public Map getTasks(JbpmContext jbpmContext, long processDefinitionId);

	/**
	 * 获取某个流程定义的任务集合。 其中key是任务名称(String)，value是org.jbpm.taskmgmt.def.Task对象。
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public Map getTasks(JbpmContext jbpmContext, String processInstanceId);

	/**
	 * 获取某个流程定义的任务集合。 其中key是任务名称(String)，value是org.jbpm.taskmgmt.def.Task对象。
	 * 
	 * @param processName
	 * @return
	 */
	public Map getMaxVersionTasks(JbpmContext jbpmContext, String processName);

	/**
	 * 获取全部流程定义实例
	 * 
	 * @return
	 */
	public List getAllProcessDefinitions(JbpmContext jbpmContext);

	/**
	 * 分页查询
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
	 * 获取一页已经完成的流程实例
	 * 
	 * @param currPageNo
	 * @param pageSize
	 * @param processDefinitionId
	 * @return
	 */
	public Page getPageFinishedProcessInstances(JbpmContext jbpmContext,
			int currPageNo, int pageSize, long processDefinitionId);

	/**
	 * 获取一页正在运行中的流程实例
	 * 
	 * @param currPageNo
	 * @param pageSize
	 * @param processDefinitionId
	 * @return
	 */
	public Page getPageRunningProcessInstances(JbpmContext jbpmContext,
			int currPageNo, int pageSize, long processDefinitionId);

	/**
	 * 获取某个参与者的任务实例
	 * 
	 * @param processName
	 * @return
	 */
	public List getTaskInstances(JbpmContext jbpmContext, String actorId);

	/**
	 * 删除流程定义实例
	 * 
	 * @param processDefinitionId
	 */
	public void deleteProcessDefinition(JbpmContext jbpmContext,
			String processDefinitionId);

	/**
	 * 删除maxResults条已经完成的并且在finishDate以前的流程实例
	 * 
	 * @param processDefinitionId
	 * @param maxResults
	 * @param finishDate
	 */
	public void deleteFinishedProcessInstances(JbpmContext jbpmContext,
			String processDefinitionId, int maxResults, Date finishDate);

	/**
	 * 删除流程实例
	 * 
	 * @param processInstanceId
	 */
	public void deleteProcessInstance(JbpmContext jbpmContext,
			String processInstanceId);

	/**
	 * 挂起某个流程，流程挂起时，任务和定时器暂停。
	 * 
	 * @param processInstanceId
	 */
	public void suspend(JbpmContext jbpmContext, String processInstanceId);

	/**
	 * 恢复挂起的流程，任务和定时器重新开始。
	 * 
	 * @param processInstanceId
	 */
	public void resume(JbpmContext jbpmContext, String processInstanceId);

}
