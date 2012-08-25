package com.glaf.base.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class ClassUtil {

	private static Map<String, Class<?>> cache = Collections
			.synchronizedMap(new HashMap<String, Class<?>>());

	public static void addClassInCache(String className, Class<?> cls) {
		if (StringUtils.isNotEmpty(className) && cls != null) {
			ClassUtil.cache.put(className, cls);
		}
	}

	public static void clear() {
		ClassUtil.cache.clear();
	}

	public static Object instantiateObject(String className) {
		return instantiateObject(className, null);
	}

	public static Object instantiateObject(String className,
			ClassLoader classLoader) {
		Class<?> cls =  ClassUtil.cache.get(className);
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
	
	public static Class<?> classForName(String className){
		return loadClass(className, null);
	}

	public static Class<?> loadClass(String className) {
		return loadClass(className, null);
	}

	public static Class<?> loadClass(String className, ClassLoader classLoader) {
		Class<?> cls =  ClassUtil.cache.get(className);
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
					cls = ClassUtil.class.getClassLoader().loadClass(className);
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
				ClassUtil.cache.put(className, cls);
			} else {
				throw new RuntimeException("Unable to load class '" + className
						+ "'");
			}
		}

		return cls;
	}

	public static void removeClassFromCache(String className) {
		ClassUtil.cache.remove(className);
	}

	private ClassUtil() {

	}

}
