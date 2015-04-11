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

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.domain.*;

/**
 * 
 * JSON工厂类
 *
 */
public class DatabaseAccessJsonFactory {

	public static DatabaseAccess jsonToObject(JSONObject jsonObject) {
		DatabaseAccess model = new DatabaseAccess();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("databaseId")) {
			model.setDatabaseId(jsonObject.getLong("databaseId"));
		}
		if (jsonObject.containsKey("actorId")) {
			model.setActorId(jsonObject.getString("actorId"));
		}

		return model;
	}

	public static JSONObject toJsonObject(DatabaseAccess model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("databaseId", model.getDatabaseId());
		if (model.getActorId() != null) {
			jsonObject.put("actorId", model.getActorId());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(DatabaseAccess model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("databaseId", model.getDatabaseId());
		if (model.getActorId() != null) {
			jsonObject.put("actorId", model.getActorId());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<DatabaseAccess> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DatabaseAccess model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<DatabaseAccess> arrayToList(JSONArray array) {
		java.util.List<DatabaseAccess> list = new java.util.ArrayList<DatabaseAccess>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DatabaseAccess model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private DatabaseAccessJsonFactory() {

	}

}
