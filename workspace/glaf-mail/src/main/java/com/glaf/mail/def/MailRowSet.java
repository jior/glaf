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

public class MailRowSet implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 数据集映射名
	 */
	protected String mapping;

	/**
	 * 数据管理器名称
	 */
	protected String dataMgr;

	/**
	 * 数据管理器Spring Bean Id
	 */
	protected String dataMgrBeanId;

	/**
	 * 自定义数据管理器类名
	 */
	protected String dataMgrClassName;

	/**
	 * 邮件管理器名称
	 */
	protected String mailMgr;

	/**
	 * 自定义邮件管理器类名
	 */
	protected String mailMgrClassName;

	/**
	 * 邮件管理器映射名
	 */
	protected String mailMgrMapping;

	/**
	 * 查询语句定义名称
	 */
	protected String query;

	/**
	 * 是否唯一结果集
	 */
	protected boolean singleResult;

	/**
	 * 结果集
	 */
	protected List<?> resultList;

	/**
	 * 邮件属性定义，主要用于自定义处理程序
	 */
	protected Map<String, Object> properties = new java.util.HashMap<String, Object>();

	protected MailDataSet mailDataSet;

	public MailRowSet() {

	}

	public String getDataMgr() {
		return dataMgr;
	}

	public String getDataMgrBeanId() {
		return dataMgrBeanId;
	}

	public String getDataMgrClassName() {
		return dataMgrClassName;
	}

	public String getMapping() {
		return mapping;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public String getQuery() {
		return query;
	}

	public MailDataSet getMailDataSet() {
		return mailDataSet;
	}

	public List<?> getResultList() {
		return resultList;
	}

	public String getMailMgr() {
		return mailMgr;
	}

	public String getMailMgrClassName() {
		return mailMgrClassName;
	}

	public String getMailMgrMapping() {
		return mailMgrMapping;
	}

	public boolean isSingleResult() {
		return singleResult;
	}

	public void setDataMgr(String dataMgr) {
		this.dataMgr = dataMgr;
	}

	public void setDataMgrBeanId(String dataMgrBeanId) {
		this.dataMgrBeanId = dataMgrBeanId;
	}

	public void setDataMgrClassName(String dataMgrClassName) {
		this.dataMgrClassName = dataMgrClassName;
	}

	public void setMapping(String mapping) {
		this.mapping = mapping;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public void setMailDataSet(MailDataSet mailDataSet) {
		this.mailDataSet = mailDataSet;
	}

	public void setResultList(List<?> resultList) {
		this.resultList = resultList;
	}

	public void setMailMgr(String mailMgr) {
		this.mailMgr = mailMgr;
	}

	public void setMailMgrClassName(String mailMgrClassName) {
		this.mailMgrClassName = mailMgrClassName;
	}

	public void setMailMgrMapping(String mailMgrMapping) {
		this.mailMgrMapping = mailMgrMapping;
	}

	public void setSingleResult(boolean singleResult) {
		this.singleResult = singleResult;
	}

}