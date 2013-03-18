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

public class DB2Dialect implements Dialect {

	private static boolean hasDistinct(String sql) {
		return sql.toLowerCase().indexOf("select distinct") >= 0;
	}

	public String getLimitString(String sql, boolean hasOffset) {
		int startOfSelect = sql.toLowerCase().indexOf("select");
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100)
				.append(sql.substring(0, startOfSelect))
				.append("select * from ( select ").append(getRowNumber(sql));
		if (hasDistinct(sql)) {
			pagingSelect.append(" row_.* from ( ")
					.append(sql.substring(startOfSelect)).append(" ) as row_");
		} else {
			pagingSelect.append(sql.substring(startOfSelect + 6));
		}
		pagingSelect.append(" ) as temp_ where rownumber_ ");
		if (hasOffset) {
			pagingSelect.append("between ?+1 and ?");
		} else {
			pagingSelect.append("<= ?");
		}
		return pagingSelect.toString();
	}

	public String getLimitString(String sql, int offset, int limit) {
		int startOfSelect = sql.toLowerCase().indexOf("select");
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100)
				.append(sql.substring(0, startOfSelect))
				.append("select * from ( select ").append(getRowNumber(sql));
		if (hasDistinct(sql)) {
			pagingSelect.append(" row_.* from ( ")
					.append(sql.substring(startOfSelect)).append(" ) as row_");
		} else {
			pagingSelect.append(sql.substring(startOfSelect + 6));
		}
		pagingSelect.append(" ) as temp_ where rownumber_ ");
		if (offset > 0) {
			pagingSelect.append("between ").append((offset + 1))
					.append(" and ").append(offset + limit);
		} else {
			pagingSelect.append("<= ").append(limit);
		}
		return pagingSelect.toString();
	}

	private String getRowNumber(String sql) {
		StringBuffer rownumber = new StringBuffer(50)
				.append("rownumber() over(");
		int orderByIndex = sql.toLowerCase().indexOf("order by");
		if (orderByIndex > 0 && !hasDistinct(sql)) {
			rownumber.append(sql.substring(orderByIndex));
		}
		rownumber.append(") as rownumber_,");
		return rownumber.toString();
	}

	public boolean supportsLimit() {
		return true;
	}

	public boolean supportsPhysicalPage() {
		return true;
	}

}