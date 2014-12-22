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

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.core.domain.*;

public class SysDataTableJsonFactory {

	public static java.util.List<SysDataTable> arrayToList(JSONArray array) {
		java.util.List<SysDataTable> list = new java.util.ArrayList<SysDataTable>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SysDataTable model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static SysDataTable jsonToObject(JSONObject jsonObject) {
		SysDataTable model = new SysDataTable();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("serviceKey")) {
			model.setServiceKey(jsonObject.getString("serviceKey"));
		}
		if (jsonObject.containsKey("tablename")) {
			model.setTablename(jsonObject.getString("tablename"));
		}
		if (model.getSortColumnName() != null) {
			jsonObject.put("sortColumnName", model.getSortColumnName());
		}

		if (jsonObject.containsKey("sortOrder")) {
			model.setSortOrder(jsonObject.getString("sortOrder"));
		}

		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getInteger("type"));
		}
		if (jsonObject.containsKey("treeType")) {
			model.setTreeType(jsonObject.getString("treeType"));
		}
		if (jsonObject.containsKey("maxUser")) {
			model.setMaxUser(jsonObject.getInteger("maxUser"));
		}
		if (jsonObject.containsKey("maxSys")) {
			model.setMaxSys(jsonObject.getInteger("maxSys"));
		}
		if (jsonObject.containsKey("readUrl")) {
			model.setReadUrl(jsonObject.getString("readUrl"));
		}
		if (jsonObject.containsKey("createUrl")) {
			model.setCreateUrl(jsonObject.getString("createUrl"));
		}
		if (jsonObject.containsKey("updateUrl")) {
			model.setUpdateUrl(jsonObject.getString("updateUrl"));
		}
		if (jsonObject.containsKey("destroyUrl")) {
			model.setDestroyUrl(jsonObject.getString("destroyUrl"));
		}
		if (jsonObject.containsKey("accessType")) {
			model.setAccessType(jsonObject.getString("accessType"));
		}
		if (jsonObject.containsKey("perms")) {
			model.setPerms(jsonObject.getString("perms"));
		}
		if (jsonObject.containsKey("addressPerms")) {
			model.setAddressPerms(jsonObject.getString("addressPerms"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createTime")) {
			model.setCreateTime(jsonObject.getDate("createTime"));
		}
		if (jsonObject.containsKey("updateTime")) {
			model.setUpdateTime(jsonObject.getDate("updateTime"));
		}
		if (jsonObject.containsKey("updateBy")) {
			model.setUpdateBy(jsonObject.getString("updateBy"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("isSubTable")) {
			model.setIsSubTable(jsonObject.getString("isSubTable"));
		}
		if (jsonObject.containsKey("deleteFlag")) {
			model.setDeleteFlag(jsonObject.getInteger("deleteFlag"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<SysDataTable> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SysDataTable model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(SysDataTable model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getServiceKey() != null) {
			jsonObject.put("serviceKey", model.getServiceKey());
		}
		if (model.getTablename() != null) {
			jsonObject.put("tablename", model.getTablename());
		}
		if (model.getSortColumnName() != null) {
			jsonObject.put("sortColumnName", model.getSortColumnName());
		}
		if (model.getSortOrder() != null) {
			jsonObject.put("sortOrder", model.getSortOrder());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}

		if (model.getType() != null && model.getType() != -1) {
			jsonObject.put("type", model.getType());
			switch (model.getType()) {
			case 10:
				jsonObject.put("typeName", "用户自定义表");
				break;
			case 20:
				jsonObject.put("typeName", "中间计量表");
				break;
			case 40:
				jsonObject.put("typeName", "流程审批表");
				break;
			case 90:
				jsonObject.put("typeName", "基础数据表");
				break;
			case 99:
				jsonObject.put("typeName", "系统表");
				break;
			default:
				jsonObject.put("typeName", "临时数据表");
				break;
			}
		}

		if (model.getTreeType() != null) {
			jsonObject.put("treeType", model.getTreeType());
		}

		jsonObject.put("maxUser", model.getMaxUser());
		jsonObject.put("maxSys", model.getMaxSys());

		if (model.getReadUrl() != null) {
			jsonObject.put("readUrl", model.getReadUrl());
		}
		if (model.getCreateUrl() != null) {
			jsonObject.put("createUrl", model.getCreateUrl());
		}
		if (model.getUpdateUrl() != null) {
			jsonObject.put("updateUrl", model.getUpdateUrl());
		}
		if (model.getDestroyUrl() != null) {
			jsonObject.put("destroyUrl", model.getDestroyUrl());
		}

		if (model.getAccessType() != null) {
			jsonObject.put("accessType", model.getAccessType());
		}

		if (model.getPerms() != null) {
			jsonObject.put("perms", model.getPerms());
		}
		if (model.getAddressPerms() != null) {
			jsonObject.put("addressPerms", model.getAddressPerms());
		}

		jsonObject.put("locked", model.getLocked());

		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime",
					DateUtils.getDateTime(model.getCreateTime()));
		}
		if (model.getUpdateTime() != null) {
			jsonObject.put("updateTime",
					DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_date",
					DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_datetime",
					DateUtils.getDateTime(model.getUpdateTime()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getIsSubTable() != null) {
			jsonObject.put("isSubTable", model.getIsSubTable());
		}
		jsonObject.put("deleteFlag", model.getDeleteFlag());
		if (model.getFields() != null && !model.getFields().isEmpty()) {
			JSONArray array = new JSONArray();
			for (SysDataField field : model.getFields()) {
				array.add(field.toJsonObject());
			}
			jsonObject.put("fields", array);
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(SysDataTable model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getServiceKey() != null) {
			jsonObject.put("serviceKey", model.getServiceKey());
		}
		if (model.getTablename() != null) {
			jsonObject.put("tablename", model.getTablename());
		}
		if (model.getSortColumnName() != null) {
			jsonObject.put("sortColumnName", model.getSortColumnName());
		}
		if (model.getSortOrder() != null) {
			jsonObject.put("sortOrder", model.getSortOrder());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}

		if (model.getType() != null && model.getType() != -1) {
			jsonObject.put("type", model.getType());
			switch (model.getType()) {
			case 10:
				jsonObject.put("typeName", "用户自定义表");
				break;
			case 20:
				jsonObject.put("typeName", "中间计量表");
				break;
			case 40:
				jsonObject.put("typeName", "流程审批表");
				break;
			case 90:
				jsonObject.put("typeName", "基础数据表");
				break;
			case 99:
				jsonObject.put("typeName", "系统表");
				break;
			default:
				jsonObject.put("typeName", "临时数据表");
				break;
			}
		}

		if (model.getTreeType() != null) {
			jsonObject.put("treeType", model.getTreeType());
		}

		if (model.getReadUrl() != null) {
			jsonObject.put("readUrl", model.getReadUrl());
		}
		if (model.getCreateUrl() != null) {
			jsonObject.put("createUrl", model.getCreateUrl());
		}
		if (model.getUpdateUrl() != null) {
			jsonObject.put("updateUrl", model.getUpdateUrl());
		}
		if (model.getDestroyUrl() != null) {
			jsonObject.put("destroyUrl", model.getDestroyUrl());
		}

		if (model.getAccessType() != null) {
			jsonObject.put("accessType", model.getAccessType());
		}

		if (model.getPerms() != null) {
			jsonObject.put("perms", model.getPerms());
		}
		if (model.getAddressPerms() != null) {
			jsonObject.put("addressPerms", model.getAddressPerms());
		}

		jsonObject.put("locked", model.getLocked());

		jsonObject.put("maxUser", model.getMaxUser());
		jsonObject.put("maxSys", model.getMaxSys());
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime",
					DateUtils.getDateTime(model.getCreateTime()));
		}
		if (model.getUpdateTime() != null) {
			jsonObject.put("updateTime",
					DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_date",
					DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_datetime",
					DateUtils.getDateTime(model.getUpdateTime()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getIsSubTable() != null) {
			jsonObject.put("isSubTable", model.getIsSubTable());
		}
		jsonObject.put("deleteFlag", model.getDeleteFlag());
		if (model.getFields() != null && !model.getFields().isEmpty()) {
			ArrayNode array = new ObjectMapper().createArrayNode();
			for (SysDataField field : model.getFields()) {
				array.add(field.toObjectNode());
			}
			jsonObject.set("fields", array);
		}
		return jsonObject;
	}

	private SysDataTableJsonFactory() {

	}

}
