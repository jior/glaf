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

package com.glaf.core.service;

import java.util.*;

import com.glaf.core.domain.*;
import com.glaf.core.query.*;

public interface ITableDefinitionService {

	/**
	 * 删除列定义
	 * 
	 * @param columnId
	 */
	void deleteColumn(String columnId);

	/**
	 * 删除表定义及管理查询
	 * 
	 * @param tableName
	 */
	void deleteTable(String tableName);

	/**
	 * 
	 * @param columnId
	 * @return
	 */
	ColumnDefinition getColumnDefinition(String columnId);

	/**
	 * 根据表名获取表对象
	 * 
	 * @return
	 */
	TableDefinition getTableDefinition(String tableName);

	List<ColumnDefinition> getColumnDefinitionsByTableName(String tableName);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getTableDefinitionCountByQueryCriteria(TableDefinitionQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<TableDefinition> getTableDefinitionsByQueryCriteria(int start,
			int pageSize, TableDefinitionQuery query);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<TableDefinition> list(TableDefinitionQuery query);

	List<TableDefinition> getTableColumnsCount(TableDefinitionQuery query);

	/**
	 * 保存表定义信息
	 * 
	 * @return
	 */
	void save(TableDefinition tableDefinition);

	/**
	 * 保存字段信息
	 * 
	 * @param tableName
	 * @param columnDefinition
	 */
	void saveColumn(String tableName, ColumnDefinition columnDefinition);

	/**
	 * 保存定义
	 * 
	 * @param tableName
	 * @param rows
	 */
	void saveSystemTable(String tableName, List<ColumnDefinition> rows);

}