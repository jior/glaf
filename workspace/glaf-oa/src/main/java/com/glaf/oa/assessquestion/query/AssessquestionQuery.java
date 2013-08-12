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
package com.glaf.oa.assessquestion.query;

import java.util.Date;
import java.util.List;

import com.glaf.core.query.DataQuery;

public class AssessquestionQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> qustionids;
	protected String title;
	protected String titleLike;
	protected List<String> titles;
	protected Date validdate;
	protected Date validdateGreaterThanOrEqual;
	protected Date validdateLessThanOrEqual;
	protected List<Date> validdates;
	protected Integer rate;
	protected Integer rateGreaterThanOrEqual;
	protected Integer rateLessThanOrEqual;
	protected List<Integer> rates;
	protected Integer iseffective;
	protected Integer iseffectiveGreaterThanOrEqual;
	protected Integer iseffectiveLessThanOrEqual;
	protected List<Integer> iseffectives;
	protected Double targetsum;
	protected Double targetsumGreaterThanOrEqual;
	protected Double targetsumLessThanOrEqual;
	protected List<Double> targetsums;
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

	public AssessquestionQuery() {

	}

	public String getTitle() {
		return title;
	}

	public String getTitleLike() {
		if (titleLike != null && titleLike.trim().length() > 0) {
			if (!titleLike.startsWith("%")) {
				titleLike = "%" + titleLike;
			}
			if (!titleLike.endsWith("%")) {
				titleLike = titleLike + "%";
			}
		}
		return titleLike;
	}

	public List<String> getTitles() {
		return titles;
	}

	public Date getValiddate() {
		return validdate;
	}

	public Date getValiddateGreaterThanOrEqual() {
		return validdateGreaterThanOrEqual;
	}

	public Date getValiddateLessThanOrEqual() {
		return validdateLessThanOrEqual;
	}

	public List<Date> getValiddates() {
		return validdates;
	}

	public Integer getRate() {
		return rate;
	}

	public Integer getRateGreaterThanOrEqual() {
		return rateGreaterThanOrEqual;
	}

	public Integer getRateLessThanOrEqual() {
		return rateLessThanOrEqual;
	}

	public List<Integer> getRates() {
		return rates;
	}

	public Integer getIseffective() {
		return iseffective;
	}

	public Integer getIseffectiveGreaterThanOrEqual() {
		return iseffectiveGreaterThanOrEqual;
	}

	public Integer getIseffectiveLessThanOrEqual() {
		return iseffectiveLessThanOrEqual;
	}

	public List<Integer> getIseffectives() {
		return iseffectives;
	}

	public Double getTargetsum() {
		return targetsum;
	}

	public Double getTargetsumGreaterThanOrEqual() {
		return targetsumGreaterThanOrEqual;
	}

	public Double getTargetsumLessThanOrEqual() {
		return targetsumLessThanOrEqual;
	}

	public List<Double> getTargetsums() {
		return targetsums;
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

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public void setTitles(List<String> titles) {
		this.titles = titles;
	}

	public void setValiddate(Date validdate) {
		this.validdate = validdate;
	}

	public void setValiddateGreaterThanOrEqual(Date validdateGreaterThanOrEqual) {
		this.validdateGreaterThanOrEqual = validdateGreaterThanOrEqual;
	}

	public void setValiddateLessThanOrEqual(Date validdateLessThanOrEqual) {
		this.validdateLessThanOrEqual = validdateLessThanOrEqual;
	}

	public void setValiddates(List<Date> validdates) {
		this.validdates = validdates;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public void setRateGreaterThanOrEqual(Integer rateGreaterThanOrEqual) {
		this.rateGreaterThanOrEqual = rateGreaterThanOrEqual;
	}

	public void setRateLessThanOrEqual(Integer rateLessThanOrEqual) {
		this.rateLessThanOrEqual = rateLessThanOrEqual;
	}

	public void setRates(List<Integer> rates) {
		this.rates = rates;
	}

	public void setIseffective(Integer iseffective) {
		this.iseffective = iseffective;
	}

	public void setIseffectiveGreaterThanOrEqual(
			Integer iseffectiveGreaterThanOrEqual) {
		this.iseffectiveGreaterThanOrEqual = iseffectiveGreaterThanOrEqual;
	}

	public void setIseffectiveLessThanOrEqual(Integer iseffectiveLessThanOrEqual) {
		this.iseffectiveLessThanOrEqual = iseffectiveLessThanOrEqual;
	}

	public void setIseffectives(List<Integer> iseffectives) {
		this.iseffectives = iseffectives;
	}

	public void setTargetsum(Double targetsum) {
		this.targetsum = targetsum;
	}

	public void setTargetsumGreaterThanOrEqual(
			Double targetsumGreaterThanOrEqual) {
		this.targetsumGreaterThanOrEqual = targetsumGreaterThanOrEqual;
	}

	public void setTargetsumLessThanOrEqual(Double targetsumLessThanOrEqual) {
		this.targetsumLessThanOrEqual = targetsumLessThanOrEqual;
	}

	public void setTargetsums(List<Double> targetsums) {
		this.targetsums = targetsums;
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

	public AssessquestionQuery title(String title) {
		if (title == null) {
			throw new RuntimeException("title is null");
		}
		this.title = title;
		return this;
	}

	public AssessquestionQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

	public AssessquestionQuery titles(List<String> titles) {
		if (titles == null) {
			throw new RuntimeException("titles is empty ");
		}
		this.titles = titles;
		return this;
	}

	public AssessquestionQuery validdate(Date validdate) {
		if (validdate == null) {
			throw new RuntimeException("validdate is null");
		}
		this.validdate = validdate;
		return this;
	}

	public AssessquestionQuery validdateGreaterThanOrEqual(
			Date validdateGreaterThanOrEqual) {
		if (validdateGreaterThanOrEqual == null) {
			throw new RuntimeException("validdate is null");
		}
		this.validdateGreaterThanOrEqual = validdateGreaterThanOrEqual;
		return this;
	}

	public AssessquestionQuery validdateLessThanOrEqual(
			Date validdateLessThanOrEqual) {
		if (validdateLessThanOrEqual == null) {
			throw new RuntimeException("validdate is null");
		}
		this.validdateLessThanOrEqual = validdateLessThanOrEqual;
		return this;
	}

	public AssessquestionQuery validdates(List<Date> validdates) {
		if (validdates == null) {
			throw new RuntimeException("validdates is empty ");
		}
		this.validdates = validdates;
		return this;
	}

	public AssessquestionQuery rate(Integer rate) {
		if (rate == null) {
			throw new RuntimeException("rate is null");
		}
		this.rate = rate;
		return this;
	}

	public AssessquestionQuery rateGreaterThanOrEqual(
			Integer rateGreaterThanOrEqual) {
		if (rateGreaterThanOrEqual == null) {
			throw new RuntimeException("rate is null");
		}
		this.rateGreaterThanOrEqual = rateGreaterThanOrEqual;
		return this;
	}

	public AssessquestionQuery rateLessThanOrEqual(Integer rateLessThanOrEqual) {
		if (rateLessThanOrEqual == null) {
			throw new RuntimeException("rate is null");
		}
		this.rateLessThanOrEqual = rateLessThanOrEqual;
		return this;
	}

	public AssessquestionQuery rates(List<Integer> rates) {
		if (rates == null) {
			throw new RuntimeException("rates is empty ");
		}
		this.rates = rates;
		return this;
	}

	public AssessquestionQuery iseffective(Integer iseffective) {
		if (iseffective == null) {
			throw new RuntimeException("iseffective is null");
		}
		this.iseffective = iseffective;
		return this;
	}

	public AssessquestionQuery iseffectiveGreaterThanOrEqual(
			Integer iseffectiveGreaterThanOrEqual) {
		if (iseffectiveGreaterThanOrEqual == null) {
			throw new RuntimeException("iseffective is null");
		}
		this.iseffectiveGreaterThanOrEqual = iseffectiveGreaterThanOrEqual;
		return this;
	}

	public AssessquestionQuery iseffectiveLessThanOrEqual(
			Integer iseffectiveLessThanOrEqual) {
		if (iseffectiveLessThanOrEqual == null) {
			throw new RuntimeException("iseffective is null");
		}
		this.iseffectiveLessThanOrEqual = iseffectiveLessThanOrEqual;
		return this;
	}

	public AssessquestionQuery iseffectives(List<Integer> iseffectives) {
		if (iseffectives == null) {
			throw new RuntimeException("iseffectives is empty ");
		}
		this.iseffectives = iseffectives;
		return this;
	}

	public AssessquestionQuery targetsum(Double targetsum) {
		if (targetsum == null) {
			throw new RuntimeException("targetsum is null");
		}
		this.targetsum = targetsum;
		return this;
	}

	public AssessquestionQuery targetsumGreaterThanOrEqual(
			Double targetsumGreaterThanOrEqual) {
		if (targetsumGreaterThanOrEqual == null) {
			throw new RuntimeException("targetsum is null");
		}
		this.targetsumGreaterThanOrEqual = targetsumGreaterThanOrEqual;
		return this;
	}

	public AssessquestionQuery targetsumLessThanOrEqual(
			Double targetsumLessThanOrEqual) {
		if (targetsumLessThanOrEqual == null) {
			throw new RuntimeException("targetsum is null");
		}
		this.targetsumLessThanOrEqual = targetsumLessThanOrEqual;
		return this;
	}

	public AssessquestionQuery targetsums(List<Double> targetsums) {
		if (targetsums == null) {
			throw new RuntimeException("targetsums is empty ");
		}
		this.targetsums = targetsums;
		return this;
	}

	public AssessquestionQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public AssessquestionQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public AssessquestionQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public AssessquestionQuery createDate(Date createDate) {
		if (createDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDate = createDate;
		return this;
	}

	public AssessquestionQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public AssessquestionQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public AssessquestionQuery createDates(List<Date> createDates) {
		if (createDates == null) {
			throw new RuntimeException("createDates is empty ");
		}
		this.createDates = createDates;
		return this;
	}

	public AssessquestionQuery updateDate(Date updateDate) {
		if (updateDate == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDate = updateDate;
		return this;
	}

	public AssessquestionQuery updateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public AssessquestionQuery updateDateLessThanOrEqual(
			Date updateDateLessThanOrEqual) {
		if (updateDateLessThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
		return this;
	}

	public AssessquestionQuery updateDates(List<Date> updateDates) {
		if (updateDates == null) {
			throw new RuntimeException("updateDates is empty ");
		}
		this.updateDates = updateDates;
		return this;
	}

	public AssessquestionQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public AssessquestionQuery updateByLike(String updateByLike) {
		if (updateByLike == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateByLike = updateByLike;
		return this;
	}

	public AssessquestionQuery updateBys(List<String> updateBys) {
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

			if ("title".equals(sortColumn)) {
				orderBy = "E.title" + a_x;
			}

			if ("validdate".equals(sortColumn)) {
				orderBy = "E.validdate" + a_x;
			}

			if ("rate".equals(sortColumn)) {
				orderBy = "E.rate" + a_x;
			}

			if ("iseffective".equals(sortColumn)) {
				orderBy = "E.iseffective" + a_x;
			}

			if ("targetsum".equals(sortColumn)) {
				orderBy = "E.targetsum" + a_x;
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
		addColumn("qustionid", "qustionid");
		addColumn("title", "title");
		addColumn("validdate", "validdate");
		addColumn("rate", "rate");
		addColumn("iseffective", "iseffective");
		addColumn("targetsum", "targetsum");
		addColumn("createBy", "createBy");
		addColumn("createDate", "createDate");
		addColumn("updateDate", "updateDate");
		addColumn("updateBy", "updateBy");
	}

}