package com.glaf.base.modules.sys.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.model.Dictory;
import com.glaf.core.util.DateUtils;

public class DictoryJsonFactory {

	public static Dictory jsonToObject(JSONObject jsonObject) {
		Dictory model = new Dictory();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("typeId")) {
			model.setTypeId(jsonObject.getLong("typeId"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("desc")) {
			model.setDesc(jsonObject.getString("desc"));
		}
		if (jsonObject.containsKey("code")) {
			model.setCode(jsonObject.getString("code"));
		}
		if (jsonObject.containsKey("sort")) {
			model.setSort(jsonObject.getInteger("sort"));
		}
		if (jsonObject.containsKey("blocked")) {
			model.setBlocked(jsonObject.getInteger("blocked"));
		}
		if (jsonObject.containsKey("ext1")) {
			model.setExt1(jsonObject.getString("ext1"));
		}
		if (jsonObject.containsKey("ext2")) {
			model.setExt2(jsonObject.getString("ext2"));
		}
		if (jsonObject.containsKey("ext3")) {
			model.setExt3(jsonObject.getString("ext3"));
		}
		if (jsonObject.containsKey("ext4")) {
			model.setExt4(jsonObject.getString("ext4"));
		}
		if (jsonObject.containsKey("ext5")) {
			model.setExt5(jsonObject.getDate("ext5"));
		}
		if (jsonObject.containsKey("ext6")) {
			model.setExt6(jsonObject.getDate("ext6"));
		}

		return model;
	}

	public static JSONObject toJsonObject(Dictory model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("typeId", model.getTypeId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		}
		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		}
		jsonObject.put("sort", model.getSort());
		jsonObject.put("blocked", model.getBlocked());
		if (model.getExt1() != null) {
			jsonObject.put("ext1", model.getExt1());
		}
		if (model.getExt2() != null) {
			jsonObject.put("ext2", model.getExt2());
		}
		if (model.getExt3() != null) {
			jsonObject.put("ext3", model.getExt3());
		}
		if (model.getExt4() != null) {
			jsonObject.put("ext4", model.getExt4());
		}
		if (model.getExt5() != null) {
			jsonObject.put("ext5", DateUtils.getDate(model.getExt5()));
			jsonObject.put("ext5_date", DateUtils.getDate(model.getExt5()));
			jsonObject.put("ext5_datetime",
					DateUtils.getDateTime(model.getExt5()));
		}
		if (model.getExt6() != null) {
			jsonObject.put("ext6", DateUtils.getDate(model.getExt6()));
			jsonObject.put("ext6_date", DateUtils.getDate(model.getExt6()));
			jsonObject.put("ext6_datetime",
					DateUtils.getDateTime(model.getExt6()));
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(Dictory model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("typeId", model.getTypeId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		}
		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		}
		jsonObject.put("sort", model.getSort());
		jsonObject.put("blocked", model.getBlocked());
		if (model.getExt1() != null) {
			jsonObject.put("ext1", model.getExt1());
		}
		if (model.getExt2() != null) {
			jsonObject.put("ext2", model.getExt2());
		}
		if (model.getExt3() != null) {
			jsonObject.put("ext3", model.getExt3());
		}
		if (model.getExt4() != null) {
			jsonObject.put("ext4", model.getExt4());
		}
		if (model.getExt5() != null) {
			jsonObject.put("ext5", DateUtils.getDate(model.getExt5()));
			jsonObject.put("ext5_date", DateUtils.getDate(model.getExt5()));
			jsonObject.put("ext5_datetime",
					DateUtils.getDateTime(model.getExt5()));
		}
		if (model.getExt6() != null) {
			jsonObject.put("ext6", DateUtils.getDate(model.getExt6()));
			jsonObject.put("ext6_date", DateUtils.getDate(model.getExt6()));
			jsonObject.put("ext6_datetime",
					DateUtils.getDateTime(model.getExt6()));
		}
		return jsonObject;
	}

	private DictoryJsonFactory() {

	}

}
