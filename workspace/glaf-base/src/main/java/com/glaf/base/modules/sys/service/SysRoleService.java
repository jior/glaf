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

import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.query.SysDepartmentQuery;
import com.glaf.base.modules.sys.query.SysRoleQuery;
import com.glaf.core.util.PageResult;

@Transactional(readOnly = true)
public interface SysRoleService {

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysRole
	 * @return boolean
	 */
	@Transactional
	boolean create(SysRole bean);

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
	 *            SysRole
	 * @return boolean
	 */
	@Transactional
	boolean delete(SysRole bean);

	/**
	 * ����ɾ��
	 * 
	 * @param ids
	 * @return
	 */
	@Transactional
	boolean deleteAll(long[] ids);

	/**
	 * ��code���Ҷ���
	 * 
	 * @param name
	 *            String
	 * @return SysRole
	 */
	SysRole findByCode(String code);

	/**
	 * ��ȡ����
	 * 
	 * @param id
	 * @return
	 */
	SysRole findById(long id);

	/**
	 * �����Ʋ��Ҷ���
	 * 
	 * @param name
	 *            String
	 * @return SysRole
	 */
	SysRole findByName(String name);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getSysRoleCountByQueryCriteria(SysRoleQuery query);

	/**
	 * ��ȡ�б�
	 * 
	 * @return List
	 */
	List<SysRole> getSysRoleList();
	
	
	List<SysRole> getSysRolesOfDepts(SysDepartmentQuery query);

	/**
	 * ��ȡ��ҳ�б�
	 * 
	 * @param pageNo
	 *            int
	 * @param pageSize
	 *            int
	 * @return
	 */
	PageResult getSysRoleList(int pageNo, int pageSize);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<SysRole> getSysRolesByQueryCriteria(int start, int pageSize,
			SysRoleQuery query);

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysRole
	 * @param operate
	 *            int ����
	 */
	@Transactional
	void sort(SysRole bean, int operate);

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysRole
	 * @return boolean
	 */
	@Transactional
	boolean update(SysRole bean);

}