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

package com.glaf.base.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import com.glaf.base.utils.PropertiesTools;

public class SystemConfig {

	private final static String SYSTEM_CONFIG = "/glaf.properties";

	private static Properties properties = new Properties();

	private static String ROOT_CONF_PATH = null;

	private static String APP_ROOT_PATH = null;

	static {
		try {
			Properties p = reload();
			if (p != null) {
				Enumeration<?> e = p.keys();
				while (e.hasMoreElements()) {
					String key = (String) e.nextElement();
					String value = p.getProperty(key);
					properties.setProperty(key, value);
				}
			}
		} catch (Exception ex) {
		}
	}

	public static String getAppRootPath() {
		if (APP_ROOT_PATH == null) {
			try {
				Resource resource = new ClassPathResource(SYSTEM_CONFIG);
				ROOT_CONF_PATH = resource.getFile().getParentFile()
						.getParentFile().getAbsolutePath();
				APP_ROOT_PATH = resource.getFile().getParentFile()
						.getParentFile().getParentFile().getAbsolutePath();
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}
		return APP_ROOT_PATH;
	}

	public static boolean getBoolean(String key) {
		if (hasObject(key)) {
			String value = properties.getProperty(key);
			return Boolean.valueOf(value).booleanValue();
		}
		return false;
	}

	public static String getConfigRootPath() {
		if (ROOT_CONF_PATH == null) {
			try {
				Resource resource = new ClassPathResource(SYSTEM_CONFIG);
				ROOT_CONF_PATH = resource.getFile().getParentFile()
						.getParentFile().getAbsolutePath();
				APP_ROOT_PATH = resource.getFile().getParentFile()
						.getParentFile().getParentFile().getAbsolutePath();
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}
		return ROOT_CONF_PATH;
	}

	public static double getDouble(String key) {
		if (hasObject(key)) {
			String value = properties.getProperty(key);
			return Double.valueOf(value).doubleValue();
		}
		return 0;
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
		return properties;
	}

	public static String getString(String key) {
		if (hasObject(key)) {
			String value = properties.getProperty(key);
			return value;
		}
		return null;
	}

	public static boolean hasObject(String key) {
		String value = properties.getProperty(key);
		if (value != null) {
			return true;
		}
		return false;
	}

	public static Properties reload() {
		synchronized (SystemConfig.class) {
			InputStream inputStream = null;
			try {
				Resource resource = new ClassPathResource(SYSTEM_CONFIG);
				ROOT_CONF_PATH = resource.getFile().getParentFile()
						.getParentFile().getAbsolutePath();
				System.out.println("load system config:"
						+ resource.getFile().getAbsolutePath());
				inputStream = new FileInputStream(resource.getFile()
						.getAbsolutePath());
				properties.clear();
				Properties p = PropertiesTools.loadProperties(inputStream);
				if (p != null) {
					Enumeration<?> e = p.keys();
					while (e.hasMoreElements()) {
						String key = (String) e.nextElement();
						String value = p.getProperty(key);
						properties.setProperty(key, value);
					}
				}
				return p;
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			} finally {
				IOUtils.closeQuietly(inputStream);
			}
		}
	}

	public static void save(Map<String, Object> dataMap) {
		Resource resource = new ClassPathResource(SYSTEM_CONFIG);
		try {
			System.out.println("save system config:"
					+ resource.getFile().getAbsolutePath());
			PropertiesTools.save(resource, dataMap);
			reload();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	private SystemConfig() {

	}

}
