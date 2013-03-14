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
public interface SysDeptRoleService {

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysDeptRole
	 * @return boolean
	 */
	@Transactional
	boolean create(SysDeptRole bean);

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysDeptRole
	 * @return boolean
	 */
	@Transactional
	boolean update(SysDeptRole bean);

	/**
	 * ɾ��
	 * 
	 * @param bean
	 *            SysDeptRole
	 * @return boolean
	 */
	@Transactional
	boolean delete(SysDeptRole bean);

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
	 * @param ids
	 * @return
	 */
	@Transactional
	boolean deleteAll(long[] ids);

	@Transactional
	boolean deleteByDept(long deptId);

	/**
	 * ��ȡ����
	 * 
	 * @param id
	 * @return
	 */
	SysDeptRole findById(long id);

	/**
	 * 
	 * @param deptId
	 * @param roleId
	 * @return
	 */
	SysDeptRole find(long deptId, long roleId);

	/**
	 * 
	 * @param deptId
	 * @param code
	 * @return
	 */
	Set<SysUser> findRoleUser(long deptId, String code);

	/**
	 * ��ȡ��ҳ�б�
	 * 
	 * @param pageNo
	 *            int
	 * @param pageSize
	 *            int
	 * @return
	 */
	PageResult getRoleList(long deptId, int pageNo, int pageSize);

	/**
	 * ��ȡ�б�
	 * 
	 * @return List
	 */
	List<SysDeptRole> getRoleList(long deptId);

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysDeptRole
	 * @param operate
	 *            int ����
	 */
	@Transactional
	void sort(SysDeptRole bean, int operate);

	/**
	 * ���ý�ɫ��Ӧ��ģ�顢����
	 * 
	 * @param roleId
	 * @param appIds
	 * @param funcIds
	 * @return
	 */
	@Transactional
	boolean saveRoleApplication(long roleId, long[] appIds, long[] funcIds);

}