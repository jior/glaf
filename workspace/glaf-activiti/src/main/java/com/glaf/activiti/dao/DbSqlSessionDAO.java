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

package com.glaf.activiti.dao;

import java.util.List;

import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.db.DbSqlSession;

public class DbSqlSessionDAO {

	protected final DbSqlSession dbSqlSession;

	public DbSqlSessionDAO(DbSqlSession dbSqlSession) {
		this.dbSqlSession = dbSqlSession;
	}

	public DbSqlSession getDbSqlSession() {
		return dbSqlSession;
	}

	@SuppressWarnings("rawtypes")
	public List selectList(String statement) {
		return getDbSqlSession().selectList(statement);
	}

	@SuppressWarnings("rawtypes")
	public List selectList(String statement, Object parameter) {
		return getDbSqlSession().selectList(statement, parameter);
	}

	@SuppressWarnings("rawtypes")
	public List selectList(String statement, Object parameter, int firstResult,
			int maxResults) {
		Page page = new Page(firstResult, maxResults);
		return getDbSqlSession().selectList(statement, parameter, page);
	}

	public Object selectOne(String statement, Object parameter) {
		return getDbSqlSession().selectOne(statement, parameter);
	}

}