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

public class TransformStepQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String activityId;
	protected List<String> activityIds;
	protected String queryId;
	protected String titleLike;
	protected String descriptionLike;
	protected Integer priority;
	protected Integer priorityGreaterThanOrEqual;
	protected Integer priorityLessThanOrEqual;
	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;
	protected Integer lockedGreaterThanOrEqual;
	protected Integer lockedLessThanOrEqual;
	protected Integer revision;
	protected Integer revisionGreaterThanOrEqual;
	protected Integer revisionLessThanOrEqual;

	public TransformStepQuery() {

	}

	public TransformStepQuery activityId(String activityId) {
		if (activityId == null) {
			throw new RuntimeException("activityId is null");
		}
		this.activityId = activityId;
		return this;
	}

	public TransformStepQuery activityIds(List<String> activityIds) {
		if (activityIds == null) {
			throw new RuntimeException("activityIds is empty ");
		}
		this.activityIds = activityIds;
		return this;
	}

	public TransformStepQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public TransformStepQuery createTimeGreaterThanOrEqual(
			Date createTimeGreaterThanOrEqual) {
		if (createTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
		return this;
	}

	public TransformStepQuery createTimeLessThanOrEqual(
			Date createTimeLessThanOrEqual) {
		if (createTimeLessThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
		return this;
	}

	public TransformStepQuery descriptionLike(String descriptionLike) {
		if (descriptionLike == null) {
			throw new RuntimeException("description is null");
		}
		this.descriptionLike = descriptionLike;
		return this;
	}

	public String getActivityId() {
		return activityId;
	}

	public List<String> getActivityIds() {
		return activityIds;
	}

	public Date getCreateTimeGreaterThanOrEqual() {
		return createTimeGreaterThanOrEqual;
	}

	public Date getCreateTimeLessThanOrEqual() {
		return createTimeLessThanOrEqual;
	}

	public String getDescriptionLike() {
		if (descriptionLike != null && descriptionLike.trim().length() > 0) {
			if (!descriptionLike.startsWith("%")) {
				descriptionLike = "%" + descriptionLike;
			}
			if (!descriptionLike.endsWith("%")) {
				descriptionLike = descriptionLike + "%";
			}
		}
		return descriptionLike;
	}

	public Integer getLockedGreaterThanOrEqual() {
		return lockedGreaterThanOrEqual;
	}

	public Integer getLockedLessThanOrEqual() {
		return lockedLessThanOrEqual;
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

			if ("queryId".equals(sortField)) {
				orderBy = "E.QUERYID_" + a_x;
			}

			if ("title".equals(sortField)) {
				orderBy = "E.TITLE_" + a_x;
			}

			if ("description".equals(sortField)) {
				orderBy = "E.DESCRIPTION_" + a_x;
			}

			if ("priority".equals(sortField)) {
				orderBy = "E.PRIORITY_" + a_x;
			}

			if ("createTime".equals(sortField)) {
				orderBy = "E.CREATETIME_" + a_x;
			}

			if ("createBy".equals(sortField)) {
				orderBy = "E.CREATEBY_" + a_x;
			}

			if ("locked".equals(sortField)) {
				orderBy = "E.LOCKED_" + a_x;
			}

			if ("revision".equals(sortField)) {
				orderBy = "E.REVISION_" + a_x;
			}

		}
		return orderBy;
	}

	public Integer getPriority() {
		return priority;
	}

	public Integer getPriorityGreaterThanOrEqual() {
		return priorityGreaterThanOrEqual;
	}

	public Integer getPriorityLessThanOrEqual() {
		return priorityLessThanOrEqual;
	}

	public String getQueryId() {
		return queryId;
	}

	public Integer getRevision() {
		return revision;
	}

	public Integer getRevisionGreaterThanOrEqual() {
		return revisionGreaterThanOrEqual;
	}

	public Integer getRevisionLessThanOrEqual() {
		return revisionLessThanOrEqual;
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
		addColumn("queryId", "QUERYID_");
		addColumn("title", "TITLE_");
		addColumn("description", "DESCRIPTION_");
		addColumn("priority", "PRIORITY_");
		addColumn("createTime", "CREATETIME_");
		addColumn("createBy", "CREATEBY_");
		addColumn("locked", "LOCKED_");
		addColumn("revision", "REVISION_");
	}

	public TransformStepQuery lockedGreaterThanOrEqual(
			Integer lockedGreaterThanOrEqual) {
		if (lockedGreaterThanOrEqual == null) {
			throw new RuntimeException("locked is null");
		}
		this.lockedGreaterThanOrEqual = lockedGreaterThanOrEqual;
		return this;
	}

	public TransformStepQuery lockedLessThanOrEqual(
			Integer lockedLessThanOrEqual) {
		if (lockedLessThanOrEqual == null) {
			throw new RuntimeException("locked is null");
		}
		this.lockedLessThanOrEqual = lockedLessThanOrEqual;
		return this;
	}

	public TransformStepQuery priority(Integer priority) {
		if (priority == null) {
			throw new RuntimeException("priority is null");
		}
		this.priority = priority;
		return this;
	}

	public TransformStepQuery priorityGreaterThanOrEqual(
			Integer priorityGreaterThanOrEqual) {
		if (priorityGreaterThanOrEqual == null) {
			throw new RuntimeException("priority is null");
		}
		this.priorityGreaterThanOrEqual = priorityGreaterThanOrEqual;
		return this;
	}

	public TransformStepQuery priorityLessThanOrEqual(
			Integer priorityLessThanOrEqual) {
		if (priorityLessThanOrEqual == null) {
			throw new RuntimeException("priority is null");
		}
		this.priorityLessThanOrEqual = priorityLessThanOrEqual;
		return this;
	}

	public TransformStepQuery queryId(String queryId) {
		if (queryId == null) {
			throw new RuntimeException("queryId is null");
		}
		this.queryId = queryId;
		return this;
	}

	public TransformStepQuery revision(Integer revision) {
		if (revision == null) {
			throw new RuntimeException("revision is null");
		}
		this.revision = revision;
		return this;
	}

	public TransformStepQuery revisionGreaterThanOrEqual(
			Integer revisionGreaterThanOrEqual) {
		if (revisionGreaterThanOrEqual == null) {
			throw new RuntimeException("revision is null");
		}
		this.revisionGreaterThanOrEqual = revisionGreaterThanOrEqual;
		return this;
	}

	public TransformStepQuery revisionLessThanOrEqual(
			Integer revisionLessThanOrEqual) {
		if (revisionLessThanOrEqual == null) {
			throw new RuntimeException("revision is null");
		}
		this.revisionLessThanOrEqual = revisionLessThanOrEqual;
		return this;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public void setActivityIds(List<String> activityIds) {
		this.activityIds = activityIds;
	}

	public void setCreateTimeGreaterThanOrEqual(
			Date createTimeGreaterThanOrEqual) {
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
	}

	public void setCreateTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
	}

	public void setDescriptionLike(String descriptionLike) {
		this.descriptionLike = descriptionLike;
	}

	public void setLockedGreaterThanOrEqual(Integer lockedGreaterThanOrEqual) {
		this.lockedGreaterThanOrEqual = lockedGreaterThanOrEqual;
	}

	public void setLockedLessThanOrEqual(Integer lockedLessThanOrEqual) {
		this.lockedLessThanOrEqual = lockedLessThanOrEqual;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public void setPriorityGreaterThanOrEqual(Integer priorityGreaterThanOrEqual) {
		this.priorityGreaterThanOrEqual = priorityGreaterThanOrEqual;
	}

	public void setPriorityLessThanOrEqual(Integer priorityLessThanOrEqual) {
		this.priorityLessThanOrEqual = priorityLessThanOrEqual;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public void setRevision(Integer revision) {
		this.revision = revision;
	}

	public void setRevisionGreaterThanOrEqual(Integer revisionGreaterThanOrEqual) {
		this.revisionGreaterThanOrEqual = revisionGreaterThanOrEqual;
	}

	public void setRevisionLessThanOrEqual(Integer revisionLessThanOrEqual) {
		this.revisionLessThanOrEqual = revisionLessThanOrEqual;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public TransformStepQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

}