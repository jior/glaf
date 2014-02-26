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

import com.glaf.batch.domain.*;

public class JobStepDefinitionJsonFactory {

	public static JobStepDefinition jsonToObject(JSONObject jsonObject) {
		JobStepDefinition model = new JobStepDefinition();
		if (jsonObject.containsKey("stepDefinitionId")) {
			model.setStepDefinitionId(jsonObject.getLong("stepDefinitionId"));
		}
		if (jsonObject.containsKey("jobDefinitionId")) {
			model.setJobDefinitionId(jsonObject.getLong("jobDefinitionId"));
		}
		if (jsonObject.containsKey("stepKey")) {
			model.setStepKey(jsonObject.getString("stepKey"));
		}
		if (jsonObject.containsKey("stepName")) {
			model.setStepName(jsonObject.getString("stepName"));
		}
		if (jsonObject.containsKey("jobStepKey")) {
			model.setJobStepKey(jsonObject.getString("jobStepKey"));
		}
		if (jsonObject.containsKey("jobClass")) {
			model.setJobClass(jsonObject.getString("jobClass"));
		}
		if (jsonObject.containsKey("listno")) {
			model.setListno(jsonObject.getInteger("listno"));
		}

		return model;
	}

	public static JSONObject toJsonObject(JobStepDefinition model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("stepDefinitionId", model.getStepDefinitionId());
		jsonObject.put("_stepDefinitionId_", model.getStepDefinitionId());
		jsonObject.put("jobDefinitionId", model.getJobDefinitionId());
		if (model.getStepKey() != null) {
			jsonObject.put("stepKey", model.getStepKey());
		}
		if (model.getStepName() != null) {
			jsonObject.put("stepName", model.getStepName());
		}
		if (model.getJobStepKey() != null) {
			jsonObject.put("jobStepKey", model.getJobStepKey());
		}
		if (model.getJobClass() != null) {
			jsonObject.put("jobClass", model.getJobClass());
		}
		jsonObject.put("listno", model.getListno());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(JobStepDefinition model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("stepDefinitionId", model.getStepDefinitionId());
		jsonObject.put("_stepDefinitionId_", model.getStepDefinitionId());
		jsonObject.put("jobDefinitionId", model.getJobDefinitionId());
		if (model.getStepKey() != null) {
			jsonObject.put("stepKey", model.getStepKey());
		}
		if (model.getStepName() != null) {
			jsonObject.put("stepName", model.getStepName());
		}
		if (model.getJobStepKey() != null) {
			jsonObject.put("jobStepKey", model.getJobStepKey());
		}
		if (model.getJobClass() != null) {
			jsonObject.put("jobClass", model.getJobClass());
		}
		jsonObject.put("listno", model.getListno());
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<JobStepDefinition> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (JobStepDefinition model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<JobStepDefinition> arrayToList(JSONArray array) {
		java.util.List<JobStepDefinition> list = new java.util.ArrayList<JobStepDefinition>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			JobStepDefinition model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private JobStepDefinitionJsonFactory() {

	}

}
