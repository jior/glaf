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
import com.glaf.core.domain.SchedulerParam;
import com.glaf.core.util.DateUtils;

public class SchedulerParamJsonFactory {

	public static java.util.List<SchedulerParam> arrayToList(JSONArray array) {
		java.util.List<SchedulerParam> list = new java.util.ArrayList<SchedulerParam>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SchedulerParam model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static SchedulerParam jsonToObject(JSONObject jsonObject) {
		SchedulerParam model = new SchedulerParam();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("taskId")) {
			model.setTaskId(jsonObject.getString("taskId"));
		}
		if (jsonObject.containsKey("typeCd")) {
			model.setTypeCd(jsonObject.getString("typeCd"));
		}
		if (jsonObject.containsKey("keyName")) {
			model.setKeyName(jsonObject.getString("keyName"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("stringVal")) {
			model.setStringVal(jsonObject.getString("stringVal"));
		}
		if (jsonObject.containsKey("textVal")) {
			model.setTextVal(jsonObject.getString("textVal"));
		}
		if (jsonObject.containsKey("dateVal")) {
			model.setDateVal(jsonObject.getDate("dateVal"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<SchedulerParam> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SchedulerParam model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(SchedulerParam model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTaskId() != null) {
			jsonObject.put("taskId", model.getTaskId());
		}
		if (model.getTypeCd() != null) {
			jsonObject.put("typeCd", model.getTypeCd());
		}
		if (model.getKeyName() != null) {
			jsonObject.put("keyName", model.getKeyName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getStringVal() != null) {
			jsonObject.put("stringVal", model.getStringVal());
		}
		if (model.getTextVal() != null) {
			jsonObject.put("textVal", model.getTextVal());
		}
		if (model.getDateVal() != null) {
			jsonObject.put("dateVal", DateUtils.getDate(model.getDateVal()));
			jsonObject.put("dateVal_date",
					DateUtils.getDate(model.getDateVal()));
			jsonObject.put("dateVal_datetime",
					DateUtils.getDateTime(model.getDateVal()));
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(SchedulerParam model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTaskId() != null) {
			jsonObject.put("taskId", model.getTaskId());
		}
		if (model.getTypeCd() != null) {
			jsonObject.put("typeCd", model.getTypeCd());
		}
		if (model.getKeyName() != null) {
			jsonObject.put("keyName", model.getKeyName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getStringVal() != null) {
			jsonObject.put("stringVal", model.getStringVal());
		}
		if (model.getTextVal() != null) {
			jsonObject.put("textVal", model.getTextVal());
		}
		if (model.getDateVal() != null) {
			jsonObject.put("dateVal", DateUtils.getDate(model.getDateVal()));
			jsonObject.put("dateVal_date",
					DateUtils.getDate(model.getDateVal()));
			jsonObject.put("dateVal_datetime",
					DateUtils.getDateTime(model.getDateVal()));
		}
		return jsonObject;
	}

	private SchedulerParamJsonFactory() {

	}

}
