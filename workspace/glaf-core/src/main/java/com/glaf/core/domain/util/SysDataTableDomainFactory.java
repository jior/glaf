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
public class SysDataTableDomainFactory {

	public static final String TABLENAME = "SYS_DATA_TABLE";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID_");
		columnMap.put("serviceKey", "SERVICEKEY_");
		columnMap.put("tablename", "TABLENAME_");
		columnMap.put("title", "TITLE_");
		columnMap.put("type", "TYPE_");
		columnMap.put("treeType", "TREETYPE_");
		columnMap.put("maxUser", "MAXUSER_");
		columnMap.put("maxSys", "MAXSYS_");
		columnMap.put("createBy", "CREATEBY_");
		columnMap.put("createTime", "CREATETIME_");
		columnMap.put("updateTime", "UPDATETIME_");
		columnMap.put("updateBy", "UPDATEBY_");
		columnMap.put("content", "CONTENT_");
		columnMap.put("isSubTable", "ISSUBTABLE_");
		columnMap.put("deleteFlag", "DELETEFLAG_");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("serviceKey", "String");
		javaTypeMap.put("tablename", "String");
		javaTypeMap.put("title", "String");
		javaTypeMap.put("type", "Integer");
		javaTypeMap.put("treeType", "String");
		javaTypeMap.put("maxUser", "Integer");
		javaTypeMap.put("maxSys", "Integer");
		javaTypeMap.put("createBy", "String");
		javaTypeMap.put("createTime", "Date");
		javaTypeMap.put("updateTime", "Date");
		javaTypeMap.put("updateBy", "String");
		javaTypeMap.put("content", "String");
		javaTypeMap.put("isSubTable", "String");
		javaTypeMap.put("deleteFlag", "Integer");
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
		tableDefinition.setName("SysDataTable");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("String");
		idColumn.setLength(255);
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

		ColumnDefinition title = new ColumnDefinition();
		title.setName("title");
		title.setColumnName("TITLE_");
		title.setJavaType("String");
		title.setLength(255);
		tableDefinition.addColumn(title);

		ColumnDefinition type = new ColumnDefinition();
		type.setName("type");
		type.setColumnName("TYPE_");
		type.setJavaType("Integer");
		tableDefinition.addColumn(type);

		ColumnDefinition treeType = new ColumnDefinition();
		treeType.setName("treeType");
		treeType.setColumnName("TREETYPE_");
		treeType.setJavaType("String");
		treeType.setLength(50);
		tableDefinition.addColumn(treeType);

		ColumnDefinition maxUser = new ColumnDefinition();
		maxUser.setName("maxUser");
		maxUser.setColumnName("MAXUSER_");
		maxUser.setJavaType("Integer");
		tableDefinition.addColumn(maxUser);

		ColumnDefinition maxSys = new ColumnDefinition();
		maxSys.setName("maxSys");
		maxSys.setColumnName("MAXSYS_");
		maxSys.setJavaType("Integer");
		tableDefinition.addColumn(maxSys);

		ColumnDefinition actorId = new ColumnDefinition();
		actorId.setName("createBy");
		actorId.setColumnName("CREATEBY_");
		actorId.setJavaType("String");
		actorId.setLength(50);
		tableDefinition.addColumn(actorId);

		ColumnDefinition createTime = new ColumnDefinition();
		createTime.setName("createTime");
		createTime.setColumnName("CREATETIME_");
		createTime.setJavaType("Date");
		tableDefinition.addColumn(createTime);

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

		ColumnDefinition content = new ColumnDefinition();
		content.setName("content");
		content.setColumnName("CONTENT_");
		content.setJavaType("String");
		content.setLength(250);
		tableDefinition.addColumn(content);

		ColumnDefinition isSubTable = new ColumnDefinition();
		isSubTable.setName("isSubTable");
		isSubTable.setColumnName("ISSUBTABLE_");
		isSubTable.setJavaType("String");
		isSubTable.setLength(1);
		tableDefinition.addColumn(isSubTable);

		ColumnDefinition deleteFlag = new ColumnDefinition();
		deleteFlag.setName("deleteFlag");
		deleteFlag.setColumnName("DELETEFLAG_");
		deleteFlag.setJavaType("Integer");
		tableDefinition.addColumn(deleteFlag);

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

	private SysDataTableDomainFactory() {

	}

}
