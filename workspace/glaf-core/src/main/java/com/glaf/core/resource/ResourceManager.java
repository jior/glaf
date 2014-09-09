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

package com.glaf.core.resource;

import java.io.File;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.IOUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分布式配置管理器
 * 
 * 
 */
class ResourceManager {

	private final static Logger log = LoggerFactory
			.getLogger(ResourceManager.class);
	private final static String CONFIG_FILE = "/conf/resource.properties";

	private static ResourceProvider _provider;

	private final static Resource _GetResource(String resource_name,
			boolean autoCreate) {
		return (_provider).buildResource(resource_name, autoCreate);
	}

	/**
	 * Clear the resource
	 */
	public final static void clear(String region) {
		Resource resource = _GetResource(region, false);
		if (resource != null) {
			resource.clear();
		}
	}

	/**
	 * 清除分布式配置中的某个数据
	 * 
	 * 
	 * @param region
	 * @param key
	 */
	public final static void remove(String region, String key) {
		if (region != null && key != null) {
			Resource resource = _GetResource(region, false);
			if (resource != null) {
				resource.remove(key);
			}
		}
	}

	/**
	 * 获取分布式配置中的数据
	 * 
	 * @param region
	 * @param key
	 * @return
	 */
	public final static byte[] getData(String region, String key) {
		if (region != null && key != null) {
			Resource resource = _GetResource(region, false);
			if (resource != null) {
				return resource.getData(key);
			}
		}
		return null;
	}

	private final static ResourceProvider getProviderInstance(String value)
			throws Exception {
		String className = value;
		if ("hazelcast".equalsIgnoreCase(value)) {
			className = "com.glaf.core.resource.hazelcast.HazelcastResourceProvider";
		}
		if ("mongodb".equalsIgnoreCase(value)) {
			className = "com.glaf.core.resource.mongodb.MongodbResourceProvider";
		}
		if ("redis".equalsIgnoreCase(value)) {
			className = "com.glaf.core.resource.redis.RedisResourceProvider";
		}

		return (ResourceProvider) Class.forName(className).newInstance();
	}

	private final static Properties getProviderProperties(Properties props,
			ResourceProvider provider) {
		Properties new_props = new Properties();
		Enumeration<Object> keys = props.keys();
		String prefix = provider.name() + '.';
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			if (key.startsWith(prefix))
				new_props.setProperty(key.substring(prefix.length()),
						props.getProperty(key));
		}
		return new_props;
	}

	public static void initResourceProvider() {
		InputStream resourceStream = null;
		Properties props = new Properties();
		try {
			String filename = SystemProperties.getConfigRootPath()
					+ CONFIG_FILE;
			File file = new File(filename);
			if (file.exists() && file.isFile()) {
				resourceStream = FileUtils.getInputStream(filename);
				props.load(resourceStream);

				ResourceManager._provider = getProviderInstance(props
						.getProperty("config.provider_class"));
				ResourceManager._provider.start(getProviderProperties(props,
						ResourceManager._provider));
				log.info("Using ResourceProvider : "
						+ _provider.getClass().getName());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("Unabled to initialize config providers", ex);
		} finally {
			IOUtils.closeStream(resourceStream);
		}
	}

	/**
	 * 写入分布式配置
	 * 
	 * @param region
	 * @param key
	 * @param value
	 */
	public final static void put(String region, String key, byte[] value) {
		if (region != null && key != null && value != null) {
			Resource resource = _GetResource(region, true);
			if (resource != null) {
				resource.put(key, value);
			}
		}
	}

	/**
	 * 关闭分布式配置
	 */
	public final static void shutdown() {
		_provider.stop();
	}

}
