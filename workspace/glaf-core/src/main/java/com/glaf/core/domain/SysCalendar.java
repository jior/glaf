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

package com.glaf.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.core.domain.util.SysCalendarJsonFactory;

@Entity
@Table(name = "SYS_CALENDAR")
public class SysCalendar implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	@Column(name = "DAY_")
	protected Integer day;

	@Column(name = "DUTYA_", length = 50)
	protected String dutyA;

	@Column(name = "DUTYB_", length = 50)
	protected String dutyB;

	@Column(name = "GROUPA_", length = 50)
	protected String groupA;

	@Column(name = "GROUPB_", length = 50)
	protected String groupB;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Long id;

	@Column(name = "ISFREEDAY_")
	protected Integer isFreeDay;

	@Column(name = "MONTH_")
	protected Integer month;

	@Column(name = "PRODUCTIONLINE_", length = 50)
	protected String productionLine;

	@Column(name = "WEEK_")
	protected Integer week;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "WORKDATE_")
	protected Date workDate;

	@Column(name = "YEAR_")
	protected Integer year;

	public SysCalendar() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysCalendar other = (SysCalendar) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public Integer getDay() {
		return this.day;
	}

	public String getDutyA() {
		return this.dutyA;
	}

	public String getDutyB() {
		return this.dutyB;
	}

	public String getGroupA() {
		return this.groupA;
	}

	public String getGroupB() {
		return this.groupB;
	}

	public Long getId() {
		return this.id;
	}

	public Integer getIsFreeDay() {
		return isFreeDay;
	}

	public Integer getMonth() {
		return this.month;
	}

	public String getProductionLine() {
		return this.productionLine;
	}

	public Integer getWeek() {
		return this.week;
	}

	public Date getWorkDate() {
		return this.workDate;
	}

	public Integer getYear() {
		return this.year;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public SysCalendar jsonToObject(JSONObject jsonObject) {
		return SysCalendarJsonFactory.jsonToObject(jsonObject);
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public void setDutyA(String dutyA) {
		this.dutyA = dutyA;
	}

	public void setDutyB(String dutyB) {
		this.dutyB = dutyB;
	}

	public void setGroupA(String groupA) {
		this.groupA = groupA;
	}

	public void setGroupB(String groupB) {
		this.groupB = groupB;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public JSONObject toJsonObject() {
		return SysCalendarJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SysCalendarJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
