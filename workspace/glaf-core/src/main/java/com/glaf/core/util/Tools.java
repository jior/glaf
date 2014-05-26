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

package com.glaf.core.util;

import java.awt.Color;
import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

public class Tools {
	protected final static Log logger = LogFactory.getLog(Tools.class);

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getDataMap(Object target) {
		Map<String, Object> dataMap = new TreeMap<String, Object>();
		if (Map.class.isAssignableFrom(target.getClass())) {
			Map<String, Object> map = (Map<String, Object>) target;
			Set<Entry<String, Object>> entrySet = map.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (key != null && value != null) {
					dataMap.put(key.toString(), value);
				}
			}
		} else {
			PropertyDescriptor[] propertyDescriptor = BeanUtils
					.getPropertyDescriptors(target.getClass());
			for (int i = 0; i < propertyDescriptor.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptor[i];
				String propertyName = descriptor.getName();
				if (propertyName.equalsIgnoreCase("class")) {
					continue;
				}
				try {
					Object value = PropertyUtils.getProperty(target,
							propertyName);
					dataMap.put(propertyName, value);
				} catch (Exception ex) {

				}
			}
		}
		return dataMap;
	}

	public static Map<String, Class<?>> getPropertyMap(Class<?> clazz) {
		Map<String, Class<?>> dataMap = new java.util.HashMap<String, Class<?>>();
		PropertyDescriptor[] propertyDescriptor = BeanUtils
				.getPropertyDescriptors(clazz);
		for (int i = 0; i < propertyDescriptor.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptor[i];
			String propertyName = descriptor.getName();
			if (propertyName.equalsIgnoreCase("class")) {
				continue;
			}
			dataMap.put(propertyName, descriptor.getPropertyType());
		}
		return dataMap;
	}

	public static Map<String, Class<?>> getPropertyMap(Object model) {
		return getPropertyMap(model.getClass());
	}

	public static Object getValue(Class<?> type, String propertyValue) {
		if (type == null || propertyValue == null
				|| propertyValue.trim().length() == 0) {
			return null;
		}
		Object value = null;
		try {
			if (type == String.class) {
				value = propertyValue;
			} else if ((type == Integer.class) || (type == int.class)) {
				if (propertyValue.indexOf(',') != -1) {
					propertyValue = propertyValue.replaceAll(",", "");
				}
				value = Integer.parseInt(propertyValue);
			} else if ((type == Long.class) || (type == long.class)) {
				if (propertyValue.indexOf(',') != -1) {
					propertyValue = propertyValue.replaceAll(",", "");
				}
				value = Long.parseLong(propertyValue);
			} else if ((type == Float.class) || (type == float.class)) {
				if (propertyValue.indexOf(',') != -1) {
					propertyValue = propertyValue.replaceAll(",", "");
				}
				value = Float.valueOf(propertyValue);
			} else if ((type == Double.class) || (type == double.class)) {
				if (propertyValue.indexOf(',') != -1) {
					propertyValue = propertyValue.replaceAll(",", "");
				}
				value = Double.parseDouble(propertyValue);
			} else if ((type == Boolean.class) || (type == boolean.class)) {
				value = Boolean.valueOf(propertyValue);
			} else if ((type == Character.class) || (type == char.class)) {
				value = Character.valueOf(propertyValue.charAt(0));
			} else if ((type == Short.class) || (type == short.class)) {
				if (propertyValue.indexOf(',') != -1) {
					propertyValue = propertyValue.replaceAll(",", "");
				}
				value = Short.valueOf(propertyValue);
			} else if ((type == Byte.class) || (type == byte.class)) {
				value = Byte.valueOf(propertyValue);
			} else if (type == java.util.Date.class) {
				value = DateUtils.toDate(propertyValue);
			} else if (type == java.sql.Date.class) {
				value = DateUtils.toDate(propertyValue);
			} else if (type == java.sql.Timestamp.class) {
				value = DateUtils.toDate(propertyValue);
			} else if (type.isAssignableFrom(List.class)) {
			} else if (type.isAssignableFrom(Set.class)) {
			} else if (type.isAssignableFrom(Collection.class)) {
			} else if (type.isAssignableFrom(Map.class)) {
			} else {
				value = propertyValue;
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return value;
	}

	public static boolean isDatabaseField(String sourceString) {
		if (sourceString == null || sourceString.trim().length() < 2
				|| sourceString.trim().length() > 26) {
			return false;
		}
		char[] sourceChrs = sourceString.toCharArray();
		Character chr = Character.valueOf(sourceChrs[0]);
		if (!((chr.charValue() == 95)
				|| (65 <= chr.charValue() && chr.charValue() <= 90) || (97 <= chr
				.charValue() && chr.charValue() <= 122))) {
			return false;
		}
		for (int i = 1; i < sourceChrs.length; i++) {
			chr = Character.valueOf(sourceChrs[i]);
			if (!((chr.charValue() == 95)
					|| (47 <= chr.charValue() && chr.charValue() <= 57)
					|| (65 <= chr.charValue() && chr.charValue() <= 90) || (97 <= chr
					.charValue() && chr.charValue() <= 122))) {
				return false;
			}
		}
		return true;
	}

	public static String javaColorToCSSColor(Color paramColor) {
		StringBuffer localStringBuffer = new StringBuffer(30);
		localStringBuffer.append("rgb(");
		localStringBuffer.append(paramColor.getRed());
		localStringBuffer.append(',');
		localStringBuffer.append(paramColor.getGreen());
		localStringBuffer.append(',');
		localStringBuffer.append(paramColor.getBlue());
		localStringBuffer.append(')');
		return localStringBuffer.toString();
	}

	@SuppressWarnings("unchecked")
	public static void populate(Object model, Map<String, Object> dataMap) {
		if (model instanceof Map) {
			Map<String, Object> map = (Map<String, Object>) model;
			Set<Entry<String, Object>> entrySet = map.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				if (dataMap.containsKey(key)) {
					map.put(key, dataMap.get(key));
				}
			}
		} else {
			PropertyDescriptor[] propertyDescriptor = BeanUtils
					.getPropertyDescriptors(model.getClass());
			for (int i = 0; i < propertyDescriptor.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptor[i];
				String propertyName = descriptor.getName();
				if (propertyName.equalsIgnoreCase("class")) {
					continue;
				}
				String value = null;
				Object x = null;
				Object object = dataMap.get(propertyName);
				if (object != null && object instanceof String) {
					value = (String) object;
				}
				try {
					Class<?> clazz = descriptor.getPropertyType();
					if (value != null) {
						x = getValue(clazz, value);
					} else {
						x = object;
					}
					if (x != null) {
						// PropertyUtils.setProperty(model, propertyName, x);
						ReflectUtils.setFieldValue(model, propertyName, x);
					}
				} catch (Exception ex) {
					logger.debug(ex);
				}
			}
		}
	}

	private Tools() {

	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		System.out.println(org.apache.commons.codec.digest.DigestUtils
				.md5Hex("jdbc:sqlserver://127.0.0.1:1433;databaseName=yz"));
		for (int i = 0; i < 10000; i++) {
			System.out.println(org.apache.commons.codec.digest.DigestUtils
					.md5Hex("jdbc:sqlserver://127.0.0.1:1433;databaseName=yz"
							+ i));
		}
		long times = System.currentTimeMillis() - start;
		System.out.println("总共耗时(毫秒):" + times);
	}

}