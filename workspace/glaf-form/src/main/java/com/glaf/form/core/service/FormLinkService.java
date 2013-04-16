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
package com.glaf.form.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.query.DataModelQuery;
import com.glaf.core.util.Paging;

import com.glaf.form.core.domain.FormApplication;
import com.glaf.form.core.domain.FormDefinition;
import com.glaf.form.core.domain.FormLink;

@Transactional(readOnly = true)
public interface FormLinkService {

	/**
	 * ��ȡӦ�ù�������Ӧ�ü���
	 * 
	 * @param app_name
	 *            Ӧ������
	 * @param rows
	 */
	List<FormApplication> getChildrenApplications(String app_name);

	/**
	 * ��ȡӦ�ù���������
	 * 
	 * @param app_name
	 *            Ӧ������
	 * @param rows
	 */
	List<FormDefinition> getChildrenFormDefinitionReference(String app_name);

	/**
	 * ��ȡӦ�ù���������
	 * 
	 * @param app_name
	 *            Ӧ������
	 * @param rows
	 */
	List<FormDefinition> getFormDefinitions(String app_name);

	/**
	 * ��ȡӦ�ù�����
	 * 
	 * @param applicationName
	 *            Ӧ������
	 * @param childName
	 *            ��Ӧ������
	 * @param rows
	 */
	FormLink getFormLink(String applicationName, String childName);

	/**
	 * ��ȡӦ�ù�����
	 * 
	 * @param app_name
	 *            Ӧ������
	 * @param rows
	 */
	List<FormLink> getFormLinks(String app_name);

	/**
	 * ���ݲ�����ȡʵ�������б�
	 * 
	 * @param formLink
	 *            ������
	 * 
	 * @param query
	 *            ��ѯ������
	 * @return
	 */
	Paging getPageDataModel(FormLink formLink, DataModelQuery query);

	/**
	 * ����Ӧ�ù�����
	 * 
	 * @param app_name
	 *            Ӧ������
	 * @param rows
	 */
	@Transactional
	void saveAll(String app_name, List<FormLink> rows);

}