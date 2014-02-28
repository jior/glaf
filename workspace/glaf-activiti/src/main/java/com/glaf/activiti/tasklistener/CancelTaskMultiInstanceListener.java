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

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.util.Constants;

public class CancelTaskMultiInstanceListener implements TaskListener {
	private static final long serialVersionUID = 1L;
	
	protected final static Log logger = LogFactory
			.getLog(CancelTaskMultiInstanceListener.class);

	protected Expression conditionExpression;

	@Override
	public void notify(DelegateTask delegateTask) {
		logger.debug("----------------------------------------------------");
		logger.debug("---------------CancelTaskMultiInstanceListener------");
		logger.debug("----------------------------------------------------");
		if (conditionExpression != null) {
			Object value = conditionExpression.getValue(delegateTask);
			if (value != null) {
				logger.debug("condition:" + value);
				if (value instanceof Boolean) {
					Boolean b = (Boolean) value;
					if (b.booleanValue()) {
						if (delegateTask
								.getVariable(Constants.NUMBER_OF_COMPLETED_INSTANCES) != null) {
							int nrOfCompletedInstances = (Integer) delegateTask
									.getVariable(Constants.NUMBER_OF_COMPLETED_INSTANCES);
							delegateTask.setVariable(
									Constants.NUMBER_OF_COMPLETED_INSTANCES,
									nrOfCompletedInstances + 10000);
						}
					}
				}
			}
		}
	}

	public void setConditionExpression(Expression conditionExpression) {
		this.conditionExpression = conditionExpression;
	}

}