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

package org.jpage.jbpm.job;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpage.jbpm.context.JbpmContextFactory;
import org.quartz.Scheduler;

public class JobServlet extends HttpServlet {
	private final static Log logger = LogFactory.getLog(JobServlet.class);

	private static final long serialVersionUID = 1L;

	public void init() {
		logger.debug("JobServlet init...");
		try {
			String loadSchedulerOnSetup = this
					.getInitParameter("loadSchedulerOnSetup");
			if (StringUtils.equalsIgnoreCase(loadSchedulerOnSetup, "true")) {
				logger.debug("正在加载调度引擎...");
				Scheduler scheduler = (Scheduler) JbpmContextFactory
						.getBean("scheduler");
				scheduler.start();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			logger.error("加载调度引擎出错:" + ex.getMessage());
		}
	}

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

	}

}
