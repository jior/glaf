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

package org.jpage.jbpm.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.file.def.FileDefinition;
import org.jbpm.graph.def.ProcessDefinition;
import org.jpage.core.cache.CacheFactory;
import org.jpage.jbpm.context.Context;

public class ProcessImageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private JbpmConfiguration jbpmConfiguration = JbpmConfiguration
			.getInstance();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		long processDefinitionId = Long.parseLong(request
				.getParameter("definitionId"));
		JbpmContext jbpmContext = null;
		byte[] bytes = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			ProcessDefinition processDefinition = jbpmContext.getGraphSession()
					.loadProcessDefinition(processDefinitionId);
			String cacheKey02 = "processimage_"
					+ String.valueOf(processDefinition.getId());
			if (CacheFactory.get(cacheKey02) != null) {
				String str = (String) CacheFactory.get(cacheKey02);
				bytes = Base64.decodeBase64(str);
			} else {
				FileDefinition fileDefinition = processDefinition
						.getFileDefinition();
				bytes = fileDefinition.getBytes("processimage.jpg");
				String str = Base64.encodeBase64String(bytes);
				CacheFactory.put(cacheKey02, str);
			}

			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Expires", "-1");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			OutputStream out = response.getOutputStream();
			out.write(bytes);
			out.flush();
			out.close();
			bytes = null;
		} catch (Exception ex) {
			ex.printStackTrace();
			PrintWriter writer = response.getWriter();
			writer.write("<font color=\"red\" size=\"3\">fetch process image error</font>");
			writer.flush();
			writer.close();
		} finally {
			bytes = null;
			Context.close(jbpmContext);
		}
	}
}
