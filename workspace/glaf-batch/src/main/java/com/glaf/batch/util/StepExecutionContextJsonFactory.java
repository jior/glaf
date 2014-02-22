/*
 * Copyright 2006-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.batch.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.batch.domain.StepExecutionContext;

public class StepExecutionContextJsonFactory {

	public static java.util.List<StepExecutionContext> arrayToList(
			JSONArray array) {
		java.util.List<StepExecutionContext> list = new java.util.ArrayList<StepExecutionContext>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			StepExecutionContext model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static StepExecutionContext jsonToObject(JSONObject jsonObject) {
		StepExecutionContext model = new StepExecutionContext();
		if (jsonObject.containsKey("stepExecutionId")) {
			model.setStepExecutionId(jsonObject.getInteger("stepExecutionId"));
		}
		if (jsonObject.containsKey("shortContext")) {
			model.setShortContext(jsonObject.getString("shortContext"));
		}
		if (jsonObject.containsKey("serializedContext")) {
			model.setSerializedContext(jsonObject
					.getString("serializedContext"));
		}

		return model;
	}

	public static JSONArray listToArray(
			java.util.List<StepExecutionContext> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (StepExecutionContext model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(StepExecutionContext model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("stepExecutionId", model.getStepExecutionId());
		jsonObject.put("_stepExecutionId_", model.getStepExecutionId());
		if (model.getShortContext() != null) {
			jsonObject.put("shortContext", model.getShortContext());
		}
		if (model.getSerializedContext() != null) {
			jsonObject.put("serializedContext", model.getSerializedContext());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(StepExecutionContext model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("stepExecutionId", model.getStepExecutionId());
		jsonObject.put("_stepExecutionId_", model.getStepExecutionId());
		if (model.getShortContext() != null) {
			jsonObject.put("shortContext", model.getShortContext());
		}
		if (model.getSerializedContext() != null) {
			jsonObject.put("serializedContext", model.getSerializedContext());
		}
		return jsonObject;
	}

	private StepExecutionContextJsonFactory() {

	}

}
