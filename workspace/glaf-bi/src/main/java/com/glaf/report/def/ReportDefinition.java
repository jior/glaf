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
	 * 报表定义唯一标识，全局唯一
	 */
	protected String reportId;

	/**
	 * 模板ID，如果使用了模板服务，可以标识一个全局的模板。
	 */
	protected String templateId;

	/**
	 * 模板文件
	 */
	protected String templateFile;

	/**
	 * 模板字节流
	 */
	protected byte[] data;

	/**
	 * 报表数据集，报表中用到的数据集合
	 */
	protected List<ReportDataSet> dataSetList = new java.util.ArrayList<ReportDataSet>();

	/**
	 * 报表属性定义，主要用于自定义处理程序
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