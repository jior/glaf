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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.jbpm.JbpmContext;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.db.GraphSession;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.PooledActor;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;
import org.jpage.core.query.paging.Page;
import org.jpage.jbpm.config.ObjectFactory;
import org.jpage.jbpm.context.ProcessContext;
import org.jpage.jbpm.dao.ProcessDAO;
import org.jpage.jbpm.dao.TaskDAO;
import org.jpage.jbpm.datafield.DataField;
import org.jpage.jbpm.mail.MailUtil;

import org.jpage.jbpm.model.StateInstance;
import org.jpage.jbpm.model.TaskItem;
import org.jpage.jbpm.model.WorkItem;
import org.jpage.jbpm.persistence.PersistenceDAO;
import org.jpage.jbpm.util.Constant;
import org.jpage.jbpm.util.ParamUtil;
import org.jpage.persistence.Executor;
import org.jpage.util.DateTools;
import org.jpage.util.Tools;
import org.jpage.util.UUID32;

public class ProcessManagerImpl implements ProcessManager {
	private final static Log logger = LogFactory
			.getLog(ProcessManagerImpl.class);

	private final static String sp = System.getProperty("file.separator");

	private TaskDAO taskDAO;

	private ProcessDAO processDAO;

	private PersistenceDAO persistenceDAO;

	public ProcessManagerImpl() {

	}

	/**
	 * ��ֹ����
	 * 
	 * @param ctx
	 * @return
	 */
	public void abortProcess(ProcessContext ctx) {
		logger.debug("........................................................");
		logger.debug("...................abort process........................");
		logger.debug("........................................................");

		String actorId = ctx.getActorId();

		long processInstanceId = 0;

		if (StringUtils.isNumeric(ctx.getProcessInstanceId())) {
			processInstanceId = new Long(ctx.getProcessInstanceId())
					.longValue();
		}

		JbpmContext jbpmContext = null;

		ProcessInstance processInstance = null;

		try {

			jbpmContext = ctx.getJbpmContext();
			jbpmContext.setActorId(actorId);

			if (processInstanceId > 0) {

				processInstance = jbpmContext
						.loadProcessInstanceForUpdate(processInstanceId);

				if (processInstance.hasEnded()) {
					logger.debug("==================�����Ѿ����================");
					return;
				}

				StateInstance stateInstance = new StateInstance();
				stateInstance.setStateInstanceId(UUID32.getUUID());
				stateInstance.setActorId(ctx.getActorId());
				stateInstance.setProcessInstanceId(String
						.valueOf(processInstanceId));
				stateInstance.setOpinion(-1);
				stateInstance.setTaskDescription("��ֹ����");
				stateInstance.setTitle("��ֹ����");
				stateInstance.setStartDate(new java.util.Date());
				stateInstance.setVersionNo(System.currentTimeMillis());

				persistenceDAO.save(jbpmContext, stateInstance);

				Collection taskInstances = processInstance
						.getTaskMgmtInstance().getTaskInstances();
				if (taskInstances != null && taskInstances.size() > 0) {
					Iterator iterator = taskInstances.iterator();
					while (iterator.hasNext()) {
						TaskInstance ti = (TaskInstance) iterator.next();
						if (!ti.hasEnded()) {
							// ���Ѿ����ɵ�����ȡ��
							if (logger.isDebugEnabled()) {
								logger.debug("�����Ѿ����ɵ�����:" + ti.getName());
							}
							ti.setSignalling(false);
							ti.suspend();
							jbpmContext.save(ti);
						}
					}
				}

				processInstance.end();

				logger.warn("�����Ѿ�����ֹ��");

			}

		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			logger.error("-----------------------ctx-----------------");
			logger.error("----------------Abort Process Error--------");
			logger.error(ctx);
			logger.error(ex);
			try {
				MailUtil.send(
						ObjectFactory.getWarnMail(),
						"JBPM��ֹ���̴���, processInstanceId:"
								+ ctx.getProcessInstanceId(), ex.getMessage());
			} catch (Exception e) {
			}
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * �������
	 * 
	 * @param ctx
	 */
	public void completeTask(ProcessContext ctx) {
		String nextStepId = ctx.getNextStepId();
		/**
		 * �������ֹ���̵�������ı�ҵ��ʵ����״̬��ͬʱ��Ҫִ������ʱ��action
		 * 
		 */
		if (Constant.ABORT_PROCESS.equals(nextStepId)) {
			this.abortProcess(ctx);
			return;
		}

		logger.debug("........................................................");
		logger.debug("...................start complete task..................");
		logger.debug("........................................................");

		Map params = ctx.getContextMap();
		if (logger.isDebugEnabled()) {
			logger.debug("params:" + params);
		}

		long taskInstanceId = 0;
		long processInstanceId = 0;

		if (StringUtils.isNumeric(ctx.getTaskInstanceId())) {
			taskInstanceId = new Long(ctx.getTaskInstanceId()).longValue();
		}

		if (StringUtils.isNumeric(ctx.getProcessInstanceId())) {
			processInstanceId = new Long(ctx.getProcessInstanceId())
					.longValue();
		}

		String opinion = ParamUtil.getString(params, Constant.PROCESS_OPINION);

		params.put(Constant.CURRENT_ACTORID, ctx.getActorId());
		params.put(Constant.CURRENT_DATE, DateTools.getDate(new Date()));

		String actorId = ctx.getActorId();
		
		if(actorId != null){
			actorId = actorId.trim();
		}

		ProcessDefinition processDefinition = null;
		ProcessInstance processInstance = null;
		TaskInstance taskInstance = null;

		ContextInstance contextInstance = null;
		Task task = null;
		JbpmContext jbpmContext = null;

		try {

			jbpmContext = ctx.getJbpmContext();
			jbpmContext.setActorId(actorId);

			if (taskInstanceId > 0) {
				taskInstance = jbpmContext
						.loadTaskInstanceForUpdate(taskInstanceId);
			}

			if (taskInstance == null && processInstanceId > 0) {
				processInstance = jbpmContext
						.getProcessInstance(processInstanceId);
				if (processInstance != null && !processInstance.isSuspended()) {
					Collection agentIds = ctx.getAgentIds();
					TaskMgmtInstance tmi = processInstance
							.getTaskMgmtInstance();
					Collection<TaskInstance> taskInstances = tmi
							.getTaskInstances();
					if (taskInstances != null && !taskInstances.isEmpty()) {
						Iterator<TaskInstance> iter = taskInstances.iterator();
						while (iter.hasNext()) {
							TaskInstance x = iter.next();
							if (x.isOpen() && !x.hasEnded()) {
								if (x.getActorId() != null) {
									if (StringUtils.equals(actorId,
											x.getActorId())) {
										taskInstanceId = x.getId();
										taskInstance = jbpmContext
												.loadTaskInstanceForUpdate(taskInstanceId);
										break;
									}

									if (agentIds != null
											&& agentIds
													.contains(x.getActorId())) {
										taskInstanceId = x.getId();
										taskInstance = jbpmContext
												.loadTaskInstanceForUpdate(taskInstanceId);
										break;
									}

								} else {
									Set<PooledActor> actors = x
											.getPooledActors();
									if (actors != null && !actors.isEmpty()) {
										for (PooledActor pa : actors) {
											if (StringUtils.equals(actorId,
													pa.getActorId())) {
												taskInstanceId = x.getId();
												taskInstance = jbpmContext
														.loadTaskInstanceForUpdate(taskInstanceId);
												break;
											}

											if (agentIds != null
													&& agentIds.contains(pa
															.getActorId())) {
												taskInstanceId = x.getId();
												taskInstance = jbpmContext
														.loadTaskInstanceForUpdate(taskInstanceId);
												break;
											}
										}
									}
								}
							}
						}
					}
				}
			}

			if (taskInstance == null) {
				logger.debug("=================���񲻴���=================");
				return;
			}

			if (taskInstance.hasEnded()) {
				logger.debug("=================�����Ѿ����=================");
				return;
			}

			processInstance = taskInstance.getProcessInstance();
			if (processInstance.hasEnded()) {
				logger.debug("==================�����Ѿ����================");
				return;
			}

			processDefinition = processInstance.getProcessDefinition();

			String previousActorId = taskInstance.getActorId();

			taskInstance.start(actorId);

			task = taskInstance.getTask();

			contextInstance = processInstance.getContextInstance();
			contextInstance.setVariable(Constant.ACTOR_ID, actorId);
			taskInstance.setVariable(Constant.ACTOR_ID, actorId);

			String taskActorKey = processDefinition.getName() + "_"
					+ taskInstance.getName();
			String taskActor = (String) contextInstance
					.getVariable(taskActorKey);
			if (taskActor != null) {
				taskActor = taskActor + "," + actorId;
			} else {
				taskActor = actorId;
			}

			contextInstance.setVariable(taskActorKey, taskActor);
			contextInstance.setVariable(Constant.PROCESS_LATEST_ACTORID,
					actorId);
			contextInstance.setVariable(Constant.PROCESS_LATEST_TASKINSTANCEID,
					String.valueOf(taskInstance.getId()));

			processInstanceId = processInstance.getId();

			params.put(Constant.PROCESS_INSTANCE_ID,
					String.valueOf(processInstanceId));
			if (task != null) {
				params.put(Constant.TASK_ID, String.valueOf(task.getId()));
				params.put(Constant.TASK_NAME, task.getName());
			}

			Collection dataFields = ctx.getDataFields();
			if (logger.isDebugEnabled()) {
				logger.debug("dataFields:" + dataFields);
			}
			if (dataFields != null && dataFields.size() > 0) {
				if (logger.isDebugEnabled()) {
					logger.debug("����������Ŀ:" + dataFields.size());
				}
				Iterator iter = dataFields.iterator();
				while (iter.hasNext()) {
					DataField dataField = (DataField) iter.next();
					logger.debug(dataField.getName());
					if (StringUtils.isNotBlank(dataField.getName())
							&& dataField.getValue() != null) {
						taskInstance.setVariable(dataField.getName(),
								dataField.getValue());
						contextInstance.setVariable(dataField.getName(),
								dataField.getValue());
						if (logger.isDebugEnabled()) {
							logger.debug("���û�������:" + dataField.getName() + "\t"
									+ dataField.getValue());
						}
						params.put(dataField.getName(), dataField.getValue());
					}
				}
			}

			String isAgree = (String) contextInstance
					.getVariable(Constant.IS_AGREE);
			if (isAgree == null) {
				isAgree = "false";
				contextInstance.setVariable(Constant.IS_AGREE, isAgree);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("����������������->isAgree:"
						+ contextInstance.getVariable(Constant.IS_AGREE));
			}
			// taskInstance.setVariable(Constant.IS_AGREE, isAgree);
			// taskInstance.setVariableLocally(Constant.IS_AGREE, isAgree);

			StateInstance stateInstance = new StateInstance();
			stateInstance.setObjectId("process_step");
			stateInstance.setObjectValue("complete");

			stateInstance.setStateInstanceId(UUID32.getUUID());
			stateInstance.setActorId(ctx.getActorId());
			stateInstance.setPreviousActorIds(previousActorId);
			stateInstance.setVersionNo(System.currentTimeMillis());
			stateInstance.setProcessName(processDefinition.getName());
			stateInstance.setTaskInstanceId(String.valueOf(taskInstanceId));
			if (task != null) {
				stateInstance.setTaskDescription(task.getDescription());
				stateInstance.setTaskId(String.valueOf(task.getId()));
				stateInstance.setTaskName(task.getName());
			}
			stateInstance.setTokenInstanceId(String.valueOf(taskInstance
					.getToken().getId()));
			stateInstance.setProcessInstanceId(String
					.valueOf(processInstanceId));
			stateInstance.setContent(opinion);
			if (StringUtils.equalsIgnoreCase(isAgree, "true")) {
				stateInstance.setOpinion(1);
			} else {
				stateInstance.setOpinion(0);
			}

			if (params.get(Constant.TASK_TITLE) != null) {
				stateInstance.setTitle(ParamUtil.getString(params,
						Constant.TASK_TITLE));
			}

			if (!StringUtils.equals(previousActorId, actorId)) {
				stateInstance.setAgentId(previousActorId);
			}

			if (previousActorId == null) {
				Collection agentIds = ctx.getAgentIds();
				Set pooledActors = taskInstance.getPooledActors();
				if (pooledActors != null && pooledActors.size() > 0) {
					StringBuffer buffer = new StringBuffer();
					StringBuffer buffer2 = new StringBuffer();
					Iterator it99 = pooledActors.iterator();
					while (it99.hasNext()) {
						PooledActor pa = (PooledActor) it99.next();
						buffer.append(pa.getActorId());
						if (it99.hasNext()) {
							buffer.append(",");
						}
						if (agentIds != null
								&& agentIds.contains(pa.getActorId())) {
							buffer2.append(pa.getActorId()).append(",");
						}
					}
					stateInstance.setPreviousActorIds(buffer.toString());
					if (buffer2.length() > 0) {
						buffer2.delete(buffer2.length() - 1, buffer2.length());
						stateInstance.setAgentId(buffer2.toString());
					}
				}
			}

			stateInstance.setStartDate(new java.util.Date());
			stateInstance.setVersionNo(System.currentTimeMillis());

			persistenceDAO.save(jbpmContext, stateInstance);

			if (nextStepId != null) {
				if (Constant.SAVE_AND_CLOSE_TASK.equals(nextStepId)) {
					taskInstance.end();
					logger.debug("finish task instance.");
				} else {
					taskInstance.end(nextStepId);
					logger.debug("finish task instance:" + nextStepId);
				}
			} else {
				taskInstance.end();
				logger.debug("finish task instance.");
			}

			logger.debug("���沢���������.");

			if (taskInstance != null) {
				ProcessInstance p = taskInstance.getToken()
						.getProcessInstance();
				if (p.hasEnded()) {
					logger.debug("�����Ѿ�����.");
				}
			}

			logger.debug("end complete task.............................");
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			logger.error("-----------------------ctx-----------------");
			logger.error(ctx);
			try {
				MailUtil.send(
						ObjectFactory.getWarnMail(),
						"JBPM���̴������, processInstanceId:"
								+ ctx.getProcessInstanceId(), ex.getMessage());
			} catch (Exception e) {
			}
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * Ϊ�û���������
	 * 
	 * @param taskInstanceId
	 * @param actorIds
	 */
	public void createTaskInstances(JbpmContext jbpmContext,
			String taskInstanceId, Set actorIds) {
		ProcessInstance processInstance = null;
		TaskInstance taskInstance = null;
		try {
			jbpmContext.setActorId("0");

			if (StringUtils.isNumeric(taskInstanceId)) {
				taskInstance = jbpmContext.loadTaskInstanceForUpdate(new Long(
						taskInstanceId).longValue());
				processInstance = taskInstance.getToken().getProcessInstance();
				if (processInstance.hasEnded()) {
					logger.debug("==================�����Ѿ����================");
					return;
				}

				if (taskInstance.hasEnded()) {
					logger.debug("=================�����Ѿ����=================");
					return;
				}

				if (actorIds != null && actorIds.size() > 0) {
					TaskMgmtInstance mgr = processInstance
							.getTaskMgmtInstance();
					Task task = taskInstance.getTask();
					Token token = taskInstance.getToken();
					Iterator iterator = actorIds.iterator();
					while (iterator.hasNext()) {
						String actorId = (String) iterator.next();
						TaskInstance ti = new TaskInstance();
						ti.setPriority(5);
						ti.setToken(token);
						ti.setTask(task);
						ti.setActorId(actorId);
						ti.setCreate(new Date());
						ti.setName(task.getName());
						ti.setTaskMgmtInstance(mgr);
						mgr.addTaskInstance(ti);
						logger.debug("create task for " + actorId);
					}
				}
			}

		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ����������ʵ��
	 * 
	 * @param processInstanceId
	 * @param taskName
	 * @param actorIds
	 */
	public void createTaskInstances(JbpmContext jbpmContext,
			String processInstanceId, String taskName, Set actorIds) {
		if (StringUtils.isEmpty(processInstanceId)
				|| !StringUtils.isNumeric(processInstanceId)) {
			logger.error("processInstanceId:" + processInstanceId);
			return;
		}

		if (StringUtils.isEmpty(taskName)) {
			logger.error("taskName:" + taskName);
			return;
		}

		if (actorIds == null || actorIds.size() == 0) {
			logger.error("actorIds:" + actorIds);
			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("processInstanceId:" + processInstanceId);
			logger.debug("taskName:" + taskName);
			logger.debug("actorIds:" + actorIds);
		}

		ProcessInstance processInstance = null;

		try {
			processInstance = jbpmContext.getProcessInstance(new Long(
					processInstanceId).longValue());

			if (processInstance == null) {
				logger.error("==================����ʵ��������================");
				return;
			}

			if (processInstance.hasEnded()) {
				logger.error("==================�����Ѿ����================");
				return;
			}

			TaskMgmtInstance tmi = processInstance.getTaskMgmtInstance();
			Collection taskInstances = tmi.getTaskInstances();

			if (taskInstances != null) {
				Iterator iter = taskInstances.iterator();
				while (iter.hasNext()) {
					TaskInstance x = (TaskInstance) iter.next();
					if (StringUtils.equalsIgnoreCase(taskName, x.getName())) {
						if (x.isOpen() && !x.hasEnded()) {
							if (StringUtils.equals(x.getName(), taskName)) {
								Iterator iter99 = actorIds.iterator();
								while (iter99.hasNext()) {
									String actorId = (String) iter99.next();
									TaskInstance xx = tmi.createTaskInstance(
											x.getTask(), x.getToken());
									xx.setActorId(actorId);
									xx.setCreate(new Date());
									xx.setSignalling(true);
								}
								break;
							}
						}
					}
				}
			}

		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ����������ʵ��
	 * 
	 * @param processInstanceId
	 * @param taskName
	 * @param actorIds
	 */
	public void createTaskInstances(JbpmContext jbpmContext,
			String processInstanceId, String taskName, Set actorIds,
			Collection<DataField> dataFields) {
		if (StringUtils.isEmpty(processInstanceId)
				|| !StringUtils.isNumeric(processInstanceId)) {
			logger.error("processInstanceId:" + processInstanceId);
			return;
		}

		if (StringUtils.isEmpty(taskName)) {
			logger.error("taskName:" + taskName);
			return;
		}

		if (actorIds == null || actorIds.size() == 0) {
			logger.error("actorIds:" + actorIds);
			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("processInstanceId:" + processInstanceId);
			logger.debug("taskName:" + taskName);
			logger.debug("actorIds:" + actorIds);
		}

		ProcessInstance processInstance = null;

		try {
			processInstance = jbpmContext.getProcessInstance(new Long(
					processInstanceId).longValue());

			if (processInstance == null) {
				logger.error("==================����ʵ��������================");
				return;
			}

			if (processInstance.hasEnded()) {
				logger.error("==================�����Ѿ����================");
				return;
			}

			if (dataFields != null && !dataFields.isEmpty()) {
				ContextInstance contextInstance = processInstance
						.getContextInstance();
				Iterator iter = dataFields.iterator();
				while (iter.hasNext()) {
					DataField dataField = (DataField) iter.next();
					logger.debug(dataField.getName());
					if (StringUtils.isNotBlank(dataField.getName())
							&& dataField.getValue() != null) {
						contextInstance.setVariable(dataField.getName(),
								dataField.getValue());
						if (logger.isDebugEnabled()) {
							logger.debug("���û�������:" + dataField.getName() + "\t"
									+ dataField.getValue());
						}
					}
				}
			}

			TaskMgmtInstance tmi = processInstance.getTaskMgmtInstance();
			Collection taskInstances = tmi.getTaskInstances();

			if (taskInstances != null) {
				Iterator iter = taskInstances.iterator();
				while (iter.hasNext()) {
					TaskInstance x = (TaskInstance) iter.next();
					if (StringUtils.equalsIgnoreCase(taskName, x.getName())) {
						if (x.isOpen() && !x.hasEnded()) {
							if (StringUtils.equals(x.getName(), taskName)) {
								Iterator iter99 = actorIds.iterator();
								while (iter99.hasNext()) {
									String actorId = (String) iter99.next();
									TaskInstance xx = tmi.createTaskInstance(
											x.getTask(), x.getToken());
									xx.setActorId(actorId);
									xx.setCreate(new Date());
									xx.setSignalling(true);
								}
								break;
							}
						}
					}
				}
			}

		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ɾ��maxResults���Ѿ���ɵĲ�����finishDate��ǰ������ʵ��
	 * 
	 * @param processDefinitionId
	 * @param maxResults
	 * @param finishDate
	 */
	public void deleteFinishedProcessInstances(JbpmContext jbpmContext,
			String processDefinitionId, int maxResults, Date finishDate) {
		try {
			if (StringUtils.isNumeric(processDefinitionId)) {
				GraphSession session = jbpmContext.getGraphSession();

				Executor queryExecutor = new Executor();

				StringBuffer querySQL = new StringBuffer();
				querySQL.append(
						" select pi from org.jbpm.graph.exe.ProcessInstance as pi ")
						.append(" where pi.processDefinition.id = :processDefinitionId and pi.end is null and pi.end < :finishDate ");

				Map params = new HashMap();
				params.put("processDefinitionId", new Long(processDefinitionId));
				params.put("finishDate", finishDate);

				queryExecutor.setQuery(querySQL.toString());
				queryExecutor.setParams(params);

				if (maxResults > 1000) {
					maxResults = 1000;
				}

				List rows = persistenceDAO.query(jbpmContext, 1, maxResults,
						queryExecutor);
				if (rows != null && rows.size() > 0) {
					Iterator iterator = rows.iterator();
					while (iterator.hasNext()) {
						ProcessInstance processInstance = (ProcessInstance) iterator
								.next();
						session.deleteProcessInstance(processInstance.getId());
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ɾ�����̶���ʵ��
	 * 
	 * @param processDefinitionId
	 */
	public void deleteProcessDefinition(JbpmContext jbpmContext,
			String processDefinitionId) {
		try {
			if (StringUtils.isNumeric(processDefinitionId)) {
				GraphSession session = jbpmContext.getGraphSession();
				List rows = session.findProcessInstances(new Long(
						processDefinitionId).longValue());
				if (rows == null || rows.size() == 0) {
					session.deleteProcessDefinition(new Long(
							processDefinitionId).longValue());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ɾ������ʵ��
	 * 
	 * @param processInstanceId
	 */
	public void deleteProcessInstance(JbpmContext jbpmContext,
			String processInstanceId) {
		try {
			if (StringUtils.isNumeric(processInstanceId)) {
				GraphSession session = jbpmContext.getGraphSession();
				session.deleteProcessInstance(new Long(processInstanceId)
						.longValue());
				processDAO
						.deleteProcessInstance(jbpmContext, processInstanceId);
				logger.debug("�����Ѿ�ɾ��!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ��ȡȫ�����̶���ʵ��
	 * 
	 * @return
	 */
	public List getAllProcessDefinitions(JbpmContext jbpmContext) {
		try {
			List rows = jbpmContext.getGraphSession()
					.findAllProcessDefinitions();
			return rows;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ��ȡȫ���û��Ĵ�������������Ϣϵͳ�Ĵ߰졣
	 * 
	 * @param jbpmContext
	 * @return
	 */
	public List getAllTaskItems(JbpmContext jbpmContext) {
		try {
			return taskDAO.getAllTaskItems(jbpmContext);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ��ȡ�û��Ѿ������������ʵ�����
	 * 
	 * @param jbpmContext
	 * @param actorIds
	 * @return
	 */
	public Collection getFinishedProcessInstanceIds(JbpmContext jbpmContext,
			Collection actorIds) {
		try {
			return taskDAO.getFinishedProcessInstanceIds(jbpmContext, actorIds);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ��ȡ�û��Ѿ������������
	 * 
	 * @param actorIds
	 * @return
	 */
	public List getFinishedTaskItems(JbpmContext jbpmContext,
			Collection actorIds) {
		try {
			return taskDAO.getFinishedTaskItems(jbpmContext, actorIds);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ��ȡ�û��Ѿ������������
	 * 
	 * @param actorId
	 * @return
	 */
	public List getFinishedTaskItems(JbpmContext jbpmContext, String actorId) {
		try {
			return taskDAO.getFinishedTaskItems(jbpmContext, actorId);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ��ȡ�û��Ѿ������������
	 * 
	 * @param processName
	 * @param actorIds
	 * @return
	 */
	public List getFinishedTaskItems(JbpmContext jbpmContext,
			String processName, Collection actorIds) {
		try {
			return taskDAO.getFinishedTaskItems(jbpmContext, processName,
					actorIds);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ��ȡ�û��Ѿ������������
	 * 
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public List getFinishedTaskItems(JbpmContext jbpmContext,
			String processName, String actorId) {
		try {
			return taskDAO.getFinishedTaskItems(jbpmContext, processName,
					actorId);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ��ȡĳ���������°汾��ȫ������ʵ��
	 * 
	 * @param processName
	 * @return
	 */
	public List getLatestProcessInstances(JbpmContext jbpmContext,
			String processName) {
		try {
			ProcessDefinition processDefinition = jbpmContext.getGraphSession()
					.findLatestProcessDefinition(processName);
			List processInstances = jbpmContext.getGraphSession()
					.findProcessInstances(processDefinition.getId());
			return processInstances;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ��ȡĳ�����̶�������񼯺ϡ� ����key����������(String)��value��org.jbpm.taskmgmt.def.Task����
	 * 
	 * @param processName
	 * @return
	 */
	public Map getMaxVersionTasks(JbpmContext jbpmContext, String processName) {
		try {
			ProcessDefinition processDefinition = jbpmContext.getGraphSession()
					.findLatestProcessDefinition(processName);
			Map tasks = processDefinition.getTaskMgmtDefinition().getTasks();
			Map taskMap = new HashMap();
			if (tasks != null) {
				Iterator iterator = tasks.keySet().iterator();
				while (iterator.hasNext()) {
					String taskName = (String) iterator.next();
					Task task = (Task) tasks.get(taskName);
					taskMap.put(task.getName(), task);
				}
			}
			return taskMap;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

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
			Executor countExecutor, Executor queryExecutor) {
		try {
			return persistenceDAO.getPage(jbpmContext, currPageNo, pageSize,
					countExecutor, queryExecutor);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("---------------count------------------");
			logger.error(countExecutor.getQuery());
			logger.error("---------------query------------------");
			logger.error(queryExecutor.getQuery());
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ��ȡһҳ�Ѿ���ɵ�����ʵ��
	 * 
	 * @param currPageNo
	 * @param pageSize
	 * @param processDefinitionId
	 * @return
	 */
	public Page getPageFinishedProcessInstances(JbpmContext jbpmContext,
			int currPageNo, int pageSize, long processDefinitionId) {
		Executor countExecutor = new Executor();
		Executor queryExecutor = new Executor();

		StringBuffer countSQL = new StringBuffer();
		countSQL.append(
				" select count(pi.id) from org.jbpm.graph.exe.ProcessInstance as pi ")
				.append(" where pi.processDefinition.id = :processDefinitionId and pi.end is not null ");

		StringBuffer querySQL = new StringBuffer();
		querySQL.append(
				" select pi from org.jbpm.graph.exe.ProcessInstance as pi ")
				.append(" where pi.processDefinition.id = :processDefinitionId and pi.end is not null ");

		Map params = new HashMap();
		params.put("processDefinitionId", new Long(processDefinitionId));

		countExecutor.setQuery(countSQL.toString());
		countExecutor.setParams(params);

		queryExecutor.setQuery(querySQL.toString());
		queryExecutor.setParams(params);

		Page page = persistenceDAO.getPage(jbpmContext, currPageNo, pageSize,
				countExecutor, queryExecutor);
		if (page.getRows() != null && page.getRows().size() > 0) {
			Iterator iterator = page.getRows().iterator();
			while (iterator.hasNext()) {
				ProcessInstance processInstance = (ProcessInstance) iterator
						.next();
				if (!Hibernate.isInitialized(processInstance
						.getProcessDefinition())) {
					Hibernate
							.initialize(processInstance.getProcessDefinition());
				}
			}
		}
		return page;
	}

	/**
	 * ��ȡһҳ���������е�����ʵ��
	 * 
	 * @param currPageNo
	 * @param pageSize
	 * @param processDefinitionId
	 * @return
	 */
	public Page getPageRunningProcessInstances(JbpmContext jbpmContext,
			int currPageNo, int pageSize, long processDefinitionId) {
		Executor countExecutor = new Executor();
		Executor queryExecutor = new Executor();

		StringBuffer countSQL = new StringBuffer();
		countSQL.append(
				" select count(pi.id) from org.jbpm.graph.exe.ProcessInstance as pi ")
				.append(" where pi.processDefinition.id = :processDefinitionId and pi.end is null ");

		StringBuffer querySQL = new StringBuffer();
		querySQL.append(
				" select pi from org.jbpm.graph.exe.ProcessInstance as pi ")
				.append(" where pi.processDefinition.id = :processDefinitionId and pi.end is null ");

		Map params = new HashMap();
		params.put("processDefinitionId", new Long(processDefinitionId));

		countExecutor.setQuery(countSQL.toString());
		countExecutor.setParams(params);

		queryExecutor.setQuery(querySQL.toString());
		queryExecutor.setParams(params);

		Page page = persistenceDAO.getPage(jbpmContext, currPageNo, pageSize,
				countExecutor, queryExecutor);
		if (page.getRows() != null && page.getRows().size() > 0) {
			Iterator iterator = page.getRows().iterator();
			while (iterator.hasNext()) {
				ProcessInstance processInstance = (ProcessInstance) iterator
						.next();
				if (!Hibernate.isInitialized(processInstance
						.getProcessDefinition())) {
					Hibernate
							.initialize(processInstance.getProcessDefinition());
				}
			}
		}
		return page;
	}

	public PersistenceDAO getPersistenceDAO() {
		return persistenceDAO;
	}

	public ProcessDAO getProcessDAO() {
		return processDAO;
	}

	/**
	 * ��ȡĳ���������Ƶ�����ʵ��
	 * 
	 * @param jbpmContext
	 * @param processName
	 * @return
	 */
	public Collection getProcessInstanceIds(JbpmContext jbpmContext,
			String processName) {
		try {
			return taskDAO.getProcessInstanceIds(jbpmContext, processName);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	public TaskDAO getTaskDAO() {
		return taskDAO;
	}

	/**
	 * ��ȡĳ�������ߵ�����ʵ��
	 * 
	 * @param processName
	 * @return
	 */
	public List getTaskInstances(JbpmContext jbpmContext, String actorId) {
		try {
			List taskInstances = jbpmContext.getTaskList(actorId);
			List actorIds = new ArrayList();
			actorIds.add(actorId);
			List pooledTaskinstances = jbpmContext.getGroupTaskList(actorIds);
			List rows = new ArrayList();
			if (taskInstances != null && taskInstances.size() > 0) {
				rows.addAll(taskInstances);
			}
			if (pooledTaskinstances != null && pooledTaskinstances.size() > 0) {
				rows.addAll(pooledTaskinstances);
			}
			return rows;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ��������ʵ����Ż�ȡ����
	 * 
	 * @param jbpmContext
	 * @param taskInstanceId
	 * @return
	 */
	public TaskItem getTaskItem(JbpmContext jbpmContext, String taskInstanceId) {
		TaskInstance taskInstance = jbpmContext.getTaskInstance(new Long(
				taskInstanceId).longValue());
		ProcessInstance processInstance = taskInstance.getToken()
				.getProcessInstance();
		long processInstanceId = processInstance.getId();

		TaskItem taskItem = new TaskItem();
		taskItem.setActorId(taskInstance.getActorId());
		taskItem.setTaskCreateDate(taskInstance.getCreate());
		taskItem.setTaskDescription(taskInstance.getDescription());
		taskItem.setTaskInstanceId(taskInstanceId);
		taskItem.setTaskName(taskInstance.getName());
		taskItem.setProcessInstanceId(String.valueOf(processInstanceId));

		return taskItem;
	}

	/**
	 * ��ȡ��������ߵĴ�������
	 * 
	 * @param jbpmContext
	 * @param actorIds
	 * @return
	 */
	public List getTaskItems(JbpmContext jbpmContext, Collection actorIds) {
		List taskItems = new ArrayList();
		try {
			if (actorIds != null && actorIds.size() > 0) {
				List rows = taskDAO.getTaskItems(jbpmContext, actorIds);
				if (rows != null && rows.size() > 0) {
					taskItems.addAll(rows);
				}
			}
			return taskItems;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ��ȡ�û��Ĵ�������
	 * 
	 * @param actorId
	 * @return
	 */
	public List getTaskItems(JbpmContext jbpmContext, String actorId) {
		try {
			return taskDAO.getTaskItems(jbpmContext, actorId);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ��������ʵ����ź��û���Ż�ȡ�û�������ʵ�����
	 * 
	 * @param actorId
	 * @param processInstanceId
	 * @return
	 */
	public List getTaskItems(JbpmContext jbpmContext, String actorId,
			String processInstanceId) {
		try {
			return taskDAO
					.getTaskItems(jbpmContext, actorId, processInstanceId);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ��ȡĳ������ʵ���������б�
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public List getTaskItemsByProcessInstanceId(JbpmContext jbpmContext,
			String processInstanceId) {
		try {
			return taskDAO.getTaskItemsByProcessInstanceId(jbpmContext,
					processInstanceId);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ��ȡĳ������ʵ���������б�
	 * 
	 * @param processInstanceIds
	 * @return
	 */
	public List getTaskItemsByProcessInstanceIds(JbpmContext jbpmContext,
			Collection processInstanceIds) {
		try {
			return taskDAO.getTaskItemsByProcessInstanceIds(jbpmContext,
					processInstanceIds);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ��ȡĳЩ�û�ĳ���������а汾�Ĵ�������
	 * 
	 * @param jbpmContext
	 * @param processName
	 * @param actorIds
	 * @return
	 */
	public List getTaskItemsByProcessName(JbpmContext jbpmContext,
			Collection processNames, Collection actorIds) {
		List taskItems = new ArrayList();
		try {
			if (actorIds != null && actorIds.size() > 0) {
				List rows = taskDAO.getTaskItemsByProcessName(jbpmContext,
						processNames, actorIds);
				if (rows != null && rows.size() > 0) {
					taskItems.addAll(rows);
				}
			}
			return taskItems;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ��ȡĳ���û�ĳ���������а汾�Ĵ�������
	 * 
	 * @param jbpmContext
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public List getTaskItemsByProcessName(JbpmContext jbpmContext,
			Collection processNames, String actorId) {
		try {
			return taskDAO.getTaskItemsByProcessName(jbpmContext, processNames,
					actorId);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ��ȡĳ���������а汾�Ĵ�������
	 * 
	 * @param jbpmContext
	 * @param processName
	 * @return
	 */
	public List getTaskItemsByProcessName(JbpmContext jbpmContext,
			String processName) {
		try {
			return taskDAO.getTaskItemsByProcessName(jbpmContext, processName);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ��ȡĳЩ�û�ĳ���������а汾�Ĵ�������
	 * 
	 * @param jbpmContext
	 * @param processName
	 * @param actorIds
	 * @return
	 */
	public List getTaskItemsByProcessName(JbpmContext jbpmContext,
			String processName, Collection actorIds) {
		List taskItems = new ArrayList();
		try {
			if (actorIds != null && actorIds.size() > 0) {
				List rows = taskDAO.getTaskItemsByProcessName(jbpmContext,
						processName, actorIds);
				if (rows != null && rows.size() > 0) {
					taskItems.addAll(rows);
				}
			}
			return taskItems;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ��ȡĳ���û�ĳ���������а汾�Ĵ�������
	 * 
	 * @param jbpmContext
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public List getTaskItemsByProcessName(JbpmContext jbpmContext,
			String processName, String actorId) {
		try {
			return taskDAO.getTaskItemsByProcessName(jbpmContext, processName,
					actorId);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ��ȡĳ�����̶�������񼯺ϡ� ����key����������(String)��value��org.jbpm.taskmgmt.def.Task����
	 * 
	 * @param processDefinitionId
	 * @return
	 */
	public Map getTasks(JbpmContext jbpmContext, long processDefinitionId) {
		try {
			ProcessDefinition processDefinition = jbpmContext.getGraphSession()
					.loadProcessDefinition(processDefinitionId);
			Map tasks = processDefinition.getTaskMgmtDefinition().getTasks();
			return tasks;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ��ȡĳ�����̶�������񼯺ϡ� ����key����������(String)��value��org.jbpm.taskmgmt.def.Task����
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public Map getTasks(JbpmContext jbpmContext, String processInstanceId) {
		try {
			ProcessInstance processInstance = jbpmContext.getGraphSession()
					.loadProcessInstance(
							new Long(processInstanceId).longValue());
			Map tasks = processInstance.getProcessDefinition()
					.getTaskMgmtDefinition().getTasks();
			return tasks;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ��ȡ�û��������б�
	 * 
	 * @param actorId
	 * @return
	 */
	public List getWorkItems(JbpmContext jbpmContext, String actorId) {
		List rows = new ArrayList();
		Map dataMap = new HashMap();
		Set processInstanceIds = new HashSet();
		try {
			List taskInstances = jbpmContext.getTaskList(actorId);
			if (taskInstances != null && taskInstances.size() > 0) {
				logger.debug("taskInstances size:" + taskInstances.size());
				Iterator iterator = taskInstances.iterator();
				while (iterator.hasNext()) {
					if (processInstanceIds.size() > Constant.MAX_WORK_ITEM_SIZE) {
						break;
					}
					TaskInstance taskInstance = (TaskInstance) iterator.next();
					Token token = taskInstance.getToken();
					if (token == null) {
						continue;
					}

					ProcessInstance processInstance = token
							.getProcessInstance();

					if (processInstance.isSuspended()) {
						continue;
					}
					if (processInstance.hasEnded()) {
						continue;
					}

					processInstanceIds.add(String.valueOf(processInstance
							.getId()));
					WorkItem workItem = new WorkItem();
					workItem.setActorId(taskInstance.getActorId());
					workItem.setTaskInstanceId(String.valueOf(taskInstance
							.getId()));
					workItem.setProcessInstanceId(String
							.valueOf(processInstance.getId()));
					workItem.setTaskCreateDate(taskInstance.getCreate());
					workItem.setTaskDescription(taskInstance.getDescription());
					workItem.setTaskName(taskInstance.getName());
					rows.add(workItem);
					dataMap.put(workItem.getProcessInstanceId(), workItem);
				}
			}

			if (processInstanceIds.size() < Constant.MAX_WORK_ITEM_SIZE) {
				List actorIds = new ArrayList();
				actorIds.add(actorId);
				List pooledTaskInstances = jbpmContext
						.getGroupTaskList(actorIds);
				if (pooledTaskInstances != null
						&& pooledTaskInstances.size() > 0) {
					logger.debug("pooled taskInstances size:"
							+ pooledTaskInstances.size());
					Iterator iterator = pooledTaskInstances.iterator();
					while (iterator.hasNext()) {
						if (processInstanceIds.size() > Constant.MAX_WORK_ITEM_SIZE) {
							break;
						}

						TaskInstance taskInstance = (TaskInstance) iterator
								.next();
						Token token = taskInstance.getToken();
						if (token == null) {
							continue;
						}

						ProcessInstance processInstance = token
								.getProcessInstance();

						if (processInstance.isSuspended()) {
							continue;
						}
						if (processInstance.hasEnded()) {
							continue;
						}
						processInstanceIds.add(String.valueOf(processInstance
								.getId()));
						WorkItem workItem = new WorkItem();
						workItem.setActorId(taskInstance.getActorId());
						workItem.setTaskInstanceId(String.valueOf(taskInstance
								.getId()));
						workItem.setProcessInstanceId(String
								.valueOf(processInstance.getId()));
						workItem.setTaskCreateDate(taskInstance.getCreate());
						workItem.setTaskDescription(taskInstance
								.getDescription());
						workItem.setTaskName(taskInstance.getName());
						rows.add(workItem);
						dataMap.put(workItem.getProcessInstanceId(), workItem);
					}
				}
			}

			return rows;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	protected String getYearMonthDayHour() {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
		return year + sp + month + sp + day + sp + hour;
	}

	/**
	 * �������д���������ǰ�Ĳ����߸���Ϊ�µĲ�����
	 * 
	 * @param jbpmContext
	 * @param previousActorId
	 * @param nowActorId
	 */
	public void reassignAllTasks(JbpmContext jbpmContext,
			String previousActorId, String nowActorId) {
		List taskInstances = jbpmContext.getTaskList(previousActorId);
		if (taskInstances != null && taskInstances.size() > 0) {
			Iterator iterator = taskInstances.iterator();
			while (iterator.hasNext()) {
				TaskInstance taskInstance = (TaskInstance) iterator.next();
				if (taskInstance.isOpen() && !taskInstance.hasEnded()) {
					taskInstance.setActorId(nowActorId);
					jbpmContext.save(taskInstance);
				}
			}
		}

		List actorIds = new ArrayList();
		actorIds.add(previousActorId);
		taskInstances = jbpmContext.getGroupTaskList(actorIds);
		if (taskInstances != null && taskInstances.size() > 0) {
			Iterator iterator = taskInstances.iterator();
			while (iterator.hasNext()) {
				TaskInstance taskInstance = (TaskInstance) iterator.next();
				if (taskInstance.isOpen() && !taskInstance.hasEnded()) {
					Set pooledActors = taskInstance.getPooledActors();
					if (pooledActors != null && pooledActors.size() > 0) {
						Iterator iter = pooledActors.iterator();
						while (iter.hasNext()) {
							PooledActor pa = (PooledActor) iter.next();
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
	 * ���·�������
	 * 
	 * @param taskInstanceId
	 * @param actorIds
	 */
	public void reassignTask(JbpmContext jbpmContext, String taskInstanceId,
			Set actorIds) {
		ProcessInstance processInstance = null;
		TaskInstance taskInstance = null;
		try {

			jbpmContext.setActorId("0");

			if (StringUtils.isNumeric(taskInstanceId)) {
				taskInstance = jbpmContext.loadTaskInstanceForUpdate(new Long(
						taskInstanceId).longValue());
				processInstance = taskInstance.getToken().getProcessInstance();

				if (processInstance.hasEnded()) {
					logger.debug("==================�����Ѿ����================");
					return;
				}

				if (taskInstance.hasEnded()) {
					logger.debug("=================�����Ѿ����=================");
					return;
				}

				if (actorIds != null && actorIds.size() > 0) {
					if (actorIds.size() == 1) {
						String actorId = (String) actorIds.iterator().next();
						taskInstance.setActorId(actorId);
					} else {
						int i = 0;
						String[] pooledActorIds = new String[actorIds.size()];
						Iterator iterator = actorIds.iterator();
						while (iterator.hasNext()) {
							pooledActorIds[i++] = (String) iterator.next();
						}
						taskInstance.setPooledActors(pooledActorIds);
					}
				}

				jbpmContext.save(taskInstance);

			}

		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ���·�������
	 * 
	 * @param taskInstanceId
	 * @param actorIds
	 */
	public void reassignTask(JbpmContext jbpmContext, String taskInstanceId,
			Set actorIds, Collection<DataField> dataFields) {
		ProcessInstance processInstance = null;
		TaskInstance taskInstance = null;
		try {

			jbpmContext.setActorId("0");

			if (StringUtils.isNumeric(taskInstanceId)) {
				taskInstance = jbpmContext.loadTaskInstanceForUpdate(new Long(
						taskInstanceId).longValue());
				processInstance = taskInstance.getToken().getProcessInstance();

				if (processInstance.hasEnded()) {
					logger.debug("==================�����Ѿ����================");
					return;
				}

				if (taskInstance.hasEnded()) {
					logger.debug("=================�����Ѿ����=================");
					return;
				}

				if (actorIds != null && actorIds.size() > 0) {
					if (actorIds.size() == 1) {
						String actorId = (String) actorIds.iterator().next();
						taskInstance.setActorId(actorId);
					} else {
						int i = 0;
						String[] pooledActorIds = new String[actorIds.size()];
						Iterator iterator = actorIds.iterator();
						while (iterator.hasNext()) {
							pooledActorIds[i++] = (String) iterator.next();
						}
						taskInstance.setPooledActors(pooledActorIds);
					}
				}

				if (dataFields != null && !dataFields.isEmpty()) {
					ContextInstance contextInstance = processInstance
							.getContextInstance();
					Iterator iter = dataFields.iterator();
					while (iter.hasNext()) {
						DataField dataField = (DataField) iter.next();
						logger.debug(dataField.getName());
						if (StringUtils.isNotBlank(dataField.getName())
								&& dataField.getValue() != null) {
							contextInstance.setVariable(dataField.getName(),
									dataField.getValue());
							if (logger.isDebugEnabled()) {
								logger.debug("���û�������:" + dataField.getName()
										+ "\t" + dataField.getValue());
							}
						}
					}
				}

				jbpmContext.save(taskInstance);

			}

		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ���·�������
	 * 
	 * @param processInstanceId
	 * @param previousActorId
	 * @param actorIds
	 */
	public void reassignTask(JbpmContext jbpmContext, String processInstanceId,
			String previousActorId, Set actorIds) {

		if (StringUtils.isEmpty(processInstanceId)
				|| !StringUtils.isNumeric(processInstanceId)) {
			logger.debug("processInstanceId:" + processInstanceId);
			return;
		}

		if (StringUtils.isEmpty(processInstanceId)) {
			logger.debug("previousActorId:" + previousActorId);
			return;
		}

		if (actorIds == null || actorIds.size() == 0) {
			logger.debug("actorIds:" + actorIds);
			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("processInstanceId:" + processInstanceId);
			logger.debug("previousActorId:" + previousActorId);
			logger.debug("actorIds:" + actorIds);
		}

		ProcessInstance processInstance = null;
		TaskInstance taskInstance = null;
		try {

			List taskItems = taskDAO.getTaskItems(jbpmContext, previousActorId,
					processInstanceId);
			if (taskItems != null && taskItems.size() > 0) {
				if (logger.isDebugEnabled()) {
					logger.debug("taskItems size:" + taskItems.size());
				}
				Collections.sort(taskItems);
				Iterator iter = taskItems.iterator();
				while (iter.hasNext()) {
					TaskItem taskItem = (TaskItem) iter.next();
					String id = taskItem.getTaskInstanceId();
					if (StringUtils.isNotBlank(id) && StringUtils.isNumeric(id)) {
						long taskInstanceId = new Long(id).longValue();
						if (taskInstanceId > 0) {
							taskInstance = jbpmContext
									.loadTaskInstanceForUpdate(taskInstanceId);
							if (taskInstance != null
									&& !taskInstance.hasEnded()) {
								break;
							}
						}
					}
				}
			}

			if (taskInstance == null) {
				logger.debug("=================���񲻴���=================");
				return;
			}

			if (taskInstance.hasEnded()) {
				logger.debug("=================�����Ѿ����=================");
				return;
			}

			processInstance = taskInstance.getToken().getProcessInstance();
			if (processInstance.hasEnded()) {
				logger.debug("==================�����Ѿ����================");
				return;
			}

			if (actorIds != null && actorIds.size() > 0) {
				if (actorIds.size() == 1) {
					String actorId = (String) actorIds.iterator().next();
					taskInstance.setActorId(actorId);
				} else {
					int i = 0;
					String[] pooledActorIds = new String[actorIds.size()];
					Iterator iterator = actorIds.iterator();
					while (iterator.hasNext()) {
						pooledActorIds[i++] = (String) iterator.next();
					}
					taskInstance.setActorId(null);
					taskInstance.setPooledActors(pooledActorIds);
				}
			}

			jbpmContext.save(taskInstance);

		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ���·�������
	 * 
	 * @param processInstanceId
	 * @param previousActorId
	 * @param actorIds
	 */
	public void reassignTask(JbpmContext jbpmContext, String processInstanceId,
			String previousActorId, Set actorIds,
			Collection<DataField> dataFields) {

		if (StringUtils.isEmpty(processInstanceId)
				|| !StringUtils.isNumeric(processInstanceId)) {
			logger.debug("processInstanceId:" + processInstanceId);
			return;
		}

		if (StringUtils.isEmpty(processInstanceId)) {
			logger.debug("previousActorId:" + previousActorId);
			return;
		}

		if (actorIds == null || actorIds.size() == 0) {
			logger.debug("actorIds:" + actorIds);
			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("processInstanceId:" + processInstanceId);
			logger.debug("previousActorId:" + previousActorId);
			logger.debug("actorIds:" + actorIds);
		}

		ProcessInstance processInstance = null;
		TaskInstance taskInstance = null;
		try {

			List taskItems = taskDAO.getTaskItems(jbpmContext, previousActorId,
					processInstanceId);
			if (taskItems != null && taskItems.size() > 0) {
				if (logger.isDebugEnabled()) {
					logger.debug("taskItems size:" + taskItems.size());
				}
				Collections.sort(taskItems);
				Iterator iter = taskItems.iterator();
				while (iter.hasNext()) {
					TaskItem taskItem = (TaskItem) iter.next();
					String id = taskItem.getTaskInstanceId();
					if (StringUtils.isNotBlank(id) && StringUtils.isNumeric(id)) {
						long taskInstanceId = new Long(id).longValue();
						if (taskInstanceId > 0) {
							taskInstance = jbpmContext
									.loadTaskInstanceForUpdate(taskInstanceId);
							if (taskInstance != null
									&& !taskInstance.hasEnded()) {
								break;
							}
						}
					}
				}
			}

			if (taskInstance == null) {
				logger.debug("=================���񲻴���=================");
				return;
			}

			if (taskInstance.hasEnded()) {
				logger.debug("=================�����Ѿ����=================");
				return;
			}

			processInstance = taskInstance.getToken().getProcessInstance();
			if (processInstance.hasEnded()) {
				logger.debug("==================�����Ѿ����================");
				return;
			}

			if (actorIds != null && actorIds.size() > 0) {
				if (actorIds.size() == 1) {
					String actorId = (String) actorIds.iterator().next();
					taskInstance.setActorId(actorId);
				} else {
					int i = 0;
					String[] pooledActorIds = new String[actorIds.size()];
					Iterator iterator = actorIds.iterator();
					while (iterator.hasNext()) {
						pooledActorIds[i++] = (String) iterator.next();
					}
					taskInstance.setActorId(null);
					taskInstance.setPooledActors(pooledActorIds);
				}
			}

			if (dataFields != null && !dataFields.isEmpty()) {
				ContextInstance contextInstance = processInstance
						.getContextInstance();
				Iterator iter = dataFields.iterator();
				while (iter.hasNext()) {
					DataField dataField = (DataField) iter.next();
					logger.debug(dataField.getName());
					if (StringUtils.isNotBlank(dataField.getName())
							&& dataField.getValue() != null) {
						contextInstance.setVariable(dataField.getName(),
								dataField.getValue());
						if (logger.isDebugEnabled()) {
							logger.debug("���û�������:" + dataField.getName() + "\t"
									+ dataField.getValue());
						}
					}
				}
			}

			jbpmContext.save(taskInstance);

		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ���·�������
	 * 
	 * @param processInstanceId
	 * @param taskName
	 * @param actorIds
	 */
	public void reassignTaskByTaskName(JbpmContext jbpmContext,
			String processInstanceId, String taskName, Set actorIds) {
		if (StringUtils.isEmpty(processInstanceId)
				|| !StringUtils.isNumeric(processInstanceId)) {
			logger.error("processInstanceId:" + processInstanceId);
			return;
		}

		if (StringUtils.isEmpty(taskName)) {
			logger.error("taskName:" + taskName);
			return;
		}

		if (actorIds == null || actorIds.size() == 0) {
			logger.error("actorIds:" + actorIds);
			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("processInstanceId:" + processInstanceId);
			logger.debug("taskName:" + taskName);
			logger.debug("actorIds:" + actorIds);
		}

		ProcessInstance processInstance = null;
		TaskInstance taskInstance = null;
		try {
			processInstance = jbpmContext.getProcessInstance(new Long(
					processInstanceId).longValue());

			if (processInstance == null) {
				logger.error("==================����ʵ��������================");
				return;
			}

			if (processInstance.hasEnded()) {
				logger.error("==================�����Ѿ����================");
				return;
			}

			TaskMgmtInstance tmi = processInstance.getTaskMgmtInstance();
			Collection taskInstances = tmi.getTaskInstances();
			List unfinishedTasks = new ArrayList();
			if (taskInstances != null) {
				Iterator iter = taskInstances.iterator();
				while (iter.hasNext()) {
					TaskInstance x = (TaskInstance) iter.next();
					if (StringUtils.equalsIgnoreCase(taskName, x.getName())) {
						if (x.isOpen() && !x.hasEnded()) {
							if (logger.isDebugEnabled()) {
								logger.debug(x.getId() + "\t" + x.getName()
										+ "\t" + x.getDescription());
							}
							unfinishedTasks.add(x);
						}
					}
				}
			}

			if (unfinishedTasks.size() > 1) {
				logger.error(" ֻ�ܴ�������ʵ��Ψһ������ʵ��:" + unfinishedTasks.size());
				return;
			}

			if (unfinishedTasks.size() == 1) {
				taskInstance = (TaskInstance) unfinishedTasks.get(0);
			}

			if (taskInstance == null) {
				logger.debug("=================���񲻴���=================");
				return;
			}

			if (actorIds != null && actorIds.size() > 0) {
				if (actorIds.size() == 1) {
					String actorId = (String) actorIds.iterator().next();
					taskInstance.setActorId(actorId);
				} else {
					int i = 0;
					String[] pooledActorIds = new String[actorIds.size()];
					Iterator iterator = actorIds.iterator();
					while (iterator.hasNext()) {
						pooledActorIds[i++] = (String) iterator.next();
					}
					taskInstance.setActorId(null);
					taskInstance.setPooledActors(pooledActorIds);
				}
			}

			jbpmContext.save(taskInstance);

		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * ���·�������
	 * 
	 * @param processInstanceId
	 * @param taskName
	 * @param actorIds
	 */
	public void reassignTaskByTaskName(JbpmContext jbpmContext,
			String processInstanceId, String taskName, Set actorIds,
			Collection<DataField> dataFields) {
		if (StringUtils.isEmpty(processInstanceId)
				|| !StringUtils.isNumeric(processInstanceId)) {
			logger.error("processInstanceId:" + processInstanceId);
			return;
		}

		if (StringUtils.isEmpty(taskName)) {
			logger.error("taskName:" + taskName);
			return;
		}

		if (actorIds == null || actorIds.size() == 0) {
			logger.error("actorIds:" + actorIds);
			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("processInstanceId:" + processInstanceId);
			logger.debug("taskName:" + taskName);
			logger.debug("actorIds:" + actorIds);
		}

		ProcessInstance processInstance = null;
		TaskInstance taskInstance = null;
		try {
			processInstance = jbpmContext.getProcessInstance(new Long(
					processInstanceId).longValue());

			if (processInstance == null) {
				logger.error("==================����ʵ��������================");
				return;
			}

			if (processInstance.hasEnded()) {
				logger.error("==================�����Ѿ����================");
				return;
			}

			TaskMgmtInstance tmi = processInstance.getTaskMgmtInstance();
			Collection taskInstances = tmi.getTaskInstances();
			List unfinishedTasks = new ArrayList();
			if (taskInstances != null) {
				Iterator iter = taskInstances.iterator();
				while (iter.hasNext()) {
					TaskInstance x = (TaskInstance) iter.next();
					if (StringUtils.equalsIgnoreCase(taskName, x.getName())) {
						if (x.isOpen() && !x.hasEnded()) {
							if (logger.isDebugEnabled()) {
								logger.debug(x.getId() + "\t" + x.getName()
										+ "\t" + x.getDescription());
							}
							unfinishedTasks.add(x);
						}
					}
				}
			}

			if (unfinishedTasks.size() > 1) {
				logger.error(" ֻ�ܴ�������ʵ��Ψһ������ʵ��:" + unfinishedTasks.size());
				return;
			}

			if (unfinishedTasks.size() == 1) {
				taskInstance = (TaskInstance) unfinishedTasks.get(0);
			}

			if (taskInstance == null) {
				logger.debug("=================���񲻴���=================");
				return;
			}

			if (actorIds != null && actorIds.size() > 0) {
				if (actorIds.size() == 1) {
					String actorId = (String) actorIds.iterator().next();
					taskInstance.setActorId(actorId);
				} else {
					int i = 0;
					String[] pooledActorIds = new String[actorIds.size()];
					Iterator iterator = actorIds.iterator();
					while (iterator.hasNext()) {
						pooledActorIds[i++] = (String) iterator.next();
					}
					taskInstance.setActorId(null);
					taskInstance.setPooledActors(pooledActorIds);
				}
			}

			if (dataFields != null && !dataFields.isEmpty()) {
				ContextInstance contextInstance = processInstance
						.getContextInstance();
				Iterator iter = dataFields.iterator();
				while (iter.hasNext()) {
					DataField dataField = (DataField) iter.next();
					logger.debug(dataField.getName());
					if (StringUtils.isNotEmpty(dataField.getName())
							&& dataField.getValue() != null) {
						contextInstance.setVariable(dataField.getName(),
								dataField.getValue());
						if (logger.isDebugEnabled()) {
							logger.debug("���û�������:" + dataField.getName() + "\t"
									+ dataField.getValue());
						}
					}
				}
			}

			jbpmContext.save(taskInstance);

		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	/**
	 * �ָ���������̣�����Ͷ�ʱ�����¿�ʼ��
	 * 
	 * @param processInstanceId
	 */
	public void resume(JbpmContext jbpmContext, String processInstanceId) {
		try {
			if (StringUtils.isNumeric(processInstanceId)) {
				ProcessInstance processInstance = jbpmContext
						.loadProcessInstanceForUpdate(new Long(
								processInstanceId).longValue());
				if (processInstance.hasEnded()) {
					logger.info("==================�����Ѿ���ɣ�����ָ�================");
					return;
				}

				/**
				 * ֻ��δ�����������ָ�
				 */
				TaskMgmtInstance tmi = processInstance.getTaskMgmtInstance();
				Collection taskInstances = tmi.getTaskInstances();
				if (taskInstances != null) {
					Iterator iter = taskInstances.iterator();
					while (iter.hasNext()) {
						TaskInstance taskInstance = (TaskInstance) iter.next();
						if (!taskInstance.hasEnded()) {
							taskInstance.resume();
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

	protected void setActors(TaskInstance taskInstance, String actorId) {
		if (StringUtils.isBlank(actorId)) {
			return;
		}
		if (actorId.indexOf(",") > 0) {
			Set actorIds = new HashSet();
			StringTokenizer token = new StringTokenizer(actorId, ",");
			while (token.hasMoreTokens()) {
				String elem = token.nextToken();
				if (StringUtils.isNotBlank(elem)) {
					actorIds.add(elem);
				}
			}
			if (actorIds.size() > 0) {
				int i = 0;
				String[] users = new String[actorIds.size()];
				Iterator iterator = actorIds.iterator();
				while (iterator.hasNext()) {
					users[i++] = (String) iterator.next();
				}
				taskInstance.setPooledActors(users);
			}
		} else {
			taskInstance.setActorId(actorId);
		}
	}

	public void setPersistenceDAO(PersistenceDAO persistenceDAO) {
		this.persistenceDAO = persistenceDAO;
	}

	public void setProcessDAO(ProcessDAO processDAO) {
		this.processDAO = processDAO;
	}

	public void setTaskDAO(TaskDAO taskDAO) {
		this.taskDAO = taskDAO;
	}

	/**
	 * ��������
	 * 
	 * @param ctx
	 * @return
	 */
	public String startProcess(ProcessContext ctx) {
		logger.debug("..................................................");
		logger.debug("....................start process.................");
		logger.debug("..................................................");
		Map params = ctx.getContextMap();

		if (logger.isDebugEnabled()) {
			logger.debug("params:" + params);
		}

		String processInstanceId = null;
		long processDefinitionId = Tools.getLong(params,
				Constant.PROCESS_DEFINITION_ID);
		String processName = ctx.getProcessName();

		String rowId = ctx.getRowId();

		params.put(Constant.CURRENT_ACTORID, ctx.getActorId());
		params.put(Constant.CURRENT_DATE, DateTools.getDate(new Date()));

		String actorId = ctx.getActorId();
		ContextInstance contextInstance = null;
		GraphSession graphSession = null;
		ProcessDefinition processDefinition = null;
		ProcessInstance processInstance = null;
		StateInstance stateInstance = null;
		TaskInstance taskInstance = null;
		Task task = null;
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ctx.getJbpmContext();
			jbpmContext.setActorId(actorId);
			graphSession = jbpmContext.getGraphSession();

			if (processDefinitionId > 0) {
				processDefinition = graphSession
						.loadProcessDefinition(processDefinitionId);
			} else {
				if (StringUtils.isNotBlank(processName)) {
					processDefinition = graphSession
							.findLatestProcessDefinition(processName);
				}
			}

			if (processDefinition == null) {
				throw new RuntimeException(" process definition is null");
			}

			processInstance = new ProcessInstance(processDefinition);
			if (StringUtils.isNotEmpty(rowId)) {
				processInstance.setKey(rowId);
			}

			jbpmContext.save(processInstance);

			processInstanceId = String.valueOf(processInstance.getId());
			params.put(Constant.PROCESS_INSTANCE_ID, processInstanceId);
			processInstance.getContextInstance().setVariable(
					Constant.PROCESS_INSTANCE_ID, processInstanceId);
			ctx.setProcessInstanceId(processInstanceId);

			contextInstance = processInstance.getContextInstance();
			contextInstance.setVariable(Constant.ACTOR_ID, actorId);
			contextInstance
					.setVariable(Constant.PROCESS_START_ACTORID, actorId);
			contextInstance.setVariable(Constant.PROCESS_LATEST_ACTORID,
					actorId);

			if (StringUtils.isNotEmpty(rowId)) {
				contextInstance.setVariable("rowId", rowId);
				if (logger.isDebugEnabled()) {
					logger.debug("���û�������->ҵ������:" + rowId);
				}
			}

			Collection dataFields = ctx.getDataFields();
			if (dataFields != null && dataFields.size() > 0) {
				Iterator iter = dataFields.iterator();
				while (iter.hasNext()) {
					DataField dataField = (DataField) iter.next();
					if (StringUtils.isNotBlank(dataField.getName())
							&& dataField.getValue() != null) {
						contextInstance.setVariable(dataField.getName(),
								dataField.getValue());
						if (logger.isDebugEnabled()) {
							logger.debug("���û�������:" + dataField.getName() + "\t"
									+ dataField.getValue());
						}
					}
				}
			}

			taskInstance = processInstance.getTaskMgmtInstance()
					.createStartTaskInstance();
			taskInstance.start(actorId);
			taskInstance.setVariable(Constant.ACTOR_ID, actorId);

			this.setActors(taskInstance, actorId);

			if (dataFields != null && dataFields.size() > 0) {
				Iterator iter = dataFields.iterator();
				while (iter.hasNext()) {
					DataField dataField = (DataField) iter.next();
					if (StringUtils.isNotBlank(dataField.getName())
							&& dataField.getValue() != null) {
						taskInstance.setVariable(dataField.getName(),
								dataField.getValue());
					}
				}
			}

			long taskInstanceId = taskInstance.getId();
			if (taskInstanceId > 0) {
				task = taskInstance.getTask();
				stateInstance = new StateInstance();
				stateInstance.setObjectId("process_step");
				stateInstance.setObjectValue("start");
				stateInstance.setBusinessId(rowId);
				stateInstance.setStateInstanceId(UUID32.getUUID());
				stateInstance.setActorId(ctx.getActorId());
				stateInstance.setProcessName(processDefinition.getName());
				stateInstance.setTaskInstanceId(String.valueOf(taskInstanceId));
				if (task != null) {
					stateInstance.setTaskDescription(task.getDescription());
					stateInstance.setTaskId(String.valueOf(task.getId()));
					stateInstance.setTaskName(task.getName());
				}
				stateInstance.setProcessInstanceId(processInstanceId);
				stateInstance.setTokenInstanceId(String.valueOf(taskInstance
						.getToken().getId()));

				if (params.get(Constant.TASK_TITLE) != null) {
					stateInstance.setTitle(ParamUtil.getString(params,
							Constant.TASK_TITLE));
				}

				stateInstance.setStartDate(new java.util.Date());
				stateInstance.setVersionNo(System.currentTimeMillis());
				if (logger.isDebugEnabled()) {
					logger.debug("����״̬ʵ��.");
					logger.debug(stateInstance);
				}
				persistenceDAO.save(jbpmContext, stateInstance);
			}

			taskInstance.end();

			logger.debug("���沢���������.");
			logger.debug("processInstanceId:" + processInstanceId);
			logger.debug("end start process...............................");
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			logger.error("-----------------------ctx-----------------");
			logger.error(ctx);
			throw new org.jbpm.JbpmException(ex);
		} finally {

		}
		return processInstanceId;
	}

	/**
	 * ����ĳ�����̵�ȫ������
	 * 
	 * @param processInstanceId
	 */
	public void suspend(JbpmContext jbpmContext, String processInstanceId) {
		try {
			if (StringUtils.isNumeric(processInstanceId)) {
				ProcessInstance processInstance = jbpmContext
						.loadProcessInstanceForUpdate(new Long(
								processInstanceId).longValue());
				if (processInstance.hasEnded()) {
					logger.info("==================�����Ѿ���ɣ��������================");
					return;
				}

				/**
				 * ֻ���������
				 */
				TaskMgmtInstance tmi = processInstance.getTaskMgmtInstance();
				Collection taskInstances = tmi.getTaskInstances();
				if (taskInstances != null) {
					Iterator iter = taskInstances.iterator();
					while (iter.hasNext()) {
						TaskInstance taskInstance = (TaskInstance) iter.next();
						if (taskInstance.isOpen()) {
							taskInstance.suspend();
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new org.jbpm.JbpmException(ex);
		}
	}

}
