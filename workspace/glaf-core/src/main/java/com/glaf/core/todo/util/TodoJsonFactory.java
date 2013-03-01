package com.glaf.core.todo.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.todo.*;

public class TodoJsonFactory {

	public static Todo jsonToObject(JSONObject jsonObject) {
		Todo model = new Todo();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
	 
		if (jsonObject.containsKey("code")) {
			model.setCode(jsonObject.getString("code"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("deptId")) {
			model.setDeptId(jsonObject.getLong("deptId"));
		}
		if (jsonObject.containsKey("deptName")) {
			model.setDeptName(jsonObject.getString("deptName"));
		}
		if (jsonObject.containsKey("enableFlag")) {
			model.setEnableFlag(jsonObject.getInteger("enableFlag"));
		}
		 
		if (jsonObject.containsKey("limitDay")) {
			model.setLimitDay(jsonObject.getInteger("limitDay"));
		}
		if (jsonObject.containsKey("xa")) {
			model.setXa(jsonObject.getInteger("xa"));
		}
		if (jsonObject.containsKey("xb")) {
			model.setXb(jsonObject.getInteger("xb"));
		}
		if (jsonObject.containsKey("link")) {
			model.setLink(jsonObject.getString("link"));
		}
		if (jsonObject.containsKey("listLink")) {
			model.setListLink(jsonObject.getString("listLink"));
		}
		if (jsonObject.containsKey("linkType")) {
			model.setLinkType(jsonObject.getString("linkType"));
		}
		if (jsonObject.containsKey("appId")) {
			model.setAppId(jsonObject.getLong("appId"));
		}
		if (jsonObject.containsKey("moduleId")) {
			model.setModuleId(jsonObject.getLong("moduleId"));
		}
		if (jsonObject.containsKey("moduleName")) {
			model.setModuleName(jsonObject.getString("moduleName"));
		}
		 
		if (jsonObject.containsKey("objectId")) {
			model.setObjectId(jsonObject.getString("objectId"));
		}
		if (jsonObject.containsKey("objectValue")) {
			model.setObjectValue(jsonObject.getString("objectValue"));
		}
		if (jsonObject.containsKey("roleCode")) {
			model.setRoleCode(jsonObject.getString("roleCode"));
		}
		if (jsonObject.containsKey("roleId")) {
			model.setRoleId(jsonObject.getLong("roleId"));
		}
		 
		if (jsonObject.containsKey("processName")) {
			model.setProcessName(jsonObject.getString("processName"));
		}
		if (jsonObject.containsKey("taskName")) {
			model.setTaskName(jsonObject.getString("taskName"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("sql")) {
			model.setSql(jsonObject.getString("sql"));
		}
		if (jsonObject.containsKey("versionNo")) {
			model.setVersionNo(jsonObject.getLong("versionNo"));
		}

		return model;
	}

	public static JSONObject toJsonObject(Todo model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		 
		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		jsonObject.put("deptId", model.getDeptId());
		 
		jsonObject.put("enableFlag", model.getEnableFlag());
		 
		jsonObject.put("limitDay", model.getLimitDay());
		jsonObject.put("xa", model.getXa());
		jsonObject.put("xb", model.getXb());
		if (model.getLink() != null) {
			jsonObject.put("link", model.getLink());
		}
		if (model.getListLink() != null) {
			jsonObject.put("listLink", model.getListLink());
		}
		if (model.getLinkType() != null) {
			jsonObject.put("linkType", model.getLinkType());
		}
		jsonObject.put("appId", model.getAppId());
		jsonObject.put("moduleId", model.getModuleId());
		if (model.getModuleName() != null) {
			jsonObject.put("moduleName", model.getModuleName());
		}
		 
		if (model.getObjectId() != null) {
			jsonObject.put("objectId", model.getObjectId());
		}
		if (model.getObjectValue() != null) {
			jsonObject.put("objectValue", model.getObjectValue());
		}
		if (model.getRoleCode() != null) {
			jsonObject.put("roleCode", model.getRoleCode());
		}
		jsonObject.put("roleId", model.getRoleId());
		 
		if (model.getProcessName() != null) {
			jsonObject.put("processName", model.getProcessName());
		}
		if (model.getTaskName() != null) {
			jsonObject.put("taskName", model.getTaskName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getSql() != null) {
			jsonObject.put("sql", model.getSql());
		}
		jsonObject.put("versionNo", model.getVersionNo());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(Todo model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		 
		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		jsonObject.put("deptId", model.getDeptId());
		 
		jsonObject.put("enableFlag", model.getEnableFlag());
		 
		jsonObject.put("limitDay", model.getLimitDay());
		jsonObject.put("xa", model.getXa());
		jsonObject.put("xb", model.getXb());
		if (model.getLink() != null) {
			jsonObject.put("link", model.getLink());
		}
		if (model.getListLink() != null) {
			jsonObject.put("listLink", model.getListLink());
		}
		if (model.getLinkType() != null) {
			jsonObject.put("linkType", model.getLinkType());
		}
		jsonObject.put("appId", model.getAppId());
		jsonObject.put("moduleId", model.getModuleId());
		if (model.getModuleName() != null) {
			jsonObject.put("moduleName", model.getModuleName());
		}
		 
		if (model.getObjectId() != null) {
			jsonObject.put("objectId", model.getObjectId());
		}
		if (model.getObjectValue() != null) {
			jsonObject.put("objectValue", model.getObjectValue());
		}
		if (model.getRoleCode() != null) {
			jsonObject.put("roleCode", model.getRoleCode());
		}
		jsonObject.put("roleId", model.getRoleId());
		 
		if (model.getProcessName() != null) {
			jsonObject.put("processName", model.getProcessName());
		}
		if (model.getTaskName() != null) {
			jsonObject.put("taskName", model.getTaskName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getSql() != null) {
			jsonObject.put("sql", model.getSql());
		}
		jsonObject.put("versionNo", model.getVersionNo());
		return jsonObject;
	}

	private TodoJsonFactory() {

	}

}
