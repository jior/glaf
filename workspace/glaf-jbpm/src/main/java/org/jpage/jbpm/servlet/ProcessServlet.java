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
