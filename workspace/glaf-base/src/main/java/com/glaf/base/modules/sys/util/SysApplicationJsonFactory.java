package com.glaf.base.modules.sys.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.model.SysApplication;

public class SysApplicationJsonFactory {

	public static SysApplication jsonToObject(JSONObject jsonObject) {
		SysApplication model = new SysApplication();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("desc")) {
			model.setDesc(jsonObject.getString("desc"));
		}
		if (jsonObject.containsKey("url")) {
			model.setUrl(jsonObject.getString("url"));
		}
		if (jsonObject.containsKey("sort")) {
			model.setSort(jsonObject.getInteger("sort"));
		}
		if (jsonObject.containsKey("showMenu")) {
			model.setShowMenu(jsonObject.getInteger("showMenu"));
		}
		if (jsonObject.containsKey("nodeId")) {
			model.setNodeId(jsonObject.getLong("nodeId"));
		}

		return model;
	}

	public static JSONObject toJsonObject(SysApplication model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		}
		if (model.getUrl() != null) {
			jsonObject.put("url", model.getUrl());
		}
		jsonObject.put("sort", model.getSort());
		jsonObject.put("showMenu", model.getShowMenu());
		jsonObject.put("nodeId", model.getNodeId());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(SysApplication model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		}
		if (model.getUrl() != null) {
			jsonObject.put("url", model.getUrl());
		}
		jsonObject.put("sort", model.getSort());
		jsonObject.put("showMenu", model.getShowMenu());
		jsonObject.put("nodeId", model.getNodeId());
		return jsonObject;
	}

	private SysApplicationJsonFactory() {

	}

}
