/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.core.domain.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.domain.BlobItemEntity;
import com.glaf.core.util.DateUtils;

public class BlobItemJsonFactory {

	public static java.util.List<BlobItemEntity> arrayToList(JSONArray array) {
		java.util.List<BlobItemEntity> list = new java.util.ArrayList<BlobItemEntity>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			BlobItemEntity model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static BlobItemEntity jsonToObject(JSONObject jsonObject) {
		BlobItemEntity model = new BlobItemEntity();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("businessKey")) {
			model.setBusinessKey(jsonObject.getString("businessKey"));
		}
		if (jsonObject.containsKey("fileId")) {
			model.setFileId(jsonObject.getString("fileId"));
		}
		if (jsonObject.containsKey("serviceKey")) {
			model.setServiceKey(jsonObject.getString("serviceKey"));
		}
		if (jsonObject.containsKey("deviceId")) {
			model.setDeviceId(jsonObject.getString("deviceId"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("filename")) {
			model.setFilename(jsonObject.getString("filename"));
		}
		if (jsonObject.containsKey("contentType")) {
			model.setContentType(jsonObject.getString("contentType"));
		}
		if (jsonObject.containsKey("objectId")) {
			model.setObjectId(jsonObject.getString("objectId"));
		}
		if (jsonObject.containsKey("objectValue")) {
			model.setObjectValue(jsonObject.getString("objectValue"));
		}
		if (jsonObject.containsKey("size")) {
			model.setSize(jsonObject.getLong("size"));
		}
		if (jsonObject.containsKey("lastModified")) {
			model.setLastModified(jsonObject.getLong("lastModified"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
		}
		if (jsonObject.containsKey("deleteFlag")) {
			model.setDeleteFlag(jsonObject.getInteger("deleteFlag"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<BlobItemEntity> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (BlobItemEntity model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(BlobItemEntity model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getBusinessKey() != null) {
			jsonObject.put("businessKey", model.getBusinessKey());
		}
		if (model.getFileId() != null) {
			jsonObject.put("fileId", model.getFileId());
		}
		if (model.getServiceKey() != null) {
			jsonObject.put("serviceKey", model.getServiceKey());
		}
		if (model.getDeviceId() != null) {
			jsonObject.put("deviceId", model.getDeviceId());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getFilename() != null) {
			jsonObject.put("filename", model.getFilename());
		}
		if (model.getContentType() != null) {
			jsonObject.put("contentType", model.getContentType());
		}
		if (model.getObjectId() != null) {
			jsonObject.put("objectId", model.getObjectId());
		}
		if (model.getObjectValue() != null) {
			jsonObject.put("objectValue", model.getObjectValue());
		}
		jsonObject.put("size", model.getSize());
		jsonObject.put("lastModified", model.getLastModified());
		jsonObject.put("locked", model.getLocked());
		jsonObject.put("status", model.getStatus());
		jsonObject.put("deleteFlag", model.getDeleteFlag());
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
		return jsonObject;
	}

	public static ObjectNode toObjectNode(BlobItemEntity model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getBusinessKey() != null) {
			jsonObject.put("businessKey", model.getBusinessKey());
		}
		if (model.getFileId() != null) {
			jsonObject.put("fileId", model.getFileId());
		}
		if (model.getServiceKey() != null) {
			jsonObject.put("serviceKey", model.getServiceKey());
		}
		if (model.getDeviceId() != null) {
			jsonObject.put("deviceId", model.getDeviceId());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getFilename() != null) {
			jsonObject.put("filename", model.getFilename());
		}
		if (model.getContentType() != null) {
			jsonObject.put("contentType", model.getContentType());
		}
		if (model.getObjectId() != null) {
			jsonObject.put("objectId", model.getObjectId());
		}
		if (model.getObjectValue() != null) {
			jsonObject.put("objectValue", model.getObjectValue());
		}
		jsonObject.put("size", model.getSize());
		jsonObject.put("lastModified", model.getLastModified());
		jsonObject.put("locked", model.getLocked());
		jsonObject.put("status", model.getStatus());
		jsonObject.put("deleteFlag", model.getDeleteFlag());
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
		return jsonObject;
	}

	private BlobItemJsonFactory() {

	}

}
