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
	 * 保存
	 * 
	 * @param bean
	 *            SysRole
	 * @return boolean
	 */
	@Transactional
	boolean create(SysRole bean);

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
	 *            SysRole
	 * @return boolean
	 */
	@Transactional
	boolean delete(SysRole bean);

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	@Transactional
	boolean deleteAll(long[] ids);

	/**
	 * 按code查找对象
	 * 
	 * @param name
	 *            String
	 * @return SysRole
	 */
	SysRole findByCode(String code);

	/**
	 * 获取对象
	 * 
	 * @param id
	 * @return
	 */
	SysRole findById(long id);

	/**
	 * 按名称查找对象
	 * 
	 * @param name
	 *            String
	 * @return SysRole
	 */
	SysRole findByName(String name);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getSysRoleCountByQueryCriteria(SysRoleQuery query);

	/**
	 * 获取列表
	 * 
	 * @return List
	 */
	List<SysRole> getSysRoleList();
	
	
	List<SysRole> getSysRolesOfDepts(SysDepartmentQuery query);

	/**
	 * 获取分页列表
	 * 
	 * @param pageNo
	 *            int
	 * @param pageSize
	 *            int
	 * @return
	 */
	PageResult getSysRoleList(int pageNo, int pageSize);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<SysRole> getSysRolesByQueryCriteria(int start, int pageSize,
			SysRoleQuery query);

	/**
	 * 排序
	 * 
	 * @param bean
	 *            SysRole
	 * @param operate
	 *            int 操作
	 */
	@Transactional
	void sort(SysRole bean, int operate);

	/**
	 * 更新
	 * 
	 * @param bean
	 *            SysRole
	 * @return boolean
	 */
	@Transactional
	boolean update(SysRole bean);

}