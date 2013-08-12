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
package com.glaf.oa.reimbursement.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class RitemQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> ritemids;
	protected Long reimbursementid;
	protected Long reimbursementidGreaterThanOrEqual;
	protected Long reimbursementidLessThanOrEqual;
	protected List<Long> reimbursementids;
	protected Integer feetype;
	protected Integer feetypeGreaterThanOrEqual;
	protected Integer feetypeLessThanOrEqual;
	protected List<Integer> feetypes;
	protected Date feedate;
	protected Date feedateGreaterThanOrEqual;
	protected Date feedateLessThanOrEqual;
	protected List<Date> feedates;
	protected String subject;
	protected String subjectLike;
	protected List<String> subjects;
	protected String currency;
	protected String currencyLike;
	protected List<String> currencys;
	protected Double itemsum;
	protected Double itemsumGreaterThanOrEqual;
	protected Double itemsumLessThanOrEqual;
	protected List<Double> itemsums;
	protected Double exrate;
	protected Double exrateGreaterThanOrEqual;
	protected Double exrateLessThanOrEqual;
	protected List<Double> exrates;
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

	public RitemQuery() {

	}

	public Long getReimbursementid() {
		return reimbursementid;
	}

	public Long getReimbursementidGreaterThanOrEqual() {
		return reimbursementidGreaterThanOrEqual;
	}

	public Long getReimbursementidLessThanOrEqual() {
		return reimbursementidLessThanOrEqual;
	}

	public List<Long> getReimbursementids() {
		return reimbursementids;
	}

	public Integer getFeetype() {
		return feetype;
	}

	public Integer getFeetypeGreaterThanOrEqual() {
		return feetypeGreaterThanOrEqual;
	}

	public Integer getFeetypeLessThanOrEqual() {
		return feetypeLessThanOrEqual;
	}

	public List<Integer> getFeetypes() {
		return feetypes;
	}

	public Date getFeedate() {
		return feedate;
	}

	public Date getFeedateGreaterThanOrEqual() {
		return feedateGreaterThanOrEqual;
	}

	public Date getFeedateLessThanOrEqual() {
		return feedateLessThanOrEqual;
	}

	public List<Date> getFeedates() {
		return feedates;
	}

	public String getSubject() {
		return subject;
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

	public List<String> getSubjects() {
		return subjects;
	}

	public String getCurrency() {
		return currency;
	}

	public String getCurrencyLike() {
		if (currencyLike != null && currencyLike.trim().length() > 0) {
			if (!currencyLike.startsWith("%")) {
				currencyLike = "%" + currencyLike;
			}
			if (!currencyLike.endsWith("%")) {
				currencyLike = currencyLike + "%";
			}
		}
		return currencyLike;
	}

	public List<String> getCurrencys() {
		return currencys;
	}

	public Double getItemsum() {
		return itemsum;
	}

	public Double getItemsumGreaterThanOrEqual() {
		return itemsumGreaterThanOrEqual;
	}

	public Double getItemsumLessThanOrEqual() {
		return itemsumLessThanOrEqual;
	}

	public List<Double> getItemsums() {
		return itemsums;
	}

	public Double getExrate() {
		return exrate;
	}

	public Double getExrateGreaterThanOrEqual() {
		return exrateGreaterThanOrEqual;
	}

	public Double getExrateLessThanOrEqual() {
		return exrateLessThanOrEqual;
	}

	public List<Double> getExrates() {
		return exrates;
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

	public void setReimbursementid(Long reimbursementid) {
		this.reimbursementid = reimbursementid;
	}

	public void setReimbursementidGreaterThanOrEqual(
			Long reimbursementidGreaterThanOrEqual) {
		this.reimbursementidGreaterThanOrEqual = reimbursementidGreaterThanOrEqual;
	}

	public void setReimbursementidLessThanOrEqual(
			Long reimbursementidLessThanOrEqual) {
		this.reimbursementidLessThanOrEqual = reimbursementidLessThanOrEqual;
	}

	public void setReimbursementids(List<Long> reimbursementids) {
		this.reimbursementids = reimbursementids;
	}

	public void setFeetype(Integer feetype) {
		this.feetype = feetype;
	}

	public void setFeetypeGreaterThanOrEqual(Integer feetypeGreaterThanOrEqual) {
		this.feetypeGreaterThanOrEqual = feetypeGreaterThanOrEqual;
	}

	public void setFeetypeLessThanOrEqual(Integer feetypeLessThanOrEqual) {
		this.feetypeLessThanOrEqual = feetypeLessThanOrEqual;
	}

	public void setFeetypes(List<Integer> feetypes) {
		this.feetypes = feetypes;
	}

	public void setFeedate(Date feedate) {
		this.feedate = feedate;
	}

	public void setFeedateGreaterThanOrEqual(Date feedateGreaterThanOrEqual) {
		this.feedateGreaterThanOrEqual = feedateGreaterThanOrEqual;
	}

	public void setFeedateLessThanOrEqual(Date feedateLessThanOrEqual) {
		this.feedateLessThanOrEqual = feedateLessThanOrEqual;
	}

	public void setFeedates(List<Date> feedates) {
		this.feedates = feedates;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setSubjectLike(String subjectLike) {
		this.subjectLike = subjectLike;
	}

	public void setSubjects(List<String> subjects) {
		this.subjects = subjects;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setCurrencyLike(String currencyLike) {
		this.currencyLike = currencyLike;
	}

	public void setCurrencys(List<String> currencys) {
		this.currencys = currencys;
	}

	public void setItemsum(Double itemsum) {
		this.itemsum = itemsum;
	}

	public void setItemsumGreaterThanOrEqual(Double itemsumGreaterThanOrEqual) {
		this.itemsumGreaterThanOrEqual = itemsumGreaterThanOrEqual;
	}

	public void setItemsumLessThanOrEqual(Double itemsumLessThanOrEqual) {
		this.itemsumLessThanOrEqual = itemsumLessThanOrEqual;
	}

	public void setItemsums(List<Double> itemsums) {
		this.itemsums = itemsums;
	}

	public void setExrate(Double exrate) {
		this.exrate = exrate;
	}

	public void setExrateGreaterThanOrEqual(Double exrateGreaterThanOrEqual) {
		this.exrateGreaterThanOrEqual = exrateGreaterThanOrEqual;
	}

	public void setExrateLessThanOrEqual(Double exrateLessThanOrEqual) {
		this.exrateLessThanOrEqual = exrateLessThanOrEqual;
	}

	public void setExrates(List<Double> exrates) {
		this.exrates = exrates;
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

	public RitemQuery reimbursementid(Long reimbursementid) {
		if (reimbursementid == null) {
			throw new RuntimeException("reimbursementid is null");
		}
		this.reimbursementid = reimbursementid;
		return this;
	}

	public RitemQuery reimbursementidGreaterThanOrEqual(
			Long reimbursementidGreaterThanOrEqual) {
		if (reimbursementidGreaterThanOrEqual == null) {
			throw new RuntimeException("reimbursementid is null");
		}
		this.reimbursementidGreaterThanOrEqual = reimbursementidGreaterThanOrEqual;
		return this;
	}

	public RitemQuery reimbursementidLessThanOrEqual(
			Long reimbursementidLessThanOrEqual) {
		if (reimbursementidLessThanOrEqual == null) {
			throw new RuntimeException("reimbursementid is null");
		}
		this.reimbursementidLessThanOrEqual = reimbursementidLessThanOrEqual;
		return this;
	}

	public RitemQuery reimbursementids(List<Long> reimbursementids) {
		if (reimbursementids == null) {
			throw new RuntimeException("reimbursementids is empty ");
		}
		this.reimbursementids = reimbursementids;
		return this;
	}

	public RitemQuery feetype(Integer feetype) {
		if (feetype == null) {
			throw new RuntimeException("feetype is null");
		}
		this.feetype = feetype;
		return this;
	}

	public RitemQuery feetypeGreaterThanOrEqual(
			Integer feetypeGreaterThanOrEqual) {
		if (feetypeGreaterThanOrEqual == null) {
			throw new RuntimeException("feetype is null");
		}
		this.feetypeGreaterThanOrEqual = feetypeGreaterThanOrEqual;
		return this;
	}

	public RitemQuery feetypeLessThanOrEqual(Integer feetypeLessThanOrEqual) {
		if (feetypeLessThanOrEqual == null) {
			throw new RuntimeException("feetype is null");
		}
		this.feetypeLessThanOrEqual = feetypeLessThanOrEqual;
		return this;
	}

	public RitemQuery feetypes(List<Integer> feetypes) {
		if (feetypes == null) {
			throw new RuntimeException("feetypes is empty ");
		}
		this.feetypes = feetypes;
		return this;
	}

	public RitemQuery feedate(Date feedate) {
		if (feedate == null) {
			throw new RuntimeException("feedate is null");
		}
		this.feedate = feedate;
		return this;
	}

	public RitemQuery feedateGreaterThanOrEqual(Date feedateGreaterThanOrEqual) {
		if (feedateGreaterThanOrEqual == null) {
			throw new RuntimeException("feedate is null");
		}
		this.feedateGreaterThanOrEqual = feedateGreaterThanOrEqual;
		return this;
	}

	public RitemQuery feedateLessThanOrEqual(Date feedateLessThanOrEqual) {
		if (feedateLessThanOrEqual == null) {
			throw new RuntimeException("feedate is null");
		}
		this.feedateLessThanOrEqual = feedateLessThanOrEqual;
		return this;
	}

	public RitemQuery feedates(List<Date> feedates) {
		if (feedates == null) {
			throw new RuntimeException("feedates is empty ");
		}
		this.feedates = feedates;
		return this;
	}

	public RitemQuery subject(String subject) {
		if (subject == null) {
			throw new RuntimeException("subject is null");
		}
		this.subject = subject;
		return this;
	}

	public RitemQuery subjectLike(String subjectLike) {
		if (subjectLike == null) {
			throw new RuntimeException("subject is null");
		}
		this.subjectLike = subjectLike;
		return this;
	}

	public RitemQuery subjects(List<String> subjects) {
		if (subjects == null) {
			throw new RuntimeException("subjects is empty ");
		}
		this.subjects = subjects;
		return this;
	}

	public RitemQuery currency(String currency) {
		if (currency == null) {
			throw new RuntimeException("currency is null");
		}
		this.currency = currency;
		return this;
	}

	public RitemQuery currencyLike(String currencyLike) {
		if (currencyLike == null) {
			throw new RuntimeException("currency is null");
		}
		this.currencyLike = currencyLike;
		return this;
	}

	public RitemQuery currencys(List<String> currencys) {
		if (currencys == null) {
			throw new RuntimeException("currencys is empty ");
		}
		this.currencys = currencys;
		return this;
	}

	public RitemQuery itemsum(Double itemsum) {
		if (itemsum == null) {
			throw new RuntimeException("itemsum is null");
		}
		this.itemsum = itemsum;
		return this;
	}

	public RitemQuery itemsumGreaterThanOrEqual(Double itemsumGreaterThanOrEqual) {
		if (itemsumGreaterThanOrEqual == null) {
			throw new RuntimeException("itemsum is null");
		}
		this.itemsumGreaterThanOrEqual = itemsumGreaterThanOrEqual;
		return this;
	}

	public RitemQuery itemsumLessThanOrEqual(Double itemsumLessThanOrEqual) {
		if (itemsumLessThanOrEqual == null) {
			throw new RuntimeException("itemsum is null");
		}
		this.itemsumLessThanOrEqual = itemsumLessThanOrEqual;
		return this;
	}

	public RitemQuery itemsums(List<Double> itemsums) {
		if (itemsums == null) {
			throw new RuntimeException("itemsums is empty ");
		}
		this.itemsums = itemsums;
		return this;
	}

	public RitemQuery exrate(Double exrate) {
		if (exrate == null) {
			throw new RuntimeException("exrate is null");
		}
		this.exrate = exrate;
		return this;
	}

	public RitemQuery exrateGreaterThanOrEqual(Double exrateGreaterThanOrEqual) {
		if (exrateGreaterThanOrEqual == null) {
			throw new RuntimeException("exrate is null");
		}
		this.exrateGreaterThanOrEqual = exrateGreaterThanOrEqual;
		return this;
	}

	public RitemQuery exrateLessThanOrEqual(Double exrateLessThanOrEqual) {
		if (exrateLessThanOrEqual == null) {
			throw new RuntimeException("exrate is null");
		}
		this.exrateLessThanOrEqual = exrateLessThanOrEqual;
		return this;
	}

	public RitemQuery exrates(List<Double> exrates) {
		if (exrates == null) {
			throw new RuntimeException("exrates is empty ");
		}
		this.exrates = exrates;
		return this;
	}

	public RitemQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public RitemQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public RitemQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public RitemQuery createDate(Date createDate) {
		if (createDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDate = createDate;
		return this;
	}

	public RitemQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public RitemQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public RitemQuery createDates(List<Date> createDates) {
		if (createDates == null) {
			throw new RuntimeException("createDates is empty ");
		}
		this.createDates = createDates;
		return this;
	}

	public RitemQuery updateDate(Date updateDate) {
		if (updateDate == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDate = updateDate;
		return this;
	}

	public RitemQuery updateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public RitemQuery updateDateLessThanOrEqual(Date updateDateLessThanOrEqual) {
		if (updateDateLessThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
		return this;
	}

	public RitemQuery updateDates(List<Date> updateDates) {
		if (updateDates == null) {
			throw new RuntimeException("updateDates is empty ");
		}
		this.updateDates = updateDates;
		return this;
	}

	public RitemQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public RitemQuery updateByLike(String updateByLike) {
		if (updateByLike == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateByLike = updateByLike;
		return this;
	}

	public RitemQuery updateBys(List<String> updateBys) {
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

			if ("reimbursementid".equals(sortColumn)) {
				orderBy = "E.reimbursementid" + a_x;
			}

			if ("feetype".equals(sortColumn)) {
				orderBy = "E.feetype" + a_x;
			}

			if ("feedate".equals(sortColumn)) {
				orderBy = "E.feedate" + a_x;
			}

			if ("subject".equals(sortColumn)) {
				orderBy = "E.subject" + a_x;
			}

			if ("currency".equals(sortColumn)) {
				orderBy = "E.currency" + a_x;
			}

			if ("itemsum".equals(sortColumn)) {
				orderBy = "E.itemsum" + a_x;
			}

			if ("exrate".equals(sortColumn)) {
				orderBy = "E.exrate" + a_x;
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
		addColumn("ritemid", "ritemid");
		addColumn("reimbursementid", "reimbursementid");
		addColumn("feetype", "feetype");
		addColumn("feedate", "feedate");
		addColumn("subject", "subject");
		addColumn("currency", "currency");
		addColumn("itemsum", "itemsum");
		addColumn("exrate", "exrate");
		addColumn("createBy", "createBy");
		addColumn("createDate", "createDate");
		addColumn("updateDate", "updateDate");
		addColumn("updateBy", "updateBy");
	}

}