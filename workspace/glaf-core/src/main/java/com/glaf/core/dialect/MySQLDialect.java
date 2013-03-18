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

public class MySQLDialect implements Dialect {

	public String getLimitString(String sql, boolean hasOffset) {
		return new StringBuffer(sql.length() + 20).append(sql)
				.append(hasOffset ? " limit ?, ?" : " limit ?").toString();
	}

	public String getLimitString(String sql, int offset, int limit) {
		sql = sql.trim();
		StringBuffer sb = new StringBuffer(sql.length() + 50);
		sb.append(sql);
		if (offset > 0) {
			sb.append(" limit ").append(offset).append(", ").append(limit);
		} else {
			sb.append(" limit ").append(limit);
		}
		return sb.toString();
	}

	public boolean supportsLimit() {
		return true;
	}

	public boolean supportsPhysicalPage() {
		return true;
	}

}