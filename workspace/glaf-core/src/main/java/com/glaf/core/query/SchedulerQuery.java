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

import java.util.Date;
import java.util.List;

public class SchedulerQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String contentLike;
	protected Date endDateGreaterThanOrEqual;
	protected Date endDateLessThanOrEqual;
	protected String jobClassLike;
	protected Date startDateGreaterThanOrEqual;
	protected Date startDateLessThanOrEqual;
	protected String taskId;
	protected List<String> taskIds;
	protected String taskNameLike;
	protected String taskType;
	protected String titleLike;
	protected Integer runType;
	protected Integer runStatus;

	public SchedulerQuery() {

	}

	public SchedulerQuery contentLike(String contentLike) {
		if (contentLike == null) {
			throw new RuntimeException("content is null");
		}
		this.contentLike = contentLike;
		return this;
	}

	public SchedulerQuery endDateGreaterThanOrEqual(
			Date endDateGreaterThanOrEqual) {
		if (endDateGreaterThanOrEqual == null) {
			throw new RuntimeException("endDate is null");
		}
		this.endDateGreaterThanOrEqual = endDateGreaterThanOrEqual;
		return this;
	}

	public SchedulerQuery endDateLessThanOrEqual(Date endDateLessThanOrEqual) {
		if (endDateLessThanOrEqual == null) {
			throw new RuntimeException("endDate is null");
		}
		this.endDateLessThanOrEqual = endDateLessThanOrEqual;
		return this;
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

	public Date getEndDateGreaterThanOrEqual() {
		return endDateGreaterThanOrEqual;
	}

	public Date getEndDateLessThanOrEqual() {
		return endDateLessThanOrEqual;
	}

	public String getJobClassLike() {
		return jobClassLike;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("attribute".equals(sortColumn)) {
				orderBy = "E.ATTRIBUTE_" + a_x;
			}

			if ("autoStartup".equals(sortColumn)) {
				orderBy = "E.AUTOSTARTUP" + a_x;
			}

			if ("content".equals(sortColumn)) {
				orderBy = "E.CONTENT" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.CREATEBY" + a_x;
			}

			if ("createDate".equals(sortColumn)) {
				orderBy = "E.CREATEDATE" + a_x;
			}

			if ("endDate".equals(sortColumn)) {
				orderBy = "E.ENDDATE" + a_x;
			}

			if ("expression".equals(sortColumn)) {
				orderBy = "E.EXPRESSION_" + a_x;
			}

			if ("intervalTime".equals(sortColumn)) {
				orderBy = "E.INTERVALTIME" + a_x;
			}

			if ("intervalType".equals(sortColumn)) {
				orderBy = "E.INTERVALTYPE" + a_x;
			}

			if ("intervalValue".equals(sortColumn)) {
				orderBy = "E.INTERVALVALUE" + a_x;
			}

			if ("jobClass".equals(sortColumn)) {
				orderBy = "E.JOBCLASS" + a_x;
			}

			if ("locked".equals(sortColumn)) {
				orderBy = "E.LOCKED_" + a_x;
			}

			if ("priority".equals(sortColumn)) {
				orderBy = "E.PRIORITY_" + a_x;
			}

			if ("repeatCount".equals(sortColumn)) {
				orderBy = "E.REPEATCOUNT" + a_x;
			}

			if ("repeatInterval".equals(sortColumn)) {
				orderBy = "E.REPEATINTERVAL" + a_x;
			}

			if ("startDate".equals(sortColumn)) {
				orderBy = "E.STARTDATE" + a_x;
			}

			if ("startDelay".equals(sortColumn)) {
				orderBy = "E.STARTDELAY" + a_x;
			}

			if ("startup".equals(sortColumn)) {
				orderBy = "E.STARTUP_" + a_x;
			}

			if ("taskName".equals(sortColumn)) {
				orderBy = "E.TASKNAME" + a_x;
			}

			if ("taskType".equals(sortColumn)) {
				orderBy = "E.TASKTYPE" + a_x;
			}

			if ("threadSize".equals(sortColumn)) {
				orderBy = "E.THREADSIZE" + a_x;
			}

			if ("title".equals(sortColumn)) {
				orderBy = "E.TITLE" + a_x;
			}

			if ("runType".equals(sortColumn)) {
				orderBy = "E.RUNTYPE" + a_x;
			}

			if ("runStatus".equals(sortColumn)) {
				orderBy = "E.RUNSTATUS" + a_x;
			}

			if ("jobRunTime".equals(sortColumn)) {
				orderBy = "E.JOBRUNTIME" + a_x;
			}

			if ("nextFireTime".equals(sortColumn)) {
				orderBy = "E.NEXTFIRETIME" + a_x;
			}

			if ("previousFireTime".equals(sortColumn)) {
				orderBy = "E.PREVIOUSFIRETIME" + a_x;
			}

		}
		return orderBy;
	}

	public Integer getRunStatus() {
		return runStatus;
	}

	public Integer getRunType() {
		return runType;
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

	public List<String> getTaskIds() {
		return taskIds;
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

	public String getTaskType() {
		return taskType;
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
		addColumn("id", "ID");
		addColumn("attribute", "ATTRIBUTE_");
		addColumn("autoStartup", "AUTOSTARTUP");
		addColumn("content", "CONTENT");
		addColumn("createBy", "CREATEBY");
		addColumn("createDate", "CREATEDATE");
		addColumn("endDate", "ENDDATE");
		addColumn("expression", "EXPRESSION_");
		addColumn("intervalTime", "INTERVALTIME");
		addColumn("intervalType", "INTERVALTYPE");
		addColumn("intervalValue", "INTERVALVALUE");
		addColumn("jobClass", "JOBCLASS");
		addColumn("locked", "LOCKED_");
		addColumn("priority", "PRIORITY_");
		addColumn("repeatCount", "REPEATCOUNT");
		addColumn("repeatInterval", "REPEATINTERVAL");
		addColumn("startDate", "STARTDATE");
		addColumn("startDelay", "STARTDELAY");
		addColumn("startup", "STARTUP_");
		addColumn("taskName", "TASKNAME");
		addColumn("taskType", "TASKTYPE");
		addColumn("threadSize", "THREADSIZE");
		addColumn("title", "TITLE");
		addColumn("runType", "RUNTYPE");
		addColumn("runStatus", "RUNSTATUS");
		addColumn("jobRunTime", "JOBRUNTIME");
		addColumn("nextFireTime", "NEXTFIRETIME");
		addColumn("previousFireTime", "PREVIOUSFIRETIME");
	}

	public SchedulerQuery jobClassLike(String jobClassLike) {
		if (jobClassLike == null) {
			throw new RuntimeException("jobClass is null");
		}
		this.jobClassLike = jobClassLike;
		return this;
	}

	public SchedulerQuery runStatus(Integer runStatus) {
		if (runStatus == null) {
			throw new RuntimeException("runStatus is null");
		}
		this.runStatus = runStatus;
		return this;
	}

	public SchedulerQuery runType(Integer runType) {
		if (runType == null) {
			throw new RuntimeException("runType is null");
		}
		this.runType = runType;
		return this;
	}

	public void setContentLike(String contentLike) {
		this.contentLike = contentLike;
	}

	public void setEndDateGreaterThanOrEqual(Date endDateGreaterThanOrEqual) {
		this.endDateGreaterThanOrEqual = endDateGreaterThanOrEqual;
	}

	public void setEndDateLessThanOrEqual(Date endDateLessThanOrEqual) {
		this.endDateLessThanOrEqual = endDateLessThanOrEqual;
	}

	public void setJobClassLike(String jobClassLike) {
		this.jobClassLike = jobClassLike;
	}

	public void setRunStatus(Integer runStatus) {
		this.runStatus = runStatus;
	}

	public void setRunType(Integer runType) {
		this.runType = runType;
	}

	public void setStartDateGreaterThanOrEqual(Date startDateGreaterThanOrEqual) {
		this.startDateGreaterThanOrEqual = startDateGreaterThanOrEqual;
	}

	public void setStartDateLessThanOrEqual(Date startDateLessThanOrEqual) {
		this.startDateLessThanOrEqual = startDateLessThanOrEqual;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setTaskIds(List<String> taskIds) {
		this.taskIds = taskIds;
	}

	public void setTaskNameLike(String taskNameLike) {
		this.taskNameLike = taskNameLike;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public SchedulerQuery startDateGreaterThanOrEqual(
			Date startDateGreaterThanOrEqual) {
		if (startDateGreaterThanOrEqual == null) {
			throw new RuntimeException("startDate is null");
		}
		this.startDateGreaterThanOrEqual = startDateGreaterThanOrEqual;
		return this;
	}

	public SchedulerQuery startDateLessThanOrEqual(Date startDateLessThanOrEqual) {
		if (startDateLessThanOrEqual == null) {
			throw new RuntimeException("startDate is null");
		}
		this.startDateLessThanOrEqual = startDateLessThanOrEqual;
		return this;
	}

	public SchedulerQuery taskId(String taskId) {
		if (taskId == null) {
			throw new RuntimeException("taskId is null");
		}
		this.taskId = taskId;
		return this;
	}

	public SchedulerQuery taskIds(List<String> taskIds) {
		if (taskIds == null) {
			throw new RuntimeException("taskIds is empty ");
		}
		this.taskIds = taskIds;
		return this;
	}

	public SchedulerQuery taskNameLike(String taskNameLike) {
		if (taskNameLike == null) {
			throw new RuntimeException("taskName is null");
		}
		this.taskNameLike = taskNameLike;
		return this;
	}

	public SchedulerQuery taskType(String taskType) {
		if (taskType == null) {
			throw new RuntimeException("taskType is null");
		}
		this.taskType = taskType;
		return this;
	}

	public SchedulerQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

}