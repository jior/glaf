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

package com.glaf.transport.domain;

import java.io.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.transport.util.*;

/**
 * 
 * 实体对象
 * 
 */

@Entity
@Table(name = "SYS_FILE_TRANSPORT")
public class FileTransport implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Long id;

	@Column(name = "NODEID_")
	protected Long nodeId;

	@Column(name = "TITLE_", length = 100)
	protected String title;

	@Column(name = "CODE_", length = 50)
	protected String code;

	@Column(name = "HOST_", length = 100)
	protected String host;

	@Column(name = "PORT_")
	protected Integer port;

	@Column(name = "USER_", length = 50)
	protected String user;

	@Column(name = "PASSWORD_", length = 100)
	protected String password;

	@Column(name = "KEY_", length = 1024)
	protected String key;

	@Column(name = "PATH_", length = 100)
	protected String path;

	@Column(name = "TYPE_", length = 50)
	protected String type;

	@Column(name = "ACTIVE_", length = 1)
	protected String active;

	@javax.persistence.Transient
	protected String connectionString;

	public FileTransport() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileTransport other = (FileTransport) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getActive() {
		return this.active;
	}

	public String getCode() {
		return code;
	}

	public String getConnectionString() {
		return this.connectionString;
	}

	public String getHost() {
		return this.host;
	}

	public Long getId() {
		return this.id;
	}

	public String getKey() {
		return key;
	}

	public Long getNodeId() {
		return this.nodeId;
	}

	public String getPassword() {
		return this.password;
	}

	public String getPath() {
		return this.path;
	}

	public Integer getPort() {
		return this.port;
	}

	public String getTitle() {
		return title;
	}

	public String getType() {
		return this.type;
	}

	public String getUser() {
		return this.user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public FileTransport jsonToObject(JSONObject jsonObject) {
		return FileTransportJsonFactory.jsonToObject(jsonObject);
	}

	public void setActive(String active) {
		this.active = active;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public JSONObject toJsonObject() {
		return FileTransportJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FileTransportJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
