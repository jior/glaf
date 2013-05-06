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
package com.glaf.cms.info.query;

import java.util.*;

import com.glaf.core.query.DataQuery;

public class PublicInfoQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> appActorIds = new ArrayList<String>();
	protected String authorLike;
	protected Integer commentFlag;
	protected String contentLike;
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;
	protected Date endDateGreaterThanOrEqual;
	protected Date endDateLessThanOrEqual;
	protected String fallbackFlag;
	protected String keywordsLike;
	protected String linkLike;
	protected String nameLike;
	protected Long nodeId;
	protected List<Long> nodeIds;
	protected Integer originalFlag;
	protected Integer publishFlag;
	protected String refererUrlLike;
	protected Integer sortNoGreaterThanOrEqual;
	protected Integer sortNoLessThanOrEqual;
	protected Date startDateGreaterThanOrEqual;
	protected Date startDateLessThanOrEqual;
	protected String subjectLike;
	protected String summaryLike;
	protected String tagLike;
	protected String unitNameLike;
	protected String updateBy;
	protected Date updateDateGreaterThanOrEqual;
	protected Date updateDateLessThanOrEqual;

	public PublicInfoQuery() {

	}

	public PublicInfoQuery authorLike(String authorLike) {
		if (authorLike == null) {
			throw new RuntimeException("author is null");
		}
		this.authorLike = authorLike;
		return this;
	}

	public PublicInfoQuery commentFlag(Integer commentFlag) {
		if (commentFlag == null) {
			throw new RuntimeException("commentFlag is null");
		}
		this.commentFlag = commentFlag;
		return this;
	}

	public PublicInfoQuery contentLike(String contentLike) {
		if (contentLike == null) {
			throw new RuntimeException("content is null");
		}
		this.contentLike = contentLike;
		return this;
	}

	public PublicInfoQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public PublicInfoQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public PublicInfoQuery endDateGreaterThanOrEqual(
			Date endDateGreaterThanOrEqual) {
		if (endDateGreaterThanOrEqual == null) {
			throw new RuntimeException("endDate is null");
		}
		this.endDateGreaterThanOrEqual = endDateGreaterThanOrEqual;
		return this;
	}

	public PublicInfoQuery endDateLessThanOrEqual(Date endDateLessThanOrEqual) {
		if (endDateLessThanOrEqual == null) {
			throw new RuntimeException("endDate is null");
		}
		this.endDateLessThanOrEqual = endDateLessThanOrEqual;
		return this;
	}

	public PublicInfoQuery fallbackFlag(String fallbackFlag) {
		if (fallbackFlag == null) {
			throw new RuntimeException("fallbackFlag is null");
		}
		this.fallbackFlag = fallbackFlag;
		return this;
	}

	public List<String> getAppActorIds() {
		return appActorIds;
	}

	public String getAuthorLike() {
		if (authorLike != null && authorLike.trim().length() > 0) {
			if (!authorLike.startsWith("%")) {
				authorLike = "%" + authorLike;
			}
			if (!authorLike.endsWith("%")) {
				authorLike = authorLike + "%";
			}
		}
		return authorLike;
	}

	public Integer getCommentFlag() {
		return commentFlag;
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

	public String getFallbackFlag() {
		return fallbackFlag;
	}

	public String getKeywordsLike() {
		if (keywordsLike != null && keywordsLike.trim().length() > 0) {
			if (!keywordsLike.startsWith("%")) {
				keywordsLike = "%" + keywordsLike;
			}
			if (!keywordsLike.endsWith("%")) {
				keywordsLike = keywordsLike + "%";
			}
		}
		return keywordsLike;
	}

	public String getLinkLike() {
		if (linkLike != null && linkLike.trim().length() > 0) {
			if (!linkLike.startsWith("%")) {
				linkLike = "%" + linkLike;
			}
			if (!linkLike.endsWith("%")) {
				linkLike = linkLike + "%";
			}
		}
		return linkLike;
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

	public Long getNodeId() {
		return nodeId;
	}

	public List<Long> getNodeIds() {
		return nodeIds;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("wfStatus".equals(sortColumn)) {
				orderBy = "E.WFSTATUS_" + a_x;
			}

			if ("originalFlag".equals(sortColumn)) {
				orderBy = "E.ORIGINALFLAG_" + a_x;
			}

			if ("startDate".equals(sortColumn)) {
				orderBy = "E.STARTDATE_" + a_x;
			}

			if ("serviceKey".equals(sortColumn)) {
				orderBy = "E.SERVICEKEY_" + a_x;
			}

			if ("tag".equals(sortColumn)) {
				orderBy = "E.TAG_" + a_x;
			}

			if ("subject".equals(sortColumn)) {
				orderBy = "E.SUBJECT_" + a_x;
			}

			if ("link".equals(sortColumn)) {
				orderBy = "E.LINK_" + a_x;
			}

			if ("processName".equals(sortColumn)) {
				orderBy = "E.PROCESSNAME_" + a_x;
			}

			if ("sortNo".equals(sortColumn)) {
				orderBy = "E.SORTNO_" + a_x;
			}

			if ("endDate".equals(sortColumn)) {
				orderBy = "E.ENDDATE_" + a_x;
			}

			if ("refererUrl".equals(sortColumn)) {
				orderBy = "E.REFERERURL_" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.CREATEBY_" + a_x;
			}

			if ("publishFlag".equals(sortColumn)) {
				orderBy = "E.PUBLISHFLAG_" + a_x;
			}

			if ("author".equals(sortColumn)) {
				orderBy = "E.AUTHOR_" + a_x;
			}

			if ("nodeId".equals(sortColumn)) {
				orderBy = "E.NODEID_" + a_x;
			}

			if ("processInstanceId".equals(sortColumn)) {
				orderBy = "E.PROCESSINSTANCEID_" + a_x;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME_" + a_x;
			}

			if ("createDate".equals(sortColumn)) {
				orderBy = "E.CREATEDATE_" + a_x;
			}

			if ("deleteFlag".equals(sortColumn)) {
				orderBy = "E.DELETEFLAG_" + a_x;
			}

			if ("commentFlag".equals(sortColumn)) {
				orderBy = "E.COMMENTFLAG_" + a_x;
			}

			if ("updateBy".equals(sortColumn)) {
				orderBy = "E.UPDATEBY_" + a_x;
			}

			if ("fallbackFlag".equals(sortColumn)) {
				orderBy = "E.FALLBACKFLAG_" + a_x;
			}

			if ("keywords".equals(sortColumn)) {
				orderBy = "E.KEYWORDS_" + a_x;
			}

			if ("status".equals(sortColumn)) {
				orderBy = "E.STATUS_" + a_x;
			}

			if ("digg".equals(sortColumn)) {
				orderBy = "E.DIGG_" + a_x;
			}

			if ("bury".equals(sortColumn)) {
				orderBy = "E.BURY_" + a_x;
			}

			if ("commentCount".equals(sortColumn)) {
				orderBy = "E.COMMENTCOUNT_" + a_x;
			}

			if ("updateDate".equals(sortColumn)) {
				orderBy = "E.UPDATEDATE_" + a_x;
			}

			if ("content".equals(sortColumn)) {
				orderBy = "E.CONTENT_" + a_x;
			}

			if ("unitName".equals(sortColumn)) {
				orderBy = "E.UNITNAME_" + a_x;
			}

			if ("viewCount".equals(sortColumn)) {
				orderBy = "E.VIEWCOUNT_" + a_x;
			}

		}
		return orderBy;
	}

	public Integer getOriginalFlag() {
		return originalFlag;
	}

	public String getProcessNameLike() {
		if (processNameLike != null && processNameLike.trim().length() > 0) {
			if (!processNameLike.startsWith("%")) {
				processNameLike = "%" + processNameLike;
			}
			if (!processNameLike.endsWith("%")) {
				processNameLike = processNameLike + "%";
			}
		}
		return processNameLike;
	}

	public Integer getPublishFlag() {
		return publishFlag;
	}

	public String getRefererUrlLike() {
		if (refererUrlLike != null && refererUrlLike.trim().length() > 0) {
			if (!refererUrlLike.startsWith("%")) {
				refererUrlLike = "%" + refererUrlLike;
			}
			if (!refererUrlLike.endsWith("%")) {
				refererUrlLike = refererUrlLike + "%";
			}
		}
		return refererUrlLike;
	}

	public Integer getSortNoGreaterThanOrEqual() {
		return sortNoGreaterThanOrEqual;
	}

	public Integer getSortNoLessThanOrEqual() {
		return sortNoLessThanOrEqual;
	}

	public Date getStartDateGreaterThanOrEqual() {
		return startDateGreaterThanOrEqual;
	}

	public Date getStartDateLessThanOrEqual() {
		return startDateLessThanOrEqual;
	}

	public Integer getStatusGreaterThanOrEqual() {
		return statusGreaterThanOrEqual;
	}

	public Integer getStatusLessThanOrEqual() {
		return statusLessThanOrEqual;
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

	public String getSummaryLike() {
		if (summaryLike != null && summaryLike.trim().length() > 0) {
			if (!summaryLike.startsWith("%")) {
				summaryLike = "%" + summaryLike;
			}
			if (!summaryLike.endsWith("%")) {
				summaryLike = summaryLike + "%";
			}
		}
		return summaryLike;
	}

	public String getTagLike() {
		if (tagLike != null && tagLike.trim().length() > 0) {
			if (!tagLike.startsWith("%")) {
				tagLike = "%" + tagLike;
			}
			if (!tagLike.endsWith("%")) {
				tagLike = tagLike + "%";
			}
		}
		return tagLike;
	}

	public String getUnitNameLike() {
		if (unitNameLike != null && unitNameLike.trim().length() > 0) {
			if (!unitNameLike.startsWith("%")) {
				unitNameLike = "%" + unitNameLike;
			}
			if (!unitNameLike.endsWith("%")) {
				unitNameLike = unitNameLike + "%";
			}
		}
		return unitNameLike;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public Date getUpdateDateGreaterThanOrEqual() {
		return updateDateGreaterThanOrEqual;
	}

	public Date getUpdateDateLessThanOrEqual() {
		return updateDateLessThanOrEqual;
	}

	public Integer getWfStatusGreaterThanOrEqual() {
		return wfStatusGreaterThanOrEqual;
	}

	public Integer getWfStatusLessThanOrEqual() {
		return wfStatusLessThanOrEqual;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("wfStatus", "WFSTATUS_");
		addColumn("originalFlag", "ORIGINALFLAG_");
		addColumn("startDate", "STARTDATE_");
		addColumn("serviceKey", "SERVICEKEY_");
		addColumn("tag", "TAG_");
		addColumn("subject", "SUBJECT_");
		addColumn("link", "LINK_");
		addColumn("processName", "PROCESSNAME_");
		addColumn("sortNo", "SORTNO_");
		addColumn("endDate", "ENDDATE_");
		addColumn("refererUrl", "REFERERURL_");
		addColumn("createBy", "CREATEBY_");
		addColumn("publishFlag", "PUBLISHFLAG_");
		addColumn("author", "AUTHOR_");
		addColumn("nodeId", "NODEID_");
		addColumn("processInstanceId", "PROCESSINSTANCEID_");
		addColumn("name", "NAME_");
		addColumn("createDate", "CREATEDATE_");
		addColumn("deleteFlag", "DELETEFLAG_");
		addColumn("commentFlag", "COMMENTFLAG_");
		addColumn("updateBy", "UPDATEBY_");
		addColumn("fallbackFlag", "FALLBACKFLAG_");
		addColumn("keywords", "KEYWORDS_");
		addColumn("status", "STATUS_");
		addColumn("digg", "DIGG_");
		addColumn("bury", "BURY_");
		addColumn("commentCount", "COMMENTCOUNT_");
		addColumn("updateDate", "UPDATEDATE_");
		addColumn("content", "CONTENT_");
		addColumn("unitName", "UNITNAME_");
		addColumn("viewCount", "VIEWCOUNT_");
	}

	public PublicInfoQuery keywordsLike(String keywordsLike) {
		if (keywordsLike == null) {
			throw new RuntimeException("keywords is null");
		}
		this.keywordsLike = keywordsLike;
		return this;
	}

	public PublicInfoQuery linkLike(String linkLike) {
		if (linkLike == null) {
			throw new RuntimeException("link is null");
		}
		this.linkLike = linkLike;
		return this;
	}

	public PublicInfoQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public PublicInfoQuery nodeId(Long nodeId) {
		if (nodeId == null) {
			throw new RuntimeException("nodeId is null");
		}
		this.nodeId = nodeId;
		return this;
	}

	public PublicInfoQuery nodeIds(List<Long> nodeIds) {
		if (nodeIds == null) {
			throw new RuntimeException("nodeIds is empty ");
		}
		this.nodeIds = nodeIds;
		return this;
	}

	public PublicInfoQuery originalFlag(Integer originalFlag) {
		if (originalFlag == null) {
			throw new RuntimeException("originalFlag is null");
		}
		this.originalFlag = originalFlag;
		return this;
	}

	public PublicInfoQuery processName(String processName) {
		if (processName == null) {
			throw new RuntimeException("processName is null");
		}
		this.processName = processName;
		return this;
	}

	public PublicInfoQuery processNameLike(String processNameLike) {
		if (processNameLike == null) {
			throw new RuntimeException("processName is null");
		}
		this.processNameLike = processNameLike;
		return this;
	}

	public PublicInfoQuery processNames(List<String> processNames) {
		if (processNames == null) {
			throw new RuntimeException("processNames is empty ");
		}
		this.processNames = processNames;
		return this;
	}

	public PublicInfoQuery publishFlag(Integer publishFlag) {
		if (publishFlag == null) {
			throw new RuntimeException("publishFlag is null");
		}
		this.publishFlag = publishFlag;
		return this;
	}

	public PublicInfoQuery refererUrlLike(String refererUrlLike) {
		if (refererUrlLike == null) {
			throw new RuntimeException("refererUrl is null");
		}
		this.refererUrlLike = refererUrlLike;
		return this;
	}

	public void setAppActorIds(List<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public void setAuthorLike(String authorLike) {
		this.authorLike = authorLike;
	}

	public void setCommentFlag(Integer commentFlag) {
		this.commentFlag = commentFlag;
	}

	public void setContentLike(String contentLike) {
		this.contentLike = contentLike;
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

	public void setFallbackFlag(String fallbackFlag) {
		this.fallbackFlag = fallbackFlag;
	}

	public void setKeywordsLike(String keywordsLike) {
		this.keywordsLike = keywordsLike;
	}

	public void setLinkLike(String linkLike) {
		this.linkLike = linkLike;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public void setNodeIds(List<Long> nodeIds) {
		this.nodeIds = nodeIds;
	}

	public void setOriginalFlag(Integer originalFlag) {
		this.originalFlag = originalFlag;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public void setProcessNameLike(String processNameLike) {
		this.processNameLike = processNameLike;
	}

	public void setProcessNames(List<String> processNames) {
		this.processNames = processNames;
	}

	public void setPublishFlag(Integer publishFlag) {
		this.publishFlag = publishFlag;
	}

	public void setRefererUrlLike(String refererUrlLike) {
		this.refererUrlLike = refererUrlLike;
	}

	public void setSortNoGreaterThanOrEqual(Integer sortNoGreaterThanOrEqual) {
		this.sortNoGreaterThanOrEqual = sortNoGreaterThanOrEqual;
	}

	public void setSortNoLessThanOrEqual(Integer sortNoLessThanOrEqual) {
		this.sortNoLessThanOrEqual = sortNoLessThanOrEqual;
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

	public void setSubjectLike(String subjectLike) {
		this.subjectLike = subjectLike;
	}

	public void setSummaryLike(String summaryLike) {
		this.summaryLike = summaryLike;
	}

	public void setTagLike(String tagLike) {
		this.tagLike = tagLike;
	}

	public void setUnitNameLike(String unitNameLike) {
		this.unitNameLike = unitNameLike;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
	}

	public void setUpdateDateLessThanOrEqual(Date updateDateLessThanOrEqual) {
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
	}

	public void setWfStatusGreaterThanOrEqual(Integer wfStatusGreaterThanOrEqual) {
		this.wfStatusGreaterThanOrEqual = wfStatusGreaterThanOrEqual;
	}

	public void setWfStatusLessThanOrEqual(Integer wfStatusLessThanOrEqual) {
		this.wfStatusLessThanOrEqual = wfStatusLessThanOrEqual;
	}

	public PublicInfoQuery startDateGreaterThanOrEqual(
			Date startDateGreaterThanOrEqual) {
		if (startDateGreaterThanOrEqual == null) {
			throw new RuntimeException("startDate is null");
		}
		this.startDateGreaterThanOrEqual = startDateGreaterThanOrEqual;
		return this;
	}

	public PublicInfoQuery startDateLessThanOrEqual(
			Date startDateLessThanOrEqual) {
		if (startDateLessThanOrEqual == null) {
			throw new RuntimeException("startDate is null");
		}
		this.startDateLessThanOrEqual = startDateLessThanOrEqual;
		return this;
	}

	public PublicInfoQuery statusGreaterThanOrEqual(
			Integer statusGreaterThanOrEqual) {
		if (statusGreaterThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
		return this;
	}

	public PublicInfoQuery statusLessThanOrEqual(Integer statusLessThanOrEqual) {
		if (statusLessThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusLessThanOrEqual = statusLessThanOrEqual;
		return this;
	}

	public PublicInfoQuery subjectLike(String subjectLike) {
		if (subjectLike == null) {
			throw new RuntimeException("subject is null");
		}
		this.subjectLike = subjectLike;
		return this;
	}

	public PublicInfoQuery summaryLike(String summaryLike) {
		if (summaryLike == null) {
			throw new RuntimeException("summary is null");
		}
		this.summaryLike = summaryLike;
		return this;
	}

	public PublicInfoQuery tagLike(String tagLike) {
		if (tagLike == null) {
			throw new RuntimeException("tag is null");
		}
		this.tagLike = tagLike;
		return this;
	}

	public PublicInfoQuery unitNameLike(String unitNameLike) {
		if (unitNameLike == null) {
			throw new RuntimeException("unitName is null");
		}
		this.unitNameLike = unitNameLike;
		return this;
	}

	public PublicInfoQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public PublicInfoQuery updateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public PublicInfoQuery updateDateLessThanOrEqual(
			Date updateDateLessThanOrEqual) {
		if (updateDateLessThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
		return this;
	}

	public PublicInfoQuery wfStatusGreaterThanOrEqual(
			Integer wfStatusGreaterThanOrEqual) {
		if (wfStatusGreaterThanOrEqual == null) {
			throw new RuntimeException("wfStatus is null");
		}
		this.wfStatusGreaterThanOrEqual = wfStatusGreaterThanOrEqual;
		return this;
	}

	public PublicInfoQuery wfStatusLessThanOrEqual(
			Integer wfStatusLessThanOrEqual) {
		if (wfStatusLessThanOrEqual == null) {
			throw new RuntimeException("wfStatus is null");
		}
		this.wfStatusLessThanOrEqual = wfStatusLessThanOrEqual;
		return this;
	}

}