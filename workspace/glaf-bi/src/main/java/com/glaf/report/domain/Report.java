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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.*;
import com.glaf.report.util.ReportJsonFactory;

@Entity
@Table(name = "BI_REPORT")
public class Report implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	@Column(name = "NODEID_")
	protected Long nodeId;

	/**
	 * ��ѯ���ݼ�
	 */
	@Column(name = "QUERYIDS_", length = 500)
	protected String queryIds;

	/**
	 * ͼ��
	 */
	@Column(name = "CHARTIDS_", length = 500)
	protected String chartIds;

	/**
	 * ����
	 */
	@Column(name = "NAME_", length = 200)
	protected String name;

	/**
	 * ����
	 */
	@Column(name = "SUBJECT_", length = 250)
	protected String subject;

	/**
	 * ��������
	 */
	@Column(name = "TYPE_", length = 50)
	protected String type;

	/**
	 * ��������
	 */
	@Column(name = "REPORTNAME_", length = 200)
	protected String reportName;

	/**
	 * �������ɸ�ʽ
	 */
	@Column(name = "REPORTFORMAT_", length = 50)
	protected String reportFormat;

	/**
	 * ����ģ��
	 */
	@Column(name = "REPORTTEMPLATE_", length = 200)
	protected String reportTemplate;

	/**
	 * �����������
	 */
	@Column(name = "REPORTTITLEDATE_", length = 50)
	protected String reportTitleDate;

	/**
	 * ��������
	 */
	@Column(name = "REPORTMONTH_", length = 50)
	protected String reportMonth;

	/**
	 * ���������ղ���
	 */
	@Column(name = "REPORTDATEYYYYMMDD_", length = 50)
	protected String reportDateYYYYMMDD;

	/**
	 * ����JSON��ʽ����
	 */
	@Lob
	@Column(name = "JSONPARAMETER_", length = 500)
	protected String jsonParameter;

	@Column(name = "TEXTTITLE_", length = 200)
	protected String textTitle;

	/**
	 * ��������
	 */
	@Lob
	@Column(name = "TEXTCONTENT_", length = 500)
	protected String textContent;

	/**
	 * �ʼ�������
	 */
	@Lob
	@Column(name = "MAILRECIPIENT_", length = 500)
	protected String mailRecipient;

	/**
	 * �ֻ�������
	 */
	@Lob
	@Column(name = "MOBILERECIPIENT_", length = 500)
	protected String mobileRecipient;

	/**
	 * ���ȱ��ʽ
	 */
	@Column(name = "CRONEXPRESSION_", length = 50)
	protected String cronExpression;

	/**
	 * �Ƿ�����
	 */
	@Column(name = "ENABLEFLAG_", length = 1)
	protected String enableFlag;

	/**
	 * ��������
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	/**
	 * ������
	 */
	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	public Report() {

	}

	public String getChartIds() {
		return this.chartIds;
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

	public String getJsonParameter() {
		return this.jsonParameter;
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

	public Long getNodeId() {
		return nodeId;
	}

	public String getQueryIds() {
		return this.queryIds;
	}

	public String getReportDateYYYYMMDD() {
		return this.reportDateYYYYMMDD;
	}

	public String getReportFormat() {
		return reportFormat;
	}

	public String getReportMonth() {
		return this.reportMonth;
	}

	public String getReportName() {
		return this.reportName;
	}

	public String getReportTemplate() {
		return reportTemplate;
	}

	public String getReportTitleDate() {
		return this.reportTitleDate;
	}

	public String getSubject() {
		return this.subject;
	}

	public String getTextContent() {
		return textContent;
	}

	public String getTextTitle() {
		return textTitle;
	}

	public String getType() {
		return this.type;
	}

	public Report jsonToObject(JSONObject jsonObject) {
		return ReportJsonFactory.jsonToObject(jsonObject);
	}

	public void setChartIds(String chartIds) {
		this.chartIds = chartIds;
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

	public void setJsonParameter(String jsonParameter) {
		this.jsonParameter = jsonParameter;
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

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public void setQueryIds(String queryIds) {
		this.queryIds = queryIds;
	}

	public void setReportDateYYYYMMDD(String reportDateYYYYMMDD) {
		this.reportDateYYYYMMDD = reportDateYYYYMMDD;
	}

	public void setReportFormat(String reportFormat) {
		this.reportFormat = reportFormat;
	}

	public void setReportMonth(String reportMonth) {
		this.reportMonth = reportMonth;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public void setReportTemplate(String reportTemplate) {
		this.reportTemplate = reportTemplate;
	}

	public void setReportTitleDate(String reportTitleDate) {
		this.reportTitleDate = reportTitleDate;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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
		return ReportJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return ReportJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}