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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.*;

@Service("authorizeService")
@Transactional(readOnly = true)
public class AuthorizeServiceImpl implements AuthorizeService {
	private static final Log logger = LogFactory
			.getLog(AuthorizeServiceImpl.class);

	private SysDepartmentService sysDepartmentService;

	private SysUserService sysUserService;

	/**
	 * �û���½
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
				// ȡ���û���Ӧ��ģ��Ȩ��
				bean = sysUserService.getUserPrivileges(bean);

				// ȡ���û��Ĳ����б�
				List<SysDepartment> list = new ArrayList<SysDepartment>();
				sysDepartmentService.findNestingDepartment(list,
						bean.getDepartment());
				bean.setNestingDepartment(list);
			}
		}
		return bean;
	}

	/**
	 * �û���½
	 * 
	 * @param account
	 * @param pwd
	 * @return
	 */
	public SysUser login(String account, String pwd) {
		SysUser bean = sysUserService.findByAccountWithAll(account);
		if (bean != null) {

			if (bean.isDepartmentAdmin()) {
				logger.debug(account + " is department admin");
			}
			if (bean.isSystemAdmin()) {
				logger.debug(account + " is system admin");
			}
			if (!bean.getPassword().equals(pwd) || // ���벻ƥ��
					bean.getBlocked() == 1) {// �ʺŽ�ֹ
				bean = null;
			} else if (bean.getAccountType() != 1) {
				// ȡ���û���Ӧ��ģ��Ȩ��
				bean = sysUserService.getUserPrivileges(bean);

				// ȡ���û��Ĳ����б�
				List<SysDepartment> list = new ArrayList<SysDepartment>();
				sysDepartmentService.findNestingDepartment(list,
						bean.getDepartment());
				bean.setNestingDepartment(list);
			}
		}
		return bean;
	}

	@Resource
	public void setSysDepartmentService(
			SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
	}

	@Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
		logger.info("setSysUserService");
	}

}