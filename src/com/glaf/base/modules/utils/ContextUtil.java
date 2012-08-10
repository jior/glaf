package com.glaf.base.modules.utils;

import java.util.HashMap;
import java.util.Map;

public class ContextUtil {
	private static ContextUtil instance = new ContextUtil();
	private static Map<Object, Object> dataMap = new HashMap<Object, Object>();

	private ContextUtil() {

	}

	public static Object get(Object key) {
		if (dataMap.containsKey(key)) {
			return dataMap.get(key);
		}
		return null;
	}

	public static String getContextPath() {
		return (String) dataMap.get("__contextPath__");
	}

	public static synchronized ContextUtil getInstance() {
		return instance;
	}

	public static void put(Object key, Object value) {
		dataMap.put(key, value);
	}

	public static void setContextPath(String contextPath) {
		dataMap.put("__contextPath__", contextPath);
	}
}
