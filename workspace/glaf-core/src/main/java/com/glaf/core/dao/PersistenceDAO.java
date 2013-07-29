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

package com.glaf.core.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.type.Type;

import com.glaf.core.util.PageResult;

/**
 * DAO�ӿڣ���װCRUD��������
 * 
 */
public interface PersistenceDAO {

	/**
	 * ����
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	boolean create(Object obj);

	/**
	 * ɾ��
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	boolean delete(Object obj);

	/**
	 * ����ɾ��
	 * 
	 * @param c
	 */
	boolean deleteAll(Collection<?> c);

	/**
	 * ִ��������������delete��update
	 * 
	 * @param hsql
	 *            hsql���
	 */
	boolean execute(final String hsql);

	/**
	 * ִ��������������delete��update
	 * 
	 * @param hsql
	 *            hsql���
	 */
	boolean execute(final String hsql, final Object[] values, final Type[] types);

	/**
	 * ִ��sql���
	 * 
	 * @param sql
	 * @return
	 */
	boolean executeSQL(final String sql);

	/**
	 * ִ��sql���
	 * 
	 * @param sql
	 * @param values
	 * @return
	 */
	boolean executeSQL(final String sql, final List<Object> values);

	/**
	 * ��ѯ
	 * 
	 * @param clazz
	 *            Class
	 * @param id
	 *            Long
	 * @return Object
	 */
	Object find(Class<?> clazz, Long id);

	/**
	 * ����sql�ü�¼��
	 * 
	 * @param sql
	 * @return
	 */
	double getCountBySQL(String sql);

	double getDoubleBySQL(String sql);

	/**
	 * 
	 * @param detachedCriteria
	 * @return
	 */
	List<?> getList(final DetachedCriteria detachedCriteria);

	/**
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	List<?> getList(final String sql, final Map<String, Object> params);

	/**
	 * ��ȡ��ҳ�б�
	 * 
	 * @param query
	 *            String
	 * @param args
	 *            Object
	 * @param pageNo
	 *            int
	 * @param pageSize
	 *            int
	 * @param count
	 *            int
	 * @return PageResult
	 */
	PageResult getList(String query, Object args[], int pageNo, int pageSize,
			int count);

	/**
	 * ��ȡ�б�
	 * 
	 * @param query
	 *            String
	 * @param values
	 *            Object[]
	 * @param types
	 *            Type[]
	 * @return List
	 */
	List<?> getList(String query, Object[] values, Type[] types);

	/**
	 * ִ��sql���
	 * 
	 * @param sql
	 * @return
	 */
	List<?> getListBySQL(final String sql);

	/**
	 * ��ȡ��ҳ�б�
	 * 
	 * @param query
	 *            String
	 * @param pageNo
	 *            int
	 * @param pageSize
	 *            int
	 * @param count
	 *            int
	 * @return PageResult
	 */
	PageResult getListBySQL(String query, int pageNo, int pageSize, int count);

	/**
	 * ��������һ������
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	Object getObject(Class<?> clazz, Long id);

	/**
	 * ��Query �õ��б��¼���ܼ�¼����
	 * 
	 * @param query
	 * @param values
	 * @param types
	 * @return
	 */
	int getResutlTotalByQuery(String query, Object[] values, Type[] types);

	/**
	 * ����sql��ָ���ֶε�ֵ
	 * 
	 * @param sql
	 * @return
	 */
	String getStringBySQL(String sql);

	Object load(Class<?> clazz, Long id);

	boolean saveAll(java.util.Collection<?> rows);

	/**
	 * ��������
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	boolean saveOrUpdate(Object obj);

	/**
	 * ����
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	boolean update(Object obj);

}