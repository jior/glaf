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
package com.glaf.core.query;

import java.util.Date;

public class SysCalendarQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected Integer day;
	protected Integer dayGreaterThanOrEqual;
	protected Integer dayLessThanOrEqual;

	protected Integer isFreeDay;
	protected Integer month;

	protected String productionLine;

	protected Integer week;

	protected Date workDateGreaterThanOrEqual;
	protected Date workDateLessThanOrEqual;
	protected Integer year;

	public SysCalendarQuery() {

	}

	public SysCalendarQuery day(Integer day) {
		if (day == null) {
			throw new RuntimeException("day is null");
		}
		this.day = day;
		return this;
	}

	public SysCalendarQuery dayGreaterThanOrEqual(Integer dayGreaterThanOrEqual) {
		if (dayGreaterThanOrEqual == null) {
			throw new RuntimeException("day is null");
		}
		this.dayGreaterThanOrEqual = dayGreaterThanOrEqual;
		return this;
	}

	public SysCalendarQuery dayLessThanOrEqual(Integer dayLessThanOrEqual) {
		if (dayLessThanOrEqual == null) {
			throw new RuntimeException("day is null");
		}
		this.dayLessThanOrEqual = dayLessThanOrEqual;
		return this;
	}

	public Integer getDay() {
		return day;
	}

	public Integer getDayGreaterThanOrEqual() {
		return dayGreaterThanOrEqual;
	}

	public Integer getDayLessThanOrEqual() {
		return dayLessThanOrEqual;
	}

	public Integer getIsFreeDay() {
		return isFreeDay;
	}

	public Integer getMonth() {
		return month;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.CREATEBY_" + a_x;
			}

			if ("createDate".equals(sortColumn)) {
				orderBy = "E.CREATEDATE_" + a_x;
			}

			if ("day".equals(sortColumn)) {
				orderBy = "E.DAY_" + a_x;
			}

			if ("week".equals(sortColumn)) {
				orderBy = "E.WEEK_" + a_x;
			}

			if ("month".equals(sortColumn)) {
				orderBy = "E.MONTH_" + a_x;
			}

			if ("year".equals(sortColumn)) {
				orderBy = "E.YEAR_" + a_x;
			}

			if ("dutyA".equals(sortColumn)) {
				orderBy = "E.DUTYA_" + a_x;
			}

			if ("dutyB".equals(sortColumn)) {
				orderBy = "E.DUTYB_" + a_x;
			}

			if ("groupA".equals(sortColumn)) {
				orderBy = "E.GROUPA_" + a_x;
			}

			if ("groupB".equals(sortColumn)) {
				orderBy = "E.GROUPB_" + a_x;
			}

			if ("productionLine".equals(sortColumn)) {
				orderBy = "E.PRODUCTIONLINE_" + a_x;
			}

			if ("isWorkDay".equals(sortColumn)) {
				orderBy = "E.ISWORKDAY_" + a_x;
			}

			if ("workDate".equals(sortColumn)) {
				orderBy = "E.WORKDATE_" + a_x;
			}

		}
		return orderBy;
	}

	public String getProductionLine() {
		return productionLine;
	}

	public Integer getWeek() {
		return week;
	}

	public Date getWorkDateGreaterThanOrEqual() {
		return workDateGreaterThanOrEqual;
	}

	public Date getWorkDateLessThanOrEqual() {
		return workDateLessThanOrEqual;
	}

	public Integer getYear() {
		return year;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("createBy", "CREATEBY_");
		addColumn("createDate", "CREATEDATE_");
		addColumn("day", "DAY_");
		addColumn("week", "WEEK_");
		addColumn("month", "MONTH_");
		addColumn("year", "YEAR_");
		addColumn("dutyA", "DUTYA_");
		addColumn("dutyB", "DUTYB_");
		addColumn("groupA", "GROUPA_");
		addColumn("groupB", "GROUPB_");
		addColumn("productionLine", "PRODUCTIONLINE_");
		addColumn("isFreeDay", "ISFREEDAY_");
		addColumn("workDate", "WORKDATE_");
	}

	public SysCalendarQuery isFreeDay(Integer isFreeDay) {
		if (isFreeDay == null) {
			throw new RuntimeException("isFreeDay is null");
		}
		this.isFreeDay = isFreeDay;
		return this;
	}

	public SysCalendarQuery month(Integer month) {
		if (month == null) {
			throw new RuntimeException("month is null");
		}
		this.month = month;
		return this;
	}

	public SysCalendarQuery productionLine(String productionLine) {
		if (productionLine == null) {
			throw new RuntimeException("productionLine is null");
		}
		this.productionLine = productionLine;
		return this;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public void setDayGreaterThanOrEqual(Integer dayGreaterThanOrEqual) {
		this.dayGreaterThanOrEqual = dayGreaterThanOrEqual;
	}

	public void setDayLessThanOrEqual(Integer dayLessThanOrEqual) {
		this.dayLessThanOrEqual = dayLessThanOrEqual;
	}

	public void setIsFreeDay(Integer isFreeDay) {
		this.isFreeDay = isFreeDay;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public void setProductionLine(String productionLine) {
		this.productionLine = productionLine;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	public void setWorkDateGreaterThanOrEqual(Date workDateGreaterThanOrEqual) {
		this.workDateGreaterThanOrEqual = workDateGreaterThanOrEqual;
	}

	public void setWorkDateLessThanOrEqual(Date workDateLessThanOrEqual) {
		this.workDateLessThanOrEqual = workDateLessThanOrEqual;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public SysCalendarQuery week(Integer week) {
		if (week == null) {
			throw new RuntimeException("week is null");
		}
		this.week = week;
		return this;
	}

	public SysCalendarQuery workDateGreaterThanOrEqual(
			Date workDateGreaterThanOrEqual) {
		if (workDateGreaterThanOrEqual == null) {
			throw new RuntimeException("workDate is null");
		}
		this.workDateGreaterThanOrEqual = workDateGreaterThanOrEqual;
		return this;
	}

	public SysCalendarQuery workDateLessThanOrEqual(Date workDateLessThanOrEqual) {
		if (workDateLessThanOrEqual == null) {
			throw new RuntimeException("workDate is null");
		}
		this.workDateLessThanOrEqual = workDateLessThanOrEqual;
		return this;
	}

	public SysCalendarQuery year(Integer year) {
		if (year == null) {
			throw new RuntimeException("year is null");
		}
		this.year = year;
		return this;
	}

}