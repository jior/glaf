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

package com.glaf.dts.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.NumberFormat;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.domain.*;
import com.glaf.core.service.*;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.FieldType;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.QueryUtils;
import com.glaf.core.util.StringTools;

public class QueryRunner {
	protected static final Log logger = LogFactory.getLog(QueryRunner.class);

	protected IQueryDefinitionService queryDefinitionService;

	protected ITableDefinitionService tableDefinitionService;

	public QueryRunner() {

	}

	protected QueryDefinition fill(String queryId, String newSql) {
		Stack<QueryDefinition> stack = getQueryDefinitionService()
				.getQueryDefinitionStack(queryId);
		QueryDefinition query = getQueryDefinitionService().getQueryDefinition(
				queryId);
		List<Map<String, Object>> resultList = null;
		if (stack != null && stack.size() > 0) {
			while (stack.size() > 0) {
				query = stack.pop();
				if (StringUtils.isNotEmpty(newSql)
						&& StringUtils.equals(queryId, query.getId())) {
					query.setSql(newSql);
				}
				logger.debug("------------------------------------------------");
				logger.debug("####title:" + query.getTitle());
				logger.debug("####parentId:" + query.getParentId());
				if (query.getParentId() != null) {
					/***
					 * 只有当父查询有返回结果才进行当前查询
					 */
					if (resultList != null && !resultList.isEmpty()) {
						/**
						 * 如果有父节点，将父节点的结果作为当前节点的参数执行查询
						 */
						resultList = this.prepare(query, resultList);
					}
				} else {
					/**
					 * 如果没有父节点，说明已经是根节点，可以直接调用结果作为返回值
					 */
					resultList = this.prepare(query);
				}
			}
		}
		return query;
	}

	public IQueryDefinitionService getQueryDefinitionService() {
		if (queryDefinitionService == null) {
			queryDefinitionService = ContextFactory
					.getBean("queryDefinitionService");
		}
		return queryDefinitionService;
	}

	public ITableDefinitionService getTableDefinitionService() {
		if (tableDefinitionService == null) {
			tableDefinitionService = ContextFactory
					.getBean("tableDefinitionService");
		}
		return tableDefinitionService;
	}

	protected List<Map<String, Object>> prepare(QueryDefinition query) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			conn = DBConnectionFactory.getConnection();
			logger.debug(">sql=" + query.getSql());
			String currentSql = QueryUtils.replaceSQLVars(query.getSql());
			query.setSql(currentSql);
			psmt = conn.prepareStatement(query.getSql());

			rs = psmt.executeQuery();
			rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			while (rs.next()) {
				Map<String, Object> rowMap = new HashMap<String, Object>();
				for (int i = 1; i <= count; i++) {
					String columnName = rsmd.getColumnName(i);
					try {
						rowMap.put(columnName, rs.getObject(i));
					} catch (SQLException ex) {
						rowMap.put(columnName, rs.getString(i));
					}
				}
				resultList.add(rowMap);
			}

			psmt.close();
			rs.close();

			query.setResultList(resultList);

			logger.debug(">resultList size=" + resultList.size());
			return resultList;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
		}
	}

	@SuppressWarnings("unchecked")
	protected List<Map<String, Object>> prepare(QueryDefinition query,
			List<Map<String, Object>> paramList) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			conn = DBConnectionFactory.getConnection();
			for (Map<String, Object> paramMap : paramList) {
				SqlExecutor sqlExecutor = DBUtils.replaceSQL(query.getSql(),
						paramMap);
				psmt = conn.prepareStatement(sqlExecutor.getSql());
				if (sqlExecutor.getParameter() != null) {
					List<Object> values = (List<Object>) sqlExecutor
							.getParameter();
					JdbcUtils.fillStatement(psmt, values);
				}

				rs = psmt.executeQuery();
				rsmd = rs.getMetaData();
				int count = rsmd.getColumnCount();
				while (rs.next()) {
					Map<String, Object> rowMap = new HashMap<String, Object>();
					for (int i = 1; i <= count; i++) {
						String columnName = rsmd.getColumnName(i);
						try {
							rowMap.put(columnName, rs.getObject(i));
						} catch (SQLException ex) {
							rowMap.put(columnName, rs.getString(i));
						}
					}
					resultList.add(rowMap);
				}

				psmt.close();
				rs.close();
			}

			query.setResultList(resultList);

			logger.debug(">resultList size=" + resultList.size());

			return resultList;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
		}
	}

	/**
	 * 查询数据
	 * 
	 * @param query
	 *            查询
	 * @param target
	 *            目标表(数据仓库中的事实表)
	 */
	public List<Map<String, Object>> search(QueryDefinition query,
			TableDefinition target) {
		return this.searchInner(query, target);
	}

	/**
	 * 获取数据
	 * 
	 * @param queryId
	 *            查询编号
	 * @param targetTable
	 *            目标表(数据仓库中的事实表)
	 */
	public List<Map<String, Object>> search(String queryId, String targetTable) {
		TableDefinition target = getTableDefinitionService()
				.getTableDefinition(targetTable);
		QueryDefinition query = getQueryDefinitionService().getQueryDefinition(
				queryId);
		return this.search(query, target);
	}

	/**
	 * 获取数据
	 * 
	 * @param query
	 *            查询
	 * @param target
	 *            目标表(数据仓库中的事实表)
	 */
	protected List<Map<String, Object>> searchInner(QueryDefinition query,
			TableDefinition target) {

		if (query.getId() != null) {
			query = this.fill(query.getId(), query.getSql());
		}

		if (query != null && query.getParentId() != null) {
			QueryDefinition parent = this.fill(query.getParentId(), null);
			if (parent != null) {
				query.setParent(parent);
			}
		}

		if (query != null && query.getParentId() != null) {
			if (query.getParent() != null
					&& query.getParent().getResultList() != null
					&& !query.getParent().getResultList().isEmpty()) {
				return this.searchMany(query, target);
			}
		} else {
			return this.searchSingle(query, target,
					new HashMap<String, Object>());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	protected List<Map<String, Object>> searchMany(QueryDefinition query,
			TableDefinition target) {
		logger.debug("------------------------------searchMany--------------");

		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<ColumnDefinition> columns = new ArrayList<ColumnDefinition>();
		StringBuffer valueBuffer = new StringBuffer();

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSetMetaData rsmd = null;
		ResultSet rs = null;
		try {
			conn = DBConnectionFactory.getConnection();

			if (query.getParent() != null
					&& query.getParent().getResultList() != null
					&& !query.getParent().getResultList().isEmpty()) {
				for (Map<String, Object> paramMap : query.getParent()
						.getResultList()) {
					String sql = query.getSql();
					List<Object> values = null;
					if (paramMap != null) {
						SqlExecutor sqlExecutor = DBUtils.replaceSQL(sql,
								paramMap);
						sql = sqlExecutor.getSql();
						values = (List<Object>) sqlExecutor.getParameter();
					}

					logger.debug("::sql::" + sql);
					psmt = conn.prepareStatement(sql);

					if (values != null && !values.isEmpty()) {
						JdbcUtils.fillStatement(psmt, values);
						logger.debug("::values::" + values);
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
						columns.add(column);
					}

					while (rs.next()) {
						int index = 0;
						valueBuffer.delete(0, valueBuffer.length());
						Map<String, Object> rowMap = new HashMap<String, Object>();
						Iterator<ColumnDefinition> iterator = columns
								.iterator();
						while (iterator.hasNext()) {
							ColumnDefinition column = iterator.next();
							String columnName = column.getColumnName();
							String javaType = column.getJavaType();
							index = index + 1;
							if ("String".equals(javaType)) {
								String value = rs.getString(columnName);
								valueBuffer.append('\n').append("columnName=")
										.append(value);
								rowMap.put(columnName, value);
							} else if ("Integer".equals(javaType)) {
								try {
									Integer value = rs.getInt(columnName);
									rowMap.put(columnName, value);
								} catch (Exception e) {
									String str = rs.getString(columnName);
									logger.error("错误的integer:" + str);
									str = StringTools.replace(str, "$", "");
									str = StringTools.replace(str, "￥", "");
									str = StringTools.replace(str, ",", "");
									NumberFormat fmt = NumberFormat
											.getInstance();
									Number num = fmt.parse(str);
									rowMap.put(columnName, num.intValue());
									logger.debug("修正后:" + num.intValue());
								}
							} else if ("Long".equals(javaType)) {
								try {
									Long value = rs.getLong(columnName);
									rowMap.put(columnName, value);
								} catch (Exception e) {
									String str = rs.getString(columnName);
									logger.error("错误的long:" + str);
									str = StringTools.replace(str, "$", "");
									str = StringTools.replace(str, "￥", "");
									str = StringTools.replace(str, ",", "");
									NumberFormat fmt = NumberFormat
											.getInstance();
									Number num = fmt.parse(str);
									rowMap.put(columnName, num.longValue());
									logger.debug("修正后:" + num.longValue());
								}
							} else if ("Double".equals(javaType)) {
								try {
									Double d = rs.getDouble(columnName);
									rowMap.put(columnName, d);
								} catch (Exception e) {
									String str = rs.getString(columnName);
									logger.error("错误的double:" + str);
									str = StringTools.replace(str, "$", "");
									str = StringTools.replace(str, "￥", "");
									str = StringTools.replace(str, ",", "");
									NumberFormat fmt = NumberFormat
											.getInstance();
									Number num = fmt.parse(str);
									rowMap.put(columnName, num.doubleValue());
									logger.debug("修正后:" + num.doubleValue());
								}
							} else if ("Boolean".equals(javaType)) {
								rowMap.put(columnName,
										rs.getBoolean(columnName));

							} else if ("Date".equals(javaType)) {
								rowMap.put(columnName,
										rs.getTimestamp(columnName));
							} else {
								String value = rs.getString(columnName);
								String string = "\n";
								valueBuffer.append(string)
										.append("columnName=").append(value);
								rowMap.put(columnName, value);
							}
						}
						query.addResult(rowMap);
						rows.add(rowMap);
					}
					psmt.close();
					psmt = null;
					rs.close();
					rs = null;
				}
			}

			return rows;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(valueBuffer.toString());
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
		}
	}

	/**
	 * 查找数据
	 * 
	 * @param query
	 *            查询
	 * @param target
	 *            目标表 
	 */
	@SuppressWarnings("unchecked")
	protected List<Map<String, Object>> searchSingle(QueryDefinition query,
			TableDefinition target, Map<String, Object> paramMap) {
		logger.debug("------------------------------searchSingle--------------");

		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<ColumnDefinition> columns = new ArrayList<ColumnDefinition>();

		String sql = query.getSql();
		
		String currentSql = QueryUtils.replaceSQLVars(query.getSql());
		query.setSql(currentSql);
		sql = currentSql;
		
		List<Object> values = null;

		if (paramMap != null) {
			SqlExecutor sqlExecutor = DBUtils.replaceSQL(sql, paramMap);
			sql = sqlExecutor.getSql();
			values = (List<Object>) sqlExecutor.getParameter();
		}

		StringBuffer valueBuffer = new StringBuffer();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSetMetaData rsmd = null;
		ResultSet rs = null;
		try {
			conn = DBConnectionFactory.getConnection();

			psmt = conn.prepareStatement(sql);
			logger.debug("##sql:" + sql);

			if (values != null && !values.isEmpty()) {
				JdbcUtils.fillStatement(psmt, values);
				logger.debug("values:" + values);
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
				columns.add(column);
			}

			while (rs.next()) {
				int index = 0;
				valueBuffer.delete(0, valueBuffer.length());
				Map<String, Object> rowMap = new HashMap<String, Object>();
				Iterator<ColumnDefinition> iterator = columns.iterator();
				while (iterator.hasNext()) {
					ColumnDefinition column = iterator.next();
					String columnName = column.getColumnName();
					String javaType = column.getJavaType();
					index = index + 1;
					if ("String".equals(javaType)) {
						String value = rs.getString(columnName);
						valueBuffer.append('\n').append("columnName=")
								.append(value);
						rowMap.put(columnName, value);
					} else if ("Integer".equals(javaType)) {
						try {
							Integer value = rs.getInt(columnName);
							rowMap.put(columnName, value);
						} catch (Exception e) {
							String str = rs.getString(columnName);
							logger.error("错误的double:" + str);
							str = StringTools.replace(str, "$", "");
							str = StringTools.replace(str, "￥", "");
							str = StringTools.replace(str, ",", "");
							NumberFormat fmt = NumberFormat.getInstance();
							Number num = fmt.parse(str);
							rowMap.put(columnName, num.intValue());
							logger.debug("修正后:" + num.intValue());
						}
					} else if ("Long".equals(javaType)) {
						try {
							Long value = rs.getLong(columnName);
							rowMap.put(columnName, value);
						} catch (Exception e) {
							String str = rs.getString(columnName);
							logger.error("错误的double:" + str);
							str = StringTools.replace(str, "$", "");
							str = StringTools.replace(str, "￥", "");
							str = StringTools.replace(str, ",", "");
							NumberFormat fmt = NumberFormat.getInstance();
							Number num = fmt.parse(str);
							rowMap.put(columnName, num.longValue());
							logger.debug("修正后:" + num.longValue());
						}
					} else if ("Double".equals(javaType)) {
						try {
							Double d = rs.getDouble(columnName);
							rowMap.put(columnName, d);
						} catch (Exception e) {
							String str = rs.getString(columnName);
							logger.error("错误的double:" + str);
							str = StringTools.replace(str, "$", "");
							str = StringTools.replace(str, "￥", "");
							str = StringTools.replace(str, ",", "");
							NumberFormat fmt = NumberFormat.getInstance();
							Number num = fmt.parse(str);
							rowMap.put(columnName, num.doubleValue());
							logger.debug("修正后:" + num.doubleValue());
						}
					} else if ("Boolean".equals(javaType)) {
						rowMap.put(columnName, rs.getBoolean(columnName));

					} else if ("Date".equals(javaType)) {
						rowMap.put(columnName, rs.getTimestamp(columnName));
					} else {
						String value = rs.getString(columnName);
						valueBuffer.append('\n').append("columnName=")
								.append(value);
						rowMap.put(columnName, value);
					}
				}
				query.addResult(rowMap);
				rows.add(rowMap);
			}

			psmt.close();
			psmt = null;
			rs.close();
			rs = null;
			return rows;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(valueBuffer.toString());
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
		}
	}

	public void setQueryDefinitionService(
			IQueryDefinitionService queryDefinitionService) {
		this.queryDefinitionService = queryDefinitionService;
	}

	public void setTableDefinitionService(
			ITableDefinitionService tableDefinitionService) {
		this.tableDefinitionService = tableDefinitionService;
	}

}