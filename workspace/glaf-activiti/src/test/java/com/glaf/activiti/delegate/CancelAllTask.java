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
package com.glaf.activiti.delegate;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.TaskQueryImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.activiti.engine.task.Task;

import com.glaf.core.util.Constants;
import com.glaf.core.util.StringTools;

public class CancelAllTask implements ActivityBehavior {
 
	private static final long serialVersionUID = 1L;

	protected final static Log logger = LogFactory.getLog(CancelAllTask.class);

	/**
	 * 条件表达式
	 */
	protected Expression conditionExpression;

	/**
	 * 转出路径变量
	 */
	protected Expression outcomeVar;

	/**
	 * 包含的任务名称，多个任务之间用半角的逗号隔开
	 */
	protected Expression includes;

	@Override
	public void execute(ActivityExecution execution) throws Exception {
		logger.debug("----------------------------------------------------");
		logger.debug("------------------CancelAllTask---------------------");
		logger.debug("----------------------------------------------------");

		boolean executable = true;

		if (conditionExpression != null) {
			Object value = conditionExpression.getValue(execution);
			if (value != null) {
				logger.debug("condition:" + value);
				if (value instanceof Boolean) {
					Boolean b = (Boolean) value;
					executable = b.booleanValue();
				}
			}
		}

		if (!executable) {
			logger.debug("表达式计算后取值为false，不执行后续动作。");
			return;
		}

		String processInstanceId = execution.getProcessInstanceId();
		CommandContext commandContext = Context.getCommandContext();
		TaskQueryImpl taskQuery = new TaskQueryImpl();
		taskQuery.processInstanceId(processInstanceId);
		Page page = new Page(0, 10000);
		taskQuery.setFirstResult(page.getFirstResult());
		taskQuery.setMaxResults(page.getMaxResults());
		List<Task> tasks = commandContext.getTaskEntityManager()
				.findTasksByQueryCriteria(taskQuery);
		if (tasks != null && !tasks.isEmpty()) {
			if (includes != null && includes.getValue(execution) != null) {
				String x_includes = (String) includes.getValue(execution);
				logger.debug("includes:" + x_includes);
				if (StringUtils.isNotEmpty(x_includes)) {
					if (StringUtils.equals(x_includes, "ALL")) {

					} else {
						List<String> list = StringTools.split(x_includes);
						for (String taskDefKey : list) {
							for (Task task : tasks) {
								if (StringUtils
										.equals(task.getTaskDefinitionKey(),
												taskDefKey)) {

								}
							}
						}
					}
				}
			}
		}

		String name = null;
		if (outcomeVar != null) {
			name = (String) outcomeVar.getValue(execution);
		}

		if (name == null) {
			name = Constants.OUTCOME;
		}
		String outcome = (String) execution.getVariable(name);
		if (outcome != null) {
			PvmTransition transition = execution.getActivity()
					.findOutgoingTransition(outcome);
			if (transition != null) {
				execution.take(transition);
			}
		} else {
			PvmTransition defaultOutgoingTransition = execution.getActivity()
					.getOutgoingTransitions().get(0);
			execution.take(defaultOutgoingTransition);
		}

	}

}
