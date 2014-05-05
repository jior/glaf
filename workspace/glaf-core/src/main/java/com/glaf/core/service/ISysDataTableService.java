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

import com.glaf.core.domain.*;
import com.glaf.core.query.*;

@Transactional(readOnly = true)
public interface ISysDataTableService {

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(String id);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<String> ids);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	SysDataTable getDataTable(String id);

	/**
	 * 根据服务ID获取一条记录
	 * 
	 * @return
	 */
	SysDataTable getDataTableByServiceKey(String serviceKey);

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
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<SysDataTable> list(SysDataTableQuery query);

	@Transactional
	void saveData(String serviceKey, Map<String, Object> dataMap);

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
	void saveDataList(String serviceKey, List<Map<String, Object>> dataList);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void saveDataTable(SysDataTable sysDataTable);

}
