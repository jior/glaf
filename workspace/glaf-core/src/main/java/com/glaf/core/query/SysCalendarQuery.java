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

import java.util.*;
import com.glaf.core.query.DataQuery;

public class SysCalendarQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected Integer day;
	protected Integer dayGreaterThanOrEqual;
	protected Integer dayLessThanOrEqual;
	protected List<Integer> days;
	protected String dutyA;
	protected String dutyALike;
	protected List<String> dutyAs;
	protected String dutyB;
	protected String dutyBLike;
	protected List<String> dutyBs;
	protected String groupA;
	protected String groupALike;
	protected List<String> groupAs;
	protected String groupB;
	protected String groupBLike;
	protected List<String> groupBs;
	protected List<Long> ids;
	protected Integer isFreeDay;
	protected Integer month;
	protected Integer monthGreaterThanOrEqual;
	protected Integer monthLessThanOrEqual;
	protected List<Integer> months;
	protected String productionLine;
	protected String productionLineLike;
	protected List<String> productionLines;
	protected Integer week;
	protected Integer weekGreaterThanOrEqual;
	protected Integer weekLessThanOrEqual;
	protected List<Integer> weeks;
	protected Date workDateGreaterThanOrEqual;
	protected Date workDateLessThanOrEqual;
	protected Integer year;
	protected Integer yearGreaterThanOrEqual;
	protected Integer yearLessThanOrEqual;
	protected List<Integer> years;

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

	public SysCalendarQuery days(List<Integer> days) {
		if (days == null) {
			throw new RuntimeException("days is empty ");
		}
		this.days = days;
		return this;
	}

	public SysCalendarQuery dutyA(String dutyA) {
		if (dutyA == null) {
			throw new RuntimeException("dutyA is null");
		}
		this.dutyA = dutyA;
		return this;
	}

	public SysCalendarQuery dutyALike(String dutyALike) {
		if (dutyALike == null) {
			throw new RuntimeException("dutyA is null");
		}
		this.dutyALike = dutyALike;
		return this;
	}

	public SysCalendarQuery dutyAs(List<String> dutyAs) {
		if (dutyAs == null) {
			throw new RuntimeException("dutyAs is empty ");
		}
		this.dutyAs = dutyAs;
		return this;
	}

	public SysCalendarQuery dutyB(String dutyB) {
		if (dutyB == null) {
			throw new RuntimeException("dutyB is null");
		}
		this.dutyB = dutyB;
		return this;
	}

	public SysCalendarQuery dutyBLike(String dutyBLike) {
		if (dutyBLike == null) {
			throw new RuntimeException("dutyB is null");
		}
		this.dutyBLike = dutyBLike;
		return this;
	}

	public SysCalendarQuery dutyBs(List<String> dutyBs) {
		if (dutyBs == null) {
			throw new RuntimeException("dutyBs is empty ");
		}
		this.dutyBs = dutyBs;
		return this;
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateDate() {
		return createDate;
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

	public List<Integer> getDays() {
		return days;
	}

	public String getDutyA() {
		return dutyA;
	}

	public String getDutyALike() {
		if (dutyALike != null && dutyALike.trim().length() > 0) {
			if (!dutyALike.startsWith("%")) {
				dutyALike = "%" + dutyALike;
			}
			if (!dutyALike.endsWith("%")) {
				dutyALike = dutyALike + "%";
			}
		}
		return dutyALike;
	}

	public List<String> getDutyAs() {
		return dutyAs;
	}

	public String getDutyB() {
		return dutyB;
	}

	public String getDutyBLike() {
		if (dutyBLike != null && dutyBLike.trim().length() > 0) {
			if (!dutyBLike.startsWith("%")) {
				dutyBLike = "%" + dutyBLike;
			}
			if (!dutyBLike.endsWith("%")) {
				dutyBLike = dutyBLike + "%";
			}
		}
		return dutyBLike;
	}

	public List<String> getDutyBs() {
		return dutyBs;
	}

	public String getGroupA() {
		return groupA;
	}

	public String getGroupALike() {
		if (groupALike != null && groupALike.trim().length() > 0) {
			if (!groupALike.startsWith("%")) {
				groupALike = "%" + groupALike;
			}
			if (!groupALike.endsWith("%")) {
				groupALike = groupALike + "%";
			}
		}
		return groupALike;
	}

	public List<String> getGroupAs() {
		return groupAs;
	}

	public String getGroupB() {
		return groupB;
	}

	public String getGroupBLike() {
		if (groupBLike != null && groupBLike.trim().length() > 0) {
			if (!groupBLike.startsWith("%")) {
				groupBLike = "%" + groupBLike;
			}
			if (!groupBLike.endsWith("%")) {
				groupBLike = groupBLike + "%";
			}
		}
		return groupBLike;
	}

	public List<String> getGroupBs() {
		return groupBs;
	}

	public Integer getIsFreeDay() {
		return isFreeDay;
	}

	public Integer getMonth() {
		return month;
	}

	public Integer getMonthGreaterThanOrEqual() {
		return monthGreaterThanOrEqual;
	}

	public Integer getMonthLessThanOrEqual() {
		return monthLessThanOrEqual;
	}

	public List<Integer> getMonths() {
		return months;
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

	public String getProductionLineLike() {
		if (productionLineLike != null
				&& productionLineLike.trim().length() > 0) {
			if (!productionLineLike.startsWith("%")) {
				productionLineLike = "%" + productionLineLike;
			}
			if (!productionLineLike.endsWith("%")) {
				productionLineLike = productionLineLike + "%";
			}
		}
		return productionLineLike;
	}

	public List<String> getProductionLines() {
		return productionLines;
	}

	public Integer getWeek() {
		return week;
	}

	public Integer getWeekGreaterThanOrEqual() {
		return weekGreaterThanOrEqual;
	}

	public Integer getWeekLessThanOrEqual() {
		return weekLessThanOrEqual;
	}

	public List<Integer> getWeeks() {
		return weeks;
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

	public Integer getYearGreaterThanOrEqual() {
		return yearGreaterThanOrEqual;
	}

	public Integer getYearLessThanOrEqual() {
		return yearLessThanOrEqual;
	}

	public List<Integer> getYears() {
		return years;
	}

	public SysCalendarQuery groupA(String groupA) {
		if (groupA == null) {
			throw new RuntimeException("groupA is null");
		}
		this.groupA = groupA;
		return this;
	}

	public SysCalendarQuery groupALike(String groupALike) {
		if (groupALike == null) {
			throw new RuntimeException("groupA is null");
		}
		this.groupALike = groupALike;
		return this;
	}

	public SysCalendarQuery groupAs(List<String> groupAs) {
		if (groupAs == null) {
			throw new RuntimeException("groupAs is empty ");
		}
		this.groupAs = groupAs;
		return this;
	}

	public SysCalendarQuery groupB(String groupB) {
		if (groupB == null) {
			throw new RuntimeException("groupB is null");
		}
		this.groupB = groupB;
		return this;
	}

	public SysCalendarQuery groupBLike(String groupBLike) {
		if (groupBLike == null) {
			throw new RuntimeException("groupB is null");
		}
		this.groupBLike = groupBLike;
		return this;
	}

	public SysCalendarQuery groupBs(List<String> groupBs) {
		if (groupBs == null) {
			throw new RuntimeException("groupBs is empty ");
		}
		this.groupBs = groupBs;
		return this;
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
		addColumn("isWorkDay", "ISWORKDAY_");
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

	public SysCalendarQuery monthGreaterThanOrEqual(
			Integer monthGreaterThanOrEqual) {
		if (monthGreaterThanOrEqual == null) {
			throw new RuntimeException("month is null");
		}
		this.monthGreaterThanOrEqual = monthGreaterThanOrEqual;
		return this;
	}

	public SysCalendarQuery monthLessThanOrEqual(Integer monthLessThanOrEqual) {
		if (monthLessThanOrEqual == null) {
			throw new RuntimeException("month is null");
		}
		this.monthLessThanOrEqual = monthLessThanOrEqual;
		return this;
	}

	public SysCalendarQuery months(List<Integer> months) {
		if (months == null) {
			throw new RuntimeException("months is empty ");
		}
		this.months = months;
		return this;
	}

	public SysCalendarQuery productionLine(String productionLine) {
		if (productionLine == null) {
			throw new RuntimeException("productionLine is null");
		}
		this.productionLine = productionLine;
		return this;
	}

	public SysCalendarQuery productionLineLike(String productionLineLike) {
		if (productionLineLike == null) {
			throw new RuntimeException("productionLine is null");
		}
		this.productionLineLike = productionLineLike;
		return this;
	}

	public SysCalendarQuery productionLines(List<String> productionLines) {
		if (productionLines == null) {
			throw new RuntimeException("productionLines is empty ");
		}
		this.productionLines = productionLines;
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

	public void setDays(List<Integer> days) {
		this.days = days;
	}

	public void setDutyA(String dutyA) {
		this.dutyA = dutyA;
	}

	public void setDutyALike(String dutyALike) {
		this.dutyALike = dutyALike;
	}

	public void setDutyAs(List<String> dutyAs) {
		this.dutyAs = dutyAs;
	}

	public void setDutyB(String dutyB) {
		this.dutyB = dutyB;
	}

	public void setDutyBLike(String dutyBLike) {
		this.dutyBLike = dutyBLike;
	}

	public void setDutyBs(List<String> dutyBs) {
		this.dutyBs = dutyBs;
	}

	public void setGroupA(String groupA) {
		this.groupA = groupA;
	}

	public void setGroupALike(String groupALike) {
		this.groupALike = groupALike;
	}

	public void setGroupAs(List<String> groupAs) {
		this.groupAs = groupAs;
	}

	public void setGroupB(String groupB) {
		this.groupB = groupB;
	}

	public void setGroupBLike(String groupBLike) {
		this.groupBLike = groupBLike;
	}

	public void setGroupBs(List<String> groupBs) {
		this.groupBs = groupBs;
	}

	public void setIsFreeDay(Integer isFreeDay) {
		this.isFreeDay = isFreeDay;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public void setMonthGreaterThanOrEqual(Integer monthGreaterThanOrEqual) {
		this.monthGreaterThanOrEqual = monthGreaterThanOrEqual;
	}

	public void setMonthLessThanOrEqual(Integer monthLessThanOrEqual) {
		this.monthLessThanOrEqual = monthLessThanOrEqual;
	}

	public void setMonths(List<Integer> months) {
		this.months = months;
	}

	public void setProductionLine(String productionLine) {
		this.productionLine = productionLine;
	}

	public void setProductionLineLike(String productionLineLike) {
		this.productionLineLike = productionLineLike;
	}

	public void setProductionLines(List<String> productionLines) {
		this.productionLines = productionLines;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	public void setWeekGreaterThanOrEqual(Integer weekGreaterThanOrEqual) {
		this.weekGreaterThanOrEqual = weekGreaterThanOrEqual;
	}

	public void setWeekLessThanOrEqual(Integer weekLessThanOrEqual) {
		this.weekLessThanOrEqual = weekLessThanOrEqual;
	}

	public void setWeeks(List<Integer> weeks) {
		this.weeks = weeks;
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

	public void setYearGreaterThanOrEqual(Integer yearGreaterThanOrEqual) {
		this.yearGreaterThanOrEqual = yearGreaterThanOrEqual;
	}

	public void setYearLessThanOrEqual(Integer yearLessThanOrEqual) {
		this.yearLessThanOrEqual = yearLessThanOrEqual;
	}

	public void setYears(List<Integer> years) {
		this.years = years;
	}

	public SysCalendarQuery week(Integer week) {
		if (week == null) {
			throw new RuntimeException("week is null");
		}
		this.week = week;
		return this;
	}

	public SysCalendarQuery weekGreaterThanOrEqual(
			Integer weekGreaterThanOrEqual) {
		if (weekGreaterThanOrEqual == null) {
			throw new RuntimeException("week is null");
		}
		this.weekGreaterThanOrEqual = weekGreaterThanOrEqual;
		return this;
	}

	public SysCalendarQuery weekLessThanOrEqual(Integer weekLessThanOrEqual) {
		if (weekLessThanOrEqual == null) {
			throw new RuntimeException("week is null");
		}
		this.weekLessThanOrEqual = weekLessThanOrEqual;
		return this;
	}

	public SysCalendarQuery weeks(List<Integer> weeks) {
		if (weeks == null) {
			throw new RuntimeException("weeks is empty ");
		}
		this.weeks = weeks;
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

	public SysCalendarQuery yearGreaterThanOrEqual(
			Integer yearGreaterThanOrEqual) {
		if (yearGreaterThanOrEqual == null) {
			throw new RuntimeException("year is null");
		}
		this.yearGreaterThanOrEqual = yearGreaterThanOrEqual;
		return this;
	}

	public SysCalendarQuery yearLessThanOrEqual(Integer yearLessThanOrEqual) {
		if (yearLessThanOrEqual == null) {
			throw new RuntimeException("year is null");
		}
		this.yearLessThanOrEqual = yearLessThanOrEqual;
		return this;
	}

	public SysCalendarQuery years(List<Integer> years) {
		if (years == null) {
			throw new RuntimeException("years is empty ");
		}
		this.years = years;
		return this;
	}

}