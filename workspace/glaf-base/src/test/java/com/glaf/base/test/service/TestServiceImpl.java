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

package com.glaf.base.test.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysRoleService;
import com.glaf.base.modules.sys.service.SysUserService;

@Service("testService")
@Transactional(readOnly = true)
public class TestServiceImpl implements TestService {

	protected SysRoleService sysRoleService;

	protected SysUserService sysUserService;

	@Transactional
	public void save() {
		SysUser user = new SysUser();
		user.setId(1);
		user.setAccount("user1");
		user.setActorId("user1");
		user.setCode("user1");
		user.setName("用户1");
		user.setBlocked(0);
		user.setDeptId(6);
		user.setLocked(0);
		sysUserService.create(user);

		SysRole bean = new SysRole();
		bean.setCode("role01");
		bean.setName("角色01");
		bean.setNodeId(0);
		bean.setDesc("xxxxxxx");
		bean.setSort(0);
		bean.setCreateBy("user1");
		sysRoleService.create(bean);

		SysRole bean2 = new SysRole();
		bean2.setId(1);// 插入已经存在的角色ID，必定抛出异常
		bean2.setCode("role02");
		bean2.setName("角色2");
		bean2.setNodeId(0);
		bean2.setDesc("zzzzzzzzzz");
		bean2.setSort(0);
		bean2.setCreateBy("user1");
		sysRoleService.create(bean2);
	}

	@Transactional
	public void save2() {
		SysUser user = new SysUser();
		user.setAccount("user1");
		user.setActorId("user1");
		user.setCode("user1");
		user.setName("用户1");
		user.setBlocked(0);
		user.setDeptId(6);
		user.setLocked(0);
		sysUserService.create(user);

		SysRole bean = new SysRole();
		bean.setCode("role01");
		bean.setName("角色01");
		bean.setNodeId(0);
		bean.setDesc("xxxxxxx");
		bean.setSort(0);
		bean.setCreateBy("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		sysRoleService.create(bean);
	}

	@Transactional
	public void save3() {
		SysUser user = new SysUser();
		user.setAccount("user1");
		user.setActorId("user1");
		user.setCode("user1");
		user.setName("用户1");
		user.setBlocked(0);
		user.setDeptId(6);
		user.setLocked(0);
		sysUserService.create(user);

		try {
			SysRole bean = new SysRole();
			bean.setCode("role01");
			bean.setName("角色01");
			bean.setNodeId(0);
			bean.setDesc("xxxxxxx");
			bean.setSort(0);
			bean.setCreateBy("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
			sysRoleService.create(bean);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@javax.annotation.Resource
	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
	}

	@javax.annotation.Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

}
