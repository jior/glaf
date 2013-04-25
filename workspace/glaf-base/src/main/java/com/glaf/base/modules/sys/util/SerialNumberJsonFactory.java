package com.glaf.base.modules.sys.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.model.SerialNumber;
import com.glaf.core.util.DateUtils;

public class SerialNumberJsonFactory {

	public static java.util.List<SerialNumber> arrayToList(JSONArray array) {
		java.util.List<SerialNumber> list = new java.util.ArrayList<SerialNumber>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SerialNumber model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static SerialNumber jsonToObject(JSONObject jsonObject) {
		SerialNumber model = new SerialNumber();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("moduleNo")) {
			model.setModuleNo(jsonObject.getString("moduleNo"));
		}
		if (jsonObject.containsKey("lastDate")) {
			model.setLastDate(jsonObject.getDate("lastDate"));
		}
		if (jsonObject.containsKey("intervelNo")) {
			model.setIntervelNo(jsonObject.getInteger("intervelNo"));
		}
		if (jsonObject.containsKey("currentSerail")) {
			model.setCurrentSerail(jsonObject.getInteger("currentSerail"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<SerialNumber> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SerialNumber model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(SerialNumber model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getModuleNo() != null) {
			jsonObject.put("moduleNo", model.getModuleNo());
		}
		if (model.getLastDate() != null) {
			jsonObject.put("lastDate", DateUtils.getDate(model.getLastDate()));
			jsonObject.put("lastDate_date",
					DateUtils.getDate(model.getLastDate()));
			jsonObject.put("lastDate_datetime",
					DateUtils.getDateTime(model.getLastDate()));
		}
		jsonObject.put("intervelNo", model.getIntervelNo());
		jsonObject.put("currentSerail", model.getCurrentSerail());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(SerialNumber model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getModuleNo() != null) {
			jsonObject.put("moduleNo", model.getModuleNo());
		}
		if (model.getLastDate() != null) {
			jsonObject.put("lastDate", DateUtils.getDate(model.getLastDate()));
			jsonObject.put("lastDate_date",
					DateUtils.getDate(model.getLastDate()));
			jsonObject.put("lastDate_datetime",
					DateUtils.getDateTime(model.getLastDate()));
		}
		jsonObject.put("intervelNo", model.getIntervelNo());
		jsonObject.put("currentSerail", model.getCurrentSerail());
		return jsonObject;
	}

	private SerialNumberJsonFactory() {

	}

}
