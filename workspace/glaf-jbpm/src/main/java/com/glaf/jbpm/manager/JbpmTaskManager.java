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

package com.glaf.jbpm.manager;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.PooledActor;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;

import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.util.LogUtils;
import com.glaf.core.util.Paging;
import com.glaf.jbpm.dao.JbpmEntityDAO;
import com.glaf.jbpm.dao.JbpmTaskDAO;
import com.glaf.jbpm.model.ActivityInstance;
import com.glaf.jbpm.model.TaskItem;
import com.glaf.jbpm.query.ProcessQuery;

public class JbpmTaskManager {
	protected static final Log logger = LogFactory
			.getLog(JbpmTaskManager.class);

	private JbpmTaskDAO jbpmTaskDAO;

	private JbpmEntityDAO jbpmEntityDAO;

	public JbpmTaskManager() {
		jbpmTaskDAO = new JbpmTaskDAO();
		jbpmEntityDAO = new JbpmEntityDAO();
	}

	/**
	 * 为用户产生任务
	 * 
	 * @param taskInstanceId
	 * @param actorIds
	 */
	public void createTaskInstances(JbpmContext jbpmContext,
			Long taskInstanceId, Set<String> actorIds) {
		ProcessInstance processInstance = null;
		TaskInstance taskInstance = null;
		jbpmContext.setActorId("0");
		if (taskInstanceId != null) {
			taskInstance = jbpmContext
					.loadTaskInstanceForUpdate(taskInstanceId);
			processInstance = taskInstance.getToken().getProcessInstance();
			if (processInstance.hasEnded()) {
				throw new JbpmException("process has finished");
			}

			if (taskInstance.hasEnded()) {
				throw new JbpmException("task has finished");
			}

			if (actorIds != null && actorIds.size() > 0) {
				final TaskMgmtInstance tmi = processInstance
						.getTaskMgmtInstance();
				final Task task = taskInstance.getTask();
				final Token token = taskInstance.getToken();
				final Iterator<String> iterator = actorIds.iterator();
				while (iterator.hasNext()) {
					String actorId = iterator.next();
					TaskInstance ti = new TaskInstance();
					ti.setPriority(5);
					ti.setToken(token);
					ti.setTask(task);
					ti.setActorId(actorId);
					ti.setCreate(new Date());
					ti.setName(task.getName());
					ti.setTaskMgmtInstance(tmi);
					tmi.addTaskInstance(ti);
					if (LogUtils.isDebug()) {
						logger.debug("create task for " + actorId);
					}
				}
			}
		}
	}

	/**
	 * 创建新任务实例
	 * 
	 * @param processInstanceId
	 * @param taskName
	 * @param actorIds
	 */
	public void createTaskInstances(JbpmContext jbpmContext,
			Long processInstanceId, String taskName, Collection<String> actorIds) {
		if (processInstanceId == null) {
			throw new JbpmException("processInstanceId is null");
		}
		if (StringUtils.isEmpty(taskName)) {
			throw new JbpmException("taskName is null");
		}
		if (actorIds == null || actorIds.size() == 0) {
			throw new JbpmException("actorIds is null");
		}
		if (LogUtils.isDebug()) {
			logger.debug("processInstanceId:" + processInstanceId);
			logger.debug("taskName:" + taskName);
			logger.debug("actorIds:" + actorIds);
		}
		ProcessInstance processInstance = null;
		processInstance = jbpmContext.getProcessInstance(processInstanceId);
		if (processInstance == null) {
			throw new JbpmException("process instance is null");
		}
		if (processInstance.hasEnded()) {
			throw new JbpmException("process has finished");
		}
		final TaskMgmtInstance tmi = processInstance.getTaskMgmtInstance();
		final Task task = tmi.getTaskMgmtDefinition().getTask(taskName);
		if (task != null) {
			final Iterator<String> iter99 = actorIds.iterator();
			while (iter99.hasNext()) {
				String actorId = iter99.next();
				TaskInstance xx = tmi.createTaskInstance(task, tmi
						.getProcessInstance().getRootToken());
				xx.setActorId(actorId);
				xx.setCreate(new Date());
				xx.setSignalling(true);
			}
		}
	}

	public Map<Long, ActivityInstance> getActivityInstanceMap(
			JbpmContext jbpmContext, Long processInstanceId) {
		final Map<Long, ActivityInstance> workedMap = new LinkedHashMap<Long, ActivityInstance>();
		final List<ActivityInstance> rows = this.getActivityInstances(
				jbpmContext, processInstanceId);
		if (rows != null && rows.size() > 0) {
			for (int i = 0; i < rows.size(); i++) {
				ActivityInstance activityInstance = rows.get(i);
				workedMap.put(activityInstance.getTaskInstanceId(), activityInstance);
			}
		}
		return workedMap;
	}

	/**
	 * 获取某个流程实例由用户处理的任务实例
	 * 
	 * @param jbpmContext
	 * @param processInstanceId
	 * @return
	 */
	public List<ActivityInstance> getActivityInstances(JbpmContext jbpmContext,
			Long processInstanceId) {
		final List<ActivityInstance> rows = new java.util.ArrayList<ActivityInstance>();
		final SqlExecutor queryExecutor = new SqlExecutor();
		queryExecutor
				.setSql(" select a from "
						+ ActivityInstance.class.getSimpleName()
						+ " as a where a.processInstanceId = :processInstanceId order by a.id asc ");
		final Map<String, Object> paramMap = new java.util.HashMap<String, Object>();
		paramMap.put("processInstanceId", processInstanceId);
		queryExecutor.setParameter(paramMap);

		final List<?> list = jbpmEntityDAO.getList(jbpmContext, queryExecutor);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				rows.add((ActivityInstance) list.get(i));
			}
		}
		return rows;
	}

	/**
	 * 获取全部用户的待办任务，用于消息系统的催办。
	 * 
	 * @param jbpmContext
	 * @return
	 */
	public List<TaskItem> getAllTaskItems(JbpmContext jbpmContext) {
		return jbpmTaskDAO.getAllTaskItems(jbpmContext);
	}

	/**
	 * 获取用户已经处理过并且流程已经完成的实例编号
	 * 
	 * @param jbpmContext
	 * @param actorIds
	 * @return
	 */
	public Collection<Long> getFinishedProcessInstanceIds(
			JbpmContext jbpmContext, Collection<String> actorIds) {
		ProcessQuery query = new ProcessQuery();
		query.setActorIds(actorIds);
		return jbpmTaskDAO.getFinishedProcessInstanceIds(jbpmContext, query);
	}

	/**
	 * 获取用户已经处理过并且流程已经完成的实例编号
	 * 
	 * @param jbpmContext
	 * @param paramMap
	 * @return
	 */
	public Collection<Long> getFinishedProcessInstanceIds(
			JbpmContext jbpmContext, ProcessQuery query) {
		return jbpmTaskDAO.getFinishedProcessInstanceIds(jbpmContext, query);
	}

	public JbpmEntityDAO getJbpmEntityDAO() {
		return jbpmEntityDAO;
	}

	/**
	 * 分页查询
	 * 
	 * @param currPageNo
	 * @param pageSize
	 * @param countExecutor
	 * @param queryExecutor
	 * @return
	 */
	public Paging getPage(JbpmContext jbpmContext, int currPageNo,
			int pageSize, SqlExecutor countExecutor, SqlExecutor queryExecutor) {
		return jbpmEntityDAO.getPage(jbpmContext, currPageNo, pageSize,
				countExecutor, queryExecutor);
	}

	/**
	 * 获取一页任务实例
	 * 
	 * @param jbpmContext
	 * @param pageNo
	 * @param pageSize
	 * @param paramMap
	 * @return
	 */
	public Paging getPageTaskInstances(JbpmContext jbpmContext, int pageNo,
			int pageSize, ProcessQuery query) {
		Map<String, Object> params = new java.util.HashMap<String, Object>();

		SqlExecutor countExecutor = new SqlExecutor();
		SqlExecutor queryExecutor = new SqlExecutor();

		StringBuffer countSQL = new StringBuffer();
		StringBuffer querySQL = new StringBuffer();
		StringBuffer whereSQL = new StringBuffer();

		countSQL.append(" select count(ti.id) from org.jbpm.taskmgmt.exe.TaskInstance as ti where 1=1 ");

		querySQL.append(" select ti from org.jbpm.taskmgmt.exe.TaskInstance as ti where 1=1 ");

		if (query.getTaskName() != null) {
			params.put("taskName", "%" + query.getTaskName() + "%");
			whereSQL.append(" and ti.name like :taskName ");
		}

		if (query.getAfterTaskCreateDate() != null) {
			params.put("afterTaskCreateDate", query.getAfterTaskCreateDate());
			whereSQL.append(" and ( ti.create >= :afterTaskCreateDate )");
		}

		if (query.getBeforeTaskCreateDate() != null) {
			params.put("beforeTaskCreateDate", query.getBeforeTaskCreateDate());
			whereSQL.append(" and ( ti.create <= :beforeTaskCreateDate )");
		}

		if (query.getAfterTaskStartDate() != null) {
			params.put("afterTaskStartDate", query.getAfterTaskStartDate());
			whereSQL.append(" and ( ti.start >= :afterTaskStartDate )");
		}

		if (query.getBeforeTaskStartDate() != null) {
			params.put("beforeTaskStartDate", query.getBeforeTaskStartDate());
			whereSQL.append(" and ( ti.start <= :beforeTaskStartDate )");
		}

		if (query.getAfterTaskEndDate() != null) {
			params.put("afterTaskEndDate", query.getAfterTaskEndDate());
			whereSQL.append(" and ( ti.end >= :afterTaskEndDate )");
		}

		if (query.getBeforeTaskEndDate() != null) {
			params.put("beforeTaskEndDate", query.getBeforeTaskEndDate());
			whereSQL.append(" and ( ti.end <= :beforeTaskEndDate )");
		}

		if (query.getTaskType() != null) {
			if (StringUtils.equals(query.getTaskType(), "running")) {
				whereSQL.append(" and ti.end is null ");
			} else if (StringUtils.equals(query.getTaskType(), "finished")) {
				whereSQL.append(" and ti.end is not null ");
			}
		}

		countSQL.append(whereSQL.toString());
		querySQL.append(whereSQL.toString());

		querySQL.append(" order by ti.id desc ");

		countExecutor.setSql(countSQL.toString());
		countExecutor.setParameter(params);

		queryExecutor.setSql(querySQL.toString());
		queryExecutor.setParameter(params);

		if (LogUtils.isDebug()) {
			logger.debug(queryExecutor.getSql());
			logger.debug(queryExecutor.getParameter());
		}

		Paging page = jbpmEntityDAO.getPage(jbpmContext, pageNo, pageSize,
				countExecutor, queryExecutor);

		return page;
	}

	/**
	 * 获取某个参与者的任务实例
	 * 
	 * @param actorId
	 * @return
	 */
	public List<TaskInstance> getTaskInstances(JbpmContext jbpmContext,
			String actorId) {
		final List<?> taskInstances = jbpmContext.getTaskList(actorId);
		final List<String> actorIds = new java.util.ArrayList<String>();
		actorIds.add(actorId);
		final List<?> pooledTaskinstances = jbpmContext
				.getGroupTaskList(actorIds);
		final List<TaskInstance> rows = new java.util.ArrayList<TaskInstance>();
		if (taskInstances != null && taskInstances.size() > 0) {
			for (int i = 0; i < taskInstances.size(); i++) {
				rows.add((TaskInstance) taskInstances.get(i));
			}
		}
		if (pooledTaskinstances != null && pooledTaskinstances.size() > 0) {
			for (int i = 0; i < pooledTaskinstances.size(); i++) {
				rows.add((TaskInstance) pooledTaskinstances.get(i));
			}
		}
		return rows;
	}

	/**
	 * 根据任务实例编号获取任务
	 * 
	 * @param jbpmContext
	 * @param taskInstanceId
	 * @return
	 */
	public TaskItem getTaskItem(JbpmContext jbpmContext, Long taskInstanceId) {
		TaskInstance taskInstance = jbpmContext.getTaskInstance(Long
				.valueOf(taskInstanceId));
		ProcessInstance processInstance = taskInstance.getToken()
				.getProcessInstance();
		ProcessDefinition processDefinition = processInstance
				.getProcessDefinition();
		Long processInstanceId = processInstance.getId();
		TaskItem taskItem = new TaskItem();
		taskItem.setCreateDate(taskInstance.getCreate());
		taskItem.setTaskDescription(taskInstance.getDescription());

		taskItem.setTaskInstanceId(taskInstanceId);
		taskItem.setTaskName(taskInstance.getName());
		taskItem.setProcessDefinitionId(processDefinition.getId());
		taskItem.setProcessInstanceId(processInstanceId);

		taskItem.setProcessName(processDefinition.getName());
		if (StringUtils.isNotEmpty(taskInstance.getActorId())) {
			taskItem.setActorId(taskInstance.getActorId());
		} else {
			Set<PooledActor> pooledActors = taskInstance.getPooledActors();
			if (pooledActors != null && pooledActors.size() > 0) {
				final StringBuffer buffer = new StringBuffer();
				final Iterator<PooledActor> iter = pooledActors.iterator();
				while (iter.hasNext()) {
					PooledActor pa = iter.next();
					buffer.append(pa.getActorId());
					if (iter.hasNext()) {
						buffer.append(',');
					}
				}
				taskItem.setActorId(buffer.toString());
			}
		}
		return taskItem;
	}

	/**
	 * 获取多个参与者的待办任务
	 * 
	 * @param jbpmContext
	 * @param actorIds
	 * @return
	 */
	public List<TaskItem> getTaskItems(JbpmContext jbpmContext,
			Collection<String> actorIds) {
		ProcessQuery query = new ProcessQuery();
		query.setActorIds(actorIds);
		return this.getTaskItems(jbpmContext, query);
	}

	/**
	 * 根据参数获取用户的任务实例
	 * 
	 * @param params
	 * @return
	 */
	public List<TaskItem> getTaskItems(JbpmContext jbpmContext,
			ProcessQuery query) {
		List<TaskItem> taskItems = new java.util.ArrayList<TaskItem>();
		if (query != null) {
			Set<String> rows = new HashSet<String>();
			List<TaskItem> rowsx = jbpmTaskDAO.getTaskItems(jbpmContext, query);
			if (rowsx != null && rowsx.size() > 0) {
				Iterator<TaskItem> iterator = rowsx.iterator();
				while (iterator.hasNext()) {
					TaskItem ti = iterator.next();
					String key = ti.getTaskInstanceId() + "-" + ti.getActorId();
					if (!rows.contains(key)) {
						rows.add(key);
						taskItems.add(ti);
					}
				}
			}
		}
		return taskItems;
	}

	/**
	 * 获取用户的待办任务
	 * 
	 * @param actorId
	 * @return
	 */
	public List<TaskItem> getTaskItems(JbpmContext jbpmContext, String actorId) {
		ProcessQuery query = new ProcessQuery();
		query.setActorId(actorId);
		return this.getTaskItems(jbpmContext, query);
	}

	/**
	 * 根据流程实例编号和用户编号获取用户的任务实例编号
	 * 
	 * @param actorId
	 * @param processInstanceId
	 * @return
	 */
	public List<TaskItem> getTaskItems(JbpmContext jbpmContext, String actorId,
			Long processInstanceId) {
		ProcessQuery query = new ProcessQuery();
		query.setActorId(actorId);
		query.setProcessInstanceId(processInstanceId);
		return this.getTaskItems(jbpmContext, query);
	}

	/**
	 * 获取某个流程实例的任务列表
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public List<TaskItem> getTaskItemsByProcessInstanceId(
			JbpmContext jbpmContext, Long processInstanceId) {
		ProcessQuery query = new ProcessQuery();
		query.setProcessInstanceId(processInstanceId);
		return this.getTaskItems(jbpmContext, query);
	}

	/**
	 * 根据流程实例编号获取用户的任务实例
	 * 
	 * @param processInstanceIds
	 * @return
	 */
	public List<TaskItem> getTaskItemsByProcessInstanceIds(
			JbpmContext jbpmContext, Collection<Long> processInstanceIds) {
		ProcessQuery query = new ProcessQuery();
		query.setProcessInstanceIds(processInstanceIds);
		return this.getTaskItems(jbpmContext, query);
	}

	/**
	 * 获取某些用户某个流程所有版本的待办任务
	 * 
	 * @param jbpmContext
	 * @param processNames
	 * @param actorIds
	 * @return
	 */
	public List<TaskItem> getTaskItemsByProcessName(JbpmContext jbpmContext,
			Collection<String> processNames, Collection<String> actorIds) {
		ProcessQuery query = new ProcessQuery();
		query.setActorIds(actorIds);
		query.setProcessNames(processNames);
		return this.getTaskItems(jbpmContext, query);
	}

	/**
	 * 获取某个用户某个流程所有版本的待办任务
	 * 
	 * @param jbpmContext
	 * @param processNames
	 * @param actorId
	 * @return
	 */
	public List<TaskItem> getTaskItemsByProcessName(JbpmContext jbpmContext,
			Collection<String> processNames, String actorId) {
		ProcessQuery query = new ProcessQuery();
		query.setActorId(actorId);
		query.setProcessNames(processNames);
		return this.getTaskItems(jbpmContext, query);
	}

	/**
	 * 获取某个流程所有版本的待办任务
	 * 
	 * @param jbpmContext
	 * @param processName
	 * @return
	 */
	public List<TaskItem> getTaskItemsByProcessName(JbpmContext jbpmContext,
			String processName) {
		ProcessQuery query = new ProcessQuery();
		query.setProcessName(processName);
		return this.getTaskItems(jbpmContext, query);
	}

	/**
	 * 获取某些用户某个流程所有版本的待办任务
	 * 
	 * @param jbpmContext
	 * @param processName
	 * @param actorIds
	 * @return
	 */
	public List<TaskItem> getTaskItemsByProcessName(JbpmContext jbpmContext,
			String processName, Collection<String> actorIds) {
		ProcessQuery query = new ProcessQuery();
		query.setActorIds(actorIds);
		query.setProcessName(processName);
		return this.getTaskItems(jbpmContext, query);
	}

	/**
	 * 获取某个用户某个流程所有版本的待办任务
	 * 
	 * @param jbpmContext
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public List<TaskItem> getTaskItemsByProcessName(JbpmContext jbpmContext,
			String processName, String actorId) {
		ProcessQuery query = new ProcessQuery();
		query.setActorId(actorId);
		query.setProcessName(processName);
		return this.getTaskItems(jbpmContext, query);
	}

	/**
	 * 获取用户已经处理过的流程实例编号
	 * 
	 * @param params
	 * @return
	 */
	public Collection<Long> getWorkedProcessInstanceIds(
			JbpmContext jbpmContext, ProcessQuery query) {
		return jbpmTaskDAO.getWorkedProcessInstanceIds(jbpmContext, query);
	}

	/**
	 * 获取用户已经处理过的任务
	 * 
	 * @param actorIds
	 * @return
	 */
	public List<TaskItem> getWorkedTaskItems(JbpmContext jbpmContext,
			Collection<String> actorIds) {
		ProcessQuery query = new ProcessQuery();
		query.setActorIds(actorIds);
		return this.getWorkedTaskItems(jbpmContext, query);
	}

	/**
	 * 获取用户已经处理过的任务
	 * 
	 * @param params
	 * @return
	 */
	public List<TaskItem> getWorkedTaskItems(JbpmContext jbpmContext,
			ProcessQuery query) {
		return jbpmTaskDAO.getWorkedTaskItems(jbpmContext, query);
	}

	/**
	 * 获取用户已经处理过的任务
	 * 
	 * @param actorId
	 * @return
	 */
	public List<TaskItem> getWorkedTaskItems(JbpmContext jbpmContext,
			String actorId) {
		ProcessQuery query = new ProcessQuery();
		query.setActorId(actorId);
		return this.getWorkedTaskItems(jbpmContext, query);
	}

	/**
	 * 获取用户已经处理过的任务
	 * 
	 * @param processName
	 * @param actorIds
	 * @return
	 */
	public List<TaskItem> getWorkedTaskItems(JbpmContext jbpmContext,
			String processName, Collection<String> actorIds) {
		ProcessQuery query = new ProcessQuery();
		query.setActorIds(actorIds);
		query.setProcessName(processName);
		return this.getWorkedTaskItems(jbpmContext, query);
	}

	/**
	 * 获取用户已经处理过的任务
	 * 
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public List<TaskItem> getWorkedTaskItems(JbpmContext jbpmContext,
			String processName, String actorId) {
		ProcessQuery query = new ProcessQuery();
		query.setActorId(actorId);
		query.setProcessName(processName);
		return this.getWorkedTaskItems(jbpmContext, query);
	}

	/**
	 * 将流程中待办任务以前的参与者更改为新的参与者
	 * 
	 * @param jbpmContext
	 * @param previousActorId
	 * @param nowActorId
	 */
	public void reassignAllTasks(JbpmContext jbpmContext,
			String previousActorId, String nowActorId) {
		List<TaskInstance> taskInstances = jbpmContext
				.getTaskList(previousActorId);
		if (taskInstances != null && taskInstances.size() > 0) {
			Iterator<TaskInstance> iterator = taskInstances.iterator();
			while (iterator.hasNext()) {
				TaskInstance taskInstance = iterator.next();
				if (taskInstance.isOpen() && !taskInstance.hasEnded()) {
					taskInstance.setActorId(nowActorId);
					jbpmContext.save(taskInstance);
				}
			}
		}

		List<String> actorIds = new java.util.ArrayList<String>();
		actorIds.add(previousActorId);
		taskInstances = jbpmContext.getGroupTaskList(actorIds);
		if (taskInstances != null && taskInstances.size() > 0) {
			Iterator<TaskInstance> iterator = taskInstances.iterator();
			while (iterator.hasNext()) {
				TaskInstance taskInstance = iterator.next();
				if (taskInstance.isOpen() && !taskInstance.hasEnded()) {
					Set<PooledActor> pooledActors = taskInstance
							.getPooledActors();
					if (pooledActors != null && pooledActors.size() > 0) {
						Iterator<PooledActor> iter = pooledActors.iterator();
						while (iter.hasNext()) {
							PooledActor pa = iter.next();
							if (StringUtils.equals(pa.getActorId(),
									previousActorId)) {
								pa.setActorId(nowActorId);
							}
						}
					}
					jbpmContext.save(taskInstance);
				}
			}
		}
	}

	/**
	 * 重新分配任务
	 * 
	 * @param taskInstanceId
	 * @param actorIds
	 */
	public void reassignTask(JbpmContext jbpmContext, Long taskInstanceId,
			Set<String> actorIds) {
		ProcessInstance processInstance = null;
		TaskInstance taskInstance = null;
		jbpmContext.setActorId("0");
		if (taskInstanceId != null) {
			taskInstance = jbpmContext
					.loadTaskInstanceForUpdate(taskInstanceId);
			processInstance = taskInstance.getToken().getProcessInstance();

			if (processInstance.hasEnded()) {
				throw new JbpmException("process has finished");
			}

			if (taskInstance.hasEnded()) {
				throw new JbpmException("task has finished");
			}

			if (actorIds != null && actorIds.size() > 0) {
				if (actorIds.size() == 1) {
					String actorId = actorIds.iterator().next();
					taskInstance.setActorId(actorId);
				} else {
					int i = 0;
					String[] pooledActorIds = new String[actorIds.size()];
					Iterator<String> iterator = actorIds.iterator();
					while (iterator.hasNext()) {
						pooledActorIds[i++] = iterator.next();
					}
					taskInstance.setActorId(null);
					taskInstance.setPooledActors(pooledActorIds);
				}
			}
			jbpmContext.save(taskInstance);
		}
	}

	/**
	 * 重新分配任务
	 * 
	 * @param processInstanceId
	 * @param taskName
	 * @param actorIds
	 */
	public void reassignTask(JbpmContext jbpmContext, Long processInstanceId,
			String taskName, Set<String> actorIds) {
		if (LogUtils.isDebug()) {
			logger.debug("processInstanceId:" + processInstanceId);
			logger.debug("taskName:" + taskName);
			logger.debug("actorIds:" + actorIds);
		}
		if (processInstanceId == null) {
			throw new JbpmException("processInstanceId is null");
		}
		if (StringUtils.isEmpty(taskName)) {
			throw new JbpmException("taskName is null");
		}
		if (actorIds == null || actorIds.size() == 0) {
			throw new JbpmException("actorIds is null");
		}

		ProcessInstance processInstance = null;
		TaskInstance taskInstance = null;
		processInstance = jbpmContext.getProcessInstance(processInstanceId);

		if (processInstance == null) {
			throw new JbpmException("process instance is null");
		}
		if (processInstance.hasEnded()) {
			throw new JbpmException("process has finished");
		}

		TaskMgmtInstance tmi = processInstance.getTaskMgmtInstance();
		Collection<TaskInstance> taskInstances = tmi.getTaskInstances();
		List<TaskInstance> unfinishedTasks = new java.util.ArrayList<TaskInstance>();
		if (taskInstances != null) {
			Iterator<TaskInstance> iter = taskInstances.iterator();
			while (iter.hasNext()) {
				TaskInstance x = iter.next();
				if (StringUtils.equalsIgnoreCase(taskName, x.getName())) {
					if (x.isOpen() && !x.hasEnded()) {
						unfinishedTasks.add(x);
					}
				}
			}
		}

		if (unfinishedTasks.size() > 1) {
			throw new JbpmException("too more unfinished tasks ");
		}

		if (unfinishedTasks.size() == 1) {
			taskInstance = unfinishedTasks.get(0);
		}

		if (taskInstance == null) {
			throw new JbpmException("task instance is null");
		}

		if (actorIds != null && actorIds.size() > 0) {
			if (actorIds.size() == 1) {
				String actorId = actorIds.iterator().next();
				taskInstance.setActorId(actorId);
			} else {
				int i = 0;
				String[] pooledActorIds = new String[actorIds.size()];
				Iterator<String> iterator = actorIds.iterator();
				while (iterator.hasNext()) {
					pooledActorIds[i++] = iterator.next();
				}
				taskInstance.setActorId(null);
				taskInstance.setPooledActors(pooledActorIds);
			}
		}
		jbpmContext.save(taskInstance);
	}

	protected void setActors(TaskInstance taskInstance, String actorId) {
		if (StringUtils.isEmpty(actorId)) {
			return;
		}
		if (actorId.indexOf(",") > 0) {
			Set<String> actorIds = new HashSet<String>();
			StringTokenizer token = new StringTokenizer(actorId, ",");
			while (token.hasMoreTokens()) {
				String elem = token.nextToken();
				if (StringUtils.isNotEmpty(elem)) {
					actorIds.add(elem);
				}
			}
			if (actorIds.size() > 0) {
				int i = 0;
				String[] users = new String[actorIds.size()];
				Iterator<String> iterator = actorIds.iterator();
				while (iterator.hasNext()) {
					users[i++] = iterator.next();
				}
				taskInstance.setPooledActors(users);
			}
		} else {
			taskInstance.setActorId(actorId);
		}
	}

	public void setJbpmEntityDAO(JbpmEntityDAO jbpmEntityDAO) {
		this.jbpmEntityDAO = jbpmEntityDAO;
	}

	public void setJbpmTaskDAO(JbpmTaskDAO jbpmTaskDAO) {
		this.jbpmTaskDAO = jbpmTaskDAO;
	}

}