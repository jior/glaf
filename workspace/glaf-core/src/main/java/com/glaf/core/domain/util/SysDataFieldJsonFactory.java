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

public class SysDataFieldJsonFactory {

	public static java.util.List<SysDataField> arrayToList(JSONArray array) {
		java.util.List<SysDataField> list = new java.util.ArrayList<SysDataField>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SysDataField model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static SysDataField jsonToObject(JSONObject jsonObject) {
		SysDataField model = new SysDataField();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("serviceKey")) {
			model.setServiceKey(jsonObject.getString("serviceKey"));
		}
		if (jsonObject.containsKey("tablename")) {
			model.setTablename(jsonObject.getString("tablename"));
		}
		if (jsonObject.containsKey("columnName")) {
			model.setColumnName(jsonObject.getString("columnName"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("frmType")) {
			model.setFrmType(jsonObject.getString("frmType"));
		}
		if (jsonObject.containsKey("dataType")) {
			model.setDataType(jsonObject.getString("dataType"));
		}
		if (jsonObject.containsKey("length")) {
			model.setLength(jsonObject.getInteger("length"));
		}
		if (jsonObject.containsKey("listWeigth")) {
			model.setListWeigth(jsonObject.getInteger("listWeigth"));
		}
		if (jsonObject.containsKey("primaryKey")) {
			model.setPrimaryKey(jsonObject.getString("primaryKey"));
		}
		if (jsonObject.containsKey("systemFlag")) {
			model.setSystemFlag(jsonObject.getString("systemFlag"));
		}
		if (jsonObject.containsKey("inputType")) {
			model.setInputType(jsonObject.getString("inputType"));
		}
		if (jsonObject.containsKey("displayType")) {
			model.setDisplayType(jsonObject.getInteger("displayType"));
		}
		if (jsonObject.containsKey("importType")) {
			model.setImportType(jsonObject.getInteger("importType"));
		}
		if (jsonObject.containsKey("formatter")) {
			model.setFormatter(jsonObject.getString("formatter"));
		}
		if (jsonObject.containsKey("searchable")) {
			model.setSearchable(jsonObject.getString("searchable"));
		}
		if (jsonObject.containsKey("editable")) {
			model.setEditable(jsonObject.getString("editable"));
		}
		if (jsonObject.containsKey("updatable")) {
			model.setUpdatable(jsonObject.getString("updatable"));
		}
		if (jsonObject.containsKey("formula")) {
			model.setFormula(jsonObject.getString("formula"));
		}
		if (jsonObject.containsKey("mask")) {
			model.setMask(jsonObject.getString("mask"));
		}
		if (jsonObject.containsKey("queryId")) {
			model.setQueryId(jsonObject.getString("queryId"));
		}
		if (jsonObject.containsKey("valueField")) {
			model.setValueField(jsonObject.getString("valueField"));
		}
		if (jsonObject.containsKey("textField")) {
			model.setTextField(jsonObject.getString("textField"));
		}
		if (jsonObject.containsKey("validType")) {
			model.setValidType(jsonObject.getString("validType"));
		}
		if (jsonObject.containsKey("required")) {
			model.setRequired(jsonObject.getString("required"));
		}
		if (jsonObject.containsKey("initValue")) {
			model.setInitValue(jsonObject.getString("initValue"));
		}
		if (jsonObject.containsKey("defaultValue")) {
			model.setDefaultValue(jsonObject.getString("defaultValue"));
		}
		if (jsonObject.containsKey("valueExpression")) {
			model.setValueExpression(jsonObject.getString("valueExpression"));
		}
		if (jsonObject.containsKey("sortable")) {
			model.setSortable(jsonObject.getString("sortable"));
		}
		if (jsonObject.containsKey("ordinal")) {
			model.setOrdinal(jsonObject.getInteger("ordinal"));
		}
		if (jsonObject.containsKey("createTime")) {
			model.setCreateTime(jsonObject.getDate("createTime"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<SysDataField> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SysDataField model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(SysDataField model) {
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
		if (model.getColumnName() != null) {
			jsonObject.put("columnName", model.getColumnName());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getFrmType() != null) {
			jsonObject.put("frmType", model.getFrmType());
		}
		if (model.getDataType() != null) {
			jsonObject.put("dataType", model.getDataType());
		}
		jsonObject.put("length", model.getLength());
		jsonObject.put("listWeigth", model.getListWeigth());
		if (model.getPrimaryKey() != null) {
			jsonObject.put("primaryKey", model.getPrimaryKey());
		}
		if (model.getSystemFlag() != null) {
			jsonObject.put("systemFlag", model.getSystemFlag());
		}
		if (model.getInputType() != null) {
			jsonObject.put("inputType", model.getInputType());
		}
		jsonObject.put("displayType", model.getDisplayType());
		jsonObject.put("importType", model.getImportType());
		if (model.getFormatter() != null) {
			jsonObject.put("formatter", model.getFormatter());
		}
		if (model.getSearchable() != null) {
			jsonObject.put("searchable", model.getSearchable());
		}
		if (model.getEditable() != null) {
			jsonObject.put("editable", model.getEditable());
		}
		if (model.getUpdatable() != null) {
			jsonObject.put("updatable", model.getUpdatable());
		}
		if (model.getFormula() != null) {
			jsonObject.put("formula", model.getFormula());
		}
		if (model.getMask() != null) {
			jsonObject.put("mask", model.getMask());
		}
		if (model.getQueryId() != null) {
			jsonObject.put("queryId", model.getQueryId());
		}
		if (model.getValueField() != null) {
			jsonObject.put("valueField", model.getValueField());
		}
		if (model.getTextField() != null) {
			jsonObject.put("textField", model.getTextField());
		}
		if (model.getValidType() != null) {
			jsonObject.put("validType", model.getValidType());
		}
		if (model.getRequired() != null) {
			jsonObject.put("required", model.getRequired());
		}
		if (model.getInitValue() != null) {
			jsonObject.put("initValue", model.getInitValue());
		}
		if (model.getDefaultValue() != null) {
			jsonObject.put("defaultValue", model.getDefaultValue());
		}
		if (model.getValueExpression() != null) {
			jsonObject.put("valueExpression", model.getValueExpression());
		}
		if (model.getSortable() != null) {
			jsonObject.put("sortable", model.getSortable());
		}
		jsonObject.put("ordinal", model.getOrdinal());
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

	public static ObjectNode toObjectNode(SysDataField model) {
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
		if (model.getColumnName() != null) {
			jsonObject.put("columnName", model.getColumnName());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getFrmType() != null) {
			jsonObject.put("frmType", model.getFrmType());
		}
		if (model.getDataType() != null) {
			jsonObject.put("dataType", model.getDataType());
		}
		jsonObject.put("length", model.getLength());
		jsonObject.put("listWeigth", model.getListWeigth());
		if (model.getPrimaryKey() != null) {
			jsonObject.put("primaryKey", model.getPrimaryKey());
		}
		if (model.getSystemFlag() != null) {
			jsonObject.put("systemFlag", model.getSystemFlag());
		}
		if (model.getInputType() != null) {
			jsonObject.put("inputType", model.getInputType());
		}
		jsonObject.put("displayType", model.getDisplayType());
		jsonObject.put("importType", model.getImportType());
		if (model.getFormatter() != null) {
			jsonObject.put("formatter", model.getFormatter());
		}
		if (model.getSearchable() != null) {
			jsonObject.put("searchable", model.getSearchable());
		}
		if (model.getEditable() != null) {
			jsonObject.put("editable", model.getEditable());
		}
		if (model.getUpdatable() != null) {
			jsonObject.put("updatable", model.getUpdatable());
		}
		if (model.getFormula() != null) {
			jsonObject.put("formula", model.getFormula());
		}
		if (model.getMask() != null) {
			jsonObject.put("mask", model.getMask());
		}
		if (model.getQueryId() != null) {
			jsonObject.put("queryId", model.getQueryId());
		}
		if (model.getValueField() != null) {
			jsonObject.put("valueField", model.getValueField());
		}
		if (model.getTextField() != null) {
			jsonObject.put("textField", model.getTextField());
		}
		if (model.getValidType() != null) {
			jsonObject.put("validType", model.getValidType());
		}
		if (model.getRequired() != null) {
			jsonObject.put("required", model.getRequired());
		}
		if (model.getInitValue() != null) {
			jsonObject.put("initValue", model.getInitValue());
		}
		if (model.getDefaultValue() != null) {
			jsonObject.put("defaultValue", model.getDefaultValue());
		}
		if (model.getValueExpression() != null) {
			jsonObject.put("valueExpression", model.getValueExpression());
		}
		if (model.getSortable() != null) {
			jsonObject.put("sortable", model.getSortable());
		}
		jsonObject.put("ordinal", model.getOrdinal());
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

	private SysDataFieldJsonFactory() {

	}

}
