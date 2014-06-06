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

package com.glaf.core.base;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.domain.util.ConnectionDefinitionJsonFactory;

public class ConnectionDefinition implements java.io.Serializable, JSONable {

	private static final long serialVersionUID = 1L;

	protected String provider;

	protected String type;

	protected String name;

	protected String subject;

	protected String datasource;

	protected String database;

	protected String host;

	protected int port;

	protected String driver;

	protected String url;

	protected String user;

	protected String password;

	protected String attribute;

	protected boolean autoCommit;

	protected java.util.Properties properties;

	public ConnectionDefinition() {

	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getAttribute() {
		return attribute;
	}

	public String getDatasource() {
		return datasource;
	}

	public String getDriver() {
		return driver;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public java.util.Properties getProperties() {
		return properties;
	}

	public String getProvider() {
		return provider;
	}

	public String getSubject() {
		return subject;
	}

	public String getType() {
		return type;
	}

	public String getUrl() {
		return url;
	}

	public String getUser() {
		return user;
	}

	public boolean isAutoCommit() {
		return autoCommit;
	}

	public ConnectionDefinition jsonToObject(JSONObject jsonObject) {
		return ConnectionDefinitionJsonFactory.jsonToObject(jsonObject);
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public void setAutoCommit(boolean autoCommit) {
		this.autoCommit = autoCommit;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setProperties(java.util.Properties properties) {
		this.properties = properties;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public JSONObject toJsonObject() {
		return ConnectionDefinitionJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return ConnectionDefinitionJsonFactory.toObjectNode(this);
	}

}
