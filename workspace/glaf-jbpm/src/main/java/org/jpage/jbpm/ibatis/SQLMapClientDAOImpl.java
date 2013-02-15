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


package org.jpage.jbpm.ibatis;

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SQLMapClientDAOImpl extends SqlMapClientDaoSupport implements
		JbpmSqlMapDAO {
	private static final Log logger = LogFactory
			.getLog(SQLMapClientDAOImpl.class);

	public SQLMapClientDAOImpl() {

	}

	public void insertObject(String statementName, Object parameterObject) {
		getSqlMapClientTemplate().insert(statementName, parameterObject);
	}

	public void updateObject(String statementName, Object parameterObject) {
		getSqlMapClientTemplate().update(statementName, parameterObject);
	}

	public void deleteObject(String statementName, Object parameterObject) {
		getSqlMapClientTemplate().delete(statementName, parameterObject);
	}

	public Object queryForObject(String statementName, Object parameterObject) {
		return getSqlMapClientTemplate().queryForObject(statementName,
				parameterObject);
	}

	public java.util.List query(String statementName, Object parameterObject) {
		return getSqlMapClientTemplate().queryForList(statementName,
				parameterObject);
	}

	public int executeBatch(java.util.List rows) {
		try {
			int count = 0;
			Iterator iterator = rows.iterator();
			while (iterator.hasNext()) {
				SQLMap sqlmap = (SQLMap) iterator.next();
				String statementName = sqlmap.getName();
				String operation = sqlmap.getOperation();
				logger.debug("name:" + statementName + "\toperation:"
						+ operation);
				Object obj = sqlmap.getObject();
				logger.debug("->obj:" + obj);
				if (StringUtils.equalsIgnoreCase("insert", operation)) {
					this.insertObject(statementName, obj);
					logger.debug("insert " + statementName + " finished.");
					count++;
				} else if (StringUtils.equalsIgnoreCase("update", operation)) {
					this.updateObject(statementName, obj);
					logger.debug("update " + statementName + " finished.");
					count++;
				} else if (StringUtils.equalsIgnoreCase("delete", operation)) {
					this.deleteObject(statementName, obj);
					logger.debug("delete " + statementName + " finished.");
					count++;
				}
			}
			logger.debug("成功执行SQL数目:" + count);
			return count;
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			throw new SqlMapException(
					"SQLMap execute batch catch sql exception", ex);
		}
	}
}
