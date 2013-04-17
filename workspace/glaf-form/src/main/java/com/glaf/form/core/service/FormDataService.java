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

import java.util.Collection;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.base.DataModel;
import com.glaf.core.query.DataModelQuery;
import com.glaf.core.util.Paging;

import com.glaf.form.core.context.FormContext;
import com.glaf.form.core.domain.FormApplication;
import com.glaf.form.core.domain.FormDefinition;
import com.glaf.form.core.query.FormApplicationQuery;
import com.glaf.form.core.query.FormDefinitionQuery;

@Transactional(readOnly = true)
public interface FormDataService {

	/**
	 * ɾ���Ѿ������ı�����
	 * 
	 * @param formName
	 */
	@Transactional
	void deleteAllFormDefinitions(String formName);

	/**
	 * ɾ����ʵ������
	 * 
	 * @param appId
	 *            Ӧ�ñ��
	 * @param businessKeys
	 */
	@Transactional
	void deleteDataModel(String appId, Collection<String> businessKeys);

	/**
	 * ɾ����Ӧ��ʵ��
	 * 
	 * @param appId
	 *            Ӧ�ñ��
	 */
	@Transactional
	void deleteFormApplication(String appId);

	/**
	 * ɾ���Ѿ������ı�����
	 * 
	 * @param formName
	 */
	@Transactional
	void deleteFormDefinitionByName(String formName);

	/**
	 * ���ݱ�ʵ����Ż�ȡ��ʵ������
	 * 
	 * @param appId
	 *            Ӧ�ñ��
	 * @param id
	 * @return
	 */
	DataModel getDataModel(String appId, Long id);

	/**
	 * ���ݱ�ҵ���Ż�ȡ��ʵ������
	 * 
	 * @param appId
	 *            Ӧ�ñ��
	 * @param businessKey
	 * @return
	 */
	DataModel getDataModelByBusinessKey(String appId, String businessKey);

	/**
	 * ���ݱ�ʵ����Ż�ȡ��ʵ������
	 * 
	 * @param app_name
	 *            Ӧ������
	 * @param id
	 * @return
	 */
	DataModel getDataModelByAppName(String app_name, Long id);

	/**
	 * ���ݱ�Ż�ȡӦ�ö�����Ϣ
	 * 
	 * @param appId
	 *            Ӧ�ñ��
	 * @return
	 */
	FormApplication getFormApplication(String appId);

	/**
	 * �������ƻ�ȡӦ�ö�����Ϣ
	 * 
	 * @param name
	 *            Ӧ������
	 * @return
	 */
	FormApplication getFormApplicationByName(String name);

	/**
	 * ���ݱ����ƻ�ȡ��������Ϣ
	 * 
	 * @param formDefinitionId
	 *            ��������
	 * @return
	 */
	FormDefinition getFormDefinition(String formDefinitionId);

	/**
	 * ���ݱ����ƻ�ȡ��������Ϣ
	 * 
	 * @param name
	 *            ������
	 * @return
	 */
	FormDefinition getLatestFormDefinition(String name);

 

	/**
	 * ��ȡһҳ��Ӧ����Ϣ
	 * 
	 * @param query
	 * @return
	 */
	Paging getPageApplication(FormApplicationQuery query);

	/**
	 * ���ݲ�����ȡʵ�������б�
	 * 
	 * @param appId
	 *            Ӧ�ñ��
	 * @param query
	 *            ��ѯ������
	 * @return
	 */
	Paging getPageDataModel(String appId, DataModelQuery query);

	/**
	 * ��ȡһҳ������
	 * @param query
	 * @return
	 */
	Paging getPageFormDefinition(FormDefinitionQuery query);

	/**
	 * �����ʵ������<br>
	 * ��Ҫ���������ݱ�����ʹӿͻ��˻�ȡ���û��������ݽ���������װ���γɱ�����ʵ�������ݱ�ʵ����Ϣ��������ݡ�<br>
	 * �ڴ������ݹ�����Ϊ�˱������ݴ������Ĵ����������ݶ�ʧ���������Ҫ���ӿͻ����������Ϣ���浽�ļ�ϵͳ���Ա��պ�ʹ�á�
	 * @param appId Ӧ�ñ��
	 * @param formContext
	 */
	@Transactional
	void saveDataModel(String appId, FormContext formContext);

	/**
	 * �����Ӧ����Ϣ
	 * 
	 * @param formApplication
	 */
	@Transactional
	void saveFormApplication(FormApplication formApplication);

	/**
	 * ��������Ӧ����Ϣ
	 *  
	 */
	@Transactional
	void saveFormApplications(List<FormApplication> apps);

	/**
	 * �����������Ϣ <br>
	 * ��Ҫ���������������Ļ������ԣ��ڵ���Ϣ�����±������ݿ�ṹ
	 * 
	 * @param formDefinition ��������Ϣ
	 */
	@Transactional
	void saveFormDefinition(FormDefinition formDefinition);

	/**
	 * �����ʵ������<br>
	 * ��Ҫ���������ݱ�����ʹӿͻ��˻�ȡ���û��������ݽ���������װ���γɱ�����ʵ�������ݱ�ʵ����Ϣ��������ݡ�<br>
	 * �ڴ������ݹ�����Ϊ�˱������ݴ������Ĵ����������ݶ�ʧ���������Ҫ���ӿͻ����������Ϣ���浽�ļ�ϵͳ���Ա��պ�ʹ�á�
	 * @param appId Ӧ�ñ��
	 * @param formContext
	 */
	@Transactional
	void updateDataModel(String appId, FormContext formContext);

}