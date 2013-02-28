package com.glaf.base.modules.sys.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.model.SysTree;

public class SysTreeJsonFactory {

	public static SysTree jsonToObject(JSONObject jsonObject) {
		SysTree model = new SysTree();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("parent")) {
			model.setParent(jsonObject.getLong("parent"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("desc")) {
			model.setDesc(jsonObject.getString("desc"));
		}
		if (jsonObject.containsKey("sort")) {
			model.setSort(jsonObject.getInteger("sort"));
		}
		if (jsonObject.containsKey("code")) {
			model.setCode(jsonObject.getString("code"));
		}

		return model;
	}

	public static JSONObject toJsonObject(SysTree model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("parent", model.getParent());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		}
		jsonObject.put("sort", model.getSort());
		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(SysTree model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("parent", model.getParent());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		}
		jsonObject.put("sort", model.getSort());
		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		}
		return jsonObject;
	}

	private SysTreeJsonFactory() {

	}

}
