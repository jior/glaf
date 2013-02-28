package com.glaf.base.modules.sys.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.model.SysDeptRole;

public class SysDeptRoleJsonFactory {

	public static SysDeptRole jsonToObject(JSONObject jsonObject) {
		SysDeptRole model = new SysDeptRole();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("grade")) {
			model.setGrade(jsonObject.getInteger("grade"));
		}
		if (jsonObject.containsKey("code")) {
			model.setCode(jsonObject.getString("code"));
		}
		if (jsonObject.containsKey("sort")) {
			model.setSort(jsonObject.getInteger("sort"));
		}
		if (jsonObject.containsKey("sysRoleId")) {
			model.setSysRoleId(jsonObject.getLong("sysRoleId"));
		}
		if (jsonObject.containsKey("deptId")) {
			model.setDeptId(jsonObject.getLong("deptId"));
		}

		return model;
	}

	public static JSONObject toJsonObject(SysDeptRole model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("grade", model.getGrade());
		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		}
		jsonObject.put("sort", model.getSort());
		jsonObject.put("sysRoleId", model.getSysRoleId());
		jsonObject.put("deptId", model.getDeptId());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(SysDeptRole model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("grade", model.getGrade());
		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		}
		jsonObject.put("sort", model.getSort());
		jsonObject.put("sysRoleId", model.getSysRoleId());
		jsonObject.put("deptId", model.getDeptId());
		return jsonObject;
	}

	private SysDeptRoleJsonFactory() {

	}

}
