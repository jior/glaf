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

package com.glaf.core.db;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.QueryDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.service.IQueryDefinitionService;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.service.ITablePageService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.ExpressionConstants;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.QueryUtils;
import com.glaf.core.util.StringTools;

public class TransformTable {
	protected static final Log logger = LogFactory.getLog(TransformTable.class);

	protected volatile IQueryDefinitionService queryDefinitionService;

	protected volatile ITableDataService tableDataService;

	protected volatile ITableDefinitionService tableDefinitionService;

	protected volatile ITablePageService tablePageService;

	public TransformTable() {

	}

	/**
	 * 创建数据库表，如果已经存在，则修改表结构
	 * 
	 * @param tableDefinition
	 *            表定义
	 */
	public void createOrAlterTable(TableDefinition tableDefinition) {
		ColumnDefinition column = new ColumnDefinition();
		column.setName("id");
		column.setTitle("系统内置主键");
		column.setColumnName("ID");
		column.setJavaType("String");
		column.setLength(50);
		column.setValueExpression(ExpressionConstants.ID_EXPRESSION);
		column.setPrimaryKey(true);
		tableDefinition.addColumn(column);
		tableDefinition.setIdColumn(column);

		ColumnDefinition column4 = new ColumnDefinition();
		column4.setTitle("聚合主键");
		column4.setName("aggregationKey");
		column4.setColumnName("AGGREGATIONKEY");
		column4.setJavaType("String");
		column4.setLength(500);
		tableDefinition.addColumn(column4);

		if (DBUtils.tableExists(tableDefinition.getTableName())) {
			logger.info("---------------------------------------------");
			logger.info("------------alter table----------------------");
			DBUtils.alterTable(tableDefinition);
		} else {
			logger.info("---------------------------------------------");
			logger.info("------------create table---------------------");
			DBUtils.createTable(tableDefinition);
		}

		getTableDefinitionService().save(tableDefinition);
	}

	public IQueryDefinitionService getQueryDefinitionService() {
		if (queryDefinitionService == null) {
			queryDefinitionService = ContextFactory
					.getBean("queryDefinitionService");
		}
		return queryDefinitionService;
	}

	public ITableDataService getTableDataService() {
		if (tableDataService == null) {
			tableDataService = ContextFactory.getBean("tableDataService");
		}
		return tableDataService;
	}

	public ITableDefinitionService getTableDefinitionService() {
		if (tableDefinitionService == null) {
			tableDefinitionService = ContextFactory
					.getBean("tableDefinitionService");
		}
		return tableDefinitionService;
	}

	public ITablePageService getTablePageService() {
		if (tablePageService == null) {
			tablePageService = ContextFactory.getBean("tablePageService");
		}
		return tablePageService;
	}

	public void setQueryDefinitionService(
			IQueryDefinitionService queryDefinitionService) {
		this.queryDefinitionService = queryDefinitionService;
	}

	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

	public void setTableDefinitionService(
			ITableDefinitionService tableDefinitionService) {
		this.tableDefinitionService = tableDefinitionService;
	}

	public void transform(String tableName) {
		TableDefinition tableDefinition = getTableDefinitionService()
				.getTableDefinition(tableName);
		if (tableDefinition != null
				&& StringUtils.isNotEmpty(tableDefinition.getAggregationKeys())
				&& !tableDefinition.getQueries().isEmpty()) {
			Map<String, Object> params = SystemConfig.getContextMap();
			List<ColumnDefinition> columns = DBUtils
					.getColumnDefinitions(tableName);
			Map<String, ColumnDefinition> columnMap = new java.util.HashMap<String, ColumnDefinition>();

			for (ColumnDefinition column : columns) {
				columnMap.put(column.getColumnName(), column);
				columnMap.put(column.getColumnName().toLowerCase(), column);
			}
			List<String> aggregationKeys = new java.util.ArrayList<String>();
			List<String> keys = StringTools.split(tableDefinition
					.getAggregationKeys());
			for (String key : keys) {
				key = key.toLowerCase();
				aggregationKeys.add(key);
			}
			logger.debug("aggregationKeys=" + aggregationKeys);
			StringBuffer sb = new StringBuffer(1000);

			List<ColumnModel> cellModelList = new java.util.ArrayList<ColumnModel>();
			Map<String, TableModel> resultMap = new java.util.HashMap<String, TableModel>();

			for (QueryDefinition q : tableDefinition.getQueries()) {
				String sql = q.getSql();
				sql = QueryUtils.replaceSQLVars(sql);
				sql = QueryUtils.replaceSQLParas(sql, params);
				logger.debug("sql=" + sql);
				logger.debug("columnMap=" + columnMap.keySet());

				List<Map<String, Object>> rows = getTablePageService()
						.getListData(sql, params);
				if (rows != null && !rows.isEmpty()) {
					logger.debug(q.getTitle() + " 查询结果：" + rows.size());
					Set<String> cols = new HashSet<String>();
					for (Map<String, Object> dataMap : rows) {
						sb.delete(0, sb.length());
						cols.clear();
						cellModelList.clear();
						logger.debug(dataMap);
						Set<Entry<String, Object>> entrySet = dataMap
								.entrySet();
						for (Entry<String, Object> entry : entrySet) {
							String key = entry.getKey();
							Object value = entry.getValue();
							if (key == null || value == null) {
								continue;
							}
							/**
							 * 不取系统内置变量
							 */
							if (cols.contains(key.toLowerCase())) {
								continue;
							}
							if (columnMap.get(key.toLowerCase()) == null) {
								logger.debug(key + " definition is null");
								continue;
							}

							if (aggregationKeys.contains(key.toLowerCase())) {
								sb.append(value).append("_");
							}

							ColumnDefinition column = columnMap.get(key
									.toLowerCase());
							String javaType = column.getJavaType();

							ColumnModel cell = new ColumnModel();
							cell.setColumnName(column.getColumnName());
							cell.setType(javaType);

							if ("String".equals(javaType)) {
								cell.setStringValue(ParamUtils.getString(
										dataMap, key));
								cell.setValue(cell.getStringValue());
							} else if ("Integer".equals(javaType)) {
								cell.setIntValue(ParamUtils
										.getInt(dataMap, key));
								cell.setValue(cell.getIntValue());
							} else if ("Long".equals(javaType)) {
								cell.setLongValue(ParamUtils.getLong(dataMap,
										key));
								cell.setValue(cell.getLongValue());
							} else if ("Double".equals(javaType)) {
								cell.setDoubleValue(ParamUtils.getDouble(
										dataMap, key));
								cell.setValue(cell.getDoubleValue());
							} else if ("Date".equals(javaType)) {
								cell.setDateValue(ParamUtils.getDate(dataMap,
										key));
								cell.setValue(cell.getDateValue());
							} else {
								cell.setValue(value);
							}

							cellModelList.add(cell);
							cols.add(cell.getColumnName());
						}

						// logger.debug(sb.toString());

						/**
						 * 处理其中一条记录
						 */
						if (sb.toString().endsWith("_")) {
							sb.delete(sb.length() - 1, sb.length());
							String rowKey = sb.toString();
							logger.debug("rowKey=" + rowKey);
							TableModel rowModel = resultMap.get(rowKey);
							if (rowModel == null) {
								rowModel = new TableModel();
								ColumnModel cell01 = new ColumnModel();
								cell01.setColumnName("ID");
								cell01.setType("String");
								cell01.setValueExpression(ExpressionConstants.ID_EXPRESSION);
								cols.add("ID");
								rowModel.addColumn(cell01);
								rowModel.setIdColumn(cell01);

								ColumnModel cell04 = new ColumnModel();
								cell04.setColumnName("AGGREGATIONKEY");
								cell04.setType("String");
								cols.add("AGGREGATIONKEY");
								rowModel.addColumn(cell04);

								resultMap.put(rowKey, rowModel);
							}

							for (ColumnModel cell : cellModelList) {
								/**
								 * 确保数据表中定义了该列
								 */
								if (columnMap.get(cell.getColumnName()
										.toLowerCase()) != null) {
									rowModel.addColumn(cell);
								}
							}
						}
					}
				}
			}

			TableModel rowModel = new TableModel();
			rowModel.setTableName(tableName);
			rowModel.setAggregationKey(tableDefinition.getAggregationKeys());

			ColumnModel column1 = new ColumnModel();
			column1.setName("id");
			column1.setTitle("系统内置主键");
			column1.setColumnName("ID");
			column1.setJavaType("String");
			column1.setValueExpression(ExpressionConstants.ID_EXPRESSION);

			ColumnModel column4 = new ColumnModel();
			column4.setTitle("聚合主键");
			column4.setName("aggregationKey");
			column4.setColumnName("AGGREGATIONKEY");
			column4.setJavaType("String");

			rowModel.addColumn(column1);
			rowModel.addColumn(column4);

			for (ColumnDefinition column : columns) {
				ColumnModel cell = new ColumnModel();
				cell.setColumnName(column.getColumnName());
				cell.setType(column.getJavaType());
				rowModel.addColumn(cell);
			}

			Collection<TableModel> rows = resultMap.values();
			logger.debug("fetch data list size:" + rows.size());
			if (rows.size() > 0) {
				String seqNo = rowModel.getTableName() + "-"
						+ DateUtils.getDateTime(new Date());
				getTableDataService().saveAll(rowModel.getTableName(), seqNo,
						rows);
			}
		}
	}

	public void transformQueryToTable(String tableName, String queryId) {
		TableDefinition tableDefinition = getTableDefinitionService()
				.getTableDefinition(tableName);
		QueryDefinition queryDefinition = getQueryDefinitionService()
				.getQueryDefinition(queryId);
		if (queryDefinition != null && tableDefinition != null
				&& StringUtils.isNotEmpty(tableDefinition.getAggregationKeys())) {
			Map<String, Object> params = SystemConfig.getContextMap();
			List<ColumnDefinition> columns = DBUtils
					.getColumnDefinitions(tableName);
			Map<String, ColumnDefinition> columnMap = new java.util.HashMap<String, ColumnDefinition>();

			for (ColumnDefinition column : columns) {
				columnMap.put(column.getColumnName(), column);
				columnMap.put(column.getColumnName().toLowerCase(), column);
			}

			List<String> keys = StringTools.split(tableDefinition
					.getAggregationKeys());
			StringBuffer sb = new StringBuffer(1000);

			List<ColumnModel> cellModelList = new java.util.ArrayList<ColumnModel>();
			Map<String, TableModel> resultMap = new java.util.HashMap<String, TableModel>();

			if (queryDefinition.getSql() != null) {
				String sql = queryDefinition.getSql();
				sql = QueryUtils.replaceSQLVars(sql);
				sql = QueryUtils.replaceSQLParas(sql, params);
				logger.debug("sql=" + sql);

				List<Map<String, Object>> rows = getTablePageService()
						.getListData(sql, params);
				if (rows != null && !rows.isEmpty()) {
					logger.debug(queryDefinition.getTitle() + " 查询结果："
							+ rows.size());
					Set<String> cols = new HashSet<String>();
					for (Map<String, Object> dataMap : rows) {
						sb.delete(0, sb.length());
						cols.clear();
						cellModelList.clear();

						Set<Entry<String, Object>> entrySet = dataMap
								.entrySet();
						for (Entry<String, Object> entry : entrySet) {
							String key = entry.getKey();
							Object value = entry.getValue();
							if (key == null || value == null) {
								continue;
							}
							/**
							 * 不取系统内置变量
							 */
							if (cols.contains(key.toLowerCase())) {
								continue;
							}
							if (columnMap.get(key.toLowerCase()) == null) {
								continue;
							}

							if (keys.contains(key)) {
								sb.append(value).append("_");
							}

							ColumnDefinition column = columnMap.get(key
									.toLowerCase());
							String javaType = column.getJavaType();

							ColumnModel cell = new ColumnModel();
							cell.setColumnName(column.getColumnName());
							cell.setType(javaType);

							if ("String".equals(javaType)) {
								cell.setStringValue(ParamUtils.getString(
										dataMap, key));
								cell.setValue(cell.getStringValue());
							} else if ("Integer".equals(javaType)) {
								cell.setIntValue(ParamUtils
										.getInt(dataMap, key));
								cell.setValue(cell.getIntValue());
							} else if ("Long".equals(javaType)) {
								cell.setLongValue(ParamUtils.getLong(dataMap,
										key));
								cell.setValue(cell.getLongValue());
							} else if ("Double".equals(javaType)) {
								cell.setDoubleValue(ParamUtils.getDouble(
										dataMap, key));
								cell.setValue(cell.getDoubleValue());
							} else if ("Date".equals(javaType)) {
								cell.setDateValue(ParamUtils.getDate(dataMap,
										key));
								cell.setValue(cell.getDateValue());
							} else {
								cell.setValue(value);
							}

							cellModelList.add(cell);
							cols.add(cell.getColumnName());
						}

						/**
						 * 处理其中一条记录
						 */
						if (sb.toString().endsWith("_")) {
							sb.delete(sb.length() - 1, sb.length());
							String rowKey = sb.toString();
							logger.debug("rowKey=" + rowKey);
							TableModel rowModel = resultMap.get(rowKey);
							if (rowModel == null) {
								rowModel = new TableModel();
								ColumnModel cell01 = new ColumnModel();
								cell01.setColumnName("ID");
								cell01.setType("String");
								cell01.setValueExpression(ExpressionConstants.ID_EXPRESSION);
								cols.add("ID");
								rowModel.addColumn(cell01);
								rowModel.setIdColumn(cell01);

								ColumnModel cell04 = new ColumnModel();
								cell04.setColumnName("AGGREGATIONKEY");
								cell04.setType("String");
								cols.add("AGGREGATIONKEY");
								rowModel.addColumn(cell04);

								resultMap.put(rowKey, rowModel);
							}

							for (ColumnModel cell : cellModelList) {
								/**
								 * 确保数据表中定义了该列
								 */
								if (columnMap.get(cell.getColumnName()
										.toLowerCase()) != null) {
									rowModel.addColumn(cell);
								}
							}
						}
					}
				}
			}

			TableModel rowModel = new TableModel();
			rowModel.setTableName(tableName);
			rowModel.setAggregationKey(tableDefinition.getAggregationKeys());

			ColumnModel cell01 = new ColumnModel();
			cell01.setColumnName("ID");
			cell01.setType("String");
			cell01.setValueExpression(ExpressionConstants.ID_EXPRESSION);
			rowModel.addColumn(cell01);
			rowModel.setIdColumn(cell01);

			ColumnModel cell04 = new ColumnModel();
			cell04.setColumnName("AGGREGATIONKEY");
			cell04.setType("String");
			rowModel.addColumn(cell04);

			for (ColumnDefinition column : columns) {
				ColumnModel cell = new ColumnModel();
				cell.setColumnName(column.getColumnName());
				cell.setType(column.getJavaType());
				rowModel.addColumn(cell);
			}

			Collection<TableModel> rows = resultMap.values();
			logger.debug("fetch data list size:" + rows.size());
			if (rows.size() > 0) {
				String seqNo = rowModel.getTableName() + "-"
						+ DateUtils.getDateTime(new Date());
				getTableDataService().saveAll(rowModel.getTableName(), seqNo,
						rows);
			}
		}
	}

}