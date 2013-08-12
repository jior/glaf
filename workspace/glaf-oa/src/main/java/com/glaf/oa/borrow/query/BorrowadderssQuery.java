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

public class BorrowadderssQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> addressids;
	protected Long borrowid;
	protected Long borrowidGreaterThanOrEqual;
	protected Long borrowidLessThanOrEqual;
	protected List<Long> borrowids;
	protected String start;
	protected String startLike;
	protected List<String> starts;
	protected String reach;
	protected String reachLike;
	protected List<String> reachs;
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

	public BorrowadderssQuery() {

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

	public String getStart() {
		return start;
	}

	public String getStartLike() {
		if (startLike != null && startLike.trim().length() > 0) {
			if (!startLike.startsWith("%")) {
				startLike = "%" + startLike;
			}
			if (!startLike.endsWith("%")) {
				startLike = startLike + "%";
			}
		}
		return startLike;
	}

	public List<String> getStarts() {
		return starts;
	}

	public String getReach() {
		return reach;
	}

	public String getReachLike() {
		if (reachLike != null && reachLike.trim().length() > 0) {
			if (!reachLike.startsWith("%")) {
				reachLike = "%" + reachLike;
			}
			if (!reachLike.endsWith("%")) {
				reachLike = reachLike + "%";
			}
		}
		return reachLike;
	}

	public List<String> getReachs() {
		return reachs;
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

	public void setStart(String start) {
		this.start = start;
	}

	public void setStartLike(String startLike) {
		this.startLike = startLike;
	}

	public void setStarts(List<String> starts) {
		this.starts = starts;
	}

	public void setReach(String reach) {
		this.reach = reach;
	}

	public void setReachLike(String reachLike) {
		this.reachLike = reachLike;
	}

	public void setReachs(List<String> reachs) {
		this.reachs = reachs;
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

	public BorrowadderssQuery borrowid(Long borrowid) {
		if (borrowid == null) {
			throw new RuntimeException("borrowid is null");
		}
		this.borrowid = borrowid;
		return this;
	}

	public BorrowadderssQuery borrowidGreaterThanOrEqual(
			Long borrowidGreaterThanOrEqual) {
		if (borrowidGreaterThanOrEqual == null) {
			throw new RuntimeException("borrowid is null");
		}
		this.borrowidGreaterThanOrEqual = borrowidGreaterThanOrEqual;
		return this;
	}

	public BorrowadderssQuery borrowidLessThanOrEqual(
			Long borrowidLessThanOrEqual) {
		if (borrowidLessThanOrEqual == null) {
			throw new RuntimeException("borrowid is null");
		}
		this.borrowidLessThanOrEqual = borrowidLessThanOrEqual;
		return this;
	}

	public BorrowadderssQuery borrowids(List<Long> borrowids) {
		if (borrowids == null) {
			throw new RuntimeException("borrowids is empty ");
		}
		this.borrowids = borrowids;
		return this;
	}

	public BorrowadderssQuery start(String start) {
		if (start == null) {
			throw new RuntimeException("start is null");
		}
		this.start = start;
		return this;
	}

	public BorrowadderssQuery startLike(String startLike) {
		if (startLike == null) {
			throw new RuntimeException("start is null");
		}
		this.startLike = startLike;
		return this;
	}

	public BorrowadderssQuery starts(List<String> starts) {
		if (starts == null) {
			throw new RuntimeException("starts is empty ");
		}
		this.starts = starts;
		return this;
	}

	public BorrowadderssQuery reach(String reach) {
		if (reach == null) {
			throw new RuntimeException("reach is null");
		}
		this.reach = reach;
		return this;
	}

	public BorrowadderssQuery reachLike(String reachLike) {
		if (reachLike == null) {
			throw new RuntimeException("reach is null");
		}
		this.reachLike = reachLike;
		return this;
	}

	public BorrowadderssQuery reachs(List<String> reachs) {
		if (reachs == null) {
			throw new RuntimeException("reachs is empty ");
		}
		this.reachs = reachs;
		return this;
	}

	public BorrowadderssQuery remark(String remark) {
		if (remark == null) {
			throw new RuntimeException("remark is null");
		}
		this.remark = remark;
		return this;
	}

	public BorrowadderssQuery remarkLike(String remarkLike) {
		if (remarkLike == null) {
			throw new RuntimeException("remark is null");
		}
		this.remarkLike = remarkLike;
		return this;
	}

	public BorrowadderssQuery remarks(List<String> remarks) {
		if (remarks == null) {
			throw new RuntimeException("remarks is empty ");
		}
		this.remarks = remarks;
		return this;
	}

	public BorrowadderssQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public BorrowadderssQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public BorrowadderssQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public BorrowadderssQuery createDate(Date createDate) {
		if (createDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDate = createDate;
		return this;
	}

	public BorrowadderssQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public BorrowadderssQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public BorrowadderssQuery createDates(List<Date> createDates) {
		if (createDates == null) {
			throw new RuntimeException("createDates is empty ");
		}
		this.createDates = createDates;
		return this;
	}

	public BorrowadderssQuery updateDate(Date updateDate) {
		if (updateDate == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDate = updateDate;
		return this;
	}

	public BorrowadderssQuery updateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public BorrowadderssQuery updateDateLessThanOrEqual(
			Date updateDateLessThanOrEqual) {
		if (updateDateLessThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
		return this;
	}

	public BorrowadderssQuery updateDates(List<Date> updateDates) {
		if (updateDates == null) {
			throw new RuntimeException("updateDates is empty ");
		}
		this.updateDates = updateDates;
		return this;
	}

	public BorrowadderssQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public BorrowadderssQuery updateByLike(String updateByLike) {
		if (updateByLike == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateByLike = updateByLike;
		return this;
	}

	public BorrowadderssQuery updateBys(List<String> updateBys) {
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

			if ("start".equals(sortColumn)) {
				orderBy = "E.start" + a_x;
			}

			if ("reach".equals(sortColumn)) {
				orderBy = "E.reach" + a_x;
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
		addColumn("addressid", "addressid");
		addColumn("borrowid", "borrowid");
		addColumn("start", "start");
		addColumn("reach", "reach");
		addColumn("remark", "remark");
		addColumn("createBy", "createBy");
		addColumn("createDate", "createDate");
		addColumn("updateDate", "updateDate");
		addColumn("updateBy", "updateBy");
	}

}