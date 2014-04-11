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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.core.config.ViewProperties;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.Tools;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.Context;
import com.glaf.jbpm.manager.JbpmExtensionManager;
import com.glaf.jbpm.model.Extension;
import com.glaf.jbpm.model.ExtensionField;

@Controller("/jbpm/extension")
@RequestMapping("/jbpm/extension")
public class MxJbpmExtensionController {
	protected static Log logger = LogFactory
			.getLog(MxJbpmExtensionController.class);

	public MxJbpmExtensionController() {

	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request) {

		String processName = request.getParameter("processName");
		String taskName = request.getParameter("taskName");
		if (StringUtils.isNotEmpty(processName)
				&& StringUtils.isNotEmpty(taskName)) {

		}
		JbpmExtensionManager jbpmExtensionManager = ProcessContainer
				.getContainer().getJbpmExtensionManager();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			Extension extension = jbpmExtensionManager.getExtensionTask(
					jbpmContext, processName, taskName);
			request.setAttribute("extension", extension);
		} catch (Exception ex) {
			request.setAttribute("error_code", 9920);
			return new ModelAndView("/error");
		} finally {
			Context.close(jbpmContext);
		}

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view);
		}

		String x_view = ViewProperties.getString("jbpm_extension.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/jbpm/extension/edit");
	}

	@RequestMapping
	public ModelAndView list(HttpServletRequest request) {
		String processName = request.getParameter("processName");
		if (StringUtils.isNotEmpty(processName)) {
			JbpmExtensionManager jbpmExtensionManager = ProcessContainer
					.getContainer().getJbpmExtensionManager();
			JbpmContext jbpmContext = null;
			try {
				jbpmContext = ProcessContainer.getContainer()
						.createJbpmContext();
				jbpmExtensionManager
						.getExtensionTasks(jbpmContext, processName);
			} catch (Exception ex) {
				request.setAttribute("error_code",9920);
				return new ModelAndView("/error");
			} finally {
				Context.close(jbpmContext);
			}
		}

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view);
		}

		String x_view = ViewProperties.getString("jbpm_extension.list");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/jbpm/extension/list");
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String processName = request.getParameter("processName");
		String taskName = request.getParameter("taskName");
		if (StringUtils.isNotEmpty(processName)
				&& StringUtils.isNotEmpty(taskName)) {
			Extension model = new Extension();
			Tools.populate(model, params);
			List<Extension> extensions = new java.util.concurrent.CopyOnWriteArrayList<Extension>();
			extensions.add(model);
			Map<String, Object> paramMap = RequestUtils.getParameterMap(
					request, "x_property_");
			if (paramMap != null && paramMap.size() > 0) {
				Set<Entry<String, Object>> entrySet = paramMap.entrySet();
				for (Entry<String, Object> entry : entrySet) {
					String name = entry.getKey();
					Object value = entry.getValue();
					if (name != null && value != null) {
						ExtensionField field = new ExtensionField();
						field.setExtension(model);
						field.setName(name);
						field.setValue(value.toString());
						model.addField(field);
					}
				}
			}
			JbpmExtensionManager jbpmExtensionManager = ProcessContainer
					.getContainer().getJbpmExtensionManager();
			JbpmContext jbpmContext = null;
			try {
				jbpmContext = ProcessContainer.getContainer()
						.createJbpmContext();
				jbpmExtensionManager.reconfig(jbpmContext, extensions);
			} catch (Exception ex) {
				request.setAttribute("error_code", 9921);
				return new ModelAndView("/error");
			} finally {
				Context.close(jbpmContext);
			}
		}

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view);
		}

		String x_view = ViewProperties.getString("jbpm_extension.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/jbpm/extension/edit");
	}

}