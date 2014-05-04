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

package com.glaf.core.dialect;

public class SQLServer2008Dialect implements Dialect {

	protected static String getOrderByPart(String sql) {
		String loweredString = sql.toLowerCase();
		int orderByIndex = loweredString.indexOf("order by");
		if (orderByIndex != -1) {
			return sql.substring(orderByIndex);
		} else {
			return "";
		}
	}

	public String getLimitString(String querySelect, boolean hasOffset) {
		return querySelect;
	}

	/**
	 * Add a LIMIT clause to the given SQL SELECT
	 * 
	 * The LIMIT SQL will look like:
	 * 
	 * WITH query AS (SELECT TOP 100 percent ROW_NUMBER() OVER (ORDER BY
	 * CURRENT_TIMESTAMP) as __row_number__, * from table_name) SELECT * FROM
	 * query WHERE __row_number__ BETWEEN :offset and :lastRows ORDER BY
	 * __row_number__
	 * 
	 * @param querySelect
	 *            The SQL statement to base the limit query off of.
	 * @param offset
	 *            Offset of the first row to be returned by the query
	 *            (zero-based)
	 * @param limit
	 *            Maximum number of rows to be returned by the query
	 * @return A new SQL statement with the LIMIT clause applied.
	 */
	@Override
	public String getLimitString(String querySelect, int offset, int limit) {
		StringBuffer pagingBuilder = new StringBuffer();
		String orderby = getOrderByPart(querySelect);
		String distinctStr = "";

		String loweredString = querySelect.toLowerCase();
		String sqlPartString = querySelect;
		if (loweredString.trim().startsWith("select")) {
			int index = 6;
			if (loweredString.startsWith("select distinct")) {
				distinctStr = "DISTINCT ";
				index = 15;
			}
			sqlPartString = sqlPartString.substring(index);
		}
		pagingBuilder.append(sqlPartString);

		// if no ORDER BY is specified use fake ORDER BY field to avoid errors
		if (orderby == null || orderby.length() == 0) {
			orderby = "ORDER BY CURRENT_TIMESTAMP";
		}

		StringBuffer result = new StringBuffer();
		result.append("WITH query AS (SELECT ").append(distinctStr)
				.append("TOP 100 PERCENT ").append(" ROW_NUMBER() OVER (")
				.append(orderby).append(") as __row_number__, ")
				.append(pagingBuilder)
				.append(") SELECT * FROM query WHERE __row_number__ BETWEEN ")
				.append(offset + 1).append(" AND ").append(offset + limit)
				.append(" ORDER BY __row_number__");

		return result.toString();
	}

	public boolean supportsLimit() {
		return true;
	}

	public boolean supportsPhysicalPage() {
		return true;
	}

}