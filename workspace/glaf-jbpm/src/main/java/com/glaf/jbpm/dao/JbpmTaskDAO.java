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

package com.glaf.jbpm.dao;

import java.util.ArrayList;
import java.util.Collection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.exe.PooledActor;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.glaf.core.util.LogUtils;
import com.glaf.jbpm.model.TaskItem;
import com.glaf.jbpm.query.ProcessQuery;

public class JbpmTaskDAO {
	protected static final Log logger = LogFactory.getLog(JbpmTaskDAO.class);

	/**
	 * 获取全部用户的待办任务，用于消息系统的催办。
	 * 
	 * @param jbpmContext
	 * @return
	 */
	public List<TaskItem> getAllTaskItems(JbpmContext jbpmContext) {
		ProcessQuery query = new ProcessQuery();
		query.setTaskType("running");
		return this.getTaskItems(jbpmContext, query);
	}

	/**
	 * 获取用户已经处理过的流程实例编号
	 * 
	 * @param paramMap
	 * @return
	 */
	public Collection<Long> getFinishedProcessInstanceIds(
			JbpmContext jbpmContext, ProcessQuery query) {
		Collection<Long> processInstanceIds = new HashSet<Long>();
		Collection<TaskInstance> taskInstances = this.getFinishedTaskInstances(
				jbpmContext, query);
		if (taskInstances != null && taskInstances.size() > 0) {
			Iterator<TaskInstance> iterator = taskInstances.iterator();
			while (iterator.hasNext()) {
				TaskInstance ti = iterator.next();
				if (ti.hasEnded() && StringUtils.isNotEmpty(ti.getActorId())) {
					Token token = ti.getToken();
					ProcessInstance pi = token.getProcessInstance();
					processInstanceIds.add(pi.getId());
				}
			}
		}
		return processInstanceIds;
	}

	/**
	 * 获取已经完成了的任务实例
	 * 
	 * @param jbpmContext
	 * @param paramMap
	 * @return
	 */
	public List<TaskInstance> getFinishedTaskInstances(JbpmContext jbpmContext,
			ProcessQuery query) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" select ti from org.jbpm.taskmgmt.exe.TaskInstance as ti where ti.actorId is not null and ti.start is not null and ti.end is not null and ti.isSuspended != true and ti.isOpen = false ");

		Map<String, Object> params = new HashMap<String, Object>();

		if (query.getActorId() != null) {
			buffer.append(" and ti.actorId = :actorId ");
			params.put("actorId", query.getActorId());
		}

		if (query.getActorIds() != null && !query.getActorIds().isEmpty()) {
			Collection<String> collection = query.getActorIds();
			if (collection != null && collection.size() > 0) {
				buffer.append(" and ( ");
				int index = 0;
				for (Object object : collection) {
					if (index > 0) {
						buffer.append(" or ");
					}
					String p_name = "actorId_" + index;
					buffer.append(" ti.actorId = :").append(p_name);
					params.put(p_name, object);
					index++;
				}
				buffer.append(" ) ");
			}
		}

		if (query.getProcessInstanceId() != null) {
			buffer.append(" and ti.processInstance.id = :processInstanceId ");
			params.put("processInstanceId", query.getProcessInstanceId());
		}

		if (query.getProcessInstanceIds() != null
				&& !query.getProcessInstanceIds().isEmpty()) {
			Collection<Long> collection = query.getProcessInstanceIds();
			if (collection != null && collection.size() > 0) {
				buffer.append(" and ( ");
				int index = 0;
				for (Long pid : collection) {
					if (index > 0) {
						buffer.append(" or ");
					}
					String p_name = "processInstanceId_" + index;
					buffer.append(" ti.processInstance.id = :").append(p_name);

					params.put(p_name, pid);

					index++;
				}
				buffer.append(" ) ");
			}
		}

		if (query.getTaskName() != null) {
			buffer.append(" and ti.name like :taskName ");
			params.put("taskName", "%" + query.getTaskName() + "%");
		}

		if (query.getTaskNames() != null && !query.getTaskNames().isEmpty()) {
			Collection<String> collection = query.getTaskNames();
			if (collection != null && collection.size() > 0) {
				buffer.append(" and ( ");
				int index = 0;
				for (Object object : collection) {
					if (index > 0) {
						buffer.append(" or ");
					}
					String p_name = "taskName_" + index;
					buffer.append(" ti.name = :").append(p_name);
					params.put(p_name, object);
					index++;
				}
				buffer.append(" ) ");
			}
		}

		if (query.getAfterTaskCreateDate() != null) {
			params.put("afterTaskCreateDate", query.getAfterTaskCreateDate());
			buffer.append(" and ( ti.create >= :afterTaskCreateDate )");
		}

		if (query.getBeforeTaskCreateDate() != null) {
			params.put("beforeTaskCreateDate", query.getBeforeTaskCreateDate());
			buffer.append(" and ( ti.create <= :beforeTaskCreateDate )");
		}

		if (query.getAfterTaskStartDate() != null) {
			params.put("afterTaskStartDate", query.getAfterTaskStartDate());
			buffer.append(" and ( ti.start >= :afterTaskStartDate )");
		}

		if (query.getBeforeTaskStartDate() != null) {
			params.put("beforeTaskStartDate", query.getBeforeTaskStartDate());
			buffer.append(" and ( ti.start <= :beforeTaskStartDate )");
		}

		if (query.getAfterTaskEndDate() != null) {
			params.put("afterTaskEndDate", query.getAfterTaskEndDate());
			buffer.append(" and ( ti.end >= :afterTaskEndDate )");
		}

		if (query.getBeforeTaskEndDate() != null) {
			params.put("beforeTaskEndDate", query.getBeforeTaskEndDate());
			buffer.append(" and ( ti.end <= :beforeTaskEndDate )");
		}

		if (query.getTaskType() != null) {
			if (StringUtils.equals(query.getTaskType(), "running")) {
				buffer.append(" and ti.end is null ");
			} else if (StringUtils.equals(query.getTaskType(), "finished")) {
				buffer.append(" and ti.end is not null ");
			}
		}

		if (query.getProcessName() != null) {
			params.put("processName", query.getProcessName());
			buffer.append(" and ti.processInstance.name = :processName ");
		}

		if (query.getProcessNames() != null
				&& !query.getProcessNames().isEmpty()) {
			Collection<String> collection = query.getProcessNames();
			if (collection != null && collection.size() > 0) {
				buffer.append(" and ( ");
				int index = 0;
				for (Object object : collection) {
					if (index > 0) {
						buffer.append(" or ");
					}
					String p_name = "processName_" + index;
					buffer.append(
							" ti.processInstance.processDefinition.name = :")
							.append(p_name);
					params.put(p_name, object);
					index++;
				}
				buffer.append(" ) ");
			}
		}

		if (query.getProcessDefinitionId() != null) {
			buffer.append(" and ti.processInstance.processDefinition.id = :processDefinitionId ");
			params.put("processDefinitionId", query.getProcessDefinitionId());
		}

		if (LogUtils.isDebug()) {
			logger.debug(buffer.toString());
		}

		Session session = jbpmContext.getSession();
		Query q = session.createQuery(buffer.toString());
		q.setMaxResults(5000);

		if (params != null && params.size() > 0) {
			Set<Entry<String, Object>> entrySet = params.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String name = entry.getKey();
				Object value = entry.getValue();
				if (value != null) {
					q.setParameter(name, value);
				}
			}
		}

		List<?> rows = q.list();
		List<TaskInstance> taskInstances = new ArrayList<TaskInstance>();
		if (rows != null && rows.size() > 0) {
			Iterator<?> iterator = rows.iterator();
			while (iterator.hasNext()) {
				taskInstances.add((TaskInstance) iterator.next());
			}
		}

		return taskInstances;
	}

	public List<TaskInstance> getPooledTaskInstances(JbpmContext jbpmContext,
			ProcessQuery query) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" select ti from org.jbpm.taskmgmt.exe.PooledActor pooledActor join pooledActor.taskInstances ti where 1=1 and ti.actorId is null and ti.isSuspended != true and ti.isOpen = true and ti.processInstance.end is null ");
		Map<String, Object> params = new HashMap<String, Object>();

		if (query.getActorId() != null) {
			buffer.append(" and pooledActor.actorId = :actorId ");
			params.put("actorId", query.getActorId());
		}

		if (query.getActorIds() != null && !query.getActorIds().isEmpty()) {
			Collection<String> collection = query.getActorIds();
			if (collection != null && collection.size() > 0) {
				buffer.append(" and ( ");
				int index = 0;
				for (Object object : collection) {
					if (index > 0) {
						buffer.append(" or ");
					}
					String p_name = "actorId_" + index;
					buffer.append(" pooledActor.actorId = :").append(p_name);
					params.put(p_name, object);
					index++;
				}
				buffer.append(" ) ");
			}
		}

		if (query.getProcessInstanceId() != null) {
			buffer.append(" and ti.processInstance.id = :processInstanceId ");
			params.put("processInstanceId", query.getProcessInstanceId());
		}

		if (query.getProcessInstanceIds() != null
				&& !query.getProcessInstanceIds().isEmpty()) {
			Collection<Long> collection = query.getProcessInstanceIds();
			if (collection != null && collection.size() > 0) {
				buffer.append(" and ( ");
				int index = 0;
				for (Long pid : collection) {
					if (index > 0) {
						buffer.append(" or ");
					}
					String p_name = "processInstanceId_" + index;
					buffer.append(" ti.processInstance.id = :").append(p_name);

					params.put(p_name, pid);

					index++;
				}
				buffer.append(" ) ");
			}
		}

		if (query.getTaskName() != null) {
			buffer.append(" and ti.name like :taskName ");
			params.put("taskName", "%" + query.getTaskName() + "%");
		}

		if (query.getTaskNames() != null && !query.getTaskNames().isEmpty()) {
			Collection<String> collection = query.getTaskNames();
			if (collection != null && collection.size() > 0) {
				buffer.append(" and ( ");
				int index = 0;
				for (Object object : collection) {
					if (index > 0) {
						buffer.append(" or ");
					}
					String p_name = "taskName_" + index;
					buffer.append(" ti.name = :").append(p_name);
					params.put(p_name, object);
					index++;
				}
				buffer.append(" ) ");
			}
		}

		if (query.getAfterTaskCreateDate() != null) {
			params.put("afterTaskCreateDate", query.getAfterTaskCreateDate());
			buffer.append(" and ( ti.create >= :afterTaskCreateDate )");
		}

		if (query.getBeforeTaskCreateDate() != null) {
			params.put("beforeTaskCreateDate", query.getBeforeTaskCreateDate());
			buffer.append(" and ( ti.create <= :beforeTaskCreateDate )");
		}

		if (query.getAfterTaskStartDate() != null) {
			params.put("afterTaskStartDate", query.getAfterTaskStartDate());
			buffer.append(" and ( ti.start >= :afterTaskStartDate )");
		}

		if (query.getBeforeTaskStartDate() != null) {
			params.put("beforeTaskStartDate", query.getBeforeTaskStartDate());
			buffer.append(" and ( ti.start <= :beforeTaskStartDate )");
		}

		if (query.getAfterTaskEndDate() != null) {
			params.put("afterTaskEndDate", query.getAfterTaskEndDate());
			buffer.append(" and ( ti.end >= :afterTaskEndDate )");
		}

		if (query.getBeforeTaskEndDate() != null) {
			params.put("beforeTaskEndDate", query.getBeforeTaskEndDate());
			buffer.append(" and ( ti.end <= :beforeTaskEndDate )");
		}

		if (query.getTaskType() != null) {
			if (StringUtils.equals(query.getTaskType(), "running")) {
				buffer.append(" and ti.end is null ");
			} else if (StringUtils.equals(query.getTaskType(), "finished")) {
				buffer.append(" and ti.end is not null ");
			}
		}

		if (query.getProcessName() != null) {
			params.put("processName", query.getProcessName());
			buffer.append(" and ti.processInstance.name = :processName ");
		}

		if (query.getProcessNames() != null
				&& !query.getProcessNames().isEmpty()) {
			Collection<String> collection = query.getProcessNames();
			if (collection != null && collection.size() > 0) {
				buffer.append(" and ( ");
				int index = 0;
				for (Object object : collection) {
					if (index > 0) {
						buffer.append(" or ");
					}
					String p_name = "processName_" + index;
					buffer.append(
							" ti.processInstance.processDefinition.name = :")
							.append(p_name);
					params.put(p_name, object);
					index++;
				}
				buffer.append(" ) ");
			}
		}

		if (query.getProcessDefinitionId() != null) {
			buffer.append(" and ti.processInstance.processDefinition.id = :processDefinitionId ");
			params.put("processDefinitionId", query.getProcessDefinitionId());
		}

		if (LogUtils.isDebug()) {
			logger.debug(buffer.toString());
		}

		Session session = jbpmContext.getSession();
		Query q = session.createQuery(buffer.toString());
		q.setMaxResults(5000);

		if (params != null && params.size() > 0) {
			Set<Entry<String, Object>> entrySet = params.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String name = entry.getKey();
				Object value = entry.getValue();
				if (value != null) {
					q.setParameter(name, value);
				}
			}
		}

		List<?> rows = q.list();
		List<TaskInstance> taskInstances = new ArrayList<TaskInstance>();
		if (rows != null && rows.size() > 0) {
			Iterator<?> iterator = rows.iterator();
			while (iterator.hasNext()) {
				taskInstances.add((TaskInstance) iterator.next());
			}
		}

		return taskInstances;
	}

	public List<TaskInstance> getTaskInstances(JbpmContext jbpmContext,
			ProcessQuery query) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("  select ti from org.jbpm.taskmgmt.exe.TaskInstance as ti where ti.actorId is not null and ti.isSuspended != true and ti.isOpen = true and ti.processInstance.end is null ");

		Map<String, Object> params = new HashMap<String, Object>();

		if (query.getActorId() != null) {
			buffer.append(" and ti.actorId = :actorId ");
			params.put("actorId", query.getActorId());
		}

		if (query.getActorIds() != null && !query.getActorIds().isEmpty()) {
			Collection<String> collection = query.getActorIds();
			if (collection != null && collection.size() > 0) {
				buffer.append(" and ( ");
				int index = 0;
				for (Object object : collection) {
					if (index > 0) {
						buffer.append(" or ");
					}
					String p_name = "actorId_" + index;
					buffer.append(" ti.actorId = :").append(p_name);
					params.put(p_name, object);
					index++;
				}
				buffer.append(" ) ");
			}
		}

		if (query.getProcessInstanceId() != null) {
			buffer.append(" and ti.processInstance.id = :processInstanceId ");

			params.put("processInstanceId", query.getProcessInstanceId());

		}

		if (query.getProcessInstanceIds() != null
				&& !query.getProcessInstanceIds().isEmpty()) {
			Collection<Long> collection = query.getProcessInstanceIds();
			if (collection != null && collection.size() > 0) {
				buffer.append(" and ( ");
				int index = 0;
				for (Long pid : collection) {
					if (index > 0) {
						buffer.append(" or ");
					}
					String p_name = "processInstanceId_" + index;
					buffer.append(" ti.processInstance.id = :").append(p_name);

					params.put(p_name, pid);

					index++;
				}
				buffer.append(" ) ");
			}
		}

		if (query.getTaskName() != null) {
			buffer.append(" and ti.name like :taskName ");
			params.put("taskName", "%" + query.getTaskName() + "%");
		}

		if (query.getTaskNames() != null && !query.getTaskNames().isEmpty()) {
			Collection<String> collection = query.getTaskNames();
			if (collection != null && collection.size() > 0) {
				buffer.append(" and ( ");
				int index = 0;
				for (Object object : collection) {
					if (index > 0) {
						buffer.append(" or ");
					}
					String p_name = "taskName_" + index;
					buffer.append(" ti.name = :").append(p_name);
					params.put(p_name, object);
					index++;
				}
				buffer.append(" ) ");
			}
		}

		if (query.getAfterTaskCreateDate() != null) {
			params.put("afterTaskCreateDate", query.getAfterTaskCreateDate());
			buffer.append(" and ( ti.create >= :afterTaskCreateDate )");
		}

		if (query.getBeforeTaskCreateDate() != null) {
			params.put("beforeTaskCreateDate", query.getBeforeTaskCreateDate());
			buffer.append(" and ( ti.create <= :beforeTaskCreateDate )");
		}

		if (query.getAfterTaskStartDate() != null) {
			params.put("afterTaskStartDate", query.getAfterTaskStartDate());
			buffer.append(" and ( ti.start >= :afterTaskStartDate )");
		}

		if (query.getBeforeTaskStartDate() != null) {
			params.put("beforeTaskStartDate", query.getBeforeTaskStartDate());
			buffer.append(" and ( ti.start <= :beforeTaskStartDate )");
		}

		if (query.getAfterTaskEndDate() != null) {
			params.put("afterTaskEndDate", query.getAfterTaskEndDate());
			buffer.append(" and ( ti.end >= :afterTaskEndDate )");
		}

		if (query.getBeforeTaskEndDate() != null) {
			params.put("beforeTaskEndDate", query.getBeforeTaskEndDate());
			buffer.append(" and ( ti.end <= :beforeTaskEndDate )");
		}

		if (query.getTaskType() != null) {
			if (StringUtils.equals(query.getTaskType(), "running")) {
				buffer.append(" and ti.end is null ");
			} else if (StringUtils.equals(query.getTaskType(), "finished")) {
				buffer.append(" and ti.end is not null ");
			}
		}

		if (query.getProcessName() != null) {
			params.put("processName", query.getProcessName());
			buffer.append(" and ti.processInstance.name = :processName ");
		}

		if (query.getProcessNames() != null
				&& !query.getProcessNames().isEmpty()) {
			Collection<String> collection = query.getProcessNames();
			if (collection != null && collection.size() > 0) {
				buffer.append(" and ( ");
				int index = 0;
				for (Object object : collection) {
					if (index > 0) {
						buffer.append(" or ");
					}
					String p_name = "processName_" + index;
					buffer.append(
							" ti.processInstance.processDefinition.name = :")
							.append(p_name);
					params.put(p_name, object);
					index++;
				}
				buffer.append(" ) ");
			}
		}

		if (query.getProcessDefinitionId() != null) {
			buffer.append(" and ti.processInstance.processDefinition.id = :processDefinitionId ");
			params.put("processDefinitionId", query.getProcessDefinitionId());
		}

		if (LogUtils.isDebug()) {
			logger.debug(buffer.toString());
		}

		Session session = jbpmContext.getSession();
		Query q = session.createQuery(buffer.toString());
		q.setMaxResults(5000);

		if (params != null && params.size() > 0) {
			Set<Entry<String, Object>> entrySet = params.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String name = entry.getKey();
				Object value = entry.getValue();
				if (value != null) {
					q.setParameter(name, value);
				}
			}
		}

		List<?> rows = q.list();
		List<TaskInstance> taskInstances = new ArrayList<TaskInstance>();
		if (rows != null && rows.size() > 0) {
			Iterator<?> iterator = rows.iterator();
			while (iterator.hasNext()) {
				taskInstances.add((TaskInstance) iterator.next());
			}
		}

		return taskInstances;
	}

	public List<TaskItem> getTaskItems(JbpmContext jbpmContext,
			ProcessQuery query) {
		List<TaskInstance> taskInstances = new ArrayList<TaskInstance>();

		List<TaskInstance> rows01 = this.getTaskInstances(jbpmContext, query);
		List<TaskInstance> rows02 = this.getPooledTaskInstances(jbpmContext,
				query);

		if (rows01 != null && rows01.size() > 0) {
			logger.debug("task size:" + rows01.size());
			Iterator<TaskInstance> iter01 = rows01.iterator();
			while (iter01.hasNext()) {
				TaskInstance taskInstance = iter01.next();
				taskInstances.add(taskInstance);
			}
		}

		if (rows02 != null && rows02.size() > 0) {
			logger.debug("pooled task size:" + rows02.size());
			Iterator<TaskInstance> iter02 = rows02.iterator();
			while (iter02.hasNext()) {
				TaskInstance taskInstance = iter02.next();
				taskInstances.add(taskInstance);
			}
		}

		List<TaskItem> taskItems = new ArrayList<TaskItem>();

		Iterator<TaskInstance> iterator = taskInstances.iterator();
		while (iterator.hasNext()) {
			TaskInstance taskInstance = iterator.next();
			if (taskInstance.isOpen() && !taskInstance.hasEnded()) {
				Token token = taskInstance.getToken();
				if (token == null) {
					continue;
				}
				String actorId = taskInstance.getActorId();
				ProcessInstance processInstance = token.getProcessInstance();
				ProcessDefinition processDefinition = processInstance
						.getProcessDefinition();
				if (StringUtils.isNotEmpty(actorId)) {
					TaskItem item = new TaskItem();
					item.setActorId(actorId);
					item.setRowId(processInstance.getKey());
					item.setTaskInstanceId(taskInstance.getId());
					item.setTaskName(taskInstance.getName());
					item.setTaskDescription(taskInstance.getDescription());
					item.setCreateDate(taskInstance.getCreate());
					item.setProcessInstanceId(processInstance.getId());
					item.setProcessName(processDefinition.getName());
					item.setProcessDescription(processDefinition
							.getDescription());
					taskItems.add(item);
				} else {
					Set<PooledActor> pooledActors = taskInstance
							.getPooledActors();
					if (pooledActors != null && pooledActors.size() > 0) {
						Iterator<PooledActor> iter = pooledActors.iterator();
						while (iter.hasNext()) {
							PooledActor actor = iter.next();
							String pooledActorId = actor.getActorId();
							TaskItem item = new TaskItem();
							item.setTaskInstanceId(taskInstance.getId());
							item.setRowId(processInstance.getKey());
							item.setActorId(pooledActorId);
							item.setTaskName(taskInstance.getName());
							item.setTaskDescription(taskInstance
									.getDescription());
							item.setCreateDate(taskInstance.getCreate());
							item.setProcessInstanceId(processInstance.getId());
							item.setProcessName(processDefinition.getName());
							item.setProcessDescription(processDefinition
									.getDescription());
							taskItems.add(item);
						}
					}
				}
			}
		}

		return taskItems;
	}

	/**
	 * 获取用户已经处理过的流程实例编号
	 * 
	 * @param query
	 * @return
	 */
	public Collection<Long> getWorkedProcessInstanceIds(
			JbpmContext jbpmContext, ProcessQuery query) {
		Collection<Long> processInstanceIds = new HashSet<Long>();
		Collection<TaskInstance> taskInstances = this.getFinishedTaskInstances(
				jbpmContext, query);
		if (taskInstances != null && taskInstances.size() > 0) {
			Iterator<TaskInstance> iterator = taskInstances.iterator();
			while (iterator.hasNext()) {
				TaskInstance ti = iterator.next();
				if (ti.hasEnded() && StringUtils.isNotEmpty(ti.getActorId())) {
					Token token = ti.getToken();
					ProcessInstance pi = token.getProcessInstance();
					processInstanceIds.add(pi.getId());
				}
			}
		}
		return processInstanceIds;
	}

	/**
	 * 获取用户已经处理过的任务
	 * 
	 * @param query
	 * @return
	 */
	public List<TaskItem> getWorkedTaskItems(JbpmContext jbpmContext,
			ProcessQuery query) {
		List<TaskItem> finishedTaskItems = new ArrayList<TaskItem>();
		Collection<TaskInstance> taskInstances = this.getFinishedTaskInstances(
				jbpmContext, query);
		if (taskInstances != null && taskInstances.size() > 0) {
			Iterator<TaskInstance> iterator = taskInstances.iterator();
			while (iterator.hasNext()) {
				TaskInstance ti = iterator.next();
				if (ti.hasEnded() && StringUtils.isNotEmpty(ti.getActorId())) {
					Token token = ti.getToken();
					ProcessInstance pi = token.getProcessInstance();
					ProcessDefinition pd = pi.getProcessDefinition();
					TaskItem item = new TaskItem();
					item.setRowId(pi.getKey());
					item.setActorId(ti.getActorId());
					item.setCreateDate(ti.getCreate());
					item.setStartDate(ti.getStart());
					item.setEndDate(ti.getEnd());
					item.setTaskDescription(ti.getDescription());
					item.setTaskName(ti.getName());
					item.setProcessName(pd.getName());
					item.setProcessDescription(pd.getDescription());
					finishedTaskItems.add(item);
				}
			}
		}
		return finishedTaskItems;
	}

}