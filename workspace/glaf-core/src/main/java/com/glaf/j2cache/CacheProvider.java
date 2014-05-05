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

import java.util.Properties;

/**
 * Support for pluggable caches.
 * 
 * @author oschina.net
 */
public interface CacheProvider {

	/**
	 * 缓存的标识名称
	 * 
	 * @return
	 */
	public String name();

	/**
	 * Configure the cache
	 * 
	 * @param regionName
	 *            the name of the cache region
	 * @param autoCreate
	 *            autoCreate settings
	 * @param listener
	 *            listener for expired elements
	 * @throws CacheException
	 */
	public Cache buildCache(String regionName, boolean autoCreate,
			CacheExpiredListener listener) throws CacheException;

	/**
	 * Callback to perform any necessary initialization of the underlying cache
	 * implementation during SessionFactory construction.
	 * 
	 * @param properties
	 *            current configuration settings.
	 */
	public void start(Properties props) throws CacheException;

	/**
	 * Callback to perform any necessary cleanup of the underlying cache
	 * implementation during SessionFactory.close().
	 */
	public void stop();

}
