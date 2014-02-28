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

package com.glaf.core.domain.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.domain.DynamicAccessEntity;

public class DynamicAccessEntityJsonFactory {

	public static DynamicAccessEntity jsonToObject(JSONObject jsonObject) {
		DynamicAccessEntity model = new DynamicAccessEntity();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("serviceKey")) {
			model.setServiceKey(jsonObject.getString("serviceKey"));
		}
		if (jsonObject.containsKey("filterSql")) {
			model.setFilterSql(jsonObject.getString("filterSql"));
		}
		if (jsonObject.containsKey("objectId")) {
			model.setObjectId(jsonObject.getString("objectId"));
		}
		if (jsonObject.containsKey("target")) {
			model.setTarget(jsonObject.getString("target"));
		}
		if (jsonObject.containsKey("entityType")) {
			model.setEntityType(jsonObject.getString("entityType"));
		}
		if (jsonObject.containsKey("targetType")) {
			model.setTargetType(jsonObject.getInteger("targetType"));
		}
		if (jsonObject.containsKey("objectValue")) {
			model.setObjectValue(jsonObject.getString("objectValue"));
		}

		return model;
	}

	public static JSONObject toJsonObject(DynamicAccessEntity model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getServiceKey() != null) {
			jsonObject.put("serviceKey", model.getServiceKey());
		}
		if (model.getFilterSql() != null) {
			jsonObject.put("filterSql", model.getFilterSql());
		}
		if (model.getObjectId() != null) {
			jsonObject.put("objectId", model.getObjectId());
		}
		if (model.getTarget() != null) {
			jsonObject.put("target", model.getTarget());
		}
		if (model.getEntityType() != null) {
			jsonObject.put("entityType", model.getEntityType());
		}
		jsonObject.put("targetType", model.getTargetType());
		if (model.getObjectValue() != null) {
			jsonObject.put("objectValue", model.getObjectValue());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(DynamicAccessEntity model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getServiceKey() != null) {
			jsonObject.put("serviceKey", model.getServiceKey());
		}
		if (model.getFilterSql() != null) {
			jsonObject.put("filterSql", model.getFilterSql());
		}
		if (model.getObjectId() != null) {
			jsonObject.put("objectId", model.getObjectId());
		}
		if (model.getTarget() != null) {
			jsonObject.put("target", model.getTarget());
		}
		if (model.getEntityType() != null) {
			jsonObject.put("entityType", model.getEntityType());
		}
		jsonObject.put("targetType", model.getTargetType());
		if (model.getObjectValue() != null) {
			jsonObject.put("objectValue", model.getObjectValue());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<DynamicAccessEntity> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DynamicAccessEntity model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<DynamicAccessEntity> arrayToList(
			JSONArray array) {
		java.util.List<DynamicAccessEntity> list = new java.util.ArrayList<DynamicAccessEntity>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DynamicAccessEntity model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private DynamicAccessEntityJsonFactory() {

	}

}
