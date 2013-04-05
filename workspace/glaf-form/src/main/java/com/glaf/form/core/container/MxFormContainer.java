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

import com.glaf.core.base.BaseDataModel;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.query.DataModelQuery;
import com.glaf.core.util.Paging;
import com.glaf.form.core.context.FormContext;
import com.glaf.form.core.graph.def.FormApplication;
import com.glaf.form.core.graph.def.FormDefinition;
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
	 * 删除表单实例数据
	 * 
	 * @param formContext
	 */
	public void deleteDataModel(FormContext formContext,
			Collection<String> businessKeys) {
		FormDefinition formDefinition = formContext.getFormDefinition();
		formDataService.deleteDataModel(formDefinition.getName(), businessKeys);
	}

	/**
	 * 根据表单实例编号获取表单实例数据
	 * 
	 * @param formContext
	 * @param id
	 * @return
	 */
	public BaseDataModel getDataModel(FormContext formContext, String id) {
		return formDataService.getDataModel(formContext, id);
	}

	/**
	 * 根据表单实例编号获取表单实例数据
	 * 
	 * @param app_name
	 * @param id
	 * @return
	 */
	public BaseDataModel getDataModel(String app_name, String id) {
		FormApplication formApplication = formDataService
				.getFormApplicationByName(app_name);
		FormDefinition formDefinition = formDataService
				.getLatestFormDefinition(formApplication.getFormName());
		FormContext formContext = new FormContext();
		formContext.setFormApplication(formApplication);
		formContext.setFormDefinition(formDefinition);
		return formDataService.getDataModel(formContext, id);
	}

	/**
	 * 根据参数获取实例数据列表
	 * 
	 * @param formContext
	 *            表单上下文
	 * @param loginContext
	 *            用户上下文
	 * @param DataQuery
	 *            查询上下文
	 * @return
	 */
	public Paging getPageDataModel(FormContext formContext, DataModelQuery query) {
		return formDataService.getPageDataModel(formContext, query);
	}

	/**
	 * 保存表单实例数据<br>
	 * 主要操作：根据表单定义和从客户端获取的用户输入数据进行数据组装，形成表单数据实例，根据表单实例信息保存表单数据。<br>
	 * 在处理数据过程中为了避免数据处理程序的错误引起数据丢失的情况，需要将从客户端输入的信息保存到文件系统中以便日后使用。
	 * 
	 * @param formContext
	 */
	public void saveDataModel(FormContext formContext) {
		formDataService.saveDataModel(formContext);
	}

	/**
	 * 保存表单实例数据<br>
	 * 主要操作：根据表单定义和从客户端获取的用户输入数据进行数据组装，形成表单数据实例，根据表单实例信息保存表单数据。<br>
	 * 在处理数据过程中为了避免数据处理程序的错误引起数据丢失的情况，需要将从客户端输入的信息保存到文件系统中以便日后使用。
	 * 
	 * @param formContext
	 */
	public void updateDataModel(FormContext formContext) {
		formDataService.updateDataModel(formContext);
	}

}