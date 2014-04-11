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
package com.glaf.shiro;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.PropertiesUtils;

public class SecurityConfig {

	private static volatile LinkedHashMap<String, String> properties = new LinkedHashMap<String, String>();

	protected static AtomicBoolean loading = new AtomicBoolean(false);

	static {
		try {
			reload();
		} catch (Exception ex) {
		}
	}

	public static boolean eq(String key, String value) {
		if (key != null && value != null) {
			String x = properties.get(key);
			if (StringUtils.equals(value, x)) {
				return true;
			}
		}
		return false;
	}

	public static boolean getBoolean(String key) {
		if (hasObject(key)) {
			String value = properties.get(key);
			return Boolean.valueOf(value).booleanValue();
		}
		return false;
	}

	public static double getDouble(String key) {
		if (hasObject(key)) {
			String value = properties.get(key);
			return Double.valueOf(value).doubleValue();
		}
		return 0;
	}

	public static int getInt(String key) {
		if (hasObject(key)) {
			String value = properties.get(key);
			return Integer.valueOf(value).intValue();
		}
		return 0;
	}

	public static long getLong(String key) {
		if (hasObject(key)) {
			String value = properties.get(key);
			return Long.valueOf(value).longValue();
		}
		return 0;
	}

	public static LinkedHashMap<String, String> getProperties() {
		return properties;
	}

	public static String getString(String key) {
		if (hasObject(key)) {
			String value = properties.get(key);
			if (value == null) {
				value = properties.get(key.toUpperCase());
			}
			return value;
		}
		return null;
	}

	public static boolean hasObject(String key) {
		if (properties == null || key == null) {
			return false;
		}
		String value = properties.get(key);
		if (value != null) {
			return true;
		}
		return false;
	}

	public static void reload() {
		InputStream inputStream = null;
		try {
			loading.set(true);
			String config = SystemProperties.getConfigRootPath()
					+ "/conf/security/";
			File directory = new File(config);
			if (directory.isDirectory()) {
				String[] filelist = directory.list();
				for (int i = 0; i < filelist.length; i++) {
					String filename = config + filelist[i];
					File file = new File(filename);
					if (file.isFile() && file.getName().endsWith(".properties")) {
						inputStream = new FileInputStream(file);
						LinkedHashMap<String, String> p = PropertiesUtils
								.load(inputStream);
						if (p != null) {
							Iterator<String> it = p.keySet().iterator();
							while (it.hasNext()) {
								String key = it.next();
								String value = p.get(key);
								properties.put(key, value);
							}
						}
						IOUtils.closeQuietly(inputStream);
						inputStream = null;
					}
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			loading.set(false);
			IOUtils.closeQuietly(inputStream);
		}
	}

	private SecurityConfig() {

	}

}
