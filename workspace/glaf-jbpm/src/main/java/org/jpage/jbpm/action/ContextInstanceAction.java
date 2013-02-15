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
