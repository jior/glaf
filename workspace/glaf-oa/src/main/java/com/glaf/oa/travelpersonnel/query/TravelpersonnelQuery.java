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
package com.glaf.oa.travelpersonnel.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class TravelpersonnelQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> personnelids;
	protected Long travelid;
	protected Long travelidGreaterThanOrEqual;
	protected Long travelidLessThanOrEqual;
	protected List<Long> travelids;
	protected String dept;
	protected String deptLike;
	protected List<String> depts;
	protected String personnel;
	protected String personnelLike;
	protected List<String> personnels;
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;
	protected List<Date> createDates;
	protected Date updateDate;
	protected Date updateDateGreaterThanOrEqual;
	protected Date updateDateLessThanOrEqual;
	protected List<Date> updateDates;
	protected String createByLike;
	protected List<String> createBys;
	protected String updateBy;
	protected String updateByLike;
	protected List<String> updateBys;

	public TravelpersonnelQuery() {

	}

	public Long getTravelid() {
		return travelid;
	}

	public Long getTravelidGreaterThanOrEqual() {
		return travelidGreaterThanOrEqual;
	}

	public Long getTravelidLessThanOrEqual() {
		return travelidLessThanOrEqual;
	}

	public List<Long> getTravelids() {
		return travelids;
	}

	public String getDept() {
		return dept;
	}

	public String getDeptLike() {
		if (deptLike != null && deptLike.trim().length() > 0) {
			if (!deptLike.startsWith("%")) {
				deptLike = "%" + deptLike;
			}
			if (!deptLike.endsWith("%")) {
				deptLike = deptLike + "%";
			}
		}
		return deptLike;
	}

	public List<String> getDepts() {
		return depts;
	}

	public String getPersonnel() {
		return personnel;
	}

	public String getPersonnelLike() {
		if (personnelLike != null && personnelLike.trim().length() > 0) {
			if (!personnelLike.startsWith("%")) {
				personnelLike = "%" + personnelLike;
			}
			if (!personnelLike.endsWith("%")) {
				personnelLike = personnelLike + "%";
			}
		}
		return personnelLike;
	}

	public List<String> getPersonnels() {
		return personnels;
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

	public void setTravelid(Long travelid) {
		this.travelid = travelid;
	}

	public void setTravelidGreaterThanOrEqual(Long travelidGreaterThanOrEqual) {
		this.travelidGreaterThanOrEqual = travelidGreaterThanOrEqual;
	}

	public void setTravelidLessThanOrEqual(Long travelidLessThanOrEqual) {
		this.travelidLessThanOrEqual = travelidLessThanOrEqual;
	}

	public void setTravelids(List<Long> travelids) {
		this.travelids = travelids;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public void setDeptLike(String deptLike) {
		this.deptLike = deptLike;
	}

	public void setDepts(List<String> depts) {
		this.depts = depts;
	}

	public void setPersonnel(String personnel) {
		this.personnel = personnel;
	}

	public void setPersonnelLike(String personnelLike) {
		this.personnelLike = personnelLike;
	}

	public void setPersonnels(List<String> personnels) {
		this.personnels = personnels;
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

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateByLike(String createByLike) {
		this.createByLike = createByLike;
	}

	public void setCreateBys(List<String> createBys) {
		this.createBys = createBys;
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

	public TravelpersonnelQuery travelid(Long travelid) {
		if (travelid == null) {
			throw new RuntimeException("travelid is null");
		}
		this.travelid = travelid;
		return this;
	}

	public TravelpersonnelQuery travelidGreaterThanOrEqual(
			Long travelidGreaterThanOrEqual) {
		if (travelidGreaterThanOrEqual == null) {
			throw new RuntimeException("travelid is null");
		}
		this.travelidGreaterThanOrEqual = travelidGreaterThanOrEqual;
		return this;
	}

	public TravelpersonnelQuery travelidLessThanOrEqual(
			Long travelidLessThanOrEqual) {
		if (travelidLessThanOrEqual == null) {
			throw new RuntimeException("travelid is null");
		}
		this.travelidLessThanOrEqual = travelidLessThanOrEqual;
		return this;
	}

	public TravelpersonnelQuery travelids(List<Long> travelids) {
		if (travelids == null) {
			throw new RuntimeException("travelids is empty ");
		}
		this.travelids = travelids;
		return this;
	}

	public TravelpersonnelQuery dept(String dept) {
		if (dept == null) {
			throw new RuntimeException("dept is null");
		}
		this.dept = dept;
		return this;
	}

	public TravelpersonnelQuery deptLike(String deptLike) {
		if (deptLike == null) {
			throw new RuntimeException("dept is null");
		}
		this.deptLike = deptLike;
		return this;
	}

	public TravelpersonnelQuery depts(List<String> depts) {
		if (depts == null) {
			throw new RuntimeException("depts is empty ");
		}
		this.depts = depts;
		return this;
	}

	public TravelpersonnelQuery personnel(String personnel) {
		if (personnel == null) {
			throw new RuntimeException("personnel is null");
		}
		this.personnel = personnel;
		return this;
	}

	public TravelpersonnelQuery personnelLike(String personnelLike) {
		if (personnelLike == null) {
			throw new RuntimeException("personnel is null");
		}
		this.personnelLike = personnelLike;
		return this;
	}

	public TravelpersonnelQuery personnels(List<String> personnels) {
		if (personnels == null) {
			throw new RuntimeException("personnels is empty ");
		}
		this.personnels = personnels;
		return this;
	}

	public TravelpersonnelQuery createDate(Date createDate) {
		if (createDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDate = createDate;
		return this;
	}

	public TravelpersonnelQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public TravelpersonnelQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public TravelpersonnelQuery createDates(List<Date> createDates) {
		if (createDates == null) {
			throw new RuntimeException("createDates is empty ");
		}
		this.createDates = createDates;
		return this;
	}

	public TravelpersonnelQuery updateDate(Date updateDate) {
		if (updateDate == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDate = updateDate;
		return this;
	}

	public TravelpersonnelQuery updateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public TravelpersonnelQuery updateDateLessThanOrEqual(
			Date updateDateLessThanOrEqual) {
		if (updateDateLessThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
		return this;
	}

	public TravelpersonnelQuery updateDates(List<Date> updateDates) {
		if (updateDates == null) {
			throw new RuntimeException("updateDates is empty ");
		}
		this.updateDates = updateDates;
		return this;
	}

	public TravelpersonnelQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public TravelpersonnelQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public TravelpersonnelQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public TravelpersonnelQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public TravelpersonnelQuery updateByLike(String updateByLike) {
		if (updateByLike == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateByLike = updateByLike;
		return this;
	}

	public TravelpersonnelQuery updateBys(List<String> updateBys) {
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

			if ("travelid".equals(sortColumn)) {
				orderBy = "E.travelid" + a_x;
			}

			if ("dept".equals(sortColumn)) {
				orderBy = "E.dept" + a_x;
			}

			if ("personnel".equals(sortColumn)) {
				orderBy = "E.personnel" + a_x;
			}

			if ("createDate".equals(sortColumn)) {
				orderBy = "E.createDate" + a_x;
			}

			if ("updateDate".equals(sortColumn)) {
				orderBy = "E.updateDate" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.createBy" + a_x;
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
		addColumn("personnelid", "personnelid");
		addColumn("travelid", "travelid");
		addColumn("dept", "dept");
		addColumn("personnel", "personnel");
		addColumn("createDate", "createDate");
		addColumn("updateDate", "updateDate");
		addColumn("createBy", "createBy");
		addColumn("updateBy", "updateBy");
	}

}