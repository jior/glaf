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

package com.glaf.core.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.glaf.core.jdbc.JsonQueryHelper;

public class DialectTest {

	@Test
	public void testPageSQL() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		JsonQueryHelper helper = new JsonQueryHelper();
		JSONArray result = helper.getJSONArray("pdemo",
				"select  * from volume ", paramMap, 10, 20);
		System.out.println(result.toJSONString());
	}

}
