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
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Title: PageResult.java
 * </p>
 * 
 * <p>
 * Description: ��ҳ����
 * </p>
 * 
 */
@SuppressWarnings("rawtypes")
public class PageResult implements Serializable {
	private static final long serialVersionUID = 4829284558188407984L;

	public final static int DEFAULT_PAGE_SIZE = 20;

	private int currentPageNo;// ��ǰҳ
	private int totalPageCount;// ��ҳ��
	private int totalRecordCount;// �ܼ�¼��

	private List results;// �����
	private int pageSize;// ҳ���С

	/**
	 * ȡ��ǰҳ��
	 * 
	 * @return ��ǰҳ��
	 */
	public int getCurrentPageNo() {
		if (currentPageNo == 0 || getTotalPageCount() == 0)
			currentPageNo = 1;
		else if (currentPageNo > getTotalPageCount())
			currentPageNo = getTotalPageCount();

		return currentPageNo;
	}

	/**
	 * ���õ�ǰҳ��
	 * 
	 * @param currentPageNo
	 *            ��ǰҳ��
	 */
	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	/**
	 * ȡ�ܵ�ҳ��
	 * 
	 * @return �ܵ�ҳ��
	 */
	public int getTotalPageCount() {
		this.totalPageCount = (this.totalRecordCount + pageSize - 1) / pageSize;
		return totalPageCount;
	}

	/**
	 * �����ܵ�ҳ��
	 * 
	 * @param totalPageCount
	 *            �ܵ�ҳ��
	 */
	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	/**
	 * ȡ�ܵļ�¼��
	 * 
	 * @return �ܵļ�¼��
	 */
	public int getTotalRecordCount() {
		return totalRecordCount;
	}

	/**
	 * �����ܵļ�¼��
	 * 
	 * @param totalRecordCount
	 *            �ܵļ�¼��
	 */
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}

	/**
	 * ȡ�����
	 * 
	 * @return �����
	 */
	public List getResults() {
		if (results == null) {
			results = new ArrayList();
		}
		return results;
	}

	/**
	 * ���ý����
	 * 
	 * @param results
	 *            �����
	 */
	public void setResults(List results) {
		this.results = results;
	}

	/**
	 * ȡҳ���С
	 * 
	 * @return ҳ���С
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * ����ҳ���С
	 * 
	 * @param pageSize
	 *            ҳ���С
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}