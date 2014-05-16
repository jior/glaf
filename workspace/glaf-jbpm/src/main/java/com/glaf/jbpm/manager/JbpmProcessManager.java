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
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.context.exe.VariableInstance;
import org.jbpm.db.GraphSession;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.def.Transition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.PooledActor;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;

import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.LogUtils;
import com.glaf.core.util.Paging;
import com.glaf.jbpm.context.ProcessContext;
import com.glaf.jbpm.dao.JbpmEntityDAO;
import com.glaf.jbpm.dao.JbpmTaskDAO;
import com.glaf.jbpm.datafield.DataField;
import com.glaf.jbpm.model.ActivityInstance;
import com.glaf.jbpm.model.TaskItem;
import com.glaf.jbpm.query.ProcessQuery;
import com.glaf.jbpm.util.Constant;
import com.glaf.jbpm.util.ThreadVariable;

public class JbpmProcessManager {
	protected final static Log logger = LogFactory
			.getLog(JbpmProcessManager.class);

	private JbpmTaskDAO jbpmTaskDAO;

	private JbpmEntityDAO jbpmEntityDAO;

	public JbpmProcessManager() {
		jbpmTaskDAO = new JbpmTaskDAO();
		jbpmEntityDAO = new JbpmEntityDAO();
	}

	/**
	 * 中止流程/结束流程
	 * 
	 * @param ctx
	 * @return
	 */
	public void abortProcess(ProcessContext ctx) {
		logger.debug("......................................................");
		logger.debug(".................abort process........................");
		logger.debug("......................................................");

		String actorId = ctx.getActorId();

		Long processInstanceId = ctx.getProcessInstanceId();

		JbpmContext jbpmContext = null;

		ProcessInstance processInstance = null;

		jbpmContext = ctx.getJbpmContext();
		jbpmContext.setActorId(actorId);

		if (processInstanceId > 0) {

			processInstance = jbpmContext
					.loadProcessInstanceForUpdate(processInstanceId);

			if (processInstance.hasEnded()) {
				throw new JbpmException("processInstance'"
						+ processInstance.getId() + "' has ended");
			}

			Collection<TaskInstance> taskInstances = processInstance
					.getTaskMgmtInstance().getTaskInstances();
			if (taskInstances != null && taskInstances.size() > 0) {
				Iterator<TaskInstance> iterator = taskInstances.iterator();
				while (iterator.hasNext()) {
					TaskInstance ti = iterator.next();
					if (!ti.hasEnded()) {
						// 将已经分派的任务取消
						ti.setSignalling(false);
						ti.suspend();
						jbpmContext.save(ti);
					}
				}
			}

			processInstance.end();

		}

		logger.debug("end abort process.............................");

	}

	/**
	 * 完成任务
	 * 
	 * @param ctx
	 */
	public void completeTask(ProcessContext ctx) {
		logger.debug("......................................................");
		logger.debug("...................start complete task................");
		logger.debug("......................................................");

		Long taskInstanceId = ctx.getTaskInstanceId();
		Long processInstanceId = ctx.getProcessInstanceId();

		String actorId = ctx.getActorId();

		ProcessDefinition processDefinition = null;
		ProcessInstance processInstance = null;
		ContextInstance contextInstance = null;
		TaskInstance taskInstance = null;
		JbpmContext jbpmContext = null;
		Task task = null;

		jbpmContext = ctx.getJbpmContext();
		jbpmContext.setActorId(actorId);

		if (taskInstanceId != null && taskInstanceId > 0) {
			taskInstance = jbpmContext
					.loadTaskInstanceForUpdate(taskInstanceId);
		}

		if (taskInstance == null && processInstanceId != null
				&& processInstanceId > 0) {

			ProcessQuery query = new ProcessQuery();
			query.setActorId(actorId);
			query.setProcessInstanceId(processInstanceId);
			query.setTaskType("running");

			final List<TaskItem> taskItems = jbpmTaskDAO.getTaskItems(
					jbpmContext, query);
			if (taskItems != null && taskItems.size() > 0) {
				Collections.sort(taskItems);
				final Iterator<TaskItem> iter = taskItems.iterator();
				while (iter.hasNext()) {
					TaskItem taskItem = iter.next();
					Long id = taskItem.getTaskInstanceId();
					if (id != null) {
						taskInstanceId = id;
						if (taskInstanceId > 0) {
							taskInstance = jbpmContext
									.loadTaskInstanceForUpdate(taskInstanceId);
							if (taskInstance != null) {
								break;
							}
						}
					}
				}
			}
		}

		if (taskInstance == null && processInstanceId > 0) {
			final Collection<String> agentIds = ctx.getAgentIds();
			if (agentIds != null && agentIds.size() > 0) {
				ProcessQuery query = new ProcessQuery();
				query.setActorIds(agentIds);
				query.setProcessInstanceId(processInstanceId);
				query.setTaskType("running");
				final List<TaskItem> taskItems = jbpmTaskDAO.getTaskItems(
						jbpmContext, query);
				if (taskItems != null && taskItems.size() > 0) {
					Collections.sort(taskItems);
					final Iterator<TaskItem> iter = taskItems.iterator();
					while (iter.hasNext()) {
						TaskItem taskItem = iter.next();
						Long id = taskItem.getTaskInstanceId();
						if (id != null) {
							taskInstanceId = id;
							if (taskInstanceId > 0) {
								taskInstance = jbpmContext
										.loadTaskInstanceForUpdate(taskInstanceId);
								if (taskInstance != null) {
									break;
								}
							}
						}
					}
				}
			}
		}

		if (taskInstance == null) {
			throw new JbpmException("taskInstance '" + taskInstanceId
					+ "' is not found");
		}

		if (taskInstance.hasEnded()) {
			throw new JbpmException("taskInstance '" + taskInstance.getId()
					+ "' has ended");
		}

		processInstance = taskInstance.getToken().getProcessInstance();
		if (processInstance.hasEnded()) {
			throw new JbpmException("processInstance'"
					+ processInstance.getId() + "' has ended");
		}

		Map<String, Object> dataMap = new java.util.HashMap<String, Object>();

		String previousActorId = null;

		if (StringUtils.isNotEmpty(taskInstance.getActorId())) {
			previousActorId = taskInstance.getActorId();
			dataMap.put(Constant.PREVIOUS_ACTOR_ID, taskInstance.getActorId());
		} else {
			Set<PooledActor> pooledActors = taskInstance.getPooledActors();
			if (pooledActors != null) {
				StringBuffer buffer = new StringBuffer();
				Iterator<PooledActor> iterator = pooledActors.iterator();
				while (iterator.hasNext()) {
					PooledActor pooledActor = iterator.next();
					buffer.append(pooledActor.getActorId());
					if (iterator.hasNext()) {
						buffer.append(',');
					}
				}
				dataMap.put(Constant.POOLED_ACTORS, buffer.toString());
			}
		}

		taskInstance.start(actorId);

		task = taskInstance.getTask();

		contextInstance = processInstance.getContextInstance();
		processDefinition = processInstance.getProcessDefinition();

		processInstanceId = processInstance.getId();

		Collection<DataField> dataFields = ctx.getDataFields();
		if (LogUtils.isDebug()) {
			logger.debug("dataFields:" + dataFields);
		}

		if (ctx.getContextMap() != null) {
			dataMap.putAll(ctx.getContextMap());
		}

		if (dataFields != null && dataFields.size() > 0) {
			Iterator<DataField> iter = dataFields.iterator();
			while (iter.hasNext()) {
				DataField dataField = iter.next();
				if (StringUtils.isNotEmpty(dataField.getName())
						&& dataField.getValue() != null) {
					contextInstance.setVariable(dataField.getName(),
							dataField.getValue());
					dataMap.put(dataField.getName(), dataField.getValue());
					logger.debug("dataField:" + dataField);
					ThreadVariable.addDataField(dataField);
				}
			}
		}

		String isAgree = (String) contextInstance
				.getVariable(Constant.IS_AGREE);
		if (StringUtils.isEmpty(isAgree)) {
			contextInstance.setVariable(Constant.IS_AGREE, "true");
		}

		if (StringUtils.isNotEmpty(ctx.getOpinion())) {
			taskInstance.addComment(ctx.getOpinion());
		}

		dataMap.put(Constant.ACTOR_ID, actorId);
		dataMap.put(Constant.PROCESS_INSTANCE_ID, processInstanceId);
		dataMap.put(Constant.PROCESS_NAME, processDefinition.getName());
		dataMap.put(Constant.PROCESS_DEFINITION_ID, processDefinition.getId());

		boolean jumpXY = false;
		String jumpToNode = ctx.getJumpToNode();
		if (StringUtils.isNotEmpty(jumpToNode)) {
			Node fromNode = taskInstance.getTask().getTaskNode();
			Map<String, Node> nodesMap = processDefinition.getNodesMap();
			if (nodesMap != null) {
				Node toNode = nodesMap.get(jumpToNode);
				if (toNode != null) {
					Transition transition = new Transition("transition");
					transition.setTo(toNode);
					fromNode.addLeavingTransition(transition);
					taskInstance.end(transition);
					jumpXY = true;
				}
			}
		}

		if (!jumpXY) {
			String transitionName = ctx.getTransitionName();
			if (StringUtils.isNotEmpty(transitionName)) {
				taskInstance.end(transitionName);
			} else {
				taskInstance.end();
			}
		}

		ActivityInstance activityInstance = new ActivityInstance();
		activityInstance.setActorId(actorId);
		activityInstance.setPreviousActors(previousActorId);
		activityInstance.setDate(new Date());
		activityInstance.setProcessInstanceId(processInstanceId);
		activityInstance.setTaskInstanceId(taskInstance.getId());
		activityInstance.setIsAgree(isAgree);
		if (ctx.getRowId() != null) {
			activityInstance.setRowId(ctx.getRowId().toString());
		}
		activityInstance.setTitle(ctx.getTitle());
		activityInstance.setContent(ctx.getOpinion());
		logger.debug("审核意见:"+ctx.getOpinion());

		if (task != null) {
			activityInstance.setTaskName(task.getName());
			activityInstance.setTaskDescription(task.getDescription());
		}

		if (StringUtils.isNotEmpty(ctx.getOpinion())) {
			dataMap.put(Constant.PROCESS_OPINION, ctx.getOpinion());
		}
		dataMap.put(Constant.IS_AGREE, isAgree);
		String json = JsonUtils.encode(dataMap);
		if (json != null && json.getBytes().length < 2000) {
			activityInstance.setVariable(json);
		}

		if (!StringUtils.equals(previousActorId, actorId)) {
			ActivityInstance act = new ActivityInstance();
			act.setActorId(previousActorId);
			act.setDate(null);
			act.setProcessInstanceId(processInstanceId);
			act.setTaskInstanceId(taskInstance.getId());
			act.setIsAgree(null);
			if (ctx.getRowId() != null) {
				act.setRowId(ctx.getRowId().toString());
			}
			act.setTitle("Agent Task");
			act.setContent("task agent is " + actorId);
			act.setObjectId("agentId");
			act.setObjectValue(actorId);
			if (task != null) {
				act.setTaskName(task.getName());
				act.setTaskDescription(task.getDescription());
			}
			jbpmEntityDAO.save(jbpmContext, act);
		}

		jbpmEntityDAO.save(jbpmContext, activityInstance);

		logger.debug("end complete task.............................");

	}

	/**
	 * 根据查询条件获取一页的流程实例
	 * 
	 * @param jbpmContext
	 * @param pageNo
	 * @param pageSize
	 * @param paramMap
	 * @return
	 */
	public Paging getPageProcessInstances(JbpmContext jbpmContext, int pageNo,
			int pageSize, ProcessQuery query) {
		Map<String, Object> params = new java.util.HashMap<String, Object>();

		SqlExecutor countExecutor = new SqlExecutor();
		SqlExecutor queryExecutor = new SqlExecutor();

		StringBuffer countSQL = new StringBuffer();
		StringBuffer querySQL = new StringBuffer();
		StringBuffer whereSQL = new StringBuffer();

		countSQL.append(" select count(pi.id) from org.jbpm.graph.exe.ProcessInstance as pi where 1=1 ");

		querySQL.append(" select pi from org.jbpm.graph.exe.ProcessInstance as pi where 1=1 ");

		if (query.getProcessDefinitionId() != null) {
			params.put("processDefinitionId", query.getProcessDefinitionId());
			whereSQL.append(" and pi.processDefinition.id = :processDefinitionId ");
		}

		if (query.getProcessInstanceId() != null) {
			params.put("processInstanceId", query.getProcessInstanceId());
			whereSQL.append(" and pi.id = :processInstanceId ");
		}

		if (query.getBusinessKey() != null) {
			params.put("businessKey", "%" + query.getBusinessKey() + "%");
			whereSQL.append(" and pi.key like :businessKey ");
		}

		if (query.getProcessName() != null) {
			params.put("processName", "%" + query.getProcessName() + "%");
			whereSQL.append(" and pi.processDefinition.name like :processName ");
		}

		if (query.getAfterProcessStartDate() != null) {
			params.put("afterProcessStartDate",
					query.getAfterProcessStartDate());
			whereSQL.append(" and ( pi.start >= :afterProcessStartDate )");
		}

		if (query.getBeforeProcessStartDate() != null) {
			params.put("beforeProcessStartDate",
					query.getBeforeProcessStartDate());
			whereSQL.append(" and ( pi.start <= :beforeProcessStartDate )");
		}

		if (query.getAfterProcessEndDate() != null) {
			params.put("afterProcessEndDate", query.getAfterProcessEndDate());
			whereSQL.append(" and ( pi.end >= :afterProcessEndDate )");
		}

		if (query.getBeforeProcessEndDate() != null) {
			params.put("beforeProcessEndDate", query.getBeforeProcessEndDate());
			whereSQL.append(" and ( pi.end <= :beforeProcessEndDate )");
		}

		if (query.getProcessType() != null) {
			if (StringUtils.equals(query.getProcessType(), "running")) {
				whereSQL.append(" and pi.end is null ");
			} else if (StringUtils.equals(query.getProcessType(), "finished")) {
				whereSQL.append(" and pi.end is not null ");
			}
		}

		countSQL.append(whereSQL.toString());
		querySQL.append(whereSQL.toString());

		querySQL.append(" order by pi.id desc ");

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

	public Map<String, VariableInstance> getVariableMap(
			JbpmContext jbpmContext, Collection<Long> processInstanceIds) {
		SqlExecutor queryExecutor = new SqlExecutor();
		Map<String, Object> params = new java.util.HashMap<String, Object>();
		params.put("name", Constant.JSON_VARIABLE_MAP);
		StringBuffer buffer = new StringBuffer();
		buffer.append(" select vi from org.jbpm.context.exe.VariableInstance vi where vi.name = :name  ");
		if (processInstanceIds != null && processInstanceIds.size() > 0) {
			buffer.append(" and ( ");
			int index = 0;
			for (Object object : processInstanceIds) {
				if (index > 0) {
					buffer.append(" or ");
				}
				String p_name = "processInstanceId_" + index;
				buffer.append(" vi.processInstance.id = :").append(p_name);
				if (object instanceof Long) {
					params.put(p_name, object);
				} else {
					params.put(p_name, Long.parseLong(object.toString()));
				}
				index++;
			}
			buffer.append(" ) ");
		}

		buffer.append(" order by vi.id desc ");

		queryExecutor.setSql(buffer.toString());
		queryExecutor.setParameter(params);

		List<?> variableInstances = jbpmEntityDAO.getList(jbpmContext,
				queryExecutor);

		Map<String, VariableInstance> variableMap = new java.util.HashMap<String, VariableInstance>();
		if (variableInstances != null && variableInstances.size() > 0) {
			Iterator<?> iterator = variableInstances.iterator();
			while (iterator.hasNext()) {
				VariableInstance vi = (VariableInstance) iterator.next();
				ProcessInstance pi = vi.getProcessInstance();
				variableMap.put(String.valueOf(pi.getId()), vi);
			}
		}

		return variableMap;
	}

	/**
	 * 恢复挂起的流程，任务和定时器重新开始。
	 * 
	 * @param processInstanceId
	 */
	public void resume(JbpmContext jbpmContext, Long processInstanceId) {
		if (processInstanceId != null && processInstanceId > 0) {
			logger.debug("准备恢复的流程实例编号：" + processInstanceId);
			ProcessInstance processInstance = jbpmContext
					.loadProcessInstanceForUpdate(processInstanceId);
			if (processInstance.hasEnded()) {
				throw new JbpmException("processInstance'"
						+ processInstance.getId() + "' has ended");
			}
			/**
			 * 只把未处理完的任务恢复
			 */
			TaskMgmtInstance tmi = processInstance.getTaskMgmtInstance();
			Collection<TaskInstance> taskInstances = tmi.getTaskInstances();
			if (taskInstances != null) {
				Iterator<TaskInstance> iter = taskInstances.iterator();
				while (iter.hasNext()) {
					TaskInstance taskInstance = iter.next();
					if (!taskInstance.hasEnded()) {
						logger.debug("正在恢复任务:" + taskInstance.getDescription());
						taskInstance.resume();
					}
				}
			}
		}
	}

	public void setJbpmEntityDAO(JbpmEntityDAO jbpmEntityDAO) {
		this.jbpmEntityDAO = jbpmEntityDAO;
	}

	public void setJbpmTaskDAO(JbpmTaskDAO jbpmTaskDAO) {
		this.jbpmTaskDAO = jbpmTaskDAO;
	}

	/**
	 * 启动流程
	 * 
	 * @param ctx
	 * @return
	 */
	public Long startProcess(ProcessContext ctx) {
		logger.debug("..................................................");
		logger.debug("....................start process.................");
		logger.debug("..................................................");

		final String processName = ctx.getProcessName();

		final String actorId = ctx.getActorId();
		Long processInstanceId = null;
		ContextInstance contextInstance = null;
		GraphSession graphSession = null;
		ProcessDefinition processDefinition = null;
		ProcessInstance processInstance = null;
		TaskInstance taskInstance = null;
		JbpmContext jbpmContext = null;

		jbpmContext = ctx.getJbpmContext();
		jbpmContext.setActorId(actorId);
		graphSession = jbpmContext.getGraphSession();

		if (StringUtils.isNotEmpty(processName)) {
			processDefinition = graphSession
					.findLatestProcessDefinition(processName);
		}

		if (processDefinition == null) {
			throw new JbpmException(" process definition is null");
		}

		processInstance = new ProcessInstance(processDefinition);
		if (ctx.getRowId() != null) {
			processInstance.setKey(ctx.getRowId().toString());
		}

		jbpmContext.save(processInstance);

		contextInstance = processInstance.getContextInstance();

		processInstanceId = processInstance.getId();

		ctx.setProcessInstanceId(processInstanceId);

		final Map<String, Object> dataMap = new java.util.HashMap<String, Object>();

		final Collection<DataField> dataFields = ctx.getDataFields();
		if (dataFields != null && dataFields.size() > 0) {
			final Iterator<DataField> iter = dataFields.iterator();
			while (iter.hasNext()) {
				DataField dataField = iter.next();
				if (StringUtils.isNotEmpty(dataField.getName())
						&& dataField.getValue() != null) {
					contextInstance.setVariable(dataField.getName(),
							dataField.getValue());
					dataMap.put(dataField.getName(), dataField.getValue());
					ThreadVariable.addDataField(dataField);
				}
			}
		}

		contextInstance = processInstance.getContextInstance();

		contextInstance.setVariable(Constant.PROCESS_STARTERID, actorId);

		dataMap.put(Constant.ACTOR_ID, actorId);
		dataMap.put(Constant.PROCESS_STARTERID, actorId);
		dataMap.put(Constant.PROCESS_INSTANCE_ID, processInstanceId);
		dataMap.put(Constant.PROCESS_NAME, processDefinition.getName());
		dataMap.put(Constant.PROCESS_DEFINITION_ID, processDefinition.getId());

		if (ctx.getRowId() != null) {
			contextInstance.setVariable(Constant.PROCESS_ROWID, ctx.getRowId());
			dataMap.put(Constant.PROCESS_ROWID, ctx.getRowId());
		}

		if (StringUtils.isNotEmpty(ctx.getTitle())) {
			dataMap.put(Constant.PROCESS_TITLE, ctx.getTitle());
		}

		final TaskMgmtInstance tmi = processInstance.getTaskMgmtInstance();
		final Task startTask = tmi.getTaskMgmtDefinition().getStartTask();
		if (startTask != null) {
			taskInstance = tmi.createStartTaskInstance();
			taskInstance.start(actorId);
			taskInstance.setActorId(actorId);
			taskInstance.setStart(new Date());
			final String transitionName = ctx.getTransitionName();
			if (StringUtils.isNotEmpty(transitionName)) {
				taskInstance.end(transitionName);
			} else {
				taskInstance.end();
			}
		}

		Object rowId = contextInstance.getVariable(Constant.PROCESS_ROWID);
		ActivityInstance ActivityInstance = new ActivityInstance();
		ActivityInstance.setActorId(actorId);
		ActivityInstance.setDate(new Date());
		ActivityInstance.setProcessInstanceId(processInstanceId);
		if (taskInstance != null) {
			ActivityInstance.setTaskInstanceId(taskInstance.getId());
		}
		if (rowId != null) {
			ActivityInstance.setRowId(rowId.toString());
		}
		ActivityInstance.setTitle(ctx.getTitle());

		if (startTask != null) {
			ActivityInstance.setTaskName(startTask.getName());
			ActivityInstance.setTaskDescription(startTask.getDescription());
		}

		final String json = JsonUtils.encode(dataMap);
		if (json.getBytes().length < 2000) {
			ActivityInstance.setVariable(json);
			contextInstance.setVariable(Constant.JSON_VARIABLE_MAP, json);
		}

		jbpmEntityDAO.save(jbpmContext, ActivityInstance);

		logger.debug("processInstanceId:" + processInstanceId);
		logger.debug("end start process...............................");

		return processInstanceId;
	}

	/**
	 * 挂起某个流程的全部任务。
	 * 
	 * @param processInstanceId
	 */
	public void suspend(JbpmContext jbpmContext, Long processInstanceId) {
		if (processInstanceId != null && processInstanceId > 0) {
			logger.debug("准备挂起的流程实例编号：" + processInstanceId);
			ProcessInstance processInstance = jbpmContext
					.loadProcessInstanceForUpdate(processInstanceId);
			if (processInstance.hasEnded()) {
				throw new JbpmException("processInstance'"
						+ processInstance.getId() + "' has ended");
			}
			/**
			 * 只把任务挂起
			 */
			TaskMgmtInstance tmi = processInstance.getTaskMgmtInstance();
			Collection<TaskInstance> taskInstances = tmi.getTaskInstances();
			if (taskInstances != null) {
				Iterator<TaskInstance> iter = taskInstances.iterator();
				while (iter.hasNext()) {
					TaskInstance taskInstance = iter.next();
					if (taskInstance.isOpen()) {
						logger.debug("正在挂起任务:" + taskInstance.getDescription());
						taskInstance.suspend();
					}
				}
			}
		}
	}

}