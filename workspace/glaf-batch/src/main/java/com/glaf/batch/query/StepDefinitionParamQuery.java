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

public class StepDefinitionParamQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Long jobDefinitionId;
	protected List<Long> jobDefinitionIds;
	protected List<Long> stepDefinitionIds;
	protected String typeCd;
	protected List<String> typeCds;
	protected String keyName;
	protected List<String> keyNames;

	public StepDefinitionParamQuery() {

	}

	public Long getJobDefinitionId() {
		return jobDefinitionId;
	}

	public List<Long> getJobDefinitionIds() {
		return jobDefinitionIds;
	}

	public String getKeyName() {
		return keyName;
	}

	public List<String> getKeyNames() {
		return keyNames;
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

			if ("stepDefinitionId".equals(sortColumn)) {
				orderBy = "E.STEP_DEFINITION_ID" + a_x;
			}

			if ("typeCd".equals(sortColumn)) {
				orderBy = "E.TYPE_CD" + a_x;
			}

			if ("keyName".equals(sortColumn)) {
				orderBy = "E.KEY_NAME" + a_x;
			}

			if ("stringVal".equals(sortColumn)) {
				orderBy = "E.STRING_VAL" + a_x;
			}

			if ("textVal".equals(sortColumn)) {
				orderBy = "E.TEXT_VAL" + a_x;
			}

			if ("dateVal".equals(sortColumn)) {
				orderBy = "E.DATE_VAL" + a_x;
			}

			if ("intVal".equals(sortColumn)) {
				orderBy = "E.INT_VAL" + a_x;
			}

			if ("longVal".equals(sortColumn)) {
				orderBy = "E.LONG_VAL" + a_x;
			}

			if ("doubleVal".equals(sortColumn)) {
				orderBy = "E.DOUBLE_VAL" + a_x;
			}

		}
		return orderBy;
	}

	public List<Long> getStepDefinitionIds() {
		return stepDefinitionIds;
	}

	public String getTypeCd() {
		return typeCd;
	}

	public List<String> getTypeCds() {
		return typeCds;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("jobDefinitionId", "JOB_DEFINITION_ID");
		addColumn("stepDefinitionId", "STEP_DEFINITION_ID");
		addColumn("typeCd", "TYPE_CD");
		addColumn("keyName", "KEY_NAME");
		addColumn("stringVal", "STRING_VAL");
		addColumn("textVal", "TEXT_VAL");
		addColumn("dateVal", "DATE_VAL");
		addColumn("intVal", "INT_VAL");
		addColumn("longVal", "LONG_VAL");
		addColumn("doubleVal", "DOUBLE_VAL");
	}

	public StepDefinitionParamQuery jobDefinitionId(Long jobDefinitionId) {
		if (jobDefinitionId == null) {
			throw new RuntimeException("jobDefinitionId is null");
		}
		this.jobDefinitionId = jobDefinitionId;
		return this;
	}

	public StepDefinitionParamQuery jobDefinitionIds(
			List<Long> jobDefinitionIds) {
		if (jobDefinitionIds == null) {
			throw new RuntimeException("jobDefinitionIds is empty ");
		}
		this.jobDefinitionIds = jobDefinitionIds;
		return this;
	}

	public StepDefinitionParamQuery keyName(String keyName) {
		if (keyName == null) {
			throw new RuntimeException("keyName is null");
		}
		this.keyName = keyName;
		return this;
	}

	public StepDefinitionParamQuery keyNames(List<String> keyNames) {
		if (keyNames == null) {
			throw new RuntimeException("keyNames is empty ");
		}
		this.keyNames = keyNames;
		return this;
	}

	public void setJobDefinitionId(Long jobDefinitionId) {
		this.jobDefinitionId = jobDefinitionId;
	}

	public void setJobDefinitionIds(List<Long> jobDefinitionIds) {
		this.jobDefinitionIds = jobDefinitionIds;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public void setKeyNames(List<String> keyNames) {
		this.keyNames = keyNames;
	}

	public void setStepDefinitionIds(List<Long> stepDefinitionIds) {
		this.stepDefinitionIds = stepDefinitionIds;
	}

	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}

	public void setTypeCds(List<String> typeCds) {
		this.typeCds = typeCds;
	}

	public StepDefinitionParamQuery stepDefinitionIds(
			List<Long> stepDefinitionIds) {
		if (stepDefinitionIds == null) {
			throw new RuntimeException("stepDefinitionIds is empty ");
		}
		this.stepDefinitionIds = stepDefinitionIds;
		return this;
	}

	public StepDefinitionParamQuery typeCd(String typeCd) {
		if (typeCd == null) {
			throw new RuntimeException("typeCd is null");
		}
		this.typeCd = typeCd;
		return this;
	}

	public StepDefinitionParamQuery typeCds(List<String> typeCds) {
		if (typeCds == null) {
			throw new RuntimeException("typeCds is empty ");
		}
		this.typeCds = typeCds;
		return this;
	}

}