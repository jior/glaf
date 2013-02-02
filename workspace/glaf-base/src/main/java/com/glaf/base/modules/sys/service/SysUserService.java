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

import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.utils.PageResult;

public interface SysUserService {

	/**
	 * 保存
	 * 
	 * @param bean
	 *            SysUser
	 * @return boolean
	 */
	boolean create(SysUser bean);

	/**
	 * 更新
	 * 
	 * @param bean
	 *            SysUser
	 * @return boolean
	 */
	boolean update(SysUser bean);

	boolean updateUser(SysUser bean);

	/**
	 * 删除
	 * 
	 * @param bean
	 *            SysUser
	 * @return boolean
	 */
	boolean delete(SysUser bean);

	/**
	 * 删除
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	boolean delete(long id);

	/**
	 * 批量删除
	 * 
	 * @param id
	 * @return
	 */
	boolean deleteAll(long[] id);

	/**
	 * 获取对象
	 * 
	 * @param id
	 * @return
	 */
	SysUser findById(long id);

	/**
	 * 按名称查找对象
	 * 
	 * @param name
	 *            String
	 * @return SysUser
	 */
	SysUser findByAccount(String account);

	/**
	 * 按名称查找对象
	 * 
	 * @param name
	 *            String
	 * @return SysUser
	 */
	SysUser findByAccountWithAll(String account);

	/**
	 * 获取特定部门的员工数据集 分页列表
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
	 * 查询获取sysUser列表
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
	 * 获取列表
	 * 
	 * @param deptId
	 *            int
	 * @return List
	 */
	List getSysUserList(int deptId);

	/**
	 * 获取列表
	 * 
	 * @param deptId
	 *            int
	 * @return List
	 */
	List getSysUserList();

	/**
	 * 获取列表
	 * 
	 * @param deptId
	 *            int
	 * @return List
	 */
	List getSysUserWithDeptList();

	/**
	 * 
	 * @param user
	 * @return
	 */
	Set getUserRoles(SysUser user);

	/**
	 * 其用户权限
	 * 
	 * @param user
	 * @return
	 */
	SysUser getUserPrivileges(SysUser user);

	SysUser getUserAndPrivileges(SysUser user);

	/**
	 * 查找供应商用户 flag = true 表示该用户存在，否则为不存在
	 * 
	 * @param supplierNo
	 * @return
	 */
	List<SysUser> getSupplierUser(String supplierNo);

	/**
	 * 设置用户权限
	 * 
	 * @param user
	 *            系统用户
	 * @param delRoles
	 *            要删除的用户权限
	 * @param newRoles
	 *            要增加的用户权限
	 */
	boolean updateRole(SysUser user, Set delRoles, Set newRoles);

	/**
	 * 获取列表
	 * 
	 */
	PageResult getSysUserList(int deptId, String userName, String account,
			int pageNo, int pageSize);

	boolean isThisPlayer(SysUser user, String code);

}