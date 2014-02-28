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
import com.glaf.core.domain.EntryPoint;

public class EntryPointJsonFactory {

	public static EntryPoint jsonToObject(JSONObject jsonObject) {
		EntryPoint model = new EntryPoint();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("nodeId")) {
			model.setNodeId(jsonObject.getLong("nodeId"));
		}
		if (jsonObject.containsKey("entityId")) {
			model.setEntityId(jsonObject.getString("entityId"));
		}
		if (jsonObject.containsKey("value")) {
			model.setValue(jsonObject.getString("value"));
		}
		if (jsonObject.containsKey("entryKey")) {
			model.setEntryKey(jsonObject.getString("entryKey"));
		}

		return model;
	}

	public static JSONObject toJsonObject(EntryPoint model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("nodeId", model.getNodeId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getEntityId() != null) {
			jsonObject.put("entityId", model.getEntityId());
		}
		if (model.getValue() != null) {
			jsonObject.put("value", model.getValue());
		}
		if (model.getEntryKey() != null) {
			jsonObject.put("entryKey", model.getEntryKey());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(EntryPoint model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("nodeId", model.getNodeId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getEntityId() != null) {
			jsonObject.put("entityId", model.getEntityId());
		}
		if (model.getValue() != null) {
			jsonObject.put("value", model.getValue());
		}
		if (model.getEntryKey() != null) {
			jsonObject.put("entryKey", model.getEntryKey());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<EntryPoint> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (EntryPoint model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<EntryPoint> arrayToList(JSONArray array) {
		java.util.List<EntryPoint> list = new java.util.ArrayList<EntryPoint>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			EntryPoint model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private EntryPointJsonFactory() {

	}

}
