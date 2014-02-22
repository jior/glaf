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

package com.glaf.batch.domain;

import java.io.*;
import java.util.*;

import javax.persistence.*;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.glaf.batch.util.StepExecutionJsonFactory;

@Entity
@Table(name = "SYS_STEP_EXECUTION")
public class StepExecution implements Serializable,
		java.lang.Comparable<StepExecution> {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "step_execution_id", nullable = false)
	protected Long stepExecutionId;

	/**
	 * version
	 */
	@Column(name = "version")
	protected int version = 1;

	/**
	 * step_name
	 */
	@Column(name = "step_key", length = 50)
	protected String stepKey;

	/**
	 * step_name
	 */
	@Column(name = "step_name", length = 100)
	protected String stepName;

	@Column(name = "job_step_key", length = 200)
	protected String jobStepKey;

	@Column(name = "job_class", length = 200)
	protected String jobClass;

	/**
	 * job_instance_id
	 */
	@Column(name = "job_instance_id")
	protected Long jobInstanceId;

	/**
	 * job_execution_id
	 */
	@Column(name = "job_execution_id")
	protected Long jobExecutionId;

	@Column(name = "listno")
	protected int listno;

	/**
	 * start_time
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time")
	protected Date startTime;

	/**
	 * end_time
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time")
	protected Date endTime;

	/**
	 * status
	 */
	@Column(name = "status", length = 10)
	protected String status;

	/**
	 * commit_count
	 */
	@Column(name = "commit_count")
	protected int commitCount;

	/**
	 * read_count
	 */
	@Column(name = "read_count")
	protected int readCount;

	/**
	 * filter_count
	 */
	@Column(name = "filter_count")
	protected int filterCount;

	/**
	 * write_count
	 */
	@Column(name = "write_count")
	protected int writeCount;

	/**
	 * read_skip_count
	 */
	@Column(name = "read_skip_count")
	protected int readSkipCount;

	/**
	 * write_skip_count
	 */
	@Column(name = "write_skip_count")
	protected int writeSkipCount;

	/**
	 * process_skip_count
	 */
	@Column(name = "process_skip_count")
	protected int processSkipCount;

	/**
	 * rollback_count
	 */
	@Column(name = "rollback_count")
	protected int rollbackCount;

	/**
	 * exit_code
	 */
	@Column(name = "exit_code", length = 100)
	protected String exitCode;

	/**
	 * exit_message
	 */
	@Column(name = "exit_message", length = 2500)
	protected String exitMessage;

	/**
	 * last_updated
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_updated")
	protected Date lastUpdated;

	public StepExecution() {

	}

	public int compareTo(StepExecution o) {
		if (o == null) {
			return -1;
		}

		StepExecution field = o;

		int l = this.listno - field.getListno();

		int ret = 0;

		if (l > 0) {
			ret = 1;
		} else if (l < 0) {
			ret = -1;
		}
		return ret;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StepExecution other = (StepExecution) obj;
		if (stepExecutionId == null) {
			if (other.stepExecutionId != null)
				return false;
		} else if (!stepExecutionId.equals(other.stepExecutionId))
			return false;
		return true;
	}

	public int getCommitCount() {
		return this.commitCount;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public String getExitCode() {
		return this.exitCode;
	}

	public String getExitMessage() {
		return this.exitMessage;
	}

	public int getFilterCount() {
		return this.filterCount;
	}

	public String getJobClass() {
		return jobClass;
	}

	public Long getJobExecutionId() {
		return this.jobExecutionId;
	}

	public Long getJobInstanceId() {
		return jobInstanceId;
	}

	public String getJobStepKey() {
		return jobStepKey;
	}

	public Date getLastUpdated() {
		return this.lastUpdated;
	}

	public int getListno() {
		return listno;
	}

	public int getProcessSkipCount() {
		return this.processSkipCount;
	}

	public int getReadCount() {
		return this.readCount;
	}

	public int getReadSkipCount() {
		return this.readSkipCount;
	}

	public int getRollbackCount() {
		return this.rollbackCount;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public String getStatus() {
		return this.status;
	}

	public Long getStepExecutionId() {
		return this.stepExecutionId;
	}

	public String getStepKey() {
		return stepKey;
	}

	public String getStepName() {
		return this.stepName;
	}

	public int getVersion() {
		return this.version;
	}

	public int getWriteCount() {
		return this.writeCount;
	}

	public int getWriteSkipCount() {
		return this.writeSkipCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((stepExecutionId == null) ? 0 : stepExecutionId.hashCode());
		return result;
	}

	public StepExecution jsonToObject(JSONObject jsonObject) {
		return StepExecutionJsonFactory.jsonToObject(jsonObject);
	}

	public void setCommitCount(int commitCount) {
		this.commitCount = commitCount;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setExitCode(String exitCode) {
		this.exitCode = exitCode;
	}

	public void setExitMessage(String exitMessage) {
		this.exitMessage = exitMessage;
	}

	public void setFilterCount(int filterCount) {
		this.filterCount = filterCount;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public void setJobExecutionId(Long jobExecutionId) {
		this.jobExecutionId = jobExecutionId;
	}

	public void setJobInstanceId(Long jobInstanceId) {
		this.jobInstanceId = jobInstanceId;
	}

	public void setJobStepKey(String jobStepKey) {
		this.jobStepKey = jobStepKey;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public void setListno(int listno) {
		this.listno = listno;
	}

	public void setProcessSkipCount(int processSkipCount) {
		this.processSkipCount = processSkipCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public void setReadSkipCount(int readSkipCount) {
		this.readSkipCount = readSkipCount;
	}

	public void setRollbackCount(int rollbackCount) {
		this.rollbackCount = rollbackCount;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setStepExecutionId(Long stepExecutionId) {
		this.stepExecutionId = stepExecutionId;
	}

	public void setStepKey(String stepKey) {
		this.stepKey = stepKey;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public void setWriteCount(int writeCount) {
		this.writeCount = writeCount;
	}

	public void setWriteSkipCount(int writeSkipCount) {
		this.writeSkipCount = writeSkipCount;
	}

	public JSONObject toJsonObject() {
		return StepExecutionJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return StepExecutionJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}