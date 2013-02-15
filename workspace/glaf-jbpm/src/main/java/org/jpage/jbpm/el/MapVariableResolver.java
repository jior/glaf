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

package org.jpage.jbpm.el;

import java.util.Map;

import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.jpdl.el.ELException;
import org.jbpm.jpdl.el.VariableResolver;

public class MapVariableResolver implements VariableResolver {
	private Map context;

	public MapVariableResolver(Map context) {
		this.context = context;
	}

	public Map getContext() {
		return context;
	}

	public void setContext(Map context) {
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
