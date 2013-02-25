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

package com.glaf.core.batch.query;

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
	protected Long commitCount;
	protected Long commitCountGreaterThanOrEqual;
	protected Long commitCountLessThanOrEqual;
	protected Long readCount;
	protected Long readCountGreaterThanOrEqual;
	protected Long readCountLessThanOrEqual;
	protected Long filterCount;
	protected Long filterCountGreaterThanOrEqual;
	protected Long filterCountLessThanOrEqual;
	protected Long writeCount;
	protected Long writeCountGreaterThanOrEqual;
	protected Long writeCountLessThanOrEqual;
	protected Long readSkipCount;
	protected Long readSkipCountGreaterThanOrEqual;
	protected Long readSkipCountLessThanOrEqual;
	protected Long writeSkipCount;
	protected Long writeSkipCountGreaterThanOrEqual;
	protected Long writeSkipCountLessThanOrEqual;
	protected Long processSkipCount;
	protected Long processSkipCountGreaterThanOrEqual;
	protected Long processSkipCountLessThanOrEqual;
	protected Long rollbackCount;
	protected Long rollbackCountGreaterThanOrEqual;
	protected Long rollbackCountLessThanOrEqual;
	protected String exitCode;
	protected String exitMessageLike;
	protected Date lastUpdated;
	protected Date lastUpdatedGreaterThanOrEqual;
	protected Date lastUpdatedLessThanOrEqual;
	protected String sortOrder;

	public StepExecutionQuery() {

	}

	public StepExecutionQuery commitCount(Long commitCount) {
		if (commitCount == null) {
			throw new RuntimeException("commitCount is null");
		}
		this.commitCount = commitCount;
		return this;
	}

	public StepExecutionQuery commitCountGreaterThanOrEqual(
			Long commitCountGreaterThanOrEqual) {
		if (commitCountGreaterThanOrEqual == null) {
			throw new RuntimeException("commitCount is null");
		}
		this.commitCountGreaterThanOrEqual = commitCountGreaterThanOrEqual;
		return this;
	}

	public StepExecutionQuery commitCountLessThanOrEqual(
			Long commitCountLessThanOrEqual) {
		if (commitCountLessThanOrEqual == null) {
			throw new RuntimeException("commitCount is null");
		}
		this.commitCountLessThanOrEqual = commitCountLessThanOrEqual;
		return this;
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

	public StepExecutionQuery exitCode(String exitCode) {
		if (exitCode == null) {
			throw new RuntimeException("exitCode is null");
		}
		this.exitCode = exitCode;
		return this;
	}

	public StepExecutionQuery exitMessageLike(String exitMessageLike) {
		if (exitMessageLike == null) {
			throw new RuntimeException("exitMessage is null");
		}
		this.exitMessageLike = exitMessageLike;
		return this;
	}

	public StepExecutionQuery filterCount(Long filterCount) {
		if (filterCount == null) {
			throw new RuntimeException("filterCount is null");
		}
		this.filterCount = filterCount;
		return this;
	}

	public StepExecutionQuery filterCountGreaterThanOrEqual(
			Long filterCountGreaterThanOrEqual) {
		if (filterCountGreaterThanOrEqual == null) {
			throw new RuntimeException("filterCount is null");
		}
		this.filterCountGreaterThanOrEqual = filterCountGreaterThanOrEqual;
		return this;
	}

	public StepExecutionQuery filterCountLessThanOrEqual(
			Long filterCountLessThanOrEqual) {
		if (filterCountLessThanOrEqual == null) {
			throw new RuntimeException("filterCount is null");
		}
		this.filterCountLessThanOrEqual = filterCountLessThanOrEqual;
		return this;
	}

	public Long getCommitCount() {
		return commitCount;
	}

	public Long getCommitCountGreaterThanOrEqual() {
		return commitCountGreaterThanOrEqual;
	}

	public Long getCommitCountLessThanOrEqual() {
		return commitCountLessThanOrEqual;
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

	public Long getFilterCount() {
		return filterCount;
	}

	public Long getFilterCountGreaterThanOrEqual() {
		return filterCountGreaterThanOrEqual;
	}

	public Long getFilterCountLessThanOrEqual() {
		return filterCountLessThanOrEqual;
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

	public Date getLastUpdated() {
		return lastUpdated;
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

	public Long getProcessSkipCount() {
		return processSkipCount;
	}

	public Long getProcessSkipCountGreaterThanOrEqual() {
		return processSkipCountGreaterThanOrEqual;
	}

	public Long getProcessSkipCountLessThanOrEqual() {
		return processSkipCountLessThanOrEqual;
	}

	public Long getReadCount() {
		return readCount;
	}

	public Long getReadCountGreaterThanOrEqual() {
		return readCountGreaterThanOrEqual;
	}

	public Long getReadCountLessThanOrEqual() {
		return readCountLessThanOrEqual;
	}

	public Long getReadSkipCount() {
		return readSkipCount;
	}

	public Long getReadSkipCountGreaterThanOrEqual() {
		return readSkipCountGreaterThanOrEqual;
	}

	public Long getReadSkipCountLessThanOrEqual() {
		return readSkipCountLessThanOrEqual;
	}

	public Long getRollbackCount() {
		return rollbackCount;
	}

	public Long getRollbackCountGreaterThanOrEqual() {
		return rollbackCountGreaterThanOrEqual;
	}

	public Long getRollbackCountLessThanOrEqual() {
		return rollbackCountLessThanOrEqual;
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

	public Long getWriteCount() {
		return writeCount;
	}

	public Long getWriteCountGreaterThanOrEqual() {
		return writeCountGreaterThanOrEqual;
	}

	public Long getWriteCountLessThanOrEqual() {
		return writeCountLessThanOrEqual;
	}

	public Long getWriteSkipCount() {
		return writeSkipCount;
	}

	public Long getWriteSkipCountGreaterThanOrEqual() {
		return writeSkipCountGreaterThanOrEqual;
	}

	public Long getWriteSkipCountLessThanOrEqual() {
		return writeSkipCountLessThanOrEqual;
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

	public String getSortOrder() {
		return sortOrder;
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

	public StepExecutionQuery lastUpdated(Date lastUpdated) {
		if (lastUpdated == null) {
			throw new RuntimeException("lastUpdated is null");
		}
		this.lastUpdated = lastUpdated;
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

	public StepExecutionQuery processSkipCount(Long processSkipCount) {
		if (processSkipCount == null) {
			throw new RuntimeException("processSkipCount is null");
		}
		this.processSkipCount = processSkipCount;
		return this;
	}

	public StepExecutionQuery processSkipCountGreaterThanOrEqual(
			Long processSkipCountGreaterThanOrEqual) {
		if (processSkipCountGreaterThanOrEqual == null) {
			throw new RuntimeException("processSkipCount is null");
		}
		this.processSkipCountGreaterThanOrEqual = processSkipCountGreaterThanOrEqual;
		return this;
	}

	public StepExecutionQuery processSkipCountLessThanOrEqual(
			Long processSkipCountLessThanOrEqual) {
		if (processSkipCountLessThanOrEqual == null) {
			throw new RuntimeException("processSkipCount is null");
		}
		this.processSkipCountLessThanOrEqual = processSkipCountLessThanOrEqual;
		return this;
	}

	public StepExecutionQuery readCount(Long readCount) {
		if (readCount == null) {
			throw new RuntimeException("readCount is null");
		}
		this.readCount = readCount;
		return this;
	}

	public StepExecutionQuery readCountGreaterThanOrEqual(
			Long readCountGreaterThanOrEqual) {
		if (readCountGreaterThanOrEqual == null) {
			throw new RuntimeException("readCount is null");
		}
		this.readCountGreaterThanOrEqual = readCountGreaterThanOrEqual;
		return this;
	}

	public StepExecutionQuery readCountLessThanOrEqual(
			Long readCountLessThanOrEqual) {
		if (readCountLessThanOrEqual == null) {
			throw new RuntimeException("readCount is null");
		}
		this.readCountLessThanOrEqual = readCountLessThanOrEqual;
		return this;
	}

	public StepExecutionQuery readSkipCount(Long readSkipCount) {
		if (readSkipCount == null) {
			throw new RuntimeException("readSkipCount is null");
		}
		this.readSkipCount = readSkipCount;
		return this;
	}

	public StepExecutionQuery readSkipCountGreaterThanOrEqual(
			Long readSkipCountGreaterThanOrEqual) {
		if (readSkipCountGreaterThanOrEqual == null) {
			throw new RuntimeException("readSkipCount is null");
		}
		this.readSkipCountGreaterThanOrEqual = readSkipCountGreaterThanOrEqual;
		return this;
	}

	public StepExecutionQuery readSkipCountLessThanOrEqual(
			Long readSkipCountLessThanOrEqual) {
		if (readSkipCountLessThanOrEqual == null) {
			throw new RuntimeException("readSkipCount is null");
		}
		this.readSkipCountLessThanOrEqual = readSkipCountLessThanOrEqual;
		return this;
	}

	public StepExecutionQuery rollbackCount(Long rollbackCount) {
		if (rollbackCount == null) {
			throw new RuntimeException("rollbackCount is null");
		}
		this.rollbackCount = rollbackCount;
		return this;
	}

	public StepExecutionQuery rollbackCountGreaterThanOrEqual(
			Long rollbackCountGreaterThanOrEqual) {
		if (rollbackCountGreaterThanOrEqual == null) {
			throw new RuntimeException("rollbackCount is null");
		}
		this.rollbackCountGreaterThanOrEqual = rollbackCountGreaterThanOrEqual;
		return this;
	}

	public StepExecutionQuery rollbackCountLessThanOrEqual(
			Long rollbackCountLessThanOrEqual) {
		if (rollbackCountLessThanOrEqual == null) {
			throw new RuntimeException("rollbackCount is null");
		}
		this.rollbackCountLessThanOrEqual = rollbackCountLessThanOrEqual;
		return this;
	}

	public void setCommitCount(Long commitCount) {
		this.commitCount = commitCount;
	}

	public void setCommitCountGreaterThanOrEqual(
			Long commitCountGreaterThanOrEqual) {
		this.commitCountGreaterThanOrEqual = commitCountGreaterThanOrEqual;
	}

	public void setCommitCountLessThanOrEqual(Long commitCountLessThanOrEqual) {
		this.commitCountLessThanOrEqual = commitCountLessThanOrEqual;
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

	public void setFilterCount(Long filterCount) {
		this.filterCount = filterCount;
	}

	public void setFilterCountGreaterThanOrEqual(
			Long filterCountGreaterThanOrEqual) {
		this.filterCountGreaterThanOrEqual = filterCountGreaterThanOrEqual;
	}

	public void setFilterCountLessThanOrEqual(Long filterCountLessThanOrEqual) {
		this.filterCountLessThanOrEqual = filterCountLessThanOrEqual;
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

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public void setLastUpdatedGreaterThanOrEqual(
			Date lastUpdatedGreaterThanOrEqual) {
		this.lastUpdatedGreaterThanOrEqual = lastUpdatedGreaterThanOrEqual;
	}

	public void setLastUpdatedLessThanOrEqual(Date lastUpdatedLessThanOrEqual) {
		this.lastUpdatedLessThanOrEqual = lastUpdatedLessThanOrEqual;
	}

	public void setProcessSkipCount(Long processSkipCount) {
		this.processSkipCount = processSkipCount;
	}

	public void setProcessSkipCountGreaterThanOrEqual(
			Long processSkipCountGreaterThanOrEqual) {
		this.processSkipCountGreaterThanOrEqual = processSkipCountGreaterThanOrEqual;
	}

	public void setProcessSkipCountLessThanOrEqual(
			Long processSkipCountLessThanOrEqual) {
		this.processSkipCountLessThanOrEqual = processSkipCountLessThanOrEqual;
	}

	public void setReadCount(Long readCount) {
		this.readCount = readCount;
	}

	public void setReadCountGreaterThanOrEqual(Long readCountGreaterThanOrEqual) {
		this.readCountGreaterThanOrEqual = readCountGreaterThanOrEqual;
	}

	public void setReadCountLessThanOrEqual(Long readCountLessThanOrEqual) {
		this.readCountLessThanOrEqual = readCountLessThanOrEqual;
	}

	public void setReadSkipCount(Long readSkipCount) {
		this.readSkipCount = readSkipCount;
	}

	public void setReadSkipCountGreaterThanOrEqual(
			Long readSkipCountGreaterThanOrEqual) {
		this.readSkipCountGreaterThanOrEqual = readSkipCountGreaterThanOrEqual;
	}

	public void setReadSkipCountLessThanOrEqual(
			Long readSkipCountLessThanOrEqual) {
		this.readSkipCountLessThanOrEqual = readSkipCountLessThanOrEqual;
	}

	public void setRollbackCount(Long rollbackCount) {
		this.rollbackCount = rollbackCount;
	}

	public void setRollbackCountGreaterThanOrEqual(
			Long rollbackCountGreaterThanOrEqual) {
		this.rollbackCountGreaterThanOrEqual = rollbackCountGreaterThanOrEqual;
	}

	public void setRollbackCountLessThanOrEqual(
			Long rollbackCountLessThanOrEqual) {
		this.rollbackCountLessThanOrEqual = rollbackCountLessThanOrEqual;
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

	public void setWriteCount(Long writeCount) {
		this.writeCount = writeCount;
	}

	public void setWriteCountGreaterThanOrEqual(
			Long writeCountGreaterThanOrEqual) {
		this.writeCountGreaterThanOrEqual = writeCountGreaterThanOrEqual;
	}

	public void setWriteCountLessThanOrEqual(Long writeCountLessThanOrEqual) {
		this.writeCountLessThanOrEqual = writeCountLessThanOrEqual;
	}

	public void setWriteSkipCount(Long writeSkipCount) {
		this.writeSkipCount = writeSkipCount;
	}

	public void setWriteSkipCountGreaterThanOrEqual(
			Long writeSkipCountGreaterThanOrEqual) {
		this.writeSkipCountGreaterThanOrEqual = writeSkipCountGreaterThanOrEqual;
	}

	public void setWriteSkipCountLessThanOrEqual(
			Long writeSkipCountLessThanOrEqual) {
		this.writeSkipCountLessThanOrEqual = writeSkipCountLessThanOrEqual;
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

	public StepExecutionQuery writeCount(Long writeCount) {
		if (writeCount == null) {
			throw new RuntimeException("writeCount is null");
		}
		this.writeCount = writeCount;
		return this;
	}

	public StepExecutionQuery writeCountGreaterThanOrEqual(
			Long writeCountGreaterThanOrEqual) {
		if (writeCountGreaterThanOrEqual == null) {
			throw new RuntimeException("writeCount is null");
		}
		this.writeCountGreaterThanOrEqual = writeCountGreaterThanOrEqual;
		return this;
	}

	public StepExecutionQuery writeCountLessThanOrEqual(
			Long writeCountLessThanOrEqual) {
		if (writeCountLessThanOrEqual == null) {
			throw new RuntimeException("writeCount is null");
		}
		this.writeCountLessThanOrEqual = writeCountLessThanOrEqual;
		return this;
	}

	public StepExecutionQuery writeSkipCount(Long writeSkipCount) {
		if (writeSkipCount == null) {
			throw new RuntimeException("writeSkipCount is null");
		}
		this.writeSkipCount = writeSkipCount;
		return this;
	}

	public StepExecutionQuery writeSkipCountGreaterThanOrEqual(
			Long writeSkipCountGreaterThanOrEqual) {
		if (writeSkipCountGreaterThanOrEqual == null) {
			throw new RuntimeException("writeSkipCount is null");
		}
		this.writeSkipCountGreaterThanOrEqual = writeSkipCountGreaterThanOrEqual;
		return this;
	}

	public StepExecutionQuery writeSkipCountLessThanOrEqual(
			Long writeSkipCountLessThanOrEqual) {
		if (writeSkipCountLessThanOrEqual == null) {
			throw new RuntimeException("writeSkipCount is null");
		}
		this.writeSkipCountLessThanOrEqual = writeSkipCountLessThanOrEqual;
		return this;
	}

}