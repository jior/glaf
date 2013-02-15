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


package org.jpage.jbpm.assignment;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.def.AssignmentHandler;
import org.jbpm.taskmgmt.exe.Assignable;
import org.jpage.jbpm.context.JbpmContextFactory;
import org.jpage.jbpm.model.StateInstance;
import org.jpage.jbpm.service.ServiceManager;
import org.jpage.jbpm.util.Constant;

public class ProcessStarterAssignment implements AssignmentHandler {
	private static final Log logger = LogFactory
			.getLog(ProcessStarterAssignment.class);

	private static final long serialVersionUID = 1L;

	private ServiceManager serviceManager;

	public ProcessStarterAssignment() {

	}

	public void assign(Assignable assignable, ExecutionContext ctx) {
		logger.debug("-------------------------------------------------------");
		logger.debug("----------------ProcessStarterAssignment---------------");
		logger.debug("-------------------------------------------------------");

		ContextInstance contextInstance = ctx.getContextInstance();
		ProcessInstance processInstance = ctx.getProcessInstance();
		String processInstanceId = String.valueOf(processInstance.getId());

		serviceManager = (ServiceManager) JbpmContextFactory
				.getBean("serviceManager");

		String actorId = (String) contextInstance
				.getVariable(Constant.PROCESS_START_ACTORID);

		if (StringUtils.isNotEmpty(actorId)) {
			assignable.setActorId(actorId);
			return;
		}

		List stateInstances = serviceManager.getStateInstances(
				ctx.getJbpmContext(), processInstanceId);
		if (stateInstances != null && stateInstances.size() > 0) {
			StateInstance stateInstance = (StateInstance) stateInstances.get(0);
			actorId = stateInstance.getActorId();
			if (StringUtils.isNotEmpty(actorId)) {
				assignable.setActorId(actorId);
				return;
			}
		}
	}
}
