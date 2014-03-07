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
import java.util.Collection;

import javax.persistence.*;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.glaf.core.base.*;
import com.glaf.batch.util.*;

@Entity
@Table(name = "SYS_JOB_STEP_DEF")
public class StepDefinition implements Serializable, JSONable,
		java.lang.Comparable<StepDefinition> {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "STEP_DEFINITION_ID", nullable = false)
	protected Long stepDefinitionId;

	@Column(name = "JOB_DEFINITION_ID")
	protected Long jobDefinitionId;

	@Column(name = "STEP_KEY", length = 50)
	protected String stepKey;

	@Column(name = "STEP_NAME", length = 100)
	protected String stepName;

	@Column(name = "JOB_STEP_KEY", length = 200)
	protected String jobStepKey;

	@Column(name = "JOB_CLASS", length = 200)
	protected String jobClass;

	@Column(name = "LISTNO")
	protected Integer listno;

	@javax.persistence.Transient
	protected Collection<StepDefinitionParam> params = new java.util.concurrent.CopyOnWriteArraySet<StepDefinitionParam>();

	public StepDefinition() {

	}

	public void addParam(StepDefinitionParam param) {
		if (params == null) {
			params = new java.util.concurrent.CopyOnWriteArraySet<StepDefinitionParam>();
		}
		params.add(param);
	}

	@Override
	public int compareTo(StepDefinition o) {
		if (o == null) {
			return -1;
		}

		StepDefinition field = o;

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
		StepDefinition other = (StepDefinition) obj;
		if (stepDefinitionId == null) {
			if (other.stepDefinitionId != null)
				return false;
		} else if (!stepDefinitionId.equals(other.stepDefinitionId))
			return false;
		return true;
	}

	public String getJobClass() {
		return this.jobClass;
	}

	public Long getJobDefinitionId() {
		return this.jobDefinitionId;
	}

	public String getJobStepKey() {
		return this.jobStepKey;
	}

	public Integer getListno() {
		return this.listno;
	}

	public Collection<StepDefinitionParam> getParams() {
		return params;
	}

	public Long getStepDefinitionId() {
		return this.stepDefinitionId;
	}

	public String getStepKey() {
		return this.stepKey;
	}

	public String getStepName() {
		return this.stepName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((stepDefinitionId == null) ? 0 : stepDefinitionId.hashCode());
		return result;
	}

	public StepDefinition jsonToObject(JSONObject jsonObject) {
		return StepDefinitionJsonFactory.jsonToObject(jsonObject);
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public void setJobDefinitionId(Long jobDefinitionId) {
		this.jobDefinitionId = jobDefinitionId;
	}

	public void setJobStepKey(String jobStepKey) {
		this.jobStepKey = jobStepKey;
	}

	public void setListno(Integer listno) {
		this.listno = listno;
	}

	public void setParams(Collection<StepDefinitionParam> params) {
		this.params = params;
	}

	public void setStepDefinitionId(Long stepDefinitionId) {
		this.stepDefinitionId = stepDefinitionId;
	}

	public void setStepKey(String stepKey) {
		this.stepKey = stepKey;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public JSONObject toJsonObject() {
		return StepDefinitionJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return StepDefinitionJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
