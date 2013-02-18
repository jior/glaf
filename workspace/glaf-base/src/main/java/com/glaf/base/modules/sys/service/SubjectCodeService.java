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

import com.glaf.base.modules.sys.model.SubjectCode;
import com.glaf.base.utils.PageResult;

public interface SubjectCodeService {

	/**
	 * ����
	 * 
	 * @param bean
	 *            SubjectCode
	 * @return boolean
	 */
	boolean create(SubjectCode bean);

	/**
	 * ����
	 * 
	 * @param bean
	 *            SubjectCode
	 * @return boolean
	 */
	boolean update(SubjectCode bean);

	/**
	 * ɾ��
	 * 
	 * @param bean
	 *            SubjectCode
	 * @return boolean
	 */
	boolean delete(SubjectCode bean);

	/**
	 * ɾ��
	 * 
	 * @param id
	 *            long
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
	SubjectCode findById(long id);

	/**
	 * �����Ʋ��Ҷ���
	 * 
	 * @param code
	 *            String
	 * @return SubjectCode
	 */
	SubjectCode findByCode(String code);

	/**
	 * ��ȡ�����б�
	 * 
	 * @return
	 */
	List<SubjectCode> getSubjectCodeList();

	/**
	 * ��ȡ�����������б�
	 * 
	 * @param parent
	 *            long
	 * @return List
	 */
	List<SubjectCode> getSysSubjectCodeList(long parent);

	/**
	 * ��ȡ���÷�ҳ�б�
	 * 
	 * @param filter
	 * @return
	 */
	PageResult getFeePage(Map<String, Object> filter);

	/**
	 * ��ȡ�¼��б�
	 * 
	 * @param filter
	 * @return
	 */
	List<SubjectCode> getSubFeeList(Map<String, Object> filter);

}