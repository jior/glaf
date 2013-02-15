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
package org.jpage.jbpm.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.jbpm.graph.def.ProcessDefinition;
import org.jpage.actor.User;
import org.jpage.context.ApplicationContext;
import org.jpage.jbpm.context.Context;
import org.jpage.jbpm.context.JbpmContextFactory;
import org.jpage.jbpm.model.DeployInstance;
import org.jpage.jbpm.service.ProcessContainer;
import org.jpage.jbpm.service.ServiceManager;
import org.jpage.jbpm.util.Constant;
import org.jpage.util.DateTools;
import org.jpage.util.FileTools;
import org.jpage.util.RequestUtil;
import org.jpage.util.UUID32;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

public class DeployController implements
		org.springframework.web.servlet.mvc.Controller {
	private static final Log logger = LogFactory.getLog(DeployController.class);

	public DeployController() {

	}

	public ModelAndView deploy(HttpServletRequest request,
			HttpServletResponse response) {
		byte[] bytes = null;
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			ServiceManager serviceManager = (ServiceManager) JbpmContextFactory
					.getBean("serviceManager");
			if (jbpmContext != null && jbpmContext.getSession() != null) {
				MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
				Map<String, MultipartFile> fileMap = req.getFileMap();
				Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
				for (Entry<String, MultipartFile> entry : entrySet) {
					MultipartFile mFile = entry.getValue();
					if (mFile.getOriginalFilename() != null
							&& mFile.getSize() > 0) {

						bytes = FileTools.getBytes(mFile.getInputStream());
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
							String filename = ApplicationContext.getAppPath()
									+ "/WEB-INF/deploy/"
									+ processDefinition.getName()
									+ "_"
									+ DateTools.getDateTime("yyyyMMddHHmmss",
											new Date()) + ".zip";
							FileTools.save(filename, bytes);

							filename = ApplicationContext.getAppPath()
									+ "/WEB-INF/deploy/"
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

						return null;
					}
				}
			}
		} catch (JbpmException ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			request.setAttribute("error_code", Integer.valueOf(9912));
			request.setAttribute("error_message", "");
			return new ModelAndView("/error");
		} catch (IOException ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			request.setAttribute("error_code", Integer.valueOf(9912));
			request.setAttribute("error_message", "");
			return new ModelAndView("/error");
		} finally {
			Context.close(jbpmContext);
			bytes = null;
		}
		return null;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = (User) request.getSession().getAttribute(
				org.jpage.util.Constant.LOGIN_USER);
		if (user == null) {
			request.setAttribute(Constant.APPLICATION_EXCEPTION_MESSAGE,
					"Not Login!");
			return new ModelAndView("error");
		}

		if (!user.isAdmin()) {
			request.setAttribute(Constant.APPLICATION_EXCEPTION_MESSAGE,
					"Access denied!");
			return new ModelAndView("error");
		}

		String method = request.getParameter("method");
		if (method == null) {
			method = "showDeploy";
		}
		try {

			if (method.equalsIgnoreCase("showDeploy")) {
				return showDeploy(request);
			} else if (method.equalsIgnoreCase("deploy")) {
				return deploy(request, response);
			}

			return showDeploy(request);

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			return new ModelAndView("error");
		}
	}

	public ModelAndView showDeploy(HttpServletRequest request) {
		RequestUtil.setRequestParameterToAttribute(request);
		return new ModelAndView("deploy");
	}

}
