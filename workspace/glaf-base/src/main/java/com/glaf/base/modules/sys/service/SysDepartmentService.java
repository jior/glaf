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

import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.utils.PageResult;

public interface SysDepartmentService {

	/**
	 * 保存
	 * 
	 * @param bean
	 *            SysDepartment
	 * @return boolean
	 */
	boolean create(SysDepartment bean);

	/**
	 * 更新
	 * 
	 * @param bean
	 *            SysDepartment
	 * @return boolean
	 */
	boolean update(SysDepartment bean);

	/**
	 * 删除
	 * 
	 * @param bean
	 *            SysDepartment
	 * @return boolean
	 */
	boolean delete(SysDepartment bean);

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
	SysDepartment findById(long id);

	/**
	 * 按编码查找对象
	 * 
	 * @param code
	 * 
	 * @return SysDepartment
	 */
	SysDepartment findByCode(String code);

	/**
	 * 按部门编号查找对象
	 * 
	 * @param deptno
	 * 
	 * @return SysDepartment
	 */

	SysDepartment findByNo(String deptno);

	/**
	 * 按名称查找对象
	 * 
	 * @param name
	 *            String
	 * @return SysDepartment
	 */
	SysDepartment findByName(String name);

	/**
	 * 获取分页列表
	 * 
	 * @param parent
	 *            int
	 * @param pageNo
	 *            int
	 * @param pageSize
	 *            int
	 * @return
	 */
	PageResult getSysDepartmentList(int parent, int pageNo, int pageSize);

	/**
	 * 获取列表
	 * 
	 * @return List
	 */
	List<SysDepartment> getSysDepartmentList();

	/**
	 * 获取列表
	 * 
	 * @param parent
	 *            int
	 * @return List
	 */
	List<SysDepartment> getSysDepartmentList(int parent);

	/**
	 * 排序
	 * 
	 * @param bean
	 *            SysDepartment
	 * @param operate
	 *            int 操作
	 */
	void sort(long parent, SysDepartment bean, int operate);

	/**
	 * 获取某个部门及下级部门列表
	 * 
	 * @param list
	 * @param deptId
	 */
	void findNestingDepartment(List<SysDepartment> list, long deptId);

	/**
	 * 获取某个部门及下级部门列表
	 * 
	 * @param list
	 * @param node
	 */
	void findNestingDepartment(List<SysDepartment> list, SysDepartment node);
}