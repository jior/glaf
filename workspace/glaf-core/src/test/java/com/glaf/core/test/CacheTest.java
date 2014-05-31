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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.glaf.core.cache.CacheFactory;
import com.glaf.core.config.CustomProperties;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.UUID32;

public class CacheTest {

	protected long start = 0L;

	@Before
	public void setUp() throws Exception {
		System.out.println("开始测试..................................");
		start = System.currentTimeMillis();
		CustomProperties.reload();
		CacheFactory.getString("xx");
		System.out.println(SystemProperties.getConfigRootPath());
	}

	@After
	public void tearDown() throws Exception {
		long times = System.currentTimeMillis() - start;
		System.out.println("总共耗时(毫秒):" + times);
		System.out.println("测试完成。");
	}

	@Test
	public void testGet() {
		for (int i = 100; i < 110; i++) {
			System.out.println(CacheFactory.getString("cache_" + i));
		}
	}

	@Test
	public void testPut() {
		for (int i = 0; i < 200; i++) {
			CacheFactory.put("cache_" + i,"value_" + i + "#" + UUID32.getUUID());
		}
		for (int i = 100; i < 110; i++) {
			System.out.println(CacheFactory.getString("cache_" + i));
		}
	}

}
