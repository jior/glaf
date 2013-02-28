package com.glaf.base.modules.sys.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.model.SysAccess;

public class SysAccessJsonFactory {

	public static SysAccess jsonToObject(JSONObject jsonObject) {
		SysAccess model = new SysAccess();
		if (jsonObject.containsKey("appId")) {
			model.setAppId(jsonObject.getLong("appId"));
		}
		if (jsonObject.containsKey("roleId")) {
			model.setRoleId(jsonObject.getLong("roleId"));
		}

		return model;
	}

	public static JSONObject toJsonObject(SysAccess model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("appId", model.getAppId());
		jsonObject.put("_appId_", model.getAppId());
		jsonObject.put("roleId", model.getRoleId());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(SysAccess model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("appId", model.getAppId());
		jsonObject.put("_appId_", model.getAppId());
		jsonObject.put("roleId", model.getRoleId());
		return jsonObject;
	}

	private SysAccessJsonFactory() {

	}

}
