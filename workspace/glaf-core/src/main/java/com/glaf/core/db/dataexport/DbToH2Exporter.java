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
import com.glaf.core.util.IOUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.QueryUtils;

public class DbToH2Exporter {

	protected static final Log logger = LogFactory.getLog(DbToH2Exporter.class);

	public static void main(String[] args) {
		DbToH2Exporter exp = new DbToH2Exporter();
		exp.exportTables(args[0], args[1]);
		//exp.exportTable("default", "/data/glafdb", "sys_tree");
		// ITablePageService tablePageService = ContextFactory
		// .getBean("tablePageService");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void exportTable(String systemName, String dataDir, String tableName) {
		Environment.setCurrentSystemName(systemName);
		logger.info("prepare transfer table:" + tableName);
		int total = 0;
		Connection conn = null;
		Connection conn2 = null;
		PreparedStatement psmt02 = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("org.h2.Driver");

			conn = DBConnectionFactory.getConnection(systemName);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(" select count(*) from " + tableName);
			if (rs.next()) {
				total = rs.getInt(1);
			}

			if (total > 0) {

				List<ColumnDefinition> columns = DBUtils.getColumnDefinitions(
						conn, tableName);
				List<String> primaryKeys = DBUtils.getPrimaryKeys(conn,
						tableName);
				JdbcUtils.close(conn);

				if (columns == null || columns.isEmpty()) {
					return;
				}

				TableDefinition tableDefinition = new TableDefinition();
				tableDefinition.setTableName(tableName);

				if (primaryKeys != null && primaryKeys.size() == 1) {
					for (ColumnDefinition c : columns) {
						if (c.isPrimaryKey()) {
							tableDefinition.setIdColumn(c);
							break;
						}
					}
				}

				tableDefinition.setColumns(columns);

				logger.debug("columns:" + columns);

				conn2 = DriverManager.getConnection("jdbc:h2:" + dataDir);
				DBUtils.dropAndCreateTable(conn2, tableDefinition);
				JdbcUtils.close(conn2);
				Thread.sleep(200);

				conn2 = DriverManager.getConnection("jdbc:h2:" + dataDir);

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

					sb2.append(c.getColumnName()).append(", ");
					sb3.append("?, ");
				}

				sb2.delete(sb2.length() - 2, sb2.length());
				sb2.append(") values( ")
						.append(sb3.substring(0, sb3.length() - 2)).append(")");

				logger.debug("insert sql=" + sb2.toString());

				ITablePageService tablePageService = ContextFactory
						.getBean("tablePageService");
				TablePageQuery query = new TablePageQuery();
				query.tableName(tableName);
				List<Map<String, Object>> rows = new java.util.ArrayList<Map<String, Object>>();
				for (int index = 0; index < (total / pageSize + 1); index++) {
					Environment.setCurrentSystemName(systemName);

					int firstResult = index * pageSize;
					rows.clear();
					logger.debug("firstResult=" + firstResult);
					query.firstResult(firstResult);
					query.maxResults(pageSize);

					List<Map<String, Object>> list = tablePageService
							.getTableData(query);

					if (list != null && !list.isEmpty()) {
						for (Map dataMap : list) {
							Map newDataMap = QueryUtils.lowerKeyMap(dataMap);
							rows.add(newDataMap);
							logger.debug(newDataMap);
						}
					}

					conn2.setAutoCommit(false);
					psmt02 = conn2.prepareStatement(sb2.toString());

					if (rows != null && !rows.isEmpty()) {
						for (Map<String, Object> dataMap : rows) {
							int i = 1;
							//logger.debug(dataMap);
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
											java.sql.Timestamp timetamp = ParamUtils
													.getTimestamp(dataMap, name);
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
					conn2.commit();
				}
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

	public void exportTables(String systemName, String dataDir) {
		List<String> tables = new java.util.ArrayList<String>();
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
				if (tableName.toLowerCase().startsWith("batch_")) {
					continue;
				}
				if (tableName.toLowerCase().startsWith("qrtz_")) {
					continue;
				}
				if (tableName.toLowerCase().startsWith("fileatt")) {
					continue;
				}
				if (tableName.toLowerCase().startsWith("filedot")) {
					continue;
				}
				if (tableName.toLowerCase().startsWith("s_folder")) {
					continue;
				}
				if (tableName.toLowerCase().startsWith("cell_useradd")) {
					continue;
				}
				if (tableName.toLowerCase().startsWith("sys_log")) {
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
						this.exportTable(systemName, dataDir, tableName);
						success = true;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}

	}

	public void exportTables(String systemName, String dataDir,
			List<String> tables) {
		if (tables != null && !tables.isEmpty()) {
			for (String tableName : tables) {
				boolean success = false;
				int retry = 0;
				while (retry < 3 && !success) {
					try {
						retry++;
						this.exportTable(systemName, dataDir, tableName);
						success = true;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}

}