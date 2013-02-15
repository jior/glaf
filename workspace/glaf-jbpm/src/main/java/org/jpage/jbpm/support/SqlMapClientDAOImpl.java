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

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class SqlMapClientDAOImpl extends SqlMapClientDaoSupport implements
		SqlMapClientDAO {

	public void insertObject(String statementName, Object parameterObject) {
		getSqlMapClientTemplate().insert(statementName, parameterObject);
	}

	public void updateObject(String statementName, Object parameterObject) {
		getSqlMapClientTemplate().update(statementName, parameterObject);
	}

	public void deleteObject(String statementName, Object parameterObject) {
		getSqlMapClientTemplate().delete(statementName, parameterObject);
	}

	public Object queryForObject(String statementId) {
		return getSqlMapClientTemplate().queryForObject(statementId);
	}

	public Object queryForObject(String statementId, Object parameterObject) {
		return getSqlMapClientTemplate().queryForObject(statementId,
				parameterObject);
	}

	public Object queryForObject(String statementId, Object parameterObject,
			Object resultObject) {
		return getSqlMapClientTemplate().queryForObject(statementId,
				parameterObject, resultObject);
	}

	public java.util.List query(String statementName, Object parameterObject) {
		if (parameterObject != null) {
			return getSqlMapClientTemplate().queryForList(statementName,
					parameterObject);
		}
		return getSqlMapClientTemplate().queryForList(statementName);
	}

}
