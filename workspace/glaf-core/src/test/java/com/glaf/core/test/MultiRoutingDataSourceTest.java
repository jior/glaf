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

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import org.junit.Test;

import com.glaf.core.config.Environment;
import com.glaf.core.domain.SysLog;
import com.glaf.core.service.ISysLogService;
import com.glaf.test.AbstractTest;

public class MultiRoutingDataSourceTest extends AbstractTest {

	protected ISysLogService sysLogService;

	@Test
	public void testMultiDS() throws IOException {
		sysLogService = super.getBean("sysLogService");
		// 下面操作不可用在serice包的类中（事务原因），只能用于控制层或调度
		String currentName = com.glaf.core.config.Environment
				.getCurrentSystemName();// 记下之前的数据源环境，默认是default
		try {
			java.util.Map<String, Properties> dataSourceProperties = com.glaf.core.config.DBConfiguration
					.getDataSourceProperties();
			java.util.Iterator<String> iter = dataSourceProperties.keySet()
					.iterator();
			while (iter.hasNext()) {
				String systemName = (String) iter.next();
				Environment.setCurrentSystemName(systemName);// 设置当前要做业务操作的数据源名称，如上面设置的ds02
				// 你的service逻辑
				// dataService.doYourBussiness(....);//调用Service方法，每个数据库上下文都是独立事务！！！
				for (int i = 0; i < 100; i++) {
					SysLog bean = new SysLog();
					bean.setAccount("test");
					bean.setCreateTime(new Date());
					bean.setFlag(1);
					bean.setIp("127.0.0.1");
					bean.setOperate("insert");
					sysLogService.create(bean);
				}
			}
		} finally {
			com.glaf.core.config.Environment.setCurrentSystemName(currentName);// 用完了记得还原
		}
	}

	@Test
	public void testMultiDS2() throws IOException {
		sysLogService = super.getBean("sysLogService");
		// 下面操作不可用在serice包的类中（事务原因），只能用于控制层或调度
		String currentName = com.glaf.core.config.Environment
				.getCurrentSystemName();// 记下之前的数据源环境，默认是default
		try {

			Environment.setCurrentSystemName("wechat");// 设置当前要做业务操作的数据源名称，如上面设置的ds02
			// 你的service逻辑
			// dataService.doYourBussiness(....);//调用Service方法，每个数据库上下文都是独立事务！！！
			for (int i = 0; i < 100; i++) {
				SysLog bean = new SysLog();
				bean.setAccount("test");
				bean.setCreateTime(new Date());
				bean.setFlag(1);
				bean.setIp("127.0.0.1");
				bean.setOperate("insert");
				sysLogService.create(bean);
			}

			Environment.setCurrentSystemName("yz");// 设置当前要做业务操作的数据源名称，如上面设置的ds02
			// 你的service逻辑
			// dataService.doYourBussiness(....);//调用Service方法，每个数据库上下文都是独立事务！！！
			for (int i = 0; i < 100; i++) {
				SysLog bean = new SysLog();
				bean.setAccount("test");
				bean.setCreateTime(new Date());
				bean.setFlag(1);
				bean.setIp("127.0.0.1");
				bean.setOperate("insert");
				sysLogService.create(bean);
			}

		} finally {
			com.glaf.core.config.Environment.setCurrentSystemName(currentName);// 用完了记得还原
		}
	}

}
