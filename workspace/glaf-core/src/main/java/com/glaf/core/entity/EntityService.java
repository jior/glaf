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

package com.glaf.core.entity;

import java.util.List;



import com.glaf.core.util.Paging;


public interface EntityService  {

	/**
	 * ɾ����¼
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	
	void delete(String statementId, Object parameterObject);

	/**
	 * ɾ��������¼
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	
	void deleteAll(String statementId, List<Object> rowIds);

	/**
	 * ���ݼ�¼����ɾ����¼
	 * 
	 * @param statementId
	 * @param rowId
	 */
	
	void deleteById(String statementId, Object rowId);

	/**
	 * ִ����������
	 * 
	 * @param sqlExecutors
	 */
	
	void executeBatch(List<SqlExecutor> sqlExecutors);

	/**
	 * ����������ȡ��¼
	 * 
	 * @param statementId
	 * @param parameterObject
	 * @return
	 */
	Object getById(String statementId, Object parameterObject);

	/**
	 * ��ȡ�ܼ�¼��
	 * 
	 * @param statementId
	 * @param parameterObject
	 * @return
	 */
	int getCount(String statementId, Object parameterObject);

	/**
	 * ��ȡһҳ����
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param queryExecutor
	 * @return
	 */
	List<Object> getList(int pageNo, int pageSize, SqlExecutor queryExecutor);

	/**
	 * ��ȡ���ݼ�
	 * 
	 * @param statementId
	 * @param parameterObject
	 * @return
	 */
	List<Object> getList(String statementId, Object parameterObject);

	/**
	 * ��ȡ��������
	 * 
	 * @param statementId
	 * @param parameterObject
	 * @return
	 */
	Object getSingleObject(String statementId, Object parameterObject);

	/**
	 * ��ȡһҳ��¼
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
	 * ����һ����¼
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	
	void insert(String statementId, Object parameterObject);

	/**
	 * ���������¼
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	
	void insertAll(String statementId, List<Object> rows);

	/**
	 * �޸�һ����¼
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	
	void update(String statementId, Object parameterObject);

	/**
	 * �޸Ķ�����¼
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	
	void updateAll(String statementId, List<Object> rows);

}