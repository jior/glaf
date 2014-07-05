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

package com.glaf.core.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.config.Environment;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.id.Dbid;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.mapper.ColumnDefinitionMapper;
import com.glaf.core.mapper.IdMapper;
import com.glaf.core.mapper.TableDataMapper;
import com.glaf.core.mapper.TablePageMapper;
import com.glaf.core.query.TablePageQuery;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.ExpressionConstants;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.UUID32;

@Service("tableDataService")
@Transactional
public class MxTableDataServiceImpl implements ITableDataService {
	protected final static Log logger = LogFactory
			.getLog(MxTableDataServiceImpl.class);

	protected EntityDAO entityDAO;

	protected IdMapper idMapper;

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	protected TableDataMapper tableDataMapper;

	protected TablePageMapper tablePageMapper;

	protected ColumnDefinitionMapper columnDefinitionMapper;

	protected ITableDefinitionService tableDefinitionService;

	public MxTableDataServiceImpl() {

	}

	/**
	 * 删除数据
	 * 
	 * @param model
	 */
	@Transactional
	public void deleteTableData(TableModel model) {
		if (StringUtils.isNotEmpty(model.getTableName())
				&& model.getColumns() != null && !model.getColumns().isEmpty()) {
			if (model.getTableName() != null) {
				model.setTableName(model.getTableName().toUpperCase());
			}
			tableDataMapper.deleteTableData(model);
		}
	}

	public List<Dbid> getAllDbids() {
		return idMapper.getAllDbids();
	}

	/**
	 * 获取一页数据
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param model
	 * @return
	 */
	public Paging getPageData(int pageNo, int pageSize, TableModel model) {
		if (model.getTableName() != null) {
			model.setTableName(model.getTableName().toUpperCase());
		}
		Paging page = new Paging();
		TablePageQuery query = new TablePageQuery();
		query.tableName(model.getTableName());

		int count = tablePageMapper.getTableCount(query);
		if (count > 0) {
			page.setTotal(count);
			SqlExecutor queryExecutor = new SqlExecutor();
			queryExecutor.setParameter(model);
			queryExecutor.setStatementId("getTableData");
			List<Object> rows = entityDAO.getList(pageNo, pageSize,
					queryExecutor);
			page.setCurrentPage(pageNo);
			page.setPageSize(pageSize);
			page.setRows(rows);
		}
		return page;
	}

	public Map<String, Object> getTableDataByPrimaryKey(TableModel model) {
		if (model.getTableName() != null) {
			model.setTableName(model.getTableName().toUpperCase());
		}
		return tableDataMapper.getTableDataByPrimaryKey(model);
	}

	public List<Map<String, Object>> getTableKeyMap(TableModel model) {
		return tableDataMapper.getTablePrimaryKeyMap(model);
	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> getTablePrimaryKeyMap(String tableName,
			String columnName) {
		TableModel tableModel = new TableModel();
		ColumnModel idColumn = new ColumnModel();
		idColumn.setColumnName(columnName);
		tableModel.setTableName(tableName.toUpperCase());
		tableModel.setIdColumn(idColumn);
		return tableDataMapper.getTablePrimaryKeyMap(tableModel);
	}

	@Transactional
	public Collection<TableModel> insertAll(TableDefinition tableDefinition,
			String seqNo, Collection<TableModel> rows) {
		logger.debug("tableDefinition=" + tableDefinition);
		logger.debug("idColumn=" + tableDefinition.getIdColumn().toString());
		if (tableDefinition.getTableName() != null) {
			tableDefinition.setTableName(tableDefinition.getTableName()
					.toUpperCase());
		}
		Map<String, Object> colMap = new java.util.HashMap<String, Object>();

		Map<String, String> exprMap = new java.util.HashMap<String, String>();
		List<ColumnDefinition> exprColumns = new java.util.ArrayList<ColumnDefinition>();

		ColumnModel idColumn = new ColumnModel();

		ColumnDefinition idCol = tableDefinition.getIdColumn();
		if (idCol != null && idCol.getColumnName() != null) {
			idColumn.setColumnName(idCol.getColumnName());
			idColumn.setJavaType(idCol.getJavaType());
			idColumn.setValueExpression(idCol.getValueExpression());
			exprColumns.add(idCol);
			exprMap.put(idCol.getColumnName().toUpperCase(),
					idCol.getValueExpression());
		}

		Iterator<ColumnDefinition> iter = tableDefinition.getColumns()
				.iterator();
		while (iter.hasNext()) {
			ColumnDefinition cell = iter.next();
			if (StringUtils.isNotEmpty(cell.getValueExpression())) {
				exprMap.put(cell.getColumnName().toUpperCase(),
						cell.getValueExpression());
				exprColumns.add(cell);
			}
		}

		logger.debug("expr map:" + exprMap);

		List<TableModel> inertRows = new java.util.ArrayList<TableModel>();

		logger.debug(" rows size = " + rows.size());
		// logger.debug(" key map: " + keyMap);
		Iterator<TableModel> iterator = rows.iterator();
		while (iterator.hasNext()) {
			TableModel tableData = iterator.next();
			ColumnModel myPK = tableData.getIdColumn();
			ColumnModel pkColumn = new ColumnModel();
			pkColumn.setColumnName(idColumn.getColumnName());
			pkColumn.setJavaType(idColumn.getJavaType());

			for (ColumnModel column : tableData.getColumns()) {
				colMap.put(column.getColumnName(), column.getValue());
			}

			for (ColumnDefinition c : exprColumns) {
				ColumnModel x = new ColumnModel();
				x.setColumnName(c.getColumnName());
				x.setJavaType(c.getJavaType());
				x.setValueExpression(c.getValueExpression());
				tableData.addColumn(x);
			}

			for (ColumnModel cell : tableData.getColumns()) {
				String expr = exprMap.get(cell.getColumnName().toUpperCase());
				if (StringUtils.isNotEmpty(expr)) {
					if (ExpressionConstants.NOW_EXPRESSION.equals(expr)
							|| ExpressionConstants.CURRENT_YYYYMMDD_EXPRESSION
									.equals(expr)) {
						if (cell.getDateValue() == null) {
							cell.setDateValue(new Date());
							cell.setValue(cell.getDateValue());
						}
					}
					if (ExpressionConstants.ID_EXPRESSION.equals(expr)) {
						if (cell.getValue() == null) {
							if (StringUtils.equals(cell.getJavaType(),
									"Integer")) {
								cell.setValue(idGenerator.nextId().intValue());
							} else if (StringUtils.equals(cell.getJavaType(),
									"Long")) {
								cell.setValue(idGenerator.nextId());
							} else {
								cell.setValue(idGenerator.getNextId());
							}
						}
					}
					if (ExpressionConstants.SEQNO_EXPRESSION.equals(expr)) {
						cell.setValue(seqNo);
					}
					if (ExpressionConstants.UUID_EXPRESSION.equals(expr)) {
						cell.setValue(UUID32.getUUID());
					}
				}
			}

			if (myPK != null && myPK.getValue() != null) {
				pkColumn.setValue(myPK.getValue());
			} else {
				if (StringUtils.equals(pkColumn.getJavaType(), "Integer")) {
					pkColumn.setValue(idGenerator.nextId().intValue());
					logger.debug("------------int--------------");
				} else if (StringUtils.equals(pkColumn.getJavaType(), "Long")) {
					pkColumn.setValue(idGenerator.nextId());
				} else {
					pkColumn.setValue(idGenerator.getNextId());
				}
			}

			tableData.removeColumn(pkColumn);
			tableData.addColumn(pkColumn);
			tableData.setIdColumn(pkColumn);

			inertRows.add(tableData);
		}

		if (!inertRows.isEmpty()) {
			logger.debug("inert rows size:" + inertRows.size());
			for (TableModel tableData : inertRows) {
				tableData.setTableName(tableDefinition.getTableName());
				logger.debug(tableData.toString());
				tableDataMapper.insertTableData(tableData);
			}
		}

		return inertRows;
	}

	@Transactional
	public void insertAllTableData(List<TableModel> rows) {
		logger.debug("#currentSystemName:" + Environment.getCurrentSystemName());
		for (TableModel model : rows) {
			if (model.getTableName() != null) {
				model.setTableName(model.getTableName().toUpperCase());
			}
			tableDataMapper.insertTableData(model);
		}
	}

	@Transactional
	public void insertTableData(List<TableModel> rows) {
		if (rows != null && !rows.isEmpty()) {
			for (TableModel t : rows) {
				if (t.getTableName() != null) {
					t.setTableName(t.getTableName().toUpperCase());
				}
				tableDataMapper.insertTableData(t);
			}
		}
	}

	@Transactional
	public void insertTableData(String tableName, List<Map<String, Object>> rows) {
		tableName = tableName.toUpperCase();
		TableDefinition tableDefinition = tableDefinitionService
				.getTableDefinition(tableName);
		logger.debug("tableDefinition:" + tableDefinition);
		if (tableDefinition != null) {
			if (tableDefinition.getTableName() != null) {
				tableDefinition.setTableName(tableDefinition.getTableName()
						.toUpperCase());
			}
			List<ColumnDefinition> columns = tableDefinitionService
					.getColumnDefinitionsByTableName(tableName);
			if (columns != null && !columns.isEmpty()) {
				logger.debug("columns size:" + columns.size());
				Iterator<Map<String, Object>> iterator = rows.iterator();
				while (iterator.hasNext()) {
					TableModel table = new TableModel();
					table.setTableName(tableName);
					Map<String, Object> dataMap = iterator.next();
					for (ColumnDefinition column : columns) {
						String javaType = column.getJavaType();
						String name = column.getColumnName();
						ColumnModel c = new ColumnModel();
						c.setColumnName(name);
						c.setJavaType(javaType);
						Object value = dataMap.get(name);
						if (value == null) {
							value = dataMap.get(name.toUpperCase());
						}
						if (value == null) {
							if (column.getName() != null) {
								value = dataMap.get(column.getName());
								if (value == null) {
									value = dataMap.get(column.getName()
											.toUpperCase());
								}
							}
						}
						if (value != null) {
							if ("Integer".equals(javaType)) {
								value = ParamUtils.getInt(dataMap, name);
							} else if ("Long".equals(javaType)) {
								value = ParamUtils.getLong(dataMap, name);
							} else if ("Double".equals(javaType)) {
								value = ParamUtils.getDouble(dataMap, name);
							} else if ("Date".equals(javaType)) {
								value = ParamUtils.getTimestamp(dataMap, name);
							} else if ("String".equals(javaType)) {
								value = ParamUtils.getString(dataMap, name);
							} else if ("Clob".equals(javaType)) {
								value = ParamUtils.getString(dataMap, name);
							}
							c.setValue(value);
							table.addColumn(c);
						}
					}
					tableDataMapper.insertTableData(table);
				}
			}
		}
	}

	@Transactional
	public void insertTableData(TableDefinition tableDefinition,
			List<Map<String, Object>> rows) {
		if (tableDefinition.getTableName() != null) {
			tableDefinition.setTableName(tableDefinition.getTableName()
					.toUpperCase());
		}
		List<ColumnDefinition> columns = tableDefinition.getColumns();
		if (columns != null && !columns.isEmpty()) {
			Iterator<Map<String, Object>> iterator = rows.iterator();
			while (iterator.hasNext()) {
				TableModel table = new TableModel();
				table.setTableName(tableDefinition.getTableName());
				Map<String, Object> dataMap = iterator.next();
				for (ColumnDefinition column : columns) {
					String javaType = column.getJavaType();
					String name = column.getColumnName();
					ColumnModel c = new ColumnModel();
					c.setColumnName(name);
					c.setJavaType(javaType);
					Object value = dataMap.get(name);
					if (value == null) {
						value = dataMap.get(name.toUpperCase());
					}
					if (value == null) {
						if (column.getName() != null) {
							value = dataMap.get(column.getName());
						}
					}
					if (value != null) {
						if ("Integer".equals(javaType)) {
							value = ParamUtils.getInt(dataMap, name);
						} else if ("Long".equals(javaType)) {
							value = ParamUtils.getLong(dataMap, name);
						} else if ("Double".equals(javaType)) {
							value = ParamUtils.getDouble(dataMap, name);
						} else if ("Date".equals(javaType)) {
							value = ParamUtils.getTimestamp(dataMap, name);
						} else if ("String".equals(javaType)) {
							value = ParamUtils.getString(dataMap, name);
						} else if ("Clob".equals(javaType)) {
							value = ParamUtils.getString(dataMap, name);
						}
						c.setValue(value);
						table.addColumn(c);
					}
				}
				tableDataMapper.insertTableData(table);
			}
		}
	}

	@Transactional
	public void insertTableData(TableModel model) {
		if (model.getTableName() != null) {
			model.setTableName(model.getTableName().toUpperCase());
		}
		tableDataMapper.insertTableData(model);
	}

	@Transactional
	public void saveAll(String tableName, String seqNo,
			Collection<TableModel> rows) {
		TableDefinition tableDefinition = tableDefinitionService
				.getTableDefinition(tableName);
		if (tableDefinition != null && tableDefinition.getIdColumn() != null
				&& tableDefinition.getAggregationKeys() != null) {
			this.saveAll(tableDefinition, seqNo, rows);
		}
	}

	@Transactional
	public Collection<TableModel> saveAll(TableDefinition tableDefinition,
			String seqNo, Collection<TableModel> rows) {
		logger.debug("tableDefinition=" + tableDefinition);
		logger.debug("idColumn=" + tableDefinition.getIdColumn().toString());
		if (tableDefinition.getTableName() != null) {
			tableDefinition.setTableName(tableDefinition.getTableName()
					.toUpperCase());
		}
		if (tableDefinition.isInsertOnly()) {
			return this.insertAll(tableDefinition, seqNo, rows);
		}

		Collection<String> aggregationKeys = new HashSet<String>();

		Map<String, Object> colMap = new java.util.HashMap<String, Object>();
		Map<String, Object> keyMap = new java.util.HashMap<String, Object>();
		Map<String, String> exprMap = new java.util.HashMap<String, String>();
		List<ColumnDefinition> exprColumns = new java.util.ArrayList<ColumnDefinition>();

		ColumnModel idColumn = new ColumnModel();

		ColumnDefinition idCol = tableDefinition.getIdColumn();
		if (idCol != null && idCol.getColumnName() != null) {
			idColumn.setColumnName(idCol.getColumnName());
			idColumn.setJavaType(idCol.getJavaType());
			idColumn.setValueExpression(idCol.getValueExpression());
		}

		Iterator<ColumnDefinition> iter = tableDefinition.getColumns()
				.iterator();
		while (iter.hasNext()) {
			ColumnDefinition cell = iter.next();
			if (StringUtils.isNotEmpty(cell.getValueExpression())) {
				exprMap.put(cell.getColumnName(), cell.getValueExpression());
				exprColumns.add(cell);
			}
		}

		logger.debug(exprMap);

		String keyCloumns = tableDefinition.getAggregationKeys();
		if (StringUtils.isNotEmpty(keyCloumns)) {
			List<String> cols = StringTools.split(keyCloumns);
			if (cols != null && !cols.isEmpty()) {
				StringBuffer buffer = new StringBuffer(1000);
				Iterator<TableModel> iterator = rows.iterator();
				while (iterator.hasNext()) {
					TableModel tableData = iterator.next();
					/**
					 * 使用聚合主键判断
					 */
					colMap.clear();
					buffer.delete(0, buffer.length());
					for (ColumnModel cell : tableData.getColumns()) {
						colMap.put(cell.getColumnName(), cell.getValue());
					}

					Iterator<String> it = cols.iterator();
					while (it.hasNext()) {
						Object val = colMap.get(it.next());
						if (val != null) {
							buffer.append(val.toString());
						} else {
							buffer.append("");
						}
						if (it.hasNext()) {
							buffer.append("_");
						}
					}
					String aggregationKey = buffer.toString();
					aggregationKeys.add(aggregationKey);
					tableData.setAggregationKey(aggregationKey);// 设置聚合主键值
				}

				if (aggregationKeys.size() > 0
						&& (aggregationKeys.size() % 200 == 0)) {
					TableModel model = new TableModel();
					model.setTableName(tableDefinition.getTableName());
					model.setIdColumn(idColumn);
					model.setAggregationKeys(aggregationKeys);
					List<Map<String, Object>> list = tableDataMapper
							.getTableKeyMap(model);
					if (list != null && !list.isEmpty()) {
						for (Map<String, Object> dataMap : list) {
							Object id = ParamUtils.getObject(dataMap, "id");
							if (id == null) {
								id = ParamUtils.getObject(dataMap, "ID");
							}
							String aggregationKey = ParamUtils.getString(
									dataMap, "aggregationKey");
							keyMap.put(aggregationKey, id);
						}
					}
				}
			}

			if (aggregationKeys.size() > 0) {
				TableModel model = new TableModel();
				model.setTableName(tableDefinition.getTableName());
				model.setIdColumn(idColumn);
				model.setAggregationKeys(aggregationKeys);
				List<Map<String, Object>> list = tableDataMapper
						.getTableKeyMap(model);
				if (list != null && !list.isEmpty()) {
					for (Map<String, Object> dataMap : list) {
						Object id = ParamUtils.getObject(dataMap, "id");
						if (id == null) {
							id = ParamUtils.getObject(dataMap, "ID");
						}
						String aggregationKey = ParamUtils.getString(dataMap,
								"aggregationKey");
						keyMap.put(aggregationKey, id);
					}
				}
			}

			List<TableModel> inertRows = new java.util.ArrayList<TableModel>();
			List<TableModel> updateRows = new java.util.ArrayList<TableModel>();
			logger.debug(" rows size = " + rows.size());
			Iterator<TableModel> iterator = rows.iterator();
			while (iterator.hasNext()) {
				TableModel tableData = iterator.next();
				ColumnModel myPK = tableData.getIdColumn();
				ColumnModel pkColumn = new ColumnModel();
				pkColumn.setColumnName(idColumn.getColumnName());
				pkColumn.setJavaType(idColumn.getJavaType());

				for (ColumnModel column : tableData.getColumns()) {
					colMap.put(column.getColumnName(), column.getValue());
				}

				if (keyMap.containsKey(tableData.getAggregationKey())) {
					Object id = keyMap.get(tableData.getAggregationKey());
					pkColumn.setValue(id);
					tableData.setIdColumn(pkColumn);
					tableData.removeColumn(pkColumn);
					updateRows.add(tableData);
				} else {
					ColumnModel col = new ColumnModel();
					col.setColumnName("AGGREGATIONKEY");
					col.setJavaType("String");
					col.setValue(tableData.getAggregationKey());
					tableData.removeColumn(col);
					tableData.addColumn(col);

					for (ColumnDefinition c : exprColumns) {
						ColumnModel x = new ColumnModel();
						x.setColumnName(c.getColumnName());
						x.setJavaType(c.getJavaType());
						x.setValueExpression(c.getValueExpression());
						tableData.addColumn(x);
					}

					for (ColumnModel cell : tableData.getColumns()) {
						String expr = exprMap.get(cell.getColumnName());
						if (StringUtils.isNotEmpty(expr)) {
							if (ExpressionConstants.NOW_EXPRESSION.equals(expr)
									|| ExpressionConstants.CURRENT_YYYYMMDD_EXPRESSION
											.equals(expr)) {
								if (cell.getDateValue() == null) {
									cell.setDateValue(new Date());
									cell.setValue(cell.getDateValue());
								}
							}
							if (ExpressionConstants.ID_EXPRESSION.equals(expr)) {
								if (cell.getValue() == null) {
									if (StringUtils.equals(cell.getJavaType(),
											"Integer")) {
										cell.setValue(idGenerator.nextId()
												.intValue());
									} else if (StringUtils.equals(
											cell.getJavaType(), "Long")) {
										cell.setValue(idGenerator.nextId());
									} else {
										cell.setValue(idGenerator.getNextId());
									}
								}
							}
							if (ExpressionConstants.SEQNO_EXPRESSION
									.equals(expr)) {
								cell.setValue(seqNo);
							}
							if (ExpressionConstants.UUID_EXPRESSION
									.equals(expr)) {
								cell.setValue(UUID32.getUUID());
							}
						}
					}

					if (myPK != null && myPK.getValue() != null) {
						pkColumn.setValue(myPK.getValue());
					} else {
						if (StringUtils.equals(pkColumn.getJavaType(),
								"Integer")) {
							pkColumn.setValue(idGenerator.nextId().intValue());
						} else if (StringUtils.equals(pkColumn.getJavaType(),
								"Long")) {
							pkColumn.setValue(idGenerator.nextId());
						} else {
							pkColumn.setValue(idGenerator.getNextId());
						}
					}

					tableData.removeColumn(pkColumn);
					tableData.addColumn(pkColumn);
					tableData.setIdColumn(pkColumn);

					inertRows.add(tableData);
				}
			}

			if (!inertRows.isEmpty()) {
				logger.debug("inert rows size:" + inertRows.size());
				for (TableModel tableData : inertRows) {
					tableData.setTableName(tableDefinition.getTableName());
					logger.debug(tableData.toString());
					tableDataMapper.insertTableData(tableData);
				}
			}
			if (!updateRows.isEmpty()) {
				logger.debug("update rows size:" + updateRows.size());
				for (TableModel tableData : updateRows) {
					tableData.setTableName(tableDefinition.getTableName());
					tableDataMapper.updateTableDataByPrimaryKey(tableData);
				}
			}

			return rows;

		} else {
			throw new RuntimeException("aggregationKeys is required.");
		}
	}

	@Transactional
	public void saveOrUpdate(String tableName, boolean updatable,
			List<Map<String, Object>> rows) {
		tableName = tableName.toUpperCase();
		TableDefinition tableDefinition = tableDefinitionService
				.getTableDefinition(tableName);
		if (tableDefinition != null) {
			if (tableDefinition.getTableName() != null) {
				tableDefinition.setTableName(tableDefinition.getTableName()
						.toUpperCase());
			}
			String idColumnName = null;
			List<Map<String, Object>> rowIds = null;
			List<ColumnDefinition> columns = tableDefinitionService
					.getColumnDefinitionsByTableName(tableName);
			if (columns != null && !columns.isEmpty()) {
				for (ColumnDefinition column : columns) {
					if (column.isPrimaryKey()) {
						idColumnName = column.getColumnName();
						rowIds = this.getTablePrimaryKeyMap(tableName,
								idColumnName);
						break;
					}
				}
			}

			Collection<String> keys = new HashSet<String>();

			if (rowIds != null && !rowIds.isEmpty()) {
				Iterator<Map<String, Object>> iter = rowIds.iterator();
				while (iter.hasNext()) {
					Map<String, Object> dataMap = iter.next();
					if (dataMap.get("id") != null) {
						keys.add(dataMap.get("id").toString());
					}
					if (dataMap.get("ID") != null) {
						keys.add(dataMap.get("ID").toString());
					}
				}
			}

			List<Map<String, Object>> inertRows = new java.util.ArrayList<Map<String, Object>>();
			List<Map<String, Object>> updateRows = new java.util.ArrayList<Map<String, Object>>();
			Iterator<Map<String, Object>> iterator = rows.iterator();
			while (iterator.hasNext()) {
				Map<String, Object> dataMap = iterator.next();
				Object id = dataMap.get(idColumnName);
				if (id != null) {
					if (keys.contains(id.toString())) {
						updateRows.add(dataMap);
					} else {
						inertRows.add(dataMap);
					}
				}
			}

			if (!inertRows.isEmpty()) {
				this.insertTableData(tableName, inertRows);
			}

			if (!updateRows.isEmpty()) {
				this.updateTableData(tableName, updateRows);
			}
		}

	}

	@Transactional
	public void saveOrUpdate(TableDefinition tableDefinition,
			boolean updatable, List<Map<String, Object>> rows) {
		if (tableDefinition.getTableName() != null) {
			tableDefinition.setTableName(tableDefinition.getTableName()
					.toUpperCase());
		}
		String tableName = tableDefinition.getTableName();
		String idColumnName = null;
		List<Map<String, Object>> rowIds = null;
		List<ColumnDefinition> columns = tableDefinition.getColumns();
		if (columns != null && !columns.isEmpty()) {
			for (ColumnDefinition column : columns) {
				if (column.isPrimaryKey()) {
					idColumnName = column.getColumnName();
					idColumnName = idColumnName.toUpperCase();
					rowIds = this
							.getTablePrimaryKeyMap(tableName, idColumnName);
					break;
				}
			}
		}

		Collection<String> keys = new HashSet<String>();

		if (rowIds != null && !rowIds.isEmpty()) {
			Iterator<Map<String, Object>> iter = rowIds.iterator();
			while (iter.hasNext()) {
				Map<String, Object> dataMap = iter.next();
				if (dataMap.get("id") != null) {
					keys.add(dataMap.get("id").toString());
				}
				if (dataMap.get("ID") != null) {
					keys.add(dataMap.get("ID").toString());
				}
			}
		}

		List<Map<String, Object>> inertRows = new java.util.ArrayList<Map<String, Object>>();
		List<Map<String, Object>> updateRows = new java.util.ArrayList<Map<String, Object>>();
		Iterator<Map<String, Object>> iterator = rows.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> dataMap = iterator.next();
			Object id = dataMap.get(idColumnName);
			if (id == null) {
				id = dataMap.get(idColumnName.toUpperCase());
			}
			if (id != null) {
				if (keys.contains(id.toString())) {
					updateRows.add(dataMap);
				} else {
					inertRows.add(dataMap);
				}
			}
		}

		if (!inertRows.isEmpty()) {
			this.insertTableData(tableDefinition, inertRows);
		}

		if (!updateRows.isEmpty()) {
			this.updateTableData(tableDefinition, updateRows);
		}

	}

	/**
	 * 保存JSON数组数据到指定的表
	 * 
	 * @param tableName
	 * @param rows
	 */
	@Transactional
	public void saveTableData(String tableName, JSONArray rows) {
		if (rows == null || rows.isEmpty()) {
			return;
		}
		tableName = tableName.toUpperCase();
		TableDefinition tableDefinition = tableDefinitionService
				.getTableDefinition(tableName);
		if (tableDefinition != null && tableDefinition.getIdColumn() != null) {
			if (tableDefinition.getTableName() != null) {
				tableDefinition.setTableName(tableDefinition.getTableName()
						.toUpperCase());
			}
			for (int i = 0, len = rows.size(); i < len; i++) {
				JSONObject jsonObject = rows.getJSONObject(i);
				this.saveTableData(tableName, jsonObject);
			}
		}
	}

	/**
	 * 保存JSON数据到指定的表
	 * 
	 * @param tableName
	 * @param jsonObject
	 */
	@Transactional
	public void saveTableData(String tableName, JSONObject jsonObject) {
		tableName = tableName.toUpperCase();
		TableDefinition tableDefinition = tableDefinitionService
				.getTableDefinition(tableName);
		if (tableDefinition != null && tableDefinition.getIdColumn() != null) {
			if (tableDefinition.getTableName() != null) {
				tableDefinition.setTableName(tableDefinition.getTableName()
						.toUpperCase());
			}
			ColumnDefinition idColumn = tableDefinition.getIdColumn();

			boolean insertData = true;
			Object primaryKey = null;
			if (idColumn.getColumnName() != null) {
				primaryKey = jsonObject.get(idColumn.getColumnName());
			} else if (idColumn.getName() != null) {
				primaryKey = jsonObject.get(idColumn.getName());
			}
			if (primaryKey != null) {
				insertData = false;
			}

			TableModel tableModel = new TableModel();
			tableModel.setTableName(tableName);

			List<ColumnDefinition> columns = tableDefinition.getColumns();
			if (columns != null && !columns.isEmpty()) {
				for (ColumnDefinition col : columns) {
					if (StringUtils.equalsIgnoreCase(idColumn.getColumnName(),
							col.getColumnName())) {
						continue;
					}
					String javaType = col.getJavaType();
					String columnName = col.getColumnName();
					String name = col.getName();
					ColumnModel cm = new ColumnModel();
					cm.setJavaType(javaType);
					cm.setColumnName(col.getColumnName());
					Object value = null;

					if (jsonObject.containsKey(columnName)) {
						value = jsonObject.get(columnName);
						if (StringUtils.equalsIgnoreCase("Integer", javaType)) {
							if (value instanceof Integer) {
								cm.setValue(jsonObject.getInteger(columnName));
							} else {
								cm.setValue(Integer.parseInt(value.toString()));
							}
						} else if (StringUtils.equalsIgnoreCase("Long",
								javaType)) {
							if (value instanceof Long) {
								cm.setValue(jsonObject.getLong(columnName));
							} else {
								cm.setValue(Long.parseLong(value.toString()));
							}
						} else if (StringUtils.equalsIgnoreCase("Double",
								javaType)) {
							if (value instanceof Double) {
								cm.setValue(jsonObject.getDouble(columnName));
							} else {
								cm.setValue(Double.parseDouble(value.toString()));
							}
						} else if (StringUtils.equalsIgnoreCase("Date",
								javaType)) {
							if (value instanceof Date) {
								cm.setValue(jsonObject.getDate(columnName));
							} else if (value instanceof Long) {
								Long t = jsonObject.getLong(columnName);
								cm.setValue(new Date(t));
							} else {
								cm.setValue(DateUtils.toDate(value.toString()));
							}
						} else if (StringUtils.equalsIgnoreCase("String",
								javaType)) {
							if (value instanceof String) {
								cm.setValue(jsonObject.getString(columnName));
							} else {
								cm.setValue(value.toString());
							}
						} else {
							cm.setValue(value);
						}
					} else if (jsonObject.containsKey(name)) {
						value = jsonObject.get(name);
						if (StringUtils.equalsIgnoreCase("Integer", javaType)) {
							if (value instanceof Integer) {
								cm.setValue(jsonObject.getInteger(name));
							} else {
								cm.setValue(Integer.parseInt(value.toString()));
							}
						} else if (StringUtils.equalsIgnoreCase("Long",
								javaType)) {
							if (value instanceof Long) {
								cm.setValue(jsonObject.getLong(name));
							} else {
								cm.setValue(Long.parseLong(value.toString()));
							}
						} else if (StringUtils.equalsIgnoreCase("Double",
								javaType)) {
							if (value instanceof Double) {
								cm.setValue(jsonObject.getDouble(name));
							} else {
								cm.setValue(Double.parseDouble(value.toString()));
							}
						} else if (StringUtils.equalsIgnoreCase("Date",
								javaType)) {
							if (value instanceof Date) {
								cm.setValue(jsonObject.getDate(name));
							} else if (value instanceof Long) {
								Long t = jsonObject.getLong(name);
								cm.setValue(new Date(t));
							} else {
								cm.setValue(DateUtils.toDate(value.toString()));
							}
						} else if (StringUtils.equalsIgnoreCase("String",
								javaType)) {
							if (value instanceof String) {
								cm.setValue(jsonObject.getString(name));
							} else {
								cm.setValue(value.toString());
							}
						} else {
							cm.setValue(value);
						}
					}
					tableModel.addColumn(cm);
				}
			}

			if (insertData) {
				ColumnModel idCol = new ColumnModel();
				idCol.setJavaType(idColumn.getJavaType());
				if (StringUtils.equalsIgnoreCase("Integer",
						idColumn.getJavaType())) {
					idCol.setValue(idGenerator.nextId().intValue());
				} else if (StringUtils.equalsIgnoreCase("Long",
						idColumn.getJavaType())) {
					idCol.setValue(idGenerator.nextId());
				} else {
					idCol.setValue(idGenerator.getNextId());
				}
				tableModel.setIdColumn(idCol);

				tableDataMapper.insertTableData(tableModel);
			} else {
				ColumnModel idCol = new ColumnModel();
				idCol.setJavaType(idColumn.getJavaType());
				idCol.setValue(primaryKey);
				tableModel.setIdColumn(idCol);
				tableDataMapper.updateTableDataByPrimaryKey(tableModel);
			}
		}
	}

	@javax.annotation.Resource
	public void setColumnDefinitionMapper(
			ColumnDefinitionMapper columnDefinitionMapper) {
		this.columnDefinitionMapper = columnDefinitionMapper;
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setIdMapper(IdMapper idMapper) {
		this.idMapper = idMapper;
	}

	@javax.annotation.Resource
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@javax.annotation.Resource
	public void setTableDataMapper(TableDataMapper tableDataMapper) {
		this.tableDataMapper = tableDataMapper;
	}

	@javax.annotation.Resource
	public void setTableDefinitionService(
			ITableDefinitionService tableDefinitionService) {
		this.tableDefinitionService = tableDefinitionService;
	}

	@javax.annotation.Resource
	public void setTablePageMapper(TablePageMapper tablePageMapper) {
		this.tablePageMapper = tablePageMapper;
	}

	@Transactional
	public void updateAllDbids(List<Dbid> rows) {
		if (rows != null && !rows.isEmpty()) {
			Map<String, Long> idMap = new java.util.HashMap<String, Long>();
			List<Dbid> list = this.getAllDbids();
			for (Dbid id : rows) {
				if (StringUtils.isNumeric(id.getValue())) {
					idMap.put(id.getName(), Long.parseLong(id.getValue()));
				}
			}
			if (list != null && !list.isEmpty()) {
				for (Dbid dbid : list) {
					if (idMap.containsKey(dbid.getName())
							&& StringUtils.isNumeric(dbid.getValue())) {
						if (idMap.get(dbid.getName()) > Long.parseLong(dbid
								.getValue())) {
							dbid.setValue(idMap.get(dbid.getName()).toString());
							dbid.setVersion(dbid.getVersion() + 1);
							idMapper.updateNextDbId(dbid);
						}
					}
				}
			}
		}
	}

	@Transactional
	public void updateTableData(List<TableModel> rows) {
		if (rows != null && !rows.isEmpty()) {
			for (TableModel t : rows) {
				if (t.getTableName() != null) {
					t.setTableName(t.getTableName().toUpperCase());
				}
				tableDataMapper.updateTableDataByPrimaryKey(t);
			}
		}
	}

	@Transactional
	public void updateTableData(String tableName, List<Map<String, Object>> rows) {
		tableName = tableName.toUpperCase();
		TableDefinition tableDefinition = tableDefinitionService
				.getTableDefinition(tableName);
		if (tableDefinition != null) {
			if (tableDefinition.getTableName() != null) {
				tableDefinition.setTableName(tableDefinition.getTableName()
						.toUpperCase());
			}
			List<ColumnDefinition> columns = tableDefinitionService
					.getColumnDefinitionsByTableName(tableName);
			if (columns != null && !columns.isEmpty()) {
				Iterator<Map<String, Object>> iterator = rows.iterator();
				while (iterator.hasNext()) {
					TableModel table = new TableModel();
					table.setTableName(tableName);
					Map<String, Object> dataMap = iterator.next();
					for (ColumnDefinition column : columns) {
						String javaType = column.getJavaType();
						String name = column.getColumnName();
						ColumnModel c = new ColumnModel();
						c.setColumnName(name);
						c.setJavaType(javaType);
						Object value = dataMap.get(name);
						if (value == null) {
							value = dataMap.get(name.toUpperCase());
						}
						if (value == null) {
							if (column.getName() != null) {
								value = dataMap.get(column.getName());
								if (value == null) {
									value = dataMap.get(column.getName()
											.toUpperCase());
								}
							}
						}
						if (value != null) {
							if ("Integer".equals(javaType)) {
								value = ParamUtils.getInt(dataMap, name);
							} else if ("Long".equals(javaType)) {
								value = ParamUtils.getLong(dataMap, name);
							} else if ("Double".equals(javaType)) {
								value = ParamUtils.getDouble(dataMap, name);
							} else if ("Date".equals(javaType)) {
								value = ParamUtils.getTimestamp(dataMap, name);
							} else if ("String".equals(javaType)) {
								value = ParamUtils.getString(dataMap, name);
							} else if ("Clob".equals(javaType)) {
								value = ParamUtils.getString(dataMap, name);
							}
							c.setValue(value);
							if (column.isPrimaryKey()) {
								table.setIdColumn(c);
							} else {
								table.addColumn(c);
							}
						}
					}
					tableDataMapper.updateTableDataByPrimaryKey(table);
				}
			}
		}
	}

	@Transactional
	public void updateTableData(TableDefinition tableDefinition,
			List<Map<String, Object>> rows) {
		if (tableDefinition.getTableName() != null) {
			tableDefinition.setTableName(tableDefinition.getTableName()
					.toUpperCase());
		}
		List<ColumnDefinition> columns = tableDefinition.getColumns();
		if (columns != null && !columns.isEmpty()) {
			Iterator<Map<String, Object>> iterator = rows.iterator();
			while (iterator.hasNext()) {
				TableModel table = new TableModel();
				table.setTableName(tableDefinition.getTableName());
				Map<String, Object> dataMap = iterator.next();
				for (ColumnDefinition column : columns) {
					String javaType = column.getJavaType();
					String name = column.getColumnName();
					ColumnModel c = new ColumnModel();
					c.setColumnName(name);
					c.setJavaType(javaType);
					Object value = dataMap.get(name);
					if (value == null) {
						value = dataMap.get(name.toUpperCase());
					}
					if (value == null) {
						if (column.getName() != null) {
							value = dataMap.get(column.getName());
						}
					}
					if (value != null) {
						if ("Integer".equals(javaType)) {
							value = ParamUtils.getInt(dataMap, name);
						} else if ("Long".equals(javaType)) {
							value = ParamUtils.getLong(dataMap, name);
						} else if ("Double".equals(javaType)) {
							value = ParamUtils.getDouble(dataMap, name);
						} else if ("Date".equals(javaType)) {
							value = ParamUtils.getTimestamp(dataMap, name);
						} else if ("String".equals(javaType)) {
							value = ParamUtils.getString(dataMap, name);
						} else if ("Clob".equals(javaType)) {
							value = ParamUtils.getString(dataMap, name);
						}
						c.setValue(value);
						if (column.isPrimaryKey()) {
							table.setIdColumn(c);
						} else {
							table.addColumn(c);
						}
					}
				}
				tableDataMapper.updateTableDataByPrimaryKey(table);
			}
		}
	}

	@Transactional
	public void updateTableData(TableModel model) {
		if (model.getTableName() != null) {
			model.setTableName(model.getTableName().toUpperCase());
		}
		tableDataMapper.updateTableDataByPrimaryKey(model);
	}

}