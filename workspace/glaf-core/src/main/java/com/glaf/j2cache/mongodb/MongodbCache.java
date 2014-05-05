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

package com.glaf.j2cache.mongodb;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.glaf.j2cache.CacheException;
import com.glaf.core.util.StringTools;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class MongodbCache implements com.glaf.j2cache.Cache {

	protected MongoClient mongoClient;

	protected DBCollection dbCollection;

	protected DB db;

	protected String servers;

	protected int cacheSize = 100000;

	protected int expireMinutes = 30;

	public MongodbCache(String servers, int cacheSize, int expireMinutes) {
		this.servers = servers;
		this.cacheSize = cacheSize;
		this.expireMinutes = expireMinutes;
		this.buildClient();
	}

	protected void buildClient() {
		List<String> list = StringTools.split(servers, ",");
		List<ServerAddress> addrList = new ArrayList<ServerAddress>();
		for (String server : list) {
			String host = server.substring(0, server.indexOf(":"));
			int port = Integer.parseInt(server.substring(
					server.indexOf(":") + 1, server.length()));
			try {
				ServerAddress addr = new ServerAddress(host, port);
				addrList.add(addr);
			} catch (UnknownHostException ex) {
				ex.printStackTrace();
			}
		}
		mongoClient = new MongoClient(addrList);
		db = mongoClient.getDB("cachedb");
		dbCollection = db.getCollection("collection");
	}

	public void clear() throws CacheException {
		dbCollection.drop();
	}

	public void destroy() throws CacheException {
		dbCollection.drop();
		db.dropDatabase();
		mongoClient.close();
	}

	@SuppressWarnings("rawtypes")
	public void evict(List keys) throws CacheException {
		if (keys != null && !keys.isEmpty()) {
			BasicDBObject query = new BasicDBObject();
			for (Object key : keys) {
				query.put("key", key);
				dbCollection.remove(query);
			}
		}
	}

	public void evict(Object key) throws CacheException {
		BasicDBObject query = new BasicDBObject();
		query.put("key", key);
		dbCollection.remove(query);
	}

	public Object get(Object key) throws CacheException {
		BasicDBObject query = new BasicDBObject();
		query.put("key", key);
		DBCursor cur = dbCollection.find(query);
		long now = System.currentTimeMillis();
		Object value = null;
		while (cur.hasNext()) {
			DBObject dbObject = cur.next();
			value = dbObject.get(key.toString());
			long saveTime = (Long) dbObject.get("time");
			if ((now - saveTime) > expireMinutes * 60000) {
				/**
				 * 如果已经过期，就删除缓存对象
				 */
				dbCollection.remove(query);
			}
		}
		return value;
	}

	public int getCacheSize() {
		return cacheSize;
	}

	public int getExpireMinutes() {
		return expireMinutes;
	}

	public List<?> keys() throws CacheException {
		return null;
	}

	public void put(Object key, Object value) throws CacheException {
		BasicDBObject doc = new BasicDBObject();
		doc.put("key", key);
		doc.put(key.toString(), value);
		doc.put("time", System.currentTimeMillis());
		dbCollection.insert(doc);
	}

	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	public void setExpireMinutes(int expireMinutes) {
		this.expireMinutes = expireMinutes;
	}

	public void update(Object key, Object value) throws CacheException {
		BasicDBObject doc = new BasicDBObject();
		doc.put("key", key);
		doc.put(key.toString(), value);
		doc.put("time", System.currentTimeMillis());
		dbCollection.insert(doc);
	}

}
