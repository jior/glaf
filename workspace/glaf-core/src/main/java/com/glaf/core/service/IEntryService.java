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
	 * ɾ����¼
	 * 
	 * @param rowId
	 */

	void deleteEntityEntry(String rowId);

	/**
	 * ����������ȡʵ�������
	 * 
	 * @param rowId
	 * @return
	 */
	EntityEntry getEntityEntry(String rowId);

	/**
	 * ���ݽڵ��ż�����Key��ȡʵ�������
	 * 
	 * @param nodeId
	 * @param entryKey
	 * @return
	 */
	EntityEntry getEntityEntry(long nodeId, String entryKey);

	/**
	 * ���ݲ�����ȡʵ�������
	 * 
	 * @param moduleId
	 *            ģ����
	 * @param entityId
	 *            ʵ����
	 * @param entryKey
	 *            ʵ��Key
	 * @return
	 */
	EntityEntry getEntityEntry(String moduleId, String entityId, String entryKey);

	/**
	 * ��ȡĳ���û��ܷ��ʵļ�¼����
	 * 
	 * 
	 * @param loginContext
	 *            �û�������
	 * @param moduleId
	 *            ģ���ʶ
	 * @param entryKey
	 *            Ȩ��
	 * @return
	 */
	List<String> getEntityIds(LoginContext loginContext, String moduleId,
			String entryKey);

	/**
	 * ��ȡĳ���û�ĳ��ģ��ķ���Ȩ��
	 * 
	 * @param loginContext
	 * @param moduleId
	 * @return
	 */
	List<String> getEntryKeys(LoginContext loginContext, String moduleId);

	/**
	 * ��ȡĳ���û��ܷ��ʵĽڵ㼯��
	 * 
	 * @param loginContext
	 *            �û�������
	 * @return
	 */
	List<TreeModel> getTreeModels(LoginContext loginContext);

	/**
	 * ���ĳ���û��Ƿ���ĳ�������ĳ��Ȩ��
	 * 
	 * @param loginContext
	 *            �û�������
	 * @param nodeId
	 *            ������
	 * @param permKey
	 *            Ȩ�޵�
	 * @return
	 */
	boolean hasPermission(LoginContext loginContext, long nodeId, String permKey);

	/**
	 * ���ĳ���û��Ƿ���ĳ����¼��ĳ��Ȩ��
	 * 
	 * @param loginContext
	 *            �û�������
	 * @param moduleId
	 *            ģ���ʶ
	 * @param entityId
	 *            ��¼���
	 * 
	 * @param permKey
	 *            Ȩ�޵�
	 * @return
	 */
	boolean hasPermission(LoginContext loginContext, String moduleId,
			String entityId, String permKey);

	/**
	 * �����¼
	 * 
	 * @param entityEntry
	 */
	void saveEntityEntry(EntityEntry entityEntry);

}