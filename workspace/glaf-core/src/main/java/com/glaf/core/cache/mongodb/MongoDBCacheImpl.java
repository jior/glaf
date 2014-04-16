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

package com.glaf.core.cache.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.glaf.core.cache.Cache;

public class MongoDBCacheImpl implements Cache {

	private Mongo mongo;
	private DB db;
	private DBCollection dbCollection;

	public MongoDBCacheImpl() {

	}

	public void clear() {
		dbCollection.drop();
	}

	public Object get(String key) {
		BasicDBObject query = new BasicDBObject();
		query.put("key", key);
		DBCursor cur = dbCollection.find(query);
		long time = 0;
		Object value = null;
		while (cur.hasNext()) {
			DBObject dbObject = cur.next();
			Object tmp = dbObject.get(key);
			long t = (Long) dbObject.get("time");
			if (t > time) {
				time = t;
				value = tmp;
			}
		}
		return value;
	}

	public void put(String key, Object value) {
		BasicDBObject doc = new BasicDBObject();
		doc.put("key", key);
		doc.put(key, value);
		doc.put("time", System.currentTimeMillis());
		dbCollection.insert(doc);
	}

	public void remove(String key) {
		BasicDBObject query = new BasicDBObject();
		query.put("key", key);
		dbCollection.remove(query);
	}

	public void setDb(DB db) {
		this.db = db;
		this.dbCollection = db.getCollection("collection");
	}

	public void setMongo(Mongo mongo) {
		this.mongo = mongo;
	}

	public void shutdown() {
		dbCollection.drop();
		db.dropDatabase();
		mongo.close();
	}

	public int size() {
		return (int) dbCollection.count();
	}

}
