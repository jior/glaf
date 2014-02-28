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

package com.glaf.core.todo.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.todo.Todo;

public class TodoJsonFactory {

	public static java.util.List<Todo> arrayToList(JSONArray array) {
		java.util.List<Todo> list = new java.util.ArrayList<Todo>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Todo model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static Todo jsonToObject(JSONObject jsonObject) {
		Todo model = new Todo();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}

		if (jsonObject.containsKey("code")) {
			model.setCode(jsonObject.getString("code"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("deptId")) {
			model.setDeptId(jsonObject.getLong("deptId"));
		}
		if (jsonObject.containsKey("deptName")) {
			model.setDeptName(jsonObject.getString("deptName"));
		}
		if (jsonObject.containsKey("enableFlag")) {
			model.setEnableFlag(jsonObject.getInteger("enableFlag"));
		}

		if (jsonObject.containsKey("limitDay")) {
			model.setLimitDay(jsonObject.getInteger("limitDay"));
		}
		if (jsonObject.containsKey("xa")) {
			model.setXa(jsonObject.getInteger("xa"));
		}
		if (jsonObject.containsKey("xb")) {
			model.setXb(jsonObject.getInteger("xb"));
		}
		if (jsonObject.containsKey("link")) {
			model.setLink(jsonObject.getString("link"));
		}
		if (jsonObject.containsKey("listLink")) {
			model.setListLink(jsonObject.getString("listLink"));
		}
		if (jsonObject.containsKey("linkType")) {
			model.setLinkType(jsonObject.getString("linkType"));
		}
		if (jsonObject.containsKey("appId")) {
			model.setAppId(jsonObject.getLong("appId"));
		}
		if (jsonObject.containsKey("moduleId")) {
			model.setModuleId(jsonObject.getLong("moduleId"));
		}
		if (jsonObject.containsKey("moduleName")) {
			model.setModuleName(jsonObject.getString("moduleName"));
		}

		if (jsonObject.containsKey("objectId")) {
			model.setObjectId(jsonObject.getString("objectId"));
		}
		if (jsonObject.containsKey("objectValue")) {
			model.setObjectValue(jsonObject.getString("objectValue"));
		}
		if (jsonObject.containsKey("roleCode")) {
			model.setRoleCode(jsonObject.getString("roleCode"));
		}
		if (jsonObject.containsKey("roleId")) {
			model.setRoleId(jsonObject.getLong("roleId"));
		}

		if (jsonObject.containsKey("processName")) {
			model.setProcessName(jsonObject.getString("processName"));
		}
		if (jsonObject.containsKey("taskName")) {
			model.setTaskName(jsonObject.getString("taskName"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("sql")) {
			model.setSql(jsonObject.getString("sql"));
		}
		if (jsonObject.containsKey("versionNo")) {
			model.setVersionNo(jsonObject.getLong("versionNo"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<Todo> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (Todo model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(Todo model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());

		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		jsonObject.put("deptId", model.getDeptId());

		jsonObject.put("enableFlag", model.getEnableFlag());

		jsonObject.put("limitDay", model.getLimitDay());
		jsonObject.put("xa", model.getXa());
		jsonObject.put("xb", model.getXb());
		if (model.getLink() != null) {
			jsonObject.put("link", model.getLink());
		}
		if (model.getListLink() != null) {
			jsonObject.put("listLink", model.getListLink());
		}
		if (model.getLinkType() != null) {
			jsonObject.put("linkType", model.getLinkType());
		}
		jsonObject.put("appId", model.getAppId());
		jsonObject.put("moduleId", model.getModuleId());
		if (model.getModuleName() != null) {
			jsonObject.put("moduleName", model.getModuleName());
		}

		if (model.getObjectId() != null) {
			jsonObject.put("objectId", model.getObjectId());
		}
		if (model.getObjectValue() != null) {
			jsonObject.put("objectValue", model.getObjectValue());
		}
		if (model.getRoleCode() != null) {
			jsonObject.put("roleCode", model.getRoleCode());
		}
		jsonObject.put("roleId", model.getRoleId());

		if (model.getProcessName() != null) {
			jsonObject.put("processName", model.getProcessName());
		}
		if (model.getTaskName() != null) {
			jsonObject.put("taskName", model.getTaskName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getSql() != null) {
			jsonObject.put("sql", model.getSql());
		}
		jsonObject.put("versionNo", model.getVersionNo());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(Todo model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());

		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		jsonObject.put("deptId", model.getDeptId());

		jsonObject.put("enableFlag", model.getEnableFlag());

		jsonObject.put("limitDay", model.getLimitDay());
		jsonObject.put("xa", model.getXa());
		jsonObject.put("xb", model.getXb());
		if (model.getLink() != null) {
			jsonObject.put("link", model.getLink());
		}
		if (model.getListLink() != null) {
			jsonObject.put("listLink", model.getListLink());
		}
		if (model.getLinkType() != null) {
			jsonObject.put("linkType", model.getLinkType());
		}
		jsonObject.put("appId", model.getAppId());
		jsonObject.put("moduleId", model.getModuleId());
		if (model.getModuleName() != null) {
			jsonObject.put("moduleName", model.getModuleName());
		}

		if (model.getObjectId() != null) {
			jsonObject.put("objectId", model.getObjectId());
		}
		if (model.getObjectValue() != null) {
			jsonObject.put("objectValue", model.getObjectValue());
		}
		if (model.getRoleCode() != null) {
			jsonObject.put("roleCode", model.getRoleCode());
		}
		jsonObject.put("roleId", model.getRoleId());

		if (model.getProcessName() != null) {
			jsonObject.put("processName", model.getProcessName());
		}
		if (model.getTaskName() != null) {
			jsonObject.put("taskName", model.getTaskName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getSql() != null) {
			jsonObject.put("sql", model.getSql());
		}
		jsonObject.put("versionNo", model.getVersionNo());
		return jsonObject;
	}

	private TodoJsonFactory() {

	}

}
