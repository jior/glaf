/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jpage.jbpm.persistence;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.jbpm.JbpmContext;
import org.jpage.core.query.paging.Page;
import org.jpage.persistence.Executor;

public interface PersistenceDAO {

	/**
	 * 删除持久化对象
	 * 
	 * @param model
	 */
	public void delete(JbpmContext jbpmContext, Serializable model);

	/**
	 * 批量删除持久化对象
	 * 
	 * @param rows
	 */
	public void deleteAll(JbpmContext jbpmContext, Collection rows);

	/**
	 * 合并持久化对象
	 * 
	 * @param model
	 */
	public void merge(JbpmContext jbpmContext, Serializable model);

	/**
	 * 批量合并持久化对象
	 * 
	 * @param rows
	 */
	public void mergeAll(JbpmContext jbpmContext, Collection rows);

	/**
	 * 保存持久化对象
	 * 
	 * @param model
	 */
	public void persist(JbpmContext jbpmContext, Serializable model);

	/**
	 * 批量保存持久化对象
	 * 
	 * @param rows
	 */
	public void persistAll(JbpmContext jbpmContext, Collection rows);

	/**
	 * 保存持久化对象
	 * 
	 * @param model
	 */
	public void save(JbpmContext jbpmContext, Serializable model);

	/**
	 * 批量保存持久化对象
	 * 
	 * @param rows
	 */
	public void saveAll(JbpmContext jbpmContext, Collection rows);

	/**
	 * 更新持久化对象
	 * 
	 * @param model
	 */
	public void update(JbpmContext jbpmContext, Serializable model);

	/**
	 * 批量更新持久化对象
	 * 
	 * @param rows
	 */
	public void updateAll(JbpmContext jbpmContext, Collection rows);

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
			java.io.Serializable persistId);

	/**
	 * 查询
	 * @param queryExecutor
	 * @return
	 */
	public List query(JbpmContext jbpmContext, Executor queryExecutor);

	/**
	 * 查询
	 * 
	 * @param currPageNo
	 * @param maxResults
	 * @param queryExecutor
	 * @return
	 */
	public List query(JbpmContext jbpmContext, int currPageNo, int maxResults,
			Executor queryExecutor);

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
			Executor countExecutor, Executor queryExecutor);

}
