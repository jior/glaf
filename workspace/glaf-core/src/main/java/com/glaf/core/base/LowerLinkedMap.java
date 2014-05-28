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

package com.glaf.core.base;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class LowerLinkedMap extends LinkedHashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	private final Locale locale;

	public LowerLinkedMap() {
		this(((Locale) (null)));
	}

	public LowerLinkedMap(int initialCapacity) {
		this(initialCapacity, null);
	}

	public LowerLinkedMap(int initialCapacity, Locale locale) {
		super(initialCapacity);
		this.locale = locale == null ? Locale.getDefault() : locale;
	}

	public LowerLinkedMap(Locale locale) {
		this.locale = locale == null ? Locale.getDefault() : locale;
	}

	public void clear() {
		super.clear();
	}

	public boolean containsKey(Object key) {
		return (key instanceof String)
				&& super.containsKey(convertKey((String) key));
	}

	protected String convertKey(String key) {
		return key.toLowerCase(locale);
	}

	public Object get(Object key) {
		if (key instanceof String) {
			return super.get(convertKey((String) key));
		}
		return null;
	}

	public Object put(String key, Object value) {
		return super.put(convertKey(key), value);
	}

	@SuppressWarnings("rawtypes")
	public void putAll(Map map) {
		if (map.isEmpty()) {
			return;
		}
		Map.Entry entry = null;
		Iterator iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			entry = (Map.Entry) iterator.next();
			put(convertKey((String) entry.getKey()), entry.getValue());
		}
	}

	public Object remove(Object key) {
		if (key instanceof String) {
			return super.remove(convertKey((String) key));
		}
		return null;
	}

}
