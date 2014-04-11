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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Entity
@Table(name = "SYS_MAIL_COUNT")
public class MailCount implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	@Column(name = "TYPE_", length = 50)
	protected String type;

	@Column(name = "TASKID_", length = 50)
	protected String taskId;

	@Column(name = "ACCOUNTID_", length = 50)
	protected String accountId;

	@Column(name = "RECEIVESTATUS_")
	protected int receiveStatus;

	@Column(name = "SENDSTATUS_")
	protected int sendStatus;

	@Column(name = "QTY_")
	protected int qty;

	@Column(name = "LASTMODIFIED_")
	protected long lastModified;

	public MailCount() {

	}

	public String getAccountId() {
		return accountId;
	}

	public String getId() {
		return id;
	}

	public long getLastModified() {
		return lastModified;
	}

	public int getQty() {
		return qty;
	}

	public int getReceiveStatus() {
		return receiveStatus;
	}

	public int getSendStatus() {
		return sendStatus;
	}

	public String getTaskId() {
		return taskId;
	}

	public String getType() {
		return type;
	}

	public MailCount jsonToObject(JSONObject jsonObject) {
		MailCount model= new MailCount();
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("taskId")) {
			model.setTaskId(jsonObject.getString("taskId"));
		}
		if (jsonObject.containsKey("accountId")) {
			model.setAccountId(jsonObject.getString("accountId"));
		}
		if (jsonObject.containsKey("receiveStatus")) {
			model.setReceiveStatus(jsonObject.getInteger("receiveStatus"));
		}
		if (jsonObject.containsKey("sendStatus")) {
			model.setSendStatus(jsonObject.getInteger("sendStatus"));
		}
		if (jsonObject.containsKey("qty")) {
			model.setQty(jsonObject.getInteger("qty"));
		}
		if (jsonObject.containsKey("lastModified")) {
			model.setLastModified(jsonObject.getLong("lastModified"));
		}
	    return model;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public void setReceiveStatus(int receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	public void setSendStatus(int sendStatus) {
		this.sendStatus = sendStatus;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setType(String type) {
		this.type = type;
	}

     
	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		if (type != null) {
			jsonObject.put("type", type);
		} 
		if (taskId != null) {
			jsonObject.put("taskId", taskId);
		} 
		if (accountId != null) {
			jsonObject.put("accountId", accountId);
		} 
        jsonObject.put("receiveStatus", receiveStatus);
        jsonObject.put("sendStatus", sendStatus);
        jsonObject.put("qty", qty);
        jsonObject.put("lastModified", lastModified);
		return jsonObject;
	}


	public ObjectNode toObjectNode(){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", id);
                if (type != null) {
                     jsonObject.put("type", type);
                } 
                if (taskId != null) {
                     jsonObject.put("taskId", taskId);
                } 
                if (accountId != null) {
                     jsonObject.put("accountId", accountId);
                } 
                jsonObject.put("receiveStatus", receiveStatus);
                jsonObject.put("sendStatus", sendStatus);
                jsonObject.put("qty", qty);
                jsonObject.put("lastModified", lastModified);
                return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}