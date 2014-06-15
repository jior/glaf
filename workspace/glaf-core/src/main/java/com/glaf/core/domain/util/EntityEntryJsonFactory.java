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

import java.util.Collection;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.domain.EntityEntry;
import com.glaf.core.domain.EntryPoint;
import com.glaf.core.util.DateUtils;

public class EntityEntryJsonFactory {

	public static EntityEntry jsonToObject(JSONObject jsonObject) {
		EntityEntry model = new EntityEntry();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("startDate")) {
			model.setStartDate(jsonObject.getDate("startDate"));
		}
		if (jsonObject.containsKey("moduleId")) {
			model.setModuleId(jsonObject.getString("moduleId"));
		}
		if (jsonObject.containsKey("subject")) {
			model.setSubject(jsonObject.getString("subject"));
		}
		if (jsonObject.containsKey("entityId")) {
			model.setEntityId(jsonObject.getString("entityId"));
		}
		if (jsonObject.containsKey("endDate")) {
			model.setEndDate(jsonObject.getDate("endDate"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("dataCode")) {
			model.setDataCode(jsonObject.getString("dataCode"));
		}
		if (jsonObject.containsKey("isPropagationAllowed")) {
			model.setIsPropagationAllowed(jsonObject
					.getString("isPropagationAllowed"));
		}
		if (jsonObject.containsKey("entryType")) {
			model.setEntryType(jsonObject.getInteger("entryType"));
		}
		if (jsonObject.containsKey("objectId")) {
			model.setObjectId(jsonObject.getString("objectId"));
		}
		if (jsonObject.containsKey("entryKey")) {
			model.setEntryKey(jsonObject.getString("entryKey"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("objectValue")) {
			model.setObjectValue(jsonObject.getString("objectValue"));
		}
		
		if (jsonObject.containsKey("nodeId")) {
			model.setNodeId(jsonObject.getLong("nodeId"));
		}
		if (jsonObject.containsKey("entityId")) {
			model.setEntityId(jsonObject.getString("entityId"));
		}
		if (jsonObject.containsKey("entryKey")) {
			model.setEntryKey(jsonObject.getString("entryKey"));
		}
		if (jsonObject.containsKey("entryType")) {
			model.setEntryType(jsonObject.getInteger("entryType"));
		}
		if (jsonObject.containsKey("moduleId")) {
			model.setModuleId(jsonObject.getString("moduleId"));
		}
		if (jsonObject.containsKey("dataCode")) {
			model.setDataCode(jsonObject.getString("dataCode"));
		}
		if (jsonObject.containsKey("isPropagationAllowed")) {
			model.setIsPropagationAllowed(jsonObject
					.getString("isPropagationAllowed"));
		}
		if (jsonObject.containsKey("startDate")) {
			model.setStartDate(jsonObject.getDate("startDate"));
		}
		if (jsonObject.containsKey("endDate")) {
			model.setEndDate(jsonObject.getDate("endDate"));
		}
		if (jsonObject.containsKey("subject")) {
			model.setSubject(jsonObject.getString("subject"));
		}
		if (jsonObject.containsKey("objectId")) {
			model.setObjectId(jsonObject.getString("objectId"));
		}
		if (jsonObject.containsKey("objectValue")) {
			model.setObjectValue(jsonObject.getString("objectValue"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}

		return model;
	}

	public static JSONObject toJsonObject(EntityEntry model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("nodeId", model.getNodeId());
		if (model.getStartDate() != null) {
			jsonObject
					.put("startDate", DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_date",
					DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_datetime",
					DateUtils.getDateTime(model.getStartDate()));
		}
		if (model.getModuleId() != null) {
			jsonObject.put("moduleId", model.getModuleId());
		}
		if (model.getSubject() != null) {
			jsonObject.put("subject", model.getSubject());
		}
		if (model.getEntityId() != null) {
			jsonObject.put("entityId", model.getEntityId());
		}
		if (model.getEndDate() != null) {
			jsonObject.put("endDate", DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_date",
					DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_datetime",
					DateUtils.getDateTime(model.getEndDate()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getDataCode() != null) {
			jsonObject.put("dataCode", model.getDataCode());
		}
		if (model.getIsPropagationAllowed() != null) {
			jsonObject.put("isPropagationAllowed",
					model.getIsPropagationAllowed());
		}
		jsonObject.put("entryType", model.getEntryType());
		if (model.getObjectId() != null) {
			jsonObject.put("objectId", model.getObjectId());
		}
		if (model.getEntryKey() != null) {
			jsonObject.put("entryKey", model.getEntryKey());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getObjectValue() != null) {
			jsonObject.put("objectValue", model.getObjectValue());
		}
		
		List<EntryPoint> entryPoints= model.getEntryPoints();
		if (entryPoints != null && entryPoints.size() > 0) {
			Collection<JSONObject> rows = new java.util.ArrayList<JSONObject>();
			for (EntryPoint p : entryPoints) {
				JSONObject json = new JSONObject();
				json.put("name", p.getName());
				if (p.getEntryKey() != null) {
					json.put("entryKey", p.getEntryKey());
				}
				if (p.getValue() != null) {
					json.put("value", p.getValue());
				}
				rows.add(json);
			}
			jsonObject.put("entryPoints", rows);
		}
		
		return jsonObject;
	}

	public static ObjectNode toObjectNode(EntityEntry model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("nodeId", model.getNodeId());
		if (model.getStartDate() != null) {
			jsonObject
					.put("startDate", DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_date",
					DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_datetime",
					DateUtils.getDateTime(model.getStartDate()));
		}
		if (model.getModuleId() != null) {
			jsonObject.put("moduleId", model.getModuleId());
		}
		if (model.getSubject() != null) {
			jsonObject.put("subject", model.getSubject());
		}
		if (model.getEntityId() != null) {
			jsonObject.put("entityId", model.getEntityId());
		}
		if (model.getEndDate() != null) {
			jsonObject.put("endDate", DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_date",
					DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_datetime",
					DateUtils.getDateTime(model.getEndDate()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getDataCode() != null) {
			jsonObject.put("dataCode", model.getDataCode());
		}
		if (model.getIsPropagationAllowed() != null) {
			jsonObject.put("isPropagationAllowed",
					model.getIsPropagationAllowed());
		}
		jsonObject.put("entryType", model.getEntryType());
		if (model.getObjectId() != null) {
			jsonObject.put("objectId", model.getObjectId());
		}
		if (model.getEntryKey() != null) {
			jsonObject.put("entryKey", model.getEntryKey());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getObjectValue() != null) {
			jsonObject.put("objectValue", model.getObjectValue());
		}
		
		List<EntryPoint> entryPoints= model.getEntryPoints();
		if (entryPoints != null && entryPoints.size() > 0) {
			ArrayNode rows = new ObjectMapper().createArrayNode();
			for (EntryPoint p : entryPoints) {
				ObjectNode json  = new ObjectMapper().createObjectNode();
				json.put("name", p.getName());
				if (p.getEntryKey() != null) {
					json.put("entryKey", p.getEntryKey());
				}
				if (p.getValue() != null) {
					json.put("value", p.getValue());
				}
				rows.add(json);
			}
			jsonObject.set("entryPoints", rows);
		}
		
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<EntityEntry> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (EntityEntry model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<EntityEntry> arrayToList(JSONArray array) {
		java.util.List<EntityEntry> list = new java.util.ArrayList<EntityEntry>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			EntityEntry model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private EntityEntryJsonFactory() {

	}

}
