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
 * Implementors define a caching algorithm. All implementors <b>must</b> be
 * threadsafe.
 * 
 * @author oschina.net
 */
public interface Cache {

	/**
	 * Get an item from the cache, nontransactionally
	 * 
	 * @param key
	 * @return the cached object or <tt>null</tt>
	 * @throws CacheException
	 */
	Object get(Object key) throws CacheException;

	/**
	 * Add an item to the cache, nontransactionally, with failfast semantics
	 * 
	 * @param key
	 * @param value
	 * @throws CacheException
	 */
	void put(Object key, Object value) throws CacheException;

	/**
	 * Add an item to the cache
	 * 
	 * @param key
	 * @param value
	 * @throws CacheException
	 */
	void update(Object key, Object value) throws CacheException;

	@SuppressWarnings("rawtypes")
	List keys() throws CacheException;

	/**
	 * Remove an item from the cache
	 */
	void evict(Object key) throws CacheException;

	/**
	 * Batch remove cache objects
	 * 
	 * @param keys
	 * @throws CacheException
	 */
	@SuppressWarnings("rawtypes")
	void evict(List keys) throws CacheException;

	/**
	 * Clear the cache
	 */
	void clear() throws CacheException;

	/**
	 * Clean up
	 */
	void destroy() throws CacheException;

}
