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

import com.glaf.base.context.ContextFactory;

import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysApplicationService;
import com.glaf.base.modules.sys.service.SysDeptRoleService;
import com.glaf.base.modules.sys.service.SysRoleService;
import com.glaf.base.modules.sys.service.SysUserRoleService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.modules.sys.service.SysDepartmentService;

public class IdentityFactory {
	protected final static Log logger = LogFactory
			.getLog(IdentityFactory.class);

	protected static SysUserService sysUserService;

	protected static SysRoleService sysRoleService;

	protected static SysUserRoleService sysUserRoleService;

	protected static SysDeptRoleService sysDeptRoleService;

	protected static SysDepartmentService sysDepartmentService;

	protected static SysApplicationService sysApplicationService;

	static {
		sysUserService = ContextFactory.getBean("sysUserProxy");
		sysRoleService = ContextFactory.getBean("sysRoleProxy");
		sysDeptRoleService = ContextFactory.getBean("sysDeptRoleProxy");
		sysUserRoleService = ContextFactory.getBean("sysUserRoleProxy");
		sysDepartmentService = ContextFactory.getBean("sysDepartmentProxy");
		sysApplicationService = ContextFactory.getBean("sysApplicationProxy");
	}

	/**
	 * 获取委托人编号集合
	 * 
	 * @param assignTo
	 *            受托人编号
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
	 * @param roleId
	 * @return
	 */
	public static List<SysUser> getChildrenMembershipUsers(int deptId,
			int roleId) {
		return sysUserRoleService.getChildrenMembershipUsers(deptId, roleId);
	}

	/**
	 * 获取某个部门及所有下级部门的某个角色的用户
	 * 
	 * @param deptId
	 * @param roleId
	 * @return
	 */
	public static List<SysUser> getChildrenMembershipUsers(int deptId,
			String roleCode) {
		SysRole role = sysRoleService.findByCode(roleCode);
		return sysUserRoleService.getChildrenMembershipUsers(deptId,
				(int) role.getId());
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
	 * 根据部门编号获取部门(sys_department表的id字段)
	 * 
	 * @param id
	 * @return
	 */
	public static SysDepartment getDepartmentById(int id) {
		SysDepartment model = sysDepartmentService.findById(id);
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
	 * 获取部门Map
	 * 
	 * @return
	 */
	public static Map<String, SysDepartment> getDepartmentMap() {
		Map<String, SysDepartment> deptMap = new HashMap<String, SysDepartment>();
		List<SysDepartment> depts = sysDepartmentService.getSysDepartmentList();
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
		List<SysDepartment> depts = sysDepartmentService.getSysDepartmentList();
		return depts;
	}

	/**
	 * 获取全部用户
	 * 
	 * @return
	 */
	public static Map<String, SysUser> getLowerCaseUserMap() {
		Map<String, SysUser> userMap = new LinkedHashMap<String, SysUser>();
		List<SysUser> users = sysUserService.getSysUserList();
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
	 * @return
	 */
	public static List<SysUser> getMembershipUsers(int deptId) {
		return sysUserService.getSysUserList(deptId);
	}

	/**
	 * 获取某个部门某个角色的用户
	 * 
	 * @param deptId
	 * @param roleId
	 * @return
	 */
	public static List<SysUser> getMembershipUsers(int deptId, int roleId) {
		return sysUserRoleService.getMembershipUsers(deptId, roleId);
	}

	/**
	 * 获取某个部门某个角色的用户
	 * 
	 * @param deptId
	 * @param roleId
	 * @return
	 */
	public static List<SysUser> getMembershipUsers(int deptId, String roleCode) {
		SysRole role = sysRoleService.findByCode(roleCode);
		return sysUserRoleService
				.getMembershipUsers(deptId, (int) role.getId());
	}

	/**
	 * 获取某个部门某个角色的用户
	 * 
	 * @param deptIds
	 * @param roleId
	 * @return
	 */
	public static List<SysUser> getMembershipUsers(List<Integer> deptIds,
			int roleId) {
		return sysUserRoleService.getMembershipUsers(deptIds, roleId);
	}

	/**
	 * 获取某个部门某个角色的用户
	 * 
	 * @param deptIds
	 * @param roleId
	 * @return
	 */
	public static List<SysUser> getMembershipUsers(List<Integer> deptIds,
			String roleCode) {
		SysRole role = sysRoleService.findByCode(roleCode);
		return sysUserRoleService.getMembershipUsers(deptIds,
				(int) role.getId());
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
			int deptId, String treeType) {
		List<SysDepartment> list = new ArrayList<SysDepartment>();
		SysDepartment dept = sysDepartmentService.findById(deptId);
		if (dept != null) {
			sysDepartmentService.findNestingDepartment(list, dept.getId());
		}
		return list;
	}

	/**
	 * 通过角色编号获取角色
	 * 
	 * @param id
	 * @return
	 */
	public static SysRole getRoleById(int id) {
		return sysRoleService.findById(id);
	}

	/**
	 * 通过角色代码获取角色
	 * 
	 * @param code
	 * @return
	 */
	public static SysRole getRoleByCode(String code) {
		return sysRoleService.findByCode(code);
	}

	/**
	 * 获取全部角色 Map
	 * 
	 * @return
	 */
	public static Map<String, SysRole> getRoleMap() {
		Map<String, SysRole> roleMap = new HashMap<String, SysRole>();
		List<SysRole> roles = sysRoleService.getSysRoleList();
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
		List<SysRole> roles = sysRoleService.getSysRoleList();
		return roles;
	}

	/**
	 * 根据用户名获取用户对象
	 * 
	 * @param actorId
	 * @return
	 */
	public static SysUser getSysUser(String actorId) {
		return sysUserService.findByAccountWithAll(actorId);
	}

	/**
	 * 获取全部用户Map
	 * 
	 * @return
	 */
	public static Map<String, SysUser> getUserMap() {
		Map<String, SysUser> userMap = new LinkedHashMap<String, SysUser>();
		List<SysUser> users = sysUserService.getSysUserList();
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
	public static List<String> getUserRoles(List<String> actorIds) {
		return null;
	}

	/**
	 * 获取某个用户及代理人的角色编号
	 * 
	 * @param actorId
	 * @return
	 */
	public static List<String> getUserRoles(String actorId) {
		List<String> actorIds = new ArrayList<String>();
		actorIds.add(actorId);
		return getUserRoles(actorIds);
	}

	/**
	 * 获取全部用户
	 * 
	 * @return
	 */
	public static List<SysUser> getUsers() {
		return sysUserService.getSysUserList();
	}

	/**
	 * 获取用户菜单
	 * 
	 * @param parentId
	 * @param actorId
	 * @return
	 */
	public JSONArray getUserMenu(long parentId, String actorId) {
		return sysApplicationService.getUserMenu(parentId, actorId);
	}

}
