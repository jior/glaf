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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;

import com.glaf.core.entity.SqlExecutor;

public final class JdbcUtils {

	public static void close(Connection con) {
		try {
			if (con != null && !con.isClosed()) {
				con.close();
			}
		} catch (SQLException ex) {
		}
	}

	public static void close(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException ex) {
		}
	}

	public static void close(SqlSession session) {
		try {
			if (session != null) {
				session.close();
			}
		} catch (Exception ex) {
		}
	}

	public static void close(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
		} catch (SQLException ex) {
		}
	}

	public static void fillStatement(PreparedStatement stmt, List<Object> params)
			throws SQLException {
		if (params == null || params.size() == 0) {
			return;
		}
		for (int i = 0, len = params.size(); i < len; i++) {
			Object object = params.get(i);
			if (object != null) {
				if (object instanceof java.sql.Date) {
					java.sql.Date sqlDate = (java.sql.Date) object;
					stmt.setDate(i + 1, sqlDate);
				} else if (object instanceof java.sql.Time) {
					java.sql.Time sqlTime = (java.sql.Time) object;
					stmt.setTime(i + 1, sqlTime);
				} else if (object instanceof java.sql.Timestamp) {
					Timestamp datetime = (Timestamp) object;
					stmt.setTimestamp(i + 1, datetime);
				} else if (object instanceof java.util.Date) {
					Timestamp datetime = DateUtils
							.toTimestamp((java.util.Date) object);
					stmt.setTimestamp(i + 1, datetime);
				} else {
					stmt.setObject(i + 1, object);
				}
			} else {
				stmt.setString(i + 1, null);
			}
		}
	}

	private static Map<String, Object> lowerKeyMap(Map<String, Object> params) {
		Map<String, Object> dataMap = new java.util.HashMap<String, Object>();
		Set<Entry<String, Object>> entrySet = params.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String key = entry.getKey();
			Object value = entry.getValue();
			dataMap.put(key, value);
			dataMap.put(key.toLowerCase(), value);
		}
		return dataMap;
	}

	public static SqlExecutor rebuildSQL(String sql, Map<String, Object> params) {
		if (sql == null || params == null) {
			return null;
		}
		SqlExecutor sqlExecutor = new SqlExecutor();
		List<Object> values = new java.util.ArrayList<Object>();
		Map<String, Object> dataMap = lowerKeyMap(params);
		StringBuffer sb = new StringBuffer(sql.length() + 1000);
		int begin = 0;
		int end = 0;
		boolean flag = false;
		for (int i = 0; i < sql.length(); i++) {
			if (sql.charAt(i) == '#' && sql.charAt(i + 1) == '{') {
				sb.append(sql.substring(end, i));
				begin = i + 2;
				flag = true;
			}
			if (flag && sql.charAt(i) == '}') {
				String temp = sql.substring(begin, i);
				temp = temp.toLowerCase();
				if (dataMap.get(temp) != null) {
					Object value = dataMap.get(temp);
					values.add(value);
					sb.append(" ? ");
					end = i + 1;
					flag = false;
				} else {
					sb.append(" ? ");
					end = i + 1;
					flag = false;
					values.add(null);
				}
			}
			if (i == sql.length() - 1) {
				sb.append(sql.substring(end, i + 1));
			}
		}
		sqlExecutor.setParameter(values);
		sqlExecutor.setSql(sb.toString());
		return sqlExecutor;
	}

	public static void rollback(Connection con) {
		try {
			if (con != null && !con.isClosed()) {
				con.rollback();
			}
		} catch (SQLException ex) {
		}
	}

	private JdbcUtils() {
	}

}