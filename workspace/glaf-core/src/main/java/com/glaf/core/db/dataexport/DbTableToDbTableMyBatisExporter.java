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
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.Environment;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.db.TableDataManager;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.query.TablePageQuery;
import com.glaf.core.service.ITablePageService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.QueryUtils;
import com.glaf.core.util.StringTools;

public class DbTableToDbTableMyBatisExporter {

	protected static Configuration conf = BaseConfiguration.create();

	protected static final Log logger = LogFactory
			.getLog(DbTableToDbTableMyBatisExporter.class);

	public static void main(String[] args) {
		DbTableToDbTableMyBatisExporter exp = new DbTableToDbTableMyBatisExporter();
		exp.exportTables(args[0], args[1], args[2]);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void exportTable(String srcSystemName, String destSystemName,
			String rootDir, String tableName) {
		logger.info("prepare transfer table:" + tableName);
		int total = 0;
		Connection conn = null;
		Connection conn2 = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnectionFactory.getConnection(srcSystemName);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(" select count(*) from " + tableName);
			if (rs.next()) {
				total = rs.getInt(1);
			}

			conn2 = DBConnectionFactory.getConnection(destSystemName);

			List<ColumnDefinition> columns = DBUtils.getColumnDefinitions(conn,
					tableName);
			TableDefinition tbl = new TableDefinition();
			tbl.setTableName(tableName);

			for (ColumnDefinition c : columns) {
				if (c.isPrimaryKey()) {
					tbl.setIdColumn(c);
					tbl.setAggregationKeys(c.getColumnName());
					break;
				}
			}

			tbl.setColumns(columns);

			if (!DBUtils.tableExists(conn2, tableName)) {
				DBUtils.createTable(conn2, tbl);
			} else {
				DBUtils.alterTable(conn2, tbl);
			}

			if (tbl.getIdColumn() == null) {
				logger.error("No Primary Key!!!");
				return;
			}

			logger.info(tableName + " primaryKey=" + tbl.getIdColumn());

			int targetTotal = DBUtils.getTableCount(conn2, tableName);

			if (total > 0 && total != targetTotal) {

				StringBuffer sb = new StringBuffer();
				sb.append(" select ");

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
				}

				sb.delete(sb.length() - 2, sb.length());
				sb.append(" from ").append(tableName);

				logger.debug("select sql=" + sb.toString());

				ITablePageService tablePageService = ContextFactory
						.getBean("tablePageService");
				TableDataManager dataManager = new TableDataManager();
				TablePageQuery query = new TablePageQuery();
				query.tableName(tableName);
				List<Map<String, Object>> rows = new java.util.concurrent.CopyOnWriteArrayList<Map<String, Object>>();
				for (int index = 0; index < (total / pageSize + 1); index++) {
					int firstResult = index * pageSize;
					rows.clear();
					logger.info("firstResult=" + firstResult);
					query.firstResult(firstResult);
					query.maxResults(pageSize);
					Environment.setCurrentSystemName(srcSystemName);
					List<Map<String, Object>> list = tablePageService
							.getTableData(query);

					if (list != null && !list.isEmpty()) {
						for (Map dataMap : list) {
							dataMap = QueryUtils.lowerKeyMap(dataMap);
							rows.add(dataMap);
						}
					}

					if (rows != null && !rows.isEmpty()) {
						dataManager.saveOrUpdate(destSystemName, tbl, rows);
						rows.clear();
					}
				}
			}
			logger.info("transfer table " + tableName + " finished.");
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(rs);
			JdbcUtils.close(conn);
			JdbcUtils.close(conn2);
		}
	}

	public void exportTables(String srcSystemName, String destSystemName,
			String rootDir) {
		String excludes = conf.get("db.export.excludes");
		List<String> list = StringTools.split(excludes);
		List<String> tables = new java.util.concurrent.CopyOnWriteArrayList<String>();
		Connection conn = null;
		DatabaseMetaData dbmd = null;
		ResultSet rs = null;
		try {
			conn = DBConnectionFactory.getConnection(srcSystemName);
			dbmd = conn.getMetaData();
			rs = dbmd.getTables(null, null, null, new String[] { "TABLE" });
			while (rs.next()) {
				String tableName = rs.getString("TABLE_NAME");
				tableName = tableName.toLowerCase();
				if (StringUtils.isNotEmpty(excludes)
						&& StringUtils.contains(excludes, tableName)) {
					continue;
				}
				if (list != null && !list.isEmpty()) {
					for (String str : list) {
						if (StringUtils.contains(tableName, str)) {
							continue;
						}
					}
				}
				tables.add(tableName);
			}
		} catch (Exception ex) {
			logger.error(ex);
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
						this.exportTable(srcSystemName, destSystemName,
								rootDir, tableName);
						success = true;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}

	}

	public void exportTables(String srcSystemName, String destSystemName,
			String rootDir, List<String> tables) {
		if (tables != null && !tables.isEmpty()) {
			for (String tableName : tables) {
				boolean success = false;
				int retry = 0;
				while (retry < 3 && !success) {
					try {
						retry++;
						this.exportTable(srcSystemName, destSystemName,
								rootDir, tableName);
						success = true;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}

}