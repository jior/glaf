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

package org.jpage.persistence;

public class SqlExecutor implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String sql;

	private String operation;

	private String statementId;

	private Object parameter;

	public SqlExecutor() {

	}

	public String getOperation() {
		return operation;
	}

	public Object getParameter() {
		return parameter;
	}

	public String getSql() {
		return sql;
	}

	public String getStatementId() {
		return statementId;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public void setParameter(Object parameter) {
		this.parameter = parameter;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void setStatementId(String statementId) {
		this.statementId = statementId;
	}

}
