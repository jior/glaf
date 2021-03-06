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

package com.glaf.jbpm;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.util.DateUtils;

public class DataFieldTest {

	@Test
	public void test() {
		JSONArray array = new JSONArray();
		JSONObject j1 = new JSONObject();
		j1.put("integer", 1234);
		j1.put("long", 4789999L);
		j1.put("double", 9986.55);
		j1.put("date_time", new Date(System.currentTimeMillis()
				- DateUtils.WEEK));
		j1.put("string", "this is text a");
		array.add(j1);

		JSONObject j2 = new JSONObject();
		j2.put("integer", 345);
		j2.put("long", 889789999L);
		j2.put("double", 999.55);
		j2.put("date_time",
				new Date(System.currentTimeMillis() - DateUtils.DAY));
		j2.put("string", "this is text b");
		array.add(j2);

		JSONObject j3 = new JSONObject();
		j3.put("integer", 568);
		j3.put("long", 1556789999L);
		j3.put("double", 168.55);
		j3.put("date_time", new Date());
		j3.put("string", "this is text c");
		array.add(j3);

		System.out.println(array.toJSONString());

		String str = array.toJSONString();

		array = JSON.parseArray(str);
		if (array != null && !array.isEmpty()) {
			for (int i = 0, len = array.size(); i < len; i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				Map<String, Object> dataMap = new java.util.concurrent.ConcurrentHashMap<String, Object>();

				Iterator<Entry<String, Object>> iterator = jsonObject
						.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, Object> entry = iterator.next();
					String key = (String) entry.getKey();
					Object value = entry.getValue();
					dataMap.put(key, value);
				}
				System.out.println(dataMap);
				Long time = (Long) dataMap.get("date_time");
				System.out.println(DateUtils.getDateTime(new Date(time)));
			}
		}

	}

}
