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
package com.glaf.activiti.test;

import org.junit.After;
import org.junit.Before;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.context.ContextFactory;

import com.glaf.activiti.service.*;

public class AbstractActivitiTest {

	protected long start = 0L;

	protected ActivitiDeployService activitiDeployService;

	protected ActivitiProcessService activitiProcessService;

	protected ActivitiProcessQueryService activitiProcessQueryService;

	protected ActivitiTaskQueryService activitiTaskQueryService;

	@Before
	public void setUp() throws Exception {
		System.out.println("开始测试..................................");
		start = System.currentTimeMillis();
		System.out.println(SystemProperties.getConfigRootPath());
		// Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
		activitiDeployService = ContextFactory.getBean("activitiDeployService");
		activitiProcessService = ContextFactory
				.getBean("activitiProcessService");
		activitiProcessQueryService = ContextFactory
				.getBean("activitiProcessQueryService");
		activitiTaskQueryService = ContextFactory
				.getBean("activitiTaskQueryService");
	}

	@After
	public void tearDown() throws Exception {
		long times = System.currentTimeMillis() - start;
		System.out.println("总共耗时(毫秒):" + times);
		System.out.println("测试完成。");
	}

}
