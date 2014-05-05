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

public class MailTemplate implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 邮件定义唯一标识，全局唯一
	 */
	protected String mailDefId;

	protected String title;

	protected String content;

	protected String description;

	/**
	 * 模板ID，如果使用了模板服务，可以标识一个全局的模板。
	 */
	protected String templateId;

	/**
	 * 模板文件
	 */
	protected String templatePath;

	/**
	 * 模板字节流
	 */
	protected byte[] data;

	/**
	 * 邮件数据集，邮件中用到的数据集合
	 */
	protected List<MailDataSet> dataSetList = new java.util.ArrayList<MailDataSet>();

	/**
	 * 邮件属性定义，主要用于自定义处理程序
	 */
	protected Map<String, Object> properties = new java.util.HashMap<String, Object>();

	public MailTemplate() {

	}

	public void addDataSet(MailDataSet mds) {
		if (dataSetList == null) {
			dataSetList = new java.util.ArrayList<MailDataSet>();
		}
		mds.setMailDefinition(this);
		dataSetList.add(mds);
	}

	public String getContent() {
		return content;
	}

	public byte[] getData() {
		return data;
	}

	public List<MailDataSet> getDataSetList() {
		if (dataSetList == null) {
			dataSetList = new java.util.ArrayList<MailDataSet>();
		}
		return dataSetList;
	}

	public String getDescription() {
		return description;
	}

	public String getMailDefId() {
		return mailDefId;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public String getTemplateId() {
		return templateId;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public String getTitle() {
		return title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public void setDataSetList(List<MailDataSet> dataSetList) {
		this.dataSetList = dataSetList;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setMailDefId(String mailDefId) {
		this.mailDefId = mailDefId;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}