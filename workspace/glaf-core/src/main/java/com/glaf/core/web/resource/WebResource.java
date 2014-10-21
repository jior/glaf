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
package com.glaf.core.web.resource;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang.StringUtils;

import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.resource.ResourceFactory;

public class WebResource {

	protected static final ConcurrentMap<String, byte[]> concurrentMap = new ConcurrentHashMap<String, byte[]>();

	protected static final ConcurrentMap<String, Integer> countConcurrentMap = new ConcurrentHashMap<String, Integer>();

	protected static final String DISTRIBUTED_ENABLED = "distributed.resource.enabled";

	protected static final Configuration conf = BaseConfiguration.create();

	protected static final String WEB_RESOURCE_REGION = "web_resource_region";

	private WebResource() {

	}

	public static byte[] getData(String path) {
		if (conf.getBoolean(DISTRIBUTED_ENABLED, false)) {
			return ResourceFactory.getData(getWebResourceRegin(), path);
		}
		return concurrentMap.get(path);
	}

	public static long getTotalSize() {
		long total = 0L;
		Iterator<String> iterator = countConcurrentMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			Integer size = countConcurrentMap.get(key);
			total = total + size;
		}
		return total;
	}

	public static String getWebResourceRegin() {
		String contextPath = com.glaf.core.context.ApplicationContext
				.getContextPath();
		if (contextPath == null) {
			contextPath = "glaf";
		}
		if (contextPath.startsWith("/")) {
			contextPath = contextPath.substring(1);
		}
		if (StringUtils.isNotEmpty(conf.get("web.resource.region"))) {
			return contextPath + "_" + conf.get("web.resource.region");
		}
		return contextPath + "_" + WEB_RESOURCE_REGION;
	}

	protected static void remove(String path) {
		Iterator<String> iterator = concurrentMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			if (StringUtils.startsWith(key, path)) {
				if (conf.getBoolean(DISTRIBUTED_ENABLED, false)) {
					ResourceFactory.remove(getWebResourceRegin(), key);
				} else {
					concurrentMap.remove(key);
				}
				countConcurrentMap.remove(key);
			}
		}
	}

	protected static void removeAll() {
		if (conf.getBoolean(DISTRIBUTED_ENABLED, false)) {
			ResourceFactory.clear(getWebResourceRegin());
		} else {
			concurrentMap.clear();
		}
		countConcurrentMap.clear();
	}

	public static void removeFile(String file) {
		Iterator<String> iterator = concurrentMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			if (StringUtils.equals(key, file)) {
				if (conf.getBoolean(DISTRIBUTED_ENABLED, false)) {
					ResourceFactory.remove(getWebResourceRegin(), key);
				} else {
					concurrentMap.remove(key);
				}
				countConcurrentMap.remove(key);
			}
		}
	}

	public static void setBytes(String path, byte[] bytes) {
		if (path != null
				&& bytes != null
				&& bytes.length < conf.getInt("web.resource.cache.size",
						5000000)) {
			if (conf.getBoolean(DISTRIBUTED_ENABLED, false)) {
				ResourceFactory.put(getWebResourceRegin(), path, bytes);
			} else {
				concurrentMap.put(path, bytes);
			}
			countConcurrentMap.put(path, bytes.length);
		}
	}

}
