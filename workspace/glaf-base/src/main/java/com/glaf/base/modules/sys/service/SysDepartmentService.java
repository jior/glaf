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

import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.utils.PageResult;

public interface SysDepartmentService {

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysDepartment
	 * @return boolean
	 */
	boolean create(SysDepartment bean);

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysDepartment
	 * @return boolean
	 */
	boolean update(SysDepartment bean);

	/**
	 * ɾ��
	 * 
	 * @param bean
	 *            SysDepartment
	 * @return boolean
	 */
	boolean delete(SysDepartment bean);

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
	SysDepartment findById(long id);

	/**
	 * ��������Ҷ���
	 * 
	 * @param code
	 * 
	 * @return SysDepartment
	 */
	SysDepartment findByCode(String code);

	/**
	 * �����ű�Ų��Ҷ���
	 * 
	 * @param deptno
	 * 
	 * @return SysDepartment
	 */

	SysDepartment findByNo(String deptno);

	/**
	 * �����Ʋ��Ҷ���
	 * 
	 * @param name
	 *            String
	 * @return SysDepartment
	 */
	SysDepartment findByName(String name);

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
	PageResult getSysDepartmentList(int parent, int pageNo, int pageSize);

	/**
	 * ��ȡ�б�
	 * 
	 * @return List
	 */
	List<SysDepartment> getSysDepartmentList();

	/**
	 * ��ȡ�б�
	 * 
	 * @param parent
	 *            int
	 * @return List
	 */
	List<SysDepartment> getSysDepartmentList(int parent);

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysDepartment
	 * @param operate
	 *            int ����
	 */
	void sort(long parent, SysDepartment bean, int operate);

	/**
	 * ��ȡĳ�����ż��¼������б�
	 * 
	 * @param list
	 * @param deptId
	 */
	void findNestingDepartment(List<SysDepartment> list, long deptId);

	/**
	 * ��ȡĳ�����ż��¼������б�
	 * 
	 * @param list
	 * @param node
	 */
	void findNestingDepartment(List<SysDepartment> list, SysDepartment node);
}