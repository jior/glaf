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

package org.jpage.jbpm.mail;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jpage.jbpm.context.Context;
import org.jpage.jbpm.context.JbpmContextFactory;
import org.jpage.jbpm.model.MessageInstance;
import org.jpage.jbpm.service.PersistenceManager;

public class MailCallbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private JbpmConfiguration jbpmConfiguration = JbpmConfiguration
			.getInstance();

	private final static Log logger = LogFactory
			.getLog(MailCallbackServlet.class);

	private PersistenceManager persistenceManager;

	public void init() {
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doProcess(request, response);
	}

	public void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/html;charset=UTF-8");
		String messageId = request.getParameter("messageId");
		JbpmContext jbpmContext = null;
		try {
			persistenceManager = (PersistenceManager) JbpmContextFactory
					.getBean("persistenceManager");

			if (StringUtils.isNotBlank(messageId)) {
				jbpmContext = jbpmConfiguration.createJbpmContext();
				MessageInstance messageInstance = (MessageInstance) persistenceManager
						.getPersistObject(jbpmContext, MessageInstance.class,
								messageId);
				if (messageInstance != null) {
					messageInstance.setLastViewDate(new java.util.Date());
					messageInstance.setLastViewIP(request.getRemoteHost());
					persistenceManager.update(jbpmContext, messageInstance);
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
		} finally {
			Context.close(jbpmContext);
		}
	}

}