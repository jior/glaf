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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jpage.jbpm.util.ThreadVariable;

public class ApproveAction implements ActionHandler {

	private static final long serialVersionUID = 1L;

	private static final Log logger = LogFactory.getLog(ApproveAction.class);

	public void execute(ExecutionContext ctx) {
		logger.debug("-------------------------------------------------------");
		logger.debug("--------------------ApproveAction----------------------");
		logger.debug("-------------------------------------------------------");
		if (ThreadVariable.getApprove() != null) {
			logger.debug("isAgree:" + ThreadVariable.getApprove());
			ctx.setVariable("isAgree", ThreadVariable.getApprove());
		}
	}

}
