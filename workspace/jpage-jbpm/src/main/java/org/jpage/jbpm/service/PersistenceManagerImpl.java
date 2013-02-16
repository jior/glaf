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
import org.jpage.jbpm.persistence.PersistenceDAO;
import org.jpage.persistence.Executor;

public class PersistenceManagerImpl implements PersistenceManager {

	private PersistenceDAO persistenceDAO;

	public PersistenceManagerImpl() {
	}

	public PersistenceDAO getPersistenceDAO() {
		return persistenceDAO;
	}

	public void setPersistenceDAO(PersistenceDAO persistenceDAO) {
		this.persistenceDAO = persistenceDAO;
	}

	/**
	 * 删除持久化对象
	 * 
	 * @param model
	 */
	public void delete(JbpmContext jbpmContext, Serializable model) {
		persistenceDAO.delete(jbpmContext, model);
	}

	/**
	 * 批量删除持久化对象
	 * 
	 * @param rows
	 */
	public void deleteAll(JbpmContext jbpmContext, Collection rows) {
		persistenceDAO.deleteAll(jbpmContext, rows);
	}

	/**
	 * 合并持久化对象
	 * 
	 * @param model
	 */
	public void merge(JbpmContext jbpmContext, Serializable model) {
		persistenceDAO.merge(jbpmContext, model);
	}

	/**
	 * 批量合并持久化对象
	 * 
	 * @param rows
	 */
	public void mergeAll(JbpmContext jbpmContext, Collection rows) {
		persistenceDAO.mergeAll(jbpmContext, rows);
	}

	/**
	 * 保存持久化对象
	 * 
	 * @param model
	 */
	public void persist(JbpmContext jbpmContext, Serializable model) {
		persistenceDAO.persist(jbpmContext, model);
	}

	/**
	 * 批量保存持久化对象
	 * 
	 * @param rows
	 */
	public void persistAll(JbpmContext jbpmContext, Collection rows) {
		persistenceDAO.persistAll(jbpmContext, rows);
	}

	/**
	 * 保存持久化对象
	 * 
	 * @param model
	 */
	public void save(JbpmContext jbpmContext, Serializable model) {
		persistenceDAO.save(jbpmContext, model);
	}

	/**
	 * 批量保存持久化对象
	 * 
	 * @param rows
	 */
	public void saveAll(JbpmContext jbpmContext, Collection rows) {
		persistenceDAO.saveAll(jbpmContext, rows);
	}

	/**
	 * 更新持久化对象
	 * 
	 * @param model
	 */
	public void update(JbpmContext jbpmContext, Serializable model) {
		persistenceDAO.update(jbpmContext, model);
	}

	/**
	 * 批量更新持久化对象
	 * 
	 * @param rows
	 */
	public void updateAll(JbpmContext jbpmContext, Collection rows) {
		persistenceDAO.updateAll(jbpmContext, rows);
	}

	/**
	 * 根据主键获取持久化对象
	 * 
	 * @param clazz
	 *            类名
	 * @param persistId
	 *            主键值
	 * @return
	 */
	public Object getPersistObject(JbpmContext jbpmContext, Class clazz,
			java.io.Serializable persistId) {
		return persistenceDAO.getPersistObject(jbpmContext, clazz, persistId);
	}

	/**
	 * 查询
	 * 
	 * @param queryExecutor
	 * @return
	 */
	public List query(JbpmContext jbpmContext, Executor queryExecutor) {
		return persistenceDAO.query(jbpmContext, queryExecutor);
	}

	/**
	 * 查询
	 * 
	 * @param currPageNo
	 * @param maxResults
	 * @param queryExecutor
	 * @return
	 */
	public List query(JbpmContext jbpmContext, int currPageNo, int maxResults,
			Executor queryExecutor) {
		return persistenceDAO.query(jbpmContext, currPageNo, maxResults,
				queryExecutor);
	}

	/**
	 * 分页查询
	 * 
	 * @param currPageNo
	 * @param pageSize
	 * @param countExecutor
	 * @param queryExecutor
	 * @return
	 */
	public Page getPage(JbpmContext jbpmContext, int currPageNo, int pageSize,
			Executor countExecutor, Executor queryExecutor) {
		return persistenceDAO.getPage(jbpmContext, currPageNo, pageSize,
				countExecutor, queryExecutor);
	}

}
