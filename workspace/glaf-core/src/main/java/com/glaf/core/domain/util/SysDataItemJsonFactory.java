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
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;
import com.glaf.core.domain.*;

/**
 * 
 * JSON工厂类
 *
 */
public class SysDataItemJsonFactory {

	public static SysDataItem jsonToObject(JSONObject jsonObject) {
		SysDataItem model = new SysDataItem();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("queryId")) {
			model.setQueryId(jsonObject.getString("queryId"));
		}
		if (jsonObject.containsKey("querySQL")) {
			model.setQuerySQL(jsonObject.getString("querySQL"));
		}
		if (jsonObject.containsKey("parameter")) {
			model.setParameter(jsonObject.getString("parameter"));
		}
		if (jsonObject.containsKey("textField")) {
			model.setTextField(jsonObject.getString("textField"));
		}
		if (jsonObject.containsKey("valueField")) {
			model.setValueField(jsonObject.getString("valueField"));
		}
		if (jsonObject.containsKey("treeIdField")) {
			model.setTreeIdField(jsonObject.getString("treeIdField"));
		}
		if (jsonObject.containsKey("treeParentIdField")) {
			model.setTreeParentIdField(jsonObject
					.getString("treeParentIdField"));
		}
		if (jsonObject.containsKey("treeTreeIdField")) {
			model.setTreeTreeIdField(jsonObject.getString("treeTreeIdField"));
		}
		if (jsonObject.containsKey("treeNameField")) {
			model.setTreeNameField(jsonObject.getString("treeNameField"));
		}
		if (jsonObject.containsKey("treeListNoField")) {
			model.setTreeListNoField(jsonObject.getString("treeListNoField"));
		}
		if (jsonObject.containsKey("url")) {
			model.setUrl(jsonObject.getString("url"));
		}
		if (jsonObject.containsKey("cacheFlag")) {
			model.setCacheFlag(jsonObject.getString("cacheFlag"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createTime")) {
			model.setCreateTime(jsonObject.getDate("createTime"));
		}
		if (jsonObject.containsKey("updateBy")) {
			model.setUpdateBy(jsonObject.getString("updateBy"));
		}
		if (jsonObject.containsKey("updateTime")) {
			model.setUpdateTime(jsonObject.getDate("updateTime"));
		}

		return model;
	}

	public static JSONObject toJsonObject(SysDataItem model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
			if ("SYS".equals(model.getType())) {
				jsonObject.put("typeName", "系统内置");
			} else {
				jsonObject.put("typeName", "用户定义");
			}
		}
		if (model.getQueryId() != null) {
			jsonObject.put("queryId", model.getQueryId());
		}
		if (model.getQuerySQL() != null) {
			jsonObject.put("querySQL", model.getQuerySQL());
		}
		if (model.getParameter() != null) {
			jsonObject.put("parameter", model.getParameter());
		}
		if (model.getTextField() != null) {
			jsonObject.put("textField", model.getTextField());
		}
		if (model.getValueField() != null) {
			jsonObject.put("valueField", model.getValueField());
		}
		if (model.getTreeIdField() != null) {
			jsonObject.put("treeIdField", model.getTreeIdField());
		}
		if (model.getTreeParentIdField() != null) {
			jsonObject.put("treeParentIdField", model.getTreeParentIdField());
		}
		if (model.getTreeTreeIdField() != null) {
			jsonObject.put("treeTreeIdField", model.getTreeTreeIdField());
		}
		if (model.getTreeNameField() != null) {
			jsonObject.put("treeNameField", model.getTreeNameField());
		}
		if (model.getTreeListNoField() != null) {
			jsonObject.put("treeListNoField", model.getTreeListNoField());
		}
		if (model.getUrl() != null) {
			jsonObject.put("url", model.getUrl());
		}
		if (model.getCacheFlag() != null) {
			jsonObject.put("cacheFlag", model.getCacheFlag());
		}
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
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getUpdateTime() != null) {
			jsonObject.put("updateTime",
					DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_date",
					DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_datetime",
					DateUtils.getDateTime(model.getUpdateTime()));
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(SysDataItem model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
			if ("SYS".equals(model.getType())) {
				jsonObject.put("typeName", "系统内置");
			} else {
				jsonObject.put("typeName", "用户定义");
			}
		}
		if (model.getQueryId() != null) {
			jsonObject.put("queryId", model.getQueryId());
		}
		if (model.getQuerySQL() != null) {
			jsonObject.put("querySQL", model.getQuerySQL());
		}
		if (model.getParameter() != null) {
			jsonObject.put("parameter", model.getParameter());
		}
		if (model.getTextField() != null) {
			jsonObject.put("textField", model.getTextField());
		}
		if (model.getValueField() != null) {
			jsonObject.put("valueField", model.getValueField());
		}
		if (model.getTreeIdField() != null) {
			jsonObject.put("treeIdField", model.getTreeIdField());
		}
		if (model.getTreeParentIdField() != null) {
			jsonObject.put("treeParentIdField", model.getTreeParentIdField());
		}
		if (model.getTreeTreeIdField() != null) {
			jsonObject.put("treeTreeIdField", model.getTreeTreeIdField());
		}
		if (model.getTreeNameField() != null) {
			jsonObject.put("treeNameField", model.getTreeNameField());
		}
		if (model.getTreeListNoField() != null) {
			jsonObject.put("treeListNoField", model.getTreeListNoField());
		}
		if (model.getUrl() != null) {
			jsonObject.put("url", model.getUrl());
		}
		if (model.getCacheFlag() != null) {
			jsonObject.put("cacheFlag", model.getCacheFlag());
		}
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
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getUpdateTime() != null) {
			jsonObject.put("updateTime",
					DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_date",
					DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_datetime",
					DateUtils.getDateTime(model.getUpdateTime()));
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<SysDataItem> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SysDataItem model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<SysDataItem> arrayToList(JSONArray array) {
		java.util.List<SysDataItem> list = new java.util.ArrayList<SysDataItem>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SysDataItem model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private SysDataItemJsonFactory() {

	}

}
