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
