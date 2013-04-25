package com.glaf.form.core.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.form.core.domain.FormApplication;

public class FormApplicationJsonFactory {

	public static FormApplication jsonToObject(JSONObject jsonObject) {
		FormApplication model = new FormApplication();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("archivesNode")) {
			model.setArchivesNode(jsonObject.getString("archivesNode"));
		}
		if (jsonObject.containsKey("auditUploadFlag")) {
			model.setAuditUploadFlag(jsonObject.getString("auditUploadFlag"));
		}
		if (jsonObject.containsKey("autoArchivesFlag")) {
			model.setAutoArchivesFlag(jsonObject.getString("autoArchivesFlag"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("description")) {
			model.setDescription(jsonObject.getString("description"));
		}
		if (jsonObject.containsKey("docRequiredFlag")) {
			model.setDocRequiredFlag(jsonObject.getString("docRequiredFlag"));
		}
		if (jsonObject.containsKey("formName")) {
			model.setFormName(jsonObject.getString("formName"));
		}
		if (jsonObject.containsKey("formRendererType")) {
			model.setFormRendererType(jsonObject.getString("formRendererType"));
		}
		if (jsonObject.containsKey("linkControllerName")) {
			model.setLinkControllerName(jsonObject
					.getString("linkControllerName"));
		}
		if (jsonObject.containsKey("linkTemplateId")) {
			model.setLinkTemplateId(jsonObject.getString("linkTemplateId"));
		}
		if (jsonObject.containsKey("listControllerName")) {
			model.setListControllerName(jsonObject
					.getString("listControllerName"));
		}
		if (jsonObject.containsKey("listTemplateId")) {
			model.setListTemplateId(jsonObject.getString("listTemplateId"));
		}
		if (jsonObject.containsKey("manualRouteFlag")) {
			model.setManualRouteFlag(jsonObject.getString("manualRouteFlag"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("objectId")) {
			model.setObjectId(jsonObject.getString("objectId"));
		}
		if (jsonObject.containsKey("objectValue")) {
			model.setObjectValue(jsonObject.getString("objectValue"));
		}
		if (jsonObject.containsKey("processDefinitionId")) {
			model.setProcessDefinitionId(jsonObject
					.getString("processDefinitionId"));
		}
		if (jsonObject.containsKey("processName")) {
			model.setProcessName(jsonObject.getString("processName"));
		}
		if (jsonObject.containsKey("releaseDate")) {
			model.setReleaseDate(jsonObject.getDate("releaseDate"));
		}
		if (jsonObject.containsKey("releaseFlag")) {
			model.setReleaseFlag(jsonObject.getString("releaseFlag"));
		}
		if (jsonObject.containsKey("tableName")) {
			model.setTableName(jsonObject.getString("tableName"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("uploadFlag")) {
			model.setUploadFlag(jsonObject.getString("uploadFlag"));
		}
		if (jsonObject.containsKey("nodeId")) {
			model.setNodeId(jsonObject.getLong("nodeId"));
		}

		return model;
	}

	public static JSONObject toJsonObject(FormApplication model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getArchivesNode() != null) {
			jsonObject.put("archivesNode", model.getArchivesNode());
		}
		if (model.getAuditUploadFlag() != null) {
			jsonObject.put("auditUploadFlag", model.getAuditUploadFlag());
		}
		if (model.getAutoArchivesFlag() != null) {
			jsonObject.put("autoArchivesFlag", model.getAutoArchivesFlag());
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
		if (model.getDescription() != null) {
			jsonObject.put("description", model.getDescription());
		}
		if (model.getDocRequiredFlag() != null) {
			jsonObject.put("docRequiredFlag", model.getDocRequiredFlag());
		}
		if (model.getFormName() != null) {
			jsonObject.put("formName", model.getFormName());
		}
		if (model.getFormRendererType() != null) {
			jsonObject.put("formRendererType", model.getFormRendererType());
		}
		if (model.getLinkControllerName() != null) {
			jsonObject.put("linkControllerName", model.getLinkControllerName());
		}
		if (model.getLinkTemplateId() != null) {
			jsonObject.put("linkTemplateId", model.getLinkTemplateId());
		}
		if (model.getListControllerName() != null) {
			jsonObject.put("listControllerName", model.getListControllerName());
		}
		if (model.getListTemplateId() != null) {
			jsonObject.put("listTemplateId", model.getListTemplateId());
		}
		if (model.getManualRouteFlag() != null) {
			jsonObject.put("manualRouteFlag", model.getManualRouteFlag());
		}
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
		if (model.getProcessDefinitionId() != null) {
			jsonObject.put("processDefinitionId",
					model.getProcessDefinitionId());
		}
		if (model.getProcessName() != null) {
			jsonObject.put("processName", model.getProcessName());
		}
		if (model.getReleaseDate() != null) {
			jsonObject.put("releaseDate",
					DateUtils.getDate(model.getReleaseDate()));
			jsonObject.put("releaseDate_date",
					DateUtils.getDate(model.getReleaseDate()));
			jsonObject.put("releaseDate_datetime",
					DateUtils.getDateTime(model.getReleaseDate()));
		}
		if (model.getReleaseFlag() != null) {
			jsonObject.put("releaseFlag", model.getReleaseFlag());
		}
		if (model.getTableName() != null) {
			jsonObject.put("tableName", model.getTableName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getUploadFlag() != null) {
			jsonObject.put("uploadFlag", model.getUploadFlag());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(FormApplication model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getArchivesNode() != null) {
			jsonObject.put("archivesNode", model.getArchivesNode());
		}
		if (model.getAuditUploadFlag() != null) {
			jsonObject.put("auditUploadFlag", model.getAuditUploadFlag());
		}
		if (model.getAutoArchivesFlag() != null) {
			jsonObject.put("autoArchivesFlag", model.getAutoArchivesFlag());
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
		if (model.getDescription() != null) {
			jsonObject.put("description", model.getDescription());
		}
		if (model.getDocRequiredFlag() != null) {
			jsonObject.put("docRequiredFlag", model.getDocRequiredFlag());
		}
		if (model.getFormName() != null) {
			jsonObject.put("formName", model.getFormName());
		}
		if (model.getFormRendererType() != null) {
			jsonObject.put("formRendererType", model.getFormRendererType());
		}
		if (model.getLinkControllerName() != null) {
			jsonObject.put("linkControllerName", model.getLinkControllerName());
		}
		if (model.getLinkTemplateId() != null) {
			jsonObject.put("linkTemplateId", model.getLinkTemplateId());
		}
		if (model.getListControllerName() != null) {
			jsonObject.put("listControllerName", model.getListControllerName());
		}
		if (model.getListTemplateId() != null) {
			jsonObject.put("listTemplateId", model.getListTemplateId());
		}
		if (model.getManualRouteFlag() != null) {
			jsonObject.put("manualRouteFlag", model.getManualRouteFlag());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getObjectId() != null) {
			jsonObject.put("objectId", model.getObjectId());
		}
		if (model.getObjectValue() != null) {
			jsonObject.put("objectValue", model.getObjectValue());
		}
		if (model.getProcessDefinitionId() != null) {
			jsonObject.put("processDefinitionId",
					model.getProcessDefinitionId());
		}
		if (model.getProcessName() != null) {
			jsonObject.put("processName", model.getProcessName());
		}
		if (model.getReleaseDate() != null) {
			jsonObject.put("releaseDate",
					DateUtils.getDate(model.getReleaseDate()));
			jsonObject.put("releaseDate_date",
					DateUtils.getDate(model.getReleaseDate()));
			jsonObject.put("releaseDate_datetime",
					DateUtils.getDateTime(model.getReleaseDate()));
		}
		if (model.getReleaseFlag() != null) {
			jsonObject.put("releaseFlag", model.getReleaseFlag());
		}
		if (model.getTableName() != null) {
			jsonObject.put("tableName", model.getTableName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getUploadFlag() != null) {
			jsonObject.put("uploadFlag", model.getUploadFlag());
		}
		if (model.getNodeId() != null) {
			jsonObject.put("nodeId", model.getNodeId());
		}
		return jsonObject;
	}

	private FormApplicationJsonFactory() {

	}

}
