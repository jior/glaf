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

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jpage.jbpm.el.DefaultExpressionEvaluator;
import org.jpage.jbpm.instantiation.CustomFieldInstantiator;

public class ContextInstanceAction implements ActionHandler {
	private static final Log logger = LogFactory
			.getLog(ContextInstanceAction.class);

	private static final long serialVersionUID = 1L;

	private Element elements = null;

	private Map variables = null;

	public Element getElements() {
		return elements;
	}

	public void setElements(Element elements) {
		this.elements = elements;
	}

	public Map getVariables() {
		return variables;
	}

	public void setVariables(Map variables) {
		this.variables = variables;
	}

	public void execute(ExecutionContext ctx) throws Exception {
		logger.debug("--------------------------------------------------------");
		logger.debug("---------------------ContextInstanceAction--------------");
		logger.debug("--------------------------------------------------------");
		ContextInstance contextInstance = ctx.getContextInstance();
		Map contextMap = contextInstance.getVariables();
		if (variables != null && variables.size() > 0) {
			Iterator iterator = variables.keySet().iterator();
			while (iterator.hasNext()) {
				String name = (String) iterator.next();
				Object value = variables.get(name);
				if (name != null && value != null) {
					if (value instanceof String) {
						String tmp = (String) value;
						if (tmp.startsWith("#{") && tmp.endsWith("}")) {
							value = DefaultExpressionEvaluator.evaluate(tmp,
									contextMap);
						}
					}
					if (logger.isDebugEnabled()) {
						logger.debug("设置环境变量:" + name + "\t" + value);
					}
					contextInstance.setVariable(name, value);
				}
			}
		}

		if (elements != null) {
			Object obj = CustomFieldInstantiator.getValue(Map.class, elements);
			if (obj instanceof Map) {
				Map paramMap = (Map) obj;
				Iterator iterator = paramMap.keySet().iterator();
				while (iterator.hasNext()) {
					String name = (String) iterator.next();
					Object value = paramMap.get(name);
					if (name != null && value != null) {
						if (value instanceof String) {
							String tmp = (String) value;
							if (tmp.startsWith("#{") && tmp.endsWith("}")) {
								value = DefaultExpressionEvaluator.evaluate(
										tmp, contextMap);
							}
						}
						if (logger.isDebugEnabled()) {
							logger.debug("设置环境变量:" + name + "\t" + value);
						}
						contextInstance.setVariable(name, value);
					}
				}
			}
		}
	}

}
