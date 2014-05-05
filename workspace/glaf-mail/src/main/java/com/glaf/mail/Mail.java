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

package com.glaf.mail;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.DataFile;
import com.glaf.core.message.MessageProvider;

@Entity
@Table(name = "SYS_MAIL")
public class Mail implements MessageProvider {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	/**
	 * 节点编号
	 */
	@Column(name = "NODEID_", length = 50)
	protected String nodeId;

	/**
	 * 邮件编号
	 */
	@Column(name = "MAILID_", length = 50)
	protected String mailId;

	/**
	 * Message-ID
	 */
	@Column(name = "MESSAGEID_", length = 250)
	protected String messageId;

	/**
	 * 资源编号
	 */
	@Column(name = "RESOURCEID_", length = 50)
	protected String businessKey;

	@Column(name = "USERNAME_", length = 100)
	protected String username;

	/**
	 * 任务编号
	 */
	@Column(name = "TASKID_", length = 50)
	protected String taskId;

	/**
	 * 邮件帐号
	 */
	@Column(name = "ACCOUNTID_", length = 50)
	protected String accountId;

	/**
	 * 发送者编号
	 */
	@Column(name = "SENDERID_", length = 50)
	protected String senderId;

	/**
	 * 发送者
	 */
	@Transient
	protected String senderName;

	/**
	 * 接收者编号
	 */
	@Column(name = "RECEIVERID_", length = 50)
	protected String receiverId;

	/**
	 * 接收者
	 */
	@Transient
	protected String receiverName;

	/**
	 * 主题
	 */
	@Column(name = "SUBJECT_")
	protected String subject;

	/**
	 * 内容
	 */
	@Lob
	@Column(name = "CONTENT_")
	protected String content;

	/**
	 * 内容
	 */
	@Lob
	@Column(name = "HTML_")
	protected String html;

	/**
	 * 模板编号
	 */
	@Column(name = "TEMPLATEID_", length = 50)
	protected String templateId;

	/**
	 * 发件人
	 */
	@Column(name = "MAILFROM_", length = 100)
	protected String mailFrom;

	/**
	 * 收件人
	 */
	@Column(name = "MAILTO_", length = 200)
	protected String mailTo;

	/**
	 * 抄送人
	 */
	@Column(name = "MAILCC_", length = 200)
	protected String mailCC;

	/**
	 * 暗送人
	 */
	@Column(name = "MAILBCC_", length = 200)
	protected String mailBCC;

	/**
	 * 回复地址
	 */
	@Column(name = "MAILREPLYTO_", length = 100)
	protected String mailReplyTo;

	/**
	 * 发件箱(S)、收件箱(R)、草稿箱(D)、垃圾箱(R)、废件箱(W)
	 */
	@Column(name = "MAILBOX_", length = 50)
	protected String mailBox;

	/**
	 * 邮件类型
	 */
	@Column(name = "MAILTYPE_", length = 50)
	protected String mailType;

	/**
	 * 邮件大小
	 */
	@Column(name = "MAILSIZE_")
	protected int mailSize;

	/**
	 * 发送日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SENDDATE_")
	protected Date sendDate;

	/**
	 * 邮件状态
	 */
	@Column(name = "STATUS_")
	protected int status;

	/**
	 * 发送状态
	 */
	@Column(name = "SENDSTATUS_")
	protected int sendStatus;

	/**
	 * 重试次数
	 */
	@Column(name = "RETRYTIMES_")
	protected int retryTimes;

	/**
	 * 接收日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECEIVEDATE_")
	protected Date receiveDate;

	/**
	 * 邮件接收状态
	 */
	@Column(name = "RECEIVESTATUS_")
	protected int receiveStatus;

	/**
	 * 创建者编号
	 */
	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	/**
	 * 创建日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_", updatable = false)
	protected Date createDate;

	/**
	 * 最后一次查看日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LASTVIEWDATE_")
	protected Date lastViewDate;

	/**
	 * 最后一次查看IP地址
	 */
	@Column(name = "LASTVIEWIP_", length = 100)
	protected String lastViewIP;

	/**
	 * 相关附件
	 */
	@Transient
	protected Collection<DataFile> dataFiles = new HashSet<DataFile>();

	public Mail() {

	}

	public void addFile(DataFile file) {
		if (dataFiles == null) {
			dataFiles = new HashSet<DataFile>();
		}
		dataFiles.add(file);
	}

	public String getAccountId() {
		return accountId;
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

	public String getHtml() {
		return html;
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

	public String getMailBCC() {
		return mailBCC;
	}

	public String getMailBox() {
		return mailBox;
	}

	public String getMailCC() {
		return mailCC;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public String getMailId() {
		return mailId;
	}

	public String getMailReplyTo() {
		return mailReplyTo;
	}

	public int getMailSize() {
		return mailSize;
	}

	public String getMailTo() {
		return mailTo;
	}

	public String getMailType() {
		return mailType;
	}

	public String getMessageId() {
		return messageId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public int getReceiveStatus() {
		return receiveStatus;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public int getRetryTimes() {
		return retryTimes;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public String getSenderId() {
		return senderId;
	}

	public String getSenderName() {
		return senderName;
	}

	public int getSendStatus() {
		return sendStatus;
	}

	public int getStatus() {
		return status;
	}

	public String getSubject() {
		return subject;
	}

	public String getTaskId() {
		return taskId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public String getUsername() {
		return username;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
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

	public void setHtml(String html) {
		this.html = html;
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

	public void setMailBCC(String mailBCC) {
		this.mailBCC = mailBCC;
	}

	public void setMailBox(String mailBox) {
		this.mailBox = mailBox;
	}

	public void setMailCC(String mailCC) {
		this.mailCC = mailCC;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public void setMailReplyTo(String mailReplyTo) {
		this.mailReplyTo = mailReplyTo;
	}

	public void setMailSize(int mailSize) {
		this.mailSize = mailSize;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

	public void setMailType(String mailType) {
		this.mailType = mailType;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public void setReceiveStatus(int receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public void setRetryTimes(int retryTimes) {
		this.retryTimes = retryTimes;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public void setSendStatus(int sendStatus) {
		this.sendStatus = sendStatus;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public JSONObject toJsonObject() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("mailId", mailId);
		jsonObject.put("senderId", senderId);
		jsonObject.put("receiverId", receiverId);
		jsonObject.put("subject", subject);
		jsonObject.put("createDate", createDate);
		if (sendDate != null) {
			jsonObject.put("sendDate", sendDate);
		}
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}