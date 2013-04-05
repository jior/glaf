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

package com.glaf.core.base;

import java.util.List;
import java.util.Map;

public class TablePage {

	protected String tableName;

	/**
	 * The total number of rows in the table.
	 */
	protected long total = -1;

	/**
	 * Identifies the index of the first result stored in this TablePage. For
	 * example in a paginated database table, this value identifies the record
	 * number of the result on the first row.
	 */
	protected long firstResult;

	/**
	 * The actual content of the database table, stored as a list of mappings of
	 * the form {colum name, value}.
	 * 
	 * This means that every map object in the list corresponds with one row in
	 * the database table.
	 */
	protected List<Map<String, Object>> rows;

	public TablePage() {

	}

	/**
	 * @return the start index of this page (ie the index of the first element
	 *         in the page)
	 */
	public long getFirstResult() {
		return firstResult;
	}

	/**
	 * @return the actual table content.
	 */
	public List<Map<String, Object>> getRows() {
		return rows;
	}

	/**
	 * @return the actual number of rows in this page.
	 */
	public long getSize() {
		return rows.size();
	}

	public String getTableName() {
		return tableName;
	}

	/**
	 * @return the total rowcount of the table from which this page is only a
	 *         subset.
	 */
	public long getTotal() {
		return total;
	}

	public void setFirstResult(long firstResult) {
		this.firstResult = firstResult;
	}

	public void setRows(List<Map<String, Object>> rows) {
		this.rows = rows;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setTotal(long total) {
		this.total = total;
	}
}