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

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.glaf.jbpm.util.ThreadVariable;

public class ThreadVariableAction implements ActionHandler {

	private static final long serialVersionUID = 1L;

	private static final Log logger = LogFactory
			.getLog(ThreadVariableAction.class);

	protected List<String> vars;

	public void execute(ExecutionContext ctx) {
		logger.debug("----------------------------------------------------------");
		logger.debug("--------------------MxThreadVariableAction----------------");
		logger.debug("----------------------------------------------------------");
		if (ThreadVariable.getDataFields() != null && vars != null) {
			for (String var : vars) {
				if (ThreadVariable.getDataFields().get(var) != null) {
					ctx.setVariable(var, ThreadVariable.getDataFields()
							.get(var));
				}
			}
		}
	}

	public List<String> getVars() {
		return vars;
	}

	public void setVars(List<String> vars) {
		this.vars = vars;
	}

}