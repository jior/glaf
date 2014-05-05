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

package com.glaf.mail.def;

import java.util.List;
import java.util.Map;

public class MailDataSet implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	protected MailTemplate mailDefinition;

	/**
	 * 邮件结果集
	 */
	protected List<MailRowSet> rowSetList = new java.util.ArrayList<MailRowSet>();

	/**
	 * 邮件属性定义，主要用于自定义处理程序
	 */
	protected Map<String, Object> properties = new java.util.HashMap<String, Object>();

	public MailDataSet() {

	}

	public void addRowSet(MailRowSet rowset) {
		if (rowSetList == null) {
			rowSetList = new java.util.ArrayList<MailRowSet>();
		}
		rowset.setMailDataSet(this);
		rowSetList.add(rowset);
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public MailTemplate getMailDefinition() {
		return mailDefinition;
	}

	public List<MailRowSet> getRowSetList() {
		if (rowSetList == null) {
			rowSetList = new java.util.ArrayList<MailRowSet>();
		}
		return rowSetList;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public void setMailDefinition(MailTemplate mailDefinition) {
		this.mailDefinition = mailDefinition;
	}

	public void setRowSetList(List<MailRowSet> rowSetList) {
		this.rowSetList = rowSetList;
	}

}