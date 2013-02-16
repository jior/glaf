/*
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements.  See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership.  The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
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