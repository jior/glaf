package com.glaf.base.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.type.Type;

import com.glaf.base.dao.AbstractSpringDao;
import com.glaf.base.utils.PageResult;

/**
 * 持久服务接口，实现CRUD基本方法
 * 
 */
public class PersistenceServiceImpl implements PersistenceService {

	private AbstractSpringDao abstractDao;

	/**
	 * 保存
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	public boolean create(Object obj) {
		return abstractDao.create(obj);
	}

	/**
	 * 删除
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	public boolean delete(Object obj) {
		return abstractDao.delete(obj);
	}

	/**
	 * 批量删除
	 * 
	 * @param c
	 */
	public boolean deleteAll(Collection c) {
		return abstractDao.deleteAll(c);
	}

	/**
	 * 执行批量操作，如delete、update
	 * 
	 * @param hsql
	 *            hsql语句
	 */
	public boolean execute(final String hsql) {
		return abstractDao.execute(hsql);
	}

	/**
	 * 执行批量操作，如delete、update
	 * 
	 * @param hsql
	 *            hsql语句
	 */
	public boolean execute(final String hsql, final Object[] values,
			final Type[] types) {
		return abstractDao.execute(hsql, values, types);
	}

	/**
	 * 执行sql语句
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
	 * 查询
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
	 * 根据sql拿记录数
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
	 * 获取分页列表
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
	 * 获取列表
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
	 * 执行sql语句
	 * 
	 * @param sql
	 * @return
	 */
	public List getListBySQL(final String sql) {
		return abstractDao.getListBySQL(sql);
	}

	/**
	 * 获取分页列表
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
	 * 立即加载一个对象
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public Object getObject(Class clazz, Long id) {
		return abstractDao.getObject(clazz, id);
	}

	/**
	 * 由Query 得到列表记录的总记录数。
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
	 * 保存或更新
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
	 * 保存
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	public boolean update(Object obj) {
		return abstractDao.update(obj);
	}

}
