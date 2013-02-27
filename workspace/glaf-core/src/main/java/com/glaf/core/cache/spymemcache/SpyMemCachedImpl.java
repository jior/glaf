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
package com.glaf.core.cache.spymemcache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.glaf.core.cache.Cache;
import net.spy.memcached.*;

/**
 * 
 * <bean id="memcachedClient"
 * class="net.spy.memcached.spring.MemcachedClientFactoryBean"> <br/>
 * <property name="servers" value="host1:11211,host2:11211,host3:11211"/> <br/>
 * <property name="protocol" value="BINARY"/><br/>
 * <property name="transcoder"> <br/>
 * <bean class="net.spy.memcached.transcoders.SerializingTranscoder"> <br/>
 * <property name="compressionThreshold" value="1024"/><br/>
 * </bean> <br/>
 * </property> <br/>
 * <property name="opTimeout" value="1000"/> <br/>
 * <property name="timeoutExceptionThreshold" value="1998"/> <br/>
 * <property name="hashAlg" value="KETAMA_HASH"/><br/>
 * <property name="locatorType" value="CONSISTENT"/> <br/>
 * <property name="failureMode" value="Redistribute"/> <br/>
 * <property name="useNagleAlgorithm" value="false"/><br/>
 * </bean><br/>
 * 
 */
public class SpyMemCachedImpl implements Cache {
	protected static final Log logger = LogFactory
			.getLog(SpyMemCachedImpl.class);

	private MemcachedClient cacheProvider;

	public SpyMemCachedImpl() {

	}

	public void clear() {

	}

	public Object get(String key) {
		return cacheProvider.get(key);
	}

	public MemcachedClient getCacheProvider() {
		return cacheProvider;
	}

	public void put(String key, Object value) {
		cacheProvider.set(key, 3600, value);
	}

	public void remove(String key) {
		cacheProvider.delete(key);
	}

	public void setCacheProvider(MemcachedClient provider) {
		cacheProvider = provider;
	}

	public void shutdown() {
		cacheProvider.shutdown();
	}

	public int size() {
		return -1;
	}

}
