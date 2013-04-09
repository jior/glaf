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

package com.glaf.base.utils;

import java.util.HashMap;
import java.util.Map;

public class ContextUtil {
	private static ContextUtil instance = new ContextUtil();
	private static Map<Object, Object> dataMap = new HashMap<Object, Object>();

	private ContextUtil() {

	}

	public static Object get(Object key) {
		if (dataMap.containsKey(key)) {
			return dataMap.get(key);
		}
		return null;
	}

	public static String getContextPath() {
		return (String) dataMap.get("__contextPath__");
	}

	public static synchronized ContextUtil getInstance() {
		return instance;
	}

	public static void put(Object key, Object value) {
		dataMap.put(key, value);
	}

	public static void setContextPath(String contextPath) {
		dataMap.put("__contextPath__", contextPath);
	}
}