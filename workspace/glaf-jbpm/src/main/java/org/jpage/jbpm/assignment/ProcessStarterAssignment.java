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
