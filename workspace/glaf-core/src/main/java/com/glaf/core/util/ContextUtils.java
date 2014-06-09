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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ContextUtils {
	protected static final Log logger = LogFactory.getLog(ContextUtils.class);

	private static ConcurrentMap<Object, Object> dataMap = new ConcurrentHashMap<Object, Object>();

	public static void clear(){
		dataMap.clear();
	}
	
	public static Object get(Object key) {
		String sys_name = Constants.SYSTEM_NAME;
		if (sys_name != null) {
			String cacheKey = sys_name + "_" + key;
			if (dataMap.containsKey(cacheKey)) {
				return dataMap.get(cacheKey);
			}
		}
		if (dataMap.containsKey(key)) {
			return dataMap.get(key);
		}
		return null;
	}

	public static String getContextPath() {
		return (String) dataMap.get("__contextPath__");
	}

	public static void put(Object key, Object value) {
		String sys_name = Constants.SYSTEM_NAME;
		if (key != null && value != null) {
			String cacheKey = sys_name + "_" + key;
			dataMap.put(cacheKey, value);
			dataMap.put(key, value);
		}
	}

	public static void setContextPath(String contextPath) {
		dataMap.put("__contextPath__", contextPath);
	}

	private ContextUtils() {

	}
}