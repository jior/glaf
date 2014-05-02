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

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ProcessContext implements Serializable {
	private static final long serialVersionUID = 1L;

	private String actorId;

	private String processName;

	private String processInstanceId;

	private String taskId;

	protected String outcome;

	protected String jumpToNode;

	private String businessKey;

	private String title;

	private String opinion;

	private Map<String, Object> variables = new java.util.HashMap<String, Object>();

	private Collection<String> agentIds = new java.util.ArrayList<String>();

	private Collection<DataField> dataFields = new java.util.ArrayList<DataField>();

	public ProcessContext() {

	}

	public void addDataField(DataField dataField) {
		if (dataFields == null) {
			dataFields = new java.util.ArrayList<DataField>();
		}
		dataFields.add(dataField);
	}

	public String getActorId() {
		return actorId;
	}

	public Collection<String> getAgentIds() {
		return agentIds;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public Collection<DataField> getDataFields() {
		if (dataFields == null) {
			dataFields = new java.util.ArrayList<DataField>();
		}
		return dataFields;
	}

	public String getJumpToNode() {
		return jumpToNode;
	}

	public String getOpinion() {
		return opinion;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public String getProcessName() {
		return processName;
	}

	public String getTaskId() {
		return taskId;
	}

	public String getTitle() {
		return title;
	}

	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setAgentIds(Collection<String> agentIds) {
		this.agentIds = agentIds;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public void setDataFields(Collection<DataField> dataFields) {
		this.dataFields = dataFields;
	}

	public void setJumpToNode(String jumpToNode) {
		this.jumpToNode = jumpToNode;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}