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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.base.ResultModel;
import com.glaf.core.base.RowModel;
import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.query.QueryCondition;
import com.glaf.core.util.FieldType;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.SearchFilter;
import com.glaf.core.util.StringTools;

public class QueryExecutor {
	protected static final Log logger = LogFactory.getLog(QueryExecutor.class);

	public static void main(String[] args) {
		List<QueryCondition> conditions = new ArrayList<QueryCondition>();
		QueryCondition c1 = new QueryCondition();
		c1.setColumn("tname");
		c1.setFilter(SearchFilter.LIKE);
		c1.setValue("%合同%");
		conditions.add(c1);

		QueryCondition c2 = new QueryCondition();
		c2.setColumn("tname");
		c2.setFilter(SearchFilter.LIKE);
		c2.setValue("%开工%");
		conditions.add(c2);

		QueryCondition c3 = new QueryCondition();
		c3.setColumn("tname");
		c3.setFilter(SearchFilter.LIKE);
		c3.setValue("%报告%");
		conditions.add(c3);

		QueryCondition c4 = new QueryCondition();
		c4.setColumn("tname");
		c4.setFilter(SearchFilter.LIKE);
		c4.setValue("%板涵%");
		conditions.add(c4);

		QueryCondition c5 = new QueryCondition();
		c5.setColumn("index_id");
		c5.setFilter(SearchFilter.EQUALS);
		c5.setValue("14");
		conditions.add(c5);

		QueryCondition c6 = new QueryCondition();
		c6.setColumn("ctime");
		c6.setFilter(SearchFilter.LESS_THAN_OR_EQUAL);
		c6.setValue(new java.util.Date());
		conditions.add(c6);

	}

	public QueryExecutor() {

	}

	@SuppressWarnings("unchecked")
	public ResultModel getList(Connection conn, int start, int pageSize,
			String sql, Map<String, Object> paramMap) {
		ResultModel resultModel = new ResultModel();
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

			List<ColumnDefinition> columns = new ArrayList<ColumnDefinition>();
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

			resultModel.setHeaders(columns);

			this.skipRows(rs, start);

			int k = 0;
			while (rs.next() && k++ < pageSize) {
				int index = 0;
				RowModel rowModel = new RowModel();
				Iterator<ColumnDefinition> iterator = columns.iterator();
				while (iterator.hasNext()) {
					ColumnDefinition column = iterator.next();
					ColumnDefinition c = new ColumnDefinition();
					c.setColumnName(column.getColumnName());
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

	public SqlExecutor getMyBatisAndConditionSql(List<QueryCondition> conditions) {
		SqlExecutor se = new SqlExecutor();
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		StringBuffer buffer = new StringBuffer();
		if (conditions != null && !conditions.isEmpty()) {
			int index = 0;
			Iterator<QueryCondition> iter = conditions.iterator();
			while (iter.hasNext()) {
				index++;
				QueryCondition c = iter.next();
				buffer.append(FileUtils.newline);
				String column = c.getColumn();
				String filter = c.getFilter();
				String alias = c.getAlias();
				params.put("param_" + index, c.getValue());
				String operator = " = ";
				if (StringUtils.isNotEmpty(filter)) {
					if (SearchFilter.GREATER_THAN.equals(filter)) {
						operator = SearchFilter.GREATER_THAN;
					} else if (SearchFilter.GREATER_THAN_OR_EQUAL
							.equals(filter)) {
						operator = SearchFilter.GREATER_THAN_OR_EQUAL;
					} else if (SearchFilter.LESS_THAN.equals(filter)) {
						operator = SearchFilter.LESS_THAN;
					} else if (SearchFilter.LESS_THAN_OR_EQUAL.equals(filter)) {
						operator = SearchFilter.LESS_THAN_OR_EQUAL;
					} else if (SearchFilter.LIKE.equals(filter)) {
						operator = SearchFilter.LIKE;
					} else if (SearchFilter.NOT_LIKE.equals(filter)) {
						operator = SearchFilter.NOT_LIKE;
					} else if (SearchFilter.EQUALS.equals(filter)) {
						operator = SearchFilter.EQUALS;
					} else if (SearchFilter.NOT_EQUALS.equals(filter)) {
						operator = SearchFilter.NOT_EQUALS;
					} else {
						operator = SearchFilter.EQUALS;
					}
				}
				if (StringUtils.isNotEmpty(alias)) {
					buffer.append(" and ").append(alias).append(".")
							.append(column).append(" ").append(operator)
							.append(" #{").append("param_" + index)
							.append("} ");
				} else {
					buffer.append(" and ").append(column).append(" ")
							.append(operator).append(" #{")
							.append("param_" + index).append("} ");
				}
			}
		}

		se.setSql(buffer.toString());
		se.setParameter(params);

		return se;
	}

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
			rsmd = rs.getMetaData();

			int count = rsmd.getColumnCount();
			List<ColumnDefinition> columns = new ArrayList<ColumnDefinition>();

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
				Map<String, Object> rowMap = new HashMap<String, Object>();
				Iterator<ColumnDefinition> iterator = columns.iterator();
				while (iterator.hasNext()) {
					ColumnDefinition column = iterator.next();
					String columnName = column.getColumnName();
					String javaType = column.getJavaType();
					index = index + 1;
					if ("String".equals(javaType)) {
						String value = rs.getString(columnName);
						if (value != null) {
							value = value.trim();
							rowMap.put(columnName, value);
						}
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
							logger.error("错误的long:" + str);
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
					} else if ("Blob".equals(javaType)) {
						// ignore
					} else {
						Object value = rs.getObject(columnName);
						if (value != null) {
							if (value instanceof String) {
								value = (String) value.toString().trim();
							}
							rowMap.put(columnName, value);
						}
					}
				}
				resultList.add(rowMap);
			}

			psmt.close();
			rs.close();

			logger.debug(">resultList size=" + resultList.size());
			return resultList;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
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
	public ResultModel getResultModel(Connection conn, int start, int pageSize,
			String tableName, Map<String, Object> paramMap) {
		ResultModel resultModel = null;
		String sql = "select count(*) from " + tableName;
		int total = this.getTotal(conn, sql, paramMap);
		if (total > 0) {
			sql = "select * from " + tableName;
			resultModel = this.getList(conn, start, pageSize, sql, paramMap);
			resultModel.setStart(start);
			resultModel.setPageSize(pageSize);
			resultModel.setTotal(total);
		}
		return resultModel;
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