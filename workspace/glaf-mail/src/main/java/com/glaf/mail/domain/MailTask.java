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
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "SYS_MAIL_TASK")
public class MailTask implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	@Column(name = "STORAGEID_", length = 50)
	protected String storageId;

	@Column(name = "SUBJECT_", length = 200)
	protected String subject;

	@Lob
	@Column(name = "CONTENT_")
	protected String content;

	@Column(name = "CALLBACKURL_", length = 200)
	protected String callbackUrl;

	/**
	 * 调度表达式
	 */
	@Column(name = "CRONEXPRESSION_", length = 50)
	protected String cronExpression;

	@Column(name = "THREADSIZE_")
	protected int threadSize;

	@Column(name = "DELAYTIME_")
	protected int delayTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "STARTDATE_")
	protected Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENDDATE_")
	protected Date endDate;

	@Column(name = "ISHTML_", length = 10)
	protected String isHtml;

	@Column(name = "ISBACK_", length = 10)
	protected String isBack;

	@Column(name = "ISUNSUBSCRIBE_", length = 10)
	protected String isUnSubscribe;

	@Column(name = "LOCKED_")
	protected int locked;

	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	@javax.persistence.Transient
	protected MailStorage storage;

	@javax.persistence.Transient
	protected List<MailAccount> accounts = new java.util.ArrayList<MailAccount>();

	public MailTask() {

	}

	public List<MailAccount> getAccounts() {
		return accounts;
	}

	public String getCallbackUrl() {
		return callbackUrl;
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

	public String getCronExpression() {
		return cronExpression;
	}

	public int getDelayTime() {
		return delayTime;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getId() {
		return id;
	}

	public String getIsBack() {
		return isBack;
	}

	public String getIsHtml() {
		return isHtml;
	}

	public String getIsUnSubscribe() {
		return isUnSubscribe;
	}

	public int getLocked() {
		return locked;
	}

	public Date getStartDate() {
		return startDate;
	}

	public MailStorage getStorage() {
		return storage;
	}

	public String getStorageId() {
		return storageId;
	}

	public String getSubject() {
		return subject;
	}

	public int getThreadSize() {
		return threadSize;
	}

	public boolean isBack() {
		if (StringUtils.equalsIgnoreCase(isBack, "1")
				|| StringUtils.equalsIgnoreCase(isBack, "Y")
				|| StringUtils.equalsIgnoreCase(isBack, "true")) {
			return true;
		}
		return false;
	}

	public boolean isHtml() {
		if (StringUtils.equalsIgnoreCase(isHtml, "1")
				|| StringUtils.equalsIgnoreCase(isHtml, "Y")
				|| StringUtils.equalsIgnoreCase(isHtml, "true")) {
			return true;
		}
		return false;
	}

	public boolean isUnSubscribe() {
		if (StringUtils.equalsIgnoreCase(isUnSubscribe, "1")
				|| StringUtils.equalsIgnoreCase(isUnSubscribe, "Y")
				|| StringUtils.equalsIgnoreCase(isUnSubscribe, "true")) {
			return true;
		}
		return false;
	}

	public MailTask jsonToObject(JSONObject jsonObject) {
		MailTask model = new MailTask();
		if (jsonObject.containsKey("storageId")) {
			model.setStorageId(jsonObject.getString("storageId"));
		}
		if (jsonObject.containsKey("subject")) {
			model.setSubject(jsonObject.getString("subject"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("callbackUrl")) {
			model.setCallbackUrl(jsonObject.getString("callbackUrl"));
		}
		if (jsonObject.containsKey("cronExpression")) {
			model.setCronExpression(jsonObject.getString("cronExpression"));
		}
		if (jsonObject.containsKey("threadSize")) {
			model.setThreadSize(jsonObject.getInteger("threadSize"));
		}
		if (jsonObject.containsKey("delayTime")) {
			model.setDelayTime(jsonObject.getInteger("delayTime"));
		}
		if (jsonObject.containsKey("startDate")) {
			model.setStartDate(jsonObject.getDate("startDate"));
		}
		if (jsonObject.containsKey("endDate")) {
			model.setEndDate(jsonObject.getDate("endDate"));
		}
		if (jsonObject.containsKey("isHtml")) {
			model.setIsHtml(jsonObject.getString("isHtml"));
		}
		if (jsonObject.containsKey("isBack")) {
			model.setIsBack(jsonObject.getString("isBack"));
		}
		if (jsonObject.containsKey("isUnSubscribe")) {
			model.setIsUnSubscribe(jsonObject.getString("isUnSubscribe"));
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
		return model;
	}

	public void setAccounts(List<MailAccount> accounts) {
		this.accounts = accounts;
	}

	public void setBack(boolean isBack) {
		if (isBack) {
			this.isBack = "1";
		} else {
			this.isBack = "0";
		}
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
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

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public void setDelayTime(int delayTime) {
		this.delayTime = delayTime;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setHtml(boolean isHtml) {
		if (isHtml) {
			this.isHtml = "1";
		} else {
			this.isHtml = "0";
		}
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}

	public void setIsHtml(String isHtml) {
		this.isHtml = isHtml;
	}

	public void setIsUnSubscribe(String isUnSubscribe) {
		this.isUnSubscribe = isUnSubscribe;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setStorage(MailStorage storage) {
		this.storage = storage;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setThreadSize(int threadSize) {
		this.threadSize = threadSize;
	}

	public void setUnSubscribe(boolean isUnSubscribe) {
		if (isUnSubscribe) {
			this.isUnSubscribe = "1";
		} else {
			this.isUnSubscribe = "0";
		}
	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		if (storageId != null) {
			jsonObject.put("storageId", storageId);
		}
		if (subject != null) {
			jsonObject.put("subject", subject);
		}
		if (content != null) {
			jsonObject.put("content", content);
		}
		if (callbackUrl != null) {
			jsonObject.put("callbackUrl", callbackUrl);
		}
		if (cronExpression != null) {
			jsonObject.put("cronExpression", cronExpression);
		}
		jsonObject.put("threadSize", threadSize);
		jsonObject.put("delayTime", delayTime);
		if (startDate != null) {
			jsonObject.put("startDate", DateUtils.getDate(startDate));
			jsonObject.put("startDate_date", DateUtils.getDate(startDate));
			jsonObject.put("startDate_datetime",
					DateUtils.getDateTime(startDate));
		}
		if (endDate != null) {
			jsonObject.put("endDate", DateUtils.getDate(endDate));
			jsonObject.put("endDate_date", DateUtils.getDate(endDate));
			jsonObject.put("endDate_datetime", DateUtils.getDateTime(endDate));
		}
		if (isHtml != null) {
			jsonObject.put("isHtml", isHtml);
		}
		if (isBack != null) {
			jsonObject.put("isBack", isBack);
		}
		if (isUnSubscribe != null) {
			jsonObject.put("isUnSubscribe", isUnSubscribe);
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
		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", id);
		if (storageId != null) {
			jsonObject.put("storageId", storageId);
		}
		if (subject != null) {
			jsonObject.put("subject", subject);
		}
		if (content != null) {
			jsonObject.put("content", content);
		}
		if (callbackUrl != null) {
			jsonObject.put("callbackUrl", callbackUrl);
		}
		if (cronExpression != null) {
			jsonObject.put("cronExpression", cronExpression);
		}
		jsonObject.put("threadSize", threadSize);
		jsonObject.put("delayTime", delayTime);
		if (startDate != null) {
			jsonObject.put("startDate", DateUtils.getDate(startDate));
			jsonObject.put("startDate_date", DateUtils.getDate(startDate));
			jsonObject.put("startDate_datetime",
					DateUtils.getDateTime(startDate));
		}
		if (endDate != null) {
			jsonObject.put("endDate", DateUtils.getDate(endDate));
			jsonObject.put("endDate_date", DateUtils.getDate(endDate));
			jsonObject.put("endDate_datetime", DateUtils.getDateTime(endDate));
		}
		if (isHtml != null) {
			jsonObject.put("isHtml", isHtml);
		}
		if (isBack != null) {
			jsonObject.put("isBack", isBack);
		}
		if (isUnSubscribe != null) {
			jsonObject.put("isUnSubscribe", isUnSubscribe);
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
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}