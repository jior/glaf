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
	 * ��ȡί���˱�ż���
	 * 
	 * @param assignTo
	 *            �����˱��
	 * @return
	 */
	public static List<String> getAgentIds(String assignTo) {
		List<String> agentIds = new ArrayList<String>();
		return agentIds;
	}

	/**
	 * ��ȡĳ�����ż������¼����ŵ�ĳ����ɫ���û�
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
	 * ��ȡĳ�����ż������¼����ŵ�ĳ����ɫ���û�
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
	 * ���ݲ��Ŵ����ȡ����(sys_department���code�ֶ�)
	 * 
	 * @param code
	 * @return
	 */
	public static SysDepartment getDepartmentByCode(String code) {
		SysDepartment model = sysDepartmentService.findByCode(code);
		return model;
	}

	/**
	 * ���ݲ��ű�Ż�ȡ����(sys_department���id�ֶ�)
	 * 
	 * @param id
	 * @return
	 */
	public static SysDepartment getDepartmentById(int id) {
		SysDepartment model = sysDepartmentService.findById(id);
		return model;
	}

	/**
	 * ���ݲ��Ŵ����ȡ����(sys_department���deptno�ֶ�)
	 * 
	 * @param deptno
	 * @return
	 */
	public static SysDepartment getDepartmentByNo(String deptno) {
		SysDepartment model = sysDepartmentService.findByNo(deptno);
		return model;
	}

	/**
	 * ��ȡ����Map
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
	 * ��ȡȫ������
	 * 
	 * @return
	 */
	public static List<SysDepartment> getDepartments() {
		List<SysDepartment> depts = sysDepartmentService.getSysDepartmentList();
		return depts;
	}

	/**
	 * ��ȡȫ���û�
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
	 * ��ȡĳ�����ŵ��û�
	 * 
	 * @param deptId
	 * @return
	 */
	public static List<SysUser> getMembershipUsers(int deptId) {
		return sysUserService.getSysUserList(deptId);
	}

	/**
	 * ��ȡĳ������ĳ����ɫ���û�
	 * 
	 * @param deptId
	 * @param roleId
	 * @return
	 */
	public static List<SysUser> getMembershipUsers(int deptId, int roleId) {
		return sysUserRoleService.getMembershipUsers(deptId, roleId);
	}

	/**
	 * ��ȡĳ������ĳ����ɫ���û�
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
	 * ��ȡĳ������ĳ����ɫ���û�
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
	 * ��ȡĳ������ĳ����ɫ���û�
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
	 * ��ȡĳ������ָ��������ϼ����ְ����������Ӳ���
	 * 
	 * @param deptId
	 *            ���ű��
	 * @param treeType
	 *            ���� 0-��˾,1(B)-��,2(K)-��,3-ϵ,4-��(��)
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
	 * ͨ����ɫ��Ż�ȡ��ɫ
	 * 
	 * @param id
	 * @return
	 */
	public static SysRole getRoleById(int id) {
		return sysRoleService.findById(id);
	}

	/**
	 * ͨ����ɫ�����ȡ��ɫ
	 * 
	 * @param code
	 * @return
	 */
	public static SysRole getRoleByCode(String code) {
		return sysRoleService.findByCode(code);
	}

	/**
	 * ��ȡȫ����ɫ Map
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
	 * ��ȡȫ����ɫ
	 * 
	 * @return
	 */
	public static List<SysRole> getRoles() {
		List<SysRole> roles = sysRoleService.getSysRoleList();
		return roles;
	}

	/**
	 * �����û�����ȡ�û�����
	 * 
	 * @param actorId
	 * @return
	 */
	public static SysUser getSysUser(String actorId) {
		return sysUserService.findByAccountWithAll(actorId);
	}

	/**
	 * ��ȡȫ���û�Map
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
	 * ��ȡĳЩ�û��Ľ�ɫ����
	 * 
	 * @param actorIds
	 * @return
	 */
	public static List<String> getUserRoles(List<String> actorIds) {
		return null;
	}

	/**
	 * ��ȡĳ���û��������˵Ľ�ɫ���
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
	 * ��ȡȫ���û�
	 * 
	 * @return
	 */
	public static List<SysUser> getUsers() {
		return sysUserService.getSysUserList();
	}

	/**
	 * ��ȡ�û��˵�
	 * 
	 * @param parentId
	 * @param actorId
	 * @return
	 */
	public JSONArray getUserMenu(long parentId, String actorId) {
		return sysApplicationService.getUserMenu(parentId, actorId);
	}

}
