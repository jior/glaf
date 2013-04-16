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
package com.glaf.form.core.util;

import java.util.*;
import java.util.Map.Entry;
import org.apache.commons.lang.StringUtils;

import com.glaf.core.base.FieldDefinition;
import com.glaf.core.util.*;

import com.glaf.form.core.graph.def.FormNode;
import com.glaf.form.core.graph.node.PersistenceNode;

public final class FormPersistenceUtil {

	private final static Map<String, String> cache = Collections
			.synchronizedMap(new HashMap<String, String>());

	public static boolean exists(Map<String, FieldDefinition> fields,
			PersistenceNode node) {
		String name = node.getName();
		String columnName = node.getColumnName();
		Set<Entry<String, FieldDefinition>> entrySet = fields.entrySet();
		for (Entry<String, FieldDefinition> entry : entrySet) {
			String key = entry.getKey();
			FieldDefinition def = entry.getValue();
			if (StringUtils.equalsIgnoreCase(key, name)
					|| StringUtils.equalsIgnoreCase(def.getColumnName(),
							columnName)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isPersistField(FormNode formNode) {
		if (!(formNode instanceof PersistenceNode)) {
			return false;
		}
		PersistenceNode node = (PersistenceNode) formNode;
		String columnName = node.getColumnName();
		if (!Tools.isDatabaseField(columnName)) {
			return false;
		}
		return true;
	}

	public static void setPersistProvider(String name, String provider) {
		FormPersistenceUtil.cache.put(name, provider);
	}

	private FormPersistenceUtil() {

	}
}