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
import org.json.JSONArray;

import com.glaf.core.context.ContextFactory;

import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysApplicationService;
import com.glaf.base.modules.sys.service.SysDeptRoleService;
import com.glaf.base.modules.sys.service.SysRoleService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.service.SysUserRoleService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.utils.ContextUtil;

public class BaseIdentityFactory {
	protected static final Log logger = LogFactory
			.getLog(BaseIdentityFactory.class);

	protected static SysApplicationService sysApplicationService;

	protected static SysDepartmentService sysDepartmentService;

	protected static SysDeptRoleService sysDeptRoleService;

	protected static SysRoleService sysRoleService;

	protected static SysTreeService sysTreeService;

	protected static SysUserRoleService sysUserRoleService;

	protected static SysUserService sysUserService;

	/**
	 * 获取委托人编号集合（用户登录账号的集合）
	 * 
	 * @param assignTo
	 *            受托人编号（登录账号）
	 * @return
	 */
	public static List<String> getAgentIds(String assignTo) {
		List<String> agentIds = new ArrayList<String>();
		return agentIds;
	}

	/**
	 * 获取某个部门及所有下级部门的某个角色的用户
	 * 
	 * @param deptId
	 *            部门编号
	 * @param roleId
	 *            角色编号
	 * @return
	 */
	public static List<SysUser> getChildrenMembershipUsers(Long deptId,
			Long roleId) {
		return getSysUserRoleService().getChildrenMembershipUsers(deptId,
				roleId);
	}

	/**
	 * 获取某个部门及所有下级部门的某个角色的用户
	 * 
	 * @param deptId
	 *            部门编号
	 * @param roleId
	 *            角色编号
	 * @return
	 */
	public static List<SysUser> getChildrenMembershipUsers(Long deptId,
			String roleCode) {
		SysRole role = getSysRoleService().findByCode(roleCode);
		return getSysUserRoleService().getChildrenMembershipUsers(deptId,
				role.getId());
	}

	/**
	 * 根据部门代码获取部门(sys_department表的code字段)
	 * 
	 * @param code
	 *            部门代码
	 * @return
	 */
	public static SysDepartment getDepartmentByCode(String code) {
		SysDepartment model = getSysDepartmentService().findByCode(code);
		return model;
	}

	/**
	 * 根据部门编号获取部门(sys_department表的id字段)
	 * 
	 * @param id
	 *            部门ID
	 * @return
	 */
	public static SysDepartment getDepartmentById(Long id) {
		SysDepartment model = getSysDepartmentService().findById(id);
		return model;
	}

	/**
	 * 根据部门代码获取部门(sys_department表的deptno字段)
	 * 
	 * @param deptno
	 *            部门deptno
	 * @return
	 */
	public static SysDepartment getDepartmentByNo(String deptno) {
		SysDepartment model = getSysDepartmentService().findByNo(deptno);
		return model;
	}

	/**
	 * 获取部门Map
	 * 
	 * @return
	 */
	public static Map<String, SysDepartment> getDepartmentMap() {
		Map<String, SysDepartment> deptMap = new HashMap<String, SysDepartment>();
		List<SysDepartment> depts = getSysDepartmentService()
				.getSysDepartmentList();
		if (depts != null && !depts.isEmpty()) {
			for (SysDepartment dept : depts) {
				depts.add(dept);
			}
		}
		return deptMap;
	}

	/**
	 * 获取全部部门
	 * 
	 * @return
	 */
	public static List<SysDepartment> getDepartments() {
		List<SysDepartment> depts = getSysDepartmentService()
				.getSysDepartmentList();
		return depts;
	}

	/**
	 * 获取全部用户
	 * 
	 * @return
	 */
	public static Map<String, SysUser> getLowerCaseUserMap() {
		Map<String, SysUser> userMap = new LinkedHashMap<String, SysUser>();
		List<SysUser> users = getSysUserService().getSysUserList();
		if (users != null && !users.isEmpty()) {
			for (SysUser user : users) {
				userMap.put(user.getAccount().toLowerCase(), user);
			}
		}
		return userMap;
	}

	/**
	 * 获取某个部门的用户
	 * 
	 * @param deptId
	 *            部门ID
	 * @return
	 */
	public static List<SysUser> getMembershipUsers(Long deptId) {
		return getSysUserService().getSysUserList(deptId);
	}

	/**
	 * 获取某个部门某个角色的用户
	 * 
	 * @param deptId
	 *            部门ID
	 * @param roleId
	 *            角色ID
	 * @return
	 */
	public static List<SysUser> getMembershipUsers(Long deptId, Long roleId) {
		return getSysUserRoleService().getMembershipUsers(deptId, roleId);
	}

	/**
	 * 获取某个部门某个角色的用户
	 * 
	 * @param deptIds
	 *            部门ID
	 * @param roleId
	 *            角色ID
	 * @return
	 */
	public static List<SysUser> getMembershipUsers(List<Long> deptIds,
			int roleId) {
		return getSysUserRoleService().getMembershipUsers(deptIds, roleId);
	}

	/**
	 * 获取某个部门某个角色的用户
	 * 
	 * @param deptIds
	 *            部门ID
	 * @param roleCode
	 *            角色代码
	 * @return
	 */
	public static List<SysUser> getMembershipUsers(List<Long> deptIds,
			String roleCode) {
		SysRole role = getSysRoleService().findByCode(roleCode);
		return getSysUserRoleService()
				.getMembershipUsers(deptIds, role.getId());
	}

	/**
	 * 获取某个部门某个角色的用户
	 * 
	 * @param deptId
	 *            部门ID
	 * @param roleCode
	 *            角色代码
	 * @return
	 */
	public static List<SysUser> getMembershipUsers(Long deptId, String roleCode) {
		SysRole role = getSysRoleService().findByCode(roleCode);
		return getSysUserRoleService().getMembershipUsers(deptId, role.getId());
	}

	/**
	 * 获取某个部门指定级别的上级部分包含的所有子部门
	 * 
	 * @param deptId
	 *            部门编号
	 * @param treeType
	 *            级别 0-公司,1(B)-部,2(K)-科,3-系,4-室(班)
	 * @return
	 */
	public static List<SysDepartment> getParentAndChildrenDepartments(
			Long deptId, String treeType) {
		List<SysDepartment> list = new ArrayList<SysDepartment>();
		SysDepartment dept = getSysDepartmentService().findById(deptId);
		if (dept != null) {
			getSysDepartmentService().findNestingDepartment(list, dept.getId());
		}
		return list;
	}

	/**
	 * 通过角色代码获取角色
	 * 
	 * @param code
	 *            角色代码
	 * @return
	 */
	public static SysRole getRoleByCode(String code) {
		return getSysRoleService().findByCode(code);
	}

	/**
	 * 通过角色编号获取角色
	 * 
	 * @param id
	 *            角色ID
	 * @return
	 */
	public static SysRole getRoleById(Long id) {
		return getSysRoleService().findById(id);
	}

	/**
	 * 获取全部角色 Map
	 * 
	 * @return
	 */
	public static Map<String, SysRole> getRoleMap() {
		Map<String, SysRole> roleMap = new HashMap<String, SysRole>();
		List<SysRole> roles = getSysRoleService().getSysRoleList();
		if (roles != null && !roles.isEmpty()) {
			for (SysRole role : roles) {
				roleMap.put(role.getCode(), role);
			}
		}
		return roleMap;
	}

	/**
	 * 获取全部角色
	 * 
	 * @return
	 */
	public static List<SysRole> getRoles() {
		List<SysRole> roles = getSysRoleService().getSysRoleList();
		return roles;
	}

	public static SysApplicationService getSysApplicationService() {
		if (sysApplicationService == null) {
			sysApplicationService = ContextFactory
					.getBean("sysApplicationService");
		}
		return sysApplicationService;
	}

	public static SysDepartmentService getSysDepartmentService() {
		if (sysDepartmentService == null) {
			sysDepartmentService = ContextFactory
					.getBean("sysDepartmentService");
		}
		return sysDepartmentService;
	}

	public static SysDeptRoleService getSysDeptRoleService() {
		if (sysDeptRoleService == null) {
			sysDeptRoleService = ContextFactory.getBean("sysDeptRoleService");
		}
		return sysDeptRoleService;
	}

	public static SysRoleService getSysRoleService() {
		if (sysRoleService == null) {
			sysRoleService = ContextFactory.getBean("sysRoleService");
		}
		return sysRoleService;
	}

	public static SysTreeService getSysTreeService() {
		if (sysTreeService == null) {
			sysTreeService = ContextFactory.getBean("sysTreeService");
		}

		return sysTreeService;
	}

	/**
	 * 根据用户名获取用户对象
	 * 
	 * @param actorId
	 *            用户登录账号
	 * @return
	 */
	public static SysUser getSysUser(String actorId) {
		return getSysUserService().findByAccountWithAll(actorId);
	}

	public static SysUserRoleService getSysUserRoleService() {
		if (sysUserRoleService == null) {
			sysUserRoleService = ContextFactory.getBean("sysUserRoleService");
		}
		return sysUserRoleService;
	}

	public static SysUserService getSysUserService() {
		if (sysUserService == null) {
			sysUserService = ContextFactory.getBean("sysUserService");
		}
		return sysUserService;
	}

	/**
	 * 根据用户名获取用户对象
	 * 
	 * @param actorId
	 *            用户登录账号
	 * @return
	 */
	public static SysUser getSysUserWithAll(String actorId) {
		SysUser user = getSysUserService().findByAccountWithAll(actorId);
		if (user != null) {
			ContextUtil.put(actorId, user);
		}
		return user;
	}

	/**
	 * 获取全部用户Map
	 * 
	 * @return
	 */
	public static Map<String, SysUser> getUserMap() {
		Map<String, SysUser> userMap = new LinkedHashMap<String, SysUser>();
		List<SysUser> users = getSysUserService().getSysUserList();
		if (users != null && !users.isEmpty()) {
			for (SysUser user : users) {
				userMap.put(user.getAccount(), user);
			}
		}
		return userMap;
	}

	/**
	 * 获取某些用户的角色代码
	 * 
	 * @param actorIds
	 * @return
	 */
	public static List<String> getUserRoleCodes(List<String> actorIds) {
		List<String> codes = new ArrayList<String>();
		List<SysRole> list = getUserRoles(actorIds);
		if (list != null && !list.isEmpty()) {
			for (SysRole role : list) {
				if (!codes.contains(role.getCode())) {
					codes.add(role.getCode());
				}
			}
		}
		return null;
	}

	public static List<SysRole> getUserRoles(List<String> actorIds) {
		return getSysUserService().getUserRoles(actorIds);
	}

	/**
	 * 获取某个用户及代理人的角色编号
	 * 
	 * @param actorId
	 *            用户登录账号
	 * @return
	 */
	public static List<String> getUserRoles(String actorId) {
		List<String> actorIds = new ArrayList<String>();
		actorIds.add(actorId);
		return getUserRoleCodes(actorIds);
	}

	/**
	 * 获取全部用户
	 * 
	 * @return
	 */
	public static List<SysUser> getUsers() {
		return getSysUserService().getSysUserList();
	}

	public static void setSysApplicationService(
			SysApplicationService sysApplicationService) {
		BaseIdentityFactory.sysApplicationService = sysApplicationService;
	}

	public static void setSysDepartmentService(
			SysDepartmentService sysDepartmentService) {
		BaseIdentityFactory.sysDepartmentService = sysDepartmentService;
	}

	public static void setSysDeptRoleService(
			SysDeptRoleService sysDeptRoleService) {
		BaseIdentityFactory.sysDeptRoleService = sysDeptRoleService;
	}

	public static void setSysRoleService(SysRoleService sysRoleService) {
		BaseIdentityFactory.sysRoleService = sysRoleService;
	}

	public static void setSysTreeService(SysTreeService sysTreeService) {
		BaseIdentityFactory.sysTreeService = sysTreeService;
	}

	public static void setSysUserRoleService(
			SysUserRoleService sysUserRoleService) {
		BaseIdentityFactory.sysUserRoleService = sysUserRoleService;
	}

	public static void setSysUserService(SysUserService sysUserService) {
		BaseIdentityFactory.sysUserService = sysUserService;
	}

	/**
	 * 获取用户菜单
	 * 
	 * @param parentId
	 *            父应用编号
	 * @param actorId
	 *            用户登录账号
	 * @return
	 */
	public JSONArray getUserMenu(Long parentId, String actorId) {
		return getSysApplicationService().getUserMenu(parentId, actorId);
	}

}
