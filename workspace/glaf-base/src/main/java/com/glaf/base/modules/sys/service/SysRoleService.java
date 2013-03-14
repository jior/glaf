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
	 * ����
	 * 
	 * @param bean
	 *            SysRole
	 * @return boolean
	 */
	@Transactional
	boolean update(SysRole bean);

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
	 * ��code���Ҷ���
	 * 
	 * @param name
	 *            String
	 * @return SysRole
	 */
	SysRole findByCode(String code);

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
	 * ��ȡ�б�
	 * 
	 * @return List
	 */
	List<SysRole> getSysRoleList();

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

}