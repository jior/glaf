package com.glaf.form.core.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.form.core.domain.FormHistoryInstance;

public class FormHistoryInstanceJsonFactory {

	public static FormHistoryInstance jsonToObject(JSONObject jsonObject) {
		FormHistoryInstance model = new FormHistoryInstance();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("nodeId")) {
			model.setNodeId(jsonObject.getString("nodeId"));
		}
		if (jsonObject.containsKey("objectId")) {
			model.setObjectId(jsonObject.getString("objectId"));
		}
		if (jsonObject.containsKey("objectValue")) {
			model.setObjectValue(jsonObject.getString("objectValue"));
		}
		if (jsonObject.containsKey("objectType")) {
			model.setObjectType(jsonObject.getInteger("objectType"));
		}
		if (jsonObject.containsKey("processInstanceId")) {
			model.setProcessInstanceId(jsonObject
					.getString("processInstanceId"));
		}
		if (jsonObject.containsKey("taskInstanceId")) {
			model.setTaskInstanceId(jsonObject.getString("taskInstanceId"));
		}
		if (jsonObject.containsKey("actorId")) {
			model.setActorId(jsonObject.getString("actorId"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("refId")) {
			model.setRefId(jsonObject.getLong("refId"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("versionNo")) {
			model.setVersionNo(jsonObject.getLong("versionNo"));
		}

		return model;
	}

	public static JSONObject toJsonObject(FormHistoryInstance model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getNodeId() != null) {
			jsonObject.put("nodeId", model.getNodeId());
		}
		if (model.getObjectId() != null) {
			jsonObject.put("objectId", model.getObjectId());
		}
		if (model.getObjectValue() != null) {
			jsonObject.put("objectValue", model.getObjectValue());
		}
		jsonObject.put("objectType", model.getObjectType());
		if (model.getProcessInstanceId() != null) {
			jsonObject.put("processInstanceId", model.getProcessInstanceId());
		}
		if (model.getTaskInstanceId() != null) {
			jsonObject.put("taskInstanceId", model.getTaskInstanceId());
		}
		if (model.getActorId() != null) {
			jsonObject.put("actorId", model.getActorId());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getRefId() != null) {
			jsonObject.put("refId", model.getRefId());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		jsonObject.put("versionNo", model.getVersionNo());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(FormHistoryInstance model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getNodeId() != null) {
			jsonObject.put("nodeId", model.getNodeId());
		}
		if (model.getObjectId() != null) {
			jsonObject.put("objectId", model.getObjectId());
		}
		if (model.getObjectValue() != null) {
			jsonObject.put("objectValue", model.getObjectValue());
		}
		jsonObject.put("objectType", model.getObjectType());
		if (model.getProcessInstanceId() != null) {
			jsonObject.put("processInstanceId", model.getProcessInstanceId());
		}
		if (model.getTaskInstanceId() != null) {
			jsonObject.put("taskInstanceId", model.getTaskInstanceId());
		}
		if (model.getActorId() != null) {
			jsonObject.put("actorId", model.getActorId());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getRefId() != null) {
			jsonObject.put("refId", model.getRefId());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		jsonObject.put("versionNo", model.getVersionNo());
		return jsonObject;
	}

	private FormHistoryInstanceJsonFactory() {

	}

}
