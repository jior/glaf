package com.glaf.base.modules.sqlmap;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpage.core.query.paging.Page;
import org.jpage.persistence.Executor;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapExecutor;
 

public class SqlMapClientDAOImpl extends SqlMapClientDaoSupport implements SqlMapClientDAO  {
	protected static final Log logger = LogFactory
			.getLog(SqlMapClientDAO.class);

	public SqlMapClientDAOImpl() {

	}

	public void debug(String statementName, Object parameterObject) {

		 

	}

	/**
	 * 批量执行操作
	 * 
	 * @param rows
	 *            Executor对象的集合
	 * @see Executor
	 */
	public void execute(final List<Executor> rows) {
		getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				Iterator<Executor> iterator = rows.iterator();
				while (iterator.hasNext()) {
					Executor model = (Executor) iterator.next();
					String query = model.getQuery();
					query = query.trim().toLowerCase();
					List<Object> list = model.getListValues();
					if (list == null || list.size() == 0) {
						continue;
					}
					Iterator<Object> iter = list.iterator();
					while (iter.hasNext()) {
						Object object = iter.next();
						if (query.startsWith("insert")) {
							executor.insert(model.getQuery(), object);
						} else if (query.startsWith("update")) {
							executor.update(model.getQuery(), object);
						} else if (query.startsWith("delete")) {
							executor.delete(model.getQuery(), object);
						}
					}
				}
				executor.executeBatch();
				return null;
			}
		});
	}

	/**
	 * 插入一条记录
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	public void insertObject(String statementId, Object parameterObject) {
		debug(statementId, parameterObject);
		getSqlMapClientTemplate().insert(statementId, parameterObject);
		
	}

	/**
	 * 修改一条记录
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	public void updateObject(String statementId, Object parameterObject) {
		debug(statementId, parameterObject);
		getSqlMapClientTemplate().update(statementId, parameterObject);
		
	}

	/**
	 * 删除一条记录
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	public void deleteObject(String statementId, Object parameterObject) {
		debug(statementId, parameterObject);
		getSqlMapClientTemplate().delete(statementId, parameterObject);
		
	}

	/**
	 * 插入多条记录
	 * 
	 * @param statementId
	 * @param rows
	 */
	public void insertAll(final String statementId, final List<Object> rows) {
		getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				Iterator<Object> iterator = rows.iterator();
				while (iterator.hasNext()) {
					Object object = iterator.next();
					debug(statementId, object);
					executor.insert(statementId, object);
				}
				executor.executeBatch();
				return null;
			}
		});
	}

	/**
	 * 修改多条记录
	 * 
	 * @param statementId
	 * @param rows
	 */
	public void updateAll(final String statementId, final List<Object> rows) {
		getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				Iterator<Object> iterator = rows.iterator();
				while (iterator.hasNext()) {
					Object object = iterator.next();
					debug(statementId, object);
					executor.update(statementId, object);
				}
				executor.executeBatch();
				return null;
			}
		});
	}

	/**
	 * 删除多条记录
	 * 
	 * @param statementId
	 * @param rows
	 */
	public void deleteAll(final String statementId, final List<Object> rows) {
		getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				Iterator<Object> iterator = rows.iterator();
				while (iterator.hasNext()) {
					Object object = iterator.next();
					debug(statementId, object);
					executor.delete(statementId, object);
				}
				executor.executeBatch();
				return null;
			}
		});
	}

	public Object queryForObject(String statementId) {
		return getSqlMapClientTemplate().queryForObject(statementId, null);
	}

	public Object queryForObject(String statementId, Object parameterObject) {
		return getSqlMapClientTemplate().queryForObject(statementId,
				parameterObject);
	}

	public Object queryForObject(String statementId, Object parameterObject,
			Object resultObject) {
		return getSqlMapClientTemplate().queryForObject(statementId,
				parameterObject, resultObject);
	}

	public List<Object> getList(Executor executor) {
		List<?> xList = null;
		Map<String, Object> params = executor.getParams();
		if (params != null && params.size() > 0) {
			debug(executor.getQuery(), params);
			xList = getSqlMapClientTemplate().queryForList(executor.getQuery(),
					params);
		} else {
			List<Object> list = executor.getListValues();
			if (list != null && list.size() > 0) {
				debug(executor.getQuery(), list.get(0));
				xList = getSqlMapClientTemplate().queryForList(
						executor.getQuery(), list.get(0));
				
			} else {
				debug(executor.getQuery(), null);
				xList = getSqlMapClientTemplate().queryForList(
						executor.getQuery(), null);
				
			}
		}
//		if (xList != null && xList.size() > 0) {
//			for (int i = 0; i < xList.size(); i++) {
//				rows.add(xList.get(i));
//			}
//		}
		return (List<Object>) xList;
	}

	/**
	 * 获取某页的记录
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param executor
	 * @return
	 */

	public List<Object> getList(int pageNo, int pageSize, Executor executor) {
		List<Object> rows = new ArrayList<Object>();
		List<?> xList = null;
		int begin = (pageNo - 1) * pageSize + 1;
		Map<String, Object> params = executor.getParams();
		if (params != null && params.size() > 0) {
			xList = getSqlMapClientTemplate().queryForList(executor.getQuery(),
					params, begin, pageSize);
		} else {
			List<Object> list = executor.getListValues();
			if (list != null && list.size() > 0) {
				debug(executor.getQuery(), list.get(0));
				xList = getSqlMapClientTemplate().queryForList(
						executor.getQuery(), list.get(0), begin, pageSize);
				
			} else {
				debug(executor.getQuery(), null);
				xList = getSqlMapClientTemplate().queryForList(
						executor.getQuery(), null, begin, pageSize);
			}
		}
		if (xList != null && xList.size() > 0) {
			for (int i = 0; i < xList.size(); i++) {
				rows.add(xList.get(i));
			}
		}
		return rows;
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
	public Page getPage(final int pageNo, final int pSize,
			final Executor countExecutor, final Executor queryExecutor) {
		return (Page) getSqlMapClientTemplate().execute(
				new SqlMapClientCallback() {
					public Object doInSqlMapClient(SqlMapExecutor executor)
							throws SQLException {
						if (logger.isDebugEnabled()) {
							logger.debug("count executor:" + countExecutor);
							logger.debug("query executor:" + queryExecutor);
						}
						Page page = new Page();
						int currPageNo = pageNo;
						int pageSize = pSize;
						if (pageSize <= 0) {
							pageSize = Page.DEFAULT_PAGE_SIZE;
						}
						if (currPageNo <= 0) {
							currPageNo = 1;
						}

						Map<String, Object> params = countExecutor.getParams();

						Object obj = null;
						int totalCount = 0;

						if (params != null && params.size() > 0) {
							obj = executor.queryForObject(countExecutor
									.getQuery(), params);
						} else {
							List<Object> list = countExecutor.getListValues();
							if (list != null && list.size() > 0) {
								obj = executor.queryForObject(countExecutor
										.getQuery(), list.get(0));
							} else {
								obj = executor.queryForObject(countExecutor
										.getQuery(), null);
							}
						}

						if (obj instanceof Integer) {
							Integer iCount = (Integer) obj;
							totalCount = iCount.intValue();
						} else if (obj instanceof Long) {
							Long iCount = (Long) obj;
							totalCount = iCount.intValue();
						} else if (obj instanceof BigDecimal) {
							BigDecimal bg = (BigDecimal) obj;
							totalCount = bg.intValue();
						} else if (obj instanceof BigInteger) {
							BigInteger bi = (BigInteger) obj;
							totalCount = bi.intValue();
						}

						if (totalCount == 0) {
							page.setRows(new ArrayList<Object>());
							page.setCurrentPage(0);
							page.setPageSize(0);
							page.setTotalRecordCount(0);
							return page;
						}

						page.setTotalRecordCount(totalCount);

						int maxPageNo = (page.getTotalRecordCount() + (pageSize - 1))
								/ pageSize;
						if (currPageNo > maxPageNo) {
							currPageNo = maxPageNo;
						}

						List<Object> rows = new ArrayList<Object>();
						List<?> xList = null;

						Map<String, Object> queryParams = queryExecutor
								.getParams();

						int begin = (pageNo - 1) * pageSize + 1;
						if (queryParams != null && queryParams.size() > 0) {
							xList = getSqlMapClientTemplate().queryForList(
									queryExecutor.getQuery(), queryParams,
									begin, pageSize);
						} else {
							List<Object> list = queryExecutor.getListValues();
							if (list != null && list.size() > 0) {
								xList = getSqlMapClientTemplate().queryForList(
										queryExecutor.getQuery(), list.get(0),
										begin, pageSize);
							} else {
								xList = getSqlMapClientTemplate().queryForList(
										queryExecutor.getQuery(), null, begin,
										pageSize);
							}
						}

						if (xList != null && xList.size() > 0) {
							for (int i = 0; i < xList.size(); i++) {
								rows.add(xList.get(i));
							}
						}

						page.setRows(rows);
						page.setPageSize(pageSize);
						page.setCurrentPage(currPageNo);

						return page;
					}
				});

	}

}
