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
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.dao.*;
import com.glaf.core.mapper.*;
import com.glaf.core.domain.*;
import com.glaf.core.query.*;
import com.glaf.core.service.ISysDataTableService;
import com.glaf.core.util.ParamUtils;

@Service("sysDataTableService")
@Transactional(readOnly = true)
public class MxSysDataTableServiceImpl implements ISysDataTableService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysDataTableMapper sysDataTableMapper;

	protected SysDataFieldMapper sysDataFieldMapper;

	protected TableDataMapper tableDataMapper;

	public MxSysDataTableServiceImpl() {

	}

	public int count(SysDataTableQuery query) {
		return sysDataTableMapper.getSysDataTableCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			sysDataTableMapper.deleteSysDataTableById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				sysDataTableMapper.deleteSysDataTableById(id);
			}
		}
	}

	public SysDataTable getDataTable(String id) {
		if (id == null) {
			return null;
		}
		SysDataTable sysDataTable = sysDataTableMapper.getSysDataTableById(id);
		if (sysDataTable != null) {
			List<SysDataField> fields = sysDataFieldMapper
					.getSysDataFieldsByTablename(sysDataTable.getTablename());
			sysDataTable.setFields(fields);
		}
		return sysDataTable;
	}

	/**
	 * 根据服务ID获取一条记录
	 * 
	 * @return
	 */
	public SysDataTable getDataTableByServiceKey(String serviceKey) {
		SysDataTableQuery query = new SysDataTableQuery();
		query.serviceKey(serviceKey);
		SysDataTable sysDataTable = null;
		List<SysDataTable> list = sysDataTableMapper.getSysDataTables(query);
		if (list != null && !list.isEmpty()) {
			sysDataTable = list.get(0);
			List<SysDataField> fields = sysDataFieldMapper
					.getSysDataFieldsByTablename(sysDataTable.getTablename());
			sysDataTable.setFields(fields);
		}
		return sysDataTable;
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

	public SysDataField getSysDataField(String id) {
		if (id == null) {
			return null;
		}
		SysDataField sysDataField = sysDataFieldMapper.getSysDataFieldById(id);
		return sysDataField;
	}

	public List<SysDataTable> list(SysDataTableQuery query) {
		List<SysDataTable> list = sysDataTableMapper.getSysDataTables(query);
		return list;
	}

	@Transactional
	public void saveData(String serviceKey, Map<String, Object> dataMap) {
		SysDataTable dataTable = this.getDataTableByServiceKey(serviceKey);
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
		String id = dataField.getTablename() + "_" + dataField.getColumnName();
		if (StringUtils.isEmpty(dataField.getId())) {
			dataField.setId(id);
			dataField.setCreateTime(new Date());
			sysDataFieldMapper.insertSysDataField(dataField);
		} else {
			if (this.getSysDataField(id) == null) {
				dataField.setCreateTime(new Date());
				sysDataFieldMapper.insertSysDataField(dataField);
			} else {
				sysDataFieldMapper.updateSysDataField(dataField);
			}
		}
	}

	@Transactional
	public void saveDataFields(List<SysDataField> fields) {
		for (SysDataField dataField : fields) {
			String id = dataField.getTablename() + "_"
					+ dataField.getColumnName();
			if (StringUtils.isEmpty(dataField.getId())) {
				dataField.setId(id);
				dataField.setCreateTime(new Date());
				sysDataFieldMapper.insertSysDataField(dataField);
			} else {
				if (this.getSysDataField(id) == null) {
					dataField.setCreateTime(new Date());
					sysDataFieldMapper.insertSysDataField(dataField);
				} else {
					sysDataFieldMapper.updateSysDataField(dataField);
				}
			}
		}
	}

	@Transactional
	public void saveDataList(String serviceKey,
			List<Map<String, Object>> dataList) {
		SysDataTable dataTable = this.getDataTableByServiceKey(serviceKey);
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
		if (StringUtils.isEmpty(dataTable.getId())) {
			dataTable.setId(idGenerator.getNextId());
			sysDataTableMapper.insertSysDataTable(dataTable);
		} else {
			sysDataTableMapper.updateSysDataTable(dataTable);
		}
		if (dataTable.getFields() != null && !dataTable.getFields().isEmpty()) {
			for (SysDataField field : dataTable.getFields()) {
				this.saveDataField(field);
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

}
