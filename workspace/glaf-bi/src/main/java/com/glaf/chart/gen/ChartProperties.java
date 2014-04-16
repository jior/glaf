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

package com.glaf.chart.gen;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.IOUtils;
import com.glaf.core.util.PropertiesUtils;

public class ChartProperties {

	protected final static String DEFAULT_CONFIG = "/conf/chart.properties";

	protected static volatile Properties properties = new Properties();

	protected static AtomicBoolean loading = new AtomicBoolean(false);

	protected static List<ChartType> types = new java.util.concurrent.CopyOnWriteArrayList<ChartType>();

	protected static ConcurrentMap<String, ChartType> chartTypes = new ConcurrentHashMap<String, ChartType>();

	static {
		try {
			reload();

		} catch (Exception ex) {
		}
	}

	public static boolean getBoolean(String key) {
		if (hasObject(key)) {
			String value = properties.getProperty(key);
			return Boolean.valueOf(value).booleanValue();
		}
		return false;
	}

	public static ChartType getChartType(String type) {
		return chartTypes.get(type);
	}

	public static List<ChartType> getChartTypes() {
		return types;
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

	public static void reload() {
		if (!loading.get()) {
			InputStream inputStream = null;
			try {
				loading.set(true);
				String filename = SystemProperties.getConfigRootPath()
						+ DEFAULT_CONFIG;
				Resource resource = new FileSystemResource(filename);
				if (resource.exists()) {
					System.out.println("load chart config:"
							+ resource.getFile().getAbsolutePath());
					inputStream = new FileInputStream(resource.getFile()
							.getAbsolutePath());
					properties.clear();
					Properties props = PropertiesUtils
							.loadProperties(inputStream);
					if (props != null) {
						Enumeration<?> e = props.keys();
						while (e.hasMoreElements()) {
							String key = (String) e.nextElement();
							String value = props.getProperty(key);
							properties.setProperty(key, value);
						}
					}

					if (properties.keys().hasMoreElements()) {
						Enumeration<?> e = properties.keys();
						while (e.hasMoreElements()) {
							String name = (String) e.nextElement();
							String value = properties.getProperty(name);
							if (StringUtils.isNotEmpty(value)
									&& (value.length() > 0 && value.charAt(0) == '{')
									&& value.endsWith("}")) {
								JSONObject jsonObject = JSON.parseObject(value);
								String className = jsonObject
										.getString("className");
								if (StringUtils.isNotEmpty(className)) {
									ChartType m = new ChartType();
									m.setClassName(className);
									m.setType(name);
									m.setTitle(jsonObject.getString("title"));
									Map<String, String> dataMap = new java.util.HashMap<String, String>();
									Set<Entry<String, Object>> entrySet = jsonObject
											.entrySet();
									for (Entry<String, Object> entry : entrySet) {
										String key = entry.getKey();
										Object v = entry.getValue();
										if (v != null) {
											dataMap.put(key, v.toString());
										}
									}
									m.setDataMap(dataMap);
									types.add(m);
									chartTypes.put(name, m);
								}
							}
						}
					}
				}
			} catch (IOException ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			} finally {
				loading.set(false);
				IOUtils.closeStream(inputStream);
			}
		}
	}

	private ChartProperties() {

	}

}
