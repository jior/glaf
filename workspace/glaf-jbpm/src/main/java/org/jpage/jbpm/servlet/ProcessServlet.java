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
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpage.jbpm.context.Context;
import org.jpage.jbpm.context.ProcessContext;
import org.jpage.jbpm.service.ProcessContainer;

public class ProcessServlet extends HttpServlet {
	private static Log logger = LogFactory.getLog(ProcessServlet.class);

	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Context.create();
		response.setContentType("text/xml;charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Expires", "-1");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0L);
		request.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version='1.0' encoding='GBK'?>\n");
		buffer.append("<processes>\n");
		try {
			ServletFileUpload fileUpload = new ServletFileUpload();
			List list = fileUpload.parseRequest(request);
			Iterator iterator = list.iterator();
			if (!iterator.hasNext()) {
				logger.debug("No process file in the request");
				return;
			}

			buffer.append("</processes>\n");
			writer.write(buffer.toString());
			writer.flush();
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			writer.write("");
			writer.flush();
			writer.close();
		}
	}
}
