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

import com.ibatis.sqlmap.client.SqlMapClient;

public class SqlMapClientDaoSupport {

	protected SqlMapClientTemplate sqlMapClientTemplate = new SqlMapClientTemplate();

	protected SqlMapClient sqlMapClient;

	public SqlMapClientDaoSupport() {

	}

	public final void setConnection(java.sql.Connection con) {
		try {
			this.sqlMapClientTemplate.setConnection(con);
			this.sqlMapClient.setUserConnection(con);
		} catch (java.sql.SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
		this.sqlMapClientTemplate.setSqlMapClient(sqlMapClient);
	}

	public final SqlMapClient getSqlMapClient() {
		return sqlMapClient;
	}

	public final void setSqlMapClientTemplate(
			SqlMapClientTemplate sqlMapClientTemplate) {
		if (sqlMapClientTemplate == null) {
			throw new IllegalArgumentException(
					"Cannot set sqlMapClientTemplate to null");
		}
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	public final SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

}
