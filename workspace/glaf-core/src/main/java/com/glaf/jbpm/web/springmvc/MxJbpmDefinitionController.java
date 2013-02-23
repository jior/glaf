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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmContext;
import org.jbpm.db.GraphSession;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.taskmgmt.def.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.core.config.ViewProperties;
import com.glaf.core.util.ResponseUtils;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.Context;
import com.glaf.jbpm.export.MxJbpmProcessExporter;

@Controller("/jbpm/definition")
@RequestMapping("/jbpm/definition")
public class MxJbpmDefinitionController {
	protected final static Log logger = LogFactory
			.getLog(MxJbpmDefinitionController.class);

	public MxJbpmDefinitionController() {

	}

	@RequestMapping("/download")
	public void download(HttpServletRequest request,
			HttpServletResponse response) {
		long id = 0;
		String processDefinitionId = request
				.getParameter("processDefinitionId");
		String processName = request.getParameter("processName");
		JbpmContext jbpmContext = null;
		String filename = null;
		byte[] bytes = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			if (StringUtils.isNotEmpty(processDefinitionId)
					&& StringUtils.isNumeric(processDefinitionId)) {
				ProcessDefinition processDefinition = jbpmContext
						.getGraphSession().getProcessDefinition(
								Long.parseLong(processDefinitionId));
				if (processDefinition != null) {
					id = processDefinition.getId();
					filename = processDefinition.getName() + ".jpdl.zip";
				}
			} else if (StringUtils.isNotEmpty(processName)) {
				ProcessDefinition processDefinition = jbpmContext
						.getGraphSession().findLatestProcessDefinition(
								processName);
				if (processDefinition != null) {
					id = processDefinition.getId();
					filename = processDefinition.getName() + ".jpdl.zip";
				}
			}
		} catch (Exception ex) {
			logger.debug(ex);
		} finally {
			Context.close(jbpmContext);
		}
		if (id > 0) {
			MxJbpmProcessExporter exporter = new MxJbpmProcessExporter();
			bytes = exporter.zipJpdl(id, "UTF-8");
			if (bytes != null) {
				try {
					ResponseUtils.download(request, response, bytes, filename);
				} catch (Exception ex) {
				}
			}
		}
	}

	@RequestMapping
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {
		GraphSession graphSession = null;
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			graphSession = jbpmContext.getGraphSession();

			List<ProcessDefinition> processDefinitions = graphSession
					.findAllProcessDefinitions();
			modelMap.put("processDefinitions", processDefinitions);
		} catch (Exception ex) {
			logger.debug(ex);
		} finally {
			Context.close(jbpmContext);
		}

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = ViewProperties.getString("jbpm_definition.list");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/jbpm/definition/list", modelMap);
	}

	@RequestMapping("/task")
	public ModelAndView task(
			@RequestParam(value = "processDefinitionId", required = false) String processDefinitionId,
			@RequestParam(value = "processName", required = false) String processName,
			ModelMap modelMap) {
		List<Task> rows = new ArrayList<Task>();
		ProcessDefinition processDefinition = null;
		GraphSession graphSession = null;
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			graphSession = jbpmContext.getGraphSession();
			if (StringUtils.isNumeric(processDefinitionId)) {
				processDefinition = graphSession.getProcessDefinition(Long
						.valueOf(processDefinitionId));
			} else if (StringUtils.isNotEmpty(processName)) {
				processDefinition = jbpmContext.getGraphSession()
						.findLatestProcessDefinition(processName);
			}

			if (processDefinition != null) {
				modelMap.put("processDefinition", processDefinition);
				Map<String, Task> taskMap = processDefinition
						.getTaskMgmtDefinition().getTasks();
				if (taskMap != null && taskMap.size() > 0) {
					Set<Entry<String, Task>> entrySet = taskMap.entrySet();
					for (Entry<String, Task> entry : entrySet) {
						String name = entry.getKey();
						Task task = entry.getValue();
						if (name != null && task != null) {
							rows.add(task);
						}
					}
					modelMap.put("tasks", rows);
				}
			}

		} catch (Exception ex) {
			logger.debug(ex);
		} finally {
			Context.close(jbpmContext);
		}

		String x_view = ViewProperties.getString("jbpm_definition.task");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/jbpm/definition/task", modelMap);
	}

}