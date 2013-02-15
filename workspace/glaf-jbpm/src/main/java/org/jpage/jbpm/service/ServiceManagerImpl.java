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

import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmContext;
import org.jpage.core.cache.CacheFactory;
import org.jpage.core.query.paging.Page;
import org.jpage.jbpm.dao.ProcessDAO;
import org.jpage.jbpm.dao.TaskDAO;
import org.jpage.jbpm.model.DeployInstance;
import org.jpage.jbpm.model.StateInstance;
import org.jpage.jbpm.persistence.PersistenceDAO;
import org.jpage.persistence.Executor;
import org.jpage.persistence.SQLCommandImpl;
import org.jpage.util.DateTools;

public class ServiceManagerImpl implements ServiceManager {
	private final static Log logger = LogFactory
			.getLog(ServiceManagerImpl.class);

	 
	private TaskDAO taskDAO;

	private ProcessDAO processDAO;

	private PersistenceDAO persistenceDAO;

	 

	public ServiceManagerImpl() {
		 
	}

	public ProcessDAO getProcessDAO() {
		return processDAO;
	}

	public void setProcessDAO(ProcessDAO processDAO) {
		this.processDAO = processDAO;
	}

	public PersistenceDAO getPersistenceDAO() {
		return persistenceDAO;
	}

	public void setPersistenceDAO(PersistenceDAO persistenceDAO) {
		this.persistenceDAO = persistenceDAO;
	}

	public TaskDAO getTaskDAO() {
		return taskDAO;
	}

	public void setTaskDAO(TaskDAO taskDAO) {
		this.taskDAO = taskDAO;
	}

	 

 
	/**
	 * 保存部署的流程定义实例
	 * 
	 * @param deployInstance
	 * @return
	 */
	public void saveDeployInstance(JbpmContext jbpmContext,
			DeployInstance deployInstance) {
		persistenceDAO.save(jbpmContext, deployInstance);
	}
 

	/**
	 * 保存状态实例
	 * 
	 * @param stateInstance
	 */
	public void saveStateInstance(JbpmContext jbpmContext,
			StateInstance stateInstance) {
		stateInstance.setStartDate(new java.util.Date());
		stateInstance.setVersionNo(System.currentTimeMillis());
		persistenceDAO.save(jbpmContext, stateInstance);
	}

	/**
	 * 保存状态实例
	 * 
	 * @param stateInstance
	 */
	public void updateStateInstance(JbpmContext jbpmContext,
			StateInstance stateInstance) {
		stateInstance.setVersionNo(System.currentTimeMillis());
		persistenceDAO.update(jbpmContext, stateInstance);
	}

	/**
	 * 获取最新部署的流程定义实例
	 * 
	 * @param processName
	 * @return
	 */
	public DeployInstance getMaxDeployInstance(JbpmContext jbpmContext,
			String processName) {
		return processDAO.getMaxDeployInstance(jbpmContext, processName);
	}

	/**
	 * 获取状态实例
	 * 
	 * @param stateInstanceId
	 * @return
	 */
	public StateInstance getStateInstance(JbpmContext jbpmContext,
			String stateInstanceId) {
		return processDAO.getStateInstance(jbpmContext, stateInstanceId);
	}

	/**
	 * 获取某个流程最新状态的实例数据
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public StateInstance getMaxStateInstance(JbpmContext jbpmContext,
			String processInstanceId) {
		return processDAO.getMaxStateInstance(jbpmContext, processInstanceId);
	}

	 
	 
	/**
	 * 获取状态实例
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public List getStateInstances(JbpmContext jbpmContext,
			String processInstanceId) {
		return processDAO.getStateInstances(jbpmContext, processInstanceId);
	}

	/**
	 * 获取状态实例
	 * 
	 * @param jbpmContext
	 * @param processName
	 * @param actorId
	 * @return
	 */
	public List getStateInstances(JbpmContext jbpmContext, String processName,
			String actorId) {
		return processDAO.getStateInstances(jbpmContext, processName, actorId);
	}

	 
	/**
	 * 获取全部流程部署实例
	 * 
	 * @return
	 */
	public List getDeployInstances(JbpmContext jbpmContext) {
		Executor queryExecutor = new Executor();
		queryExecutor
				.setQuery(" from org.jpage.jbpm.model.DeployInstance as a ");
		return persistenceDAO.query(jbpmContext, 1, 10000, queryExecutor);
	}

	 

	/**
	 * 执行定义的SQL语句
	 * 
	 * @param con
	 * @param executors
	 */
	public void execute(Connection con, List executors) {
		SQLCommandImpl cmd = new SQLCommandImpl();
		cmd.setConnection(con);
		cmd.setExecutors(executors);
		cmd.execute();
	}

	/**
	 * 执行定义的SQL语句
	 * 
	 * @param con
	 * @param executor
	 * @param params
	 */
	public void execute(Connection con, Executor executor, Map params) {
		List executors = new ArrayList();
		executor.setParams(params);
		executors.add(executor);
		SQLCommandImpl cmd = new SQLCommandImpl();
		cmd.setConnection(con);
		cmd.setExecutors(executors);
		cmd.execute();
	}
	
	/**
	 * 根据流程实例编号获取用户的任务实例
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public List getProcessTaskItems(JbpmContext jbpmContext,
			String processInstanceId) {
		return taskDAO.getProcessTaskItems(jbpmContext, processInstanceId);
	}

	 
 
	/**
	 * 获取一页状态实例对象
	 * 
	 * @param currPageNo
	 * @param pageSize
	 * @param paramMap
	 * @return
	 */
	public Page getPageStateInstance(JbpmContext jbpmContext, int currPageNo,
			int pageSize, Map paramMap) {
		Map params = new HashMap();
		StringBuffer buffer = new StringBuffer();
		buffer
				.append(" from org.jpage.jbpm.model.StateInstance as a where 1=1 ");

		if (paramMap.get("processInstanceId") != null) {
			buffer.append(" and a.processInstanceId = :processInstanceId ");
			params.put("processInstanceId", paramMap.get("processInstanceId"));
		}

		if (paramMap.get("taskName") != null) {
			buffer.append(" and a.taskName = :taskName ");
			params.put("taskName", paramMap.get("taskName"));
		}

		if (paramMap.get("taskInstanceId") != null) {
			buffer.append(" and a.taskInstanceId = :taskInstanceId ");
			params.put("taskInstanceId", paramMap.get("taskInstanceId"));
		}

		if (paramMap.get("operatorId") != null) {
			buffer.append(" and a.operatorId = :operatorId ");
			params.put("operatorId", paramMap.get("operatorId"));
		}

		if (paramMap.get("objectId") != null) {
			buffer.append(" and a.objectId = :objectId ");
			params.put("objectId", paramMap.get("objectId"));
		}

		if (paramMap.get("objectValue") != null) {
			buffer.append(" and a.objectValue = :objectValue ");
			params.put("objectValue", paramMap.get("objectValue"));
		}

		if (paramMap.get("status") != null) {
			Object obj = paramMap.get("status");
			if (obj instanceof java.lang.Integer) {
				params.put("status", paramMap.get("status"));
			} else {
				String status = (String) obj;
				params.put("status", new Integer(status));
			}
			buffer.append(" and ( a.status = :status )");
		}

		Executor countExecutor = new Executor();
		Executor queryExecutor = new Executor();

		countExecutor.setQuery(" select count(distinct a.stateInstanceId) "
				+ buffer.toString());
		queryExecutor.setQuery(" select distinct a " + buffer.toString());
		countExecutor.setParams(params);
		queryExecutor.setParams(params);

		return persistenceDAO.getPage(jbpmContext, currPageNo, pageSize,
				countExecutor, queryExecutor);
	}

	/**
	 * 获取一页消息实例对象
	 * 
	 * @param currPageNo
	 * @param pageSize
	 * @param paramMap
	 * @return
	 */
	public Page getPageMessageInstance(JbpmContext jbpmContext, int currPageNo,
			int pageSize, Map paramMap) {
		Map params = new HashMap();
		StringBuffer buffer = new StringBuffer();
		buffer
				.append(" from org.jpage.jbpm.model.MessageInstance as a where 1=1 ");

		if (paramMap.get("messageType") != null) {
			buffer.append(" and a.messageType = :messageType ");
			params.put("messageType", paramMap.get("messageType"));
		}

		if (paramMap.get("senderId") != null) {
			buffer.append(" and a.senderId = :senderId ");
			params.put("senderId", paramMap.get("senderId"));
		}

		if (paramMap.get("receiverId") != null) {
			buffer.append(" and a.receiverId = :receiverId ");
			params.put("receiverId", paramMap.get("receiverId"));
		}

		if (paramMap.get("title") != null) {
			buffer.append(" and a.title = :title ");
			params.put("title", paramMap.get("title"));
		}

		if (paramMap.get("objectId") != null) {
			buffer.append(" and a.objectId = :objectId ");
			params.put("objectId", paramMap.get("objectId"));
		}

		if (paramMap.get("objectValue") != null) {
			buffer.append(" and a.objectValue = :objectValue ");
			params.put("objectValue", paramMap.get("objectValue"));
		}

		if (paramMap.get("receiverType") != null) {
			Object obj = paramMap.get("receiverType");
			if (obj instanceof java.lang.Integer) {
				params.put("receiverType", paramMap.get("receiverType"));
			} else {
				String receiverType = (String) obj;
				params.put("receiverType", new Integer(receiverType));
			}
			buffer.append(" and ( a.receiverType = :receiverType )");
		}

		if (paramMap.get("status") != null) {
			Object obj = paramMap.get("status");
			if (obj instanceof java.lang.Integer) {
				params.put("status", paramMap.get("status"));
			} else {
				String status = (String) obj;
				params.put("status", new Integer(status));
			}
			buffer.append(" and ( a.status = :status )");
		}

		Executor countExecutor = new Executor();
		Executor queryExecutor = new Executor();

		countExecutor.setQuery(" select count(distinct a.messageId) "
				+ buffer.toString());
		queryExecutor.setQuery(" select distinct a " + buffer.toString());
		countExecutor.setParams(params);
		queryExecutor.setParams(params);

		return persistenceDAO.getPage(jbpmContext, currPageNo, pageSize,
				countExecutor, queryExecutor);
	}

	/**
	 * 根据流程实例编号和任务编号获取该任务的处理者
	 * 
	 * @param processInstanceId
	 * @param taskId
	 * @return
	 */
	public Set getActorIds(JbpmContext jbpmContext, String processInstanceId,
			String taskId) {
		return taskDAO.getActorIds(jbpmContext, processInstanceId, taskId);
	}

}
