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

public class SchedulerQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String contentLike;
	protected Date endDateGreaterThanOrEqual;
	protected Date endDateLessThanOrEqual;
	protected String jobClassLike;
	protected Date startDateGreaterThanOrEqual;
	protected Date startDateLessThanOrEqual;
	protected String taskId;
	protected List<String> taskIds;
	protected String taskNameLike;
	protected String taskType;
	protected String titleLike;

	public SchedulerQuery() {

	}

	public SchedulerQuery contentLike(String contentLike) {
		if (contentLike == null) {
			throw new RuntimeException("content is null");
		}
		this.contentLike = contentLike;
		return this;
	}

	public SchedulerQuery endDateGreaterThanOrEqual(
			Date endDateGreaterThanOrEqual) {
		if (endDateGreaterThanOrEqual == null) {
			throw new RuntimeException("endDate is null");
		}
		this.endDateGreaterThanOrEqual = endDateGreaterThanOrEqual;
		return this;
	}

	public SchedulerQuery endDateLessThanOrEqual(Date endDateLessThanOrEqual) {
		if (endDateLessThanOrEqual == null) {
			throw new RuntimeException("endDate is null");
		}
		this.endDateLessThanOrEqual = endDateLessThanOrEqual;
		return this;
	}

	public String getContentLike() {
		return contentLike;
	}

	public Date getEndDateGreaterThanOrEqual() {
		return endDateGreaterThanOrEqual;
	}

	public Date getEndDateLessThanOrEqual() {
		return endDateLessThanOrEqual;
	}

	public String getJobClassLike() {
		return jobClassLike;
	}

	public Date getStartDateGreaterThanOrEqual() {
		return startDateGreaterThanOrEqual;
	}

	public Date getStartDateLessThanOrEqual() {
		return startDateLessThanOrEqual;
	}

	public String getTaskId() {
		return taskId;
	}

	public List<String> getTaskIds() {
		return taskIds;
	}

	public String getTaskNameLike() {
		return taskNameLike;
	}

	public String getTaskType() {
		return taskType;
	}

	public String getTitleLike() {
		return titleLike;
	}

	public SchedulerQuery jobClassLike(String jobClassLike) {
		if (jobClassLike == null) {
			throw new RuntimeException("jobClass is null");
		}
		this.jobClassLike = jobClassLike;
		return this;
	}

	public void setContentLike(String contentLike) {
		this.contentLike = contentLike;
	}

	public void setEndDateGreaterThanOrEqual(Date endDateGreaterThanOrEqual) {
		this.endDateGreaterThanOrEqual = endDateGreaterThanOrEqual;
	}

	public void setEndDateLessThanOrEqual(Date endDateLessThanOrEqual) {
		this.endDateLessThanOrEqual = endDateLessThanOrEqual;
	}

	public void setJobClassLike(String jobClassLike) {
		this.jobClassLike = jobClassLike;
	}

	public void setStartDateGreaterThanOrEqual(Date startDateGreaterThanOrEqual) {
		this.startDateGreaterThanOrEqual = startDateGreaterThanOrEqual;
	}

	public void setStartDateLessThanOrEqual(Date startDateLessThanOrEqual) {
		this.startDateLessThanOrEqual = startDateLessThanOrEqual;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setTaskIds(List<String> taskIds) {
		this.taskIds = taskIds;
	}

	public void setTaskNameLike(String taskNameLike) {
		this.taskNameLike = taskNameLike;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public SchedulerQuery startDateGreaterThanOrEqual(
			Date startDateGreaterThanOrEqual) {
		if (startDateGreaterThanOrEqual == null) {
			throw new RuntimeException("startDate is null");
		}
		this.startDateGreaterThanOrEqual = startDateGreaterThanOrEqual;
		return this;
	}

	public SchedulerQuery startDateLessThanOrEqual(Date startDateLessThanOrEqual) {
		if (startDateLessThanOrEqual == null) {
			throw new RuntimeException("startDate is null");
		}
		this.startDateLessThanOrEqual = startDateLessThanOrEqual;
		return this;
	}

	public SchedulerQuery taskId(String taskId) {
		if (taskId == null) {
			throw new RuntimeException("taskId is null");
		}
		this.taskId = taskId;
		return this;
	}

	public SchedulerQuery taskIds(List<String> taskIds) {
		if (taskIds == null) {
			throw new RuntimeException("taskIds is empty ");
		}
		this.taskIds = taskIds;
		return this;
	}

	public SchedulerQuery taskNameLike(String taskNameLike) {
		if (taskNameLike == null) {
			throw new RuntimeException("taskName is null");
		}
		this.taskNameLike = taskNameLike;
		return this;
	}

	public SchedulerQuery taskType(String taskType) {
		if (taskType == null) {
			throw new RuntimeException("taskType is null");
		}
		this.taskType = taskType;
		return this;
	}

	public SchedulerQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

}