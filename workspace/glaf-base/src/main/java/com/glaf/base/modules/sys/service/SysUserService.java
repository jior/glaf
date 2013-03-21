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
import com.glaf.base.modules.sys.model.SysUser;

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
	 * ����
	 * 
	 * @param bean
	 *            SysUser
	 * @return boolean
	 */
	@Transactional
	boolean update(SysUser bean);

	@Transactional
	boolean updateUser(SysUser bean);

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
	 * ɾ��
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	@Transactional
	boolean delete(long id);

	/**
	 * ����ɾ��
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	boolean deleteAll(long[] id);

	/**
	 * ��ȡ����
	 * 
	 * @param id
	 * @return
	 */
	SysUser findById(long id);

	/**
	 * �����Ʋ��Ҷ���
	 * 
	 * @param name
	 *            String
	 * @return SysUser
	 */
	SysUser findByAccount(String account);

	/**
	 * �����Ʋ��Ҷ���
	 * 
	 * @param name
	 *            String
	 * @return SysUser
	 */
	SysUser findByAccountWithAll(String account);

	/**
	 * ��ȡĳ���û����ϼ�
	 * 
	 * @param account
	 * @return
	 */
	List<SysUser> getSuperiors(String account);

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
	PageResult getSysUserList(int deptId, int pageNo, int pageSize);

	/**
	 * ��ѯ��ȡsysUser�б�
	 * 
	 * @param deptId
	 * @param fullName
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageResult getSysUserList(int deptId, String fullName, int pageNo,
			int pageSize);

	/**
	 * ��ȡ�б�
	 * 
	 * @param deptId
	 *            int
	 * @return List
	 */
	List<SysUser> getSysUserList(int deptId);

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
	List<SysUser> getSysUserWithDeptList();

	/**
	 * 
	 * @param user
	 * @return
	 */
	Set<SysDeptRole> getUserRoles(SysUser user);

	/**
	 * ���û�Ȩ��
	 * 
	 * @param user
	 * @return
	 */
	SysUser getUserPrivileges(SysUser user);

	SysUser getUserAndPrivileges(SysUser user);

	/**
	 * ���ҹ�Ӧ���û� flag = true ��ʾ���û����ڣ�����Ϊ������
	 * 
	 * @param supplierNo
	 * @return
	 */
	List<SysUser> getSupplierUser(String supplierNo);
	
	/**
	 * ��ȡĳ��Ӧ�õ�Ȩ���û�
	 * @param appId
	 * @return
	 */
	List<SysUser> getSysUsersByAppId(Long appId);

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
	boolean updateRole(SysUser user, Set delRoles, Set newRoles);

	/**
	 * ��ȡ�б�
	 * 
	 */
	PageResult getSysUserList(int deptId, String userName, String account,
			int pageNo, int pageSize);

	boolean isThisPlayer(SysUser user, String code);

}