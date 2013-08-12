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
package com.glaf.oa.traveladdress.query;

import java.util.*;

import com.glaf.core.query.DataQuery;

public class TraveladdressQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> addressids;
	protected Long travelid;
	protected Long travelidGreaterThanOrEqual;
	protected Long travelidLessThanOrEqual;
	protected List<Long> travelids;
	protected String startadd;
	protected String startaddLike;
	protected List<String> startadds;
	protected String endadd;
	protected String endaddLike;
	protected List<String> endadds;
	protected String transportation;
	protected String transportationLike;
	protected List<String> transportations;
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

	public TraveladdressQuery() {

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

	public String getStartadd() {
		return startadd;
	}

	public String getStartaddLike() {
		if (startaddLike != null && startaddLike.trim().length() > 0) {
			if (!startaddLike.startsWith("%")) {
				startaddLike = "%" + startaddLike;
			}
			if (!startaddLike.endsWith("%")) {
				startaddLike = startaddLike + "%";
			}
		}
		return startaddLike;
	}

	public List<String> getStartadds() {
		return startadds;
	}

	public String getEndadd() {
		return endadd;
	}

	public String getEndaddLike() {
		if (endaddLike != null && endaddLike.trim().length() > 0) {
			if (!endaddLike.startsWith("%")) {
				endaddLike = "%" + endaddLike;
			}
			if (!endaddLike.endsWith("%")) {
				endaddLike = endaddLike + "%";
			}
		}
		return endaddLike;
	}

	public List<String> getEndadds() {
		return endadds;
	}

	public String getTransportation() {
		return transportation;
	}

	public String getTransportationLike() {
		if (transportationLike != null
				&& transportationLike.trim().length() > 0) {
			if (!transportationLike.startsWith("%")) {
				transportationLike = "%" + transportationLike;
			}
			if (!transportationLike.endsWith("%")) {
				transportationLike = transportationLike + "%";
			}
		}
		return transportationLike;
	}

	public List<String> getTransportations() {
		return transportations;
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

	public void setStartadd(String startadd) {
		this.startadd = startadd;
	}

	public void setStartaddLike(String startaddLike) {
		this.startaddLike = startaddLike;
	}

	public void setStartadds(List<String> startadds) {
		this.startadds = startadds;
	}

	public void setEndadd(String endadd) {
		this.endadd = endadd;
	}

	public void setEndaddLike(String endaddLike) {
		this.endaddLike = endaddLike;
	}

	public void setEndadds(List<String> endadds) {
		this.endadds = endadds;
	}

	public void setTransportation(String transportation) {
		this.transportation = transportation;
	}

	public void setTransportationLike(String transportationLike) {
		this.transportationLike = transportationLike;
	}

	public void setTransportations(List<String> transportations) {
		this.transportations = transportations;
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

	public TraveladdressQuery travelid(Long travelid) {
		if (travelid == null) {
			throw new RuntimeException("travelid is null");
		}
		this.travelid = travelid;
		return this;
	}

	public TraveladdressQuery travelidGreaterThanOrEqual(
			Long travelidGreaterThanOrEqual) {
		if (travelidGreaterThanOrEqual == null) {
			throw new RuntimeException("travelid is null");
		}
		this.travelidGreaterThanOrEqual = travelidGreaterThanOrEqual;
		return this;
	}

	public TraveladdressQuery travelidLessThanOrEqual(
			Long travelidLessThanOrEqual) {
		if (travelidLessThanOrEqual == null) {
			throw new RuntimeException("travelid is null");
		}
		this.travelidLessThanOrEqual = travelidLessThanOrEqual;
		return this;
	}

	public TraveladdressQuery travelids(List<Long> travelids) {
		if (travelids == null) {
			throw new RuntimeException("travelids is empty ");
		}
		this.travelids = travelids;
		return this;
	}

	public TraveladdressQuery startadd(String startadd) {
		if (startadd == null) {
			throw new RuntimeException("startadd is null");
		}
		this.startadd = startadd;
		return this;
	}

	public TraveladdressQuery startaddLike(String startaddLike) {
		if (startaddLike == null) {
			throw new RuntimeException("startadd is null");
		}
		this.startaddLike = startaddLike;
		return this;
	}

	public TraveladdressQuery startadds(List<String> startadds) {
		if (startadds == null) {
			throw new RuntimeException("startadds is empty ");
		}
		this.startadds = startadds;
		return this;
	}

	public TraveladdressQuery endadd(String endadd) {
		if (endadd == null) {
			throw new RuntimeException("endadd is null");
		}
		this.endadd = endadd;
		return this;
	}

	public TraveladdressQuery endaddLike(String endaddLike) {
		if (endaddLike == null) {
			throw new RuntimeException("endadd is null");
		}
		this.endaddLike = endaddLike;
		return this;
	}

	public TraveladdressQuery endadds(List<String> endadds) {
		if (endadds == null) {
			throw new RuntimeException("endadds is empty ");
		}
		this.endadds = endadds;
		return this;
	}

	public TraveladdressQuery transportation(String transportation) {
		if (transportation == null) {
			throw new RuntimeException("transportation is null");
		}
		this.transportation = transportation;
		return this;
	}

	public TraveladdressQuery transportationLike(String transportationLike) {
		if (transportationLike == null) {
			throw new RuntimeException("transportation is null");
		}
		this.transportationLike = transportationLike;
		return this;
	}

	public TraveladdressQuery transportations(List<String> transportations) {
		if (transportations == null) {
			throw new RuntimeException("transportations is empty ");
		}
		this.transportations = transportations;
		return this;
	}

	public TraveladdressQuery createDate(Date createDate) {
		if (createDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDate = createDate;
		return this;
	}

	public TraveladdressQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public TraveladdressQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public TraveladdressQuery createDates(List<Date> createDates) {
		if (createDates == null) {
			throw new RuntimeException("createDates is empty ");
		}
		this.createDates = createDates;
		return this;
	}

	public TraveladdressQuery updateDate(Date updateDate) {
		if (updateDate == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDate = updateDate;
		return this;
	}

	public TraveladdressQuery updateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public TraveladdressQuery updateDateLessThanOrEqual(
			Date updateDateLessThanOrEqual) {
		if (updateDateLessThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
		return this;
	}

	public TraveladdressQuery updateDates(List<Date> updateDates) {
		if (updateDates == null) {
			throw new RuntimeException("updateDates is empty ");
		}
		this.updateDates = updateDates;
		return this;
	}

	public TraveladdressQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public TraveladdressQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public TraveladdressQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public TraveladdressQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public TraveladdressQuery updateByLike(String updateByLike) {
		if (updateByLike == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateByLike = updateByLike;
		return this;
	}

	public TraveladdressQuery updateBys(List<String> updateBys) {
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

			if ("startadd".equals(sortColumn)) {
				orderBy = "E.startadd" + a_x;
			}

			if ("endadd".equals(sortColumn)) {
				orderBy = "E.endadd" + a_x;
			}

			if ("transportation".equals(sortColumn)) {
				orderBy = "E.transportation" + a_x;
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
		addColumn("addressid", "addressid");
		addColumn("travelid", "travelid");
		addColumn("startadd", "startadd");
		addColumn("endadd", "endadd");
		addColumn("transportation", "transportation");
		addColumn("createDate", "createDate");
		addColumn("updateDate", "updateDate");
		addColumn("createBy", "createBy");
		addColumn("updateBy", "updateBy");
	}

}