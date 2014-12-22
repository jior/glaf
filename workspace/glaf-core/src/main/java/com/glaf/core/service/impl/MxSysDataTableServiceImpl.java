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

import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.Map.Entry;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.id.*;
import com.glaf.core.base.BaseTree;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.base.TreeModel;
import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.dao.*;
import com.glaf.core.mapper.*;
import com.glaf.core.domain.*;
import com.glaf.core.query.*;
import com.glaf.core.service.ISysDataTableService;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.util.CaseInsensitiveHashMap;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.SearchFilter;
import com.glaf.core.util.Tools;
import com.glaf.core.xml.XmlReader;

@Service("sysDataTableService")
@Transactional(readOnly = true)
public class MxSysDataTableServiceImpl implements ISysDataTableService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected static Configuration conf = BaseConfiguration.create();

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysDataTableMapper sysDataTableMapper;

	protected SysDataFieldMapper sysDataFieldMapper;

	protected TableDataMapper tableDataMapper;

	protected TablePageMapper tablePageMapper;

	protected ITableDataService tableDataService;

	public MxSysDataTableServiceImpl() {

	}

	public int count(SysDataTableQuery query) {
		return sysDataTableMapper.getSysDataTableCount(query);
	}

	/**
	 * 删除字段信息
	 * 
	 * @param fieldId
	 */
	@Transactional
	public void deleteDataFieldById(String fieldId) {
		sysDataFieldMapper.deleteSysDataFieldById(fieldId);
	}

	public SysDataField getDataFieldById(String fieldId) {
		return sysDataFieldMapper.getSysDataFieldById(fieldId);
	}

	public int getDataFieldCountByTablename(String tableName) {
		SysDataFieldQuery query = new SysDataFieldQuery();
		query.tablename(tableName);
		return sysDataFieldMapper.getSysDataFieldCount(query);
	}

	public List<SysDataField> getDataFieldsByTablename(String tableName) {
		SysDataFieldQuery query = new SysDataFieldQuery();
		query.tablename(tableName);
		return sysDataFieldMapper.getSysDataFields(query);
	}

	public SysDataTable getDataTableById(String datatableId) {
		if (datatableId == null) {
			return null;
		}
		SysDataTable sysDataTable = sysDataTableMapper
				.getSysDataTableById(datatableId);
		if (sysDataTable != null) {
			List<SysDataField> fields = sysDataFieldMapper
					.getSysDataFieldsByTablename(sysDataTable.getTablename());
			sysDataTable.setFields(fields);
		}
		return sysDataTable;
	}

	/**
	 * 根据tableName获取一条记录
	 * 
	 * @return
	 */
	public SysDataTable getDataTableByName(String tableName) {
		SysDataTable sysDataTable = sysDataTableMapper
				.getSysDataTableByTable(tableName);
		if (sysDataTable != null) {
			List<SysDataField> fields = sysDataFieldMapper
					.getSysDataFieldsByTablename(sysDataTable.getTablename());
			sysDataTable.setFields(fields);
		}
		return sysDataTable;
	}

	/**
	 * 根据tableName获取一条业务数据
	 * 
	 * @param tableName
	 *            表名
	 * @param businessKey
	 *            业务主键
	 * 
	 * @return
	 */
	public SysDataTable getDataTableWithData(String tableName,
			String businessKey) {
		SysDataTable dataTable = this.getDataTableByName(tableName);
		if (dataTable != null && dataTable.getFields() != null) {
			TableModel tableModel = new TableModel();
			tableModel.setTableName(tableName);
			for (SysDataField field : dataTable.getFields()) {
				if (StringUtils.equals(field.getPrimaryKey(), "Y")) {
					ColumnModel idColumn = new ColumnModel();
					idColumn.setColumnName(field.getColumnName());
					if (StringUtils.equals("Integer", field.getDataType())) {
						idColumn.setValue(Integer.parseInt(businessKey));
					} else if (StringUtils.equals("Long", field.getDataType())) {
						idColumn.setValue(Long.parseLong(businessKey));
					} else {
						idColumn.setValue(businessKey);
					}
					tableModel.setIdColumn(idColumn);
					break;
				}
			}
			if (tableModel.getIdColumn() != null) {
				Map<String, Object> dataMap = tableDataMapper
						.getTableDataByPrimaryKey(tableModel);
				Map<String, Object> newDataMap = new CaseInsensitiveHashMap();
				if (dataMap != null && !dataMap.isEmpty()) {
					Iterator<Entry<String, Object>> iterator = dataMap
							.entrySet().iterator();
					while (iterator.hasNext()) {
						Entry<String, Object> entry = iterator.next();
						String name = entry.getKey();
						Object value = entry.getValue();
						newDataMap.put(name, value);
						newDataMap.put(name.toLowerCase(), value);
					}
				}
				for (SysDataField field : dataTable.getFields()) {
					if (field.getColumnName() != null) {
						field.setValue(newDataMap.get(field.getColumnName()
								.toLowerCase()));
					}
				}
			}
		}
		return dataTable;
	}

	public int getDataTableCountByQueryCriteria(SysDataTableQuery query) {
		return sysDataTableMapper.getSysDataTableCount(query);
	}

	public List<SysDataTable> getDataTablesByQueryCriteria(int start,
			int pageSize, SysDataTableQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SysDataTable> rows = sqlSessionTemplate.selectList(
				"getSysDataTables", query, rowBounds);
		return rows;
	}

	/**
	 * 获取一页数据
	 * 
	 * @param start
	 * @param pageSize
	 * @param query
	 * @return
	 */
	public JSONObject getPageTableData(int start, int pageSize,
			SysDataTableQuery query) {
		JSONObject result = new JSONObject();
		SysDataTable dataTable = this.getDataTableByName(query.getTablename());
		if (dataTable == null) {
			return result;
		}

		TableModel tableModel = new TableModel();
		tableModel.setTableName(query.getTablename());
		tableModel.setDataRequest(query.getDataRequest());
		int total = tableDataMapper.getTableCountByConditions(tableModel);
		if (total > 0) {
			result.put("total", total);
			Map<String, SysDataField> fieldMap = new HashMap<String, SysDataField>();
			if (dataTable.getFields() != null) {
				List<SysDataField> fields = dataTable.getFields();
				for (SysDataField field : fields) {
					if (field.getName() != null) {
						fieldMap.put(field.getColumnName(), field);
						fieldMap.put(field.getColumnName().toLowerCase(), field);
					}
				}
			}

			if (StringUtils.isNotEmpty(dataTable.getSortColumnName())) {
				String orderBy = " order by E." + dataTable.getSortColumnName();
				if (StringUtils.equals(dataTable.getSortOrder(), "desc")) {
					orderBy = orderBy + " desc";
				} else {
					orderBy = orderBy + " asc";
				}
				tableModel.setOrderBy(orderBy);
			}

			RowBounds rowBounds = new RowBounds(start, pageSize);
			List<Map<String, Object>> list = sqlSessionTemplate.selectList(
					"getTableDataByConditions", tableModel, rowBounds);
			if (list != null && !list.isEmpty()) {
				JSONArray array = new JSONArray();
				for (Map<String, Object> rowMap : list) {
					JSONObject json = new JSONObject();
					Set<Entry<String, Object>> entrySet = rowMap.entrySet();
					for (Entry<String, Object> entry : entrySet) {
						String key = entry.getKey();
						Object value = entry.getValue();
						if (value != null) {
							if (fieldMap.get(key.toLowerCase()) != null) {
								SysDataField field = fieldMap.get(key
										.toLowerCase());
								if ("Date".equals(field.getDataType())) {
									if (value instanceof Date) {
										Date date = (Date) value;
										json.put(field.getName(),
												DateUtils.getDate(date));
										json.put(field.getName() + "_date",
												DateUtils.getDate(date));
										json.put(field.getName() + "_datetime",
												DateUtils.getDateTime(date));
									} else if (value instanceof Long) {
										Date date = new Date((Long) value);
										json.put(field.getName(),
												DateUtils.getDate(date));
										json.put(field.getName() + "_date",
												DateUtils.getDate(date));
										json.put(field.getName() + "_datetime",
												DateUtils.getDateTime(date));
									} else {
										json.put(field.getName(), value);
									}
								} else {
									json.put(field.getName(), value);
								}
							} else {
								json.put(key.toLowerCase(), value);
							}
						}
					}
					array.add(json);
				}
				result.put("rows", array);
			}
		}
		return result;
	}

	public SysDataField getSysDataField(String id) {
		if (id == null) {
			return null;
		}
		SysDataField sysDataField = sysDataFieldMapper.getSysDataFieldById(id);
		return sysDataField;
	}

	/**
	 * 获取某个表满足条件的记录总数
	 * 
	 * @param query
	 * @return
	 */
	public int getTableDataCount(SysDataTableQuery query) {
		TableModel q = new TableModel();
		q.setTableName(query.getTablename());
		q.setDataRequest(query.getDataRequest());

		SysDataTable dataTable = this.getDataTableByName(query.getTablename());
		if (dataTable != null && dataTable.getFields() != null) {
			List<SysDataField> fields = dataTable.getFields();
			Map<String, String> nameMap = new HashMap<String, String>();
			Map<String, Object> params = query.getParameters();
			for (SysDataField field : fields) {
				ColumnModel column = new ColumnModel();
				column.setColumnName(field.getColumnName());
				column.setJavaType(field.getDataType());

				nameMap.put(field.getColumnName().toLowerCase(),
						field.getName());
				q.setTableName(field.getTablename());
				Object value = params.get(field.getName());
				if (value == null) {
					if (StringUtils.equals(field.getDataType(), "String")) {
						value = params.get(field.getName() + "Like");
						if (value != null) {
							column.setOperator(SearchFilter.LIKE);
							column.setValue(value);
						}
						value = params.get(field.getName() + "NotLike");
						if (value != null) {
							column.setOperator(SearchFilter.NOT_LIKE);
							column.setValue(value);
						}
					}

					if (StringUtils.equals(field.getDataType(), "Integer")
							|| StringUtils.equals(field.getDataType(), "Long")) {
						value = params.get(field.getName() + "GreaterThan");
						if (value != null) {
							column.setOperator(SearchFilter.GREATER_THAN_OR_EQUAL);
							column.setValue(value);
						}

						value = params.get(field.getName() + "LessThan");
						if (value != null) {
							column.setOperator(SearchFilter.LESS_THAN_OR_EQUAL);
							column.setValue(value);
						}
					}
				}
				if (column.getValue() != null) {
					q.addColumn(column);
				}
			}
		}

		int total = tableDataMapper.getTableCountByConditions(q);
		return total;
	}

	public List<TreeModel> getTreeModels(String datatableId, Object parentId) {
		List<TreeModel> treeModels = new ArrayList<TreeModel>();
		SysDataTable dataTable = this.getDataTableById(datatableId);
		if (dataTable != null && dataTable.getFields() != null) {
			List<SysDataField> fields = dataTable.getFields();
			SysDataField idField = null;
			SysDataField treeField = null;
			String treeId = null;
			Map<String, String> nameMap = new HashMap<String, String>();
			for (SysDataField field : fields) {
				if ("Y".equals(field.getPrimaryKey())) {
					idField = field;
				}
				if (StringUtils.equalsIgnoreCase(field.getName(), "treeId")) {
					treeField = field;
				}
				nameMap.put(field.getColumnName().toLowerCase(),
						field.getName());
			}
			if (idField != null) {
				TableModel tableModel = new TableModel();
				tableModel.setTableName(idField.getTablename());
				ColumnModel idColumn = new ColumnModel();
				idColumn.setColumnName(idField.getColumnName());
				idColumn.setJavaType(idField.getDataType());
				idColumn.setValue(parentId);
				if (StringUtils.equals(idField.getDataType(), "Integer")) {
					idColumn.setValue(parentId);
				} else if (StringUtils.equals(idField.getDataType(), "Long")) {
					idColumn.setValue(parentId);
				}
				tableModel.setIdColumn(idColumn);

				Map<String, Object> dataMap = tableDataMapper
						.getTableDataByPrimaryKey(tableModel);
				Map<String, Object> newDataMap = new CaseInsensitiveHashMap();
				if (dataMap != null && !dataMap.isEmpty()) {
					Iterator<Entry<String, Object>> iterator = dataMap
							.entrySet().iterator();
					while (iterator.hasNext()) {
						Entry<String, Object> entry = iterator.next();
						String name = entry.getKey();
						Object value = entry.getValue();
						newDataMap.put(name, value);
						newDataMap.put(name.toLowerCase(), value);
					}
				}
				if (treeField != null) {
					treeId = (String) newDataMap.get(treeField.getColumnName()
							.toLowerCase());
				}
				if (treeId != null) {
					// 通过一条SQL取出查询结果
					StringBuffer buffer = new StringBuffer();
					buffer.append(" select * from ")
							.append(treeField.getTablename()).append(" where ")
							.append(treeField.getColumnName())
							.append(" like '").append(treeId).append("%'");
					Map<String, Object> queryMap = new HashMap<String, Object>();
					queryMap.put("queryString", buffer.toString());
					List<Map<String, Object>> list = tablePageMapper
							.getSqlQueryList(queryMap);

					if (list != null && !list.isEmpty()) {
						for (Map<String, Object> rowMap : list) {
							Map<String, Object> newRowMap = new HashMap<String, Object>();
							Iterator<Entry<String, Object>> iterator = rowMap
									.entrySet().iterator();
							while (iterator.hasNext()) {
								Entry<String, Object> entry = iterator.next();
								String key = entry.getKey();
								Object value = entry.getValue();
								String name = nameMap.get(key.toLowerCase());
								newRowMap.put(name, value);
							}

							TreeModel tree = new BaseTree();
							tree.setDataMap(newRowMap);
							Tools.populate(tree, newRowMap);
							tree.setName(ParamUtils
									.getString(newRowMap, "name"));
							tree.setCode(ParamUtils
									.getString(newRowMap, "code"));
							tree.setIcon(ParamUtils
									.getString(newRowMap, "icon"));
							tree.setIconCls(ParamUtils.getString(newRowMap,
									"iconCls"));
							tree.setUrl(ParamUtils.getString(newRowMap, "url"));
							tree.setDescription(ParamUtils.getString(newRowMap,
									"description"));
							tree.setTreeId(ParamUtils.getString(newRowMap,
									"treeId"));
							tree.setId(ParamUtils.getLong(newRowMap, "id"));
							tree.setParentId(ParamUtils.getLong(newRowMap,
									"parentId"));
							tree.setSortNo(ParamUtils.getInt(newRowMap,
									"ordinal"));
							tree.setLevel(ParamUtils.getInt(newRowMap, "level"));
							tree.setLocked(ParamUtils.getInt(newRowMap,
									"locked"));
							treeModels.add(tree);
						}
					}
				} else {
					// 递归调用取出查询结果
				}
			}
		}
		return treeModels;
	}

	public List<SysDataTable> list(SysDataTableQuery query) {
		List<SysDataTable> list = sysDataTableMapper.getSysDataTables(query);
		return list;
	}

	@Transactional
	public void saveData(String datatableId, Map<String, Object> dataMap) {
		SysDataTable dataTable = this.getDataTableById(datatableId);
		Map<String, Object> newData = new HashMap<String, Object>();
		Set<Entry<String, Object>> entrySet = dataMap.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value != null) {
				newData.put(key, value);
				newData.put(key.toUpperCase(), value);
			}
		}
		SysDataField idField = null;
		List<SysDataField> fields = dataTable.getFields();
		if (fields != null && !fields.isEmpty()) {
			for (SysDataField field : fields) {
				if (StringUtils.equalsIgnoreCase("1", field.getPrimaryKey())
						|| StringUtils.equalsIgnoreCase("Y",
								field.getPrimaryKey())
						|| StringUtils.equalsIgnoreCase("true",
								field.getPrimaryKey())) {
					idField = field;
					break;
				}
			}
		}

		if (idField == null) {
			throw new java.lang.RuntimeException("primary key not found.");
		}

		Object idValue = newData.get(idField.getColumnName().toUpperCase());
		if (idValue == null) {
			idValue = newData.get(idField.getName().toUpperCase());
		}

		TableModel row = new TableModel();
		row.setTableName(dataTable.getTablename());

		ColumnModel col01 = new ColumnModel();
		col01.setColumnName(idField.getColumnName());

		if (idValue == null) {
			if (StringUtils.equalsIgnoreCase(idField.getDataType(), "Integer")) {
				col01.setJavaType("Long");
				Long id = idGenerator.nextId();
				col01.setIntValue(id.intValue());
				col01.setValue(Integer.valueOf(id.intValue()));
			} else if (StringUtils.equalsIgnoreCase(idField.getDataType(),
					"Long")) {
				col01.setJavaType("Long");
				Long id = idGenerator.nextId();
				col01.setLongValue(id);
				col01.setValue(id);
			} else {
				col01.setJavaType("String");
				String id = idGenerator.getNextId();
				col01.setStringValue(id);
				col01.setValue(id);
			}
			row.setIdColumn(col01);
			row.addColumn(col01);
		} else {
			if (StringUtils.equalsIgnoreCase(idField.getDataType(), "Integer")) {
				col01.setJavaType("Long");
				String id = idValue.toString();
				col01.setIntValue(Integer.parseInt(id));
				col01.setValue(col01.getIntValue());
			} else if (StringUtils.equalsIgnoreCase(idField.getDataType(),
					"Long")) {
				col01.setJavaType("Long");
				String id = idValue.toString();
				col01.setLongValue(Long.parseLong(id));
				col01.setValue(col01.getLongValue());
			} else {
				col01.setJavaType("String");
				String id = idValue.toString();
				col01.setStringValue(id);
				col01.setValue(id);
			}
			row.setIdColumn(col01);
			row.addColumn(col01);
		}

		if (fields != null && !fields.isEmpty()) {
			for (SysDataField field : fields) {
				if (StringUtils.equalsIgnoreCase(idField.getColumnName(),
						field.getColumnName())) {
					continue;
				}
				String name = field.getColumnName().toUpperCase();
				String javaType = field.getDataType();
				ColumnModel c = new ColumnModel();
				c.setColumnName(field.getColumnName());
				c.setJavaType(javaType);
				Object value = newData.get(name);
				if (value != null) {
					if ("Integer".equals(javaType)) {
						value = ParamUtils.getInt(newData, name);
					} else if ("Long".equals(javaType)) {
						value = ParamUtils.getLong(newData, name);
					} else if ("Double".equals(javaType)) {
						value = ParamUtils.getDouble(newData, name);
					} else if ("Date".equals(javaType)) {
						value = ParamUtils.getTimestamp(newData, name);
					} else if ("String".equals(javaType)) {
						value = ParamUtils.getString(newData, name);
					} else if ("Clob".equals(javaType)) {
						value = ParamUtils.getString(newData, name);
					}
					c.setValue(value);
					row.addColumn(c);
				} else {
					name = field.getName().toUpperCase();
					value = newData.get(name);
					if (value != null) {
						if ("Integer".equals(javaType)) {
							value = ParamUtils.getInt(newData, name);
						} else if ("Long".equals(javaType)) {
							value = ParamUtils.getLong(newData, name);
						} else if ("Double".equals(javaType)) {
							value = ParamUtils.getDouble(newData, name);
						} else if ("Date".equals(javaType)) {
							value = ParamUtils.getTimestamp(newData, name);
						} else if ("String".equals(javaType)) {
							value = ParamUtils.getString(newData, name);
						} else if ("Clob".equals(javaType)) {
							value = ParamUtils.getString(newData, name);
						}
						c.setValue(value);
						row.addColumn(c);
					}
				}
			}
		}
		if (idValue == null) {
			tableDataMapper.insertTableData(row);
		} else {
			tableDataMapper.updateTableDataByPrimaryKey(row);
		}
	}

	@Transactional
	public void saveDataField(SysDataField dataField) {
		if (dataField.getColumnName() == null
				|| dataField.getDataType() == null) {
			return;
		}

		if (!DBUtils.isTableColumn(dataField.getColumnName())) {
			logger.debug(dataField.toJsonObject().toJSONString());
			throw new RuntimeException("column name '"
					+ dataField.getColumnName() + "' is not iegal");
		}
		String id = dataField.getId();
		if (id == null) {
			id = dataField.getTablename() + "_" + dataField.getColumnName();
		}
		id = id.toLowerCase();
		if (dataField.getLength() == 0
				&& StringUtils.equals(dataField.getDataType(), "String")) {
			dataField.setLength(250);
		}
		SysDataField model = this.getDataFieldById(id);
		if (model == null) {
			dataField.setId(id);
			dataField.setCreateTime(new Date());
			dataField.setColumnName(dataField.getColumnName().toUpperCase());
			if (dataField.getServiceKey() == null) {
				dataField.setServiceKey(dataField.getTablename().toLowerCase());
			}
			sysDataFieldMapper.insertSysDataField(dataField);
		} else {
			dataField.setId(id);
			dataField.setUpdateTime(new Date());
			sysDataFieldMapper.updateSysDataField(dataField);
		}
	}

	@Transactional
	public void saveDataFields(List<SysDataField> fields) {
		for (SysDataField dataField : fields) {
			this.saveDataField(dataField);
		}
	}

	@Transactional
	public void saveDataList(String datatableId,
			List<Map<String, Object>> dataList) {
		SysDataTable dataTable = this.getDataTableById(datatableId);
		Map<String, Object> newData = new HashMap<String, Object>();

		SysDataField idField = null;
		List<SysDataField> fields = dataTable.getFields();
		if (fields != null && !fields.isEmpty()) {
			for (SysDataField field : fields) {
				if (StringUtils.equalsIgnoreCase("1", field.getPrimaryKey())
						|| StringUtils.equalsIgnoreCase("y",
								field.getPrimaryKey())
						|| StringUtils.equalsIgnoreCase("true",
								field.getPrimaryKey())) {
					idField = field;
					break;
				}
			}
		}

		if (idField == null) {
			throw new java.lang.RuntimeException("primary key not found.");
		}

		for (Map<String, Object> dataMap : dataList) {
			newData.clear();
			Set<Entry<String, Object>> entrySet = dataMap.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (value != null) {
					newData.put(key, value);
					newData.put(key.toUpperCase(), value);
				}
			}

			Object idValue = newData.get(idField.getColumnName().toUpperCase());
			if (idValue == null) {
				idValue = newData.get(idField.getName().toUpperCase());
			}

			TableModel row = new TableModel();
			row.setTableName(dataTable.getTablename());

			ColumnModel col01 = new ColumnModel();
			col01.setColumnName(idField.getColumnName());

			if (idValue == null) {
				if (StringUtils.equalsIgnoreCase(idField.getDataType(),
						"Integer")) {
					col01.setJavaType("Long");
					Long id = idGenerator.nextId();
					col01.setIntValue(id.intValue());
					col01.setValue(Integer.valueOf(id.intValue()));
				} else if (StringUtils.equalsIgnoreCase(idField.getDataType(),
						"Long")) {
					col01.setJavaType("Long");
					Long id = idGenerator.nextId();
					col01.setLongValue(id);
					col01.setValue(id);
				} else {
					col01.setJavaType("String");
					String id = idGenerator.getNextId();
					col01.setStringValue(id);
					col01.setValue(id);
				}
				row.setIdColumn(col01);
			} else {
				if (StringUtils.equalsIgnoreCase(idField.getDataType(),
						"Integer")) {
					col01.setJavaType("Long");
					String id = idValue.toString();
					col01.setIntValue(Integer.parseInt(id));
					col01.setValue(col01.getIntValue());
				} else if (StringUtils.equalsIgnoreCase(idField.getDataType(),
						"Long")) {
					col01.setJavaType("Long");
					String id = idValue.toString();
					col01.setLongValue(Long.parseLong(id));
					col01.setValue(col01.getLongValue());
				} else {
					col01.setJavaType("String");
					String id = idValue.toString();
					col01.setStringValue(id);
					col01.setValue(id);
				}
				row.setIdColumn(col01);
			}

			if (fields != null && !fields.isEmpty()) {
				for (SysDataField field : fields) {
					if (StringUtils.equalsIgnoreCase(idField.getColumnName(),
							field.getColumnName())) {
						continue;
					}
					String name = field.getColumnName().toUpperCase();
					String javaType = field.getDataType();
					ColumnModel c = new ColumnModel();
					c.setColumnName(field.getColumnName());
					c.setJavaType(javaType);
					Object value = newData.get(name);
					if (value != null) {
						if ("Integer".equals(javaType)) {
							value = ParamUtils.getInt(newData, name);
						} else if ("Long".equals(javaType)) {
							value = ParamUtils.getLong(newData, name);
						} else if ("Double".equals(javaType)) {
							value = ParamUtils.getDouble(newData, name);
						} else if ("Date".equals(javaType)) {
							value = ParamUtils.getTimestamp(newData, name);
						} else if ("String".equals(javaType)) {
							value = ParamUtils.getString(newData, name);
						} else if ("Clob".equals(javaType)) {
							value = ParamUtils.getString(newData, name);
						}
						c.setValue(value);
						row.addColumn(c);
					} else {
						name = field.getName().toUpperCase();
						value = newData.get(name);
						if (value != null) {
							if ("Integer".equals(javaType)) {
								value = ParamUtils.getInt(newData, name);
							} else if ("Long".equals(javaType)) {
								value = ParamUtils.getLong(newData, name);
							} else if ("Double".equals(javaType)) {
								value = ParamUtils.getDouble(newData, name);
							} else if ("Date".equals(javaType)) {
								value = ParamUtils.getTimestamp(newData, name);
							} else if ("String".equals(javaType)) {
								value = ParamUtils.getString(newData, name);
							} else if ("Clob".equals(javaType)) {
								value = ParamUtils.getString(newData, name);
							}
							c.setValue(value);
							row.addColumn(c);
						}
					}
				}
			}
			if (idValue == null) {
				tableDataMapper.insertTableData(row);
			} else {
				tableDataMapper.updateTableDataByPrimaryKey(row);
			}
		}
	}

	@Transactional
	public void saveDataTable(SysDataTable dataTable) {
		SysDataTable model = sysDataTableMapper
				.getSysDataTableByTable(dataTable.getTablename());
		if (model == null) {
			dataTable.setId(DigestUtils.md5Hex(dataTable.getTablename()));
			dataTable.setCreateTime(new Date());
			dataTable.setTablename(dataTable.getTablename().toLowerCase());
			if (dataTable.getServiceKey() == null) {
				dataTable.setServiceKey(dataTable.getTablename().toLowerCase());
			}
			sysDataTableMapper.insertSysDataTable(dataTable);
		} else {
			dataTable.setId(model.getId());
			dataTable.setUpdateTime(new Date());
			dataTable.setTablename(dataTable.getTablename().toLowerCase());
			sysDataTableMapper.updateSysDataTable(dataTable);
		}
		if (dataTable.getFields() != null && !dataTable.getFields().isEmpty()) {
			for (SysDataField field : dataTable.getFields()) {
				field.setTablename(dataTable.getTablename().toLowerCase());
				field.setServiceKey(dataTable.getServiceKey());
				this.saveDataField(field);
			}
		} else {
			String tpl = conf.get("sys_table_template",
					"/conf/templates/mapping/Global.mapping.xml");
			String filename = SystemProperties.getConfigRootPath() + tpl;
			XmlReader reader = new XmlReader();
			try {
				File file = new File(filename);
				if (file.exists() && file.isFile()
						&& file.getAbsolutePath().endsWith(".mapping.xml")) {
					SysDataTable bean = reader
							.getSysDataTable(new FileInputStream(filename));
					if (bean.getFields() != null && !bean.getFields().isEmpty()) {
						for (SysDataField field : bean.getFields()) {
							field.setTablename(dataTable.getTablename()
									.toLowerCase());
							field.setServiceKey(dataTable.getServiceKey());
							this.saveDataField(field);
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	protected void saveJson(String tableName, JSONObject jsonData,
			SysDataField idField, List<SysDataField> fields) {
		if (jsonData != null && idField != null) {
			TableModel tableModel = new TableModel();
			tableModel.setTableName(tableName);
			ColumnModel idColumn = new ColumnModel();
			idColumn.setColumnName(idField.getColumnName());
			idColumn.setJavaType(idField.getDataType());
			Object idValue = jsonData
					.get(idField.getColumnName().toLowerCase());
			if (idValue == null) {
				idValue = jsonData.get(idField.getName().toLowerCase());
			}

			boolean isUpdate = false;
			if (idValue != null) {
				isUpdate = true;
			} else {
				if (StringUtils.equalsIgnoreCase(idField.getDataType(),
						"Integer")) {
					idValue = idGenerator.nextId();
				} else if (StringUtils.equalsIgnoreCase(idField.getDataType(),
						"Long")) {
					idValue = idGenerator.nextId();
				} else {
					idValue = idGenerator.getNextId();
				}
			}
			idColumn.setValue(idValue);
			tableModel.addColumn(idColumn);
			tableModel.setIdColumn(idColumn);

			for (SysDataField f : fields) {
				ColumnModel column = new ColumnModel();
				column.setColumnName(f.getColumnName());
				column.setJavaType(f.getDataType());

				Object value = jsonData.get(f.getColumnName().toLowerCase());
				if (value == null) {
					value = jsonData.get(f.getName().toLowerCase());
				}
				if (value == null) {
					value = jsonData.get(f.getName());
				}

				column.setValue(value);
				tableModel.addColumn(column);
			}
			if (isUpdate) {
				tableDataService.updateTableData(tableModel);
			} else {
				tableDataService.insertTableData(tableModel);
			}
		}
	}

	/**
	 * 保存数据
	 * 
	 * @param datatableId
	 * @param jsonObject
	 */
	@Transactional
	public void saveJsonData(String datatableId, JSONObject jsonObject) {
		SysDataTable dataTable = this.getDataTableById(datatableId);
		if (jsonObject != null && dataTable != null
				&& dataTable.getFields() != null) {
			List<SysDataField> fields = dataTable.getFields();

			if (!fields.isEmpty()) {
				Map<String, SysDataField> tableDefs = new HashMap<String, SysDataField>();
				Map<String, List<SysDataField>> tableFields = new HashMap<String, List<SysDataField>>();
				for (SysDataField field : fields) {
					if (field.getTablename() != null
							&& "Y".equals(field.getPrimaryKey())) {
						tableDefs
								.put(field.getTablename().toLowerCase(), field);
					}
					if (field.getTablename() != null
							&& field.getColumnName() != null) {
						List<SysDataField> tfields = tableFields.get(field
								.getTablename().toLowerCase());
						if (tfields == null) {
							tfields = new ArrayList<SysDataField>();
							tableFields.put(field.getTablename().toLowerCase(),
									tfields);
						}
						tfields.add(field);
					}
				}

				logger.debug("1--------------------------------------------");
				logger.debug("tableFields:" + tableFields.size());

				Iterator<Entry<String, List<SysDataField>>> iterator = tableFields
						.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, List<SysDataField>> entry = iterator.next();
					String tableName = entry.getKey();
					SysDataField idField = tableDefs.get(tableName);
					logger.debug("-------------------xxxx-------------------------");
					Object object = jsonObject.get(tableName.toLowerCase());
					if (object != null && object instanceof JSONArray) {
						logger.debug("2--------------------------------------------");
						JSONArray array = jsonObject.getJSONArray(tableName
								.toLowerCase());
						for (int i = 0, len = array.size(); i < len; i++) {
							JSONObject jsonData = array.getJSONObject(i);
							this.saveJson(tableName, jsonData, idField,
									entry.getValue());
						}
					} else {
						logger.debug("3--------------------------------------------");
						JSONObject jsonData = jsonObject
								.getJSONObject(tableName.toLowerCase());
						this.saveJson(tableName, jsonData, idField,
								entry.getValue());
					}
				}
			}
		}
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
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource
	public void setSysDataFieldMapper(SysDataFieldMapper sysDataFieldMapper) {
		this.sysDataFieldMapper = sysDataFieldMapper;
	}

	@javax.annotation.Resource
	public void setSysDataTableMapper(SysDataTableMapper sysDataTableMapper) {
		this.sysDataTableMapper = sysDataTableMapper;
	}

	@javax.annotation.Resource
	public void setTableDataMapper(TableDataMapper tableDataMapper) {
		this.tableDataMapper = tableDataMapper;
	}

	@javax.annotation.Resource
	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

	@javax.annotation.Resource
	public void setTablePageMapper(TablePageMapper tablePageMapper) {
		this.tablePageMapper = tablePageMapper;
	}

}
