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

package com.glaf.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class SchedulerLogQuery extends DataQuery {
	private static final long serialVersionUID = 1L;

	protected Collection<String> appActorIds;
	protected Date startDateGreaterThanOrEqual;
	protected Date startDateLessThanOrEqual;
	protected Date endDateGreaterThanOrEqual;
	protected Date endDateLessThanOrEqual;

	protected String taskId;
	protected String taskName;
	protected String taskNameLike;

	protected String title;
	protected String titleLike;

	protected String contentLike;

	protected String exitCode;

	protected String exitMessageLike;

	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;

	public SchedulerLogQuery() {

	}

	public SchedulerLogQuery contentLike(String contentLike) {
		if (contentLike == null) {
			throw new RuntimeException("content is null");
		}
		this.contentLike = contentLike;
		return this;
	}

	public SchedulerLogQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public SchedulerLogQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public SchedulerLogQuery endDateGreaterThanOrEqual(
			Date endDateGreaterThanOrEqual) {
		if (endDateGreaterThanOrEqual == null) {
			throw new RuntimeException("endDate is null");
		}
		this.endDateGreaterThanOrEqual = endDateGreaterThanOrEqual;
		return this;
	}

	public SchedulerLogQuery endDateLessThanOrEqual(Date endDateLessThanOrEqual) {
		if (endDateLessThanOrEqual == null) {
			throw new RuntimeException("endDate is null");
		}
		this.endDateLessThanOrEqual = endDateLessThanOrEqual;
		return this;
	}

	public SchedulerLogQuery exitCode(String exitCode) {
		if (exitCode == null) {
			throw new RuntimeException("exitCode is null");
		}
		this.exitCode = exitCode;
		return this;
	}

	public SchedulerLogQuery exitMessageLike(String exitMessageLike) {
		if (exitMessageLike == null) {
			throw new RuntimeException("exitMessage is null");
		}
		this.exitMessageLike = exitMessageLike;
		return this;
	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public String getContentLike() {
		if (contentLike != null && contentLike.trim().length() > 0) {
			if (!contentLike.startsWith("%")) {
				contentLike = "%" + contentLike;
			}
			if (!contentLike.endsWith("%")) {
				contentLike = contentLike + "%";
			}
		}
		return contentLike;
	}

	public Date getCreateDateGreaterThanOrEqual() {
		return createDateGreaterThanOrEqual;
	}

	public Date getCreateDateLessThanOrEqual() {
		return createDateLessThanOrEqual;
	}

	public Date getEndDateGreaterThanOrEqual() {
		return endDateGreaterThanOrEqual;
	}

	public Date getEndDateLessThanOrEqual() {
		return endDateLessThanOrEqual;
	}

	public String getExitCode() {
		return exitCode;
	}

	public String getExitMessageLike() {
		if (exitMessageLike != null && exitMessageLike.trim().length() > 0) {
			if (!exitMessageLike.startsWith("%")) {
				exitMessageLike = "%" + exitMessageLike;
			}
			if (!exitMessageLike.endsWith("%")) {
				exitMessageLike = exitMessageLike + "%";
			}
		}
		return exitMessageLike;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("startDate".equals(sortColumn)) {
				orderBy = "E.STARTDATE_" + a_x;
			}

			if ("endDate".equals(sortColumn)) {
				orderBy = "E.ENDDATE_" + a_x;
			}

			if ("jobRunTime".equals(sortColumn)) {
				orderBy = "E.JOBRUNTIME" + a_x;
			}

			if ("status".equals(sortColumn)) {
				orderBy = "E.STATUS_" + a_x;
			}

			if ("taskId".equals(sortColumn)) {
				orderBy = "E.TASKID_" + a_x;
			}

			if ("taskName".equals(sortColumn)) {
				orderBy = "E.TASKNAME_" + a_x;
			}

			if ("title".equals(sortColumn)) {
				orderBy = "E.TITLE_" + a_x;
			}

			if ("content".equals(sortColumn)) {
				orderBy = "E.CONTENT_" + a_x;
			}

			if ("exitCode".equals(sortColumn)) {
				orderBy = "E.EXITCODE_" + a_x;
			}

			if ("exitMessage".equals(sortColumn)) {
				orderBy = "E.EXITMESSAGE_" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.CREATEBY_" + a_x;
			}

			if ("createDate".equals(sortColumn)) {
				orderBy = "E.CREATEDATE_" + a_x;
			}

		}
		return orderBy;
	}

	public Date getStartDateGreaterThanOrEqual() {
		return startDateGreaterThanOrEqual;
	}

	public Date getStartDateLessThanOrEqual() {
		return startDateLessThanOrEqual;
	}

	public String getTaskId() {
		return taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getTaskNameLike() {
		if (taskNameLike != null && taskNameLike.trim().length() > 0) {
			if (!taskNameLike.startsWith("%")) {
				taskNameLike = "%" + taskNameLike;
			}
			if (!taskNameLike.endsWith("%")) {
				taskNameLike = taskNameLike + "%";
			}
		}
		return taskNameLike;
	}

	public String getTitle() {
		return title;
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
		addColumn("taskId", "TASKID_");
		addColumn("startDate", "STARTDATE_");
		addColumn("endDate", "ENDDATE_");
		addColumn("jobRunTime", "JOBRUNTIME");
		addColumn("status", "STATUS_");
		addColumn("taskName", "TASKNAME_");
		addColumn("title", "TITLE_");
		addColumn("content", "CONTENT_");
		addColumn("exitCode", "EXITCODE_");
		addColumn("exitMessage", "EXITMESSAGE_");
		addColumn("createBy", "CREATEBY_");
		addColumn("createDate", "CREATEDATE_");
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public void setContentLike(String contentLike) {
		this.contentLike = contentLike;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
	}

	public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
	}

	public void setEndDateGreaterThanOrEqual(Date endDateGreaterThanOrEqual) {
		this.endDateGreaterThanOrEqual = endDateGreaterThanOrEqual;
	}

	public void setEndDateLessThanOrEqual(Date endDateLessThanOrEqual) {
		this.endDateLessThanOrEqual = endDateLessThanOrEqual;
	}

	public void setExitCode(String exitCode) {
		this.exitCode = exitCode;
	}

	public void setExitMessageLike(String exitMessageLike) {
		this.exitMessageLike = exitMessageLike;
	}

	public void setStartDateGreaterThanOrEqual(Date startDateGreaterThanOrEqual) {
		this.startDateGreaterThanOrEqual = startDateGreaterThanOrEqual;
	}

	public void setStartDateLessThanOrEqual(Date startDateLessThanOrEqual) {
		this.startDateLessThanOrEqual = startDateLessThanOrEqual;
	}

	public void setStatusGreaterThanOrEqual(Integer statusGreaterThanOrEqual) {
		this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
	}

	public void setStatusLessThanOrEqual(Integer statusLessThanOrEqual) {
		this.statusLessThanOrEqual = statusLessThanOrEqual;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setTaskNameLike(String taskNameLike) {
		this.taskNameLike = taskNameLike;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public SchedulerLogQuery startDateGreaterThanOrEqual(
			Date startDateGreaterThanOrEqual) {
		if (startDateGreaterThanOrEqual == null) {
			throw new RuntimeException("startDate is null");
		}
		this.startDateGreaterThanOrEqual = startDateGreaterThanOrEqual;
		return this;
	}

	public SchedulerLogQuery startDateLessThanOrEqual(
			Date startDateLessThanOrEqual) {
		if (startDateLessThanOrEqual == null) {
			throw new RuntimeException("startDate is null");
		}
		this.startDateLessThanOrEqual = startDateLessThanOrEqual;
		return this;
	}

	public SchedulerLogQuery taskId(String taskId) {
		if (taskId == null) {
			throw new RuntimeException("taskId is null");
		}
		this.taskId = taskId;
		return this;
	}

	public SchedulerLogQuery taskName(String taskName) {
		if (taskName == null) {
			throw new RuntimeException("taskName is null");
		}
		this.taskName = taskName;
		return this;
	}

	public SchedulerLogQuery taskNameLike(String taskNameLike) {
		if (taskNameLike == null) {
			throw new RuntimeException("taskName is null");
		}
		this.taskNameLike = taskNameLike;
		return this;
	}

	public SchedulerLogQuery title(String title) {
		if (title == null) {
			throw new RuntimeException("title is null");
		}
		this.title = title;
		return this;
	}

	public SchedulerLogQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

}