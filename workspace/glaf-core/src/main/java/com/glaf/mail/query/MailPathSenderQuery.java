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

package com.glaf.mail.query;

import java.util.*;
import com.glaf.core.query.BaseQuery;

public class MailPathSenderQuery extends BaseQuery {
	private static final long serialVersionUID = 1L;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected String subject;
	protected String subjectLike;
	protected String enableFlag;
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;
	protected String createByLike;
	protected String sortColumn;

	public MailPathSenderQuery() {

	}

	public MailPathSenderQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public MailPathSenderQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public MailPathSenderQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public MailPathSenderQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public MailPathSenderQuery enableFlag(String enableFlag) {
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

			if ("mailTaskName".equals(sortColumn)) {
				orderBy = "E.REPORTNAME_" + a_x;
			}

			if ("mailTaskTitleDate".equals(sortColumn)) {
				orderBy = "E.REPORTTITLEDATE_" + a_x;
			}

			if ("mailTaskMonth".equals(sortColumn)) {
				orderBy = "E.REPORTMONTH_" + a_x;
			}

			if ("mailTaskDateYYYYMMDD".equals(sortColumn)) {
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

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");

		addColumn("name", "NAME_");
		addColumn("subject", "SUBJECT_");

		addColumn("jsonParameter", "JSONPARAMETER_");
		addColumn("createDate", "CREATEDATE_");
		addColumn("createBy", "CREATEBY_");
	}

	public MailPathSenderQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public MailPathSenderQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public MailPathSenderQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
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

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setSubjectLike(String subjectLike) {
		this.subjectLike = subjectLike;
	}

	public MailPathSenderQuery subject(String subject) {
		if (subject == null) {
			throw new RuntimeException("subject is null");
		}
		this.subject = subject;
		return this;
	}

	public MailPathSenderQuery subjectLike(String subjectLike) {
		if (subjectLike == null) {
			throw new RuntimeException("subject is null");
		}
		this.subjectLike = subjectLike;
		return this;
	}

}