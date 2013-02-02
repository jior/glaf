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

package com.glaf.base.modules.sqlmap;

import java.util.List;
import java.util.Map;

import org.jpage.core.query.paging.Page;
import org.jpage.persistence.Executor;

public interface SqlMapManager {

	public void executeBatch(List<Executor> rows);

	public List getSqlMapList(String queryId, Map params);

	public void insertObject(String statementId, Object obj);

	public void updateObject(String statementId, Object obj);

	public void deleteObject(String statementId, Object obj);

	public void insertAll(String statementId, List<Object> rows);

	public void updateAll(String statementId, List<Object> rows);

	public void deleteAll(String statementId, List<Object> rows);

	public Object queryForObject(String statementId);

	public Object queryForObject(String statementId, Object parameterObject);

	public Object queryForObject(String statementId, Object parameterObject,
			Object resultObject);

	/**
	 * 查询
	 * 
	 * @param executor
	 * @return
	 */
	public List<Object> getList(Executor executor);

	/**
	 * 获取某页的记录
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param queryExecutor
	 * @return
	 */

	public List<Object> getList(int pageNo, int pageSize, Executor queryExecutor);

	/**
	 * 分页查询
	 * 
	 * @param currPageNo
	 * @param pageSize
	 * @param countExecutor
	 * @param queryExecutor
	 * @return
	 */
	public Page getPage(int currPageNo, int pageSize, Executor countExecutor,
			Executor queryExecutor);

}