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

import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;

import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.activiti.engine.impl.pvm.delegate.ExecutionListenerExecution;
import org.activiti.engine.impl.pvm.process.ActivityImpl;

import com.glaf.core.util.Constants;

public class LeaveNode implements ActivityBehavior {
 
	private static final long serialVersionUID = 1L;

	protected final static Log logger = LogFactory.getLog(LeaveNode.class);

	/**
	 * 条件表达式
	 */
	protected Expression conditionExpression;

	protected void callActivityEndListeners(ActivityExecution execution) {
		ActivityImpl activity = (ActivityImpl) execution.getActivity();
		List<ExecutionListener> listeners = activity
				.getExecutionListeners(org.activiti.engine.impl.pvm.PvmEvent.EVENTNAME_END);
		for (ExecutionListener executionListener : listeners) {
			try {
				executionListener
						.notify((ExecutionListenerExecution) execution);
			} catch (Exception e) {
				throw new ActivitiException("Couldn't execute end listener", e);
			}
		}
	}

	@Override
	public void execute(ActivityExecution execution) throws Exception {
		logger.debug("----------------------------------------------------");
		logger.debug("------------------LeaveNode-------------------------");
		logger.debug("----------------------------------------------------");

		if (conditionExpression != null) {
			Object value = conditionExpression.getValue(execution);
			if (value != null) {
				logger.debug("condition:" + value);
				if (value instanceof Boolean) {
					Boolean b = (Boolean) value;
					if (b.booleanValue()) {
						this.leave(execution);
					}
				}
			}
		}

	}

	protected Integer getLoopVariable(ActivityExecution execution,
			String variableName) {
		Object value = execution.getVariableLocal(variableName);
		ActivityExecution parent = execution.getParent();
		while (value == null && parent != null) {
			value = parent.getVariableLocal(variableName);
			parent = parent.getParent();
		}
		if (value != null) {
			return (Integer) value;
		}
		return 0;
	}

	protected void leave(ActivityExecution execution) {
		callActivityEndListeners(execution);

		int nrOfCompletedInstances = getLoopVariable(execution,
				Constants.NUMBER_OF_COMPLETED_INSTANCES) + 1;
		int nrOfActiveInstances = getLoopVariable(execution,
				Constants.NUMBER_OF_ACTIVE_INSTANCES) - 1;

		if (execution.getParent() != null) {
			setLoopVariable(execution.getParent(),
					Constants.NUMBER_OF_COMPLETED_INSTANCES,
					nrOfCompletedInstances);
			setLoopVariable(execution.getParent(),
					Constants.NUMBER_OF_ACTIVE_INSTANCES, nrOfActiveInstances);
		}

		ExecutionEntity executionEntity = (ExecutionEntity) execution;
		executionEntity.inactivate();
		if (executionEntity.getParent() != null) {
			executionEntity.getParent().forceUpdate();
		}

		List<ActivityExecution> joinedExecutions = executionEntity
				.findInactiveConcurrentExecutions(execution.getActivity());

		// Removing all active child executions (ie because
		// completionCondition is true)
		List<ExecutionEntity> executionsToRemove = new java.util.concurrent.CopyOnWriteArrayList<ExecutionEntity>();
		if (executionEntity.getParent() != null) {
			for (ActivityExecution childExecution : executionEntity.getParent()
					.getExecutions()) {
				if (childExecution.isActive()) {
					executionsToRemove.add((ExecutionEntity) childExecution);
				}
			}
		}

		for (ExecutionEntity executionToRemove : executionsToRemove) {
			executionToRemove.inactivate();
			executionToRemove.deleteCascade("multi-instance completed");
		}

		if (executionEntity.getExecutions() != null) {
			executionsToRemove.clear();
			for (ActivityExecution childExecution : executionEntity
					.getExecutions()) {
				if (childExecution.isActive()) {
					executionsToRemove.add((ExecutionEntity) childExecution);
				}
			}
		}

		for (ExecutionEntity executionToRemove : executionsToRemove) {
			executionToRemove.inactivate();
			executionToRemove.deleteCascade("multi-instance completed");
		}

		executionEntity.takeAll(executionEntity.getActivity()
				.getOutgoingTransitions(), joinedExecutions);

	}

	protected void setLoopVariable(ActivityExecution execution,
			String variableName, Object value) {
		execution.setVariableLocal(variableName, value);
	}
}
