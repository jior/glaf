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

package com.glaf.core.message;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.base.DataFile;
import com.glaf.core.util.DateUtils;

public class BaseMessage implements MessageProvider {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */

	protected String id;

	/**
	 * 消息编号，32位UUID
	 */
	protected String messageId;

	/**
	 * 资源编号
	 */
	protected String businessKey;

	/**
	 * 消息类型
	 */
	protected String messageType;

	/**
	 * 消息存放位置 <br>
	 * 发件箱(S)、收件箱(R)、保存箱(T)、废件箱(W)
	 */
	protected String messageBox;

	/**
	 * 消息模板编号
	 */
	protected String templateId;

	/**
	 * 主题
	 */
	protected String subject;

	/**
	 * 内容
	 */
	protected String content;

	/**
	 * 发送者编号
	 */
	protected String senderId;

	/**
	 * 发送者
	 */
	protected String senderName;

	/**
	 * 接收者编号
	 */
	protected String receiverId;

	/**
	 * 接收者
	 */
	protected String receiverName;

	protected String objectId;

	protected String objectValue;

	/**
	 * 消息状态
	 */
	protected int status;

	/**
	 * 删除标记
	 */
	protected int deleteFlag;

	/**
	 * 创建者编号
	 */
	protected String createBy;

	/**
	 * 创建日期
	 */
	protected Date createDate;

	/**
	 * 最后一次查看日期
	 */
	protected Date lastViewDate;

	/**
	 * 最后一次查看IP地址
	 */
	protected String lastViewIP;

	/**
	 * 相关附件
	 */
	protected Collection<DataFile> dataFiles = new HashSet<DataFile>();

	public BaseMessage() {

	}

	public String getContent() {
		return content;
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Collection<DataFile> getDataFiles() {
		return dataFiles;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public String getId() {
		return id;
	}

	public Date getLastViewDate() {
		return lastViewDate;
	}

	public String getLastViewIP() {
		return lastViewIP;
	}

	public String getMessageBox() {
		return messageBox;
	}

	public String getMessageId() {
		return messageId;
	}

	public String getMessageType() {
		return messageType;
	}

	public String getObjectId() {
		return objectId;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public String getSenderId() {
		return senderId;
	}

	public String getSenderName() {
		return senderName;
	}

	public int getStatus() {
		return status;
	}

	public String getSubject() {
		return subject;
	}

	public String getTemplateId() {
		return templateId;
	}

	public BaseMessage jsonToObject(JSONObject jsonObject) {
		BaseMessage model = new BaseMessage();
		if (jsonObject.containsKey("receiverId")) {
			model.setReceiverId(jsonObject.getString("receiverId"));
		}
		if (jsonObject.containsKey("templateId")) {
			model.setTemplateId(jsonObject.getString("templateId"));
		}
		if (jsonObject.containsKey("senderId")) {
			model.setSenderId(jsonObject.getString("senderId"));
		}
		if (jsonObject.containsKey("subject")) {
			model.setSubject(jsonObject.getString("subject"));
		}
		if (jsonObject.containsKey("messageBox")) {
			model.setMessageBox(jsonObject.getString("messageBox"));
		}
		if (jsonObject.containsKey("lastViewDate")) {
			model.setLastViewDate(jsonObject.getDate("lastViewDate"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("businessKey")) {
			model.setBusinessKey(jsonObject.getString("businessKey"));
		}
		if (jsonObject.containsKey("objectValue")) {
			model.setObjectValue(jsonObject.getString("objectValue"));
		}
		if (jsonObject.containsKey("objectId")) {
			model.setObjectId(jsonObject.getString("objectId"));
		}
		if (jsonObject.containsKey("lastViewIP")) {
			model.setLastViewIP(jsonObject.getString("lastViewIP"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("deleteFlag")) {
			model.setDeleteFlag(jsonObject.getInteger("deleteFlag"));
		}
		if (jsonObject.containsKey("messageId")) {
			model.setMessageId(jsonObject.getString("messageId"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
		}
		if (jsonObject.containsKey("messageType")) {
			model.setMessageType(jsonObject.getString("messageType"));
		}
		return model;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setDataFiles(Collection<DataFile> dataFiles) {
		this.dataFiles = dataFiles;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLastViewDate(Date lastViewDate) {
		this.lastViewDate = lastViewDate;
	}

	public void setLastViewIP(String lastViewIP) {
		this.lastViewIP = lastViewIP;
	}

	public void setMessageBox(String messageBox) {
		this.messageBox = messageBox;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		if (receiverId != null) {
			jsonObject.put("receiverId", receiverId);
		}
		if (templateId != null) {
			jsonObject.put("templateId", templateId);
		}
		if (senderId != null) {
			jsonObject.put("senderId", senderId);
		}
		if (subject != null) {
			jsonObject.put("subject", subject);
		}
		if (messageBox != null) {
			jsonObject.put("messageBox", messageBox);
		}
		if (lastViewDate != null) {
			jsonObject.put("lastViewDate", DateUtils.getDate(lastViewDate));
			jsonObject
					.put("lastViewDate_date", DateUtils.getDate(lastViewDate));
			jsonObject.put("lastViewDate_datetime",
					DateUtils.getDateTime(lastViewDate));
		}
		if (content != null) {
			jsonObject.put("content", content);
		}
		if (createDate != null) {
			jsonObject.put("createDate", DateUtils.getDate(createDate));
			jsonObject.put("createDate_date", DateUtils.getDate(createDate));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(createDate));
		}
		if (businessKey != null) {
			jsonObject.put("businessKey", businessKey);
		}
		if (objectValue != null) {
			jsonObject.put("objectValue", objectValue);
		}
		if (objectId != null) {
			jsonObject.put("objectId", objectId);
		}
		if (lastViewIP != null) {
			jsonObject.put("lastViewIP", lastViewIP);
		}
		if (createBy != null) {
			jsonObject.put("createBy", createBy);
		}
		jsonObject.put("deleteFlag", deleteFlag);
		if (messageId != null) {
			jsonObject.put("messageId", messageId);
		}
		jsonObject.put("status", status);
		if (messageType != null) {
			jsonObject.put("messageType", messageType);
		}
		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", id);
		if (receiverId != null) {
			jsonObject.put("receiverId", receiverId);
		}
		if (templateId != null) {
			jsonObject.put("templateId", templateId);
		}
		if (senderId != null) {
			jsonObject.put("senderId", senderId);
		}
		if (subject != null) {
			jsonObject.put("subject", subject);
		}
		if (messageBox != null) {
			jsonObject.put("messageBox", messageBox);
		}
		if (lastViewDate != null) {
			jsonObject.put("lastViewDate", DateUtils.getDate(lastViewDate));
			jsonObject
					.put("lastViewDate_date", DateUtils.getDate(lastViewDate));
			jsonObject.put("lastViewDate_datetime",
					DateUtils.getDateTime(lastViewDate));
		}
		if (content != null) {
			jsonObject.put("content", content);
		}
		if (createDate != null) {
			jsonObject.put("createDate", DateUtils.getDate(createDate));
			jsonObject.put("createDate_date", DateUtils.getDate(createDate));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(createDate));
		}
		if (businessKey != null) {
			jsonObject.put("businessKey", businessKey);
		}
		if (objectValue != null) {
			jsonObject.put("objectValue", objectValue);
		}
		if (objectId != null) {
			jsonObject.put("objectId", objectId);
		}
		if (lastViewIP != null) {
			jsonObject.put("lastViewIP", lastViewIP);
		}
		if (createBy != null) {
			jsonObject.put("createBy", createBy);
		}
		jsonObject.put("deleteFlag", deleteFlag);
		if (messageId != null) {
			jsonObject.put("messageId", messageId);
		}
		jsonObject.put("status", status);
		if (messageType != null) {
			jsonObject.put("messageType", messageType);
		}
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}