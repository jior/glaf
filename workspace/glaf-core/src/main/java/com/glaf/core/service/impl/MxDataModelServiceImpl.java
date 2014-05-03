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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.DataModel;
import com.glaf.core.base.DataModelEntity;
import com.glaf.core.base.TableModel;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.mapper.ColumnDefinitionMapper;
import com.glaf.core.mapper.DataModelMapper;
import com.glaf.core.mapper.IdMapper;
import com.glaf.core.mapper.TableDataMapper;
import com.glaf.core.mapper.TablePageMapper;
import com.glaf.core.query.DataModelQuery;
import com.glaf.core.service.DataModelService;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.util.ParamUtils;

@Service("dataModelService")
@Transactional
public class MxDataModelServiceImpl implements DataModelService {
	protected final static Log logger = LogFactory
			.getLog(MxDataModelServiceImpl.class);

	protected ColumnDefinitionMapper columnDefinitionMapper;

	protected DataModelMapper dataModelMapper;

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected IdMapper idMapper;

	protected SqlSession sqlSession;

	protected TableDataMapper tableDataMapper;

	protected ITableDataService tableDataService;

	protected ITableDefinitionService tableDefinitionService;

	protected TablePageMapper tablePageMapper;

	public MxDataModelServiceImpl() {

	}

	@Transactional
	public void deleteAll(String tableName, Collection<String> businessKeys) {
		List<ColumnDefinition> cols = tableDefinitionService
				.getColumnDefinitionsByTableName(tableName);
		if (cols != null && !cols.isEmpty()) {
			for (ColumnDefinition col : cols) {
				if (StringUtils.equalsIgnoreCase(col.getColumnName(),
						"BUSINESSKEY_")) {
					TableModel tableModel = new TableModel();
					tableModel.setTableName(tableName);
					ColumnModel cm = new ColumnModel();
					cm.setColumnName("BUSINESSKEY_");
					cm.setJavaType(col.getJavaType());
					cm.setCollectionValues(businessKeys);
					tableModel.addColumn(cm);
					tableDataService.deleteTableData(tableModel);
					break;
				}
				if (StringUtils.equalsIgnoreCase(col.getColumnName(), "ID_")) {
					TableModel tableModel = new TableModel();
					tableModel.setTableName(tableName);
					ColumnModel cm = new ColumnModel();
					cm.setColumnName("ID_");
					cm.setJavaType(col.getJavaType());
					cm.setCollectionValues(businessKeys);
					tableModel.addColumn(cm);
					tableDataService.deleteTableData(tableModel);
					break;
				}
			}
		}
	}

	@Transactional
	public void deleteAllById(String tableName, Collection<Long> rowIds) {
		List<ColumnDefinition> cols = tableDefinitionService
				.getColumnDefinitionsByTableName(tableName);
		if (cols != null && !cols.isEmpty()) {
			for (ColumnDefinition col : cols) {
				if (StringUtils.equalsIgnoreCase(col.getColumnName(), "ID_")) {
					TableModel tableModel = new TableModel();
					tableModel.setTableName(tableName);
					ColumnModel cm = new ColumnModel();
					cm.setColumnName("ID_");
					cm.setJavaType(col.getJavaType());
					cm.setCollectionValues(rowIds);
					tableModel.addColumn(cm);
					tableDataService.deleteTableData(tableModel);
					break;
				}
			}
		}
	}

	public DataModel getDataModel(String tableName, Long id) {
		List<ColumnDefinition> cols = tableDefinitionService
				.getColumnDefinitionsByTableName(tableName);
		if (cols != null && !cols.isEmpty()) {
			for (ColumnDefinition col : cols) {
				if (StringUtils.equalsIgnoreCase(col.getColumnName(), "ID_")) {
					TableModel tableModel = new TableModel();
					tableModel.setTableName(tableName);
					ColumnModel cm = new ColumnModel();
					cm.setColumnName("ID_");
					cm.setJavaType(col.getJavaType());
					cm.setLongValue(id);
					cm.setValue(id);
					tableModel.setIdColumn(cm);
					Map<String, Object> dataMap = tableDataMapper
							.getTableDataByPrimaryKey(tableModel);
					if (dataMap != null && !dataMap.isEmpty()) {
						DataModelEntity model = this.populate(dataMap);
						return model;
					}
				}
			}
		}

		return null;
	}

	public DataModel getDataModelByBusinessKey(String tableName,
			String businessKey) {
		List<ColumnDefinition> cols = tableDefinitionService
				.getColumnDefinitionsByTableName(tableName);
		if (cols != null && !cols.isEmpty()) {
			for (ColumnDefinition col : cols) {
				if (StringUtils.equalsIgnoreCase(col.getColumnName(),
						"BUSINESSKEY_")) {
					TableModel tableModel = new TableModel();
					tableModel.setTableName(tableName);
					ColumnModel cm = new ColumnModel();
					cm.setColumnName("BUSINESSKEY_");
					cm.setJavaType(col.getJavaType());
					cm.setStringValue(businessKey);
					cm.setValue(businessKey);
					tableModel.setIdColumn(cm);
					Map<String, Object> dataMap = tableDataMapper
							.getTableDataByPrimaryKey(tableModel);
					if (dataMap != null && !dataMap.isEmpty()) {
						DataModelEntity model = this.populate(dataMap);
						return model;
					}
				}
			}
		}

		return null;
	}

	public DataModel getDataModelByProcessInstanceId(String tableName,
			String processInstanceId) {
		List<ColumnDefinition> cols = tableDefinitionService
				.getColumnDefinitionsByTableName(tableName);
		if (cols != null && !cols.isEmpty()) {
			for (ColumnDefinition col : cols) {
				if (StringUtils.equalsIgnoreCase(col.getColumnName(),
						"PROCESSINSTANCEID_")) {
					TableModel tableModel = new TableModel();
					tableModel.setTableName(tableName);
					ColumnModel cm = new ColumnModel();
					cm.setColumnName("PROCESSINSTANCEID_");
					cm.setJavaType(col.getJavaType());
					cm.setStringValue(processInstanceId);
					cm.setValue(processInstanceId);
					tableModel.setIdColumn(cm);
					Map<String, Object> dataMap = tableDataMapper
							.getTableDataByPrimaryKey(tableModel);
					if (dataMap != null && !dataMap.isEmpty()) {
						DataModelEntity model = this.populate(dataMap);
						return model;
					}
				}
			}
		}

		return null;
	}

	public int getDataModelCount(DataModelQuery query) {
		return dataModelMapper.getTableCountByDataModelQuery(query);
	}

	@Transactional
	public void insert(DataModel dataModel) {
		TableModel tableModel = new TableModel();
		JSONObject jsonObject = dataModel.toJsonObject();
		String tableName = dataModel.getTableName();
		tableModel.setTableName(tableName);
		List<ColumnDefinition> cols = tableDefinitionService
				.getColumnDefinitionsByTableName(tableName);
		if (cols != null && !cols.isEmpty()) {
			for (ColumnDefinition col : cols) {
				ColumnModel cm = new ColumnModel();
				cm.setColumnName(col.getColumnName());
				cm.setName(col.getName());
				cm.setJavaType(col.getJavaType());
				if (col.isPrimaryKey()) {
					if (dataModel.getId() == null || dataModel.getId() == 0) {
						cm.setValue(idGenerator.nextId(tableName));
					}
				} else {
					if ("Integer".equals(col.getJavaType())) {
						cm.setValue(jsonObject.getInteger(col.getName()));
					} else if ("Long".equals(col.getJavaType())) {
						cm.setValue(jsonObject.getLong(col.getName()));
					} else if ("Double".equals(col.getJavaType())) {
						cm.setValue(jsonObject.getDouble(col.getName()));
					} else if ("Date".equals(col.getJavaType())) {
						cm.setValue(jsonObject.getDate(col.getName()));
					} else if ("String".equals(col.getJavaType())) {
						cm.setValue(jsonObject.getString(col.getName()));
					} else {
						cm.setValue(jsonObject.get(col.getName()));
					}
				}
				tableModel.addColumn(cm);
			}
			tableDataMapper.insertTableData(tableModel);
		}
	}

	public List<DataModel> list(int pageNo, int pageSize, DataModelQuery query) {
		int begin = (pageNo - 1) * pageSize;
		if (begin < 0) {
			begin = 0;
		}
		RowBounds rowBounds = new RowBounds(begin, pageSize);
		List<Map<String, Object>> rows = sqlSession.selectList(
				"getTableDataByDataModelQuery", query, rowBounds);
		List<DataModel> dataModels = new java.util.ArrayList<DataModel>();
		if (rows != null && !rows.isEmpty()) {
			for (Map<String, Object> dataMap : rows) {
				DataModelEntity model = this.populate(dataMap);
				dataModels.add(model);
			}
		}
		return dataModels;
	}

	public DataModelEntity populate(Map<String, Object> rowMap) {
		Map<String, Object> dataMap = new java.util.HashMap<String, Object>();
		if (rowMap != null && rowMap.size() > 0) {
			Iterator<?> iterator = rowMap.keySet().iterator();
			while (iterator.hasNext()) {
				String name = (String) iterator.next();
				Object value = rowMap.get(name);
				dataMap.put(name.toUpperCase(), value);
			}
		}
		DataModelEntity dataModel = new DataModelEntity();
		dataModel.setDataMap(dataMap);
		dataModel.setBusinessKey(ParamUtils.getString(dataMap, "BUSINESSKEY_"));
		dataModel.setCreateBy(ParamUtils.getString(dataMap, "CREATEBY_"));
		dataModel.setCreateDate(ParamUtils.getDate(dataMap, "CREATEDATE_"));
		dataModel.setDeleteFlag(ParamUtils.getInt(dataMap, "DELETEFLAG_"));
		dataModel.setFallbackFlag(ParamUtils
				.getString(dataMap, "FALLBACKFLAG_"));
		dataModel.setFormName(ParamUtils.getString(dataMap, "FORMNAME_"));
		dataModel.setId(ParamUtils.getLongValue(dataMap, "ID_"));
		dataModel.setLevel(ParamUtils.getInt(dataMap, "LEVEL_"));
		dataModel.setListNo(ParamUtils.getInt(dataMap, "LISTNO_"));
		dataModel.setLocked(ParamUtils.getInt(dataMap, "LOCKED_"));
		dataModel.setName(ParamUtils.getString(dataMap, "NAME_"));
		dataModel.setObjectId(ParamUtils.getString(dataMap, "OBJECTID_"));
		dataModel.setObjectValue(ParamUtils.getString(dataMap, "OBJECTVALUE_"));
		dataModel.setParentId(ParamUtils.getLongValue(dataMap, "PARENTID_"));
		dataModel.setProcessInstanceId(ParamUtils.getString(dataMap,
				"PROCESSINSTANCEID_"));
		dataModel.setProcessName(ParamUtils.getString(dataMap, "PROCESSNAME_"));
		dataModel.setServiceKey(ParamUtils.getString(dataMap, "SERVICEKEY_"));
		dataModel.setSignForFlag(ParamUtils.getString(dataMap, "SIGNFORFLAG_"));
		dataModel.setStatus(ParamUtils.getInt(dataMap, "STATUS_"));
		dataModel.setSubject(ParamUtils.getString(dataMap, "SUBJECT_"));
		dataModel.setTreeId(ParamUtils.getString(dataMap, "TREEID_"));
		dataModel.setTypeId(ParamUtils.getString(dataMap, "TYPEID_"));
		dataModel.setUpdateBy(ParamUtils.getString(dataMap, "UPDATEBY_"));
		dataModel.setUpdateDate(ParamUtils.getDate(dataMap, "UPDATEDATE_"));
		dataModel.setWfStatus(ParamUtils.getInt(dataMap, "WFSTATUS_"));

		return dataModel;
	}

	@javax.annotation.Resource
	public void setColumnDefinitionMapper(
			ColumnDefinitionMapper columnDefinitionMapper) {
		this.columnDefinitionMapper = columnDefinitionMapper;
	}

	@javax.annotation.Resource
	public void setDataModelMapper(DataModelMapper dataModelMapper) {
		this.dataModelMapper = dataModelMapper;
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
	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
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
	public void update(DataModel dataModel) {
		TableModel tableModel = new TableModel();
		JSONObject jsonObject = dataModel.toJsonObject();
		String tableName = dataModel.getTableName();
		tableModel.setTableName(tableName);
		List<ColumnDefinition> cols = tableDefinitionService
				.getColumnDefinitionsByTableName(tableName);
		if (cols != null && !cols.isEmpty()) {
			for (ColumnDefinition col : cols) {
				ColumnModel cm = new ColumnModel();
				cm.setColumnName(col.getColumnName());
				cm.setName(col.getName());
				cm.setJavaType(col.getJavaType());
				if (col.isPrimaryKey()) {
					if ("Integer".equals(col.getJavaType())) {
						cm.setValue(jsonObject.getInteger(col.getName()));
						tableModel.setIdColumn(cm);
					} else if ("Long".equals(col.getJavaType())) {
						cm.setValue(jsonObject.getLong(col.getName()));
						tableModel.setIdColumn(cm);
					} else if ("String".equals(col.getJavaType())) {
						cm.setValue(jsonObject.getString(col.getName()));
						tableModel.setIdColumn(cm);
					}
				} else {
					if ("Integer".equals(col.getJavaType())) {
						cm.setValue(jsonObject.getInteger(col.getName()));
					} else if ("Long".equals(col.getJavaType())) {
						cm.setValue(jsonObject.getLong(col.getName()));
					} else if ("Double".equals(col.getJavaType())) {
						cm.setValue(jsonObject.getDouble(col.getName()));
					} else if ("Date".equals(col.getJavaType())) {
						cm.setValue(jsonObject.getDate(col.getName()));
					} else if ("String".equals(col.getJavaType())) {
						cm.setValue(jsonObject.getString(col.getName()));
					} else {
						cm.setValue(jsonObject.get(col.getName()));
					}
					tableModel.addColumn(cm);
				}
			}
			tableDataMapper.updateTableDataByPrimaryKey(tableModel);
		}
	}

}