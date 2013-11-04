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

package com.glaf.core.identity.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.identity.Role;
import com.glaf.core.identity.impl.RoleImpl;

public class RoleJsonFactory {
	
	public static JSONObject toJsonObject(Role role) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", role.getId());
		jsonObject.put("_id_", role.getId());
		jsonObject.put("_oid_", role.getId());
		jsonObject.put("roleId", role.getId());
		jsonObject.put("name", role.getName());
		jsonObject.put("locked", role.getLocked());
		jsonObject.put("type", role.getType());
		jsonObject.put("remark", role.getRemark());
		jsonObject.put("listno", role.getSort());
		 
		return jsonObject;
	}
	
	public static ObjectNode toObjectNode(Role role) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", role.getId());
		jsonObject.put("_id_", role.getId());
		jsonObject.put("_oid_", role.getId());
		jsonObject.put("roleId", role.getId());
		jsonObject.put("name", role.getName());
		jsonObject.put("locked", role.getLocked());
		jsonObject.put("type", role.getType());
		jsonObject.put("remark", role.getRemark());
		jsonObject.put("listno", role.getSort());
		return jsonObject;
	}
	
	public static Role jsonToObject(JSONObject jsonObject) {
		Role model = new RoleImpl();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getInteger("id"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getInteger("type"));
		}
		 
		return model;
	}

}
