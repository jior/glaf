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

import java.util.List;

import org.bson.Document;

import com.glaf.j2cache.CacheException;

import com.glaf.core.container.MongodbContainer;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongodbCache implements com.glaf.j2cache.Cache {

	protected MongoCollection<Document> dbCollection;

	protected MongoDatabase db;

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
		db = MongodbContainer.getInstance().getDatabase("cachedb");
		dbCollection = db.getCollection("collection");
	}

	public void clear() throws CacheException {
		dbCollection.drop();
	}

	public void destroy() throws CacheException {
		dbCollection.drop();
		db.drop();
	}

	@SuppressWarnings("rawtypes")
	public void evict(List keys) throws CacheException {
		if (keys != null && !keys.isEmpty()) {
			BasicDBObject filter = new BasicDBObject();
			for (Object key : keys) {
				filter.put("key", key);
				dbCollection.deleteOne(filter);
			}
		}
	}

	public void evict(Object key) throws CacheException {
		BasicDBObject filter = new BasicDBObject();
		filter.put("key", key);
		dbCollection.deleteOne(filter);
	}

	public Object get(Object key) throws CacheException {
		BasicDBObject filter = new BasicDBObject();
		filter.put("key", key);
		MongoCursor<Document> cur = dbCollection.find(filter).iterator();
		long now = System.currentTimeMillis();
		Object value = null;
		try {
			while (cur.hasNext()) {
				Document doc = cur.next();
				value = doc.get(key.toString());
				long saveTime = (Long) doc.get("time");
				if ((now - saveTime) > expireMinutes * 60000) {
					/**
					 * 如果已经过期，就删除缓存对象
					 */
					dbCollection.deleteOne(filter);
				}
			}
		} finally {
			cur.close();
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
		Document doc = new Document();
		doc.put("key", key);
		doc.put(key.toString(), value);
		doc.put("time", System.currentTimeMillis());
		dbCollection.insertOne(doc);
	}

	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	public void setExpireMinutes(int expireMinutes) {
		this.expireMinutes = expireMinutes;
	}

	public void update(Object key, Object value) throws CacheException {
		Document doc = new Document();
		doc.put("key", key);
		doc.put(key.toString(), value);
		doc.put("time", System.currentTimeMillis());
		dbCollection.insertOne(doc);
	}

}
