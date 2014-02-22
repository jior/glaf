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
import com.glaf.batch.domain.JobInstance;

public class JobInstanceJsonFactory {

	public static java.util.List<JobInstance> arrayToList(JSONArray array) {
		java.util.List<JobInstance> list = new java.util.ArrayList<JobInstance>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			JobInstance model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static JobInstance jsonToObject(JSONObject jsonObject) {
		JobInstance model = new JobInstance();
		if (jsonObject.containsKey("jobInstanceId")) {
			model.setJobInstanceId(jsonObject.getLong("jobInstanceId"));
		}
		if (jsonObject.containsKey("version")) {
			model.setVersion(jsonObject.getInteger("version"));
		}
		if (jsonObject.containsKey("jobName")) {
			model.setJobName(jsonObject.getString("jobName"));
		}
		if (jsonObject.containsKey("jobKey")) {
			model.setJobKey(jsonObject.getString("jobKey"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<JobInstance> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (JobInstance model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(JobInstance model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("jobInstanceId", model.getJobInstanceId());
		jsonObject.put("_jobInstanceId_", model.getJobInstanceId());
		jsonObject.put("version", model.getVersion());
		if (model.getJobName() != null) {
			jsonObject.put("jobName", model.getJobName());
		}
		if (model.getJobKey() != null) {
			jsonObject.put("jobKey", model.getJobKey());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(JobInstance model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("jobInstanceId", model.getJobInstanceId());
		jsonObject.put("_jobInstanceId_", model.getJobInstanceId());
		jsonObject.put("version", model.getVersion());
		if (model.getJobName() != null) {
			jsonObject.put("jobName", model.getJobName());
		}
		if (model.getJobKey() != null) {
			jsonObject.put("jobKey", model.getJobKey());
		}
		return jsonObject;
	}

	private JobInstanceJsonFactory() {

	}

}
