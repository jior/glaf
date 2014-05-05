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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 页对象。用来表示数据的翻页显示时的页信息，包括页数据和分页信息。
 */
public class Paging implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用来传递页码信息的参数名称
	 */
	public final static String PAGENO_PARAMNAME = "__go2pageNo";

	/**
	 * 空白页
	 */
	public final static Paging EMPTY_PAGE = new Paging(Collections.emptyList(),
			0, 0, 0);

	/**
	 * 每页记录条数
	 */
	public final static int DEFAULT_PAGE_SIZE = 10;

	/**
	 * 最大记录条数
	 */
	public final static int MAX_RECORD_COUNT = 2000;

	/**
	 * 当前页号
	 */
	private int currentPage = 1;

	/**
	 * 每页记录数
	 */
	private int pageSize = DEFAULT_PAGE_SIZE;

	/**
	 * 总记录数
	 */
	private int total = 0;

	/**
	 * 本页的数据
	 */
	private List<Object> rows = new java.util.ArrayList<Object>();

	/**
	 * 是否需要缓存结果集
	 */
	private boolean cacheable;

	/**
	 * 显示页的主题
	 */
	private String title;

	private String prefix;

	/**
	 * 环境变量
	 */
	private Map<String, Object> contextMap = new java.util.HashMap<String, Object>();

	/**
	 * 显示页的参数
	 */
	private Map<String, Object> paramMap = new java.util.HashMap<String, Object>();

	public Paging() {
		rows = new ArrayList<Object>();
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
			rows = new ArrayList<Object>();
		}
		rows.add(row);
	}

	public void addRows(List<?> list) {
		if (rows == null) {
			rows = new ArrayList<Object>();
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
	 * 获得当前页数。
	 * 
	 * @return 当前页数
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * 获得每页最大记录数。
	 * 
	 * @return 每页最大记录数
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
			rows = new ArrayList<Object>();
		}
		return rows;
	}

	public String getTitle() {
		return title;
	}

	/**
	 * 获得记录总数
	 * 
	 * @return 记录总数
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * 得到总页数 算法：总记录数除以每页的纪录数，结果为整数，若还有余数，则需要加一。
	 * 
	 * @return int
	 */
	public int getTotalPage() {
		if (pageSize <= 0) {
			pageSize = DEFAULT_PAGE_SIZE;
		}
		// 先求得整除的结果
		int pageCount = this.getTotal() / pageSize;
		// 再求得模运算的结果
		int temp = this.getTotal() % pageSize;
		// 若模运算的结果不为零，则总页数为整除的结果加上模运算的结果
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
	 * 设置当前页数。
	 * 
	 * @param currPageNo
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * 设置每页最大记录数。
	 * 
	 * @param pageSize
	 *            每页最大记录数
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
	 * 设置记录总数
	 * 
	 * @param i
	 */
	public void setTotal(int i) {
		this.total = i;
	}

}