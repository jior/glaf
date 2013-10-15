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

package com.glaf.mail.query;

import java.util.Date;
import java.util.List;

import com.glaf.core.query.DataQuery;

public class MailQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String accountId;
	protected List<String> accountIds;
	protected List<String> createBys;
	protected Date lastViewDate;
	protected Date lastViewDateGreaterThanOrEqual;
	protected Date lastViewDateLessThanOrEqual;
	protected String lastViewIPLike;
	protected String mailBCCLike;
	protected String mailBox;
	protected List<String> mailBoxs;
	protected String mailCCLike;
	protected String mailFromLike;
	protected String mailId;
	protected List<String> mailIds;
	protected String mailReplyToLike;
	protected Integer mailSizeGreaterThanOrEqual;
	protected Integer mailSizeLessThanOrEqual;
	protected String mailToLike;
	protected String mailType;
	protected List<String> mailTypes;
	protected String messageId;
	protected List<String> messageIds;
	protected String nodeId;
	protected List<String> nodeIds;
	protected Date receiveDateGreaterThanOrEqual;
	protected Date receiveDateLessThanOrEqual;
	protected String receiverId;
	protected List<String> receiverIds;
	protected Integer receiveStatus;
	protected Integer receiveStatusGreaterThanOrEqual;
	protected Integer receiveStatusLessThanOrEqual;
	protected Integer retryTimes;
	protected Integer retryTimesGreaterThanOrEqual;
	protected Integer retryTimesLessThanOrEqual;
	protected Date sendDateGreaterThanOrEqual;
	protected Date sendDateLessThanOrEqual;
	protected String senderId;
	protected List<String> senderIds;
	protected Integer sendStatus;
	protected Integer sendStatusGreaterThanOrEqual;
	protected Integer sendStatusLessThanOrEqual;
	protected String subjectLike;
	protected String taskId;
	protected List<String> taskIds;
	protected String templateId;
	protected List<String> templateIds;

	public MailQuery() {

	}

	public MailQuery accountId(String accountId) {
		if (accountId == null) {
			throw new RuntimeException("accountId is null");
		}
		this.accountId = accountId;
		return this;
	}

	public MailQuery accountIds(List<String> accountIds) {
		if (accountIds == null) {
			throw new RuntimeException("accountIds is empty ");
		}
		this.accountIds = accountIds;
		return this;
	}

	public MailQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public String getAccountId() {
		return accountId;
	}

	public List<String> getAccountIds() {
		return accountIds;
	}

	public List<String> getBusinessKeys() {
		return businessKeys;
	}

	public List<String> getCreateBys() {
		return createBys;
	}

	public Date getLastViewDate() {
		return lastViewDate;
	}

	public Date getLastViewDateGreaterThanOrEqual() {
		return lastViewDateGreaterThanOrEqual;
	}

	public Date getLastViewDateLessThanOrEqual() {
		return lastViewDateLessThanOrEqual;
	}

	public String getLastViewIPLike() {
		return lastViewIPLike;
	}

	public String getMailBCCLike() {
		return mailBCCLike;
	}

	public String getMailBox() {
		return mailBox;
	}

	public List<String> getMailBoxs() {
		return mailBoxs;
	}

	public String getMailCCLike() {
		return mailCCLike;
	}

	public String getMailFromLike() {
		return mailFromLike;
	}

	public String getMailId() {
		return mailId;
	}

	public List<String> getMailIds() {
		return mailIds;
	}

	public String getMailReplyToLike() {
		return mailReplyToLike;
	}

	public Integer getMailSizeGreaterThanOrEqual() {
		return mailSizeGreaterThanOrEqual;
	}

	public Integer getMailSizeLessThanOrEqual() {
		return mailSizeLessThanOrEqual;
	}

	public String getMailToLike() {
		return mailToLike;
	}

	public String getMailType() {
		return mailType;
	}

	public List<String> getMailTypes() {
		return mailTypes;
	}

	public String getMessageId() {
		return messageId;
	}

	public List<String> getMessageIds() {
		return messageIds;
	}

	public String getNodeId() {
		return nodeId;
	}

	public List<String> getNodeIds() {
		return nodeIds;
	}

	public Date getReceiveDateGreaterThanOrEqual() {
		return receiveDateGreaterThanOrEqual;
	}

	public Date getReceiveDateLessThanOrEqual() {
		return receiveDateLessThanOrEqual;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public List<String> getReceiverIds() {
		return receiverIds;
	}

	public Integer getReceiveStatus() {
		return receiveStatus;
	}

	public Integer getReceiveStatusGreaterThanOrEqual() {
		return receiveStatusGreaterThanOrEqual;
	}

	public Integer getReceiveStatusLessThanOrEqual() {
		return receiveStatusLessThanOrEqual;
	}

	public Integer getRetryTimes() {
		return retryTimes;
	}

	public Integer getRetryTimesGreaterThanOrEqual() {
		return retryTimesGreaterThanOrEqual;
	}

	public Integer getRetryTimesLessThanOrEqual() {
		return retryTimesLessThanOrEqual;
	}

	public Date getSendDateGreaterThanOrEqual() {
		return sendDateGreaterThanOrEqual;
	}

	public Date getSendDateLessThanOrEqual() {
		return sendDateLessThanOrEqual;
	}

	public String getSenderId() {
		return senderId;
	}

	public List<String> getSenderIds() {
		return senderIds;
	}

	public Integer getSendStatus() {
		return sendStatus;
	}

	public Integer getSendStatusGreaterThanOrEqual() {
		return sendStatusGreaterThanOrEqual;
	}

	public Integer getSendStatusLessThanOrEqual() {
		return sendStatusLessThanOrEqual;
	}

	public String getSubjectLike() {
		return subjectLike;
	}

	public String getTaskId() {
		return taskId;
	}

	public List<String> getTaskIds() {
		return taskIds;
	}

	public String getTemplateId() {
		return templateId;
	}

	public List<String> getTemplateIds() {
		return templateIds;
	}

	public MailQuery lastViewDate(Date lastViewDate) {
		if (lastViewDate == null) {
			throw new RuntimeException("lastViewDate is null");
		}
		this.lastViewDate = lastViewDate;
		return this;
	}

	public MailQuery lastViewDateGreaterThanOrEqual(
			Date lastViewDateGreaterThanOrEqual) {
		if (lastViewDateGreaterThanOrEqual == null) {
			throw new RuntimeException("lastViewDate is null");
		}
		this.lastViewDateGreaterThanOrEqual = lastViewDateGreaterThanOrEqual;
		return this;
	}

	public MailQuery lastViewDateLessThanOrEqual(
			Date lastViewDateLessThanOrEqual) {
		if (lastViewDateLessThanOrEqual == null) {
			throw new RuntimeException("lastViewDate is null");
		}
		this.lastViewDateLessThanOrEqual = lastViewDateLessThanOrEqual;
		return this;
	}

	public MailQuery lastViewIPLike(String lastViewIPLike) {
		if (lastViewIPLike == null) {
			throw new RuntimeException("lastViewIP is null");
		}
		this.lastViewIPLike = lastViewIPLike;
		return this;
	}

	public MailQuery mailBCCLike(String mailBCCLike) {
		if (mailBCCLike == null) {
			throw new RuntimeException("mailBCC is null");
		}
		this.mailBCCLike = mailBCCLike;
		return this;
	}

	public MailQuery mailBox(String mailBox) {
		if (mailBox == null) {
			throw new RuntimeException("mailBox is null");
		}
		this.mailBox = mailBox;
		return this;
	}

	public MailQuery mailBoxs(List<String> mailBoxs) {
		if (mailBoxs == null) {
			throw new RuntimeException("mailBoxs is empty ");
		}
		this.mailBoxs = mailBoxs;
		return this;
	}

	public MailQuery mailCCLike(String mailCCLike) {
		if (mailCCLike == null) {
			throw new RuntimeException("mailCC is null");
		}
		this.mailCCLike = mailCCLike;
		return this;
	}

	public MailQuery mailFromLike(String mailFromLike) {
		if (mailFromLike == null) {
			throw new RuntimeException("mailFrom is null");
		}
		this.mailFromLike = mailFromLike;
		return this;
	}

	public MailQuery mailId(String mailId) {
		if (mailId == null) {
			throw new RuntimeException("mailId is null");
		}
		this.mailId = mailId;
		return this;
	}

	public MailQuery mailIds(List<String> mailIds) {
		if (mailIds == null) {
			throw new RuntimeException("mailIds is empty ");
		}
		this.mailIds = mailIds;
		return this;
	}

	public MailQuery mailReplyToLike(String mailReplyToLike) {
		if (mailReplyToLike == null) {
			throw new RuntimeException("mailReplyTo is null");
		}
		this.mailReplyToLike = mailReplyToLike;
		return this;
	}

	public MailQuery mailSizeGreaterThanOrEqual(
			Integer mailSizeGreaterThanOrEqual) {
		if (mailSizeGreaterThanOrEqual == null) {
			throw new RuntimeException("mailSize is null");
		}
		this.mailSizeGreaterThanOrEqual = mailSizeGreaterThanOrEqual;
		return this;
	}

	public MailQuery mailSizeLessThanOrEqual(Integer mailSizeLessThanOrEqual) {
		if (mailSizeLessThanOrEqual == null) {
			throw new RuntimeException("mailSize is null");
		}
		this.mailSizeLessThanOrEqual = mailSizeLessThanOrEqual;
		return this;
	}

	public MailQuery mailToLike(String mailToLike) {
		if (mailToLike == null) {
			throw new RuntimeException("mailTo is null");
		}
		this.mailToLike = mailToLike;
		return this;
	}

	public MailQuery mailType(String mailType) {
		if (mailType == null) {
			throw new RuntimeException("mailType is null");
		}
		this.mailType = mailType;
		return this;
	}

	public MailQuery mailTypes(List<String> mailTypes) {
		if (mailTypes == null) {
			throw new RuntimeException("mailTypes is empty ");
		}
		this.mailTypes = mailTypes;
		return this;
	}

	public MailQuery messageId(String messageId) {
		if (messageId == null) {
			throw new RuntimeException("messageId is null");
		}
		this.messageId = messageId;
		return this;
	}

	public MailQuery messageIds(List<String> messageIds) {
		if (messageIds == null) {
			throw new RuntimeException("messageIds is empty ");
		}
		this.messageIds = messageIds;
		return this;
	}

	public MailQuery nodeId(String nodeId) {
		if (nodeId == null) {
			throw new RuntimeException("nodeId is null");
		}
		this.nodeId = nodeId;
		return this;
	}

	public MailQuery nodeIds(List<String> nodeIds) {
		if (nodeIds == null) {
			throw new RuntimeException("nodeIds is empty ");
		}
		this.nodeIds = nodeIds;
		return this;
	}

	public MailQuery receiveDateGreaterThanOrEqual(
			Date receiveDateGreaterThanOrEqual) {
		if (receiveDateGreaterThanOrEqual == null) {
			throw new RuntimeException("receiveDate is null");
		}
		this.receiveDateGreaterThanOrEqual = receiveDateGreaterThanOrEqual;
		return this;
	}

	public MailQuery receiveDateLessThanOrEqual(Date receiveDateLessThanOrEqual) {
		if (receiveDateLessThanOrEqual == null) {
			throw new RuntimeException("receiveDate is null");
		}
		this.receiveDateLessThanOrEqual = receiveDateLessThanOrEqual;
		return this;
	}

	public MailQuery receiverId(String receiverId) {
		if (receiverId == null) {
			throw new RuntimeException("receiverId is null");
		}
		this.receiverId = receiverId;
		return this;
	}

	public MailQuery receiverIds(List<String> receiverIds) {
		if (receiverIds == null) {
			throw new RuntimeException("receiverIds is empty ");
		}
		this.receiverIds = receiverIds;
		return this;
	}

	public MailQuery receiveStatus(Integer receiveStatus) {
		if (receiveStatus == null) {
			throw new RuntimeException("receiveStatus is null");
		}
		this.receiveStatus = receiveStatus;
		return this;
	}

	public MailQuery receiveStatusGreaterThanOrEqual(
			Integer receiveStatusGreaterThanOrEqual) {
		if (receiveStatusGreaterThanOrEqual == null) {
			throw new RuntimeException("receiveStatus is null");
		}
		this.receiveStatusGreaterThanOrEqual = receiveStatusGreaterThanOrEqual;
		return this;
	}

	public MailQuery receiveStatusLessThanOrEqual(
			Integer receiveStatusLessThanOrEqual) {
		if (receiveStatusLessThanOrEqual == null) {
			throw new RuntimeException("receiveStatus is null");
		}
		this.receiveStatusLessThanOrEqual = receiveStatusLessThanOrEqual;
		return this;
	}

	public MailQuery retryTimes(Integer retryTimes) {
		if (retryTimes == null) {
			throw new RuntimeException("retryTimes is null");
		}
		this.retryTimes = retryTimes;
		return this;
	}

	public MailQuery retryTimesGreaterThanOrEqual(
			Integer retryTimesGreaterThanOrEqual) {
		if (retryTimesGreaterThanOrEqual == null) {
			throw new RuntimeException("retryTimes is null");
		}
		this.retryTimesGreaterThanOrEqual = retryTimesGreaterThanOrEqual;
		return this;
	}

	public MailQuery retryTimesLessThanOrEqual(Integer retryTimesLessThanOrEqual) {
		if (retryTimesLessThanOrEqual == null) {
			throw new RuntimeException("retryTimes is null");
		}
		this.retryTimesLessThanOrEqual = retryTimesLessThanOrEqual;
		return this;
	}

	public MailQuery sendDateGreaterThanOrEqual(Date sendDateGreaterThanOrEqual) {
		if (sendDateGreaterThanOrEqual == null) {
			throw new RuntimeException("sendDate is null");
		}
		this.sendDateGreaterThanOrEqual = sendDateGreaterThanOrEqual;
		return this;
	}

	public MailQuery sendDateLessThanOrEqual(Date sendDateLessThanOrEqual) {
		if (sendDateLessThanOrEqual == null) {
			throw new RuntimeException("sendDate is null");
		}
		this.sendDateLessThanOrEqual = sendDateLessThanOrEqual;
		return this;
	}

	public MailQuery senderId(String senderId) {
		if (senderId == null) {
			throw new RuntimeException("senderId is null");
		}
		this.senderId = senderId;
		return this;
	}

	public MailQuery senderIds(List<String> senderIds) {
		if (senderIds == null) {
			throw new RuntimeException("senderIds is empty ");
		}
		this.senderIds = senderIds;
		return this;
	}

	public MailQuery sendStatus(Integer sendStatus) {
		if (sendStatus == null) {
			throw new RuntimeException("sendStatus is null");
		}
		this.sendStatus = sendStatus;
		return this;
	}

	public MailQuery sendStatusGreaterThanOrEqual(
			Integer sendStatusGreaterThanOrEqual) {
		if (sendStatusGreaterThanOrEqual == null) {
			throw new RuntimeException("sendStatus is null");
		}
		this.sendStatusGreaterThanOrEqual = sendStatusGreaterThanOrEqual;
		return this;
	}

	public MailQuery sendStatusLessThanOrEqual(Integer sendStatusLessThanOrEqual) {
		if (sendStatusLessThanOrEqual == null) {
			throw new RuntimeException("sendStatus is null");
		}
		this.sendStatusLessThanOrEqual = sendStatusLessThanOrEqual;
		return this;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public void setAccountIds(List<String> accountIds) {
		this.accountIds = accountIds;
	}

	public void setCreateBys(List<String> createBys) {
		this.createBys = createBys;
	}

	public void setLastViewDate(Date lastViewDate) {
		this.lastViewDate = lastViewDate;
	}

	public void setLastViewDateGreaterThanOrEqual(
			Date lastViewDateGreaterThanOrEqual) {
		this.lastViewDateGreaterThanOrEqual = lastViewDateGreaterThanOrEqual;
	}

	public void setLastViewDateLessThanOrEqual(Date lastViewDateLessThanOrEqual) {
		this.lastViewDateLessThanOrEqual = lastViewDateLessThanOrEqual;
	}

	public void setLastViewIPLike(String lastViewIPLike) {
		this.lastViewIPLike = lastViewIPLike;
	}

	public void setMailBCCLike(String mailBCCLike) {
		this.mailBCCLike = mailBCCLike;
	}

	public void setMailBox(String mailBox) {
		this.mailBox = mailBox;
	}

	public void setMailBoxs(List<String> mailBoxs) {
		this.mailBoxs = mailBoxs;
	}

	public void setMailCCLike(String mailCCLike) {
		this.mailCCLike = mailCCLike;
	}

	public void setMailFromLike(String mailFromLike) {
		this.mailFromLike = mailFromLike;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public void setMailIds(List<String> mailIds) {
		this.mailIds = mailIds;
	}

	public void setMailReplyToLike(String mailReplyToLike) {
		this.mailReplyToLike = mailReplyToLike;
	}

	public void setMailSizeGreaterThanOrEqual(Integer mailSizeGreaterThanOrEqual) {
		this.mailSizeGreaterThanOrEqual = mailSizeGreaterThanOrEqual;
	}

	public void setMailSizeLessThanOrEqual(Integer mailSizeLessThanOrEqual) {
		this.mailSizeLessThanOrEqual = mailSizeLessThanOrEqual;
	}

	public void setMailToLike(String mailToLike) {
		this.mailToLike = mailToLike;
	}

	public void setMailType(String mailType) {
		this.mailType = mailType;
	}

	public void setMailTypes(List<String> mailTypes) {
		this.mailTypes = mailTypes;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public void setMessageIds(List<String> messageIds) {
		this.messageIds = messageIds;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public void setNodeIds(List<String> nodeIds) {
		this.nodeIds = nodeIds;
	}

	public void setReceiveDateGreaterThanOrEqual(
			Date receiveDateGreaterThanOrEqual) {
		this.receiveDateGreaterThanOrEqual = receiveDateGreaterThanOrEqual;
	}

	public void setReceiveDateLessThanOrEqual(Date receiveDateLessThanOrEqual) {
		this.receiveDateLessThanOrEqual = receiveDateLessThanOrEqual;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public void setReceiverIds(List<String> receiverIds) {
		this.receiverIds = receiverIds;
	}

	public void setReceiveStatus(Integer receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	public void setReceiveStatusGreaterThanOrEqual(
			Integer receiveStatusGreaterThanOrEqual) {
		this.receiveStatusGreaterThanOrEqual = receiveStatusGreaterThanOrEqual;
	}

	public void setReceiveStatusLessThanOrEqual(
			Integer receiveStatusLessThanOrEqual) {
		this.receiveStatusLessThanOrEqual = receiveStatusLessThanOrEqual;
	}

	public void setRetryTimes(Integer retryTimes) {
		this.retryTimes = retryTimes;
	}

	public void setRetryTimesGreaterThanOrEqual(
			Integer retryTimesGreaterThanOrEqual) {
		this.retryTimesGreaterThanOrEqual = retryTimesGreaterThanOrEqual;
	}

	public void setRetryTimesLessThanOrEqual(Integer retryTimesLessThanOrEqual) {
		this.retryTimesLessThanOrEqual = retryTimesLessThanOrEqual;
	}

	public void setSendDateGreaterThanOrEqual(Date sendDateGreaterThanOrEqual) {
		this.sendDateGreaterThanOrEqual = sendDateGreaterThanOrEqual;
	}

	public void setSendDateLessThanOrEqual(Date sendDateLessThanOrEqual) {
		this.sendDateLessThanOrEqual = sendDateLessThanOrEqual;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public void setSenderIds(List<String> senderIds) {
		this.senderIds = senderIds;
	}

	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}

	public void setSendStatusGreaterThanOrEqual(
			Integer sendStatusGreaterThanOrEqual) {
		this.sendStatusGreaterThanOrEqual = sendStatusGreaterThanOrEqual;
	}

	public void setSendStatusLessThanOrEqual(Integer sendStatusLessThanOrEqual) {
		this.sendStatusLessThanOrEqual = sendStatusLessThanOrEqual;
	}

	public void setSubjectLike(String subjectLike) {
		this.subjectLike = subjectLike;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setTaskIds(List<String> taskIds) {
		this.taskIds = taskIds;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public void setTemplateIds(List<String> templateIds) {
		this.templateIds = templateIds;
	}

	public MailQuery subjectLike(String subjectLike) {
		if (subjectLike == null) {
			throw new RuntimeException("subject is null");
		}
		this.subjectLike = subjectLike;
		return this;
	}

	public MailQuery taskId(String taskId) {
		if (taskId == null) {
			throw new RuntimeException("taskId is null");
		}
		this.taskId = taskId;
		return this;
	}

	public MailQuery taskIds(List<String> taskIds) {
		if (taskIds == null) {
			throw new RuntimeException("taskIds is empty ");
		}
		this.taskIds = taskIds;
		return this;
	}

	public MailQuery templateId(String templateId) {
		if (templateId == null) {
			throw new RuntimeException("templateId is null");
		}
		this.templateId = templateId;
		return this;
	}

	public MailQuery templateIds(List<String> templateIds) {
		if (templateIds == null) {
			throw new RuntimeException("templateIds is empty ");
		}
		this.templateIds = templateIds;
		return this;
	}

}