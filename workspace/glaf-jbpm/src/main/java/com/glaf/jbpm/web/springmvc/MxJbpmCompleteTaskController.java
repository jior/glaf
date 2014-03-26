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
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.CustomProperties;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.LogUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.template.util.TemplateUtils;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.ProcessContext;
import com.glaf.jbpm.datafield.DataField;
import com.glaf.jbpm.model.TaskItem;

@Controller("/jbpm/complete")
@RequestMapping("/jbpm/complete")
public class MxJbpmCompleteTaskController {
	protected final static Log logger = LogFactory
			.getLog(MxJbpmCompleteTaskController.class);

	public MxJbpmCompleteTaskController() {

	}

	@RequestMapping
	public ModelAndView completeTask(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		logger.debug(paramMap);
		String json = RequestUtils.getStringValue(request, "json");
		Long processInstanceId = ParamUtils.getLong(paramMap,
				"processInstanceId");
		Long taskInstanceId = ParamUtils.getLong(paramMap, "taskInstanceId");
		String transitionName = RequestUtils.getStringValue(request,
				"transitionName");
		String jumpToNode = RequestUtils.getStringValue(request, "jumpToNode");
		String isAgree = RequestUtils.getStringValue(request, "isAgree");
		String opinion = RequestUtils.getStringValue(request, "opinion");
		logger.debug("json:" + json);
		logger.debug("processInstanceId:" + processInstanceId);
		logger.debug("taskInstanceId:" + taskInstanceId);
		logger.debug("transitionName:" + transitionName);
		logger.debug("jumpToNode:" + jumpToNode);
		logger.debug("isAgree:" + isAgree);
		logger.debug("opinion:" + opinion);
		String actorId = RequestUtils.getActorId(request);
		ProcessContainer container = ProcessContainer.getContainer();
		String processName = null;
		boolean canSubmit = false;
		boolean isOK = false;
		TaskItem taskItem = null;
		if ("0".equals(isAgree)) {
			isAgree = "false";
		} else if ("1".equals(isAgree)) {
			isAgree = "true";
		}

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
				processName = taskItem.getProcessName();
			}
		}

		/**
		 * 如果存在待办任务并且可以提交审核
		 */
		if (canSubmit && taskItem != null) {
			ProcessContext ctx = new ProcessContext();
			/**
			 * 流程控制参数为json格式的字符串.<br>
			 * 例如：{money:100000,day:5,pass:true,deptId:"123", roleId:"R001"}
			 */
			if (StringUtils.isNotEmpty(json)) {
				Map<String, Object> jsonMap = JsonUtils.decode(json);
				if (jsonMap != null && jsonMap.size() > 0) {
					Set<Entry<String, Object>> entrySet = jsonMap.entrySet();
					for (Entry<String, Object> entry : entrySet) {
						String name = entry.getKey();
						Object value = entry.getValue();
						if (name != null && value != null) {
							DataField dataField = new DataField();
							dataField.setName(name);
							dataField.setValue(value);
							ctx.addDataField(dataField);
						}
					}
				}
			}

			DataField dataField = new DataField();
			dataField.setName("isAgree");
			dataField.setValue(isAgree);
			ctx.addDataField(dataField);

			ctx.setActorId(actorId);
			ctx.setOpinion(opinion);
			ctx.setProcessInstanceId(taskItem.getProcessInstanceId());
			ctx.setTaskInstanceId(taskItem.getTaskInstanceId());
			ctx.setJumpToNode(jumpToNode);
			ctx.setTransitionName(transitionName);

			try {
				isOK = container.completeTask(ctx);
				request.setAttribute("isOK", Boolean.valueOf(isOK));
				if (isOK) {
					request.setAttribute("statusCode", 200);
					request.setAttribute("message",
							ViewProperties.getString("res_submit_ok"));
				} else {
					request.setAttribute("statusCode", 500);
					request.setAttribute("message",
							ViewProperties.getString("res_submit_error"));
				}
			} catch (Exception ex) {
				request.setAttribute("statusCode", 500);
				request.setAttribute("message",
						ViewProperties.getString("res_submit_error"));
				if (LogUtils.isDebug()) {
					ex.printStackTrace();
					logger.debug(ex);
				}
			}
		}

		String responseDataType = RequestUtils.getStringValue(request,
				"responseDataType");
		/**
		 * 如果响应的数据类型为json或xml，那么根据处理结果进行响应。<br>
		 * 200-成功 <br>
		 * 500-失败<br>
		 */
		if (StringUtils.equals(responseDataType, "json")) {
			String encoding = request.getParameter("encoding");
			if (encoding == null) {
				encoding = "UTF-8";
			}
			request.setCharacterEncoding(encoding);
			response.setCharacterEncoding(encoding);
			response.setContentType("text/plain;charset=" + encoding);
			Map<String, Object> jsonMap = new java.util.concurrent.ConcurrentHashMap<String, Object>();
			if (isOK) {
				jsonMap.put("statusCode", 200);
				jsonMap.put("success", "true");
				jsonMap.put("data",
						ViewProperties.getString("res_submit_ok"));
				jsonMap.put("message",
						ViewProperties.getString("res_submit_ok"));
			} else {
				jsonMap.put("statusCode", 500);
				jsonMap.put("success", "false");
				jsonMap.put("data",
						ViewProperties.getString("res_submit_error"));
				jsonMap.put("message",
						ViewProperties.getString("res_submit_error"));
			}
			JSONObject object = new JSONObject(jsonMap);
			Writer writer = response.getWriter();
			writer.write(object.toString());
			writer.flush();
			writer.close();
			writer = null;
			return null;
		} else if (StringUtils.equals(responseDataType, "xml")) {
			String encoding = request.getParameter("encoding");
			if (encoding == null) {
				encoding = "UTF-8";
			}
			request.setCharacterEncoding(encoding);
			response.setCharacterEncoding(encoding);
			response.setContentType("text/xml;charset=" + encoding);
			StringBuffer buffer = new StringBuffer();
			buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			buffer.append("<response>");
			if (isOK) {
				buffer.append("\n    <statusCode>200</statusCode>");
				buffer.append("\n    <message>")
						.append(ViewProperties.getString("res_submit_ok"))
						.append("</message>");
			} else {
				buffer.append("\n    <statusCode>500</statusCode>");
				buffer.append("\n    <message>")
						.append(ViewProperties.getString("res_submit_error"))
						.append("</message>");
			}
			buffer.append("\n</response>");
			Writer writer = response.getWriter();
			writer.write(buffer.toString());
			writer.flush();
			writer.close();
			writer = null;
			return null;
		}

		/**
		 * 如果配置了模板，则根据模板定义进行响应<br>
		 * 模板定义编号规则是“流程名称_complete_template”，也可以是全局的jbpm_complete_template，
		 * 定义在WEB-INF\conf\extension目录下的任何属性文件均可。
		 */
		String templateId = RequestUtils
				.getStringValue(request, "y_templateId");
		if (StringUtils.isEmpty(templateId)) {
			templateId = CustomProperties.getString(processName
					+ "_complete_template");
		}
		if (StringUtils.isEmpty(templateId)) {
			templateId = CustomProperties.getString("jbpm_complete_template");
		}
		if (StringUtils.isNotEmpty(templateId)) {
			 
			Map<String, Object> context = new java.util.concurrent.ConcurrentHashMap<String, Object>();
			context.putAll(paramMap);
			context.put("actorId", actorId);
			context.put("canSubmit", canSubmit);
			context.put("isOK", isOK);
			context.put("taskItem", taskItem);
			context.put("processInstanceId", processInstanceId);
			context.put("contextPath", request.getContextPath());
			 
			if (isOK) {
				context.put("statusCode", 200);
				context.put("message",
						ViewProperties.getString("res_submit_ok"));
			} else {
				context.put("statusCode", 500);
				context.put("message",
						ViewProperties.getString("res_submit_error"));
			}
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

		String x_view = ViewProperties.getString("jbpm_complete.complateTask");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/jbpm/complete");
	}

}