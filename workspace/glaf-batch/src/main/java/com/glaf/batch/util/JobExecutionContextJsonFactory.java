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
import com.glaf.batch.domain.JobExecutionContext;

public class JobExecutionContextJsonFactory {

	public static java.util.List<JobExecutionContext> arrayToList(
			JSONArray array) {
		java.util.List<JobExecutionContext> list = new java.util.ArrayList<JobExecutionContext>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			JobExecutionContext model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static JobExecutionContext jsonToObject(JSONObject jsonObject) {
		JobExecutionContext model = new JobExecutionContext();
		if (jsonObject.containsKey("jobExecutionId")) {
			model.setJobExecutionId(jsonObject.getLong("jobExecutionId"));
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

	public static JSONArray listToArray(java.util.List<JobExecutionContext> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (JobExecutionContext model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(JobExecutionContext model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("jobExecutionId", model.getJobExecutionId());
		jsonObject.put("_jobExecutionId_", model.getJobExecutionId());
		if (model.getShortContext() != null) {
			jsonObject.put("shortContext", model.getShortContext());
		}
		if (model.getSerializedContext() != null) {
			jsonObject.put("serializedContext", model.getSerializedContext());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(JobExecutionContext model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("jobExecutionId", model.getJobExecutionId());
		jsonObject.put("_jobExecutionId_", model.getJobExecutionId());
		if (model.getShortContext() != null) {
			jsonObject.put("shortContext", model.getShortContext());
		}
		if (model.getSerializedContext() != null) {
			jsonObject.put("serializedContext", model.getSerializedContext());
		}
		return jsonObject;
	}

	private JobExecutionContextJsonFactory() {

	}

}
