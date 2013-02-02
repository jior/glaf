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

import com.glaf.base.modules.sys.model.Dictory;
import com.glaf.base.utils.PageResult;

public interface DictoryService {

	/**
	 * 保存
	 * 
	 * @param bean
	 *            Dictory
	 * @return boolean
	 */
	boolean create(Dictory bean);

	/**
	 * 删除
	 * 
	 * @param bean
	 *            Dictory
	 * @return boolean
	 */
	boolean delete(Dictory bean);

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
	Dictory find(long id);

	/**
	 * 返回某分类下的所有字典列表
	 * 
	 * @param parent
	 * @return
	 */
	List<Dictory> getAvailableDictoryList(long parent);

	/**
	 * 根据ID拿code
	 * 
	 * @param id
	 * @return
	 */
	String getCodeById(long id);

	/**
	 * 获取分页列表
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageResult getDictoryList(int pageNo, int pageSize);

	/**
	 * 返回某分类下的所有字典列表
	 * 
	 * @param parent
	 * @return
	 */
	List<Dictory> getDictoryList(long parent);

	/**
	 * 按类型号搜索列表
	 * 
	 * @param parent
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageResult getDictoryList(long parent, int pageNo, int pageSize);

	/**
	 * 主要用在获得核算项目的键值对
	 * 
	 * @param list
	 * @param purchaseId
	 * @return
	 */
	Map<String, String> getDictoryMap(List<Dictory> list, long purchaseId);

	/**
	 * 排序
	 * 
	 * @param bean
	 *            Dictory
	 * @param operate
	 *            int 操作
	 */
	void sort(long parent, Dictory bean, int operate);

	/**
	 * 更新
	 * 
	 * @param bean
	 *            Dictory
	 * @return boolean
	 */
	boolean update(Dictory bean);
}