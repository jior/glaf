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
package com.glaf.oa.paymentplan.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class PaymentplanQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> planids;
	protected Long budgetid;
	protected Long budgetidGreaterThanOrEqual;
	protected Long budgetidLessThanOrEqual;
	protected List<Long> budgetids;
	protected Double paymemtsum;
	protected Double paymemtsumGreaterThanOrEqual;
	protected Double paymemtsumLessThanOrEqual;
	protected List<Double> paymemtsums;
	protected Date paymentdate;
	protected Date paymentdateGreaterThanOrEqual;
	protected Date paymentdateLessThanOrEqual;
	protected List<Date> paymentdates;
	protected Integer sequence;
	protected Integer sequenceGreaterThanOrEqual;
	protected Integer sequenceLessThanOrEqual;
	protected List<Integer> sequences;
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

	public PaymentplanQuery() {

	}

	public Long getBudgetid() {
		return budgetid;
	}

	public Long getBudgetidGreaterThanOrEqual() {
		return budgetidGreaterThanOrEqual;
	}

	public Long getBudgetidLessThanOrEqual() {
		return budgetidLessThanOrEqual;
	}

	public List<Long> getBudgetids() {
		return budgetids;
	}

	public Double getPaymemtsum() {
		return paymemtsum;
	}

	public Double getPaymemtsumGreaterThanOrEqual() {
		return paymemtsumGreaterThanOrEqual;
	}

	public Double getPaymemtsumLessThanOrEqual() {
		return paymemtsumLessThanOrEqual;
	}

	public List<Double> getPaymemtsums() {
		return paymemtsums;
	}

	public Date getPaymentdate() {
		return paymentdate;
	}

	public Date getPaymentdateGreaterThanOrEqual() {
		return paymentdateGreaterThanOrEqual;
	}

	public Date getPaymentdateLessThanOrEqual() {
		return paymentdateLessThanOrEqual;
	}

	public List<Date> getPaymentdates() {
		return paymentdates;
	}

	public Integer getSequence() {
		return sequence;
	}

	public Integer getSequenceGreaterThanOrEqual() {
		return sequenceGreaterThanOrEqual;
	}

	public Integer getSequenceLessThanOrEqual() {
		return sequenceLessThanOrEqual;
	}

	public List<Integer> getSequences() {
		return sequences;
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

	public void setBudgetid(Long budgetid) {
		this.budgetid = budgetid;
	}

	public void setBudgetidGreaterThanOrEqual(Long budgetidGreaterThanOrEqual) {
		this.budgetidGreaterThanOrEqual = budgetidGreaterThanOrEqual;
	}

	public void setBudgetidLessThanOrEqual(Long budgetidLessThanOrEqual) {
		this.budgetidLessThanOrEqual = budgetidLessThanOrEqual;
	}

	public void setBudgetids(List<Long> budgetids) {
		this.budgetids = budgetids;
	}

	public void setPaymemtsum(Double paymemtsum) {
		this.paymemtsum = paymemtsum;
	}

	public void setPaymemtsumGreaterThanOrEqual(
			Double paymemtsumGreaterThanOrEqual) {
		this.paymemtsumGreaterThanOrEqual = paymemtsumGreaterThanOrEqual;
	}

	public void setPaymemtsumLessThanOrEqual(Double paymemtsumLessThanOrEqual) {
		this.paymemtsumLessThanOrEqual = paymemtsumLessThanOrEqual;
	}

	public void setPaymemtsums(List<Double> paymemtsums) {
		this.paymemtsums = paymemtsums;
	}

	public void setPaymentdate(Date paymentdate) {
		this.paymentdate = paymentdate;
	}

	public void setPaymentdateGreaterThanOrEqual(
			Date paymentdateGreaterThanOrEqual) {
		this.paymentdateGreaterThanOrEqual = paymentdateGreaterThanOrEqual;
	}

	public void setPaymentdateLessThanOrEqual(Date paymentdateLessThanOrEqual) {
		this.paymentdateLessThanOrEqual = paymentdateLessThanOrEqual;
	}

	public void setPaymentdates(List<Date> paymentdates) {
		this.paymentdates = paymentdates;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public void setSequenceGreaterThanOrEqual(Integer sequenceGreaterThanOrEqual) {
		this.sequenceGreaterThanOrEqual = sequenceGreaterThanOrEqual;
	}

	public void setSequenceLessThanOrEqual(Integer sequenceLessThanOrEqual) {
		this.sequenceLessThanOrEqual = sequenceLessThanOrEqual;
	}

	public void setSequences(List<Integer> sequences) {
		this.sequences = sequences;
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

	public PaymentplanQuery budgetid(Long budgetid) {
		if (budgetid == null) {
			throw new RuntimeException("budgetid is null");
		}
		this.budgetid = budgetid;
		return this;
	}

	public PaymentplanQuery budgetidGreaterThanOrEqual(
			Long budgetidGreaterThanOrEqual) {
		if (budgetidGreaterThanOrEqual == null) {
			throw new RuntimeException("budgetid is null");
		}
		this.budgetidGreaterThanOrEqual = budgetidGreaterThanOrEqual;
		return this;
	}

	public PaymentplanQuery budgetidLessThanOrEqual(Long budgetidLessThanOrEqual) {
		if (budgetidLessThanOrEqual == null) {
			throw new RuntimeException("budgetid is null");
		}
		this.budgetidLessThanOrEqual = budgetidLessThanOrEqual;
		return this;
	}

	public PaymentplanQuery budgetids(List<Long> budgetids) {
		if (budgetids == null) {
			throw new RuntimeException("budgetids is empty ");
		}
		this.budgetids = budgetids;
		return this;
	}

	public PaymentplanQuery paymemtsum(Double paymemtsum) {
		if (paymemtsum == null) {
			throw new RuntimeException("paymemtsum is null");
		}
		this.paymemtsum = paymemtsum;
		return this;
	}

	public PaymentplanQuery paymemtsumGreaterThanOrEqual(
			Double paymemtsumGreaterThanOrEqual) {
		if (paymemtsumGreaterThanOrEqual == null) {
			throw new RuntimeException("paymemtsum is null");
		}
		this.paymemtsumGreaterThanOrEqual = paymemtsumGreaterThanOrEqual;
		return this;
	}

	public PaymentplanQuery paymemtsumLessThanOrEqual(
			Double paymemtsumLessThanOrEqual) {
		if (paymemtsumLessThanOrEqual == null) {
			throw new RuntimeException("paymemtsum is null");
		}
		this.paymemtsumLessThanOrEqual = paymemtsumLessThanOrEqual;
		return this;
	}

	public PaymentplanQuery paymemtsums(List<Double> paymemtsums) {
		if (paymemtsums == null) {
			throw new RuntimeException("paymemtsums is empty ");
		}
		this.paymemtsums = paymemtsums;
		return this;
	}

	public PaymentplanQuery paymentdate(Date paymentdate) {
		if (paymentdate == null) {
			throw new RuntimeException("paymentdate is null");
		}
		this.paymentdate = paymentdate;
		return this;
	}

	public PaymentplanQuery paymentdateGreaterThanOrEqual(
			Date paymentdateGreaterThanOrEqual) {
		if (paymentdateGreaterThanOrEqual == null) {
			throw new RuntimeException("paymentdate is null");
		}
		this.paymentdateGreaterThanOrEqual = paymentdateGreaterThanOrEqual;
		return this;
	}

	public PaymentplanQuery paymentdateLessThanOrEqual(
			Date paymentdateLessThanOrEqual) {
		if (paymentdateLessThanOrEqual == null) {
			throw new RuntimeException("paymentdate is null");
		}
		this.paymentdateLessThanOrEqual = paymentdateLessThanOrEqual;
		return this;
	}

	public PaymentplanQuery paymentdates(List<Date> paymentdates) {
		if (paymentdates == null) {
			throw new RuntimeException("paymentdates is empty ");
		}
		this.paymentdates = paymentdates;
		return this;
	}

	public PaymentplanQuery sequence(Integer sequence) {
		if (sequence == null) {
			throw new RuntimeException("sequence is null");
		}
		this.sequence = sequence;
		return this;
	}

	public PaymentplanQuery sequenceGreaterThanOrEqual(
			Integer sequenceGreaterThanOrEqual) {
		if (sequenceGreaterThanOrEqual == null) {
			throw new RuntimeException("sequence is null");
		}
		this.sequenceGreaterThanOrEqual = sequenceGreaterThanOrEqual;
		return this;
	}

	public PaymentplanQuery sequenceLessThanOrEqual(
			Integer sequenceLessThanOrEqual) {
		if (sequenceLessThanOrEqual == null) {
			throw new RuntimeException("sequence is null");
		}
		this.sequenceLessThanOrEqual = sequenceLessThanOrEqual;
		return this;
	}

	public PaymentplanQuery sequences(List<Integer> sequences) {
		if (sequences == null) {
			throw new RuntimeException("sequences is empty ");
		}
		this.sequences = sequences;
		return this;
	}

	public PaymentplanQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public PaymentplanQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public PaymentplanQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public PaymentplanQuery createDate(Date createDate) {
		if (createDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDate = createDate;
		return this;
	}

	public PaymentplanQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public PaymentplanQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public PaymentplanQuery createDates(List<Date> createDates) {
		if (createDates == null) {
			throw new RuntimeException("createDates is empty ");
		}
		this.createDates = createDates;
		return this;
	}

	public PaymentplanQuery updateDate(Date updateDate) {
		if (updateDate == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDate = updateDate;
		return this;
	}

	public PaymentplanQuery updateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public PaymentplanQuery updateDateLessThanOrEqual(
			Date updateDateLessThanOrEqual) {
		if (updateDateLessThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
		return this;
	}

	public PaymentplanQuery updateDates(List<Date> updateDates) {
		if (updateDates == null) {
			throw new RuntimeException("updateDates is empty ");
		}
		this.updateDates = updateDates;
		return this;
	}

	public PaymentplanQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public PaymentplanQuery updateByLike(String updateByLike) {
		if (updateByLike == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateByLike = updateByLike;
		return this;
	}

	public PaymentplanQuery updateBys(List<String> updateBys) {
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

			if ("budgetid".equals(sortColumn)) {
				orderBy = "E.budgetid" + a_x;
			}

			if ("paymemtsum".equals(sortColumn)) {
				orderBy = "E.paymemtsum" + a_x;
			}

			if ("paymentdate".equals(sortColumn)) {
				orderBy = "E.paymentdate" + a_x;
			}

			if ("sequence".equals(sortColumn)) {
				orderBy = "E.sequence" + a_x;
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
		addColumn("planid", "planid");
		addColumn("budgetid", "budgetid");
		addColumn("paymemtsum", "paymemtsum");
		addColumn("paymentdate", "paymentdate");
		addColumn("sequence", "sequence");
		addColumn("createBy", "createBy");
		addColumn("createDate", "createDate");
		addColumn("updateDate", "updateDate");
		addColumn("updateBy", "updateBy");
	}

}