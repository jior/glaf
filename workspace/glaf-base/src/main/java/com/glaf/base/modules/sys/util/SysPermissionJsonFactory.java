package com.glaf.base.modules.sys.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.model.SysPermission;

public class SysPermissionJsonFactory {

	public static SysPermission jsonToObject(JSONObject jsonObject) {
		SysPermission model = new SysPermission();
		if (jsonObject.containsKey("funcId")) {
			model.setFuncId(jsonObject.getLong("funcId"));
		}
		if (jsonObject.containsKey("roleId")) {
			model.setRoleId(jsonObject.getLong("roleId"));
		}

		return model;
	}

	public static JSONObject toJsonObject(SysPermission model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("funcId", model.getFuncId());
		jsonObject.put("_funcId_", model.getFuncId());
		jsonObject.put("roleId", model.getRoleId());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(SysPermission model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("funcId", model.getFuncId());
		jsonObject.put("_funcId_", model.getFuncId());
		jsonObject.put("roleId", model.getRoleId());
		return jsonObject;
	}

	private SysPermissionJsonFactory() {

	}

}
