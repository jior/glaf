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

package com.glaf.setup.conf;

import java.util.*;

public class Database implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String name;

	private String subject;

	private String dialect;

	private String type;

	private String driverClassName;

	private String url;

	private String host;

	private int port;

	private String username;

	private String password;

	private String databaseName;

	private String datasourceName;

	private Map<String, Object> dataMap = new TreeMap<String, Object>();

	public Database() {

	}

	public String getDatabaseName() {
		return databaseName;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public String getDatasourceName() {
		return datasourceName;
	}

	public String getDialect() {
		return dialect;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public String getHost() {
		return host;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public int getPort() {
		return port;
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

	public String getUsername() {
		return username;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPort(int port) {
		this.port = port;
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

	public void setUsername(String username) {
		this.username = username;
	}

	public String toString() {
		return subject;
	}
}