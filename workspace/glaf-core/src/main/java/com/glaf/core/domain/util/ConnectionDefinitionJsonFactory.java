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

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.*;

/**
 * 
 * JSON工厂类
 * 
 */
public class ConnectionDefinitionJsonFactory {

	public static ConnectionDefinition jsonToObject(JSONObject jsonObject) {
		ConnectionDefinition model = new ConnectionDefinition();
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("provider")) {
			model.setProvider(jsonObject.getString("provider"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("subject")) {
			model.setSubject(jsonObject.getString("subject"));
		}
		if (jsonObject.containsKey("datasource")) {
			model.setDatasource(jsonObject.getString("datasource"));
		}
		if (jsonObject.containsKey("driver")) {
			model.setDriver(jsonObject.getString("driver"));
		}
		if (jsonObject.containsKey("url")) {
			model.setUrl(jsonObject.getString("url"));
		}
		if (jsonObject.containsKey("user")) {
			model.setUser(jsonObject.getString("user"));
		}
		if (jsonObject.containsKey("password")) {
			model.setPassword(jsonObject.getString("password"));
		}
		if (jsonObject.containsKey("host")) {
			model.setHost(jsonObject.getString("host"));
		}
		if (jsonObject.containsKey("port")) {
			model.setPort(jsonObject.getInteger("port"));
		}
		if (jsonObject.containsKey("database")) {
			model.setDatabase(jsonObject.getString("database"));
		}
		if (jsonObject.containsKey("attribute")) {
			model.setAttribute(jsonObject.getString("attribute"));
		}

		return model;
	}

	public static JSONObject toJsonObject(ConnectionDefinition model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", model.getName());
		jsonObject.put("_name_", model.getName());
		if (model.getProvider() != null) {
			jsonObject.put("provider", model.getProvider());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getSubject() != null) {
			jsonObject.put("subject", model.getSubject());
		}
		if (model.getDatasource() != null) {
			jsonObject.put("datasource", model.getDatasource());
		}
		if (model.getDriver() != null) {
			jsonObject.put("driver", model.getDriver());
		}
		if (model.getUrl() != null) {
			jsonObject.put("url", model.getUrl());
		}
		if (model.getUser() != null) {
			jsonObject.put("user", model.getUser());
		}
		if (model.getPassword() != null) {
			jsonObject.put("password", model.getPassword());
		}
		if (model.getHost() != null) {
			jsonObject.put("host", model.getHost());
		}
		if (model.getPort() != 0) {
			jsonObject.put("port", model.getPort());
		}
		if (model.getDatabase() != null) {
			jsonObject.put("database", model.getDatabase());
		}
		if (model.getAttribute() != null) {
			jsonObject.put("attribute", model.getAttribute());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(ConnectionDefinition model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("name", model.getName());
		jsonObject.put("_name_", model.getName());
		if (model.getProvider() != null) {
			jsonObject.put("provider", model.getProvider());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getSubject() != null) {
			jsonObject.put("subject", model.getSubject());
		}
		if (model.getDatasource() != null) {
			jsonObject.put("datasource", model.getDatasource());
		}
		if (model.getDriver() != null) {
			jsonObject.put("driver", model.getDriver());
		}
		if (model.getUrl() != null) {
			jsonObject.put("url", model.getUrl());
		}
		if (model.getUser() != null) {
			jsonObject.put("user", model.getUser());
		}
		if (model.getPassword() != null) {
			jsonObject.put("password", model.getPassword());
		}
		if (model.getHost() != null) {
			jsonObject.put("host", model.getHost());
		}
		if (model.getPort() != 0) {
			jsonObject.put("port", model.getPort());
		}
		if (model.getDatabase() != null) {
			jsonObject.put("database", model.getDatabase());
		}
		if (model.getAttribute() != null) {
			jsonObject.put("attribute", model.getAttribute());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(
			java.util.List<ConnectionDefinition> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ConnectionDefinition model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ConnectionDefinition> arrayToList(
			JSONArray array) {
		java.util.List<ConnectionDefinition> list = new java.util.ArrayList<ConnectionDefinition>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ConnectionDefinition model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private ConnectionDefinitionJsonFactory() {

	}

}
