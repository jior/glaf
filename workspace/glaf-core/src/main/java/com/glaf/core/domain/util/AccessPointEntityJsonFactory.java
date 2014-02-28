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
import com.glaf.core.domain.AccessPointEntity;

public class AccessPointEntityJsonFactory {

	public static AccessPointEntity jsonToObject(JSONObject jsonObject) {
		AccessPointEntity model = new AccessPointEntity();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("accessEntryId")) {
			model.setAccessEntryId(jsonObject.getString("accessEntryId"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("accessLevel")) {
			model.setAccessLevel(jsonObject.getInteger("accessLevel"));
		}

		return model;
	}

	public static JSONObject toJsonObject(AccessPointEntity model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getAccessEntryId() != null) {
			jsonObject.put("accessEntryId", model.getAccessEntryId());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		jsonObject.put("accessLevel", model.getAccessLevel());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(AccessPointEntity model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getAccessEntryId() != null) {
			jsonObject.put("accessEntryId", model.getAccessEntryId());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		jsonObject.put("accessLevel", model.getAccessLevel());
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<AccessPointEntity> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (AccessPointEntity model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<AccessPointEntity> arrayToList(JSONArray array) {
		java.util.List<AccessPointEntity> list = new java.util.ArrayList<AccessPointEntity>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			AccessPointEntity model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private AccessPointEntityJsonFactory() {

	}

}
