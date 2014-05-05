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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
import com.glaf.core.util.FieldType;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.QueryUtils;
import com.glaf.core.util.StringTools;

public class QueryToDBMyBatisExporter {

	protected static final Log logger = LogFactory
			.getLog(QueryToDBMyBatisExporter.class);

	public static void main(String[] args) {
		QueryToDBMyBatisExporter exp = new QueryToDBMyBatisExporter();
		exp.exportTable("default", "sqlite", "/data/glafdb2.db", "sys_tree",
				"select * from sys_tree");
		exp.exportTable("default", "sqlite", "/data/glafdb2.db",
				"sys_department", "select * from sys_department");
		exp.exportTable("default", "sqlite", "/data/glafdb2.db",
				"sys_application", "select * from sys_application");
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
	 *            目标表名称
	 * @param querySQL
	 *            查询SQL
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void exportTable(String systemName, String dbtype, String dbPath,
			String tableName, String querySQL) {
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
			rs = stmt.executeQuery(querySQL);
			if (rs.next()) {
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				List<ColumnDefinition> columns = new ArrayList<ColumnDefinition>();
				for (int i = 0; i < columnCount; i++) {
					// 取得每列的列名，列标签作为列名
					String columnName = rsmd.getColumnLabel(i + 1);
					// 取得每列的类型
					int typeCode = rsmd.getColumnType(i + 1);
					ColumnDefinition column = new ColumnDefinition();
					column.setName(StringTools.lower(StringTools
							.camelStyle(columnName)));
					column.setColumnName(columnName.toLowerCase());
					column.setTitle(column.getName());
					column.setJavaType(FieldType.getJavaType(typeCode));
					column.setPrecision(rsmd.getPrecision(i + 1));
					column.setScale(rsmd.getScale(i + 1));
					column.setLength(rsmd.getColumnDisplaySize(i + 1));
					column.setColumnLabel(rsmd.getColumnLabel(i + 1));

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

					columns.add(column);
				}

				JdbcUtils.close(conn);

				TableDefinition tableDefinition = new TableDefinition();
				tableDefinition.setTableName(tableName);
				tableDefinition.setColumns(columns);

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

}