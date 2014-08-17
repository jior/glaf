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

package com.glaf.transport.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.transport.domain.*;

/**
 * 
 * JSON工厂类
 * 
 */
public class FileTransportJsonFactory {

	public static FileTransport jsonToObject(JSONObject jsonObject) {
		FileTransport model = new FileTransport();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("nodeId")) {
			model.setNodeId(jsonObject.getLong("nodeId"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("host")) {
			model.setHost(jsonObject.getString("host"));
		}
		if (jsonObject.containsKey("port")) {
			model.setPort(jsonObject.getInteger("port"));
		}
		if (jsonObject.containsKey("user")) {
			model.setUser(jsonObject.getString("user"));
		}
		if (jsonObject.containsKey("password")) {
			model.setPassword(jsonObject.getString("password"));
		}
		if (jsonObject.containsKey("path")) {
			model.setPath(jsonObject.getString("path"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("providerClass")) {
			model.setProviderClass(jsonObject.getString("providerClass"));
		}
		if (jsonObject.containsKey("active")) {
			model.setActive(jsonObject.getString("active"));
		}

		return model;
	}

	public static JSONObject toJsonObject(FileTransport model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("nodeId", model.getNodeId());
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getHost() != null) {
			jsonObject.put("host", model.getHost());
		}
		jsonObject.put("port", model.getPort());
		if (model.getUser() != null) {
			jsonObject.put("user", model.getUser());
		}
		if (model.getPassword() != null) {
			jsonObject.put("password", model.getPassword());
		}
		if (model.getPath() != null) {
			jsonObject.put("path", model.getPath());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getProviderClass() != null) {
			jsonObject.put("providerClass", model.getProviderClass());
		}
		if (model.getActive() != null) {
			jsonObject.put("active", model.getActive());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(FileTransport model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("nodeId", model.getNodeId());
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getHost() != null) {
			jsonObject.put("host", model.getHost());
		}
		jsonObject.put("port", model.getPort());
		if (model.getUser() != null) {
			jsonObject.put("user", model.getUser());
		}
		if (model.getPassword() != null) {
			jsonObject.put("password", model.getPassword());
		}
		if (model.getPath() != null) {
			jsonObject.put("path", model.getPath());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getProviderClass() != null) {
			jsonObject.put("providerClass", model.getProviderClass());
		}
		if (model.getActive() != null) {
			jsonObject.put("active", model.getActive());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<FileTransport> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FileTransport model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FileTransport> arrayToList(JSONArray array) {
		java.util.List<FileTransport> list = new java.util.ArrayList<FileTransport>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FileTransport model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private FileTransportJsonFactory() {

	}

}
