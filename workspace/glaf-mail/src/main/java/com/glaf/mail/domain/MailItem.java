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
@Table(name = "SYS_MAIL_ITEM")
public class MailItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	@Column(name = "ACCOUNTID_", length = 50)
	protected String accountId;

	@Column(name = "TASKID_", length = 50)
	protected String taskId;

	@Column(name = "MAILTO_", length = 100)
	protected String mailTo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SENDDATE_")
	protected Date sendDate;

	@Column(name = "SENDSTATUS_")
	protected int sendStatus;

	@Column(name = "RETRYTIMES_")
	protected int retryTimes;

	@Column(name = "RECEIVEIP_", length = 50)
	protected String receiveIP;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECEIVEDATE_")
	protected Date receiveDate;

	@Column(name = "CONTENTTYPE_", length = 200)
	protected String contentType;

	@Column(name = "CLIENTOS_", length = 50)
	protected String clientOS;

	@Column(name = "BROWSER_", length = 50)
	protected String browser;

	@Column(name = "RECEIVESTATUS_")
	protected int receiveStatus;

	@Column(name = "LASTMODIFIED_")
	protected long lastModified;

	@javax.persistence.Transient
	protected String tableName;

	public MailItem() {

	}

	public String getAccountId() {
		return accountId;
	}

	public String getBrowser() {
		return browser;
	}

	public String getClientOS() {
		return clientOS;
	}

	public String getContentType() {
		return contentType;
	}

	public String getId() {
		return id;
	}

	public long getLastModified() {
		return lastModified;
	}

	public String getMailTo() {
		return mailTo;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public String getReceiveIP() {
		return receiveIP;
	}

	public int getReceiveStatus() {
		return receiveStatus;
	}

	public int getRetryTimes() {
		return retryTimes;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public int getSendStatus() {
		return sendStatus;
	}

	public String getTableName() {
		return tableName;
	}

	public String getTaskId() {
		return taskId;
	}

	public MailItem jsonToObject(JSONObject jsonObject) {
		MailItem model = new MailItem();
		if (jsonObject.containsKey("accountId")) {
			model.setAccountId(jsonObject.getString("accountId"));
		}
		if (jsonObject.containsKey("taskId")) {
			model.setTaskId(jsonObject.getString("taskId"));
		}
		if (jsonObject.containsKey("mailTo")) {
			model.setMailTo(jsonObject.getString("mailTo"));
		}
		if (jsonObject.containsKey("sendDate")) {
			model.setSendDate(jsonObject.getDate("sendDate"));
		}
		if (jsonObject.containsKey("sendStatus")) {
			model.setSendStatus(jsonObject.getInteger("sendStatus"));
		}
		if (jsonObject.containsKey("retryTimes")) {
			model.setRetryTimes(jsonObject.getInteger("retryTimes"));
		}
		if (jsonObject.containsKey("receiveIP")) {
			model.setReceiveIP(jsonObject.getString("receiveIP"));
		}
		if (jsonObject.containsKey("receiveDate")) {
			model.setReceiveDate(jsonObject.getDate("receiveDate"));
		}
		if (jsonObject.containsKey("contentType")) {
			model.setContentType(jsonObject.getString("contentType"));
		}
		if (jsonObject.containsKey("clientOS")) {
			model.setClientOS(jsonObject.getString("clientOS"));
		}
		if (jsonObject.containsKey("browser")) {
			model.setBrowser(jsonObject.getString("browser"));
		}
		if (jsonObject.containsKey("receiveStatus")) {
			model.setReceiveStatus(jsonObject.getInteger("receiveStatus"));
		}
		if (jsonObject.containsKey("lastModified")) {
			model.setLastModified(jsonObject.getLong("lastModified"));
		}
		return model;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public void setClientOS(String clientOS) {
		this.clientOS = clientOS;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public void setReceiveIP(String receiveIP) {
		this.receiveIP = receiveIP;
	}

	public void setReceiveStatus(int receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	public void setRetryTimes(int retryTimes) {
		this.retryTimes = retryTimes;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public void setSendStatus(int sendStatus) {
		this.sendStatus = sendStatus;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		if (accountId != null) {
			jsonObject.put("accountId", accountId);
		}
		if (taskId != null) {
			jsonObject.put("taskId", taskId);
		}
		if (mailTo != null) {
			jsonObject.put("mailTo", mailTo);
		}
		if (sendDate != null) {
			jsonObject.put("sendDate", DateUtils.getDate(sendDate));
			jsonObject.put("sendDate_date", DateUtils.getDate(sendDate));
			jsonObject
					.put("sendDate_datetime", DateUtils.getDateTime(sendDate));
		}
		jsonObject.put("sendStatus", sendStatus);
		jsonObject.put("retryTimes", retryTimes);
		if (receiveIP != null) {
			jsonObject.put("receiveIP", receiveIP);
		}
		if (receiveDate != null) {
			jsonObject.put("receiveDate", DateUtils.getDate(receiveDate));
			jsonObject.put("receiveDate_date", DateUtils.getDate(receiveDate));
			jsonObject.put("receiveDate_datetime",
					DateUtils.getDateTime(receiveDate));
		}
		if (contentType != null) {
			jsonObject.put("contentType", contentType);
		}
		if (clientOS != null) {
			jsonObject.put("clientOS", clientOS);
		}
		if (browser != null) {
			jsonObject.put("browser", browser);
		}
		jsonObject.put("receiveStatus", receiveStatus);
		jsonObject.put("lastModified", lastModified);
		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", id);
		if (accountId != null) {
			jsonObject.put("accountId", accountId);
		}
		if (taskId != null) {
			jsonObject.put("taskId", taskId);
		}
		if (mailTo != null) {
			jsonObject.put("mailTo", mailTo);
		}
		if (sendDate != null) {
			jsonObject.put("sendDate", DateUtils.getDate(sendDate));
			jsonObject.put("sendDate_date", DateUtils.getDate(sendDate));
			jsonObject
					.put("sendDate_datetime", DateUtils.getDateTime(sendDate));
		}
		jsonObject.put("sendStatus", sendStatus);
		jsonObject.put("retryTimes", retryTimes);
		if (receiveIP != null) {
			jsonObject.put("receiveIP", receiveIP);
		}
		if (receiveDate != null) {
			jsonObject.put("receiveDate", DateUtils.getDate(receiveDate));
			jsonObject.put("receiveDate_date", DateUtils.getDate(receiveDate));
			jsonObject.put("receiveDate_datetime",
					DateUtils.getDateTime(receiveDate));
		}
		if (contentType != null) {
			jsonObject.put("contentType", contentType);
		}
		if (clientOS != null) {
			jsonObject.put("clientOS", clientOS);
		}
		if (browser != null) {
			jsonObject.put("browser", browser);
		}
		jsonObject.put("receiveStatus", receiveStatus);
		jsonObject.put("lastModified", lastModified);
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}