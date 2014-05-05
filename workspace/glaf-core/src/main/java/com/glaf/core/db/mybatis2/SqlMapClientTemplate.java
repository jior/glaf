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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.JdbcAccessor;
import org.springframework.util.Assert;

import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.util.LogUtils;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapException;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.ibatis.sqlmap.client.SqlMapSession;
import com.ibatis.sqlmap.client.event.RowHandler;

/**
 * Helper class that simplifies data access via the iBATIS
 * {@link com.ibatis.sqlmap.client.SqlMapClient} API, converting checked
 * SQLExceptions into unchecked DataAccessExceptions, following the
 * {@code org.springframework.dao} exception hierarchy. Uses the same
 * {@link org.springframework.jdbc.support.SQLExceptionTranslator} mechanism as
 * {@link org.springframework.jdbc.core.JdbcTemplate}.
 * 
 * <p>
 * The main method of this class executes a callback that implements a data
 * access action. Furthermore, this class provides numerous convenience methods
 * that mirror {@link com.ibatis.sqlmap.client.SqlMapExecutor}'s execution
 * methods.
 * 
 * <p>
 * It is generally recommended to use the convenience methods on this template
 * for plain query/insert/update/delete operations. However, for more complex
 * operations like batch updates, a custom SqlMapClientCallback must be
 * implemented, usually as anonymous inner class. For example:
 * 
 * <pre class="code">
 * getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
 * 	public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
 * 		executor.startBatch();
 * 		executor.update(&quot;insertSomething&quot;, &quot;myParamValue&quot;);
 * 		executor.update(&quot;insertSomethingElse&quot;, &quot;myOtherParamValue&quot;);
 * 		executor.executeBatch();
 * 		return null;
 * 	}
 * });
 * </pre>
 * 
 * The template needs a SqlMapClient to work on, passed in via the
 * "sqlMapClient" property. A Spring context typically uses a
 * {@link SqlMapClientFactoryBean} to build the SqlMapClient. The template an
 * additionally be configured with a DataSource for fetching Connections,
 * although this is not necessary if a DataSource is specified for the
 * SqlMapClient itself (typically through SqlMapClientFactoryBean's "dataSource"
 * property).
 * 
 * @author Juergen Hoeller
 * @since 24.02.2004
 * @see #execute
 * @see #setSqlMapClient
 * @see #setDataSource
 * @see #setExceptionTranslator
 * @see SqlMapClientFactoryBean#setDataSource
 * @see com.ibatis.sqlmap.client.SqlMapClient#getDataSource
 * @see com.ibatis.sqlmap.client.SqlMapExecutor
 */

public class SqlMapClientTemplate extends JdbcAccessor implements
		SqlMapClientOperations {

	private SqlMapClient sqlMapClient;

	/**
	 * Create a new SqlMapClientTemplate.
	 */
	public SqlMapClientTemplate() {
	}

	/**
	 * Create a new SqlMapTemplate.
	 * 
	 * @param sqlMapClient
	 *            iBATIS SqlMapClient that defines the mapped statements
	 */
	public SqlMapClientTemplate(SqlMapClient sqlMapClient) {
		setSqlMapClient(sqlMapClient);
		afterPropertiesSet();
	}

	public void setConnection(Connection connection) {
		try {
			this.sqlMapClient.setUserConnection(connection);
		} catch (java.sql.SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Set the iBATIS Database Layer SqlMapClient that defines the mapped
	 * statements.
	 */
	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}

	/**
	 * Return the iBATIS Database Layer SqlMapClient that this template works
	 * with.
	 */
	public SqlMapClient getSqlMapClient() {
		return this.sqlMapClient;
	}

	@Override
	public void afterPropertiesSet() {
		if (this.sqlMapClient == null) {
			throw new IllegalArgumentException(
					"Property 'sqlMapClient' is required");
		}
		super.afterPropertiesSet();
	}

	public Object executeBatch(final List<SqlExecutor> sqlExecutors)
			throws DataAccessException {
		return execute(new SqlMapClientCallback<Object>() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				int batch = 0;
				for (SqlExecutor sqlExecutor : sqlExecutors) {
					String statementId = sqlExecutor.getStatementId();
					String operation = sqlExecutor.getOperation();
					Object parameter = sqlExecutor.getParameter();
					if (StringUtils.equalsIgnoreCase("insert", operation)) {
						executor.insert(statementId, parameter);
					} else if (StringUtils
							.equalsIgnoreCase("update", operation)) {
						executor.update(statementId, parameter);
					} else if (StringUtils
							.equalsIgnoreCase("delete", operation)) {
						executor.delete(statementId, parameter);
					}
					batch++;
					// 每500条批量提交一次。
					if (batch == 500) {
						executor.executeBatch();
						batch = 0;
					}
				}
				executor.executeBatch();
				return null;
			}
		});
	}

	/**
	 * Execute the given data access action on a SqlMapExecutor.
	 * 
	 * @param action
	 *            callback object that specifies the data access action
	 * @return a result object returned by the action, or {@code null}
	 * @throws DataAccessException
	 *             in case of SQL Maps errors
	 */
	public <T> T execute(SqlMapClientCallback<T> action)
			throws DataAccessException {
		Assert.notNull(action, "Callback object must not be null");
		Assert.notNull(this.sqlMapClient, "No SqlMapClient specified");
		SqlMapSession session = null;
		try {
			if (this.sqlMapClient.getCurrentConnection() != null) {
				session = this.sqlMapClient.openSession(this.sqlMapClient
						.getCurrentConnection());
				if (LogUtils.isDebug()) {
					logger.debug("Opened SqlMapSession [" + session
							+ "] for iBATIS operation");
				}
				return action.doInSqlMapClient(session);
			}
			throw new SqlMapException(" connection is null ");
		} catch (SQLException ex) {
			throw getExceptionTranslator().translate("SqlMapClient operation",
					null, ex);
		}
	}

	public Object queryForObject(String statementName)
			throws DataAccessException {
		return queryForObject(statementName, null);
	}

	public Object queryForObject(final String statementName,
			final Object parameterObject) throws DataAccessException {

		return execute(new SqlMapClientCallback<Object>() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				return executor.queryForObject(statementName, parameterObject);
			}
		});
	}

	public Object queryForObject(final String statementName,
			final Object parameterObject, final Object resultObject)
			throws DataAccessException {

		return execute(new SqlMapClientCallback<Object>() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				return executor.queryForObject(statementName, parameterObject,
						resultObject);
			}
		});
	}

	public List<Object> queryForList(String statementName)
			throws DataAccessException {
		return queryForList(statementName, null);
	}

	public List<Object> queryForList(final String statementName,
			final Object parameterObject) throws DataAccessException {

		return execute(new SqlMapClientCallback<List<Object>>() {
			@SuppressWarnings("unchecked")
			public List<Object> doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				return executor.queryForList(statementName, parameterObject);
			}
		});
	}

	public List<Object> queryForList(String statementName, int skipResults,
			int maxResults) throws DataAccessException {

		return queryForList(statementName, null, skipResults, maxResults);
	}

	public List<Object> queryForList(final String statementName,
			final Object parameterObject, final int skipResults,
			final int maxResults) throws DataAccessException {

		return execute(new SqlMapClientCallback<List<Object>>() {
			@SuppressWarnings("unchecked")
			public List<Object> doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				return executor.queryForList(statementName, parameterObject,
						skipResults, maxResults);
			}
		});
	}

	public void queryWithRowHandler(String statementName, RowHandler rowHandler)
			throws DataAccessException {

		queryWithRowHandler(statementName, null, rowHandler);
	}

	public void queryWithRowHandler(final String statementName,
			final Object parameterObject, final RowHandler rowHandler)
			throws DataAccessException {

		execute(new SqlMapClientCallback<Object>() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.queryWithRowHandler(statementName, parameterObject,
						rowHandler);
				return null;
			}
		});
	}

	public Map<Object, Object> queryForMap(final String statementName,
			final Object parameterObject, final String keyProperty)
			throws DataAccessException {

		return execute(new SqlMapClientCallback<Map<Object, Object>>() {
			@SuppressWarnings("unchecked")
			public Map<Object, Object> doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				return executor.queryForMap(statementName, parameterObject,
						keyProperty);
			}
		});
	}

	public Map<Object, Object> queryForMap(final String statementName,
			final Object parameterObject, final String keyProperty,
			final String valueProperty) throws DataAccessException {
		return execute(new SqlMapClientCallback<Map<Object, Object>>() {
			@SuppressWarnings("unchecked")
			public Map<Object, Object> doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				return executor.queryForMap(statementName, parameterObject,
						keyProperty, valueProperty);
			}
		});
	}

	public Object insert(String statementName) throws DataAccessException {
		return insert(statementName, null);
	}

	public Object insert(final String statementName,
			final Object parameterObject) throws DataAccessException {

		return execute(new SqlMapClientCallback<Object>() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				return executor.insert(statementName, parameterObject);
			}
		});
	}

	public int update(String statementName) throws DataAccessException {
		return update(statementName, null);
	}

	public int update(final String statementName, final Object parameterObject)
			throws DataAccessException {

		return execute(new SqlMapClientCallback<Integer>() {
			public Integer doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				return executor.update(statementName, parameterObject);
			}
		});
	}

	public void update(String statementName, Object parameterObject,
			int requiredRowsAffected) throws DataAccessException {

		int actualRowsAffected = update(statementName, parameterObject);
		if (actualRowsAffected != requiredRowsAffected) {
			throw new RuntimeException("SQL update '" + statementName
					+ "' affected " + actualRowsAffected + " rows, not "
					+ requiredRowsAffected + " as expected");
		}
	}

	public int delete(String statementName) throws DataAccessException {
		return delete(statementName, null);
	}

	public int delete(final String statementName, final Object parameterObject)
			throws DataAccessException {

		return execute(new SqlMapClientCallback<Integer>() {
			public Integer doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				return executor.delete(statementName, parameterObject);
			}
		});
	}

	public void delete(String statementName, Object parameterObject,
			int requiredRowsAffected) throws DataAccessException {

		int actualRowsAffected = delete(statementName, parameterObject);
		if (actualRowsAffected != requiredRowsAffected) {
			throw new RuntimeException("SQL delete '" + statementName
					+ "' affected " + actualRowsAffected + " rows, not "
					+ requiredRowsAffected + " as expected");
		}
	}

}
