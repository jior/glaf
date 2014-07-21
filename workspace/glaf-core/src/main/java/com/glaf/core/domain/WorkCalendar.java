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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.domain.util.WorkCalendarJsonFactory;
import com.glaf.core.base.JSONable;

@Entity
@Table(name = "SYS_WORKCALENDAR")
public class WorkCalendar implements Serializable, JSONable {
	private static final long serialVersionUID = -5396045849722935648L;
	@Id
	@Column(name = "ID", nullable = false)
	protected long id;

	/**
	 * 休息日
	 */
	@Column(name = "FREEDAY")
	protected int freeDay;

	/**
	 * 月
	 */
	@Column(name = "FREEMONTH")
	protected int freeMonth;

	/**
	 * 年
	 */
	@Column(name = "FREEYEAR")
	protected int freeYear;

	public WorkCalendar() {

	}

	public int getFreeDay() {
		return freeDay;
	}

	public int getFreeMonth() {
		return freeMonth;
	}

	public int getFreeYear() {
		return freeYear;
	}

	public long getId() {
		return id;
	}

	public WorkCalendar jsonToObject(JSONObject jsonObject) {
		return WorkCalendarJsonFactory.jsonToObject(jsonObject);
	}

	public void setFreeDay(int freeDay) {
		this.freeDay = freeDay;
	}

	public void setFreeMonth(int freeMonth) {
		this.freeMonth = freeMonth;
	}

	public void setFreeYear(int freeYear) {
		this.freeYear = freeYear;
	}

	public void setId(long id) {
		this.id = id;
	}

	public JSONObject toJsonObject() {
		return WorkCalendarJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return WorkCalendarJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return toJsonObject().toJSONString();
	}

}