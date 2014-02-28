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

public class VariableMappingListener implements TaskListener {
	private static final long serialVersionUID = 1L;
	protected final static Log logger = LogFactory
			.getLog(VariableMappingListener.class);

	protected Expression fromVar;

	protected Expression toVar;

	@Override
	public void notify(DelegateTask delegateTask) {
		logger.debug("----------------------------------------------------");
		logger.debug("------------------VariableMappingListener-----------");
		logger.debug("----------------------------------------------------");
		if (fromVar != null && toVar != null) {
			String from = (String) fromVar
					.getValue(delegateTask.getExecution());
			String to = (String) toVar.getValue(delegateTask.getExecution());
			if (from != null && to != null && !from.equals(to)) {
				Object value = delegateTask.getExecution().getVariable(from);
				if (value != null) {
					delegateTask.getExecution().setVariable(to, value);
					logger.debug("var '" + to + "' " + " has set");
				}
			}
		}
	}

	public void setFromVar(Expression fromVar) {
		this.fromVar = fromVar;
	}

	public void setToVar(Expression toVar) {
		this.toVar = toVar;
	}

}