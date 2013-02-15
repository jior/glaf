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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

public class RemoveDataFieldAction implements ActionHandler {
	private static final Log logger = LogFactory
			.getLog(RemoveDataFieldAction.class);

	private static final long serialVersionUID = 1L;

	private String dataFields;

	public void execute(ExecutionContext ctx) throws Exception {
		logger.debug("--------------------------------------------------------");
		logger.debug("---------------RemoveDataFieldAction--------------------");
		logger.debug("--------------------------------------------------------");
		if (StringUtils.isNotEmpty(dataFields)) {
			ContextInstance contextInstance = ctx.getContextInstance();
			Map variables = contextInstance.getVariables();
			if (variables != null && variables.size() > 0) {
				Iterator iterator = variables.keySet().iterator();
				while (iterator.hasNext()) {
					String name = (String) iterator.next();
					Object value = variables.get(name);
					if (name != null
							&& (name.startsWith("isAgree") || name
									.startsWith("isPass"))) {
						if (value != null) {
							contextInstance.setVariable(name, null);
							contextInstance.setVariable(name + "_xy", value);
						}
					} else {
						if (name != null && dataFields.indexOf(name) != -1) {
							contextInstance.setVariable(name, null);
							contextInstance.setVariable(name + "_xy", value);
						}
					}
				}
			}
		}
	}

	public String getDataFields() {
		return dataFields;
	}

	public void setDataFields(String dataFields) {
		this.dataFields = dataFields;
	}
}
