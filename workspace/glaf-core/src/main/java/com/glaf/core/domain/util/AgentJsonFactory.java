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
import com.glaf.core.domain.AgentEntity;
import com.glaf.core.util.DateUtils;

public class AgentJsonFactory {

	public static java.util.List<AgentEntity> arrayToList(JSONArray array) {
		java.util.List<AgentEntity> list = new java.util.ArrayList<AgentEntity>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			AgentEntity model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static AgentEntity jsonToObject(JSONObject jsonObject) {
		AgentEntity model = new AgentEntity();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("agentType")) {
			model.setAgentType(jsonObject.getInteger("agentType"));
		}
		if (jsonObject.containsKey("assignFrom")) {
			model.setAssignFrom(jsonObject.getString("assignFrom"));
		}
		if (jsonObject.containsKey("assignTo")) {
			model.setAssignTo(jsonObject.getString("assignTo"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("endDate")) {
			model.setEndDate(jsonObject.getDate("endDate"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}
		if (jsonObject.containsKey("objectId")) {
			model.setObjectId(jsonObject.getString("objectId"));
		}
		if (jsonObject.containsKey("objectValue")) {
			model.setObjectValue(jsonObject.getString("objectValue"));
		}
		if (jsonObject.containsKey("processName")) {
			model.setProcessName(jsonObject.getString("processName"));
		}
		if (jsonObject.containsKey("serviceKey")) {
			model.setServiceKey(jsonObject.getString("serviceKey"));
		}
		if (jsonObject.containsKey("startDate")) {
			model.setStartDate(jsonObject.getDate("startDate"));
		}
		if (jsonObject.containsKey("taskName")) {
			model.setTaskName(jsonObject.getString("taskName"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<AgentEntity> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (AgentEntity model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(AgentEntity model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("agentType", model.getAgentType());
		if (model.getAssignFrom() != null) {
			jsonObject.put("assignFrom", model.getAssignFrom());
		}
		if (model.getAssignTo() != null) {
			jsonObject.put("assignTo", model.getAssignTo());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getEndDate() != null) {
			jsonObject.put("endDate", DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_date",
					DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_datetime",
					DateUtils.getDateTime(model.getEndDate()));
		}
		jsonObject.put("locked", model.getLocked());
		if (model.getObjectId() != null) {
			jsonObject.put("objectId", model.getObjectId());
		}
		if (model.getObjectValue() != null) {
			jsonObject.put("objectValue", model.getObjectValue());
		}
		if (model.getProcessName() != null) {
			jsonObject.put("processName", model.getProcessName());
		}
		if (model.getServiceKey() != null) {
			jsonObject.put("serviceKey", model.getServiceKey());
		}
		if (model.getStartDate() != null) {
			jsonObject
					.put("startDate", DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_date",
					DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_datetime",
					DateUtils.getDateTime(model.getStartDate()));
		}
		if (model.getTaskName() != null) {
			jsonObject.put("taskName", model.getTaskName());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(AgentEntity model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("agentType", model.getAgentType());
		if (model.getAssignFrom() != null) {
			jsonObject.put("assignFrom", model.getAssignFrom());
		}
		if (model.getAssignTo() != null) {
			jsonObject.put("assignTo", model.getAssignTo());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getEndDate() != null) {
			jsonObject.put("endDate", DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_date",
					DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_datetime",
					DateUtils.getDateTime(model.getEndDate()));
		}
		jsonObject.put("locked", model.getLocked());
		if (model.getObjectId() != null) {
			jsonObject.put("objectId", model.getObjectId());
		}
		if (model.getObjectValue() != null) {
			jsonObject.put("objectValue", model.getObjectValue());
		}
		if (model.getProcessName() != null) {
			jsonObject.put("processName", model.getProcessName());
		}
		if (model.getServiceKey() != null) {
			jsonObject.put("serviceKey", model.getServiceKey());
		}
		if (model.getStartDate() != null) {
			jsonObject
					.put("startDate", DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_date",
					DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_datetime",
					DateUtils.getDateTime(model.getStartDate()));
		}
		if (model.getTaskName() != null) {
			jsonObject.put("taskName", model.getTaskName());
		}
		return jsonObject;
	}

	private AgentJsonFactory() {

	}

}
