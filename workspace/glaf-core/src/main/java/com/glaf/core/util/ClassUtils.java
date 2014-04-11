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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.StringUtils;

public class ClassUtils {

	private static ConcurrentMap<String, Class<?>> cache = new ConcurrentHashMap<String, Class<?>>();

	public static void addClassInCache(String className, Class<?> cls) {
		if (StringUtils.isNotEmpty(className) && cls != null) {
			ClassUtils.cache.put(className, cls);
		}
	}

	public static Class<?> classForName(String className) {
		return loadClass(className, null);
	}

	public static void clear() {
		ClassUtils.cache.clear();
	}

	@SuppressWarnings("unchecked")
	public static <T> Class<T> getClass(T o) {
		return (Class<T>) o.getClass();
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

	public static Object instantiateObject(String className) {
		return instantiateObject(className, null);
	}

	public static Object instantiateObject(String className,
			ClassLoader classLoader) {
		Class<?> cls = ClassUtils.cache.get(className);
		if (cls == null) {
			cls = loadClass(className, classLoader);
		}
		Object object = null;
		if (cls != null) {
			try {
				object = cls.newInstance();
			} catch (Throwable e) {
				throw new RuntimeException(
						"Unable to instantiate object for class '" + className
								+ "'", e);
			}
		}
		return object;
	}

	public static Class<?> loadClass(String className) {
		return loadClass(className, null);
	}

	public static Class<?> loadClass(String className, ClassLoader classLoader) {
		Class<?> cls = ClassUtils.cache.get(className);
		if (cls == null) {
			try {
				cls = Class.forName(className);
			} catch (Exception e) {
			}

			if (cls == null && classLoader != null) {
				try {
					cls = classLoader.loadClass(className);
				} catch (Exception e) {
				}
			}

			if (cls == null) {
				try {
					cls = ClassUtils.class.getClassLoader()
							.loadClass(className);
				} catch (Exception e) {
				}
			}

			if (cls == null) {
				try {
					cls = Thread.currentThread().getContextClassLoader()
							.loadClass(className);
				} catch (Exception e) {
				}
			}

			if (cls == null) {
				try {
					cls = ClassLoader.getSystemClassLoader().loadClass(
							className);
				} catch (Exception e) {
				}
			}

			if (cls == null) {

			}

			if (cls != null) {
				ClassUtils.cache.put(className, cls);
			} else {
				throw new RuntimeException("Unable to load class '" + className
						+ "'");
			}
		}

		return cls;
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

	public static void removeClassFromCache(String className) {
		ClassUtils.cache.remove(className);
	}

	private ClassUtils() {

	}

}