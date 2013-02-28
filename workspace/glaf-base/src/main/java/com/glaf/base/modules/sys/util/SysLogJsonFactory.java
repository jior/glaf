package com.glaf.base.modules.sys.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.model.SysLog;
import com.glaf.core.util.DateUtils;

public class SysLogJsonFactory {

	public static SysLog jsonToObject(JSONObject jsonObject) {
		SysLog model = new SysLog();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("account")) {
			model.setAccount(jsonObject.getString("account"));
		}
		if (jsonObject.containsKey("ip")) {
			model.setIp(jsonObject.getString("ip"));
		}
		if (jsonObject.containsKey("createTime")) {
			model.setCreateTime(jsonObject.getDate("createTime"));
		}
		if (jsonObject.containsKey("operate")) {
			model.setOperate(jsonObject.getString("operate"));
		}
		if (jsonObject.containsKey("flag")) {
			model.setFlag(jsonObject.getInteger("flag"));
		}

		return model;
	}

	public static JSONObject toJsonObject(SysLog model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getAccount() != null) {
			jsonObject.put("account", model.getAccount());
		}
		if (model.getIp() != null) {
			jsonObject.put("ip", model.getIp());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime",
					DateUtils.getDateTime(model.getCreateTime()));
		}
		if (model.getOperate() != null) {
			jsonObject.put("operate", model.getOperate());
		}
		jsonObject.put("flag", model.getFlag());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(SysLog model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getAccount() != null) {
			jsonObject.put("account", model.getAccount());
		}
		if (model.getIp() != null) {
			jsonObject.put("ip", model.getIp());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime",
					DateUtils.getDateTime(model.getCreateTime()));
		}
		if (model.getOperate() != null) {
			jsonObject.put("operate", model.getOperate());
		}
		jsonObject.put("flag", model.getFlag());
		return jsonObject;
	}

	private SysLogJsonFactory() {

	}

}
