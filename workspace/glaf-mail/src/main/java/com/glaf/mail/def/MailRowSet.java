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
	 * ���ݼ�ӳ����
	 */
	protected String mapping;

	/**
	 * ���ݹ���������
	 */
	protected String dataMgr;

	/**
	 * ���ݹ�����Spring Bean Id
	 */
	protected String dataMgrBeanId;

	/**
	 * �Զ������ݹ���������
	 */
	protected String dataMgrClassName;

	/**
	 * �ʼ�����������
	 */
	protected String mailMgr;

	/**
	 * �Զ����ʼ�����������
	 */
	protected String mailMgrClassName;

	/**
	 * �ʼ�������ӳ����
	 */
	protected String mailMgrMapping;

	/**
	 * ��ѯ��䶨������
	 */
	protected String query;

	/**
	 * �Ƿ�Ψһ�����
	 */
	protected boolean singleResult;

	/**
	 * �����
	 */
	protected List<?> resultList;

	/**
	 * �ʼ����Զ��壬��Ҫ�����Զ��崦�����
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