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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.domain.InputDefinition;
import com.glaf.core.domain.SysDataTable;
import com.glaf.core.domain.SysExtension;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.mapper.SysDataTableMapper;
import com.glaf.core.mapper.TableDataMapper;
import com.glaf.core.query.SysDataTableQuery;
import com.glaf.core.service.IExtensionService;
import com.glaf.core.service.ISysDataTableService;
import com.glaf.core.service.ISystemParamService;
import com.glaf.core.service.ITableDataService;

@Service("extensionService")
@Transactional(readOnly = true)
public class MxExtensionServiceImpl implements IExtensionService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysDataTableMapper sysDataTableMapper;

	protected TableDataMapper tableDataMapper;

	protected ISysDataTableService sysDataTableService;

	protected ISystemParamService systemParamService;

	protected ITableDataService tableDataService;

	public Map<String, Object> getDataMap(String serviceKey, String businessKey) {
		SysDataTable sysDataTable = sysDataTableService
				.getDataTableByServiceKey(serviceKey);
		List<InputDefinition> inputs = this.getExtensionFields(serviceKey);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String tableName = sysDataTable.getTablename();
		if (StringUtils.isEmpty(tableName)) {
			tableName = "SYS_EXTENSION";
		}
		Map<String, InputDefinition> inputMap = new HashMap<String, InputDefinition>();
		if (inputs != null && !inputs.isEmpty()) {
			for (InputDefinition m : inputs) {
				inputMap.put(m.getKeyName(), m);
			}
		}
		TableModel tableModel = new TableModel();
		tableModel.setTableName(tableName);

		ColumnModel keyColumn = new ColumnModel();
		keyColumn.setColumnName("SERVICE_KEY");
		keyColumn.setJavaType("String");
		keyColumn.setValue(serviceKey);
		keyColumn.setStringValue(serviceKey);
		tableModel.addColumn(keyColumn);

		ColumnModel keyColumn2 = new ColumnModel();
		keyColumn2.setColumnName("BUSINESS_KEY");
		keyColumn2.setJavaType("String");
		keyColumn2.setValue(businessKey);
		keyColumn2.setStringValue(businessKey);
		tableModel.addColumn(keyColumn2);

		List<SysExtension> list = tableDataMapper
				.getExtensionDataByConditions(tableModel);
		if (list != null && !list.isEmpty()) {
			for (SysExtension e : list) {
				String typeName = "String";
				InputDefinition inputDefinition = inputMap.get(e.getKeyName());
				if (inputDefinition != null) {
					typeName = inputDefinition.getJavaType();
				}
				if (StringUtils.equals(typeName, "Integer")) {
					dataMap.put(e.getKeyName(), e.getIntVal());
				} else if (StringUtils.equals(typeName, "Long")) {
					dataMap.put(e.getKeyName(), e.getLongVal());
				} else if (StringUtils.equals(typeName, "Double")) {
					dataMap.put(e.getKeyName(), e.getDoubleVal());
				} else if (StringUtils.equals(typeName, "Date")) {
					dataMap.put(e.getKeyName(), e.getDateVal());
				} else {
					dataMap.put(e.getKeyName(), e.getStringVal());
				}
			}
		}

		return dataMap;
	}

	public List<InputDefinition> getExtensionFields(String serviceKey) {
		return systemParamService.getInputDefinitions(serviceKey);
	}

	public List<SysDataTable> getExtensionModules() {
		SysDataTableQuery query = new SysDataTableQuery();
		query.type(1);
		return sysDataTableService.list(query);
	}

	@Transactional
	public void saveAll(String serviceKey, String businessKey,
			Map<String, Object> dataMap) {
		SysDataTable sysDataTable = sysDataTableService
				.getDataTableByServiceKey(serviceKey);
		List<InputDefinition> inputs = this.getExtensionFields(serviceKey);
		Map<String, InputDefinition> inputMap = new HashMap<String, InputDefinition>();
		if (inputs != null && !inputs.isEmpty()) {
			for (InputDefinition m : inputs) {
				inputMap.put(m.getKeyName(), m);
			}
		}
		String tableName = sysDataTable.getTablename();
		if (StringUtils.isEmpty(tableName)) {
			tableName = "SYS_EXTENSION";
		}
		TableModel tableModel = new TableModel();
		tableModel.setTableName(tableName);

		ColumnModel keyColumn = new ColumnModel();
		keyColumn.setColumnName("SERVICE_KEY");
		keyColumn.setJavaType("String");
		keyColumn.setValue(serviceKey);
		keyColumn.setStringValue(serviceKey);
		tableModel.addColumn(keyColumn);

		ColumnModel keyColumn2 = new ColumnModel();
		keyColumn2.setColumnName("BUSINESS_KEY");
		keyColumn2.setJavaType("String");
		keyColumn2.setValue(businessKey);
		keyColumn2.setStringValue(businessKey);
		tableModel.addColumn(keyColumn2);

		tableDataService.deleteTableData(tableModel);

		if (dataMap != null && !dataMap.isEmpty()) {
			Set<Entry<String, Object>> entrySet = dataMap.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (value != null) {
					TableModel row = new TableModel();
					row.setTableName(tableName);
					long id = idGenerator.nextId();
					ColumnModel col01 = new ColumnModel();
					col01.setColumnName("ID");
					col01.setJavaType("Long");
					col01.setValue(id);
					col01.setLongValue(id);
					row.setIdColumn(col01);

					ColumnModel col02 = new ColumnModel();
					col02.setColumnName("BUSINESS_KEY");
					col02.setJavaType("String");
					col02.setValue(businessKey);
					col02.setStringValue(businessKey);
					row.addColumn(col02);

					ColumnModel col03 = new ColumnModel();
					col03.setColumnName("SERVICE_KEY");
					col03.setJavaType("String");
					col03.setValue(serviceKey);
					col03.setStringValue(serviceKey);
					row.addColumn(col03);

					InputDefinition inputDefinition = inputMap.get(key);
					if (inputDefinition != null) {
						ColumnModel col04 = new ColumnModel();
						col04.setColumnName("TYPE_CD");
						col04.setJavaType("String");
						col04.setValue(inputDefinition.getTypeCd());
						col04.setStringValue(inputDefinition.getTypeCd());
						row.addColumn(col04);
					}

					ColumnModel col05 = new ColumnModel();
					col05.setColumnName("KEY_NAME");
					col05.setJavaType("String");
					col05.setValue(key);
					col05.setStringValue(key);
					row.addColumn(col05);

					ColumnModel col06 = new ColumnModel();
					col06.setColumnName("STRING_VAL");
					col06.setJavaType("String");
					col06.setValue(value.toString());
					col06.setStringValue(value.toString());
					row.addColumn(col06);

					if (value instanceof Integer) {
						Integer val = (Integer) value;
						ColumnModel col = new ColumnModel();
						col.setColumnName("INT_VAL");
						col.setJavaType("Integer");
						col.setValue(val);
						col.setIntValue(val);
						row.addColumn(col);
					} else if (value instanceof Long) {
						Long val = (Long) value;
						ColumnModel col = new ColumnModel();
						col.setColumnName("LONG_VAL");
						col.setJavaType("Long");
						col.setValue(val);
						col.setLongValue(val);
						row.addColumn(col);
					} else if (value instanceof Double) {
						Double val = (Double) value;
						ColumnModel col = new ColumnModel();
						col.setColumnName("DOUBLE_VAL");
						col.setJavaType("Double");
						col.setValue(val);
						col.setDoubleValue(val);
						row.addColumn(col);
					} else if (value instanceof Date) {
						Date val = (Date) value;
						ColumnModel col = new ColumnModel();
						col.setColumnName("DATE_VAL");
						col.setJavaType("Date");
						col.setValue(val);
						col.setDateValue(val);
						row.addColumn(col);
					} else if (value instanceof String) {
						String val = (String) value;
						ColumnModel col = new ColumnModel();
						col.setColumnName("TEXT_VAL");
						col.setJavaType("String");
						col.setValue(val);
						col.setStringValue(val);
						row.addColumn(col);
					}

					tableDataService.insertTableData(row);
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
	public void setSysDataTableMapper(SysDataTableMapper sysDataTableMapper) {
		this.sysDataTableMapper = sysDataTableMapper;
	}

	@javax.annotation.Resource
	public void setSysDataTableService(ISysDataTableService sysDataTableService) {
		this.sysDataTableService = sysDataTableService;
	}

	@javax.annotation.Resource
	public void setSystemParamService(ISystemParamService systemParamService) {
		this.systemParamService = systemParamService;
	}

	@javax.annotation.Resource
	public void setTableDataMapper(TableDataMapper tableDataMapper) {
		this.tableDataMapper = tableDataMapper;
	}

	@javax.annotation.Resource
	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

}
