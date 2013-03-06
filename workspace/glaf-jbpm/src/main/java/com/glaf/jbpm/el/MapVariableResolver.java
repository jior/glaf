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

package com.glaf.jbpm.el;

import java.util.Map;

import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.jpdl.el.ELException;
import org.jbpm.jpdl.el.VariableResolver;

public class MapVariableResolver implements VariableResolver {

	private Map<String, Object> context;

	public MapVariableResolver(Map<String, Object> context) {
		this.context = context;
	}

	public Map<String, Object> getContext() {
		return context;
	}

	public void setContext(Map<String, Object> context) {
		this.context = context;
	}

	public Object resolveVariable(String name) throws ELException {
		Object value = null;
		ExecutionContext ctx = ExecutionContext.currentExecutionContext();
		if (ctx != null) {
			if ("taskInstance".equals(name)) {
				value = ctx.getTaskInstance();
			} else if ("processInstance".equals(name)) {
				value = ctx.getProcessInstance();
			} else if ("processDefinition".equals(name)) {
				value = ctx.getProcessDefinition();
			} else if ("token".equals(name)) {
				value = ctx.getToken();
			} else if ("taskMgmtInstance".equals(name)) {
				value = ctx.getTaskMgmtInstance();
			} else if ("contextInstance".equals(name)) {
				value = ctx.getContextInstance();
			} else if ((ctx.getTaskInstance() != null)
					&& (ctx.getTaskInstance().hasVariableLocally(name))) {
				value = ctx.getTaskInstance().getVariable(name);
			} else {
				ContextInstance contextInstance = ctx.getContextInstance();
				Token token = ctx.getToken();
				value = contextInstance.getVariable(name, token);
				if (value == null) {
					if (context != null) {
						value = context.get(name);
					}
				}
			}
		} else {
			if (context != null) {
				value = context.get(name);
			}
		}
		return value;
	}
}