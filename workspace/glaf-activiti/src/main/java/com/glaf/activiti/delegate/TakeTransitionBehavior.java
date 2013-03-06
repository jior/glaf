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

import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.util.Constants;

public class TakeTransitionBehavior implements ActivityBehavior {
	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory
			.getLog(TakeTransitionBehavior.class);

	protected Expression outcomeVar;

	public void execute(ActivityExecution execution) throws Exception {
		logger.debug("----------------------------------------------------");
		logger.debug("----------------TakeTransitionBehavior--------------");
		logger.debug("----------------------------------------------------");
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
		}
	}

	public void setOutcomeVar(Expression outcomeVar) {
		this.outcomeVar = outcomeVar;
	}

}