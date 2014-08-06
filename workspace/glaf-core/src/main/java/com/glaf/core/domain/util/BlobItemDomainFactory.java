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

import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.DBUtils;

/**
 * 
 * 实体数据工厂类
 * 
 */
public class BlobItemDomainFactory {

	public final static String TABLENAME = "SYS_LOB";

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

	public static TableDefinition getTableDefinition() {
		return getTableDefinition(TABLENAME);
	}

	public static TableDefinition getTableDefinition(String tableName) {
		tableName = tableName.toUpperCase();
		TableDefinition tableDefinition = new TableDefinition();
		tableDefinition.setTableName(tableName);

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition fileId = new ColumnDefinition();
		fileId.setColumnName("FILEID_");
		fileId.setJavaType("String");
		fileId.setLength(50);
		tableDefinition.addColumn(fileId);

		ColumnDefinition businessKey = new ColumnDefinition();
		businessKey.setColumnName("BUSINESSKEY_");
		businessKey.setJavaType("String");
		businessKey.setLength(50);
		tableDefinition.addColumn(businessKey);

		ColumnDefinition serviceKey = new ColumnDefinition();
		serviceKey.setColumnName("SERVICEKEY_");
		serviceKey.setJavaType("String");
		serviceKey.setLength(50);
		tableDefinition.addColumn(serviceKey);

		ColumnDefinition deviceId = new ColumnDefinition();
		deviceId.setColumnName("DEVICEID_");
		deviceId.setJavaType("String");
		deviceId.setLength(20);
		tableDefinition.addColumn(deviceId);

		ColumnDefinition name = new ColumnDefinition();
		name.setColumnName("NAME_");
		name.setJavaType("String");
		name.setLength(50);
		tableDefinition.addColumn(name);

		ColumnDefinition type = new ColumnDefinition();
		type.setColumnName("TYPE_");
		type.setJavaType("String");
		type.setLength(50);
		tableDefinition.addColumn(type);

		ColumnDefinition filename = new ColumnDefinition();
		filename.setColumnName("FILENAME_");
		filename.setJavaType("String");
		filename.setLength(500);
		tableDefinition.addColumn(filename);

		ColumnDefinition path = new ColumnDefinition();
		path.setColumnName("PATH_");
		path.setJavaType("String");
		path.setLength(500);
		tableDefinition.addColumn(path);

		ColumnDefinition contentType = new ColumnDefinition();
		contentType.setColumnName("CONTENTTYPE_");
		contentType.setJavaType("String");
		contentType.setLength(50);
		tableDefinition.addColumn(contentType);

		ColumnDefinition objectId = new ColumnDefinition();
		objectId.setColumnName("OBJECTID_");
		objectId.setJavaType("String");
		objectId.setLength(255);
		tableDefinition.addColumn(objectId);

		ColumnDefinition objectValue = new ColumnDefinition();
		objectValue.setColumnName("OBJECTVALUE_");
		objectValue.setJavaType("String");
		objectValue.setLength(255);
		tableDefinition.addColumn(objectValue);

		ColumnDefinition size = new ColumnDefinition();
		size.setColumnName("SIZE_");
		size.setJavaType("Long");
		tableDefinition.addColumn(size);

		ColumnDefinition lastModified = new ColumnDefinition();
		lastModified.setColumnName("LASTMODIFIED_");
		lastModified.setJavaType("Long");
		tableDefinition.addColumn(lastModified);

		ColumnDefinition locked = new ColumnDefinition();
		locked.setColumnName("LOCKED_");
		locked.setJavaType("Integer");
		tableDefinition.addColumn(locked);

		ColumnDefinition status = new ColumnDefinition();
		status.setColumnName("STATUS_");
		status.setJavaType("Integer");
		tableDefinition.addColumn(status);

		ColumnDefinition deleteFlag = new ColumnDefinition();
		deleteFlag.setColumnName("DELETEFLAG_");
		deleteFlag.setJavaType("Integer");
		tableDefinition.addColumn(deleteFlag);

		ColumnDefinition createBy = new ColumnDefinition();
		createBy.setColumnName("CREATEBY_");
		createBy.setJavaType("String");
		createBy.setLength(50);
		tableDefinition.addColumn(createBy);

		ColumnDefinition createDate = new ColumnDefinition();
		createDate.setColumnName("CREATEDATE_");
		createDate.setJavaType("Date");
		tableDefinition.addColumn(createDate);

		ColumnDefinition data = new ColumnDefinition();
		data.setColumnName("DATA_");
		data.setJavaType("Blob");
		tableDefinition.addColumn(data);

		return tableDefinition;
	}

	private BlobItemDomainFactory() {

	}

}
