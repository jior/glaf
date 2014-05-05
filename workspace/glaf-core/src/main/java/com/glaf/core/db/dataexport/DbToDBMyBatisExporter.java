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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.druid.util.StringUtils;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.config.DBConfiguration;
import com.glaf.core.config.Environment;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.query.TablePageQuery;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.service.ITablePageService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.QueryUtils;

public class DbToDBMyBatisExporter {

	protected static final Log logger = LogFactory
			.getLog(DbToDBMyBatisExporter.class);

	public static void main(String[] args) {
		DbToDBMyBatisExporter exp = new DbToDBMyBatisExporter();
		exp.exportTables(args[0], args[1], args[2]);
		// exp.exportTable("default", "/data/glafdb", "sys_tree");
		// ITablePageService tablePageService = ContextFactory
		// .getBean("tablePageService");
	}

	/**
	 * 导出表数据
	 * 
	 * @param systemName
	 *            系统名称
	 * @param dbtype
	 *            导出数据库类型
	 * @param dbPath
	 *            导出数据库路径
	 * @param tableName
	 *            导出表名称
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void exportTable(String systemName, String dbtype, String dbPath,
			String tableName) {
		Environment.setCurrentSystemName(systemName);
		Properties props = new Properties();
		String jdbc_name = "db_exp_" + dbtype;
		if (StringUtils.equals(dbtype, "h2")) {
			props.setProperty("jdbc.name", jdbc_name);
			props.setProperty("jdbc.type", "h2");
			props.setProperty("jdbc.driver", "org.h2.Driver");
			props.setProperty("jdbc.url", "jdbc:h2:" + dbPath);
			props.setProperty("jdbc.user", "sa");
			props.setProperty("jdbc.password", "");
			DBConfiguration.addDataSourceProperties(jdbc_name, props);
		} else if (StringUtils.equals(dbtype, "sqlite")) {
			props.setProperty("jdbc.name", jdbc_name);
			props.setProperty("jdbc.type", "sqlite");
			props.setProperty("jdbc.driver", "org.sqlite.JDBC");
			props.setProperty("jdbc.url", "jdbc:sqlite:" + dbPath);
			props.setProperty("jdbc.user", "sa");
			props.setProperty("jdbc.password", "");
			DBConfiguration.addDataSourceProperties(jdbc_name, props);
		}

		int total = 0;
		Connection conn = null;
		Connection conn2 = null;

		Statement stmt = null;
		ResultSet rs = null;
		try {

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

				TableDefinition tableDefinition = new TableDefinition();
				tableDefinition.setTableName(tableName);
				tableDefinition.setColumns(columns);
				if (primaryKeys != null && primaryKeys.size() == 1) {
					for (ColumnDefinition c : columns) {
						if (c.isPrimaryKey()) {
							tableDefinition.setIdColumn(c);
							break;
						}
					}
				}

				conn2 = DBConnectionFactory.getConnection(jdbc_name);
				DBUtils.dropAndCreateTable(conn2, tableDefinition);
				JdbcUtils.close(conn2);
				Thread.sleep(200);

				int pageSize = 1000;
				for (ColumnDefinition c : columns) {
					String javaType = c.getJavaType();
					if ("Blob".equals(javaType)) {
						/**
						 * 有二进制流的情况，每页取数不能太大
						 */
						pageSize = 5;
					}
				}

				ITablePageService tablePageService = ContextFactory
						.getBean("tablePageService");
				ITableDataService tableDataService = ContextFactory
						.getBean("tableDataService");
				TablePageQuery query = new TablePageQuery();
				query.tableName(tableName);
				List<TableModel> inserList = new ArrayList<TableModel>();
				List<Map<String, Object>> rows = new java.util.ArrayList<Map<String, Object>>();
				for (int index = 0; index < (total / pageSize + 1); index++) {
					int firstResult = index * pageSize;

					logger.debug("firstResult=" + firstResult);
					query.firstResult(firstResult);
					query.maxResults(pageSize);

					Environment.setCurrentSystemName(systemName);
					List<Map<String, Object>> list = tablePageService
							.getTableData(query);

					rows.clear();
					if (list != null && !list.isEmpty()) {
						for (Map dataMap : list) {
							dataMap = QueryUtils.lowerKeyMap(dataMap);
							rows.add(dataMap);
						}
					}

					if (rows != null && !rows.isEmpty()) {
						inserList.clear();
						for (Map<String, Object> dataMap : rows) {
							TableModel tableModel = new TableModel();
							tableModel.setTableName(tableName);
							for (ColumnDefinition c : columns) {
								String name = c.getColumnName();
								Object value = ParamUtils.get(dataMap, name);
								if (value != null) {
									ColumnModel col = new ColumnModel();
									col.setJavaType(c.getJavaType());
									col.setColumnName(c.getColumnName());
									col.setValue(value);
									if (value instanceof java.sql.Clob) {
										col.setJavaType("Clob");
									} else if (value instanceof java.sql.Blob) {
										col.setJavaType("Blob");
									}
									tableModel.addColumn(col);
								}
							}
							inserList.add(tableModel);
						}
						Environment.setCurrentSystemName(jdbc_name);
						tableDataService.insertAllTableData(inserList);
						inserList.clear();
					}
					rows.clear();
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

	/**
	 * 导出表数据
	 * 
	 * @param systemName
	 *            系统名称
	 * @param dbtype
	 *            导出数据库类型
	 * @param dbPath
	 *            导出数据库路径
	 */
	public void exportTables(String systemName, String dbtype, String dbPath) {
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
						this.exportTable(systemName, dbtype, dbPath, tableName);
						success = true;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}

	}

	/**
	 * 导出表数据
	 * 
	 * @param systemName
	 *            系统名称
	 * @param dbtype
	 *            导出数据库类型
	 * @param dbPath
	 *            导出数据库路径
	 * @param tables
	 *            导出表集合
	 */
	public void exportTables(String systemName, String dbtype, String dbPath,
			List<String> tables) {
		if (tables != null && !tables.isEmpty()) {
			for (String tableName : tables) {
				boolean success = false;
				int retry = 0;
				while (retry < 3 && !success) {
					try {
						retry++;
						this.exportTable(systemName, dbtype, dbPath, tableName);
						success = true;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}

}