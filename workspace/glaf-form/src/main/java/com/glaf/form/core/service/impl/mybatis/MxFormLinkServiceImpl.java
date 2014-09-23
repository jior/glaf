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
package com.glaf.form.core.service.impl.mybatis;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.query.DataModelQuery;
import com.glaf.core.util.Paging;
import com.glaf.form.core.context.FormContext;
import com.glaf.form.core.domain.FormApplication;
import com.glaf.form.core.domain.FormDefinition;
import com.glaf.form.core.domain.FormLink;
import com.glaf.form.core.mapper.FormLinkMapper;
import com.glaf.form.core.query.FormLinkQuery;
import com.glaf.form.core.service.FormDataService;
import com.glaf.form.core.service.FormLinkService;

@Service("formLinkService")
@Transactional(readOnly = true)
public class MxFormLinkServiceImpl implements FormLinkService {
	protected final static Log logger = LogFactory
			.getLog(MxFormLinkServiceImpl.class);

	protected EntityDAO entityDAO;

	protected FormDataService formDataService;

	protected FormLinkMapper formLinkMapper;

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	public MxFormLinkServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		formLinkMapper.deleteFormLinkById(id);
	}

	public List<FormApplication> getChildrenApplications(String appId) {
		List<FormLink> list = this.getFormLinks(appId);
		List<FormApplication> rows = new java.util.ArrayList<FormApplication>();
		if (list != null && !list.isEmpty()) {
			for (FormLink model : list) {
				FormApplication childApplication = formDataService
						.getFormApplication(model.getChildAppId());
				rows.add(childApplication);
			}
		}
		return rows;
	}

	public List<FormDefinition> getChildrenFormDefinitionReference(String appId) {
		FormLinkQuery query = new FormLinkQuery();
		query.appId(appId);
		List<FormLink> list = this.list(query);
		List<FormDefinition> rows = new java.util.ArrayList<FormDefinition>();
		if (list != null && !list.isEmpty()) {
			for (FormLink model : list) {
				FormApplication childApplication = formDataService
						.getFormApplication(model.getChildAppId());
				FormDefinition formDefinition = formDataService
						.getLatestFormDefinition(childApplication.getFormName());
				if (formDefinition != null && formDefinition.getLocked() != 1) {
					rows.add(formDefinition);
				}
			}
		}
		return rows;
	}

	public List<FormDefinition> getFormDefinitions(String appId) {
		FormLinkQuery query = new FormLinkQuery();
		query.appId(appId);
		List<FormLink> list = this.list(query);
		List<FormDefinition> rows = new java.util.ArrayList<FormDefinition>();
		if (list != null && !list.isEmpty()) {
			for (FormLink model : list) {
				FormApplication childApplication = formDataService
						.getFormApplication(model.getChildAppId());
				FormDefinition formDefinition = formDataService
						.getLatestFormDefinition(childApplication.getFormName());
				if (formDefinition != null && formDefinition.getLocked() != 1) {
					rows.add(formDefinition);
				}
			}
		}
		return rows;
	}

	public FormLink getFormLink(String id) {
		FormLink formLink = formLinkMapper.getFormLinkById(id);
		return formLink;
	}

	public FormLink getFormLink(String applicationName, String childName) {

		FormLinkQuery query = new FormLinkQuery();
		query.appId(applicationName);
		query.childAppId(childName);
		List<FormLink> list = this.list(query);

		FormLink formLink = null;
		if (list != null && !list.isEmpty()) {
			formLink = list.get(0);
		}
		return formLink;
	}

	public List<FormLink> getFormLinks(String appId) {

		FormLinkQuery query = new FormLinkQuery();
		query.appId(appId);
		List<FormLink> list = this.list(query);
		return list;
	}

	public Paging getPageDataModel(FormLink formLink, DataModelQuery query) {
		FormContext formContext = new FormContext();
		FormApplication formApplication = formDataService
				.getFormApplication(formLink.getAppId());
		FormApplication childApplication = formDataService
				.getFormApplication(formLink.getChildAppId());
		FormDefinition formDefinition = formDataService
				.getLatestFormDefinition(childApplication.getFormName());

		formContext.setFormApplication(formApplication);
		formContext.setFormDefinition(formDefinition);

		return formDataService.getPageDataModel(formApplication.getId(), query);
	}

	public List<FormLink> list(FormLinkQuery query) {
		query.ensureInitialized();
		List<FormLink> list = formLinkMapper.getFormLinks(query);
		return list;
	}

	@Transactional
	public void save(FormLink formLink) {
		if (StringUtils.isEmpty(formLink.getId())) {
			formLink.setId(idGenerator.getNextId());
			formLinkMapper.insertFormLink(formLink);
		} else {
			formLinkMapper.updateFormLink(formLink);
		}
	}

	@Transactional
	public void saveAll(String appId, List<FormLink> rows) {
		formLinkMapper.deleteFormLinks(appId);
		for (FormLink model : rows) {
			model.setAppId(appId);
			this.save(model);
		}
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setFormDataService(FormDataService formDataService) {
		this.formDataService = formDataService;
	}

	@javax.annotation.Resource
	public void setFormLinkMapper(FormLinkMapper formLinkMapper) {
		this.formLinkMapper = formLinkMapper;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

}