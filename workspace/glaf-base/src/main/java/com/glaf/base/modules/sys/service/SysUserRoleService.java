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



@SuppressWarnings("rawtypes")
@Transactional(readOnly = true)
public interface SysUserRoleService {

	/**
	 * 授权
	 * 
	 * @param fromUser
	 *            SysUser 授权人
	 * @param toUser
	 *            SysUser 被授权人
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
	 * 保存
	 * 
	 * @param bean
	 *            SysUserRole
	 * @return boolean
	 */
	@Transactional
	boolean create(SysUserRole bean);

	/**
	 * 删除
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	@Transactional
	boolean delete(long id);

	/**
	 * 删除
	 * 
	 * @param bean
	 *            SysUserRole
	 * @return boolean
	 */
	@Transactional
	boolean delete(SysUserRole bean);

	/**
	 * 获取对象
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
	 * 获取某个部门及所有下级部门的某个角色的用户
	 * 
	 * @param deptId
	 * @param roleId
	 * @return
	 */
	List<SysUser> getChildrenMembershipUsers(long deptId, long roleId);

	/**
	 * 获取某个部门某个角色的用户
	 * 
	 * @param deptId
	 * @param roleId
	 * @return
	 */
	List<SysUser> getMembershipUsers(long deptId, long roleId);

	/**
	 * 获取多个部门某个角色的用户
	 * 
	 * @param deptIds
	 * @param roleId
	 * @return
	 */
	List<SysUser> getMembershipUsers(List<Long> deptIds, long roleId);

	/**
	 * 某人已授权的用户列表
	 * 
	 * @param user
	 *            SysUser
	 * @return
	 */
	List getAuthorizedUser(SysUser user);

	/**
	 * 获取用户所有的审批工作流
	 */
	List getProcessByUser(SysUser user);

	/**
	 * 取本部门下的未授权用户列表（除了自己、已授权用户）
	 * 
	 * @param user
	 *            SysUser
	 * @return
	 */
	List getUnAuthorizedUser(SysUser user);

	/**
	 * 工作流授权
	 * 
	 * @param fromUser
	 * @param toUser
	 */
	@Transactional
	void insertAgent(SysUser fromUser, SysUser toUser, String startDate,
			String endDate, int mark, String processNames);

	/**
	 * 判断是否已经授权了
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
	 * 取消授权
	 * 
	 * @param fromUser
	 *            SysUser 授权人
	 * @param toUser
	 *            SysUser 被授权人
	 */
	@Transactional
	boolean removeRole(long fromUserId, long toUserId);

	/**
	 * 定时批量删除过期代理的权限
	 * 
	 * @return
	 */
	@Transactional
	public boolean removeRoles();

	/**
	 * 更新
	 * 
	 * @param bean
	 *            SysUserRole
	 * @return boolean
	 */
	@Transactional
	boolean update(SysUserRole bean);
}