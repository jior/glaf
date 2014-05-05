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

package com.glaf.template.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.template.query.TemplateQuery;
import com.glaf.template.Template;

@Transactional(readOnly = true)
public interface ITemplateService {

	/**
	 * 删除模板
	 * 
	 * @param templateId
	 */
	@Transactional
	void deleteTemplate(String templateId);

	/**
	 * 获取全部模板
	 * 
	 * @return
	 */
	Map<String, Template> getAllTemplate();

	/**
	 * 获取模板
	 * 
	 * @param templateId
	 * @return
	 */
	Template getTemplate(String templateId);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getTemplateCountByQueryCriteria(TemplateQuery query);

	/**
	 * 根据条件获取模板
	 * 
	 * @param query
	 * @return
	 */
	List<Template> getTemplates(TemplateQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<Template> getTemplatesByQueryCriteria(int start, int pageSize,
			TemplateQuery query);

	/**
	 * 批量导入模板
	 */
	@Transactional
	void installAllTemplates();

	/**
	 * 保持模板
	 * 
	 * @param template
	 */
	@Transactional
	void saveTemplate(Template template);
}