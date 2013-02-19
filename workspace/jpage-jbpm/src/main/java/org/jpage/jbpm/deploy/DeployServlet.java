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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

 
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.jpage.jbpm.context.Context;
import org.jpage.jbpm.model.DeployInstance;
import org.jpage.jbpm.service.ProcessContainer;
import org.jpage.jbpm.service.ServiceManager;

import org.jpage.util.DateTools;
import org.jpage.util.FileTools;
import org.jpage.util.UUID32;

public class DeployServlet extends HttpServlet {
	private static Log logger = LogFactory.getLog(DeployServlet.class);
	 
	private ServiceManager serviceManager;

	private static final long serialVersionUID = 1L;

	public void init() {
		logger.debug("DeployServlet init...");
		String appPath = this.getServletContext().getRealPath("");
		serviceManager = (ServiceManager) org.jpage.jbpm.context.JbpmContextFactory
				.getBean("serviceManager");
		Context.create();
		String deployPath = appPath + "/WEB-INF/config/jbpm/deploy";
		deployPath = FileTools.getJavaFileSystemPath(deployPath);
		logger.debug("deploy par path:" + deployPath);
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			java.io.File file = new java.io.File(deployPath);
			if (file.isDirectory()) {
				// 列出所有的子文件（夹）名字
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					if (!(filelist[i].endsWith(".par") || filelist[i]
							.endsWith(".rar"))) {
						continue;
					}
					String filename = deployPath + "/" + filelist[i];
					filename = FileTools.getJavaFileSystemPath(filename);
					logger.debug("读取流程定义文件:" + filename);
					java.io.File f = new java.io.File(filename);
					ZipInputStream zis = new ZipInputStream(
							new java.io.FileInputStream(f));
					ProcessDefinition processDefinition = ProcessDefinition
							.parseParZipInputStream(zis);
					zis.close();
					zis = null;
					ProcessDefinition pd = jbpmContext.getGraphSession()
							.findLatestProcessDefinition(
									processDefinition.getName());
					if (pd != null) {
						// 如果工作流中已经存在该流程，舍弃
						continue;
					}
					DeployInstance deployer = serviceManager
							.getMaxDeployInstance(jbpmContext,
									processDefinition.getName());
					if (deployer != null) {
						if (f.lastModified() < deployer
								.getLastModifiedTimeMillis()) {
							// 如果流程定义文件没有数据库的版本新，舍弃
							continue;
						}
					}

					jbpmContext.deployProcessDefinition(processDefinition);
					DeployInstance deployInstance = new DeployInstance();
					deployInstance.setLastModifiedTimeMillis(System
							.currentTimeMillis());
					deployInstance.setObjectId(UUID32.getUUID());
					deployInstance.setObjectName(processDefinition.getName());
					deployInstance.setProcessName(processDefinition.getName());
					deployInstance.setTitle(processDefinition.getName());
					deployInstance.setDeployDate(new Date());
					deployInstance.setProcessDefinitionId(String
							.valueOf(processDefinition.getId()));
					serviceManager.saveDeployInstance(jbpmContext,
							deployInstance);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("部署出错，错误信息请查看服务器日志。");
		} finally {
			Context.close(jbpmContext);
		}
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String appPath = this.getServletContext().getRealPath("");
		String archive = request.getParameter("archive");
		logger.debug("deploying archive " + archive);
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Expires", "-1");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0L);
		request.setCharacterEncoding("UTF-8");

		JbpmContext jbpmContext = null;

		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			if (StringUtils.isNotEmpty(archive)) {
				URL archiveUrl = new URL(archive);
				ZipInputStream zis = new ZipInputStream(archiveUrl.openStream());
				ProcessDefinition processDefinition = ProcessDefinition
						.parseParZipInputStream(zis);
				jbpmContext.deployProcessDefinition(processDefinition);
				DeployInstance deployInstance = new DeployInstance();
				deployInstance.setLastModifiedTimeMillis(System
						.currentTimeMillis());
				deployInstance.setObjectId(UUID32.getUUID());
				deployInstance.setObjectName(processDefinition.getName());
				deployInstance.setProcessName(processDefinition.getName());
				deployInstance.setTitle(processDefinition.getName());
				deployInstance.setVersionNo(processDefinition.getVersion());
				deployInstance.setDeployDate(new Date());
				deployInstance.setProcessDefinitionId(String
						.valueOf(processDefinition.getId()));
				serviceManager.saveDeployInstance(jbpmContext, deployInstance);
				PrintWriter writer = response.getWriter();
				writer.write(archive + " 已经成功部署！");
				return;
			}

			ServletFileUpload upload = new ServletFileUpload();
			upload.setSizeMax(1048576 * 2);// 2MB
			upload.setHeaderEncoding("UTF-8");
			InputStream inputStream = null;

			if (ServletFileUpload.isMultipartContent(request)) {
				FileItemIterator iter = upload.getItemIterator(request);

				while (iter.hasNext()) {
					FileItemStream item = iter.next();
					inputStream = item.openStream();
					if (!item.isFormField() && inputStream != null) {
						byte[] bytes = FileTools.getBytes(inputStream);
						ZipInputStream zis = new ZipInputStream(
								new ByteArrayInputStream(bytes));

						ProcessDefinition processDefinition = ProcessDefinition
								.parseParZipInputStream(zis);
						jbpmContext.deployProcessDefinition(processDefinition);
						zis.close();
						zis = null;

						DeployInstance deployInstance = new DeployInstance();
						deployInstance.setLastModifiedTimeMillis(System
								.currentTimeMillis());
						deployInstance.setObjectId(UUID32.getUUID());
						deployInstance.setObjectName(processDefinition
								.getName());
						deployInstance.setProcessName(processDefinition
								.getName());
						deployInstance.setTitle(processDefinition.getName());
						deployInstance.setVersionNo(processDefinition
								.getVersion());
						deployInstance.setDeployDate(new Date());
						deployInstance.setProcessDefinitionId(String
								.valueOf(processDefinition.getId()));
						serviceManager.saveDeployInstance(jbpmContext,
								deployInstance);

						String processDefinitionId = String
								.valueOf(processDefinition.getId());
						String processName = processDefinition.getName();
						logger.debug("processDefinitionId:"
								+ processDefinitionId);
						logger.debug("processName:" + processName);
						Map taskMap = processDefinition.getTaskMgmtDefinition()
								.getTasks();
						if (taskMap != null && taskMap.size() > 0) {
							Iterator iterator = taskMap.keySet().iterator();
							while (iterator.hasNext()) {
								String taskName = (String) iterator.next();
								logger.debug("taskName:" + taskName);
							}
						}

						try {
							String filename = appPath
									+ "/WEB-INF/deploy/"
									+ processDefinition.getName()
									+ "_"
									+ DateTools.getDateTime("yyyyMMddHHmmss",
											new Date()) + ".zip";
							FileTools.save(filename, bytes);

							filename = appPath + "/WEB-INF/deploy/"
									+ processDefinition.getName() + "_V"
									+ processDefinition.getVersion() + ".zip";
							FileTools.save(filename, bytes);

						} catch (Exception ex) {
							ex.printStackTrace();
						}

						bytes = processDefinition.getFileDefinition().getBytes(
								"processimage.jpg");
						OutputStream out = response.getOutputStream();
						out.write(bytes);
						out.flush();
						out.close();
						bytes = null;
						return;
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			PrintWriter writer = response.getWriter();
			writer.write("部署出错，错误信息请查看服务器日志。");
		} finally {
			Context.close(jbpmContext);
		}
	}

}
