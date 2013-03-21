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
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.model.SysUserRole;
import com.glaf.core.util.PageResult;

@Transactional(readOnly = true)
public interface SysUserRoleService {

	/**
	 * ��Ȩ
	 * 
	 * @param fromUser
	 *            SysUser ��Ȩ��
	 * @param toUser
	 *            SysUser ����Ȩ��
	 * @param startDate
	 *            String
	 * @param endDate
	 *            String
	 */
	@Transactional
	boolean addRole(long fromUserId, long toUserId, String startDate,
			String endDate, int mark, String processNames,
			String processDescriptions);

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysUserRole
	 * @return boolean
	 */
	@Transactional
	boolean create(SysUserRole bean);

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
	 *            SysUserRole
	 * @return boolean
	 */
	@Transactional
	boolean delete(SysUserRole bean);

	/**
	 * ��ȡ����
	 * 
	 * @param id
	 *            long
	 * @return
	 */
	SysUserRole findById(long id);

	/**
	 * 
	 * @param filter
	 * @return
	 */
	PageResult getAllAuthorizedUser(Map<String, String> filter);

	/**
	 * ��ȡĳ�����ż������¼����ŵ�ĳ����ɫ���û�
	 * 
	 * @param deptId
	 * @param roleId
	 * @return
	 */
	List<SysUser> getChildrenMembershipUsers(int deptId, int roleId);

	/**
	 * ��ȡĳ������ĳ����ɫ���û�
	 * 
	 * @param deptId
	 * @param roleId
	 * @return
	 */
	List<SysUser> getMembershipUsers(int deptId, int roleId);

	/**
	 * ��ȡ�������ĳ����ɫ���û�
	 * 
	 * @param deptIds
	 * @param roleId
	 * @return
	 */
	List<SysUser> getMembershipUsers(List<Integer> deptIds, int roleId);

	/**
	 * ĳ������Ȩ���û��б�
	 * 
	 * @param user
	 *            SysUser
	 * @return
	 */
	List getAuthorizedUser(SysUser user);

	/**
	 * ��ȡ�û����е�����������
	 */
	List getProcessByUser(SysUser user);

	/**
	 * ȡ�������µ�δ��Ȩ�û��б������Լ�������Ȩ�û���
	 * 
	 * @param user
	 *            SysUser
	 * @return
	 */
	List getUnAuthorizedUser(SysUser user);

	/**
	 * ��������Ȩ
	 * 
	 * @param fromUser
	 * @param toUser
	 */
	@Transactional
	void insertAgent(SysUser fromUser, SysUser toUser, String startDate,
			String endDate, int mark, String processNames);

	/**
	 * �ж��Ƿ��Ѿ���Ȩ��
	 * 
	 * @param fromUserId
	 *            long
	 * @param toUserId
	 *            long
	 * @return
	 */
	boolean isAuthorized(long fromUserId, long toUserId);

	@Transactional
	void removeAgent(SysUser fromUser, SysUser toUser);

	/**
	 * ȡ����Ȩ
	 * 
	 * @param fromUser
	 *            SysUser ��Ȩ��
	 * @param toUser
	 *            SysUser ����Ȩ��
	 */
	@Transactional
	boolean removeRole(long fromUserId, long toUserId);

	/**
	 * ��ʱ����ɾ�����ڴ����Ȩ��
	 * 
	 * @return
	 */
	@Transactional
	public boolean removeRoles();

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysUserRole
	 * @return boolean
	 */
	@Transactional
	boolean update(SysUserRole bean);
}