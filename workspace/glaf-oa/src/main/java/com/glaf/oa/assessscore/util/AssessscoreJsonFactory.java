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
package com.glaf.oa.assessscore.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;
import com.glaf.oa.assessscore.model.*;

public class AssessscoreJsonFactory {

	public static Assessscore jsonToObject(JSONObject jsonObject) {
		Assessscore model = new Assessscore();
		if (jsonObject.containsKey("scoreid")) {
			model.setScoreid(jsonObject.getLong("scoreid"));
		}
		if (jsonObject.containsKey("contentid")) {
			model.setContentid(jsonObject.getLong("contentid"));
		}
		if (jsonObject.containsKey("resultid")) {
			model.setResultid(jsonObject.getLong("resultid"));
		}
		if (jsonObject.containsKey("score")) {
			model.setScore(jsonObject.getLong("score"));
		}
		if (jsonObject.containsKey("reason")) {
			model.setReason(jsonObject.getString("reason"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("updateDate")) {
			model.setUpdateDate(jsonObject.getDate("updateDate"));
		}
		if (jsonObject.containsKey("updateBy")) {
			model.setUpdateBy(jsonObject.getString("updateBy"));
		}

		return model;
	}

	public static JSONObject toJsonObject(Assessscore model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("scoreid", model.getScoreid());
		jsonObject.put("_scoreid_", model.getScoreid());
		jsonObject.put("contentid", model.getContentid());
		jsonObject.put("resultid", model.getResultid());
		jsonObject.put("score", model.getScore());
		if (model.getReason() != null) {
			jsonObject.put("reason", model.getReason());
		}
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
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime",
					DateUtils.getDateTime(model.getUpdateDate()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(Assessscore model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("scoreid", model.getScoreid());
		jsonObject.put("_scoreid_", model.getScoreid());
		jsonObject.put("contentid", model.getContentid());
		jsonObject.put("resultid", model.getResultid());
		jsonObject.put("score", model.getScore());
		if (model.getReason() != null) {
			jsonObject.put("reason", model.getReason());
		}
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
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime",
					DateUtils.getDateTime(model.getUpdateDate()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<Assessscore> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (Assessscore model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<Assessscore> arrayToList(JSONArray array) {
		java.util.List<Assessscore> list = new java.util.ArrayList<Assessscore>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Assessscore model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private AssessscoreJsonFactory() {

	}

}