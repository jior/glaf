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

package com.glaf.base.test;

import org.junit.Test;

import com.glaf.base.test.service.TestService;

public class TransactionTest extends AbstractTest {

	protected TestService testService;

	/**
	 * 测试主键已经存在
	 */
	@Test
	public void testSave() {
		testService = super.getBean("testService");
		testService.save();
	}

	/**
	 * 测试字段超长
	 */
	@Test
	public void testSave2() {
		testService = super.getBean("testService");
		testService.save2();
	}

	/**
	 * 测试字段超长并抛出运行时异常
	 */
	@Test
	public void testSave3() {
		testService = super.getBean("testService");
		testService.save3();
	}

}
