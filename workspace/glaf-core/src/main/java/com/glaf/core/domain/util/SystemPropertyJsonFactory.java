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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.domain.SystemProperty;
import com.glaf.core.util.FileUtils;

public class SystemPropertyJsonFactory {

	public static java.util.List<SystemProperty> arrayToList(JSONArray array) {
		java.util.List<SystemProperty> list = new java.util.ArrayList<SystemProperty>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SystemProperty model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static SystemProperty jsonToObject(JSONObject jsonObject) {
		SystemProperty model = new SystemProperty();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("category")) {
			model.setCategory(jsonObject.getString("category"));
		}
		if (jsonObject.containsKey("description")) {
			model.setDescription(jsonObject.getString("description"));
		}
		if (jsonObject.containsKey("initValue")) {
			model.setInitValue(jsonObject.getString("initValue"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("value")) {
			model.setValue(jsonObject.getString("value"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<SystemProperty> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SystemProperty model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(SystemProperty model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getCategory() != null) {
			jsonObject.put("category", model.getCategory());
		}
		if (model.getDescription() != null) {
			jsonObject.put("description", model.getDescription());
		}
		if (model.getInitValue() != null) {
			jsonObject.put("initValue", model.getInitValue());
		}
		jsonObject.put("locked", model.getLocked());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getValue() != null) {
			jsonObject.put("value", model.getValue());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(SystemProperty model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getCategory() != null) {
			jsonObject.put("category", model.getCategory());
		}
		if (model.getDescription() != null) {
			jsonObject.put("description", model.getDescription());
		}
		if (model.getInitValue() != null) {
			jsonObject.put("initValue", model.getInitValue());
		}
		jsonObject.put("locked", model.getLocked());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getValue() != null) {
			jsonObject.put("value", model.getValue());
		}
		return jsonObject;
	}

	private SystemPropertyJsonFactory() {

	}

	public static void main(String[] args) {
		String str = "[{\"name\":\"限制\",\"value\":\"true\"},{\"name\":\"不限制\",\"value\":\"false\"}]";
		System.out.println(str);
		JSONArray array = JSON.parseArray(str);
		StringBuffer buffer = new StringBuffer();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject json = array.getJSONObject(i);
			buffer.append("<option value=\"").append(json.getString("value"))
					.append("\">").append(json.getString("name"))
					.append("</option>").append(FileUtils.newline);
		}
		System.out.println(buffer.toString());
	}

}
