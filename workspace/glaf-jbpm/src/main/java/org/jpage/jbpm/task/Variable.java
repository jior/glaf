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

package org.jpage.jbpm.task;

import java.io.Serializable;

import org.jbpm.context.def.Access;

public class Variable implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String variableName = null;

	protected String mappedName = null;

	protected String defaultValue = null;

	protected String expression = null;

	protected Access access = null;

	public Variable() {
	}

	public Variable(String variableName, String access, String mappedName) {
		this.variableName = variableName;
		if (access != null)
			access = access.toLowerCase();
		this.access = new Access(access);
		this.mappedName = mappedName;
	}

	public String getMappedName() {
		if (mappedName == null) {
			return variableName;
		}
		return mappedName;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public void setAccess(Access access) {
		this.access = access;
	}

	public void setMappedName(String mappedName) {
		this.mappedName = mappedName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	public Access getAccess() {
		return access;
	}

	public String getVariableName() {
		return variableName;
	}

	public boolean isReadable() {
		return access.isReadable();
	}

	public boolean isWritable() {
		return access.isWritable();
	}

	public boolean isRequired() {
		return access.isRequired();
	}

	public boolean isLock() {
		return access.isLock();
	}
}
