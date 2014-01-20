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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.model.SysDeptRole;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.model.SysUserRole;
import com.glaf.base.modules.sys.service.ComplexUserService;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysDeptRoleService;
import com.glaf.base.modules.sys.service.SysRoleService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.service.SysUserRoleService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.Tools;

@Service("complexUserService")
@Transactional(readOnly = true)
public class ComplexUserServiceImpl implements ComplexUserService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysUserService sysUserService;

	protected SysUserRoleService sysUserRoleService;

	protected SysDepartmentService sysDepartmentService;

	protected SysDeptRoleService sysDeptRoleService;

	protected SysRoleService sysRoleService;

	protected SysTreeService sysTreeService;

	@Transactional
	public boolean createUser(SysUser bean, List<String> roleCodes) {
		boolean ret = false;
		if (sysUserService.findByMail(bean.getEmail()) != null) {
			throw new RuntimeException(bean.getEmail() + " is exist.");
		}
		if (sysUserService.create(bean)) {
			for (String roleCode : roleCodes) {
				SysRole role = sysRoleService.findByCode(roleCode);
				if (role != null) {
					SysDeptRole deptRole = sysDeptRoleService.find(
							bean.getDeptId(), role.getId());
					if (deptRole == null) {
						deptRole = new SysDeptRole();
						deptRole.setDeptId(bean.getDeptId());
						deptRole.setCreateBy("system");
						deptRole.setCreateDate(new Date());
						deptRole.setRole(role);
						deptRole.setSysRoleId(role.getId());
						sysDeptRoleService.create(deptRole);
					}
					if (deptRole != null) {
						Map<String, Object> dataMap = new HashMap<String, Object>();
						dataMap.put("authorizeFrom", "0");
						dataMap.put("userId", bean.getId());
						dataMap.put("deptRoleId", deptRole.getId());
						SysUserRole userRole = new SysUserRole();
						Tools.populate(userRole, dataMap);
						userRole.setAuthorized(0);
						userRole.setCreateBy("system");
						userRole.setDeptRole(deptRole);
						userRole.setUser(bean);
						userRole.setCreateDate(new Date());
						userRole.setAvailDateStart(new Date());
						userRole.setAvailDateEnd(DateUtils.toDate("2049-10-01"));
						sysUserRoleService.create(userRole);
					}
				}
			}
			ret = true;
		}
		return ret;
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource
	public void setSysDepartmentService(
			SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
	}

	@javax.annotation.Resource
	public void setSysDeptRoleService(SysDeptRoleService sysDeptRoleService) {
		this.sysDeptRoleService = sysDeptRoleService;
	}

	@javax.annotation.Resource
	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
	}

	@javax.annotation.Resource
	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
	}

	@javax.annotation.Resource
	public void setSysUserRoleService(SysUserRoleService sysUserRoleService) {
		this.sysUserRoleService = sysUserRoleService;
	}

	@javax.annotation.Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
		logger.info("setSysUserService");
	}

}
