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

import java.util.List;

import org.activiti.engine.repository.ProcessDefinition;

public class ProcessDefinitionInfo {

	ProcessDefinition processDefinition;

	List<ActivityInfo> activities;

	List<UserTask> userTasks;

	public ProcessDefinitionInfo() {

	}

	public List<ActivityInfo> getActivities() {
		return activities;
	}

	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}

	public List<UserTask> getUserTasks() {
		return userTasks;
	}

	public void setActivities(List<ActivityInfo> activities) {
		this.activities = activities;
	}

	public void setProcessDefinition(ProcessDefinition processDefinition) {
		this.processDefinition = processDefinition;
	}

	public void setUserTasks(List<UserTask> userTasks) {
		this.userTasks = userTasks;
	}

}