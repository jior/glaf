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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;

public class Automatic implements ActivityBehavior {
	 
	private static final long serialVersionUID = 1L;

	protected final static Log logger = LogFactory.getLog(Automatic.class);

	protected Expression conditionExpression;

	public void execute(ActivityExecution activityContext) throws Exception {
		if (conditionExpression != null) {
			Object value = conditionExpression.getValue(activityContext);
			if (value != null) {
				logger.debug("condition:" + value);
				if (value instanceof Boolean) {
					Boolean b = (Boolean) value;
					if (b.booleanValue()) {
						PvmTransition defaultOutgoingTransition = activityContext
								.getActivity().getOutgoingTransitions().get(0);
						activityContext.take(defaultOutgoingTransition);
					}
				}
			}
		}
	}
}