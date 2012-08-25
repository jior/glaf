package com.glaf.base.modules.sqlmap;

import java.util.List;
import java.util.Map;

import org.jpage.core.query.paging.Page;
import org.jpage.persistence.Executor;

public interface SqlMapManager {

	public void executeBatch(List<Executor> rows);

	public List getSqlMapTmsList(String queryId, Map params);

	public void insertObject(String statementId, Object obj);

	public void updateObject(String statementId, Object obj);

	public void deleteObject(String statementId, Object obj);

	public void insertAll(String statementId, List<Object> rows);

	public void updateAll(String statementId, List<Object> rows);

	public void deleteAll(String statementId, List<Object> rows);

	public Object queryForObject(String statementId);

	public Object queryForObject(String statementId, Object parameterObject);

	public Object queryForObject(String statementId, Object parameterObject,
			Object resultObject);

	/**
	 * 查询
	 * 
	 * @param executor
	 * @return
	 */
	public List<Object> getList(Executor executor);

	/**
	 * 获取某页的记录
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param queryExecutor
	 * @return
	 */

	public List<Object> getList(int pageNo, int pageSize, Executor queryExecutor);

	/**
	 * 分页查询
	 * 
	 * @param currPageNo
	 * @param pageSize
	 * @param countExecutor
	 * @param queryExecutor
	 * @return
	 */
	public Page getPage(int currPageNo, int pageSize, Executor countExecutor,
			Executor queryExecutor);

}