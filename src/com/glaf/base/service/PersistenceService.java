package com.glaf.base.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.type.Type;

import com.glaf.base.utils.PageResult;

/**
 * �־÷���ӿڣ�ʵ��CRUD��������
 * 
 */
public interface PersistenceService {

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
	boolean deleteAll(Collection c);

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

	boolean executeSQL(final String sql, final List values);

	/**
	 * ��ѯ
	 * 
	 * @param clazz
	 *            Class
	 * @param id
	 *            Long
	 * @return Object
	 */
	Object find(Class clazz, Long id);

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
	List getList(final DetachedCriteria detachedCriteria);

	/**
	 * 
	 * @param criteria
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageResult getList(final DetachedCriteria detachedCriteria,
			final int pageNo, final int pageSize);

	List getList(final String sql, final Map params);

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
	List getList(String query, Object[] values, Type[] types);

	/**
	 * ִ��sql���
	 * 
	 * @param sql
	 * @return
	 */
	List getListBySQL(final String sql);

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
	Object getObject(Class clazz, Long id);

	/**
	 * ��Query �õ��б��¼���ܼ�¼����
	 * 
	 * @param query
	 * @param values
	 * @param types
	 * @return
	 */
	int getResutlTotalByQuery(String query, Object[] values, Type[] types);

	Object load(Class clazz, Long id);

	boolean saveAll(java.util.Collection rows);

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
