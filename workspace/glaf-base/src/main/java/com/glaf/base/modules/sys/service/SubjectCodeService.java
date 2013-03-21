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

import com.glaf.base.modules.sys.model.SubjectCode;
import com.glaf.core.util.PageResult;

@Transactional(readOnly = true)
public interface SubjectCodeService {

	/**
	 * 保存
	 * 
	 * @param bean
	 *            SubjectCode
	 * @return boolean
	 */
	@Transactional
	boolean create(SubjectCode bean);

	/**
	 * 更新
	 * 
	 * @param bean
	 *            SubjectCode
	 * @return boolean
	 */
	@Transactional
	boolean update(SubjectCode bean);

	/**
	 * 删除
	 * 
	 * @param bean
	 *            SubjectCode
	 * @return boolean
	 */
	@Transactional
	boolean delete(SubjectCode bean);

	/**
	 * 删除
	 * 
	 * @param id
	 *            long
	 * @return boolean
	 */
	@Transactional
	boolean delete(long id);

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
	SubjectCode findById(long id);

	/**
	 * 按名称查找对象
	 * 
	 * @param code
	 *            String
	 * @return SubjectCode
	 */
	SubjectCode findByCode(String code);

	/**
	 * 获取所有列表
	 * 
	 * @return
	 */
	List<SubjectCode> getSubjectCodeList();

	/**
	 * 获取满足条件的列表
	 * 
	 * @param parent
	 *            long
	 * @return List
	 */
	List<SubjectCode> getSysSubjectCodeList(long parent);

	/**
	 * 获取费用分页列表
	 * 
	 * @param filter
	 * @return
	 */
	PageResult getFeePage(Map<String, String> filter);

	/**
	 * 获取下级列表
	 * 
	 * @param filter
	 * @return
	 */
	List<SubjectCode> getSubFeeList(Map<String, String> filter);

}