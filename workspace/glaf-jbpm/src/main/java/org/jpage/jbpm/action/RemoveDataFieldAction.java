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
