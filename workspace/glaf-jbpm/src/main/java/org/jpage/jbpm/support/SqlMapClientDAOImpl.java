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

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class SqlMapClientDAOImpl extends SqlMapClientDaoSupport implements
		SqlMapClientDAO {

	public void insertObject(String statementName, Object parameterObject) {
		getSqlMapClientTemplate().insert(statementName, parameterObject);
	}

	public void updateObject(String statementName, Object parameterObject) {
		getSqlMapClientTemplate().update(statementName, parameterObject);
	}

	public void deleteObject(String statementName, Object parameterObject) {
		getSqlMapClientTemplate().delete(statementName, parameterObject);
	}

	public Object queryForObject(String statementId) {
		return getSqlMapClientTemplate().queryForObject(statementId);
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

	public java.util.List query(String statementName, Object parameterObject) {
		if (parameterObject != null) {
			return getSqlMapClientTemplate().queryForList(statementName,
					parameterObject);
		}
		return getSqlMapClientTemplate().queryForList(statementName);
	}

}
