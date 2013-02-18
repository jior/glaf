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
	 * ��ȡĳ�����ż������¼����ŵ�ĳ����ɫ���û�
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
	 * ���ݲ��ű�Ż�ȡ����
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
	 * ��ȡ����
	 * 
	 * @return
	 */
	public static List<SysDepartment> getDepartments() {
		List<SysDepartment> depts = sysDepartmentService.getSysDepartmentList();
		return depts;
	}

	/**
	 * ��ȡȫ���û� <br>
	 * ͨ������getUsers��ȡUser����
	 * 
	 * @return
	 */
	public static Map<String, SysUser> getLowerCaseUserMap() {
		Map<String, SysUser> userMap = new LinkedHashMap<String, SysUser>();

		return userMap;
	}

	/**
	 * ��ȡĳЩ�û��Ľ�ɫ���<br>
	 * ͨ������getMembershipRoleIds��ȡ��ɫ���
	 * 
	 * @param actorIds
	 * @return
	 */
	public static List<String> getMembershipRoleIds(List<String> actorIds) {
		return null;
	}

	/**
	 * ��ȡĳ���û��������˵Ľ�ɫ���
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
	 * ��ȡĳ������ĳ����ɫ���û�
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
	 * ��ȡĳ�����ŵ��û�
	 * 
	 * @param deptId
	 * @return
	 */
	public static List<SysUser> getMembershipUsers(String deptId) {
		return null;
	}

	/**
	 * ��ȡĳ������ĳ����ɫ���û�
	 * 
	 * @param deptId
	 * @param roleId
	 * @return
	 */
	public static List<SysUser> getMembershipUsers(String deptId, String roleId) {
		return null;
	}

	/**
	 * ��ȡĳ������ָ��������ϼ����ְ����������Ӳ���
	 * 
	 * @param deptId
	 *            ���ű��
	 * @param treeType
	 *            ���� 0-��˾,1-��,2-��,3-ϵ,4-��(��)
	 * @return
	 */
	public static List<SysDepartment> getParentAndChildrenDepartments(
			String deptId, String treeType) {
		List<SysDepartment> depts = new ArrayList<SysDepartment>();

		return depts;
	}

	/**
	 * ��ȡȨ�޵���
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
	 * ͨ����ɫ��Ż�ȡ��ɫ
	 * 
	 * @param roleId
	 * @return
	 */
	public static SysRole getRole(String roleId) {
		return null;
	}

	/**
	 * ��ȡȫ����ɫ <br>
	 * ͨ������getRoles��ȡRole����
	 * 
	 * @return
	 */
	public static Map<String, SysRole> getRoleMap() {
		Map<String, SysRole> roleMap = new HashMap<String, SysRole>();

		return roleMap;
	}

	/**
	 * ��ȡȫ����ɫ <br>
	 * ͨ������getRoles��ȡRole����
	 * 
	 * @return
	 */
	public static List<SysRole> getRoles() {
		List<SysRole> roles = new ArrayList<SysRole>();

		return roles;
	}

	/**
	 * ��ȡĳ����ɫ���û�
	 * 
	 * @param roleId
	 * @return
	 */
	public static List<SysUser> getRoleUsers(String roleId) {
		return null;
	}

	/**
	 * ��ȡ�������
	 * 
	 * @return
	 */
	public static SysDepartment getRootDepartment() {
		return null;
	}

	/**
	 * �����û�����ȡ�û���ȫ������
	 * 
	 * @param actorId
	 * @return
	 */
	public static SysUser getSysUser(String actorId) {
		return null;
	}

	/**
	 * ��ȡȫ���û� <br>
	 * ͨ������getUsers��ȡUser����
	 * 
	 * @return
	 */
	public static Map<String, SysUser> getUserMap() {
		Map<String, SysUser> userMap = new LinkedHashMap<String, SysUser>();

		return userMap;
	}

	/**
	 * ��ȡ�û��˵�
	 * 
	 * @param actorId
	 * @return
	 */
	public static List<SysApplication> getUserMenus(String actorId) {

		return null;
	}

	/**
	 * ��ȡȫ���û� <br>
	 * ͨ������getUsers��ȡUser����
	 * 
	 * @return
	 */
	public static List<SysUser> getUsers() {
		return null;
	}

}
