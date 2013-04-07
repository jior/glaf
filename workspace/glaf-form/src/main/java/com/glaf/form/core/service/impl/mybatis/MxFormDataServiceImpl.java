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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.base.DataModel;
import com.glaf.core.base.DataModelEntity;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.query.DataModelQuery;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.DataModelService;
import com.glaf.core.service.IBlobService;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.SearchFilter;
import com.glaf.core.util.Tools;

import com.glaf.form.core.context.FormContext;
import com.glaf.form.core.graph.def.FormApplication;
import com.glaf.form.core.graph.def.FormDefinition;
import com.glaf.form.core.graph.def.FormNode;
import com.glaf.form.core.graph.node.PersistenceNode;
import com.glaf.form.core.mapper.FormApplicationMapper;
import com.glaf.form.core.mapper.FormDefinitionMapper;
import com.glaf.form.core.query.FormApplicationQuery;
import com.glaf.form.core.query.FormDefinitionQuery;
import com.glaf.form.core.service.FormDataService;
import com.glaf.form.core.util.EntityAssembly;

@Service("formDataService")
@Transactional(readOnly = true)
public class MxFormDataServiceImpl implements FormDataService {
	protected final static Log logger = LogFactory
			.getLog(MxFormDataServiceImpl.class);

	protected IBlobService blobService;

	protected DataModelService dataModelService;

	protected EntityDAO entityDAO;

	protected FormApplicationMapper formApplicationMapper;

	protected FormDefinitionMapper formDefinitionMapper;

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	public MxFormDataServiceImpl() {

	}

	@Transactional
	public void deleteAllFormDefinitions(String formName) {

	}

	@Transactional
	public void deleteDataModel(String appId, Collection<String> businessKeys) {
		FormApplication formApplication = this.getFormApplication(appId);
		if (formApplication != null && formApplication.getTableName() != null) {
			dataModelService.deleteAll(formApplication.getTableName(),
					businessKeys);
		}
	}

	@Transactional
	public void deleteFormApplication(String appId) {
		formApplicationMapper.deleteFormApplicationProperties(appId);
		formApplicationMapper.deleteFormApplicationById(appId);
	}

	@Transactional
	public void deleteFormDefinitionByName(String formName) {
		formDefinitionMapper.deleteFormDefinitionByName(formName);
	}

	public int formApplicationCount(FormApplicationQuery query) {
		return formApplicationMapper.getFormApplicationCount(query);
	}

	public List<FormApplication> formApplicationList(FormApplicationQuery query) {
		List<FormApplication> list = formApplicationMapper
				.getFormApplications(query);
		return list;
	}

	public int formDefinitionCount(FormDefinitionQuery query) {
		return formDefinitionMapper.getFormDefinitionCount(query);
	}

	public List<FormDefinition> formDefinitionList(FormDefinitionQuery query) {
		List<FormDefinition> list = formDefinitionMapper
				.getFormDefinitions(query);
		return list;
	}

	public DataModel getDataModel(String appId, Long id) {
		FormApplication formApplication = this.getFormApplication(appId);
		if (formApplication != null && formApplication.getTableName() != null) {
			return dataModelService.getDataModel(
					formApplication.getTableName(), id);
		}
		return null;
	}

	public DataModel getDataModelByAppName(String app_name, Long id) {
		FormApplication formApplication = this
				.getFormApplicationByName(app_name);
		DataModel dataModel = null;
		if (formApplication != null && formApplication.getTableName() != null) {
			dataModel = this.getDataModel(formApplication.getTableName(), id);
			return dataModel;
		}
		return null;
	}

	public DataModel getDataModelByBusinessKey(String appId, String businessKey) {
		FormApplication formApplication = this.getFormApplication(appId);
		if (formApplication != null && formApplication.getTableName() != null) {
			return dataModelService.getDataModelByBusinessKey(
					formApplication.getTableName(), businessKey);
		}
		return null;
	}

	public FormApplication getFormApplication(String appId) {
		String cacheKey = "form_app_" + appId;
		if (CacheFactory.get(cacheKey) != null) {
			return (FormApplication) CacheFactory.get(cacheKey);
		}
		FormApplication formApplication = formApplicationMapper
				.getFormApplicationById(appId);

		return formApplication;
	}

	public FormApplication getFormApplicationByName(String name) {
		String cacheKey = "form_app_" + name;
		if (CacheFactory.get(cacheKey) != null) {
			return (FormApplication) CacheFactory.get(cacheKey);
		}
		FormApplication formApplication = formApplicationMapper
				.getFormApplicationByName(name);

		return formApplication;
	}

	public Map<String, FormApplication> getFormApplicationMap() {
		Map<String, FormApplication> appMap = new HashMap<String, FormApplication>();
		FormApplicationQuery query = new FormApplicationQuery();
		List<FormApplication> list = this.formApplicationList(query);
		if (list != null && !list.isEmpty()) {
			for (FormApplication app : list) {
				appMap.put(app.getName(), app);
			}
		}
		return appMap;
	}

	public FormDefinition getFormDefinition(String formDefinitionId) {
		String cacheKey = "form_def_" + formDefinitionId;
		if (CacheFactory.get(cacheKey) != null) {
			return (FormDefinition) CacheFactory.get(cacheKey);
		}
		FormDefinition formDefinition = formDefinitionMapper
				.getFormDefinition(formDefinitionId);
		return formDefinition;
	}

	public List<FormDefinition> getFormDefinitions(String name) {
		FormDefinitionQuery query = new FormDefinitionQuery();
		query.name(name);
		return this.formDefinitionList(query);
	}

	public FormDefinition getLatestFormDefinition(String name) {
		String cacheKey = "form_def_" + name;
		if (CacheFactory.get(cacheKey) != null) {
			return (FormDefinition) CacheFactory.get(cacheKey);
		}
		FormDefinitionQuery query = new FormDefinitionQuery();
		query.name(name);
		query.setOrderBy(" E.VERSION_ desc ");
		FormDefinition formDefinition = null;
		List<FormDefinition> list = this.formDefinitionList(query);
		if (list != null && !list.isEmpty()) {
			formDefinition = list.get(0);

		}
		return formDefinition;
	}

	public FormDefinition getLatestFormDefinitionReference(String name) {
		String cacheKey = "form_def_ref_" + name;
		if (CacheFactory.get(cacheKey) != null) {
			return (FormDefinition) CacheFactory.get(cacheKey);
		}
		FormDefinitionQuery query = new FormDefinitionQuery();
		query.name(name);
		query.setOrderBy(" E.VERSION_ desc ");
		List<FormDefinition> list = this.formDefinitionList(query);
		FormDefinition formDefinition = null;
		if (list != null && !list.isEmpty()) {
			formDefinition = list.get(0);
		}
		return formDefinition;
	}

	public Paging getPageApplication(FormApplicationQuery query) {
		Paging jpage = new Paging();
		int count = formApplicationMapper.getFormApplicationCount(query);
		if (count > 0) {
			SqlExecutor queryExecutor = new SqlExecutor();
			queryExecutor.setParameter(query);
			queryExecutor.setStatementId("getFormApplications");
			List<Object> rows = entityDAO.getList(query.getPageNo(),
					query.getPageSize(), queryExecutor);
			jpage.setTotal(count);
			jpage.setCurrentPage(query.getPageNo());
			jpage.setPageSize(query.getPageSize());
			jpage.setRows(rows);
		} else {
			jpage.setPageSize(0);
			jpage.setCurrentPage(0);
			jpage.setTotal(0);
		}
		return jpage;
	}

	@SuppressWarnings("unchecked")
	public Paging getPageDataModel(String appId, DataModelQuery query) {
		LoginContext loginContext = query.getLoginContext();
		FormApplication formApplication = this.getFormApplication(appId);
		query.setTableName(formApplication.getTableName());

		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (query.getParameter() != null) {
			if (Map.class.isAssignableFrom(query.getParameter().getClass())) {
				paramMap.putAll((Map<String, Object>) query.getParameter());
			}
		}

		String taskType = ParamUtils.getString(paramMap, "taskType");
		if (ParamUtils.isNotEmpty(paramMap, "processInstanceIds")) {
			Object object = ParamUtils.get(paramMap, "processInstanceIds");
			if (object instanceof java.util.Collection<?>) {
				Collection<?> rows = (Collection<?>) object;
				List<String> processInstanceIds = new ArrayList<String>();
				Iterator<?> iterator = rows.iterator();
				while (iterator.hasNext()) {
					Object value = iterator.next();
					if (value != null) {
						if (!(value instanceof Collection<?>)) {
							processInstanceIds.add(value.toString());
						}
					}
				}
				query.processInstanceIds(processInstanceIds);
			}
		} else {
			if (!loginContext.hasAdvancedPermission()) {
				if ("draft".equals(taskType)) {
					query.createBy(loginContext.getActorId());
				} else if ("finished".equals(taskType)
						|| "worked".equals(taskType)) {

				}
			}
		}

		if ("draft".equals(taskType)) {
			query.setProcessInstanceIsNull(true);
			query.createBy(loginContext.getActorId());
		} else if ("runnig".equals(taskType)) {
			query.setProcessInstanceIsNotNull(true);
		} else if ("finished".equals(taskType)) {
			query.setProcessInstanceIsNotNull(true);
			query.wfStatus(9999);
		} else if ("worked".equals(taskType)) {
			query.setProcessInstanceIsNotNull(true);
		} else if ("archives".equals(taskType)) {
			query.setProcessInstanceIsNotNull(true);
			query.status(50);
			query.wfStatus(9999);
		}

		query.setLoginContext(loginContext);
		query.setPageNo(query.getPageNo());
		query.setPageSize(query.getPageSize());

		Set<Entry<String, Object>> entrySet = paramMap.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String name = entry.getKey();
			Object value = entry.getValue();
			if (name.endsWith("_filter") && value != null) {
				String k = name.substring(0, name.lastIndexOf("_"));
				Object v = paramMap.get(k);
				if (k != null && v != null) {
					String filter = null;
					if (SearchFilter.EQUALS.equals(value.toString())) {
						filter = SearchFilter.EQUALS;
					} else if (SearchFilter.NOT_EQUALS.equals(value.toString())) {
						filter = SearchFilter.NOT_EQUALS;
					} else if (SearchFilter.LIKE.equals(value.toString())) {
						filter = SearchFilter.LIKE;
					} else if (SearchFilter.NOT_LIKE.equals(value.toString())) {
						filter = SearchFilter.NOT_LIKE;
					} else if (SearchFilter.GREATER_THAN_OR_EQUAL.equals(value
							.toString())) {
						filter = SearchFilter.GREATER_THAN_OR_EQUAL;
					} else if (SearchFilter.GREATER_THAN.equals(value
							.toString())) {
						filter = SearchFilter.GREATER_THAN;
					} else if (SearchFilter.LESS_THAN_OR_EQUAL.equals(value
							.toString())) {
						filter = SearchFilter.LESS_THAN_OR_EQUAL;
					} else if (SearchFilter.LESS_THAN.equals(value.toString())) {
						filter = SearchFilter.LESS_THAN;
					}
					if (filter != null) {
						query.addCondition(name, "E", filter, value);
						logger.debug("add filter condition:[" + k + "=\t" + v
								+ "]\t(" + value + ")");
					}
				}
			}
		}

		Paging page = new Paging();
		List<Object> rows = new ArrayList<Object>();

		if (rows != null && rows.size() > 0) {
			page.setRows(rows);
		}
		return page;
	}

	public Paging getPageFormDefinition(FormDefinitionQuery query) {
		Paging jpage = new Paging();
		int count = formDefinitionMapper.getFormDefinitionCount(query);
		if (count > 0) {
			SqlExecutor queryExecutor = new SqlExecutor();
			queryExecutor.setParameter(query);
			queryExecutor.setStatementId("getFormDefinitions");
			List<Object> rows = entityDAO.getList(query.getPageNo(),
					query.getPageSize(), queryExecutor);
			jpage.setTotal(count);
			jpage.setCurrentPage(query.getPageNo());
			jpage.setPageSize(query.getPageSize());
			jpage.setRows(rows);
		} else {
			jpage.setPageSize(0);
			jpage.setCurrentPage(0);
			jpage.setTotal(0);
		}
		return jpage;
	}

	@Transactional
	public void saveDataModel(String appId, FormContext formContext) {
		EntityAssembly assembly = new EntityAssembly();
		Map<String, Object> persistMap = assembly.assemble(formContext, null,
				true);
		FormDefinition formDefinition = formContext.getFormDefinition();
		String entityName = formDefinition.getName();
		DataModelEntity dataModelEntity = new DataModelEntity();
		Tools.populate(dataModelEntity, persistMap);

		dataModelEntity.setId(idGenerator.nextId(entityName));

		dataModelEntity.setCreateBy(formContext.getActorId());
		dataModelEntity.setServiceKey(entityName);
		dataModelEntity.setFormName(formDefinition.getName());

		Set<Entry<String, Object>> entrySet = persistMap.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String name = entry.getKey();
			Object value = entry.getValue();
			if (value != null) {
				ColumnModel field = new ColumnModel();
				field.setName(name);
				field.setValue(value);
				dataModelEntity.addField(field);
			}
		}

	}

	@Transactional
	public void saveFormApplication(FormApplication formApplication) {
		if (StringUtils.equals(formApplication.getReleaseFlag(), "Y")) {
			formApplication.setReleaseDate(new Date());
		} else {
			formApplication.setReleaseDate(null);
		}
		if (StringUtils.isEmpty(formApplication.getId())) {
			formApplication.setId(idGenerator.getNextId());
			formApplication.setCreateDate(new Date());
			formApplicationMapper.insertFormApplication(formApplication);
			try {

			} catch (Exception ex) {
			}
		} else {
			formApplicationMapper.updateFormApplication(formApplication);
		}
	}

	@Transactional
	public void saveFormApplications(List<FormApplication> applications) {
		if (applications != null && !applications.isEmpty()) {
			for (FormApplication app : applications) {
				this.saveFormApplication(app);
			}
		}
	}

	@Transactional
	public void saveFormDefinition(FormDefinition formDefinition) {

		try {

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		formDefinitionMapper.lockOldVersionFormDefinitions(formDefinition
				.getName());
		if (StringUtils.isEmpty(formDefinition.getId())) {
			formDefinition.setId(idGenerator.getNextId());
		}
		FormDefinition model = this
				.getLatestFormDefinitionReference(formDefinition.getName());
		if (model != null) {
			formDefinition.setVersion(model.getVersion() + 1);
		} else {
			formDefinition.setVersion(1);
		}
		formDefinition.setCreateDate(new Date());

		if (StringUtils.isEmpty(formDefinition.getFormDefinitionId())) {
			formDefinition.setFormDefinitionId(formDefinition.getName() + "_"
					+ formDefinition.getVersion());
		}

		formDefinitionMapper.insertFormDefinition(formDefinition);

		if (formDefinition.getTemplateData() != null) {

		}
	}

	@Transactional
	public void saveFormDefinition(FormDefinition formDefinition,
			boolean updateEntity) {
		this.saveFormDefinition(formDefinition);
	}

	@javax.annotation.Resource
	public void setBlobService(IBlobService blobService) {
		this.blobService = blobService;
	}

	@javax.annotation.Resource
	public void setDataModelService(DataModelService dataModelService) {
		this.dataModelService = dataModelService;
	}

	@javax.annotation.Resource
	@Qualifier("myBatisEntityDAO")
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setFormApplicationMapper(
			FormApplicationMapper formApplicationMapper) {
		this.formApplicationMapper = formApplicationMapper;
	}

	@javax.annotation.Resource
	public void setFormDefinitionMapper(
			FormDefinitionMapper formDefinitionMapper) {
		this.formDefinitionMapper = formDefinitionMapper;
	}

	@javax.annotation.Resource
	@Qualifier("myBatisDbIdGenerator")
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	protected DataModel toDataModel(FormDefinition formDefinition,
			DataModelEntity dataModelEntity) {
		DataModelEntity dataModel = new DataModelEntity();
		dataModel.setCreateBy(dataModelEntity.getCreateBy());
		dataModel.setCreateDate(dataModelEntity.getCreateDate());
		dataModel.setDeleteFlag(dataModelEntity.getDeleteFlag());
		dataModel.setFormName(dataModelEntity.getFormName());
		dataModel.setId(dataModelEntity.getId());
		dataModel.setBusinessKey(dataModelEntity.getBusinessKey());

		dataModel.setParentId(dataModelEntity.getParentId());
		dataModel.setProcessInstanceId(dataModelEntity.getProcessInstanceId());
		dataModel.setProcessName(dataModelEntity.getProcessName());
		dataModel.setSubject(dataModelEntity.getSubject());
		dataModel.setStatus(dataModelEntity.getStatus());
		dataModel.setWfStatus(dataModelEntity.getWfStatus());
		if (dataModelEntity.getFields() != null) {
			Map<String, ColumnModel> fields = dataModelEntity.getFields();
			Map<String, FormNode> nodesMap = formDefinition.getNodesMap();
			if (nodesMap != null && !nodesMap.isEmpty()) {
				Collection<FormNode> nodes = nodesMap.values();
				for (FormNode formNode : nodes) {
					if (formNode instanceof PersistenceNode) {
						PersistenceNode node = (PersistenceNode) formNode;
						ColumnModel field = fields.get(node.getName());
						if (field != null) {
							Object value = field.getValue();
							node.setValue(value);
							dataModel.addField(field);
						}
					}
				}
			}
		}
		return dataModel;
	}

	@Transactional
	public void updateDataModel(String appId, FormContext formContext) {
		EntityAssembly assembly = new EntityAssembly();
		Map<String, Object> persistMap = assembly.assemble(formContext, null,
				false);
		FormDefinition formDefinition = formContext.getFormDefinition();
		DataModelEntity dataModelEntity = new DataModelEntity();
		Tools.populate(dataModelEntity, persistMap);
		String businessKey = formContext.getString("businessKey");
		String subject = formContext.getString("subject");
		dataModelEntity.setId(Long.parseLong(businessKey));
		dataModelEntity.setSubject(subject);
		dataModelEntity.setServiceKey(formDefinition.getName());
		dataModelEntity.setFormName(formDefinition.getName());

		Set<Entry<String, Object>> entrySet = persistMap.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String name = entry.getKey();
			Object value = entry.getValue();
			if (value != null) {
				ColumnModel field = new ColumnModel();
				field.setName(name);
				field.setValue(value);
				dataModelEntity.addField(field);
			}
		}
	}

}