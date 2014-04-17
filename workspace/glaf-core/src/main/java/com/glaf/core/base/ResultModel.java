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

import com.glaf.core.domain.ColumnDefinition;

public class ResultModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	protected int start;

	protected int pageSize;

	protected int total;

	protected List<ColumnDefinition> headers = new java.util.ArrayList<ColumnDefinition>();

	protected List<RowModel> rows = new java.util.ArrayList<RowModel>();

	public ResultModel() {

	}

	public void addHeader(ColumnDefinition column) {
		if (headers == null) {
			headers = new java.util.ArrayList<ColumnDefinition>();
		}
		headers.add(column);
	}

	public void addRow(RowModel row) {
		if (rows == null) {
			rows = new java.util.ArrayList<RowModel>();
		}
		rows.add(row);
	}

	public List<ColumnDefinition> getHeaders() {
		return headers;
	}

	public int getPageSize() {
		return pageSize;
	}

	public List<RowModel> getRows() {
		return rows;
	}

	public int getStart() {
		return start;
	}

	public int getTotal() {
		return total;
	}

	public void setHeaders(List<ColumnDefinition> headers) {
		this.headers = headers;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setRows(List<RowModel> rows) {
		this.rows = rows;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}