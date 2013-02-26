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

package com.glaf.dts.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class TransformExcecutionQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String activityId;
	protected List<String> activityIds;
	protected String stepId;
	protected List<String> stepIds;
	protected Date startTimeGreaterThanOrEqual;
	protected Date startTimeLessThanOrEqual;
	protected Date endTimeGreaterThanOrEqual;
	protected Date endTimeLessThanOrEqual;
	protected Integer priority;
	protected Integer priorityGreaterThanOrEqual;
	protected Integer priorityLessThanOrEqual;

	public TransformExcecutionQuery() {

	}

	public TransformExcecutionQuery activityId(String activityId) {
		if (activityId == null) {
			throw new RuntimeException("activityId is null");
		}
		this.activityId = activityId;
		return this;
	}

	public TransformExcecutionQuery activityIds(List<String> activityIds) {
		if (activityIds == null) {
			throw new RuntimeException("activityIds is empty ");
		}
		this.activityIds = activityIds;
		return this;
	}

	public TransformExcecutionQuery endTimeGreaterThanOrEqual(
			Date endTimeGreaterThanOrEqual) {
		if (endTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("endTime is null");
		}
		this.endTimeGreaterThanOrEqual = endTimeGreaterThanOrEqual;
		return this;
	}

	public TransformExcecutionQuery endTimeLessThanOrEqual(
			Date endTimeLessThanOrEqual) {
		if (endTimeLessThanOrEqual == null) {
			throw new RuntimeException("endTime is null");
		}
		this.endTimeLessThanOrEqual = endTimeLessThanOrEqual;
		return this;
	}

	public String getActivityId() {
		return activityId;
	}

	public List<String> getActivityIds() {
		return activityIds;
	}

	public Date getEndTimeGreaterThanOrEqual() {
		return endTimeGreaterThanOrEqual;
	}

	public Date getEndTimeLessThanOrEqual() {
		return endTimeLessThanOrEqual;
	}

	public String getOrderBy() {
		if (sortField != null) {
			String a_x = " asc ";
			if (getSortOrder() != null) {
				a_x = " desc ";
			}

			if ("activityId".equals(sortField)) {
				orderBy = "E.ACTIVITYID_" + a_x;
			}

			if ("stepId".equals(sortField)) {
				orderBy = "E.STEPID_" + a_x;
			}

			if ("startTime".equals(sortField)) {
				orderBy = "E.STARTTIME_" + a_x;
			}

			if ("endTime".equals(sortField)) {
				orderBy = "E.ENDTIME_" + a_x;
			}

			if ("priority".equals(sortField)) {
				orderBy = "E.PRIORITY_" + a_x;
			}

		}
		return orderBy;
	}

	public Integer getPriority() {
		return priority;
	}

	public Integer getPriorityGreaterThanOrEqual() {
		return priorityGreaterThanOrEqual;
	}

	public Integer getPriorityLessThanOrEqual() {
		return priorityLessThanOrEqual;
	}

	public Date getStartTimeGreaterThanOrEqual() {
		return startTimeGreaterThanOrEqual;
	}

	public Date getStartTimeLessThanOrEqual() {
		return startTimeLessThanOrEqual;
	}

	public String getStepId() {
		return stepId;
	}

	public List<String> getStepIds() {
		return stepIds;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("activityId", "ACTIVITYID_");
		addColumn("stepId", "STEPID_");
		addColumn("startTime", "STARTTIME_");
		addColumn("endTime", "ENDTIME_");
		addColumn("priority", "PRIORITY_");
		addColumn("execute", "EXECUTE_");
		addColumn("success", "SUCCESS_");
	}

	public TransformExcecutionQuery priority(Integer priority) {
		if (priority == null) {
			throw new RuntimeException("priority is null");
		}
		this.priority = priority;
		return this;
	}

	public TransformExcecutionQuery priorityGreaterThanOrEqual(
			Integer priorityGreaterThanOrEqual) {
		if (priorityGreaterThanOrEqual == null) {
			throw new RuntimeException("priority is null");
		}
		this.priorityGreaterThanOrEqual = priorityGreaterThanOrEqual;
		return this;
	}

	public TransformExcecutionQuery priorityLessThanOrEqual(
			Integer priorityLessThanOrEqual) {
		if (priorityLessThanOrEqual == null) {
			throw new RuntimeException("priority is null");
		}
		this.priorityLessThanOrEqual = priorityLessThanOrEqual;
		return this;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public void setActivityIds(List<String> activityIds) {
		this.activityIds = activityIds;
	}

	public void setEndTimeGreaterThanOrEqual(Date endTimeGreaterThanOrEqual) {
		this.endTimeGreaterThanOrEqual = endTimeGreaterThanOrEqual;
	}

	public void setEndTimeLessThanOrEqual(Date endTimeLessThanOrEqual) {
		this.endTimeLessThanOrEqual = endTimeLessThanOrEqual;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public void setPriorityGreaterThanOrEqual(Integer priorityGreaterThanOrEqual) {
		this.priorityGreaterThanOrEqual = priorityGreaterThanOrEqual;
	}

	public void setPriorityLessThanOrEqual(Integer priorityLessThanOrEqual) {
		this.priorityLessThanOrEqual = priorityLessThanOrEqual;
	}

	public void setStartTimeGreaterThanOrEqual(Date startTimeGreaterThanOrEqual) {
		this.startTimeGreaterThanOrEqual = startTimeGreaterThanOrEqual;
	}

	public void setStartTimeLessThanOrEqual(Date startTimeLessThanOrEqual) {
		this.startTimeLessThanOrEqual = startTimeLessThanOrEqual;
	}

	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

	public void setStepIds(List<String> stepIds) {
		this.stepIds = stepIds;
	}

	public TransformExcecutionQuery startTimeGreaterThanOrEqual(
			Date startTimeGreaterThanOrEqual) {
		if (startTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("startTime is null");
		}
		this.startTimeGreaterThanOrEqual = startTimeGreaterThanOrEqual;
		return this;
	}

	public TransformExcecutionQuery startTimeLessThanOrEqual(
			Date startTimeLessThanOrEqual) {
		if (startTimeLessThanOrEqual == null) {
			throw new RuntimeException("startTime is null");
		}
		this.startTimeLessThanOrEqual = startTimeLessThanOrEqual;
		return this;
	}

	public TransformExcecutionQuery stepId(String stepId) {
		if (stepId == null) {
			throw new RuntimeException("stepId is null");
		}
		this.stepId = stepId;
		return this;
	}

	public TransformExcecutionQuery stepIds(List<String> stepIds) {
		if (stepIds == null) {
			throw new RuntimeException("stepIds is empty ");
		}
		this.stepIds = stepIds;
		return this;
	}

}