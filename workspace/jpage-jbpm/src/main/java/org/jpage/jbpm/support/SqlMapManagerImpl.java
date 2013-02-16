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


package org.jpage.jbpm.support;

import java.util.List;

public class SqlMapManagerImpl implements SqlMapManager {

	private SqlMapClientDAO sqlMapClientDAO;

	public SqlMapManagerImpl() {

	}

	public SqlMapClientDAO getSqlMapClientDAO() {
		return sqlMapClientDAO;
	}

	public void setSqlMapClientDAO(SqlMapClientDAO sqlMapClientDAO) {
		this.sqlMapClientDAO = sqlMapClientDAO;
	}

	public void insertObject(String statementId, Object obj) {
		sqlMapClientDAO.insertObject(statementId, obj);
	}

	public void updateObject(String statementId, Object obj) {
		sqlMapClientDAO.updateObject(statementId, obj);
	}

	public void deleteObject(String statementId, Object obj) {
		sqlMapClientDAO.deleteObject(statementId, obj);
	}

	public Object queryForObject(String statementId) {
		return sqlMapClientDAO.queryForObject(statementId);
	}

	public Object queryForObject(String statementId, Object parameterObject) {
		return sqlMapClientDAO.queryForObject(statementId, parameterObject);
	}

	public Object queryForObject(String statementId, Object parameterObject,
			Object resultObject) {
		return sqlMapClientDAO.queryForObject(statementId, parameterObject,
				resultObject);
	}

	public Object queryForSingleObject(String statementId,
			Object parameterObject) {
		List rows = this.query(statementId, parameterObject);
		if (rows != null && rows.size() > 0) {
			return rows.get(0);
		}
		return null;
	}

	public List query(String statementId) {
		return sqlMapClientDAO.query(statementId, null);
	}

	public List query(String statementId, Object parameterObject) {
		return sqlMapClientDAO.query(statementId, parameterObject);
	}
}
