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

package com.glaf.core.query;

import java.util.Date;
import java.util.List;

public class AgentQuery extends BaseQuery {
	private static final long serialVersionUID = 1L;
	protected Integer agentType;
	protected List<Integer> agentTypes;
	protected String assignFrom;
	protected String assignFromNameLike;
	protected List<String> assignFroms;
	protected String assignTo;
	protected String assignToNameLike;
	protected List<String> assignTos;
	protected Date endDateGreaterThanOrEqual;
	protected Date endDateLessThanOrEqual;
	protected List<String> objectIds;
	protected List<String> objectValues;
	protected Date startDateGreaterThanOrEqual;
	protected Date startDateLessThanOrEqual;
	protected String taskName;
	protected String taskNameLike;
	protected List<String> taskNames;

	public AgentQuery() {

	}

	public AgentQuery agentType(Integer agentType) {
		if (agentType == null) {
			throw new RuntimeException("agentType is null");
		}
		this.agentType = agentType;
		return this;
	}

	public AgentQuery agentTypes(List<Integer> agentTypes) {
		if (agentTypes == null) {
			throw new RuntimeException("agentTypes is empty ");
		}
		this.agentTypes = agentTypes;
		return this;
	}

	public AgentQuery assignFrom(String assignFrom) {
		if (assignFrom == null) {
			throw new RuntimeException("assignFrom is null");
		}
		this.assignFrom = assignFrom;
		return this;
	}

	public AgentQuery assignFromNameLike(String assignFromNameLike) {
		if (assignFromNameLike == null) {
			throw new RuntimeException("assignFromName is null");
		}
		this.assignFromNameLike = assignFromNameLike;
		return this;
	}

	public AgentQuery assignFroms(List<String> assignFroms) {
		if (assignFroms == null) {
			throw new RuntimeException("assignFroms is empty ");
		}
		this.assignFroms = assignFroms;
		return this;
	}

	public AgentQuery assignTo(String assignTo) {
		if (assignTo == null) {
			throw new RuntimeException("assignTo is null");
		}
		this.assignTo = assignTo;
		return this;
	}

	public AgentQuery assignToNameLike(String assignToNameLike) {
		if (assignToNameLike == null) {
			throw new RuntimeException("assignToName is null");
		}
		this.assignToNameLike = assignToNameLike;
		return this;
	}

	public AgentQuery assignTos(List<String> assignTos) {
		if (assignTos == null) {
			throw new RuntimeException("assignTos is empty ");
		}
		this.assignTos = assignTos;
		return this;
	}

	public AgentQuery endDateGreaterThanOrEqual(Date endDateGreaterThanOrEqual) {
		if (endDateGreaterThanOrEqual == null) {
			throw new RuntimeException("endDate is null");
		}
		this.endDateGreaterThanOrEqual = endDateGreaterThanOrEqual;
		return this;
	}

	public AgentQuery endDateLessThanOrEqual(Date endDateLessThanOrEqual) {
		if (endDateLessThanOrEqual == null) {
			throw new RuntimeException("endDate is null");
		}
		this.endDateLessThanOrEqual = endDateLessThanOrEqual;
		return this;
	}

	public Integer getAgentType() {
		return agentType;
	}

	public List<Integer> getAgentTypes() {
		return agentTypes;
	}

	public String getAssignFrom() {
		return assignFrom;
	}

	public String getAssignFromNameLike() {
		return assignFromNameLike;
	}

	public List<String> getAssignFroms() {
		return assignFroms;
	}

	public String getAssignTo() {
		return assignTo;
	}

	public String getAssignToNameLike() {
		return assignToNameLike;
	}

	public List<String> getAssignTos() {
		return assignTos;
	}

	public Date getEndDateGreaterThanOrEqual() {
		return endDateGreaterThanOrEqual;
	}

	public Date getEndDateLessThanOrEqual() {
		return endDateLessThanOrEqual;
	}

	public List<String> getObjectIds() {
		return objectIds;
	}

	public List<String> getObjectValues() {
		return objectValues;
	}

	public Date getStartDateGreaterThanOrEqual() {
		return startDateGreaterThanOrEqual;
	}

	public Date getStartDateLessThanOrEqual() {
		return startDateLessThanOrEqual;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getTaskNameLike() {
		return taskNameLike;
	}

	public List<String> getTaskNames() {
		return taskNames;
	}

	public AgentQuery objectIds(List<String> objectIds) {
		if (objectIds == null) {
			throw new RuntimeException("objectIds is empty ");
		}
		this.objectIds = objectIds;
		return this;
	}

	public AgentQuery objectValues(List<String> objectValues) {
		if (objectValues == null) {
			throw new RuntimeException("objectValues is empty ");
		}
		this.objectValues = objectValues;
		return this;
	}

	public void setAgentType(Integer agentType) {
		this.agentType = agentType;
	}

	public void setAgentTypes(List<Integer> agentTypes) {
		this.agentTypes = agentTypes;
	}

	public void setAssignFrom(String assignFrom) {
		this.assignFrom = assignFrom;
	}

	public void setAssignFromNameLike(String assignFromNameLike) {
		this.assignFromNameLike = assignFromNameLike;
	}

	public void setAssignFroms(List<String> assignFroms) {
		this.assignFroms = assignFroms;
	}

	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}

	public void setAssignToNameLike(String assignToNameLike) {
		this.assignToNameLike = assignToNameLike;
	}

	public void setAssignTos(List<String> assignTos) {
		this.assignTos = assignTos;
	}

	public void setEndDateGreaterThanOrEqual(Date endDateGreaterThanOrEqual) {
		this.endDateGreaterThanOrEqual = endDateGreaterThanOrEqual;
	}

	public void setEndDateLessThanOrEqual(Date endDateLessThanOrEqual) {
		this.endDateLessThanOrEqual = endDateLessThanOrEqual;
	}

	public void setObjectIds(List<String> objectIds) {
		this.objectIds = objectIds;
	}

	public void setObjectValues(List<String> objectValues) {
		this.objectValues = objectValues;
	}

	public void setStartDateGreaterThanOrEqual(Date startDateGreaterThanOrEqual) {
		this.startDateGreaterThanOrEqual = startDateGreaterThanOrEqual;
	}

	public void setStartDateLessThanOrEqual(Date startDateLessThanOrEqual) {
		this.startDateLessThanOrEqual = startDateLessThanOrEqual;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setTaskNameLike(String taskNameLike) {
		this.taskNameLike = taskNameLike;
	}

	public void setTaskNames(List<String> taskNames) {
		this.taskNames = taskNames;
	}

	public AgentQuery startDateGreaterThanOrEqual(
			Date startDateGreaterThanOrEqual) {
		if (startDateGreaterThanOrEqual == null) {
			throw new RuntimeException("startDate is null");
		}
		this.startDateGreaterThanOrEqual = startDateGreaterThanOrEqual;
		return this;
	}

	public AgentQuery startDateLessThanOrEqual(Date startDateLessThanOrEqual) {
		if (startDateLessThanOrEqual == null) {
			throw new RuntimeException("startDate is null");
		}
		this.startDateLessThanOrEqual = startDateLessThanOrEqual;
		return this;
	}

	public AgentQuery taskName(String taskName) {
		if (taskName == null) {
			throw new RuntimeException("taskName is null");
		}
		this.taskName = taskName;
		return this;
	}

	public AgentQuery taskNameLike(String taskNameLike) {
		if (taskNameLike == null) {
			throw new RuntimeException("taskName is null");
		}
		this.taskNameLike = taskNameLike;
		return this;
	}

	public AgentQuery taskNames(List<String> taskNames) {
		if (taskNames == null) {
			throw new RuntimeException("taskNames is empty ");
		}
		this.taskNames = taskNames;
		return this;
	}

}