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
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.batch.util.*;

@Entity
@Table(name = "SYS_JOB_DEF")
public class JobDefinition implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "JOB_DEFINITION_ID", nullable = false)
	protected Long jobDefinitionId;

	@Column(name = "JOB_KEY", length = 50)
	protected String jobKey;

	@Column(name = "JOB_NAME", length = 100)
	protected String jobName;

	@Column(name = "CREATEBY", length = 50)
	protected String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME")
	protected Date createTime;

	@javax.persistence.Transient
	protected List<StepDefinition> steps = new java.util.ArrayList<StepDefinition>();

	public JobDefinition() {

	}

	public void addStep(StepDefinition step) {
		if (steps == null) {
			steps = new java.util.ArrayList<StepDefinition>();
		}
		steps.add(step);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobDefinition other = (JobDefinition) obj;
		if (jobDefinitionId == null) {
			if (other.jobDefinitionId != null)
				return false;
		} else if (!jobDefinitionId.equals(other.jobDefinitionId))
			return false;
		return true;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public Long getJobDefinitionId() {
		return this.jobDefinitionId;
	}

	public String getJobKey() {
		return this.jobKey;
	}

	public String getJobName() {
		return this.jobName;
	}

	public List<StepDefinition> getSteps() {
		return steps;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((jobDefinitionId == null) ? 0 : jobDefinitionId.hashCode());
		return result;
	}

	public JobDefinition jsonToObject(JSONObject jsonObject) {
		return JobDefinitionJsonFactory.jsonToObject(jsonObject);
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setJobDefinitionId(Long jobDefinitionId) {
		this.jobDefinitionId = jobDefinitionId;
	}

	public void setJobKey(String jobKey) {
		this.jobKey = jobKey;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public void setSteps(List<StepDefinition> steps) {
		this.steps = steps;
	}

	public JSONObject toJsonObject() {
		return JobDefinitionJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return JobDefinitionJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
