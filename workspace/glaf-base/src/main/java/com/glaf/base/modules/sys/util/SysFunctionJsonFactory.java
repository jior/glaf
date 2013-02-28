package com.glaf.base.modules.sys.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.model.SysFunction;

public class SysFunctionJsonFactory {

	public static SysFunction jsonToObject(JSONObject jsonObject) {
		SysFunction model = new SysFunction();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("funcDesc")) {
			model.setFuncDesc(jsonObject.getString("funcDesc"));
		}
		if (jsonObject.containsKey("funcMethod")) {
			model.setFuncMethod(jsonObject.getString("funcMethod"));
		}
		if (jsonObject.containsKey("sort")) {
			model.setSort(jsonObject.getInteger("sort"));
		}
		if (jsonObject.containsKey("appId")) {
			model.setAppId(jsonObject.getLong("appId"));
		}

		return model;
	}

	public static JSONObject toJsonObject(SysFunction model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getFuncDesc() != null) {
			jsonObject.put("funcDesc", model.getFuncDesc());
		}
		if (model.getFuncMethod() != null) {
			jsonObject.put("funcMethod", model.getFuncMethod());
		}
		jsonObject.put("sort", model.getSort());
		jsonObject.put("appId", model.getAppId());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(SysFunction model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getFuncDesc() != null) {
			jsonObject.put("funcDesc", model.getFuncDesc());
		}
		if (model.getFuncMethod() != null) {
			jsonObject.put("funcMethod", model.getFuncMethod());
		}
		jsonObject.put("sort", model.getSort());
		jsonObject.put("appId", model.getAppId());
		return jsonObject;
	}

	private SysFunctionJsonFactory() {

	}

}
