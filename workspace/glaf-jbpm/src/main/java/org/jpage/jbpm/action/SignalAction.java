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


package org.jpage.jbpm.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;

public class SignalAction implements ActionHandler {

	private static final Log logger = LogFactory.getLog(SignalAction.class);

	private static final long serialVersionUID = 1L;

	private String expression;

	private String transitionName;

	public SignalAction() {

	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getTransitionName() {
		return transitionName;
	}

	public void setTransitionName(String transitionName) {
		this.transitionName = transitionName;
	}

	public void execute(ExecutionContext ctx) throws Exception {
		logger.debug("--------------------------------------------------------");
		logger.debug("---------------------SignalAction-----------------------");
		logger.debug("--------------------------------------------------------");
		boolean signal = false;
		if (StringUtils.isNotBlank(expression)) {
			Map params = new HashMap();
			ContextInstance contextInstance = ctx.getContextInstance();
			Map variables = contextInstance.getVariables();
			if (variables != null && variables.size() > 0) {
				Iterator iterator = variables.keySet().iterator();
				while (iterator.hasNext()) {
					String variableName = (String) iterator.next();
					if (params.get(variableName) == null) {
						Object value = contextInstance
								.getVariable(variableName);
						params.put(variableName, value);
					}
				}
			}

			logger.debug(params);

			if (expression.startsWith("#{") && expression.endsWith("}")) {
				if (logger.isDebugEnabled()) {
					logger.debug("expression->" + expression);
					logger.debug("params->" + params);
				}
				Object value = org.jpage.jbpm.el.DefaultExpressionEvaluator
						.evaluate(expression, params);
				if (value != null) {
					if (value instanceof Boolean) {
						Boolean b = (Boolean) value;
						signal = b.booleanValue();
						logger.debug("signal->" + signal);
					}
				}
			}
		}

		if (signal) {
			ProcessInstance processInstance = ctx.getProcessInstance();
			if (StringUtils.isNotBlank(transitionName)) {
				processInstance.signal(transitionName);
			} else {
				processInstance.signal();
			}
		}
	}

}
