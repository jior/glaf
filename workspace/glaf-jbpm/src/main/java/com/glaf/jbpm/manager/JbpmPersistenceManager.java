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

package com.glaf.jbpm.manager;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.jbpm.JbpmContext;

import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.util.Paging;
import com.glaf.jbpm.dao.JbpmEntityDAO;

public class JbpmPersistenceManager {

	private JbpmEntityDAO jbpmEntityDAO;

	public JbpmPersistenceManager() {
		jbpmEntityDAO = new JbpmEntityDAO();
	}

	public void setJbpmEntityDAO(JbpmEntityDAO jbpmEntityDAO) {
		this.jbpmEntityDAO = jbpmEntityDAO;
	}

	/**
	 * 删除持久化对象
	 * 
	 * @param model
	 */
	public void delete(JbpmContext jbpmContext, Serializable model) {
		jbpmEntityDAO.delete(jbpmContext, model);
	}

	/**
	 * 批量删除持久化对象
	 * 
	 * @param rows
	 */
	public void deleteAll(JbpmContext jbpmContext, Collection<Object> rows) {
		jbpmEntityDAO.deleteAll(jbpmContext, rows);
	}

	/**
	 * 保存持久化对象
	 * 
	 * @param model
	 */
	public void save(JbpmContext jbpmContext, Serializable model) {
		jbpmEntityDAO.save(jbpmContext, model);
	}

	/**
	 * 批量保存持久化对象
	 * 
	 * @param rows
	 */
	public void saveAll(JbpmContext jbpmContext, Collection<Object> rows) {
		jbpmEntityDAO.saveAll(jbpmContext, rows);
	}

	/**
	 * 更新持久化对象
	 * 
	 * @param model
	 */
	public void update(JbpmContext jbpmContext, Serializable model) {
		jbpmEntityDAO.update(jbpmContext, model);
	}

	/**
	 * 批量更新持久化对象
	 * 
	 * @param rows
	 */
	public void updateAll(JbpmContext jbpmContext, Collection<Object> rows) {
		jbpmEntityDAO.updateAll(jbpmContext, rows);
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
	public Object getPersistObject(JbpmContext jbpmContext, Class<?> clazz,
			java.io.Serializable persistId) {
		return jbpmEntityDAO.getPersistObject(jbpmContext, clazz, persistId);
	}

	/**
	 * 查询
	 * 
	 * @param queryExecutor
	 * @return
	 */
	public List<Object> getList(JbpmContext jbpmContext,
			SqlExecutor queryExecutor) {
		return jbpmEntityDAO.getList(jbpmContext, queryExecutor);
	}

	/**
	 * 查询
	 * 
	 * @param currPageNo
	 * @param maxResults
	 * @param queryExecutor
	 * @return
	 */
	public List<Object> getList(JbpmContext jbpmContext, int currPageNo,
			int maxResults, SqlExecutor queryExecutor) {
		return jbpmEntityDAO.getList(jbpmContext, currPageNo, maxResults,
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
	public Paging getPage(JbpmContext jbpmContext, int currPageNo,
			int pageSize, SqlExecutor countExecutor, SqlExecutor queryExecutor) {
		return jbpmEntityDAO.getPage(jbpmContext, currPageNo, pageSize,
				countExecutor, queryExecutor);
	}

}