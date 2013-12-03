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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import com.glaf.core.util.Constants;
import com.glaf.core.util.PropertiesUtils;

public class MailProperties {
	private static Properties conf = new Properties();

	static {
		reload();
	}

	public static boolean getBoolean(String key) {
		if (hasObject(key)) {
			String value = conf.getProperty(key);
			return Boolean.valueOf(value).booleanValue();
		}
		return false;
	}

	public static boolean getBoolean(String key, boolean defaultValue) {
		if (hasObject(key)) {
			String value = conf.getProperty(key);
			return Boolean.valueOf(value).booleanValue();
		}
		return defaultValue;
	}

	public static double getDouble(String key) {
		if (hasObject(key)) {
			String value = conf.getProperty(key);
			return Double.parseDouble(value);
		}
		return 0;
	}

	public static int getInt(String key) {
		if (hasObject(key)) {
			String value = conf.getProperty(key);
			return Integer.parseInt(value);
		}
		return 0;
	}

	public static long getLong(String key) {
		if (hasObject(key)) {
			String value = conf.getProperty(key);
			return Long.parseLong(value);
		}
		return 0;
	}

	public static String getString(String key) {
		if (hasObject(key)) {
			String value = conf.getProperty(key);
			return value;
		}
		return null;
	}

	public static String getString(String key, String defaultValue) {
		if (hasObject(key)) {
			String value = conf.getProperty(key);
			return value;
		}
		return defaultValue;
	}

	public static boolean hasObject(String key) {
		String value = conf.getProperty(key);
		if (value != null) {
			return true;
		}
		return false;
	}

	public static void reload() {
		try {
			String filename = SystemProperties.getConfigRootPath()
					+ Constants.MAIL_CONFIG;
			java.io.FileInputStream fis = new FileInputStream(filename);
			System.out.println("load mail config:" + filename);
			Properties p = PropertiesUtils.loadProperties(fis);
			if (p != null) {
				Enumeration<?> e = p.keys();
				while (e.hasMoreElements()) {
					String key = (String) e.nextElement();
					String value = p.getProperty(key);
					conf.setProperty(key, value);
					conf.setProperty(key.toLowerCase(), value);
					conf.setProperty(key.toUpperCase(), value);
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	private MailProperties() {

	}

}