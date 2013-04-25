package com.glaf.apps.trip.util;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.apps.trip.model.*;

public class TripJsonFactory {

	public static JSONArray listToArray(List<Trip> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (Trip trip : list) {
				JSONObject jsonObject = trip.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static List<Trip> arrayToList(JSONArray array) {
		List<Trip> list = new ArrayList<Trip>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Trip model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static Trip jsonToObject(JSONObject jsonObject) {
		Trip model = new Trip();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("transType")) {
			model.setTransType(jsonObject.getString("transType"));
		}
		if (jsonObject.containsKey("applyDate")) {
			model.setApplyDate(jsonObject.getDate("applyDate"));
		}
		if (jsonObject.containsKey("startDate")) {
			model.setStartDate(jsonObject.getDate("startDate"));
		}
		if (jsonObject.containsKey("endDate")) {
			model.setEndDate(jsonObject.getDate("endDate"));
		}
		if (jsonObject.containsKey("days")) {
			model.setDays(jsonObject.getDouble("days"));
		}
		if (jsonObject.containsKey("money")) {
			model.setMoney(jsonObject.getDouble("money"));
		}
		if (jsonObject.containsKey("cause")) {
			model.setCause(jsonObject.getString("cause"));
		}
		if (jsonObject.containsKey("deleteFlag")) {
			model.setDeleteFlag(jsonObject.getInteger("deleteFlag"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createByName")) {
			model.setCreateByName(jsonObject.getString("createByName"));
		}
		if (jsonObject.containsKey("updateDate")) {
			model.setUpdateDate(jsonObject.getDate("updateDate"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
		}
		if (jsonObject.containsKey("processName")) {
			model.setProcessName(jsonObject.getString("processName"));
		}
		if (jsonObject.containsKey("processInstanceId")) {
			model.setProcessInstanceId(jsonObject.getLong("processInstanceId"));
		}
		if (jsonObject.containsKey("wfStatus")) {
			model.setWfStatus(jsonObject.getInteger("wfStatus"));
		}
		if (jsonObject.containsKey("wfStartDate")) {
			model.setWfStartDate(jsonObject.getDate("wfStartDate"));
		}
		if (jsonObject.containsKey("wfEndDate")) {
			model.setWfEndDate(jsonObject.getDate("wfEndDate"));
		}

		return model;
	}

	public static JSONObject toJsonObject(Trip model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTransType() != null) {
			jsonObject.put("transType", model.getTransType());
		}
		if (model.getApplyDate() != null) {
			jsonObject
					.put("applyDate", DateUtils.getDate(model.getApplyDate()));
			jsonObject.put("applyDate_date",
					DateUtils.getDate(model.getApplyDate()));
			jsonObject.put("applyDate_datetime",
					DateUtils.getDateTime(model.getApplyDate()));
		}
		if (model.getStartDate() != null) {
			jsonObject
					.put("startDate", DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_date",
					DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_datetime",
					DateUtils.getDateTime(model.getStartDate()));
		}
		if (model.getEndDate() != null) {
			jsonObject.put("endDate", DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_date",
					DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_datetime",
					DateUtils.getDateTime(model.getEndDate()));
		}
		jsonObject.put("days", model.getDays());
		jsonObject.put("money", model.getMoney());
		if (model.getCause() != null) {
			jsonObject.put("cause", model.getCause());
		}
		jsonObject.put("deleteFlag", model.getDeleteFlag());
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateByName() != null) {
			jsonObject.put("createByName", model.getCreateByName());
		}
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime",
					DateUtils.getDateTime(model.getUpdateDate()));
		}
		jsonObject.put("locked", model.getLocked());
		jsonObject.put("status", model.getStatus());
		if (model.getProcessName() != null) {
			jsonObject.put("processName", model.getProcessName());
		}
		jsonObject.put("processInstanceId", model.getProcessInstanceId());
		jsonObject.put("wfStatus", model.getWfStatus());
		if (model.getWfStartDate() != null) {
			jsonObject.put("wfStartDate",
					DateUtils.getDate(model.getWfStartDate()));
			jsonObject.put("wfStartDate_date",
					DateUtils.getDate(model.getWfStartDate()));
			jsonObject.put("wfStartDate_datetime",
					DateUtils.getDateTime(model.getWfStartDate()));
		}
		if (model.getWfEndDate() != null) {
			jsonObject
					.put("wfEndDate", DateUtils.getDate(model.getWfEndDate()));
			jsonObject.put("wfEndDate_date",
					DateUtils.getDate(model.getWfEndDate()));
			jsonObject.put("wfEndDate_datetime",
					DateUtils.getDateTime(model.getWfEndDate()));
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(Trip model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTransType() != null) {
			jsonObject.put("transType", model.getTransType());
		}
		if (model.getApplyDate() != null) {
			jsonObject
					.put("applyDate", DateUtils.getDate(model.getApplyDate()));
			jsonObject.put("applyDate_date",
					DateUtils.getDate(model.getApplyDate()));
			jsonObject.put("applyDate_datetime",
					DateUtils.getDateTime(model.getApplyDate()));
		}
		if (model.getStartDate() != null) {
			jsonObject
					.put("startDate", DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_date",
					DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_datetime",
					DateUtils.getDateTime(model.getStartDate()));
		}
		if (model.getEndDate() != null) {
			jsonObject.put("endDate", DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_date",
					DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_datetime",
					DateUtils.getDateTime(model.getEndDate()));
		}
		jsonObject.put("days", model.getDays());
		jsonObject.put("money", model.getMoney());
		if (model.getCause() != null) {
			jsonObject.put("cause", model.getCause());
		}
		jsonObject.put("deleteFlag", model.getDeleteFlag());
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateByName() != null) {
			jsonObject.put("createByName", model.getCreateByName());
		}
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime",
					DateUtils.getDateTime(model.getUpdateDate()));
		}
		jsonObject.put("locked", model.getLocked());
		jsonObject.put("status", model.getStatus());
		if (model.getProcessName() != null) {
			jsonObject.put("processName", model.getProcessName());
		}
		jsonObject.put("processInstanceId", model.getProcessInstanceId());
		jsonObject.put("wfStatus", model.getWfStatus());
		if (model.getWfStartDate() != null) {
			jsonObject.put("wfStartDate",
					DateUtils.getDate(model.getWfStartDate()));
			jsonObject.put("wfStartDate_date",
					DateUtils.getDate(model.getWfStartDate()));
			jsonObject.put("wfStartDate_datetime",
					DateUtils.getDateTime(model.getWfStartDate()));
		}
		if (model.getWfEndDate() != null) {
			jsonObject
					.put("wfEndDate", DateUtils.getDate(model.getWfEndDate()));
			jsonObject.put("wfEndDate_date",
					DateUtils.getDate(model.getWfEndDate()));
			jsonObject.put("wfEndDate_datetime",
					DateUtils.getDateTime(model.getWfEndDate()));
		}
		return jsonObject;
	}

	private TripJsonFactory() {

	}

}
