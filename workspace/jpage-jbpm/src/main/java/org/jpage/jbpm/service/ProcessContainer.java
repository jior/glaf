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

package org.jpage.jbpm.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
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
import org.jpage.jbpm.config.ObjectFactory;
import org.jpage.jbpm.context.Context;
import org.jpage.jbpm.context.JbpmContextFactory;
import org.jpage.jbpm.context.ProcessContext;
import org.jpage.jbpm.datafield.DataField;
import org.jpage.jbpm.model.AgentInstance;

import org.jpage.jbpm.model.StateInstance;
import org.jpage.jbpm.model.TaskItem;
import org.jpage.jbpm.util.Constant;
import org.jpage.jbpm.util.ConvertUtil;

public class ProcessContainer {
	private final static Log logger = LogFactory.getLog(ProcessContainer.class);

	public final static ProcessContainer getContainer() {
		if (container == null) {
			container = new ProcessContainer();
		}
		return container;
	}

	private JbpmConfiguration jbpmConfiguration = JbpmConfiguration
			.getInstance();

	private static ProcessContainer container;

	private ActorManager actorManager;

	private ProcessManager processManager;

	private ServiceManager serviceManager;

	private ProcessContainer() {
		actorManager = (ActorManager) JbpmContextFactory
				.getBean("actorManager");
		processManager = (ProcessManager) JbpmContextFactory
				.getBean("processManager");
		serviceManager = (ServiceManager) JbpmContextFactory
				.getBean("serviceManager");
	}

	public JbpmContext createJbpmContext() {
		return jbpmConfiguration.createJbpmContext();
	}

	/**
	 * ��ֹ����
	 * 
	 * @param ctx
	 * @return
	 */
	public boolean abortProcess(ProcessContext ctx) {
		boolean isAbortOK = false;
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				ctx.setJbpmContext(jbpmContext);
				ctx.setNextStepId(Constant.SAVE_AND_CLOSE_TASK);
				processManager.abortProcess(ctx);
				isAbortOK = true;
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			isAbortOK = false;
			logger.error(ex);
			throw new org.jbpm.JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return isAbortOK;
	}

	/**
	 * �������
	 * 
	 * @param ctx
	 * @return
	 */
	public boolean completeTask(ProcessContext ctx) {
		boolean isCompleteOK = true;
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				ctx.setJbpmContext(jbpmContext);
				ctx.setNextStepId(Constant.SAVE_AND_CLOSE_TASK);
				Collection agentIds = this.getAgentIds(jbpmContext,
						ctx.getActorId());
				ctx.setAgentIds(agentIds);
				processManager.completeTask(ctx);
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			isCompleteOK = false;
			logger.error(ex);
			throw new org.jbpm.JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return isCompleteOK;
	}

	/**
	 * ����������ʵ��
	 * 
	 * @param processInstanceId
	 * @param taskName
	 * @param actorIds
	 */
	public void createTaskInstances(String processInstanceId, String taskName,
			Set actorIds) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				processManager.createTaskInstances(jbpmContext,
						processInstanceId, taskName, actorIds);
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			logger.error(ex);
			throw new org.jbpm.JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * ����������ʵ��
	 * 
	 * @param processInstanceId
	 * @param taskName
	 * @param actorIds
	 */
	public void createTaskInstances(String processInstanceId, String taskName,
			Set actorIds, Collection<DataField> dataFields) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				processManager.createTaskInstances(jbpmContext,
						processInstanceId, taskName, actorIds, dataFields);
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			logger.error(ex);
			throw new org.jbpm.JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * ��ȡ������,�����Լ�����ȫ�ִ�����
	 * 
	 * @param jbpmContext
	 * @param actorId
	 * @return
	 */
	public Collection getAgentIds(JbpmContext jbpmContext, String actorId) {
		Collection agentIds = new HashSet();
		try {
			boolean isAgentEnable = org.jpage.jbpm.config.ObjectFactory
					.isAgentEnable();
			if (isAgentEnable) {
				if (jbpmContext.getSession() != null) {
					Map params = new HashMap();
					params.put("actorId", actorId);
					params.put("objectId", "agent");
					List actors = actorManager.getAgents(jbpmContext, params);
					if (actors != null && actors.size() > 0) {
						Iterator iterator = actors.iterator();
						while (iterator.hasNext()) {
							AgentInstance agent = (AgentInstance) iterator
									.next();
							if (agent.isValid()) {
								agentIds.add(agent.getAgentId());
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw new org.jbpm.JbpmException(ex);
		}
		logger.debug(actorId + "�Ĵ�����:" + agentIds);
		return agentIds;
	}

	/**
	 * ��ȡ������,�����Լ������̴�����
	 * 
	 * @param jbpmContext
	 * @param actorId
	 * @param processName
	 * @return
	 */
	protected Collection getAgentIds(JbpmContext jbpmContext, String actorId,
			String processName) {
		Collection agentIds = new HashSet();
		try {
			boolean isAgentEnable = org.jpage.jbpm.config.ObjectFactory
					.isAgentEnable();
			if (isAgentEnable) {
				if (jbpmContext.getSession() != null) {
					Map params = new HashMap();
					params.put("actorId", actorId);
					params.put("objectId", "agent");
					params.put("objectValue", processName);
					List actors = actorManager.getAgents(jbpmContext, params);
					if (actors != null && actors.size() > 0) {
						Iterator iterator = actors.iterator();
						while (iterator.hasNext()) {
							AgentInstance agent = (AgentInstance) iterator
									.next();
							if (agent.isValid()) {
								agentIds.add(agent.getAgentId());
							}
						}
					}
					if (agentIds.size() == 0) {
						agentIds = this.getAgentIds(jbpmContext, actorId);
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw new org.jbpm.JbpmException(ex);
		}

		logger.debug(actorId + "������������'" + processName + "'�Ĵ�����:" + agentIds);

		return agentIds;
	}

	/**
	 * ��ȡ������,�����Լ�����ȫ�ִ�����
	 * 
	 * @param actorId
	 * @return
	 */
	public Collection getAgentIds(String actorId) {
		Collection agentIds = new HashSet();
		JbpmContext jbpmContext = null;
		try {
			boolean isAgentEnable = org.jpage.jbpm.config.ObjectFactory
					.isAgentEnable();
			if (isAgentEnable) {
				jbpmContext = jbpmConfiguration.createJbpmContext();
				if (jbpmContext.getSession() != null) {
					Collection rows = this.getAgentIds(jbpmContext, actorId);
					if (rows != null && rows.size() > 0) {
						agentIds.addAll(rows);
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw new org.jbpm.JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return agentIds;
	}

	/**
	 * ��ȡ������,�����Լ������̴�����
	 * 
	 * @param actorId
	 * @param processName
	 * @return
	 */
	public Collection getAgentIds(String actorId, String processName) {
		Collection agentIds = new HashSet();
		JbpmContext jbpmContext = null;
		try {
			boolean isAgentEnable = org.jpage.jbpm.config.ObjectFactory
					.isAgentEnable();
			if (isAgentEnable) {
				jbpmContext = jbpmConfiguration.createJbpmContext();
				if (jbpmContext.getSession() != null) {
					Collection rows = this.getAgentIds(jbpmContext, actorId,
							processName);
					if (rows != null && rows.size() > 0) {
						agentIds.addAll(rows);
					}
					if (agentIds.size() == 0) {
						agentIds = this.getAgentIds(jbpmContext, actorId);
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw new org.jbpm.JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}

		return agentIds;
	}

	/**
	 * ��ȡ������,�����Լ�����ȫ�ִ�����
	 * 
	 * @param jbpmContext
	 * @param actorId
	 * @return
	 */
	public Collection getAgents(JbpmContext jbpmContext, String actorId) {
		Collection agents = new HashSet();
		try {
			boolean isAgentEnable = org.jpage.jbpm.config.ObjectFactory
					.isAgentEnable();
			if (isAgentEnable) {
				if (jbpmContext.getSession() != null) {
					Map params = new HashMap();
					params.put("actorId", actorId);
					params.put("objectId", "agent");
					List actors = actorManager.getAgents(jbpmContext, params);
					if (actors != null && actors.size() > 0) {
						Iterator iterator = actors.iterator();
						while (iterator.hasNext()) {
							AgentInstance agent = (AgentInstance) iterator
									.next();
							if (agent.isValid()) {
								agents.add(agent);
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw new org.jbpm.JbpmException(ex);
		}
		logger.debug(actorId + "�Ĵ�����:" + agents);
		return agents;
	}

	/**
	 * ��ȡȫ���û��Ĵ�������������Ϣϵͳ�Ĵ߰졣
	 * 
	 * @param jbpmContext
	 * @return
	 */
	public List getAllTaskItems() {
		List rows = null;
		List taskItems = new ArrayList();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				rows = processManager.getAllTaskItems(jbpmContext);
				if (rows != null && rows.size() > 0) {
					Iterator iterator = rows.iterator();
					while (iterator.hasNext()) {
						TaskItem taskItem = (TaskItem) iterator.next();
						taskItems.add(taskItem);
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			Context.close(jbpmContext);
		}
		if (logger.isDebugEnabled()) {
			logger.debug(" all tasks processInstanceId size:"
					+ taskItems.size());
		}
		return taskItems;
	}

	/**
	 * ��ȡĳ������ʵ�����е����񣨰����Ѿ����������ͻ�δ���������
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public List getAllTaskItems(String processInstanceId) {
		List taskItems = new ArrayList();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				ProcessInstance processInstance = jbpmContext
						.getProcessInstance(new Long(processInstanceId)
								.longValue());
				TaskMgmtInstance tmi = processInstance.getTaskMgmtInstance();
				Collection taskInstances = tmi.getTaskInstances();
				if (taskInstances != null && taskInstances.size() > 0) {
					Iterator iterator = taskInstances.iterator();
					while (iterator.hasNext()) {
						TaskInstance taskInstance = (TaskInstance) iterator
								.next();
						if (taskInstance.hasEnded()) {
							TaskItem taskItem = new TaskItem();
							taskItem.setActorId(taskInstance.getActorId());
							taskItem.setProcessInstanceId(processInstanceId);
							taskItem.setTaskCreateDate(taskInstance.getCreate());
							taskItem.setTaskInstanceId(String
									.valueOf(taskInstance.getId()));
							taskItem.setTaskName(taskInstance.getName());
							taskItem.setEnd(taskInstance.hasEnded());
							taskItems.add(taskItem);
						} else {
							if (StringUtils.isNotEmpty(taskInstance
									.getActorId())) {
								TaskItem taskItem = new TaskItem();
								taskItem.setActorId(taskInstance.getActorId());
								taskItem.setProcessInstanceId(processInstanceId);
								taskItem.setTaskCreateDate(taskInstance
										.getCreate());
								taskItem.setTaskInstanceId(String
										.valueOf(taskInstance.getId()));
								taskItem.setTaskName(taskInstance.getName());
								taskItem.setEnd(taskInstance.hasEnded());
								taskItems.add(taskItem);
							} else {
								Set pooledActors = taskInstance
										.getPooledActors();
								if (pooledActors != null
										&& pooledActors.size() > 0) {
									Iterator iter = pooledActors.iterator();
									while (iter.hasNext()) {
										PooledActor pa = (PooledActor) iter
												.next();
										TaskItem taskItem = new TaskItem();
										taskItem.setActorId(pa.getActorId());
										taskItem.setProcessInstanceId(processInstanceId);
										taskItem.setTaskCreateDate(taskInstance
												.getCreate());
										taskItem.setTaskInstanceId(String
												.valueOf(taskInstance.getId()));
										taskItem.setTaskName(taskInstance
												.getName());
										taskItem.setEnd(taskInstance.hasEnded());
										taskItems.add(taskItem);
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return taskItems;
	}

	/**
	 * ��ȡĳ���û��Ѿ���ɵ�����ʵ�����
	 * 
	 * @param actorId
	 * @return
	 */
	public Collection getFinishedProcessInstanceIds(String actorId) {
		Collection processInstanceIds = new HashSet();
		List taskItems = this.getFinishedTaskItems(actorId);
		if (taskItems != null && taskItems.size() > 0) {
			Iterator iterator = taskItems.iterator();
			while (iterator.hasNext()) {
				TaskItem taskItem = (TaskItem) iterator.next();
				processInstanceIds.add(taskItem.getProcessInstanceId());
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug(actorId + " finished processInstanceId size:"
					+ processInstanceIds.size());
		}

		return processInstanceIds;
	}

	/**
	 * ��ȡĳ���û�ĳ�������Ѿ���ɵ�����ʵ�����
	 * 
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public List getFinishedProcessInstanceIds(String processName,
			String actorId) {
		List processInstanceIds = new ArrayList();
		List taskItems = this.getFinishedTaskItems(processName, actorId);
		if (taskItems != null && taskItems.size() > 0) {
			Iterator iterator = taskItems.iterator();
			while (iterator.hasNext()) {
				TaskItem taskItem = (TaskItem) iterator.next();
				processInstanceIds.add(taskItem.getProcessInstanceId());
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug(actorId + " '" + processName
					+ "' finished processInstanceId size:"
					+ processInstanceIds.size());
		}
		return processInstanceIds;
	}

	/**
	 * ��ȡ�û��Ѿ������������
	 * 
	 * @param actorId
	 * @return
	 */
	public List getFinishedTaskItems(String actorId) {
		List taskItems = new ArrayList();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				List rows = processManager.getFinishedTaskItems(jbpmContext,
						actorId);

				if (rows != null && rows.size() > 0) {
					if (logger.isDebugEnabled()) {
						logger.debug(actorId + " finished tasks size:"
								+ rows.size());
					}
					taskItems.addAll(rows);
				}

				Collection agentIds = this.getAgentIds(jbpmContext, actorId);
				if (agentIds.size() > 0) {
					List rowsxy = processManager.getFinishedTaskItems(
							jbpmContext, agentIds);

					if (rowsxy != null && rowsxy.size() > 0) {
						if (logger.isDebugEnabled()) {
							logger.debug(agentIds + " finished tasks size:"
									+ rowsxy.size());
						}
						taskItems.addAll(rowsxy);
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return taskItems;
	}

	/**
	 * ��ȡ�û��Ѿ������������
	 * 
	 * @param actorId
	 * @return
	 */
	public List getFinishedTaskItems(String processName, String actorId) {
		List taskItems = new ArrayList();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				List rows = processManager.getFinishedTaskItems(jbpmContext,
						processName, actorId);

				if (rows != null && rows.size() > 0) {
					if (logger.isDebugEnabled()) {
						logger.debug(actorId + " '" + processName
								+ "' finished tasks size:" + rows.size());
					}
					taskItems.addAll(rows);
				}

				Collection agentIds = this.getAgentIds(jbpmContext, actorId);
				if (agentIds.size() > 0) {
					List rowsxy = processManager.getFinishedTaskItems(
							jbpmContext, processName, agentIds);

					if (rowsxy != null && rowsxy.size() > 0) {
						if (logger.isDebugEnabled()) {
							logger.debug(agentIds + " finished tasks size:"
									+ rowsxy.size());
						}
						taskItems.addAll(rowsxy);
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return taskItems;
	}

	/**
	 * ��ȡͨ����˵�����ʵ�����
	 * 
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public Collection getLastAgreeProcessInstanceId(String processName,
			String actorId) {
		Collection processInstanceIds = new HashSet();
		List rows = this.getStateInstances(processName, actorId);
		if (rows != null && rows.size() > 0) {
			Map dataMap = new HashMap();
			Iterator iterator = rows.iterator();
			while (iterator.hasNext()) {
				StateInstance stateInstance = (StateInstance) iterator.next();
				dataMap.put(stateInstance.getProcessInstanceId(), stateInstance);
			}
			Collection values = dataMap.values();
			Iterator iter = values.iterator();
			while (iter.hasNext()) {
				StateInstance stateInstance = (StateInstance) iter.next();
				if ("complete".equals(stateInstance.getObjectValue())
						&& stateInstance.getOpinion() != 0) {
					processInstanceIds
							.add(stateInstance.getProcessInstanceId());
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug(actorId + " '" + processName
					+ "' last agree processInstanceId size:"
					+ processInstanceIds.size());
		}

		return processInstanceIds;
	}

	/**
	 * ��ȡû��ͨ����˵�����ʵ�����
	 * 
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public Collection getLastNotAgreeProcessInstanceId(String processName,
			String actorId) {
		Collection processInstanceIds = new HashSet();
		List rows = this.getStateInstances(processName, actorId);
		if (rows != null && rows.size() > 0) {
			Map dataMap = new HashMap();
			Iterator iterator = rows.iterator();
			while (iterator.hasNext()) {
				StateInstance stateInstance = (StateInstance) iterator.next();
				dataMap.put(stateInstance.getProcessInstanceId(), stateInstance);
			}
			Collection values = dataMap.values();
			Iterator iter = values.iterator();
			while (iter.hasNext()) {
				StateInstance stateInstance = (StateInstance) iter.next();
				if ("complete".equals(stateInstance.getObjectValue())
						&& stateInstance.getOpinion() == 0) {
					processInstanceIds
							.add(stateInstance.getProcessInstanceId());
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug(actorId + " '" + processName
					+ "' last not agree processInstanceId size:"
					+ processInstanceIds.size());
		}

		return processInstanceIds;
	}

	/**
	 * �����û���ź�����ʵ����Ż�ȡ������ʵ����С��ŵ�����
	 * 
	 * 
	 * @param actorId
	 * @param processInstanceId
	 * @return
	 */
	public TaskItem getMinTaskItem(String actorId, String processInstanceId) {
		TaskItem taskItem = null;
		List taskItems = this.getTaskItems(actorId, processInstanceId);
		if (taskItems != null && taskItems.size() > 0) {
			taskItem = (TaskItem) taskItems.get(0);
			Iterator iterator = taskItems.iterator();
			while (iterator.hasNext()) {
				TaskItem model = (TaskItem) iterator.next();
				if (StringUtils.isNotBlank(taskItem.getTaskInstanceId())
						&& StringUtils.isNotBlank(model.getTaskInstanceId())) {
					if (new Long(taskItem.getTaskInstanceId()).longValue() > new Long(
							model.getTaskInstanceId()).longValue()) {
						taskItem = model;
					}
				}
			}
		}
		return taskItem;
	}

	public Collection<String> getNodeNames(String processInstanceId) {
		Collection<String> names = new ArrayList<String>();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				ProcessInstance pi = jbpmContext.getProcessInstance(Long
						.valueOf(processInstanceId));
				ProcessDefinition pd = pi.getProcessDefinition();
				List<Node> nodes = pd.getNodes();
				Iterator<Node> iterator = nodes.iterator();
				while (iterator.hasNext()) {
					Node node = iterator.next();
					logger.debug("node name:" + node.getName());
					if (node instanceof TaskNode) {
						names.add(node.getName());
					} else if (node instanceof org.jbpm.graph.node.EndState) {
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

	/**
	 * ��ȡĳ���û������������ʵ�����
	 * 
	 * @param actorId
	 * @return �����������ʵ����ŵļ���
	 */

	public Collection getProcessInstanceIds(String actorId) {
		Set processInstanceIds = new HashSet();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				List rowxs = jbpmContext.getTaskList(actorId);
				if (rowxs != null && rowxs.size() > 0) {
					Iterator iterator = rowxs.iterator();
					while (iterator.hasNext()) {
						TaskInstance taskInstance = (TaskInstance) iterator
								.next();
						if (taskInstance.getToken() != null) {
							ProcessInstance processInstance = taskInstance
									.getToken().getProcessInstance();
							processInstanceIds.add(String
									.valueOf(processInstance.getId()));
						}
					}
				}
				List actorIds = new ArrayList();
				actorIds.add(actorId);
				List rowxy = jbpmContext.getGroupTaskList(actorIds);
				if (rowxy != null && rowxy.size() > 0) {
					Iterator iterator = rowxy.iterator();
					while (iterator.hasNext()) {
						TaskInstance taskInstance = (TaskInstance) iterator
								.next();
						if (taskInstance.getToken() != null) {
							ProcessInstance processInstance = taskInstance
									.getToken().getProcessInstance();
							processInstanceIds.add(String
									.valueOf(processInstance.getId()));
						}
					}
				}

				// ��ȡ�����˵�����ʵ��
				Collection agentIds = this.getAgentIds(jbpmContext, actorId);
				if (agentIds.size() > 0) {
					Iterator iterator99 = agentIds.iterator();
					while (iterator99.hasNext()) {
						String tmp = (String) iterator99.next();
						List rows002 = jbpmContext.getTaskList(tmp);
						if (rows002 != null && rows002.size() > 0) {
							Iterator iterator = rows002.iterator();
							while (iterator.hasNext()) {
								TaskInstance taskInstance = (TaskInstance) iterator
										.next();
								if (taskInstance.getToken() != null) {
									ProcessInstance processInstance = taskInstance
											.getToken().getProcessInstance();
									processInstanceIds.add(String
											.valueOf(processInstance.getId()));
								}
							}
						}
					}
					List rowys = jbpmContext.getGroupTaskList(ConvertUtil
							.toList(agentIds));
					if (rowys != null && rowys.size() > 0) {
						Iterator iterator = rowys.iterator();
						while (iterator.hasNext()) {
							TaskInstance taskInstance = (TaskInstance) iterator
									.next();
							if (taskInstance.getToken() != null) {
								ProcessInstance processInstance = taskInstance
										.getToken().getProcessInstance();
								processInstanceIds.add(String
										.valueOf(processInstance.getId()));
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			Context.close(jbpmContext);
		}

		if (logger.isDebugEnabled()) {
			logger.debug(actorId + "  processInstanceId size:"
					+ processInstanceIds.size());
		}

		return processInstanceIds;
	}

	/**
	 * ��ȡĳ���û������������ʵ��
	 * 
	 * @param actorId
	 * @return org.jbpm.graph.exe.ProcessInstance �ļ���
	 */
	public List getProcessInstances(String actorId) {
		List rows = new ArrayList();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				List rowxs = jbpmContext.getTaskList(actorId);
				if (rowxs != null && rowxs.size() > 0) {
					Iterator iterator = rowxs.iterator();
					while (iterator.hasNext()) {
						TaskInstance taskInstance = (TaskInstance) iterator
								.next();
						if (taskInstance.getToken() != null) {
							ProcessInstance processInstance = taskInstance
									.getToken().getProcessInstance();
							rows.add(processInstance);
						}
					}
				}

				List actorIds = new ArrayList();
				actorIds.add(actorId);
				List rowys = jbpmContext.getGroupTaskList(actorIds);
				if (rowys != null && rowys.size() > 0) {
					Iterator iterator = rowys.iterator();
					while (iterator.hasNext()) {
						TaskInstance taskInstance = (TaskInstance) iterator
								.next();
						if (taskInstance.getToken() != null) {
							ProcessInstance processInstance = taskInstance
									.getToken().getProcessInstance();
							rows.add(processInstance);
						}
					}
				}

				// ��ȡ�����˵�����ʵ��
				Collection agentIds = this.getAgentIds(jbpmContext, actorId);
				if (agentIds.size() > 0) {
					Iterator iterator99 = agentIds.iterator();
					while (iterator99.hasNext()) {
						String tmp = (String) iterator99.next();
						List rows002 = jbpmContext.getTaskList(tmp);
						if (rows002 != null && rows002.size() > 0) {
							Iterator iterator = rows002.iterator();
							while (iterator.hasNext()) {
								TaskInstance taskInstance = (TaskInstance) iterator
										.next();
								if (taskInstance.getToken() != null) {
									ProcessInstance processInstance = taskInstance
											.getToken().getProcessInstance();
									rows.add(processInstance);
								}
							}
						}
					}
					List rowzs = jbpmContext.getGroupTaskList(ConvertUtil
							.toList(agentIds));
					if (rowzs != null && rowzs.size() > 0) {
						Iterator iterator = rowzs.iterator();
						while (iterator.hasNext()) {
							TaskInstance taskInstance = (TaskInstance) iterator
									.next();
							if (taskInstance.getToken() != null) {
								ProcessInstance processInstance = taskInstance
										.getToken().getProcessInstance();
								rows.add(processInstance);
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			Context.close(jbpmContext);
		}

		if (logger.isDebugEnabled()) {
			logger.debug(actorId + "  processInstance size:" + rows.size());
		}

		return rows;
	}

	/**
	 * ��������ʵ����Ż�ȡ�û�������ʵ��
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public List getProcessTaskItems(String processInstanceId) {
		List rows = null;
		List taskItems = new ArrayList();
		org.jpage.jbpm.context.Context.create();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = Context.getCurrentJbpmContext();
			if (jbpmContext.getSession() != null) {
				rows = serviceManager.getProcessTaskItems(jbpmContext,
						processInstanceId);
				if (rows != null && rows.size() > 0) {
					Iterator iterator = rows.iterator();
					while (iterator.hasNext()) {
						TaskItem taskItem = (TaskItem) iterator.next();
						taskItems.add(taskItem);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		} finally {
			Context.close(jbpmContext);
			Context.destroy();
		}
		return taskItems;
	}

	/**
	 * ��ȡĳЩ�û������������ʵ�����
	 * 
	 * @param actorIds
	 * @return
	 */
	public Collection getRunningProcessInstanceIds(Collection actorIds) {
		Collection processInstanceIds = new HashSet();
		List taskItems = this.getTaskItems(actorIds);
		if (taskItems != null && taskItems.size() > 0) {
			Iterator iterator = taskItems.iterator();
			while (iterator.hasNext()) {
				TaskItem taskItem = (TaskItem) iterator.next();
				processInstanceIds.add(taskItem.getProcessInstanceId());
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug(actorIds + " running processInstanceId size:"
					+ processInstanceIds.size());
		}

		return processInstanceIds;
	}

	/**
	 * ��ȡĳ���û������������ʵ�����
	 * 
	 * @param actorId
	 * @return
	 */
	public List getRunningProcessInstanceIds(String actorId) {
		logger.debug("----------------------------------------------------");
		logger.debug("-----------getRunningProcessInstanceIds-------------");
		List processInstanceIds = new ArrayList();
		List taskItems = this.getTaskItems(actorId);
		if (taskItems != null && taskItems.size() > 0) {
			Iterator iterator = taskItems.iterator();
			while (iterator.hasNext()) {
				TaskItem taskItem = (TaskItem) iterator.next();
				processInstanceIds.add(taskItem.getProcessInstanceId());
			}
		}
		logger.debug(actorId + " task size:" + taskItems.size());
		logger.debug("----------------------------------------------------");
		return processInstanceIds;
	}

	/**
	 * ��ȡĳ���������������ʵ�����
	 * 
	 * @param processName
	 * @return
	 */
	public Collection getRunningProcessInstanceIdsByName(String processName) {
		Collection processInstanceIds = new HashSet();
		List taskItems = this.getTaskItemsByProcessName(processName);
		if (taskItems != null && taskItems.size() > 0) {
			Iterator iterator = taskItems.iterator();
			while (iterator.hasNext()) {
				TaskItem taskItem = (TaskItem) iterator.next();
				processInstanceIds.add(taskItem.getProcessInstanceId());
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug(processName + " running processInstanceId size:"
					+ processInstanceIds.size());
		}

		return processInstanceIds;
	}

	/**
	 * ��ȡĳ���û�ĳ���������������ʵ�����
	 * 
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public List getRunningProcessInstanceIdsByName(String processName,
			String actorId) {
		logger.debug("----------------------------------------------------");
		logger.debug("-----------getRunningProcessInstanceIdsByName-------");
		List processInstanceIds = new ArrayList();
		List taskItems = this.getTaskItemsByProcessName(processName, actorId);
		if (taskItems != null && taskItems.size() > 0) {
			Iterator iterator = taskItems.iterator();
			while (iterator.hasNext()) {
				TaskItem taskItem = (TaskItem) iterator.next();
				processInstanceIds.add(taskItem.getProcessInstanceId());
			}
		}
		logger.debug(actorId + " task size:" + taskItems.size());
		logger.debug("----------------------------------------------------");
		return processInstanceIds;
	}

	/**
	 * ��ȡĳЩ�û�������ʵ�����
	 * 
	 * @param actorIds
	 * @return
	 */
	public Collection getRunningTaskInstanceIds(Collection actorIds) {
		Collection taskInstanceIds = new HashSet();
		List taskItems = this.getTaskItems(actorIds);
		if (taskItems != null && taskItems.size() > 0) {
			Iterator iterator = taskItems.iterator();
			while (iterator.hasNext()) {
				TaskItem taskItem = (TaskItem) iterator.next();
				taskInstanceIds.add(taskItem.getTaskInstanceId());
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug(actorIds + " running taskInstanceId size:"
					+ taskInstanceIds.size());
		}

		return taskInstanceIds;
	}

	/**
	 * ��ȡĳ���û�������ʵ�����
	 * 
	 * @param actorId
	 * @return
	 */
	public Collection getRunningTaskInstanceIds(String actorId) {
		Collection taskInstanceIds = new HashSet();
		List taskItems = this.getTaskItems(actorId);
		if (taskItems != null && taskItems.size() > 0) {
			Iterator iterator = taskItems.iterator();
			while (iterator.hasNext()) {
				TaskItem taskItem = (TaskItem) iterator.next();
				taskInstanceIds.add(taskItem.getTaskInstanceId());
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug(actorId + " running taskInstanceId size:"
					+ taskInstanceIds.size());
		}

		return taskInstanceIds;
	}

	/**
	 * ��ȡĳ�����̵���������ʵ�����
	 * 
	 * @param processName
	 * @return
	 */
	public Collection getRunningTaskInstanceIdsByName(String processName) {
		Collection taskInstanceIds = new HashSet();
		List taskItems = this.getTaskItemsByProcessName(processName);
		if (taskItems != null && taskItems.size() > 0) {
			Iterator iterator = taskItems.iterator();
			while (iterator.hasNext()) {
				TaskItem taskItem = (TaskItem) iterator.next();
				taskInstanceIds.add(taskItem.getTaskInstanceId());
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug(processName + " running taskInstanceId size:"
					+ taskInstanceIds.size());
		}

		return taskInstanceIds;
	}

	/**
	 * ��ȡĳ���û�ĳ�����̵�����ʵ�����
	 * 
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public Collection getRunningTaskInstanceIdsByName(String processName,
			String actorId) {
		logger.debug("----------------------------------------------------");
		logger.debug("-----------getRunningTaskInstanceIdsByName----------");
		Collection taskInstanceIds = new HashSet();
		List taskItems = this.getTaskItemsByProcessName(processName, actorId);
		if (taskItems != null && taskItems.size() > 0) {
			Iterator iterator = taskItems.iterator();
			while (iterator.hasNext()) {
				TaskItem taskItem = (TaskItem) iterator.next();
				taskInstanceIds.add(taskItem.getTaskInstanceId());
			}
		}
		logger.debug(actorId + " task size:" + taskItems.size());
		logger.debug("----------------------------------------------------");
		return taskInstanceIds;
	}

	public int getSignal(String taskInstanceId) {
		int signal = -1;
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				TaskInstance taskInstance = jbpmContext.getTaskInstance(Long
						.valueOf(taskInstanceId));
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
	 * ��ȡĳ������ʵ���Ĵ���״̬�б�
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public List getStateInstances(String processInstanceId) {
		List rows = null;
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				rows = serviceManager.getStateInstances(jbpmContext,
						processInstanceId);
			}
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return rows;
	}

	/**
	 * ��ȡĳ���������Ƶ��������̵�״̬ʵ���б�
	 * 
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public List getStateInstances(String processName, String actorId) {
		List stateInstances = new ArrayList();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				List rows = serviceManager.getStateInstances(jbpmContext,
						processName, actorId);
				if (rows != null && rows.size() > 0) {
					stateInstances.addAll(rows);
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return stateInstances;
	}

	/**
	 * ��ȡĳ���û����������������ʵ�����
	 * 
	 * @param actorId
	 * @return �����������ʵ����ŵļ���
	 */
	public Collection getTaskInstanceIds(String actorId) {
		Collection taskInstanceIds = new HashSet();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				List rows01 = jbpmContext.getTaskList(actorId);
				if (rows01 != null && rows01.size() > 0) {
					Iterator iterator = rows01.iterator();
					while (iterator.hasNext()) {
						TaskInstance taskInstance = (TaskInstance) iterator
								.next();
						taskInstanceIds
								.add(String.valueOf(taskInstance.getId()));
					}
				}

				List actorIds = new ArrayList();
				List rows02 = jbpmContext.getGroupTaskList(actorIds);
				if (rows02 != null && rows02.size() > 0) {
					Iterator iterator = rows02.iterator();
					while (iterator.hasNext()) {
						TaskInstance taskInstance = (TaskInstance) iterator
								.next();
						taskInstanceIds
								.add(String.valueOf(taskInstance.getId()));
					}
				}

				// ��ȡ�����˵�����ʵ��
				Collection agentIds = this.getAgentIds(jbpmContext, actorId);
				if (agentIds.size() > 0) {
					Iterator iterator99 = agentIds.iterator();
					while (iterator99.hasNext()) {
						String tmp = (String) iterator99.next();
						List rows03 = jbpmContext.getTaskList(tmp);
						if (rows03 != null && rows03.size() > 0) {
							Iterator iterator = rows03.iterator();
							while (iterator.hasNext()) {
								TaskInstance taskInstance = (TaskInstance) iterator
										.next();
								taskInstanceIds.add(String.valueOf(taskInstance
										.getId()));
							}
						}
					}

					List rows04 = jbpmContext.getGroupTaskList(ConvertUtil
							.toList(agentIds));
					if (rows04 != null && rows04.size() > 0) {
						Iterator iterator = rows04.iterator();
						while (iterator.hasNext()) {
							TaskInstance taskInstance = (TaskInstance) iterator
									.next();
							if (taskInstance.getPooledActors() != null) {
								taskInstanceIds.add(String.valueOf(taskInstance
										.getId()));
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			Context.close(jbpmContext);
		}

		if (logger.isDebugEnabled()) {
			logger.debug(actorId + " running taskInstanceId size:"
					+ taskInstanceIds.size());
		}

		return taskInstanceIds;
	}

	/**
	 * ��ȡĳ���û����������������ʵ��
	 * 
	 * @param actorId
	 * @return org.jbpm.taskmgmt.exe.TaskInstance �ļ���
	 */
	public List getTaskInstances(String actorId) {
		List rows = new ArrayList();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				List rowxs = jbpmContext.getTaskList(actorId);
				if (rowxs != null && rowxs.size() > 0) {
					Iterator iterator = rowxs.iterator();
					while (iterator.hasNext()) {
						TaskInstance taskInstance = (TaskInstance) iterator
								.next();
						rows.add(taskInstance);
					}
				}

				List actorIds = new ArrayList();
				List rows02 = jbpmContext.getGroupTaskList(actorIds);
				if (rows02 != null && rows02.size() > 0) {
					Iterator iterator = rows02.iterator();
					while (iterator.hasNext()) {
						TaskInstance taskInstance = (TaskInstance) iterator
								.next();
						rows.add(taskInstance);
					}
				}

				// ��ȡ�����˵�����ʵ��
				Collection agentIds = this.getAgentIds(jbpmContext, actorId);
				if (agentIds.size() > 0) {
					Iterator iterator99 = agentIds.iterator();
					while (iterator99.hasNext()) {
						String tmp = (String) iterator99.next();
						List rows03 = jbpmContext.getTaskList(tmp);
						if (rows03 != null && rows03.size() > 0) {
							Iterator iterator = rows03.iterator();
							while (iterator.hasNext()) {
								TaskInstance taskInstance = (TaskInstance) iterator
										.next();
								rows.add(taskInstance);
							}
						}
					}

					List rows04 = jbpmContext.getGroupTaskList(ConvertUtil
							.toList(agentIds));
					if (rows04 != null && rows04.size() > 0) {
						Iterator iterator = rows04.iterator();
						while (iterator.hasNext()) {
							TaskInstance taskInstance = (TaskInstance) iterator
									.next();
							if (taskInstance.getPooledActors() != null) {
								rows.add(taskInstance);
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			Context.close(jbpmContext);
		}

		if (logger.isDebugEnabled()) {
			logger.debug(actorId + " running taskInstance size:" + rows.size());
		}

		return rows;
	}

	/**
	 * ��������ʵ����Ż�ȡ����
	 * 
	 * @param taskInstanceId
	 * @return
	 */
	public TaskItem getTaskItem(String taskInstanceId) {
		TaskItem taskItem = null;
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				taskItem = processManager.getTaskItem(jbpmContext,
						taskInstanceId);
			}
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return taskItem;
	}

	/**
	 * ��������ʵ����Ż�ȡ����
	 * 
	 * @param taskInstanceId
	 * @param actorId
	 * @return
	 */
	public TaskItem getTaskItem(String taskInstanceId, String actorId) {
		TaskItem taskItem = null;
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				TaskInstance taskInstance = jbpmContext.getTaskInstance(Long
						.valueOf(taskInstanceId));
				ProcessInstance processInstance = taskInstance.getToken()
						.getProcessInstance();
				ProcessDefinition processDefinition = processInstance
						.getProcessDefinition();
				long processInstanceId = processInstance.getId();
				taskItem = new TaskItem();
				taskItem.setTaskCreateDate(taskInstance.getCreate());
				taskItem.setTaskDescription(taskInstance.getDescription());

				taskItem.setTaskInstanceId(taskInstanceId);
				taskItem.setTaskName(taskInstance.getName());

				taskItem.setProcessInstanceId(String.valueOf(processInstanceId));

				taskItem.setProcessName(processDefinition.getName());
				if (StringUtils.isNotEmpty(taskInstance.getActorId())) {
					taskItem.setActorId(taskInstance.getActorId());
				} else {
					Set<PooledActor> pooledActors = taskInstance
							.getPooledActors();
					if (pooledActors != null && pooledActors.size() > 0) {
						final StringBuffer buffer = new StringBuffer();
						final Iterator<PooledActor> iter = pooledActors
								.iterator();
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
		} catch (Exception ex) {
			logger.debug(ex);
			throw new JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return taskItem;
	}

	/**
	 * ��ȡ��������ߵĴ�������
	 * 
	 * @param actorIds
	 * @return
	 */
	public List getTaskItems(Collection actorIds) {
		List taskItems = new ArrayList();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				List rows = processManager.getTaskItems(jbpmContext, actorIds);
				if (rows != null && rows.size() > 0) {
					Iterator iterator = rows.iterator();
					while (iterator.hasNext()) {
						TaskItem taskItem = (TaskItem) iterator.next();
						taskItems.add(taskItem);
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			Context.close(jbpmContext);
		}

		if (logger.isDebugEnabled()) {
			logger.debug(actorIds + " running tasks size:" + taskItems.size());
		}

		return taskItems;
	}

	/**
	 * ��ȡ�û��������б�
	 * 
	 * @param actorId
	 * @return
	 */
	public List getTaskItems(String actorId) {
		logger.debug("----------------------------------------------------");
		logger.debug("-----------------getTaskItems-----------------------");
		List taskItems = new ArrayList();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				List rowsx = processManager.getTaskItems(jbpmContext, actorId);
				if (rowsx != null && rowsx.size() > 0) {
					logger.debug(actorId + "�Լ���������:" + rowsx.size());
					Iterator iterator = rowsx.iterator();
					while (iterator.hasNext()) {
						TaskItem taskItem = (TaskItem) iterator.next();
						if (taskItem.getWfStatus() != -5555) {
							taskItems.add(taskItem);
						}
					}
				}
				// ��ȡ�����˵�����
				Collection agentIds = this.getAgentIds(jbpmContext, actorId);
				if (agentIds.size() > 0) {
					List rowsy = processManager.getTaskItems(jbpmContext,
							agentIds);
					if (rowsy != null && rowsy.size() > 0) {
						logger.debug(actorId + "�����������:" + rowsy.size());
						Iterator iterator = rowsy.iterator();
						while (iterator.hasNext()) {
							TaskItem taskItem = (TaskItem) iterator.next();
							if (taskItem.getWfStatus() != -5555) {
								taskItems.add(taskItem);
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			Context.close(jbpmContext);
		}
		logger.debug(actorId + " task size:" + taskItems.size());
		logger.debug("----------------------------------------------------");
		return taskItems;
	}

	/**
	 * ��������ʵ����ź��û���Ż�ȡ�û�������ʵ�����
	 * 
	 * @param actorId
	 * @param processInstanceId
	 * @return
	 */
	public List getTaskItems(String actorId, String processInstanceId) {
		List taskItems = new ArrayList();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				jbpmContext.setActorId(actorId);
				List rowsx = processManager.getTaskItems(jbpmContext, actorId,
						processInstanceId);
				if (rowsx != null && rowsx.size() > 0) {
					Iterator iterator = rowsx.iterator();
					while (iterator.hasNext()) {
						TaskItem taskItem = (TaskItem) iterator.next();
						taskItems.add(taskItem);
					}
				}
				List rowsy = processManager.getTaskItemsByProcessInstanceId(
						jbpmContext, processInstanceId);
				if (rowsy != null && rowsy.size() > 0) {
					// ��ȡ�����˵�����
					Collection agentIds = this
							.getAgentIds(jbpmContext, actorId);
					Iterator iterator = rowsy.iterator();
					while (iterator.hasNext()) {
						TaskItem taskItem = (TaskItem) iterator.next();
						if (agentIds != null
								&& agentIds.contains(taskItem.getActorId())) {
							taskItems.add(taskItem);
						}
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return taskItems;
	}

	/**
	 * ��������ʵ����Ż�ȡ�û�������ʵ��
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public List getTaskItemsByProcessInstanceId(String processInstanceId) {
		List rows = null;
		JbpmContext jbpmContext = null;
		List taskItems = new ArrayList();
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				rows = processManager.getTaskItemsByProcessInstanceId(
						jbpmContext, processInstanceId);
				if (rows != null && rows.size() > 0) {
					Iterator iterator = rows.iterator();
					while (iterator.hasNext()) {
						TaskItem taskItem = (TaskItem) iterator.next();
						taskItems.add(taskItem);
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			Context.close(jbpmContext);
		}

		if (logger.isDebugEnabled()) {
			logger.debug(processInstanceId + "  tasks size:" + taskItems.size());
		}

		return taskItems;
	}

	/**
	 * ��������ʵ����Ż�ȡ�û�������ʵ��
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public List getTaskItemsByProcessInstanceIds(Collection processInstanceIds) {
		List rows = null;
		JbpmContext jbpmContext = null;
		List taskItems = new ArrayList();
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				rows = processManager.getTaskItemsByProcessInstanceIds(
						jbpmContext, processInstanceIds);
				if (rows != null && rows.size() > 0) {
					Iterator iterator = rows.iterator();
					while (iterator.hasNext()) {
						TaskItem taskItem = (TaskItem) iterator.next();
						taskItems.add(taskItem);
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			Context.close(jbpmContext);
		}

		if (logger.isDebugEnabled()) {
			logger.debug(processInstanceIds + "  tasks size:"
					+ taskItems.size());
		}

		return taskItems;
	}

	/**
	 * ��ȡĳ���û�ĳ���������а汾�Ĵ�������
	 * 
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public List getTaskItemsByProcessName(Collection processNames,
			String actorId) {
		logger.debug("----------------------------------------------------");
		logger.debug("-----------------getTaskItemsByProcessName----------");
		List taskItems = new ArrayList();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				List rowsx = processManager.getTaskItemsByProcessName(
						jbpmContext, processNames, actorId);
				if (rowsx != null && rowsx.size() > 0) {
					Iterator iterator = rowsx.iterator();
					while (iterator.hasNext()) {
						TaskItem taskItem = (TaskItem) iterator.next();
						taskItems.add(taskItem);
					}
				}

				// ��ȡ�����˵�����
				Collection agentIds = this.getAgentIds(jbpmContext, actorId);
				if (agentIds != null && agentIds.size() > 0) {
					List rowsy = processManager.getTaskItemsByProcessName(
							jbpmContext, processNames, agentIds);
					if (rowsy != null && rowsy.size() > 0) {
						Iterator iterator = rowsy.iterator();
						while (iterator.hasNext()) {
							TaskItem taskItem = (TaskItem) iterator.next();
							taskItems.add(taskItem);
						}
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			Context.close(jbpmContext);
		}
		logger.debug(actorId + " task size:" + taskItems.size());
		logger.debug("----------------------------------------------------");
		return taskItems;
	}

	/**
	 * ��ȡĳ���������а汾�Ĵ�������
	 * 
	 * @param jbpmContext
	 * @param processName
	 * @return
	 */
	public List getTaskItemsByProcessName(String processName) {
		List taskItems = new ArrayList();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				List rows = processManager.getTaskItemsByProcessName(
						jbpmContext, processName);
				if (rows != null && rows.size() > 0) {
					Iterator iterator = rows.iterator();
					while (iterator.hasNext()) {
						TaskItem taskItem = (TaskItem) iterator.next();
						taskItems.add(taskItem);
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			Context.close(jbpmContext);
		}

		if (logger.isDebugEnabled()) {
			logger.debug(processName + " running tasks size:"
					+ taskItems.size());
		}

		return taskItems;
	}

	/**
	 * ��ȡĳ���û�ĳ���������а汾�Ĵ�������
	 * 
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public List getTaskItemsByProcessName(String processName, String actorId) {
		logger.debug("----------------------------------------------------");
		logger.debug("-----------------getTaskItemsByProcessName----------");
		List taskItems = new ArrayList();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				List rowsx = processManager.getTaskItemsByProcessName(
						jbpmContext, processName, actorId);
				if (rowsx != null && rowsx.size() > 0) {
					Iterator iterator = rowsx.iterator();
					while (iterator.hasNext()) {
						TaskItem taskItem = (TaskItem) iterator.next();
						taskItems.add(taskItem);
					}
				}
				Collection agentIds = this.getAgentIds(jbpmContext, actorId);

				// ��ȡ�����˵�����
				if (agentIds != null && agentIds.size() > 0) {
					List rowsy = processManager.getTaskItemsByProcessName(
							jbpmContext, processName, agentIds);
					if (rowsy != null && rowsy.size() > 0) {
						Iterator iterator = rowsy.iterator();
						while (iterator.hasNext()) {
							TaskItem taskItem = (TaskItem) iterator.next();
							taskItems.add(taskItem);
						}
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			Context.close(jbpmContext);
		}
		logger.debug(actorId + " task size:" + taskItems.size());
		logger.debug("----------------------------------------------------");
		return taskItems;
	}

	public Collection<String> getTransitionNames(String taskInstanceId) {
		Collection<String> transitions = new ArrayList<String>();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				TaskInstance taskInstance = jbpmContext.getTaskInstance(Long
						.valueOf(taskInstanceId));
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

	/**
	 * ��ȡ�û��������б�
	 * 
	 * @param actorId
	 * @return
	 */
	public List getWorkItems(String actorId) {
		List rows = null;
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				jbpmContext.setActorId(actorId);
				rows = processManager.getWorkItems(jbpmContext, actorId);
			}
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return rows;
	}

	/**
	 * ��ȡĳ���û��Ѿ���ɵ�����ʵ�����
	 * 
	 * @param actorId
	 * @return
	 */
	public Collection getXYFinishedProcessInstanceIds(String actorId) {
		Collection processInstanceIds = new HashSet();
		Collection actorIds = new HashSet();
		actorIds.add(actorId);
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				Collection agentIds = this.getAgentIds(jbpmContext, actorId);
				if (agentIds.size() > 0) {
					actorIds.addAll(agentIds);
				}
				Collection rows = processManager.getFinishedProcessInstanceIds(
						jbpmContext, actorIds);
				if (rows != null) {
					processInstanceIds.addAll(rows);
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			Context.close(jbpmContext);
		}

		if (logger.isDebugEnabled()) {
			logger.debug(actorId + " finished processInstanceId size:"
					+ processInstanceIds.size());
		}

		return processInstanceIds;
	}

	/**
	 * ��ȡĳ���û�ĳ�������Ѿ���ɵ�����ʵ�����
	 * 
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public Collection getXYFinishedProcessInstanceIds(String processName,
			String actorId) {
		Collection processInstanceIds = new HashSet();
		Collection actorIds = new HashSet();
		actorIds.add(actorId);
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				Collection agentIds = this.getAgentIds(jbpmContext, actorId);
				if (agentIds.size() > 0) {
					actorIds.addAll(agentIds);
				}
				Collection rows = processManager.getFinishedProcessInstanceIds(
						jbpmContext, actorIds);
				if (rows != null) {
					processInstanceIds.addAll(rows);
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			Context.close(jbpmContext);
		}

		if (logger.isDebugEnabled()) {
			logger.debug(actorId + " finished processInstanceId size:"
					+ processInstanceIds.size());
		}

		return processInstanceIds;
	}

	/**
	 * �������д���������ǰ�Ĳ����߸���Ϊ�µĲ�����
	 * 
	 * @param jbpmContext
	 * @param previousActorId
	 * @param nowActorId
	 */
	public void reassignAllTasks(String previousActorId, String nowActorId) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				processManager.reassignAllTasks(jbpmContext, previousActorId,
						nowActorId);
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			logger.error(ex);
			throw new org.jbpm.JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * ���·�������
	 * 
	 * @param taskInstanceId
	 * @param actorIds
	 */
	public void reassignTask(String taskInstanceId, Set actorIds) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				processManager.reassignTask(jbpmContext, taskInstanceId,
						actorIds);
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			logger.error(ex);
			throw new org.jbpm.JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * ���·�������
	 * 
	 * @param taskInstanceId
	 * @param actorIds
	 */
	public void reassignTask(String taskInstanceId, Set actorIds,
			Collection<DataField> dataFields) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				processManager.reassignTask(jbpmContext, taskInstanceId,
						actorIds, dataFields);
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			logger.error(ex);
			throw new org.jbpm.JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * ���·�������
	 * 
	 * @param processInstanceId
	 * @param previousActorId
	 * @param actorIds
	 */
	public void reassignTask(String processInstanceId, String previousActorId,
			Set actorIds) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				processManager.reassignTask(jbpmContext, processInstanceId,
						previousActorId, actorIds);
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			logger.error(ex);
			throw new org.jbpm.JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * ���·�������
	 * 
	 * @param processInstanceId
	 * @param previousActorId
	 * @param actorIds
	 */
	public void reassignTask(String processInstanceId, String previousActorId,
			Set actorIds, Collection<DataField> dataFields) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				processManager.reassignTask(jbpmContext, processInstanceId,
						previousActorId, actorIds, dataFields);
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			logger.error(ex);
			throw new org.jbpm.JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * ���·�������
	 * 
	 * @param processInstanceId
	 * @param taskName
	 * @param actorIds
	 */
	public void reassignTaskByTaskName(String processInstanceId,
			String taskName, Set actorIds) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				processManager.reassignTaskByTaskName(jbpmContext,
						processInstanceId, taskName, actorIds);
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			logger.error(ex);
			throw new org.jbpm.JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * ���·�������
	 * 
	 * @param processInstanceId
	 * @param taskName
	 * @param actorIds
	 */
	public void reassignTaskByTaskName(String processInstanceId,
			String taskName, Set actorIds, Collection<DataField> dataFields) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				processManager.reassignTaskByTaskName(jbpmContext,
						processInstanceId, taskName, actorIds, dataFields);
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			logger.error(ex);
			throw new org.jbpm.JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * �ָ���������̣�����Ͷ�ʱ�����¿�ʼ��
	 * 
	 * @param processInstanceId
	 */
	public void resume(String processInstanceId) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				if (ObjectFactory.canSuspendProcess()) {
					processManager.resume(jbpmContext, processInstanceId);
				}
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			logger.error(ex);
			throw new org.jbpm.JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

	/**
	 * ��������
	 * 
	 * @param ctx
	 * 
	 * @return
	 */
	public String startProcess(ProcessContext ctx) {
		JbpmContext jbpmContext = null;
		String processInstanceId = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null
					&& ctx.getProcessName() != null) {
				ctx.setJbpmContext(jbpmContext);
				processInstanceId = processManager.startProcess(ctx);
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			logger.error(ex);
			throw new org.jbpm.JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return processInstanceId;
	}

	/**
	 * ����ĳ�����̣����̹���ʱ������Ͷ�ʱ����ͣ��
	 * 
	 * @param processInstanceId
	 */
	public void suspend(String processInstanceId) {
		if (!ObjectFactory.canSuspendProcess()) {
			throw new RuntimeException("Can't suspend process ");
		}
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			if (jbpmContext.getSession() != null) {
				if (ObjectFactory.canSuspendProcess()) {
					processManager.suspend(jbpmContext, processInstanceId);
				}
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			logger.error(ex);
			throw new org.jbpm.JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

}
