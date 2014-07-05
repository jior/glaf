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

package com.glaf.core.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.util.DBUtils;

public class TableDataServiceTest {
	protected static final Log logger = LogFactory
			.getLog(TableDataServiceTest.class);

	protected final static String SYS_NAME = "default";

	protected static ITableDataService tableDataService = ContextFactory
			.getBean("tableDataService");

	public TableDefinition getTableDefinition(String tableName) {
		tableName = tableName.toUpperCase();
		TableDefinition tableDefinition = new TableDefinition();
		tableDefinition.setTableName(tableName);

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		idColumn.setPrimaryKey(true);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition column1 = new ColumnDefinition();
		column1.setName("actorId");
		column1.setColumnName("ACTORID_");
		column1.setJavaType("String");
		column1.setLength(50);
		tableDefinition.addColumn(column1);

		ColumnDefinition column11 = new ColumnDefinition();
		column11.setName("accountId");
		column11.setColumnName("ACCOUNTID_");
		column11.setJavaType("Long");
		tableDefinition.addColumn(column11);

		ColumnDefinition column12 = new ColumnDefinition();
		column12.setColumnName("OPENID_");
		column12.setJavaType("String");
		column12.setLength(200);
		tableDefinition.addColumn(column12);

		ColumnDefinition column2 = new ColumnDefinition();
		column2.setName("ip");
		column2.setColumnName("IP_");
		column2.setJavaType("String");
		column2.setLength(100);
		tableDefinition.addColumn(column2);

		ColumnDefinition column3 = new ColumnDefinition();
		column3.setName("createTime");
		column3.setColumnName("CREATETIME_");
		column3.setJavaType("Date");
		tableDefinition.addColumn(column3);

		ColumnDefinition column4 = new ColumnDefinition();
		column4.setName("operate");
		column4.setColumnName("OPERATE_");
		column4.setJavaType("String");
		column4.setLength(50);
		tableDefinition.addColumn(column4);

		ColumnDefinition column6 = new ColumnDefinition();
		column6.setName("content");
		column6.setColumnName("CONTENT_");
		column6.setJavaType("String");
		column6.setLength(500);
		tableDefinition.addColumn(column6);

		ColumnDefinition column8 = new ColumnDefinition();
		column8.setName("flag");
		column8.setColumnName("FLAG_");
		column8.setJavaType("Integer");
		tableDefinition.addColumn(column8);

		ColumnDefinition column9 = new ColumnDefinition();
		column9.setName("moduleId");
		column9.setColumnName("MODULEID_");
		column9.setJavaType("String");
		column9.setLength(50);
		tableDefinition.addColumn(column9);

		ColumnDefinition column10 = new ColumnDefinition();
		column10.setName("timeMS");
		column10.setColumnName("TIMEMS_");
		column10.setJavaType("Integer");
		tableDefinition.addColumn(column10);

		return tableDefinition;
	}

	@Test
	public void testCreateTable() {
		TableDefinition tableDefinition = this.getTableDefinition("TEST_DATA");
		if (!DBUtils.tableExists(SYS_NAME, "TEST_DATA")) {
			DBUtils.createTable(SYS_NAME, tableDefinition);
		} else {
			DBUtils.alterTable(SYS_NAME, tableDefinition);
		}
		ITableDefinitionService tableDefinitionService = ContextFactory
				.getBean("tableDefinitionService");
		tableDefinitionService.save(tableDefinition);
	}

	public void testInserTableData() {
		TableDefinition tableDefinition = this.getTableDefinition("TEST_DATA");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 10; i++) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("ID_", String.valueOf(i + 1));
			dataMap.put("ACTORID_", "admin");
			dataMap.put("ACCOUNTID_", i);
			dataMap.put("CREATETIME_", new Date());
			dataMap.put("FLAG_", 1);
			dataMap.put("TIMEMS_", System.currentTimeMillis());
			list.add(dataMap);
		}
		tableDataService.insertTableData(tableDefinition.getTableName(), list);
	}

	@Test
	public void testSaveOrUpdateTableData() {
		TableDefinition tableDefinition = this.getTableDefinition("TEST_DATA");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 10; i++) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("ID_", String.valueOf(i + 1));
			dataMap.put("ACTORID_", "joy");
			dataMap.put("ACCOUNTID_", i * i);
			dataMap.put("CREATETIME_", new Date());
			dataMap.put("FLAG_", 2);
			dataMap.put("TIMEMS_", i * i * i);
			list.add(dataMap);
		}
		tableDataService.saveOrUpdate(tableDefinition.getTableName(), true,
				list);
	}

	public void testUpdateTableData() {
		TableDefinition tableDefinition = this.getTableDefinition("TEST_DATA");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 10; i++) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("ID_", String.valueOf(i + 1));
			dataMap.put("ACTORID_", "joy");
			dataMap.put("ACCOUNTID_", i * i);
			dataMap.put("CREATETIME_", new Date());
			dataMap.put("FLAG_", 2);
			dataMap.put("TIMEMS_", System.currentTimeMillis());
			list.add(dataMap);
		}
		tableDataService.updateTableData(tableDefinition.getTableName(), list);
	}

}
