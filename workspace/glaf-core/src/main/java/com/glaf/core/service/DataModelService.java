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

import java.util.Collection;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.base.DataModel;
import com.glaf.core.query.DataModelQuery;

@Transactional(readOnly = true)
public interface DataModelService {

	/**
	 * 删除多条记录
	 * 
	 * @param tableName
	 * @param businessKeys
	 */
	@Transactional
	void deleteAll(String tableName, Collection<String> businessKeys);

	/**
	 * 根据主键删除记录
	 * 
	 * @param tableName
	 * @param rowIds
	 */
	@Transactional
	void deleteAllById(String tableName, Collection<Long> rowIds);

	/**
	 * 获取数据模型对象
	 * 
	 * @param tableName
	 * @param id
	 * @return
	 */
	DataModel getDataModel(String tableName, Long id);

	/**
	 * 获取数据模型对象
	 * 
	 * @param tableName
	 * @param businessKey
	 * @return
	 */
	DataModel getDataModelByBusinessKey(String tableName, String businessKey);

	/**
	 * 获取数据模型对象
	 * 
	 * @param tableName
	 * @param processInstanceId
	 * @return
	 */
	DataModel getDataModelByProcessInstanceId(String tableName,
			String processInstanceId);

	/**
	 * 统计数据模型对象个数
	 * 
	 * @param query
	 * @return
	 */
	int getDataModelCount(DataModelQuery query);

	/**
	 * 保存数据对象实体
	 * 
	 * @param dataModel
	 */
	@Transactional
	void insert(DataModel dataModel);

	/**
	 * 获取一页数据对象
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param query
	 * @return
	 */
	List<DataModel> list(int pageNo, int pageSize, DataModelQuery query);

	/**
	 * 更新数据对象
	 * 
	 * @param dataModel
	 */
	@Transactional
	void update(DataModel dataModel);

}