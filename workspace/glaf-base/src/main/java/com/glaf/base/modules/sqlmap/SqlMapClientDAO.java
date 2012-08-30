package com.glaf.base.modules.sqlmap;

import java.util.List;

import org.jpage.core.query.paging.Page;
import org.jpage.persistence.Executor;

public interface SqlMapClientDAO {

	/**
	 * 批量执行操作
	 * 
	 * @param rows
	 *            Executor对象的集合
	 * @see Executor
	 */
	public void execute(final List<Executor> rows);

	/**
	 * 插入一条记录
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	public void insertObject(String statementId, Object parameterObject);

	/**
	 * 修改一条记录
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	public void updateObject(String statementId, Object parameterObject);

	/**
	 * 删除一条记录
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	public void deleteObject(String statementId, Object parameterObject);

	/**
	 * 插入多条记录
	 * 
	 * @param statementId
	 * @param rows
	 */
	public void insertAll(final String statementId, final List<Object> rows);

	/**
	 * 修改多条记录
	 * 
	 * @param statementId
	 * @param rows
	 */
	public void updateAll(final String statementId, final List<Object> rows);

	/**
	 * 删除多条记录
	 * 
	 * @param statementId
	 * @param rows
	 */
	public void deleteAll(final String statementId, final List<Object> rows);

	public Object queryForObject(String statementId);

	public Object queryForObject(String statementId, Object parameterObject);

	public Object queryForObject(String statementId, Object parameterObject,
			Object resultObject);

	public List<Object> getList(Executor executor);

	/**
	 * 获取某页的记录
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param executor
	 * @return
	 */

	public List<Object> getList(int pageNo, int pageSize, Executor executor);

	/**
	 * 分页查询
	 * 
	 * @param currPageNo
	 * @param pageSize
	 * @param countExecutor
	 * @param queryExecutor
	 * @return
	 */
	public Page getPage(final int pageNo, final int pSize,
			final Executor countExecutor, final Executor queryExecutor);

}
