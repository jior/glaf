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
package com.glaf.report.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.glaf.core.util.DateUtils;

@Entity
@Table(name = "SYS_REPORT_TASK")
public class ReportTask implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	/**
	 * 报表集编号
	 */
	@Column(name = "REPORTIDS_", length = 500)
	protected String reportIds;

	/**
	 * 名称
	 */
	@Column(name = "NAME_", length = 50)
	protected String name;

	/**
	 * 标题
	 */
	@Column(name = "SUBJECT_", length = 200)
	protected String subject;

	/**
	 * 邮件接收人
	 */
	@Column(name = "MAILRECIPIENT_", length = 20)
	protected String mailRecipient;

	/**
	 * 手机接收人
	 */
	@Column(name = "MOBILERECIPIENT_", length = 20)
	protected String mobileRecipient;

	/**
	 * 发送标题
	 */
	@Column(name = "SENDTITLE_", length = 20)
	protected String sendTitle;

	/**
	 * 发送内容
	 */
	@Column(name = "SENDCONTENT_", length = 20)
	protected String sendContent;

	/**
	 * 调度表达式
	 */
	@Column(name = "CRONEXPRESSION_", length = 50)
	protected String cronExpression;

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

	/**
	 * 是否启用
	 */
	@Column(name = "ENABLEFLAG_", length = 1)
	protected String enableFlag;

	public ReportTask() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportTask other = (ReportTask) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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

	public String getId() {
		return this.id;
	}

	public String getMailRecipient() {
		return this.mailRecipient;
	}

	public String getMobileRecipient() {
		return this.mobileRecipient;
	}

	public String getName() {
		return this.name;
	}

	public String getReportIds() {
		return this.reportIds;
	}

	public String getSendContent() {
		return this.sendContent;
	}

	public String getSendTitle() {
		return this.sendTitle;
	}

	public String getSubject() {
		return this.subject;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public ReportTask jsonToObject(JSONObject jsonObject) {
		ReportTask model = new ReportTask();
		if (jsonObject.containsKey("reportIds")) {
			model.setReportIds(jsonObject.getString("reportIds"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("subject")) {
			model.setSubject(jsonObject.getString("subject"));
		}
		if (jsonObject.containsKey("mailRecipient")) {
			model.setMailRecipient(jsonObject.getString("mailRecipient"));
		}
		if (jsonObject.containsKey("mobileRecipient")) {
			model.setMobileRecipient(jsonObject.getString("mobileRecipient"));
		}
		if (jsonObject.containsKey("sendTitle")) {
			model.setSendTitle(jsonObject.getString("sendTitle"));
		}
		if (jsonObject.containsKey("sendContent")) {
			model.setSendContent(jsonObject.getString("sendContent"));
		}
		if (jsonObject.containsKey("cronExpression")) {
			model.setCronExpression(jsonObject.getString("cronExpression"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("enableFlag")) {
			model.setEnableFlag(jsonObject.getString("enableFlag"));
		}
		return model;
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

	public void setId(String id) {
		this.id = id;
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

	public void setReportIds(String reportIds) {
		this.reportIds = reportIds;
	}

	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
	}

	public void setSendTitle(String sendTitle) {
		this.sendTitle = sendTitle;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		if (reportIds != null) {
			jsonObject.put("reportIds", reportIds);
		}
		if (name != null) {
			jsonObject.put("name", name);
		}
		if (subject != null) {
			jsonObject.put("subject", subject);
		}
		if (mailRecipient != null) {
			jsonObject.put("mailRecipient", mailRecipient);
		}
		if (mobileRecipient != null) {
			jsonObject.put("mobileRecipient", mobileRecipient);
		}
		if (sendTitle != null) {
			jsonObject.put("sendTitle", sendTitle);
		}
		if (sendContent != null) {
			jsonObject.put("sendContent", sendContent);
		}
		if (cronExpression != null) {
			jsonObject.put("cronExpression", cronExpression);
		}
		if (createDate != null) {
			jsonObject.put("createDate", DateUtils.getDate(createDate));
			jsonObject.put("createDate_date", DateUtils.getDate(createDate));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(createDate));
		}
		if (createBy != null) {
			jsonObject.put("createBy", createBy);
		}
		if (enableFlag != null) {
			jsonObject.put("enableFlag", enableFlag);
		}
		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", id);
		if (reportIds != null) {
			jsonObject.put("reportIds", reportIds);
		}
		if (name != null) {
			jsonObject.put("name", name);
		}
		if (subject != null) {
			jsonObject.put("subject", subject);
		}
		if (mailRecipient != null) {
			jsonObject.put("mailRecipient", mailRecipient);
		}
		if (mobileRecipient != null) {
			jsonObject.put("mobileRecipient", mobileRecipient);
		}
		if (sendTitle != null) {
			jsonObject.put("sendTitle", sendTitle);
		}
		if (sendContent != null) {
			jsonObject.put("sendContent", sendContent);
		}
		if (cronExpression != null) {
			jsonObject.put("cronExpression", cronExpression);
		}
		if (createDate != null) {
			jsonObject.put("createDate", DateUtils.getDate(createDate));
			jsonObject.put("createDate_date", DateUtils.getDate(createDate));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(createDate));
		}
		if (createBy != null) {
			jsonObject.put("createBy", createBy);
		}
		if (enableFlag != null) {
			jsonObject.put("enableFlag", enableFlag);
		}
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
