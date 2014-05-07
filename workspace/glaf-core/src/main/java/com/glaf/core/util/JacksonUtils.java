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

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * 
 * @see http://jackson.codehaus.org/
 * @see https://github.com/FasterXML/jackson
 * @see http://wiki.fasterxml.com/JacksonHome
 * 
 */
@SuppressWarnings("unchecked")
public class JacksonUtils {
	private static ObjectMapper objectMapper = new ObjectMapper();
	private static XmlMapper xmlMapper = new XmlMapper();

	/**
	 * javaBean,list,array convert to json string
	 */
	public static String obj2json(Object obj) throws Exception {
		return objectMapper.writeValueAsString(obj);
	}

	/**
	 * json string convert to javaBean
	 */
	public static <T> T json2pojo(String jsonStr, Class<T> clazz)
			throws Exception {
		return objectMapper.readValue(jsonStr, clazz);
	}

	/**
	 * json string convert to map
	 */
	public static <T> Map<String, Object> json2map(String jsonStr)
			throws Exception {
		return objectMapper.readValue(jsonStr, Map.class);
	}

	/**
	 * json string convert to map with javaBean
	 */
	public static <T> Map<String, T> json2map(String jsonStr, Class<T> clazz)
			throws Exception {
		Map<String, Map<String, Object>> map = objectMapper.readValue(jsonStr,
				new TypeReference<Map<String, T>>() {
				});
		Map<String, T> result = new HashMap<String, T>();
		for (Entry<String, Map<String, Object>> entry : map.entrySet()) {
			result.put(entry.getKey(), map2pojo(entry.getValue(), clazz));
		}
		return result;
	}

	/**
	 * json array string convert to list with javaBean
	 */
	public static <T> List<T> json2list(String jsonArrayStr, Class<T> clazz)
			throws Exception {
		List<Map<String, Object>> list = objectMapper.readValue(jsonArrayStr,
				new TypeReference<List<T>>() {
				});
		List<T> result = new ArrayList<T>();
		for (Map<String, Object> map : list) {
			result.add(map2pojo(map, clazz));
		}
		return result;
	}

	/**
	 * map convert to javaBean
	 */
	@SuppressWarnings("rawtypes")
	public static <T> T map2pojo(Map map, Class<T> clazz) {
		return objectMapper.convertValue(map, clazz);
	}

	/**
	 * json string convert to xml string
	 */
	public static String json2xml(String jsonStr) throws Exception {
		JsonNode root = objectMapper.readTree(jsonStr);
		String xml = xmlMapper.writeValueAsString(root);
		return xml;
	}
	
	protected static JsonGenerator configure(JsonGenerator generator, boolean pretty) {
		generator.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
		if (pretty) {
			generator.useDefaultPrettyPrinter();
		}
		return generator;
	}

	/**
	 * xml string convert to json string
	 */
	public static String xml2json(String xml) throws Exception {
		StringWriter w = new StringWriter();
		JsonParser jp = xmlMapper.getFactory().createParser(xml);
		JsonGenerator jg = objectMapper.getFactory().createGenerator(w);
		configure(jg, true);
		while (jp.nextToken() != null) {
			jg.copyCurrentEvent(jp);
		}
		jp.close();
		jg.close();
		return w.toString();
	}

}
