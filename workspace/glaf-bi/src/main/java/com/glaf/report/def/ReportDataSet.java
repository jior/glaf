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

public class ReportDataSet implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 数据源定义
	 */
	protected String datasourceName;

	protected ReportDefinition reportDefinition;

	/**
	 * 报表结果集
	 */
	protected List<ReportRowSet> rowSetList = new java.util.ArrayList<ReportRowSet>();

	/**
	 * 报表属性定义，主要用于自定义处理程序
	 */
	protected Map<String, Object> properties = new java.util.HashMap<String, Object>();

	public ReportDataSet() {

	}

	public void addRowSet(ReportRowSet rowset) {
		if (rowSetList == null) {
			rowSetList = new java.util.ArrayList<ReportRowSet>();
		}
		rowset.setReportDataSet(this);
		rowSetList.add(rowset);
	}

	public String getDatasourceName() {
		return datasourceName;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public ReportDefinition getReportDefinition() {
		return reportDefinition;
	}

	public List<ReportRowSet> getRowSetList() {
		if (rowSetList == null) {
			rowSetList = new java.util.ArrayList<ReportRowSet>();
		}
		return rowSetList;
	}

	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public void setReportDefinition(ReportDefinition reportDefinition) {
		this.reportDefinition = reportDefinition;
	}

	public void setRowSetList(List<ReportRowSet> rowSetList) {
		this.rowSetList = rowSetList;
	}

}