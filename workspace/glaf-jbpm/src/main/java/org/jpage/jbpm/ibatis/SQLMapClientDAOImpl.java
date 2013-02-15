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

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SQLMapClientDAOImpl extends SqlMapClientDaoSupport implements
		JbpmSqlMapDAO {
	private static final Log logger = LogFactory
			.getLog(SQLMapClientDAOImpl.class);

	public SQLMapClientDAOImpl() {

	}

	public void insertObject(String statementName, Object parameterObject) {
		getSqlMapClientTemplate().insert(statementName, parameterObject);
	}

	public void updateObject(String statementName, Object parameterObject) {
		getSqlMapClientTemplate().update(statementName, parameterObject);
	}

	public void deleteObject(String statementName, Object parameterObject) {
		getSqlMapClientTemplate().delete(statementName, parameterObject);
	}

	public Object queryForObject(String statementName, Object parameterObject) {
		return getSqlMapClientTemplate().queryForObject(statementName,
				parameterObject);
	}

	public java.util.List query(String statementName, Object parameterObject) {
		return getSqlMapClientTemplate().queryForList(statementName,
				parameterObject);
	}

	public int executeBatch(java.util.List rows) {
		try {
			int count = 0;
			Iterator iterator = rows.iterator();
			while (iterator.hasNext()) {
				SQLMap sqlmap = (SQLMap) iterator.next();
				String statementName = sqlmap.getName();
				String operation = sqlmap.getOperation();
				logger.debug("name:" + statementName + "\toperation:"
						+ operation);
				Object obj = sqlmap.getObject();
				logger.debug("->obj:" + obj);
				if (StringUtils.equalsIgnoreCase("insert", operation)) {
					this.insertObject(statementName, obj);
					logger.debug("insert " + statementName + " finished.");
					count++;
				} else if (StringUtils.equalsIgnoreCase("update", operation)) {
					this.updateObject(statementName, obj);
					logger.debug("update " + statementName + " finished.");
					count++;
				} else if (StringUtils.equalsIgnoreCase("delete", operation)) {
					this.deleteObject(statementName, obj);
					logger.debug("delete " + statementName + " finished.");
					count++;
				}
			}
			logger.debug("成功执行SQL数目:" + count);
			return count;
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			throw new SqlMapException(
					"SQLMap execute batch catch sql exception", ex);
		}
	}
}
