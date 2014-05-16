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

package com.glaf.activiti.tasklistener;

import java.util.Date;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.activiti.model.ActivityInstance;
import com.glaf.activiti.util.ExecutionUtils;
import com.glaf.core.dao.MyBatisEntityDAO;
import com.glaf.core.util.UUID32;

public class TaskCompleteListener implements TaskListener {
	private static final long serialVersionUID = 1L;
	
	protected final static Log logger = LogFactory
			.getLog(TaskCompleteListener.class);

	protected Expression message;

	protected Expression sql;

	public void notify(DelegateTask delegateTask) {
		logger.debug("----------------------------------------------------");
		logger.debug("------------------TaskCompleteListener--------------");
		logger.debug("----------------------------------------------------");
		if (message != null) {
			logger.debug("message:"
					+ message.getValue(delegateTask.getExecution()));
		}
		DelegateExecution execution = delegateTask.getExecution();
		String taskDefinitionKey = delegateTask.getTaskDefinitionKey();
		if (execution != null && taskDefinitionKey != null) {
			ActivityInstance activityInstance = new ActivityInstance();
			activityInstance.setId(UUID32.getUUID());
			activityInstance
					.setActorId(Authentication.getAuthenticatedUserId());

			Map<String, Object> variables = execution.getVariables();
			if (variables != null) {
				activityInstance.setApprove((String) variables.get("approve"));
				activityInstance.setOpinion((String) variables.get("opinion"));
			}

			activityInstance.setCreateTime(new Date());
			activityInstance.setEndTime(activityInstance.getCreateTime());
			activityInstance.setProcessInstanceId(execution.getId());
			activityInstance.setState("completed");
			activityInstance.setType("userTask");

			CommandContext commandContext = Context.getCommandContext();
	 
			MyBatisEntityDAO entityDAO = new MyBatisEntityDAO(commandContext
					.getDbSqlSession().getSqlSession());
			entityDAO.insert("insertActivityInstance", activityInstance);
			logger.debug("insert activityInstance:" + activityInstance);
		}
		if (sql != null) {
			ExecutionUtils.executeSqlUpdate(execution, sql);
		}
	}

	public void setSql(Expression sql) {
		this.sql = sql;
	}

	public void setMessage(Expression message) {
		this.message = message;
	}

}