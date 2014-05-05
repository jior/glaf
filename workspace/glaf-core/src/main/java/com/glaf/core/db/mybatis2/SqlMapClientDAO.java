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
package com.glaf.core.db.mybatis2;

import java.util.List;

import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.id.IdBlock;
import com.glaf.core.util.Paging;

public interface SqlMapClientDAO {

	void setConnection(java.sql.Connection connection);

	/**
	 * 删除记录
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	void delete(String statementId, Object parameterObject);

	/**
	 * 删除多条记录
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	void deleteAll(String statementId, List<Object> rowIds);

	/**
	 * 根据记录主键删除记录
	 * 
	 * @param statementId
	 * @param rowId
	 */
	void deleteById(String statementId, Object rowId);

	/**
	 * 执行批量更新
	 * 
	 * @param sqlExecutors
	 */
	void executeBatch(List<SqlExecutor> sqlExecutors);

	/**
	 * 根据主键获取记录
	 * 
	 * @param statementId
	 * @param parameterObject
	 * @return
	 */
	Object getById(String statementId, Object parameterObject);

	/**
	 * 获取总记录数
	 * 
	 * @param statementId
	 * @param parameterObject
	 * @return
	 */
	int getCount(String statementId, Object parameterObject);

	/**
	 * 获取一页数据
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param queryExecutor
	 * @return
	 */
	List<Object> getList(int pageNo, int pageSize, SqlExecutor queryExecutor);

	/**
	 * 获取数据集
	 * 
	 * @param statementId
	 * @param parameterObject
	 * @return
	 */
	List<Object> getList(String statementId, Object parameterObject);

	/**
	 * 获取单个对象
	 * 
	 * @param statementId
	 * @param parameterObject
	 * @return
	 */
	Object getSingleObject(String statementId, Object parameterObject);

	/**
	 * 获取一页记录
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param countExecutor
	 * @param queryExecutor
	 * @return
	 */
	Paging getPage(int pageNo, int pageSize, SqlExecutor countExecutor,
			SqlExecutor queryExecutor);

	/**
	 * 获取下一条记录编号
	 * 
	 * @return
	 */
	IdBlock nextDbidBlock();
	
	/**
	 * 获取下一条记录编号
	 * @param name 名称
	 * @return
	 */
	IdBlock nextDbidBlock(String name);

	/**
	 * 插入一条记录
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	void insert(String statementId, Object parameterObject);

	/**
	 * 插入多条记录
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	void insertAll(String statementId, List<Object> rows);

	/**
	 * 修改一条记录
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	void update(String statementId, Object parameterObject);

	/**
	 * 修改多条记录
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	void updateAll(String statementId, List<Object> rows);

}