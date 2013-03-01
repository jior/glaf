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

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.model.SysRole;

public class SysRoleJsonFactory {

	public static SysRole jsonToObject(JSONObject jsonObject) {
		SysRole model = new SysRole();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("desc")) {
			model.setDesc(jsonObject.getString("desc"));
		}
		if (jsonObject.containsKey("code")) {
			model.setCode(jsonObject.getString("code"));
		}
		if (jsonObject.containsKey("sort")) {
			model.setSort(jsonObject.getInteger("sort"));
		}

		return model;
	}

	public static JSONObject toJsonObject(SysRole model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		}
		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		}
		jsonObject.put("sort", model.getSort());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(SysRole model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		}
		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		}
		jsonObject.put("sort", model.getSort());
		return jsonObject;
	}

	private SysRoleJsonFactory() {

	}

}
