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
import com.glaf.core.query.DataQuery;

public class JobStepDefinitionQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> stepDefinitionIds;
	protected Long jobDefinitionId;
	protected List<Long> jobDefinitionIds;
	protected String stepKey;
	protected List<String> stepKeys;
	protected String stepName;
	protected List<String> stepNames;
	protected String jobStepKey;
	protected List<String> jobStepKeys;

	public JobStepDefinitionQuery() {

	}

	public Long getJobDefinitionId() {
		return jobDefinitionId;
	}

	public List<Long> getJobDefinitionIds() {
		return jobDefinitionIds;
	}

	public String getJobStepKey() {
		return jobStepKey;
	}

	public List<String> getJobStepKeys() {
		return jobStepKeys;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("jobDefinitionId".equals(sortColumn)) {
				orderBy = "E.JOB_DEFINITION_ID" + a_x;
			}

			if ("stepKey".equals(sortColumn)) {
				orderBy = "E.STEP_KEY" + a_x;
			}

			if ("stepName".equals(sortColumn)) {
				orderBy = "E.STEP_NAME" + a_x;
			}

			if ("jobStepKey".equals(sortColumn)) {
				orderBy = "E.JOB_STEP_KEY" + a_x;
			}

			if ("jobClass".equals(sortColumn)) {
				orderBy = "E.JOB_CLASS" + a_x;
			}

			if ("listno".equals(sortColumn)) {
				orderBy = "E.LISTNO" + a_x;
			}

		}
		return orderBy;
	}

	public String getStepKey() {
		return stepKey;
	}

	public List<String> getStepKeys() {
		return stepKeys;
	}

	public String getStepName() {
		return stepName;
	}

	public List<String> getStepNames() {
		return stepNames;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("stepDefinitionId", "STEP_DEFINITION_ID");
		addColumn("jobDefinitionId", "JOB_DEFINITION_ID");
		addColumn("stepKey", "STEP_KEY");
		addColumn("stepName", "STEP_NAME");
		addColumn("jobStepKey", "JOB_STEP_KEY");
		addColumn("jobClass", "JOB_CLASS");
		addColumn("listno", "LISTNO");
	}

	public JobStepDefinitionQuery jobDefinitionId(Long jobDefinitionId) {
		if (jobDefinitionId == null) {
			throw new RuntimeException("jobDefinitionId is null");
		}
		this.jobDefinitionId = jobDefinitionId;
		return this;
	}

	public JobStepDefinitionQuery jobDefinitionIds(List<Long> jobDefinitionIds) {
		if (jobDefinitionIds == null) {
			throw new RuntimeException("jobDefinitionIds is empty ");
		}
		this.jobDefinitionIds = jobDefinitionIds;
		return this;
	}

	public JobStepDefinitionQuery jobStepKey(String jobStepKey) {
		if (jobStepKey == null) {
			throw new RuntimeException("jobStepKey is null");
		}
		this.jobStepKey = jobStepKey;
		return this;
	}

	public JobStepDefinitionQuery jobStepKeys(List<String> jobStepKeys) {
		if (jobStepKeys == null) {
			throw new RuntimeException("jobStepKeys is empty ");
		}
		this.jobStepKeys = jobStepKeys;
		return this;
	}

	public void setJobDefinitionId(Long jobDefinitionId) {
		this.jobDefinitionId = jobDefinitionId;
	}

	public void setJobDefinitionIds(List<Long> jobDefinitionIds) {
		this.jobDefinitionIds = jobDefinitionIds;
	}

	public void setJobStepKey(String jobStepKey) {
		this.jobStepKey = jobStepKey;
	}

	public void setJobStepKeys(List<String> jobStepKeys) {
		this.jobStepKeys = jobStepKeys;
	}

	public void setStepKey(String stepKey) {
		this.stepKey = stepKey;
	}

	public void setStepKeys(List<String> stepKeys) {
		this.stepKeys = stepKeys;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public void setStepNames(List<String> stepNames) {
		this.stepNames = stepNames;
	}

	public JobStepDefinitionQuery stepKey(String stepKey) {
		if (stepKey == null) {
			throw new RuntimeException("stepKey is null");
		}
		this.stepKey = stepKey;
		return this;
	}

	public JobStepDefinitionQuery stepKeys(List<String> stepKeys) {
		if (stepKeys == null) {
			throw new RuntimeException("stepKeys is empty ");
		}
		this.stepKeys = stepKeys;
		return this;
	}

	public JobStepDefinitionQuery stepName(String stepName) {
		if (stepName == null) {
			throw new RuntimeException("stepName is null");
		}
		this.stepName = stepName;
		return this;
	}

	public JobStepDefinitionQuery stepNames(List<String> stepNames) {
		if (stepNames == null) {
			throw new RuntimeException("stepNames is empty ");
		}
		this.stepNames = stepNames;
		return this;
	}

}