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

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.TreeModel;
import com.glaf.core.domain.*;
import com.glaf.core.query.*;

@Transactional(readOnly = true)
public interface ISysDataTableService {

	/**
	 * 删除字段信息
	 * 
	 * @param fieldId
	 */
	@Transactional
	void deleteDataFieldById(String fieldId);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	SysDataField getDataFieldById(String fieldId);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @param tableName
	 * @return
	 */
	int getDataFieldCountByTablename(String tableName);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @param tableName
	 * @return
	 */
	List<SysDataField> getDataFieldsByTablename(String tableName);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	SysDataTable getDataTableById(String id);

	/**
	 * 根据tableName获取一条记录
	 * 
	 * @return
	 */
	SysDataTable getDataTableByName(String tableName);

	/**
	 * 根据tableName获取一条业务数据
	 * 
	 * @param tableName
	 *            表名
	 * @param businessKey
	 *            业务主键
	 * 
	 * @return
	 */
	SysDataTable getDataTableWithData(String tableName, String businessKey);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getDataTableCountByQueryCriteria(SysDataTableQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<SysDataTable> getDataTablesByQueryCriteria(int start, int pageSize,
			SysDataTableQuery query);

	/**
	 * 获取一页数据
	 * 
	 * @param start
	 * @param pageSize
	 * @param query
	 * @return
	 */
	JSONObject getPageTableData(int start, int pageSize, SysDataTableQuery query);

	/**
	 * 获取某个表满足条件的记录总数
	 * 
	 * @param query
	 * @return
	 */
	int getTableDataCount(SysDataTableQuery query);

	/**
	 * 获取某个节点下的全部子节点
	 * 
	 * @param datatableId
	 * @param parentId
	 * @return
	 */
	List<TreeModel> getTreeModels(String datatableId, Object parentId);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<SysDataTable> list(SysDataTableQuery query);

	@Transactional
	void saveData(String datatableId, Map<String, Object> dataMap);

	/**
	 * 保存字段定义
	 * 
	 * @param sysDataField
	 */
	@Transactional
	void saveDataField(SysDataField sysDataField);

	/**
	 * 保存多个字段定义
	 * 
	 * @param fields
	 */
	@Transactional
	void saveDataFields(List<SysDataField> fields);

	@Transactional
	void saveDataList(String datatableId, List<Map<String, Object>> dataList);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void saveDataTable(SysDataTable sysDataTable);

	/**
	 * 保存数据
	 * 
	 * @param datatableId
	 * @param jsonObject
	 */
	@Transactional
	void saveJsonData(String datatableId, JSONObject jsonObject);

}
