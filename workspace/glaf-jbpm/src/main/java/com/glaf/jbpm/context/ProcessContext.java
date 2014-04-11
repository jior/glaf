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

package com.glaf.jbpm.context;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.jbpm.JbpmContext;

import com.glaf.jbpm.datafield.DataField;

public class ProcessContext implements Serializable {
	private static final long serialVersionUID = 1L;

	private String actorId;

	private String processName;

	private Long processInstanceId;

	private Long taskInstanceId;

	protected String transitionName;

	protected String jumpToNode;

	private Object rowId;

	private String title;

	private String opinion;

	private JbpmContext jbpmContext;

	private Map<String, Object> contextMap = new java.util.concurrent.ConcurrentHashMap<String, Object>();

	private Collection<String> agentIds = new java.util.concurrent.CopyOnWriteArrayList<String>();

	private Collection<DataField> dataFields = new java.util.concurrent.CopyOnWriteArrayList<DataField>();

	public ProcessContext() {

	}

	public void addDataField(DataField dataField) {
		if (dataFields == null) {
			dataFields = new java.util.concurrent.CopyOnWriteArrayList<DataField>();
		}
		dataFields.add(dataField);
	}

	public String getActorId() {
		return actorId;
	}

	public Collection<String> getAgentIds() {
		return agentIds;
	}

	public Map<String, Object> getContextMap() {
		return contextMap;
	}

	public Collection<DataField> getDataFields() {
		if (dataFields == null) {
			dataFields = new java.util.concurrent.CopyOnWriteArrayList<DataField>();
		}
		return dataFields;
	}

	public JbpmContext getJbpmContext() {
		return jbpmContext;
	}

	public String getJumpToNode() {
		return jumpToNode;
	}

	public String getOpinion() {
		return opinion;
	}

	public Long getProcessInstanceId() {
		return processInstanceId;
	}

	public String getProcessName() {
		return processName;
	}

	public Object getRowId() {
		return rowId;
	}

	public Long getTaskInstanceId() {
		return taskInstanceId;
	}

	public String getTitle() {
		return title;
	}

	public String getTransitionName() {
		return transitionName;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setAgentIds(Collection<String> agentIds) {
		this.agentIds = agentIds;
	}

	public void setContextMap(Map<String, ?> map) {
		if (map != null) {
			this.contextMap.putAll(map);
		}
	}

	public void setDataFields(Collection<DataField> dataFields) {
		this.dataFields = dataFields;
	}

	public void setJbpmContext(JbpmContext jbpmContext) {
		this.jbpmContext = jbpmContext;
	}

	public void setJumpToNode(String jumpToNode) {
		this.jumpToNode = jumpToNode;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public void setProcessInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public void setRowId(Object rowId) {
		this.rowId = rowId;
	}

	public void setTaskInstanceId(Long taskInstanceId) {
		this.taskInstanceId = taskInstanceId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTransitionName(String transitionName) {
		this.transitionName = transitionName;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}