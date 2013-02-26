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
package com.glaf.report.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ReportFileQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String reportId;
	protected List<String> reportIds;
	protected String filenameLike;
	protected Integer fileSizeGreaterThanOrEqual;
	protected Integer fileSizeLessThanOrEqual;
	protected Integer reportYearMonthDay;
	protected Integer reportYearMonthDayGreaterThanOrEqual;
	protected Integer reportYearMonthDayLessThanOrEqual;
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;

	public ReportFileQuery() {

	}

	public ReportFileQuery createDate(Date createDate) {
		if (createDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDate = createDate;
		return this;
	}

	public ReportFileQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public ReportFileQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public ReportFileQuery filenameLike(String filenameLike) {
		if (filenameLike == null) {
			throw new RuntimeException("filename is null");
		}
		this.filenameLike = filenameLike;
		return this;
	}

	public ReportFileQuery fileSizeGreaterThanOrEqual(
			Integer fileSizeGreaterThanOrEqual) {
		if (fileSizeGreaterThanOrEqual == null) {
			throw new RuntimeException("fileSize is null");
		}
		this.fileSizeGreaterThanOrEqual = fileSizeGreaterThanOrEqual;
		return this;
	}

	public ReportFileQuery fileSizeLessThanOrEqual(
			Integer fileSizeLessThanOrEqual) {
		if (fileSizeLessThanOrEqual == null) {
			throw new RuntimeException("fileSize is null");
		}
		this.fileSizeLessThanOrEqual = fileSizeLessThanOrEqual;
		return this;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Date getCreateDateGreaterThanOrEqual() {
		return createDateGreaterThanOrEqual;
	}

	public Date getCreateDateLessThanOrEqual() {
		return createDateLessThanOrEqual;
	}

	public String getFilenameLike() {
		if (filenameLike != null && filenameLike.trim().length() > 0) {
			if (!filenameLike.startsWith("%")) {
				filenameLike = "%" + filenameLike;
			}
			if (!filenameLike.endsWith("%")) {
				filenameLike = filenameLike + "%";
			}
		}
		return filenameLike;
	}

	public Integer getFileSizeGreaterThanOrEqual() {
		return fileSizeGreaterThanOrEqual;
	}

	public Integer getFileSizeLessThanOrEqual() {
		return fileSizeLessThanOrEqual;
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

	public String getReportId() {
		return reportId;
	}

	public List<String> getReportIds() {
		return reportIds;
	}

	public Integer getReportYearMonthDay() {
		return reportYearMonthDay;
	}

	public Integer getReportYearMonthDayGreaterThanOrEqual() {
		return reportYearMonthDayGreaterThanOrEqual;
	}

	public Integer getReportYearMonthDayLessThanOrEqual() {
		return reportYearMonthDayLessThanOrEqual;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("reportId", "REPORTID_");
		addColumn("filename", "FILENAME_");
		addColumn("fileSize", "FILESIZE_");
		addColumn("reportYearMonthDay", "REPORTYEARMONTHDAY_");
		addColumn("createDate", "CREATEDATE_");
	}

	public ReportFileQuery reportId(String reportId) {
		if (reportId == null) {
			throw new RuntimeException("reportId is null");
		}
		this.reportId = reportId;
		return this;
	}

	public ReportFileQuery reportIds(List<String> reportIds) {
		if (reportIds == null) {
			throw new RuntimeException("reportIds is empty ");
		}
		this.reportIds = reportIds;
		return this;
	}

	public ReportFileQuery reportYearMonthDay(Integer reportYearMonthDay) {
		if (reportYearMonthDay == null) {
			throw new RuntimeException("reportYearMonthDay is null");
		}
		this.reportYearMonthDay = reportYearMonthDay;
		return this;
	}

	public ReportFileQuery reportYearMonthDayGreaterThanOrEqual(
			Integer reportYearMonthDayGreaterThanOrEqual) {
		if (reportYearMonthDayGreaterThanOrEqual == null) {
			throw new RuntimeException("reportYearMonthDay is null");
		}
		this.reportYearMonthDayGreaterThanOrEqual = reportYearMonthDayGreaterThanOrEqual;
		return this;
	}

	public ReportFileQuery reportYearMonthDayLessThanOrEqual(
			Integer reportYearMonthDayLessThanOrEqual) {
		if (reportYearMonthDayLessThanOrEqual == null) {
			throw new RuntimeException("reportYearMonthDay is null");
		}
		this.reportYearMonthDayLessThanOrEqual = reportYearMonthDayLessThanOrEqual;
		return this;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setCreateDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
	}

	public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
	}

	public void setFilenameLike(String filenameLike) {
		this.filenameLike = filenameLike;
	}

	public void setFileSizeGreaterThanOrEqual(Integer fileSizeGreaterThanOrEqual) {
		this.fileSizeGreaterThanOrEqual = fileSizeGreaterThanOrEqual;
	}

	public void setFileSizeLessThanOrEqual(Integer fileSizeLessThanOrEqual) {
		this.fileSizeLessThanOrEqual = fileSizeLessThanOrEqual;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public void setReportIds(List<String> reportIds) {
		this.reportIds = reportIds;
	}

	public void setReportYearMonthDay(Integer reportYearMonthDay) {
		this.reportYearMonthDay = reportYearMonthDay;
	}

	public void setReportYearMonthDayGreaterThanOrEqual(
			Integer reportYearMonthDayGreaterThanOrEqual) {
		this.reportYearMonthDayGreaterThanOrEqual = reportYearMonthDayGreaterThanOrEqual;
	}

	public void setReportYearMonthDayLessThanOrEqual(
			Integer reportYearMonthDayLessThanOrEqual) {
		this.reportYearMonthDayLessThanOrEqual = reportYearMonthDayLessThanOrEqual;
	}

}