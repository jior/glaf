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

public class JobExecutionQuery extends BaseQuery {
	private static final long serialVersionUID = 1L;
	protected Long jobInstanceId;
	protected List<Long> jobInstanceIds;
	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;
	protected Date startTimeGreaterThanOrEqual;
	protected Date startTimeLessThanOrEqual;
	protected Date endTimeGreaterThanOrEqual;
	protected Date endTimeLessThanOrEqual;
	protected String status;
	protected String exitCode;
	protected String exitMessageLike;
	protected Date lastUpdatedGreaterThanOrEqual;
	protected Date lastUpdatedLessThanOrEqual;

	public JobExecutionQuery() {

	}

	public JobExecutionQuery createTimeGreaterThanOrEqual(
			Date createTimeGreaterThanOrEqual) {
		if (createTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
		return this;
	}

	public JobExecutionQuery createTimeLessThanOrEqual(
			Date createTimeLessThanOrEqual) {
		if (createTimeLessThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
		return this;
	}

	public JobExecutionQuery endTimeGreaterThanOrEqual(
			Date endTimeGreaterThanOrEqual) {
		if (endTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("endTime is null");
		}
		this.endTimeGreaterThanOrEqual = endTimeGreaterThanOrEqual;
		return this;
	}

	public JobExecutionQuery endTimeLessThanOrEqual(Date endTimeLessThanOrEqual) {
		if (endTimeLessThanOrEqual == null) {
			throw new RuntimeException("endTime is null");
		}
		this.endTimeLessThanOrEqual = endTimeLessThanOrEqual;
		return this;
	}

	public JobExecutionQuery exitCode(String exitCode) {
		if (exitCode == null) {
			throw new RuntimeException("exitCode is null");
		}
		this.exitCode = exitCode;
		return this;
	}

	public JobExecutionQuery exitMessageLike(String exitMessageLike) {
		if (exitMessageLike == null) {
			throw new RuntimeException("exitMessage is null");
		}
		this.exitMessageLike = exitMessageLike;
		return this;
	}

	public Date getCreateTimeGreaterThanOrEqual() {
		return createTimeGreaterThanOrEqual;
	}

	public Date getCreateTimeLessThanOrEqual() {
		return createTimeLessThanOrEqual;
	}

	public Date getEndTimeGreaterThanOrEqual() {
		return endTimeGreaterThanOrEqual;
	}

	public Date getEndTimeLessThanOrEqual() {
		return endTimeLessThanOrEqual;
	}

	public String getExitCode() {
		return exitCode;
	}

	public String getExitMessageLike() {
		if (exitMessageLike != null && exitMessageLike.trim().length() > 0) {
			if (!exitMessageLike.startsWith("%")) {
				exitMessageLike = "%" + exitMessageLike;
			}
			if (!exitMessageLike.endsWith("%")) {
				exitMessageLike = exitMessageLike + "%";
			}
		}
		return exitMessageLike;
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

	public Date getStartTimeGreaterThanOrEqual() {
		return startTimeGreaterThanOrEqual;
	}

	public Date getStartTimeLessThanOrEqual() {
		return startTimeLessThanOrEqual;
	}

	public String getStatus() {
		return status;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("jobExecutionId", "job_execution_id");
		addColumn("version", "version");
		addColumn("jobInstanceId", "job_instance_id");
		addColumn("createTime", "create_time");
		addColumn("startTime", "start_time");
		addColumn("endTime", "end_time");
		addColumn("status", "status");
		addColumn("exitCode", "exit_code");
		addColumn("exitMessage", "exit_message");
		addColumn("lastUpdated", "last_updated");
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public JobExecutionQuery jobInstanceId(Long jobInstanceId) {
		if (jobInstanceId == null) {
			throw new RuntimeException("jobInstanceId is null");
		}
		this.jobInstanceId = jobInstanceId;
		return this;
	}

	public JobExecutionQuery jobInstanceIds(List<Long> jobInstanceIds) {
		if (jobInstanceIds == null) {
			throw new RuntimeException("jobInstanceIds is empty ");
		}
		this.jobInstanceIds = jobInstanceIds;
		return this;
	}

	public JobExecutionQuery lastUpdatedGreaterThanOrEqual(
			Date lastUpdatedGreaterThanOrEqual) {
		if (lastUpdatedGreaterThanOrEqual == null) {
			throw new RuntimeException("lastUpdated is null");
		}
		this.lastUpdatedGreaterThanOrEqual = lastUpdatedGreaterThanOrEqual;
		return this;
	}

	public JobExecutionQuery lastUpdatedLessThanOrEqual(
			Date lastUpdatedLessThanOrEqual) {
		if (lastUpdatedLessThanOrEqual == null) {
			throw new RuntimeException("lastUpdated is null");
		}
		this.lastUpdatedLessThanOrEqual = lastUpdatedLessThanOrEqual;
		return this;
	}

	public void setCreateTimeGreaterThanOrEqual(
			Date createTimeGreaterThanOrEqual) {
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
	}

	public void setCreateTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
	}

	public void setEndTimeGreaterThanOrEqual(Date endTimeGreaterThanOrEqual) {
		this.endTimeGreaterThanOrEqual = endTimeGreaterThanOrEqual;
	}

	public void setEndTimeLessThanOrEqual(Date endTimeLessThanOrEqual) {
		this.endTimeLessThanOrEqual = endTimeLessThanOrEqual;
	}

	public void setExitCode(String exitCode) {
		this.exitCode = exitCode;
	}

	public void setExitMessageLike(String exitMessageLike) {
		this.exitMessageLike = exitMessageLike;
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

	public JobExecutionQuery startTimeGreaterThanOrEqual(
			Date startTimeGreaterThanOrEqual) {
		if (startTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("startTime is null");
		}
		this.startTimeGreaterThanOrEqual = startTimeGreaterThanOrEqual;
		return this;
	}

	public JobExecutionQuery startTimeLessThanOrEqual(
			Date startTimeLessThanOrEqual) {
		if (startTimeLessThanOrEqual == null) {
			throw new RuntimeException("startTime is null");
		}
		this.startTimeLessThanOrEqual = startTimeLessThanOrEqual;
		return this;
	}

	public JobExecutionQuery status(String status) {
		if (status == null) {
			throw new RuntimeException("status is null");
		}
		this.status = status;
		return this;
	}

}