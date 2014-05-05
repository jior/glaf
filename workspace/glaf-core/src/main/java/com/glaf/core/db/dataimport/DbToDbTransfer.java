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
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.FieldType;
import com.glaf.core.util.JdbcUtils;

public class DbToDbTransfer {
	protected final static Log logger = LogFactory.getLog(DbToDbTransfer.class);

	public static void main(String[] args) {
		String srcSystemName = "src";
		String destSystemName = "dest";
		DbToDbTransfer transfer = new DbToDbTransfer();
		List<String> tables = new java.util.concurrent.CopyOnWriteArrayList<String>();
		List<String> error_tables = new java.util.concurrent.CopyOnWriteArrayList<String>();

		Connection conn = null;

		DatabaseMetaData dbmd = null;
		ResultSet rs = null;
		try {
			conn = DBConnectionFactory.getConnection(srcSystemName);
			String dbtype = DBConnectionFactory.getDatabaseType(conn);
			dbmd = conn.getMetaData();
			logger.debug("username:" + dbmd.getUserName());

			if ("oracle".equals(dbtype)) {
				rs = dbmd.getTables(null, dbmd.getUserName(), null,
						new String[] { "TABLE" });
			} else {
				rs = dbmd.getTables(null, null, null, new String[] { "TABLE" });
			}
			while (rs.next()) {
				String tableName = rs.getString("TABLE_NAME");
				if (StringUtils.startsWithIgnoreCase(tableName, "cell_useradd")) {
					continue;
				}
				if (StringUtils.startsWithIgnoreCase(tableName, "act_")) {
					continue;
				}
				if (StringUtils.startsWithIgnoreCase(tableName, "jbpm_")) {
					continue;
				}
				if ("fileatt".equalsIgnoreCase(tableName)) {
					continue;
				}
				if ("filedot".equalsIgnoreCase(tableName)) {
					continue;
				}
				if ("mx_lob".equalsIgnoreCase(tableName)) {
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
				try {
					List<String> pks = DBUtils.getPrimaryKeys(srcSystemName,
							tableName);
					if (pks != null && pks.size() == 1) {
						transfer.transferTable(srcSystemName, destSystemName,
								tableName, pks.get(0), true);
					} else {
						transfer.transferTable(srcSystemName, destSystemName,
								tableName, null, true);
					}
				} catch (Exception ex) {
					error_tables.add(tableName);
					ex.printStackTrace();
				}
			}
			logger.error("error transfer tables:\n" + error_tables);
		}

	}

	public void transferQueryToTable(Connection srcCon, Connection destCon,
			String sql, String tableName, String primaryKeyColumn,
			boolean rebuildTargartTable) {
		String sqlx = sql;
		if (StringUtils.contains(sql, " where ")) {
			sqlx = sql.substring(0, sql.toLowerCase().lastIndexOf(" where "));
			sqlx = sqlx + " where 1 = 0 ";
		} else {
			sqlx = sqlx + " where 1 = 0 ";
		}
		logger.debug("sqlx:" + sqlx);
		TableDefinition tableDefinition = new TableDefinition();
		tableDefinition.setTableName(tableName);
		List<ColumnDefinition> columns = new java.util.concurrent.CopyOnWriteArrayList<ColumnDefinition>();
		PreparedStatement psmt = null;
		PreparedStatement psmt2 = null;
		ResultSetMetaData rsmd = null;
		ResultSet rs = null;
		String dbType = null;
		try {
			dbType = DBConnectionFactory.getDatabaseType(destCon);
			psmt = srcCon.prepareStatement(sqlx);
			rs = psmt.executeQuery();
			rsmd = rs.getMetaData();

			int count = rsmd.getColumnCount();
			for (int i = 1; i <= count; i++) {
				int sqlType = rsmd.getColumnType(i);
				ColumnDefinition column = new ColumnDefinition();
				column.setColumnName(rsmd.getColumnName(i));
				column.setJavaType(FieldType.getJavaType(sqlType));
				column.setPrecision(rsmd.getPrecision(i));
				column.setScale(rsmd.getScale(i));
				column.setOrdinal(i);
				if (StringUtils.equalsIgnoreCase(primaryKeyColumn,
						column.getColumnName())) {
					column.setPrimaryKey(true);
					tableDefinition.setIdColumn(column);
				} else {
					columns.add(column);
				}
			}

			/**
			 * 重建目标表
			 */
			if (rebuildTargartTable) {
				tableDefinition.setColumns(columns);
				if (!DBUtils.tableExists(destCon, tableName)) {
					DBUtils.dropAndCreateTable(destCon, tableDefinition);
				} else {
					if (StringUtils.equalsIgnoreCase(dbType, "sqlite")) {
						DBUtils.dropAndCreateTable(destCon, tableDefinition);
					}
				}
			}

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (psmt != null) {
				try {
					psmt.close();
				} catch (SQLException e) {
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
		}

		if (tableDefinition.getIdColumn() != null) {
			columns.add(0, tableDefinition.getIdColumn());
		}

		StringBuilder sb = new StringBuilder();
		sb.append(" insert into ").append(tableName).append(" (");

		int index = 0;
		for (ColumnDefinition column : columns) {
			index++;

			sb.append(column.getColumnName());
			if (index < columns.size()) {
				sb.append(", ");

			}
		}

		sb.append(" ) values ( ");

		for (int i = 0, len = columns.size(); i < len; i++) {
			sb.append(" ? ");
			if (i < columns.size() - 1) {
				sb.append(", ");
			}
		}

		sb.append(" ) ");

		logger.info("select sql:\n" + sql);
		logger.info("insert sql:\n" + sb.toString());

		boolean autoCommit = false;

		try {
			autoCommit = destCon.getAutoCommit();
			destCon.setAutoCommit(false);
			psmt2 = destCon.prepareStatement(sb.toString());
			psmt = srcCon.prepareStatement(sql);
			rs = psmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				i++;
				// logger.debug(i);
				for (ColumnDefinition column : columns) {
					String javaType = column.getJavaType();
					if ("Integer".equals(javaType)) {
						try {
							psmt2.setInt(column.getOrdinal(),
									rs.getInt(column.getOrdinal()));
						} catch (Exception ex) {
							logger.error(column.getColumnName()
									+ " datatype error");
							psmt2.setString(column.getOrdinal(),
									rs.getString(column.getOrdinal()));
						}
					} else if ("Long".equals(javaType)) {
						try {
							psmt2.setLong(column.getOrdinal(),
									rs.getLong(column.getOrdinal()));
						} catch (Exception ex) {
							logger.error(column.getColumnName()
									+ " datatype error");
							psmt2.setString(column.getOrdinal(),
									rs.getString(column.getOrdinal()));
						}
					} else if ("Double".equals(javaType)) {
						try {
							psmt2.setDouble(column.getOrdinal(),
									rs.getDouble(column.getOrdinal()));
						} catch (Exception ex) {
							logger.error(column.getColumnName()
									+ " datatype error");
							psmt2.setString(column.getOrdinal(),
									rs.getString(column.getOrdinal()));
						}
					} else if ("Date".equals(javaType)) {
						if ("sqlite".equals(dbType)) {
							Timestamp t = rs.getTimestamp(column.getOrdinal());
							if (t != null) {
								psmt2.setString(column.getOrdinal(),
										DateUtils.getDate(t));
							} else {
								psmt2.setString(column.getOrdinal(), null);
							}
						} else {
							psmt2.setTimestamp(column.getOrdinal(),
									rs.getTimestamp(column.getOrdinal()));
						}
					} else if ("Boolean".equals(javaType)) {
						try {
							psmt2.setBoolean(column.getOrdinal(),
									rs.getBoolean(column.getOrdinal()));
						} catch (Exception ex) {
							logger.error(column.getColumnName()
									+ " datatype error");
							psmt2.setString(column.getOrdinal(),
									rs.getString(column.getOrdinal()));
						}
					} else if ("String".equals(javaType)) {
						psmt2.setString(column.getOrdinal(),
								rs.getString(column.getOrdinal()));
					} else {
						try {
							psmt2.setObject(column.getOrdinal(),
									rs.getObject(column.getOrdinal()));
						} catch (Exception ex) {
							ex.printStackTrace();
							psmt2.setNull(column.getOrdinal(), Types.NULL);
						}
					}
				}
				psmt2.addBatch();
				if (i > 0 && i % 50 == 0) {
					logger.debug("process " + i + " row ");
					psmt2.executeBatch();
					if (i > 0 && i % 1000 == 0) {
						destCon.commit();
						logger.debug(" commit " + i);
					}
				}
			}

			logger.debug("total process " + i + " row ");

			psmt2.executeBatch();
			destCon.commit();
			psmt2.close();
			psmt2 = null;

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			try {
				destCon.setAutoCommit(autoCommit);
			} catch (SQLException e) {
			}
			if (psmt2 != null) {
				try {
					psmt2.close();
				} catch (SQLException e) {
				}
			}
			if (psmt != null) {
				try {
					psmt.close();
				} catch (SQLException e) {
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	public void transferQueryToTable(String srcSystemName,
			String destSystemName, String sql, String tableName,
			String primaryKeyColumn, boolean rebuildTargartTable) {
		Connection srcCon = null;
		Connection destCon = null;
		try {
			srcCon = DBConnectionFactory.getConnection(srcSystemName);
			destCon = DBConnectionFactory.getConnection(destSystemName);
			this.transferQueryToTable(srcCon, destCon, sql, tableName,
					primaryKeyColumn, rebuildTargartTable);
		} finally {
			JdbcUtils.close(srcCon);
			JdbcUtils.close(destCon);
		}
	}

	public void transferTable(Connection srcCon, Connection destCon,
			String tableName, String primaryKeyColumn,
			boolean rebuildTargartTable) {
		String sql = " select * from " + tableName;
		this.transferQueryToTable(srcCon, destCon, sql, tableName,
				primaryKeyColumn, rebuildTargartTable);
	}

	public void transferTable(String srcSystemName, String destSystemName,
			String tableName, String primaryKeyColumn,
			boolean rebuildTargartTable) {
		String sql = " select * from " + tableName;
		this.transferQueryToTable(srcSystemName, destSystemName, sql,
				tableName, primaryKeyColumn, rebuildTargartTable);
	}

}