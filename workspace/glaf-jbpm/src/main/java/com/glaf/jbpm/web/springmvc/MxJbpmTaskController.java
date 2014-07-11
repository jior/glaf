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
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.PooledActor;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.User;
import com.glaf.core.util.LogUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.Tools;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.Context;
import com.glaf.jbpm.manager.JbpmTaskManager;
import com.glaf.jbpm.model.ActivityInstance;
import com.glaf.jbpm.model.TaskItem;
import com.glaf.jbpm.query.ProcessQuery;
import com.glaf.jbpm.util.Constant;

@Controller("/jbpm/task")
@RequestMapping("/jbpm/task")
public class MxJbpmTaskController {
	protected final static Log logger = LogFactory
			.getLog(MxJbpmTaskController.class);

	protected final static String TASK_ACTION = "redirect:/mx/jbpm/task";

	public MxJbpmTaskController() {

	}

	@RequestMapping("/activityInstances")
	public ModelAndView activityInstances(HttpServletRequest request,
			ModelMap modelMap) {
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		Long processInstanceId = ParamUtils.getLong(paramMap,
				"processInstanceId");
		ProcessInstance processInstance;
		JbpmContext jbpmContext = null;
		try {
			Map<String, User> userMap = ProcessContainer.getContainer()
					.getUserMap();
			if (processInstanceId != null) {
				List<TaskItem> taskItems = ProcessContainer.getContainer()
						.getTaskItemsByProcessInstanceId(processInstanceId);
				jbpmContext = ProcessContainer.getContainer()
						.createJbpmContext();
				processInstance = jbpmContext.getProcessInstance(Long
						.valueOf(processInstanceId));
				if (processInstance != null) {
					Map<String, Object> variables = new java.util.HashMap<String, Object>();
					variables.putAll(processInstance.getContextInstance()
							.getVariables());
					modelMap.put("processInstance", processInstance);
					modelMap.put("variables", variables);
					modelMap.put("taskItems", taskItems);
					modelMap.put("userMap", userMap);
				}
			}
		} catch (Exception ex) {
			if (LogUtils.isDebug()) {
				logger.debug(ex);
				ex.printStackTrace();
			}
		} finally {
			Context.close(jbpmContext);
		}

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = ViewProperties.getString("jbpm_task.ActivityInstances");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/jbpm/task/ActivityInstances", modelMap);
	}

	@RequestMapping("/chooseUser")
	public ModelAndView chooseUser(ModelMap modelMap, HttpServletRequest request) {
		String processInstanceId = request.getParameter("processInstanceId");
		String taskName = request.getParameter("taskName");
		StringBuffer taskNameBuffer = new StringBuffer();
		StringBuffer userBuffer = new StringBuffer();
		StringBuffer taskUserBuffer = new StringBuffer();
		Set<String> actorIds = new HashSet<String>();
		JbpmContext jbpmContext = null;
		try {
			Map<String, User> userMap = ProcessContainer.getContainer()
					.getUserMap();
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			if (StringUtils.isNotEmpty(processInstanceId)
					&& StringUtils.isNumeric(processInstanceId)) {
				ProcessInstance processInstance = jbpmContext
						.getProcessInstance(Long.parseLong(processInstanceId));
				if (processInstance != null) {
					TaskMgmtInstance tmi = processInstance
							.getTaskMgmtInstance();
					Collection<TaskInstance> taskInstances = tmi
							.getUnfinishedTasks(processInstance.getRootToken());
					if (taskInstances != null && taskInstances.size() > 0) {
						Iterator<TaskInstance> iter = taskInstances.iterator();
						while (iter.hasNext()) {
							TaskInstance taskInstance = iter.next();
							if (StringUtils.equals(taskName,
									taskInstance.getName())) {
								taskNameBuffer.append("<option value=\"")
										.append(taskInstance.getName())
										.append("\" selected>")
										.append(taskInstance.getName())
										.append('[')
										.append(taskInstance.getDescription())
										.append("]</option>");
								if (StringUtils.isNotEmpty(taskInstance
										.getActorId())) {
									actorIds.add(taskInstance.getActorId());
								} else {
									Set<PooledActor> pooledActors = taskInstance
											.getPooledActors();
									if (pooledActors != null
											&& pooledActors.size() > 0) {
										Iterator<PooledActor> iter2 = pooledActors
												.iterator();
										while (iter2.hasNext()) {
											PooledActor actor = iter2.next();
											String pooledActorId = actor
													.getActorId();
											actorIds.add(pooledActorId);
										}
									}
								}
							} else {
								taskNameBuffer.append("<option value=\"")
										.append(taskInstance.getName())
										.append("\">")
										.append(taskInstance.getName())
										.append('[')
										.append(taskInstance.getDescription())
										.append("]</option>");
							}
						}

						if (userMap != null && userMap.size() > 0) {
							Set<Entry<String, User>> entrySet = userMap
									.entrySet();
							for (Entry<String, User> entry : entrySet) {
								String name = entry.getKey();
								User user = entry.getValue();
								if (name != null && user != null) {
									if (actorIds.contains(user.getActorId())) {
										taskUserBuffer
												.append("<option value=\"")
												.append(user.getActorId())
												.append("\">")
												.append(user.getActorId())
												.append('[')
												.append(user.getName())
												.append("]</option>");
									} else {
										userBuffer.append("<option value=\"")
												.append(user.getActorId())
												.append("\">")
												.append(user.getActorId())
												.append('[')
												.append(user.getName())
												.append("]</option>");
									}
								}
							}
						}
					}
				}

				modelMap.put("selectedScript", taskUserBuffer.toString());
				modelMap.put("noselectedScript", userBuffer.toString());
				modelMap.put("taskNameScript", taskNameBuffer.toString());
			}
		} catch (Exception ex) {
			if (LogUtils.isDebug()) {
				logger.debug(ex);
				ex.printStackTrace();
			}
		} finally {
			Context.close(jbpmContext);
		}

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = ViewProperties.getString("jbpm_task.chooseUser");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/jbpm/task/chooseUser", modelMap);
	}

	@RequestMapping
	public ModelAndView list(ModelMap modelMap, HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String actionType = ParamUtils.getString(params, "actionType", "");
		logger.debug("params->" + params);

		List<TaskItem> taskItems = null;
		try {
			ProcessQuery query = new ProcessQuery();
			Tools.populate(query, params);
			Map<String, User> userMap = ProcessContainer.getContainer()
					.getUserMap();

			if (StringUtils.equals(actionType, "finished")) {
				taskItems = ProcessContainer.getContainer().getWorkedTaskItems(
						query);
			} else {
				taskItems = ProcessContainer.getContainer().getXYTaskItems(
						query);
			}
			if (userMap != null && userMap.size() > 0 && taskItems != null
					&& taskItems.size() > 0) {
				Iterator<TaskItem> iter = taskItems.iterator();
				while (iter.hasNext()) {
					TaskItem ti = iter.next();
					User user = userMap.get(ti.getActorId());
					if (user != null) {
						ti.setActorName(user.getName());
					}
				}
			}
			modelMap.put("taskItems", taskItems);
		} catch (Exception ex) {
			if (LogUtils.isDebug()) {
				logger.debug(ex);
				ex.printStackTrace();
			}
		}

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = ViewProperties.getString("jbpm_task.list");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/jbpm/task/list", modelMap);
	}

	@RequestMapping("/processimage")
	public ModelAndView processimage(HttpServletRequest request) {
		RequestUtils.setRequestParameterToAttribute(request);
		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view);
		}

		String x_view = ViewProperties.getString("jbpm_task.processimage");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}
		return new ModelAndView("/jbpm/task/processimage");
	}

	@RequestMapping("/query")
	public ModelAndView query(ModelMap modelMap, HttpServletRequest request) {
		try {
			Map<String, User> userMap = ProcessContainer.getContainer()
					.getUserMap();
			modelMap.put("userMap", userMap);
		} catch (Exception ex) {
			if (LogUtils.isDebug()) {
				logger.debug(ex);
				ex.printStackTrace();
			}
		}

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = ViewProperties.getString("jbpm_task.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/jbpm/task/query", modelMap);
	}

	@RequestMapping("/reassign")
	@ResponseBody
	public void reassign(ModelMap modelMap, HttpServletRequest request) {
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		Long processInstanceId = ParamUtils.getLong(paramMap,
				"processInstanceId");
		Long taskInstanceId = ParamUtils.getLong(paramMap, "taskInstanceId");
		String actorIdXY = request.getParameter("actorIdXY");
		String taskName = request.getParameter("taskName");

		Set<String> actorIds = new HashSet<String>();
		if (StringUtils.isNotEmpty(actorIdXY)) {
			StringTokenizer token = new StringTokenizer(actorIdXY, ",");
			while (token.hasMoreTokens()) {
				String str = token.nextToken();
				actorIds.add(str);
			}
		}

		if (LogUtils.isDebug()) {
			logger.debug("processInstanceId:" + processInstanceId);
			logger.debug("taskName:" + taskName);
			logger.debug("actorIds:" + actorIds);
		}

		if (actorIds.size() > 0) {
			if (taskInstanceId != null && taskInstanceId > 0) {
				ProcessContainer.getContainer().reassignTask(taskInstanceId,
						actorIds);
			}
			if (processInstanceId != null && StringUtils.isNotEmpty(taskName)) {
				ProcessContainer.getContainer().reassignTask(processInstanceId,
						taskName, actorIds);
			}
		}
	}

	@RequestMapping("/resume")
	@ResponseBody
	public void resume(ModelMap modelMap, HttpServletRequest request) {
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		Long processInstanceId = ParamUtils.getLong(paramMap,
				"processInstanceId");
		if (processInstanceId != null) {
			ProcessContainer.getContainer().resume(processInstanceId);
		}

	}

	@RequestMapping("/suspend")
	@ResponseBody
	public void suspend(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		Long processInstanceId = ParamUtils.getLong(paramMap,
				"processInstanceId");
		if (processInstanceId != null) {
			ProcessContainer.getContainer().suspend(processInstanceId);
		}
	}

	@RequestMapping("/task")
	public ModelAndView task(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		Long processInstanceId = ParamUtils.getLong(paramMap,
				"processInstanceId");
		logger.debug("processInstanceId=" + processInstanceId);
		ProcessInstance processInstance = null;
		ProcessDefinition processDefinition = null;
		JbpmContext jbpmContext = null;
		try {
			Map<String, User> userMap = ProcessContainer.getContainer()
					.getUserMap();
			if (processInstanceId != null && processInstanceId > 0) {
				List<TaskItem> taskItems = ProcessContainer.getContainer()
						.getTaskItemsByProcessInstanceId(processInstanceId);

				jbpmContext = ProcessContainer.getContainer()
						.createJbpmContext();

				Map<String, Object> params = new java.util.HashMap<String, Object>();
				params.put("processInstanceId", processInstanceId);

				processInstance = jbpmContext.getProcessInstance(Long
						.valueOf(processInstanceId));

				if (processInstance != null) {
					processDefinition = processInstance.getProcessDefinition();
					logger.debug(processDefinition.getName() + "-"
							+ processDefinition.getDescription());

					Map<String, Object> variables = new java.util.HashMap<String, Object>();
					String json = (String) processInstance.getContextInstance()
							.getVariable(Constant.JSON_VARIABLE_MAP);
					logger.debug(json);
					if (StringUtils.isNotEmpty(json)) {
						Map<String, Object> jsonMap = com.glaf.core.util.JsonUtils
								.decode(json);
						if (jsonMap != null) {
							variables.putAll(jsonMap);
						}
					}

					TaskMgmtInstance tmi = processInstance
							.getTaskMgmtInstance();
					List<TaskItem> finishedTaskItems = new java.util.ArrayList<TaskItem>();
					Collection<?> taskInstances = tmi.getTaskInstances();
					if (taskInstances != null && taskInstances.size() > 0) {
						JbpmTaskManager jbpmTaskManager = ProcessContainer
								.getContainer().getJbpmTaskManager();
						Map<Long, ActivityInstance> workedMap = jbpmTaskManager
								.getActivityInstanceMap(jbpmContext,
										processInstanceId);
						Iterator<?> iterator = taskInstances.iterator();
						while (iterator.hasNext()) {
							TaskInstance ti = (TaskInstance) iterator.next();
							if (ti.hasEnded()
									&& StringUtils.isNotEmpty(ti.getActorId())) {
								TaskItem item = new TaskItem();
								item.setTaskInstanceId(ti.getId());
								item.setActorId(ti.getActorId());
								item.setCreateDate(ti.getCreate());
								item.setStartDate(ti.getStart());
								item.setEndDate(ti.getEnd());
								item.setTaskDescription(ti.getDescription());
								item.setTaskName(ti.getName());
								ActivityInstance activityInstance = workedMap
										.get(ti.getId());
								if (activityInstance != null) {
									item.setIsAgree(activityInstance
											.getIsAgree());
									item.setOpinion(activityInstance
											.getContent());
									item.setRowId(activityInstance.getRowId());
									item.setJson(activityInstance.getVariable());
								}
								finishedTaskItems.add(item);
							}
						}
					}

					Collections.sort(finishedTaskItems);
					modelMap.put("finishedTaskItems", finishedTaskItems);
					modelMap.put("processInstance", processInstance);
					modelMap.put("processDefinition", processDefinition);
					modelMap.put("variables", variables);
					modelMap.put("taskItems", taskItems);
					modelMap.put("userMap", userMap);
				}
			}
		} catch (Throwable ex) {
			if (LogUtils.isDebug()) {
				logger.debug(ex);
				ex.printStackTrace();
			}
		} finally {
			try {
				Context.close(jbpmContext);
			} catch (java.lang.Throwable ex) {
			}
		}

		logger.debug("----------------------------view task---------------------");

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = ViewProperties.getString("jbpm_task.task");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/jbpm/task/task", modelMap);
	}

	@RequestMapping("/taskInstances")
	public ModelAndView taskInstances(ModelMap modelMap,
			HttpServletRequest request) {
		String actorId = request.getParameter("actorId");
		String actionType = request.getParameter("actionType");
		String processName = request.getParameter("processName");
		List<TaskItem> taskItems = null;

		Map<String, User> userMap = ProcessContainer.getContainer()
				.getUserMap();

		if (StringUtils.equals(actionType, "running")) {
			if (StringUtils.isNotEmpty(processName)) {
				if (StringUtils.isNotEmpty(actorId)) {
					taskItems = ProcessContainer.getContainer()
							.getTaskItemsByProcessName(processName, actorId);
				} else {
					taskItems = ProcessContainer.getContainer()
							.getTaskItemsByProcessName(processName);
				}
			} else {
				if (StringUtils.isNotEmpty(actorId)) {
					taskItems = ProcessContainer.getContainer().getTaskItems(
							actorId);
				}
			}
		} else if (StringUtils.equals(actionType, "finished")) {
			if (StringUtils.isNotEmpty(processName)) {
				if (StringUtils.isNotEmpty(actorId)) {
					taskItems = ProcessContainer.getContainer()
							.getWorkedTaskItems(processName, actorId);
				}
			} else {
				if (StringUtils.isNotEmpty(actorId)) {
					taskItems = ProcessContainer.getContainer()
							.getWorkedTaskItems(actorId);
				}
			}
		}

		if (taskItems == null) {
			taskItems = ProcessContainer.getContainer().getAllTaskItems();
		}

		modelMap.put("taskItems", taskItems);

		modelMap.put("userMap", userMap);

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = ViewProperties.getString("jbpm_task.taskInstances");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/jbpm/monitor/taskInstances", modelMap);
	}

	@RequestMapping("/taskItems")
	public ModelAndView taskItems(ModelMap modelMap, HttpServletRequest request) {
		String actorId = RequestUtils.getActorId(request);
		java.util.Map<String, Object> paramMap = RequestUtils
				.getParameterMap(request);
		String processName = ParamUtils.getString(paramMap, "processName");
		List<TaskItem> taskItems = null;

		if (StringUtils.isNotEmpty(processName)) {
			taskItems = ProcessContainer.getContainer()
					.getTaskItemsByProcessName(processName, actorId);
		} else {
			taskItems = ProcessContainer.getContainer().getTaskItems(actorId);
		}
		request.setAttribute("taskItems", taskItems);

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = ViewProperties.getString("jbpm_task.taskItems");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/jbpm/task/taskItems", modelMap);
	}

	@RequestMapping("/taskList")
	public ModelAndView taskList(ModelMap modelMap, HttpServletRequest request) {
		RequestUtils.setRequestParameterToAttribute(request);

		String x_view = ViewProperties.getString("jbpm_task.taskList");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/jbpm/task/taskList", modelMap);
	}

	@RequestMapping("/taskListJson")
	public ModelAndView taskListJson(ModelMap modelMap,
			HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String actionType = ParamUtils.getString(params, "actionType", "");
		logger.debug("params->" + params);

		List<TaskItem> taskItems = null;
		try {
			ProcessQuery query = new ProcessQuery();
			Tools.populate(query, params);
			Map<String, User> userMap = ProcessContainer.getContainer()
					.getUserMap();

			if (StringUtils.equals(actionType, "finished")) {
				taskItems = ProcessContainer.getContainer().getWorkedTaskItems(
						query);
			} else {
				taskItems = ProcessContainer.getContainer().getTaskItems(query);
			}
			if (userMap != null && userMap.size() > 0 && taskItems != null
					&& taskItems.size() > 0) {
				Iterator<TaskItem> iter = taskItems.iterator();
				while (iter.hasNext()) {
					TaskItem ti = iter.next();
					User user = userMap.get(ti.getActorId());
					if (user != null) {
						ti.setActorName(user.getName());
					}
				}
			}
			modelMap.put("taskItems", taskItems);
		} catch (Exception ex) {
			if (LogUtils.isDebug()) {
				logger.debug(ex);
				ex.printStackTrace();
			}
		}

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = ViewProperties.getString("jbpm_task.taskListJson");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/jbpm/task/taskList-json", modelMap);
	}

}