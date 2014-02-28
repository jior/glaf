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
import com.glaf.core.todo.TodoInstance;
import com.glaf.core.util.DateUtils;

public class TodoInstanceJsonFactory {

	public static java.util.List<TodoInstance> arrayToList(JSONArray array) {
		java.util.List<TodoInstance> list = new java.util.ArrayList<TodoInstance>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			TodoInstance model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static TodoInstance jsonToObject(JSONObject jsonObject) {
		TodoInstance model = new TodoInstance();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("actorId")) {
			model.setActorId(jsonObject.getString("actorId"));
		}
		if (jsonObject.containsKey("actorName")) {
			model.setActorName(jsonObject.getString("actorName"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("provider")) {
			model.setProvider(jsonObject.getString("provider"));
		}
		if (jsonObject.containsKey("link")) {
			model.setLink(jsonObject.getString("link"));
		}
		if (jsonObject.containsKey("linkType")) {
			model.setLinkType(jsonObject.getString("linkType"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("startDate")) {
			model.setStartDate(jsonObject.getDate("startDate"));
		}
		if (jsonObject.containsKey("endDate")) {
			model.setEndDate(jsonObject.getDate("endDate"));
		}
		if (jsonObject.containsKey("alarmDate")) {
			model.setAlarmDate(jsonObject.getDate("alarmDate"));
		}
		if (jsonObject.containsKey("pastDueDate")) {
			model.setPastDueDate(jsonObject.getDate("pastDueDate"));
		}
		if (jsonObject.containsKey("taskInstanceId")) {
			model.setTaskInstanceId(jsonObject.getString("taskInstanceId"));
		}
		if (jsonObject.containsKey("processInstanceId")) {
			model.setProcessInstanceId(jsonObject
					.getString("processInstanceId"));
		}
		if (jsonObject.containsKey("deptId")) {
			model.setDeptId(jsonObject.getLong("deptId"));
		}
		if (jsonObject.containsKey("deptName")) {
			model.setDeptName(jsonObject.getString("deptName"));
		}
		if (jsonObject.containsKey("roleId")) {
			model.setRoleId(jsonObject.getLong("roleId"));
		}
		if (jsonObject.containsKey("roleCode")) {
			model.setRoleCode(jsonObject.getString("roleCode"));
		}
		if (jsonObject.containsKey("rowId")) {
			model.setRowId(jsonObject.getString("rowId"));
		}
		if (jsonObject.containsKey("todoId")) {
			model.setTodoId(jsonObject.getLong("todoId"));
		}
		if (jsonObject.containsKey("appId")) {
			model.setAppId(jsonObject.getLong("appId"));
		}
		if (jsonObject.containsKey("moduleId")) {
			model.setModuleId(jsonObject.getLong("moduleId"));
		}
		if (jsonObject.containsKey("objectId")) {
			model.setObjectId(jsonObject.getString("objectId"));
		}
		if (jsonObject.containsKey("objectValue")) {
			model.setObjectValue(jsonObject.getString("objectValue"));
		}
		if (jsonObject.containsKey("versionNo")) {
			model.setVersionNo(jsonObject.getLong("versionNo"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<TodoInstance> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (TodoInstance model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(TodoInstance model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getActorId() != null) {
			jsonObject.put("actorId", model.getActorId());
		}
		if (model.getActorName() != null) {
			jsonObject.put("actorName", model.getActorName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getProvider() != null) {
			jsonObject.put("provider", model.getProvider());
		}
		if (model.getLink() != null) {
			jsonObject.put("link", model.getLink());
		}
		if (model.getLinkType() != null) {
			jsonObject.put("linkType", model.getLinkType());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getStartDate() != null) {
			jsonObject
					.put("startDate", DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_date",
					DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_datetime",
					DateUtils.getDateTime(model.getStartDate()));
		}
		if (model.getEndDate() != null) {
			jsonObject.put("endDate", DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_date",
					DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_datetime",
					DateUtils.getDateTime(model.getEndDate()));
		}
		if (model.getAlarmDate() != null) {
			jsonObject
					.put("alarmDate", DateUtils.getDate(model.getAlarmDate()));
			jsonObject.put("alarmDate_date",
					DateUtils.getDate(model.getAlarmDate()));
			jsonObject.put("alarmDate_datetime",
					DateUtils.getDateTime(model.getAlarmDate()));
		}
		if (model.getPastDueDate() != null) {
			jsonObject.put("pastDueDate",
					DateUtils.getDate(model.getPastDueDate()));
			jsonObject.put("pastDueDate_date",
					DateUtils.getDate(model.getPastDueDate()));
			jsonObject.put("pastDueDate_datetime",
					DateUtils.getDateTime(model.getPastDueDate()));
		}
		if (model.getTaskInstanceId() != null) {
			jsonObject.put("taskInstanceId", model.getTaskInstanceId());
		}
		if (model.getProcessInstanceId() != null) {
			jsonObject.put("processInstanceId", model.getProcessInstanceId());
		}
		jsonObject.put("deptId", model.getDeptId());
		if (model.getDeptName() != null) {
			jsonObject.put("deptName", model.getDeptName());
		}
		jsonObject.put("roleId", model.getRoleId());
		if (model.getRoleCode() != null) {
			jsonObject.put("roleCode", model.getRoleCode());
		}
		if (model.getRowId() != null) {
			jsonObject.put("rowId", model.getRowId());
		}
		jsonObject.put("todoId", model.getTodoId());
		jsonObject.put("appId", model.getAppId());
		jsonObject.put("moduleId", model.getModuleId());
		if (model.getObjectId() != null) {
			jsonObject.put("objectId", model.getObjectId());
		}
		if (model.getObjectValue() != null) {
			jsonObject.put("objectValue", model.getObjectValue());
		}
		jsonObject.put("versionNo", model.getVersionNo());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(TodoInstance model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getActorId() != null) {
			jsonObject.put("actorId", model.getActorId());
		}
		if (model.getActorName() != null) {
			jsonObject.put("actorName", model.getActorName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getProvider() != null) {
			jsonObject.put("provider", model.getProvider());
		}
		if (model.getLink() != null) {
			jsonObject.put("link", model.getLink());
		}
		if (model.getLinkType() != null) {
			jsonObject.put("linkType", model.getLinkType());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getStartDate() != null) {
			jsonObject
					.put("startDate", DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_date",
					DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_datetime",
					DateUtils.getDateTime(model.getStartDate()));
		}
		if (model.getEndDate() != null) {
			jsonObject.put("endDate", DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_date",
					DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_datetime",
					DateUtils.getDateTime(model.getEndDate()));
		}
		if (model.getAlarmDate() != null) {
			jsonObject
					.put("alarmDate", DateUtils.getDate(model.getAlarmDate()));
			jsonObject.put("alarmDate_date",
					DateUtils.getDate(model.getAlarmDate()));
			jsonObject.put("alarmDate_datetime",
					DateUtils.getDateTime(model.getAlarmDate()));
		}
		if (model.getPastDueDate() != null) {
			jsonObject.put("pastDueDate",
					DateUtils.getDate(model.getPastDueDate()));
			jsonObject.put("pastDueDate_date",
					DateUtils.getDate(model.getPastDueDate()));
			jsonObject.put("pastDueDate_datetime",
					DateUtils.getDateTime(model.getPastDueDate()));
		}
		if (model.getTaskInstanceId() != null) {
			jsonObject.put("taskInstanceId", model.getTaskInstanceId());
		}
		if (model.getProcessInstanceId() != null) {
			jsonObject.put("processInstanceId", model.getProcessInstanceId());
		}
		jsonObject.put("deptId", model.getDeptId());
		if (model.getDeptName() != null) {
			jsonObject.put("deptName", model.getDeptName());
		}
		jsonObject.put("roleId", model.getRoleId());
		if (model.getRoleCode() != null) {
			jsonObject.put("roleCode", model.getRoleCode());
		}
		if (model.getRowId() != null) {
			jsonObject.put("rowId", model.getRowId());
		}
		jsonObject.put("todoId", model.getTodoId());
		jsonObject.put("appId", model.getAppId());
		jsonObject.put("moduleId", model.getModuleId());
		if (model.getObjectId() != null) {
			jsonObject.put("objectId", model.getObjectId());
		}
		if (model.getObjectValue() != null) {
			jsonObject.put("objectValue", model.getObjectValue());
		}
		jsonObject.put("versionNo", model.getVersionNo());
		return jsonObject;
	}

	private TodoInstanceJsonFactory() {

	}

}
