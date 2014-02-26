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

public class JobDefinitionJsonFactory {

	public static JobDefinition jsonToObject(JSONObject jsonObject) {
		JobDefinition model = new JobDefinition();
		if (jsonObject.containsKey("jobDefinitionId")) {
			model.setJobDefinitionId(jsonObject.getLong("jobDefinitionId"));
		}
		if (jsonObject.containsKey("jobKey")) {
			model.setJobKey(jsonObject.getString("jobKey"));
		}
		if (jsonObject.containsKey("jobName")) {
			model.setJobName(jsonObject.getString("jobName"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createTime")) {
			model.setCreateTime(jsonObject.getDate("createTime"));
		}

		return model;
	}

	public static JSONObject toJsonObject(JobDefinition model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("jobDefinitionId", model.getJobDefinitionId());
		jsonObject.put("_jobDefinitionId_", model.getJobDefinitionId());
		if (model.getJobKey() != null) {
			jsonObject.put("jobKey", model.getJobKey());
		}
		if (model.getJobName() != null) {
			jsonObject.put("jobName", model.getJobName());
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime",
					DateUtils.getDateTime(model.getCreateTime()));
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(JobDefinition model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("jobDefinitionId", model.getJobDefinitionId());
		jsonObject.put("_jobDefinitionId_", model.getJobDefinitionId());
		if (model.getJobKey() != null) {
			jsonObject.put("jobKey", model.getJobKey());
		}
		if (model.getJobName() != null) {
			jsonObject.put("jobName", model.getJobName());
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime",
					DateUtils.getDateTime(model.getCreateTime()));
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<JobDefinition> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (JobDefinition model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<JobDefinition> arrayToList(JSONArray array) {
		java.util.List<JobDefinition> list = new java.util.ArrayList<JobDefinition>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			JobDefinition model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private JobDefinitionJsonFactory() {

	}

}
