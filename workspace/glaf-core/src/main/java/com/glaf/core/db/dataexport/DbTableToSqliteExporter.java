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

package com.glaf.core.db.dataexport;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.config.Environment;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.query.TablePageQuery;
import com.glaf.core.service.ITablePageService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.IOUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.QueryUtils;

public class DbTableToSqliteExporter {

	protected static final Log logger = LogFactory
			.getLog(DbTableToSqliteExporter.class);

	public static void main(String[] args) {
		DbTableToSqliteExporter exp = new DbTableToSqliteExporter();
		exp.exportTables(args[0], args[1]);
		// exp.exportTable("default", "data/exportDb", "sys_tree");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void exportTable(String systemName, String rootDir, String tableName) {
		int total = 0;
		Connection conn = null;
		Connection conn2 = null;
		PreparedStatement psmt02 = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("org.sqlite.JDBC");

			conn = DBConnectionFactory.getConnection(systemName);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(" select count(*) from " + tableName);
			if (rs.next()) {
				total = rs.getInt(1);
			}

			if (total > 0) {

				String sqlitedb = rootDir + FileUtils.sp + tableName + ".db";
				try {
					FileUtils.deleteFile(sqlitedb);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				conn2 = DriverManager.getConnection("jdbc:sqlite:" + sqlitedb);

				List<ColumnDefinition> columns = DBUtils.getColumnDefinitions(
						conn, tableName);
				TableDefinition tbl = new TableDefinition();
				tbl.setTableName(tableName);

				for (ColumnDefinition c : columns) {
					if (c.isPrimaryKey()) {
						tbl.setIdColumn(c);
						break;
					}
				}

				tbl.setColumns(columns);

				DBUtils.createTable(conn2, tbl);
				conn2.setAutoCommit(false);

				StringBuffer sb = new StringBuffer();
				sb.append(" select ");
				StringBuffer sb2 = new StringBuffer();
				sb2.append(" insert into ").append(tableName).append("(");
				StringBuffer sb3 = new StringBuffer();
				int pageSize = 1000;
				for (ColumnDefinition c : columns) {
					String javaType = c.getJavaType();
					if ("Blob".equals(javaType)) {
						/**
						 * 有二进制流的情况，每页取数不能太大
						 */
						pageSize = 5;
						logger.debug(c.getColumnName() + "是二进制流。");
					}
					sb.append(c.getColumnName()).append(", ");
					sb2.append(c.getColumnName()).append(", ");
					sb3.append("?, ");
				}

				sb.delete(sb.length() - 2, sb.length());
				sb.append(" from ").append(tableName);
				sb2.delete(sb2.length() - 2, sb2.length());
				sb2.append(") values( ")
						.append(sb3.substring(0, sb3.length() - 2)).append(")");

				logger.debug("select sql=" + sb.toString());
				logger.debug("insert sql=" + sb2.toString());

				ITablePageService tablePageService = ContextFactory
						.getBean("tablePageService");
				TablePageQuery query = new TablePageQuery();
				query.tableName(tableName);
				List<Map<String, Object>> rows = new java.util.concurrent.CopyOnWriteArrayList<Map<String, Object>>();
				for (int index = 0; index < (total / pageSize + 1); index++) {
					int firstResult = index * pageSize;
					rows.clear();
					logger.info("firstResult=" + firstResult);
					query.firstResult(firstResult);
					query.maxResults(pageSize);
					Environment.setCurrentSystemName(systemName);
					List<Map<String, Object>> list = tablePageService
							.getTableData(query);

					if (list != null && !list.isEmpty()) {
						for (Map dataMap : list) {
							dataMap = QueryUtils.lowerKeyMap(dataMap);
							rows.add(dataMap);
						}
					}

					psmt02 = conn2.prepareStatement(sb2.toString());

					if (rows != null && !rows.isEmpty()) {

						for (Map<String, Object> dataMap : rows) {
							int i = 1;
							for (ColumnDefinition c : columns) {
								String name = c.getColumnName();
								Object object = ParamUtils.get(dataMap, name);
								if (object != null) {
									String javaType = c.getJavaType();
									if ("Integer".equals(javaType)) {
										psmt02.setInt(i++, ParamUtils.getInt(
												dataMap, name));
									} else if ("Long".equals(javaType)) {
										psmt02.setLong(i++, ParamUtils.getLong(
												dataMap, name));
									} else if ("Double".equals(javaType)) {
										psmt02.setDouble(i++, ParamUtils
												.getDouble(dataMap, name));
									} else if ("Date".equals(javaType)) {
										if (object instanceof java.sql.Date) {
											java.sql.Date date = (java.sql.Date) object;
											psmt02.setDate(i++, date);
										} else if (object instanceof java.sql.Time) {
											java.sql.Time time = (java.sql.Time) object;
											psmt02.setTime(i++, time);
										} else if (object instanceof java.sql.Timestamp) {
											java.sql.Timestamp timetamp = (java.sql.Timestamp) object;
											psmt02.setTimestamp(i++, timetamp);
										}
									} else if ("String".equals(javaType)) {
										psmt02.setString(i++, ParamUtils
												.getString(dataMap, name));
									} else if ("Clob".equals(javaType)) {
										if (object instanceof java.io.Reader) {
											java.io.Reader reader = (java.io.Reader) object;
											psmt02.setCharacterStream(i++,
													reader);
										} else if (object instanceof java.sql.Clob) {
											java.sql.Clob clob = (java.sql.Clob) object;
											java.io.Reader reader = clob
													.getCharacterStream();
											String content = IOUtils
													.read(reader);
											psmt02.setString(i++, content);
										} else if (object instanceof String) {
											psmt02.setString(i++,
													(String) object);
										}
									} else if ("Blob".equals(javaType)) {
										if (object instanceof java.sql.Blob) {
											psmt02.setBlob(i++,
													(java.sql.Blob) object);
										} else if (object instanceof byte[]) {
											psmt02.setBytes(i++,
													(byte[]) object);
										}
									} else if ("Boolean".equals(javaType)) {
										if (object instanceof Boolean) {
											Boolean b = (Boolean) object;
											if (b) {
												psmt02.setInt(i++, 1);
											} else {
												psmt02.setInt(i++, 0);
											}
										} else {
											psmt02.setInt(i++, ParamUtils
													.getInt(dataMap, name));
										}
									} else {
										psmt02.setString(i++, ParamUtils
												.getString(dataMap, name));
									}
								} else {
									if ("Blob".equals(c.getJavaType())) {
										psmt02.setBytes(i++, null);
									} else {
										psmt02.setObject(i++, null);
									}
								}
							}
							psmt02.addBatch();
						}
					}
					psmt02.executeBatch();
					psmt02.close();
				}
				conn2.commit();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(rs);
			JdbcUtils.close(conn);
			JdbcUtils.close(conn2);
		}
	}

	public void exportTables(String systemName, String rootDir) {
		List<String> tables = new java.util.concurrent.CopyOnWriteArrayList<String>();
		Connection conn = null;
		DatabaseMetaData dbmd = null;
		ResultSet rs = null;
		try {
			conn = DBConnectionFactory.getConnection(systemName);
			dbmd = conn.getMetaData();
			rs = dbmd.getTables(null, null, null, new String[] { "TABLE" });
			while (rs.next()) {
				String tableName = rs.getString("TABLE_NAME");
				if (DBUtils.isTemoraryTable(tableName)) {
					continue;
				}
				tables.add(tableName);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(rs);
			JdbcUtils.close(conn);
		}

		if (!tables.isEmpty()) {
			for (String tableName : tables) {
				boolean success = false;
				int retry = 0;
				while (retry < 3 && !success) {
					try {
						retry++;
						this.exportTable(systemName, rootDir, tableName);
						success = true;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}

	}

	public void exportTables(String systemName, String rootDir,
			List<String> tables) {
		if (tables != null && !tables.isEmpty()) {
			for (String tableName : tables) {
				boolean success = false;
				int retry = 0;
				while (retry < 3 && !success) {
					try {
						retry++;
						this.exportTable(systemName, rootDir, tableName);
						success = true;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}

}