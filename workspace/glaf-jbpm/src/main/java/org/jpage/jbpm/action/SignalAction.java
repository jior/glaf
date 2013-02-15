/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
