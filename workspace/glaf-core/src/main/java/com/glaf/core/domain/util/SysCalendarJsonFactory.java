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

package com.glaf.core.domain.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.domain.SysCalendar;
import com.glaf.core.util.DateUtils;

public class SysCalendarJsonFactory {

	public static java.util.List<SysCalendar> arrayToList(JSONArray array) {
		java.util.List<SysCalendar> list = new java.util.ArrayList<SysCalendar>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SysCalendar model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static SysCalendar jsonToObject(JSONObject jsonObject) {
		SysCalendar model = new SysCalendar();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("day")) {
			model.setDay(jsonObject.getInteger("day"));
		}
		if (jsonObject.containsKey("week")) {
			model.setWeek(jsonObject.getInteger("week"));
		}
		if (jsonObject.containsKey("month")) {
			model.setMonth(jsonObject.getInteger("month"));
		}
		if (jsonObject.containsKey("year")) {
			model.setYear(jsonObject.getInteger("year"));
		}
		if (jsonObject.containsKey("dutyA")) {
			model.setDutyA(jsonObject.getString("dutyA"));
		}
		if (jsonObject.containsKey("dutyB")) {
			model.setDutyB(jsonObject.getString("dutyB"));
		}
		if (jsonObject.containsKey("groupA")) {
			model.setGroupA(jsonObject.getString("groupA"));
		}
		if (jsonObject.containsKey("groupB")) {
			model.setGroupB(jsonObject.getString("groupB"));
		}
		if (jsonObject.containsKey("productionLine")) {
			model.setProductionLine(jsonObject.getString("productionLine"));
		}
		if (jsonObject.containsKey("isFreeDay")) {
			model.setIsFreeDay(jsonObject.getInteger("isFreeDay"));
		}
		if (jsonObject.containsKey("workDate")) {
			model.setWorkDate(jsonObject.getDate("workDate"));
		}
		 

		return model;
	}

	public static JSONArray listToArray(java.util.List<SysCalendar> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SysCalendar model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(SysCalendar model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		jsonObject.put("day", model.getDay());
		jsonObject.put("week", model.getWeek());
		jsonObject.put("month", model.getMonth());
		jsonObject.put("year", model.getYear());
		if (model.getDutyA() != null) {
			jsonObject.put("dutyA", model.getDutyA());
		}
		if (model.getDutyB() != null) {
			jsonObject.put("dutyB", model.getDutyB());
		}
		if (model.getGroupA() != null) {
			jsonObject.put("groupA", model.getGroupA());
		}
		if (model.getGroupB() != null) {
			jsonObject.put("groupB", model.getGroupB());
		}
		if (model.getProductionLine() != null) {
			jsonObject.put("productionLine", model.getProductionLine());
		}
		jsonObject.put("isFreeDay", model.getIsFreeDay());
		if (model.getWorkDate() != null) {
			jsonObject.put("workDate", DateUtils.getDate(model.getWorkDate()));
			jsonObject.put("workDate_date",
					DateUtils.getDate(model.getWorkDate()));
			jsonObject.put("workDate_datetime",
					DateUtils.getDateTime(model.getWorkDate()));
		}
		 
		return jsonObject;
	}

	public static ObjectNode toObjectNode(SysCalendar model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		jsonObject.put("day", model.getDay());
		jsonObject.put("week", model.getWeek());
		jsonObject.put("month", model.getMonth());
		jsonObject.put("year", model.getYear());
		if (model.getDutyA() != null) {
			jsonObject.put("dutyA", model.getDutyA());
		}
		if (model.getDutyB() != null) {
			jsonObject.put("dutyB", model.getDutyB());
		}
		if (model.getGroupA() != null) {
			jsonObject.put("groupA", model.getGroupA());
		}
		if (model.getGroupB() != null) {
			jsonObject.put("groupB", model.getGroupB());
		}
		if (model.getProductionLine() != null) {
			jsonObject.put("productionLine", model.getProductionLine());
		}
		jsonObject.put("isFreeDay", model.getIsFreeDay());
		if (model.getWorkDate() != null) {
			jsonObject.put("workDate", DateUtils.getDate(model.getWorkDate()));
			jsonObject.put("workDate_date",
					DateUtils.getDate(model.getWorkDate()));
			jsonObject.put("workDate_datetime",
					DateUtils.getDateTime(model.getWorkDate()));
		}
		 
		return jsonObject;
	}

	private SysCalendarJsonFactory() {

	}

}
