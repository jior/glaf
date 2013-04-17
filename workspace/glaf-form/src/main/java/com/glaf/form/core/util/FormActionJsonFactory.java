package com.glaf.form.core.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;
import com.glaf.form.core.domain.FormAction;

public class FormActionJsonFactory {

	public static FormAction jsonToObject(JSONObject jsonObject) {
		FormAction model = new FormAction();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("url")) {
			model.setUrl(jsonObject.getString("url"));
		}
		if (jsonObject.containsKey("provider")) {
			model.setProvider(jsonObject.getString("provider"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("description")) {
			model.setDescription(jsonObject.getString("description"));
		}
		if (jsonObject.containsKey("activity")) {
			model.setActivity(jsonObject.getString("activity"));
		}
		if (jsonObject.containsKey("formName")) {
			model.setFormName(jsonObject.getString("formName"));
		}
		if (jsonObject.containsKey("appId")) {
			model.setAppId(jsonObject.getString("appId"));
		}

		return model;
	}

	public static JSONObject toJsonObject(FormAction model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getUrl() != null) {
			jsonObject.put("url", model.getUrl());
		}
		if (model.getProvider() != null) {
			jsonObject.put("provider", model.getProvider());
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getDescription() != null) {
			jsonObject.put("description", model.getDescription());
		}
		if (model.getActivity() != null) {
			jsonObject.put("activity", model.getActivity());
		}
		if (model.getFormName() != null) {
			jsonObject.put("formName", model.getFormName());
		}
		if (model.getAppId() != null) {
			jsonObject.put("appId", model.getAppId());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(FormAction model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getUrl() != null) {
			jsonObject.put("url", model.getUrl());
		}
		if (model.getProvider() != null) {
			jsonObject.put("provider", model.getProvider());
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getDescription() != null) {
			jsonObject.put("description", model.getDescription());
		}
		if (model.getActivity() != null) {
			jsonObject.put("activity", model.getActivity());
		}
		if (model.getFormName() != null) {
			jsonObject.put("formName", model.getFormName());
		}
		if (model.getAppId() != null) {
			jsonObject.put("appId", model.getAppId());
		}
		return jsonObject;
	}

	private FormActionJsonFactory() {

	}

}
