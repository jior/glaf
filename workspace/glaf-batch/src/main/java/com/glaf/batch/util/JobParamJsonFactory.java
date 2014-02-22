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
import com.glaf.batch.domain.JobParam;
import com.glaf.core.util.DateUtils;

public class JobParamJsonFactory {

	public static java.util.List<JobParam> arrayToList(JSONArray array) {
		java.util.List<JobParam> list = new java.util.ArrayList<JobParam>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			JobParam model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static JobParam jsonToObject(JSONObject jsonObject) {
		JobParam model = new JobParam();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getInteger("id"));
		}
		if (jsonObject.containsKey("jobInstanceId")) {
			model.setJobInstanceId(jsonObject.getInteger("jobInstanceId"));
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
		if (jsonObject.containsKey("dateVal")) {
			model.setDateVal(jsonObject.getDate("dateVal"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<JobParam> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (JobParam model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(JobParam model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("jobInstanceId", model.getJobInstanceId());
		if (model.getTypeCd() != null) {
			jsonObject.put("typeCd", model.getTypeCd());
		}
		if (model.getKeyName() != null) {
			jsonObject.put("keyName", model.getKeyName());
		}
		if (model.getStringVal() != null) {
			jsonObject.put("stringVal", model.getStringVal());
		}
		if (model.getDateVal() != null) {
			jsonObject.put("dateVal", DateUtils.getDate(model.getDateVal()));
			jsonObject.put("dateVal_date",
					DateUtils.getDate(model.getDateVal()));
			jsonObject.put("dateVal_datetime",
					DateUtils.getDateTime(model.getDateVal()));
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(JobParam model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("jobInstanceId", model.getJobInstanceId());
		if (model.getTypeCd() != null) {
			jsonObject.put("typeCd", model.getTypeCd());
		}
		if (model.getKeyName() != null) {
			jsonObject.put("keyName", model.getKeyName());
		}
		if (model.getStringVal() != null) {
			jsonObject.put("stringVal", model.getStringVal());
		}
		if (model.getDateVal() != null) {
			jsonObject.put("dateVal", DateUtils.getDate(model.getDateVal()));
			jsonObject.put("dateVal_date",
					DateUtils.getDate(model.getDateVal()));
			jsonObject.put("dateVal_datetime",
					DateUtils.getDateTime(model.getDateVal()));
		}
		return jsonObject;
	}

	private JobParamJsonFactory() {

	}

}
