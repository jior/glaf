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

package com.glaf.jbpm.query;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ProcessQuery implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	protected String actorId;

	protected List<String> actorIds;

	protected Long processDefinitionId;

	protected Long processInstanceId;

	protected List<Long> processInstanceIds;

	protected String businessKey;

	protected String processName;

	protected List<String> processNames;

	protected String processType;

	protected String taskType;

	protected String taskName;

	protected List<String> taskNames;

	protected Date beforeProcessStartDate;

	protected Date afterProcessStartDate;

	protected Date beforeProcessEndDate;

	protected Date afterProcessEndDate;

	protected Date beforeTaskCreateDate;

	protected Date afterTaskCreateDate;

	protected Date beforeTaskStartDate;

	protected Date afterTaskStartDate;

	protected Date beforeTaskEndDate;

	protected Date afterTaskEndDate;

	protected String booleanDataFlag = "false";

	public ProcessQuery() {

	}

	public String getActorId() {
		return actorId;
	}

	public List<String> getActorIds() {
		return actorIds;
	}

	public Date getAfterProcessEndDate() {
		return afterProcessEndDate;
	}

	public Date getAfterProcessStartDate() {
		return afterProcessStartDate;
	}

	public Date getAfterTaskCreateDate() {
		return afterTaskCreateDate;
	}

	public Date getAfterTaskEndDate() {
		return afterTaskEndDate;
	}

	public Date getAfterTaskStartDate() {
		return afterTaskStartDate;
	}

	public Date getBeforeProcessEndDate() {
		return beforeProcessEndDate;
	}

	public Date getBeforeProcessStartDate() {
		return beforeProcessStartDate;
	}

	public Date getBeforeTaskCreateDate() {
		return beforeTaskCreateDate;
	}

	public Date getBeforeTaskEndDate() {
		return beforeTaskEndDate;
	}

	public Date getBeforeTaskStartDate() {
		return beforeTaskStartDate;
	}

	public String getBooleanDataFlag() {
		return booleanDataFlag;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public Long getProcessDefinitionId() {
		return processDefinitionId;
	}

	public Long getProcessInstanceId() {
		return processInstanceId;
	}

	public List<Long> getProcessInstanceIds() {
		return processInstanceIds;
	}

	public String getProcessName() {
		return processName;
	}

	public List<String> getProcessNames() {
		return processNames;
	}

	public String getProcessType() {
		return processType;
	}

	public String getTaskName() {
		return taskName;
	}

	public List<String> getTaskNames() {
		return taskNames;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setActorIds(Collection<String> userIds) {
		if (this.actorIds == null) {
			this.actorIds = new java.util.ArrayList<String>();
		}
		for (String actorId : userIds) {
			this.actorIds.add(actorId);
		}
	}

	public void setActorIds(List<String> actorIds) {
		this.actorIds = actorIds;
	}

	public void setAfterProcessEndDate(Date afterProcessEndDate) {
		this.afterProcessEndDate = afterProcessEndDate;
	}

	public void setAfterProcessStartDate(Date afterProcessStartDate) {
		this.afterProcessStartDate = afterProcessStartDate;
	}

	public void setAfterTaskCreateDate(Date afterTaskCreateDate) {
		this.afterTaskCreateDate = afterTaskCreateDate;
	}

	public void setAfterTaskEndDate(Date afterTaskEndDate) {
		this.afterTaskEndDate = afterTaskEndDate;
	}

	public void setAfterTaskStartDate(Date afterTaskStartDate) {
		this.afterTaskStartDate = afterTaskStartDate;
	}

	public void setBeforeProcessEndDate(Date beforeProcessEndDate) {
		this.beforeProcessEndDate = beforeProcessEndDate;
	}

	public void setBeforeProcessStartDate(Date beforeProcessStartDate) {
		this.beforeProcessStartDate = beforeProcessStartDate;
	}

	public void setBeforeTaskCreateDate(Date beforeTaskCreateDate) {
		this.beforeTaskCreateDate = beforeTaskCreateDate;
	}

	public void setBeforeTaskEndDate(Date beforeTaskEndDate) {
		this.beforeTaskEndDate = beforeTaskEndDate;
	}

	public void setBeforeTaskStartDate(Date beforeTaskStartDate) {
		this.beforeTaskStartDate = beforeTaskStartDate;
	}

	public void setBooleanDataFlag(String booleanDataFlag) {
		this.booleanDataFlag = booleanDataFlag;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public void setProcessDefinitionId(Long processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public void setProcessInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public void setProcessInstanceIds(Collection<Long> pIds) {
		if (this.processInstanceIds == null) {
			this.processInstanceIds = new java.util.ArrayList<Long>();
		}
		for (Long id : pIds) {
			this.processInstanceIds.add(id);
		}
	}

	public void setProcessInstanceIds(List<Long> processInstanceIds) {
		this.processInstanceIds = processInstanceIds;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public void setProcessNames(Collection<String> names) {
		if (this.processNames == null) {
			this.processNames = new java.util.ArrayList<String>();
		}
		for (String name : names) {
			this.processNames.add(name);
		}
	}

	public void setProcessNames(List<String> processNames) {
		this.processNames = processNames;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setTaskNames(List<String> taskNames) {
		this.taskNames = taskNames;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

}