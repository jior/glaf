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
	 * 删除已经废弃的表单定义
	 * 
	 * @param formName
	 */
	@Transactional
	void deleteAllFormDefinitions(String formName);

	/**
	 * 删除表单实例数据
	 * 
	 * @param appId
	 *            应用编号
	 * @param businessKeys
	 */
	@Transactional
	void deleteDataModel(String appId, Collection<String> businessKeys);

	/**
	 * 删除表单应用实例
	 * 
	 * @param appId
	 *            应用编号
	 */
	@Transactional
	void deleteFormApplication(String appId);

	/**
	 * 删除已经废弃的表单定义
	 * 
	 * @param formName
	 */
	@Transactional
	void deleteFormDefinitionByName(String formName);

	/**
	 * 根据表单实例编号获取表单实例数据
	 * 
	 * @param appId
	 *            应用编号
	 * @param id
	 * @return
	 */
	DataModel getDataModel(String appId, Long id);

	/**
	 * 根据表单实例编号获取表单实例数据
	 * 
	 * @param app_name
	 *            应用名称
	 * @param id
	 * @return
	 */
	DataModel getDataModelByAppName(String app_name, Long id);

	/**
	 * 根据表单业务编号获取表单实例数据
	 * 
	 * @param appId
	 *            应用编号
	 * @param businessKey
	 * @return
	 */
	DataModel getDataModelByBusinessKey(String appId, String businessKey);

	/**
	 * 根据编号获取应用定义信息
	 * 
	 * @param appId
	 *            应用编号
	 * @return
	 */
	FormApplication getFormApplication(String appId);

	/**
	 * 根据名称获取应用定义信息
	 * 
	 * @param name
	 *            应用名称
	 * @return
	 */
	FormApplication getFormApplicationByName(String name);

	/**
	 * 根据表单名称获取表单定义信息
	 * 
	 * @param formDefinitionId
	 *            表单定义编号
	 * @return
	 */
	FormDefinition getFormDefinition(String formDefinitionId);

	/**
	 * 根据表单名称获取表单定义信息
	 * 
	 * @param name
	 *            表单名称
	 * @return
	 */
	FormDefinition getLatestFormDefinition(String name);

	/**
	 * 根据条件获取最新版本的表单定义信息
	 * 
	 * @param query
	 * @return
	 */
	List<FormDefinition> getLatestFormDefinitions(FormDefinitionQuery query);

	/**
	 * 获取一页表单应用信息
	 * 
	 * @param query
	 * @return
	 */
	Paging getPageApplication(FormApplicationQuery query);

	/**
	 * 根据参数获取实例数据列表
	 * 
	 * @param appId
	 *            应用编号
	 * @param query
	 *            查询上下文
	 * @return
	 */
	Paging getPageDataModel(String appId, DataModelQuery query);

	/**
	 * 获取一页表单对象
	 * 
	 * @param query
	 * @return
	 */
	Paging getPageFormDefinition(FormDefinitionQuery query);

	/**
	 * 保存表单实例数据<br>
	 * 主要操作：根据表单定义和从客户端获取的用户输入数据进行数据组装，形成表单数据实例，根据表单实例信息保存表单数据。<br>
	 * 在处理数据过程中为了避免数据处理程序的错误引起数据丢失的情况，需要将从客户端输入的信息保存到文件系统中以便日后使用。
	 * 
	 * @param appId
	 *            应用编号
	 * @param formContext
	 */
	@Transactional
	void saveDataModel(String appId, FormContext formContext);

	/**
	 * 保存表单应用信息
	 * 
	 * @param formApplication
	 */
	@Transactional
	void saveFormApplication(FormApplication formApplication);

	/**
	 * 保存多个表单应用信息
	 * 
	 */
	@Transactional
	void saveFormApplications(List<FormApplication> apps);

	/**
	 * 保存表单定义信息 <br>
	 * 主要操作：保存表单定义的基本属性，节点信息及更新表单的数据库结构
	 * 
	 * @param formDefinition
	 *            表单定义信息
	 */
	@Transactional
	void saveFormDefinition(FormDefinition formDefinition);

	/**
	 * 保存表单实例数据<br>
	 * 主要操作：根据表单定义和从客户端获取的用户输入数据进行数据组装，形成表单数据实例，根据表单实例信息保存表单数据。<br>
	 * 在处理数据过程中为了避免数据处理程序的错误引起数据丢失的情况，需要将从客户端输入的信息保存到文件系统中以便日后使用。
	 * 
	 * @param appId
	 *            应用编号
	 * @param formContext
	 */
	@Transactional
	void updateDataModel(String appId, FormContext formContext);

}