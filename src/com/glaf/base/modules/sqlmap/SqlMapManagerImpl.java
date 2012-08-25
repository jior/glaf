package com.glaf.base.modules.sqlmap;

import java.util.List;
import java.util.Map;

import org.jpage.core.query.paging.Page;
import org.jpage.persistence.Executor;

public class SqlMapManagerImpl implements SqlMapManager {

	private SqlMapClientDAO sqlMapClientDAO;

	public SqlMapManagerImpl() {

	}

	public SqlMapClientDAO getSqlMapClientDAO() {
		return sqlMapClientDAO;
	}

	public void setSqlMapClientDAO(SqlMapClientDAO sqlMapClientDAO) {
		this.sqlMapClientDAO = sqlMapClientDAO;
	}

	public void executeBatch(List<Executor> rows) {
		if (rows != null && rows.size() > 0) {
			sqlMapClientDAO.execute(rows);
		}
	}
	
	public List getSqlMapTmsList(String queryId, Map params) {
		Executor executor = new Executor();
		executor.setQuery(queryId);
		executor.setParams(params);
		List rows = sqlMapClientDAO.getList(executor);
		return rows;
	}
	public void insertObject(String statementId, Object obj) {
		sqlMapClientDAO.insertObject(statementId, obj);
	}

	public void updateObject(String statementId, Object obj) {
		sqlMapClientDAO.updateObject(statementId, obj);
	}

	public void deleteObject(String statementId, Object obj) {
		sqlMapClientDAO.deleteObject(statementId, obj);
	}

	public void insertAll(String statementId, List<Object> rows) {
		if (rows != null && rows.size() > 0) {
			sqlMapClientDAO.insertAll(statementId, rows);
		}
	}

	public void updateAll(String statementId, List<Object> rows) {
		if (rows != null && rows.size() > 0) {
			sqlMapClientDAO.updateAll(statementId, rows);
		}
	}

	public void deleteAll(String statementId, List<Object> rows) {
		if (rows != null && rows.size() > 0) {
			sqlMapClientDAO.deleteAll(statementId, rows);
		}
	}

	public Object queryForObject(String statementId) {
		return sqlMapClientDAO.queryForObject(statementId);
	}

	public Object queryForObject(String statementId, Object parameterObject) {
		return sqlMapClientDAO.queryForObject(statementId, parameterObject);
	}

	public Object queryForObject(String statementId, Object parameterObject,
			Object resultObject) {
		return sqlMapClientDAO.queryForObject(statementId, parameterObject,
				resultObject);
	}

	/**
	 * 查询
	 * 
	 * @param executor
	 * @return
	 */
	public List<Object> getList(Executor executor) {
		return sqlMapClientDAO.getList(executor);
	}

	/**
	 * 获取某页的记录
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param queryExecutor
	 * @return
	 */

	public List<Object> getList(int pageNo, int pageSize, Executor queryExecutor) {
		return sqlMapClientDAO.getList(pageNo, pageSize, queryExecutor);
	}

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
			Executor queryExecutor) {
		return sqlMapClientDAO.getPage(currPageNo, pageSize, countExecutor,
				queryExecutor);
	}

}