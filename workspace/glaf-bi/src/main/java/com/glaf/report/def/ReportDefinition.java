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

package com.glaf.report.def;

import java.util.List;
import java.util.Map;

public class ReportDefinition implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ������Ψһ��ʶ��ȫ��Ψһ
	 */
	protected String reportId;

	/**
	 * ģ��ID�����ʹ����ģ����񣬿��Ա�ʶһ��ȫ�ֵ�ģ�塣
	 */
	protected String templateId;

	/**
	 * ģ���ļ�
	 */
	protected String templateFile;

	/**
	 * ģ���ֽ���
	 */
	protected byte[] data;

	/**
	 * �������ݼ����������õ������ݼ���
	 */
	protected List<ReportDataSet> dataSetList = new java.util.ArrayList<ReportDataSet>();

	/**
	 * �������Զ��壬��Ҫ�����Զ��崦�����
	 */
	protected Map<String, Object> properties = new java.util.HashMap<String, Object>();

	public ReportDefinition() {

	}

	public void addDataSet(ReportDataSet rds) {
		if (dataSetList == null) {
			dataSetList = new java.util.ArrayList<ReportDataSet>();
		}
		rds.setReportDefinition(this);
		dataSetList.add(rds);
	}

	public byte[] getData() {
		return data;
	}

	public List<ReportDataSet> getDataSetList() {
		if (dataSetList == null) {
			dataSetList = new java.util.ArrayList<ReportDataSet>();
		}
		return dataSetList;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public String getReportId() {
		return reportId;
	}

	public String getTemplateFile() {
		return templateFile;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public void setDataSetList(List<ReportDataSet> dataSetList) {
		this.dataSetList = dataSetList;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

}