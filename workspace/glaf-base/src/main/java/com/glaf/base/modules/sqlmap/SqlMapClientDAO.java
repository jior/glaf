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

import org.jpage.core.query.paging.Page;
import org.jpage.persistence.Executor;

public interface SqlMapClientDAO {

	/**
	 * ����ִ�в���
	 * 
	 * @param rows
	 *            Executor����ļ���
	 * @see Executor
	 */
	public void execute(final List<Executor> rows);

	/**
	 * ����һ����¼
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	public void insertObject(String statementId, Object parameterObject);

	/**
	 * �޸�һ����¼
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	public void updateObject(String statementId, Object parameterObject);

	/**
	 * ɾ��һ����¼
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	public void deleteObject(String statementId, Object parameterObject);

	/**
	 * ���������¼
	 * 
	 * @param statementId
	 * @param rows
	 */
	public void insertAll(final String statementId, final List<Object> rows);

	/**
	 * �޸Ķ�����¼
	 * 
	 * @param statementId
	 * @param rows
	 */
	public void updateAll(final String statementId, final List<Object> rows);

	/**
	 * ɾ��������¼
	 * 
	 * @param statementId
	 * @param rows
	 */
	public void deleteAll(final String statementId, final List<Object> rows);

	public Object queryForObject(String statementId);

	public Object queryForObject(String statementId, Object parameterObject);

	public Object queryForObject(String statementId, Object parameterObject,
			Object resultObject);

	public List<Object> getList(Executor executor);

	/**
	 * ��ȡĳҳ�ļ�¼
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param executor
	 * @return
	 */

	public List<Object> getList(int pageNo, int pageSize, Executor executor);

	/**
	 * ��ҳ��ѯ
	 * 
	 * @param currPageNo
	 * @param pageSize
	 * @param countExecutor
	 * @param queryExecutor
	 * @return
	 */
	public Page getPage(final int pageNo, final int pSize,
			final Executor countExecutor, final Executor queryExecutor);

}