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

import java.util.HashMap;
import java.util.Map;

public final class PersistenceType {

	public final static int QUERY_TYPE = 0;

	public final static int SAVE_TYPE = 1;

	public final static int UPDATE_TYPE = 2;

	public final static int DELETE_TYPE = 3;

	private int value = 0;

	private static Map stringMap = new HashMap();

	private PersistenceType(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public String toString() {
		return (String) stringMap.get(this);
	}

	public static PersistenceType stringToType(String type) {
		java.util.Iterator keys = stringMap.keySet().iterator();
		while (keys.hasNext()) {
			PersistenceType key = (PersistenceType) keys.next();
			String stringValue = (String) stringMap.get(key);
			if (stringValue.equals(type)) {
				return key;
			}
		}
		return null;
	}

	public static String typeToString(PersistenceType type) {
		return (String) stringMap.get(type);
	}

	public static PersistenceType QUERY = new PersistenceType(QUERY_TYPE);

	public static PersistenceType SAVE = new PersistenceType(SAVE_TYPE);

	public static PersistenceType UPDATE = new PersistenceType(UPDATE_TYPE);

	public static PersistenceType DELETE = new PersistenceType(DELETE_TYPE);

	static {
		stringMap.put(QUERY, "QUERY");
		stringMap.put(SAVE, "SAVE");
		stringMap.put(UPDATE, "UPDATE");
		stringMap.put(DELETE, "DELETE");

	}

}