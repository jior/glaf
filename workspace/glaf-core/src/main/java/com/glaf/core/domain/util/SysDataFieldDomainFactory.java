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

package com.glaf.core.domain.util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.FilterDescriptor;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.DBUtils;

/**
 * 
 * 实体数据工厂类
 *
 */
public class SysDataFieldDomainFactory {

	public static final String TABLENAME = "SYS_DATA_FIELD";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID_");
		columnMap.put("serviceKey", "SERVICEKEY_");
		columnMap.put("tablename", "TABLENAME_");
		columnMap.put("columnName", "COLUMNNAME_");
		columnMap.put("name", "NAME_");
		columnMap.put("title", "TITLE_");
		columnMap.put("frmType", "FRMTYPE_");
		columnMap.put("dataType", "DATATYPE_");
		columnMap.put("length", "LENGTH_");
		columnMap.put("listWeigth", "LISTWEIGTH_");
		columnMap.put("primaryKey", "PRIMARYKEY_");
		columnMap.put("systemFlag", "SYSTEMFLAG_");
		columnMap.put("inputType", "INPUTTYPE_");
		columnMap.put("displayType", "DISPLAYTYPE_");
		columnMap.put("importType", "IMPORTTYPE_");
		columnMap.put("formatter", "FORMATTER_");
		columnMap.put("searchable", "SEARCHABLE_");
		columnMap.put("editable", "EDITABLE_");
		columnMap.put("updatable", "UPDATEABLE_");
		columnMap.put("formula", "FORMULA_");
		columnMap.put("mask", "MASK_");
		columnMap.put("queryId", "QUERYID_");
		columnMap.put("valueField", "VALUEFIELD_");
		columnMap.put("textField", "TEXTFIELD_");
		columnMap.put("validType", "VALIDTYPE_");
		columnMap.put("required", "REQUIRED_");
		columnMap.put("dataItemId", "DATAITEMID_");
		columnMap.put("initValue", "INITVALUE_");
		columnMap.put("defaultValue", "DEFAULTVALUE_");
		columnMap.put("maxValue", "MAXVALUE_");
		columnMap.put("minValue", "MINVALUE_");
		columnMap.put("stepValue", "STEPVALUE_");
		columnMap.put("placeholder", "PLACEHOLDER_");
		columnMap.put("valueExpression", "VALUEEXPRESSION_");
		columnMap.put("sortable", "SORTABLE_");
		columnMap.put("ordinal", "ORDINAL_");
		columnMap.put("createTime", "CREATETIME_");
		columnMap.put("createBy", "CREATEBY_");
		columnMap.put("updateTime", "UPDATETIME_");
		columnMap.put("updateBy", "UPDATEBY_");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("serviceKey", "String");
		javaTypeMap.put("tablename", "String");
		javaTypeMap.put("columnName", "String");
		javaTypeMap.put("name", "String");
		javaTypeMap.put("title", "String");
		javaTypeMap.put("frmType", "String");
		javaTypeMap.put("dataType", "String");
		javaTypeMap.put("length", "Integer");
		javaTypeMap.put("listWeigth", "Integer");
		javaTypeMap.put("primaryKey", "String");
		javaTypeMap.put("systemFlag", "String");
		javaTypeMap.put("inputType", "String");
		javaTypeMap.put("displayType", "Integer");
		javaTypeMap.put("importType", "Integer");
		javaTypeMap.put("formatter", "String");
		javaTypeMap.put("searchable", "String");
		javaTypeMap.put("editable", "String");
		javaTypeMap.put("updatable", "String");
		javaTypeMap.put("formula", "String");
		javaTypeMap.put("mask", "String");
		javaTypeMap.put("queryId", "String");
		javaTypeMap.put("valueField", "String");
		javaTypeMap.put("textField", "String");
		javaTypeMap.put("validType", "String");
		javaTypeMap.put("required", "String");
		javaTypeMap.put("dataItemId", "Long");
		javaTypeMap.put("initValue", "String");
		javaTypeMap.put("defaultValue", "String");
		javaTypeMap.put("maxValue", "Double");
		javaTypeMap.put("minValue", "Double");
		javaTypeMap.put("stepValue", "Double");
		javaTypeMap.put("placeholder", "String");
		javaTypeMap.put("valueExpression", "String");
		javaTypeMap.put("sortable", "String");
		javaTypeMap.put("ordinal", "Integer");
		javaTypeMap.put("createTime", "Date");
		javaTypeMap.put("createBy", "String");
		javaTypeMap.put("updateTime", "Date");
		javaTypeMap.put("updateBy", "String");
	}

	public static Map<String, String> getColumnMap() {
		return columnMap;
	}

	public static Map<String, String> getJavaTypeMap() {
		return javaTypeMap;
	}

	public static TableDefinition getTableDefinition() {
		return getTableDefinition(TABLENAME);
	}

	public static TableDefinition getTableDefinition(String tableName) {
		tableName = tableName.toUpperCase();
		TableDefinition tableDefinition = new TableDefinition();
		tableDefinition.setTableName(tableName);
		tableDefinition.setName("SysDataField");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("String");
		idColumn.setLength(80);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition serviceKey = new ColumnDefinition();
		serviceKey.setName("serviceKey");
		serviceKey.setColumnName("SERVICEKEY_");
		serviceKey.setJavaType("String");
		serviceKey.setLength(50);
		tableDefinition.addColumn(serviceKey);

		ColumnDefinition tablename = new ColumnDefinition();
		tablename.setName("tablename");
		tablename.setColumnName("TABLENAME_");
		tablename.setJavaType("String");
		tablename.setLength(50);
		tableDefinition.addColumn(tablename);

		ColumnDefinition columnName = new ColumnDefinition();
		columnName.setName("columnName");
		columnName.setColumnName("COLUMNNAME_");
		columnName.setJavaType("String");
		columnName.setLength(50);
		tableDefinition.addColumn(columnName);

		ColumnDefinition name = new ColumnDefinition();
		name.setName("name");
		name.setColumnName("NAME_");
		name.setJavaType("String");
		name.setLength(50);
		tableDefinition.addColumn(name);

		ColumnDefinition title = new ColumnDefinition();
		title.setName("title");
		title.setColumnName("TITLE_");
		title.setJavaType("String");
		title.setLength(100);
		tableDefinition.addColumn(title);

		ColumnDefinition frmType = new ColumnDefinition();
		frmType.setName("frmType");
		frmType.setColumnName("FRMTYPE_");
		frmType.setJavaType("String");
		frmType.setLength(50);
		tableDefinition.addColumn(frmType);

		ColumnDefinition dataType = new ColumnDefinition();
		dataType.setName("dataType");
		dataType.setColumnName("DATATYPE_");
		dataType.setJavaType("String");
		dataType.setLength(50);
		tableDefinition.addColumn(dataType);

		ColumnDefinition length = new ColumnDefinition();
		length.setName("length");
		length.setColumnName("LENGTH_");
		length.setJavaType("Integer");
		tableDefinition.addColumn(length);

		ColumnDefinition listWeigth = new ColumnDefinition();
		listWeigth.setName("listWeigth");
		listWeigth.setColumnName("LISTWEIGTH_");
		listWeigth.setJavaType("Integer");
		tableDefinition.addColumn(listWeigth);

		ColumnDefinition primaryKey = new ColumnDefinition();
		primaryKey.setName("primaryKey");
		primaryKey.setColumnName("PRIMARYKEY_");
		primaryKey.setJavaType("String");
		primaryKey.setLength(5);
		tableDefinition.addColumn(primaryKey);

		ColumnDefinition systemFlag = new ColumnDefinition();
		systemFlag.setName("systemFlag");
		systemFlag.setColumnName("SYSTEMFLAG_");
		systemFlag.setJavaType("String");
		systemFlag.setLength(5);
		tableDefinition.addColumn(systemFlag);

		ColumnDefinition inputType = new ColumnDefinition();
		inputType.setName("inputType");
		inputType.setColumnName("INPUTTYPE_");
		inputType.setJavaType("String");
		inputType.setLength(50);
		tableDefinition.addColumn(inputType);

		ColumnDefinition displayType = new ColumnDefinition();
		displayType.setName("displayType");
		displayType.setColumnName("DISPLAYTYPE_");
		displayType.setJavaType("Integer");
		tableDefinition.addColumn(displayType);

		ColumnDefinition importType = new ColumnDefinition();
		importType.setName("importType");
		importType.setColumnName("IMPORTTYPE_");
		importType.setJavaType("Integer");
		tableDefinition.addColumn(importType);

		ColumnDefinition formatter = new ColumnDefinition();
		formatter.setName("formatter");
		formatter.setColumnName("FORMATTER_");
		formatter.setJavaType("String");
		formatter.setLength(50);
		tableDefinition.addColumn(formatter);

		ColumnDefinition searchable = new ColumnDefinition();
		searchable.setName("searchable");
		searchable.setColumnName("SEARCHABLE_");
		searchable.setJavaType("String");
		searchable.setLength(5);
		tableDefinition.addColumn(searchable);

		ColumnDefinition editable = new ColumnDefinition();
		editable.setName("editable");
		editable.setColumnName("EDITABLE_");
		editable.setJavaType("String");
		editable.setLength(5);
		tableDefinition.addColumn(editable);

		ColumnDefinition updatable = new ColumnDefinition();
		updatable.setName("updatable");
		updatable.setColumnName("UPDATEABLE_");
		updatable.setJavaType("String");
		updatable.setLength(5);
		tableDefinition.addColumn(updatable);

		ColumnDefinition formula = new ColumnDefinition();
		formula.setName("formula");
		formula.setColumnName("FORMULA_");
		formula.setJavaType("String");
		formula.setLength(100);
		tableDefinition.addColumn(formula);

		ColumnDefinition mask = new ColumnDefinition();
		mask.setName("mask");
		mask.setColumnName("MASK_");
		mask.setJavaType("String");
		mask.setLength(100);
		tableDefinition.addColumn(mask);

		ColumnDefinition queryId = new ColumnDefinition();
		queryId.setName("queryId");
		queryId.setColumnName("QUERYID_");
		queryId.setJavaType("String");
		queryId.setLength(50);
		tableDefinition.addColumn(queryId);

		ColumnDefinition valueField = new ColumnDefinition();
		valueField.setName("valueField");
		valueField.setColumnName("VALUEFIELD_");
		valueField.setJavaType("String");
		valueField.setLength(50);
		tableDefinition.addColumn(valueField);

		ColumnDefinition textField = new ColumnDefinition();
		textField.setName("textField");
		textField.setColumnName("TEXTFIELD_");
		textField.setJavaType("String");
		textField.setLength(50);
		tableDefinition.addColumn(textField);

		ColumnDefinition validType = new ColumnDefinition();
		validType.setName("validType");
		validType.setColumnName("VALIDTYPE_");
		validType.setJavaType("String");
		validType.setLength(50);
		tableDefinition.addColumn(validType);

		ColumnDefinition required = new ColumnDefinition();
		required.setName("required");
		required.setColumnName("REQUIRED_");
		required.setJavaType("String");
		required.setLength(5);
		tableDefinition.addColumn(required);

		ColumnDefinition dataItemId = new ColumnDefinition();
		dataItemId.setName("dataItemId");
		dataItemId.setColumnName("DATAITEMID_");
		dataItemId.setJavaType("Long");
		tableDefinition.addColumn(dataItemId);

		ColumnDefinition initValue = new ColumnDefinition();
		initValue.setName("initValue");
		initValue.setColumnName("INITVALUE_");
		initValue.setJavaType("String");
		initValue.setLength(200);
		tableDefinition.addColumn(initValue);

		ColumnDefinition defaultValue = new ColumnDefinition();
		defaultValue.setName("defaultValue");
		defaultValue.setColumnName("DEFAULTVALUE_");
		defaultValue.setJavaType("String");
		defaultValue.setLength(100);
		tableDefinition.addColumn(defaultValue);

		ColumnDefinition maxValue = new ColumnDefinition();
		maxValue.setName("maxValue");
		maxValue.setColumnName("MAXVALUE_");
		maxValue.setJavaType("Double");
		tableDefinition.addColumn(maxValue);

		ColumnDefinition minValue = new ColumnDefinition();
		minValue.setName("minValue");
		minValue.setColumnName("MINVALUE_");
		minValue.setJavaType("Double");
		tableDefinition.addColumn(minValue);

		ColumnDefinition stepValue = new ColumnDefinition();
		stepValue.setName("stepValue");
		stepValue.setColumnName("STEPVALUE_");
		stepValue.setJavaType("Double");
		tableDefinition.addColumn(stepValue);

		ColumnDefinition placeholder = new ColumnDefinition();
		placeholder.setName("placeholder");
		placeholder.setColumnName("PLACEHOLDER_");
		placeholder.setJavaType("String");
		placeholder.setLength(200);
		tableDefinition.addColumn(placeholder);

		ColumnDefinition valueExpression = new ColumnDefinition();
		valueExpression.setName("valueExpression");
		valueExpression.setColumnName("VALUEEXPRESSION_");
		valueExpression.setJavaType("String");
		valueExpression.setLength(100);
		tableDefinition.addColumn(valueExpression);

		ColumnDefinition sortable = new ColumnDefinition();
		sortable.setName("sortable");
		sortable.setColumnName("SORTABLE_");
		sortable.setJavaType("String");
		sortable.setLength(5);
		tableDefinition.addColumn(sortable);

		ColumnDefinition ordinal = new ColumnDefinition();
		ordinal.setName("ordinal");
		ordinal.setColumnName("ORDINAL_");
		ordinal.setJavaType("Integer");
		tableDefinition.addColumn(ordinal);

		ColumnDefinition createTime = new ColumnDefinition();
		createTime.setName("createTime");
		createTime.setColumnName("CREATETIME_");
		createTime.setJavaType("Date");
		tableDefinition.addColumn(createTime);

		ColumnDefinition createBy = new ColumnDefinition();
		createBy.setName("createBy");
		createBy.setColumnName("CREATEBY_");
		createBy.setJavaType("String");
		createBy.setLength(50);
		tableDefinition.addColumn(createBy);

		ColumnDefinition updateTime = new ColumnDefinition();
		updateTime.setName("updateTime");
		updateTime.setColumnName("UPDATETIME_");
		updateTime.setJavaType("Date");
		tableDefinition.addColumn(updateTime);

		ColumnDefinition updateBy = new ColumnDefinition();
		updateBy.setName("updateBy");
		updateBy.setColumnName("UPDATEBY_");
		updateBy.setJavaType("String");
		updateBy.setLength(50);
		tableDefinition.addColumn(updateBy);

		return tableDefinition;
	}

	public static TableDefinition createTable() {
		TableDefinition tableDefinition = getTableDefinition(TABLENAME);
		if (!DBUtils.tableExists(TABLENAME)) {
			DBUtils.createTable(tableDefinition);
		} else {
			DBUtils.alterTable(tableDefinition);
		}
		return tableDefinition;
	}

	public static TableDefinition createTable(String tableName) {
		TableDefinition tableDefinition = getTableDefinition(tableName);
		if (!DBUtils.tableExists(tableName)) {
			DBUtils.createTable(tableDefinition);
		} else {
			DBUtils.alterTable(tableDefinition);
		}
		return tableDefinition;
	}

	public static void processDataRequest(DataRequest dataRequest) {
		if (dataRequest != null) {
			if (dataRequest.getFilter() != null) {
				if (dataRequest.getFilter().getField() != null) {
					dataRequest.getFilter().setColumn(
							columnMap.get(dataRequest.getFilter().getField()));
					dataRequest.getFilter()
							.setJavaType(
									javaTypeMap.get(dataRequest.getFilter()
											.getField()));
				}

				List<FilterDescriptor> filters = dataRequest.getFilter()
						.getFilters();
				for (FilterDescriptor filter : filters) {
					filter.setParent(dataRequest.getFilter());
					if (filter.getField() != null) {
						filter.setColumn(columnMap.get(filter.getField()));
						filter.setJavaType(javaTypeMap.get(filter.getField()));
					}

					List<FilterDescriptor> subFilters = filter.getFilters();
					for (FilterDescriptor f : subFilters) {
						f.setColumn(columnMap.get(f.getField()));
						f.setJavaType(javaTypeMap.get(f.getField()));
						f.setParent(filter);
					}
				}
			}
		}
	}

	private SysDataFieldDomainFactory() {

	}

}
