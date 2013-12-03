package com.glaf.base.modules.workspace.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.workspace.model.*;

public class MyMenuJsonFactory {

	public static java.util.List<MyMenu> arrayToList(JSONArray array) {
		java.util.List<MyMenu> list = new java.util.ArrayList<MyMenu>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			MyMenu model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static MyMenu jsonToObject(JSONObject jsonObject) {
		MyMenu model = new MyMenu();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("userId")) {
			model.setUserId(jsonObject.getLong("userId"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("url")) {
			model.setUrl(jsonObject.getString("url"));
		}
		if (jsonObject.containsKey("sort")) {
			model.setSort(jsonObject.getInteger("sort"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<MyMenu> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (MyMenu model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(MyMenu model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("userId", model.getUserId());
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getUrl() != null) {
			jsonObject.put("url", model.getUrl());
		}
		jsonObject.put("sort", model.getSort());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(MyMenu model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("userId", model.getUserId());
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getUrl() != null) {
			jsonObject.put("url", model.getUrl());
		}
		jsonObject.put("sort", model.getSort());
		return jsonObject;
	}

	private MyMenuJsonFactory() {

	}

}
