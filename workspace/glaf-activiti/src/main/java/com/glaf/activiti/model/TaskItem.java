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

package com.glaf.activiti.model;

import java.util.Date;

public class TaskItem implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	protected String id;
	protected String actorId;
	protected String actorName;
	protected String businessKey;
	protected String owner;
	protected String groupId;
	protected String groupName;
	protected String executionId;
	protected String processName;
	protected String processDescription;
	protected String processDefinitionId;
	protected String processInstanceId;
	protected String parentTaskId;
	protected String taskName;
	protected String taskInstanceId;
	protected String taskDescription;
	protected String taskDefinitionKey;
	protected String activityName;
	protected String activityType;
	protected String formResourceName;
	protected String identityLinkType;
	protected String state;
	protected int priority;
	protected int progress;
	protected Date createTime;
	protected Date startTime;
	protected Date endTime;
	protected Date duedate;
	protected long duration;
	protected String outcome;

	public TaskItem() {

	}

	public String getActivityName() {
		return activityName;
	}

	public String getActivityType() {
		return activityType;
	}

	public String getActorId() {
		return actorId;
	}

	public String getActorName() {
		return actorName;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Date getDuedate() {
		return duedate;
	}

	public long getDuration() {
		return duration;
	}

	public Date getEndTime() {
		return endTime;
	}

	public String getExecutionId() {
		return executionId;
	}

	public String getFormResourceName() {
		return formResourceName;
	}

	public String getGroupId() {
		return groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public String getId() {
		return id;
	}

	public String getIdentityLinkType() {
		return identityLinkType;
	}

	public String getOutcome() {
		return outcome;
	}

	public String getOwner() {
		return owner;
	}

	public String getParentTaskId() {
		return parentTaskId;
	}

	public int getPriority() {
		return priority;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public String getProcessDescription() {
		return processDescription;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public String getProcessName() {
		return processName;
	}

	public int getProgress() {
		return progress;
	}

	public Date getStartTime() {
		return startTime;
	}

	public String getState() {
		return state;
	}

	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public String getTaskInstanceId() {
		return taskInstanceId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public void setFormResourceName(String formResourceName) {
		this.formResourceName = formResourceName;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIdentityLinkType(String identityLinkType) {
		this.identityLinkType = identityLinkType;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setParentTaskId(String parentTaskId) {
		this.parentTaskId = parentTaskId;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public void setProcessDescription(String processDescription) {
		this.processDescription = processDescription;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public void setTaskInstanceId(String taskInstanceId) {
		this.taskInstanceId = taskInstanceId;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

}