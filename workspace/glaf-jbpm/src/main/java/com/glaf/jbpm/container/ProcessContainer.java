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

package com.glaf.jbpm.container;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.def.Transition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.node.TaskNode;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.PooledActor;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;

import com.glaf.core.config.CustomProperties;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.identity.Agent;
import com.glaf.core.identity.User;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.IOUtils;
import com.glaf.core.util.LogUtils;
import com.glaf.core.util.StringTools;
import com.glaf.jbpm.context.Context;
import com.glaf.jbpm.context.ProcessContext;
import com.glaf.jbpm.db.mybatis2.SqlMapContainer;
import com.glaf.jbpm.manager.JbpmExtensionManager;
import com.glaf.jbpm.manager.JbpmProcessManager;
import com.glaf.jbpm.manager.JbpmTaskManager;
import com.glaf.jbpm.model.ActivityInstance;
import com.glaf.jbpm.model.Extension;
import com.glaf.jbpm.model.TaskItem;
import com.glaf.jbpm.query.ProcessQuery;

public class ProcessContainer {

	private static class ProcessContainerHolder {
		public static ProcessContainer instance = new ProcessContainer();
	}

	protected final static Log logger = LogFactory
			.getLog(ProcessContainer.class);

	private static ConcurrentMap<String, Object> cache = new ConcurrentHashMap<String, Object>();

	public static ProcessContainer getContainer() {
		return ProcessContainerHolder.instance;
	}

	private JbpmConfiguration jbpmConfiguration = null;

	private JbpmExtensionManager jbpmExtensionManager;

	private JbpmTaskManager jbpmTaskManager;

	private JbpmProcessManager jbpmProcessManager;

	private ProcessContainer() {
		jbpmTaskManager = new JbpmTaskManager();
		jbpmProcessManager = new JbpmProcessManager();
		jbpmExtensionManager = new JbpmExtensionManager();
	}

	/**
	 * 中止流程
	 * 
	 * @param ctx
	 * @return
	 */
	public boolean abortProcess(ProcessContext ctx) {
		boolean isAbortOK = false;
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = this.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				ctx.setJbpmContext(jbpmContext);
				jbpmProcessManager.abortProcess(ctx);
				isAbortOK = true;
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			isAbortOK = false;
			if (LogUtils.isDebug()) {
				logger.debug(ex);
				ex.printStackTrace();
			}
			throw new JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return isAbortOK;
	}

	/**
	 * 完成任务
	 * 
	 * @param actorId
	 * @param params
	 * @return
	 */
	public boolean completeTask(ProcessContext ctx) {
		// 确保每个流程对每个业务单据在同一时刻只能启动一个实例
		String cacheKey = "x_";
		if (ctx.getProcessInstanceId() != null) {
			cacheKey += "pid_" + ctx.getProcessInstanceId();
		} else if (ctx.getTaskInstanceId() != null) {
			cacheKey += "tid_" + ctx.getTaskInstanceId();
		}
		boolean isCompleteOK = false;
		JbpmContext jbpmContext = null;
		try {
			if (cache.get(cacheKey) == null) {
				cache.put(cacheKey, "1");
				List<String> agentIds = this.getAgentIds(ctx.getActorId());
				ctx.setAgentIds(agentIds);
				jbpmContext = this.createJbpmContext();
				if (jbpmContext.getSession() != null) {
					ctx.setJbpmContext(jbpmContext);
					jbpmProcessManager.completeTask(ctx);
					isCompleteOK = true;
				}
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			isCompleteOK = false;
			logger.debug(ex);
			throw new JbpmException(ex);
		} finally {
			cache.remove(cacheKey);
			Context.close(jbpmContext);
		}
		return isCompleteOK;
	}

	public JbpmContext createJbpmContext() {
		return getJbpmConfiguration().createJbpmContext();
	}

	/**
	 * 创建新任务实例
	 * 
	 * @param processInstanceId
	 * @param taskName
	 * @param actorIds
	 */
	public boolean createTaskInstances(Long processInstanceId, String taskName,
			List<String> actorIds) {
		boolean isOK = true;
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = this.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				jbpmTaskManager.createTaskInstances(jbpmContext,
						processInstanceId, taskName, actorIds);
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			isOK = false;
			logger.debug(ex);
			throw new JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return isOK;
	}

	/**
	 * 过滤任务
	 * 
	 * @param actorId
	 * @param rows
	 * @return
	 */
	public List<TaskItem> filter(String actorId, List<TaskItem> rows) {
		List<Agent> agents = this.getAgents(actorId);
		logger.debug(agents);
		List<TaskItem> taskItems = new java.util.concurrent.CopyOnWriteArrayList<TaskItem>();
		if (rows != null && rows.size() > 0) {
			Iterator<TaskItem> iter = rows.iterator();
			while (iter.hasNext()) {
				TaskItem item = iter.next();
				// logger.debug(item.getProcessDescription() + "\t"
				// + item.getTaskDescription() + "\t" + item.getActorId());
				/**
				 * 如果是他本人的任务就直接处理
				 */
				if (StringUtils.equals(actorId, item.getActorId())) {
					taskItems.add(item);
				} else if (StringUtils.contains(item.getActorId(), actorId)) {
					List<String> actorIds = StringTools
							.split(item.getActorId());
					if (actorIds != null && actorIds.contains(actorId)) {
						taskItems.add(item);
					}
				} else {
					if (agents != null && agents.size() > 0) {
						Iterator<Agent> it = agents.iterator();
						while (it.hasNext()) {
							Agent agent = it.next();
							if (!agent.isValid()) {
								continue;
							}
							/**
							 * 判断是否为某个代理人的任务
							 */
							if (!StringUtils.equals(item.getActorId(),
									agent.getAssignFrom())) {
								continue;
							}
							switch (agent.getAgentType()) {
							case 0:// 全局代理
								taskItems.add(item);
								break;
							case 1:// 流程代理
								if (StringUtils.equalsIgnoreCase(
										agent.getProcessName(),
										item.getProcessName())) {
									taskItems.add(item);
								}
								break;
							case 2:// 指定流程任务代理
								if (StringUtils.equalsIgnoreCase(
										agent.getProcessName(),
										item.getProcessName())
										&& StringUtils.equalsIgnoreCase(
												agent.getTaskName(),
												item.getTaskName())) {
									taskItems.add(item);
								}
								break;
							default:
								break;
							}
						}
					}
				}
			}
		}
		return taskItems;
	}

	/**
	 * 过滤任务
	 * 
	 * @param actorId
	 * @param rows
	 * @return
	 */
	public TaskItem filterOne(String actorId, TaskItem item) {
		List<Agent> agents = this.getAgents(actorId);
		// logger.debug(agents);
		// logger.debug(item.getProcessDescription() + "\t"
		// + item.getTaskDescription() + "\t" + item.getActorId());
		TaskItem taskItem = null;
		/**
		 * 如果是他本人的任务就直接处理
		 */
		if (StringUtils.equals(actorId, item.getActorId())) {
			taskItem = item;
		} else if (StringUtils.contains(item.getActorId(), actorId)) {
			List<String> actorIds = StringTools.split(item.getActorId());
			if (actorIds != null && actorIds.contains(actorId)) {
				taskItem = item;
			}
		} else {
			if (agents != null && agents.size() > 0) {
				Iterator<Agent> it = agents.iterator();
				while (it.hasNext()) {
					Agent agent = it.next();
					if (!agent.isValid()) {
						continue;
					}
					/**
					 * 判断是否为某个代理人的任务
					 */
					if (!StringUtils.equals(item.getActorId(),
							agent.getAssignFrom())) {
						continue;
					}
					switch (agent.getAgentType()) {
					case 0:// 全局代理
						taskItem = item;
						break;
					case 1:// 流程代理
						if (StringUtils.equalsIgnoreCase(
								agent.getProcessName(), item.getProcessName())) {
							taskItem = item;
						}
						break;
					case 2:// 指定流程任务代理
						if (StringUtils.equalsIgnoreCase(
								agent.getProcessName(), item.getProcessName())
								&& StringUtils
										.equalsIgnoreCase(agent.getTaskName(),
												item.getTaskName())) {
							taskItem = item;
						}
						break;
					default:
						break;
					}
				}
			}
		}

		return taskItem;
	}

	/**
	 * 过滤任务
	 * 
	 * @param actorId
	 * @param rows
	 * @return
	 */
	public List<TaskInstance> filterTaskInstance(String actorId,
			List<TaskInstance> rows) {
		List<Agent> agents = this.getAgents(actorId);
		List<TaskInstance> taskItems = new java.util.concurrent.CopyOnWriteArrayList<TaskInstance>();
		if (rows != null && rows.size() > 0) {
			Iterator<TaskInstance> iter = rows.iterator();
			while (iter.hasNext()) {
				TaskInstance taskInstance = iter.next();
				/**
				 * 如果是他本人的任务就直接处理
				 */
				if (StringUtils.equals(actorId, taskInstance.getActorId())) {
					taskItems.add(taskInstance);
				} else {
					if (agents != null && agents.size() > 0) {
						Iterator<Agent> it = agents.iterator();
						while (it.hasNext()) {
							Agent agent = it.next();
							if (!agent.isValid()) {
								continue;
							}
							/**
							 * 判断是否为某个代理人的任务
							 */
							if (!StringUtils.equals(taskInstance.getActorId(),
									agent.getAssignFrom())) {
								continue;
							}
							switch (agent.getAgentType()) {
							case 0:// 全局代理
								taskItems.add(taskInstance);
								break;
							case 1:// 流程代理
								if (StringUtils.equals(taskInstance
										.getProcessInstance()
										.getProcessDefinition().getName(),
										agent.getProcessName())) {
									taskItems.add(taskInstance);
								}
								break;
							case 2:// 指定流程任务代理
								if (StringUtils.equals(taskInstance
										.getProcessInstance()
										.getProcessDefinition().getName(),
										agent.getProcessName())
										&& StringUtils.equals(
												taskInstance.getName(),
												agent.getTaskName())) {
									taskItems.add(taskInstance);
								}
								break;
							default:
								break;
							}
						}
					}
				}
			}
		}
		return taskItems;
	}

	public Map<Long, ActivityInstance> getActivityInstanceMap(
			Long processInstanceId) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = this.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				return jbpmTaskManager.getActivityInstanceMap(jbpmContext,
						processInstanceId);
			}
		} catch (Exception ex) {
			throw new JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return null;
	}

	public List<ActivityInstance> getActivityInstances(Long processInstanceId) {
		JbpmContext jbpmContext = null;
		try {
			Map<String, User> userMap = this.getUserMap();
			jbpmContext = this.createJbpmContext();
			List<ActivityInstance> rows = new java.util.concurrent.CopyOnWriteArrayList<ActivityInstance>();
			List<ActivityInstance> list = jbpmTaskManager.getActivityInstances(
					jbpmContext, processInstanceId);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					ActivityInstance ActivityInstance = list.get(i);
					if (userMap != null
							&& userMap.get(ActivityInstance.getActorId()) != null) {
						User user = userMap.get(ActivityInstance.getActorId());
						ActivityInstance.setActorName(user.getName());
					}
					rows.add(ActivityInstance);
				}
			}
			return rows;
		} catch (Exception ex) {
			logger.debug(ex);
			throw new org.jbpm.JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * 获取委托人编号集合
	 * 
	 * @param assignTo
	 *            受托人编号
	 * @return
	 */
	public List<String> getAgentIds(String assignTo) {
		List<String> agentIds = new java.util.concurrent.CopyOnWriteArrayList<String>();
		List<Agent> agents = getAgents(assignTo);
		if (agents != null && agents.size() > 0) {
			Iterator<Agent> iterator = agents.iterator();
			while (iterator.hasNext()) {
				Agent agent = iterator.next();
				agentIds.add(agent.getAssignFrom());
			}
		}
		return agentIds;
	}

	/**
	 * 获取委托人编号集合
	 * 
	 * @param assignTo
	 *            受托人编号
	 * @return
	 */
	public List<String> getAgentIds(String assignTo, String processName) {
		List<String> agentIds = new java.util.concurrent.CopyOnWriteArrayList<String>();
		List<Agent> agents = getAgents(assignTo);
		if (agents != null && agents.size() > 0) {
			Iterator<Agent> iterator = agents.iterator();
			while (iterator.hasNext()) {
				Agent agent = iterator.next();
				agentIds.add(agent.getAssignFrom());
			}
		}
		return agentIds;
	}

	/**
	 * 获取委托人对象集合
	 * 
	 * @param assignTo
	 *            受托人编号
	 * @return
	 */
	public List<Agent> getAgents(String assignTo) {
		List<Agent> agents = new java.util.concurrent.CopyOnWriteArrayList<Agent>();
		Map<String, Object> params = new java.util.HashMap<String, Object>();
		params.put("assignTo", assignTo);
		SqlMapContainer container = SqlMapContainer.getContainer();
		try {
			Collection<?> actors = container.getList("getAgents", params);

			if (actors != null && actors.size() > 0) {
				Iterator<?> iterator = actors.iterator();
				while (iterator.hasNext()) {
					Object object = iterator.next();
					if (object instanceof Agent) {
						Agent user = (Agent) object;
						agents.add(user);
					}
				}
			}
		} catch (Exception ex) {
			logger.debug(ex);
			throw new org.jbpm.JbpmException(ex);
		}

		return agents;
	}

	/**
	 * 获取全部用户的待办任务，用于消息系统的催办。
	 * 
	 * @param jbpmContext
	 * @return
	 */
	public List<TaskItem> getAllTaskItems() {
		List<TaskItem> rows = null;
		List<TaskItem> taskItems = new java.util.concurrent.CopyOnWriteArrayList<TaskItem>();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = this.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				rows = jbpmTaskManager.getAllTaskItems(jbpmContext);
				if (rows != null && rows.size() > 0) {
					Iterator<TaskItem> iterator = rows.iterator();
					while (iterator.hasNext()) {
						TaskItem taskItem = iterator.next();
						taskItems.add(taskItem);
					}
				}
			}
		} catch (Exception ex) {
			logger.debug(ex);
			throw new JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
		if (LogUtils.isDebug()) {
			logger.debug(" all tasks processInstanceId size:"
					+ taskItems.size());
		}
		return taskItems;
	}

	/**
	 * 获取某个流程实例所有的任务（包含已经处理的任务和还未处理的任务）
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public List<TaskItem> getAllTaskItems(Long processInstanceId) {
		List<TaskItem> taskItems = new java.util.concurrent.CopyOnWriteArrayList<TaskItem>();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = this.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				ProcessInstance processInstance = jbpmContext
						.getProcessInstance(processInstanceId);
				TaskMgmtInstance tmi = processInstance.getTaskMgmtInstance();
				Collection<TaskInstance> taskInstances = tmi.getTaskInstances();
				if (taskInstances != null && taskInstances.size() > 0) {
					Iterator<TaskInstance> iterator = taskInstances.iterator();
					while (iterator.hasNext()) {
						TaskInstance taskInstance = iterator.next();
						if (taskInstance.hasEnded()) {
							TaskItem taskItem = new TaskItem();
							taskItem.setActorId(taskInstance.getActorId());
							taskItem.setProcessInstanceId(processInstanceId);
							taskItem.setCreateDate(taskInstance.getCreate());
							taskItem.setStartDate(taskInstance.getStart());
							taskItem.setEndDate(taskInstance.getEnd());
							taskItem.setTaskInstanceId(taskInstance.getId());
							taskItem.setTaskName(taskInstance.getName());

							taskItems.add(taskItem);
						} else {
							if (StringUtils.isNotEmpty(taskInstance
									.getActorId())) {
								TaskItem taskItem = new TaskItem();
								taskItem.setActorId(taskInstance.getActorId());
								taskItem.setProcessInstanceId(processInstanceId);

								taskItem.setTaskInstanceId(taskInstance.getId());
								taskItem.setTaskName(taskInstance.getName());
								taskItem.setCreateDate(taskInstance.getCreate());
								taskItem.setStartDate(taskInstance.getStart());
								taskItem.setEndDate(taskInstance.getEnd());
								taskItems.add(taskItem);
							} else {
								Set<PooledActor> pooledActors = taskInstance
										.getPooledActors();
								if (pooledActors != null
										&& pooledActors.size() > 0) {
									Iterator<PooledActor> iter = pooledActors
											.iterator();
									while (iter.hasNext()) {
										PooledActor pa = iter.next();
										TaskItem taskItem = new TaskItem();
										taskItem.setActorId(pa.getActorId());
										taskItem.setProcessInstanceId(processInstanceId);
										taskItem.setCreateDate(taskInstance
												.getCreate());
										taskItem.setStartDate(taskInstance
												.getStart());
										taskItem.setEndDate(taskInstance
												.getEnd());
										taskItem.setTaskInstanceId(taskInstance
												.getId());
										taskItem.setTaskName(taskInstance
												.getName());
										taskItems.add(taskItem);
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			logger.debug(ex);
			throw new JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return taskItems;
	}

	public List<Extension> getExtensions(JbpmContext jbpmContext,
			String processName) {
		List<Extension> extensions = jbpmExtensionManager.getExtensions(
				jbpmContext, processName);
		return extensions;
	}

	/**
	 * 获取用户已经处理过并且流程已经完成的实例编号
	 * 
	 * @param params
	 * @return
	 */
	public List<Long> getFinishedProcessInstanceIds(ProcessQuery query) {
		List<Long> processInstanceIds = new java.util.concurrent.CopyOnWriteArrayList<Long>();
		String statementId = CustomProperties
				.getString("jbpm.getFinishedProcessInstanceId");
		if (StringUtils.isEmpty(statementId)) {
			statementId = "getFinishedProcessInstanceIds";
		}
		List<Object> rows = SqlMapContainer.getContainer().getList(statementId,
				query);
		if (rows != null && rows.size() > 0) {
			Iterator<Object> iterator = rows.iterator();
			while (iterator.hasNext()) {
				Object object = iterator.next();
				if (object instanceof Long) {
					processInstanceIds.add((Long) object);
				} else {
					processInstanceIds.add(Long.parseLong(object.toString()));
				}
			}
		}
		return processInstanceIds;
	}

	/**
	 * 获取用户已经处理过并且流程已经完成的实例编号
	 * 
	 * @param actorId
	 * @return
	 */
	public List<Long> getFinishedProcessInstanceIds(String actorId) {
		List<Long> processInstanceIds = new java.util.concurrent.CopyOnWriteArrayList<Long>();
		ProcessQuery query = new ProcessQuery();
		query.setActorId(actorId);
		String statementId = CustomProperties
				.getString("jbpm.getFinishedProcessInstanceId");
		if (StringUtils.isEmpty(statementId)) {
			statementId = "getFinishedProcessInstanceIds";
		}
		List<Object> rows = SqlMapContainer.getContainer().getList(statementId,
				query);
		if (rows != null && rows.size() > 0) {
			Iterator<Object> iterator = rows.iterator();
			while (iterator.hasNext()) {
				Object object = iterator.next();
				if (object instanceof Long) {
					processInstanceIds.add((Long) object);
				} else {
					processInstanceIds.add(Long.parseLong(object.toString()));
				}
			}
		}
		return processInstanceIds;
	}

	/**
	 * 获取用户已经处理过并且流程已经完成的实例编号
	 * 
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public List<Long> getFinishedProcessInstanceIds(String processName,
			String actorId) {
		ProcessQuery query = new ProcessQuery();
		query.setActorId(actorId);
		query.setProcessName(processName);

		String statementId = CustomProperties
				.getString("jbpm.getFinishedProcessInstanceIds");
		if (StringUtils.isEmpty(statementId)) {
			statementId = "getFinishedProcessInstanceIds";
		}
		List<Object> rows = SqlMapContainer.getContainer().getList(statementId,
				query);
		List<Long> processInstanceIds = new java.util.concurrent.CopyOnWriteArrayList<Long>();
		if (rows != null && rows.size() > 0) {
			Iterator<Object> iterator = rows.iterator();
			while (iterator.hasNext()) {
				Object object = iterator.next();
				if (object instanceof Long) {
					processInstanceIds.add((Long) object);
				} else {
					processInstanceIds.add(Long.parseLong(object.toString()));
				}
			}
		}
		return processInstanceIds;
	}

	public JbpmConfiguration getJbpmConfiguration() {
		if (jbpmConfiguration == null) {
			String filename = SystemProperties.getConfigRootPath()
					+ "/conf/jbpm/jbpm.cfg.xml";
			InputStream inputStream = null;
			try {
				inputStream = FileUtils.getInputStream(filename);
				jbpmConfiguration = JbpmConfiguration
						.parseInputStream(inputStream);
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				IOUtils.closeStream(inputStream);
			}
		}
		return jbpmConfiguration;
	}

	public JbpmExtensionManager getJbpmExtensionManager() {
		return jbpmExtensionManager;
	}

	public JbpmProcessManager getJbpmProcessManager() {
		return jbpmProcessManager;
	}

	public JbpmTaskManager getJbpmTaskManager() {
		return jbpmTaskManager;
	}

	/**
	 * 获取全部最新的流程定义
	 * 
	 * @return
	 */
	public List<ProcessDefinition> getLatestProcessDefinitions() {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = this.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				return jbpmContext.getGraphSession()
						.findLatestProcessDefinitions();
			}
		} catch (Exception ex) {
			throw new JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return null;
	}

	public List<String> getMembershipActorIds(Map<String, Object> params) {
		List<String> actorIds = new java.util.concurrent.CopyOnWriteArrayList<String>();
		SqlMapContainer container = SqlMapContainer.getContainer();
		try {
			Collection<?> actors = container.getList("getXYDeptRoleUsers",
					params);
			if (actors != null && actors.size() > 0) {
				Iterator<?> iterator = actors.iterator();
				while (iterator.hasNext()) {
					Object object = iterator.next();
					String actorId = null;
					if (object instanceof String) {
						actorId = (String) object;
					} else if (object instanceof User) {
						User user = (User) object;
						actorId = user.getActorId();
					}
					if (actorId != null) {
						actorIds.add(actorId);
					}
				}
			}
		} catch (Exception ex) {
			logger.debug(ex);
			throw new org.jbpm.JbpmException(ex);
		}
		return actorIds;
	}

	/**
	 * 根据用户编号和流程实例编号获取该流程实例最小编号的任务
	 * 
	 * 
	 * @param actorId
	 * @param processInstanceId
	 * @return
	 */
	public TaskItem getMinTaskItem(String actorId, Long processInstanceId) {
		TaskItem taskItem = null;
		List<TaskItem> taskItems = this
				.getTaskItems(actorId, processInstanceId);
		if (taskItems != null && taskItems.size() > 0) {
			taskItem = taskItems.get(0);
			Iterator<TaskItem> iterator = taskItems.iterator();
			while (iterator.hasNext()) {
				TaskItem model = iterator.next();
				if (taskItem.getTaskInstanceId() != null
						&& model.getTaskInstanceId() != null) {
					if (taskItem.getTaskInstanceId() > model
							.getTaskInstanceId()) {
						taskItem = model;
					}
				}
			}
		}
		return taskItem;
	}

	public List<String> getNodeNames(Long processInstanceId) {
		List<String> names = new java.util.concurrent.CopyOnWriteArrayList<String>();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = this.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				ProcessInstance pi = jbpmContext
						.getProcessInstance(processInstanceId);
				ProcessDefinition pd = pi.getProcessDefinition();
				List<Node> nodes = pd.getNodes();
				Iterator<Node> iterator = nodes.iterator();
				while (iterator.hasNext()) {
					Node node = iterator.next();
					if (node instanceof TaskNode) {
						names.add(node.getName());
					}
				}
			}
		} catch (Exception ex) {
			logger.debug(ex);
			throw new JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return names;
	}

	public Collection<Node> getNodes(Long processInstanceId) {
		Collection<Node> nodes = new java.util.concurrent.CopyOnWriteArrayList<Node>();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = this.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				ProcessInstance pi = jbpmContext
						.getProcessInstance(processInstanceId);
				ProcessDefinition pd = pi.getProcessDefinition();
				List<Node> rows = pd.getNodes();
				Iterator<Node> iterator = rows.iterator();
				while (iterator.hasNext()) {
					Node node = iterator.next();
					if (node instanceof TaskNode) {
						nodes.add(node);
					}
				}
			}
		} catch (Exception ex) {
			logger.debug(ex);
			throw new JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return nodes;
	}

	public ProcessDefinition getProcessDefinition(Long processDefinitionId) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = this.createJbpmContext();
			if (jbpmContext.getSession() != null && processDefinitionId != null) {
				return jbpmContext.getGraphSession().getProcessDefinition(
						processDefinitionId);
			}
		} catch (Exception ex) {
			throw new JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return null;
	}

	public ProcessInstance getProcessInstance(Long processInstanceId) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = this.createJbpmContext();
			if (jbpmContext.getSession() != null && processInstanceId != null) {
				return jbpmContext.getProcessInstance(processInstanceId);
			}
		} catch (Exception ex) {
			throw new JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return null;
	}

	public List<ProcessInstance> getProcessInstances(String process_name,
			int start, int pageSize) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = this.createJbpmContext();
			if (jbpmContext.getSession() != null
					&& StringUtils.isNotEmpty(process_name)) {

			}
		} catch (Exception ex) {
			throw new JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return null;
	}

	/**
	 * 获取某些用户的任务的流程实例编号
	 * 
	 * @param actorIds
	 * @return
	 */
	public List<Long> getRunningProcessInstanceIds(List<String> actorIds) {
		List<Long> processInstanceIds = new java.util.concurrent.CopyOnWriteArrayList<Long>();
		List<TaskItem> taskItems = this.getTaskItems(actorIds);
		if (taskItems != null && taskItems.size() > 0) {
			Iterator<TaskItem> iterator = taskItems.iterator();
			while (iterator.hasNext()) {
				TaskItem taskItem = iterator.next();
				processInstanceIds.add(taskItem.getProcessInstanceId());
			}
		}

		if (LogUtils.isDebug()) {
			logger.debug(actorIds + " running processInstanceId size:"
					+ processInstanceIds.size());
		}

		return processInstanceIds;
	}

	/**
	 * 获取某个用户的任务的流程实例编号
	 * 
	 * @param actorId
	 * @return
	 */
	public List<Long> getRunningProcessInstanceIds(String actorId) {
		logger.debug("----------------------------------------------------");
		logger.debug("-----------getRunningProcessInstanceIds-------------");
		List<Long> processInstanceIds = new java.util.concurrent.CopyOnWriteArrayList<Long>();
		List<TaskItem> taskItems = this.getTaskItems(actorId);
		if (taskItems != null && taskItems.size() > 0) {
			Iterator<TaskItem> iterator = taskItems.iterator();
			while (iterator.hasNext()) {
				TaskItem taskItem = iterator.next();
				processInstanceIds.add(taskItem.getProcessInstanceId());
			}
		}
		logger.debug(actorId + " task size:" + taskItems.size());
		logger.debug("----------------------------------------------------");
		return processInstanceIds;
	}

	/**
	 * 获取某个用户的任务的流程实例编号
	 * 
	 * @param actorId
	 * @return
	 */
	public List<Long> getRunningProcessInstanceIds(String processName,
			String actorId) {
		logger.debug("----------------------------------------------------");
		logger.debug("-----------getRunningProcessInstanceIds-------------");
		List<Long> processInstanceIds = new java.util.concurrent.CopyOnWriteArrayList<Long>();
		List<TaskItem> taskItems = this.getTaskItemsByProcessName(processName,
				actorId);
		if (taskItems != null && taskItems.size() > 0) {
			Iterator<TaskItem> iterator = taskItems.iterator();
			while (iterator.hasNext()) {
				TaskItem taskItem = iterator.next();
				processInstanceIds.add(taskItem.getProcessInstanceId());
			}
		}
		logger.debug(actorId + " " + processName + " task size:"
				+ taskItems.size());
		logger.debug("----------------------------------------------------");
		return processInstanceIds;
	}

	/**
	 * 获取某个流程任务的流程实例编号
	 * 
	 * @param processName
	 * @return
	 */
	public List<Long> getRunningProcessInstanceIdsByName(String processName) {
		List<Long> processInstanceIds = new java.util.concurrent.CopyOnWriteArrayList<Long>();
		List<TaskItem> taskItems = this.getTaskItemsByProcessName(processName);
		if (taskItems != null && taskItems.size() > 0) {
			Iterator<TaskItem> iterator = taskItems.iterator();
			while (iterator.hasNext()) {
				TaskItem taskItem = iterator.next();
				processInstanceIds.add(taskItem.getProcessInstanceId());
			}
		}

		if (LogUtils.isDebug()) {
			logger.debug(processName + " running processInstanceId size:"
					+ processInstanceIds.size());
		}

		return processInstanceIds;
	}

	/**
	 * 获取某个用户某个流程任务的流程实例编号
	 * 
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public List<Long> getRunningProcessInstanceIdsByName(String processName,
			String actorId) {
		logger.debug("----------------------------------------------------");
		logger.debug("-----------getRunningProcessInstanceIdsByName-------");
		List<Long> processInstanceIds = new java.util.concurrent.CopyOnWriteArrayList<Long>();
		List<TaskItem> taskItems = this.getTaskItemsByProcessName(processName,
				actorId);
		if (taskItems != null && taskItems.size() > 0) {
			Iterator<TaskItem> iterator = taskItems.iterator();
			while (iterator.hasNext()) {
				TaskItem taskItem = iterator.next();
				processInstanceIds.add(taskItem.getProcessInstanceId());
			}
		}
		logger.debug(actorId + " task size:" + taskItems.size());
		logger.debug("----------------------------------------------------");
		return processInstanceIds;
	}

	/**
	 * 获取某些用户的任务实例编号
	 * 
	 * @param actorIds
	 * @return
	 */
	public List<Long> getRunningTaskInstanceIds(List<String> actorIds) {
		List<Long> taskInstanceIds = new java.util.concurrent.CopyOnWriteArrayList<Long>();
		List<TaskItem> taskItems = this.getTaskItems(actorIds);
		if (taskItems != null && taskItems.size() > 0) {
			Iterator<TaskItem> iterator = taskItems.iterator();
			while (iterator.hasNext()) {
				TaskItem taskItem = iterator.next();
				taskInstanceIds.add(taskItem.getTaskInstanceId());
			}
		}

		if (LogUtils.isDebug()) {
			logger.debug(actorIds + " running taskInstanceId size:"
					+ taskInstanceIds.size());
		}

		return taskInstanceIds;
	}

	/**
	 * 获取某个用户的任务实例编号
	 * 
	 * @param actorId
	 * @return
	 */
	public List<Long> getRunningTaskInstanceIds(String actorId) {
		List<Long> taskInstanceIds = new java.util.concurrent.CopyOnWriteArrayList<Long>();
		List<TaskItem> taskItems = this.getTaskItems(actorId);
		if (taskItems != null && taskItems.size() > 0) {
			Iterator<TaskItem> iterator = taskItems.iterator();
			while (iterator.hasNext()) {
				TaskItem taskItem = iterator.next();
				taskInstanceIds.add(taskItem.getTaskInstanceId());
			}
		}

		if (LogUtils.isDebug()) {
			logger.debug(actorId + " running taskInstanceId size:"
					+ taskInstanceIds.size());
		}

		return taskInstanceIds;
	}

	/**
	 * 获取某个流程的任务流程实例编号
	 * 
	 * @param processName
	 * @return
	 */
	public List<Long> getRunningTaskInstanceIdsByName(String processName) {
		List<Long> taskInstanceIds = new java.util.concurrent.CopyOnWriteArrayList<Long>();
		List<TaskItem> taskItems = this.getTaskItemsByProcessName(processName);
		if (taskItems != null && taskItems.size() > 0) {
			Iterator<TaskItem> iterator = taskItems.iterator();
			while (iterator.hasNext()) {
				TaskItem taskItem = iterator.next();
				taskInstanceIds.add(taskItem.getTaskInstanceId());
			}
		}

		if (LogUtils.isDebug()) {
			logger.debug(processName + " running taskInstanceId size:"
					+ taskInstanceIds.size());
		}

		return taskInstanceIds;
	}

	/**
	 * 获取某个用户某个流程的任务实例编号
	 * 
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public List<Long> getRunningTaskInstanceIdsByName(String processName,
			String actorId) {
		logger.debug("----------------------------------------------------");
		logger.debug("-----------getRunningTaskInstanceIdsByName----------");
		List<Long> taskInstanceIds = new java.util.concurrent.CopyOnWriteArrayList<Long>();
		List<TaskItem> taskItems = this.getTaskItemsByProcessName(processName,
				actorId);
		if (taskItems != null && taskItems.size() > 0) {
			Iterator<TaskItem> iterator = taskItems.iterator();
			while (iterator.hasNext()) {
				TaskItem taskItem = iterator.next();
				taskInstanceIds.add(taskItem.getTaskInstanceId());
			}
		}
		logger.debug(actorId + " task size:" + taskItems.size());
		logger.debug("----------------------------------------------------");
		return taskInstanceIds;
	}

	public int getSignal(Long taskInstanceId) {
		int signal = -1;
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = this.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				TaskInstance taskInstance = jbpmContext
						.getTaskInstance(taskInstanceId);
				Task task = taskInstance.getTask();
				TaskNode taskNode = task.getTaskNode();
				signal = taskNode.getSignal();
			}
		} catch (Exception ex) {
			logger.debug(ex);
			throw new JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return signal;
	}

	/**
	 * 获取某个用户待处理的流程任务实例编号
	 * 
	 * @param actorId
	 * @return 待处理的任务实例编号的集合
	 */
	public List<Long> getTaskInstanceIds(String actorId) {
		List<Long> taskInstanceIds = new java.util.concurrent.CopyOnWriteArrayList<Long>();
		JbpmContext jbpmContext = null;
		try {
			// 获取代理人的任务实例
			List<String> agentIds = this.getAgentIds(actorId);
			jbpmContext = this.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				List<TaskInstance> rows01 = jbpmContext.getTaskList(actorId);
				if (rows01 != null && rows01.size() > 0) {
					Iterator<TaskInstance> iterator = rows01.iterator();
					while (iterator.hasNext()) {
						TaskInstance taskInstance = iterator.next();
						taskInstanceIds.add(taskInstance.getId());
					}
				}

				List<String> actorIds = new java.util.concurrent.CopyOnWriteArrayList<String>();
				actorIds.add(actorId);

				List<TaskInstance> rows02 = jbpmContext
						.getGroupTaskList(actorIds);
				if (rows02 != null && rows02.size() > 0) {
					Iterator<TaskInstance> iterator = rows02.iterator();
					while (iterator.hasNext()) {
						TaskInstance taskInstance = iterator.next();
						taskInstanceIds.add(taskInstance.getId());
					}
				}

				if (agentIds.size() > 0) {
					Iterator<String> iterator99 = agentIds.iterator();
					while (iterator99.hasNext()) {
						String tmp = iterator99.next();
						List<TaskInstance> rows03 = jbpmContext
								.getTaskList(tmp);
						if (rows03 != null && rows03.size() > 0) {
							Iterator<TaskInstance> iterator = rows03.iterator();
							while (iterator.hasNext()) {
								TaskInstance taskInstance = iterator.next();
								taskInstanceIds.add(taskInstance.getId());
							}
						}
					}

					List<TaskInstance> rows04 = jbpmContext
							.getGroupTaskList(agentIds);
					if (rows04 != null && rows04.size() > 0) {
						Iterator<TaskInstance> iterator = rows04.iterator();
						while (iterator.hasNext()) {
							TaskInstance taskInstance = iterator.next();
							if (taskInstance.getPooledActors() != null) {
								taskInstanceIds.add(taskInstance.getId());
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			logger.debug(ex);
			throw new JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}

		if (LogUtils.isDebug()) {
			logger.debug(actorId + " running taskInstanceId size:"
					+ taskInstanceIds.size());
		}

		return taskInstanceIds;
	}

	/**
	 * 获取某个用户待处理的流程任务实例
	 * 
	 * @param actorId
	 * @return org.jbpm.taskmgmt.exe.TaskInstance 的集合
	 */
	public List<TaskInstance> getTaskInstances(String actorId) {
		List<TaskInstance> rows = new java.util.concurrent.CopyOnWriteArrayList<TaskInstance>();
		JbpmContext jbpmContext = null;
		try {
			// 获取代理人的任务实例
			List<String> agentIds = this.getAgentIds(actorId);
			jbpmContext = this.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				List<TaskInstance> rowxs = jbpmContext.getTaskList(actorId);
				if (rowxs != null && rowxs.size() > 0) {
					Iterator<TaskInstance> iterator = rowxs.iterator();
					while (iterator.hasNext()) {
						TaskInstance taskInstance = iterator.next();
						rows.add(taskInstance);
					}
				}

				List<String> actorIds = new java.util.concurrent.CopyOnWriteArrayList<String>();
				actorIds.add(actorId);

				List<TaskInstance> rows02 = jbpmContext
						.getGroupTaskList(actorIds);
				if (rows02 != null && rows02.size() > 0) {
					Iterator<TaskInstance> iterator = rows02.iterator();
					while (iterator.hasNext()) {
						TaskInstance taskInstance = iterator.next();
						rows.add(taskInstance);
					}
				}

				if (agentIds.size() > 0) {
					Iterator<String> iterator99 = agentIds.iterator();
					while (iterator99.hasNext()) {
						String tmp = iterator99.next();
						List<TaskInstance> rows03 = jbpmContext
								.getTaskList(tmp);
						if (rows03 != null && rows03.size() > 0) {
							Iterator<TaskInstance> iterator = rows03.iterator();
							while (iterator.hasNext()) {
								TaskInstance taskInstance = iterator.next();
								rows.add(taskInstance);
							}
						}
					}

					List<TaskInstance> rows04 = jbpmContext
							.getGroupTaskList(agentIds);
					if (rows04 != null && rows04.size() > 0) {
						Iterator<TaskInstance> iterator = rows04.iterator();
						while (iterator.hasNext()) {
							TaskInstance taskInstance = iterator.next();
							if (taskInstance.getPooledActors() != null) {
								rows.add(taskInstance);
							}
						}
					}
				}
				rows = this.filterTaskInstance(actorId, rows);
			}
		} catch (Exception ex) {
			logger.debug(ex);
			throw new JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}

		if (LogUtils.isDebug()) {
			logger.debug(actorId + " running taskInstance size:" + rows.size());
		}

		return rows;
	}

	/**
	 * 根据任务实例编号获取任务
	 * 
	 * @param taskInstanceId
	 * @param actorId
	 * @return
	 */
	public TaskItem getTaskItem(Long taskInstanceId, String actorId) {
		TaskItem taskItem = null;
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = this.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				TaskItem item = jbpmTaskManager.getTaskItem(jbpmContext,
						taskInstanceId);
				taskItem = this.filterOne(actorId, item);
			}
		} catch (Exception ex) {
			logger.debug(ex);
			throw new JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return taskItem;
	}

	/**
	 * 获取多个参与者的待办任务
	 * 
	 * @param actorIds
	 * @return
	 */
	public List<TaskItem> getTaskItems(List<String> actorIds) {
		List<TaskItem> taskItems = new java.util.concurrent.CopyOnWriteArrayList<TaskItem>();

		ProcessQuery query = new ProcessQuery();
		query.setActorIds(actorIds);

		String statementId = CustomProperties.getString("jbpm.getTaskItems");
		if (StringUtils.isEmpty(statementId)) {
			statementId = "getTaskItems";
		}

		String statementId2 = CustomProperties
				.getString("jbpm.getPooledTaskItems");
		if (StringUtils.isEmpty(statementId2)) {
			statementId2 = "getPooledTaskItems";
		}

		List<Object> rows = new java.util.concurrent.CopyOnWriteArrayList<Object>();
		List<Object> rows01 = SqlMapContainer.getContainer().getList(
				statementId, query);
		List<Object> rows02 = SqlMapContainer.getContainer().getList(
				statementId2, query);
		if (rows01 != null && rows01.size() > 0) {
			rows.addAll(rows01);
		}
		if (rows02 != null && rows02.size() > 0) {
			rows.addAll(rows02);
		}

		Iterator<Object> iter = rows.iterator();
		while (iter.hasNext()) {
			TaskItem item = (TaskItem) iter.next();
			taskItems.add(item);
		}

		return taskItems;
	}

	/**
	 * 根据参数获取用户的任务实例
	 * 
	 * @param params
	 * @return
	 */
	public List<TaskItem> getTaskItems(ProcessQuery query) {
		logger.debug("----------------------------------------------------");
		logger.debug("-----------------getTaskItems-----------------------");
		List<TaskItem> taskItems = new java.util.concurrent.CopyOnWriteArrayList<TaskItem>();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = this.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				List<TaskItem> rowsx = jbpmTaskManager.getTaskItems(
						jbpmContext, query);
				if (rowsx != null && rowsx.size() > 0) {
					Iterator<TaskItem> iterator = rowsx.iterator();
					while (iterator.hasNext()) {
						TaskItem taskItem = iterator.next();
						taskItems.add(taskItem);
					}
				}
			}
		} catch (Exception ex) {
			logger.debug(ex);
			throw new JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return taskItems;
	}

	/**
	 * 获取用户的任务列表
	 * 
	 * @param actorId
	 * @return
	 */
	public List<TaskItem> getTaskItems(String actorId) {
		List<TaskItem> taskItems = new java.util.concurrent.CopyOnWriteArrayList<TaskItem>();
		List<String> agentIds = this.getAgentIds(actorId);

		List<String> actorIds = new java.util.concurrent.CopyOnWriteArrayList<String>();
		actorIds.add(actorId);
		if (agentIds != null && agentIds.size() > 0) {
			actorIds.addAll(agentIds);
		}

		String statementId = CustomProperties.getString("jbpm.getTaskItems");
		if (StringUtils.isEmpty(statementId)) {
			statementId = "getTaskItems";
		}

		String statementId2 = CustomProperties
				.getString("jbpm.getPooledTaskItems");
		if (StringUtils.isEmpty(statementId2)) {
			statementId2 = "getPooledTaskItems";
		}

		ProcessQuery query = new ProcessQuery();
		query.setActorIds(actorIds);

		this.limitStartDate(query);

		List<Object> rows = new java.util.concurrent.CopyOnWriteArrayList<Object>();
		List<Object> rows01 = SqlMapContainer.getContainer().getList(
				statementId, query);
		List<Object> rows02 = SqlMapContainer.getContainer().getList(
				statementId2, query);
		if (rows01 != null && rows01.size() > 0) {
			rows.addAll(rows01);
		}
		if (rows02 != null && rows02.size() > 0) {
			rows.addAll(rows02);
		}

		Iterator<Object> iter = rows.iterator();
		while (iter.hasNext()) {
			TaskItem item = (TaskItem) iter.next();
			taskItems.add(item);
		}

		logger.debug("taskItems size:" + taskItems.size());

		taskItems = this.filter(actorId, taskItems);

		return taskItems;
	}

	/**
	 * 获取某个流程指定用户的待办任务
	 * 
	 * @param actorId
	 * @param processInstanceId
	 * @return
	 */
	public List<TaskItem> getTaskItems(String actorId, Long processInstanceId) {
		List<TaskItem> taskItems = new java.util.concurrent.CopyOnWriteArrayList<TaskItem>();
		List<String> agentIds = this.getAgentIds(actorId);
		List<String> actorIds = new java.util.concurrent.CopyOnWriteArrayList<String>();
		actorIds.add(actorId);
		if (agentIds != null && agentIds.size() > 0) {
			actorIds.addAll(agentIds);
		}
		ProcessQuery query = new ProcessQuery();
		query.setActorIds(actorIds);
		query.setProcessInstanceId(processInstanceId);

		String statementId = CustomProperties.getString("jbpm.getTaskItems");
		if (StringUtils.isEmpty(statementId)) {
			statementId = "getTaskItems";
		}

		String statementId2 = CustomProperties
				.getString("jbpm.getPooledTaskItems");
		if (StringUtils.isEmpty(statementId2)) {
			statementId2 = "getPooledTaskItems";
		}

		this.limitStartDate(query);

		List<Object> rows = new java.util.concurrent.CopyOnWriteArrayList<Object>();
		List<Object> rows01 = SqlMapContainer.getContainer().getList(
				statementId, query);
		List<Object> rows02 = SqlMapContainer.getContainer().getList(
				statementId2, query);
		if (rows01 != null && rows01.size() > 0) {
			rows.addAll(rows01);
		}
		if (rows02 != null && rows02.size() > 0) {
			rows.addAll(rows02);
		}

		Iterator<Object> iter = rows.iterator();
		while (iter.hasNext()) {
			TaskItem item = (TaskItem) iter.next();
			taskItems.add(item);
		}

		taskItems = this.filter(actorId, taskItems);

		return taskItems;
	}

	/**
	 * 获取用户的任务列表
	 * 
	 * @param actorId
	 *            用户编号
	 * @param paramMap
	 *            参数集合
	 * @return
	 */
	public List<TaskItem> getTaskItems(String actorId, ProcessQuery query) {
		List<TaskItem> taskItems = new java.util.concurrent.CopyOnWriteArrayList<TaskItem>();
		List<String> agentIds = this.getAgentIds(actorId);

		List<String> actorIds = new java.util.concurrent.CopyOnWriteArrayList<String>();
		actorIds.add(actorId);
		if (agentIds != null && agentIds.size() > 0) {
			actorIds.addAll(agentIds);
		}

		String statementId = CustomProperties.getString("jbpm.getTaskItems");
		if (StringUtils.isEmpty(statementId)) {
			statementId = "getTaskItems";
		}

		String statementId2 = CustomProperties
				.getString("jbpm.getPooledTaskItems");
		if (StringUtils.isEmpty(statementId2)) {
			statementId2 = "getPooledTaskItems";
		}

		query.setActorIds(actorIds);

		this.limitStartDate(query);

		List<Object> rows = new java.util.concurrent.CopyOnWriteArrayList<Object>();
		List<Object> rows01 = SqlMapContainer.getContainer().getList(
				statementId, query);
		List<Object> rows02 = SqlMapContainer.getContainer().getList(
				statementId2, query);
		if (rows01 != null && rows01.size() > 0) {
			rows.addAll(rows01);
		}
		if (rows02 != null && rows02.size() > 0) {
			rows.addAll(rows02);
		}

		Iterator<Object> iter = rows.iterator();
		while (iter.hasNext()) {
			TaskItem item = (TaskItem) iter.next();
			taskItems.add(item);
		}

		taskItems = this.filter(actorId, taskItems);

		return taskItems;
	}

	/**
	 * 根据流程实例编号获取用户的任务实例
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public List<TaskItem> getTaskItemsByProcessInstanceId(Long processInstanceId) {
		List<TaskItem> taskItems = new java.util.concurrent.CopyOnWriteArrayList<TaskItem>();
		ProcessQuery query = new ProcessQuery();
		query.setProcessInstanceId(processInstanceId);

		String statementId = CustomProperties.getString("jbpm.getTaskItems");
		if (StringUtils.isEmpty(statementId)) {
			statementId = "getTaskItems";
		}

		String statementId2 = CustomProperties
				.getString("jbpm.getPooledTaskItems");
		if (StringUtils.isEmpty(statementId2)) {
			statementId2 = "getPooledTaskItems";
		}

		List<Object> rows = new java.util.concurrent.CopyOnWriteArrayList<Object>();
		List<Object> rows01 = SqlMapContainer.getContainer().getList(
				statementId, query);
		List<Object> rows02 = SqlMapContainer.getContainer().getList(
				statementId2, query);
		if (rows01 != null && rows01.size() > 0) {
			rows.addAll(rows01);
		}
		if (rows02 != null && rows02.size() > 0) {
			rows.addAll(rows02);
		}

		Iterator<Object> iter = rows.iterator();
		while (iter.hasNext()) {
			TaskItem item = (TaskItem) iter.next();
			taskItems.add(item);
		}

		return taskItems;
	}

	/**
	 * 根据流程实例编号获取用户的任务实例
	 * 
	 * @param processInstanceIds
	 * @return
	 */
	public List<TaskItem> getTaskItemsByProcessInstanceIds(
			List<Long> processInstanceIds) {
		List<TaskItem> taskItems = new java.util.concurrent.CopyOnWriteArrayList<TaskItem>();
		ProcessQuery query = new ProcessQuery();
		query.setProcessInstanceIds(processInstanceIds);

		String statementId = CustomProperties.getString("jbpm.getTaskItems");
		if (StringUtils.isEmpty(statementId)) {
			statementId = "getTaskItems";
		}

		String statementId2 = CustomProperties
				.getString("jbpm.getPooledTaskItems");
		if (StringUtils.isEmpty(statementId2)) {
			statementId2 = "getPooledTaskItems";
		}

		List<Object> rows = new java.util.concurrent.CopyOnWriteArrayList<Object>();
		List<Object> rows01 = SqlMapContainer.getContainer().getList(
				statementId, query);
		List<Object> rows02 = SqlMapContainer.getContainer().getList(
				statementId2, query);
		if (rows01 != null && rows01.size() > 0) {
			rows.addAll(rows01);
		}
		if (rows02 != null && rows02.size() > 0) {
			rows.addAll(rows02);
		}

		Iterator<Object> iter = rows.iterator();
		while (iter.hasNext()) {
			TaskItem item = (TaskItem) iter.next();
			taskItems.add(item);
		}

		return taskItems;
	}

	/**
	 * 获取某个用户某个流程所有版本的待办任务
	 * 
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public List<TaskItem> getTaskItemsByProcessName(List<String> processNames,
			String actorId) {
		List<TaskItem> taskItems = new java.util.concurrent.CopyOnWriteArrayList<TaskItem>();
		List<String> agentIds = this.getAgentIds(actorId);
		List<String> actorIds = new java.util.concurrent.CopyOnWriteArrayList<String>();
		actorIds.add(actorId);
		if (agentIds != null && agentIds.size() > 0) {
			actorIds.addAll(agentIds);
		}
		ProcessQuery query = new ProcessQuery();
		query.setActorIds(actorIds);
		query.setProcessNames(processNames);

		String statementId = CustomProperties.getString("jbpm.getTaskItems");
		if (StringUtils.isEmpty(statementId)) {
			statementId = "getTaskItems";
		}

		String statementId2 = CustomProperties
				.getString("jbpm.getPooledTaskItems");
		if (StringUtils.isEmpty(statementId2)) {
			statementId2 = "getPooledTaskItems";
		}

		this.limitStartDate(query);

		List<Object> rows = new java.util.concurrent.CopyOnWriteArrayList<Object>();
		List<Object> rows01 = SqlMapContainer.getContainer().getList(
				statementId, query);
		List<Object> rows02 = SqlMapContainer.getContainer().getList(
				statementId2, query);
		if (rows01 != null && rows01.size() > 0) {
			rows.addAll(rows01);
		}
		if (rows02 != null && rows02.size() > 0) {
			rows.addAll(rows02);
		}

		Iterator<Object> iter = rows.iterator();
		while (iter.hasNext()) {
			TaskItem item = (TaskItem) iter.next();
			taskItems.add(item);
		}

		taskItems = this.filter(actorId, taskItems);

		return taskItems;
	}

	/**
	 * 获取某个流程所有版本的待办任务
	 * 
	 * @param jbpmContext
	 * @param processName
	 * @return
	 */
	public List<TaskItem> getTaskItemsByProcessName(String processName) {
		List<TaskItem> taskItems = new java.util.concurrent.CopyOnWriteArrayList<TaskItem>();
		ProcessQuery query = new ProcessQuery();
		query.setProcessName(processName);

		String statementId = CustomProperties.getString("jbpm.getTaskItems");
		if (StringUtils.isEmpty(statementId)) {
			statementId = "getTaskItems";
		}

		String statementId2 = CustomProperties
				.getString("jbpm.getPooledTaskItems");
		if (StringUtils.isEmpty(statementId2)) {
			statementId2 = "getPooledTaskItems";
		}

		this.limitStartDate(query);

		List<Object> rows = new java.util.concurrent.CopyOnWriteArrayList<Object>();
		List<Object> rows01 = SqlMapContainer.getContainer().getList(
				statementId, query);
		List<Object> rows02 = SqlMapContainer.getContainer().getList(
				statementId2, query);
		if (rows01 != null && rows01.size() > 0) {
			rows.addAll(rows01);
		}
		if (rows02 != null && rows02.size() > 0) {
			rows.addAll(rows02);
		}

		Iterator<Object> iter = rows.iterator();
		while (iter.hasNext()) {
			TaskItem item = (TaskItem) iter.next();
			taskItems.add(item);
		}

		return taskItems;
	}

	/**
	 * 获取某个用户某个流程所有版本的待办任务
	 * 
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public List<TaskItem> getTaskItemsByProcessName(String processName,
			String actorId) {
		List<TaskItem> taskItems = new java.util.concurrent.CopyOnWriteArrayList<TaskItem>();
		List<String> agentIds = this.getAgentIds(actorId);
		List<String> actorIds = new java.util.concurrent.CopyOnWriteArrayList<String>();
		actorIds.add(actorId);
		if (agentIds != null && agentIds.size() > 0) {
			actorIds.addAll(agentIds);
		}
		ProcessQuery query = new ProcessQuery();
		query.setActorIds(actorIds);
		query.setProcessName(processName);

		String statementId = CustomProperties.getString("jbpm.getTaskItems");
		if (StringUtils.isEmpty(statementId)) {
			statementId = "getTaskItems";
		}

		String statementId2 = CustomProperties
				.getString("jbpm.getPooledTaskItems");
		if (StringUtils.isEmpty(statementId2)) {
			statementId2 = "getPooledTaskItems";
		}

		this.limitStartDate(query);

		List<Object> rows = new java.util.concurrent.CopyOnWriteArrayList<Object>();
		List<Object> rows01 = SqlMapContainer.getContainer().getList(
				statementId, query);
		List<Object> rows02 = SqlMapContainer.getContainer().getList(
				statementId2, query);
		if (rows01 != null && rows01.size() > 0) {
			rows.addAll(rows01);
		}
		if (rows02 != null && rows02.size() > 0) {
			rows.addAll(rows02);
		}

		Iterator<Object> iter = rows.iterator();
		while (iter.hasNext()) {
			TaskItem item = (TaskItem) iter.next();
			taskItems.add(item);
		}

		taskItems = this.filter(actorId, taskItems);

		return taskItems;
	}

	public List<String> getTransitionNames(Long taskInstanceId) {
		List<String> transitions = new java.util.concurrent.CopyOnWriteArrayList<String>();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = this.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				TaskInstance taskInstance = jbpmContext
						.getTaskInstance(taskInstanceId);
				Task task = taskInstance.getTask();
				TaskNode taskNode = task.getTaskNode();
				List<Transition> leavingTransitions = taskNode
						.getLeavingTransitions();
				if (leavingTransitions != null && leavingTransitions.size() > 0) {
					Iterator<Transition> iterator = leavingTransitions
							.iterator();
					while (iterator.hasNext()) {
						Transition transition = iterator.next();
						transitions.add(transition.getName());
					}
				}
			}
		} catch (Exception ex) {
			logger.debug(ex);
			throw new JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return transitions;
	}

	public Collection<Transition> getTransitions(Long taskInstanceId) {
		Collection<Transition> transitions = new java.util.concurrent.CopyOnWriteArrayList<Transition>();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = this.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				TaskInstance taskInstance = jbpmContext
						.getTaskInstance(taskInstanceId);
				Task task = taskInstance.getTask();
				TaskNode taskNode = task.getTaskNode();
				List<Transition> leavingTransitions = taskNode
						.getLeavingTransitions();
				if (leavingTransitions != null && leavingTransitions.size() > 0) {
					Iterator<Transition> iterator = leavingTransitions
							.iterator();
					while (iterator.hasNext()) {
						Transition transition = iterator.next();
						transitions.add(transition);
					}
				}
			}
		} catch (Exception ex) {
			logger.debug(ex);
			throw new JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return transitions;
	}

	public Map<String, User> getUserMap() {
		Map<String, User> userMap = new java.util.concurrent.ConcurrentHashMap<String, User>();
		List<User> users = this.getUsers();
		for (User user : users) {
			userMap.put(user.getActorId(), user);
		}
		return userMap;
	}

	public List<User> getUsers() {
		List<User> users = new java.util.concurrent.CopyOnWriteArrayList<User>();
		Map<String, Object> params = new java.util.HashMap<String, Object>();
		SqlMapContainer container = SqlMapContainer.getContainer();
		try {
			Collection<?> actors = container.getList("getUsers", params);
			if (actors != null && actors.size() > 0) {
				Iterator<?> iterator = actors.iterator();
				while (iterator.hasNext()) {
					Object object = iterator.next();
					if (object instanceof User) {
						User user = (User) object;
						users.add(user);
					}
				}
			}
		} catch (Exception ex) {
			logger.debug(ex);
			throw new org.jbpm.JbpmException(ex);
		}
		return users;
	}

	/**
	 * 获取用户已经处理过的流程实例编号
	 * 
	 * @param params
	 * @return
	 */
	public List<Long> getWorkedProcessInstanceIds(ProcessQuery query) {
		String statementId = CustomProperties
				.getString("jbpm.getWorkedProcessInstanceIds");
		if (StringUtils.isEmpty(statementId)) {
			statementId = "getWorkedProcessInstanceIds";
		}

		this.limitStartDate(query);

		List<Object> rows = SqlMapContainer.getContainer().getList(statementId,
				query);
		List<Long> processInstanceIds = new java.util.concurrent.CopyOnWriteArrayList<Long>();
		if (rows != null && rows.size() > 0) {
			Iterator<Object> iterator = rows.iterator();
			while (iterator.hasNext()) {
				Object object = iterator.next();
				if (object instanceof Long) {
					processInstanceIds.add((Long) object);
				} else {
					processInstanceIds.add(Long.parseLong(object.toString()));
				}
			}
		}
		return processInstanceIds;
	}

	/**
	 * 获取用户已经处理过的流程实例编号
	 * 
	 * @param actorId
	 * @return
	 */
	public List<Long> getWorkedProcessInstanceIds(String actorId) {
		ProcessQuery query = new ProcessQuery();
		query.setActorId(actorId);
		String statementId = CustomProperties
				.getString("jbpm.getWorkedProcessInstanceIds");
		if (StringUtils.isEmpty(statementId)) {
			statementId = "getWorkedProcessInstanceIds";
		}

		this.limitStartDate(query);

		List<Object> rows = SqlMapContainer.getContainer().getList(statementId,
				query);
		List<Long> processInstanceIds = new java.util.concurrent.CopyOnWriteArrayList<Long>();
		if (rows != null && rows.size() > 0) {
			Iterator<Object> iterator = rows.iterator();
			while (iterator.hasNext()) {
				Object object = iterator.next();
				if (object instanceof Long) {
					processInstanceIds.add((Long) object);
				} else {
					processInstanceIds.add(Long.parseLong(object.toString()));
				}
			}
		}
		return processInstanceIds;
	}

	/**
	 * 获取用户已经处理过的流程实例编号
	 * 
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public List<Long> getWorkedProcessInstanceIds(String processName,
			String actorId) {
		ProcessQuery query = new ProcessQuery();
		query.setActorId(actorId);
		query.setProcessName(processName);

		String statementId = CustomProperties
				.getString("jbpm.getWorkedProcessInstanceIds");
		if (StringUtils.isEmpty(statementId)) {
			statementId = "getWorkedProcessInstanceIds";
		}

		this.limitStartDate(query);

		List<Object> rows = SqlMapContainer.getContainer().getList(statementId,
				query);
		List<Long> processInstanceIds = new java.util.concurrent.CopyOnWriteArrayList<Long>();
		if (rows != null && rows.size() > 0) {
			Iterator<Object> iterator = rows.iterator();
			while (iterator.hasNext()) {
				Object object = iterator.next();
				if (object instanceof Long) {
					processInstanceIds.add((Long) object);
				} else {
					processInstanceIds.add(Long.parseLong(object.toString()));
				}
			}
		}
		return processInstanceIds;
	}

	/**
	 * 获取用户已经处理过的流程实例编号
	 * 
	 * processName 流程名称
	 * 
	 * @param actorId
	 * @param paramMap
	 * @return
	 */
	public List<Long> getWorkedProcessInstanceIds(String processName,
			String actorId, ProcessQuery query) {
		query.setActorId(actorId);
		query.setProcessName(processName);

		String statementId = CustomProperties
				.getString("jbpm.getWorkedProcessInstanceIds");
		if (StringUtils.isEmpty(statementId)) {
			statementId = "getWorkedProcessInstanceIds";
		}

		this.limitStartDate(query);

		List<Object> rows = SqlMapContainer.getContainer().getList(statementId,
				query);
		List<Long> processInstanceIds = new java.util.concurrent.CopyOnWriteArrayList<Long>();
		if (rows != null && rows.size() > 0) {
			Iterator<Object> iterator = rows.iterator();
			while (iterator.hasNext()) {
				Object object = iterator.next();
				if (object instanceof Long) {
					processInstanceIds.add((Long) object);
				} else {
					processInstanceIds.add(Long.parseLong(object.toString()));
				}
			}
		}
		return processInstanceIds;
	}

	/**
	 * 根据参数获取已经处理的任务
	 * 
	 * @param params
	 * @return
	 */
	public List<TaskItem> getWorkedTaskItems(ProcessQuery query) {
		String statementId = CustomProperties
				.getString("jbpm.getWorkedTaskItems");
		if (StringUtils.isEmpty(statementId)) {
			statementId = "getWorkedTaskItems";
		}

		this.limitStartDate(query);

		List<Object> rows = SqlMapContainer.getContainer().getList(statementId,
				query);
		List<TaskItem> taskItems = new java.util.concurrent.CopyOnWriteArrayList<TaskItem>();
		if (rows != null && rows.size() > 0) {
			logger.debug("finished task size:" + rows.size());
			Iterator<Object> iter01 = rows.iterator();
			while (iter01.hasNext()) {
				TaskItem taskItem = (TaskItem) iter01.next();
				taskItems.add(taskItem);
			}
		}
		return taskItems;
	}

	/**
	 * 获取用户已经处理过的任务
	 * 
	 * @param actorId
	 * @return
	 */
	public List<TaskItem> getWorkedTaskItems(String actorId) {
		List<String> agentIds = this.getAgentIds(actorId);
		List<String> actorIds = new java.util.concurrent.CopyOnWriteArrayList<String>();
		actorIds.add(actorId);
		if (agentIds != null && agentIds.size() > 0) {
			actorIds.addAll(agentIds);
		}
		ProcessQuery query = new ProcessQuery();
		query.setActorIds(actorIds);

		String statementId = CustomProperties
				.getString("jbpm.getWorkedTaskItems");
		if (StringUtils.isEmpty(statementId)) {
			statementId = "getWorkedTaskItems";
		}

		this.limitStartDate(query);

		List<Object> rows = SqlMapContainer.getContainer().getList(statementId,
				query);

		List<TaskItem> taskItems = new java.util.concurrent.CopyOnWriteArrayList<TaskItem>();

		if (rows != null && rows.size() > 0) {
			logger.debug("query parameter:" + query);
			logger.debug("finished task size:" + rows.size());
			Iterator<Object> iter01 = rows.iterator();
			while (iter01.hasNext()) {
				TaskItem taskItem = (TaskItem) iter01.next();
				taskItems.add(taskItem);
			}
		}

		return taskItems;
	}

	/**
	 * 获取用户已经处理过的任务
	 * 
	 * @param actorId
	 * @return
	 */
	public List<TaskItem> getWorkedTaskItems(String processName, String actorId) {
		List<String> agentIds = this.getAgentIds(actorId);
		List<String> actorIds = new java.util.concurrent.CopyOnWriteArrayList<String>();
		actorIds.add(actorId);
		if (agentIds != null && agentIds.size() > 0) {
			actorIds.addAll(agentIds);
		}
		ProcessQuery query = new ProcessQuery();
		query.setActorIds(actorIds);
		query.setProcessName(processName);

		String statementId = CustomProperties
				.getString("jbpm.getWorkedTaskItems");
		if (StringUtils.isEmpty(statementId)) {
			statementId = "getWorkedTaskItems";
		}

		this.limitStartDate(query);

		List<Object> rows = SqlMapContainer.getContainer().getList(statementId,
				query);

		List<TaskItem> taskItems = new java.util.concurrent.CopyOnWriteArrayList<TaskItem>();

		if (rows != null && rows.size() > 0) {
			logger.debug("query params:" + query);
			logger.debug("finished task size:" + rows.size());
			Iterator<Object> iter01 = rows.iterator();
			while (iter01.hasNext()) {
				TaskItem taskItem = (TaskItem) iter01.next();
				taskItems.add(taskItem);
			}
		}

		return taskItems;
	}

	/**
	 * 获取用户已经处理过的任务
	 * 
	 * @param processName
	 *            流程名称
	 * @param actorId
	 *            参与者编号
	 * @param paramMap
	 *            参数集合
	 * @return
	 */
	public List<TaskItem> getWorkedTaskItems(String processName,
			String actorId, ProcessQuery query) {
		List<String> agentIds = this.getAgentIds(actorId);
		List<String> actorIds = new java.util.concurrent.CopyOnWriteArrayList<String>();
		actorIds.add(actorId);
		if (agentIds != null && agentIds.size() > 0) {
			actorIds.addAll(agentIds);
		}
		query.setActorIds(actorIds);
		query.setProcessName(processName);

		String statementId = CustomProperties
				.getString("jbpm.getWorkedTaskItems");
		if (StringUtils.isEmpty(statementId)) {
			statementId = "getWorkedTaskItems";
		}

		this.limitStartDate(query);

		List<Object> rows = SqlMapContainer.getContainer().getList(statementId,
				query);

		List<TaskItem> taskItems = new java.util.concurrent.CopyOnWriteArrayList<TaskItem>();

		if (rows != null && rows.size() > 0) {
			logger.debug("query params:" + query);
			logger.debug("finished task size:" + rows.size());
			Iterator<Object> iter01 = rows.iterator();
			while (iter01.hasNext()) {
				TaskItem taskItem = (TaskItem) iter01.next();
				taskItems.add(taskItem);
			}
		}

		return taskItems;
	}

	/**
	 * 获取用户的任务列表
	 * 
	 * @param actorId
	 * @return
	 */
	public List<TaskItem> getXYTaskItems(ProcessQuery query) {
		List<TaskItem> taskItems = new java.util.concurrent.CopyOnWriteArrayList<TaskItem>();

		String statementId = CustomProperties.getString("jbpm.getTaskItems");
		if (StringUtils.isEmpty(statementId)) {
			statementId = "getTaskItems";
		}

		String statementId2 = CustomProperties
				.getString("jbpm.getPooledTaskItems");
		if (StringUtils.isEmpty(statementId2)) {
			statementId2 = "getPooledTaskItems";
		}

		this.limitStartDate(query);

		List<Object> rows01 = SqlMapContainer.getContainer().getList(
				statementId, query);
		List<Object> rows02 = SqlMapContainer.getContainer().getList(
				statementId2, query);

		List<Object> rows = new java.util.concurrent.CopyOnWriteArrayList<Object>();

		if (rows01 != null && rows01.size() > 0) {
			rows.addAll(rows01);
		}
		if (rows02 != null && rows02.size() > 0) {
			rows.addAll(rows02);
		}

		Iterator<Object> iter = rows.iterator();
		while (iter.hasNext()) {
			TaskItem item = (TaskItem) iter.next();
			taskItems.add(item);
		}

		return taskItems;
	}

	protected void limitStartDate(ProcessQuery query) {
		if (query.getAfterProcessStartDate() == null) {
			if (CustomProperties.getInt("jbpm.process.limit.day") > 0) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH);
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				int num = CustomProperties.getInt("jbpm.process.limit.day");
				calendar.set(year, month, day - num);
				Date startDate = calendar.getTime();
				query.setAfterProcessStartDate(startDate);
			}
		}
	}

	/**
	 * 将流程中待办任务以前的参与者更改为新的参与者
	 * 
	 * @param previousActorId
	 * @param nowActorId
	 */
	public void reassignAllTasks(String previousActorId, String nowActorId) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = this.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				jbpmTaskManager.reassignAllTasks(jbpmContext, previousActorId,
						nowActorId);
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			logger.debug(ex);
			throw new org.jbpm.JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * 重新分配任务
	 * 
	 * @param taskInstanceId
	 * @param actorIds
	 */
	public void reassignTask(Long taskInstanceId, Set<String> actorIds) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = this.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				jbpmTaskManager.reassignTask(jbpmContext, taskInstanceId,
						actorIds);
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			logger.debug(ex);
			throw new org.jbpm.JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * 重新分配任务
	 * 
	 * @param processInstanceId
	 * @param taskName
	 * @param actorIds
	 */
	public void reassignTask(Long processInstanceId, String taskName,
			Set<String> actorIds) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = this.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				jbpmTaskManager.reassignTask(jbpmContext, processInstanceId,
						taskName, actorIds);
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			logger.debug(ex);
			throw new org.jbpm.JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * 恢复挂起的流程，任务和定时器重新开始。
	 * 
	 * @param processInstanceId
	 */
	public void resume(Long processInstanceId) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = this.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				jbpmProcessManager.resume(jbpmContext, processInstanceId);
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			logger.debug(ex);
			throw new org.jbpm.JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	public void setJbpmExtensionManager(
			JbpmExtensionManager jbpmExtensionManager) {
		this.jbpmExtensionManager = jbpmExtensionManager;
	}

	public void setJbpmProcessManager(JbpmProcessManager jbpmProcessManager) {
		this.jbpmProcessManager = jbpmProcessManager;
	}

	public void setJbpmTaskManager(JbpmTaskManager jbpmTaskManager) {
		this.jbpmTaskManager = jbpmTaskManager;
	}

	/**
	 * 启动流程
	 * 
	 * @param actorId
	 *            当前用户的ID
	 * @param params
	 *            参数Map
	 * @return
	 */
	public Long startProcess(ProcessContext ctx) {
		// 确保每个流程对每个业务单据在同一时刻只能启动一个实例
		String cacheKey = ctx.getProcessName() + "_" + ctx.getRowId();
		JbpmContext jbpmContext = null;
		Long processInstanceId = null;
		try {
			if (cache.get(cacheKey) == null) {
				cache.put(cacheKey, "1");
				jbpmContext = this.createJbpmContext();
				if (jbpmContext.getSession() != null) {
					ctx.setJbpmContext(jbpmContext);
					processInstanceId = jbpmProcessManager.startProcess(ctx);
				}
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			if (logger.isDebugEnabled()) {
				ex.printStackTrace();
				logger.debug(ex);
			}
			throw new org.jbpm.JbpmException(ex);
		} finally {
			cache.remove(cacheKey);
			Context.close(jbpmContext);
		}
		return processInstanceId;
	}

	/**
	 * 挂起某个流程，流程挂起时，任务和定时器暂停。
	 * 
	 * @param processInstanceId
	 */
	public void suspend(Long processInstanceId) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = this.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				jbpmProcessManager.suspend(jbpmContext, processInstanceId);
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			logger.debug(ex);
			throw new org.jbpm.JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}
}