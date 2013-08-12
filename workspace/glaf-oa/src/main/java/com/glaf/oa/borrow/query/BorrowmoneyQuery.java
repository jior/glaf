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
package com.glaf.oa.borrow.query;

import java.util.Date;
import java.util.List;

import com.glaf.core.query.DataQuery;

public class BorrowmoneyQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> borrowmoneyids;
	protected Long borrowid;
	protected Long borrowidGreaterThanOrEqual;
	protected Long borrowidLessThanOrEqual;
	protected List<Long> borrowids;
	protected String feename;
	protected String feenameLike;
	protected List<String> feenames;
	protected Double feesum;
	protected Double feesumGreaterThanOrEqual;
	protected Double feesumLessThanOrEqual;
	protected List<Double> feesums;
	protected String remark;
	protected String remarkLike;
	protected List<String> remarks;
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

	public BorrowmoneyQuery() {

	}

	public Long getBorrowid() {
		return borrowid;
	}

	public Long getBorrowidGreaterThanOrEqual() {
		return borrowidGreaterThanOrEqual;
	}

	public Long getBorrowidLessThanOrEqual() {
		return borrowidLessThanOrEqual;
	}

	public List<Long> getBorrowids() {
		return borrowids;
	}

	public String getFeename() {
		return feename;
	}

	public String getFeenameLike() {
		if (feenameLike != null && feenameLike.trim().length() > 0) {
			if (!feenameLike.startsWith("%")) {
				feenameLike = "%" + feenameLike;
			}
			if (!feenameLike.endsWith("%")) {
				feenameLike = feenameLike + "%";
			}
		}
		return feenameLike;
	}

	public List<String> getFeenames() {
		return feenames;
	}

	public Double getFeesum() {
		return feesum;
	}

	public Double getFeesumGreaterThanOrEqual() {
		return feesumGreaterThanOrEqual;
	}

	public Double getFeesumLessThanOrEqual() {
		return feesumLessThanOrEqual;
	}

	public List<Double> getFeesums() {
		return feesums;
	}

	public String getRemark() {
		return remark;
	}

	public String getRemarkLike() {
		if (remarkLike != null && remarkLike.trim().length() > 0) {
			if (!remarkLike.startsWith("%")) {
				remarkLike = "%" + remarkLike;
			}
			if (!remarkLike.endsWith("%")) {
				remarkLike = remarkLike + "%";
			}
		}
		return remarkLike;
	}

	public List<String> getRemarks() {
		return remarks;
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

	public void setBorrowid(Long borrowid) {
		this.borrowid = borrowid;
	}

	public void setBorrowidGreaterThanOrEqual(Long borrowidGreaterThanOrEqual) {
		this.borrowidGreaterThanOrEqual = borrowidGreaterThanOrEqual;
	}

	public void setBorrowidLessThanOrEqual(Long borrowidLessThanOrEqual) {
		this.borrowidLessThanOrEqual = borrowidLessThanOrEqual;
	}

	public void setBorrowids(List<Long> borrowids) {
		this.borrowids = borrowids;
	}

	public void setFeename(String feename) {
		this.feename = feename;
	}

	public void setFeenameLike(String feenameLike) {
		this.feenameLike = feenameLike;
	}

	public void setFeenames(List<String> feenames) {
		this.feenames = feenames;
	}

	public void setFeesum(Double feesum) {
		this.feesum = feesum;
	}

	public void setFeesumGreaterThanOrEqual(Double feesumGreaterThanOrEqual) {
		this.feesumGreaterThanOrEqual = feesumGreaterThanOrEqual;
	}

	public void setFeesumLessThanOrEqual(Double feesumLessThanOrEqual) {
		this.feesumLessThanOrEqual = feesumLessThanOrEqual;
	}

	public void setFeesums(List<Double> feesums) {
		this.feesums = feesums;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setRemarkLike(String remarkLike) {
		this.remarkLike = remarkLike;
	}

	public void setRemarks(List<String> remarks) {
		this.remarks = remarks;
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

	public BorrowmoneyQuery borrowid(Long borrowid) {
		if (borrowid == null) {
			throw new RuntimeException("borrowid is null");
		}
		this.borrowid = borrowid;
		return this;
	}

	public BorrowmoneyQuery borrowidGreaterThanOrEqual(
			Long borrowidGreaterThanOrEqual) {
		if (borrowidGreaterThanOrEqual == null) {
			throw new RuntimeException("borrowid is null");
		}
		this.borrowidGreaterThanOrEqual = borrowidGreaterThanOrEqual;
		return this;
	}

	public BorrowmoneyQuery borrowidLessThanOrEqual(Long borrowidLessThanOrEqual) {
		if (borrowidLessThanOrEqual == null) {
			throw new RuntimeException("borrowid is null");
		}
		this.borrowidLessThanOrEqual = borrowidLessThanOrEqual;
		return this;
	}

	public BorrowmoneyQuery borrowids(List<Long> borrowids) {
		if (borrowids == null) {
			throw new RuntimeException("borrowids is empty ");
		}
		this.borrowids = borrowids;
		return this;
	}

	public BorrowmoneyQuery feename(String feename) {
		if (feename == null) {
			throw new RuntimeException("feename is null");
		}
		this.feename = feename;
		return this;
	}

	public BorrowmoneyQuery feenameLike(String feenameLike) {
		if (feenameLike == null) {
			throw new RuntimeException("feename is null");
		}
		this.feenameLike = feenameLike;
		return this;
	}

	public BorrowmoneyQuery feenames(List<String> feenames) {
		if (feenames == null) {
			throw new RuntimeException("feenames is empty ");
		}
		this.feenames = feenames;
		return this;
	}

	public BorrowmoneyQuery feesum(Double feesum) {
		if (feesum == null) {
			throw new RuntimeException("feesum is null");
		}
		this.feesum = feesum;
		return this;
	}

	public BorrowmoneyQuery feesumGreaterThanOrEqual(
			Double feesumGreaterThanOrEqual) {
		if (feesumGreaterThanOrEqual == null) {
			throw new RuntimeException("feesum is null");
		}
		this.feesumGreaterThanOrEqual = feesumGreaterThanOrEqual;
		return this;
	}

	public BorrowmoneyQuery feesumLessThanOrEqual(Double feesumLessThanOrEqual) {
		if (feesumLessThanOrEqual == null) {
			throw new RuntimeException("feesum is null");
		}
		this.feesumLessThanOrEqual = feesumLessThanOrEqual;
		return this;
	}

	public BorrowmoneyQuery feesums(List<Double> feesums) {
		if (feesums == null) {
			throw new RuntimeException("feesums is empty ");
		}
		this.feesums = feesums;
		return this;
	}

	public BorrowmoneyQuery remark(String remark) {
		if (remark == null) {
			throw new RuntimeException("remark is null");
		}
		this.remark = remark;
		return this;
	}

	public BorrowmoneyQuery remarkLike(String remarkLike) {
		if (remarkLike == null) {
			throw new RuntimeException("remark is null");
		}
		this.remarkLike = remarkLike;
		return this;
	}

	public BorrowmoneyQuery remarks(List<String> remarks) {
		if (remarks == null) {
			throw new RuntimeException("remarks is empty ");
		}
		this.remarks = remarks;
		return this;
	}

	public BorrowmoneyQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public BorrowmoneyQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public BorrowmoneyQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public BorrowmoneyQuery createDate(Date createDate) {
		if (createDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDate = createDate;
		return this;
	}

	public BorrowmoneyQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public BorrowmoneyQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public BorrowmoneyQuery createDates(List<Date> createDates) {
		if (createDates == null) {
			throw new RuntimeException("createDates is empty ");
		}
		this.createDates = createDates;
		return this;
	}

	public BorrowmoneyQuery updateDate(Date updateDate) {
		if (updateDate == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDate = updateDate;
		return this;
	}

	public BorrowmoneyQuery updateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public BorrowmoneyQuery updateDateLessThanOrEqual(
			Date updateDateLessThanOrEqual) {
		if (updateDateLessThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
		return this;
	}

	public BorrowmoneyQuery updateDates(List<Date> updateDates) {
		if (updateDates == null) {
			throw new RuntimeException("updateDates is empty ");
		}
		this.updateDates = updateDates;
		return this;
	}

	public BorrowmoneyQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public BorrowmoneyQuery updateByLike(String updateByLike) {
		if (updateByLike == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateByLike = updateByLike;
		return this;
	}

	public BorrowmoneyQuery updateBys(List<String> updateBys) {
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

			if ("borrowid".equals(sortColumn)) {
				orderBy = "E.borrowid" + a_x;
			}

			if ("feename".equals(sortColumn)) {
				orderBy = "E.feename" + a_x;
			}

			if ("feesum".equals(sortColumn)) {
				orderBy = "E.feesum" + a_x;
			}

			if ("remark".equals(sortColumn)) {
				orderBy = "E.remark" + a_x;
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
		addColumn("borrowmoneyid", "borrowmoneyid");
		addColumn("borrowid", "borrowid");
		addColumn("feename", "feename");
		addColumn("feesum", "feesum");
		addColumn("remark", "remark");
		addColumn("createBy", "createBy");
		addColumn("createDate", "createDate");
		addColumn("updateDate", "updateDate");
		addColumn("updateBy", "updateBy");
	}

}