package com.glaf.base.district.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.district.domain.DistrictEntity;

public class DistrictJsonFactory {

	public static ObjectNode toObjectNode(DistrictEntity bean) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", bean.getId());
		jsonObject.put("parentId", bean.getParentId());

		if (bean.getName() != null) {
			jsonObject.put("name", bean.getName());
		}
		if (bean.getCode() != null) {
			jsonObject.put("code", bean.getCode());
		}
		if (bean.getTreeId() != null) {
			jsonObject.put("treeId", bean.getTreeId());
		}
		jsonObject.put("level", bean.getLevel());
		if (bean.getUseType() != null) {
			jsonObject.put("useType", bean.getUseType());
		}
		jsonObject.put("sortNo", bean.getSortNo());
		jsonObject.put("locked", bean.getLocked());

		return jsonObject;
	}

	public static JSONObject toJsonObject(DistrictEntity bean) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", bean.getId());
		jsonObject.put("parentId", bean.getParentId());

		if (bean.getName() != null) {
			jsonObject.put("name", bean.getName());
		}
		if (bean.getCode() != null) {
			jsonObject.put("code", bean.getCode());
		}
		if (bean.getTreeId() != null) {
			jsonObject.put("treeId", bean.getTreeId());
		}
		jsonObject.put("level", bean.getLevel());
		if (bean.getUseType() != null) {
			jsonObject.put("useType", bean.getUseType());
		}

		jsonObject.put("sortNo", bean.getSortNo());
		jsonObject.put("locked", bean.getLocked());
		return jsonObject;
	}

	public static DistrictEntity jsonToObject(JSONObject jsonObject) {
		DistrictEntity model = new DistrictEntity();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("parentId")) {
			model.setParentId(jsonObject.getLong("parentId"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("code")) {
			model.setCode(jsonObject.getString("code"));
		}
		if (jsonObject.containsKey("treeId")) {
			model.setTreeId(jsonObject.getString("treeId"));
		}
		if (jsonObject.containsKey("level")) {
			model.setLevel(jsonObject.getInteger("level"));
		}
		if (jsonObject.containsKey("useType")) {
			model.setUseType(jsonObject.getString("useType"));
		}
		if (jsonObject.containsKey("sortNo")) {
			model.setSortNo(jsonObject.getInteger("sortNo"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}
		return model;
	}

}
