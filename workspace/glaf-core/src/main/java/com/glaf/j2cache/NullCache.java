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

package com.glaf.j2cache;

import java.util.List;

/**
 * 空的缓存Provider
 * 
 * @author oschina.net
 */
public class NullCache implements Cache {

	public Object get(Object key) throws CacheException {
		return null;
	}

	public void put(Object key, Object value) throws CacheException {
	}

	public void update(Object key, Object value) throws CacheException {
	}

	@SuppressWarnings("rawtypes")
	public List keys() throws CacheException {
		return null;
	}

	public void evict(Object key) throws CacheException {
	}

	@SuppressWarnings("rawtypes")
	public void evict(List keys) throws CacheException {
	}

	public void clear() throws CacheException {
	}

	public void destroy() throws CacheException {
	}

}
