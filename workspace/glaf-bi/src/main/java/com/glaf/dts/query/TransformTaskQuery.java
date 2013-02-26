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

package com.glaf.dts.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class TransformTaskQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String activityId;
	protected List<String> activityIds;
	protected String stepId;
	protected List<String> stepIds;
	protected String queryId;
	protected List<String> queryIds;
	protected String titleLike;
	protected Date startTimeGreaterThanOrEqual;
	protected Date startTimeLessThanOrEqual;
	protected Date endTimeGreaterThanOrEqual;
	protected Date endTimeLessThanOrEqual;

	public TransformTaskQuery() {

	}

	public TransformTaskQuery activityId(String activityId) {
		if (activityId == null) {
			throw new RuntimeException("activityId is null");
		}
		this.activityId = activityId;
		return this;
	}

	public TransformTaskQuery activityIds(List<String> activityIds) {
		if (activityIds == null) {
			throw new RuntimeException("activityIds is empty ");
		}
		this.activityIds = activityIds;
		return this;
	}

	public TransformTaskQuery endTimeGreaterThanOrEqual(
			Date endTimeGreaterThanOrEqual) {
		if (endTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("endTime is null");
		}
		this.endTimeGreaterThanOrEqual = endTimeGreaterThanOrEqual;
		return this;
	}

	public TransformTaskQuery endTimeLessThanOrEqual(Date endTimeLessThanOrEqual) {
		if (endTimeLessThanOrEqual == null) {
			throw new RuntimeException("endTime is null");
		}
		this.endTimeLessThanOrEqual = endTimeLessThanOrEqual;
		return this;
	}

	public String getActivityId() {
		return activityId;
	}

	public List<String> getActivityIds() {
		return activityIds;
	}

	public Date getEndTimeGreaterThanOrEqual() {
		return endTimeGreaterThanOrEqual;
	}

	public Date getEndTimeLessThanOrEqual() {
		return endTimeLessThanOrEqual;
	}

	public String getOrderBy() {
		if (sortField != null) {
			String a_x = " asc ";
			if (getSortOrder() != null) {
				a_x = " desc ";
			}

			if ("activityId".equals(sortField)) {
				orderBy = "E.ACTIVITYID_" + a_x;
			}

			if ("stepId".equals(sortField)) {
				orderBy = "E.STEPID_" + a_x;
			}

			if ("queryId".equals(sortField)) {
				orderBy = "E.QUERYID_" + a_x;
			}

			if ("title".equals(sortField)) {
				orderBy = "E.TITLE_" + a_x;
			}

			if ("parameter".equals(sortField)) {
				orderBy = "E.PARAMETER_" + a_x;
			}

			if ("startTime".equals(sortField)) {
				orderBy = "E.STARTTIME_" + a_x;
			}

			if ("endTime".equals(sortField)) {
				orderBy = "E.ENDTIME_" + a_x;
			}

			if ("status".equals(sortField)) {
				orderBy = "E.STATUS_" + a_x;
			}

		}
		return orderBy;
	}

	public String getQueryId() {
		return queryId;
	}

	public List<String> getQueryIds() {
		return queryIds;
	}

	public Date getStartTimeGreaterThanOrEqual() {
		return startTimeGreaterThanOrEqual;
	}

	public Date getStartTimeLessThanOrEqual() {
		return startTimeLessThanOrEqual;
	}

	public String getStepId() {
		return stepId;
	}

	public List<String> getStepIds() {
		return stepIds;
	}

	public String getTitleLike() {
		if (titleLike != null && titleLike.trim().length() > 0) {
			if (!titleLike.startsWith("%")) {
				titleLike = "%" + titleLike;
			}
			if (!titleLike.endsWith("%")) {
				titleLike = titleLike + "%";
			}
		}
		return titleLike;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("activityId", "ACTIVITYID_");
		addColumn("stepId", "STEPID_");
		addColumn("queryId", "QUERYID_");
		addColumn("title", "TITLE_");
		addColumn("parameter", "PARAMETER_");
		addColumn("startTime", "STARTTIME_");
		addColumn("endTime", "ENDTIME_");
		addColumn("status", "STATUS_");
	}

	public TransformTaskQuery queryId(String queryId) {
		if (queryId == null) {
			throw new RuntimeException("queryId is null");
		}
		this.queryId = queryId;
		return this;
	}

	public TransformTaskQuery queryIds(List<String> queryIds) {
		if (queryIds == null) {
			throw new RuntimeException("queryIds is empty ");
		}
		this.queryIds = queryIds;
		return this;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public void setActivityIds(List<String> activityIds) {
		this.activityIds = activityIds;
	}

	public void setEndTimeGreaterThanOrEqual(Date endTimeGreaterThanOrEqual) {
		this.endTimeGreaterThanOrEqual = endTimeGreaterThanOrEqual;
	}

	public void setEndTimeLessThanOrEqual(Date endTimeLessThanOrEqual) {
		this.endTimeLessThanOrEqual = endTimeLessThanOrEqual;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public void setQueryIds(List<String> queryIds) {
		this.queryIds = queryIds;
	}

	public void setStartTimeGreaterThanOrEqual(Date startTimeGreaterThanOrEqual) {
		this.startTimeGreaterThanOrEqual = startTimeGreaterThanOrEqual;
	}

	public void setStartTimeLessThanOrEqual(Date startTimeLessThanOrEqual) {
		this.startTimeLessThanOrEqual = startTimeLessThanOrEqual;
	}

	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

	public void setStepIds(List<String> stepIds) {
		this.stepIds = stepIds;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public TransformTaskQuery startTimeGreaterThanOrEqual(
			Date startTimeGreaterThanOrEqual) {
		if (startTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("startTime is null");
		}
		this.startTimeGreaterThanOrEqual = startTimeGreaterThanOrEqual;
		return this;
	}

	public TransformTaskQuery startTimeLessThanOrEqual(
			Date startTimeLessThanOrEqual) {
		if (startTimeLessThanOrEqual == null) {
			throw new RuntimeException("startTime is null");
		}
		this.startTimeLessThanOrEqual = startTimeLessThanOrEqual;
		return this;
	}

	public TransformTaskQuery stepId(String stepId) {
		if (stepId == null) {
			throw new RuntimeException("stepId is null");
		}
		this.stepId = stepId;
		return this;
	}

	public TransformTaskQuery stepIds(List<String> stepIds) {
		if (stepIds == null) {
			throw new RuntimeException("stepIds is empty ");
		}
		this.stepIds = stepIds;
		return this;
	}

	public TransformTaskQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

}