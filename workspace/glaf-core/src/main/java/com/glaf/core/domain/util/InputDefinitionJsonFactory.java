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
import com.glaf.core.domain.InputDefinition;

public class InputDefinitionJsonFactory {

	public static java.util.List<InputDefinition> arrayToList(JSONArray array) {
		java.util.List<InputDefinition> list = new java.util.ArrayList<InputDefinition>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			InputDefinition model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static InputDefinition jsonToObject(JSONObject jsonObject) {
		InputDefinition model = new InputDefinition();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("serviceKey")) {
			model.setServiceKey(jsonObject.getString("serviceKey"));
		}
		if (jsonObject.containsKey("typeCd")) {
			model.setTypeCd(jsonObject.getString("typeCd"));
		}
		if (jsonObject.containsKey("typeTitle")) {
			model.setTypeTitle(jsonObject.getString("typeTitle"));
		}
		if (jsonObject.containsKey("keyName")) {
			model.setKeyName(jsonObject.getString("keyName"));
		}
		if (jsonObject.containsKey("javaType")) {
			model.setJavaType(jsonObject.getString("javaType"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("inputType")) {
			model.setInputType(jsonObject.getString("inputType"));
		}
		if (jsonObject.containsKey("valueField")) {
			model.setValueField(jsonObject.getString("valueField"));
		}
		if (jsonObject.containsKey("textField")) {
			model.setTextField(jsonObject.getString("textField"));
		}
		if (jsonObject.containsKey("url")) {
			model.setUrl(jsonObject.getString("url"));
		}
		if (jsonObject.containsKey("validType")) {
			model.setValidType(jsonObject.getString("validType"));
		}
		if (jsonObject.containsKey("required")) {
			model.setRequired(jsonObject.getString("required"));
		}
		if (jsonObject.containsKey("initValue")) {
			model.setInitValue(jsonObject.getString("initValue"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<InputDefinition> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (InputDefinition model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(InputDefinition model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getServiceKey() != null) {
			jsonObject.put("serviceKey", model.getServiceKey());
		}
		if (model.getTypeCd() != null) {
			jsonObject.put("typeCd", model.getTypeCd());
		}
		if (model.getTypeTitle() != null) {
			jsonObject.put("typeTitle", model.getTypeTitle());
		}
		if (model.getKeyName() != null) {
			jsonObject.put("keyName", model.getKeyName());
		}
		if (model.getJavaType() != null) {
			jsonObject.put("javaType", model.getJavaType());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getInputType() != null) {
			jsonObject.put("inputType", model.getInputType());
		}
		if (model.getValueField() != null) {
			jsonObject.put("valueField", model.getValueField());
		}
		if (model.getTextField() != null) {
			jsonObject.put("textField", model.getTextField());
		}
		if (model.getUrl() != null) {
			jsonObject.put("url", model.getUrl());
		}
		if (model.getValidType() != null) {
			jsonObject.put("validType", model.getValidType());
		}
		if (model.getRequired() != null) {
			jsonObject.put("required", model.getRequired());
		}
		if (model.getInitValue() != null) {
			jsonObject.put("initValue", model.getInitValue());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(InputDefinition model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getServiceKey() != null) {
			jsonObject.put("serviceKey", model.getServiceKey());
		}
		if (model.getTypeCd() != null) {
			jsonObject.put("typeCd", model.getTypeCd());
		}
		if (model.getTypeTitle() != null) {
			jsonObject.put("typeTitle", model.getTypeTitle());
		}
		if (model.getKeyName() != null) {
			jsonObject.put("keyName", model.getKeyName());
		}
		if (model.getJavaType() != null) {
			jsonObject.put("javaType", model.getJavaType());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getInputType() != null) {
			jsonObject.put("inputType", model.getInputType());
		}
		if (model.getValueField() != null) {
			jsonObject.put("valueField", model.getValueField());
		}
		if (model.getTextField() != null) {
			jsonObject.put("textField", model.getTextField());
		}
		if (model.getUrl() != null) {
			jsonObject.put("url", model.getUrl());
		}
		if (model.getValidType() != null) {
			jsonObject.put("validType", model.getValidType());
		}
		if (model.getRequired() != null) {
			jsonObject.put("required", model.getRequired());
		}
		if (model.getInitValue() != null) {
			jsonObject.put("initValue", model.getInitValue());
		}
		return jsonObject;
	}

	private InputDefinitionJsonFactory() {

	}

}
