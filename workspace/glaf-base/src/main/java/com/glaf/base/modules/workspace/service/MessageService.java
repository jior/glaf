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

package com.glaf.base.modules.workspace.service;

import java.util.Collection;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.workspace.model.Message;
import com.glaf.core.util.PageResult;

@SuppressWarnings("rawtypes")
@Transactional(readOnly = true)
public interface MessageService {

	/**
	 * ����������Ϣ
	 * 
	 * @param bean
	 * @return
	 */
	@Transactional
	boolean create(Message bean);

	/**
	 * ����������Ϣ
	 * 
	 * @param bean
	 * @return
	 */
	@Transactional
	boolean saveOrUpdate(Message bean);

	/**
	 * ������Ϣ
	 * 
	 * @param bean
	 * @return
	 */
	@Transactional
	boolean update(Message bean);

	/**
	 * ����ɾ��
	 * 
	 * @param bean
	 * @return
	 */
	@Transactional
	boolean delete(Message bean);

	/**
	 * ����ɾ��
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
	 * @param c
	 * @return
	 */
	@Transactional
	boolean deleteAll(Collection<Object> c);

	/**
	 * ��ȡ����
	 * 
	 * @param id
	 * @return
	 */
	Message find(long id);

	/**
	 * ������Ϣ(���û�)
	 * 
	 * @param message
	 * @param recverIdStr
	 * @return
	 */
	@Transactional
	boolean saveSendMessage(Message message, String[] recverIds);

	/**
	 * ������Ϣ(�����ŵ��û�)
	 * 
	 * @param message
	 * @param recverIds
	 * @return
	 */
	@Transactional
	boolean saveSendMessageToDept(Message message, String[] recverIds);

	/**
	 * �Ķ���Ϣ
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	Message updateReadMessage(long id);

	/**
	 * ��ȡ�б�
	 * 
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageResult getMessageList(Map params, int pageNo, int pageSize);

	/**
	 * ��ȡ�ռ����б�
	 * 
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageResult getReceiveList(long userId, Map params, int pageNo, int pageSize);

	/**
	 * ��ȡδ���ռ����б�
	 * 
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageResult getNoReadList(long userId, Map params, int pageNo, int pageSize);

	/**
	 * ��ȡ�������б�
	 * 
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageResult getSendedList(long userId, Map params, int pageNo, int pageSize);
}