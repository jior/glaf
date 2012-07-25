package com.glaf.base.modules.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ContextUtil {
	private final static Log logger = LogFactory.getLog(ContextUtil.class);
	private static ContextUtil instance = new ContextUtil();
	private static Map data = new HashMap();

	public static Object get(Object key) {
		if (data.containsKey(key)) {
			return data.get(key);
		}
		return null;
	}

	public static String getContextPath() {
		return (String) data.get("__contextPath__");
	}

	public static synchronized ContextUtil getInstance() throws Exception {
		logger.info("getInstance");
		return instance;
	}

	public static void put(Object key, Object value) {
		data.put(key, value);
	}

	public static void setContextPath(String contextPath) {
		data.put("__contextPath__", contextPath);
	}
}
