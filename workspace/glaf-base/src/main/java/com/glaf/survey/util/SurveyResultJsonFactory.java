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
import com.glaf.core.util.DateUtils;
import com.glaf.survey.domain.*;

public class SurveyResultJsonFactory {

	public static java.util.List<SurveyResult> arrayToList(JSONArray array) {
		java.util.List<SurveyResult> list = new java.util.ArrayList<SurveyResult>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SurveyResult model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static SurveyResult jsonToObject(JSONObject jsonObject) {
		SurveyResult model = new SurveyResult();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("surveyId")) {
			model.setSurveyId(jsonObject.getLong("surveyId"));
		}
		if (jsonObject.containsKey("result")) {
			model.setResult(jsonObject.getString("result"));
		}
		if (jsonObject.containsKey("ip")) {
			model.setIp(jsonObject.getString("ip"));
		}
		if (jsonObject.containsKey("surveyDate")) {
			model.setSurveyDate(jsonObject.getDate("surveyDate"));
		}
		if (jsonObject.containsKey("actorId")) {
			model.setActorId(jsonObject.getString("actorId"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<SurveyResult> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SurveyResult model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(SurveyResult model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("surveyId", model.getSurveyId());
		if (model.getResult() != null) {
			jsonObject.put("result", model.getResult());
		}
		if (model.getIp() != null) {
			jsonObject.put("ip", model.getIp());
		}
		if (model.getSurveyDate() != null) {
			jsonObject.put("surveyDate",
					DateUtils.getDate(model.getSurveyDate()));
			jsonObject.put("surveyDate_date",
					DateUtils.getDate(model.getSurveyDate()));
			jsonObject.put("surveyDate_datetime",
					DateUtils.getDateTime(model.getSurveyDate()));
		}
		if (model.getActorId() != null) {
			jsonObject.put("actorId", model.getActorId());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(SurveyResult model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("surveyId", model.getSurveyId());
		if (model.getResult() != null) {
			jsonObject.put("result", model.getResult());
		}
		if (model.getIp() != null) {
			jsonObject.put("ip", model.getIp());
		}
		if (model.getSurveyDate() != null) {
			jsonObject.put("surveyDate",
					DateUtils.getDate(model.getSurveyDate()));
			jsonObject.put("surveyDate_date",
					DateUtils.getDate(model.getSurveyDate()));
			jsonObject.put("surveyDate_datetime",
					DateUtils.getDateTime(model.getSurveyDate()));
		}
		if (model.getActorId() != null) {
			jsonObject.put("actorId", model.getActorId());
		}
		return jsonObject;
	}

	private SurveyResultJsonFactory() {

	}

}
