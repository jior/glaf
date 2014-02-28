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
import com.glaf.core.domain.EntityDefinition;
import com.glaf.core.util.DateUtils;

public class EntityDefinitionJsonFactory {

	public static EntityDefinition jsonToObject(JSONObject jsonObject) {
		EntityDefinition model = new EntityDefinition();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("tablename")) {
			model.setTablename(jsonObject.getString("tablename"));
		}
		if (jsonObject.containsKey("parseType")) {
			model.setParseType(jsonObject.getString("parseType"));
		}
		if (jsonObject.containsKey("primaryKey")) {
			model.setPrimaryKey(jsonObject.getString("primaryKey"));
		}
		if (jsonObject.containsKey("filePrefix")) {
			model.setFilePrefix(jsonObject.getString("filePrefix"));
		}
		if (jsonObject.containsKey("stopWord")) {
			model.setStopWord(jsonObject.getString("stopWord"));
		}
		if (jsonObject.containsKey("javaType")) {
			model.setJavaType(jsonObject.getString("javaType"));
		}
		if (jsonObject.containsKey("aggregationKeys")) {
			model.setAggregationKeys(jsonObject.getString("aggregationKeys"));
		}
		if (jsonObject.containsKey("startRow")) {
			model.setStartRow(jsonObject.getInteger("startRow"));
		}
		if (jsonObject.containsKey("insertOnly")) {
			model.setInsertOnly(jsonObject.getString("insertOnly"));
		}
		if (jsonObject.containsKey("filename")) {
			model.setFilename(jsonObject.getString("filename"));
		}
		if (jsonObject.containsKey("fileContent")) {
			model.setFileContent(jsonObject.getString("fileContent"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("updateBy")) {
			model.setUpdateBy(jsonObject.getString("updateBy"));
		}
		if (jsonObject.containsKey("updateDate")) {
			model.setUpdateDate(jsonObject.getDate("updateDate"));
		}

		return model;
	}

	public static JSONObject toJsonObject(EntityDefinition model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getTablename() != null) {
			jsonObject.put("tablename", model.getTablename());
		}
		if (model.getParseType() != null) {
			jsonObject.put("parseType", model.getParseType());
		}
		if (model.getPrimaryKey() != null) {
			jsonObject.put("primaryKey", model.getPrimaryKey());
		}
		if (model.getFilePrefix() != null) {
			jsonObject.put("filePrefix", model.getFilePrefix());
		}
		if (model.getStopWord() != null) {
			jsonObject.put("stopWord", model.getStopWord());
		}
		if (model.getJavaType() != null) {
			jsonObject.put("javaType", model.getJavaType());
		}
		if (model.getAggregationKeys() != null) {
			jsonObject.put("aggregationKeys", model.getAggregationKeys());
		}
		jsonObject.put("startRow", model.getStartRow());
		if (model.getInsertOnly() != null) {
			jsonObject.put("insertOnly", model.getInsertOnly());
		}
		if (model.getFilename() != null) {
			jsonObject.put("filename", model.getFilename());
		}
		if (model.getFileContent() != null) {
			jsonObject.put("fileContent", model.getFileContent());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime",
					DateUtils.getDateTime(model.getUpdateDate()));
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(EntityDefinition model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getTablename() != null) {
			jsonObject.put("tablename", model.getTablename());
		}
		if (model.getParseType() != null) {
			jsonObject.put("parseType", model.getParseType());
		}
		if (model.getPrimaryKey() != null) {
			jsonObject.put("primaryKey", model.getPrimaryKey());
		}
		if (model.getFilePrefix() != null) {
			jsonObject.put("filePrefix", model.getFilePrefix());
		}
		if (model.getStopWord() != null) {
			jsonObject.put("stopWord", model.getStopWord());
		}
		if (model.getJavaType() != null) {
			jsonObject.put("javaType", model.getJavaType());
		}
		if (model.getAggregationKeys() != null) {
			jsonObject.put("aggregationKeys", model.getAggregationKeys());
		}
		jsonObject.put("startRow", model.getStartRow());
		if (model.getInsertOnly() != null) {
			jsonObject.put("insertOnly", model.getInsertOnly());
		}
		if (model.getFilename() != null) {
			jsonObject.put("filename", model.getFilename());
		}
		if (model.getFileContent() != null) {
			jsonObject.put("fileContent", model.getFileContent());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime",
					DateUtils.getDateTime(model.getUpdateDate()));
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<EntityDefinition> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (EntityDefinition model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<EntityDefinition> arrayToList(JSONArray array) {
		java.util.List<EntityDefinition> list = new java.util.ArrayList<EntityDefinition>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			EntityDefinition model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private EntityDefinitionJsonFactory() {

	}

}
