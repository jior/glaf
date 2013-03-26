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



package com.glaf.core.service.impl;

import java.util.*;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.id.IdBlock;
import com.glaf.core.service.EntityService;
import com.glaf.core.util.Paging;

public class MyBatisEntityService implements EntityService {

	protected EntityDAO entityDAO;

	public MyBatisEntityService() {

	}

	public void delete(String statementId, Object parameter) {
		entityDAO.delete(statementId, parameter);
	}

	public void deleteAll(String statementId, List<Object> rows) {
		if (rows != null && rows.size() > 0) {
			entityDAO.deleteAll(statementId, rows);
		}
	}

	public void deleteById(String statementId, Object rowId) {
		entityDAO.deleteById(statementId, rowId);
	}

	public void executeBatch(List<SqlExecutor> sqlExecutors) {
		entityDAO.executeBatch(sqlExecutors);
	}

	public Object getById(String statementId, Object parameterObject) {
		return entityDAO.getById(statementId, parameterObject);
	}

	public int getCount(String statementId, Object parameterObject) {
		return entityDAO.getCount(statementId, parameterObject);
	}

	public List<Object> getList(int pageNo, int pageSize,
			SqlExecutor queryExecutor) {
		return entityDAO.getList(pageNo, pageSize, queryExecutor);
	}

	/**
	 * ≤È—Ø
	 * 
	 * @param queryId
	 * @param params
	 * @return
	 */
	public List<Object> getList(String statementId, Object parameter) {
		return entityDAO.getList(statementId, parameter);
	}

	public Paging getPage(int pageNo, int pageSize, SqlExecutor countExecutor,
			SqlExecutor queryExecutor) {
		return entityDAO.getPage(pageNo, pageSize, countExecutor, queryExecutor);
	}
	
	public Object getSingleObject(String statementId, Object parameterObject) {
		return entityDAO.getSingleObject(statementId, parameterObject);
	}

	public void insert(String statementId, Object obj) {
		entityDAO.insert(statementId, obj);
	}

	public void insertAll(String statementId, List<Object> rows) {
		if (rows != null && rows.size() > 0) {
			entityDAO.insertAll(statementId, rows);
		}
	}

	public IdBlock nextDbidBlock(String name){
		return entityDAO.nextDbidBlock(name);
	}

  
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	public void update(String statementId, Object obj) {
		entityDAO.update(statementId, obj);
	}

	public void updateAll(String statementId, List<Object> rows) {
		if (rows != null && rows.size() > 0) {
			entityDAO.updateAll(statementId, rows);
		}
	}

}