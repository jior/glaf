package com.glaf.form.core.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.form.core.domain.FormLink;

public class FormLinkJsonFactory {

	public static FormLink jsonToObject(JSONObject jsonObject) {
		FormLink model = new FormLink();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("appId")) {
			model.setAppId(jsonObject.getString("appId"));
		}
		if (jsonObject.containsKey("childAppId")) {
			model.setChildAppId(jsonObject.getString("childAppId"));
		}
		if (jsonObject.containsKey("objectId")) {
			model.setObjectId(jsonObject.getString("objectId"));
		}
		if (jsonObject.containsKey("objectValue")) {
			model.setObjectValue(jsonObject.getString("objectValue"));
		}
		if (jsonObject.containsKey("oneToMany")) {
			model.setOneToMany(jsonObject.getInteger("oneToMany"));
		}
		if (jsonObject.containsKey("orderBy")) {
			model.setOrderBy(jsonObject.getString("orderBy"));
		}
		if (jsonObject.containsKey("sortNo")) {
			model.setSortNo(jsonObject.getInteger("sortNo"));
		}

		return model;
	}

	public static JSONObject toJsonObject(FormLink model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getAppId() != null) {
			jsonObject.put("appId", model.getAppId());
		}
		if (model.getChildAppId() != null) {
			jsonObject.put("childAppId", model.getChildAppId());
		}
		if (model.getObjectId() != null) {
			jsonObject.put("objectId", model.getObjectId());
		}
		if (model.getObjectValue() != null) {
			jsonObject.put("objectValue", model.getObjectValue());
		}
		jsonObject.put("oneToMany", model.getOneToMany());
		if (model.getOrderBy() != null) {
			jsonObject.put("orderBy", model.getOrderBy());
		}
		jsonObject.put("sortNo", model.getSortNo());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(FormLink model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getAppId() != null) {
			jsonObject.put("appId", model.getAppId());
		}
		if (model.getChildAppId() != null) {
			jsonObject.put("childAppId", model.getChildAppId());
		}
		if (model.getObjectId() != null) {
			jsonObject.put("objectId", model.getObjectId());
		}
		if (model.getObjectValue() != null) {
			jsonObject.put("objectValue", model.getObjectValue());
		}
		jsonObject.put("oneToMany", model.getOneToMany());
		if (model.getOrderBy() != null) {
			jsonObject.put("orderBy", model.getOrderBy());
		}
		jsonObject.put("sortNo", model.getSortNo());
		return jsonObject;
	}

	private FormLinkJsonFactory() {

	}

}
