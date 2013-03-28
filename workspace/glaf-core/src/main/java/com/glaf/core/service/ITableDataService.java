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

import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.base.TableModel;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.id.Dbid;
import com.glaf.core.util.Paging;

@Transactional(readOnly = true)
public interface ITableDataService {
	
	/**
	 * 删除数据
	 * @param model
	 */
	@Transactional
	void deleteTableData(TableModel model);

	List<Dbid> getAllDbids();
	
	/**
	 * 更新序列
	 * @param rows
	 */
	@Transactional
	void updateAllDbids(List<Dbid> rows);

	/**
	 * 获取一页数据
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param model
	 * @return
	 */
	Paging getPageData(int pageNo, int pageSize, TableModel model);

	/**
	 * 根据主键获取记录
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	List<Map<String, Object>> getTableDataByPrimaryKey(Object id);

	/**
	 * 批量插入数据
	 * 
	 * @param rows
	 */
	@Transactional
	void insertTableData(List<TableModel> rows);

	/**
	 * 批量插入数据
	 * 
	 * @param tableName
	 * @param rows
	 */
	@Transactional
	void insertTableData(String tableName, List<Map<String, Object>> rows);
	
	/**
	 * 插入一条记录
	 * 
	 * @param model
	 */
	@Transactional
	void insertTableData(TableModel model);

	/**
	 * 批量保存记录
	 * 
	 * @param tableName
	 * @param rows
	 */
	@Transactional
	void saveAll(String tableName, Collection<TableModel> rows);

	/**
	 * 批量保存记录
	 * 
	 * @param tableDefinition
	 * @param rows
	 */
	@Transactional
	void saveAll(TableDefinition tableDefinition, Collection<TableModel> rows);

	/**
	 * 批量新增或修改记录，如果存在，可以选择是否更新
	 * 
	 * @param tableName
	 * @param updatable
	 * @param rows
	 */
	@Transactional
	void saveOrUpdate(String tableName, boolean updatable,
			List<Map<String, Object>> rows);

	/**
	 * 批量更新记录
	 * 
	 * @param rows
	 */
	@Transactional
	void updateTableData(List<TableModel> rows);

	/**
	 * 批量更新记录
	 * 
	 * @param tableName
	 * @param rows
	 */
	@Transactional
	void updateTableData(String tableName, List<Map<String, Object>> rows);

	/**
	 * 更新一条记录
	 * 
	 * @param model
	 */
	@Transactional
	void updateTableData(TableModel model);
}