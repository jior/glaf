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


package org.jpage.jbpm.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class StateInstance implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String stateInstanceId;

	protected String businessId;

	protected String processName;

	protected String processInstanceId;

	protected String taskId;

	protected String taskName;

	protected String taskDescription;

	protected String taskInstanceId;

	protected String tokenInstanceId;

	protected String actorId;

	protected String agentId;

	protected String previousActorIds;

	protected String objectId;

	protected String objectValue;

	protected int objectType;

	protected int status;

	protected Date startDate;

	protected int opinion = -1;

	protected String title;

	protected String content;

	protected String dataFile;

	protected long versionNo;

	protected Map valueMap = new HashMap();

	public StateInstance() {

	}

	public String getActorId() {
		return actorId;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDataFile() {
		return dataFile;
	}

	public void setDataFile(String dataFile) {
		this.dataFile = dataFile;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public int getObjectType() {
		return objectType;
	}

	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	public int getOpinion() {
		return opinion;
	}

	public void setOpinion(int opinion) {
		this.opinion = opinion;
	}

	public String getPreviousActorIds() {
		return previousActorIds;
	}

	public void setPreviousActorIds(String previousActorIds) {
		this.previousActorIds = previousActorIds;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getStateInstanceId() {
		return stateInstanceId;
	}

	public void setStateInstanceId(String stateInstanceId) {
		this.stateInstanceId = stateInstanceId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public String getTaskInstanceId() {
		return taskInstanceId;
	}

	public void setTaskInstanceId(String taskInstanceId) {
		this.taskInstanceId = taskInstanceId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTokenInstanceId() {
		return tokenInstanceId;
	}

	public void setTokenInstanceId(String tokenInstanceId) {
		this.tokenInstanceId = tokenInstanceId;
	}

	public long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(long versionNo) {
		this.versionNo = versionNo;
	}

	public Map getValueMap() {
		return valueMap;
	}

	public void setValueMap(Map valueMap) {
		this.valueMap = valueMap;
	}

	public Object getProperty(String key) {
		if (valueMap != null) {
			return valueMap.get(key);
		}
		return null;
	}

	public void setProperty(String key, Object value) {
		if (valueMap != null) {
			valueMap.put(key, value);
		}
	}

	public int hashCode() {
		if (stateInstanceId != null) {
			return stateInstanceId.hashCode();
		}
		return 0;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof StateInstance)) {
			return false;
		}

		final StateInstance model = (StateInstance) o;

		if (stateInstanceId != null ? !stateInstanceId.equals(model
				.getStateInstanceId()) : model.getStateInstanceId() != null) {
			return false;
		}

		return true;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}