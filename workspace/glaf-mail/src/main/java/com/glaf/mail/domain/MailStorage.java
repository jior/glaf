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

package com.glaf.mail.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;

@Entity
@Table(name = "SYS_MAIL_STORAGE")
public class MailStorage implements Serializable {

	public final static String DEFAULT_DATA_SPACE = "storageDB";

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	/**
	 * 主题
	 */
	@Column(name = "SUBJECT_", length = 200)
	protected String subject;

	/**
	 * 数据空间
	 */
	@Column(name = "DATASPACE_", length = 50)
	protected String dataSpace;

	/**
	 * 数据表
	 */
	@Column(name = "DATATABLE_", length = 25)
	protected String dataTable;

	/**
	 * 存储类型
	 */
	@Column(name = "STORAGETYPE_", length = 50)
	protected String storageType;

	@Column(name = "HOST_", length = 200)
	protected String host;

	@Column(name = "PORT_")
	protected int port = 0;

	@Column(name = "USERNAME_", length = 100)
	protected String username;

	@Column(name = "PASSWORD_", length = 100)
	protected String password;

	/**
	 * 使用状态
	 */
	@Column(name = "STATUS_")
	protected Integer status;

	/**
	 * 创建人
	 */
	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	/**
	 * 创建日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	public MailStorage() {

	}

	public String getCreateBy() {
		return this.createBy;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public String getDataSpace() {
		if (dataSpace == null) {
			dataSpace = DEFAULT_DATA_SPACE;
		}
		return dataSpace;
	}

	public String getDataTable() {
		return this.dataTable;
	}

	public String getHost() {
		return host;
	}

	public String getId() {
		return this.id;
	}

	public String getPassword() {
		return password;
	}

	public int getPort() {
		return port;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getStorageType() {
		return this.storageType;
	}

	public String getSubject() {
		return this.subject;
	}

	public String getUsername() {
		return username;
	}

	public MailStorage jsonToObject(JSONObject jsonObject) {
		MailStorage model = new MailStorage();
		if (jsonObject.containsKey("subject")) {
			model.setSubject(jsonObject.getString("subject"));
		}
		if (jsonObject.containsKey("dataSpace")) {
			model.setDataSpace(jsonObject.getString("dataSpace"));
		}
		if (jsonObject.containsKey("dataTable")) {
			model.setDataTable(jsonObject.getString("dataTable"));
		}
		if (jsonObject.containsKey("storageType")) {
			model.setStorageType(jsonObject.getString("storageType"));
		}
		if (jsonObject.containsKey("host")) {
			model.setHost(jsonObject.getString("host"));
		}
		if (jsonObject.containsKey("port")) {
			model.setPort(jsonObject.getInteger("port"));
		}
		if (jsonObject.containsKey("username")) {
			model.setUsername(jsonObject.getString("username"));
		}
		if (jsonObject.containsKey("password")) {
			model.setPassword(jsonObject.getString("password"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		return model;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setDataSpace(String dataSpace) {
		this.dataSpace = dataSpace;
	}

	public void setDataTable(String dataTable) {
		this.dataTable = dataTable;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		if (subject != null) {
			jsonObject.put("subject", subject);
		}
		if (dataSpace != null) {
			jsonObject.put("dataSpace", dataSpace);
		}
		if (dataTable != null) {
			jsonObject.put("dataTable", dataTable);
		}
		if (storageType != null) {
			jsonObject.put("storageType", storageType);
		}
		if (host != null) {
			jsonObject.put("host", host);
		}
		jsonObject.put("port", port);
		if (username != null) {
			jsonObject.put("username", username);
		}
		if (password != null) {
			jsonObject.put("password", password);
		}
		if (createBy != null) {
			jsonObject.put("createBy", createBy);
		}
		if (createDate != null) {
			jsonObject.put("createDate", DateUtils.getDate(createDate));
			jsonObject.put("createDate_date", DateUtils.getDate(createDate));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(createDate));
		}
		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", id);
		if (subject != null) {
			jsonObject.put("subject", subject);
		}
		if (dataSpace != null) {
			jsonObject.put("dataSpace", dataSpace);
		}
		if (dataTable != null) {
			jsonObject.put("dataTable", dataTable);
		}
		if (storageType != null) {
			jsonObject.put("storageType", storageType);
		}
		if (host != null) {
			jsonObject.put("host", host);
		}
		jsonObject.put("port", port);
		if (username != null) {
			jsonObject.put("username", username);
		}
		if (password != null) {
			jsonObject.put("password", password);
		}
		if (createBy != null) {
			jsonObject.put("createBy", createBy);
		}
		if (createDate != null) {
			jsonObject.put("createDate", DateUtils.getDate(createDate));
			jsonObject.put("createDate_date", DateUtils.getDate(createDate));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(createDate));
		}
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}