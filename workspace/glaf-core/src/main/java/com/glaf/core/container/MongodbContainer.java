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

package com.glaf.core.container;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.IOUtils;
import com.glaf.core.util.PropertiesUtils;
import com.glaf.core.util.ShutdownHookManager;
import com.glaf.core.util.StringTools;
import com.mongodb.client.MongoDatabase;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

public class MongodbContainer {

	public static final String MONGODB_CONFIG = "/conf/mongodb.properties";

	protected static volatile Properties properties = new Properties();

	protected static AtomicBoolean loading = new AtomicBoolean(false);

	protected MongoClient mongoClient;

	private MongodbContainer() {
		reload();
		init();
	}

	private static class MongodbSingletonHolder {
		public static MongodbContainer instance = new MongodbContainer();
	}

	private synchronized void init() {
		if (mongoClient == null) {
			String servers = getString("servers");
			if (servers == null) {
				servers = "127.0.0.1:27017";
			}
			List<String> list = StringTools.split(servers, ",");
			List<ServerAddress> addrList = new ArrayList<ServerAddress>();
			for (String server : list) {
				String host = server.substring(0, server.indexOf(":"));
				int port = Integer.parseInt(server.substring(
						server.indexOf(":") + 1, server.length()));
				try {
					ServerAddress addr = new ServerAddress(host, port);
					addrList.add(addr);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			mongoClient = new MongoClient(addrList);
			mongoClient.setWriteConcern(WriteConcern.JOURNALED);
			Runnable shutdownHook = new MongodbShutdownHook(mongoClient);
			ShutdownHookManager.get().addShutdownHook(shutdownHook,
					Thread.NORM_PRIORITY);
		}
	}

	public MongoDatabase getDatabase(String dbname) {
		return getMongoClient().getDatabase(dbname);
	}

	@SuppressWarnings("deprecation")
	public DB getDB(String dbName) {
		return getMongoClient().getDB(dbName);
	}

	private MongoClient getMongoClient() {
		if (mongoClient == null) {
			this.init();
		}
		return mongoClient;
	}

	public static boolean getBoolean(String key) {
		if (hasObject(key)) {
			String value = properties.getProperty(key);
			return Boolean.valueOf(value).booleanValue();
		}
		return false;
	}

	public static double getDouble(String key) {
		if (hasObject(key)) {
			String value = properties.getProperty(key);
			return Double.valueOf(value).doubleValue();
		}
		return 0;
	}

	public static MongodbContainer getInstance() {
		return MongodbSingletonHolder.instance;
	}

	public static int getInt(String key) {
		if (hasObject(key)) {
			String value = properties.getProperty(key);
			return Integer.valueOf(value).intValue();
		}
		return 0;
	}

	public static long getLong(String key) {
		if (hasObject(key)) {
			String value = properties.getProperty(key);
			return Long.valueOf(value).longValue();
		}
		return 0;
	}

	public static Properties getProperties() {
		Properties p = new Properties();
		Enumeration<?> e = properties.keys();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = properties.getProperty(key);
			p.put(key, value);
		}
		return p;
	}

	public static String getString(String key) {
		if (hasObject(key)) {
			String value = properties.getProperty(key);
			return value;
		}
		return "";
	}

	public static boolean hasObject(String key) {
		if (key == null || properties == null) {
			return false;
		}
		String value = properties.getProperty(key);
		if (value != null) {
			return true;
		}
		return false;
	}

	private synchronized void reload() {
		if (!loading.get()) {
			InputStream inputStream = null;
			try {
				loading.set(true);

				String filename = SystemProperties.getConfigRootPath()
						+ MONGODB_CONFIG;
				inputStream = new FileInputStream(filename);
				System.out.println("load mongodb properties:" + filename);
				Properties p = PropertiesUtils.loadProperties(inputStream);
				if (p != null) {
					Enumeration<?> e = p.keys();
					while (e.hasMoreElements()) {
						String key = (String) e.nextElement();
						String value = p.getProperty(key);
						properties.setProperty(key, value);
						properties.setProperty(key.toLowerCase(), value);
						properties.setProperty(key.toUpperCase(), value);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			} finally {
				loading.set(false);
				IOUtils.closeStream(inputStream);
			}
		}
	}

	protected synchronized void reloadAndReset() {
		reload();
		if (mongoClient != null) {
			mongoClient.close();
			mongoClient = null;
		}
		init();
	}

}