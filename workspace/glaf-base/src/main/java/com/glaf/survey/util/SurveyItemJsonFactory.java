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

package com.glaf.survey.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.survey.domain.*;

public class SurveyItemJsonFactory {

	public static java.util.List<SurveyItem> arrayToList(JSONArray array) {
		java.util.List<SurveyItem> list = new java.util.ArrayList<SurveyItem>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SurveyItem model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static SurveyItem jsonToObject(JSONObject jsonObject) {
		SurveyItem model = new SurveyItem();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("surveyId")) {
			model.setSurveyId(jsonObject.getLong("surveyId"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("value")) {
			model.setValue(jsonObject.getString("value"));
		}
		if (jsonObject.containsKey("sort")) {
			model.setSort(jsonObject.getInteger("sort"));
		}
		if (jsonObject.containsKey("icon")) {
			model.setIcon(jsonObject.getString("icon"));
		}
		if (jsonObject.containsKey("url")) {
			model.setUrl(jsonObject.getString("url"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<SurveyItem> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SurveyItem model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(SurveyItem model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("surveyId", model.getSurveyId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getValue() != null) {
			jsonObject.put("value", model.getValue());
		}
		jsonObject.put("sort", model.getSort());
		if (model.getIcon() != null) {
			jsonObject.put("icon", model.getIcon());
		}
		if (model.getUrl() != null) {
			jsonObject.put("url", model.getUrl());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(SurveyItem model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("surveyId", model.getSurveyId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getValue() != null) {
			jsonObject.put("value", model.getValue());
		}
		jsonObject.put("sort", model.getSort());
		if (model.getIcon() != null) {
			jsonObject.put("icon", model.getIcon());
		}
		if (model.getUrl() != null) {
			jsonObject.put("url", model.getUrl());
		}
		return jsonObject;
	}

	private SurveyItemJsonFactory() {

	}

}
