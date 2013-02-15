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


package org.jpage.util;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jpage.persistence.DatabaseType;
import org.jpage.persistence.SqlExecutor;

public final class JdbcUtil {

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
			}
		} catch (SQLException ex) {
		}
	}

	public static void close(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException ex) {
		}
	}

	public static void fillStatement(PreparedStatement stmt, List params)
			throws SQLException {
		if (params == null || params.size() == 0) {
			return;
		}
		for (int i = 0; i < params.size(); i++) {
			Object obj = params.get(i);
			if (obj != null) {
				if (obj instanceof java.sql.Date) {
					java.sql.Date sqlDate = (java.sql.Date) obj;
					stmt.setDate(i + 1, sqlDate);
				} else if (obj instanceof java.sql.Time) {
					java.sql.Time sqlTime = (java.sql.Time) obj;
					stmt.setTime(i + 1, sqlTime);
				} else if (obj instanceof java.sql.Timestamp) {
					Timestamp datetime = (Timestamp) obj;
					stmt.setTimestamp(i + 1, datetime);
				} else if (obj instanceof java.util.Date) {
					Timestamp datetime = DateTools
							.toTimestamp((java.util.Date) obj);
					stmt.setTimestamp(i + 1, datetime);
				} else {
					stmt.setObject(i + 1, obj);
				}
			} else {
				stmt.setString(i + 1, null);
			}
		}
	}

	public static void fillStatement(PreparedStatement stmt, Object[] params)
			throws SQLException {
		if (params == null || params.length == 0) {
			return;
		}
		for (int i = 0; i < params.length; i++) {
			Object obj = params[i];
			if (obj != null) {
				if (obj instanceof java.sql.Date) {
					java.sql.Date sqlDate = (java.sql.Date) obj;
					stmt.setDate(i + 1, sqlDate);
				} else if (obj instanceof java.sql.Time) {
					java.sql.Time sqlTime = (java.sql.Time) obj;
					stmt.setTime(i + 1, sqlTime);
				} else if (obj instanceof java.sql.Timestamp) {
					Timestamp datetime = (Timestamp) obj;
					stmt.setTimestamp(i + 1, datetime);
				} else if (obj instanceof java.util.Date) {
					Timestamp datetime = DateTools
							.toTimestamp((java.util.Date) obj);
					stmt.setTimestamp(i + 1, datetime);
				} else {
					stmt.setObject(i + 1, obj);
				}
			} else {
				stmt.setString(i + 1, null);
			}
		}
	}

	private static int getAfterSelectInsertPoint(String sql) {
		sql = sql.toLowerCase();
		return (sql.startsWith("select distinct") ? 15 : 6);
	}

	public static String getLimitString(String sql, int startPage, int pageSize) {
		int start = (startPage - 1) * pageSize;
		int end = startPage * pageSize;
		String startRow = String.valueOf(start);
		String endRow = String.valueOf(end);
		sql = sql.trim();
		StringBuffer sqlBuffer = new StringBuffer();
		int dbType = DatabaseType.getDatabaseType();
		switch (dbType) {
		case DatabaseType.DB2_TYPE:
			int startOfSelect = sql.indexOf("select");
			startRow = String.valueOf(start + 1);
			sqlBuffer.append(sql.substring(0, startOfSelect))
					.append(" select * from ( select ")
					.append(getRowNumber(sql));
			if (hasDistinct(sql)) {
				sqlBuffer.append(" row_.* from ( ")
						.append(sql.substring(startOfSelect))
						.append(" ) as row_");
			} else {
				sqlBuffer.append(sql.substring(startOfSelect + 6));
			}
			sqlBuffer.append(" ) as temp_ where rownumber_ ");
			sqlBuffer.append(" between ").append(startRow).append(" and ")
					.append(endRow);
			break;
		case DatabaseType.HSQL_TYPE:
			StringBuffer buffer = new StringBuffer();
			buffer.append(" limit ").append(startRow).append(" ")
					.append(String.valueOf(pageSize)).append(" ");
			sqlBuffer.append(sql);
			sqlBuffer.insert(6, buffer.toString());
			break;
		case DatabaseType.INTERBASE_TYPE:
			sqlBuffer.append(sql).append(" rows ").append(startRow)
					.append(" to ").append(endRow);
			break;
		case DatabaseType.MYSQL_TYPE:
			sqlBuffer.append(sql).append(" limit ").append(startRow)
					.append(" , ").append(String.valueOf(pageSize));
			break;
		case DatabaseType.ORACLE_TYPE:
			sqlBuffer
					.append(" select * from ( select row_.*, rownum rownum_ from ( ");
			sqlBuffer.append(sql);
			sqlBuffer.append(" ) row_ ) where rownum_ <= ").append(endRow)
					.append(" and rownum_ ").append(startRow);
			break;
		case DatabaseType.POSTGRESQL_TYPE:
			sqlBuffer.append(sql).append(" limit ").append(pageSize)
					.append(" offset ").append(startRow);
			break;
		case DatabaseType.SQLSERVER_TYPE:
			sqlBuffer.append(sql);
			sqlBuffer.insert(getAfterSelectInsertPoint(sql), " top " + endRow);
			break;
		default:
			sqlBuffer.append(sql);
			break;
		}
		return sqlBuffer.toString();
	}

	public static Object getObject(ResultSet rs, int index) throws SQLException {
		Object obj = rs.getObject(index);
		return obj;
	}

	public static Object getObject(ResultSet rs, String name)
			throws SQLException {
		Object obj = rs.getObject(name);
		return obj;
	}

	public static Object getResultSetValue(ResultSet rs, int index)
			throws SQLException {
		Object obj = rs.getObject(index);
		if (obj instanceof Blob) {
			obj = rs.getBytes(index);
		} else if (obj instanceof Clob) {
			try {
				obj = rs.getString(index);
			} catch (Exception ex) {
				obj = rs.getClob(index);
			}
		} else if (obj != null
				&& obj.getClass().getName().startsWith("oracle.sql.TIMESTAMP")) {
			obj = rs.getTimestamp(index);
		} else if (obj != null && obj instanceof java.sql.Date) {
			if ("java.sql.Timestamp".equals(rs.getMetaData()
					.getColumnClassName(index))) {
				obj = rs.getTimestamp(index);
			}
		}
		return obj;
	}

	public static Object getResultSetValue(ResultSet rs, String name)
			throws SQLException {
		Object obj = rs.getObject(name);
		if (obj instanceof Blob) {
			obj = rs.getBytes(name);
		} else if (obj instanceof Clob) {
			try {
				obj = rs.getString(name);
			} catch (Exception ex) {
				obj = rs.getClob(name);
			}
		} else if (obj != null
				&& obj.getClass().getName().startsWith("oracle.sql.TIMESTAMP")) {
			obj = rs.getTimestamp(name);
		}
		return obj;
	}

	private static String getRowNumber(String sql) {
		StringBuffer rownumber = new StringBuffer(50)
				.append("rownumber() over(");
		int orderByIndex = sql.toLowerCase().indexOf("order by");
		if (orderByIndex > 0 && !hasDistinct(sql)) {
			rownumber.append(sql.substring(orderByIndex));
		}
		rownumber.append(") as rownumber_,");
		return rownumber.toString();
	}

	public static String getSQLAndCondition(Collection<String> rows,
			String alias, String columnName) {
		if (rows == null || rows.size() <= 0) {
			return "";
		}
		int index = 1;
		StringBuffer whereBuffer = new StringBuffer();
		whereBuffer.append(" and ( ").append(alias).append(".")
				.append(columnName).append(" in ('0') ");
		StringBuffer idsBuffer = new StringBuffer();
		Iterator<String> iter = rows.iterator();
		while (iter.hasNext()) {
			String x = iter.next();
			idsBuffer.append('\'').append(x).append('\'');
			if (index == 500) {
				whereBuffer.append(" or ").append(alias).append(".")
						.append(columnName).append(" in (")
						.append(idsBuffer.toString()).append(')');
				index = 0;
				idsBuffer.delete(0, idsBuffer.length());
			}
			if (iter.hasNext() && index > 0) {
				idsBuffer.append(',');
			}
			index++;
		}
		if (idsBuffer.length() > 0) {
			whereBuffer.append(" or ").append(alias).append(".")
					.append(columnName).append(" in (")
					.append(idsBuffer.toString()).append(')');
			idsBuffer.delete(0, idsBuffer.length());
		}
		whereBuffer.append(" ) ");
		return whereBuffer.toString();
	}

	private static boolean hasDistinct(String sql) {
		return sql.toLowerCase().indexOf("select distinct") >= 0;
	}

	private static Map lowerKeyMap(Map params) {
		Map dataMap = new HashMap();
		Iterator iterator = dataMap.keySet().iterator();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			Object value = dataMap.get(key);
			dataMap.put(key, value);
			dataMap.put(key.toString().toLowerCase(), value);
		}
		return dataMap;
	}

	public static SqlExecutor replaceSQL(String sql, Map params) {
		if (sql == null || params == null) {
			return null;
		}
		SqlExecutor sqlExecutor = new SqlExecutor();
		List values = new ArrayList();
		Map dataMap = lowerKeyMap(params);
		StringBuffer sb = new StringBuffer();
		int begin = 0;
		int end = 0;
		boolean flag = false;
		for (int i = 0; i < sql.length(); i++) {
			if (sql.charAt(i) == '$' && sql.charAt(i + 1) == '{') {
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

	private JdbcUtil() {
	}

}