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

package com.glaf.core.query;

import java.util.Date;
import java.util.List;

public class MessageQuery extends BaseQuery {
	private static final long serialVersionUID = 1L;
	protected String businessKey;
	protected String contentLike;
	protected List<String> createBys;
	protected Date lastViewDateGreaterThanOrEqual;
	protected Date lastViewDateLessThanOrEqual;
	protected String lastViewIPLike;
	protected String messageBox;
	protected List<String> messageBoxs;
	protected String messageId;
	protected List<String> messageIds;
	protected String messageType;
	protected List<String> messageTypes;
	protected List<String> objectIds;
	protected List<String> objectValues;
	protected String receiverId;
	protected List<String> receiverIds;
	protected String senderId;
	protected List<String> senderIds;
	protected String subjectLike;
	protected String templateId;
	protected List<String> templateIds;

	public MessageQuery() {

	}

	public MessageQuery businessKey(String businessKey) {
		if (businessKey == null) {
			throw new RuntimeException("businessKey is null");
		}
		this.businessKey = businessKey;
		return this;
	}

	public MessageQuery contentLike(String contentLike) {
		if (contentLike == null) {
			throw new RuntimeException("content is null");
		}
		this.contentLike = contentLike;
		return this;
	}

	public MessageQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public String getContentLike() {
		return contentLike;
	}

	public List<String> getCreateBys() {
		return createBys;
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

	public String getMessageBox() {
		return messageBox;
	}

	public List<String> getMessageBoxs() {
		return messageBoxs;
	}

	public String getMessageId() {
		return messageId;
	}

	public List<String> getMessageIds() {
		return messageIds;
	}

	public String getMessageType() {
		return messageType;
	}

	public List<String> getMessageTypes() {
		return messageTypes;
	}

	public List<String> getObjectIds() {
		return objectIds;
	}

	public List<String> getObjectValues() {
		return objectValues;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public List<String> getReceiverIds() {
		return receiverIds;
	}

	public String getSenderId() {
		return senderId;
	}

	public List<String> getSenderIds() {
		return senderIds;
	}

	public String getSubjectLike() {
		return subjectLike;
	}

	public String getTemplateId() {
		return templateId;
	}

	public List<String> getTemplateIds() {
		return templateIds;
	}

	public MessageQuery lastViewDateGreaterThanOrEqual(
			Date lastViewDateGreaterThanOrEqual) {
		if (lastViewDateGreaterThanOrEqual == null) {
			throw new RuntimeException("lastViewDate is null");
		}
		this.lastViewDateGreaterThanOrEqual = lastViewDateGreaterThanOrEqual;
		return this;
	}

	public MessageQuery lastViewDateLessThanOrEqual(
			Date lastViewDateLessThanOrEqual) {
		if (lastViewDateLessThanOrEqual == null) {
			throw new RuntimeException("lastViewDate is null");
		}
		this.lastViewDateLessThanOrEqual = lastViewDateLessThanOrEqual;
		return this;
	}

	public MessageQuery lastViewIPLike(String lastViewIPLike) {
		if (lastViewIPLike == null) {
			throw new RuntimeException("lastViewIP is null");
		}
		this.lastViewIPLike = lastViewIPLike;
		return this;
	}

	public MessageQuery messageBox(String messageBox) {
		if (messageBox == null) {
			throw new RuntimeException("messageBox is null");
		}
		this.messageBox = messageBox;
		return this;
	}

	public MessageQuery messageBoxs(List<String> messageBoxs) {
		if (messageBoxs == null) {
			throw new RuntimeException("messageBoxs is empty ");
		}
		this.messageBoxs = messageBoxs;
		return this;
	}

	public MessageQuery messageId(String messageId) {
		if (messageId == null) {
			throw new RuntimeException("messageId is null");
		}
		this.messageId = messageId;
		return this;
	}

	public MessageQuery messageIds(List<String> messageIds) {
		if (messageIds == null) {
			throw new RuntimeException("messageIds is empty ");
		}
		this.messageIds = messageIds;
		return this;
	}

	public MessageQuery messageType(String messageType) {
		if (messageType == null) {
			throw new RuntimeException("messageType is null");
		}
		this.messageType = messageType;
		return this;
	}

	public MessageQuery messageTypes(List<String> messageTypes) {
		if (messageTypes == null) {
			throw new RuntimeException("messageTypes is empty ");
		}
		this.messageTypes = messageTypes;
		return this;
	}

	public MessageQuery objectIds(List<String> objectIds) {
		if (objectIds == null) {
			throw new RuntimeException("objectIds is empty ");
		}
		this.objectIds = objectIds;
		return this;
	}

	public MessageQuery objectValues(List<String> objectValues) {
		if (objectValues == null) {
			throw new RuntimeException("objectValues is empty ");
		}
		this.objectValues = objectValues;
		return this;
	}

	public MessageQuery receiverId(String receiverId) {
		if (receiverId == null) {
			throw new RuntimeException("receiverId is null");
		}
		this.receiverId = receiverId;
		return this;
	}

	public MessageQuery receiverIds(List<String> receiverIds) {
		if (receiverIds == null) {
			throw new RuntimeException("receiverIds is empty ");
		}
		this.receiverIds = receiverIds;
		return this;
	}

	public MessageQuery senderId(String senderId) {
		if (senderId == null) {
			throw new RuntimeException("senderId is null");
		}
		this.senderId = senderId;
		return this;
	}

	public MessageQuery senderIds(List<String> senderIds) {
		if (senderIds == null) {
			throw new RuntimeException("senderIds is empty ");
		}
		this.senderIds = senderIds;
		return this;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public void setContentLike(String contentLike) {
		this.contentLike = contentLike;
	}

	public void setCreateBys(List<String> createBys) {
		this.createBys = createBys;
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

	public void setMessageBox(String messageBox) {
		this.messageBox = messageBox;
	}

	public void setMessageBoxs(List<String> messageBoxs) {
		this.messageBoxs = messageBoxs;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public void setMessageIds(List<String> messageIds) {
		this.messageIds = messageIds;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public void setMessageTypes(List<String> messageTypes) {
		this.messageTypes = messageTypes;
	}

	public void setObjectIds(List<String> objectIds) {
		this.objectIds = objectIds;
	}

	public void setObjectValues(List<String> objectValues) {
		this.objectValues = objectValues;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public void setReceiverIds(List<String> receiverIds) {
		this.receiverIds = receiverIds;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public void setSenderIds(List<String> senderIds) {
		this.senderIds = senderIds;
	}

	public void setSubjectLike(String subjectLike) {
		this.subjectLike = subjectLike;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public void setTemplateIds(List<String> templateIds) {
		this.templateIds = templateIds;
	}

	public MessageQuery subjectLike(String subjectLike) {
		if (subjectLike == null) {
			throw new RuntimeException("subject is null");
		}
		this.subjectLike = subjectLike;
		return this;
	}

	public MessageQuery templateId(String templateId) {
		if (templateId == null) {
			throw new RuntimeException("templateId is null");
		}
		this.templateId = templateId;
		return this;
	}

	public MessageQuery templateIds(List<String> templateIds) {
		if (templateIds == null) {
			throw new RuntimeException("templateIds is empty ");
		}
		this.templateIds = templateIds;
		return this;
	}

}