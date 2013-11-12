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

package com.glaf.base.modules.sys.service;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.model.SysDeptRole;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.query.SysUserQuery;

import com.glaf.core.util.PageResult;

@Transactional(readOnly = true)
public interface SysUserService {

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysUser
	 * @return boolean
	 */
	@Transactional
	boolean create(SysUser bean);

	/**
	 * ɾ��
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	@Transactional
	boolean delete(long id);

	/**
	 * ɾ��
	 * 
	 * @param bean
	 *            SysUser
	 * @return boolean
	 */
	@Transactional
	boolean delete(SysUser bean);

	/**
	 * ����ɾ��
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	boolean deleteAll(long[] id);

	/**
	 * ɾ�����Ž�ɫ�û�
	 * 
	 * @param deptRole
	 * @param userIds
	 */
	@Transactional
	void deleteRoleUsers(SysDeptRole deptRole, long[] userIds);

	/**
	 * �����Ʋ��Ҷ���
	 * 
	 * @param name
	 *            String
	 * @return SysUser
	 */
	SysUser findByAccount(String account);
	
	/**
	 * ��������Ҷ���
	 * 
	 * @param mail
	 *            String
	 * @return SysUser
	 */
	SysUser findByMail(String mail);
	
	
	/**
	 * ���ֻ����Ҷ���
	 * 
	 * @param mobile
	 *            String
	 * @return SysUser
	 */
	SysUser findByMobile(String mobile);

	/**
	 * �����Ʋ��Ҷ���
	 * 
	 * @param name
	 *            String
	 * @return SysUser
	 */
	SysUser findByAccountWithAll(String account);

	/**
	 * ��ȡ����
	 * 
	 * @param id
	 * @return
	 */
	SysUser findById(long id);

	/**
	 * ��ȡĳ���û����ϼ�
	 * 
	 * @param account
	 * @return
	 */
	List<SysUser> getSuperiors(String account);

	/**
	 * ���ҹ�Ӧ���û� flag = true ��ʾ���û����ڣ�����Ϊ������
	 * 
	 * @param supplierNo
	 * @return
	 */
	List<SysUser> getSupplierUser(String supplierNo);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getSysUserCountByQueryCriteria(SysUserQuery query);

	/**
	 * ��ȡ�б�
	 * 
	 * @param deptId
	 *            int
	 * @return List
	 */
	List<SysUser> getSysUserList();

	/**
	 * ��ȡ�б�
	 * 
	 * @param deptId
	 *            int
	 * @return List
	 */
	List<SysUser> getSysUserList(long deptId);

	/**
	 * ��ȡ�ض����ŵ�Ա�����ݼ� ��ҳ�б�
	 * 
	 * @param deptId
	 *            int
	 * @param pageNo
	 *            int
	 * @param pageSize
	 *            int
	 * @return
	 */
	PageResult getSysUserList(long deptId, int pageNo, int pageSize);

	/**
	 * ��ѯ��ȡsysUser�б�
	 * 
	 * @param deptId
	 * @param fullName
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageResult getSysUserList(long deptId, String fullName, int pageNo,
			int pageSize);

	/**
	 * ��ȡ�б�
	 * 
	 */
	PageResult getSysUserList(long deptId, String userName, String account,
			int pageNo, int pageSize);

	/**
	 * ��ȡĳ��Ӧ�õ�Ȩ���û�
	 * 
	 * @param appId
	 * @return
	 */
	List<SysUser> getSysUsersByAppId(Long appId);

	List<SysUser> getSysUsersByDeptRole(Long deptId, String roleCode);

	List<SysUser> getSysUsersByDeptRoleId(Long deptRoleId);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<SysUser> getSysUsersByQueryCriteria(int start, int pageSize,
			SysUserQuery query);

	/**
	 * ��ȡĳ����ɫ������û�
	 * 
	 * @param roleCode
	 * @return
	 */
	List<SysUser> getSysUsersByRoleCode(String roleCode);

	/**
	 * ��ȡ�б�
	 * 
	 * @param deptId
	 *            int
	 * @return List
	 */
	List<SysUser> getSysUserWithDeptList();

	SysUser getUserAndPrivileges(SysUser user);

	/**
	 * ���û�Ȩ��
	 * 
	 * @param user
	 * @return
	 */
	SysUser getUserPrivileges(SysUser user);

	/**
	 * ��ȡĳЩ�û��Ľ�ɫ
	 * 
	 * @param actorIds
	 * @return
	 */
	List<SysRole> getUserRoles(List<String> actorIds);

	/**
	 * 
	 * @param user
	 * @return
	 */
	Set<SysDeptRole> getUserRoles(SysUser user);

	boolean isThisPlayer(SysUser user, String code);

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysUser
	 * @return boolean
	 */
	@Transactional
	boolean update(SysUser bean);

	/**
	 * �����û�Ȩ��
	 * 
	 * @param user
	 *            ϵͳ�û�
	 * @param delRoles
	 *            Ҫɾ�����û�Ȩ��
	 * @param newRoles
	 *            Ҫ���ӵ��û�Ȩ��
	 */
	@Transactional
	boolean updateRole(SysUser user, Set<SysDeptRole> delRoles,
			Set<SysDeptRole> newRoles);

	@Transactional
	boolean updateUser(SysUser bean);

}