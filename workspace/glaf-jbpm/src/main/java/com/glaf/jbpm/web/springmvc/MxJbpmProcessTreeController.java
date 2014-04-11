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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmContext;
import org.jbpm.db.GraphSession;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.taskmgmt.def.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.core.config.ViewProperties;
import com.glaf.core.util.RequestUtils;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.Context;

@Controller("/jbpm/tree")
@RequestMapping("/jbpm/tree")
public class MxJbpmProcessTreeController {
	protected final static Log logger = LogFactory
			.getLog(MxJbpmProcessTreeController.class);

	@RequestMapping("/exttree")
	public ModelAndView exttree(HttpServletRequest request) {
		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view);
		}

		String x_view = ViewProperties.getString("jbpm_tree.exttree");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}
		return new ModelAndView("/jbpm/tree/exttree");
	}

	@RequestMapping("/json")
	public ModelAndView json(HttpServletRequest request) {
		RequestUtils.setRequestParameterToAttribute(request);
		String node = request.getParameter("node");
		String process_name = null;
		String processDefinitionId = null;
		if (StringUtils.isNotEmpty(node)) {
			if (StringUtils.startsWith(node, "x_pdid:")) {
				processDefinitionId = StringUtils.replace(node, "x_pdid:", "");
			} else if (StringUtils.startsWith(node, "x_pdname:")) {
				process_name = StringUtils.replace(node, "x_pdname:", "");
			}
		}
		logger.debug("process_name:" + process_name);
		logger.debug("processDefinitionId:" + processDefinitionId);
		List<ProcessDefinition> result = null;
		GraphSession graphSession = null;
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			graphSession = jbpmContext.getGraphSession();
			if (StringUtils.isNotEmpty(process_name)) {
				result = graphSession
						.findAllProcessDefinitionVersions(process_name);
			} else {
				result = graphSession.findAllProcessDefinitions();
			}
			if (StringUtils.isNotEmpty(processDefinitionId)
					&& StringUtils.isNumeric(processDefinitionId)) {
				ProcessDefinition pd = graphSession.getProcessDefinition(Long
						.parseLong(processDefinitionId));
				if (pd != null) {
					Map<String, Task> tasks = pd.getTaskMgmtDefinition()
							.getTasks();
					if (tasks != null) {
						List<Task> rows = new java.util.ArrayList<Task>();
						Iterator<Task> iterator = tasks.values().iterator();
						while (iterator.hasNext()) {
							Task task = iterator.next();
							rows.add(task);
						}
						request.setAttribute("tasks", rows);
						request.setAttribute("processDefinition", pd);
					}
				}
			}
			request.setAttribute("rows", result);
		} catch (Exception ex) {
			logger.debug(ex);
		} finally {
			Context.close(jbpmContext);
		}

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view);
		}

		String x_view = ViewProperties.getString("jbpm_tree.json");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/jbpm/tree/json");
	}

	@RequestMapping
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {
		String processDefinitionId = request
				.getParameter("processDefinitionId");
		String processName = request.getParameter("processName");
		List<ProcessDefinition> result = null;
		GraphSession graphSession = null;
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			graphSession = jbpmContext.getGraphSession();
			if (StringUtils.isNotEmpty(processDefinitionId)
					&& StringUtils.isNumeric(processDefinitionId)) {
				Collection<Long> processDefinitionIds = new java.util.ArrayList<Long>();
				processDefinitionIds.add(Long.parseLong(processDefinitionId));
				result = graphSession
						.findProcessDefinitions(processDefinitionIds);
			} else {
				if (StringUtils.isNotEmpty(processName)) {
					result = graphSession
							.findAllProcessDefinitionVersions(processName);
				} else {
					result = graphSession.findAllProcessDefinitions();
				}
			}
			modelMap.put("rows", result);
		} catch (Exception ex) {
			logger.debug(ex);
		} finally {
			Context.close(jbpmContext);
		}

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = ViewProperties.getString("jbpm_tree.list");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/jbpm/tree/list", modelMap);
	}

	@RequestMapping("/main")
	public ModelAndView main(HttpServletRequest request) {
		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view);
		}

		String x_view = ViewProperties.getString("jbpm_tree.main");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}
		return new ModelAndView("/jbpm/tree/main");
	}

}