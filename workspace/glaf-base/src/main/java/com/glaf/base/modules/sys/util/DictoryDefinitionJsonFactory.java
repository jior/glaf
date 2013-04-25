package com.glaf.base.modules.sys.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.model.*;

public class DictoryDefinitionJsonFactory {

	public static java.util.List<DictoryDefinition> arrayToList(JSONArray array) {
		java.util.List<DictoryDefinition> list = new java.util.ArrayList<DictoryDefinition>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DictoryDefinition model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static DictoryDefinition jsonToObject(JSONObject jsonObject) {
		DictoryDefinition model = new DictoryDefinition();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("nodeId")) {
			model.setNodeId(jsonObject.getLong("nodeId"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("columnName")) {
			model.setColumnName(jsonObject.getString("columnName"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("length")) {
			model.setLength(jsonObject.getInteger("length"));
		}
		if (jsonObject.containsKey("sort")) {
			model.setSort(jsonObject.getInteger("sort"));
		}
		if (jsonObject.containsKey("required")) {
			model.setRequired(jsonObject.getInteger("required"));
		}
		if (jsonObject.containsKey("target")) {
			model.setTarget(jsonObject.getString("target"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<DictoryDefinition> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DictoryDefinition model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(DictoryDefinition model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("nodeId", model.getNodeId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getColumnName() != null) {
			jsonObject.put("columnName", model.getColumnName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		jsonObject.put("length", model.getLength());
		jsonObject.put("sort", model.getSort());
		jsonObject.put("required", model.getRequired());
		if (model.getTarget() != null) {
			jsonObject.put("target", model.getTarget());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(DictoryDefinition model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("nodeId", model.getNodeId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getColumnName() != null) {
			jsonObject.put("columnName", model.getColumnName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		jsonObject.put("length", model.getLength());
		jsonObject.put("sort", model.getSort());
		jsonObject.put("required", model.getRequired());
		if (model.getTarget() != null) {
			jsonObject.put("target", model.getTarget());
		}
		return jsonObject;
	}

	private DictoryDefinitionJsonFactory() {

	}

}
