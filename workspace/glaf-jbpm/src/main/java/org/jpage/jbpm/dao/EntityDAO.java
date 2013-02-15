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

package org.jpage.jbpm.dao;

import java.util.List;

import org.jpage.core.query.paging.Page;
import org.jpage.persistence.SqlExecutor;

 

public interface EntityDAO  {

	/**
	 * �������ݿ�����
	 * 
	 * @param con
	 */
	void setConnection(java.sql.Connection con);

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
	Page getPage(int pageNo, int pageSize, SqlExecutor countExecutor,
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
