package com.glaf.base.callback;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.glaf.base.utils.*;

public class CallbackProperties {

	private final static String MESSAGE_CONFIG = "/login.properties";

	private static Properties properties = new Properties();

	static {
		try {
			Properties p = reload();
			if (p != null) {
				Enumeration<?> e = p.keys();
				while (e.hasMoreElements()) {
					String key = (String) e.nextElement();
					String value = p.getProperty(key);
					properties.setProperty(key, value);
				}
			}
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
		return properties;
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

	public static Properties reload() {
		synchronized (CallbackProperties.class) {
			InputStream inputStream = null;
			try {
				Resource resource = new ClassPathResource(MESSAGE_CONFIG);
				System.out.println("load message config:"
						+ resource.getFile().getAbsolutePath());
				inputStream = new FileInputStream(resource.getFile()
						.getAbsolutePath());
				properties.clear();
				Properties p = PropertiesTools.loadProperties(inputStream);
				if (p != null) {
					Enumeration<?> e = p.keys();
					while (e.hasMoreElements()) {
						String key = (String) e.nextElement();
						String value = p.getProperty(key);
						properties.setProperty(key, value);
					}
				}
				return p;
			} catch (IOException ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			} finally {
				IOUtils.closeQuietly(inputStream);
			}
		}
	}

	private CallbackProperties() {

	}

}
