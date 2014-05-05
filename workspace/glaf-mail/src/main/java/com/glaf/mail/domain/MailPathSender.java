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
import javax.persistence.Lob;
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
@Table(name = "SYS_MAIL_PATH_SENDER")
public class MailPathSender implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	/**
	 * 任务编号
	 */
	@Column(name = "TASKID_", length = 50)
	protected String taskId;

	/**
	 * 名称
	 */
	@Column(name = "NAME_", length = 50)
	protected String name;

	/**
	 * 标题
	 */
	@Column(name = "SUBJECT_", length = 250)
	protected String subject;

	/**
	 * 邮件类型
	 */
	@Column(name = "TYPE_", length = 50)
	protected String type;

	/**
	 * 文件路径，多个以逗号隔开
	 */
	@Lob
	@Column(name = "MAILFILEPATH_", length = 2000)
	protected String mailFilePath;

	/**
	 * JSON格式参数
	 */
	@Lob
	@Column(name = "JSONPARAMETER_", length = 500)
	protected String jsonParameter;

	@Column(name = "TEXTTITLE_", length = 200)
	protected String textTitle;

	/**
	 * 发送内容
	 */
	@Lob
	@Column(name = "TEXTCONTENT_", length = 500)
	protected String textContent;

	/**
	 * 邮件接收人
	 */
	@Lob
	@Column(name = "MAILRECIPIENT_", length = 500)
	protected String mailRecipient;

	/**
	 * 手机接收人
	 */
	@Lob
	@Column(name = "MOBILERECIPIENT_", length = 500)
	protected String mobileRecipient;

	/**
	 * 调度表达式
	 */
	@Column(name = "CRONEXPRESSION_", length = 50)
	protected String cronExpression;

	/**
	 * 是否启用
	 */
	@Column(name = "ENABLEFLAG_", length = 1)
	protected String enableFlag;

	/**
	 * 是否压缩
	 */
	@Column(name = "COMPRESSFLAG_", length = 1)
	protected String compressFlag;

	/**
	 * 包含文件的扩展名列表，以逗号分隔
	 */
	@Column(name = "INCLUDES_", length = 200)
	protected String includes;

	/**
	 * 排除文件的扩展名列表，以逗号分隔
	 */
	@Column(name = "EXCLUDES_", length = 200)
	protected String excludes;

	/**
	 * 大小
	 */
	@Column(name = "SIZE_")
	protected int size;

	/**
	 * 创建日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	/**
	 * 创建人
	 */
	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	public MailPathSender() {

	}

	public String getCompressFlag() {
		return compressFlag;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public String getEnableFlag() {
		return enableFlag;
	}

	public String getExcludes() {
		return excludes;
	}

	public String getId() {
		return this.id;
	}

	public String getIncludes() {
		return includes;
	}

	public String getJsonParameter() {
		return this.jsonParameter;
	}

	public String getMailFilePath() {
		return mailFilePath;
	}

	public String getMailRecipient() {
		return mailRecipient;
	}

	public String getMobileRecipient() {
		return mobileRecipient;
	}

	public String getName() {
		return this.name;
	}

	public int getSize() {
		return size;
	}

	public String getSubject() {
		return this.subject;
	}

	public String getTaskId() {
		return taskId;
	}

	public String getTextContent() {
		return textContent;
	}

	public String getTextTitle() {
		return textTitle;
	}

	public String getType() {
		return type;
	}

	public MailPathSender jsonToObject(JSONObject jsonObject) {
		MailPathSender model = new MailPathSender();
		if (jsonObject.containsKey("taskId")) {
			model.setTaskId(jsonObject.getString("taskId"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("subject")) {
			model.setSubject(jsonObject.getString("subject"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("mailFilePath")) {
			model.setMailFilePath(jsonObject.getString("mailFilePath"));
		}
		if (jsonObject.containsKey("jsonParameter")) {
			model.setJsonParameter(jsonObject.getString("jsonParameter"));
		}
		if (jsonObject.containsKey("textTitle")) {
			model.setTextTitle(jsonObject.getString("textTitle"));
		}
		if (jsonObject.containsKey("textContent")) {
			model.setTextContent(jsonObject.getString("textContent"));
		}
		if (jsonObject.containsKey("mailRecipient")) {
			model.setMailRecipient(jsonObject.getString("mailRecipient"));
		}
		if (jsonObject.containsKey("mobileRecipient")) {
			model.setMobileRecipient(jsonObject.getString("mobileRecipient"));
		}
		if (jsonObject.containsKey("cronExpression")) {
			model.setCronExpression(jsonObject.getString("cronExpression"));
		}
		if (jsonObject.containsKey("enableFlag")) {
			model.setEnableFlag(jsonObject.getString("enableFlag"));
		}
		if (jsonObject.containsKey("compressFlag")) {
			model.setCompressFlag(jsonObject.getString("compressFlag"));
		}
		if (jsonObject.containsKey("includes")) {
			model.setIncludes(jsonObject.getString("includes"));
		}
		if (jsonObject.containsKey("excludes")) {
			model.setExcludes(jsonObject.getString("excludes"));
		}
		if (jsonObject.containsKey("size")) {
			model.setSize(jsonObject.getInteger("size"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		return model;
	}

	public void setCompressFlag(String compressFlag) {
		this.compressFlag = compressFlag;
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

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}

	public void setExcludes(String excludes) {
		this.excludes = excludes;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIncludes(String includes) {
		this.includes = includes;
	}

	public void setJsonParameter(String jsonParameter) {
		this.jsonParameter = jsonParameter;
	}

	public void setMailFilePath(String mailFilePath) {
		this.mailFilePath = mailFilePath;
	}

	public void setMailRecipient(String mailRecipient) {
		this.mailRecipient = mailRecipient;
	}

	public void setMobileRecipient(String mobileRecipient) {
		this.mobileRecipient = mobileRecipient;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public void setTextTitle(String textTitle) {
		this.textTitle = textTitle;
	}

	public void setType(String type) {
		this.type = type;
	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		if (taskId != null) {
			jsonObject.put("taskId", taskId);
		}
		if (name != null) {
			jsonObject.put("name", name);
		}
		if (subject != null) {
			jsonObject.put("subject", subject);
		}
		if (type != null) {
			jsonObject.put("type", type);
		}
		if (mailFilePath != null) {
			jsonObject.put("mailFilePath", mailFilePath);
		}
		if (jsonParameter != null) {
			jsonObject.put("jsonParameter", jsonParameter);
		}
		if (textTitle != null) {
			jsonObject.put("textTitle", textTitle);
		}
		if (textContent != null) {
			jsonObject.put("textContent", textContent);
		}
		if (mailRecipient != null) {
			jsonObject.put("mailRecipient", mailRecipient);
		}
		if (mobileRecipient != null) {
			jsonObject.put("mobileRecipient", mobileRecipient);
		}
		if (cronExpression != null) {
			jsonObject.put("cronExpression", cronExpression);
		}
		if (enableFlag != null) {
			jsonObject.put("enableFlag", enableFlag);
		}
		if (compressFlag != null) {
			jsonObject.put("compressFlag", compressFlag);
		}
		if (includes != null) {
			jsonObject.put("includes", includes);
		}
		if (excludes != null) {
			jsonObject.put("excludes", excludes);
		}
		jsonObject.put("size", size);
		if (createDate != null) {
			jsonObject.put("createDate", DateUtils.getDate(createDate));
			jsonObject.put("createDate_date", DateUtils.getDate(createDate));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(createDate));
		}
		if (createBy != null) {
			jsonObject.put("createBy", createBy);
		}
		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", id);
		if (taskId != null) {
			jsonObject.put("taskId", taskId);
		}
		if (name != null) {
			jsonObject.put("name", name);
		}
		if (subject != null) {
			jsonObject.put("subject", subject);
		}
		if (type != null) {
			jsonObject.put("type", type);
		}
		if (mailFilePath != null) {
			jsonObject.put("mailFilePath", mailFilePath);
		}
		if (jsonParameter != null) {
			jsonObject.put("jsonParameter", jsonParameter);
		}
		if (textTitle != null) {
			jsonObject.put("textTitle", textTitle);
		}
		if (textContent != null) {
			jsonObject.put("textContent", textContent);
		}
		if (mailRecipient != null) {
			jsonObject.put("mailRecipient", mailRecipient);
		}
		if (mobileRecipient != null) {
			jsonObject.put("mobileRecipient", mobileRecipient);
		}
		if (cronExpression != null) {
			jsonObject.put("cronExpression", cronExpression);
		}
		if (enableFlag != null) {
			jsonObject.put("enableFlag", enableFlag);
		}
		if (compressFlag != null) {
			jsonObject.put("compressFlag", compressFlag);
		}
		if (includes != null) {
			jsonObject.put("includes", includes);
		}
		if (excludes != null) {
			jsonObject.put("excludes", excludes);
		}
		jsonObject.put("size", size);
		if (createDate != null) {
			jsonObject.put("createDate", DateUtils.getDate(createDate));
			jsonObject.put("createDate_date", DateUtils.getDate(createDate));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(createDate));
		}
		if (createBy != null) {
			jsonObject.put("createBy", createBy);
		}
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}