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

package com.glaf.base.modules.sys.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class WorkCalendarQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected Integer freeDay;
	protected Integer freeDayGreaterThanOrEqual;
	protected Integer freeDayLessThanOrEqual;
	protected List<Integer> freeDays;
	protected Integer freeMonth;
	protected Integer freeMonthGreaterThanOrEqual;
	protected Integer freeMonthLessThanOrEqual;
	protected List<Integer> freeMonths;
	protected Integer freeYear;
	protected Integer freeYearGreaterThanOrEqual;
	protected Integer freeYearLessThanOrEqual;
	protected List<Integer> freeYears;

	public WorkCalendarQuery() {

	}

	public Integer getFreeDay() {
		return freeDay;
	}

	public Integer getFreeDayGreaterThanOrEqual() {
		return freeDayGreaterThanOrEqual;
	}

	public Integer getFreeDayLessThanOrEqual() {
		return freeDayLessThanOrEqual;
	}

	public List<Integer> getFreeDays() {
		return freeDays;
	}

	public Integer getFreeMonth() {
		return freeMonth;
	}

	public Integer getFreeMonthGreaterThanOrEqual() {
		return freeMonthGreaterThanOrEqual;
	}

	public Integer getFreeMonthLessThanOrEqual() {
		return freeMonthLessThanOrEqual;
	}

	public List<Integer> getFreeMonths() {
		return freeMonths;
	}

	public Integer getFreeYear() {
		return freeYear;
	}

	public Integer getFreeYearGreaterThanOrEqual() {
		return freeYearGreaterThanOrEqual;
	}

	public Integer getFreeYearLessThanOrEqual() {
		return freeYearLessThanOrEqual;
	}

	public List<Integer> getFreeYears() {
		return freeYears;
	}

	public void setFreeDay(Integer freeDay) {
		this.freeDay = freeDay;
	}

	public void setFreeDayGreaterThanOrEqual(Integer freeDayGreaterThanOrEqual) {
		this.freeDayGreaterThanOrEqual = freeDayGreaterThanOrEqual;
	}

	public void setFreeDayLessThanOrEqual(Integer freeDayLessThanOrEqual) {
		this.freeDayLessThanOrEqual = freeDayLessThanOrEqual;
	}

	public void setFreeDays(List<Integer> freeDays) {
		this.freeDays = freeDays;
	}

	public void setFreeMonth(Integer freeMonth) {
		this.freeMonth = freeMonth;
	}

	public void setFreeMonthGreaterThanOrEqual(
			Integer freeMonthGreaterThanOrEqual) {
		this.freeMonthGreaterThanOrEqual = freeMonthGreaterThanOrEqual;
	}

	public void setFreeMonthLessThanOrEqual(Integer freeMonthLessThanOrEqual) {
		this.freeMonthLessThanOrEqual = freeMonthLessThanOrEqual;
	}

	public void setFreeMonths(List<Integer> freeMonths) {
		this.freeMonths = freeMonths;
	}

	public void setFreeYear(Integer freeYear) {
		this.freeYear = freeYear;
	}

	public void setFreeYearGreaterThanOrEqual(Integer freeYearGreaterThanOrEqual) {
		this.freeYearGreaterThanOrEqual = freeYearGreaterThanOrEqual;
	}

	public void setFreeYearLessThanOrEqual(Integer freeYearLessThanOrEqual) {
		this.freeYearLessThanOrEqual = freeYearLessThanOrEqual;
	}

	public void setFreeYears(List<Integer> freeYears) {
		this.freeYears = freeYears;
	}

	public WorkCalendarQuery freeDay(Integer freeDay) {
		if (freeDay == null) {
			throw new RuntimeException("freeDay is null");
		}
		this.freeDay = freeDay;
		return this;
	}

	public WorkCalendarQuery freeDayGreaterThanOrEqual(
			Integer freeDayGreaterThanOrEqual) {
		if (freeDayGreaterThanOrEqual == null) {
			throw new RuntimeException("freeDay is null");
		}
		this.freeDayGreaterThanOrEqual = freeDayGreaterThanOrEqual;
		return this;
	}

	public WorkCalendarQuery freeDayLessThanOrEqual(
			Integer freeDayLessThanOrEqual) {
		if (freeDayLessThanOrEqual == null) {
			throw new RuntimeException("freeDay is null");
		}
		this.freeDayLessThanOrEqual = freeDayLessThanOrEqual;
		return this;
	}

	public WorkCalendarQuery freeDays(List<Integer> freeDays) {
		if (freeDays == null) {
			throw new RuntimeException("freeDays is empty ");
		}
		this.freeDays = freeDays;
		return this;
	}

	public WorkCalendarQuery freeMonth(Integer freeMonth) {
		if (freeMonth == null) {
			throw new RuntimeException("freeMonth is null");
		}
		this.freeMonth = freeMonth;
		return this;
	}

	public WorkCalendarQuery freeMonthGreaterThanOrEqual(
			Integer freeMonthGreaterThanOrEqual) {
		if (freeMonthGreaterThanOrEqual == null) {
			throw new RuntimeException("freeMonth is null");
		}
		this.freeMonthGreaterThanOrEqual = freeMonthGreaterThanOrEqual;
		return this;
	}

	public WorkCalendarQuery freeMonthLessThanOrEqual(
			Integer freeMonthLessThanOrEqual) {
		if (freeMonthLessThanOrEqual == null) {
			throw new RuntimeException("freeMonth is null");
		}
		this.freeMonthLessThanOrEqual = freeMonthLessThanOrEqual;
		return this;
	}

	public WorkCalendarQuery freeMonths(List<Integer> freeMonths) {
		if (freeMonths == null) {
			throw new RuntimeException("freeMonths is empty ");
		}
		this.freeMonths = freeMonths;
		return this;
	}

	public WorkCalendarQuery freeYear(Integer freeYear) {
		if (freeYear == null) {
			throw new RuntimeException("freeYear is null");
		}
		this.freeYear = freeYear;
		return this;
	}

	public WorkCalendarQuery freeYearGreaterThanOrEqual(
			Integer freeYearGreaterThanOrEqual) {
		if (freeYearGreaterThanOrEqual == null) {
			throw new RuntimeException("freeYear is null");
		}
		this.freeYearGreaterThanOrEqual = freeYearGreaterThanOrEqual;
		return this;
	}

	public WorkCalendarQuery freeYearLessThanOrEqual(
			Integer freeYearLessThanOrEqual) {
		if (freeYearLessThanOrEqual == null) {
			throw new RuntimeException("freeYear is null");
		}
		this.freeYearLessThanOrEqual = freeYearLessThanOrEqual;
		return this;
	}

	public WorkCalendarQuery freeYears(List<Integer> freeYears) {
		if (freeYears == null) {
			throw new RuntimeException("freeYears is empty ");
		}
		this.freeYears = freeYears;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("freeDay".equals(sortColumn)) {
				orderBy = "E.FREEDAY" + a_x;
			}

			if ("freeMonth".equals(sortColumn)) {
				orderBy = "E.FREEMONTH" + a_x;
			}

			if ("freeYear".equals(sortColumn)) {
				orderBy = "E.FREEYEAR" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("freeDay", "FREEDAY");
		addColumn("freeMonth", "FREEMONTH");
		addColumn("freeYear", "FREEYEAR");
	}

}