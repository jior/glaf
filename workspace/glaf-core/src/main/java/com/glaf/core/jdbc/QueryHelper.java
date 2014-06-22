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

package com.glaf.core.jdbc;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import com.glaf.core.base.ResultModel;
import com.glaf.core.base.RowModel;
import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.DBConfiguration;
import com.glaf.core.dialect.Dialect;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.FieldType;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.StringTools;

public class QueryHelper {
	protected static final Log logger = LogFactory.getLog(QueryHelper.class);

	protected static Configuration conf = BaseConfiguration.create();

	protected static TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();

	public QueryHelper() {

	}

	/**
	 * @param conn
	 *            数据库连接对象
	 * @param sqlExecutor
	 *            查询对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getResultList(Connection conn,
			SqlExecutor sqlExecutor) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		PreparedStatement psmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			psmt = conn.prepareStatement(sqlExecutor.getSql());
			if (sqlExecutor.getParameter() != null) {
				List<Object> values = (List<Object>) sqlExecutor.getParameter();
				JdbcUtils.fillStatement(psmt, values);
			}

			rs = psmt.executeQuery();

			if (conf.getBoolean("useMyBatisResultHandler", false)) {

				resultList = this.getResults(rs);

			} else {

				rsmd = rs.getMetaData();

				int count = rsmd.getColumnCount();
				List<ColumnDefinition> columns = new ArrayList<ColumnDefinition>();

				for (int i = 1; i <= count; i++) {
					int sqlType = rsmd.getColumnType(i);
					ColumnDefinition column = new ColumnDefinition();
					column.setColumnName(rsmd.getColumnName(i));
					column.setColumnLabel(rsmd.getColumnLabel(i));
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
					Map<String, Object> rowMap = new HashMap<String, Object>();
					Iterator<ColumnDefinition> iterator = columns.iterator();
					while (iterator.hasNext()) {
						ColumnDefinition column = iterator.next();
						String columnName = column.getColumnName();
						String columnLabel = column.getColumnLabel();
						String javaType = column.getJavaType();
						index = index + 1;
						if ("String".equals(javaType)) {
							String value = rs.getString(columnName);
							if (value != null) {
								value = value.trim();
								rowMap.put(columnName, value);
								rowMap.put(columnLabel, value);
								rowMap.put(columnName.toLowerCase(),
										rowMap.get(columnName));
								rowMap.put(columnLabel.toLowerCase(), value);
							}
						} else if ("Integer".equals(javaType)) {
							try {
								Integer value = rs.getInt(columnName);
								rowMap.put(columnName, value);
								rowMap.put(columnLabel, value);
								rowMap.put(columnName.toLowerCase(),
										rowMap.get(columnName));
								rowMap.put(columnLabel.toLowerCase(), value);
							} catch (Exception e) {
								String str = rs.getString(columnName);
								logger.error("错误的integer:" + str);
								str = StringTools.replace(str, "$", "");
								str = StringTools.replace(str, "￥", "");
								str = StringTools.replace(str, ",", "");
								NumberFormat fmt = NumberFormat.getInstance();
								Number num = fmt.parse(str);
								rowMap.put(columnName, num.intValue());
								rowMap.put(columnLabel, rowMap.get(columnName));
								rowMap.put(columnName.toLowerCase(),
										rowMap.get(columnName));
								rowMap.put(columnLabel.toLowerCase(),
										rowMap.get(columnName));
								logger.debug("修正后:" + num.intValue());
							}
						} else if ("Long".equals(javaType)) {
							try {
								Long value = rs.getLong(columnName);
								rowMap.put(columnName, value);
								rowMap.put(columnLabel, rowMap.get(columnName));
								rowMap.put(columnName.toLowerCase(),
										rowMap.get(columnName));
								rowMap.put(columnLabel.toLowerCase(),
										rowMap.get(columnName));
							} catch (Exception e) {
								String str = rs.getString(columnName);
								logger.error("错误的long:" + str);
								str = StringTools.replace(str, "$", "");
								str = StringTools.replace(str, "￥", "");
								str = StringTools.replace(str, ",", "");
								NumberFormat fmt = NumberFormat.getInstance();
								Number num = fmt.parse(str);
								rowMap.put(columnName, num.longValue());
								rowMap.put(columnLabel, num.longValue());
								rowMap.put(columnName.toLowerCase(),
										rowMap.get(columnName));
								rowMap.put(columnLabel.toLowerCase(),
										rowMap.get(columnName));
								logger.debug("修正后:" + num.longValue());
							}
						} else if ("Double".equals(javaType)) {
							try {
								Double d = rs.getDouble(columnName);
								rowMap.put(columnName, d);
								rowMap.put(columnLabel, d);
								rowMap.put(columnName.toLowerCase(),
										rowMap.get(columnName));
								rowMap.put(columnLabel.toLowerCase(),
										rowMap.get(columnName));
							} catch (Exception e) {
								String str = rs.getString(columnName);
								logger.error("错误的double:" + str);
								str = StringTools.replace(str, "$", "");
								str = StringTools.replace(str, "￥", "");
								str = StringTools.replace(str, ",", "");
								NumberFormat fmt = NumberFormat.getInstance();
								Number num = fmt.parse(str);
								rowMap.put(columnName, num.doubleValue());
								rowMap.put(columnLabel, num.doubleValue());
								rowMap.put(columnName.toLowerCase(),
										rowMap.get(columnName));
								rowMap.put(columnLabel.toLowerCase(),
										rowMap.get(columnName));
								logger.debug("修正后:" + num.doubleValue());
							}
						} else if ("Boolean".equals(javaType)) {
							rowMap.put(columnName, rs.getBoolean(columnName));
							rowMap.put(columnLabel, rowMap.get(columnName));
							rowMap.put(columnName.toLowerCase(),
									rowMap.get(columnName));
							rowMap.put(columnLabel.toLowerCase(),
									rowMap.get(columnName));
						} else if ("Date".equals(javaType)) {
							rowMap.put(columnName, rs.getTimestamp(columnName));
							rowMap.put(columnLabel, rowMap.get(columnName));
							rowMap.put(columnName.toLowerCase(),
									rowMap.get(columnName));
							rowMap.put(columnLabel.toLowerCase(),
									rowMap.get(columnName));
						} else if ("Blob".equals(javaType)) {
							// ignore
						} else {
							Object value = rs.getObject(columnName);
							if (value != null) {
								if (value instanceof String) {
									value = (String) value.toString().trim();
								}
								rowMap.put(columnName, value);
								rowMap.put(columnLabel, rowMap.get(columnName));
								rowMap.put(columnName.toLowerCase(),
										rowMap.get(columnName));
								rowMap.put(columnLabel.toLowerCase(),
										rowMap.get(columnName));
							}
						}
					}
					resultList.add(rowMap);
				}
			}

			psmt.close();
			rs.close();

			logger.debug(">resultList size=" + resultList.size());
			return resultList;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(psmt);
			JdbcUtils.close(rs);
		}
	}

	/**
	 * @param conn
	 *            数据库连接对象
	 * @param sqlExecutor
	 *            查询语句
	 * @param start
	 *            开始记录游标
	 * @param pageSize
	 *            每页记录数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getResultList(Connection conn,
			SqlExecutor sqlExecutor, int start, int pageSize) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		String sql = sqlExecutor.getSql();
		PreparedStatement psmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		boolean supportsPhysicalPage = false;
		try {
			Dialect dialect = DBConfiguration.getDatabaseDialect(conn);
			if (dialect != null && dialect.supportsPhysicalPage()) {
				supportsPhysicalPage = true;
				sql = dialect.getLimitString(sql, start, pageSize);
				logger.debug("sql=" + sqlExecutor.getSql());
				logger.debug(">>sql=" + sql);
			}

			psmt = conn.prepareStatement(sql);
			if (sqlExecutor.getParameter() != null) {
				List<Object> values = (List<Object>) sqlExecutor.getParameter();
				JdbcUtils.fillStatement(psmt, values);
			}

			rs = psmt.executeQuery();

			if (conf.getBoolean("useMyBatisResultHandler", false)) {

				resultList = this.getResults(rs);

			} else {
				rsmd = rs.getMetaData();

				int count = rsmd.getColumnCount();
				List<ColumnDefinition> columns = new ArrayList<ColumnDefinition>();

				for (int i = 1; i <= count; i++) {
					int sqlType = rsmd.getColumnType(i);
					ColumnDefinition column = new ColumnDefinition();
					column.setColumnName(rsmd.getColumnName(i));
					column.setColumnLabel(rsmd.getColumnLabel(i));
					column.setJavaType(FieldType.getJavaType(sqlType));
					column.setPrecision(rsmd.getPrecision(i));
					column.setScale(rsmd.getScale(i));
					if (column.getScale() == 0 && sqlType == Types.NUMERIC) {
						column.setJavaType("Long");
					}
					columns.add(column);
				}

				if (!supportsPhysicalPage) {
					this.skipRows(rs, start, pageSize);
				}

				int index = 0;
				while (rs.next() && index++ < pageSize) {
					Map<String, Object> rowMap = new HashMap<String, Object>();
					Iterator<ColumnDefinition> iterator = columns.iterator();
					while (iterator.hasNext()) {
						ColumnDefinition column = iterator.next();
						String columnName = column.getColumnName();
						String columnLabel = column.getColumnLabel();
						String javaType = column.getJavaType();

						if ("String".equals(javaType)) {
							String value = rs.getString(columnName);
							if (value != null) {
								value = value.trim();
								rowMap.put(columnName, value);
								rowMap.put(columnLabel, rowMap.get(columnName));
								rowMap.put(columnName.toLowerCase(),
										rowMap.get(columnName));
								rowMap.put(columnLabel.toLowerCase(),
										rowMap.get(columnName));
							}
						} else if ("Integer".equals(javaType)) {
							try {
								Integer value = rs.getInt(columnName);
								rowMap.put(columnName, value);
								rowMap.put(columnLabel, rowMap.get(columnName));
								rowMap.put(columnName.toLowerCase(),
										rowMap.get(columnName));
								rowMap.put(columnLabel.toLowerCase(),
										rowMap.get(columnName));
							} catch (Exception e) {
								String str = rs.getString(columnName);
								logger.error("错误的integer:" + str);
								str = StringTools.replace(str, "$", "");
								str = StringTools.replace(str, "￥", "");
								str = StringTools.replace(str, ",", "");
								NumberFormat fmt = NumberFormat.getInstance();
								Number num = fmt.parse(str);
								rowMap.put(columnName, num.intValue());
								rowMap.put(columnLabel, rowMap.get(columnName));
								rowMap.put(columnName.toLowerCase(),
										rowMap.get(columnName));
								rowMap.put(columnLabel.toLowerCase(),
										rowMap.get(columnName));
								logger.debug("修正后:" + num.intValue());
							}
						} else if ("Long".equals(javaType)) {
							try {
								Long value = rs.getLong(columnName);
								rowMap.put(columnName, value);
								rowMap.put(columnLabel, rowMap.get(columnName));
								rowMap.put(columnName.toLowerCase(),
										rowMap.get(columnName));
								rowMap.put(columnLabel.toLowerCase(),
										rowMap.get(columnName));
							} catch (Exception e) {
								String str = rs.getString(columnName);
								logger.error("错误的long:" + str);
								str = StringTools.replace(str, "$", "");
								str = StringTools.replace(str, "￥", "");
								str = StringTools.replace(str, ",", "");
								NumberFormat fmt = NumberFormat.getInstance();
								Number num = fmt.parse(str);
								rowMap.put(columnName, num.longValue());
								rowMap.put(columnLabel, rowMap.get(columnName));
								rowMap.put(columnName.toLowerCase(),
										rowMap.get(columnName));
								rowMap.put(columnLabel.toLowerCase(),
										rowMap.get(columnName));
								logger.debug("修正后:" + num.longValue());
							}
						} else if ("Double".equals(javaType)) {
							try {
								Double d = rs.getDouble(columnName);
								rowMap.put(columnName, d);
								rowMap.put(columnLabel, rowMap.get(columnName));
								rowMap.put(columnName.toLowerCase(),
										rowMap.get(columnName));
								rowMap.put(columnLabel.toLowerCase(),
										rowMap.get(columnName));
							} catch (Exception e) {
								String str = rs.getString(columnName);
								logger.error("错误的double:" + str);
								str = StringTools.replace(str, "$", "");
								str = StringTools.replace(str, "￥", "");
								str = StringTools.replace(str, ",", "");
								NumberFormat fmt = NumberFormat.getInstance();
								Number num = fmt.parse(str);
								rowMap.put(columnName, num.doubleValue());
								rowMap.put(columnLabel, rowMap.get(columnName));
								rowMap.put(columnName.toLowerCase(),
										rowMap.get(columnName));
								rowMap.put(columnLabel.toLowerCase(),
										rowMap.get(columnName));
								logger.debug("修正后:" + num.doubleValue());
							}
						} else if ("Boolean".equals(javaType)) {
							rowMap.put(columnName, rs.getBoolean(columnName));
							rowMap.put(columnLabel, rowMap.get(columnName));
							rowMap.put(columnName.toLowerCase(),
									rowMap.get(columnName));
							rowMap.put(columnLabel.toLowerCase(),
									rowMap.get(columnName));
						} else if ("Date".equals(javaType)) {
							rowMap.put(columnName, rs.getTimestamp(columnName));
							rowMap.put(columnLabel, rowMap.get(columnName));
							rowMap.put(columnName.toLowerCase(),
									rowMap.get(columnName));
							rowMap.put(columnLabel.toLowerCase(),
									rowMap.get(columnName));
						} else if ("Blob".equals(javaType)) {
							rowMap.put(columnName, rs.getBytes(columnName));
							rowMap.put(columnLabel, rowMap.get(columnName));
							rowMap.put(columnName.toLowerCase(),
									rowMap.get(columnName));
							rowMap.put(columnLabel.toLowerCase(),
									rowMap.get(columnName));
						} else {
							Object value = rs.getObject(columnName);
							if (value != null) {
								if (value instanceof String) {
									value = (String) value.toString().trim();
								}
								rowMap.put(columnName, value);
								rowMap.put(columnLabel, rowMap.get(columnName));
								rowMap.put(columnName.toLowerCase(),
										rowMap.get(columnName));
								rowMap.put(columnLabel.toLowerCase(),
										rowMap.get(columnName));
							}
						}
					}
					resultList.add(rowMap);
				}
			}

			psmt.close();
			rs.close();

			logger.debug(">resultList size = " + resultList.size());
			return resultList;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(psmt);
			JdbcUtils.close(rs);
		}
	}

	public List<Map<String, Object>> getResultList(Connection conn, String sql,
			Map<String, Object> paramMap) {
		SqlExecutor sqlExecutor = DBUtils.replaceSQL(sql, paramMap);
		return this.getResultList(conn, sqlExecutor);
	}

	/**
	 * @param conn
	 *            数据库连接对象
	 * @param start
	 *            开始记录游标
	 * @param pageSize
	 *            每页记录数
	 * @param sql
	 *            查询语句
	 * @param paramMap
	 *            参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ResultModel getResultList(Connection conn, String sql,
			Map<String, Object> paramMap, int start, int pageSize) {
		ResultModel resultModel = new ResultModel();
		boolean supportsPhysicalPage = false;
		PreparedStatement psmt = null;
		ResultSetMetaData rsmd = null;
		ResultSet rs = null;
		Dialect dialect = null;
		try {
			dialect = DBConfiguration.getDatabaseDialect(conn);
			if (dialect != null && dialect.supportsPhysicalPage()) {
				logger.debug("sql=" + sql);
				supportsPhysicalPage = dialect.supportsPhysicalPage();
				sql = dialect.getLimitString(sql, start, pageSize);
				logger.debug(">>sql=" + sql);
			}

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

			List<ColumnDefinition> columns = new ArrayList<ColumnDefinition>();
			rs = psmt.executeQuery();
			rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			for (int i = 1; i <= count; i++) {
				int sqlType = rsmd.getColumnType(i);
				ColumnDefinition column = new ColumnDefinition();
				column.setColumnName(rsmd.getColumnName(i));
				column.setColumnLabel(rsmd.getColumnLabel(i));
				column.setJavaType(FieldType.getJavaType(sqlType));
				column.setPrecision(rsmd.getPrecision(i));
				column.setScale(rsmd.getScale(i));
				if (column.getScale() == 0 && sqlType == Types.NUMERIC) {
					column.setJavaType("Long");
				}
				columns.add(column);
			}

			resultModel.setHeaders(columns);

			if (!supportsPhysicalPage) {
				this.skipRows(rs, start);
			}

			int k = 0;
			while (rs.next() && k++ < pageSize) {
				int index = 0;
				RowModel rowModel = new RowModel();
				Iterator<ColumnDefinition> iterator = columns.iterator();
				while (iterator.hasNext()) {
					ColumnDefinition column = iterator.next();
					ColumnDefinition c = new ColumnDefinition();
					c.setColumnName(column.getColumnName());
					c.setColumnLabel(column.getColumnLabel());
					c.setJavaType(column.getJavaType());
					c.setPrecision(column.getPrecision());
					c.setScale(column.getScale());
					String columnName = column.getColumnName();
					String javaType = column.getJavaType();
					index = index + 1;
					if ("String".equals(javaType)) {
						String value = rs.getString(columnName);
						c.setValue(value);
					} else if ("Integer".equals(javaType)) {
						try {
							Integer value = rs.getInt(columnName);
							c.setValue(value);
						} catch (Exception e) {
							String str = rs.getString(columnName);
							str = StringTools.replace(str, "$", "");
							str = StringTools.replace(str, "￥", "");
							str = StringTools.replace(str, ",", "");
							NumberFormat fmt = NumberFormat.getInstance();
							Number num = fmt.parse(str);
							c.setValue(num.intValue());
						}
					} else if ("Long".equals(javaType)) {
						try {
							Long value = rs.getLong(columnName);
							c.setValue(value);
						} catch (Exception e) {
							String str = rs.getString(columnName);
							str = StringTools.replace(str, "$", "");
							str = StringTools.replace(str, "￥", "");
							str = StringTools.replace(str, ",", "");
							NumberFormat fmt = NumberFormat.getInstance();
							Number num = fmt.parse(str);
							c.setValue(num.longValue());
						}
					} else if ("Double".equals(javaType)) {
						try {
							Double value = rs.getDouble(columnName);
							c.setValue(value);
						} catch (Exception e) {
							String str = rs.getString(columnName);
							str = StringTools.replace(str, "$", "");
							str = StringTools.replace(str, "￥", "");
							str = StringTools.replace(str, ",", "");
							NumberFormat fmt = NumberFormat.getInstance();
							Number num = fmt.parse(str);
							c.setValue(num.doubleValue());
						}
					} else if ("Boolean".equals(javaType)) {
						Boolean value = rs.getBoolean(columnName);
						c.setValue(value);
					} else if ("Date".equals(javaType)) {
						Timestamp value = rs.getTimestamp(columnName);
						c.setValue(value);
					} else {
						c.setValue(rs.getObject(columnName));
					}
					rowModel.addColumn(c);
				}
				resultModel.addRow(rowModel);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(psmt);
			JdbcUtils.close(rs);
		}
		return resultModel;
	}

	public List<Map<String, Object>> getResultList(SqlExecutor sqlExecutor,
			int start, int pageSize) {
		Connection conn = null;
		try {
			conn = DBConnectionFactory.getConnection();
			return this.getResultList(conn, sqlExecutor, start, pageSize);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
		}
	}

	/**
	 * 根据SQL语句获取结果集
	 * 
	 * @param sql
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> getResultList(String sql,
			Map<String, Object> paramMap) {
		Connection conn = null;
		try {
			conn = DBConnectionFactory.getConnection();
			return this.getResultList(conn, sql, paramMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
		}
	}

	/**
	 * 根据SQL语句获取指定页码的结果集
	 * 
	 * @param sql
	 * @param start
	 * @param pageSize
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> getResultList(String sql,
			Map<String, Object> paramMap, int start, int pageSize) {
		SqlExecutor sqlExecutor = DBUtils.replaceSQL(sql, paramMap);
		Connection conn = null;
		try {
			conn = DBConnectionFactory.getConnection();
			return this.getResultList(conn, sqlExecutor, start, pageSize);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
		}
	}

	/**
	 * 根据SQL语句获取结果集
	 * 
	 * @param systemName
	 * @param sqlExecutor
	 * @return
	 */
	public List<Map<String, Object>> getResultList(String systemName,
			SqlExecutor sqlExecutor) {
		Connection conn = null;
		try {
			conn = DBConnectionFactory.getConnection(systemName);
			return this.getResultList(conn, sqlExecutor);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
		}
	}

	/**
	 * 根据指定数据源及SQL语句获取结果集
	 * 
	 * @param systemName
	 * @param sql
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> getResultList(String systemName,
			String sql, Map<String, Object> paramMap) {
		Connection conn = null;
		try {
			conn = DBConnectionFactory.getConnection(systemName);
			return this.getResultList(conn, sql, paramMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
		}
	}

	/**
	 * 根据指定数据源及SQL语句获取指定页码的结果集
	 * 
	 * @param systemName
	 * @param sql
	 * @param firstResult
	 * @param maxResults
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> getResultList(String systemName,
			String sql, Map<String, Object> paramMap, int start, int pageSize) {
		SqlExecutor sqlExecutor = DBUtils.replaceSQL(sql, paramMap);
		Connection conn = null;
		try {
			conn = DBConnectionFactory.getConnection(systemName);
			return this.getResultList(conn, sqlExecutor, start, pageSize);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
		}
	}

	/**
	 * 获取某个表的数据
	 * 
	 * @param conn
	 *            JDBC连接
	 * @param start
	 *            开始记录位置
	 * @param pageSize
	 *            每页记录数
	 * @param tableName
	 *            数据表
	 * @param paramMap
	 *            参数集合
	 * @return
	 */
	public ResultModel getResultModel(Connection conn, String tableName,
			Map<String, Object> paramMap, int start, int pageSize) {
		ResultModel resultModel = null;
		String sql = "select count(*) from " + tableName;
		int total = this.getTotal(conn, sql, paramMap);
		if (total > 0) {
			sql = "select * from " + tableName;
			resultModel = this.getResultList(conn, sql, paramMap, start,
					pageSize);
			resultModel.setStart(start);
			resultModel.setPageSize(pageSize);
			resultModel.setTotal(total);
		}
		return resultModel;
	}

	public List<Map<String, Object>> getResults(ResultSet rs) {
		logger.debug("--------------use mybatis results----------------");
		try {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<String> columns = new ArrayList<String>();
			List<TypeHandler<?>> typeHandlers = new ArrayList<TypeHandler<?>>();
			ResultSetMetaData rsmd = rs.getMetaData();
			for (int i = 0, n = rsmd.getColumnCount(); i < n; i++) {
				columns.add(rsmd.getColumnLabel(i + 1));
				try {
					Class<?> type = Resources.classForName(rsmd
							.getColumnClassName(i + 1));
					TypeHandler<?> typeHandler = typeHandlerRegistry
							.getTypeHandler(type);
					if (typeHandler == null) {
						typeHandler = typeHandlerRegistry
								.getTypeHandler(Object.class);
					}
					typeHandlers.add(typeHandler);
				} catch (Exception ex) {
					ex.printStackTrace();
					typeHandlers.add(typeHandlerRegistry
							.getTypeHandler(Object.class));
				}
			}
			while (rs.next()) {
				Map<String, Object> row = new HashMap<String, Object>();
				for (int i = 0, n = columns.size(); i < n; i++) {
					String name = columns.get(i);
					TypeHandler<?> handler = typeHandlers.get(i);
					Object value = handler.getResult(rs, name);
					row.put(name, value);
					row.put(name.toLowerCase(Locale.ENGLISH), value);
					row.put(name.toUpperCase(Locale.ENGLISH), value);
				}
				list.add(row);
			}
			return list;
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
			} catch (SQLException e) {
			}
		}
	}

	@SuppressWarnings("unchecked")
	public int getTotal(Connection conn, SqlExecutor sqlExecutor) {
		int total = 0;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			psmt = conn.prepareStatement(sqlExecutor.getSql());
			if (sqlExecutor.getParameter() != null) {
				List<Object> values = (List<Object>) sqlExecutor.getParameter();
				JdbcUtils.fillStatement(psmt, values);
			}

			rs = psmt.executeQuery();
			if (rs.next()) {
				Object object = rs.getObject(1);
				if (object instanceof Integer) {
					Integer iCount = (Integer) object;
					total = iCount.intValue();
				} else if (object instanceof Long) {
					Long iCount = (Long) object;
					total = iCount.intValue();
				} else if (object instanceof BigDecimal) {
					BigDecimal bg = (BigDecimal) object;
					total = bg.intValue();
				} else if (object instanceof BigInteger) {
					BigInteger bi = (BigInteger) object;
					total = bi.intValue();
				} else {
					String x = object.toString();
					if (StringUtils.isNotEmpty(x)) {
						total = Integer.parseInt(x);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(psmt);
			JdbcUtils.close(rs);
		}
		return total;
	}

	@SuppressWarnings("unchecked")
	public int getTotal(Connection conn, String sql,
			Map<String, Object> paramMap) {
		int total = -1;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			List<Object> values = null;
			if (paramMap != null) {
				SqlExecutor sqlExecutor = DBUtils.replaceSQL(sql, paramMap);
				sql = sqlExecutor.getSql();
				values = (List<Object>) sqlExecutor.getParameter();
			}

			sql = DBUtils.removeOrders(sql);

			logger.debug("sql:\n" + sql);
			logger.debug("values:" + values);

			psmt = conn.prepareStatement(sql);

			if (values != null && !values.isEmpty()) {
				JdbcUtils.fillStatement(psmt, values);
			}

			rs = psmt.executeQuery();
			if (rs.next()) {
				Object object = rs.getObject(1);
				if (object != null) {
					if (object instanceof Integer) {
						Integer iCount = (Integer) object;
						total = iCount.intValue();
					} else if (object instanceof Long) {
						Long iCount = (Long) object;
						total = iCount.intValue();
					} else if (object instanceof BigDecimal) {
						BigDecimal bg = (BigDecimal) object;
						total = bg.intValue();
					} else if (object instanceof BigInteger) {
						BigInteger bi = (BigInteger) object;
						total = bi.intValue();
					} else {
						String x = object.toString();
						if (StringUtils.isNotEmpty(x)) {
							total = Integer.parseInt(x);
						}
					}
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(psmt);
			JdbcUtils.close(rs);
		}

		return total;
	}

	public int getTotal(SqlExecutor sqlExecutor) {
		Connection conn = null;
		try {
			conn = DBConnectionFactory.getConnection();
			return this.getTotal(conn, sqlExecutor);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
		}
	}

	public int getTotal(String systemName, SqlExecutor sqlExecutor) {
		Connection conn = null;
		try {
			conn = DBConnectionFactory.getConnection(systemName);
			return this.getTotal(conn, sqlExecutor);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
		}
	}

	public Map<String, Object> selectOne(Connection conn,
			SqlExecutor sqlExecutor) {
		List<Map<String, Object>> results = getResultList(conn, sqlExecutor);
		if (results != null && results.size() > 0) {
			return results.get(0);
		}
		return null;
	}

	/**
	 * 根据SQL语句获取结果集
	 * 
	 * @param sql
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> selectOne(String sql,
			Map<String, Object> paramMap) {
		SqlExecutor sqlExecutor = DBUtils.replaceSQL(sql, paramMap);
		Connection conn = null;
		try {
			conn = DBConnectionFactory.getConnection();
			return this.selectOne(conn, sqlExecutor);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
		}
	}

	public Map<String, Object> selectOne(String systemName,
			SqlExecutor sqlExecutor) {
		Connection conn = null;
		try {
			conn = DBConnectionFactory.getConnection(systemName);
			List<Map<String, Object>> results = getResultList(conn, sqlExecutor);
			if (results != null && results.size() > 0) {
				return results.get(0);
			}
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
		}
	}

	/**
	 * 根据SQL语句获取结果集
	 * 
	 * @param sql
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> selectOne(String systemName, String sql,
			Map<String, Object> paramMap) {
		SqlExecutor sqlExecutor = DBUtils.replaceSQL(sql, paramMap);
		Connection conn = null;
		try {
			conn = DBConnectionFactory.getConnection(systemName);
			return this.selectOne(conn, sqlExecutor);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
		}
	}

	protected void skipRows(ResultSet rs, int start) throws SQLException {
		if (rs.getType() != ResultSet.TYPE_FORWARD_ONLY) {
			if (start != 0) {
				logger.debug("rs absolute " + start);
				rs.absolute(start);
			}
		} else {
			for (int i = 0; i < start; i++) {
				rs.next();
			}
		}
	}

	protected void skipRows(ResultSet rs, int firstResult, int maxResults)
			throws SQLException {
		if (rs.getType() != ResultSet.TYPE_FORWARD_ONLY) {
			if (firstResult != 0) {
				rs.absolute(firstResult);
			}
		} else {
			for (int i = 0; i < firstResult; i++) {
				rs.next();
			}
		}
	}

}