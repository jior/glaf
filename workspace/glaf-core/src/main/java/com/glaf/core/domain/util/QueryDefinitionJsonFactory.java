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
import com.glaf.core.domain.QueryDefinition;
import com.glaf.core.util.DateUtils;

public class QueryDefinitionJsonFactory {

	public static java.util.List<QueryDefinition> arrayToList(JSONArray array) {
		java.util.List<QueryDefinition> list = new java.util.ArrayList<QueryDefinition>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			QueryDefinition model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static QueryDefinition jsonToObject(JSONObject jsonObject) {
		QueryDefinition model = new QueryDefinition();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("parentId")) {
			model.setParentId(jsonObject.getString("parentId"));
		}
		if (jsonObject.containsKey("nodeId")) {
			model.setNodeId(jsonObject.getLong("nodeId"));
		}
		if (jsonObject.containsKey("targetTableName")) {
			model.setTargetTableName(jsonObject.getString("targetTableName"));
		}
		if (jsonObject.containsKey("serviceKey")) {
			model.setServiceKey(jsonObject.getString("serviceKey"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("mapping")) {
			model.setMapping(jsonObject.getString("mapping"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("description")) {
			model.setDescription(jsonObject.getString("description"));
		}
		if (jsonObject.containsKey("dsName")) {
			model.setDsName(jsonObject.getString("dsName"));
		}

		if (jsonObject.containsKey("idField")) {
			model.setIdField(jsonObject.getString("idField"));
		}
		if (jsonObject.containsKey("statementId")) {
			model.setStatementId(jsonObject.getString("statementId"));
		}
		if (jsonObject.containsKey("countStatementId")) {
			model.setCountStatementId(jsonObject.getString("countStatementId"));
		}
		if (jsonObject.containsKey("parameterType")) {
			model.setParameterType(jsonObject.getString("parameterType"));
		}
		if (jsonObject.containsKey("resultType")) {
			model.setResultType(jsonObject.getString("resultType"));
		}
		if (jsonObject.containsKey("createTime")) {
			model.setCreateTime(jsonObject.getDate("createTime"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}
		if (jsonObject.containsKey("deleteFlag")) {
			model.setDeleteFlag(jsonObject.getInteger("deleteFlag"));
		}
		if (jsonObject.containsKey("revision")) {
			model.setRevision(jsonObject.getInteger("revision"));
		}
		if (jsonObject.containsKey("listUrl")) {
			model.setListUrl(jsonObject.getString("listUrl"));
		}
		if (jsonObject.containsKey("detailUrl")) {
			model.setDetailUrl(jsonObject.getString("detailUrl"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<QueryDefinition> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (QueryDefinition model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(QueryDefinition model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getParentId() != null) {
			jsonObject.put("parentId", model.getParentId());
		}
		if (model.getNodeId() != null) {
			jsonObject.put("nodeId", model.getNodeId());
		}
		if (model.getTargetTableName() != null) {
			jsonObject.put("targetTableName", model.getTargetTableName());
		}
		if (model.getServiceKey() != null) {
			jsonObject.put("serviceKey", model.getServiceKey());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getMapping() != null) {
			jsonObject.put("mapping", model.getMapping());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getDescription() != null) {
			jsonObject.put("description", model.getDescription());
		}
		if (model.getDsName() != null) {
			jsonObject.put("dsName", model.getDsName());
		}

		if (model.getIdField() != null) {
			jsonObject.put("idField", model.getIdField());
		}
		if (model.getStatementId() != null) {
			jsonObject.put("statementId", model.getStatementId());
		}
		if (model.getCountStatementId() != null) {
			jsonObject.put("countStatementId", model.getCountStatementId());
		}
		if (model.getParameterType() != null) {
			jsonObject.put("parameterType", model.getParameterType());
		}
		if (model.getResultType() != null) {
			jsonObject.put("resultType", model.getResultType());
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
		jsonObject.put("locked", model.getLocked());
		jsonObject.put("deleteFlag", model.getDeleteFlag());
		jsonObject.put("revision", model.getRevision());
		if (model.getListUrl() != null) {
			jsonObject.put("listUrl", model.getListUrl());
		}
		if (model.getDetailUrl() != null) {
			jsonObject.put("detailUrl", model.getDetailUrl());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(QueryDefinition model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getParentId() != null) {
			jsonObject.put("parentId", model.getParentId());
		}
		if (model.getNodeId() != null) {
			jsonObject.put("nodeId", model.getNodeId());
		}
		if (model.getTargetTableName() != null) {
			jsonObject.put("targetTableName", model.getTargetTableName());
		}
		if (model.getServiceKey() != null) {
			jsonObject.put("serviceKey", model.getServiceKey());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getMapping() != null) {
			jsonObject.put("mapping", model.getMapping());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getDescription() != null) {
			jsonObject.put("description", model.getDescription());
		}
		if (model.getDsName() != null) {
			jsonObject.put("dsName", model.getDsName());
		}

		if (model.getIdField() != null) {
			jsonObject.put("idField", model.getIdField());
		}
		if (model.getStatementId() != null) {
			jsonObject.put("statementId", model.getStatementId());
		}
		if (model.getCountStatementId() != null) {
			jsonObject.put("countStatementId", model.getCountStatementId());
		}
		if (model.getParameterType() != null) {
			jsonObject.put("parameterType", model.getParameterType());
		}
		if (model.getResultType() != null) {
			jsonObject.put("resultType", model.getResultType());
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
		jsonObject.put("locked", model.getLocked());
		jsonObject.put("deleteFlag", model.getDeleteFlag());
		jsonObject.put("revision", model.getRevision());
		if (model.getListUrl() != null) {
			jsonObject.put("listUrl", model.getListUrl());
		}
		if (model.getDetailUrl() != null) {
			jsonObject.put("detailUrl", model.getDetailUrl());
		}
		return jsonObject;
	}

	private QueryDefinitionJsonFactory() {

	}

}
