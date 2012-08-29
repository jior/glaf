package com.glaf.base.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.type.Type;

import com.glaf.base.utils.PageResult;

/**
 * 持久服务接口，实现CRUD基本方法
 * 
 */
public interface PersistenceService {

	/**
	 * 保存
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	boolean create(Object obj);

	/**
	 * 删除
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	boolean delete(Object obj);

	/**
	 * 批量删除
	 * 
	 * @param c
	 */
	boolean deleteAll(Collection c);

	/**
	 * 执行批量操作，如delete、update
	 * 
	 * @param hsql
	 *            hsql语句
	 */
	boolean execute(final String hsql);

	/**
	 * 执行批量操作，如delete、update
	 * 
	 * @param hsql
	 *            hsql语句
	 */
	boolean execute(final String hsql, final Object[] values, final Type[] types);

	/**
	 * 执行sql语句
	 * 
	 * @param sql
	 * @return
	 */
	boolean executeSQL(final String sql);

	boolean executeSQL(final String sql, final List values);

	/**
	 * 查询
	 * 
	 * @param clazz
	 *            Class
	 * @param id
	 *            Long
	 * @return Object
	 */
	Object find(Class clazz, Long id);

	/**
	 * 根据sql拿记录数
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
	PageResult getList(String query, Object args[], int pageNo, int pageSize,
			int count);

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
	List getList(String query, Object[] values, Type[] types);

	/**
	 * 执行sql语句
	 * 
	 * @param sql
	 * @return
	 */
	List getListBySQL(final String sql);

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
	PageResult getListBySQL(String query, int pageNo, int pageSize, int count);

	/**
	 * 立即加载一个对象
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	Object getObject(Class clazz, Long id);

	/**
	 * 由Query 得到列表记录的总记录数。
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
	 * 保存或更新
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	boolean saveOrUpdate(Object obj);

	/**
	 * 保存
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	boolean update(Object obj);

}
