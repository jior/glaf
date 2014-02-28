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
import com.glaf.core.freemarker.TemplateUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.LogUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.ProcessContext;
import com.glaf.jbpm.datafield.DataField;

@Controller("/jbpm/start")
@RequestMapping("/jbpm/start")
public class MxJbpmStartProcessController {
	protected final static Log logger = LogFactory
			.getLog(MxJbpmStartProcessController.class);

	public MxJbpmStartProcessController() {

	}

	@RequestMapping
	public ModelAndView startProcess(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		logger.debug(paramMap);
		String json = RequestUtils.getStringValue(request, "json");
		String rowId = RequestUtils.getStringValue(request, "rowId");
		String processName = RequestUtils
				.getStringValue(request, "processName");
		String actorId = RequestUtils.getActorId(request);
		ProcessContainer container = ProcessContainer.getContainer();
		Long processInstanceId = null;

		if (StringUtils.isNotEmpty(rowId)
				&& StringUtils.isNotEmpty(processName)
				&& StringUtils.isNotEmpty(actorId)) {
			ProcessContext ctx = new ProcessContext();
			ctx.setRowId(rowId);
			ctx.setActorId(actorId);
			ctx.setTitle("���ݱ�ţ�" + rowId);
			ctx.setProcessName(processName);

			/**
			 * ���̿��Ʋ���Ϊjson��ʽ���ַ���.<br>
			 * ���磺{money:100000,day:5,pass:true,deptId:"123", roleId:"R001"}
			 */
			if (StringUtils.isNotEmpty(json)) {
				Map<String, Object> jsonMap = JsonUtils.decode(json);
				if (jsonMap != null && jsonMap.size() > 0) {
					Set<Entry<String, Object>> entrySet = jsonMap.entrySet();
					for (Entry<String, Object> entry : entrySet) {
						String name = entry.getKey();
						Object value = entry.getValue();
						if (name != null && value != null) {
							if (name != null && value != null) {
								DataField dataField = new DataField();
								dataField.setName(name);
								dataField.setValue(value);
								ctx.addDataField(dataField);
							}
						}
					}
				}
			}
			try {
				processInstanceId = container.startProcess(ctx);
				if (processInstanceId != null) {
					request.setAttribute("processInstanceId", processInstanceId);
					request.setAttribute("statusCode", 200);
					request.setAttribute("message",
							ViewProperties.getString("res_submit_ok"));
				} else {
					request.setAttribute("statusCode", 500);
					request.setAttribute("message",
							ViewProperties.getString("res_submit_error"));
				}

				logger.debug("processInstanceId:" + processInstanceId);
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

		/**
		 * �����Ӧ����������Ϊjson��xml����ô���ݴ�����������Ӧ��<br>
		 * 200-�ɹ� <br>
		 * 500-ʧ��<br>
		 */
		String responseDataType = RequestUtils.getStringValue(request,
				"responseDataType");
		if (StringUtils.equals(responseDataType, "json")) {
			String encoding = request.getParameter("encoding");
			if (encoding == null) {
				encoding = "UTF-8";
			}
			request.setCharacterEncoding(encoding);
			response.setCharacterEncoding(encoding);
			response.setContentType("text/plain;charset=" + encoding);
			Map<String, Object> jsonMap = new java.util.concurrent.ConcurrentHashMap<String, Object>();
			if (processInstanceId != null) {
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
			response.getWriter().write(object.toString());
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
			if (processInstanceId != null) {
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
			response.getWriter().write(buffer.toString());
			return null;
		}

		/**
		 * ���������ģ�壬�����ģ�嶨�������Ӧ<br>
		 * ģ�嶨���Ź����ǡ���������_start_template����Ҳ������ȫ�ֵ�jbpm_start_template��
		 * ������WEB-INF\conf\extensionĿ¼�µ��κ������ļ����ɡ�
		 */
		String templateId = RequestUtils
				.getStringValue(request, "x_templateId");

		if (StringUtils.isEmpty(templateId)) {
			templateId = CustomProperties.getString(processName
					+ "_start_template");
		}
		if (StringUtils.isEmpty(templateId)) {
			templateId = CustomProperties.getString("jbpm_start_template");
		}
		if (StringUtils.isNotEmpty(templateId)) {
			 
			Map<String, Object> context = new java.util.concurrent.ConcurrentHashMap<String, Object>();
			context.putAll(paramMap);
			context.put("actorId", actorId);
			context.put("processInstanceId", processInstanceId);
			context.put("contextPath", request.getContextPath());
			 
			 
			if (processInstanceId != null) {
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

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("jbpm_start.startProcess");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/jbpm/start");
	}

}