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

package com.glaf.jbpm.model;

import java.util.Collection;
import java.util.List;

public class TaskInfo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	protected String actorId;

	protected String processInstanceId;

	protected String taskInstanceId;

	protected TaskItem taskItem;

	protected Collection<String> nodeNames;

	protected Collection<String> transitionNames;

	protected List<ActivityInstance> ActivityInstances;

	public TaskInfo() {

	}

	public String getActorId() {
		return actorId;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getTaskInstanceId() {
		return taskInstanceId;
	}

	public void setTaskInstanceId(String taskInstanceId) {
		this.taskInstanceId = taskInstanceId;
	}

	public TaskItem getTaskItem() {
		return taskItem;
	}

	public void setTaskItem(TaskItem taskItem) {
		this.taskItem = taskItem;
	}

	public Collection<String> getNodeNames() {
		return nodeNames;
	}

	public void setNodeNames(Collection<String> nodeNames) {
		this.nodeNames = nodeNames;
	}

	public Collection<String> getTransitionNames() {
		return transitionNames;
	}

	public void setTransitionNames(Collection<String> transitionNames) {
		this.transitionNames = transitionNames;
	}

	public List<ActivityInstance> getActivityInstances() {
		return ActivityInstances;
	}

	public void setActivityInstances(List<ActivityInstance> ActivityInstances) {
		this.ActivityInstances = ActivityInstances;
	}

}