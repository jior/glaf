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

package com.glaf.jbpm.assignment;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.def.AssignmentHandler;
import org.jbpm.taskmgmt.exe.Assignable;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;

import com.glaf.jbpm.util.Constant;

public class ProcessStarterAssignment implements AssignmentHandler {
	private static final Log logger = LogFactory
			.getLog(ProcessStarterAssignment.class);

	private static final long serialVersionUID = 1L;

	public ProcessStarterAssignment() {

	}

	public void assign(Assignable assignable, ExecutionContext ctx) {
		logger.debug("-------------------------------------------------------");
		logger.debug("----------------ProcessStarterAssignment---------------");
		logger.debug("-------------------------------------------------------");

		ContextInstance contextInstance = ctx.getContextInstance();

		String actorId = (String) contextInstance
				.getVariable(Constant.PROCESS_STARTERID);
		if (StringUtils.isEmpty(actorId)) {
			long taskInstanceId = Long.MAX_VALUE;
			TaskMgmtInstance tmi = ctx.getProcessInstance()
					.getTaskMgmtInstance();
			Collection<TaskInstance> taskInstances = tmi.getTaskInstances();
			Iterator<TaskInstance> iterator = taskInstances.iterator();
			while (iterator.hasNext()) {
				TaskInstance taskInstance = iterator.next();
				if (taskInstance.hasEnded()
						&& StringUtils.isNotEmpty(taskInstance.getActorId())) {
					if (taskInstance.getId() < taskInstanceId) {
						taskInstanceId = taskInstance.getId();
					}
				}
			}
			if (taskInstanceId < Long.MAX_VALUE) {
				TaskInstance taskInstance = ctx.getJbpmContext()
						.getTaskInstance(taskInstanceId);
				actorId = taskInstance.getActorId();
			}
		}

		if (StringUtils.isNotEmpty(actorId)) {
			assignable.setActorId(actorId);
			return;
		}

	}
}