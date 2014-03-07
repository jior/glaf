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

package com.glaf.batch.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;
import com.glaf.batch.domain.*;

public class StepDefinitionParamJsonFactory {

	public static StepDefinitionParam jsonToObject(JSONObject jsonObject) {
		StepDefinitionParam model = new StepDefinitionParam();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("jobDefinitionId")) {
			model.setJobDefinitionId(jsonObject.getLong("jobDefinitionId"));
		}
		if (jsonObject.containsKey("stepDefinitionId")) {
			model.setStepDefinitionId(jsonObject.getLong("stepDefinitionId"));
		}
		if (jsonObject.containsKey("typeCd")) {
			model.setTypeCd(jsonObject.getString("typeCd"));
		}
		if (jsonObject.containsKey("keyName")) {
			model.setKeyName(jsonObject.getString("keyName"));
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
		if (jsonObject.containsKey("intVal")) {
			model.setIntVal(jsonObject.getInteger("intVal"));
		}
		if (jsonObject.containsKey("longVal")) {
			model.setLongVal(jsonObject.getLong("longVal"));
		}
		if (jsonObject.containsKey("doubleVal")) {
			model.setDoubleVal(jsonObject.getDouble("doubleVal"));
		}

		return model;
	}

	public static JSONObject toJsonObject(StepDefinitionParam model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("jobDefinitionId", model.getJobDefinitionId());
		jsonObject.put("stepDefinitionId", model.getStepDefinitionId());
		if (model.getTypeCd() != null) {
			jsonObject.put("typeCd", model.getTypeCd());
		}
		if (model.getKeyName() != null) {
			jsonObject.put("keyName", model.getKeyName());
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
		jsonObject.put("intVal", model.getIntVal());
		jsonObject.put("longVal", model.getLongVal());
		jsonObject.put("doubleVal", model.getDoubleVal());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(StepDefinitionParam model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("jobDefinitionId", model.getJobDefinitionId());
		jsonObject.put("stepDefinitionId", model.getStepDefinitionId());
		if (model.getTypeCd() != null) {
			jsonObject.put("typeCd", model.getTypeCd());
		}
		if (model.getKeyName() != null) {
			jsonObject.put("keyName", model.getKeyName());
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
		jsonObject.put("intVal", model.getIntVal());
		jsonObject.put("longVal", model.getLongVal());
		jsonObject.put("doubleVal", model.getDoubleVal());
		return jsonObject;
	}

	public static JSONArray listToArray(
			java.util.List<StepDefinitionParam> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (StepDefinitionParam model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<StepDefinitionParam> arrayToList(
			JSONArray array) {
		java.util.List<StepDefinitionParam> list = new java.util.ArrayList<StepDefinitionParam>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			StepDefinitionParam model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private StepDefinitionParamJsonFactory() {

	}

}
