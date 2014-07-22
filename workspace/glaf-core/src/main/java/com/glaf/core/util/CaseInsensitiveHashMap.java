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

package com.glaf.core.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class CaseInsensitiveHashMap extends LinkedHashMap<String, Object> {
	/**
	 * The internal mapping from lowercase keys to the real keys.
	 * 
	 * <p>
	 * Any query operation using the key ({@link #get(Object)},
	 * {@link #containsKey(Object)}) is done in three steps:
	 * <ul>
	 * <li>convert the parameter key to lower case</li>
	 * <li>get the actual key that corresponds to the lower case key</li>
	 * <li>query the map with the actual key</li>
	 * </ul>
	 * </p>
	 */
	private final Map<String, String> lowerCaseMap = new HashMap<String, String>();

	/**
	 * Required for serialization support.
	 * 
	 * @see java.io.Serializable
	 */
	private static final long serialVersionUID = -2848100435296897392L;

	@Override
	public boolean containsKey(Object key) {
		Object realKey = lowerCaseMap.get(key.toString().toLowerCase(
				Locale.ENGLISH));
		return super.containsKey(realKey);
	}

	@Override
	public Object get(Object key) {
		Object realKey = lowerCaseMap.get(key.toString().toLowerCase(
				Locale.ENGLISH));
		return super.get(realKey);
	}

	@Override
	public Object put(String key, Object value) {
		/*
		 * In order to keep the map and lowerCaseMap synchronized, we have to
		 * remove the old mapping before putting the new one. Indeed, oldKey and
		 * key are not necessaliry equals. (That's why we call
		 * super.remove(oldKey) and not just super.put(key, value))
		 */
		Object oldKey = lowerCaseMap.put(key.toLowerCase(Locale.ENGLISH), key);
		Object oldValue = super.remove(oldKey);
		super.put(key, value);
		return oldValue;
	}

	@Override
	public void putAll(Map<? extends String, ?> m) {
		for (Map.Entry<? extends String, ?> entry : m.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			this.put(key, value);
		}
	}

	@Override
	public Object remove(Object key) {
		Object realKey = lowerCaseMap.remove(key.toString().toLowerCase(
				Locale.ENGLISH));
		return super.remove(realKey);
	}

}
