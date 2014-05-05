/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.core.db.mybatis2;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.id.Dbid;
import com.glaf.core.id.IdBlock;
import com.glaf.core.util.LogUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ReflectUtils;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;

public class SqlMapClientDAOImpl implements SqlMapClientDAO {

	private static final Log logger = LogFactory
			.getLog(SqlMapClientDAOImpl.class);

	private SqlMapClientTemplate sqlMapClientTemplate = new SqlMapClientTemplate();

	private com.ibatis.sqlmap.engine.execution.SqlExecutor sqlExecutor;

	private SqlMapClient sqlMapClient;

	public SqlMapClientDAOImpl() {

	}

	public void delete(String statementId, Object parameterObject) {
		getSqlMapClientTemplate().delete(statementId, parameterObject);
	}

	public void deleteAll(String statementId, List<Object> rowIds) {
		getSqlMapClientTemplate().delete(statementId, rowIds);
	}

	public void deleteById(String statementId, Object rowId) {
		getSqlMapClientTemplate().delete(statementId, rowId);
	}

	public void executeBatch(List<SqlExecutor> sqlExecutors) {
		getSqlMapClientTemplate().executeBatch(sqlExecutors);
	}

	public Object getById(String statementId, Object parameterObject) {
		return getSqlMapClientTemplate().queryForObject(statementId,
				parameterObject);
	}

	public int getCount(String statementId, Object parameterObject) {
		int totalCount = 0;
		Object object = null;
		if (parameterObject != null) {
			object = getSqlMapClientTemplate().queryForObject(statementId,
					parameterObject);
		} else {
			object = getSqlMapClientTemplate()
					.queryForObject(statementId, null);
		}

		if (object instanceof Integer) {
			Integer iCount = (Integer) object;
			totalCount = iCount.intValue();
		} else if (object instanceof Long) {
			Long iCount = (Long) object;
			totalCount = iCount.intValue();
		} else if (object instanceof BigDecimal) {
			BigDecimal bg = (BigDecimal) object;
			totalCount = bg.intValue();
		} else if (object instanceof BigInteger) {
			BigInteger bi = (BigInteger) object;
			totalCount = bi.intValue();
		}

		return totalCount;
	}

	public List<Object> getList(int pageNo, int pageSize,
			SqlExecutor queryExecutor) {
		List<Object> rows = null;

		Object queryParams = queryExecutor.getParameter();

		int begin = (pageNo - 1) * pageSize + 1;

		if (queryParams != null) {
			rows = getSqlMapClientTemplate().queryForList(
					queryExecutor.getStatementId(), queryParams, begin,
					pageSize);
		} else {
			rows = getSqlMapClientTemplate().queryForList(
					queryExecutor.getStatementId(), null, begin, pageSize);
		}

		return rows;
	}

	public java.util.List<Object> getList(String statementId,
			Object parameterObject) {
		return getSqlMapClientTemplate().queryForList(statementId,
				parameterObject);
	}

	public Paging getPage(int pageNo, int pageSize, SqlExecutor countExecutor,
			SqlExecutor queryExecutor) {
		if (LogUtils.isDebug()) {
			logger.debug("count executor:" + countExecutor);
			logger.debug("query executor:" + queryExecutor);
		}
		Paging page = new Paging();

		if (pageSize <= 0) {
			pageSize = Paging.DEFAULT_PAGE_SIZE;
		}
		if (pageNo <= 0) {
			pageNo = 1;
		}

		Object params = countExecutor.getParameter();

		Object object = null;
		int totalCount = 0;

		if (params != null) {
			object = getSqlMapClientTemplate().queryForObject(
					countExecutor.getStatementId(), params);
		} else {
			object = getSqlMapClientTemplate().queryForObject(
					countExecutor.getStatementId(), null);
		}

		if (object instanceof Integer) {
			Integer iCount = (Integer) object;
			totalCount = iCount.intValue();
		} else if (object instanceof Long) {
			Long iCount = (Long) object;
			totalCount = iCount.intValue();
		} else if (object instanceof BigDecimal) {
			BigDecimal bg = (BigDecimal) object;
			totalCount = bg.intValue();
		} else if (object instanceof BigInteger) {
			BigInteger bi = (BigInteger) object;
			totalCount = bi.intValue();
		}

		if (totalCount == 0) {
			page.setRows(new java.util.concurrent.CopyOnWriteArrayList<Object>());
			page.setCurrentPage(0);
			page.setPageSize(0);
			page.setTotal(0);
			return page;
		}

		page.setTotal(totalCount);

		int maxPageNo = (page.getTotal() + (pageSize - 1)) / pageSize;
		if (pageNo > maxPageNo) {
			pageNo = maxPageNo;
		}

		List<Object> rows = null;

		Object queryParams = queryExecutor.getParameter();

		int begin = (pageNo - 1) * pageSize + 1;

		if (queryParams != null) {
			rows = getSqlMapClientTemplate().queryForList(
					queryExecutor.getStatementId(), queryParams, begin,
					pageSize);
		} else {
			rows = getSqlMapClientTemplate().queryForList(
					queryExecutor.getStatementId(), null, begin, pageSize);
		}

		page.setRows(rows);
		page.setPageSize(pageSize);
		page.setCurrentPage(pageNo);

		if (LogUtils.isDebug()) {
			logger.debug("params:" + params);
			logger.debug("rows size:" + rows.size());
		}

		return page;
	}

	public Object getSingleObject(String statementName, Object parameterObject) {
		return getSqlMapClientTemplate().queryForObject(statementName,
				parameterObject);
	}

	public com.ibatis.sqlmap.engine.execution.SqlExecutor getSqlExecutor() {
		return sqlExecutor;
	}

	/**
	 * Return the iBATIS Database Layer SqlMapClient that this template works
	 * with.
	 */
	public final SqlMapClient getSqlMapClient() {
		return this.sqlMapClientTemplate.getSqlMapClient();
	}

	/**
	 * Return the SqlMapClientTemplate for this DAO, pre-initialized with the
	 * SqlMapClient or set explicitly.
	 */
	public final SqlMapClientTemplate getSqlMapClientTemplate() {
		return this.sqlMapClientTemplate;
	}

	public void initialize() {
		if (sqlExecutor != null) {
			SqlMapClient sqlMapClient = getSqlMapClientTemplate()
					.getSqlMapClient();
			if (sqlMapClient instanceof SqlMapClientImpl) {
				logger.debug("start inject sqlExecutor ...");
				ReflectUtils.setFieldValue(
						((SqlMapClientImpl) sqlMapClient).getDelegate(),
						"sqlExecutor", sqlExecutor);
				logger.debug("sqlExecutor injected.");
			}
		}
	}

	public void insert(String statementName, Object parameterObject) {
		getSqlMapClientTemplate().insert(statementName, parameterObject);
	}

	public void insertAll(String statementId, List<Object> rows) {
		if (rows != null && !rows.isEmpty()) {
			for (Object object : rows) {
				getSqlMapClientTemplate().insert(statementId, object);
			}
		}
	}

	public IdBlock nextDbidBlock(String name) {
		Dbid dbid = (Dbid) getSqlMapClientTemplate().queryForObject(
				"getNextDbId", name);
		if (dbid == null) {
			dbid = new Dbid();
			dbid.setTitle("系统内置主键");
			dbid.setName(name);
			dbid.setValue("1001");
			dbid.setVersion(1);
			getSqlMapClientTemplate().insert("inertNextDbId", dbid);
			dbid = (Dbid) getSqlMapClientTemplate().queryForObject(
					"getNextDbId", name);
		}
		long oldValue = Long.parseLong(dbid.getValue());
		long newValue = oldValue + 1;
		dbid.setTitle("系统内置主键");
		dbid.setValue(Long.toString(newValue));
		dbid.setVersion(dbid.getVersion() + 1);
		getSqlMapClientTemplate().update("updateNextDbId", dbid);
		return new IdBlock(oldValue, newValue - 1);
	}

	public IdBlock nextDbidBlock() {
		return nextDbidBlock("next.dbid");
	}

	public void setConnection(Connection connection) {
		try {
			this.sqlMapClient.setUserConnection(connection);
			this.sqlMapClientTemplate.setConnection(connection);
		} catch (java.sql.SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public void setSqlExecutor(
			com.ibatis.sqlmap.engine.execution.SqlExecutor sqlExecutor) {
		this.sqlExecutor = sqlExecutor;
	}

	/**
	 * Set the iBATIS Database Layer SqlMapClient to work with. Either this or a
	 * "sqlMapClientTemplate" is required.
	 * 
	 * @see #setSqlMapClientTemplate
	 */
	public final void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
		this.sqlMapClientTemplate.setSqlMapClient(sqlMapClient);
	}

	/**
	 * Set the SqlMapClientTemplate for this DAO explicitly, as an alternative
	 * to specifying a SqlMapClient.
	 * 
	 * @see #setSqlMapClient
	 */
	public final void setSqlMapClientTemplate(
			SqlMapClientTemplate sqlMapClientTemplate) {
		Assert.notNull(sqlMapClientTemplate,
				"SqlMapClientTemplate must not be null");
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	public void update(String statementName, Object parameterObject) {
		getSqlMapClientTemplate().update(statementName, parameterObject);
	}

	public void updateAll(String statementId, List<Object> rows) {
		if (rows != null && !rows.isEmpty()) {
			for (Object object : rows) {
				getSqlMapClientTemplate().update(statementId, object);
			}
		}
	}

}
