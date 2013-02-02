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

import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.utils.PageResult;

public interface SysTreeService {

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysTree
	 * @return boolean
	 */
	boolean create(SysTree bean);

	/**
	 * ɾ��
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	boolean delete(long id);

	/**
	 * ɾ��
	 * 
	 * @param bean
	 *            SysTree
	 * @return boolean
	 */
	boolean delete(SysTree bean);

	/**
	 * ����ɾ��
	 * 
	 * @param id
	 * @return
	 */
	boolean deleteAll(long[] id);

	/**
	 * ��ȡ����
	 * 
	 * @param id
	 * @return
	 */
	SysTree findById(long id);

	/**
	 * �����Ʋ��Ҷ���
	 * 
	 * @param name
	 *            String
	 * @return SysTree
	 */
	SysTree findByName(String name);

	/**
	 * ��ȡ�����б�
	 * 
	 * @param parent
	 *            int
	 * @return List
	 */
	void getSysTree(List<SysTree> tree, int parent, int deep);

	/**
	 * ������Ż�ȡ���ڵ�
	 * 
	 * @param tree
	 * @return SysTree
	 */
	SysTree getSysTreeByCode(String code);

	/**
	 * ��ȡȫ���б�
	 * 
	 * @param parent
	 *            int
	 * @return List
	 */
	List<SysTree> getSysTreeList(int parent);

	/**
	 * ��ȡ��ҳ�б�
	 * 
	 * @param parent
	 *            int
	 * @param pageNo
	 *            int
	 * @param pageSize
	 *            int
	 * @return
	 */
	PageResult getSysTreeList(int parent, int pageNo, int pageSize);

	/**
	 * ��ȡȫ���б�
	 * 
	 * @param parent
	 *            int ��ID
	 * @param status
	 *            int ״̬
	 * @return List
	 */
	List<SysTree> getSysTreeListForDept(int parent, int status);

	/**
	 * ��ȡ���ڵ��б���:��Ŀ¼>A>A1>A11
	 * 
	 * @param tree
	 * @param int id
	 */
	void getSysTreeParent(List<SysTree> tree, long id);

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysTree
	 * @param operate
	 *            int ����
	 */
	void sort(long parent, SysTree bean, int operate);

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysTree
	 * @return boolean
	 */
	boolean update(SysTree bean);
}