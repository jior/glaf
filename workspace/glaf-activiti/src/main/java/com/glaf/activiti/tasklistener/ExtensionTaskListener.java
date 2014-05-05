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

import java.util.Iterator;
import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import com.glaf.activiti.extension.factory.ExtensionFactory;
import com.glaf.activiti.extension.model.ExtensionEntity;
import com.glaf.activiti.extension.model.ExtensionFieldEntity;
import com.glaf.activiti.extension.service.ActivitiExtensionService;
import com.glaf.activiti.tasklistener.factory.TaskListenerFactory;
import com.glaf.activiti.util.ThreadHolder;
import com.glaf.core.util.StringTools;

public class ExtensionTaskListener implements TaskListener {
 
	private static final long serialVersionUID = 1L;

	protected final static Log logger = LogFactory
			.getLog(ExtensionTaskListener.class);

	protected Expression extensionId;

	public void notify(DelegateTask delegateTask) {
		logger.debug("----------------------------------------------------");
		logger.debug("----------------ExtensionTaskListener---------------");
		logger.debug("----------------------------------------------------");
		ActivitiExtensionService service = ExtensionFactory
				.getActivitiExtensionService();
		CommandContext commandContext = Context.getCommandContext();
		SqlSession sqlSession = commandContext.getDbSqlSession()
				.getSqlSession();
		service.setSqlSession(sqlSession);
		DelegateExecution execution = delegateTask.getExecution();
		ExecutionEntity executionEntity = commandContext.getExecutionEntityManager()
				.findExecutionById(execution.getId());
		String processDefinitionId = executionEntity.getProcessDefinitionId();
		ProcessDefinitionEntity processDefinitionEntity = commandContext
				.getProcessDefinitionEntityManager().findProcessDefinitionById(
						processDefinitionId);
		String processName = processDefinitionEntity.getKey();

		String extendId = null;
		String extensionName = null;
		ExtensionEntity extension = null;

		if (extensionId != null) {
			extensionName = (String) extensionId.getValue(execution);
		}

		if (StringUtils.isNotEmpty(extensionName)) {
			if (extension == null) {
				extendId = processName + "_" + extensionName;
				logger.debug("1--search EX1......");
				extension = service.getExtension(extendId);
			}
			if (extension == null) {
				/**
				 * 取指定流程指定名称的动作定义信息
				 */
				logger.debug("2--search EX2......");
				extension = service.getExtensionListener(processName,
						extensionName);
			}
		}

		if (extension == null) {
			extendId = processName + "_" + delegateTask.getTaskDefinitionKey();
			extension = service.getExtension(extendId);
			if (extension == null) {
				extension = service.getExtensionListener(processName,
						delegateTask.getTaskDefinitionKey());
			}
		}

		if (extension == null) {
			if (StringUtils.isNotEmpty(extensionName)) {
				extension = service.getExtension(extensionName);
			}
		}

		if (extension != null) {
			ThreadHolder.setExtensionTask(extension);
			execution.setVariableLocal("extensionTask", extension);

			ExtensionFieldEntity extensionField = extension
					.getField("executionListeners");
			if (extensionField != null) {
				String val = extensionField.getValue();
				List<String> listeners = StringTools.split(val, ",");
				if (listeners != null && listeners.size() > 0) {
					Iterator<String> iter = listeners.iterator();
					while (iter.hasNext()) {
						String name = iter.next();
						ExtensionEntity ex = service.getExtensionListener(
								processName, name);
						if (ex != null) {
							execution.setVariableLocal("ex_taskListener",
									extension);
							ExtensionFieldEntity taskListenerField = ex
									.getField("taskListener");
							if (taskListenerField != null) {
								String taskListener = taskListenerField
										.getValue();
								TaskListenerFactory.notify(taskListener,
										delegateTask);
							}
							execution.removeVariableLocal("ex_taskListener");
						}
					}
				}
			}

			ExtensionFieldEntity taskListenersField = extension
					.getField("taskListeners");
			if (taskListenersField != null) {
				String val = taskListenersField.getValue();
				List<String> taskListeners = StringTools.split(val, ",");
				if (taskListeners != null && taskListeners.size() > 0) {
					Iterator<String> iter = taskListeners.iterator();
					while (iter.hasNext()) {
						String taskListener = iter.next();
						TaskListenerFactory.notify(taskListener, delegateTask);
					}
				}
			} else {
				ExtensionFieldEntity taskListenerField = extension
						.getField("taskListener");
				if (taskListenerField != null) {
					String taskListener = taskListenerField.getValue();
					TaskListenerFactory.notify(taskListener, delegateTask);
				}
			}

			execution.removeVariableLocal("extensionTask");
			ThreadHolder.removeExtensionTask();
		}

	}

}