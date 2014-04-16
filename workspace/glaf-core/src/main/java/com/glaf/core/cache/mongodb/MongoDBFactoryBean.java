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

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;
import com.mongodb.*;

public class MongoDBFactoryBean implements FactoryBean<DB> {

	private Mongo mongo;

	private String name;

	@Override
	public DB getObject() throws Exception {
		Assert.notNull(mongo);
		Assert.notNull(name);
		return mongo.getDB(name);
	}

	@Override
	public Class<?> getObjectType() {
		return DB.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Required
	public void setMongo(Mongo mongo) {
		this.mongo = mongo;
	}

	@Required
	public void setName(String name) {
		this.name = name;
	}
}
