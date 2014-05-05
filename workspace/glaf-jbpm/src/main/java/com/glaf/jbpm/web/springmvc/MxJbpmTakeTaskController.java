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

import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.graph.node.TaskNode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.core.config.CustomProperties;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.util.LogUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.template.util.TemplateUtils;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.model.ActivityInstance;
import com.glaf.jbpm.model.TaskItem;

@Controller("/jbpm/take")
@RequestMapping("/jbpm/take")
public class MxJbpmTakeTaskController {
	protected final static Log logger = LogFactory
			.getLog(MxJbpmTakeTaskController.class);

	public MxJbpmTakeTaskController() {

	}

	@RequestMapping
	public ModelAndView takeTask(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		Long processInstanceId = ParamUtils.getLong(paramMap,
				"processInstanceId");
		Long taskInstanceId = ParamUtils.getLong(paramMap, "taskInstanceId");
		String actorId = RequestUtils.getActorId(request);
		ProcessContainer container = ProcessContainer.getContainer();
		boolean canSubmit = false;
		TaskItem taskItem = null;
		String processName = null;
		if (taskInstanceId != null && StringUtils.isNotEmpty(actorId)) {
			/**
			 * 根据任务实例编号和用户编号获取待办任务
			 */
			taskItem = container.getTaskItem(taskInstanceId, actorId);
			if (taskItem != null) {
				if (processInstanceId != null) {
					canSubmit = true;
					processInstanceId = taskItem.getProcessInstanceId();
					processName = taskItem.getProcessName();
				}
			}
		} else if (processInstanceId != null && StringUtils.isNotEmpty(actorId)) {
			/**
			 * 根据流程实例编号和用户编号获取待办任务
			 */
			taskItem = container.getMinTaskItem(actorId, processInstanceId);
			if (taskItem != null) {
				canSubmit = true;
				taskInstanceId = taskItem.getTaskInstanceId();
				processName = taskItem.getProcessName();
			}
		}

		/**
		 * 如果存在待办任务并且可以提交审核
		 */
		if (canSubmit && taskInstanceId != null) {
			String routeMode = CustomProperties.getString(processName
					+ "_routeMode");
			logger.debug("routeMode:" + routeMode);
			if (StringUtils.equals(routeMode, "MANUAL")) {
				if (taskInstanceId != null) {
					Collection<String> transitionNames = container
							.getTransitionNames(taskInstanceId);
					request.setAttribute("transitionNames", transitionNames);
				}
			} else if (StringUtils.equals(routeMode, "FREE")) {
				int signal = container.getSignal(taskInstanceId);
				if (signal != TaskNode.SIGNAL_LAST) {
					Collection<String> nodeNames = container
							.getNodeNames(processInstanceId);
					request.setAttribute("nodeNames", nodeNames);
				}
			}
			request.setAttribute("taskItem", taskItem);
		}

		/**
		 * 加载历史审核意见
		 */
		if (processInstanceId != null) {
			List<ActivityInstance> ActivityInstances = container
					.getActivityInstances(processInstanceId);
			request.setAttribute("ActivityInstances", ActivityInstances);
		}

		/**
		 * 如果配置了模板，则根据模板定义进行响应<br>
		 * 模板定义编号规则是“流程名称_take_template”，也可以是全局的jbpm_take_template，
		 * 定义在WEB-INF\conf\extension目录下的任何属性文件均可。
		 */
		String templateId = ParamUtils.getString(paramMap, "z_templateId");

		if (taskItem != null) {
			templateId = CustomProperties.getString(processName + "_"
					+ taskItem.getTaskName() + "_take_template");
		}
		if (StringUtils.isEmpty(templateId)) {
			templateId = CustomProperties.getString(processName
					+ "_take_template");
		}
		if (StringUtils.isEmpty(templateId)) {
			templateId = CustomProperties.getString("jbpm_take_template");
		}
		logger.info("templateId=" + templateId);
		if (StringUtils.isNotEmpty(templateId)) {

			Map<String, Object> context = new java.util.HashMap<String, Object>();
			context.putAll(paramMap);
			context.put("actorId", actorId);
			context.put("contextPath", request.getContextPath());

			context.put("taskItem", taskItem);
			context.put("nodeNames", request.getAttribute("nodeNames"));
			context.put("ActivityInstances",
					request.getAttribute("ActivityInstances"));
			context.put("transitionNames",
					request.getAttribute("transitionNames"));
			try {
				logger.debug(context);
				Writer writer = new StringWriter();
				TemplateUtils.evaluate(templateId, context, writer);
				request.setAttribute("templateScript", writer.toString());
				String encoding = request.getParameter("encoding");
				if (encoding == null) {
					encoding = "UTF-8";
				}
				request.setCharacterEncoding(encoding);
				response.setCharacterEncoding(encoding);
				response.setContentType("text/html;charset=" + encoding);
				response.getWriter().write(writer.toString());
				return null;
			} catch (Exception ex) {
				if (LogUtils.isDebug()) {
					ex.printStackTrace();
					logger.debug(ex);
				}
			}
		}

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view);
		}

		String x_view = ViewProperties.getString("jbpm_take.takeTask");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/jbpm/take");
	}
}