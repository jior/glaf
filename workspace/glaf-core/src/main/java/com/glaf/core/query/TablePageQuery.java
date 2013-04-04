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

package com.glaf.core.query;

import java.io.Serializable;

public class TablePageQuery implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String SORTORDER_ASC = "asc";

	public static final String SORTORDER_DESC = "desc";

	protected int firstResult;
	protected int maxResults;
	protected String orderBy;
	protected String tableName;

	public TablePageQuery() {

	}

	protected void addOrder(String column, String sortOrder) {
		if (orderBy == null) {
			orderBy = "";
		} else {
			orderBy = orderBy + ", ";
		}
		orderBy = orderBy + column + " " + sortOrder;
	}

	public TablePageQuery firstResult(int firstResult) {
		this.firstResult = firstResult;
		return this;
	}

	public int getFirstResult() {
		return firstResult;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public String getTableName() {
		return tableName;
	}

	public TablePageQuery maxResults(int maxResults) {
		this.maxResults = maxResults;
		return this;
	}

	public TablePageQuery orderAsc(String column) {
		addOrder(column, SORTORDER_ASC);
		return this;
	}

	public TablePageQuery orderDesc(String column) {
		addOrder(column, SORTORDER_DESC);
		return this;
	}

	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	public TablePageQuery tableName(String tableName) {
		this.tableName = tableName;
		return this;
	}

}