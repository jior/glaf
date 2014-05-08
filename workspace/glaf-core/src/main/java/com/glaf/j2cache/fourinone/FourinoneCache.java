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

package com.glaf.j2cache.fourinone;

import java.util.List;

import com.fourinone.BeanContext;
import com.fourinone.CacheLocal;
import com.glaf.j2cache.CacheException;

public class FourinoneCache implements com.glaf.j2cache.Cache {

	protected CacheLocal cache;

	protected String host;

	protected int port;

	public FourinoneCache(String host, int port) {
		this.host = host;
		this.port = port;
		this.cache = BeanContext.getCache(host, port);
	}

	public void clear() throws CacheException {

	}

	public void destroy() throws CacheException {

	}

	@SuppressWarnings("rawtypes")
	public void evict(List keys) throws CacheException {
		if (keys != null && !keys.isEmpty()) {
			for (Object key : keys) {
				getCache().remove(key.toString());
			}
		}
	}

	public void evict(Object key) throws CacheException {
		getCache().remove(key.toString());
	}

	public Object get(Object key) throws CacheException {
		return getCache().get(key.toString());
	}

	public CacheLocal getCache() {
		if (cache == null) {
			cache = BeanContext.getCache(host, port);
		}
		return cache;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public List<?> keys() throws CacheException {
		return null;
	}

	public void put(Object key, Object value) throws CacheException {
		getCache().add(key.toString(), (java.io.Serializable) value);
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void update(Object key, Object value) throws CacheException {
		this.put(key, value);
	}

}
