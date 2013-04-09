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

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;

import com.glaf.base.business.ApplicationBean;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysApplicationService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.core.util.FileUtils;

public class UserTest extends AbstractTest {

	protected SysUserService sysUserService;

	protected SysApplicationService sysApplicationService;

	@Test
	public void testUser() {
		sysUserService = super.getBean("sysUserService");
		SysUser user = sysUserService.findByAccountWithAll("root");
		if (user != null) {
			logger.debug(user.toJsonObject().toJSONString());
		}
	}

	@Test
	public void testList() {
		sysUserService = super.getBean("sysUserService");
		logger.info("user list:" + sysUserService.getSysUserList());
	}

	@Test
	public void testUserMenu() {
		sysApplicationService = super.getBean("sysApplicationService");
		JSONArray array = sysApplicationService.getUserMenu(3, "root");
		System.out.println("menus:\n" + array.toString('\n'));
		System.out.println();
		try {
			FileUtils.save("menus.json", array.toString('\n').getBytes());
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		ApplicationBean bean = new ApplicationBean();
		bean.setSysApplicationService(sysApplicationService);
		String scripts = bean.getMenuScripts(3, "root", "/glaf");
		System.out.println(scripts);
	}

}