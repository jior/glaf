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

package com.glaf.batch.query;

import java.util.*;

import com.glaf.core.query.BaseQuery;

public class StepExecutionQuery extends BaseQuery {
	private static final long serialVersionUID = 1L;
	protected String stepNameLike;
	protected Long jobExecutionId;
	protected List<Long> jobExecutionIds;
	protected Long jobInstanceId;
	protected List<Long> jobInstanceIds;
	protected Date startTimeGreaterThanOrEqual;
	protected Date startTimeLessThanOrEqual;
	protected Date endTimeGreaterThanOrEqual;
	protected Date endTimeLessThanOrEqual;
	protected String status;
	protected Date lastUpdatedGreaterThanOrEqual;
	protected Date lastUpdatedLessThanOrEqual;

	public StepExecutionQuery() {

	}

	public StepExecutionQuery endTimeGreaterThanOrEqual(
			Date endTimeGreaterThanOrEqual) {
		if (endTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("endTime is null");
		}
		this.endTimeGreaterThanOrEqual = endTimeGreaterThanOrEqual;
		return this;
	}

	public StepExecutionQuery endTimeLessThanOrEqual(Date endTimeLessThanOrEqual) {
		if (endTimeLessThanOrEqual == null) {
			throw new RuntimeException("endTime is null");
		}
		this.endTimeLessThanOrEqual = endTimeLessThanOrEqual;
		return this;
	}

	public Date getEndTimeGreaterThanOrEqual() {
		return endTimeGreaterThanOrEqual;
	}

	public Date getEndTimeLessThanOrEqual() {
		return endTimeLessThanOrEqual;
	}

	public Long getJobExecutionId() {
		return jobExecutionId;
	}

	public List<Long> getJobExecutionIds() {
		return jobExecutionIds;
	}

	public Long getJobInstanceId() {
		return jobInstanceId;
	}

	public List<Long> getJobInstanceIds() {
		return jobInstanceIds;
	}

	public Date getLastUpdatedGreaterThanOrEqual() {
		return lastUpdatedGreaterThanOrEqual;
	}

	public Date getLastUpdatedLessThanOrEqual() {
		return lastUpdatedLessThanOrEqual;
	}

	public String getOrderBy() {
		if (sortField != null) {
			String a_x = " asc ";
			if (getSortOrder() != null) {
				a_x = " desc ";
			}

			if (columns.get(sortField) != null) {
				orderBy = " E." + columns.get(sortField) + a_x;
			}
		}
		return orderBy;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public Date getStartTimeGreaterThanOrEqual() {
		return startTimeGreaterThanOrEqual;
	}

	public Date getStartTimeLessThanOrEqual() {
		return startTimeLessThanOrEqual;
	}

	public String getStatus() {
		return status;
	}

	public String getStepNameLike() {
		if (stepNameLike != null && stepNameLike.trim().length() > 0) {
			if (!stepNameLike.startsWith("%")) {
				stepNameLike = "%" + stepNameLike;
			}
			if (!stepNameLike.endsWith("%")) {
				stepNameLike = stepNameLike + "%";
			}
		}
		return stepNameLike;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("stepExecutionId", "step_execution_id");
		addColumn("version", "version");
		addColumn("stepName", "step_name");
		addColumn("jobExecutionId", "job_execution_id");
		addColumn("jobInstanceId", "job_instance_id");
		addColumn("startTime", "start_time");
		addColumn("endTime", "end_time");
		addColumn("status", "status");
		addColumn("commitCount", "commit_count");
		addColumn("readCount", "read_count");
		addColumn("filterCount", "filter_count");
		addColumn("writeCount", "write_count");
		addColumn("readSkipCount", "read_skip_count");
		addColumn("writeSkipCount", "write_skip_count");
		addColumn("processSkipCount", "process_skip_count");
		addColumn("rollbackCount", "rollback_count");
		addColumn("exitCode", "exit_code");
		addColumn("exitMessage", "exit_message");
		addColumn("lastUpdated", "last_updated");
	}

	public StepExecutionQuery jobExecutionId(Long jobExecutionId) {
		if (jobExecutionId == null) {
			throw new RuntimeException("jobExecutionId is null");
		}
		this.jobExecutionId = jobExecutionId;
		return this;
	}

	public StepExecutionQuery jobExecutionIds(List<Long> jobExecutionIds) {
		if (jobExecutionIds == null) {
			throw new RuntimeException("jobExecutionIds is empty ");
		}
		this.jobExecutionIds = jobExecutionIds;
		return this;
	}

	public StepExecutionQuery jobInstanceId(Long jobInstanceId) {
		if (jobInstanceId == null) {
			throw new RuntimeException("jobInstanceId is null");
		}
		this.jobInstanceId = jobInstanceId;
		return this;
	}

	public StepExecutionQuery lastUpdatedGreaterThanOrEqual(
			Date lastUpdatedGreaterThanOrEqual) {
		if (lastUpdatedGreaterThanOrEqual == null) {
			throw new RuntimeException("lastUpdated is null");
		}
		this.lastUpdatedGreaterThanOrEqual = lastUpdatedGreaterThanOrEqual;
		return this;
	}

	public StepExecutionQuery lastUpdatedLessThanOrEqual(
			Date lastUpdatedLessThanOrEqual) {
		if (lastUpdatedLessThanOrEqual == null) {
			throw new RuntimeException("lastUpdated is null");
		}
		this.lastUpdatedLessThanOrEqual = lastUpdatedLessThanOrEqual;
		return this;
	}

	public void setEndTimeGreaterThanOrEqual(Date endTimeGreaterThanOrEqual) {
		this.endTimeGreaterThanOrEqual = endTimeGreaterThanOrEqual;
	}

	public void setEndTimeLessThanOrEqual(Date endTimeLessThanOrEqual) {
		this.endTimeLessThanOrEqual = endTimeLessThanOrEqual;
	}

	public void setJobExecutionId(Long jobExecutionId) {
		this.jobExecutionId = jobExecutionId;
	}

	public void setJobExecutionIds(List<Long> jobExecutionIds) {
		this.jobExecutionIds = jobExecutionIds;
	}

	public void setJobInstanceId(Long jobInstanceId) {
		this.jobInstanceId = jobInstanceId;
	}

	public void setJobInstanceIds(List<Long> jobInstanceIds) {
		this.jobInstanceIds = jobInstanceIds;
	}

	public void setLastUpdatedGreaterThanOrEqual(
			Date lastUpdatedGreaterThanOrEqual) {
		this.lastUpdatedGreaterThanOrEqual = lastUpdatedGreaterThanOrEqual;
	}

	public void setLastUpdatedLessThanOrEqual(Date lastUpdatedLessThanOrEqual) {
		this.lastUpdatedLessThanOrEqual = lastUpdatedLessThanOrEqual;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public void setStartTimeGreaterThanOrEqual(Date startTimeGreaterThanOrEqual) {
		this.startTimeGreaterThanOrEqual = startTimeGreaterThanOrEqual;
	}

	public void setStartTimeLessThanOrEqual(Date startTimeLessThanOrEqual) {
		this.startTimeLessThanOrEqual = startTimeLessThanOrEqual;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setStepNameLike(String stepNameLike) {
		this.stepNameLike = stepNameLike;
	}

	public StepExecutionQuery startTimeGreaterThanOrEqual(
			Date startTimeGreaterThanOrEqual) {
		if (startTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("startTime is null");
		}
		this.startTimeGreaterThanOrEqual = startTimeGreaterThanOrEqual;
		return this;
	}

	public StepExecutionQuery startTimeLessThanOrEqual(
			Date startTimeLessThanOrEqual) {
		if (startTimeLessThanOrEqual == null) {
			throw new RuntimeException("startTime is null");
		}
		this.startTimeLessThanOrEqual = startTimeLessThanOrEqual;
		return this;
	}

	public StepExecutionQuery status(String status) {
		if (status == null) {
			throw new RuntimeException("status is null");
		}
		this.status = status;
		return this;
	}

	public StepExecutionQuery stepNameLike(String stepNameLike) {
		if (stepNameLike == null) {
			throw new RuntimeException("stepName is null");
		}
		this.stepNameLike = stepNameLike;
		return this;
	}

}