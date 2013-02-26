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

public class ReportQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> reportIds;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected String subject;
	protected String subjectLike;
	protected String type;
	protected String typeLike;
	protected List<String> types;
	protected String reportName;
	protected String reportNameLike;
	protected String enableFlag;
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;
	protected String createByLike;
	protected String sortColumn;

	public ReportQuery() {

	}

	public ReportQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public ReportQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public ReportQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public ReportQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public ReportQuery enableFlag(String enableFlag) {
		if (enableFlag == null) {
			throw new RuntimeException("enableFlag is null");
		}
		this.enableFlag = enableFlag;
		return this;
	}

	public String getCreateBy() {
		return createBy;
	}

	public String getCreateByLike() {
		if (createByLike != null && createByLike.trim().length() > 0) {
			if (!createByLike.startsWith("%")) {
				createByLike = "%" + createByLike;
			}
			if (!createByLike.endsWith("%")) {
				createByLike = createByLike + "%";
			}
		}
		return createByLike;
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

	public String getEnableFlag() {
		return enableFlag;
	}

	public String getName() {
		return name;
	}

	public String getNameLike() {
		if (nameLike != null && nameLike.trim().length() > 0) {
			if (!nameLike.startsWith("%")) {
				nameLike = "%" + nameLike;
			}
			if (!nameLike.endsWith("%")) {
				nameLike = nameLike + "%";
			}
		}
		return nameLike;
	}

	public List<String> getNames() {
		return names;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("queryIds".equals(sortColumn)) {
				orderBy = "E.QUERYIDS_" + a_x;
			}

			if ("chartIds".equals(sortColumn)) {
				orderBy = "E.CHARTIDS_" + a_x;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME_" + a_x;
			}

			if ("subject".equals(sortColumn)) {
				orderBy = "E.SUBJECT_" + a_x;
			}

			if ("type".equals(sortColumn)) {
				orderBy = "E.TYPE_" + a_x;
			}

			if ("reportName".equals(sortColumn)) {
				orderBy = "E.REPORTNAME_" + a_x;
			}

			if ("reportTitleDate".equals(sortColumn)) {
				orderBy = "E.REPORTTITLEDATE_" + a_x;
			}

			if ("reportMonth".equals(sortColumn)) {
				orderBy = "E.REPORTMONTH_" + a_x;
			}

			if ("reportDateYYYYMMDD".equals(sortColumn)) {
				orderBy = "E.REPORTDATEYYYYMMDD_" + a_x;
			}

			if ("jsonParameter".equals(sortColumn)) {
				orderBy = "E.JSONPARAMETER_" + a_x;
			}

			if ("createDate".equals(sortColumn)) {
				orderBy = "E.CREATEDATE_" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.CREATEBY_" + a_x;
			}

		}
		return orderBy;
	}

	public List<String> getReportIds() {
		return reportIds;
	}

	public String getReportName() {
		return reportName;
	}

	public String getReportNameLike() {
		if (reportNameLike != null && reportNameLike.trim().length() > 0) {
			if (!reportNameLike.startsWith("%")) {
				reportNameLike = "%" + reportNameLike;
			}
			if (!reportNameLike.endsWith("%")) {
				reportNameLike = reportNameLike + "%";
			}
		}
		return reportNameLike;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public String getSubject() {
		return subject;
	}

	public String getSubjectLike() {
		if (subjectLike != null && subjectLike.trim().length() > 0) {
			if (!subjectLike.startsWith("%")) {
				subjectLike = "%" + subjectLike;
			}
			if (!subjectLike.endsWith("%")) {
				subjectLike = subjectLike + "%";
			}
		}
		return subjectLike;
	}

	public String getType() {
		return type;
	}

	public String getTypeLike() {
		if (typeLike != null && typeLike.trim().length() > 0) {
			if (!typeLike.startsWith("%")) {
				typeLike = "%" + typeLike;
			}
			if (!typeLike.endsWith("%")) {
				typeLike = typeLike + "%";
			}
		}
		return typeLike;
	}

	public List<String> getTypes() {
		return types;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("queryIds", "QUERYIDS_");
		addColumn("chartIds", "CHARTIDS_");
		addColumn("name", "NAME_");
		addColumn("subject", "SUBJECT_");
		addColumn("type", "TYPE_");
		addColumn("reportName", "REPORTNAME_");
		addColumn("reportTitleDate", "REPORTTITLEDATE_");
		addColumn("reportMonth", "REPORTMONTH_");
		addColumn("reportDateYYYYMMDD", "REPORTDATEYYYYMMDD_");
		addColumn("jsonParameter", "JSONPARAMETER_");
		addColumn("createDate", "CREATEDATE_");
		addColumn("createBy", "CREATEBY_");
	}

	public ReportQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public ReportQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public ReportQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public ReportQuery reportIds(List<String> reportIds) {
		if (reportIds == null) {
			throw new RuntimeException("reportIds is empty ");
		}
		this.reportIds = reportIds;
		return this;
	}

	public ReportQuery reportName(String reportName) {
		if (reportName == null) {
			throw new RuntimeException("reportName is null");
		}
		this.reportName = reportName;
		return this;
	}

	public ReportQuery reportNameLike(String reportNameLike) {
		if (reportNameLike == null) {
			throw new RuntimeException("reportName is null");
		}
		this.reportNameLike = reportNameLike;
		return this;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateByLike(String createByLike) {
		this.createByLike = createByLike;
	}

	public void setCreateDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
	}

	public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public void setReportIds(List<String> reportIds) {
		this.reportIds = reportIds;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public void setReportNameLike(String reportNameLike) {
		this.reportNameLike = reportNameLike;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setSubjectLike(String subjectLike) {
		this.subjectLike = subjectLike;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTypeLike(String typeLike) {
		this.typeLike = typeLike;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public ReportQuery subject(String subject) {
		if (subject == null) {
			throw new RuntimeException("subject is null");
		}
		this.subject = subject;
		return this;
	}

	public ReportQuery subjectLike(String subjectLike) {
		if (subjectLike == null) {
			throw new RuntimeException("subject is null");
		}
		this.subjectLike = subjectLike;
		return this;
	}

	public ReportQuery type(String type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

	public ReportQuery typeLike(String typeLike) {
		if (typeLike == null) {
			throw new RuntimeException("type is null");
		}
		this.typeLike = typeLike;
		return this;
	}

	public ReportQuery types(List<String> types) {
		if (types == null) {
			throw new RuntimeException("types is empty ");
		}
		this.types = types;
		return this;
	}

}