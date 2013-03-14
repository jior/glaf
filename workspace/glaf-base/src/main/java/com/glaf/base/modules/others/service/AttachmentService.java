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
	 * ����
	 * 
	 * @param bean
	 *            Attachment
	 * @return boolean
	 */
	@Transactional
	boolean create(Attachment bean);

	/**
	 * ����
	 * 
	 * @param bean
	 *            Attachment
	 * @return boolean
	 */
	@Transactional
	boolean update(Attachment bean);

	/**
	 * ɾ��
	 * 
	 * @param bean
	 *            Attachment
	 * @return boolean
	 */
	@Transactional
	boolean delete(Attachment bean);

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
	Attachment find(long id);

	/**
	 * �������и����б�
	 * 
	 * @param parent
	 * @return
	 */
	List getAttachmentList(long referId, int referType);

	/**
	 * ���ظ���
	 * 
	 * @param referId
	 * @param referType
	 * @return Attachment
	 */
	Attachment find(long referId, int referType);

	/**
	 * ���ظ���
	 * 
	 * @param referId
	 * @param referType
	 * @return Attachment
	 */
	Attachment find(long id, long referId, int referType);

	/**
	 * ���ظ���
	 * 
	 * @param referId
	 * @param referType
	 * @param name
	 * @return Attachment
	 */
	Attachment find(long referId, int referType, String name);

	/**
	 * �������и��������б�
	 * 
	 * @param parent
	 * @return
	 */
	Map getNameMap(long referId, int referType);

	/**
	 * �������и����б�
	 * 
	 * @param referId
	 * @param referType
	 * @return
	 */
	List getAttachmentList(long[] referIds, int referType);

	/**
	 * ���ظ�������
	 * 
	 * @param referId
	 * @param referType
	 * @return
	 */
	int getAttachmentCount(long[] referIds, int referType);

	/**
	 * ���ض�Ӧ�ĸ����б�
	 * 
	 * @param parent
	 * @return
	 */
	List getPurchaseAttachmentList(long[] ids, int referType);
}