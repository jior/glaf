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
import com.glaf.base.modules.sys.model.SysPermission;

public class SysPermissionJsonFactory {

	public static java.util.List<SysPermission> arrayToList(JSONArray array) {
		java.util.List<SysPermission> list = new java.util.ArrayList<SysPermission>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SysPermission model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static SysPermission jsonToObject(JSONObject jsonObject) {
		SysPermission model = new SysPermission();
		if (jsonObject.containsKey("funcId")) {
			model.setFuncId(jsonObject.getLong("funcId"));
		}
		if (jsonObject.containsKey("roleId")) {
			model.setRoleId(jsonObject.getLong("roleId"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<SysPermission> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SysPermission model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(SysPermission model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("funcId", model.getFuncId());
		jsonObject.put("_funcId_", model.getFuncId());
		jsonObject.put("roleId", model.getRoleId());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(SysPermission model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("funcId", model.getFuncId());
		jsonObject.put("_funcId_", model.getFuncId());
		jsonObject.put("roleId", model.getRoleId());
		return jsonObject;
	}

	private SysPermissionJsonFactory() {

	}

}
