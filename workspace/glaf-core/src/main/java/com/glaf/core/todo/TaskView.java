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

package com.glaf.core.todo;

import java.util.Date;

public class TaskView implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	protected String actorId;
	protected String rowId;
	protected String processName;
	protected Long processInstanceId;
	protected String taskName;
	protected Long taskInstanceId;
	protected String taskDescription;
	protected Date createDate;
	protected Date endDate;
	protected Date limitDate;
	protected int light;
	protected int status;

	public TaskView() {

	}

	public String getActorId() {
		return actorId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public int getLight() {
		return light;
	}

	public Date getLimitDate() {
		return limitDate;
	}

	public Long getProcessInstanceId() {
		return processInstanceId;
	}

	public String getProcessName() {
		return processName;
	}

	public String getRowId() {
		return rowId;
	}

	public int getStatus() {
		return status;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public Long getTaskInstanceId() {
		return taskInstanceId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setLight(int light) {
		this.light = light;
	}

	public void setLimitDate(Date limitDate) {
		this.limitDate = limitDate;
	}

	public void setProcessInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public void setTaskInstanceId(Long taskInstanceId) {
		this.taskInstanceId = taskInstanceId;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

}