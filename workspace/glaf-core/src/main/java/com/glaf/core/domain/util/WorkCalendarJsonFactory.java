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

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.domain.WorkCalendar;

public class WorkCalendarJsonFactory {

	public static java.util.List<WorkCalendar> arrayToList(JSONArray array) {
		java.util.List<WorkCalendar> list = new java.util.ArrayList<WorkCalendar>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			WorkCalendar model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static WorkCalendar jsonToObject(JSONObject jsonObject) {
		WorkCalendar model = new WorkCalendar();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("freeDay")) {
			model.setFreeDay(jsonObject.getInteger("freeDay"));
		}
		if (jsonObject.containsKey("freeMonth")) {
			model.setFreeMonth(jsonObject.getInteger("freeMonth"));
		}
		if (jsonObject.containsKey("freeYear")) {
			model.setFreeYear(jsonObject.getInteger("freeYear"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<WorkCalendar> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (WorkCalendar model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(WorkCalendar model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("freeDay", model.getFreeDay());
		jsonObject.put("freeMonth", model.getFreeMonth());
		jsonObject.put("freeYear", model.getFreeYear());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(WorkCalendar model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("freeDay", model.getFreeDay());
		jsonObject.put("freeMonth", model.getFreeMonth());
		jsonObject.put("freeYear", model.getFreeYear());
		return jsonObject;
	}

	private WorkCalendarJsonFactory() {

	}

}
