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
@Table(name = "MX_MAIL")
public class Mail implements MessageProvider {
	private static final long serialVersionUID = 1L;

	/**
	 * ����
	 */
	protected String id;

	/**
	 * �ڵ���
	 */
	protected String nodeId;

	/**
	 * �ʼ����
	 */
	protected String mailId;

	/**
	 * Message-ID
	 */
	protected String messageId;

	/**
	 * ��Դ���
	 */
	protected String resourceId;

	/**
	 * ������
	 */
	protected String taskId;

	/**
	 * �ʼ��ʺ�
	 */
	protected String accountId;

	/**
	 * �����߱��
	 */
	protected String senderId;

	/**
	 * ������
	 */
	protected String senderName;

	/**
	 * �����߱��
	 */
	protected String receiverId;

	/**
	 * ������
	 */
	protected String receiverName;

	/**
	 * ����
	 */
	protected String subject;

	/**
	 * ����
	 */
	protected String content;

	/**
	 * ģ����
	 */
	protected String templateId;

	/**
	 * ������
	 */
	protected String mailFrom;

	/**
	 * �ռ���
	 */
	protected String mailTo;

	/**
	 * ������
	 */
	protected String mailCC;

	/**
	 * ������
	 */
	protected String mailBCC;

	/**
	 * �ظ���ַ
	 */
	protected String mailReplyTo;

	/**
	 * ������(S)���ռ���(R)���ݸ���(D)��������(R)���ϼ���(W)
	 */
	protected String mailBox;

	/**
	 * �ʼ�����
	 */
	protected String mailType;

	/**
	 * �ʼ���С
	 */
	protected int mailSize;

	/**
	 * ��������
	 */
	protected Date sendDate;

	/**
	 * �ʼ�״̬
	 */
	protected int status;

	/**
	 * ����״̬
	 */
	protected int sendStatus;

	/**
	 * ���Դ���
	 */
	protected int retryTimes;

	/**
	 * ��������
	 */
	protected Date receiveDate;

	/**
	 * �ʼ�����״̬
	 */
	protected int receiveStatus;

	/**
	 * �����߱��
	 */
	protected String createBy;

	/**
	 * ��������
	 */
	protected Date createDate;

	/**
	 * ���һ�β鿴����
	 */
	protected Date lastViewDate;

	/**
	 * ���һ�β鿴IP��ַ
	 */
	protected String lastViewIP;

	/**
	 * ��ظ���
	 */
	protected Collection<DataFile> dataFiles = new HashSet<DataFile>();

	public Mail() {

	}

	@Column(name = "ACCOUNTID_", length = 50)
	public String getAccountId() {
		return accountId;
	}

	@Lob
	@Column(name = "CONTENT_")
	public String getContent() {
		return content;
	}

	@Column(name = "CREATEBY_", length = 50, updatable = false)
	public String getCreateBy() {
		return createBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_", updatable = false)
	public Date getCreateDate() {
		return createDate;
	}

	@Transient
	public Collection<DataFile> getDataFiles() {
		return dataFiles;
	}

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	public String getId() {
		return id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LASTVIEWDATE_")
	public Date getLastViewDate() {
		return lastViewDate;
	}

	@Column(name = "LASTVIEWIP_", length = 50)
	public String getLastViewIP() {
		return lastViewIP;
	}

	@Column(name = "MAILBCC_")
	public String getMailBCC() {
		return mailBCC;
	}

	@Column(name = "MAILBOX_", length = 50)
	public String getMailBox() {
		return mailBox;
	}

	@Column(name = "MAILCC_")
	public String getMailCC() {
		return mailCC;
	}

	@Column(name = "MAILFROM_", length = 100)
	public String getMailFrom() {
		return mailFrom;
	}

	@Column(name = "MAILID_", length = 50, updatable = false)
	public String getMailId() {
		return mailId;
	}

	@Column(name = "MAILREPLYTO_", length = 100)
	public String getMailReplyTo() {
		return mailReplyTo;
	}

	@Column(name = "MAILSIZE_")
	public int getMailSize() {
		return mailSize;
	}

	@Column(name = "MAILTO_", length = 100)
	public String getMailTo() {
		return mailTo;
	}

	@Column(name = "MAILTYPE_", length = 50)
	public String getMailType() {
		return mailType;
	}

	@Column(name = "MESSAGEID_", length = 50, updatable = false)
	public String getMessageId() {
		return messageId;
	}

	@Column(name = "NODEID_", length = 50)
	public String getNodeId() {
		return nodeId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECEIVEDATE_")
	public Date getReceiveDate() {
		return receiveDate;
	}

	@Column(name = "RECEIVERID_", length = 50)
	public String getReceiverId() {
		return receiverId;
	}

	@Transient
	public String getReceiverName() {
		return receiverName;
	}

	@Column(name = "RECEIVESTATUS_")
	public int getReceiveStatus() {
		return receiveStatus;
	}

	@Column(name = "RESOURCEID_", length = 50, updatable = false)
	public String getResourceId() {
		return resourceId;
	}

	@Column(name = "RETRYTIMES_")
	public int getRetryTimes() {
		return retryTimes;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SENDDATE_")
	public Date getSendDate() {
		return sendDate;
	}

	@Column(name = "SENDERID_", length = 50)
	public String getSenderId() {
		return senderId;
	}

	@Transient
	public String getSenderName() {
		return senderName;
	}

	@Column(name = "SENDSTATUS_")
	public int getSendStatus() {
		return sendStatus;
	}

	@Column(name = "STATUS_")
	public int getStatus() {
		return status;
	}

	@Column(name = "SUBJECT_")
	public String getSubject() {
		return subject;
	}

	@Column(name = "TASKID_", length = 50)
	public String getTaskId() {
		return taskId;
	}

	@Column(name = "TEMPLATEID_", length = 50)
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