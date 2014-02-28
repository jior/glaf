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

package com.glaf.core.util;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * ҳ����������ʾ���ݵķ�ҳ��ʾʱ��ҳ��Ϣ������ҳ���ݺͷ�ҳ��Ϣ��
 */
public class Paging implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ��������ҳ����Ϣ�Ĳ�������
	 */
	public final static String PAGENO_PARAMNAME = "__go2pageNo";

	/**
	 * �հ�ҳ
	 */
	public final static Paging EMPTY_PAGE = new Paging(Collections.emptyList(),
			0, 0, 0);

	/**
	 * ÿҳ��¼����
	 */
	public final static int DEFAULT_PAGE_SIZE = 10;

	/**
	 * ����¼����
	 */
	public final static int MAX_RECORD_COUNT = 2000;

	/**
	 * ��ǰҳ��
	 */
	private int currentPage = 1;

	/**
	 * ÿҳ��¼��
	 */
	private int pageSize = DEFAULT_PAGE_SIZE;

	/**
	 * �ܼ�¼��
	 */
	private int total = 0;

	/**
	 * ��ҳ������
	 */
	private List<Object> rows = new java.util.concurrent.CopyOnWriteArrayList<Object>();

	/**
	 * �Ƿ���Ҫ��������
	 */
	private boolean cacheable;

	/**
	 * ��ʾҳ������
	 */
	private String title;

	private String prefix;

	/**
	 * ��������
	 */
	private Map<String, Object> contextMap = new java.util.concurrent.ConcurrentHashMap<String, Object>();

	/**
	 * ��ʾҳ�Ĳ���
	 */
	private Map<String, Object> paramMap = new java.util.concurrent.ConcurrentHashMap<String, Object>();

	public Paging() {
		rows = new java.util.concurrent.CopyOnWriteArrayList<Object>();
		this.currentPage = 0;
		this.pageSize = 0;
		this.total = 0;
	}

	public Paging(List<Object> rows, int currPageNo, int pageSize, int total) {
		this.rows = rows;
		this.currentPage = currPageNo;
		this.pageSize = pageSize;
		this.total = total;
	}

	public void addRow(Object row) {
		if (rows == null) {
			rows = new java.util.concurrent.CopyOnWriteArrayList<Object>();
		}
		rows.add(row);
	}

	public void addRows(List<?> list) {
		if (rows == null) {
			rows = new java.util.concurrent.CopyOnWriteArrayList<Object>();
		}
		if (list != null && !list.isEmpty()) {
			for (Object row : list) {
				rows.add(row);
			}
		}
	}

	public Map<String, Object> getContextMap() {
		return contextMap;
	}

	/**
	 * ��õ�ǰҳ����
	 * 
	 * @return ��ǰҳ��
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * ���ÿҳ����¼����
	 * 
	 * @return ÿҳ����¼��
	 */
	public int getMaxPageRecordCount() {
		return pageSize;
	}

	public int getPageSize() {
		if (pageSize <= 0) {
			pageSize = DEFAULT_PAGE_SIZE;
		}
		return pageSize;
	}

	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	public String getPrefix() {
		return prefix;
	}

	public List<Object> getRows() {
		if (rows == null) {
			rows = new java.util.concurrent.CopyOnWriteArrayList<Object>();
		}
		return rows;
	}

	public String getTitle() {
		return title;
	}

	/**
	 * ��ü�¼����
	 * 
	 * @return ��¼����
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * �õ���ҳ�� �㷨���ܼ�¼������ÿҳ�ļ�¼�������Ϊ����������������������Ҫ��һ��
	 * 
	 * @return int
	 */
	public int getTotalPage() {
		if (pageSize <= 0) {
			pageSize = DEFAULT_PAGE_SIZE;
		}
		// ����������Ľ��
		int pageCount = this.getTotal() / pageSize;
		// �����ģ����Ľ��
		int temp = this.getTotal() % pageSize;
		// ��ģ����Ľ����Ϊ�㣬����ҳ��Ϊ�����Ľ������ģ����Ľ��
		if (temp > 0) {
			pageCount += 1;
		}
		return pageCount;
	}

	public boolean isCacheable() {
		return cacheable;
	}

	public void setCacheable(boolean cacheable) {
		this.cacheable = cacheable;
	}

	public void setContextMap(Map<String, Object> contextMap) {
		this.contextMap = contextMap;
	}

	/**
	 * ���õ�ǰҳ����
	 * 
	 * @param currPageNo
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * ����ÿҳ����¼����
	 * 
	 * @param pageSize
	 *            ÿҳ����¼��
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setRows(List<Object> rows) {
		this.rows = rows;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * ���ü�¼����
	 * 
	 * @param i
	 */
	public void setTotal(int i) {
		this.total = i;
	}

}