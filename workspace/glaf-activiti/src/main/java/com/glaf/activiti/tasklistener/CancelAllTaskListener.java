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
import java.util.concurrent.TimeUnit;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.TaskQueryImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.util.StringTools;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class CancelAllTaskListener implements TaskListener {
	private static final long serialVersionUID = 1L;
	
	protected final static Log logger = LogFactory
			.getLog(CancelAllTaskListener.class);

	private static Cache<String, Object> cache = CacheBuilder.newBuilder()
			.maximumSize(1000).expireAfterAccess(5, TimeUnit.MINUTES).build();

	protected Expression conditionExpression;

	protected Expression includesExpression;

	@Override
	public void notify(DelegateTask delegateTask) {
		logger.debug("----------------------------------------------------");
		logger.debug("---------------CancelAllTaskListener----------------");
		logger.debug("----------------------------------------------------");

		logger.debug("cucrent task: " + delegateTask.getName());
		logger.debug("cucrent taskDefinitionKey: "
				+ delegateTask.getTaskDefinitionKey());

		if (conditionExpression != null) {
			logger.debug("variables:" + delegateTask.getVariables());
			logger.debug("conditionExpression:"
					+ conditionExpression.getExpressionText());
			Object value = conditionExpression.getValue(delegateTask);
			if (value != null) {
				logger.debug("condition:" + value);
				if (value instanceof Boolean) {
					Boolean b = (Boolean) value;
					if (!b.booleanValue()) {
						return;
					}
				}
			}
		}

		CommandContext commandContext = Context.getCommandContext();

		TaskQueryImpl taskQuery = new TaskQueryImpl();
		taskQuery.processInstanceId(delegateTask.getProcessInstanceId());

		List<Task> tasks = commandContext.getTaskEntityManager()
				.findTasksByQueryCriteria(taskQuery);
		logger.debug("tasks:" + tasks);
		if (tasks != null && !tasks.isEmpty()) {
			String includes = null;
			if (includesExpression != null) {
				includes = (String) includesExpression.getValue(delegateTask);
			}
			List<String> list = new java.util.ArrayList<String>();
			if (includes != null && includes.trim().length() > 0) {
				list = StringTools.split(includes);
			}

			cache.put("ACT_TASK_" + delegateTask.getId(), "1");

			Iterator<Task> iterator = tasks.iterator();
			while (iterator.hasNext()) {
				Task task = iterator.next();
				String cacheKey = "ACT_TASK_" + task.getId();
				if (StringUtils.equals(task.getTaskDefinitionKey(),
						delegateTask.getTaskDefinitionKey())) {
					logger.debug(task.getTaskDefinitionKey() + " ["
							+ task.getName() + "] skiped.");
					cache.put(cacheKey, "1");
					continue;
				}

				if (list != null && !list.isEmpty()) {
					if (!list.contains(task.getTaskDefinitionKey())) {
						continue;
					}
				}

				if (task instanceof TaskEntity) {
					TaskEntity taskEntity = (TaskEntity) task;
					if (cache.getIfPresent(cacheKey) == null) {
						cache.put(cacheKey, "1");
						logger.debug(taskEntity.getTaskDefinitionKey() + " ["
								+ taskEntity.getName() + "] canceled");
						taskEntity.complete();
					}
				}
			}
		}
	}

	public void setIncludesExpression(Expression includesExpression) {
		this.includesExpression = includesExpression;
	}

	public void setConditionExpression(Expression conditionExpression) {
		this.conditionExpression = conditionExpression;
	}

}