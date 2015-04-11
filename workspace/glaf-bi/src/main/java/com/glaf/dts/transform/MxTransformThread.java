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

package com.glaf.dts.transform;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;

import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.db.TransformTable;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.QueryDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.IQueryDefinitionService;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.FieldType;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.QueryUtils;
import com.glaf.core.util.StringTools;
import com.glaf.dts.domain.TransformTask;
import com.glaf.dts.service.ITransformTaskService;

public class MxTransformThread implements java.lang.Runnable {
	protected static final Log logger = LogFactory
			.getLog(MxTransformThread.class);

	protected int execution;

	protected String taskId;

	protected Map<String, Object> paramMap;

	protected QueryDefinition queryDefinition;

	protected TableDefinition tableDefinition;

	protected IDatabaseService databaseService;

	protected ITransformTaskService transformTaskService;

	protected IQueryDefinitionService queryDefinitionService;

	protected ITableDefinitionService tableDefinitionService;

	protected ITableDataService tableDataService;

	protected Map<String, ColumnDefinition> columnMap = new java.util.HashMap<String, ColumnDefinition>();

	public MxTransformThread(String taskId) {
		this.init(taskId);
	}

	public IDatabaseService getDatabaseService() {
		if (databaseService == null) {
			databaseService = ContextFactory.getBean("databaseService");
		}
		return databaseService;
	}

	@SuppressWarnings("unchecked")
	private void init(String taskId) {
		transformTaskService = ContextFactory.getBean("transformTaskService");
		queryDefinitionService = ContextFactory
				.getBean("queryDefinitionService");
		tableDefinitionService = ContextFactory
				.getBean("tableDefinitionService");
		tableDataService = ContextFactory.getBean("tableDataService");
		this.taskId = taskId;
		TransformTask task = transformTaskService.getTransformTask(taskId);
		String queryId = task.getQueryId();
		queryDefinition = queryDefinitionService.getQueryDefinition(queryId);
		String tableName = queryDefinition.getTargetTableName();
		tableDefinition = tableDefinitionService.getTableDefinition(tableName);
		if (tableDefinition.getColumns() != null) {
			for (ColumnDefinition column : tableDefinition.getColumns()) {
				columnMap.put(column.getColumnName(), column);
				columnMap.put(column.getColumnName().toLowerCase(), column);
			}
		}
		String parameter = task.getParameter();
		if (parameter != null) {
			JsonFactory f = new JsonFactory();
			ObjectMapper mapper = new ObjectMapper(f);
			try {
				paramMap = new java.util.HashMap<String, Object>();
				paramMap = (Map<String, Object>) mapper.readValue(parameter,
						HashMap.class);
				logger.debug("paramMap:" + paramMap);
			} catch (Exception ex) {
				paramMap = JsonUtils.decode(parameter);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void run() {
		logger.debug(taskId + "----------------execution-----------------");
		TransformTask task = transformTaskService.getTransformTask(taskId);
		if (task != null) {
			if (task.getStatus() == 9 || task.getRetryTimes() > 3) {
				return;
			}
			task.setStartTime(new java.util.Date());
			task.setRetryTimes(task.getRetryTimes() + 1);
			task.setStatus(1);
			transformTaskService.save(task);
		}

		List<TableModel> resultList = new java.util.ArrayList<TableModel>();
		Map<String, Object> singleDataMap = new HashMap<String, Object>();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		boolean success = true;
		long start = System.currentTimeMillis();
		logger.debug("start:" + DateUtils.getDateTime(new java.util.Date()));
		try {
			Database database = getDatabaseService().getDatabaseById(
					queryDefinition.getDatabaseId());
			if (database != null) {
				conn = DBConnectionFactory.getConnection(database.getName());
			} else {
				conn = DBConnectionFactory.getConnection();
			}

			logger.debug("conn:" + conn.toString());

			String sql = queryDefinition.getSql();
			sql = QueryUtils.replaceSQLVars(sql);
			List<Object> values = null;
			if (paramMap != null) {
				SqlExecutor sqlExecutor = JdbcUtils.rebuildSQL(sql, paramMap);
				sql = sqlExecutor.getSql();
				values = (List<Object>) sqlExecutor.getParameter();
			}

			logger.debug("--------------execute query----------------------");
			logger.debug(queryDefinition.getTitle());

			logger.debug("::sql::" + sql);
			psmt = conn.prepareStatement(sql);

			if (values != null && !values.isEmpty()) {
				JdbcUtils.fillStatement(psmt, values);
				logger.debug("::values::" + values);
			}

			List<ColumnDefinition> columns = new java.util.ArrayList<ColumnDefinition>();

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
				columns.add(column);
			}

			Set<String> cols = new HashSet<String>();

			while (rs.next()) {
				int index = 0;
				TableModel rowModel = new TableModel();

				ColumnModel cell01 = new ColumnModel();
				cell01.setColumnName("ID");
				cell01.setType("String");
				rowModel.addColumn(cell01);
				rowModel.setIdColumn(cell01);
				cols.add(cell01.getColumnName());

				ColumnModel cell04 = new ColumnModel();
				cell04.setColumnName("AGGREGATIONKEY");
				cell04.setType("String");
				rowModel.addColumn(cell04);
				cols.add(cell04.getColumnName());

				Iterator<ColumnDefinition> iterator = columns.iterator();
				while (iterator.hasNext()) {
					ColumnDefinition column = iterator.next();
					/**
					 * 不取系统内置变量
					 */
					if (cols.contains(column.getColumnName())) {
						continue;
					}
					ColumnModel cell = new ColumnModel();
					String columnName = column.getColumnName();
					String javaType = column.getJavaType();
					cell.setColumnName(columnName);
					cell.setType(javaType);
					index = index + 1;
					if ("String".equals(javaType)) {
						String value = rs.getString(columnName);
						cell.setStringValue(value);
						cell.setValue(value);
					} else if ("Integer".equals(javaType)) {
						try {
							Integer value = rs.getInt(columnName);
							cell.setIntValue(value);
							cell.setValue(value);
						} catch (Exception e) {
							String str = rs.getString(columnName);
							logger.error("错误的integer:" + str);
							str = StringTools.replace(str, "$", "");
							str = StringTools.replace(str, "￥", "");
							str = StringTools.replace(str, ",", "");
							NumberFormat fmt = NumberFormat.getInstance();
							Number num = fmt.parse(str);
							cell.setIntValue(num.intValue());
							cell.setValue(cell.getIntValue());
							logger.debug("修正后:" + num.intValue());
						}
					} else if ("Long".equals(javaType)) {
						try {
							Long value = rs.getLong(columnName);
							cell.setLongValue(value);
							cell.setValue(value);
						} catch (Exception e) {
							String str = rs.getString(columnName);
							logger.error("错误的long:" + str);
							str = StringTools.replace(str, "￥", "");
							str = StringTools.replace(str, ",", "");
							NumberFormat fmt = NumberFormat.getInstance();
							Number num = fmt.parse(str);
							cell.setLongValue(num.longValue());
							cell.setValue(cell.getLongValue());
							logger.debug("修正后:" + num.longValue());
						}
					} else if ("Double".equals(javaType)) {
						try {
							Double d = rs.getDouble(columnName);
							cell.setDoubleValue(d);
							cell.setValue(d);
						} catch (Exception e) {
							String str = rs.getString(columnName);
							logger.error("错误的double:" + str);
							str = StringTools.replace(str, "￥", "");
							str = StringTools.replace(str, ",", "");
							NumberFormat fmt = NumberFormat.getInstance();
							Number num = fmt.parse(str);
							cell.setDoubleValue(num.doubleValue());
							cell.setValue(cell.getDoubleValue());
							logger.debug("修正后:" + num.doubleValue());
						}
					} else if ("Boolean".equals(javaType)) {
						Boolean value = rs.getBoolean(columnName);
						cell.setBooleanValue(value);
						cell.setValue(value);
					} else if ("Date".equals(javaType)) {
						Date value = rs.getTimestamp(columnName);
						cell.setDateValue(value);
						cell.setValue(value);
					} else {
						String value = rs.getString(columnName);
						cell.setStringValue(value);
						cell.setValue(value);
					}
					rowModel.addColumn(cell);
					if (resultList.isEmpty()) {
						singleDataMap.put(column.getColumnLabel(),
								cell.getValue());
					}
				}
				resultList.add(rowModel);
			}

			logger.debug("--------------------resultList size:"
					+ resultList.size());

		} catch (Exception ex) {
			success = false;
			ex.printStackTrace();
			logger.error(ex);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(rs);
			JdbcUtils.close(psmt);
			JdbcUtils.close(conn);
			if (!success) {
				if (task != null) {
					task.setStatus(2);
					transformTaskService.save(task);
				}
			}
		}

		logger.debug("--------------execute mybatis save----------------------");

		try {

			if (!StringUtils.equalsIgnoreCase(
					queryDefinition.getRotatingFlag(), "R2C")) {
				TransformTable tbl = new TransformTable();
				tbl.createOrAlterTable(tableDefinition);
			}

			List<ColumnDefinition> columns = DBUtils
					.getColumnDefinitions(tableDefinition.getTableName());
			if (columns != null && !columns.isEmpty()) {
				tableDefinition.setColumns(columns);
			}

			if (resultList != null && !resultList.isEmpty()
					&& tableDefinition.getTableName() != null
					&& tableDefinition.getAggregationKeys() != null) {
				logger.debug("RotatingFlag:"
						+ queryDefinition.getRotatingFlag());
				logger.debug("RotatingColumn:"
						+ queryDefinition.getRotatingColumn());
				/**
				 * 处理一条行记录转成多条列记录
				 */
				if (StringUtils.equalsIgnoreCase(
						queryDefinition.getRotatingFlag(), "R2C")
						&& StringUtils.isNotEmpty(queryDefinition
								.getRotatingColumn()) && resultList.size() == 1) {

					logger.debug("待转换的dataMap数据:" + singleDataMap);
					logger.debug("AggregationKeys:"
							+ tableDefinition.getAggregationKeys());
					ColumnDefinition idField = columnMap.get(tableDefinition
							.getAggregationKeys().toLowerCase());
					ColumnDefinition field = columnMap.get(queryDefinition
							.getRotatingColumn().toLowerCase());
					logger.debug("idField:" + idField);
					logger.debug("field:" + field);
					if (idField != null && field != null) {
						String javaType = field.getJavaType();
						List<TableModel> list = new ArrayList<TableModel>();
						Set<Entry<String, Object>> entrySet = singleDataMap
								.entrySet();
						for (Entry<String, Object> entry : entrySet) {
							String key = entry.getKey();
							Object value = entry.getValue();
							if (key == null || value == null) {
								continue;
							}
							TableModel tableModel = new TableModel();
							tableModel.setTableName(queryDefinition
									.getTargetTableName());
							ColumnModel cell = new ColumnModel();
							cell.setColumnName(queryDefinition
									.getRotatingColumn());
							cell.setType(javaType);

							// logger.debug(cell.getColumnName()+"->"+javaType);

							if ("String".equals(javaType)) {
								cell.setStringValue(ParamUtils.getString(
										singleDataMap, key));
								cell.setValue(cell.getStringValue());
							} else if ("Integer".equals(javaType)) {
								cell.setIntValue(ParamUtils.getInt(
										singleDataMap, key));
								cell.setValue(cell.getIntValue());
							} else if ("Long".equals(javaType)) {
								cell.setLongValue(ParamUtils.getLong(
										singleDataMap, key));
								cell.setValue(cell.getLongValue());
							} else if ("Double".equals(javaType)) {
								cell.setDoubleValue(ParamUtils.getDouble(
										singleDataMap, key));
								cell.setValue(cell.getDoubleValue());
							} else if ("Date".equals(javaType)) {
								cell.setDateValue(ParamUtils.getDate(
										singleDataMap, key));
								cell.setValue(cell.getDateValue());
							} else {
								cell.setValue(value);
							}

							tableModel.addColumn(cell);

							ColumnModel idColumn = new ColumnModel();
							idColumn.setColumnName(tableDefinition
									.getAggregationKeys());
							idColumn.setJavaType(idField.getJavaType());
							idColumn.setValue(key);
							tableModel.setIdColumn(idColumn);
							list.add(tableModel);
						}
						logger.debug("update datalist:" + list);
						tableDataService.updateTableData(list);
					}
				} else {
					tableDataService.saveAll(tableDefinition, null, resultList);
				}
			}

			resultList.clear();
			resultList = null;

			long time = System.currentTimeMillis() - start;

			if (task != null) {
				task.setEndTime(new java.util.Date());
				task.setStatus(9);
				task.setDuration(time);
			}
			logger.debug("execute time(ms)--------------------------" + time);
		} catch (Exception ex) {
			if (task != null) {
				task.setStatus(2);
			}
			ex.printStackTrace();
			logger.error(ex);
			throw new RuntimeException(ex);
		} finally {
			if (task != null) {
				transformTaskService.save(task);
				if (task.getStatus() != 9) {
					this.run();
				}
			}
		}
	}

}