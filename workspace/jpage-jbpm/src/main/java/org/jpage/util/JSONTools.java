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


package org.jpage.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONTools {

	public static String encode(Map params) {
		JSONObject jsonObject = null;
		String str = null;
		if (params != null) {
			jsonObject = new JSONObject();
			Iterator iterator = params.keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				Object value = params.get(key);
				try {
					if (value != null) {
						if (value instanceof Object[]) {
							Object[] array = (Object[]) value;
							Collection collection = new ArrayList();
							for (int i = 0; i < array.length; i++) {
								collection.add(array[i]);
							}
							jsonObject.put(key, collection);
						} else if (value instanceof Collection) {
							Collection collection = (Collection) value;
							jsonObject.put(key, collection);
						} else if (value instanceof Map) {
							Map map = (Map) value;
							jsonObject.put(key, map);
						} else if (value instanceof java.util.Date) {
							java.util.Date date = (java.util.Date) value;
							String datetime = DateTools.getDateTime(date);
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

	public static Map decode(String str) {
		Map dataMap = new HashMap();
		if (str != null) {
			try {
				JSONObject jsonObject = new JSONObject(str);
				Iterator iterator = jsonObject.keys();
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					Object value = jsonObject.get(key);
					if (value != null) {
						if (value instanceof Object[]) {
							Object[] array = (Object[]) value;
							Collection collection = new ArrayList();
							for (int i = 0; i < array.length; i++) {
								collection.add(array[i]);
							}
							dataMap.put(key, collection);
						} else if (value instanceof org.json.JSONArray) {
							JSONArray array = (JSONArray) value;
							Collection collection = new ArrayList();
							for (int i = 0; i < array.length(); i++) {
								collection.add(array.get(i));
							}
							dataMap.put(key, collection);
						} else if (value instanceof Collection) {
							Collection collection = (Collection) value;
							dataMap.put(key, collection);
						} else if (value instanceof Map) {
							Map map = (Map) value;
							dataMap.put(key, map);
						} else {
							if (key.toLowerCase().indexOf("date") != -1) {
								String datetime = value.toString();
								java.util.Date date = DateTools
										.toDate(datetime);
								dataMap.put(key, date);
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

	public static void main(String[] args) {
		Map dataMap = new HashMap();
		dataMap.put("key01", "����");
		dataMap.put("key02", new Integer(12345));
		dataMap.put("key03", new Double(789.85));
		dataMap.put("date", new Date());
		Collection actorIds = new HashSet();
		actorIds.add("sales01");
		actorIds.add("sales02");
		actorIds.add("sales03");
		actorIds.add("sales04");
		actorIds.add("sales05");
		dataMap.put("actorIds", actorIds.toArray());
		dataMap.put("x_sale_actor_actorIds", actorIds);

		String str = JSONTools.encode(dataMap);
		System.out.println(str);
		Map p = JSONTools.decode(str);
		System.out.println(p);
		System.out.println(p.get("date").getClass().getName());
	}

}