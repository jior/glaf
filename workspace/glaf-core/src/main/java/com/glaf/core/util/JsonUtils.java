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

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class JsonUtils {

	public static String encode(Map<String, Object> params) {
		JSONObject jsonObject = null;
		String str = null;
		if (params != null) {
			jsonObject = new JSONObject();
			Set<Entry<String, Object>> entrySet = params.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				Object value = entry.getValue();
				try {
					if (value != null) {
						if (value instanceof Object[]) {
							Object[] array = (Object[]) value;
							Collection<Object> collection = new java.util.ArrayList<Object>();
							for (int i = 0; i < array.length; i++) {
								collection.add(array[i]);
							}
							jsonObject.put(key, collection);
						} else if (value instanceof Collection<?>) {
							Collection<?> collection = (Collection<?>) value;
							jsonObject.put(key, collection);
						} else if (value instanceof Map<?, ?>) {
							Map<?, ?> map = (Map<?, ?>) value;
							jsonObject.put(key, map);
						} else if (value instanceof java.util.Date) {
							java.util.Date date = (java.util.Date) value;
							String datetime = DateUtils.getDateTime(date);
							jsonObject.put(key, datetime);
						} else {
							jsonObject.put(key, value);
						}
					}
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
			str = jsonObject.toString();
		}
		return str;
	}

	public static Map<String, Object> decode(String str) {
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		if (StringUtils.isNotEmpty(str)
				&& (str.length() > 0 && str.charAt(0) == '{')
				&& str.endsWith("}")) {
			try {
				JSONObject jsonObject = (JSONObject) JSON.parse(str);
				Iterator<Entry<String, Object>> iterator = jsonObject
						.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, Object> entry = iterator.next();
					String key = (String) entry.getKey();
					Object value = entry.getValue();
					if (value != null) {
						if (value instanceof Object[]) {
							Object[] array = (Object[]) value;
							Collection<Object> collection = new java.util.ArrayList<Object>();
							for (int i = 0; i < array.length; i++) {
								collection.add(array[i]);
							}
							dataMap.put(key, collection);
						} else if (value instanceof JSONArray) {
							JSONArray array = (JSONArray) value;
							Collection<Object> collection = new java.util.ArrayList<Object>();
							for (int i = 0, len = array.size(); i < len; i++) {
								collection.add(array.get(i));
							}
							dataMap.put(key, collection);
						} else if (value instanceof Collection<?>) {
							Collection<?> collection = (Collection<?>) value;
							dataMap.put(key, collection);
						} else if (value instanceof Map<?, ?>) {
							Map<?, ?> map = (Map<?, ?>) value;
							dataMap.put(key, map);
						} else {
							if ((!key.startsWith("x_filter_"))
									&& key.toLowerCase().endsWith("date")) {
								String datetime = value.toString();
								try {
									java.util.Date date = DateUtils
											.toDate(datetime);
									dataMap.put(key, date);
								} catch (Exception ex) {
									ex.printStackTrace();
									dataMap.put(key, value);
								}
							} else {
								dataMap.put(key, value);
							}
						}
					}
				}
			} catch (JSONException ex) {
				ex.printStackTrace();
			}
		}
		return dataMap;
	}

	public static Map<String, String> decodeStringMap(String str) {
		Map<String, String> dataMap = new LinkedHashMap<String, String>();
		if (StringUtils.isNotEmpty(str)
				&& (str.length() > 0 && str.charAt(0) == '{')
				&& str.endsWith("}")) {
			try {
				JSONObject jsonObject = (JSONObject) JSON.parse(str);
				Iterator<Entry<String, Object>> iterator = jsonObject
						.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, Object> entry = iterator.next();
					String key = (String) entry.getKey();
					Object value = entry.getValue();
					if (value != null) {
						if (value instanceof Object[]) {
							Object[] array = (Object[]) value;
							Collection<Object> collection = new java.util.ArrayList<Object>();
							for (int i = 0; i < array.length; i++) {
								collection.add(array[i]);
							}

						} else if (value instanceof JSONArray) {
							JSONArray array = (JSONArray) value;
							Collection<Object> collection = new java.util.ArrayList<Object>();
							for (int i = 0, len = array.size(); i < len; i++) {
								collection.add(array.get(i));
							}

						} else if (value instanceof Collection<?>) {

						} else if (value instanceof Map<?, ?>) {

						} else {

							dataMap.put(key, value.toString());

						}
					}
				}
			} catch (JSONException ex) {
				ex.printStackTrace();
			}
		}
		return dataMap;
	}

	public static JSONObject toJSONObject(Map<String, Object> params) {
		JSONObject jsonObject = null;
		if (params != null) {
			jsonObject = new JSONObject();
			Set<Entry<String, Object>> entrySet = params.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				Object value = entry.getValue();
				try {
					if (value != null) {
						if (value instanceof Object[]) {
							Object[] array = (Object[]) value;
							Collection<Object> collection = new java.util.ArrayList<Object>();
							for (int i = 0; i < array.length; i++) {
								collection.add(array[i]);
							}
							jsonObject.put(key, collection);
						} else if (value instanceof Collection<?>) {
							Collection<?> collection = (Collection<?>) value;
							jsonObject.put(key, collection);
						} else if (value instanceof Map<?, ?>) {
							Map<?, ?> map = (Map<?, ?>) value;
							jsonObject.put(key, map);
						} else if (value instanceof java.util.Date) {
							java.util.Date date = (java.util.Date) value;
							String datetime = DateUtils.getDateTime(date);
							jsonObject.put(key, datetime);
						} else {
							jsonObject.put(key, value);
						}
					}
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
		}
		return jsonObject;
	}

	public static void main(String[] args) throws Exception {
		Map<String, Object> dataMap = new java.util.HashMap<String, Object>();
		dataMap.put("key01", "广州");
		dataMap.put("key02", 12345);
		dataMap.put("key03", 789.85D);
		dataMap.put("date", new Date());
		Collection<Object> actorIds = new HashSet<Object>();
		actorIds.add("sales01");
		actorIds.add("sales02");
		actorIds.add("sales03");
		actorIds.add("sales04");
		actorIds.add("sales05");
		dataMap.put("actorIds", actorIds.toArray());
		dataMap.put("x_sale_actor_actorIds", actorIds);

		Map<String, Object> xxxMap = new java.util.HashMap<String, Object>();
		xxxMap.put("0", "----请选择----");
		xxxMap.put("1", "火车");
		xxxMap.put("2", "飞机");
		xxxMap.put("3", "汽车");

		dataMap.put("trans", xxxMap);

		String str = JsonUtils.encode(dataMap);
		System.out.println(str);
		Map<?, ?> p = JsonUtils.decode(str);
		System.out.println(p);
		System.out.println(p.get("date").getClass().getName());

		String xx = "{name:\"trans\",nodeType:\"select\",children:{\"1\":\"火车\",\"3\":\"汽车\",\"2\":\"飞机\",\"0\":\"----请选择----\"}}";
		Map<String, Object> xMap = JsonUtils.decode(xx);
		System.out.println(xMap);
		Set<Entry<String, Object>> entrySet = xMap.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String key = entry.getKey();
			Object value = entry.getValue();
			System.out.println(key + " = " + value);
			System.out.println(key.getClass().getName() + "  "
					+ value.getClass().getName());
			if (value instanceof JSONObject) {
				JSONObject json = (JSONObject) value;
				Iterator<?> iter = json.keySet().iterator();
				while (iter.hasNext()) {
					String kk = (String) iter.next();
					System.out.println(kk + " = " + json.get(kk));
				}
			}
		}
	}

}