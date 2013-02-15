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

package org.jpage.jbpm.support;

import java.util.List;

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

	public void insertObject(String statementId, Object obj) {
		sqlMapClientDAO.insertObject(statementId, obj);
	}

	public void updateObject(String statementId, Object obj) {
		sqlMapClientDAO.updateObject(statementId, obj);
	}

	public void deleteObject(String statementId, Object obj) {
		sqlMapClientDAO.deleteObject(statementId, obj);
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

	public Object queryForSingleObject(String statementId,
			Object parameterObject) {
		List rows = this.query(statementId, parameterObject);
		if (rows != null && rows.size() > 0) {
			return rows.get(0);
		}
		return null;
	}

	public List query(String statementId) {
		return sqlMapClientDAO.query(statementId, null);
	}

	public List query(String statementId, Object parameterObject) {
		return sqlMapClientDAO.query(statementId, parameterObject);
	}
}
