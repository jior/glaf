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

package org.jpage.jbpm.cmd;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MailInvokerCmd {

	private static final Log logger = LogFactory.getLog(MailInvokerCmd.class);

	public MailInvokerCmd() {

	}

	public void notify(java.util.Map<String, Object> params) {
		logger.debug("------------------------------------------------------");
		logger.debug("---------------MailInvokerCmd-------------------------");
		logger.debug("------------------------------------------------------");
		logger.debug(params);
	}

}
