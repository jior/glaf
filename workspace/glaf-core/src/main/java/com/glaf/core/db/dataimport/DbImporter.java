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

package com.glaf.core.db.dataimport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.ParamUtils;

public class DbImporter {

	protected static final Log logger = LogFactory.getLog(DbImporter.class);

	public void importToDb(String systemName, String tableName,
			boolean updatable, List<Map<String, Object>> rows) {
		List<ColumnDefinition> columns = DBUtils.getColumnDefinitions(
				systemName, tableName);
		StringBuffer selectBuffer = new StringBuffer();
		StringBuffer insertBuffer = new StringBuffer();
		StringBuffer valuesBuffer = new StringBuffer();
		StringBuffer updateBuffer = new StringBuffer();
		insertBuffer.append(" insert into ").append(tableName).append(" (");
		updateBuffer.append(" update ").append(tableName).append(" set ");
		ColumnDefinition idColumn = null;
		Iterator<ColumnDefinition> iter = columns.iterator();
		while (iter.hasNext()) {
			ColumnDefinition c = iter.next();
			if (c.isPrimaryKey()) {
				idColumn = c;
			}
			if (!c.isPrimaryKey()) {
				updateBuffer.append(c.getColumnName()).append(" = ? , ");
			}
			insertBuffer.append(c.getColumnName());
			valuesBuffer.append("?");
			if (iter.hasNext()) {
				insertBuffer.append(", ");
				valuesBuffer.append(", ");
			}
		}

		insertBuffer.append(" ) values (").append(valuesBuffer.toString())
				.append(") ");

		if (idColumn == null) {
			throw new RuntimeException(tableName + " primary key is null");
		}

		selectBuffer.append(" select ").append(idColumn.getColumnName())
				.append(" from ").append(tableName);
		updateBuffer.delete(updateBuffer.length() - 2, updateBuffer.length());
		updateBuffer.append(" where ").append(idColumn.getColumnName())
				.append(" = ? ");

		logger.debug(updateBuffer.toString());

		Collection<Object> rowIds = new HashSet<Object>();

		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnectionFactory.getConnection(systemName);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(selectBuffer.toString());
			while (rs.next()) {
				rowIds.add(rs.getString(1));
			}
			stmt.close();
			rs.close();

			conn.setAutoCommit(false);

			Iterator<Map<String, Object>> iterator = rows.iterator();
			while (iterator.hasNext()) {
				Map<String, Object> dataMap = iterator.next();
				String id = ParamUtils.getString(dataMap,
						idColumn.getColumnName());
				// logger.debug(id + "=" + dataMap);
				// logger.debug(id + " " + rowIds.contains(id));

				if (rowIds.contains(id)) {
					// update
					if (updatable) {
						int index = 0;
						pstmt = conn.prepareStatement(updateBuffer.toString());
						for (ColumnDefinition column : columns) {
							if (column.isPrimaryKey()) {
								continue;
							}
							index++;
							String javaType = column.getJavaType();
							String name = column.getColumnName();
							logger.debug(index + "   " + name + "\t" + javaType
									+ "\t" + dataMap.get(name));
							if (dataMap.get(name) != null) {
								if ("Integer".equals(javaType)) {
									pstmt.setInt(index,
											ParamUtils.getInt(dataMap, name));
								} else if ("Long".equals(javaType)) {
									pstmt.setLong(index,
											ParamUtils.getLong(dataMap, name));
								} else if ("Double".equals(javaType)) {
									pstmt.setDouble(index,
											ParamUtils.getDouble(dataMap, name));
								} else if ("Date".equals(javaType)) {
									pstmt.setTimestamp(index, ParamUtils
											.getTimestamp(dataMap, name));
								} else if ("String".equals(javaType)) {
									pstmt.setString(index,
											ParamUtils.getString(dataMap, name));
								} else if ("Clob".equals(javaType)) {
									pstmt.setString(index,
											ParamUtils.getString(dataMap, name));
								} else {
									pstmt.setString(index,
											ParamUtils.getString(dataMap, name));
								}
							} else {
								pstmt.setNull(index, Types.NULL);
								logger.debug(index + "  " + name + "\t"
										+ javaType + " is null");
							}
						}

						index++;
						String javaType = idColumn.getJavaType();

						if ("Integer".equals(javaType)) {
							pstmt.setInt(
									index,
									ParamUtils.getInt(dataMap,
											idColumn.getColumnName()));
						} else if ("Long".equals(javaType)) {
							pstmt.setLong(
									index,
									ParamUtils.getLong(dataMap,
											idColumn.getColumnName()));
						} else if ("String".equals(javaType)) {
							pstmt.setString(
									index,
									ParamUtils.getString(dataMap,
											idColumn.getColumnName()));
						}

						pstmt.executeUpdate();
						pstmt.close();
					}
				} else {
					// insert
					int index = 1;
					pstmt = conn.prepareStatement(insertBuffer.toString());
					for (ColumnDefinition column : columns) {
						String javaType = column.getJavaType();
						String name = column.getColumnName();
						if (dataMap.get(name) != null) {
							if ("Integer".equals(javaType)) {
								pstmt.setInt(index++,
										ParamUtils.getInt(dataMap, name));
							} else if ("Long".equals(javaType)) {
								pstmt.setLong(index++,
										ParamUtils.getLong(dataMap, name));
							} else if ("Double".equals(javaType)) {
								pstmt.setDouble(index++,
										ParamUtils.getDouble(dataMap, name));
							} else if ("Date".equals(javaType)) {
								pstmt.setTimestamp(index++,
										ParamUtils.getTimestamp(dataMap, name));
							} else if ("String".equals(javaType)) {
								pstmt.setString(index++,
										ParamUtils.getString(dataMap, name));
							} else if ("Clob".equals(javaType)) {
								pstmt.setString(index,
										ParamUtils.getString(dataMap, name));
							} else {
								pstmt.setObject(index++, dataMap.get(name));
							}
						} else {
							pstmt.setNull(index++, Types.NULL);
						}
					}
					pstmt.executeUpdate();
					pstmt.close();
				}
			}

			conn.commit();

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		} finally {
			JdbcUtils.close(rs);
			JdbcUtils.close(stmt);
			JdbcUtils.close(pstmt);
			JdbcUtils.close(conn);
		}
	}
}