package com.glaf.base.modules.sys.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.base.modules.sys.model.*;

public class BaseDataJsonFactory {

	public static java.util.List<BaseDataInfo> arrayToList(JSONArray array) {
		java.util.List<BaseDataInfo> list = new java.util.ArrayList<BaseDataInfo>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			BaseDataInfo model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static BaseDataInfo jsonToObject(JSONObject jsonObject) {
		BaseDataInfo model = new BaseDataInfo();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("nodeId")) {
			model.setNodeId(jsonObject.getLong("nodeId"));
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
		if (jsonObject.containsKey("value")) {
			model.setValue(jsonObject.getString("value"));
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
		if (jsonObject.containsKey("ext7")) {
			model.setExt7(jsonObject.getDate("ext7"));
		}
		if (jsonObject.containsKey("ext8")) {
			model.setExt8(jsonObject.getDate("ext8"));
		}
		if (jsonObject.containsKey("ext9")) {
			model.setExt9(jsonObject.getDate("ext9"));
		}
		if (jsonObject.containsKey("ext10")) {
			model.setExt10(jsonObject.getDate("ext10"));
		}
		if (jsonObject.containsKey("ext11")) {
			model.setExt11(jsonObject.getLong("ext11"));
		}
		if (jsonObject.containsKey("ext12")) {
			model.setExt12(jsonObject.getLong("ext12"));
		}
		if (jsonObject.containsKey("ext13")) {
			model.setExt13(jsonObject.getLong("ext13"));
		}
		if (jsonObject.containsKey("ext14")) {
			model.setExt14(jsonObject.getLong("ext14"));
		}
		if (jsonObject.containsKey("ext15")) {
			model.setExt15(jsonObject.getLong("ext15"));
		}
		if (jsonObject.containsKey("ext16")) {
			model.setExt16(jsonObject.getDouble("ext16"));
		}
		if (jsonObject.containsKey("ext17")) {
			model.setExt17(jsonObject.getDouble("ext17"));
		}
		if (jsonObject.containsKey("ext18")) {
			model.setExt18(jsonObject.getDouble("ext18"));
		}
		if (jsonObject.containsKey("ext19")) {
			model.setExt19(jsonObject.getDouble("ext19"));
		}
		if (jsonObject.containsKey("ext20")) {
			model.setExt20(jsonObject.getDouble("ext20"));
		}
		 

		return model;
	}

	public static JSONArray listToArray(java.util.List<BaseDataInfo> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (BaseDataInfo model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(BaseDataInfo model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("nodeId", model.getNodeId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		}
		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		}
		if (model.getValue() != null) {
			jsonObject.put("value", model.getValue());
		}
		 
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
		if (model.getExt7() != null) {
			jsonObject.put("ext7", DateUtils.getDate(model.getExt7()));
			jsonObject.put("ext7_date", DateUtils.getDate(model.getExt7()));
			jsonObject.put("ext7_datetime",
					DateUtils.getDateTime(model.getExt7()));
		}
		if (model.getExt8() != null) {
			jsonObject.put("ext8", DateUtils.getDate(model.getExt8()));
			jsonObject.put("ext8_date", DateUtils.getDate(model.getExt8()));
			jsonObject.put("ext8_datetime",
					DateUtils.getDateTime(model.getExt8()));
		}
		if (model.getExt9() != null) {
			jsonObject.put("ext9", DateUtils.getDate(model.getExt9()));
			jsonObject.put("ext9_date", DateUtils.getDate(model.getExt9()));
			jsonObject.put("ext9_datetime",
					DateUtils.getDateTime(model.getExt9()));
		}
		if (model.getExt10() != null) {
			jsonObject.put("ext10", DateUtils.getDate(model.getExt10()));
			jsonObject.put("ext10_date", DateUtils.getDate(model.getExt10()));
			jsonObject.put("ext10_datetime",
					DateUtils.getDateTime(model.getExt10()));
		}
		jsonObject.put("ext11", model.getExt11());
		jsonObject.put("ext12", model.getExt12());
		jsonObject.put("ext13", model.getExt13());
		jsonObject.put("ext14", model.getExt14());
		jsonObject.put("ext15", model.getExt15());
		jsonObject.put("ext16", model.getExt16());
		jsonObject.put("ext17", model.getExt17());
		jsonObject.put("ext18", model.getExt18());
		jsonObject.put("ext19", model.getExt19());
		jsonObject.put("ext20", model.getExt20());

		 

		return jsonObject;
	}

	public static ObjectNode toObjectNode(BaseDataInfo model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("nodeId", model.getNodeId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		}
		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		}
		if (model.getValue() != null) {
			jsonObject.put("value", model.getValue());
		}
		 
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
		if (model.getExt7() != null) {
			jsonObject.put("ext7", DateUtils.getDate(model.getExt7()));
			jsonObject.put("ext7_date", DateUtils.getDate(model.getExt7()));
			jsonObject.put("ext7_datetime",
					DateUtils.getDateTime(model.getExt7()));
		}
		if (model.getExt8() != null) {
			jsonObject.put("ext8", DateUtils.getDate(model.getExt8()));
			jsonObject.put("ext8_date", DateUtils.getDate(model.getExt8()));
			jsonObject.put("ext8_datetime",
					DateUtils.getDateTime(model.getExt8()));
		}
		if (model.getExt9() != null) {
			jsonObject.put("ext9", DateUtils.getDate(model.getExt9()));
			jsonObject.put("ext9_date", DateUtils.getDate(model.getExt9()));
			jsonObject.put("ext9_datetime",
					DateUtils.getDateTime(model.getExt9()));
		}
		if (model.getExt10() != null) {
			jsonObject.put("ext10", DateUtils.getDate(model.getExt10()));
			jsonObject.put("ext10_date", DateUtils.getDate(model.getExt10()));
			jsonObject.put("ext10_datetime",
					DateUtils.getDateTime(model.getExt10()));
		}
		jsonObject.put("ext11", model.getExt11());
		jsonObject.put("ext12", model.getExt12());
		jsonObject.put("ext13", model.getExt13());
		jsonObject.put("ext14", model.getExt14());
		jsonObject.put("ext15", model.getExt15());
		jsonObject.put("ext16", model.getExt16());
		jsonObject.put("ext17", model.getExt17());
		jsonObject.put("ext18", model.getExt18());
		jsonObject.put("ext19", model.getExt19());
		jsonObject.put("ext20", model.getExt20());

		 
		return jsonObject;
	}

	private BaseDataJsonFactory() {

	}

}
