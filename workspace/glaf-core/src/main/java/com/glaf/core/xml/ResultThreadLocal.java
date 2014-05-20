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

package com.glaf.core.xml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultThreadLocal {

	protected static ThreadLocal<Map<String, List<Map<String, Object>>>> threadLocalVaribles = new ThreadLocal<Map<String, List<Map<String, Object>>>>();

	public static void clear() {
		if (threadLocalVaribles.get() != null) {
			threadLocalVaribles.get().clear();
		}
		threadLocalVaribles.set(null);
		threadLocalVaribles.remove();
	}

	public static List<Map<String, Object>> getResultList(String key) {
		if (threadLocalVaribles.get() != null) {
			return threadLocalVaribles.get().get(key);
		}
		return null;
	}

	public static void setResultList(String key, List<Map<String, Object>> rows) {
		Map<String, List<Map<String, Object>>> dataMap = threadLocalVaribles
				.get();
		if (dataMap == null) {
			dataMap = new HashMap<String, List<Map<String, Object>>>();
		}
		dataMap.put(key, rows);
		threadLocalVaribles.set(dataMap);
	}

	private ResultThreadLocal() {

	}
}