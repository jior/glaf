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

package com.glaf.j2cache.xmemcache;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.TimeoutException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;
import net.rubyeye.xmemcached.transcoders.SerializingTranscoder;

import com.glaf.core.util.StringTools;
import com.glaf.j2cache.CacheException;

public class XMemCache implements com.glaf.j2cache.Cache {
	protected static final Log logger = LogFactory.getLog(XMemCache.class);

	protected MemcachedClient memcachedClient;

	protected String servers;

	protected int cacheSize = 100000;

	protected int expireMinutes = 30;

	public XMemCache(String servers, int cacheSize, int expireMinutes) {
		this.servers = servers;
		this.cacheSize = cacheSize;
		this.expireMinutes = expireMinutes;
		this.buildClient();
	}

	protected void buildClient() {
		List<String> list = StringTools.split(servers, ",");
		List<InetSocketAddress> addressList = new ArrayList<InetSocketAddress>();
		for (String server : list) {
			String host = server.substring(0, server.indexOf(":"));
			int port = Integer.parseInt(server.substring(
					server.indexOf(":") + 1, server.length()));
			InetSocketAddress addr = new InetSocketAddress(host, port);
			addressList.add(addr);
			logger.info("add server-> " + host + ":" + port);
		}
		XMemcachedClientBuilder builder = new XMemcachedClientBuilder(
				addressList);
		// builder.setConnectionPoolSize(2);
		builder.setTranscoder(new SerializingTranscoder());
		builder.setSessionLocator(new KetamaMemcachedSessionLocator());
		builder.setConnectTimeout(5000);
		builder.setCommandFactory(new BinaryCommandFactory());
		try {
			memcachedClient = builder.build();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void clear() throws CacheException {

	}

	public void destroy() throws CacheException {
		try {
			getCache().shutdown();
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	@SuppressWarnings("rawtypes")
	public void evict(List keys) throws CacheException {
		if (keys != null && !keys.isEmpty()) {
			try {
				for (Object key : keys) {
					getCache().delete(key.toString());
				}
			} catch (TimeoutException e) {
				throw new CacheException(e);
			} catch (InterruptedException e) {
				throw new CacheException(e);
			} catch (MemcachedException e) {
				throw new CacheException(e);
			}
		}
	}

	public void evict(Object key) throws CacheException {
		try {
			getCache().delete(key.toString());
		} catch (TimeoutException e) {
			throw new CacheException(e);
		} catch (InterruptedException e) {
			throw new CacheException(e);
		} catch (MemcachedException e) {
			throw new CacheException(e);
		}
	}

	public Object get(Object key) throws CacheException {
		try {
			return getCache().get(key.toString());
		} catch (TimeoutException e) {
			throw new CacheException(e);
		} catch (InterruptedException e) {
			throw new CacheException(e);
		} catch (MemcachedException e) {
			throw new CacheException(e);
		}
	}

	public MemcachedClient getCache() {
		if (memcachedClient == null) {
			this.buildClient();
		}
		return memcachedClient;
	}

	public int getCacheSize() {
		return cacheSize;
	}

	public int getExpireMinutes() {
		return expireMinutes;
	}

	public String getServers() {
		return servers;
	}

	public List<?> keys() throws CacheException {
		return null;
	}

	public void put(Object key, Object value) throws CacheException {
		try {
			getCache().set(key.toString(), expireMinutes * 60, value);
		} catch (TimeoutException e) {
			throw new CacheException(e);
		} catch (InterruptedException e) {
			throw new CacheException(e);
		} catch (MemcachedException e) {
			throw new CacheException(e);
		}
	}

	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	public void setExpireMinutes(int expireMinutes) {
		this.expireMinutes = expireMinutes;
	}

	public void setServers(String servers) {
		this.servers = servers;
	}

	public void update(Object key, Object value) throws CacheException {
		try {
			getCache().set(key.toString(), expireMinutes * 60, value);
		} catch (TimeoutException e) {
			throw new CacheException(e);
		} catch (InterruptedException e) {
			throw new CacheException(e);
		} catch (MemcachedException e) {
			throw new CacheException(e);
		}
	}

}
