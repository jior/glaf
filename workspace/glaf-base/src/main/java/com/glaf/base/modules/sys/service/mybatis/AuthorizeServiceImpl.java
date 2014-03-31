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

package com.glaf.base.modules.sys.service.mybatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.AuthorizeService;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysUserService;

@Service("authorizeService")
@Transactional(readOnly = true)
public class AuthorizeServiceImpl implements AuthorizeService {
	private static final Log logger = LogFactory
			.getLog(AuthorizeServiceImpl.class);

	private SysDepartmentService sysDepartmentService;

	private SysUserService sysUserService;
	
	

	/**
	 * 用户认证
	 * 
	 * @param account
	 * @param pwd
	 * @return
	 */
	public SysUser authorize(String account, String pwd) {
		SysUser bean = sysUserService.findByAccount(account);
		if (bean != null) {
			if (bean.isDepartmentAdmin()) {
				logger.debug(account + " is department admin");
			}
			if (bean.isSystemAdmin()) {
				logger.debug(account + " is system admin");
			}
			if (!bean.getPassword().equals(pwd) || // 密码不匹配
					bean.getLocked() == 1) {// 帐号禁止
				bean = null;
			}
		}
		return bean;
	}


	/**
	 * 用户登陆
	 * 
	 * @param account
	 * @param pwd
	 * @return
	 */
	public SysUser login(String account) {
		SysUser bean = sysUserService.findByAccountWithAll(account);
		if (bean != null) {

			if (bean.isDepartmentAdmin()) {
				logger.debug(account + " is department admin");
			}
			if (bean.isSystemAdmin()) {
				logger.debug(account + " is system admin");
			}
			if (bean.getAccountType() != 1) {
				// 取出用户对应的模块权限
				bean = sysUserService.getUserPrivileges(bean);

				// 取出用户的部门列表
				List<SysDepartment> list = new java.util.concurrent.CopyOnWriteArrayList<SysDepartment>();
				sysDepartmentService.findNestingDepartment(list,
						bean.getDepartment());
				bean.setNestingDepartment(list);
			}
		}
		return bean;
	}

	/**
	 * 用户登陆
	 * 
	 * @param account
	 * @param pwd
	 * @return
	 */
	public SysUser login(String account, String pwd) {
		SysUser bean = sysUserService.findByAccount(account);
		if (bean != null) {
			if (bean.isDepartmentAdmin()) {
				logger.debug(account + " is department admin");
			}
			if (bean.isSystemAdmin()) {
				logger.debug(account + " is system admin");
			}
			if (!bean.getPassword().equals(pwd) || // 密码不匹配
					bean.getBlocked() == 1) {// 帐号禁止
				bean = null;
			}
		}
		return bean;
	}

	@javax.annotation.Resource
	public void setSysDepartmentService(
			SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
	}

	@javax.annotation.Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
		logger.info("setSysUserService");
	}

}