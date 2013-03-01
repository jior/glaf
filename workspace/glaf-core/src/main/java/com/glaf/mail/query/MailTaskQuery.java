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

public class MailTaskQuery extends BaseQuery {
	private static final long serialVersionUID = 1L;
	protected String subjectLike;
	protected String callbackUrlLike;
	protected String storageId;
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;
	protected Date startDateGreaterThanOrEqual;
	protected Date startDateLessThanOrEqual;
	protected Date endDateGreaterThanOrEqual;
	protected Date endDateLessThanOrEqual;

	public MailTaskQuery() {

	}

	public MailTaskQuery callbackUrlLike(String callbackUrlLike) {
		if (callbackUrlLike == null) {
			throw new RuntimeException("callbackUrl is null");
		}
		this.callbackUrlLike = callbackUrlLike;
		return this;
	}

	public MailTaskQuery endDateGreaterThanOrEqual(
			Date endDateGreaterThanOrEqual) {
		if (endDateGreaterThanOrEqual == null) {
			throw new RuntimeException("endDate is null");
		}
		this.endDateGreaterThanOrEqual = endDateGreaterThanOrEqual;
		return this;
	}

	public MailTaskQuery endDateLessThanOrEqual(Date endDateLessThanOrEqual) {
		if (endDateLessThanOrEqual == null) {
			throw new RuntimeException("endDate is null");
		}
		this.endDateLessThanOrEqual = endDateLessThanOrEqual;
		return this;
	}

	public String getCallbackUrlLike() {
		if (callbackUrlLike != null && callbackUrlLike.trim().length() > 0) {
			if (!callbackUrlLike.startsWith("%")) {
				callbackUrlLike = "%" + callbackUrlLike;
			}
			if (!callbackUrlLike.endsWith("%")) {
				callbackUrlLike = callbackUrlLike + "%";
			}
		}
		return callbackUrlLike;
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

	public String getOrderBy() {
		if (sortField != null) {
			String a_x = " asc ";
			if (getSortOrder() != null) {
				a_x = " desc ";
			}

			if ("threadSize".equals(sortField)) {
				orderBy = "E.THREADSIZE_" + a_x;
			}

			if ("delayTime".equals(sortField)) {
				orderBy = "E.DELAYTIME_" + a_x;
			}

			if ("startDate".equals(sortField)) {
				orderBy = "E.STARTDATE_" + a_x;
			}

			if ("endDate".equals(sortField)) {
				orderBy = "E.ENDDATE_" + a_x;
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

	public String getStorageId() {
		return storageId;
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
		addColumn("subject", "SUBJECT_");
		addColumn("callbackUrl", "CALLBACKURL_");
		addColumn("dataTable", "DATATABLE_");
		addColumn("threadSize", "THREADSIZE_");
		addColumn("delayTime", "DELAYTIME_");
		addColumn("startDate", "STARTDATE_");
		addColumn("endDate", "ENDDATE_");
		addColumn("isHtml", "ISHTML_");
		addColumn("isBack", "ISBACK_");
		addColumn("isUnSubscribe", "ISUNSUBSCRIBE_");
	}

	public void setCallbackUrlLike(String callbackUrlLike) {
		this.callbackUrlLike = callbackUrlLike;
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

	public void setStartDateGreaterThanOrEqual(Date startDateGreaterThanOrEqual) {
		this.startDateGreaterThanOrEqual = startDateGreaterThanOrEqual;
	}

	public void setStartDateLessThanOrEqual(Date startDateLessThanOrEqual) {
		this.startDateLessThanOrEqual = startDateLessThanOrEqual;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}

	public void setSubjectLike(String subjectLike) {
		this.subjectLike = subjectLike;
	}

	public MailTaskQuery startDateGreaterThanOrEqual(
			Date startDateGreaterThanOrEqual) {
		if (startDateGreaterThanOrEqual == null) {
			throw new RuntimeException("startDate is null");
		}
		this.startDateGreaterThanOrEqual = startDateGreaterThanOrEqual;
		return this;
	}

	public MailTaskQuery startDateLessThanOrEqual(Date startDateLessThanOrEqual) {
		if (startDateLessThanOrEqual == null) {
			throw new RuntimeException("startDate is null");
		}
		this.startDateLessThanOrEqual = startDateLessThanOrEqual;
		return this;
	}

	public MailTaskQuery subjectLike(String subjectLike) {
		if (subjectLike == null) {
			throw new RuntimeException("subject is null");
		}
		this.subjectLike = subjectLike;
		return this;
	}

}