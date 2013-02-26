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

package com.glaf.core.mail;

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

import org.apache.commons.lang.builder.ToStringBuilder;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import com.glaf.core.base.DataFile;
import com.glaf.core.message.MessageProvider;

@Entity
@Table(name = "SYS_MAIL")
public class Mail implements MessageProvider {
	private static final long serialVersionUID = 1L;

	/**
	 * ����
	 */
	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	/**
	 * �ڵ���
	 */
	@Column(name = "NODEID_", length = 50)
	protected String nodeId;

	/**
	 * �ʼ����
	 */
	@Column(name = "MAILID_", length = 50)
	protected String mailId;

	/**
	 * Message-ID
	 */
	@Column(name = "MESSAGEID_", length = 50)
	protected String messageId;

	/**
	 * ��Դ���
	 */
	@Column(name = "RESOURCEID_", length = 50)
	protected String resourceId;

	/**
	 * ������
	 */
	@Column(name = "TASKID_", length = 50)
	protected String taskId;

	/**
	 * �ʼ��ʺ�
	 */
	@Column(name = "ACCOUNTID_", length = 50)
	protected String accountId;

	/**
	 * �����߱��
	 */
	@Column(name = "SENDERID_", length = 50)
	protected String senderId;

	/**
	 * ������
	 */
	@Transient
	protected String senderName;

	/**
	 * �����߱��
	 */
	@Column(name = "RECEIVERID_", length = 50)
	protected String receiverId;

	/**
	 * ������
	 */
	@Transient
	protected String receiverName;

	/**
	 * ����
	 */
	@Column(name = "SUBJECT_")
	protected String subject;

	/**
	 * ����
	 */
	@Lob
	@Column(name = "CONTENT_")
	protected String content;

	/**
	 * ģ����
	 */
	@Column(name = "TEMPLATEID_", length = 50)
	protected String templateId;

	/**
	 * ������
	 */
	@Column(name = "MAILFROM_", length = 100)
	protected String mailFrom;

	/**
	 * �ռ���
	 */
	@Column(name = "MAILTO_", length = 200)
	protected String mailTo;

	/**
	 * ������
	 */
	@Column(name = "MAILCC_", length = 200)
	protected String mailCC;

	/**
	 * ������
	 */
	@Column(name = "MAILBCC_", length = 200)
	protected String mailBCC;

	/**
	 * �ظ���ַ
	 */
	@Column(name = "MAILREPLYTO_", length = 100)
	protected String mailReplyTo;

	/**
	 * ������(S)���ռ���(R)���ݸ���(D)��������(R)���ϼ���(W)
	 */
	@Column(name = "MAILBOX_", length = 50)
	protected String mailBox;

	/**
	 * �ʼ�����
	 */
	@Column(name = "MAILTYPE_", length = 50)
	protected String mailType;

	/**
	 * �ʼ���С
	 */
	@Column(name = "MAILSIZE_")
	protected int mailSize;

	/**
	 * ��������
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SENDDATE_")
	protected Date sendDate;

	/**
	 * �ʼ�״̬
	 */
	@Column(name = "STATUS_")
	protected int status;

	/**
	 * ����״̬
	 */
	@Column(name = "SENDSTATUS_")
	protected int sendStatus;

	/**
	 * ���Դ���
	 */
	@Column(name = "RETRYTIMES_")
	protected int retryTimes;

	/**
	 * ��������
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECEIVEDATE_")
	protected Date receiveDate;

	/**
	 * �ʼ�����״̬
	 */
	@Column(name = "RECEIVESTATUS_")
	protected int receiveStatus;

	/**
	 * �����߱��
	 */
	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	/**
	 * ��������
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_", updatable = false)
	protected Date createDate;

	/**
	 * ���һ�β鿴����
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LASTVIEWDATE_")
	protected Date lastViewDate;

	/**
	 * ���һ�β鿴IP��ַ
	 */
	@Column(name = "LASTVIEWIP_", length = 100)
	protected String lastViewIP;

	/**
	 * ��ظ���
	 */
	@Transient
	protected Collection<DataFile> dataFiles = new HashSet<DataFile>();

	public Mail() {

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

	public String getResourceId() {
		return resourceId;
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

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
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

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
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

}