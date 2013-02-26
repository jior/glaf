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

public class ReportTaskQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String name;
	protected String nameLike;
	protected String subjectLike;
	protected String mailRecipientLike;
	protected String mobileRecipientLike;
	protected String sendTitleLike;
	protected String sendContentLike;
	protected String enableFlag;
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;

	public ReportTaskQuery() {

	}

	public ReportTaskQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public ReportTaskQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public ReportTaskQuery enableFlag(String enableFlag) {
		if (enableFlag == null) {
			throw new RuntimeException("enableFlag is null");
		}
		this.enableFlag = enableFlag;
		return this;
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

	public String getMailRecipientLike() {
		if (mailRecipientLike != null && mailRecipientLike.trim().length() > 0) {
			if (!mailRecipientLike.startsWith("%")) {
				mailRecipientLike = "%" + mailRecipientLike;
			}
			if (!mailRecipientLike.endsWith("%")) {
				mailRecipientLike = mailRecipientLike + "%";
			}
		}
		return mailRecipientLike;
	}

	public String getMobileRecipientLike() {
		if (mobileRecipientLike != null
				&& mobileRecipientLike.trim().length() > 0) {
			if (!mobileRecipientLike.startsWith("%")) {
				mobileRecipientLike = "%" + mobileRecipientLike;
			}
			if (!mobileRecipientLike.endsWith("%")) {
				mobileRecipientLike = mobileRecipientLike + "%";
			}
		}
		return mobileRecipientLike;
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

	public String getSendContentLike() {
		if (sendContentLike != null && sendContentLike.trim().length() > 0) {
			if (!sendContentLike.startsWith("%")) {
				sendContentLike = "%" + sendContentLike;
			}
			if (!sendContentLike.endsWith("%")) {
				sendContentLike = sendContentLike + "%";
			}
		}
		return sendContentLike;
	}

	public String getSendTitleLike() {
		if (sendTitleLike != null && sendTitleLike.trim().length() > 0) {
			if (!sendTitleLike.startsWith("%")) {
				sendTitleLike = "%" + sendTitleLike;
			}
			if (!sendTitleLike.endsWith("%")) {
				sendTitleLike = sendTitleLike + "%";
			}
		}
		return sendTitleLike;
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
		addColumn("reportIds", "REPORTIDS_");
		addColumn("name", "NAME_");
		addColumn("subject", "SUBJECT_");
		addColumn("mailRecipient", "MAILRECIPIENT_");
		addColumn("mobileRecipient", "MOBILERECIPIENT_");
		addColumn("sendTitle", "SENDTITLE_");
		addColumn("sendContent", "SENDCONTENT_");
		addColumn("createDate", "CREATEDATE_");
		addColumn("createBy", "CREATEBY_");
	}

	public ReportTaskQuery mailRecipientLike(String mailRecipientLike) {
		if (mailRecipientLike == null) {
			throw new RuntimeException("mailRecipient is null");
		}
		this.mailRecipientLike = mailRecipientLike;
		return this;
	}

	public ReportTaskQuery mobileRecipientLike(String mobileRecipientLike) {
		if (mobileRecipientLike == null) {
			throw new RuntimeException("mobileRecipient is null");
		}
		this.mobileRecipientLike = mobileRecipientLike;
		return this;
	}

	public ReportTaskQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public ReportTaskQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public ReportTaskQuery sendContentLike(String sendContentLike) {
		if (sendContentLike == null) {
			throw new RuntimeException("sendContent is null");
		}
		this.sendContentLike = sendContentLike;
		return this;
	}

	public ReportTaskQuery sendTitleLike(String sendTitleLike) {
		if (sendTitleLike == null) {
			throw new RuntimeException("sendTitle is null");
		}
		this.sendTitleLike = sendTitleLike;
		return this;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
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

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}

	public void setMailRecipientLike(String mailRecipientLike) {
		this.mailRecipientLike = mailRecipientLike;
	}

	public void setMobileRecipientLike(String mobileRecipientLike) {
		this.mobileRecipientLike = mobileRecipientLike;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setSendContentLike(String sendContentLike) {
		this.sendContentLike = sendContentLike;
	}

	public void setSendTitleLike(String sendTitleLike) {
		this.sendTitleLike = sendTitleLike;
	}

	public void setSubjectLike(String subjectLike) {
		this.subjectLike = subjectLike;
	}

	public ReportTaskQuery subjectLike(String subjectLike) {
		if (subjectLike == null) {
			throw new RuntimeException("subject is null");
		}
		this.subjectLike = subjectLike;
		return this;
	}

}