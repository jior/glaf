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

public class JobInstanceQuery extends BaseQuery {
	private static final long serialVersionUID = 1L;
	protected String jobNameLike;
	protected String jobKey;
	protected List<String> jobKeys;
	protected String sortOrder;

	public JobInstanceQuery() {

	}

	public String getJobKey() {
		return jobKey;
	}

	public List<String> getJobKeys() {
		return jobKeys;
	}

	public String getJobNameLike() {
		if (jobNameLike != null && jobNameLike.trim().length() > 0) {
			if (!jobNameLike.startsWith("%")) {
				jobNameLike = "%" + jobNameLike;
			}
			if (!jobNameLike.endsWith("%")) {
				jobNameLike = jobNameLike + "%";
			}
		}
		return jobNameLike;
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

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("jobInstanceId", "job_instance_id");
		addColumn("version", "version");
		addColumn("jobName", "job_name");
		addColumn("jobKey", "job_key");
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public JobInstanceQuery jobKey(String jobKey) {
		if (jobKey == null) {
			throw new RuntimeException("jobKey is null");
		}
		this.jobKey = jobKey;
		return this;
	}

	public JobInstanceQuery jobKeys(List<String> jobKeys) {
		if (jobKeys == null) {
			throw new RuntimeException("jobKeys is empty ");
		}
		this.jobKeys = jobKeys;
		return this;
	}

	public JobInstanceQuery jobNameLike(String jobNameLike) {
		if (jobNameLike == null) {
			throw new RuntimeException("jobName is null");
		}
		this.jobNameLike = jobNameLike;
		return this;
	}

	public void setJobKey(String jobKey) {
		this.jobKey = jobKey;
	}

	public void setJobKeys(List<String> jobKeys) {
		this.jobKeys = jobKeys;
	}

	public void setJobNameLike(String jobNameLike) {
		this.jobNameLike = jobNameLike;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

}