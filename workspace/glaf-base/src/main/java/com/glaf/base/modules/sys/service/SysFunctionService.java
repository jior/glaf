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

import com.glaf.base.modules.sys.model.SysFunction;

public interface SysFunctionService {

	/**
	 * 保存
	 * 
	 * @param bean
	 *            SysFunction
	 * @return boolean
	 */
	boolean create(SysFunction bean);

	/**
	 * 更新
	 * 
	 * @param bean
	 *            SysFunction
	 * @return boolean
	 */
	boolean update(SysFunction bean);

	/**
	 * 删除
	 * 
	 * @param bean
	 *            SysFunction
	 * @return boolean
	 */
	boolean delete(SysFunction bean);

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
	SysFunction findById(long id);

	/**
	 * 获取列表
	 * 
	 * @param appId
	 *            int
	 * @return List
	 */
	List<SysFunction> getSysFunctionList(int appId);

	/**
	 * 获取全部列表
	 * 
	 * @return List
	 */
	List<SysFunction> getSysFunctionList();

	/**
	 * 排序
	 * 
	 * @param bean
	 *            SysFunction
	 * @param operate
	 *            int 操作
	 */
	void sort(SysFunction bean, int operate);

}