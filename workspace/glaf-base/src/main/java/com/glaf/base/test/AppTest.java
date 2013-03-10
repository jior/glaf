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

import com.glaf.base.business.ApplicationBean;
import com.glaf.base.modules.sys.service.SysApplicationService;
import com.glaf.core.util.FileUtils;

public class AppTest extends AbstractTest {
	
	protected SysApplicationService sysApplicationService;

	@Test
	public void testUserMenu() {
		sysApplicationService = super.getBean("sysApplicationService");
		ApplicationBean bean = new ApplicationBean();
		bean.setSysApplicationService(sysApplicationService);
		System.out.println("sysApplicationService="+sysApplicationService);
		String scripts = bean.getMenuScripts(3, "root", "/glaf");
		System.out.println(scripts);
		FileUtils.save("menu.html", scripts.getBytes());
	}

}