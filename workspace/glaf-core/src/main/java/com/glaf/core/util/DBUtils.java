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

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.glaf.core.base.FieldDefinition;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.el.ExpressionTools;
import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.jdbc.DBConnectionFactory;

public class DBUtils {

	protected final static Log logger = LogFactory.getLog(DBUtils.class);

	public final static String newline = System.getProperty("line.separator");

	public static final String POSTGRESQL = "postgresql";

	public static final String ORACLE = "oracle";

	public static void alterTable(Connection connection,
			TableDefinition tableDefinition) {
		List<String> cloumns = new java.util.ArrayList<String>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String dbType = DBConnectionFactory.getDatabaseType(connection);
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select * from "
					+ tableDefinition.getTableName() + " where 1=0 ");
			ResultSetMetaData rss = rs.getMetaData();
			int columnCount = rss.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				String column = rss.getColumnName(i);
				cloumns.add(column.toUpperCase());
			}

			logger.debug(tableDefinition.getTableName() + " cloumns:" + cloumns);

			stmt.close();
			rs.close();
			stmt = null;
			rs = null;

			Collection<ColumnDefinition> fields = tableDefinition.getColumns();
			for (ColumnDefinition field : fields) {
				if (field.getColumnName() != null
						&& !cloumns.contains(field.getColumnName()
								.toUpperCase())) {
					String sql = getMyAddColumnSql(dbType,
							tableDefinition.getTableName(), field);
					if (sql != null && sql.length() > 0) {
						Statement statement = connection.createStatement();
						logger.info("alter table "
								+ tableDefinition.getTableName() + ":\n" + sql);
						statement.execute(sql);
						statement.close();
						statement = null;
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	public static void alterTable(String systemName, String tableName,
			List<ColumnDefinition> columns) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSetMetaData rsmd = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<String> columnNames = new java.util.ArrayList<String>();
		try {
			conn = DBConnectionFactory.getConnection();
			pstmt = conn.prepareStatement(" select * from " + tableName
					+ " where 1=0 ");
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			for (int i = 1; i <= count; i++) {
				columnNames.add(rsmd.getColumnName(i).toLowerCase());
			}

			if (columns != null && !columns.isEmpty()) {
				String dbType = DBConnectionFactory.getDatabaseType(conn);
				for (ColumnDefinition column : columns) {
					if (columnNames.contains(column.getColumnName()
							.toLowerCase())) {
						continue;
					}
					String javaType = column.getJavaType();
					String sql = " alter table " + tableName + " add "
							+ column.getColumnName();
					if ("db2".equalsIgnoreCase(dbType)) {
						if ("String".equals(javaType)) {
							if (column.getLength() > 0) {
								sql += " varchar(" + column.getLength() + ")";
							} else {
								sql += " varchar(50) ";
							}
						} else if ("Integer".equals(javaType)) {
							sql += " integer ";
						} else if ("Long".equals(javaType)) {
							sql += " bigint ";
						} else if ("Double".equals(javaType)) {
							sql += " double precision ";
						} else if ("Date".equals(javaType)) {
							sql += " timestamp ";
						} else if ("Clob".equals(javaType)) {
							sql += " clob(10240000) ";
						} else if ("Blob".equals(javaType)) {
							sql += " blob ";
						}
					} else if ("oracle".equalsIgnoreCase(dbType)) {
						if ("String".equals(javaType)) {
							if (column.getLength() > 0) {
								sql += " NVARCHAR2(" + column.getLength() + ")";
							} else {
								sql += " NVARCHAR2(50)";
							}
						} else if ("Integer".equals(javaType)) {
							sql += " INTEGER ";
						} else if ("Long".equals(javaType)) {
							sql += " NUMBER(19,0) ";
						} else if ("Double".equals(javaType)) {
							sql += " NUMBER(*,10) ";
						} else if ("Date".equals(javaType)) {
							sql += " TIMESTAMP(6) ";
						} else if ("Clob".equals(javaType)) {
							sql += " CLOB ";
						} else if ("Blob".equals(javaType)) {
							sql += " BLOB ";
						}
					} else if ("mysql".equalsIgnoreCase(dbType)) {
						if ("String".equals(javaType)) {
							if (column.getLength() > 0) {
								sql += " varchar(" + column.getLength() + ")";
							} else {
								sql += " varchar(50)";
							}
						} else if ("Integer".equals(javaType)) {
							sql += " int  ";
						} else if ("Long".equals(javaType)) {
							sql += " bigint ";
						} else if ("Double".equals(javaType)) {
							sql += " double ";
						} else if ("Date".equals(javaType)) {
							sql += " datetime ";
						} else if ("Clob".equals(javaType)) {
							sql += " longtext ";
						} else if ("Blob".equals(javaType)) {
							sql += " longblob ";
						}
					} else if ("postgresql".equalsIgnoreCase(dbType)) {
						if ("String".equals(javaType)) {
							if (column.getLength() > 0) {
								sql += " varchar(" + column.getLength() + ")";
							} else {
								sql += " varchar(50)";
							}
						} else if ("Integer".equals(javaType)) {
							sql += " integer ";
						} else if ("Long".equals(javaType)) {
							sql += " bigint ";
						} else if ("Double".equals(javaType)) {
							sql += " double precision ";
						} else if ("Date".equals(javaType)) {
							sql += " timestamp ";
						} else if ("Clob".equals(javaType)) {
							sql += " text ";
						} else if ("Blob".equals(javaType)) {
							sql += " bytea ";
						}
					} else if ("sqlserver".equalsIgnoreCase(dbType)) {
						if ("String".equals(javaType)) {
							if (column.getLength() > 0) {
								sql += " nvarchar(" + column.getLength() + ")";
							} else {
								sql += " nvarchar(50)";
							}
						} else if ("Integer".equals(javaType)) {
							sql += " int ";
						} else if ("Long".equals(javaType)) {
							sql += " numeric(19,0) ";
						} else if ("Double".equals(javaType)) {
							sql += " double precision ";
						} else if ("Date".equals(javaType)) {
							sql += " datetime ";
						} else if ("Clob".equals(javaType)) {
							sql += " text ";
						} else if ("Blob".equals(javaType)) {
							sql += " image ";
						}
					} else if ("h2".equalsIgnoreCase(dbType)) {
						if ("String".equals(javaType)) {
							if (column.getLength() > 0) {
								sql += " varchar(" + column.getLength() + ")";
							} else {
								sql += " varchar(50)";
							}
						} else if ("Integer".equals(javaType)) {
							sql += " int  ";
						} else if ("Long".equals(javaType)) {
							sql += " bigint ";
						} else if ("Double".equals(javaType)) {
							sql += " double ";
						} else if ("Date".equals(javaType)) {
							sql += " timestamp ";
						} else if ("Clob".equals(javaType)) {
							sql += " clob ";
						} else if ("Blob".equals(javaType)) {
							sql += " longvarbinary ";
						} else if ("Boolean".equals(javaType)) {
							sql += " BOOLEAN ";
						}
					} else if ("sqlite".equalsIgnoreCase(dbType)) {
						if ("String".equals(javaType)) {
							if (column.getLength() > 0) {
								sql += " TEXT(" + column.getLength() + ")";
							} else {
								sql += " TEXT(50)";
							}
						} else if ("Integer".equals(javaType)) {
							sql += " INTEGER  ";
						} else if ("Long".equals(javaType)) {
							sql += " INTEGER ";
						} else if ("Double".equals(javaType)) {
							sql += " REAL ";
						} else if ("Date".equals(javaType)) {
							sql += " TEXT ";
						} else if ("Clob".equals(javaType)) {
							sql += " TEXT ";
						} else if ("Blob".equals(javaType)) {
							sql += " BLOB ";
						}
					} else {
						if ("String".equals(javaType)) {
							if (column.getLength() > 0) {
								sql += " varchar(" + column.getLength() + ")";
							} else {
								sql += " varchar(50)";
							}
						} else if ("Integer".equals(javaType)) {
							sql += " int ";
						} else if ("Long".equals(javaType)) {
							sql += " bigint ";
						} else if ("Double".equals(javaType)) {
							sql += " double ";
						} else if ("Date".equals(javaType)) {
							sql += " timestamp ";
						} else if ("Clob".equals(javaType)) {
							sql += " clob ";
						} else if ("Blob".equals(javaType)) {
							sql += " blob ";
						}
					}
					logger.info("execute alter:" + sql);

					stmt = conn.createStatement();
					stmt.executeUpdate(sql);
					stmt.close();
					stmt = null;
				}
			}

			pstmt.close();
			pstmt = null;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
		}

	}

	public static void alterTable(TableDefinition tableDefinition) {
		List<String> cloumns = new java.util.ArrayList<String>();
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			connection = DBConnectionFactory.getConnection();
			String dbType = DBConnectionFactory.getDatabaseType(connection);
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select * from "
					+ tableDefinition.getTableName() + " where 1=0 ");
			ResultSetMetaData rss = rs.getMetaData();
			int columnCount = rss.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				String column = rss.getColumnName(i);
				cloumns.add(column.toUpperCase());
			}

			logger.debug(tableDefinition.getTableName() + " cloumns:" + cloumns);

			stmt.close();
			rs.close();
			stmt = null;
			rs = null;

			Collection<ColumnDefinition> fields = tableDefinition.getColumns();
			for (ColumnDefinition field : fields) {
				if (field.getColumnName() != null
						&& !cloumns.contains(field.getColumnName()
								.toUpperCase())) {
					String sql = getMyAddColumnSql(dbType,
							tableDefinition.getTableName(), field);
					if (sql != null && sql.length() > 0) {
						Statement statement = connection.createStatement();
						logger.info("alter table "
								+ tableDefinition.getTableName() + ":\n" + sql);
						statement.execute(sql);
						statement.close();
						statement = null;
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(connection);
		}
	}

	public static void alterTable(String systemName,
			TableDefinition tableDefinition) {
		List<String> cloumns = new java.util.ArrayList<String>();
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			connection = DBConnectionFactory.getConnection(systemName);
			String dbType = DBConnectionFactory.getDatabaseType(connection);
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select * from "
					+ tableDefinition.getTableName() + " where 1=0 ");
			ResultSetMetaData rss = rs.getMetaData();
			int columnCount = rss.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				String column = rss.getColumnName(i);
				cloumns.add(column.toUpperCase());
			}

			logger.debug(tableDefinition.getTableName() + " cloumns:" + cloumns);

			stmt.close();
			rs.close();
			stmt = null;
			rs = null;

			Collection<ColumnDefinition> fields = tableDefinition.getColumns();
			for (ColumnDefinition field : fields) {
				if (field.getColumnName() != null
						&& !cloumns.contains(field.getColumnName()
								.toUpperCase())) {
					String sql = getMyAddColumnSql(dbType,
							tableDefinition.getTableName(), field);
					if (sql != null && sql.length() > 0) {
						Statement statement = connection.createStatement();
						logger.info("alter table "
								+ tableDefinition.getTableName() + ":\n" + sql);
						statement.execute(sql);
						statement.close();
						statement = null;
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(connection);
		}
	}

	/**
	 * 创建数据库表，如果已经存在，则不重建
	 * 
	 * @param connection
	 *            JDBC连接
	 * @param tableDefinition
	 *            表定义
	 */
	public static void createTable(Connection connection,
			TableDefinition tableDefinition) {
		try {
			String tableName = tableDefinition.getTableName();
			if (tableExists(connection, tableName)) {
				return;
			}
			String dbType = DBConnectionFactory.getDatabaseType(connection);
			logger.info("dbType:" + dbType);
			String sql = getCreateTableScript(dbType, tableDefinition);
			if (sql != null && sql.length() > 0) {
				connection.setAutoCommit(false);
				Statement statement = connection.createStatement();
				logger.info("create table " + tableDefinition.getTableName()
						+ ":\n" + sql);
				statement.execute(sql);
				statement.close();
				connection.commit();
				statement = null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	public static void createTable(TableDefinition classDefinition) {
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getConnection();
			connection.setAutoCommit(false);
			String dbType = DBConnectionFactory.getDatabaseType(connection);
			String sql = getCreateTableSql(dbType, classDefinition);
			if (sql != null && sql.length() > 0) {
				Statement statement = connection.createStatement();
				logger.info("create table " + classDefinition.getTableName()
						+ ":\n" + sql);
				statement.execute(sql);
				statement.close();
				connection.commit();
				statement = null;
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(connection);
		}
	}

	public static void createTable(String systemName,
			TableDefinition classDefinition) {
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getConnection(systemName);
			connection.setAutoCommit(false);
			String dbType = DBConnectionFactory.getDatabaseType(connection);
			String sql = getCreateTableSql(dbType, classDefinition);
			if (sql != null && sql.length() > 0) {
				Statement statement = connection.createStatement();
				logger.info("create table " + classDefinition.getTableName()
						+ ":\n" + sql);
				statement.execute(sql);
				statement.close();
				connection.commit();
				statement = null;
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(connection);
		}
	}

	/**
	 * 创建数据库表，如果已经存在，则删除重建
	 * 
	 * @param connection
	 *            JDBC连接
	 * @param tableDefinition
	 *            表定义
	 */
	public static void dropAndCreateTable(Connection connection,
			TableDefinition tableDefinition) {
		String tableName = tableDefinition.getTableName();
		logger.info("check table:" + tableName);
		if (tableExists(connection, tableName)) {
			dropTable(connection, tableName);
		}
		if (!tableExists(connection, tableName)) {
			createTable(connection, tableDefinition);
		}
	}

	/**
	 * 如果已经存在，则删除
	 * 
	 * @param connection
	 *            JDBC连接
	 * @param tableDefinition
	 *            表定义
	 */
	public static void dropTable(Connection connection, String tableName) {
		logger.info("check table:" + tableName);
		if (tableExists(connection, tableName)) {
			/**
			 * 只能在开发模式下才能删除表
			 */
			String dbType = DBConnectionFactory.getDatabaseType(connection);
			if (System.getProperty("devMode") != null
					|| StringUtils.equalsIgnoreCase(dbType, "sqlite")
					|| StringUtils.equalsIgnoreCase(dbType, "h2")) {
				try {
					Statement statement = connection.createStatement();
					logger.info("drop table " + tableName);
					statement.executeUpdate(" drop table " + tableName);
					statement.close();
					statement = null;
				} catch (Exception ex) {
					ex.printStackTrace();
					throw new RuntimeException(ex);
				}
			}
		}
	}

	public static void executeSchemaResource(Connection conn,
			String ddlStatements) {
		Exception exception = null;
		Statement statement = null;
		String sqlStatement = null;
		try {
			StringTokenizer tokenizer = new StringTokenizer(ddlStatements, ";");
			while (tokenizer.hasMoreTokens()) {
				sqlStatement = tokenizer.nextToken().trim();
				if (!sqlStatement.startsWith("#") && !"".equals(sqlStatement)) {
					logger.debug(sqlStatement);
					statement = conn.createStatement();
					try {
						statement.executeUpdate(sqlStatement);
						statement.close();
						statement = null;
					} catch (Exception ex) {
						if (exception == null) {
							exception = ex;
						}
						logger.debug(" execute statement error: "
								+ sqlStatement, ex);
					} finally {
						if (statement != null) {
							statement.close();
						}
					}
				}
			}

			if (exception != null) {
				exception.printStackTrace();
				throw exception;
			}

			logger.info("execute db schema successful");

		} catch (Exception ex) {
			throw new RuntimeException("couldn't execute db schema: "
					+ sqlStatement, ex);
		}
	}

	public static void executeSchemaResource(String operation,
			String resourceName, InputStream inputStream,
			Map<String, Object> context) {
		Exception exception = null;
		Connection connection = null;
		String sqlStatement = null;
		try {
			connection = DBConnectionFactory.getConnection();
			byte[] bytes = FileUtils.getBytes(inputStream);
			String ddlStatements = new String(bytes);
			StringTokenizer tokenizer = new StringTokenizer(ddlStatements, ";");
			while (tokenizer.hasMoreTokens()) {
				sqlStatement = tokenizer.nextToken().trim();
				if (!sqlStatement.startsWith("#") && !"".equals(sqlStatement)) {
					sqlStatement = ExpressionTools.evaluate(sqlStatement,
							context);
					Statement statement = connection.createStatement();
					try {
						statement.execute(sqlStatement);
						statement.close();
					} catch (Exception e) {
						if (exception == null) {
							exception = e;
						}
						logger.debug("problem during schema " + operation
								+ ", statement '" + sqlStatement, e);
					}
				}
			}

			if (exception != null) {
				throw exception;
			}

			logger.info("extension db schema " + operation + " successful");

		} catch (Exception e) {
			throw new RuntimeException("couldn't " + operation + " db schema: "
					+ sqlStatement, e);
		} finally {
			JdbcUtils.close(connection);
		}
	}

	public static void executeSchemaResourceIgnoreException(Connection conn,
			String ddlStatements) {
		Statement statement = null;
		String sqlStatement = null;
		try {
			StringTokenizer tokenizer = new StringTokenizer(ddlStatements, ";");
			while (tokenizer.hasMoreTokens()) {
				sqlStatement = tokenizer.nextToken().trim();
				if (!sqlStatement.startsWith("#") && !"".equals(sqlStatement)) {
					logger.debug(sqlStatement);
					statement = conn.createStatement();
					try {
						statement.executeUpdate(sqlStatement);
						statement.close();
						statement = null;
					} catch (Exception ex) {
						logger.error(" execute statement error: "
								+ sqlStatement, ex);
					} finally {
						if (statement != null) {
							statement.close();
						}
					}
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException("couldn't execute db schema: "
					+ sqlStatement, ex);
		}
	}

	public static String getAddColumnSql(String dbType, String tableName,
			ColumnDefinition field) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" alter table ").append(tableName);
		buffer.append(" add ").append(field.getColumnName());
		if ("h2".equalsIgnoreCase(dbType)) {
			if ("Integer".equals(field.getJavaType())) {
				buffer.append(" integer ");
			} else if ("Long".equals(field.getJavaType())) {
				buffer.append(" bigint ");
			} else if ("Double".equals(field.getJavaType())) {
				buffer.append(" double ");
			} else if ("Date".equals(field.getJavaType())) {
				buffer.append(" timestamp ");
			} else if ("Clob".equals(field.getJavaType())) {
				buffer.append(" clob ");
			} else if ("Blob".equals(field.getJavaType())) {
				buffer.append(" longvarbinary ");
			} else if ("String".equals(field.getJavaType())) {
				buffer.append(" varchar ");
				if (field.getLength() > 0) {
					buffer.append(" (").append(field.getLength()).append(") ");
				} else {
					buffer.append(" (50) ");
				}
			}
		} else if ("db2".equalsIgnoreCase(dbType)) {
			if ("Integer".equals(field.getJavaType())) {
				buffer.append(" integer ");
			} else if ("Long".equals(field.getJavaType())) {
				buffer.append(" bigint ");
			} else if ("Double".equals(field.getJavaType())) {
				buffer.append(" double precision ");
			} else if ("Date".equals(field.getJavaType())) {
				buffer.append(" timestamp ");
			} else if ("Clob".equals(field.getJavaType())) {
				buffer.append(" clob (10240000) ");
			} else if ("Blob".equals(field.getJavaType())) {
				buffer.append(" blob ");
			} else if ("String".equals(field.getJavaType())) {
				buffer.append(" varchar ");
				if (field.getLength() > 0) {
					buffer.append(" (").append(field.getLength()).append(") ");
				} else {
					buffer.append(" (50) ");
				}
			}
		} else if ("oracle".equalsIgnoreCase(dbType)) {
			if ("Integer".equals(field.getJavaType())) {
				buffer.append(" INTEGER ");
			} else if ("Long".equals(field.getJavaType())) {
				buffer.append(" NUMBER(19,0) ");
			} else if ("Double".equals(field.getJavaType())) {
				buffer.append(" NUMBER(*,10) ");
			} else if ("Date".equals(field.getJavaType())) {
				buffer.append(" TIMESTAMP(6) ");
			} else if ("Clob".equals(field.getJavaType())) {
				buffer.append(" CLOB ");
			} else if ("Blob".equals(field.getJavaType())) {
				buffer.append(" BLOB ");
			} else if ("String".equals(field.getJavaType())) {
				buffer.append(" NVARCHAR2 ");
				if (field.getLength() > 0) {
					buffer.append(" (").append(field.getLength()).append(") ");
				} else {
					buffer.append(" (50) ");
				}
			}
		} else if ("mysql".equalsIgnoreCase(dbType)) {
			if ("Integer".equals(field.getJavaType())) {
				buffer.append(" integer ");
			} else if ("Long".equals(field.getJavaType())) {
				buffer.append(" bigint ");
			} else if ("Double".equals(field.getJavaType())) {
				buffer.append(" double ");
			} else if ("Date".equals(field.getJavaType())) {
				buffer.append(" datetime ");
			} else if ("Clob".equals(field.getJavaType())) {
				buffer.append(" longtext ");
			} else if ("Blob".equals(field.getJavaType())) {
				buffer.append(" longblob ");
			} else if ("String".equals(field.getJavaType())) {
				buffer.append(" varchar");
				if (field.getLength() > 0) {
					buffer.append("(").append(field.getLength()).append(") ");
				} else {
					buffer.append("(50) ");
				}
			}
		} else if ("sqlserver".equalsIgnoreCase(dbType)) {
			if ("Integer".equals(field.getJavaType())) {
				buffer.append(" integer ");
			} else if ("Long".equals(field.getJavaType())) {
				buffer.append(" bigint ");
			} else if ("Double".equals(field.getJavaType())) {
				buffer.append(" double precision ");
			} else if ("Date".equals(field.getJavaType())) {
				buffer.append(" datetime ");
			} else if ("Clob".equals(field.getJavaType())) {
				buffer.append(" text ");
			} else if ("Blob".equals(field.getJavaType())) {
				buffer.append(" image ");
			} else if ("String".equals(field.getJavaType())) {
				buffer.append(" nvarchar ");
				if (field.getLength() > 0) {
					buffer.append(" (").append(field.getLength()).append(") ");
				} else {
					buffer.append(" (50) ");
				}
			}
		} else if (POSTGRESQL.equalsIgnoreCase(dbType)) {
			if ("Integer".equals(field.getJavaType())) {
				buffer.append(" integer ");
			} else if ("Long".equals(field.getJavaType())) {
				buffer.append(" bigint ");
			} else if ("Double".equals(field.getJavaType())) {
				buffer.append(" double precision ");
			} else if ("Date".equals(field.getJavaType())) {
				buffer.append(" timestamp ");
			} else if ("Clob".equals(field.getJavaType())) {
				buffer.append(" text ");
			} else if ("Blob".equals(field.getJavaType())) {
				buffer.append(" bytea ");
			} else if ("String".equals(field.getJavaType())) {
				buffer.append(" varchar ");
				if (field.getLength() > 0) {
					buffer.append(" (").append(field.getLength()).append(") ");
				} else {
					buffer.append(" (50) ");
				}
			}
		} else if ("sqlite".equalsIgnoreCase(dbType)) {
			if ("Integer".equals(field.getJavaType())) {
				buffer.append(" INTEGER ");
			} else if ("Long".equals(field.getJavaType())) {
				buffer.append(" INTEGER ");
			} else if ("Double".equals(field.getJavaType())) {
				buffer.append(" REAL ");
			} else if ("Date".equals(field.getJavaType())) {
				buffer.append(" TEXT ");
			} else if ("Clob".equals(field.getJavaType())) {
				buffer.append(" TEXT ");
			} else if ("Blob".equals(field.getJavaType())) {
				buffer.append(" BLOB ");
			} else if ("String".equals(field.getJavaType())) {
				buffer.append(" TEXT ");
				if (field.getLength() > 0) {
					buffer.append(" (").append(field.getLength()).append(") ");
				} else {
					buffer.append(" (50) ");
				}
			}
		} else {
			throw new RuntimeException(dbType
					+ " is not support database type.");
		}

		buffer.append(";");

		return buffer.toString();
	}

	public static String getAlterTable(TableDefinition classDefinition) {
		StringBuffer buffer = new StringBuffer();
		List<String> cloumns = new java.util.ArrayList<String>();
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			connection = DBConnectionFactory.getConnection();
			String dbType = DBConnectionFactory.getDatabaseType(connection);
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select * from "
					+ classDefinition.getTableName() + " where 1=0 ");
			ResultSetMetaData rss = rs.getMetaData();
			int columnCount = rss.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				String column = rss.getColumnName(i);
				cloumns.add(column.toUpperCase());
			}

			stmt.close();
			rs.close();
			stmt = null;
			rs = null;

			Collection<ColumnDefinition> fields = classDefinition.getColumns();
			for (ColumnDefinition field : fields) {
				if (field.getColumnName() != null
						&& !cloumns.contains(field.getColumnName()
								.toUpperCase())) {
					String str = getAddColumnSql(dbType,
							classDefinition.getTableName(), field);
					buffer.append(str);
					buffer.append("\r\r");
				}
			}

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(connection);
		}

		return buffer.toString();
	}

	public static List<ColumnDefinition> getColumnDefinitions(Connection conn,
			String tableName) {
		List<ColumnDefinition> columns = new java.util.ArrayList<ColumnDefinition>();
		ResultSet rs = null;
		try {
			List<String> primaryKeys = getPrimaryKeys(conn, tableName);
			String dbType = DBConnectionFactory.getDatabaseType(conn);
			DatabaseMetaData metaData = conn.getMetaData();
			if ("h2".equals(dbType)) {
				tableName = tableName.toUpperCase();
			} else if ("oracle".equals(dbType)) {
				tableName = tableName.toUpperCase();
			} else if ("db2".equals(dbType)) {
				tableName = tableName.toUpperCase();
			} else if ("mysql".equals(dbType)) {
				tableName = tableName.toLowerCase();
			} else if ("postgresql".equals(dbType)) {
				tableName = tableName.toLowerCase();
			}
			rs = metaData.getColumns(null, null, tableName, null);
			while (rs.next()) {
				String columnName = rs.getString("COLUMN_NAME");
				int dataType = rs.getInt("DATA_TYPE");
				int nullable = rs.getInt("NULLABLE");
				int length = rs.getInt("COLUMN_SIZE");
				int ordinal = rs.getInt("ORDINAL_POSITION");
				ColumnDefinition column = new ColumnDefinition();
				column.setName(StringTools.lower(StringTools
						.camelStyle(columnName)));
				column.setColumnName(columnName.toLowerCase());
				column.setTitle(column.getName());
				column.setEnglishTitle(column.getName());
				column.setJavaType(FieldType.getJavaType(dataType));
				if (nullable == 1) {
					column.setNullable(true);
				} else {
					column.setNullable(false);
				}
				column.setLength(length);
				column.setOrdinal(ordinal);

				if ("String".equals(column.getJavaType())) {
					if (column.getLength() > 8000) {
						column.setJavaType("Clob");
					}
				}

				if ("Double".equals(column.getJavaType())) {
					if (column.getLength() == 19) {
						column.setJavaType("Long");
					}
				}

				if (primaryKeys.contains(columnName.toLowerCase())) {
					column.setPrimaryKey(true);
				}

				if (!columns.contains(column)) {
					logger.debug("column name:" + column.getColumnName());
					columns.add(column);
				}
			}
			rs.close();
			rs = null;
			return columns;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(rs);
		}
	}

	public static List<ColumnDefinition> getColumnDefinitions(String tableName) {
		List<ColumnDefinition> columns = new java.util.ArrayList<ColumnDefinition>();
		Connection conn = null;
		ResultSet rs = null;
		try {
			List<String> primaryKeys = getPrimaryKeys(tableName);

			conn = DBConnectionFactory.getConnection();
			String dbType = DBConnectionFactory.getDatabaseType(conn);

			DatabaseMetaData metaData = conn.getMetaData();
			if ("h2".equals(dbType)) {
				tableName = tableName.toUpperCase();
			} else if ("oracle".equals(dbType)) {
				tableName = tableName.toUpperCase();
			} else if ("db2".equals(dbType)) {
				tableName = tableName.toUpperCase();
			} else if ("mysql".equals(dbType)) {
				tableName = tableName.toLowerCase();
			} else if ("postgresql".equals(dbType)) {
				tableName = tableName.toLowerCase();
			}
			rs = metaData.getColumns(null, null, tableName, null);
			while (rs.next()) {
				String name = rs.getString("COLUMN_NAME");
				int dataType = rs.getInt("DATA_TYPE");
				int nullable = rs.getInt("NULLABLE");
				int length = rs.getInt("COLUMN_SIZE");
				int ordinal = rs.getInt("ORDINAL_POSITION");
				ColumnDefinition column = new ColumnDefinition();
				column.setColumnName(name);
				column.setJavaType(FieldType.getJavaType(dataType));
				if (nullable == 1) {
					column.setNullable(true);
				} else {
					column.setNullable(false);
				}
				column.setLength(length);
				column.setOrdinal(ordinal);

				if ("String".equals(column.getJavaType())) {
					if (column.getLength() > 8000) {
						column.setJavaType("Clob");
					}
				}

				if ("Double".equals(column.getJavaType())) {
					if (column.getLength() == 19) {
						column.setJavaType("Long");
					}
				}

				if (primaryKeys.contains(name)
						|| primaryKeys.contains(name.toUpperCase())
						|| primaryKeys.contains(name.toLowerCase())) {
					column.setPrimaryKey(true);
				}

				if (!columns.contains(column)) {
					columns.add(column);
				}
			}
			rs.close();
			rs = null;
			return columns;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(rs);
			JdbcUtils.close(conn);
		}
	}

	public static List<ColumnDefinition> getColumnDefinitions(
			String systemName, String tableName) {
		Connection conn = null;
		try {
			conn = DBConnectionFactory.getConnection(systemName);
			return getColumnDefinitions(conn, tableName);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
		}
	}

	@SuppressWarnings("unchecked")
	public static List<ColumnDefinition> getColumns(Connection conn,
			String sql, Map<String, Object> paramMap) {
		List<ColumnDefinition> columns = new java.util.ArrayList<ColumnDefinition>();
		PreparedStatement psmt = null;
		ResultSetMetaData rsmd = null;
		ResultSet rs = null;
		try {
			List<Object> values = null;
			if (paramMap != null) {
				SqlExecutor sqlExecutor = DBUtils.replaceSQL(sql, paramMap);
				sql = sqlExecutor.getSql();
				values = (List<Object>) sqlExecutor.getParameter();
			}

			logger.debug("sql:\n" + sql);
			logger.debug("values:" + values);

			psmt = conn.prepareStatement(sql);

			if (values != null && !values.isEmpty()) {
				JdbcUtils.fillStatement(psmt, values);
			}

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
				if (column.getScale() == 0 && sqlType == Types.NUMERIC) {
					column.setJavaType("Long");
				}
				if (!columns.contains(column)) {
					columns.add(column);
				}
				logger.debug(column.getColumnName() + " sqlType:" + sqlType
						+ " precision:" + column.getPrecision() + " scale:"
						+ column.getScale());
			}

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(psmt);
			JdbcUtils.close(rs);
		}
		return columns;
	}

	public static String getCreateTableDDL() {
		StringBuffer buffer = new StringBuffer();
		List<String> tables = getTables();
		String dbType = DBConnectionFactory.getDatabaseType();
		if (tables != null && !tables.isEmpty()) {
			for (String tableName : tables) {
				List<ColumnDefinition> columns = getColumnDefinitions(tableName);
				TableDefinition tableDefinition = new TableDefinition();
				tableDefinition.setTableName(tableName);
				List<String> pks = getPrimaryKeys(tableName);
				if (pks != null && columns != null && !columns.isEmpty()) {
					for (ColumnDefinition c : columns) {
						if (pks.contains(c.getColumnName())) {
							c.setPrimaryKey(true);
							tableDefinition.setIdColumn(c);
						}
					}
				}
				tableDefinition.setColumns(columns);
				String str = getCreateTableScript(dbType, tableDefinition);
				buffer.append(str);
				buffer.append(FileUtils.newline);
				buffer.append(FileUtils.newline);
			}
		}
		return buffer.toString();
	}

	public static String getCreateTableDDL(String targetDbType) {
		StringBuffer buffer = new StringBuffer();
		List<String> tables = getTables();

		if (tables != null && !tables.isEmpty()) {
			for (String tableName : tables) {
				List<ColumnDefinition> columns = getColumnDefinitions(tableName);
				TableDefinition tableDefinition = new TableDefinition();
				tableDefinition.setTableName(tableName);
				List<String> pks = getPrimaryKeys(tableName);
				if (pks != null && columns != null && !columns.isEmpty()) {
					for (ColumnDefinition c : columns) {
						if (pks.contains(c.getColumnName())) {
							c.setPrimaryKey(true);
							tableDefinition.setIdColumn(c);
						}
					}
				}
				tableDefinition.setColumns(columns);
				String str = getCreateTableScript(targetDbType, tableDefinition);
				buffer.append(str);
				buffer.append(FileUtils.newline);
				buffer.append(FileUtils.newline);
			}
		}
		return buffer.toString();
	}

	public static String getCreateTableScript(String dbType,
			TableDefinition tableDefinition) {
		StringBuffer buffer = new StringBuffer();
		Collection<ColumnDefinition> columns = tableDefinition.getColumns();
		buffer.append(" create table ").append(
				tableDefinition.getTableName().toUpperCase());
		buffer.append(" ( ");
		Collection<String> cols = new HashSet<String>();
		ColumnDefinition idField = tableDefinition.getIdColumn();
		if (idField != null) {
			buffer.append(newline);
			buffer.append("    ").append(idField.getColumnName().toUpperCase());
			if ("db2".equalsIgnoreCase(dbType)) {
				if ("Integer".equals(idField.getJavaType())) {
					buffer.append(" integer ");
				} else if ("Long".equals(idField.getJavaType())) {
					buffer.append(" bigint ");
				} else if ("Double".equals(idField.getJavaType())) {
					buffer.append(" double precision ");
				} else if ("Date".equals(idField.getJavaType())) {
					buffer.append(" timestamp ");
				} else if ("String".equals(idField.getJavaType())) {
					buffer.append(" varchar ");
					if (idField.getLength() > 0) {
						buffer.append(" (").append(idField.getLength())
								.append(") ");
					} else {
						buffer.append(" (50) ");
					}
				}
			} else if ("oracle".equalsIgnoreCase(dbType)) {
				if ("Integer".equals(idField.getJavaType())) {
					buffer.append(" INTEGER ");
				} else if ("Long".equals(idField.getJavaType())) {
					buffer.append(" NUMBER(19,0) ");
				} else if ("Double".equals(idField.getJavaType())) {
					buffer.append(" NUMBER(*,10) ");
				} else if ("Date".equals(idField.getJavaType())) {
					buffer.append(" TIMESTAMP(6) ");
				} else if ("String".equals(idField.getJavaType())) {
					buffer.append(" NVARCHAR2 ");
					if (idField.getLength() > 0) {
						buffer.append(" (").append(idField.getLength())
								.append(") ");
					} else {
						buffer.append(" (50) ");
					}
				}
			} else if ("mysql".equalsIgnoreCase(dbType)) {
				if ("Integer".equals(idField.getJavaType())) {
					buffer.append(" integer ");
				} else if ("Long".equals(idField.getJavaType())) {
					buffer.append(" bigint ");
				} else if ("Double".equals(idField.getJavaType())) {
					buffer.append(" double ");
				} else if ("Date".equals(idField.getJavaType())) {
					buffer.append(" datetime ");
				} else if ("String".equals(idField.getJavaType())) {
					buffer.append(" varchar ");
					if (idField.getLength() > 0) {
						buffer.append(" (").append(idField.getLength())
								.append(") ");
					} else {
						buffer.append(" (50) ");
					}
				}
			} else if ("sqlserver".equalsIgnoreCase(dbType)) {
				if ("Integer".equals(idField.getJavaType())) {
					buffer.append(" integer ");
				} else if ("Long".equals(idField.getJavaType())) {
					buffer.append(" bigint ");
				} else if ("Double".equals(idField.getJavaType())) {
					buffer.append(" double precision ");
				} else if ("Date".equals(idField.getJavaType())) {
					buffer.append(" datetime ");
				} else if ("String".equals(idField.getJavaType())) {
					buffer.append(" nvarchar ");
					if (idField.getLength() > 0) {
						buffer.append(" (").append(idField.getLength())
								.append(") ");
					} else {
						buffer.append(" (50) ");
					}
				}
			} else if ("postgresql".equalsIgnoreCase(dbType)) {
				if ("Integer".equals(idField.getJavaType())) {
					buffer.append(" integer ");
				} else if ("Long".equals(idField.getJavaType())) {
					buffer.append(" bigint ");
				} else if ("Double".equals(idField.getJavaType())) {
					buffer.append(" double precision ");
				} else if ("Date".equals(idField.getJavaType())) {
					buffer.append(" timestamp ");
				} else if ("String".equals(idField.getJavaType())) {
					buffer.append(" varchar ");
					if (idField.getLength() > 0) {
						buffer.append(" (").append(idField.getLength())
								.append(") ");
					} else {
						buffer.append(" (50) ");
					}
				}
			} else if ("h2".equalsIgnoreCase(dbType)) {
				if ("Integer".equals(idField.getJavaType())) {
					buffer.append(" integer ");
				} else if ("Long".equals(idField.getJavaType())) {
					buffer.append(" bigint ");
				} else if ("Double".equals(idField.getJavaType())) {
					buffer.append(" double ");
				} else if ("Date".equals(idField.getJavaType())) {
					buffer.append(" timestamp ");
				} else if ("String".equals(idField.getJavaType())) {
					buffer.append(" varchar ");
					if (idField.getLength() > 0) {
						buffer.append(" (").append(idField.getLength())
								.append(") ");
					} else {
						buffer.append(" (50) ");
					}
				}
			} else if ("sqlite".equalsIgnoreCase(dbType)) {
				if ("Integer".equals(idField.getJavaType())) {
					buffer.append(" INTEGER ");
				} else if ("Long".equals(idField.getJavaType())) {
					buffer.append(" INTEGER ");
				} else if ("Double".equals(idField.getJavaType())) {
					buffer.append(" REAL ");
				} else if ("Date".equals(idField.getJavaType())) {
					buffer.append(" TEXT ");
				} else if ("String".equals(idField.getJavaType())) {
					buffer.append(" TEXT ");
				}
			}
			buffer.append(" not null ");
		}
		int index = 0;
		for (ColumnDefinition column : columns) {
			if (StringUtils.isEmpty(column.getColumnName())
					|| StringUtils.isEmpty(column.getJavaType())
					|| (idField != null && StringUtils.equalsIgnoreCase(
							idField.getColumnName(), column.getColumnName()))) {
				continue;
			}
			if (cols.contains(column.getColumnName().toLowerCase())) {
				continue;
			}
			if (idField != null) {
				buffer.append(",");
			} else {
				if (index > 0) {
					buffer.append(",");
				}
			}

			index++;
			buffer.append(newline);
			buffer.append("    ").append(column.getColumnName().toUpperCase());
			if ("db2".equalsIgnoreCase(dbType)) {
				if ("Integer".equals(column.getJavaType())) {
					buffer.append(" integer ");
				} else if ("Long".equals(column.getJavaType())) {
					buffer.append(" bigint ");
				} else if ("Double".equals(column.getJavaType())) {
					buffer.append(" double precision ");
				} else if ("Date".equals(column.getJavaType())) {
					buffer.append(" timestamp ");
				} else if ("Clob".equals(column.getJavaType())) {
					buffer.append(" clob (10240000) ");
				} else if ("Blob".equals(column.getJavaType())) {
					buffer.append(" blob ");
				} else if ("String".equals(column.getJavaType())) {
					buffer.append(" varchar ");
					if (column.getLength() > 0 && column.getLength() <= 4000) {
						buffer.append(" (").append(column.getLength())
								.append(") ");
					} else {
						buffer.append(" (250) ");
					}
				}
			} else if ("oracle".equalsIgnoreCase(dbType)) {
				if ("Integer".equals(column.getJavaType())) {
					buffer.append(" INTEGER ");
				} else if ("Long".equals(column.getJavaType())) {
					buffer.append(" NUMBER(19,0) ");
				} else if ("Double".equals(column.getJavaType())) {
					buffer.append(" NUMBER(*,10) ");
				} else if ("Date".equals(column.getJavaType())) {
					buffer.append(" TIMESTAMP(6) ");
				} else if ("Clob".equals(column.getJavaType())) {
					buffer.append(" CLOB ");
				} else if ("Blob".equals(column.getJavaType())) {
					buffer.append(" BLOB ");
				} else if ("String".equals(column.getJavaType())) {
					buffer.append(" NVARCHAR2 ");
					if (column.getLength() > 0 && column.getLength() <= 4000) {
						buffer.append(" (").append(column.getLength())
								.append(") ");
					} else {
						buffer.append(" (250) ");
					}
				}
			} else if ("mysql".equalsIgnoreCase(dbType)) {
				if ("Integer".equals(column.getJavaType())) {
					buffer.append(" integer ");
				} else if ("Long".equals(column.getJavaType())) {
					buffer.append(" bigint ");
				} else if ("Double".equals(column.getJavaType())) {
					buffer.append(" double ");
				} else if ("Date".equals(column.getJavaType())) {
					buffer.append(" datetime ");
				} else if ("Clob".equals(column.getJavaType())) {
					buffer.append(" longtext ");
				} else if ("Blob".equals(column.getJavaType())) {
					buffer.append(" longblob ");
				} else if ("String".equals(column.getJavaType())) {
					buffer.append(" varchar ");
					if (column.getLength() > 0 && column.getLength() <= 4000) {
						buffer.append(" (").append(column.getLength())
								.append(") ");
					} else {
						buffer.append(" (250) ");
					}
				}
			} else if ("sqlserver".equalsIgnoreCase(dbType)) {
				if ("Integer".equals(column.getJavaType())) {
					buffer.append(" integer ");
				} else if ("Long".equals(column.getJavaType())) {
					buffer.append(" bigint ");
				} else if ("Double".equals(column.getJavaType())) {
					buffer.append(" double precision ");
				} else if ("Date".equals(column.getJavaType())) {
					buffer.append(" datetime ");
				} else if ("Clob".equals(column.getJavaType())) {
					buffer.append(" text ");
				} else if ("Blob".equals(column.getJavaType())) {
					buffer.append(" image ");
				} else if ("String".equals(column.getJavaType())) {
					buffer.append(" nvarchar ");
					if (column.getLength() > 0 && column.getLength() <= 4000) {
						buffer.append(" (").append(column.getLength())
								.append(") ");
					} else {
						buffer.append(" (250) ");
					}
				}
			} else if ("postgresql".equalsIgnoreCase(dbType)) {
				if ("Integer".equals(column.getJavaType())) {
					buffer.append(" integer ");
				} else if ("Long".equals(column.getJavaType())) {
					buffer.append(" bigint ");
				} else if ("Double".equals(column.getJavaType())) {
					buffer.append(" double precision ");
				} else if ("Date".equals(column.getJavaType())) {
					buffer.append(" timestamp ");
				} else if ("Clob".equals(column.getJavaType())) {
					buffer.append(" text ");
				} else if ("Blob".equals(column.getJavaType())) {
					buffer.append(" bytea ");
				} else if ("String".equals(column.getJavaType())) {
					buffer.append(" varchar ");
					if (column.getLength() > 0 && column.getLength() <= 4000) {
						buffer.append(" (").append(column.getLength())
								.append(") ");
					} else {
						buffer.append(" (250) ");
					}
				}
			} else if ("h2".equalsIgnoreCase(dbType)) {
				if ("Integer".equals(column.getJavaType())) {
					buffer.append(" integer ");
				} else if ("Long".equals(column.getJavaType())) {
					buffer.append(" bigint ");
				} else if ("Double".equals(column.getJavaType())) {
					buffer.append(" double ");
				} else if ("Date".equals(column.getJavaType())) {
					buffer.append(" timestamp ");
				} else if ("Boolean".equals(column.getJavaType())) {
					buffer.append(" boolean   ");
				} else if ("Clob".equals(column.getJavaType())) {
					buffer.append(" clob ");
				} else if ("Blob".equals(column.getJavaType())) {
					buffer.append(" longvarbinary ");
				} else if ("String".equals(column.getJavaType())) {
					buffer.append(" varchar ");
					if (column.getLength() > 0 && column.getLength() <= 4000) {
						buffer.append(" (").append(column.getLength())
								.append(") ");
					} else {
						buffer.append(" (50) ");
					}
				}
			} else if ("sqlite".equalsIgnoreCase(dbType)) {
				if ("Integer".equals(column.getJavaType())) {
					buffer.append(" INTEGER ");
				} else if ("Boolean".equals(column.getJavaType())) {
					buffer.append(" INTEGER ");
				} else if ("Long".equals(column.getJavaType())) {
					buffer.append(" INTEGER ");
				} else if ("Double".equals(column.getJavaType())) {
					buffer.append(" REAL ");
				} else if ("Date".equals(column.getJavaType())) {
					buffer.append(" TEXT ");
				} else if ("Clob".equals(column.getJavaType())) {
					buffer.append(" TEXT ");
				} else if ("Blob".equals(column.getJavaType())) {
					buffer.append(" BLOB ");
				} else if ("String".equals(column.getJavaType())) {
					buffer.append(" TEXT ");
				}
			}
			cols.add(column.getColumnName().toLowerCase());
		}

		if (idField != null) {
			buffer.append(",");
			buffer.append(newline);
			buffer.append("    primary key (")
					.append(idField.getColumnName().toUpperCase()).append(") ");
		}
		buffer.append(newline);
		buffer.append(");");

		return buffer.toString();
	}

	public static String getCreateTableSql(String dbType,
			TableDefinition classDefinition) {
		StringBuffer buffer = new StringBuffer();
		Collection<ColumnDefinition> fields = classDefinition.getColumns();
		buffer.append(" create table ").append(
				classDefinition.getTableName().toUpperCase());
		buffer.append(" ( ");
		Collection<String> cols = new HashSet<String>();
		ColumnDefinition idField = classDefinition.getIdColumn();
		if (idField != null) {
			buffer.append(newline);
			buffer.append("    ").append(idField.getColumnName().toUpperCase());
			if ("h2".equalsIgnoreCase(dbType)) {
				if ("Integer".equals(idField.getJavaType())) {
					buffer.append(" integer ");
				} else if ("Long".equals(idField.getJavaType())) {
					buffer.append(" bigint ");
				} else if ("String".equals(idField.getJavaType())) {
					buffer.append(" varchar ");
					if (idField.getLength() > 0) {
						buffer.append(" (").append(idField.getLength())
								.append(") ");
					} else {
						buffer.append(" (50) ");
					}
				}
			} else if ("db2".equalsIgnoreCase(dbType)) {
				if ("Integer".equals(idField.getJavaType())) {
					buffer.append(" integer ");
				} else if ("Long".equals(idField.getJavaType())) {
					buffer.append(" bigint ");
				} else if ("String".equals(idField.getJavaType())) {
					buffer.append(" varchar ");
					if (idField.getLength() > 0) {
						buffer.append(" (").append(idField.getLength())
								.append(") ");
					} else {
						buffer.append(" (50) ");
					}
				}
			} else if ("oracle".equalsIgnoreCase(dbType)) {
				if ("Integer".equals(idField.getJavaType())) {
					buffer.append(" INTEGER ");
				} else if ("Long".equals(idField.getJavaType())) {
					buffer.append(" NUMBER(19,0) ");
				} else if ("String".equals(idField.getJavaType())) {
					buffer.append(" NVARCHAR2 ");
					if (idField.getLength() > 0) {
						buffer.append(" (").append(idField.getLength())
								.append(") ");
					} else {
						buffer.append(" (50) ");
					}
				}
			} else if ("mysql".equalsIgnoreCase(dbType)) {
				if ("Integer".equals(idField.getJavaType())) {
					buffer.append(" integer ");
				} else if ("Long".equals(idField.getJavaType())) {
					buffer.append(" bigint ");
				} else if ("String".equals(idField.getJavaType())) {
					buffer.append(" varchar ");
					if (idField.getLength() > 0) {
						buffer.append(" (").append(idField.getLength())
								.append(") ");
					} else {
						buffer.append(" (50) ");
					}
				}
			} else if ("sqlserver".equalsIgnoreCase(dbType)) {
				if ("Integer".equals(idField.getJavaType())) {
					buffer.append(" integer ");
				} else if ("Long".equals(idField.getJavaType())) {
					buffer.append(" bigint ");
				} else if ("String".equals(idField.getJavaType())) {
					buffer.append(" nvarchar ");
					if (idField.getLength() > 0) {
						buffer.append(" (").append(idField.getLength())
								.append(") ");
					} else {
						buffer.append(" (50) ");
					}
				}
			} else if (POSTGRESQL.equalsIgnoreCase(dbType)) {
				if ("Integer".equals(idField.getJavaType())) {
					buffer.append(" integer ");
				} else if ("Long".equals(idField.getJavaType())) {
					buffer.append(" bigint ");
				} else if ("String".equals(idField.getJavaType())) {
					buffer.append(" varchar ");
					if (idField.getLength() > 0) {
						buffer.append(" (").append(idField.getLength())
								.append(") ");
					} else {
						buffer.append(" (50) ");
					}
				}
			} else if ("sqlite".equalsIgnoreCase(dbType)) {
				if ("Integer".equals(idField.getJavaType())) {
					buffer.append(" INTEGER ");
				} else if ("Long".equals(idField.getJavaType())) {
					buffer.append(" INTEGER ");
				} else if ("String".equals(idField.getJavaType())) {
					buffer.append(" TEXT ");
					if (idField.getLength() > 0) {
						buffer.append(" (").append(idField.getLength())
								.append(") ");
					} else {
						buffer.append(" (50) ");
					}
				}
			}
			buffer.append(" not null ");
		}
		int index = 0;
		for (ColumnDefinition field : fields) {
			if (StringUtils.isEmpty(field.getColumnName())
					|| (idField != null && StringUtils.equalsIgnoreCase(
							idField.getColumnName(), field.getColumnName()))) {
				continue;
			}
			if (cols.contains(field.getColumnName().toLowerCase())) {
				continue;
			}
			if (idField != null) {
				buffer.append(",");
			} else {
				if (index > 0) {
					buffer.append(",");
				}
			}
			index++;
			buffer.append(newline);
			buffer.append("    ").append(field.getColumnName());
			if ("h2".equalsIgnoreCase(dbType)) {
				if ("Integer".equals(field.getJavaType())) {
					buffer.append(" integer ");
				} else if ("Long".equals(field.getJavaType())) {
					buffer.append(" bigint ");
				} else if ("Double".equals(field.getJavaType())) {
					buffer.append(" double ");
				} else if ("Date".equals(field.getJavaType())) {
					buffer.append(" timestamp ");
				} else if ("Boolean".equals(field.getJavaType())) {
					buffer.append(" boolean ");
				} else if ("Clob".equals(field.getJavaType())) {
					buffer.append(" clob ");
				} else if ("Blob".equals(field.getJavaType())) {
					buffer.append(" longvarbinary ");
				} else if ("String".equals(field.getJavaType())) {
					buffer.append(" varchar ");
					if (field.getLength() > 0) {
						buffer.append(" (").append(field.getLength())
								.append(") ");
					} else {
						buffer.append(" (50) ");
					}
				}
			} else if ("db2".equalsIgnoreCase(dbType)) {
				if ("Integer".equals(field.getJavaType())) {
					buffer.append(" integer ");
				} else if ("Long".equals(field.getJavaType())) {
					buffer.append(" bigint ");
				} else if ("Double".equals(field.getJavaType())) {
					buffer.append(" double precision ");
				} else if ("Date".equals(field.getJavaType())) {
					buffer.append(" timestamp ");
				} else if ("Clob".equals(field.getJavaType())) {
					buffer.append(" clob (10240000) ");
				} else if ("Blob".equals(field.getJavaType())) {
					buffer.append(" blob ");
				} else if ("String".equals(field.getJavaType())) {
					buffer.append(" varchar ");
					if (field.getLength() > 0) {
						buffer.append(" (").append(field.getLength())
								.append(") ");
					} else {
						buffer.append(" (50) ");
					}
				}
			} else if ("oracle".equalsIgnoreCase(dbType)) {
				if ("Integer".equals(field.getJavaType())) {
					buffer.append(" INTEGER ");
				} else if ("Long".equals(field.getJavaType())) {
					buffer.append(" NUMBER(19,0) ");
				} else if ("Double".equals(field.getJavaType())) {
					buffer.append(" NUMBER(*,10) ");
				} else if ("Date".equals(field.getJavaType())) {
					buffer.append(" TIMESTAMP(6) ");
				} else if ("Clob".equals(field.getJavaType())) {
					buffer.append(" CLOB ");
				} else if ("Blob".equals(field.getJavaType())) {
					buffer.append(" BLOB ");
				} else if ("String".equals(field.getJavaType())) {
					buffer.append(" NVARCHAR2 ");
					if (field.getLength() > 0) {
						buffer.append(" (").append(field.getLength())
								.append(") ");
					} else {
						buffer.append(" (50) ");
					}
				}
			} else if ("mysql".equalsIgnoreCase(dbType)) {
				if ("Integer".equals(field.getJavaType())) {
					buffer.append(" integer ");
				} else if ("Long".equals(field.getJavaType())) {
					buffer.append(" bigint ");
				} else if ("Double".equals(field.getJavaType())) {
					buffer.append(" double ");
				} else if ("Date".equals(field.getJavaType())) {
					buffer.append(" datetime ");
				} else if ("Clob".equals(field.getJavaType())) {
					buffer.append(" longtext ");
				} else if ("Blob".equals(field.getJavaType())) {
					buffer.append(" longblob ");
				} else if ("String".equals(field.getJavaType())) {
					buffer.append(" varchar ");
					if (field.getLength() > 0) {
						buffer.append(" (").append(field.getLength())
								.append(") ");
					} else {
						buffer.append(" (50) ");
					}
				}
			} else if ("sqlserver".equalsIgnoreCase(dbType)) {
				if ("Integer".equals(field.getJavaType())) {
					buffer.append(" integer ");
				} else if ("Long".equals(field.getJavaType())) {
					buffer.append(" bigint ");
				} else if ("Double".equals(field.getJavaType())) {
					buffer.append(" double precision ");
				} else if ("Date".equals(field.getJavaType())) {
					buffer.append(" datetime ");
				} else if ("Clob".equals(field.getJavaType())) {
					buffer.append(" text ");
				} else if ("Blob".equals(field.getJavaType())) {
					buffer.append(" image ");
				} else if ("String".equals(field.getJavaType())) {
					buffer.append(" nvarchar ");
					if (field.getLength() > 0) {
						buffer.append(" (").append(field.getLength())
								.append(") ");
					} else {
						buffer.append(" (50) ");
					}
				}
			} else if (POSTGRESQL.equalsIgnoreCase(dbType)) {
				if ("Integer".equals(field.getJavaType())) {
					buffer.append(" integer ");
				} else if ("Long".equals(field.getJavaType())) {
					buffer.append(" bigint ");
				} else if ("Double".equals(field.getJavaType())) {
					buffer.append(" double precision ");
				} else if ("Date".equals(field.getJavaType())) {
					buffer.append(" timestamp ");
				} else if ("Clob".equals(field.getJavaType())) {
					buffer.append(" text ");
				} else if ("Blob".equals(field.getJavaType())) {
					buffer.append(" bytea ");
				} else if ("String".equals(field.getJavaType())) {
					buffer.append(" varchar ");
					if (field.getLength() > 0) {
						buffer.append(" (").append(field.getLength())
								.append(") ");
					} else {
						buffer.append(" (50) ");
					}
				}
			} else if ("sqlite".equalsIgnoreCase(dbType)) {
				if ("Integer".equals(field.getJavaType())) {
					buffer.append(" INTEGER ");
				} else if ("Long".equals(field.getJavaType())) {
					buffer.append(" INTEGER ");
				} else if ("Double".equals(field.getJavaType())) {
					buffer.append(" REAL ");
				} else if ("Date".equals(field.getJavaType())) {
					buffer.append(" TEXT ");
				} else if ("Clob".equals(field.getJavaType())) {
					buffer.append(" TEXT ");
				} else if ("Blob".equals(field.getJavaType())) {
					buffer.append(" BLOB ");
				} else if ("String".equals(field.getJavaType())) {
					buffer.append(" TEXT ");
					if (field.getLength() > 0) {
						buffer.append(" (").append(field.getLength())
								.append(") ");
					} else {
						buffer.append(" (50) ");
					}
				}
			}
			cols.add(field.getColumnName().toLowerCase());
		}

		if (idField != null) {
			buffer.append(",");
			buffer.append(newline);
			buffer.append("    primary key (").append(idField.getColumnName())
					.append(") ");
		}
		buffer.append(newline);

		if ("mysql".equalsIgnoreCase(dbType)) {
			buffer.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;");
		} else {
			buffer.append(");");
		}

		return buffer.toString();
	}

	public static List<FieldDefinition> getFieldDefinitions(String tableName) {
		List<FieldDefinition> columns = new java.util.ArrayList<FieldDefinition>();
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DBConnectionFactory.getConnection();
			List<String> primaryKeys = getPrimaryKeys(conn, tableName);
			String dbType = DBConnectionFactory.getDatabaseType(conn);
			DatabaseMetaData metaData = conn.getMetaData();

			if ("h2".equals(dbType)) {
				tableName = tableName.toUpperCase();
			} else if ("oracle".equals(dbType)) {
				tableName = tableName.toUpperCase();
			} else if ("db2".equals(dbType)) {
				tableName = tableName.toUpperCase();
			} else if ("mysql".equals(dbType)) {
				tableName = tableName.toLowerCase();
			} else if ("postgresql".equals(dbType)) {
				tableName = tableName.toLowerCase();
			}
			rs = metaData.getColumns(null, null, tableName, null);
			while (rs.next()) {
				String name = rs.getString("COLUMN_NAME");
				int dataType = rs.getInt("DATA_TYPE");
				int nullable = rs.getInt("NULLABLE");
				int length = rs.getInt("COLUMN_SIZE");
				int ordinal = rs.getInt("ORDINAL_POSITION");
				FieldDefinition column = new ColumnDefinition();
				column.setColumnName(name);
				column.setType(FieldType.getJavaType(dataType));
				if (nullable == 1) {
					column.setNullable(true);
				} else {
					column.setNullable(false);
				}
				column.setLength(length);
				column.setSortNo(ordinal);

				if ("String".equals(column.getType())) {
					if (column.getLength() > 8000) {
						column.setType("Clob");
					}
				}

				if (primaryKeys.contains(name)) {
					column.setNullable(false);
				}

				column.setName(StringTools.camelStyle(name));
				column.setEnglishTitle(StringTools.camelStyle(name));

				columns.add(column);
			}
			rs.close();
			rs = null;

			return columns;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(rs);
			JdbcUtils.close(conn);
		}
	}

	public static String getMyAddColumnSql(String dbType, String tableName,
			ColumnDefinition field) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" alter table ").append(tableName);
		buffer.append(" add ").append(field.getColumnName());
		if ("h2".equalsIgnoreCase(dbType)) {
			if ("Integer".equals(field.getJavaType())) {
				buffer.append(" integer ");
			} else if ("Long".equals(field.getJavaType())) {
				buffer.append(" bigint ");
			} else if ("Double".equals(field.getJavaType())) {
				buffer.append(" double ");
			} else if ("Date".equals(field.getJavaType())) {
				buffer.append(" timestamp ");
			} else if ("Boolean".equals(field.getJavaType())) {
				buffer.append(" boolean ");
			} else if ("Clob".equals(field.getJavaType())) {
				buffer.append(" clob ");
			} else if ("Blob".equals(field.getJavaType())) {
				buffer.append(" longvarbinary ");
			} else if ("String".equals(field.getJavaType())) {
				buffer.append(" varchar ");
				if (field.getLength() > 0) {
					buffer.append(" (").append(field.getLength()).append(") ");
				} else {
					buffer.append(" (50) ");
				}
			}
		} else if ("db2".equalsIgnoreCase(dbType)) {
			if ("Integer".equals(field.getJavaType())) {
				buffer.append(" integer ");
			} else if ("Long".equals(field.getJavaType())) {
				buffer.append(" bigint ");
			} else if ("Double".equals(field.getJavaType())) {
				buffer.append(" double precision ");
			} else if ("Date".equals(field.getJavaType())) {
				buffer.append(" timestamp ");
			} else if ("Clob".equals(field.getJavaType())) {
				buffer.append(" clob (10240000) ");
			} else if ("Blob".equals(field.getJavaType())) {
				buffer.append(" blob ");
			} else if ("String".equals(field.getJavaType())) {
				buffer.append(" varchar ");
				if (field.getLength() > 0) {
					buffer.append(" (").append(field.getLength()).append(") ");
				} else {
					buffer.append(" (50) ");
				}
			}
		} else if ("oracle".equalsIgnoreCase(dbType)) {
			if ("Integer".equals(field.getJavaType())) {
				buffer.append(" INTEGER ");
			} else if ("Long".equals(field.getJavaType())) {
				buffer.append(" NUMBER(19,0) ");
			} else if ("Double".equals(field.getJavaType())) {
				buffer.append(" NUMBER(*,10) ");
			} else if ("Date".equals(field.getJavaType())) {
				buffer.append(" TIMESTAMP(6) ");
			} else if ("Clob".equals(field.getJavaType())) {
				buffer.append(" CLOB ");
			} else if ("Blob".equals(field.getJavaType())) {
				buffer.append(" BLOB ");
			} else if ("String".equals(field.getJavaType())) {
				buffer.append(" NVARCHAR2 ");
				if (field.getLength() > 0) {
					buffer.append(" (").append(field.getLength()).append(") ");
				} else {
					buffer.append(" (50) ");
				}
			}
		} else if ("mysql".equalsIgnoreCase(dbType)) {
			if ("Integer".equals(field.getJavaType())) {
				buffer.append(" integer ");
			} else if ("Long".equals(field.getJavaType())) {
				buffer.append(" bigint ");
			} else if ("Double".equals(field.getJavaType())) {
				buffer.append(" double ");
			} else if ("Date".equals(field.getJavaType())) {
				buffer.append(" datetime ");
			} else if ("Clob".equals(field.getJavaType())) {
				buffer.append(" longtext ");
			} else if ("Blob".equals(field.getJavaType())) {
				buffer.append(" longblob ");
			} else if ("String".equals(field.getJavaType())) {
				buffer.append(" varchar");
				if (field.getLength() > 0) {
					buffer.append("(").append(field.getLength()).append(") ");
				} else {
					buffer.append("(50) ");
				}
			}
		} else if ("sqlserver".equalsIgnoreCase(dbType)) {
			if ("Integer".equals(field.getJavaType())) {
				buffer.append(" integer ");
			} else if ("Long".equals(field.getJavaType())) {
				buffer.append(" bigint ");
			} else if ("Double".equals(field.getJavaType())) {
				buffer.append(" double precision ");
			} else if ("Date".equals(field.getJavaType())) {
				buffer.append(" datetime ");
			} else if ("Clob".equals(field.getJavaType())) {
				buffer.append(" text ");
			} else if ("Blob".equals(field.getJavaType())) {
				buffer.append(" image ");
			} else if ("String".equals(field.getJavaType())) {
				buffer.append(" nvarchar ");
				if (field.getLength() > 0) {
					buffer.append(" (").append(field.getLength()).append(") ");
				} else {
					buffer.append(" (50) ");
				}
			}
		} else if (POSTGRESQL.equalsIgnoreCase(dbType)) {
			if ("Integer".equals(field.getJavaType())) {
				buffer.append(" integer ");
			} else if ("Long".equals(field.getJavaType())) {
				buffer.append(" bigint ");
			} else if ("Double".equals(field.getJavaType())) {
				buffer.append(" double precision ");
			} else if ("Date".equals(field.getJavaType())) {
				buffer.append(" timestamp ");
			} else if ("Clob".equals(field.getJavaType())) {
				buffer.append(" text ");
			} else if ("Blob".equals(field.getJavaType())) {
				buffer.append(" bytea ");
			} else if ("String".equals(field.getJavaType())) {
				buffer.append(" varchar ");
				if (field.getLength() > 0) {
					buffer.append(" (").append(field.getLength()).append(") ");
				} else {
					buffer.append(" (50) ");
				}
			}
		} else if ("sqlite".equalsIgnoreCase(dbType)) {
			if ("Integer".equals(field.getJavaType())) {
				buffer.append(" INTEGER ");
			} else if ("Long".equals(field.getJavaType())) {
				buffer.append(" INTEGER ");
			} else if ("Double".equals(field.getJavaType())) {
				buffer.append(" REAL ");
			} else if ("Date".equals(field.getJavaType())) {
				buffer.append(" TEXT ");
			} else if ("Clob".equals(field.getJavaType())) {
				buffer.append(" TEXT ");
			} else if ("Blob".equals(field.getJavaType())) {
				buffer.append(" BLOB ");
			} else if ("String".equals(field.getJavaType())) {
				buffer.append(" TEXT ");
				if (field.getLength() > 0) {
					buffer.append(" (").append(field.getLength()).append(") ");
				} else {
					buffer.append(" (50) ");
				}
			}
		} else {
			throw new RuntimeException(dbType
					+ " is not support database type.");
		}

		buffer.append(";");

		return buffer.toString();
	}

	public static List<String> getPrimaryKeys(Connection connection,
			String tableName) {
		List<String> primaryKeys = new java.util.ArrayList<String>();
		try {
			String dbType = DBConnectionFactory.getDatabaseType(connection);
			DatabaseMetaData metaData = connection.getMetaData();

			if ("h2".equals(dbType)) {
				tableName = tableName.toUpperCase();
			} else if ("oracle".equals(dbType)) {
				tableName = tableName.toUpperCase();
			} else if ("db2".equals(dbType)) {
				tableName = tableName.toUpperCase();
			} else if ("mysql".equals(dbType)) {
				tableName = tableName.toLowerCase();
			} else if (POSTGRESQL.equals(dbType)) {
				tableName = tableName.toLowerCase();
			}

			ResultSet rs = metaData.getPrimaryKeys(null, null, tableName);
			while (rs.next()) {
				primaryKeys.add(rs.getString("column_name").toLowerCase());
			}
			// logger.debug(tableName + " primaryKeys:" + primaryKeys);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return primaryKeys;
	}

	public static List<String> getPrimaryKeys(String tableName) {
		List<String> primaryKeys = new java.util.ArrayList<String>();
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getConnection();
			String dbType = DBConnectionFactory.getDatabaseType(connection);
			DatabaseMetaData metaData = connection.getMetaData();

			if ("h2".equals(dbType)) {
				tableName = tableName.toUpperCase();
			} else if ("oracle".equals(dbType)) {
				tableName = tableName.toUpperCase();
			} else if ("db2".equals(dbType)) {
				tableName = tableName.toUpperCase();
			} else if ("mysql".equals(dbType)) {
				tableName = tableName.toLowerCase();
			} else if (POSTGRESQL.equals(dbType)) {
				tableName = tableName.toLowerCase();
			}

			ResultSet rs = metaData.getPrimaryKeys(null, null, tableName);
			while (rs.next()) {
				primaryKeys.add(rs.getString("column_name"));
			}
			// logger.debug(tableName + " primaryKeys:" + primaryKeys);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(connection);
		}
		return primaryKeys;
	}

	public static List<String> getPrimaryKeys(String systemName,
			String tableName) {
		List<String> primaryKeys = new java.util.ArrayList<String>();
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getConnection(systemName);
			String dbType = DBConnectionFactory.getDatabaseType(connection);
			DatabaseMetaData metaData = connection.getMetaData();

			if ("h2".equals(dbType)) {
				tableName = tableName.toUpperCase();
			} else if ("oracle".equals(dbType)) {
				tableName = tableName.toUpperCase();
			} else if ("db2".equals(dbType)) {
				tableName = tableName.toUpperCase();
			} else if ("mysql".equals(dbType)) {
				tableName = tableName.toLowerCase();
			} else if (POSTGRESQL.equals(dbType)) {
				tableName = tableName.toLowerCase();
			}

			ResultSet rs = metaData.getPrimaryKeys(null, null, tableName);
			while (rs.next()) {
				primaryKeys.add(rs.getString("column_name"));
			}

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(connection);
		}
		return primaryKeys;
	}

	public static int getTableCount(Connection connection, String tableName) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(" select count(*) from " + tableName);
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(rs);
			JdbcUtils.close(stmt);
		}
		return -1;
	}

	public static int getTableCount(String tableName) {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			connection = DBConnectionFactory.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(" select count(*) from " + tableName);

			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(rs);
			JdbcUtils.close(stmt);
			JdbcUtils.close(connection);
		}
		return -1;
	}

	public static List<String> getTables() {
		List<String> tables = new java.util.ArrayList<String>();
		String[] types = { "TABLE" };
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getConnection();
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet rs = metaData.getTables(null, null, null, types);
			while (rs.next()) {
				tables.add(rs.getObject("TABLE_NAME").toString());
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(connection);
		}
		return tables;
	}

	public static List<String> getTables(Connection connection) {
		List<String> tables = new java.util.ArrayList<String>();
		String[] types = { "TABLE" };
		try {
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet rs = metaData.getTables(null, null, null, types);
			while (rs.next()) {
				tables.add(rs.getObject("TABLE_NAME").toString());
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return tables;
	}

	public static boolean isLegalQuerySql(String sql) {
		boolean isLegal = true;

		sql = sql.toLowerCase();
		if (sql.indexOf(" insert ") != -1) {
			isLegal = false;
		}
		if (sql.indexOf(" update ") != -1) {
			isLegal = false;
		}
		if (sql.indexOf(" delete ") != -1) {
			isLegal = false;
		}
		if (sql.indexOf(" create ") != -1) {
			isLegal = false;
		}
		if (sql.indexOf(" alter ") != -1) {
			isLegal = false;
		}
		if (sql.indexOf(" drop ") != -1) {
			isLegal = false;
		}

		return isLegal;
	}

	public static boolean isTableName(String sourceString) {
		if (sourceString == null || sourceString.trim().length() < 2
				|| sourceString.trim().length() > 25) {
			return false;
		}
		char[] sourceChrs = sourceString.toCharArray();
		Character chr = Character.valueOf(sourceChrs[0]);
		if (!((chr.charValue() == 95)
				|| (65 <= chr.charValue() && chr.charValue() <= 90) || (97 <= chr
				.charValue() && chr.charValue() <= 122))) {
			return false;
		}
		for (int i = 1; i < sourceChrs.length; i++) {
			chr = Character.valueOf(sourceChrs[i]);
			if (!((chr.charValue() == 95)
					|| (47 <= chr.charValue() && chr.charValue() <= 57)
					|| (65 <= chr.charValue() && chr.charValue() <= 90) || (97 <= chr
					.charValue() && chr.charValue() <= 122))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isTemoraryTable(String tableName) {
		tableName = tableName.toLowerCase();

		if (tableName.startsWith("tmp_")) {
			return true;
		}
		if (tableName.startsWith("temp_")) {
			return true;
		}
		if (tableName.startsWith("demo_")) {
			return true;
		}
		if (tableName.startsWith("wwv_")) {
			return true;
		}
		if (tableName.startsWith("aq_")) {
			return true;
		}
		if (tableName.startsWith("bsln_")) {
			return true;
		}
		if (tableName.startsWith("mgmt_")) {
			return true;
		}
		if (tableName.startsWith("ogis_")) {
			return true;
		}
		if (tableName.startsWith("ols_")) {
			return true;
		}
		if (tableName.startsWith("em_")) {
			return true;
		}
		if (tableName.startsWith("openls_")) {
			return true;
		}
		if (tableName.startsWith("mrac_")) {
			return true;
		}
		if (tableName.startsWith("orddcm_")) {
			return true;
		}
		if (tableName.startsWith("x_")) {
			return true;
		}
		if (tableName.startsWith("wlm_")) {
			return true;
		}
		if (tableName.startsWith("olap_")) {
			return true;
		}
		if (tableName.startsWith("ggs_")) {
			return true;
		}

		if (tableName.startsWith("logmnrc_")) {
			return true;
		}
		if (tableName.startsWith("logmnrg_")) {
			return true;
		}
		if (tableName.startsWith("olap_")) {
			return true;
		}
		if (tableName.startsWith("sto_")) {
			return true;
		}
		if (tableName.startsWith("sdo_")) {
			return true;
		}
		if (tableName.startsWith("sys_iot_")) {
			return true;
		}
		if (tableName.indexOf("$") != -1) {
			return true;
		}
		if (tableName.indexOf("+") != -1) {
			return true;
		}
		if (tableName.indexOf("-") != -1) {
			return true;
		}
		if (tableName.indexOf("?") != -1) {
			return true;
		}
		if (tableName.indexOf("=") != -1) {
			return true;
		}
		return false;
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

	public static void main(String[] args) {
		List<ColumnDefinition> columns = new java.util.ArrayList<ColumnDefinition>();
		ColumnDefinition c1 = new ColumnDefinition();
		c1.setColumnName("choosepublishflag");
		c1.setLength(1);
		c1.setJavaType("String");
		columns.add(c1);

		ColumnDefinition c2 = new ColumnDefinition();
		c2.setColumnName("choosepublishtime");
		c2.setJavaType("Long");
		columns.add(c2);

		// DBUtils.alterTable("qlhighway", "volume", columns);

		System.out.println();
		// FileUtils.save("data/pMagicDev.sql",
		// DBUtils.getCreateTableDDL("pMagicDev")
		// .getBytes());

		FileUtils.save("data/glaf.sql", DBUtils.getCreateTableDDL("glaf")
				.getBytes());
	}

	public static String removeOrders(String sql) {
		Assert.hasText(sql);
		Pattern pattern = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(sql);
		StringBuffer buf = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(buf, "");
		}
		matcher.appendTail(buf);
		return buf.toString();
	}

	public static SqlExecutor replaceSQL(String sql, Map<String, Object> params) {
		SqlExecutor sqlExecutor = new SqlExecutor();
		sqlExecutor.setSql(sql);
		if (sql == null || params == null) {
			return sqlExecutor;
		}

		List<Object> values = new java.util.ArrayList<Object>();
		Map<String, Object> dataMap = lowerKeyMap(params);
		StringBuffer sb = new StringBuffer();
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
					/**
					 * 如果是Collection参数，必须至少有一个值
					 */
					if (value != null && value instanceof Collection) {
						Collection<?> coll = (Collection<?>) value;
						if (coll != null && !coll.isEmpty()) {
							Iterator<?> iter = coll.iterator();
							while (iter.hasNext()) {
								values.add(iter.next());
								sb.append(" ? ");
								if (iter.hasNext()) {
									sb.append(", ");
								}
							}
						}
					} else {
						sb.append(" ? ");
						values.add(value);
					}
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

	public static String replaceTextParas(String str, Map<String, Object> params) {
		if (str == null || params == null) {
			return str;
		}
		Map<String, Object> dataMap = lowerKeyMap(params);
		StringBuffer sb = new StringBuffer();
		int begin = 0;
		int end = 0;
		boolean flag = false;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '#' && str.charAt(i + 1) == '{') {
				sb.append(str.substring(end, i));
				begin = i + 2;
				flag = true;
			}
			if (flag && str.charAt(i) == '}') {
				String temp = str.substring(begin, i);
				temp = temp.toLowerCase();
				if (dataMap.get(temp) != null) {
					Object value = dataMap.get(temp);
					if (value instanceof Date) {
						String s = DateUtils.getDate((Date) value);
						sb.append(s);
					} else {
						sb.append(value.toString());
					}
					end = i + 1;
					flag = false;
				} else {
					sb.append("#{").append(temp).append('}');
					end = i + 1;
					flag = false;
				}
			}
			if (i == str.length() - 1) {
				sb.append(str.substring(end, i + 1));
			}
		}
		return sb.toString();
	}

	public static boolean tableExists(Connection connection, String tableName) {
		DatabaseMetaData dbmd = null;
		ResultSet rs = null;
		try {
			dbmd = connection.getMetaData();
			rs = dbmd.getTables(null, null, null, new String[] { "TABLE" });
			while (rs.next()) {
				String table = rs.getString("TABLE_NAME");
				if (StringUtils.equalsIgnoreCase(tableName, table)) {
					return true;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException ex) {
				}
			}
		}
		logger.info(tableName + " not exist.");
		return false;
	}

	/**
	 * 判断表是否已经存在
	 * 
	 * @param systemName
	 * @param tableName
	 * @return
	 */
	public static boolean tableExists(String tableName) {
		Connection conn = null;
		try {
			conn = DBConnectionFactory.getConnection();
			return tableExists(conn, tableName);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
		}
	}

	/**
	 * 判断表是否已经存在
	 * 
	 * @param systemName
	 * @param tableName
	 * @return
	 */
	public static boolean tableExists(String systemName, String tableName) {
		Connection conn = null;
		try {
			conn = DBConnectionFactory.getConnection(systemName);
			return tableExists(conn, tableName);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
		}
	}

}