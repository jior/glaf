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

import java.util.List;
import java.util.Map;
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
public class DatabaseDomainFactory {

	public static final String TABLENAME = "SYS_DATABASE";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID_");
		columnMap.put("nodeId", "NODEID_");
		columnMap.put("name", "NAME_");
		columnMap.put("code", "CODE_");
		columnMap.put("title", "TITLE_");
		columnMap.put("host", "HOST_");
		columnMap.put("port", "PORT_");
		columnMap.put("user", "USER_");
		columnMap.put("password", "PASSWORD_");
		columnMap.put("key", "KEY_");
		columnMap.put("type", "TYPE_");
		columnMap.put("level", "LEVEL_");
		columnMap.put("priority", "PRIORITY_");
		columnMap.put("operation", "OPERATION_");
		columnMap.put("dbname", "DBNAME_");
		columnMap.put("active", "ACTIVE_");
		columnMap.put("verify", "VERIFY_");
		columnMap.put("initFlag", "INITFLAG_");
		columnMap.put("providerClass", "PROVIDERCLASS_");
		columnMap.put("createBy", "CREATEBY_");
		columnMap.put("createTime", "CREATETIME_");
		columnMap.put("updateBy", "UPDATEBY_");
		columnMap.put("updateTime", "UPDATETIME_");

		javaTypeMap.put("id", "Long");
		javaTypeMap.put("nodeId", "Long");
		javaTypeMap.put("name", "String");
		javaTypeMap.put("code", "String");
		javaTypeMap.put("title", "String");
		javaTypeMap.put("host", "String");
		javaTypeMap.put("port", "Integer");
		javaTypeMap.put("user", "String");
		javaTypeMap.put("password", "String");
		javaTypeMap.put("key", "String");
		javaTypeMap.put("type", "String");
		javaTypeMap.put("level", "Integer");
		javaTypeMap.put("priority", "Integer");
		javaTypeMap.put("operation", "Integer");
		javaTypeMap.put("dbname", "String");
		javaTypeMap.put("active", "String");
		javaTypeMap.put("verify", "String");
		javaTypeMap.put("initFlag", "String");
		javaTypeMap.put("providerClass", "String");
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

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("Long");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition nodeId = new ColumnDefinition();
		nodeId.setName("nodeId");
		nodeId.setColumnName("NODEID_");
		nodeId.setJavaType("Long");
		tableDefinition.addColumn(nodeId);

		ColumnDefinition title = new ColumnDefinition();
		title.setName("title");
		title.setColumnName("TITLE_");
		title.setJavaType("String");
		title.setLength(100);
		tableDefinition.addColumn(title);

		ColumnDefinition code = new ColumnDefinition();
		code.setName("code");
		code.setColumnName("CODE_");
		code.setJavaType("String");
		code.setLength(50);
		tableDefinition.addColumn(code);

		ColumnDefinition host = new ColumnDefinition();
		host.setName("host");
		host.setColumnName("HOST_");
		host.setJavaType("String");
		host.setLength(100);
		tableDefinition.addColumn(host);

		ColumnDefinition port = new ColumnDefinition();
		port.setName("port");
		port.setColumnName("PORT_");
		port.setJavaType("Integer");
		tableDefinition.addColumn(port);

		ColumnDefinition user = new ColumnDefinition();
		user.setName("user");
		user.setColumnName("USER_");
		user.setJavaType("String");
		user.setLength(50);
		tableDefinition.addColumn(user);

		ColumnDefinition password = new ColumnDefinition();
		password.setName("password");
		password.setColumnName("PASSWORD_");
		password.setJavaType("String");
		password.setLength(100);
		tableDefinition.addColumn(password);

		ColumnDefinition key = new ColumnDefinition();
		key.setName("key");
		key.setColumnName("KEY_");
		key.setJavaType("String");
		key.setLength(1024);
		tableDefinition.addColumn(key);

		ColumnDefinition name = new ColumnDefinition();
		name.setName("name");
		name.setColumnName("NAME_");
		name.setJavaType("String");
		name.setLength(200);
		tableDefinition.addColumn(name);

		ColumnDefinition dbname = new ColumnDefinition();
		dbname.setName("dbname");
		dbname.setColumnName("DBNAME_");
		dbname.setJavaType("String");
		dbname.setLength(50);
		tableDefinition.addColumn(dbname);

		ColumnDefinition type = new ColumnDefinition();
		type.setName("type");
		type.setColumnName("TYPE_");
		type.setJavaType("String");
		type.setLength(50);
		tableDefinition.addColumn(type);

		ColumnDefinition level = new ColumnDefinition();
		level.setName("level");
		level.setColumnName("LEVEL_");
		level.setJavaType("Integer");
		tableDefinition.addColumn(level);

		ColumnDefinition priority = new ColumnDefinition();
		priority.setName("priority");
		priority.setColumnName("PRIORITY_");
		priority.setJavaType("Integer");
		tableDefinition.addColumn(priority);

		ColumnDefinition operation = new ColumnDefinition();
		operation.setName("operation");
		operation.setColumnName("OPERATION_");
		operation.setJavaType("Integer");
		tableDefinition.addColumn(operation);

		ColumnDefinition providerClass = new ColumnDefinition();
		providerClass.setName("providerClass");
		providerClass.setColumnName("PROVIDERCLASS_");
		providerClass.setJavaType("String");
		providerClass.setLength(100);
		tableDefinition.addColumn(providerClass);

		ColumnDefinition active = new ColumnDefinition();
		active.setName("active");
		active.setColumnName("ACTIVE_");
		active.setJavaType("String");
		active.setLength(1);
		tableDefinition.addColumn(active);

		ColumnDefinition verify = new ColumnDefinition();
		verify.setName("verify");
		verify.setColumnName("VERIFY_");
		verify.setJavaType("String");
		verify.setLength(1);
		tableDefinition.addColumn(verify);

		ColumnDefinition initFlag = new ColumnDefinition();
		initFlag.setName("initFlag");
		initFlag.setColumnName("INITFLAG_");
		initFlag.setJavaType("String");
		initFlag.setLength(1);
		tableDefinition.addColumn(initFlag);

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

	private DatabaseDomainFactory() {

	}

}
