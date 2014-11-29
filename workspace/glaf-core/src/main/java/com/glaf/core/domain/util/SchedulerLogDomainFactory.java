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
public class SchedulerLogDomainFactory {

	public static final String TABLENAME = "SYS_SCHEDULER_LOG";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID_");
		columnMap.put("taskId", "TASKID_");
		columnMap.put("startDate", "STARTDATE_");
		columnMap.put("endDate", "ENDDATE_");
		columnMap.put("jobRunTime", "JOBRUNTIME");
		columnMap.put("status", "STATUS_");
		columnMap.put("taskName", "TASKNAME_");
		columnMap.put("title", "TITLE_");
		columnMap.put("content", "CONTENT_");
		columnMap.put("exitCode", "EXITCODE_");
		columnMap.put("exitMessage", "EXITMESSAGE_");
		columnMap.put("createBy", "CREATEBY_");
		columnMap.put("createDate", "CREATEDATE_");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("taskId", "String");
		javaTypeMap.put("startDate", "Date");
		javaTypeMap.put("endDate", "Date");
		javaTypeMap.put("jobRunTime", "Long");
		javaTypeMap.put("status", "Integer");
		javaTypeMap.put("taskName", "String");
		javaTypeMap.put("title", "String");
		javaTypeMap.put("content", "String");
		javaTypeMap.put("exitCode", "String");
		javaTypeMap.put("exitMessage", "String");
		javaTypeMap.put("createBy", "String");
		javaTypeMap.put("createDate", "Date");
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
		tableDefinition.setName("SchedulerLog");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);
		
		ColumnDefinition taskIdColumn = new ColumnDefinition();
		taskIdColumn.setName("taskId");
		taskIdColumn.setColumnName("TASKID_");
		taskIdColumn.setJavaType("String");
		taskIdColumn.setLength(50);
		tableDefinition.setIdColumn(taskIdColumn);

		ColumnDefinition startDate = new ColumnDefinition();
		startDate.setName("startDate");
		startDate.setColumnName("STARTDATE_");
		startDate.setJavaType("Date");
		tableDefinition.addColumn(startDate);

		ColumnDefinition endDate = new ColumnDefinition();
		endDate.setName("endDate");
		endDate.setColumnName("ENDDATE_");
		endDate.setJavaType("Date");
		tableDefinition.addColumn(endDate);

		ColumnDefinition jobRunTime = new ColumnDefinition();
		jobRunTime.setName("jobRunTime");
		jobRunTime.setColumnName("JOBRUNTIME");
		jobRunTime.setJavaType("Long");
		tableDefinition.addColumn(jobRunTime);

		ColumnDefinition status = new ColumnDefinition();
		status.setName("status");
		status.setColumnName("STATUS_");
		status.setJavaType("Integer");
		tableDefinition.addColumn(status);

		ColumnDefinition taskName = new ColumnDefinition();
		taskName.setName("taskName");
		taskName.setColumnName("TASKNAME_");
		taskName.setJavaType("String");
		taskName.setLength(255);
		tableDefinition.addColumn(taskName);

		ColumnDefinition title = new ColumnDefinition();
		title.setName("title");
		title.setColumnName("TITLE_");
		title.setJavaType("String");
		title.setLength(255);
		tableDefinition.addColumn(title);

		ColumnDefinition content = new ColumnDefinition();
		content.setName("content");
		content.setColumnName("CONTENT_");
		content.setJavaType("String");
		content.setLength(255);
		tableDefinition.addColumn(content);

		ColumnDefinition exitCode = new ColumnDefinition();
		exitCode.setName("exitCode");
		exitCode.setColumnName("EXITCODE_");
		exitCode.setJavaType("String");
		exitCode.setLength(255);
		tableDefinition.addColumn(exitCode);

		ColumnDefinition exitMessage = new ColumnDefinition();
		exitMessage.setName("exitMessage");
		exitMessage.setColumnName("EXITMESSAGE_");
		exitMessage.setJavaType("Clob");
		exitMessage.setLength(255);
		tableDefinition.addColumn(exitMessage);

		ColumnDefinition createBy = new ColumnDefinition();
		createBy.setName("createBy");
		createBy.setColumnName("CREATEBY_");
		createBy.setJavaType("String");
		createBy.setLength(255);
		tableDefinition.addColumn(createBy);

		ColumnDefinition createDate = new ColumnDefinition();
		createDate.setName("createDate");
		createDate.setColumnName("CREATEDATE_");
		createDate.setJavaType("Date");
		tableDefinition.addColumn(createDate);

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
		if (dataRequest.getFilter() != null) {
			if (dataRequest.getFilter().getField() != null) {
				dataRequest.getFilter().setColumn(
						columnMap.get(dataRequest.getFilter().getField()));
				dataRequest.getFilter().setJavaType(
						javaTypeMap.get(dataRequest.getFilter().getField()));
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

	private SchedulerLogDomainFactory() {

	}

}
