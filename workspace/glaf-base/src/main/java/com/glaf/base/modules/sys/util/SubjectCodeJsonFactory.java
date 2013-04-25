package com.glaf.base.modules.sys.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.model.*;

public class SubjectCodeJsonFactory {

	public static java.util.List<SubjectCode> arrayToList(JSONArray array) {
		java.util.List<SubjectCode> list = new java.util.ArrayList<SubjectCode>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SubjectCode model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static SubjectCode jsonToObject(JSONObject jsonObject) {
		SubjectCode model = new SubjectCode();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("parent")) {
			model.setParent(jsonObject.getLong("parent"));
		}
		if (jsonObject.containsKey("subjectCode")) {
			model.setSubjectCode(jsonObject.getString("subjectCode"));
		}
		if (jsonObject.containsKey("subjectName")) {
			model.setSubjectName(jsonObject.getString("subjectName"));
		}
		if (jsonObject.containsKey("sort")) {
			model.setSort(jsonObject.getInteger("sort"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<SubjectCode> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SubjectCode model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(SubjectCode model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("parent", model.getParent());
		if (model.getSubjectCode() != null) {
			jsonObject.put("subjectCode", model.getSubjectCode());
		}
		if (model.getSubjectName() != null) {
			jsonObject.put("subjectName", model.getSubjectName());
		}
		jsonObject.put("sort", model.getSort());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(SubjectCode model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("parent", model.getParent());
		if (model.getSubjectCode() != null) {
			jsonObject.put("subjectCode", model.getSubjectCode());
		}
		if (model.getSubjectName() != null) {
			jsonObject.put("subjectName", model.getSubjectName());
		}
		jsonObject.put("sort", model.getSort());
		return jsonObject;
	}

	private SubjectCodeJsonFactory() {

	}

}
