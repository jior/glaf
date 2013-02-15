/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jpage.jbpm.ibatis;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.ibatis.sqlmap.client.SqlMapSession;
import com.ibatis.sqlmap.client.event.RowHandler;

public class SqlMapClientTemplate implements SqlMapClientOperations {
	private static final Log logger = LogFactory
			.getLog(SqlMapClientTemplate.class);

	private SqlMapClient sqlMapClient;

	private Connection con;

	public SqlMapClientTemplate() {
	}

	public final void setConnection(Connection con) {
		this.con = con;
	}

	public SqlMapClientTemplate(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}

	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}

	public SqlMapClient getSqlMapClient() {
		return sqlMapClient;
	}

	public Object execute(SqlMapClientCallback action) throws SqlMapException {
		if (sqlMapClient == null) {
			throw new SqlMapException("No SqlMapClient specified");
		}
		SqlMapSession session = null;
		try {
			session = sqlMapClient.openSession(con);
			return action.doInSqlMapClient(session);
		} catch (SQLException ex) {
			logger.error(ex);
			throw new SqlMapException("SqlMapClient operation", ex);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public List executeWithListResult(SqlMapClientCallback action)
			throws SqlMapException {
		return (List) execute(action);
	}

	public Map executeWithMapResult(SqlMapClientCallback action)
			throws SqlMapException {
		return (Map) execute(action);
	}

	public Object queryForObject(final String statementName,
			final Object parameterObject) throws SqlMapException {
		return execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				return executor.queryForObject(statementName, parameterObject);
			}
		});
	}

	public Object queryForObject(final String statementName,
			final Object parameterObject, final Object resultObject)
			throws SqlMapException {
		return execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				return executor.queryForObject(statementName, parameterObject,
						resultObject);
			}
		});
	}

	public List queryForList(final String statementName,
			final Object parameterObject) throws SqlMapException {
		return executeWithListResult(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				return executor.queryForList(statementName, parameterObject);
			}
		});
	}

	public List queryForList(final String statementName,
			final Object parameterObject, final int skipResults,
			final int maxResults) throws SqlMapException {
		return executeWithListResult(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				return executor.queryForList(statementName, parameterObject,
						skipResults, maxResults);
			}
		});
	}

	public void queryWithRowHandler(final String statementName,
			final Object parameterObject, final RowHandler rowHandler)
			throws SqlMapException {
		execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.queryWithRowHandler(statementName, parameterObject,
						rowHandler);
				return null;
			}
		});
	}

	public Map queryForMap(final String statementName,
			final Object parameterObject, final String keyProperty)
			throws SqlMapException {
		return executeWithMapResult(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				return executor.queryForMap(statementName, parameterObject,
						keyProperty);
			}
		});
	}

	public Map queryForMap(final String statementName,
			final Object parameterObject, final String keyProperty,
			final String valueProperty) throws SqlMapException {
		return executeWithMapResult(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				return executor.queryForMap(statementName, parameterObject,
						keyProperty, valueProperty);
			}
		});
	}

	public Object insert(final String statementName,
			final Object parameterObject) throws SqlMapException {
		return execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				return executor.insert(statementName, parameterObject);
			}
		});
	}

	public int update(final String statementName, final Object parameterObject)
			throws SqlMapException {
		Integer result = (Integer) execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				return new Integer(executor.update(statementName,
						parameterObject));
			}
		});
		return result.intValue();
	}

	public int delete(final String statementName, final Object parameterObject)
			throws SqlMapException {
		Integer result = (Integer) execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				return new Integer(executor.delete(statementName,
						parameterObject));
			}
		});
		return result.intValue();
	}

	public void update(String statementName, Object parameterObject,
			int requiredRowsAffected) throws SqlMapException {
		int actualRowsAffected = update(statementName, parameterObject);
		if (actualRowsAffected != requiredRowsAffected) {
			throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(
					statementName, requiredRowsAffected, actualRowsAffected);
		}
	}

	public void delete(String statementName, Object parameterObject,
			int requiredRowsAffected) throws SqlMapException {
		int actualRowsAffected = delete(statementName, parameterObject);
		if (actualRowsAffected != requiredRowsAffected) {
			throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(
					statementName, requiredRowsAffected, actualRowsAffected);
		}
	}

}
