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

package com.glaf.jbpm.web.springmvc;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.jbpm.graph.def.ProcessDefinition;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.core.config.ViewProperties;
import com.glaf.core.util.LogUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.jbpm.config.JbpmExtensionReader;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.Context;
import com.glaf.jbpm.deploy.MxJbpmProcessDeployer;
import com.glaf.jbpm.manager.JbpmExtensionManager;
import com.glaf.jbpm.model.Extension;

@Controller("/jbpm/deploy")
@RequestMapping("/jbpm/deploy")
public class MxJbpmDeployController {
	private final static Log logger = LogFactory
			.getLog(MxJbpmDeployController.class);

	public MxJbpmDeployController() {

	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showDeploy(HttpServletRequest request) {
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("jbpm_deploy.showDeploy");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/jbpm/deploy/showDeploy");
	}

	@RequestMapping("/showConfig")
	public ModelAndView showConfig(HttpServletRequest request) {
		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view);
		}

		String x_view = ViewProperties.getString("jbpm_deploy.showConfig");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/jbpm/deploy/showConfig");
	}

	@RequestMapping(value = "/deploy", method = RequestMethod.POST)
	public ModelAndView deploy(HttpServletRequest request,
			HttpServletResponse response) {
		String archive = request.getParameter("archive");
		if (LogUtils.isDebug()) {
			logger.debug("deploying archive " + archive);
		}

		MxJbpmProcessDeployer deployer = new MxJbpmProcessDeployer();

		byte[] bytes = null;
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();

			if (jbpmContext != null && jbpmContext.getSession() != null) {
				MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
				Map<String, MultipartFile> fileMap = req.getFileMap();
				Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
				for (Entry<String, MultipartFile> entry : entrySet) {
					MultipartFile mFile = entry.getValue();
					if (mFile.getOriginalFilename() != null
							&& mFile.getSize() > 0) {
						ProcessDefinition processDefinition = deployer.deploy(
								jbpmContext, mFile.getBytes());
						bytes = processDefinition.getFileDefinition().getBytes(
								"processimage.jpg");
						response.setContentType("image/jpeg");
						response.setHeader("Pragma", "No-cache");
						response.setHeader("Expires", "-1");
						response.setHeader("ICache-Control", "no-cache");
						response.setDateHeader("Expires", 0L);
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
			request.setAttribute("error_code", 9912);
			request.setAttribute("error_message", "");
			return new ModelAndView("/error");
		} catch (IOException ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			request.setAttribute("error_code", 9912);
			request.setAttribute("error_message", "");
			return new ModelAndView("/error");
		} finally {
			Context.close(jbpmContext);
			bytes = null;
		}
		return null;
	}

	@RequestMapping(value = "/reconfig", method = RequestMethod.POST)
	public ModelAndView reconfig(HttpServletRequest request) {
		String actorId = RequestUtils.getActorId(request);
		String archive = request.getParameter("archive");
		if (LogUtils.isDebug()) {
			logger.debug("deploying archive " + archive);
		}
		MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
		JbpmExtensionReader reader = new JbpmExtensionReader();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			Map<String, MultipartFile> fileMap = req.getFileMap();
			Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
			for (Entry<String, MultipartFile> entry : entrySet) {
				MultipartFile mFile = entry.getValue();
				if (mFile.getOriginalFilename() != null && mFile.getSize() > 0) {
					List<Extension> extensions = reader.readTasks(mFile
							.getInputStream());
					if (extensions != null && extensions.size() > 0) {
						Iterator<Extension> iter = extensions.iterator();
						while (iter.hasNext()) {
							Extension extension = iter.next();
							extension.setCreateActorId(actorId);
						}
						JbpmExtensionManager jbpmExtensionManager = ProcessContainer
								.getContainer().getJbpmExtensionManager();
						jbpmExtensionManager.reconfig(jbpmContext, extensions);
						request.setAttribute("message", "???");
					}
				}
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			request.setAttribute("error_code", 9913);
			request.setAttribute("error_message", "");
			return new ModelAndView("/error");
		} finally {
			Context.close(jbpmContext);
		}

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view);
		}

		String x_view = ViewProperties.getString("jbpm_deploy.configFinish");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/jbpm/deploy/configFinish");
	}

}