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

import java.io.IOException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import com.glaf.core.util.Constants;

public class SystemProperties {

	private static final Configuration conf = BaseConfiguration.create();

	private static String ROOT_CONF_PATH = null;

	private static String ROOT_APP_PATH = null;

	public static String getAppPath() {
		if (ROOT_APP_PATH == null) {
			reload();
		}
		return ROOT_APP_PATH;
	}

	public static boolean getBoolean(String key) {
		if (hasObject(key)) {
			String value = conf.get(key);
			return Boolean.valueOf(value).booleanValue();
		}
		return false;
	}

	public static String getConfigRootPath() {
		if (ROOT_CONF_PATH == null) {
			reload();
		}
		return ROOT_CONF_PATH;
	}

	public static double getDouble(String key) {
		if (hasObject(key)) {
			String value = conf.get(key);
			return Double.parseDouble(value);
		}
		return 0;
	}

	public static int getInt(String key) {
		if (hasObject(key)) {
			String value = conf.get(key);
			return Integer.parseInt(value);
		}
		return 0;
	}

	public static long getLong(String key) {
		if (hasObject(key)) {
			String value = conf.get(key);
			return Long.parseLong(value);
		}
		return 0;
	}

	public static String getString(String key) {
		if (hasObject(key)) {
			String value = conf.get(key);
			return value;
		}
		return null;
	}

	public static String getString(String key, String defaultValue) {
		if (hasObject(key)) {
			String value = conf.get(key);
			return value;
		}
		return defaultValue;
	}

	public static boolean hasObject(String key) {
		String value = conf.get(key);
		if (value != null) {
			return true;
		}
		return false;
	}

	public synchronized static void reload() {
		try {
			Resource resource = new ClassPathResource(Constants.SYSTEM_CONFIG);
			ROOT_CONF_PATH = resource.getFile().getParentFile().getParentFile()
					.getAbsolutePath();
			ROOT_APP_PATH = resource.getFile().getParentFile().getParentFile()
					.getParentFile().getAbsolutePath();
			System.out.println("load system config:"
					+ resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	private SystemProperties() {

	}

}