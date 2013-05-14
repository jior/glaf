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

import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.query.SysTreeQuery;
import com.glaf.base.modules.sys.query.SysUserQuery;
import com.glaf.core.util.PageResult;

@Transactional(readOnly = true)
public interface SysTreeService {

	/**
	 * 保存
	 * 
	 * @param bean
	 *            SysTree
	 * @return boolean
	 */
	@Transactional
	boolean create(SysTree bean);

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
	 *            SysTree
	 * @return boolean
	 */
	@Transactional
	boolean delete(SysTree bean);

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	@Transactional
	boolean deleteAll(long[] ids);

	/**
	 * 获取对象
	 * 
	 * @param id
	 * @return
	 */
	SysTree findById(long id);

	/**
	 * 按名称查找对象
	 * 
	 * @param name
	 *            String
	 * @return SysTree
	 */
	SysTree findByName(String name);

	/**
	 * 获取全部列表
	 * 
	 * @return List
	 */
	List<SysTree> getAllSysTreeList();

	/**
	 * 获取全部列表
	 * 
	 * @param parent
	 *            int 父ID
	 * @param status
	 *            int 状态
	 * @return List
	 */
	List<SysTree> getAllSysTreeListForDept(long parent, int status);

	/**
	 * 获取应用模块树型结构
	 * 
	 * @param query
	 * @return
	 */
	List<SysTree> getApplicationSysTrees(SysTreeQuery query);

	/**
	 * 获取部门树型结构
	 * 
	 * @param query
	 * @return
	 */
	List<SysTree> getDepartmentSysTrees(SysTreeQuery query);

	/**
	 * 获取字典树型结构
	 * 
	 * @param query
	 * @return
	 */
	List<SysTree> getDictorySysTrees(SysTreeQuery query);

	/**
	 * 获取关联表树型结构
	 * 
	 * @param relationTable
	 *            表名
	 * @param relationColumn
	 *            关联字段名
	 * @param query
	 * @return
	 */
	List<SysTree> getRelationSysTrees(String relationTable,
			String relationColumn, SysTreeQuery query);

	/**
	 * 获取某个用户某个角色的树节点
	 * 
	 * @param query
	 * @return
	 */
	List<SysTree> getRoleUserTrees(SysUserQuery query);

	/**
	 * 获取树型列表
	 * 
	 * @param parent
	 *            int
	 * @return List
	 */
	void getSysTree(List<SysTree> tree, long parentId, int deep);

	/**
	 * 按树编号获取树节点
	 * 
	 * @param tree
	 * @return SysTree
	 */
	SysTree getSysTreeByCode(String code);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getSysTreeCountByQueryCriteria(SysTreeQuery query);

	/**
	 * 获取全部列表
	 * 
	 * @param parent
	 *            int
	 * @return List
	 */
	List<SysTree> getSysTreeList(long parentId);

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
	PageResult getSysTreeList(long parentId, int pageNo, int pageSize);

	/**
	 * 获取全部列表
	 * 
	 * @param parent
	 *            int 父ID
	 * @param status
	 *            int 状态
	 * @return List
	 */
	List<SysTree> getSysTreeListForDept(long parentId, int status);

	/**
	 * 获取父节点列表，如:根目录>A>A1>A11
	 * 
	 * @param tree
	 * @param int id
	 */
	void getSysTreeParent(List<SysTree> tree, long id);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<SysTree> getSysTreesByQueryCriteria(int start, int pageSize,
			SysTreeQuery query);

	/**
	 * 排序
	 * 
	 * @param bean
	 *            SysTree
	 * @param operate
	 *            int 操作
	 */
	@Transactional
	void sort(long parent, SysTree bean, int operate);

	/**
	 * 更新
	 * 
	 * @param bean
	 *            SysTree
	 * @return boolean
	 */
	@Transactional
	boolean update(SysTree bean);

	/**
	 * 更新树的treeId字段
	 * 
	 * @param treeMap
	 */
	@Transactional
	void updateTreeIds();

	/**
	 * 更新指定树的treeId字段
	 * 
	 * @param treeMap
	 */
	@Transactional
	void updateTreeIds(Map<Long, String> treeMap);
}