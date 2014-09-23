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

	private static class MxFormContainerHolder {
		private static final MxFormContainer INSTANCE = new MxFormContainer();
	}

	public static final MxFormContainer getContainer() {
		return MxFormContainerHolder.INSTANCE;
	}

	private FormDataService formDataService;

	private MxFormContainer() {
		formDataService = ContextFactory.getBean("formDataService");
	}

	/**
	 * 删除表单实例数据
	 * 
	 */
	public void deleteDataModel(String appId, Collection<String> businessKeys) {
		formDataService.deleteDataModel(appId, businessKeys);
	}

	/**
	 * 根据表单实例编号获取表单实例数据
	 * 
	 * @param appId
	 * @param id
	 * @return
	 */
	public DataModel getDataModel(String appId, Long id) {
		return formDataService.getDataModel(appId, id);
	}

	/**
	 * 根据表单实例编号获取表单实例数据
	 * 
	 * @param appId
	 * @param id
	 * @return
	 */
	public DataModel getDataModelByAppName(String appId, Long id) {
		FormApplication formApplication = formDataService
				.getFormApplication(appId);
		return formDataService.getDataModel(formApplication.getId(), id);
	}

	/**
	 * 根据表单实例编号获取表单实例数据
	 * 
	 * @param appId
	 * @param businessKey
	 * @return
	 */
	public DataModel getDataModelByBusinessKey(String appId, String businessKey) {
		return formDataService.getDataModelByBusinessKey(appId, businessKey);
	}

	/**
	 * 根据参数获取实例数据列表
	 * 
	 * @param appId
	 *            应用编号
	 * @param query
	 *            查询上下文
	 * @return
	 */
	public Paging getPageDataModel(String appId, DataModelQuery query) {
		return formDataService.getPageDataModel(appId, query);
	}

	/**
	 * 保存表单实例数据<br>
	 * 主要操作：根据表单定义和从客户端获取的用户输入数据进行数据组装，形成表单数据实例，根据表单实例信息保存表单数据。<br>
	 * 在处理数据过程中为了避免数据处理程序的错误引起数据丢失的情况，需要将从客户端输入的信息保存到文件系统中以便日后使用。
	 * 
	 * @param appId
	 *            应用编号
	 * @param formContext
	 */
	public void saveDataModel(String appId, FormContext formContext) {
		formDataService.saveDataModel(appId, formContext);
	}

	/**
	 * 保存表单实例数据<br>
	 * 主要操作：根据表单定义和从客户端获取的用户输入数据进行数据组装，形成表单数据实例，根据表单实例信息保存表单数据。<br>
	 * 在处理数据过程中为了避免数据处理程序的错误引起数据丢失的情况，需要将从客户端输入的信息保存到文件系统中以便日后使用。
	 * 
	 * @param appId
	 *            应用编号
	 * @param formContext
	 */
	public void updateDataModel(String appId, FormContext formContext) {
		formDataService.updateDataModel(appId, formContext);
	}

}