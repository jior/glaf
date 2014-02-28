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
import com.glaf.core.domain.AccessEntryEntity;
import com.glaf.core.util.DateUtils;

public class AccessEntryEntityJsonFactory {

	public static AccessEntryEntity jsonToObject(JSONObject jsonObject) {
		AccessEntryEntity model = new AccessEntryEntity();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("roleId")) {
			model.setRoleId(jsonObject.getLong("roleId"));
		}
		if (jsonObject.containsKey("entryType")) {
			model.setEntryType(jsonObject.getInteger("entryType"));
		}
		if (jsonObject.containsKey("objectId")) {
			model.setObjectId(jsonObject.getString("objectId"));
		}
		if (jsonObject.containsKey("applicationName")) {
			model.setApplicationName(jsonObject.getString("applicationName"));
		}
		if (jsonObject.containsKey("formName")) {
			model.setFormName(jsonObject.getString("formName"));
		}
		if (jsonObject.containsKey("processName")) {
			model.setProcessName(jsonObject.getString("processName"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("processDefinitionId")) {
			model.setProcessDefinitionId(jsonObject
					.getString("processDefinitionId"));
		}
		if (jsonObject.containsKey("taskName")) {
			model.setTaskName(jsonObject.getString("taskName"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("editFile")) {
			model.setEditFile(jsonObject.getString("editFile"));
		}
		if (jsonObject.containsKey("objectValue")) {
			model.setObjectValue(jsonObject.getString("objectValue"));
		}

		return model;
	}

	public static JSONObject toJsonObject(AccessEntryEntity model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("entryType", model.getEntryType());

		if (model.getRoleId() != null) {
			jsonObject.put("roleId", model.getRoleId());
		}
		if (model.getApplicationName() != null) {
			jsonObject.put("applicationName", model.getApplicationName());
		}
		if (model.getFormName() != null) {
			jsonObject.put("formName", model.getFormName());
		}
		if (model.getProcessName() != null) {
			jsonObject.put("processName", model.getProcessName());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getProcessDefinitionId() != null) {
			jsonObject.put("processDefinitionId",
					model.getProcessDefinitionId());
		}
		if (model.getTaskName() != null) {
			jsonObject.put("taskName", model.getTaskName());
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getEditFile() != null) {
			jsonObject.put("editFile", model.getEditFile());
		}
		if (model.getObjectId() != null) {
			jsonObject.put("objectId", model.getObjectId());
		}
		if (model.getObjectValue() != null) {
			jsonObject.put("objectValue", model.getObjectValue());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(AccessEntryEntity model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("entryType", model.getEntryType());
		if (model.getRoleId() != null) {
			jsonObject.put("roleId", model.getRoleId());
		}

		if (model.getApplicationName() != null) {
			jsonObject.put("applicationName", model.getApplicationName());
		}
		if (model.getFormName() != null) {
			jsonObject.put("formName", model.getFormName());
		}
		if (model.getProcessName() != null) {
			jsonObject.put("processName", model.getProcessName());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getProcessDefinitionId() != null) {
			jsonObject.put("processDefinitionId",
					model.getProcessDefinitionId());
		}
		if (model.getTaskName() != null) {
			jsonObject.put("taskName", model.getTaskName());
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getEditFile() != null) {
			jsonObject.put("editFile", model.getEditFile());
		}
		if (model.getObjectId() != null) {
			jsonObject.put("objectId", model.getObjectId());
		}
		if (model.getObjectValue() != null) {
			jsonObject.put("objectValue", model.getObjectValue());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<AccessEntryEntity> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (AccessEntryEntity model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<AccessEntryEntity> arrayToList(JSONArray array) {
		java.util.List<AccessEntryEntity> list = new java.util.ArrayList<AccessEntryEntity>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			AccessEntryEntity model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private AccessEntryEntityJsonFactory() {

	}

}
