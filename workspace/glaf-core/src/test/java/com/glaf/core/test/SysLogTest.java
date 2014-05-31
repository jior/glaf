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

import org.junit.Test;

import com.glaf.core.domain.SysLog;
import com.glaf.core.service.ISysLogService;
import com.glaf.test.AbstractTest;

public class SysLogTest extends AbstractTest {
	
	protected ISysLogService sysLogService;
	
	@Test
	public void testInsertLogs() {
		sysLogService = super.getBean("sysLogService");
		for(int i=0;i<2000;i++){
			SysLog bean=new SysLog();
			bean.setAccount("test");
			bean.setIp("127.0.0.1");
			bean.setOperate("add");
			sysLogService.create(bean);
		}
	}

}
