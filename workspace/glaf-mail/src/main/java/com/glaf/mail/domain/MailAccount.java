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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;

@Entity
@Table(name = "SYS_MAIL_ACCOUNT")
public class MailAccount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	@Column(name = "ACCOUNTTYPE_", length = 50)
	protected String accountType;

	@Column(name = "MAILADDRESS_", length = 100)
	protected String mailAddress;

	@Column(name = "SHOWNAME_", length = 50)
	protected String showName;

	@Column(name = "USERNAME_", length = 100)
	protected String username;

	@Column(name = "PASSWORD_", length = 200)
	protected String password;

	@Column(name = "POP3SERVER_", length = 100)
	protected String pop3Server;

	@Column(name = "RECEIVEPORT_")
	protected int receivePort = 110;

	@Column(name = "SMTPSERVER_", length = 100)
	protected String smtpServer;

	@Column(name = "SENDPORT_")
	protected int sendPort = 25;

	@Column(name = "AUTORECEIVE_", length = 10)
	protected String autoReceive;

	@Column(name = "REMEMBERPASSWORD_", length = 10)
	protected String rememberPassword;

	@Column(name = "LOCKED_")
	protected int locked;

	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	@Column(name = "AUTHFLAG_", length = 10)
	protected String authFlag;

	public MailAccount() {

	}

	public String getAccountType() {
		return accountType;
	}

	public String getAuthFlag() {
		return authFlag;
	}

	public String getAutoReceive() {
		return autoReceive;
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getId() {
		return id;
	}

	public int getLocked() {
		return locked;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public String getPassword() {
		return password;
	}

	public String getPop3Server() {
		return pop3Server;
	}

	public int getReceivePort() {
		return receivePort;
	}

	public String getRememberPassword() {
		return rememberPassword;
	}

	public int getSendPort() {
		return sendPort;
	}

	public String getShowName() {
		return showName;
	}

	public String getSmtpServer() {
		return smtpServer;
	}

	public String getUsername() {
		return username;
	}

	public boolean authFlag() {
		if (StringUtils.equalsIgnoreCase(authFlag, "1")
				|| StringUtils.equalsIgnoreCase(authFlag, "Y")
				|| StringUtils.equalsIgnoreCase(authFlag, "true")) {
			return true;
		}
		return false;
	}

	public boolean autoReceive() {
		if (StringUtils.equalsIgnoreCase(autoReceive, "1")
				|| StringUtils.equalsIgnoreCase(autoReceive, "Y")
				|| StringUtils.equalsIgnoreCase(autoReceive, "true")) {
			return true;
		}
		return false;
	}

	public boolean rememberPassword() {
		if (StringUtils.equalsIgnoreCase(rememberPassword, "1")
				|| StringUtils.equalsIgnoreCase(rememberPassword, "Y")
				|| StringUtils.equalsIgnoreCase(rememberPassword, "true")) {
			return true;
		}
		return false;
	}

	public MailAccount jsonToObject(JSONObject jsonObject) {
		MailAccount model = new MailAccount();
		if (jsonObject.containsKey("accountType")) {
			model.setAccountType(jsonObject.getString("accountType"));
		}
		if (jsonObject.containsKey("mailAddress")) {
			model.setMailAddress(jsonObject.getString("mailAddress"));
		}
		if (jsonObject.containsKey("showName")) {
			model.setShowName(jsonObject.getString("showName"));
		}
		if (jsonObject.containsKey("username")) {
			model.setUsername(jsonObject.getString("username"));
		}
		if (jsonObject.containsKey("password")) {
			model.setPassword(jsonObject.getString("password"));
		}
		if (jsonObject.containsKey("pop3Server")) {
			model.setPop3Server(jsonObject.getString("pop3Server"));
		}
		if (jsonObject.containsKey("receivePort")) {
			model.setReceivePort(jsonObject.getInteger("receivePort"));
		}
		if (jsonObject.containsKey("smtpServer")) {
			model.setSmtpServer(jsonObject.getString("smtpServer"));
		}
		if (jsonObject.containsKey("sendPort")) {
			model.setSendPort(jsonObject.getInteger("sendPort"));
		}
		if (jsonObject.containsKey("autoReceive")) {
			model.setAutoReceive(jsonObject.getString("autoReceive"));
		}
		if (jsonObject.containsKey("rememberPassword")) {
			model.setRememberPassword(jsonObject.getString("rememberPassword"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("authFlag")) {
			model.setAuthFlag(jsonObject.getString("authFlag"));
		}
		return model;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public void setAuthFlag(boolean authFlag) {
		if (authFlag) {
			this.authFlag = "1";
		} else {
			this.authFlag = "0";
		}
	}

	public void setAuthFlag(String authFlag) {
		this.authFlag = authFlag;
	}

	public void setAutoReceive(boolean autoReceive) {
		if (autoReceive) {
			this.autoReceive = "1";
		} else {
			this.autoReceive = "0";
		}
	}

	public void setAutoReceive(String autoReceive) {
		this.autoReceive = autoReceive;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPop3Server(String pop3Server) {
		this.pop3Server = pop3Server;
	}

	public void setReceivePort(int receivePort) {
		this.receivePort = receivePort;
	}

	public void setRememberPassword(boolean rememberPassword) {
		if (rememberPassword) {
			this.rememberPassword = "1";
		} else {
			this.rememberPassword = "0";
		}
	}

	public void setRememberPassword(String rememberPassword) {
		this.rememberPassword = rememberPassword;
	}

	public void setSendPort(int sendPort) {
		this.sendPort = sendPort;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		if (accountType != null) {
			jsonObject.put("accountType", accountType);
		}
		if (mailAddress != null) {
			jsonObject.put("mailAddress", mailAddress);
		}
		if (showName != null) {
			jsonObject.put("showName", showName);
		}
		if (username != null) {
			jsonObject.put("username", username);
		}
		if (password != null) {
			jsonObject.put("password", password);
		}
		if (pop3Server != null) {
			jsonObject.put("pop3Server", pop3Server);
		}
		jsonObject.put("receivePort", receivePort);
		if (smtpServer != null) {
			jsonObject.put("smtpServer", smtpServer);
		}
		jsonObject.put("sendPort", sendPort);
		if (autoReceive != null) {
			jsonObject.put("autoReceive", autoReceive);
		}
		if (rememberPassword != null) {
			jsonObject.put("rememberPassword", rememberPassword);
		}
		jsonObject.put("locked", locked);
		if (createBy != null) {
			jsonObject.put("createBy", createBy);
		}
		if (createDate != null) {
			jsonObject.put("createDate", DateUtils.getDate(createDate));
			jsonObject.put("createDate_date", DateUtils.getDate(createDate));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(createDate));
		}
		if (authFlag != null) {
			jsonObject.put("authFlag", authFlag);
		}
		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", id);
		if (accountType != null) {
			jsonObject.put("accountType", accountType);
		}
		if (mailAddress != null) {
			jsonObject.put("mailAddress", mailAddress);
		}
		if (showName != null) {
			jsonObject.put("showName", showName);
		}
		if (username != null) {
			jsonObject.put("username", username);
		}
		if (password != null) {
			jsonObject.put("password", password);
		}
		if (pop3Server != null) {
			jsonObject.put("pop3Server", pop3Server);
		}
		jsonObject.put("receivePort", receivePort);
		if (smtpServer != null) {
			jsonObject.put("smtpServer", smtpServer);
		}
		jsonObject.put("sendPort", sendPort);
		if (autoReceive != null) {
			jsonObject.put("autoReceive", autoReceive);
		}
		if (rememberPassword != null) {
			jsonObject.put("rememberPassword", rememberPassword);
		}
		jsonObject.put("locked", locked);
		if (createBy != null) {
			jsonObject.put("createBy", createBy);
		}
		if (createDate != null) {
			jsonObject.put("createDate", DateUtils.getDate(createDate));
			jsonObject.put("createDate_date", DateUtils.getDate(createDate));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(createDate));
		}
		if (authFlag != null) {
			jsonObject.put("authFlag", authFlag);
		}
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}