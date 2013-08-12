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
package com.glaf.oa.assesssort.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class AssesssortQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> assesssortids;
	protected Long qustionid;
	protected Long qustionidGreaterThanOrEqual;
	protected Long qustionidLessThanOrEqual;
	protected List<Long> qustionids;
	protected Long sortid;
	protected Long sortidGreaterThanOrEqual;
	protected Long sortidLessThanOrEqual;
	protected List<Long> sortids;
	protected String createByLike;
	protected List<String> createBys;
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;
	protected List<Date> createDates;
	protected Date updateDate;
	protected Date updateDateGreaterThanOrEqual;
	protected Date updateDateLessThanOrEqual;
	protected List<Date> updateDates;
	protected String updateBy;
	protected String updateByLike;
	protected List<String> updateBys;

	public AssesssortQuery() {

	}

	public Long getQustionid() {
		return qustionid;
	}

	public Long getQustionidGreaterThanOrEqual() {
		return qustionidGreaterThanOrEqual;
	}

	public Long getQustionidLessThanOrEqual() {
		return qustionidLessThanOrEqual;
	}

	public List<Long> getQustionids() {
		return qustionids;
	}

	public Long getSortid() {
		return sortid;
	}

	public Long getSortidGreaterThanOrEqual() {
		return sortidGreaterThanOrEqual;
	}

	public Long getSortidLessThanOrEqual() {
		return sortidLessThanOrEqual;
	}

	public List<Long> getSortids() {
		return sortids;
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

	public List<String> getCreateBys() {
		return createBys;
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

	public List<Date> getCreateDates() {
		return createDates;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public Date getUpdateDateGreaterThanOrEqual() {
		return updateDateGreaterThanOrEqual;
	}

	public Date getUpdateDateLessThanOrEqual() {
		return updateDateLessThanOrEqual;
	}

	public List<Date> getUpdateDates() {
		return updateDates;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public String getUpdateByLike() {
		if (updateByLike != null && updateByLike.trim().length() > 0) {
			if (!updateByLike.startsWith("%")) {
				updateByLike = "%" + updateByLike;
			}
			if (!updateByLike.endsWith("%")) {
				updateByLike = updateByLike + "%";
			}
		}
		return updateByLike;
	}

	public List<String> getUpdateBys() {
		return updateBys;
	}

	public void setQustionid(Long qustionid) {
		this.qustionid = qustionid;
	}

	public void setQustionidGreaterThanOrEqual(Long qustionidGreaterThanOrEqual) {
		this.qustionidGreaterThanOrEqual = qustionidGreaterThanOrEqual;
	}

	public void setQustionidLessThanOrEqual(Long qustionidLessThanOrEqual) {
		this.qustionidLessThanOrEqual = qustionidLessThanOrEqual;
	}

	public void setQustionids(List<Long> qustionids) {
		this.qustionids = qustionids;
	}

	public void setSortid(Long sortid) {
		this.sortid = sortid;
	}

	public void setSortidGreaterThanOrEqual(Long sortidGreaterThanOrEqual) {
		this.sortidGreaterThanOrEqual = sortidGreaterThanOrEqual;
	}

	public void setSortidLessThanOrEqual(Long sortidLessThanOrEqual) {
		this.sortidLessThanOrEqual = sortidLessThanOrEqual;
	}

	public void setSortids(List<Long> sortids) {
		this.sortids = sortids;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateByLike(String createByLike) {
		this.createByLike = createByLike;
	}

	public void setCreateBys(List<String> createBys) {
		this.createBys = createBys;
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

	public void setCreateDates(List<Date> createDates) {
		this.createDates = createDates;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setUpdateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
	}

	public void setUpdateDateLessThanOrEqual(Date updateDateLessThanOrEqual) {
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
	}

	public void setUpdateDates(List<Date> updateDates) {
		this.updateDates = updateDates;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateByLike(String updateByLike) {
		this.updateByLike = updateByLike;
	}

	public void setUpdateBys(List<String> updateBys) {
		this.updateBys = updateBys;
	}

	public AssesssortQuery qustionid(Long qustionid) {
		if (qustionid == null) {
			throw new RuntimeException("qustionid is null");
		}
		this.qustionid = qustionid;
		return this;
	}

	public AssesssortQuery qustionidGreaterThanOrEqual(
			Long qustionidGreaterThanOrEqual) {
		if (qustionidGreaterThanOrEqual == null) {
			throw new RuntimeException("qustionid is null");
		}
		this.qustionidGreaterThanOrEqual = qustionidGreaterThanOrEqual;
		return this;
	}

	public AssesssortQuery qustionidLessThanOrEqual(
			Long qustionidLessThanOrEqual) {
		if (qustionidLessThanOrEqual == null) {
			throw new RuntimeException("qustionid is null");
		}
		this.qustionidLessThanOrEqual = qustionidLessThanOrEqual;
		return this;
	}

	public AssesssortQuery qustionids(List<Long> qustionids) {
		if (qustionids == null) {
			throw new RuntimeException("qustionids is empty ");
		}
		this.qustionids = qustionids;
		return this;
	}

	public AssesssortQuery sortid(Long sortid) {
		if (sortid == null) {
			throw new RuntimeException("sortid is null");
		}
		this.sortid = sortid;
		return this;
	}

	public AssesssortQuery sortidGreaterThanOrEqual(
			Long sortidGreaterThanOrEqual) {
		if (sortidGreaterThanOrEqual == null) {
			throw new RuntimeException("sortid is null");
		}
		this.sortidGreaterThanOrEqual = sortidGreaterThanOrEqual;
		return this;
	}

	public AssesssortQuery sortidLessThanOrEqual(Long sortidLessThanOrEqual) {
		if (sortidLessThanOrEqual == null) {
			throw new RuntimeException("sortid is null");
		}
		this.sortidLessThanOrEqual = sortidLessThanOrEqual;
		return this;
	}

	public AssesssortQuery sortids(List<Long> sortids) {
		if (sortids == null) {
			throw new RuntimeException("sortids is empty ");
		}
		this.sortids = sortids;
		return this;
	}

	public AssesssortQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public AssesssortQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public AssesssortQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public AssesssortQuery createDate(Date createDate) {
		if (createDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDate = createDate;
		return this;
	}

	public AssesssortQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public AssesssortQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public AssesssortQuery createDates(List<Date> createDates) {
		if (createDates == null) {
			throw new RuntimeException("createDates is empty ");
		}
		this.createDates = createDates;
		return this;
	}

	public AssesssortQuery updateDate(Date updateDate) {
		if (updateDate == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDate = updateDate;
		return this;
	}

	public AssesssortQuery updateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public AssesssortQuery updateDateLessThanOrEqual(
			Date updateDateLessThanOrEqual) {
		if (updateDateLessThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
		return this;
	}

	public AssesssortQuery updateDates(List<Date> updateDates) {
		if (updateDates == null) {
			throw new RuntimeException("updateDates is empty ");
		}
		this.updateDates = updateDates;
		return this;
	}

	public AssesssortQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public AssesssortQuery updateByLike(String updateByLike) {
		if (updateByLike == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateByLike = updateByLike;
		return this;
	}

	public AssesssortQuery updateBys(List<String> updateBys) {
		if (updateBys == null) {
			throw new RuntimeException("updateBys is empty ");
		}
		this.updateBys = updateBys;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("qustionid".equals(sortColumn)) {
				orderBy = "E.qustionid" + a_x;
			}

			if ("sortid".equals(sortColumn)) {
				orderBy = "E.sortid" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.createBy" + a_x;
			}

			if ("createDate".equals(sortColumn)) {
				orderBy = "E.createDate" + a_x;
			}

			if ("updateDate".equals(sortColumn)) {
				orderBy = "E.updateDate" + a_x;
			}

			if ("updateBy".equals(sortColumn)) {
				orderBy = "E.updateBy" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("assesssortid", "assesssortid");
		addColumn("qustionid", "qustionid");
		addColumn("sortid", "sortid");
		addColumn("createBy", "createBy");
		addColumn("createDate", "createDate");
		addColumn("updateDate", "updateDate");
		addColumn("updateBy", "updateBy");
	}

}