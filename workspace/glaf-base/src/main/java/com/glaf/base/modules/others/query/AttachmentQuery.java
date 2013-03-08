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

package com.glaf.base.modules.others.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class AttachmentQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> rowIds;
	protected Long referId;
	protected Long referIdGreaterThanOrEqual;
	protected Long referIdLessThanOrEqual;
	protected List<Long> referIds;
	protected Integer referType;
	protected Integer referTypeGreaterThanOrEqual;
	protected Integer referTypeLessThanOrEqual;
	protected List<Integer> referTypes;
	protected String name;
	protected String nameLike;
	protected String url;
	protected String urlLike;
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;
	protected Long createId;
	protected Long createIdGreaterThanOrEqual;
	protected Long createIdLessThanOrEqual;
	protected List<Long> createIds;

	public AttachmentQuery() {

	}

	public Long getReferId() {
		return referId;
	}

	public Long getReferIdGreaterThanOrEqual() {
		return referIdGreaterThanOrEqual;
	}

	public Long getReferIdLessThanOrEqual() {
		return referIdLessThanOrEqual;
	}

	public List<Long> getReferIds() {
		return referIds;
	}

	public Integer getReferType() {
		return referType;
	}

	public Integer getReferTypeGreaterThanOrEqual() {
		return referTypeGreaterThanOrEqual;
	}

	public Integer getReferTypeLessThanOrEqual() {
		return referTypeLessThanOrEqual;
	}

	public List<Integer> getReferTypes() {
		return referTypes;
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

	public String getUrl() {
		return url;
	}

	public String getUrlLike() {
		if (urlLike != null && urlLike.trim().length() > 0) {
			if (!urlLike.startsWith("%")) {
				urlLike = "%" + urlLike;
			}
			if (!urlLike.endsWith("%")) {
				urlLike = urlLike + "%";
			}
		}
		return urlLike;
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

	public Long getCreateId() {
		return createId;
	}

	public Long getCreateIdGreaterThanOrEqual() {
		return createIdGreaterThanOrEqual;
	}

	public Long getCreateIdLessThanOrEqual() {
		return createIdLessThanOrEqual;
	}

	public List<Long> getCreateIds() {
		return createIds;
	}

	public void setReferId(Long referId) {
		this.referId = referId;
	}

	public void setReferIdGreaterThanOrEqual(Long referIdGreaterThanOrEqual) {
		this.referIdGreaterThanOrEqual = referIdGreaterThanOrEqual;
	}

	public void setReferIdLessThanOrEqual(Long referIdLessThanOrEqual) {
		this.referIdLessThanOrEqual = referIdLessThanOrEqual;
	}

	public void setReferIds(List<Long> referIds) {
		this.referIds = referIds;
	}

	public void setReferType(Integer referType) {
		this.referType = referType;
	}

	public void setReferTypeGreaterThanOrEqual(
			Integer referTypeGreaterThanOrEqual) {
		this.referTypeGreaterThanOrEqual = referTypeGreaterThanOrEqual;
	}

	public void setReferTypeLessThanOrEqual(Integer referTypeLessThanOrEqual) {
		this.referTypeLessThanOrEqual = referTypeLessThanOrEqual;
	}

	public void setReferTypes(List<Integer> referTypes) {
		this.referTypes = referTypes;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUrlLike(String urlLike) {
		this.urlLike = urlLike;
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

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public void setCreateIdGreaterThanOrEqual(Long createIdGreaterThanOrEqual) {
		this.createIdGreaterThanOrEqual = createIdGreaterThanOrEqual;
	}

	public void setCreateIdLessThanOrEqual(Long createIdLessThanOrEqual) {
		this.createIdLessThanOrEqual = createIdLessThanOrEqual;
	}

	public void setCreateIds(List<Long> createIds) {
		this.createIds = createIds;
	}

	public AttachmentQuery referId(Long referId) {
		if (referId == null) {
			throw new RuntimeException("referId is null");
		}
		this.referId = referId;
		return this;
	}

	public AttachmentQuery referIdGreaterThanOrEqual(
			Long referIdGreaterThanOrEqual) {
		if (referIdGreaterThanOrEqual == null) {
			throw new RuntimeException("referId is null");
		}
		this.referIdGreaterThanOrEqual = referIdGreaterThanOrEqual;
		return this;
	}

	public AttachmentQuery referIdLessThanOrEqual(Long referIdLessThanOrEqual) {
		if (referIdLessThanOrEqual == null) {
			throw new RuntimeException("referId is null");
		}
		this.referIdLessThanOrEqual = referIdLessThanOrEqual;
		return this;
	}

	public AttachmentQuery referIds(List<Long> referIds) {
		if (referIds == null) {
			throw new RuntimeException("referIds is empty ");
		}
		this.referIds = referIds;
		return this;
	}

	public AttachmentQuery referType(Integer referType) {
		if (referType == null) {
			throw new RuntimeException("referType is null");
		}
		this.referType = referType;
		return this;
	}

	public AttachmentQuery referTypeGreaterThanOrEqual(
			Integer referTypeGreaterThanOrEqual) {
		if (referTypeGreaterThanOrEqual == null) {
			throw new RuntimeException("referType is null");
		}
		this.referTypeGreaterThanOrEqual = referTypeGreaterThanOrEqual;
		return this;
	}

	public AttachmentQuery referTypeLessThanOrEqual(
			Integer referTypeLessThanOrEqual) {
		if (referTypeLessThanOrEqual == null) {
			throw new RuntimeException("referType is null");
		}
		this.referTypeLessThanOrEqual = referTypeLessThanOrEqual;
		return this;
	}

	public AttachmentQuery referTypes(List<Integer> referTypes) {
		if (referTypes == null) {
			throw new RuntimeException("referTypes is empty ");
		}
		this.referTypes = referTypes;
		return this;
	}

	public AttachmentQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public AttachmentQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public AttachmentQuery url(String url) {
		if (url == null) {
			throw new RuntimeException("url is null");
		}
		this.url = url;
		return this;
	}

	public AttachmentQuery urlLike(String urlLike) {
		if (urlLike == null) {
			throw new RuntimeException("url is null");
		}
		this.urlLike = urlLike;
		return this;
	}

	public AttachmentQuery createDate(Date createDate) {
		if (createDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDate = createDate;
		return this;
	}

	public AttachmentQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public AttachmentQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public AttachmentQuery createId(Long createId) {
		if (createId == null) {
			throw new RuntimeException("createId is null");
		}
		this.createId = createId;
		return this;
	}

	public AttachmentQuery createIdGreaterThanOrEqual(
			Long createIdGreaterThanOrEqual) {
		if (createIdGreaterThanOrEqual == null) {
			throw new RuntimeException("createId is null");
		}
		this.createIdGreaterThanOrEqual = createIdGreaterThanOrEqual;
		return this;
	}

	public AttachmentQuery createIdLessThanOrEqual(Long createIdLessThanOrEqual) {
		if (createIdLessThanOrEqual == null) {
			throw new RuntimeException("createId is null");
		}
		this.createIdLessThanOrEqual = createIdLessThanOrEqual;
		return this;
	}

	public AttachmentQuery createIds(List<Long> createIds) {
		if (createIds == null) {
			throw new RuntimeException("createIds is empty ");
		}
		this.createIds = createIds;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("referId".equals(sortColumn)) {
				orderBy = "E.REFERID" + a_x;
			}

			if ("referType".equals(sortColumn)) {
				orderBy = "E.REFERTYPE" + a_x;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME" + a_x;
			}

			if ("url".equals(sortColumn)) {
				orderBy = "E.URL" + a_x;
			}

			if ("createDate".equals(sortColumn)) {
				orderBy = "E.CREATEDATE" + a_x;
			}

			if ("createId".equals(sortColumn)) {
				orderBy = "E.CREATEID" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("referId", "REFERID");
		addColumn("referType", "REFERTYPE");
		addColumn("name", "NAME");
		addColumn("url", "URL");
		addColumn("createDate", "CREATEDATE");
		addColumn("createId", "CREATEID");
	}

}