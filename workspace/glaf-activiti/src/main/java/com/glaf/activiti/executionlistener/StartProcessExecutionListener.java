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

package com.glaf.activiti.executionlistener;

import java.util.Date;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.activiti.model.ActivityInstance;
import com.glaf.core.dao.MyBatisEntityDAO;
import com.glaf.core.util.UUID32;

public class StartProcessExecutionListener implements ExecutionListener {
	private static final long serialVersionUID = 1L;
	
	protected final static Log logger = LogFactory
			.getLog(StartProcessExecutionListener.class);

	public void notify(DelegateExecution execution) throws Exception {
		logger.debug("----------------------------------------------------");
		logger.debug("--------------StartProcessExecutionListener---------");
		logger.debug("----------------------------------------------------");

		ActivityInstance activityInstance = new ActivityInstance();
		activityInstance.setId(UUID32.getUUID());
		activityInstance.setActorId(Authentication.getAuthenticatedUserId());
		activityInstance.setCreateTime(new Date());
		activityInstance.setEndTime(activityInstance.getCreateTime());
		activityInstance.setProcessInstanceId(execution.getId());
		activityInstance.setState("completed");
		activityInstance.setType("startEvent");
		CommandContext commandContext = Context.getCommandContext();
		MyBatisEntityDAO entityDAO = new MyBatisEntityDAO(commandContext
				.getDbSqlSession().getSqlSession());
		entityDAO.insert("insertActivityInstance", activityInstance);
		logger.debug("insert activityInstance:" + activityInstance);
	}

}