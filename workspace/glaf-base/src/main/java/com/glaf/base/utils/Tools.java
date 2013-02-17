/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.base.utils;

import java.awt.Color;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
	
	public static String getFileExt(String filename) {
		String value = "";
		int start = 0;
		int end = 0;
		if (filename == null) {
			return value;
		}
		start = filename.lastIndexOf(46) + 1;
		end = filename.length();
		value = filename.substring(start, end);
		if (filename.lastIndexOf(46) > 0) {
			return value;
		} else {
			return "";
		}
	}

	public static String getFilename(String filename) {
		String value = "";
		int start = 0;
		int end = 0;
		if (filename == null) {
			return value;
		}
		if (filename.indexOf("/") != -1) {
			start = filename.lastIndexOf("/") + 1;
			end = filename.length();
			value = filename.substring(start, end);
			if (filename.lastIndexOf("/") > 0) {
				return value;
			}
		} else if (filename.indexOf("\\") != -1) {
			start = filename.lastIndexOf("\\") + 1;
			end = filename.length();
			value = filename.substring(start, end);
			if (filename.lastIndexOf("\\") > 0) {
				return value;
			}
		}
		return filename;
	}

	public static Map<String, Class<?>> getPropertyMap(Class<?> clazz) {
		Map<String, Class<?>> dataMap = new HashMap<String, Class<?>>();
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
				value = Integer.valueOf(propertyValue);
			} else if ((type == Long.class) || (type == long.class)) {
				if (propertyValue.indexOf(',') != -1) {
					propertyValue = propertyValue.replaceAll(",", "");
				}
				value = Long.valueOf(propertyValue);
			} else if ((type == Float.class) || (type == float.class)) {
				if (propertyValue.indexOf(',') != -1) {
					propertyValue = propertyValue.replaceAll(",", "");
				}
				value = Float.valueOf(propertyValue);
			} else if ((type == Double.class) || (type == double.class)) {
				if (propertyValue.indexOf(',') != -1) {
					propertyValue = propertyValue.replaceAll(",", "");
				}
				value = Double.valueOf(propertyValue);
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
				value = DateTools.toDate(propertyValue);
			} else if (type == java.sql.Date.class) {
				value = DateTools.toDate(propertyValue);
			} else if (type == java.sql.Timestamp.class) {
				value = DateTools.toDate(propertyValue);
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

	public static Object instantiateClass(String className) {
		try {
			Class<?> clazz = Class.forName(className);
			return org.springframework.beans.BeanUtils.instantiateClass(clazz);
		} catch (Throwable e) {
			throw new RuntimeException(
					"Unable to instantiate object for class '" + className
							+ "'", e);
		}
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
		StringBuffer localStringBuffer = new StringBuffer("rgb(");
		localStringBuffer.append(paramColor.getRed());
		localStringBuffer.append(',');
		localStringBuffer.append(paramColor.getGreen());
		localStringBuffer.append(',');
		localStringBuffer.append(paramColor.getBlue());
		localStringBuffer.append(')');
		return localStringBuffer.toString();
	}

	public static Class<?> loadClass(String className) {
		try {
			Class<?> clazz = Class.forName(className);
			return clazz;
		} catch (Throwable e) {
			throw new RuntimeException(
					"Unable to instantiate object for class '" + className
							+ "'", e);
		}
	}

	public static Object newObject(Class<?> clazz) {
		try {
			return org.springframework.beans.BeanUtils.instantiateClass(clazz);
		} catch (Throwable e) {
			throw new RuntimeException(
					"Unable to instantiate object for class '"
							+ clazz.getName() + "'", e);
		}
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
						ReflectUtil.setFieldValue(model, propertyName, x);
					}
				} catch (Exception ex) {
					logger.debug(ex);
				}
			}
		}
	}

	public static List<Object> toList(Collection<Object> rows){
		List<Object> list = new ArrayList<Object>();
		if(rows != null && !rows.isEmpty()){
		for(Object object:rows){
			list.add(object);
		}
		}
		return list;
	}

	public static List<String> toStringList(Collection<String> rows){
		List<String> list = new ArrayList<String>();
		if(rows != null && !rows.isEmpty()){
		for(String str:rows){
			list.add(str);
		}
		}
		return list;
	}

	private Tools() {

	}

}