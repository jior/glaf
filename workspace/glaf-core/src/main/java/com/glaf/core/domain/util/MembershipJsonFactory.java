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
import com.glaf.core.domain.Membership;
import com.glaf.core.util.DateUtils;

public class MembershipJsonFactory {

	public static java.util.List<Membership> arrayToList(JSONArray array) {
		java.util.List<Membership> list = new java.util.ArrayList<Membership>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Membership model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static Membership jsonToObject(JSONObject jsonObject) {
		Membership model = new Membership();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("superiorId")) {
			model.setSuperiorId(jsonObject.getString("superiorId"));
		}
		if (jsonObject.containsKey("nodeId")) {
			model.setNodeId(jsonObject.getLong("nodeId"));
		}
		if (jsonObject.containsKey("roleId")) {
			model.setRoleId(jsonObject.getLong("roleId"));
		}
		if (jsonObject.containsKey("modifyBy")) {
			model.setModifyBy(jsonObject.getString("modifyBy"));
		}
		if (jsonObject.containsKey("objectId")) {
			model.setObjectId(jsonObject.getString("objectId"));
		}
		if (jsonObject.containsKey("actorId")) {
			model.setActorId(jsonObject.getString("actorId"));
		}
		if (jsonObject.containsKey("attribute")) {
			model.setAttribute(jsonObject.getString("attribute"));
		}
		if (jsonObject.containsKey("modifyDate")) {
			model.setModifyDate(jsonObject.getDate("modifyDate"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("objectValue")) {
			model.setObjectValue(jsonObject.getString("objectValue"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<Membership> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (Membership model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(Membership model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getSuperiorId() != null) {
			jsonObject.put("superiorId", model.getSuperiorId());
		}
		jsonObject.put("nodeId", model.getNodeId());
		if (model.getModifyBy() != null) {
			jsonObject.put("modifyBy", model.getModifyBy());
		}
		if (model.getObjectId() != null) {
			jsonObject.put("objectId", model.getObjectId());
		}
		if (model.getActorId() != null) {
			jsonObject.put("actorId", model.getActorId());
		}
		if (model.getAttribute() != null) {
			jsonObject.put("attribute", model.getAttribute());
		}
		if (model.getModifyDate() != null) {
			jsonObject.put("modifyDate",
					DateUtils.getDate(model.getModifyDate()));
			jsonObject.put("modifyDate_date",
					DateUtils.getDate(model.getModifyDate()));
			jsonObject.put("modifyDate_datetime",
					DateUtils.getDateTime(model.getModifyDate()));
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getObjectValue() != null) {
			jsonObject.put("objectValue", model.getObjectValue());
		}
		if (model.getRoleId() != null) {
			jsonObject.put("roleId", model.getRoleId());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(Membership model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getSuperiorId() != null) {
			jsonObject.put("superiorId", model.getSuperiorId());
		}
		jsonObject.put("nodeId", model.getNodeId());
		if (model.getModifyBy() != null) {
			jsonObject.put("modifyBy", model.getModifyBy());
		}
		if (model.getObjectId() != null) {
			jsonObject.put("objectId", model.getObjectId());
		}
		if (model.getActorId() != null) {
			jsonObject.put("actorId", model.getActorId());
		}
		if (model.getAttribute() != null) {
			jsonObject.put("attribute", model.getAttribute());
		}
		if (model.getModifyDate() != null) {
			jsonObject.put("modifyDate",
					DateUtils.getDate(model.getModifyDate()));
			jsonObject.put("modifyDate_date",
					DateUtils.getDate(model.getModifyDate()));
			jsonObject.put("modifyDate_datetime",
					DateUtils.getDateTime(model.getModifyDate()));
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getObjectValue() != null) {
			jsonObject.put("objectValue", model.getObjectValue());
		}
		if (model.getRoleId() != null) {
			jsonObject.put("roleId", model.getRoleId());
		}
		return jsonObject;
	}

	private MembershipJsonFactory() {

	}

}
