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
package com.glaf.form.core.container;

import java.util.Collection;

import com.glaf.core.base.DataModel;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.query.DataModelQuery;
import com.glaf.core.util.Paging;
import com.glaf.form.core.context.FormContext;
import com.glaf.form.core.domain.FormApplication;
import com.glaf.form.core.service.FormDataService;

public class MxFormContainer {

	private static MxFormContainer container = new MxFormContainer();

	public static MxFormContainer getContainer() {
		return container;
	}

	private FormDataService formDataService;

	private MxFormContainer() {
		formDataService = ContextFactory.getBean("formDataService");
	}

	/**
	 * ɾ����ʵ������
	 * 
	 */
	public void deleteDataModel(String appId, Collection<String> businessKeys) {
		formDataService.deleteDataModel(appId, businessKeys);
	}

	/**
	 * ���ݱ�ʵ����Ż�ȡ��ʵ������
	 * 
	 * @param appId
	 * @param id
	 * @return
	 */
	public DataModel getDataModel(String appId, Long id) {
		return formDataService.getDataModel(appId, id);
	}

	/**
	 * ���ݱ�ʵ����Ż�ȡ��ʵ������
	 * 
	 * @param app_name
	 * @param id
	 * @return
	 */
	public DataModel getDataModelByAppName(String app_name, Long id) {
		FormApplication formApplication = formDataService
				.getFormApplicationByName(app_name);
		return formDataService.getDataModel(formApplication.getId(), id);
	}

	/**
	 * ���ݱ�ʵ����Ż�ȡ��ʵ������
	 * 
	 * @param appId
	 * @param businessKey
	 * @return
	 */
	public DataModel getDataModelByBusinessKey(String appId, String businessKey) {
		return formDataService.getDataModelByBusinessKey(appId, businessKey);
	}

	/**
	 * ���ݲ�����ȡʵ�������б�
	 * 
	 * @param appId
	 *            Ӧ�ñ��
	 * @param query
	 *            ��ѯ������
	 * @return
	 */
	public Paging getPageDataModel(String appId, DataModelQuery query) {
		return formDataService.getPageDataModel(appId, query);
	}

	/**
	 * �����ʵ������<br>
	 * ��Ҫ���������ݱ�����ʹӿͻ��˻�ȡ���û��������ݽ���������װ���γɱ�����ʵ�������ݱ�ʵ����Ϣ��������ݡ�<br>
	 * �ڴ������ݹ�����Ϊ�˱������ݴ������Ĵ����������ݶ�ʧ���������Ҫ���ӿͻ����������Ϣ���浽�ļ�ϵͳ���Ա��պ�ʹ�á�
	 * 
	 * @param appId
	 *            Ӧ�ñ��
	 * @param formContext
	 */
	public void saveDataModel(String appId, FormContext formContext) {
		formDataService.saveDataModel(appId, formContext);
	}

	/**
	 * �����ʵ������<br>
	 * ��Ҫ���������ݱ�����ʹӿͻ��˻�ȡ���û��������ݽ���������װ���γɱ�����ʵ�������ݱ�ʵ����Ϣ��������ݡ�<br>
	 * �ڴ������ݹ�����Ϊ�˱������ݴ������Ĵ����������ݶ�ʧ���������Ҫ���ӿͻ����������Ϣ���浽�ļ�ϵͳ���Ա��պ�ʹ�á�
	 * 
	 * @param appId
	 *            Ӧ�ñ��
	 * @param formContext
	 */
	public void updateDataModel(String appId, FormContext formContext) {
		formDataService.updateDataModel(appId, formContext);
	}

}