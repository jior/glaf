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

package com.glaf.core.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.glaf.core.util.PropertiesUtils;

public class SystemConfig {

	private final static String SYSTEM_CONFIG = "/glaf.properties";

	public 	static Map<String, Object> contextMap = new HashMap<String, Object>();
	
	private static Properties properties = new Properties();

	private static String ROOT_CONF_PATH = null;
	
	private static String ROOT_APP_PATH = null;

	static {
		try {
			reload();
		} catch (Exception ex) {
		}
	}

	public static boolean eq(String key, String value) {
		if (key != null && value != null) {
			String x = properties.getProperty(key);
			if (StringUtils.equals(value, x)) {
				return true;
			}
		}
		return false;
	}

	public static boolean getBoolean(String key) {
		if (hasObject(key)) {
			String value = properties.getProperty(key);
			return Boolean.valueOf(value).booleanValue();
		}
		return false;
	}
	
	public static String getAppPath() {
		if (ROOT_APP_PATH == null) {
			reload();
		}
		return ROOT_APP_PATH;
	}

	public static String getConfigRootPath() {
		if (ROOT_CONF_PATH == null) {
			reload();
		}
		return ROOT_CONF_PATH;
	}

	public static double getDouble(String key) {
		if (hasObject(key)) {
			String value = properties.getProperty(key);
			return Double.parseDouble(value);
		}
		return 0;
	}

	public static int getInt(String key) {
		if (hasObject(key)) {
			String value = properties.getProperty(key);
			return Integer.parseInt(value);
		}
		return 0;
	}

	public static long getLong(String key) {
		if (hasObject(key)) {
			String value = properties.getProperty(key);
			return Long.parseLong(value);
		}
		return 0;
	}

	public static Properties getProperties() {
		return properties;
	}

	public static String getString(String key) {
		if (hasObject(key)) {
			String value = properties.getProperty(key);
			if (value == null) {
				value = properties.getProperty(key.toUpperCase());
			}
			return value;
		}
		return null;
	}

	public static boolean hasObject(String key) {
		if (properties == null || key == null) {
			return false;
		}
		String value = properties.getProperty(key);
		if (value != null) {
			return true;
		}
		return false;
	}
	
	
	public 	static Map<String, Object> getContextMap(){
		return contextMap;
	}
	

	public synchronized static void reload() {
		try {
			Resource resource = new ClassPathResource(SYSTEM_CONFIG);
			ROOT_CONF_PATH = resource.getFile().getParentFile()
					.getParentFile().getAbsolutePath();
			ROOT_APP_PATH = resource.getFile().getParentFile().getParentFile()
					.getParentFile().getAbsolutePath();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		try {
			String config = getConfigRootPath() + "/conf/system/";
			File directory = new File(config);
			if (directory.isDirectory()) {
				String[] filelist = directory.list();
				for (int i = 0; i < filelist.length; i++) {
					String filename = config + filelist[i];
					File file = new File(filename);
					if (file.isFile() && file.getName().endsWith(".properties")) {
						InputStream inputStream = new FileInputStream(file);
						Properties p = PropertiesUtils
								.loadProperties(inputStream);
						if (p != null) {
							Enumeration<?> e = p.keys();
							while (e.hasMoreElements()) {
								String key = (String) e.nextElement();
								String value = p.getProperty(key);
								contextMap.put(key, value);
								properties.setProperty(key, value);
								properties
										.setProperty(key.toLowerCase(), value);
								properties
										.setProperty(key.toUpperCase(), value);
							}
						}
						inputStream.close();
						inputStream = null;
					}
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private SystemConfig() {

	}

}