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

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * Title: PageResult.java
 * </p>
 * 
 * <p>
 * Description: 分页对象
 * </p>
 * 
 */
@SuppressWarnings("rawtypes")
public class PageResult implements Serializable {
	private static final long serialVersionUID = 4829284558188407984L;

	public final static int DEFAULT_PAGE_SIZE = 20;

	private int currentPageNo;// 当前页
	private int totalPageCount;// 总页数
	private int totalRecordCount;// 总记录数

	private List results;// 结果集
	private int pageSize;// 页面大小

	/**
	 * 取当前页号
	 * 
	 * @return 当前页号
	 */
	public int getCurrentPageNo() {
		if (currentPageNo == 0 || getTotalPageCount() == 0)
			currentPageNo = 1;
		else if (currentPageNo > getTotalPageCount())
			currentPageNo = getTotalPageCount();

		return currentPageNo;
	}

	/**
	 * 设置当前页号
	 * 
	 * @param currentPageNo
	 *            当前页号
	 */
	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	/**
	 * 取总的页数
	 * 
	 * @return 总的页数
	 */
	public int getTotalPageCount() {
		this.totalPageCount = (this.totalRecordCount + pageSize - 1) / pageSize;
		return totalPageCount;
	}

	/**
	 * 设置总的页数
	 * 
	 * @param totalPageCount
	 *            总的页数
	 */
	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	/**
	 * 取总的记录数
	 * 
	 * @return 总的记录数
	 */
	public int getTotalRecordCount() {
		return totalRecordCount;
	}

	/**
	 * 设置总的记录数
	 * 
	 * @param totalRecordCount
	 *            总的记录数
	 */
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}

	/**
	 * 取结果集
	 * 
	 * @return 结果集
	 */
	public List getResults() {
		if (results == null) {
			results = new java.util.ArrayList();
		}
		return results;
	}

	/**
	 * 设置结果集
	 * 
	 * @param results
	 *            结果集
	 */
	public void setResults(List results) {
		this.results = results;
	}

	/**
	 * 取页面大小
	 * 
	 * @return 页面大小
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置页面大小
	 * 
	 * @param pageSize
	 *            页面大小
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}