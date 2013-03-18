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

public class SQLServerDialect implements Dialect {

	static int getAfterSelectInsertPoint(String sql) {
		int selectIndex = sql.toLowerCase().indexOf("select");
		final int selectDistinctIndex = sql.toLowerCase().indexOf(
				"select distinct");
		return selectIndex + (selectDistinctIndex == selectIndex ? 15 : 6);
	}

	public String getLimitString(String querySelect, boolean hasOffset) {
		return querySelect;
	}

	public String getLimitString(String querySelect, int offset, int limit) {
		int start = offset + limit;
		return new StringBuffer(querySelect.length() + 8)
				.append(querySelect)
				.insert(getAfterSelectInsertPoint(querySelect), " top " + start)
				.toString();
	}

	public boolean supportsLimit() {
		return true;
	}

	public boolean supportsPhysicalPage() {
		return false;
	}

}