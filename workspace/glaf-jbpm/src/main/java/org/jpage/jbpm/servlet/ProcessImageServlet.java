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
