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
package com.glaf.dts.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;
import com.glaf.dts.domain.*;

public class DataTransferJsonFactory {

	public static java.util.List<DataTransfer> arrayToList(JSONArray array) {
		java.util.List<DataTransfer> list = new java.util.ArrayList<DataTransfer>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DataTransfer model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static DataTransfer jsonToObject(JSONObject jsonObject) {
		DataTransfer model = new DataTransfer();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("tableName")) {
			model.setTableName(jsonObject.getString("tableName"));
		}
		if (jsonObject.containsKey("parentTableName")) {
			model.setParentTableName(jsonObject.getString("parentTableName"));
		}
		if (jsonObject.containsKey("packageName")) {
			model.setPackageName(jsonObject.getString("packageName"));
		}
		if (jsonObject.containsKey("entityName")) {
			model.setEntityName(jsonObject.getString("entityName"));
		}
		if (jsonObject.containsKey("className")) {
			model.setClassName(jsonObject.getString("className"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("englishTitle")) {
			model.setEnglishTitle(jsonObject.getString("englishTitle"));
		}
		if (jsonObject.containsKey("primaryKey")) {
			model.setPrimaryKey(jsonObject.getString("primaryKey"));
		}
		if (jsonObject.containsKey("filePrefix")) {
			model.setFilePrefix(jsonObject.getString("filePrefix"));
		}
		if (jsonObject.containsKey("parseType")) {
			model.setParseType(jsonObject.getString("parseType"));
		}
		if (jsonObject.containsKey("parseClass")) {
			model.setParseClass(jsonObject.getString("parseClass"));
		}
		if (jsonObject.containsKey("split")) {
			model.setSplit(jsonObject.getString("split"));
		}
		if (jsonObject.containsKey("batchSize")) {
			model.setBatchSize(jsonObject.getInteger("batchSize"));
		}
		if (jsonObject.containsKey("insertOnly")) {
			model.setInsertOnly(jsonObject.getString("insertOnly"));
		}
		if (jsonObject.containsKey("startRow")) {
			model.setStartRow(jsonObject.getInteger("startRow"));
		}
		if (jsonObject.containsKey("stopWord")) {
			model.setStopWord(jsonObject.getString("stopWord"));
		}
		if (jsonObject.containsKey("stopSkipRow")) {
			model.setStopSkipRow(jsonObject.getInteger("stopSkipRow"));
		}
		if (jsonObject.containsKey("aggregationKeys")) {
			model.setAggregationKeys(jsonObject.getString("aggregationKeys"));
		}
		if (jsonObject.containsKey("queryIds")) {
			model.setQueryIds(jsonObject.getString("queryIds"));
		}
		if (jsonObject.containsKey("temporaryFlag")) {
			model.setTemporaryFlag(jsonObject.getString("temporaryFlag"));
		}
		if (jsonObject.containsKey("deleteFetch")) {
			model.setDeleteFetch(jsonObject.getString("deleteFetch"));
		}
		if (jsonObject.containsKey("description")) {
			model.setDescription(jsonObject.getString("description"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("nodeId")) {
			model.setNodeId(jsonObject.getLong("nodeId"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}
		if (jsonObject.containsKey("deleteFlag")) {
			model.setDeleteFlag(jsonObject.getInteger("deleteFlag"));
		}
		if (jsonObject.containsKey("systemFlag")) {
			model.setSystemFlag(jsonObject.getString("systemFlag"));
		}
		if (jsonObject.containsKey("createTime")) {
			model.setCreateTime(jsonObject.getDate("createTime"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<DataTransfer> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DataTransfer model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(DataTransfer model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTableName() != null) {
			jsonObject.put("tableName", model.getTableName());
		}
		if (model.getParentTableName() != null) {
			jsonObject.put("parentTableName", model.getParentTableName());
		}
		if (model.getPackageName() != null) {
			jsonObject.put("packageName", model.getPackageName());
		}
		if (model.getEntityName() != null) {
			jsonObject.put("entityName", model.getEntityName());
		}
		if (model.getClassName() != null) {
			jsonObject.put("className", model.getClassName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getEnglishTitle() != null) {
			jsonObject.put("englishTitle", model.getEnglishTitle());
		}
		if (model.getPrimaryKey() != null) {
			jsonObject.put("primaryKey", model.getPrimaryKey());
		}
		if (model.getFilePrefix() != null) {
			jsonObject.put("filePrefix", model.getFilePrefix());
		}
		if (model.getParseType() != null) {
			jsonObject.put("parseType", model.getParseType());
		}
		if (model.getParseClass() != null) {
			jsonObject.put("parseClass", model.getParseClass());
		}
		if (model.getSplit() != null) {
			jsonObject.put("split", model.getSplit());
		}
		jsonObject.put("batchSize", model.getBatchSize());
		if (model.getInsertOnly() != null) {
			jsonObject.put("insertOnly", model.getInsertOnly());
		}
		jsonObject.put("startRow", model.getStartRow());
		if (model.getStopWord() != null) {
			jsonObject.put("stopWord", model.getStopWord());
		}
		jsonObject.put("stopSkipRow", model.getStopSkipRow());
		if (model.getAggregationKeys() != null) {
			jsonObject.put("aggregationKeys", model.getAggregationKeys());
		}
		if (model.getQueryIds() != null) {
			jsonObject.put("queryIds", model.getQueryIds());
		}
		if (model.getTemporaryFlag() != null) {
			jsonObject.put("temporaryFlag", model.getTemporaryFlag());
		}
		if (model.getDeleteFetch() != null) {
			jsonObject.put("deleteFetch", model.getDeleteFetch());
		}
		if (model.getDescription() != null) {
			jsonObject.put("description", model.getDescription());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		jsonObject.put("nodeId", model.getNodeId());
		jsonObject.put("locked", model.getLocked());
		jsonObject.put("deleteFlag", model.getDeleteFlag());
		if (model.getSystemFlag() != null) {
			jsonObject.put("systemFlag", model.getSystemFlag());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime",
					DateUtils.getDateTime(model.getCreateTime()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(DataTransfer model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTableName() != null) {
			jsonObject.put("tableName", model.getTableName());
		}
		if (model.getParentTableName() != null) {
			jsonObject.put("parentTableName", model.getParentTableName());
		}
		if (model.getPackageName() != null) {
			jsonObject.put("packageName", model.getPackageName());
		}
		if (model.getEntityName() != null) {
			jsonObject.put("entityName", model.getEntityName());
		}
		if (model.getClassName() != null) {
			jsonObject.put("className", model.getClassName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getEnglishTitle() != null) {
			jsonObject.put("englishTitle", model.getEnglishTitle());
		}
		if (model.getPrimaryKey() != null) {
			jsonObject.put("primaryKey", model.getPrimaryKey());
		}
		if (model.getFilePrefix() != null) {
			jsonObject.put("filePrefix", model.getFilePrefix());
		}
		if (model.getParseType() != null) {
			jsonObject.put("parseType", model.getParseType());
		}
		if (model.getParseClass() != null) {
			jsonObject.put("parseClass", model.getParseClass());
		}
		if (model.getSplit() != null) {
			jsonObject.put("split", model.getSplit());
		}
		jsonObject.put("batchSize", model.getBatchSize());
		if (model.getInsertOnly() != null) {
			jsonObject.put("insertOnly", model.getInsertOnly());
		}
		jsonObject.put("startRow", model.getStartRow());
		if (model.getStopWord() != null) {
			jsonObject.put("stopWord", model.getStopWord());
		}
		jsonObject.put("stopSkipRow", model.getStopSkipRow());
		if (model.getAggregationKeys() != null) {
			jsonObject.put("aggregationKeys", model.getAggregationKeys());
		}
		if (model.getQueryIds() != null) {
			jsonObject.put("queryIds", model.getQueryIds());
		}
		if (model.getTemporaryFlag() != null) {
			jsonObject.put("temporaryFlag", model.getTemporaryFlag());
		}
		if (model.getDeleteFetch() != null) {
			jsonObject.put("deleteFetch", model.getDeleteFetch());
		}
		if (model.getDescription() != null) {
			jsonObject.put("description", model.getDescription());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		jsonObject.put("nodeId", model.getNodeId());
		jsonObject.put("locked", model.getLocked());
		jsonObject.put("deleteFlag", model.getDeleteFlag());
		if (model.getSystemFlag() != null) {
			jsonObject.put("systemFlag", model.getSystemFlag());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime",
					DateUtils.getDateTime(model.getCreateTime()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		return jsonObject;
	}

	private DataTransferJsonFactory() {

	}

}
