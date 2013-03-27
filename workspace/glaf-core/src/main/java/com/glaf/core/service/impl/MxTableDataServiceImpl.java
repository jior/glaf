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

import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.domain.*;
import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.mapper.*;
import com.glaf.core.query.TablePageQuery;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.util.Constants;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.StringTools;

@Service("tableDataService")
@Transactional
public class MxTableDataServiceImpl implements ITableDataService {
	protected final static Log logger = LogFactory
			.getLog(MxTableDataServiceImpl.class);

	protected IdGenerator idGenerator;

	protected EntityDAO entityDAO;

	protected SqlSession sqlSession;

	protected ColumnDefinitionMapper columnDefinitionMapper;

	protected TableDataMapper tableDataMapper;

	protected TablePageMapper tablePageMapper;

	protected ITableDefinitionService tableDefinitionService;

	public MxTableDataServiceImpl() {

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

	@Transactional(readOnly = true)
	public List<Map<String, Object>> getTableDataByPrimaryKey(Object id) {
		return tableDataMapper.getTableDataByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> getTablePrimaryKeyMap(String tableName,
			String columnName) {
		TableModel tableModel = new TableModel();
		ColumnModel idColumn = new ColumnModel();
		idColumn.setColumnName(columnName);
		tableModel.setTableName(tableName);
		tableModel.setIdColumn(idColumn);
		return tableDataMapper.getTablePrimaryKeyMap(tableModel);
	}

	@Transactional
	public void insertTableData(List<TableModel> rows) {
		if (rows != null && !rows.isEmpty()) {
			for (TableModel t : rows) {
				tableDataMapper.insertTableData(t);
			}
		}
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
			tableDataMapper.deleteTableData(model);
		}
	}

	@Transactional
	public void insertTableData(String tableName, List<Map<String, Object>> rows) {
		TableDefinition tableDefinition = tableDefinitionService
				.getTableDefinition(tableName);
		if (tableDefinition != null) {
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
	public void insertTableData(TableModel model) {
		tableDataMapper.insertTableData(model);
	}

	@Transactional
	public void saveAll(String tableName, Collection<TableModel> rows) {
		TableDefinition tableDefinition = tableDefinitionService
				.getTableDefinition(tableName);
		if (tableDefinition != null && tableDefinition.getIdColumn() != null
				&& tableDefinition.getAggregationKeys() != null) {
			this.saveAll(tableDefinition, rows);
		}
	}

	@Transactional
	public void saveAll(TableDefinition tableDefinition,
			Collection<TableModel> rows) {
		logger.debug("tableDefinition=" + tableDefinition);
		logger.debug("idColumn=" + tableDefinition.getIdColumn().toString());

		Collection<String> aggregationKeys = new HashSet<String>();

		Map<String, Object> colMap = new HashMap<String, Object>();
		Map<String, Object> keyMap = new HashMap<String, Object>();
		Map<String, String> exprMap = new HashMap<String, String>();
		List<ColumnDefinition> exprColumns = new ArrayList<ColumnDefinition>();

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
				StringBuffer buffer = new StringBuffer();
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
						String aggregationKey = ParamUtils.getString(dataMap,
								"aggregationKey");
						keyMap.put(aggregationKey, id);
					}
				}
			}

			List<TableModel> inertRows = new ArrayList<TableModel>();
			List<TableModel> updateRows = new ArrayList<TableModel>();
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
							// logger.debug(cell.getColumnName() + "=" + expr);
							if (Constants.CURRENT_YYYYMMDD_EXPRESSION
									.equals(expr)) {
								if (cell.getDateValue() == null) {
									cell.setDateValue(new Date());
									cell.setValue(cell.getDateValue());
								}
							}
							if (Constants.ID_EXPRESSION.equals(expr)) {
								if (cell.getValue() == null) {
									if (StringUtils.equals(cell.getJavaType(),
											"Integer")) {
										cell.setValue(idGenerator.nextId()
												.intValue());
										logger.debug("------------int--------------");
									} else if (StringUtils.equals(
											cell.getJavaType(), "Long")) {
										cell.setValue(idGenerator.nextId());
									} else {
										cell.setValue(idGenerator.getNextId());
									}
								}
							}
						}
					}

					if (myPK != null && myPK.getValue() != null) {
						pkColumn.setValue(myPK.getValue());
					} else {
						if (StringUtils.equals(pkColumn.getJavaType(),
								"Integer")) {
							pkColumn.setValue(idGenerator.nextId().intValue());
							logger.debug("------------int--------------");
						} else if (StringUtils.equals(pkColumn.getJavaType(),
								"Long")) {
							pkColumn.setValue(idGenerator.nextId());
						} else {
							pkColumn.setValue(idGenerator.getNextId());
						}
					}

					tableData.removeColumn(pkColumn);
					tableData.addColumn(pkColumn);

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

		} else {
			throw new RuntimeException("aggregationKeys is required.");
		}

	}

	@Transactional
	public void saveOrUpdate(String tableName, boolean updatable,
			List<Map<String, Object>> rows) {
		TableDefinition tableDefinition = tableDefinitionService
				.getTableDefinition(tableName);
		if (tableDefinition != null) {
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
				}
			}

			List<Map<String, Object>> inertRows = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> updateRows = new ArrayList<Map<String, Object>>();
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

	@Resource
	public void setColumnDefinitionMapper(
			ColumnDefinitionMapper columnDefinitionMapper) {
		this.columnDefinitionMapper = columnDefinitionMapper;
	}

	@Resource
	@Qualifier("myBatisEntityDAO")
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@Resource
	@Qualifier("myBatisDbIdGenerator")
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@Resource
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Resource
	public void setTableDataMapper(TableDataMapper tableDataMapper) {
		this.tableDataMapper = tableDataMapper;
	}

	@Resource
	public void setTableDefinitionService(
			ITableDefinitionService tableDefinitionService) {
		this.tableDefinitionService = tableDefinitionService;
	}

	@Resource
	public void setTablePageMapper(TablePageMapper tablePageMapper) {
		this.tablePageMapper = tablePageMapper;
	}

	@Transactional
	public void updateTableData(List<TableModel> rows) {
		if (rows != null && !rows.isEmpty()) {
			for (TableModel t : rows) {
				tableDataMapper.updateTableDataByPrimaryKey(t);
			}
		}
	}

	@Transactional
	public void updateTableData(String tableName, List<Map<String, Object>> rows) {
		TableDefinition tableDefinition = tableDefinitionService
				.getTableDefinition(tableName);
		if (tableDefinition != null) {
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
	public void updateTableData(TableModel model) {
		tableDataMapper.updateTableDataByPrimaryKey(model);
	}

}