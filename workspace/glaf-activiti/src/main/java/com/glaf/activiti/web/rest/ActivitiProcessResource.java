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

package com.glaf.activiti.web.rest;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glaf.activiti.model.ActivityCoordinates;
import com.glaf.activiti.model.ActivityInfo;
import com.glaf.activiti.model.ProcessInstanceInfo;
import com.glaf.activiti.service.ActivitiDeployQueryService;
import com.glaf.activiti.service.ActivitiProcessQueryService;
import com.glaf.activiti.service.ActivitiProcessService;
import com.glaf.activiti.service.ActivitiTaskQueryService;
import com.glaf.activiti.util.ProcessUtils;
import com.glaf.core.config.CustomProperties;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.QueryUtils;

@Controller("/rs/activiti/process")
@Path("/rs/activiti/process")
public class ActivitiProcessResource {
	private static ConcurrentMap<String, Object> cache = new ConcurrentHashMap<String, Object>();

	protected static final Log logger = LogFactory
			.getLog(ActivitiProcessResource.class);

	protected ActivitiDeployQueryService activitiDeployQueryService;

	protected ActivitiProcessQueryService activitiProcessQueryService;

	protected ActivitiProcessService activitiProcessService;

	protected ActivitiTaskQueryService activitiTaskQueryService;

	@GET
	@Path("image/{processInstanceId}")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	@ResponseBody
	public byte[] image(@PathParam("processInstanceId") String processInstanceId) {
		ProcessDefinition processDefinition = null;
		if (StringUtils.isNotEmpty(processInstanceId)) {
			processDefinition = activitiProcessQueryService
					.getProcessDefinitionByProcessInstanceId(processInstanceId);
		}
		if (processDefinition != null) {
			byte[] bytes = null;
			try {
				bytes = ProcessUtils.getImage(processDefinition.getId());
			} catch (Exception e) {
			}
			return bytes;
		}

		return null;
	}

	@javax.annotation.Resource
	public void setActivitiDeployQueryService(
			ActivitiDeployQueryService activitiDeployQueryService) {
		this.activitiDeployQueryService = activitiDeployQueryService;
	}

	@javax.annotation.Resource
	public void setActivitiProcessQueryService(
			ActivitiProcessQueryService activitiProcessQueryService) {
		this.activitiProcessQueryService = activitiProcessQueryService;
	}

	@javax.annotation.Resource
	public void setActivitiProcessService(
			ActivitiProcessService activitiProcessService) {
		this.activitiProcessService = activitiProcessService;
	}

	@javax.annotation.Resource
	public void setActivitiTaskQueryService(
			ActivitiTaskQueryService activitiTaskQueryService) {
		this.activitiTaskQueryService = activitiTaskQueryService;
	}

	@GET
	@Path("view/{processInstanceId}")
	@Produces({ MediaType.TEXT_HTML })
	@ResponseBody
	@Transactional(readOnly = true)
	public byte[] view(@Context HttpServletRequest request,
			@PathParam("processInstanceId") String processInstanceId) {
		String contextPath = request.getContextPath();
		StringBuffer buffer = new StringBuffer();
		if (StringUtils.isNotEmpty(processInstanceId)) {
			ProcessInstanceInfo processInstanceInfo = activitiProcessQueryService
					.getProcessInstanceInfo(processInstanceId);
			if (processInstanceInfo != null) {

				StringBuffer positionBuffer = new StringBuffer();
				StringBuffer position = new StringBuffer();

				StringBuffer text = new StringBuffer();

				int id = 0;
				List<ActivityInfo> processedActivityInfos = processInstanceInfo
						.getProcessedActivityInfos();
				List<ActivityInfo> activeActivityInfos = processInstanceInfo
						.getActiveActivityInfos();
				ActivityCoordinates coordinates = null;
				HistoricActivityInstance activityInstance = null;
				if (processedActivityInfos != null
						&& !processedActivityInfos.isEmpty()) {
					for (ActivityInfo info : processedActivityInfos) {
						coordinates = info.getCoordinates();
						activityInstance = info.getActivityInstance();
						if (activityInstance == null) {
							continue;
						}
						position.delete(0, position.length());
						text.delete(0, text.length());

						String elId = "_pai_" + (++id);
						String title = activityInstance.getActivityName();

						position.append("left:").append(coordinates.getX() - 2)
								.append("px;");
						position.append("top:").append(coordinates.getY() - 2)
								.append("px;");
						position.append("height:")
								.append(coordinates.getHeight() - 2)
								.append("px;");
						position.append("width:")
								.append(coordinates.getWidth() - 2)
								.append("px;");

						buffer.append(
								"\n        <div class=\"tip processed\" id=\"")
								.append(elId).append("\" style=\"")
								.append(position).append("\"></div>");

						Date startDate = activityInstance.getStartTime();
						Date endDate = activityInstance.getEndTime();
						buffer.append("\n        <script>$('").append(elId)
								.append("').store('tip:title', '")
								.append(title).append('\'')
								.append(").store('tip:text', '")
								.append("<br/><b>开始时间:</b> ")
								.append(DateUtils.getDateTime(startDate))
								.append("<br/><b>结束时间:</b> ")
								.append(DateUtils.getDateTime(endDate));
						if (activityInstance.getAssignee() != null) {
							buffer.append("<br/><b>执行人:</b> ").append(
									activityInstance.getAssignee());
						}
						buffer.append("');</script>");
					}
				}

				if (activeActivityInfos != null
						&& !activeActivityInfos.isEmpty()) {
					id = 0;
					for (ActivityInfo activityInfo : activeActivityInfos) {
						coordinates = activityInfo.getCoordinates();
						String elId = "_aai_" + (++id);
						positionBuffer.delete(0, positionBuffer.length());
						positionBuffer.append("left:")
								.append(coordinates.getX() - 2).append("px;");
						positionBuffer.append("top:")
								.append(coordinates.getY() - 2).append("px;");
						positionBuffer.append("height:")
								.append(coordinates.getHeight() - 2)
								.append("px;");
						positionBuffer.append("width:")
								.append(coordinates.getWidth() - 2)
								.append("px;");
						buffer.append(
								"\n        <div class=\"tip active\" id=\"")
								.append(elId).append("\" style=\"")
								.append(positionBuffer.toString())
								.append("\"></div>");
						activityInstance = activityInfo.getActivityInstance();
						if (activityInstance != null) {
							String title = activityInstance.getActivityName();
							Date startDate = activityInstance.getStartTime();
							buffer.append("\n        <script>$('").append(elId)
									.append("').store('tip:title', '")
									.append(title).append('\'')
									.append(").store('tip:text', '")
									.append("<br/><b>开始时间:</b> ")
									.append(DateUtils.getDateTime(startDate));
							if (activityInstance.getAssignee() != null) {
								buffer.append("<br/><b>执行人:</b> ").append(
										activityInstance.getAssignee());
							}
							buffer.append("');</script>");
						}
					}
				}
			}
		}

		String view = CustomProperties.getString("activiti.rs.view");
		if (StringUtils.isEmpty(view)) {
			view = "com/glaf/activiti/web/rest/view.ftl";
		}

		String content = null;

		if (cache.get(view) != null) {
			content = (String) cache.get(view);
		} else {
			try {
				Resource resouce = new ClassPathResource(view);
				content = FileUtils.readFile(resouce.getInputStream());
				cache.put(view, content);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Map<String, Object> context = new java.util.HashMap<String, Object>();
		context.put("contextPath", contextPath);
		context.put("x_script", buffer.toString());
		context.put("processInstanceId", processInstanceId);
		content = QueryUtils.replaceTextParas(content, context);

		try {
			return content.getBytes("UTF-8");
		} catch (IOException e) {
			return content.getBytes();
		}
	}

}