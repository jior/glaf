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

public interface JbpmSqlMapDAO {

	public void setConnection(java.sql.Connection con);

	public void insertObject(String statementId, Object parameterObject);

	public void updateObject(String statementId, Object parameterObject);

	public void deleteObject(String statementId, Object parameterObject);

	public Object queryForObject(String statementId, Object parameterObject);

	public java.util.List query(String statementId, Object parameterObject);

	public int executeBatch(java.util.List rows);

}
