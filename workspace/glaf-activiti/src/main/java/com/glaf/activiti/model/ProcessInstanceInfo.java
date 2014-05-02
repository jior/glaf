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

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

public class ProcessInstanceInfo {
	private ProcessDefinition processDefinition;
	private ProcessInstance processInstance;
	private HistoricProcessInstance historicProcessInstance;
	private List<ActivityInfo> activeActivityInfos;
	private List<ActivityInfo> processedActivityInfos;

	public ProcessInstanceInfo() {

	}

	public void addActiveActivityInfo(ActivityInfo activityInfo) {
		if (activeActivityInfos == null) {
			activeActivityInfos = new java.util.ArrayList<ActivityInfo>();
		}
		activeActivityInfos.add(activityInfo);
	}

	public void addProcessedActivityInfo(ActivityInfo activityInfo) {
		if (processedActivityInfos == null) {
			processedActivityInfos = new java.util.ArrayList<ActivityInfo>();
		}
		processedActivityInfos.add(activityInfo);
	}

	public ProcessInstanceInfo(List<ActivityInfo> activeActivityInfos,
			List<ActivityInfo> processedActivityInfos) {
		this.activeActivityInfos = activeActivityInfos;
		this.processedActivityInfos = processedActivityInfos;
	}

	public List<ActivityInfo> getActiveActivityInfos() {
		return activeActivityInfos;
	}

	public HistoricProcessInstance getHistoricProcessInstance() {
		return historicProcessInstance;
	}

	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}

	public List<ActivityInfo> getProcessedActivityInfos() {
		return processedActivityInfos;
	}

	public ProcessInstance getProcessInstance() {
		return processInstance;
	}

	public void setActiveActivityInfos(List<ActivityInfo> activeActivityInfos) {
		this.activeActivityInfos = activeActivityInfos;
	}

	public void setHistoricProcessInstance(
			HistoricProcessInstance historicProcessInstance) {
		this.historicProcessInstance = historicProcessInstance;
	}

	public void setProcessDefinition(ProcessDefinition processDefinition) {
		this.processDefinition = processDefinition;
	}

	public void setProcessedActivityInfos(
			List<ActivityInfo> processedActivityInfos) {
		this.processedActivityInfos = processedActivityInfos;
	}

	public void setProcessInstance(ProcessInstance processInstance) {
		this.processInstance = processInstance;
	}

}