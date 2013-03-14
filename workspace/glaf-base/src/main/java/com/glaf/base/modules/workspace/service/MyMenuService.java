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
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.workspace.model.MyMenu;
import com.glaf.core.util.PageResult;

@Transactional(readOnly = true)
public interface MyMenuService {

	/**
	 * ����������Ϣ
	 * 
	 * @param myMenu
	 * @return
	 */
	@Transactional
	boolean create(MyMenu bean);

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
	 * @param myMenu
	 * @return
	 */
	@Transactional
	boolean delete(MyMenu myMenu);

	/**
	 * ����ɾ��
	 * 
	 * @param c
	 * @return
	 */
	@Transactional
	boolean deleteAll(Collection c);

	/**
	 * ��ȡ����
	 * 
	 * @param id
	 * @return
	 */
	MyMenu find(long id);

	/**
	 * ��ȡ�б�(�����кš��������)
	 * 
	 * @param userId
	 * @return
	 */
	List getMyMenuList(long userId);

	/**
	 * ��ȡ�б�(�����кš��������)
	 * 
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageResult getMyMenuList(long userId, int pageNo, int pageSize);

	/**
	 * ����
	 * 
	 * @param bean
	 *            MyMenu
	 * @param operate
	 *            int ����
	 */
	@Transactional
	void sort(MyMenu bean, int operate);

	/**
	 * ������Ϣ
	 * 
	 * @param myMenu
	 * @return
	 */
	@Transactional
	boolean update(MyMenu myMenu);

}