package com.glaf.generator;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import com.glaf.core.util.FileUtils;
import com.glaf.core.util.PropertiesUtils;

public class PropertiesToXml {

	public final static String sp = System.getProperty("file.separator");

	public final static String newline = System.getProperty("line.separator");

	public static void convert(String input, String output) throws Exception {
		Properties p = PropertiesUtils
				.loadProperties(new FileInputStream(input));
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append(newline);
		sb.append("<configuration>");
		sb.append(newline);
		Map<String, String> map = new TreeMap<String, String>();
		Enumeration<?> e = p.keys();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = p.getProperty(key);
			// System.out.println(key + "=" + value);
			map.put(key, value);
		}
		if (map != null && map.size() > 0) {
			Set<Entry<String, String>> entrySet = map.entrySet();
			for (Entry<String, String> entry : entrySet) {
				String key = entry.getKey();
				String value = entry.getValue();
				sb.append("  <property>");
				sb.append(newline);
				sb.append("    <name>").append(key).append("</name>");
				sb.append(newline);
				sb.append("    <value>").append(value).append("</value>");
				sb.append(newline);
				sb.append("  </property>");
				sb.append(newline);
			}
		}
		sb.append(newline);
		sb.append("</configuration>");
		FileUtils.save(output, sb.toString().getBytes("utf-8"));
	}

	public static void main(String[] args) throws Exception {
		convert("messages.properties", "messages-default.xml");
		// java.util.Random r = new Random();
		Map<Integer, Integer> map = new TreeMap<Integer, Integer>();
		for (int i = 0; i < 10000; i++) {
			int v = (UUID.randomUUID().toString().hashCode() & Integer.MAX_VALUE) % 20;
			System.out.println(v);
			Integer t = map.get(v);
			if (t != null) {
				map.put(v, t + 1);
			} else {
				map.put(v, 1);
			}
		}
		System.out.println(map);
	}

}
