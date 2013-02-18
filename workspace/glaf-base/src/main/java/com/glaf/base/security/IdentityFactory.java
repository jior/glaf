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

package com.glaf.base.security;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.context.ContextFactory;
import com.glaf.base.modules.sys.model.SysApplication;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysApplicationService;
import com.glaf.base.modules.sys.service.SysRoleService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.modules.sys.service.SysDepartmentService;

public class IdentityFactory {
	protected final static Log logger = LogFactory
			.getLog(IdentityFactory.class);

	protected static SysUserService sysUserService;

	protected static SysRoleService sysRoleService;

	protected static SysDepartmentService sysDepartmentService;

	protected static SysApplicationService sysApplicationService;

	static {
		sysUserService = ContextFactory.getBean("sysUserService");
		sysRoleService = ContextFactory.getBean("sysRoleService");
		sysDepartmentService = ContextFactory.getBean("sysDepartmentService");
		sysApplicationService = ContextFactory.getBean("sysApplicationService");
	}

	/**
	 * 获取某个部门及所有下级部门的某个角色的用户
	 * 
	 * @param deptId
	 * @param roleId
	 * @return
	 */
	public static List<SysUser> getChildrenMembershipUsers(Long deptId,
			Long roleId) {
		 
		return null;
	}

	/**
	 * 根据部门代码获取部门(sys_department表的code字段)
	 * 
	 * @param code
	 * @return
	 */
	public static SysDepartment getDepartmentByCode(String code) {
		SysDepartment model = sysDepartmentService.findByCode(code);
		return model;
	}
	
	/**
	 * 根据部门代码获取部门(sys_department表的deptno字段)
	 * 
	 * @param deptno
	 * @return
	 */
	public static SysDepartment getDepartmentByNo(String deptno) {
		SysDepartment model = sysDepartmentService.findByNo(deptno);
		return model;
	}

	/**
	 * 根据部门编号获取部门
	 * 
	 * @param id
	 * @return
	 */
	public static SysDepartment getDepartmentById(Long id) {
		SysDepartment model = sysDepartmentService.findById(id);
		return model;
	}

	public static Map<String, SysDepartment> getDepartmentMap() {
		Map<String, SysDepartment> deptMap = new HashMap<String, SysDepartment>();
		List<SysDepartment> depts = sysDepartmentService.getSysDepartmentList();
		if(depts != null && !depts.isEmpty()){
			for(SysDepartment dept:depts){
				depts.add(dept);
			}
		}
		return deptMap;
	}

	/**
	 * 获取部门
	 * 
	 * @return
	 */
	public static List<SysDepartment> getDepartments() {
		List<SysDepartment> depts = sysDepartmentService.getSysDepartmentList();
		return depts;
	}

	/**
	 * 获取全部用户 <br>
	 * 通过配置getUsers获取User对象
	 * 
	 * @return
	 */
	public static Map<String, SysUser> getLowerCaseUserMap() {
		Map<String, SysUser> userMap = new LinkedHashMap<String, SysUser>();

		return userMap;
	}

	/**
	 * 获取某些用户的角色编号<br>
	 * 通过配置getMembershipRoleIds获取角色编号
	 * 
	 * @param actorIds
	 * @return
	 */
	public static List<String> getMembershipRoleIds(List<String> actorIds) {
		return null;
	}

	/**
	 * 获取某个用户及代理人的角色编号
	 * 
	 * @param actorId
	 * @return
	 */
	public static List<String> getMembershipRoleIds(String actorId) {

		List<String> actorIds = new ArrayList<String>();
		actorIds.add(actorId);

		return IdentityFactory.getMembershipRoleIds(actorIds);
	}

	/**
	 * 获取某个部门某个角色的用户
	 * 
	 * @param deptIds
	 * @param roleId
	 * @return
	 */
	public static List<SysUser> getMembershipUsers(List<String> deptIds,
			String roleId) {
		return null;
	}

	/**
	 * 获取某个部门的用户
	 * 
	 * @param deptId
	 * @return
	 */
	public static List<SysUser> getMembershipUsers(String deptId) {
		return null;
	}

	/**
	 * 获取某个部门某个角色的用户
	 * 
	 * @param deptId
	 * @param roleId
	 * @return
	 */
	public static List<SysUser> getMembershipUsers(String deptId, String roleId) {
		return null;
	}

	/**
	 * 获取某个部门指定级别的上级部分包含的所有子部门
	 * 
	 * @param deptId
	 *            部门编号
	 * @param treeType
	 *            级别 0-公司,1-部,2-科,3-系,4-室(班)
	 * @return
	 */
	public static List<SysDepartment> getParentAndChildrenDepartments(
			String deptId, String treeType) {
		List<SysDepartment> depts = new ArrayList<SysDepartment>();

		return depts;
	}

	/**
	 * 获取权限点编号
	 * 
	 * @param actorId
	 * @return
	 */
	public static List<String> getPermissionIds(List<String> roleIds) {
		logger.debug("-------------------getPermissionIds----------");
		logger.debug("roleIds:" + roleIds);

		return null;
	}

	/**
	 * 通过角色编号获取角色
	 * 
	 * @param roleId
	 * @return
	 */
	public static SysRole getRole(String roleId) {
		return null;
	}

	/**
	 * 获取全部角色 <br>
	 * 通过配置getRoles获取Role对象
	 * 
	 * @return
	 */
	public static Map<String, SysRole> getRoleMap() {
		Map<String, SysRole> roleMap = new HashMap<String, SysRole>();

		return roleMap;
	}

	/**
	 * 获取全部角色 <br>
	 * 通过配置getRoles获取Role对象
	 * 
	 * @return
	 */
	public static List<SysRole> getRoles() {
		List<SysRole> roles = new ArrayList<SysRole>();

		return roles;
	}

	/**
	 * 获取某个角色的用户
	 * 
	 * @param roleId
	 * @return
	 */
	public static List<SysUser> getRoleUsers(String roleId) {
		return null;
	}

	/**
	 * 获取最顶级部门
	 * 
	 * @return
	 */
	public static SysDepartment getRootDepartment() {
		return null;
	}

	/**
	 * 根据用户名获取用户安全上下文
	 * 
	 * @param actorId
	 * @return
	 */
	public static SysUser getSysUser(String actorId) {
		return null;
	}

	/**
	 * 获取全部用户 <br>
	 * 通过配置getUsers获取User对象
	 * 
	 * @return
	 */
	public static Map<String, SysUser> getUserMap() {
		Map<String, SysUser> userMap = new LinkedHashMap<String, SysUser>();

		return userMap;
	}

	/**
	 * 获取用户菜单
	 * 
	 * @param actorId
	 * @return
	 */
	public static List<SysApplication> getUserMenus(String actorId) {

		return null;
	}

	/**
	 * 获取全部用户 <br>
	 * 通过配置getUsers获取User对象
	 * 
	 * @return
	 */
	public static List<SysUser> getUsers() {
		return null;
	}

}
