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

import com.alibaba.fastjson.JSONArray;
import com.glaf.core.domain.*;
import com.glaf.core.query.*;

@Transactional(readOnly = true)
public interface ISysDataItemService {

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(long id);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<SysDataItem> list(SysDataItemQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getSysDataItemCountByQueryCriteria(SysDataItemQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<SysDataItem> getSysDataItemsByQueryCriteria(int start, int pageSize,
			SysDataItemQuery query);

	JSONArray getJsonData(long id, Map<String, Object> params);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	SysDataItem getSysDataItemById(long id);

	/**
	 * 根据名称获取一条记录
	 * 
	 * @return
	 */
	SysDataItem getSysDataItemByName(String name);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(SysDataItem sysDataItem);

	/**
	 * 
	 * @param name
	 * @param columnDefinition
	 */
	@Transactional
	void saveColumn(String name, ColumnDefinition columnDefinition);
	
	/**
	 * 
	 * @param columnDefinition
	 */
	@Transactional
	void updateColumn(ColumnDefinition columnDefinition);

}
