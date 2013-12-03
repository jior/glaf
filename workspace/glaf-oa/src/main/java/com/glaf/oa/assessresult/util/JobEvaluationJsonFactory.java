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
package com.glaf.oa.assessresult.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.oa.assessresult.model.JobEvaluation;

public class JobEvaluationJsonFactory {

	public static JobEvaluation jsonToObject(JSONObject jsonObject) {
		JobEvaluation model = new JobEvaluation();
		if (jsonObject.containsKey("treeName")) {
			model.setTreeName(jsonObject.getString("treeName"));
		}
		if (jsonObject.containsKey("dictoryName")) {
			model.setDictoryName(jsonObject.getString("dictoryName"));
		}
		if (jsonObject.containsKey("contentId")) {
			model.setContentId(jsonObject.getLong("contentId"));
		}
		if (jsonObject.containsKey("contentName")) {
			model.setContentName(jsonObject.getString("contentName"));
		}
		if (jsonObject.containsKey("basis")) {
			model.setBasis(jsonObject.getString("basis"));
		}
		if (jsonObject.containsKey("standard")) {
			model.setStandard(jsonObject.getDouble("standard"));
		}
		if (jsonObject.containsKey("score")) {
			model.setScore(jsonObject.getInteger("score"));
		}
		if (jsonObject.containsKey("reason")) {
			model.setReason(jsonObject.getString("reason"));
		}

		return model;
	}

	public static JSONObject toJsonObject(JobEvaluation model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("treeName", model.getTreeName());
		jsonObject.put("dictoryName", model.getDictoryName());
		jsonObject.put("contentId", model.getContentId());
		jsonObject.put("contentName", model.getContentName());
		if (model.getBasis() != null) {
			jsonObject.put("basis", model.getBasis());
		}
		if (model.getStandard() != null) {
			jsonObject.put("standard", model.getStandard());
		}
		if (model.getScore() != null) {
			jsonObject.put("score", model.getScore());
		}
		if (model.getReason() != null) {
			jsonObject.put("reason", model.getReason());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(JobEvaluation model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("treeName", model.getTreeName());
		jsonObject.put("dictoryName", model.getDictoryName());
		jsonObject.put("contentId", model.getContentId());
		jsonObject.put("contentName", model.getContentName());
		if (model.getBasis() != null) {
			jsonObject.put("basis", model.getBasis());
		}
		if (model.getStandard() != null) {
			jsonObject.put("standard", model.getStandard());
		}
		if (model.getScore() != null) {
			jsonObject.put("score", model.getScore());
		}
		if (model.getReason() != null) {
			jsonObject.put("reason", model.getReason());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<JobEvaluation> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (JobEvaluation model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<JobEvaluation> arrayToList(JSONArray array) {
		java.util.List<JobEvaluation> list = new java.util.ArrayList<JobEvaluation>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			JobEvaluation model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private JobEvaluationJsonFactory() {

	}

}