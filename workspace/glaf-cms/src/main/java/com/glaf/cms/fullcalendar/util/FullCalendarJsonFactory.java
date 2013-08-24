package com.glaf.cms.fullcalendar.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;
import com.glaf.cms.fullcalendar.model.*;

public class FullCalendarJsonFactory {

	public static java.util.List<FullCalendar> arrayToList(JSONArray array) {
		java.util.List<FullCalendar> list = new java.util.ArrayList<FullCalendar>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FullCalendar model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static FullCalendar jsonToObject(JSONObject jsonObject) {
		FullCalendar model = new FullCalendar();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("address")) {
			model.setAddress(jsonObject.getString("address"));
		}
		if (jsonObject.containsKey("remark")) {
			model.setRemark(jsonObject.getString("remark"));
		}
		if (jsonObject.containsKey("shareFlag")) {
			model.setShareFlag(jsonObject.getInteger("shareFlag"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
		}
		if (jsonObject.containsKey("dateStart")) {
			model.setDateStart(jsonObject.getDate("dateStart"));
		}
		if (jsonObject.containsKey("dateEnd")) {
			model.setDateEnd(jsonObject.getDate("dateEnd"));
		}
		if (jsonObject.containsKey("ext1")) {
			model.setExt1(jsonObject.getString("ext1"));
		}
		if (jsonObject.containsKey("ext2")) {
			model.setExt2(jsonObject.getString("ext2"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("updateDate")) {
			model.setUpdateDate(jsonObject.getDate("updateDate"));
		}
		if (jsonObject.containsKey("updateBy")) {
			model.setUpdateBy(jsonObject.getString("updateBy"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<FullCalendar> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FullCalendar model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(FullCalendar model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getAddress() != null) {
			jsonObject.put("address", model.getAddress());
		}
		if (model.getRemark() != null) {
			jsonObject.put("remark", model.getRemark());
		}
		jsonObject.put("shareFlag", model.getShareFlag());
		jsonObject.put("status", model.getStatus());
		if (model.getDateStart() != null) {
			jsonObject
					.put("dateStart", DateUtils.getDate(model.getDateStart()));
			jsonObject.put("start", DateUtils.getDate(model.getDateStart()));
			jsonObject.put("dateStart_date",
					DateUtils.getDate(model.getDateStart()));
			jsonObject.put("dateStart_datetime",
					DateUtils.getDateTime(model.getDateStart()));
		}
		if (model.getDateEnd() != null) {
			jsonObject.put("dateEnd", DateUtils.getDate(model.getDateEnd()));
			jsonObject.put("end", DateUtils.getDate(model.getDateEnd()));
			jsonObject.put("dateEnd_date",
					DateUtils.getDate(model.getDateEnd()));
			jsonObject.put("dateEnd_datetime",
					DateUtils.getDateTime(model.getDateEnd()));
		}
		if (model.getExt1() != null) {
			jsonObject.put("ext1", model.getExt1());
		}
		if (model.getExt2() != null) {
			jsonObject.put("ext2", model.getExt2());
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateByName() != null) {
			jsonObject.put("createByName", model.getCreateByName());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime",
					DateUtils.getDateTime(model.getUpdateDate()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(FullCalendar model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getAddress() != null) {
			jsonObject.put("address", model.getAddress());
		}
		if (model.getRemark() != null) {
			jsonObject.put("remark", model.getRemark());
		}
		jsonObject.put("shareFlag", model.getShareFlag());
		jsonObject.put("status", model.getStatus());
		if (model.getDateStart() != null) {
			jsonObject
					.put("dateStart", DateUtils.getDate(model.getDateStart()));
			jsonObject.put("dateStart_date",
					DateUtils.getDate(model.getDateStart()));
			jsonObject.put("dateStart_datetime",
					DateUtils.getDateTime(model.getDateStart()));
		}
		if (model.getDateEnd() != null) {
			jsonObject.put("dateEnd", DateUtils.getDate(model.getDateEnd()));
			jsonObject.put("dateEnd_date",
					DateUtils.getDate(model.getDateEnd()));
			jsonObject.put("dateEnd_datetime",
					DateUtils.getDateTime(model.getDateEnd()));
		}
		if (model.getExt1() != null) {
			jsonObject.put("ext1", model.getExt1());
		}
		if (model.getExt2() != null) {
			jsonObject.put("ext2", model.getExt2());
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
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime",
					DateUtils.getDateTime(model.getUpdateDate()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		return jsonObject;
	}

	private FullCalendarJsonFactory() {

	}

}
