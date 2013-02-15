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

package org.jpage.jbpm.variable;

public class MutableIntegerInstance extends MutableVariableInstance {

	private static final long serialVersionUID = 1L;

	protected Integer value = null;

	public boolean isStorable(Object value) {
		if (value == null)
			return true;
		return (Integer.class == value.getClass());
	}

	public Object getObject() {
		return value;
	}

	public void setObject(Object value) {
		this.value = (Integer) value;
	}

}
