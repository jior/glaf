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
public class SysDataItemDomainFactory {

	public static final String TABLENAME = "SYS_DATA_ITEM";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID_");
		columnMap.put("name", "NAME_");
		columnMap.put("title", "TITLE_");
		columnMap.put("type", "TYPE_");
		columnMap.put("queryId", "QUERYID_");
		columnMap.put("querySQL", "QUERYSQL_");
		columnMap.put("parameter", "PARAMETER_");
		columnMap.put("textField", "TEXTFIELD_");
		columnMap.put("valueField", "VALUEFIELD_");
		columnMap.put("treeIdField", "TREEIDFIELD_");
		columnMap.put("treeParentIdField", "TREEPARENTIDFIELD_");
		columnMap.put("treeTreeIdField", "TREETREEIDFIELD_");
		columnMap.put("treeNameField", "TREENAMEFIELD_");
		columnMap.put("treeListNoField", "TREELISTNOFIELD_");
		columnMap.put("url", "URL_");
		columnMap.put("cacheFlag", "CACHEFLAG_");
		columnMap.put("createBy", "CREATEBY_");
		columnMap.put("createTime", "CREATETIME_");
		columnMap.put("updateBy", "UPDATEBY_");
		columnMap.put("updateTime", "UPDATETIME_");

		javaTypeMap.put("id", "Long");
		javaTypeMap.put("name", "String");
		javaTypeMap.put("title", "String");
		javaTypeMap.put("type", "String");
		javaTypeMap.put("queryId", "String");
		javaTypeMap.put("querySQL", "String");
		javaTypeMap.put("parameter", "String");
		javaTypeMap.put("textField", "String");
		javaTypeMap.put("valueField", "String");
		javaTypeMap.put("treeIdField", "String");
		javaTypeMap.put("treeParentIdField", "String");
		javaTypeMap.put("treeTreeIdField", "String");
		javaTypeMap.put("treeNameField", "String");
		javaTypeMap.put("treeListNoField", "String");
		javaTypeMap.put("url", "String");
		javaTypeMap.put("cacheFlag", "String");
		javaTypeMap.put("createBy", "String");
		javaTypeMap.put("createTime", "Date");
		javaTypeMap.put("updateBy", "String");
		javaTypeMap.put("updateTime", "Date");
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
		tableDefinition.setName("SysDataItem");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("Long");
		tableDefinition.setIdColumn(idColumn);

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

		ColumnDefinition type = new ColumnDefinition();
		type.setName("type");
		type.setColumnName("TYPE_");
		type.setJavaType("String");
		type.setLength(50);
		tableDefinition.addColumn(type);

		ColumnDefinition queryId = new ColumnDefinition();
		queryId.setName("queryId");
		queryId.setColumnName("QUERYID_");
		queryId.setJavaType("String");
		queryId.setLength(50);
		tableDefinition.addColumn(queryId);

		ColumnDefinition querySQL = new ColumnDefinition();
		querySQL.setName("querySQL");
		querySQL.setColumnName("QUERYSQL_");
		querySQL.setJavaType("String");
		querySQL.setLength(2000);
		tableDefinition.addColumn(querySQL);

		ColumnDefinition parameter = new ColumnDefinition();
		parameter.setName("parameter");
		parameter.setColumnName("PARAMETER_");
		parameter.setJavaType("String");
		parameter.setLength(2000);
		tableDefinition.addColumn(parameter);

		ColumnDefinition textField = new ColumnDefinition();
		textField.setName("textField");
		textField.setColumnName("TEXTFIELD_");
		textField.setJavaType("String");
		textField.setLength(50);
		tableDefinition.addColumn(textField);

		ColumnDefinition valueField = new ColumnDefinition();
		valueField.setName("valueField");
		valueField.setColumnName("VALUEFIELD_");
		valueField.setJavaType("String");
		valueField.setLength(50);
		tableDefinition.addColumn(valueField);

		ColumnDefinition treeIdField = new ColumnDefinition();
		treeIdField.setName("treeIdField");
		treeIdField.setColumnName("TREEIDFIELD_");
		treeIdField.setJavaType("String");
		treeIdField.setLength(50);
		tableDefinition.addColumn(treeIdField);

		ColumnDefinition treeParentIdField = new ColumnDefinition();
		treeParentIdField.setName("treeParentIdField");
		treeParentIdField.setColumnName("TREEPARENTIDFIELD_");
		treeParentIdField.setJavaType("String");
		treeParentIdField.setLength(50);
		tableDefinition.addColumn(treeParentIdField);

		ColumnDefinition treeTreeIdField = new ColumnDefinition();
		treeTreeIdField.setName("treeTreeIdField");
		treeTreeIdField.setColumnName("TREETREEIDFIELD_");
		treeTreeIdField.setJavaType("String");
		treeTreeIdField.setLength(50);
		tableDefinition.addColumn(treeTreeIdField);

		ColumnDefinition treeNameField = new ColumnDefinition();
		treeNameField.setName("treeNameField");
		treeNameField.setColumnName("TREENAMEFIELD_");
		treeNameField.setJavaType("String");
		treeNameField.setLength(50);
		tableDefinition.addColumn(treeNameField);

		ColumnDefinition treeListNoField = new ColumnDefinition();
		treeListNoField.setName("treeListNoField");
		treeListNoField.setColumnName("TREELISTNOFIELD_");
		treeListNoField.setJavaType("String");
		treeListNoField.setLength(50);
		tableDefinition.addColumn(treeListNoField);

		ColumnDefinition url = new ColumnDefinition();
		url.setName("url");
		url.setColumnName("URL_");
		url.setJavaType("String");
		url.setLength(500);
		tableDefinition.addColumn(url);

		ColumnDefinition cacheFlag = new ColumnDefinition();
		cacheFlag.setName("cacheFlag");
		cacheFlag.setColumnName("CACHEFLAG_");
		cacheFlag.setJavaType("String");
		cacheFlag.setLength(5);
		tableDefinition.addColumn(cacheFlag);

		ColumnDefinition createBy = new ColumnDefinition();
		createBy.setName("createBy");
		createBy.setColumnName("CREATEBY_");
		createBy.setJavaType("String");
		createBy.setLength(50);
		tableDefinition.addColumn(createBy);

		ColumnDefinition createTime = new ColumnDefinition();
		createTime.setName("createTime");
		createTime.setColumnName("CREATETIME_");
		createTime.setJavaType("Date");
		tableDefinition.addColumn(createTime);

		ColumnDefinition updateBy = new ColumnDefinition();
		updateBy.setName("updateBy");
		updateBy.setColumnName("UPDATEBY_");
		updateBy.setJavaType("String");
		updateBy.setLength(50);
		tableDefinition.addColumn(updateBy);

		ColumnDefinition updateTime = new ColumnDefinition();
		updateTime.setName("updateTime");
		updateTime.setColumnName("UPDATETIME_");
		updateTime.setJavaType("Date");
		tableDefinition.addColumn(updateTime);

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

	private SysDataItemDomainFactory() {

	}

}
