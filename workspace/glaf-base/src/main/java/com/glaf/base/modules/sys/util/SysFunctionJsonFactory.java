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

package com.glaf.base.modules.sys.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.model.SysFunction;

public class SysFunctionJsonFactory {

	public static java.util.List<SysFunction> arrayToList(JSONArray array) {
		java.util.List<SysFunction> list = new java.util.ArrayList<SysFunction>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SysFunction model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static SysFunction jsonToObject(JSONObject jsonObject) {
		SysFunction model = new SysFunction();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("funcDesc")) {
			model.setFuncDesc(jsonObject.getString("funcDesc"));
		}
		if (jsonObject.containsKey("funcMethod")) {
			model.setFuncMethod(jsonObject.getString("funcMethod"));
		}
		if (jsonObject.containsKey("sort")) {
			model.setSort(jsonObject.getInteger("sort"));
		}
		if (jsonObject.containsKey("appId")) {
			model.setAppId(jsonObject.getLong("appId"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<SysFunction> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SysFunction model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(SysFunction model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getFuncDesc() != null) {
			jsonObject.put("funcDesc", model.getFuncDesc());
		}
		if (model.getFuncMethod() != null) {
			jsonObject.put("funcMethod", model.getFuncMethod());
		}
		jsonObject.put("sort", model.getSort());
		jsonObject.put("appId", model.getAppId());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(SysFunction model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getFuncDesc() != null) {
			jsonObject.put("funcDesc", model.getFuncDesc());
		}
		if (model.getFuncMethod() != null) {
			jsonObject.put("funcMethod", model.getFuncMethod());
		}
		jsonObject.put("sort", model.getSort());
		jsonObject.put("appId", model.getAppId());
		return jsonObject;
	}

	private SysFunctionJsonFactory() {

	}

}
