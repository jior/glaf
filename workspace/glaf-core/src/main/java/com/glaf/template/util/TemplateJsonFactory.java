package com.glaf.template.util;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.template.Template;
import com.glaf.core.util.DateUtils;

public class TemplateJsonFactory {

	public static Template jsonToObject(JSONObject jsonObject) {
		Template model = new Template();

		if (jsonObject.containsKey("templateId")) {
			model.setTemplateId(jsonObject.getString("templateId"));
		}

		if (jsonObject.containsKey("templateType")) {
			model.setTemplateType(jsonObject.getString("templateType"));
		}

		if (jsonObject.containsKey("sysType")) {
			model.setSysType(jsonObject.getString("sysType"));
		}

		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("description")) {
			model.setDescription(jsonObject.getString("description"));
		}
		if (jsonObject.containsKey("callbackUrl")) {
			model.setCallbackUrl(jsonObject.getString("callbackUrl"));
		}
		if (jsonObject.containsKey("objectId")) {
			model.setObjectId(jsonObject.getString("objectId"));
		}
		if (jsonObject.containsKey("objectValue")) {
			model.setObjectValue(jsonObject.getString("objectValue"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("language")) {
			model.setLanguage(jsonObject.getString("language"));
		}
		if (jsonObject.containsKey("moduleId")) {
			model.setModuleId(jsonObject.getString("moduleId"));
		}
		if (jsonObject.containsKey("moduleName")) {
			model.setModuleName(jsonObject.getString("moduleName"));
		}
		if (jsonObject.containsKey("dataFile")) {
			model.setDataFile(jsonObject.getString("dataFile"));
		}
		if (jsonObject.containsKey("encoding")) {
			model.setEncoding(jsonObject.getString("encoding"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}
		if (jsonObject.containsKey("nodeId")) {
			model.setNodeId(jsonObject.getLong("nodeId"));
		}
		if (jsonObject.containsKey("lastModified")) {
			model.setLastModified(jsonObject.getLong("lastModified"));
		}
		if (jsonObject.containsKey("fileSize")) {
			model.setFileSize(jsonObject.getLong("fileSize"));
		}
		if (jsonObject.containsKey("fileType")) {
			model.setFileType(jsonObject.getInteger("fileType"));
		}

		if (jsonObject.containsKey("json")) {
			String json = jsonObject.getString("json");
			byte[] bytes = Base64.decodeBase64(json);
			model.setJson(new String(bytes));
		}

		if (jsonObject.containsKey("data")) {
			String data = jsonObject.getString("data");
			byte[] bytes = Base64.decodeBase64(data);
			model.setData(bytes);
		}
		return model;
	}

	public static JSONObject toJsonObject(Template model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getTemplateId());
		jsonObject.put("_id_", model.getTemplateId());
		jsonObject.put("templateId", model.getTemplateId());
		if (model.getTemplateType() != null) {
			jsonObject.put("templateType", model.getTemplateType());
		}
		if (jsonObject.containsKey("sysType")) {
			model.setSysType(jsonObject.getString("sysType"));
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getDescription() != null) {
			jsonObject.put("description", model.getDescription());
		}
		if (model.getCallbackUrl() != null) {
			jsonObject.put("callbackUrl", model.getCallbackUrl());
		}
		if (model.getObjectId() != null) {
			jsonObject.put("objectId", model.getObjectId());
		}
		if (model.getObjectValue() != null) {
			jsonObject.put("objectValue", model.getObjectValue());
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
		if (model.getLanguage() != null) {
			jsonObject.put("language", model.getLanguage());
		}
		if (model.getModuleId() != null) {
			jsonObject.put("moduleId", model.getModuleId());
		}
		if (model.getModuleName() != null) {
			jsonObject.put("moduleName", model.getModuleName());
		}
		if (model.getDataFile() != null) {
			jsonObject.put("dataFile", model.getDataFile());
		}
		if (model.getEncoding() != null) {
			jsonObject.put("encoding", model.getEncoding());
		}
		if (model.getData() != null && model.getData().length < 2048000) {
			jsonObject.put("data", Base64.encodeBase64URLSafe(model.getData()));
		}

		if (model.getJson() != null && model.getJson().length() < 2048000) {
			jsonObject.put("json",
					Base64.encodeBase64URLSafe(model.getJson().getBytes()));
		}

		jsonObject.put("locked", model.getLocked());
		jsonObject.put("nodeId", model.getNodeId());
		jsonObject.put("lastModified", model.getLastModified());
		jsonObject.put("fileSize", model.getFileSize());
		jsonObject.put("fileType", model.getFileType());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(Template model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getTemplateId());
		jsonObject.put("_id_", model.getTemplateId());
		jsonObject.put("templateId", model.getTemplateId());
		if (model.getTemplateType() != null) {
			jsonObject.put("templateType", model.getTemplateType());
		}
		if (model.getSysType() != null) {
			jsonObject.put("sysType", model.getSysType());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getDescription() != null) {
			jsonObject.put("description", model.getDescription());
		}
		if (model.getCallbackUrl() != null) {
			jsonObject.put("callbackUrl", model.getCallbackUrl());
		}
		if (model.getObjectId() != null) {
			jsonObject.put("objectId", model.getObjectId());
		}
		if (model.getObjectValue() != null) {
			jsonObject.put("objectValue", model.getObjectValue());
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
		if (model.getLanguage() != null) {
			jsonObject.put("language", model.getLanguage());
		}
		if (model.getModuleId() != null) {
			jsonObject.put("moduleId", model.getModuleId());
		}
		if (model.getModuleName() != null) {
			jsonObject.put("moduleName", model.getModuleName());
		}
		if (model.getDataFile() != null) {
			jsonObject.put("dataFile", model.getDataFile());
		}
		if (model.getEncoding() != null) {
			jsonObject.put("encoding", model.getEncoding());
		}
		if (model.getJson() != null && model.getJson().length() < 2048000) {
			jsonObject.put("json",
					Base64.encodeBase64URLSafe(model.getJson().getBytes()));
		}
		if (model.getData() != null && model.getData().length < 2048000) {
			jsonObject.put("data", Base64.encodeBase64URLSafe(model.getData()));
		}
		jsonObject.put("locked", model.getLocked());
		jsonObject.put("nodeId", model.getNodeId());
		jsonObject.put("lastModified", model.getLastModified());
		jsonObject.put("fileSize", model.getFileSize());
		jsonObject.put("fileType", model.getFileType());
		return jsonObject;
	}

}
