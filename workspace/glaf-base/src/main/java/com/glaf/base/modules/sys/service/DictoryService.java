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
	 * ����
	 * 
	 * @param bean
	 *            Dictory
	 * @return boolean
	 */
	boolean create(Dictory bean);

	/**
	 * ɾ��
	 * 
	 * @param bean
	 *            Dictory
	 * @return boolean
	 */
	boolean delete(Dictory bean);

	/**
	 * ɾ��
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	boolean delete(long id);

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
	Dictory find(long id);

	/**
	 * ����ĳ�����µ������ֵ��б�
	 * 
	 * @param parent
	 * @return
	 */
	List<Dictory> getAvailableDictoryList(long parent);

	/**
	 * ����ID��code
	 * 
	 * @param id
	 * @return
	 */
	String getCodeById(long id);

	/**
	 * ��ȡ��ҳ�б�
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageResult getDictoryList(int pageNo, int pageSize);

	/**
	 * ����ĳ�����µ������ֵ��б�
	 * 
	 * @param parent
	 * @return
	 */
	List<Dictory> getDictoryList(long parent);

	/**
	 * �����ͺ������б�
	 * 
	 * @param parent
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageResult getDictoryList(long parent, int pageNo, int pageSize);

	/**
	 * ��Ҫ���ڻ�ú�����Ŀ�ļ�ֵ��
	 * 
	 * @param list
	 * @param purchaseId
	 * @return
	 */
	Map<String, String> getDictoryMap(List<Dictory> list, long purchaseId);

	/**
	 * ����
	 * 
	 * @param bean
	 *            Dictory
	 * @param operate
	 *            int ����
	 */
	void sort(long parent, Dictory bean, int operate);

	/**
	 * ����
	 * 
	 * @param bean
	 *            Dictory
	 * @return boolean
	 */
	boolean update(Dictory bean);
}