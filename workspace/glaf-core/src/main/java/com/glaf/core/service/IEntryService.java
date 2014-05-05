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

package com.glaf.core.service;

import java.util.List;

import com.glaf.core.domain.EntityEntry;
import com.glaf.core.base.TreeModel;
import com.glaf.core.security.LoginContext;

public interface IEntryService {

	/**
	 * 删除记录
	 * 
	 * @param rowId
	 */

	void deleteEntityEntry(String rowId);

	/**
	 * 根据主键获取实体访问项
	 * 
	 * @param rowId
	 * @return
	 */
	EntityEntry getEntityEntry(String rowId);

	/**
	 * 根据节点编号及访问Key获取实体访问项
	 * 
	 * @param nodeId
	 * @param entryKey
	 * @return
	 */
	EntityEntry getEntityEntry(long nodeId, String entryKey);

	/**
	 * 根据参数获取实体访问项
	 * 
	 * @param moduleId
	 *            模块编号
	 * @param entityId
	 *            实体编号
	 * @param entryKey
	 *            实体Key
	 * @return
	 */
	EntityEntry getEntityEntry(String moduleId, String entityId, String entryKey);

	/**
	 * 获取某个用户能访问的记录集合
	 * 
	 * 
	 * @param loginContext
	 *            用户上下文
	 * @param moduleId
	 *            模块标识
	 * @param entryKey
	 *            权限
	 * @return
	 */
	List<String> getEntityIds(LoginContext loginContext, String moduleId,
			String entryKey);

	/**
	 * 获取某个用户某个模块的访问权限
	 * 
	 * @param loginContext
	 * @param moduleId
	 * @return
	 */
	List<String> getEntryKeys(LoginContext loginContext, String moduleId);

	/**
	 * 获取某个用户能访问的节点集合
	 * 
	 * @param loginContext
	 *            用户上下文
	 * @return
	 */
	List<TreeModel> getTreeModels(LoginContext loginContext);

	/**
	 * 检查某个用户是否有某个分类的某个权限
	 * 
	 * @param loginContext
	 *            用户上下文
	 * @param nodeId
	 *            分类编号
	 * @param permKey
	 *            权限点
	 * @return
	 */
	boolean hasPermission(LoginContext loginContext, long nodeId, String permKey);

	/**
	 * 检查某个用户是否有某个记录的某个权限
	 * 
	 * @param loginContext
	 *            用户上下文
	 * @param moduleId
	 *            模块标识
	 * @param entityId
	 *            记录编号
	 * 
	 * @param permKey
	 *            权限点
	 * @return
	 */
	boolean hasPermission(LoginContext loginContext, String moduleId,
			String entityId, String permKey);

	/**
	 * 保存记录
	 * 
	 * @param entityEntry
	 */
	void saveEntityEntry(EntityEntry entityEntry);

}