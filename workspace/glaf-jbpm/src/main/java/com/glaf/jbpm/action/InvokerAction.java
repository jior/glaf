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

package com.glaf.jbpm.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.jbpm.JbpmException;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.springframework.util.MethodInvoker;

import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.LogUtils;
import com.glaf.jbpm.util.Constant;
import com.glaf.jbpm.util.CustomFieldInstantiator;

@SuppressWarnings("unchecked")
public class InvokerAction implements ActionHandler {
	private static final Log logger = LogFactory.getLog(InvokerAction.class);

	private static final long serialVersionUID = 1L;

	protected transient Element elements;

	protected String className;

	protected String method;

	protected String description;

	protected String nodeType;

	protected boolean visible;

	protected boolean selected;

	public void execute(ExecutionContext ctx) {
		logger.debug("-------------------------------------------------------");
		logger.debug("--------------------InvokerAction----------------------");
		logger.debug("-------------------------------------------------------");

		if (StringUtils.isNotEmpty(className) && StringUtils.isNotEmpty(method)) {
			MethodInvoker methodInvoker = new MethodInvoker();
			Object target = ClassUtils.instantiateObject(className);

			Map<String, Object> params = new HashMap<String, Object>();

			ContextInstance contextInstance = ctx.getContextInstance();
			Map<String, Object> variables = contextInstance.getVariables();
			if (variables != null && variables.size() > 0) {
				Set<Entry<String, Object>> entrySet = variables.entrySet();
				for (Entry<String, Object> entry : entrySet) {
					String name = entry.getKey();
					Object value = entry.getValue();
					if (name != null && value != null
							&& params.get(name) == null) {
						params.put(name, value);
					}
				}
			}

			if (elements != null) {
				if (LogUtils.isDebug()) {
					logger.debug("elements:" + elements.asXML());
				}
				Object object = CustomFieldInstantiator.getValue(Map.class,
						elements);
				if (object instanceof Map) {
					Map<String, Object> paramMap = (Map<String, Object>) object;
					Set<Entry<String, Object>> entrySet = paramMap.entrySet();
					for (Entry<String, Object> entry : entrySet) {
						String key = entry.getKey();
						Object value = entry.getValue();
						if (key != null && value != null) {
							params.put(key, value);
						}
					}
				}
			}

			Map<String, Object> varMap = contextInstance.getVariables();
			if (varMap != null && varMap.size() > 0) {
				Set<Entry<String, Object>> entrySet = varMap.entrySet();
				for (Entry<String, Object> entry : entrySet) {
					String variableName = entry.getKey();
					if (params.get(variableName) == null) {
						Object value = contextInstance
								.getVariable(variableName);
						params.put(variableName, value);
					}
				}
			}

			Object rowId = contextInstance.getVariable(Constant.PROCESS_ROWID);
			params.put("rowId", rowId);

			ProcessInstance processInstance = ctx.getProcessInstance();
			ProcessDefinition processDefinition = processInstance
					.getProcessDefinition();
			Long processInstanceId = processInstance.getId();
			params.put("processInstanceId", processInstanceId);
			params.put("processName", processDefinition.getName());
			params.put("processDefinitionId", processDefinition.getId());
			params.put("processDefinition", processDefinition);
			params.put("processInstance", processInstance);

			Object[] arguments = { params, ctx.getJbpmContext().getConnection() };
			methodInvoker.setArguments(arguments);
			methodInvoker.setTargetObject(target);
			methodInvoker.setTargetMethod(method);
			try {
				methodInvoker.prepare();
				methodInvoker.invoke();
			} catch (Exception ex) {
				if (LogUtils.isDebug()) {
					ex.printStackTrace();
					logger.debug(ex);
				}
				throw new JbpmException(ex);
			}
		}

	}

	public String getClassName() {
		return className;
	}

	public String getDescription() {
		return description;
	}

	public Element getElements() {
		return elements;
	}

	public String getMethod() {
		return method;
	}

	public String getNodeType() {
		return nodeType;
	}

	public boolean isSelected() {
		return selected;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setElements(Element elements) {
		this.elements = elements;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}