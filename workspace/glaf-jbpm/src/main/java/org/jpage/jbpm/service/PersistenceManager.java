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


package org.jpage.jbpm.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.jbpm.JbpmContext;
import org.jpage.core.query.paging.Page;
import org.jpage.persistence.Executor;

public interface PersistenceManager {

	/**
	 * ɾ���־û�����
	 * 
	 * @param model
	 */
	public void delete(JbpmContext jbpmContext, Serializable model);

	/**
	 * ����ɾ���־û�����
	 * 
	 * @param rows
	 */
	public void deleteAll(JbpmContext jbpmContext, Collection rows);

	/**
	 * �ϲ��־û�����
	 * 
	 * @param model
	 */
	public void merge(JbpmContext jbpmContext, Serializable model);

	/**
	 * �����ϲ��־û�����
	 * 
	 * @param rows
	 */
	public void mergeAll(JbpmContext jbpmContext, Collection rows);

	/**
	 * ����־û�����
	 * 
	 * @param model
	 */
	public void persist(JbpmContext jbpmContext, Serializable model);

	/**
	 * ��������־û�����
	 * 
	 * @param rows
	 */
	public void persistAll(JbpmContext jbpmContext, Collection rows);

	/**
	 * ����־û�����
	 * 
	 * @param model
	 */
	public void save(JbpmContext jbpmContext, Serializable model);

	/**
	 * ��������־û�����
	 * 
	 * @param rows
	 */
	public void saveAll(JbpmContext jbpmContext, Collection rows);

	/**
	 * ���³־û�����
	 * 
	 * @param model
	 */
	public void update(JbpmContext jbpmContext, Serializable model);

	/**
	 * �������³־û�����
	 * 
	 * @param rows
	 */
	public void updateAll(JbpmContext jbpmContext, Collection rows);
	
	
	/**
	 * ����������ȡ�־û�����
	 * 
	 * @param clazz
	 *            ����
	 * @param persistId
	 *            ����ֵ
	 * @return
	 */
	public Object getPersistObject(JbpmContext jbpmContext, Class clazz,
			java.io.Serializable persistId);
	
	

	/**
	 * ��ѯ
	 * @param queryExecutor
	 * @return
	 */
	public List query(JbpmContext jbpmContext, Executor queryExecutor);

	/**
	 * ��ѯ
	 * 
	 * @param currPageNo
	 * @param maxResults
	 * @param queryExecutor
	 * @return
	 */
	public List query(JbpmContext jbpmContext, int currPageNo, int maxResults,
			Executor queryExecutor);

	/**
	 * ��ҳ��ѯ
	 * 
	 * @param currPageNo
	 * @param pageSize
	 * @param countExecutor
	 * @param queryExecutor
	 * @return
	 */
	public Page getPage(JbpmContext jbpmContext, int currPageNo, int pageSize,
			Executor countExecutor, Executor queryExecutor);

}
