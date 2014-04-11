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

package com.glaf.activiti.web.springmvc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLinkType;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glaf.activiti.service.ActivitiProcessQueryService;
import com.glaf.activiti.service.ActivitiProcessService;
import com.glaf.activiti.service.ActivitiTaskQueryService;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;

@Controller("/activiti/monitor")
@RequestMapping("/activiti/monitor")
public class ActivitiMonitorController {

	protected ActivitiProcessService activitiProcessService;

	protected ActivitiProcessQueryService activitiProcessQueryService;

	protected ActivitiTaskQueryService activitiTaskQueryService;

	@RequestMapping("/addGroupIdentityLink")
	@ResponseBody
	public byte[] addGroupIdentityLink(HttpServletRequest request) {
		String taskId = request.getParameter("taskId");
		String groupIds = request.getParameter("groupIds");
		String identityLinkType = request.getParameter("identityLinkType");
		if (StringUtils.isEmpty(identityLinkType)) {
			identityLinkType = IdentityLinkType.CANDIDATE;
		}
		boolean success = false;
		if (StringUtils.isNotEmpty(groupIds) && StringUtils.isNotEmpty(taskId)) {
			Task task = activitiTaskQueryService.getTask(taskId);
			List<String> groups = StringTools.split(groupIds);
			if (task != null && groups != null && groups.size() > 0) {
				activitiProcessService.addGroupIdentityLink(taskId, groupIds,
						identityLinkType);
				success = true;
			}
		}
		return ResponseUtils.responseResult(success);
	}

	@RequestMapping("/addUserIdentityLink")
	@ResponseBody
	public byte[] addUserIdentityLink(HttpServletRequest request) {
		String taskId = request.getParameter("taskId");
		String actorIds = request.getParameter("actorIds");
		String identityLinkType = request.getParameter("identityLinkType");
		if (StringUtils.isEmpty(identityLinkType)) {
			identityLinkType = IdentityLinkType.CANDIDATE;
		}
		boolean success = false;
		if (StringUtils.isNotEmpty(actorIds) && StringUtils.isNotEmpty(taskId)) {
			Task task = activitiTaskQueryService.getTask(taskId);
			List<String> actors = StringTools.split(actorIds);
			if (task != null && actors != null && actors.size() > 0) {
				activitiProcessService.addUserIdentityLink(taskId, actorIds,
						identityLinkType);
				success = true;
			}
		}
		return ResponseUtils.responseResult(success);
	}

	@RequestMapping("/addVisitTasks")
	@ResponseBody
	public byte[] addVisitTasks(HttpServletRequest request) {
		String taskId = request.getParameter("taskId");
		String actorIds = request.getParameter("actorIds");
		boolean success = false;
		if (StringUtils.isNotEmpty(actorIds) && StringUtils.isNotEmpty(taskId)) {
			Task task = activitiTaskQueryService.getTask(taskId);
			List<String> actors = StringTools.split(actorIds);
			if (task != null && actors != null && actors.size() > 0) {

				success = true;
			}
		}
		return ResponseUtils.responseResult(success);
	}

	@RequestMapping("/claimTask")
	@ResponseBody
	public byte[] claimTask(HttpServletRequest request) {
		String taskId = request.getParameter("taskId");
		String actorId = request.getParameter("actorId");
		boolean success = false;
		if (StringUtils.isNotEmpty(actorId) && StringUtils.isNotEmpty(taskId)) {
			Task task = activitiTaskQueryService.getTask(taskId);
			if (task != null) {
				activitiProcessService.claimTask(taskId, actorId);
				success = true;
			}
		}
		return ResponseUtils.responseResult(success);
	}

	@RequestMapping("/resume")
	@ResponseBody
	public byte[] resume(HttpServletRequest request) {
		String processInstanceId = request.getParameter("processInstanceId");
		if (StringUtils.isNotEmpty(processInstanceId)) {
			ProcessInstance processInstance = activitiProcessQueryService
					.getProcessInstance(processInstanceId);
			if (processInstance != null) {

			}
		}
		return null;
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

	@RequestMapping("/suspend")
	@ResponseBody
	public byte[] suspend(HttpServletRequest request) {
		String processInstanceId = request.getParameter("processInstanceId");
		if (StringUtils.isNotEmpty(processInstanceId)) {
			ProcessInstance processInstance = activitiProcessQueryService
					.getProcessInstance(processInstanceId);
			if (processInstance != null) {

			}
		}
		return null;
	}

}