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
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmContext;
import org.jbpm.context.exe.VariableInstance;
import org.jbpm.db.GraphSession;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.node.TaskNode;
import org.jbpm.taskmgmt.def.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.core.config.CustomProperties;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.User;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.LogUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.Tools;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.Context;
import com.glaf.jbpm.manager.JbpmProcessManager;
import com.glaf.jbpm.manager.JbpmTaskManager;
import com.glaf.jbpm.model.TaskItem;
import com.glaf.jbpm.query.ProcessQuery;

@Controller("/jbpm/monitor")
@RequestMapping("/jbpm/monitor")
public class MxJbpmMonitorController {
	protected final static Log logger = LogFactory
			.getLog(MxJbpmMonitorController.class);

	public MxJbpmMonitorController() {

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

		String x_view = ViewProperties.getString("jbpm_monitor.list");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/jbpm/monitor/list", modelMap);
	}

	@RequestMapping("/processInstances")
	public ModelAndView processInstances(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		int currPageNo = 1;
		String temp = request.getParameter(Paging.PAGENO_PARAMNAME);
		if ((temp != null) && (temp.length() > 0)) {
			try {
				currPageNo = Integer.parseInt(temp);
			} catch (NumberFormatException e) {
			}
		}

		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);

		logger.debug("paramMap:" + paramMap);

		int start = ParamUtils.getInt(paramMap, "start");
		int limit = ParamUtils.getInt(paramMap, "limit");

		if (start > 0 && limit > 0) {
			currPageNo = start / limit + 1;
		}

		ProcessQuery query = new ProcessQuery();
		Tools.populate(query, paramMap);

		Paging jpage = null;
		JbpmContext jbpmContext = null;
		try {
			Map<String, User> userMap = ProcessContainer.getContainer()
					.getUserMap();
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			JbpmProcessManager jbpmProcessManager = ProcessContainer
					.getContainer().getJbpmProcessManager();
			jpage = jbpmProcessManager.getPageProcessInstances(jbpmContext,
					currPageNo, limit, query);
			if (jpage.getRows() != null && jpage.getRows().size() > 0) {
				Map<String, Object> variables = new java.util.concurrent.ConcurrentHashMap<String, Object>();
				Collection<Long> processInstanceIds = new HashSet<Long>();
				Iterator<Object> iterator = jpage.getRows().iterator();
				while (iterator.hasNext()) {
					ProcessInstance processInstance = (ProcessInstance) iterator
							.next();
					processInstanceIds.add(processInstance.getId());
				}
				Map<String, VariableInstance> variableMap = jbpmProcessManager
						.getVariableMap(jbpmContext, processInstanceIds);
				if (variableMap != null) {
					Set<Entry<String, VariableInstance>> entrySet = variableMap
							.entrySet();
					for (Entry<String, VariableInstance> entry : entrySet) {
						String name = entry.getKey();
						VariableInstance vi = entry.getValue();
						if (name != null && vi != null) {
							String json = (String) vi.getValue();
							logger.debug(json);
							if (StringUtils.isNotEmpty(json)) {
								Map<String, Object> jsonMap = JsonUtils
										.decode(json);
								if (jsonMap != null) {
									variables.put(name, jsonMap);
								}
							}
						}
					}
					request.setAttribute("variables", variables);
				}
			}

			request.setAttribute("userMap", userMap);

			request.setAttribute("jpage", jpage);
		} catch (Exception ex) {
			if (LogUtils.isDebug()) {
				ex.printStackTrace();
				logger.debug(ex);
			}
		} finally {
			Context.close(jbpmContext);
		}

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = CustomProperties
				.getString("jbpm_monitor.processInstances");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/jbpm/monitor/processInstances");
	}

	@RequestMapping("/processInstancesX")
	public ModelAndView processInstancesX(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		int currPageNo = 1;
		String temp = request.getParameter(Paging.PAGENO_PARAMNAME);
		if ((temp != null) && (temp.length() > 0)) {
			try {
				currPageNo = Integer.parseInt(temp);
			} catch (NumberFormatException e) {
			}
		}

		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);

		logger.debug("paramMap:" + paramMap);

		ProcessQuery query = new ProcessQuery();
		Tools.populate(query, paramMap);

		int start = ParamUtils.getInt(paramMap, "start");
		int limit = ParamUtils.getInt(paramMap, "limit");

		if (start > 0 && limit > 0) {
			currPageNo = start / limit + 1;
		}

		long processDefinitionId = ParamUtils.getLong(paramMap,
				"processDefinitionId");

		Paging jpage = null;
		JbpmContext jbpmContext = null;
		List<TaskItem> taskItems = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			if (processDefinitionId > 0) {
				ProcessDefinition pd = jbpmContext.getGraphSession()
						.loadProcessDefinition(processDefinitionId);
				request.setAttribute("processName", pd.getName());
				List<Node> nodes = pd.getNodes();
				request.setAttribute("nodes", nodes);
				Map<String, Task> taskMap = new LinkedHashMap<String, Task>();
				Iterator<Node> iter = nodes.iterator();
				while (iter.hasNext()) {
					Node node = iter.next();
					if (node instanceof TaskNode) {
						TaskNode taskNode = (TaskNode) node;
						Map<String, Task> tasksMap = taskNode.getTasksMap();
						taskMap.putAll(tasksMap);
					}
				}
				request.setAttribute("taskMap", taskMap);
			}
			JbpmProcessManager jbpmProcessManager = ProcessContainer
					.getContainer().getJbpmProcessManager();
			jpage = jbpmProcessManager.getPageProcessInstances(jbpmContext,
					currPageNo, limit, query);
			if (jpage.getRows() != null && jpage.getRows().size() > 0) {
				Collection<Long> processInstanceIds = new HashSet<Long>();
				Iterator<Object> iterator = jpage.getRows().iterator();
				while (iterator.hasNext()) {
					ProcessInstance processInstance = (ProcessInstance) iterator
							.next();
					processInstanceIds.add(processInstance.getId());
				}

				paramMap.clear();
				ProcessQuery q = new ProcessQuery();

				q.setProcessInstanceIds(processInstanceIds);
				JbpmTaskManager jbpmTaskManager = ProcessContainer
						.getContainer().getJbpmTaskManager();
				taskItems = jbpmTaskManager.getWorkedTaskItems(jbpmContext, q);

			}

			request.setAttribute("jpage", jpage);
		} catch (Exception ex) {
			if (LogUtils.isDebug()) {
				ex.printStackTrace();
				logger.debug(ex);
			}
		} finally {
			Context.close(jbpmContext);
		}

		Map<String, User> userMap = ProcessContainer.getContainer()
				.getUserMap();
		if (userMap != null && taskItems != null && taskItems.size() > 0) {

			Iterator<TaskItem> iterator = taskItems.iterator();
			while (iterator.hasNext()) {
				TaskItem taskItem = iterator.next();
				User u = userMap.get(taskItem.getActorId());
				if (u != null) {
					taskItem.setActorName(u.getName());
				}

			}
			request.setAttribute("taskItems", taskItems);
		}

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = CustomProperties
				.getString("jbpm_monitor.processInstancesX");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/jbpm/monitor/processInstancesX");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		GraphSession graphSession = null;
		JbpmContext jbpmContext = null;
		try {
			Map<String, User> userMap = ProcessContainer.getContainer()
					.getUserMap();
			modelMap.put("userMap", userMap);
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

		String x_view = ViewProperties.getString("jbpm_monitor.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/jbpm/monitor/query", modelMap);
	}

}