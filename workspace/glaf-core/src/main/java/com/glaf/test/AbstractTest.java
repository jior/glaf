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

package com.glaf.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AbstractTest {
	protected static final Log logger = LogFactory.getLog(AbstractTest.class);

	protected static String configurationResource = "spring/spring-config.xml";

	protected static org.springframework.context.ApplicationContext ctx;

	protected long start = 0L;

	@Before
	public void setUp() throws Exception {
		System.out.println("开始测试..................................");
		start = System.currentTimeMillis();
		if (ctx == null) {
			ctx = new ClassPathXmlApplicationContext(configurationResource);
		}
	}

	@After
	public void tearDown() throws Exception {
		long times = System.currentTimeMillis() - start;
		System.out.println("总共耗时(毫秒):" + times);
		System.out.println("测试完成。");
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(String name) {
		if (ctx == null) {
			ctx = new ClassPathXmlApplicationContext(configurationResource);
		}
		return (T) ctx.getBean(name);
	}

	@Test
	public void showBeans() {
		String[] ids = ctx.getBeanDefinitionNames();
		for (String id : ids) {
			System.out.println(id);
		}
	}

}