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

import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.event.RowHandler;

public interface SqlMapClientOperations {

	Object queryForObject(String statementName, Object parameterObject)
			throws SqlMapException;

	Object queryForObject(String statementName, Object parameterObject,
			Object resultObject) throws SqlMapException;

	List queryForList(String statementName, Object parameterObject)
			throws SqlMapException;

	List queryForList(String statementName, Object parameterObject,
			int skipResults, int maxResults) throws SqlMapException;

	void queryWithRowHandler(String statementName, Object parameterObject,
			RowHandler rowHandler) throws SqlMapException;

	Map queryForMap(String statementName, Object parameterObject,
			String keyProperty) throws SqlMapException;

	Map queryForMap(String statementName, Object parameterObject,
			String keyProperty, String valueProperty) throws SqlMapException;

	Object insert(String statementName, Object parameterObject)
			throws SqlMapException;

	int update(String statementName, Object parameterObject)
			throws SqlMapException;

	int delete(String statementName, Object parameterObject)
			throws SqlMapException;

	void update(String statementName, Object parameterObject,
			int requiredRowsAffected) throws SqlMapException;

	void delete(String statementName, Object parameterObject,
			int requiredRowsAffected) throws SqlMapException;

}
