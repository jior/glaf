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
package com.glaf.oa.assesscontent.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class AssesscontentQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> contentids;
	protected Long sortid;
	protected Long sortidGreaterThanOrEqual;
	protected Long sortidLessThanOrEqual;
	protected List<Long> sortids;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected Double standard;
	protected Double standardGreaterThanOrEqual;
	protected Double standardLessThanOrEqual;
	protected List<Double> standards;
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

	public AssesscontentQuery() {

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

	public Double getStandard() {
		return standard;
	}

	public Double getStandardGreaterThanOrEqual() {
		return standardGreaterThanOrEqual;
	}

	public Double getStandardLessThanOrEqual() {
		return standardLessThanOrEqual;
	}

	public List<Double> getStandards() {
		return standards;
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

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public void setStandard(Double standard) {
		this.standard = standard;
	}

	public void setStandardGreaterThanOrEqual(Double standardGreaterThanOrEqual) {
		this.standardGreaterThanOrEqual = standardGreaterThanOrEqual;
	}

	public void setStandardLessThanOrEqual(Double standardLessThanOrEqual) {
		this.standardLessThanOrEqual = standardLessThanOrEqual;
	}

	public void setStandards(List<Double> standards) {
		this.standards = standards;
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

	public AssesscontentQuery sortid(Long sortid) {
		if (sortid == null) {
			throw new RuntimeException("sortid is null");
		}
		this.sortid = sortid;
		return this;
	}

	public AssesscontentQuery sortidGreaterThanOrEqual(
			Long sortidGreaterThanOrEqual) {
		if (sortidGreaterThanOrEqual == null) {
			throw new RuntimeException("sortid is null");
		}
		this.sortidGreaterThanOrEqual = sortidGreaterThanOrEqual;
		return this;
	}

	public AssesscontentQuery sortidLessThanOrEqual(Long sortidLessThanOrEqual) {
		if (sortidLessThanOrEqual == null) {
			throw new RuntimeException("sortid is null");
		}
		this.sortidLessThanOrEqual = sortidLessThanOrEqual;
		return this;
	}

	public AssesscontentQuery sortids(List<Long> sortids) {
		if (sortids == null) {
			throw new RuntimeException("sortids is empty ");
		}
		this.sortids = sortids;
		return this;
	}

	public AssesscontentQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public AssesscontentQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public AssesscontentQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public AssesscontentQuery standard(Double standard) {
		if (standard == null) {
			throw new RuntimeException("standard is null");
		}
		this.standard = standard;
		return this;
	}

	public AssesscontentQuery standardGreaterThanOrEqual(
			Double standardGreaterThanOrEqual) {
		if (standardGreaterThanOrEqual == null) {
			throw new RuntimeException("standard is null");
		}
		this.standardGreaterThanOrEqual = standardGreaterThanOrEqual;
		return this;
	}

	public AssesscontentQuery standardLessThanOrEqual(
			Double standardLessThanOrEqual) {
		if (standardLessThanOrEqual == null) {
			throw new RuntimeException("standard is null");
		}
		this.standardLessThanOrEqual = standardLessThanOrEqual;
		return this;
	}

	public AssesscontentQuery standards(List<Double> standards) {
		if (standards == null) {
			throw new RuntimeException("standards is empty ");
		}
		this.standards = standards;
		return this;
	}

	public AssesscontentQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public AssesscontentQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public AssesscontentQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public AssesscontentQuery createDate(Date createDate) {
		if (createDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDate = createDate;
		return this;
	}

	public AssesscontentQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public AssesscontentQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public AssesscontentQuery createDates(List<Date> createDates) {
		if (createDates == null) {
			throw new RuntimeException("createDates is empty ");
		}
		this.createDates = createDates;
		return this;
	}

	public AssesscontentQuery updateDate(Date updateDate) {
		if (updateDate == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDate = updateDate;
		return this;
	}

	public AssesscontentQuery updateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public AssesscontentQuery updateDateLessThanOrEqual(
			Date updateDateLessThanOrEqual) {
		if (updateDateLessThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
		return this;
	}

	public AssesscontentQuery updateDates(List<Date> updateDates) {
		if (updateDates == null) {
			throw new RuntimeException("updateDates is empty ");
		}
		this.updateDates = updateDates;
		return this;
	}

	public AssesscontentQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public AssesscontentQuery updateByLike(String updateByLike) {
		if (updateByLike == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateByLike = updateByLike;
		return this;
	}

	public AssesscontentQuery updateBys(List<String> updateBys) {
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

			if ("sortid".equals(sortColumn)) {
				orderBy = "E.sortid" + a_x;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.name" + a_x;
			}

			if ("standard".equals(sortColumn)) {
				orderBy = "E.standard" + a_x;
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
		addColumn("contentid", "contentid");
		addColumn("sortid", "sortid");
		addColumn("name", "name");
		addColumn("standard", "standard");
		addColumn("createBy", "createBy");
		addColumn("createDate", "createDate");
		addColumn("updateDate", "updateDate");
		addColumn("updateBy", "updateBy");
	}

}