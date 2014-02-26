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

public class JobDefinitionQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> jobDefinitionIds;
	protected String jobKey;
	protected List<String> jobKeys;
	protected String jobName;
	protected List<String> jobNames;
	protected List<String> createBys;
	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;

	public JobDefinitionQuery() {

	}

	public JobDefinitionQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public JobDefinitionQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public JobDefinitionQuery createTimeGreaterThanOrEqual(
			Date createTimeGreaterThanOrEqual) {
		if (createTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
		return this;
	}

	public JobDefinitionQuery createTimeLessThanOrEqual(
			Date createTimeLessThanOrEqual) {
		if (createTimeLessThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
		return this;
	}

	public String getCreateBy() {
		return createBy;
	}

	public List<String> getCreateBys() {
		return createBys;
	}

	public Date getCreateTimeGreaterThanOrEqual() {
		return createTimeGreaterThanOrEqual;
	}

	public Date getCreateTimeLessThanOrEqual() {
		return createTimeLessThanOrEqual;
	}

	public String getJobKey() {
		return jobKey;
	}

	public List<String> getJobKeys() {
		return jobKeys;
	}

	public String getJobName() {
		return jobName;
	}

	public List<String> getJobNames() {
		return jobNames;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("jobKey".equals(sortColumn)) {
				orderBy = "E.JOB_KEY" + a_x;
			}

			if ("jobName".equals(sortColumn)) {
				orderBy = "E.JOB_NAME" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.CREATEBY" + a_x;
			}

			if ("createTime".equals(sortColumn)) {
				orderBy = "E.CREATETIME" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("jobDefinitionId", "JOB_DEFINITION_ID");
		addColumn("jobKey", "JOB_KEY");
		addColumn("jobName", "JOB_NAME");
		addColumn("createBy", "CREATEBY");
		addColumn("createTime", "CREATETIME");
	}

	public JobDefinitionQuery jobKey(String jobKey) {
		if (jobKey == null) {
			throw new RuntimeException("jobKey is null");
		}
		this.jobKey = jobKey;
		return this;
	}

	public JobDefinitionQuery jobKeys(List<String> jobKeys) {
		if (jobKeys == null) {
			throw new RuntimeException("jobKeys is empty ");
		}
		this.jobKeys = jobKeys;
		return this;
	}

	public JobDefinitionQuery jobName(String jobName) {
		if (jobName == null) {
			throw new RuntimeException("jobName is null");
		}
		this.jobName = jobName;
		return this;
	}

	public JobDefinitionQuery jobNames(List<String> jobNames) {
		if (jobNames == null) {
			throw new RuntimeException("jobNames is empty ");
		}
		this.jobNames = jobNames;
		return this;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateBys(List<String> createBys) {
		this.createBys = createBys;
	}

	public void setCreateTimeGreaterThanOrEqual(
			Date createTimeGreaterThanOrEqual) {
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
	}

	public void setCreateTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
	}

	public void setJobKey(String jobKey) {
		this.jobKey = jobKey;
	}

	public void setJobKeys(List<String> jobKeys) {
		this.jobKeys = jobKeys;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public void setJobNames(List<String> jobNames) {
		this.jobNames = jobNames;
	}

}