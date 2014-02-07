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

package com.glaf.survey.query;

import java.util.*;

import com.glaf.core.query.DataQuery;

public class SurveyQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String titleLike;
	protected String contentLike;
	protected Integer signFlag;
	protected Integer multiFlag;
	protected Integer limitFlag;
	protected Date startDateGreaterThanOrEqual;
	protected Date startDateLessThanOrEqual;
	protected Date endDateGreaterThanOrEqual;
	protected Date endDateLessThanOrEqual;
	protected List<Long> surveyIds;
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;

	public SurveyQuery() {

	}

	public SurveyQuery contentLike(String contentLike) {
		if (contentLike == null) {
			throw new RuntimeException("content is null");
		}
		this.contentLike = contentLike;
		return this;
	}

	public SurveyQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public SurveyQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public SurveyQuery endDateGreaterThanOrEqual(Date endDateGreaterThanOrEqual) {
		if (endDateGreaterThanOrEqual == null) {
			throw new RuntimeException("endDate is null");
		}
		this.endDateGreaterThanOrEqual = endDateGreaterThanOrEqual;
		return this;
	}

	public SurveyQuery endDateLessThanOrEqual(Date endDateLessThanOrEqual) {
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

	public Integer getLimitFlag() {
		return limitFlag;
	}

	public Integer getMultiFlag() {
		return multiFlag;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("title".equals(sortColumn)) {
				orderBy = "E.TITLE_" + a_x;
			}

			if ("content".equals(sortColumn)) {
				orderBy = "E.CONTENT_" + a_x;
			}

			if ("icon".equals(sortColumn)) {
				orderBy = "E.ICON_" + a_x;
			}

			if ("status".equals(sortColumn)) {
				orderBy = "E.STATUS_" + a_x;
			}

			if ("signFlag".equals(sortColumn)) {
				orderBy = "E.SIGNFLAG_" + a_x;
			}

			if ("multiFlag".equals(sortColumn)) {
				orderBy = "E.MULTIFLAG_" + a_x;
			}

			if ("limitFlag".equals(sortColumn)) {
				orderBy = "E.LIMITFLAG_" + a_x;
			}

			if ("limitTimeInterval".equals(sortColumn)) {
				orderBy = "E.LIMITTIMEINTERVAL_" + a_x;
			}

			if ("startDate".equals(sortColumn)) {
				orderBy = "E.STARTDATE_" + a_x;
			}

			if ("endDate".equals(sortColumn)) {
				orderBy = "E.ENDDATE_" + a_x;
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

	public Integer getSignFlag() {
		return signFlag;
	}

	public Date getStartDateGreaterThanOrEqual() {
		return startDateGreaterThanOrEqual;
	}

	public Date getStartDateLessThanOrEqual() {
		return startDateLessThanOrEqual;
	}

	public Integer getStatus() {
		return status;
	}

	public Integer getStatusGreaterThanOrEqual() {
		return statusGreaterThanOrEqual;
	}

	public Integer getStatusLessThanOrEqual() {
		return statusLessThanOrEqual;
	}

	public List<Long> getSurveyIds() {
		return surveyIds;
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
		addColumn("title", "TITLE_");
		addColumn("content", "CONTENT_");
		addColumn("icon", "ICON_");
		addColumn("status", "STATUS_");
		addColumn("signFlag", "SIGNFLAG_");
		addColumn("multiFlag", "MULTIFLAG_");
		addColumn("limitFlag", "LIMITFLAG_");
		addColumn("limitTimeInterval", "LIMITTIMEINTERVAL_");
		addColumn("startDate", "STARTDATE_");
		addColumn("endDate", "ENDDATE_");
		addColumn("createBy", "CREATEBY_");
		addColumn("createDate", "CREATEDATE_");
	}

	public SurveyQuery limitFlag(Integer limitFlag) {
		if (limitFlag == null) {
			throw new RuntimeException("limitFlag is null");
		}
		this.limitFlag = limitFlag;
		return this;
	}

	public SurveyQuery multiFlag(Integer multiFlag) {
		if (multiFlag == null) {
			throw new RuntimeException("multiFlag is null");
		}
		this.multiFlag = multiFlag;
		return this;
	}

	public void setContentLike(String contentLike) {
		this.contentLike = contentLike;
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

	public void setEndDateGreaterThanOrEqual(Date endDateGreaterThanOrEqual) {
		this.endDateGreaterThanOrEqual = endDateGreaterThanOrEqual;
	}

	public void setEndDateLessThanOrEqual(Date endDateLessThanOrEqual) {
		this.endDateLessThanOrEqual = endDateLessThanOrEqual;
	}

	public void setLimitFlag(Integer limitFlag) {
		this.limitFlag = limitFlag;
	}

	public void setMultiFlag(Integer multiFlag) {
		this.multiFlag = multiFlag;
	}

	public void setSignFlag(Integer signFlag) {
		this.signFlag = signFlag;
	}

	public void setStartDateGreaterThanOrEqual(Date startDateGreaterThanOrEqual) {
		this.startDateGreaterThanOrEqual = startDateGreaterThanOrEqual;
	}

	public void setStartDateLessThanOrEqual(Date startDateLessThanOrEqual) {
		this.startDateLessThanOrEqual = startDateLessThanOrEqual;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setStatusGreaterThanOrEqual(Integer statusGreaterThanOrEqual) {
		this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
	}

	public void setStatusLessThanOrEqual(Integer statusLessThanOrEqual) {
		this.statusLessThanOrEqual = statusLessThanOrEqual;
	}

	public void setSurveyIds(List<Long> surveyIds) {
		this.surveyIds = surveyIds;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public SurveyQuery signFlag(Integer signFlag) {
		if (signFlag == null) {
			throw new RuntimeException("signFlag is null");
		}
		this.signFlag = signFlag;
		return this;
	}

	public SurveyQuery startDateGreaterThanOrEqual(
			Date startDateGreaterThanOrEqual) {
		if (startDateGreaterThanOrEqual == null) {
			throw new RuntimeException("startDate is null");
		}
		this.startDateGreaterThanOrEqual = startDateGreaterThanOrEqual;
		return this;
	}

	public SurveyQuery startDateLessThanOrEqual(Date startDateLessThanOrEqual) {
		if (startDateLessThanOrEqual == null) {
			throw new RuntimeException("startDate is null");
		}
		this.startDateLessThanOrEqual = startDateLessThanOrEqual;
		return this;
	}

	public SurveyQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

}