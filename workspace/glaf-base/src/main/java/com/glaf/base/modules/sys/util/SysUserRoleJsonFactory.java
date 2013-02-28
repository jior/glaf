package com.glaf.base.modules.sys.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.model.SysUserRole;
import com.glaf.core.util.DateUtils;

public class SysUserRoleJsonFactory {

	public static SysUserRole jsonToObject(JSONObject jsonObject) {
		SysUserRole model = new SysUserRole();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("userId")) {
			model.setUserId(jsonObject.getLong("userId"));
		}
		if (jsonObject.containsKey("deptRoleId")) {
			model.setDeptRoleId(jsonObject.getLong("deptRoleId"));
		}
		if (jsonObject.containsKey("authorized")) {
			model.setAuthorized(jsonObject.getInteger("authorized"));
		}
		if (jsonObject.containsKey("authorizeFromId")) {
			model.setAuthorizeFromId(jsonObject.getLong("authorizeFromId"));
		}
		if (jsonObject.containsKey("availDateStart")) {
			model.setAvailDateStart(jsonObject.getDate("availDateStart"));
		}
		if (jsonObject.containsKey("availDateEnd")) {
			model.setAvailDateEnd(jsonObject.getDate("availDateEnd"));
		}
		if (jsonObject.containsKey("processDescription")) {
			model.setProcessDescription(jsonObject
					.getString("processDescription"));
		}

		return model;
	}

	public static JSONObject toJsonObject(SysUserRole model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("userId", model.getUserId());
		jsonObject.put("deptRoleId", model.getDeptRoleId());
		jsonObject.put("authorized", model.getAuthorized());
		jsonObject.put("authorizeFromId", model.getAuthorizeFromId());
		if (model.getAvailDateStart() != null) {
			jsonObject.put("availDateStart",
					DateUtils.getDate(model.getAvailDateStart()));
			jsonObject.put("availDateStart_date",
					DateUtils.getDate(model.getAvailDateStart()));
			jsonObject.put("availDateStart_datetime",
					DateUtils.getDateTime(model.getAvailDateStart()));
		}
		if (model.getAvailDateEnd() != null) {
			jsonObject.put("availDateEnd",
					DateUtils.getDate(model.getAvailDateEnd()));
			jsonObject.put("availDateEnd_date",
					DateUtils.getDate(model.getAvailDateEnd()));
			jsonObject.put("availDateEnd_datetime",
					DateUtils.getDateTime(model.getAvailDateEnd()));
		}
		if (model.getProcessDescription() != null) {
			jsonObject.put("processDescription", model.getProcessDescription());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(SysUserRole model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("userId", model.getUserId());
		jsonObject.put("deptRoleId", model.getDeptRoleId());
		jsonObject.put("authorized", model.getAuthorized());
		jsonObject.put("authorizeFromId", model.getAuthorizeFromId());
		if (model.getAvailDateStart() != null) {
			jsonObject.put("availDateStart",
					DateUtils.getDate(model.getAvailDateStart()));
			jsonObject.put("availDateStart_date",
					DateUtils.getDate(model.getAvailDateStart()));
			jsonObject.put("availDateStart_datetime",
					DateUtils.getDateTime(model.getAvailDateStart()));
		}
		if (model.getAvailDateEnd() != null) {
			jsonObject.put("availDateEnd",
					DateUtils.getDate(model.getAvailDateEnd()));
			jsonObject.put("availDateEnd_date",
					DateUtils.getDate(model.getAvailDateEnd()));
			jsonObject.put("availDateEnd_datetime",
					DateUtils.getDateTime(model.getAvailDateEnd()));
		}
		if (model.getProcessDescription() != null) {
			jsonObject.put("processDescription", model.getProcessDescription());
		}
		return jsonObject;
	}

	private SysUserRoleJsonFactory() {

	}

}
