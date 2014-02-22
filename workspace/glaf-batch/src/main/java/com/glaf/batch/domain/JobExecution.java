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

import com.glaf.batch.util.JobExecutionJsonFactory;

@Entity
@Table(name = "SYS_JOB_EXECUTION")
public class JobExecution implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "job_execution_id", nullable = false)
	protected long jobExecutionId;

	/**
	 * version
	 */
	@Column(name = "version")
	protected int version = 1;

	/**
	 * job_instance_id
	 */
	@Column(name = "job_instance_id")
	protected long jobInstanceId;

	/**
	 * create_time
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	protected Date createTime = new Date(System.currentTimeMillis());

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

	@javax.persistence.Transient
	protected Collection<JobExecutionParam> params = new HashSet<JobExecutionParam>();

	@javax.persistence.Transient
	protected List<StepExecution> steps = new ArrayList<StepExecution>();

	public JobExecution() {

	}

	public void addParam(JobExecutionParam param) {
		if (params == null) {
			params = new HashSet<JobExecutionParam>();
		}
		params.add(param);
	}

	public void addStep(StepExecution step) {
		if (steps == null) {
			steps = new ArrayList<StepExecution>();
		}
		steps.add(step);
	}

	public Date getCreateTime() {
		return this.createTime;
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

	public long getJobExecutionId() {
		return this.jobExecutionId;
	}

	public long getJobInstanceId() {
		return this.jobInstanceId;
	}

	public Date getLastUpdated() {
		return this.lastUpdated;
	}

	public Collection<JobExecutionParam> getParams() {
		return params;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public String getStatus() {
		return this.status;
	}

	public List<StepExecution> getSteps() {
		return steps;
	}

	public int getVersion() {
		return this.version;
	}

	public JobExecution jsonToObject(JSONObject jsonObject) {
		return JobExecutionJsonFactory.jsonToObject(jsonObject);
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public void setJobExecutionId(long jobExecutionId) {
		this.jobExecutionId = jobExecutionId;
	}

	public void setJobInstanceId(long jobInstanceId) {
		this.jobInstanceId = jobInstanceId;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public void setParams(Collection<JobExecutionParam> params) {
		this.params = params;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setSteps(List<StepExecution> steps) {
		this.steps = steps;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public JSONObject toJsonObject() {
		return JobExecutionJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return JobExecutionJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}