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

import com.glaf.base.modules.sys.model.Dictory;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.query.DictoryQuery;
import com.glaf.core.util.PageResult;

@Transactional(readOnly = true)
public interface DictoryService {

	/**
	 * ����
	 * 
	 * @param bean
	 *            Dictory
	 * @return boolean
	 */
	@Transactional
	boolean create(Dictory bean);

	/**
	 * ɾ��
	 * 
	 * @param bean
	 *            Dictory
	 * @return boolean
	 */
	@Transactional
	boolean delete(Dictory bean);

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
	Dictory find(long id);

	/**
	 * ��ȡȫ���������ݵķ�����
	 * 
	 * @return
	 */
	List<SysTree> getAllCategories();

	/**
	 * ����ĳ�����µ������ֵ��б�
	 * 
	 * @param parent
	 * @return
	 */
	List<Dictory> getAvailableDictoryList(long parent);
	
	/**
	 * ����ĳ�����µ������ֵ��б�
	 * 
	 * @param nodeCode
	 * @return
	 */
	List<Dictory> getDictoryList(String nodeCode);

	/**
	 * ����ID��code
	 * 
	 * @param id
	 * @return
	 */
	String getCodeById(long id);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getDictoryCountByQueryCriteria(DictoryQuery query);

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
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<Dictory> getDictorysByQueryCriteria(int start, int pageSize,
			DictoryQuery query);

	/**
	 * ����
	 * 
	 * @param bean
	 *            Dictory
	 * @param operate
	 *            int ����
	 */
	@Transactional
	void sort(long parent, Dictory bean, int operate);

	/**
	 * ����
	 * 
	 * @param bean
	 *            Dictory
	 * @return boolean
	 */
	@Transactional
	boolean update(Dictory bean);
}