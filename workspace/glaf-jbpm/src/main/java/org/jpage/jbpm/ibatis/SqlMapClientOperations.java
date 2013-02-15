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

import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.event.RowHandler;

public interface SqlMapClientOperations {

	Object queryForObject(String statementName, Object parameterObject)
			throws SqlMapException;

	Object queryForObject(String statementName, Object parameterObject,
			Object resultObject) throws SqlMapException;

	List queryForList(String statementName, Object parameterObject)
			throws SqlMapException;

	List queryForList(String statementName, Object parameterObject,
			int skipResults, int maxResults) throws SqlMapException;

	void queryWithRowHandler(String statementName, Object parameterObject,
			RowHandler rowHandler) throws SqlMapException;

	Map queryForMap(String statementName, Object parameterObject,
			String keyProperty) throws SqlMapException;

	Map queryForMap(String statementName, Object parameterObject,
			String keyProperty, String valueProperty) throws SqlMapException;

	Object insert(String statementName, Object parameterObject)
			throws SqlMapException;

	int update(String statementName, Object parameterObject)
			throws SqlMapException;

	int delete(String statementName, Object parameterObject)
			throws SqlMapException;

	void update(String statementName, Object parameterObject,
			int requiredRowsAffected) throws SqlMapException;

	void delete(String statementName, Object parameterObject,
			int requiredRowsAffected) throws SqlMapException;

}
