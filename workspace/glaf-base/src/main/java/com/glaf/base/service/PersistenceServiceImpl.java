package com.glaf.base.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.type.Type;

import com.glaf.base.dao.AbstractSpringDao;
import com.glaf.base.utils.PageResult;

/**
 * �־÷���ӿڣ�ʵ��CRUD��������
 * 
 */
public class PersistenceServiceImpl implements PersistenceService {

	private AbstractSpringDao abstractDao;

	/**
	 * ����
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	public boolean create(Object obj) {
		return abstractDao.create(obj);
	}

	/**
	 * ɾ��
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	public boolean delete(Object obj) {
		return abstractDao.delete(obj);
	}

	/**
	 * ����ɾ��
	 * 
	 * @param c
	 */
	public boolean deleteAll(Collection c) {
		return abstractDao.deleteAll(c);
	}

	/**
	 * ִ��������������delete��update
	 * 
	 * @param hsql
	 *            hsql���
	 */
	public boolean execute(final String hsql) {
		return abstractDao.execute(hsql);
	}

	/**
	 * ִ��������������delete��update
	 * 
	 * @param hsql
	 *            hsql���
	 */
	public boolean execute(final String hsql, final Object[] values,
			final Type[] types) {
		return abstractDao.execute(hsql, values, types);
	}

	/**
	 * ִ��sql���
	 * 
	 * @param sql
	 * @return
	 */
	public boolean executeSQL(final String sql) {
		return abstractDao.executeSQL(sql);
	}

	public boolean executeSQL(final String sql, final List values) {
		return abstractDao.executeSQL(sql, values);
	}

	/**
	 * ��ѯ
	 * 
	 * @param clazz
	 *            Class
	 * @param id
	 *            Long
	 * @return Object
	 */
	public Object find(Class clazz, Long id) {
		return abstractDao.find(clazz, id);
	}

	/**
	 * ����sql�ü�¼��
	 * 
	 * @param sql
	 * @return
	 */
	public double getCountBySQL(String sql) {
		return abstractDao.getCountBySQL(sql);
	}

	public double getDoubleBySQL(String sql) {
		return abstractDao.getDoubleBySQL(sql);
	}

	/**
	 * 
	 * @param detachedCriteria
	 * @return
	 */
	public List getList(final DetachedCriteria detachedCriteria) {
		return abstractDao.getList(detachedCriteria);
	}

	/**
	 * 
	 * @param criteria
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageResult getList(final DetachedCriteria detachedCriteria,
			final int pageNo, final int pageSize) {
		return abstractDao.getList(detachedCriteria, pageNo, pageSize);
	}

	public List getList(final String sql, final Map params) {
		return abstractDao.getList(sql, params);
	}

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
	public PageResult getList(String query, Object args[], int pageNo,
			int pageSize, int count) {
		return abstractDao.getList(query, args, pageNo, pageSize, count);
	}

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
	public List getList(String query, Object[] values, Type[] types) {
		return abstractDao.getList(query, values, types);
	}

	/**
	 * ִ��sql���
	 * 
	 * @param sql
	 * @return
	 */
	public List getListBySQL(final String sql) {
		return abstractDao.getListBySQL(sql);
	}

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
	public PageResult getListBySQL(String query, int pageNo, int pageSize,
			int count) {
		return abstractDao.getListBySQL(query, pageNo, pageSize, count);
	}

	/**
	 * ��������һ������
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public Object getObject(Class clazz, Long id) {
		return abstractDao.getObject(clazz, id);
	}

	/**
	 * ��Query �õ��б��¼���ܼ�¼����
	 * 
	 * @param query
	 * @param values
	 * @param types
	 * @return
	 */
	public int getResutlTotalByQuery(String query, Object[] values, Type[] types) {
		return abstractDao.getResutlTotalByQuery(query, values, types);
	}

	public Object load(Class clazz, Long id) {
		return abstractDao.load(clazz, id);
	}

	public boolean saveAll(java.util.Collection rows) {
		return abstractDao.saveAll(rows);
	}

	/**
	 * ��������
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	public boolean saveOrUpdate(Object obj) {
		return abstractDao.saveOrUpdate(obj);
	}

	public void setAbstractDao(AbstractSpringDao abstractDao) {
		this.abstractDao = abstractDao;
	}

	/**
	 * ����
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	public boolean update(Object obj) {
		return abstractDao.update(obj);
	}

}
