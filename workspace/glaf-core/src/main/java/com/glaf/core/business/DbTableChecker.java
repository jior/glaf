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

package com.glaf.core.business;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.query.TableDefinitionQuery;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.util.DBUtils;

public class DbTableChecker {

	protected final static Log logger = LogFactory.getLog(DbTableChecker.class);

	protected ITableDefinitionService tableDefinitionService;

	public DbTableChecker() {

	}

	public void checkTables() {
		List<String> tables = DBUtils.getTables();
		TableDefinitionQuery query = new TableDefinitionQuery();

		List<TableDefinition> list = tableDefinitionService
				.getTableColumnsCount(query);
		Map<String, TableDefinition> tableMap = new HashMap<String, TableDefinition>();
		if (list != null && !list.isEmpty()) {
			for (TableDefinition t : list) {
				tableMap.put(t.getTableName().toLowerCase(), t);
			}
			list.clear();
		}

		if (tables != null && !tables.isEmpty()) {
			for (String tableName : tables) {
				String tbl = tableName;
				tableName = tableName.toLowerCase();
				if (tableName.startsWith("act_")) {
					continue;
				}
				if (tableName.startsWith("jbpm_")) {
					continue;
				}
				if (tableName.startsWith("tmp_")) {
					continue;
				}
				if (tableName.startsWith("temp_")) {
					continue;
				}
				if (tableName.startsWith("demo_")) {
					continue;
				}
				if (tableMap.get(tableName) == null) {
					try {
						List<ColumnDefinition> columns = DBUtils
								.getColumnDefinitions(tbl);
						tableDefinitionService.saveSystemTable(tableName,
								columns);
						logger.debug(tableName + " save ok");
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else {
					TableDefinition table = tableMap.get(tableName);
					try {
						List<ColumnDefinition> columns = DBUtils
								.getColumnDefinitions(tbl);
						if (table.getColumnQty() != columns.size()) {
							tableDefinitionService.saveSystemTable(tableName,
									columns);
							logger.debug(tableName + " save ok");
						} else {
							logger.debug(tableName + " check ok");
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
			tables.clear();
		}

		list = null;
		tables = null;
	}

	public void setTableDefinitionService(
			ITableDefinitionService tableDefinitionService) {
		this.tableDefinitionService = tableDefinitionService;
	}

	public static void main(String[] args) {
		ITableDefinitionService tableDefinitionService = null;
		try {
			tableDefinitionService = ContextFactory
					.getBean("tableDefinitionService");
			if (tableDefinitionService != null) {
				DbTableChecker checker = new DbTableChecker();
				checker.setTableDefinitionService(tableDefinitionService);
				checker.checkTables();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}