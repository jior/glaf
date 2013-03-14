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

package com.glaf.base.modules.others.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.others.model.Attachment;

@Transactional(readOnly = true)
public interface AttachmentService {

	/**
	 * 保存
	 * 
	 * @param bean
	 *            Attachment
	 * @return boolean
	 */
	@Transactional
	boolean create(Attachment bean);

	/**
	 * 更新
	 * 
	 * @param bean
	 *            Attachment
	 * @return boolean
	 */
	@Transactional
	boolean update(Attachment bean);

	/**
	 * 删除
	 * 
	 * @param bean
	 *            Attachment
	 * @return boolean
	 */
	@Transactional
	boolean delete(Attachment bean);

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
	Attachment find(long id);

	/**
	 * 返回所有附件列表
	 * 
	 * @param parent
	 * @return
	 */
	List getAttachmentList(long referId, int referType);

	/**
	 * 返回附件
	 * 
	 * @param referId
	 * @param referType
	 * @return Attachment
	 */
	Attachment find(long referId, int referType);

	/**
	 * 返回附件
	 * 
	 * @param referId
	 * @param referType
	 * @return Attachment
	 */
	Attachment find(long id, long referId, int referType);

	/**
	 * 返回附件
	 * 
	 * @param referId
	 * @param referType
	 * @param name
	 * @return Attachment
	 */
	Attachment find(long referId, int referType, String name);

	/**
	 * 返回所有附件名称列表
	 * 
	 * @param parent
	 * @return
	 */
	Map getNameMap(long referId, int referType);

	/**
	 * 返回所有附件列表
	 * 
	 * @param referId
	 * @param referType
	 * @return
	 */
	List getAttachmentList(long[] referIds, int referType);

	/**
	 * 返回附件个数
	 * 
	 * @param referId
	 * @param referType
	 * @return
	 */
	int getAttachmentCount(long[] referIds, int referType);

	/**
	 * 返回对应的附件列表
	 * 
	 * @param parent
	 * @return
	 */
	List getPurchaseAttachmentList(long[] ids, int referType);
}