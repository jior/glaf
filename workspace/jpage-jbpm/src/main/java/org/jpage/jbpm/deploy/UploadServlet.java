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

package org.jpage.jbpm.deploy;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.jpage.jbpm.context.Context;
import org.jpage.jbpm.model.DeployInstance;
import org.jpage.jbpm.service.ServiceManager;
import org.jpage.util.DateTools;
import org.jpage.util.FileTools;
import org.jpage.util.UUID32;

public class UploadServlet extends HttpServlet {
	private static Log logger = LogFactory.getLog(UploadServlet.class);
	private JbpmConfiguration jbpmConfiguration = JbpmConfiguration
			.getInstance();
	private static final long serialVersionUID = 1L;

	private ServiceManager serviceManager;

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html");
		response.getWriter().println(handleRequest(request));
	}

	public void printInput(HttpServletRequest request) throws IOException {
		InputStream inputStream = request.getInputStream();
		StringBuffer buffer = new StringBuffer();
		int read;
		while ((read = inputStream.read()) != -1) {
			buffer.append((char) read);
		}
		logger.debug(buffer.toString());
	}

	private String handleRequest(HttpServletRequest request) {
		if (!ServletFileUpload.isMultipartContent(new ServletRequestContext(
				request))) {
			logger.debug("Not a multipart request");
			return "Not a multipart request";
		}
		try {
			ServletFileUpload fileUpload = new ServletFileUpload();
			List list = fileUpload.parseRequest(request);
			Iterator iterator = list.iterator();
			if (!iterator.hasNext()) {
				logger.debug("No process file in the request");
				return "No process file in the request";
			}
			FileItem fileItem = (FileItem) iterator.next();
			if (fileItem.getContentType().indexOf(
					"application/x-zip-compressed") == -1) {
				logger.debug("Not a process archive");
				return "Not a process archive";
			}
			return doDeployment(fileItem);
		} catch (FileUploadException ex) {
			ex.printStackTrace();
			return "FileUploadException";
		}
	}

	private String doDeployment(FileItem fileItem) {
		JbpmContext jbpmContext = null;
		try {
			serviceManager = (ServiceManager) org.jpage.jbpm.context.JbpmContextFactory
					.getBean("serviceManager");
			ZipInputStream zipInputStream = new ZipInputStream(fileItem
					.getInputStream());
			jbpmContext = jbpmConfiguration.createJbpmContext();
			ProcessDefinition processDefinition = ProcessDefinition
					.parseParZipInputStream(zipInputStream);
			logger.debug("Created a processdefinition : "
					+ processDefinition.getName());
			jbpmContext.deployProcessDefinition(processDefinition);
			zipInputStream.close();
			DeployInstance deployInstance = new DeployInstance();
			deployInstance
					.setLastModifiedTimeMillis(System.currentTimeMillis());
			deployInstance.setObjectId(UUID32.getUUID());
			deployInstance.setObjectName(processDefinition.getName());
			deployInstance.setProcessName(processDefinition.getName());
			deployInstance.setTitle(processDefinition.getName());
			deployInstance.setVersionNo(System.currentTimeMillis());
			serviceManager.saveDeployInstance(jbpmContext, deployInstance);
			String appPath = this.getServletContext().getRealPath("");
			try {
				String filename = appPath + "/WEB-INF/deploy/"
						+ processDefinition.getName() + "_"
						+ DateTools.getDateTime("yyyyMMddHHmmss", new Date())
						+ ".zip";
				FileTools.save(filename, fileItem.get());
				
				filename = appPath + "/WEB-INF/deploy/"
						+ processDefinition.getName() + "_V"
						+ processDefinition.getVersion()
						+ ".zip";
				FileTools.save(filename, fileItem.get());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			return "Deployed archive " + processDefinition.getName()
					+ " successfully";
		} catch (IOException ex) {
			return "IOException";
		} finally {
			Context.close(jbpmContext);
		}
	}

}