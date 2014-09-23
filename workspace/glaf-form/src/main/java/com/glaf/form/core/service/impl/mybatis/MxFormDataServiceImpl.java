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

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.DataFile;
import com.glaf.core.base.DataModel;
import com.glaf.core.base.DataModelEntity;
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.domain.BlobItemEntity;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.query.DataModelQuery;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.DataModelService;
import com.glaf.core.service.IBlobService;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.SearchFilter;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.core.util.UUID32;
import com.glaf.form.core.context.FormContext;
import com.glaf.form.core.domain.FormApplication;
import com.glaf.form.core.domain.FormDefinition;
import com.glaf.form.core.domain.FormHistoryInstance;
import com.glaf.form.core.graph.def.FormNode;
import com.glaf.form.core.graph.node.PersistenceNode;
import com.glaf.form.core.mapper.FormApplicationMapper;
import com.glaf.form.core.mapper.FormDefinitionMapper;
import com.glaf.form.core.mapper.FormHistoryInstanceMapper;
import com.glaf.form.core.query.FormApplicationQuery;
import com.glaf.form.core.query.FormDefinitionQuery;
import com.glaf.form.core.service.FormDataService;
import com.glaf.form.core.util.FormApplicationJsonFactory;
import com.glaf.form.core.util.FormDefinitionJsonFactory;

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

	protected FormHistoryInstanceMapper formHistoryInstanceMapper;

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	protected ITableDefinitionService tableDefinitionService;

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

	public DataModel getDataModelByAppName(String appId, Long id) {
		FormApplication formApplication = this
				.getFormApplicationByName(appId);
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
		if (SystemConfig.getBoolean("use_query_cache")
				&& CacheFactory.getString(cacheKey) != null) {
			String text = CacheFactory.getString(cacheKey);
			JSONObject jsonObject = JSON.parseObject(text);
			return FormApplicationJsonFactory.jsonToObject(jsonObject);
		}
		FormApplication formApplication = formApplicationMapper
				.getFormApplicationById(appId);
		if (SystemConfig.getBoolean("use_query_cache")
				&& formApplication != null) {
			CacheFactory.put(cacheKey, formApplication.toJsonObject()
					.toJSONString());
		}

		return formApplication;
	}

	public FormApplication getFormApplicationByName(String name) {
		String cacheKey = "form_app_" + name;
		if (SystemConfig.getBoolean("use_query_cache")
				&& CacheFactory.getString(cacheKey) != null) {
			String text = CacheFactory.getString(cacheKey);
			JSONObject jsonObject = JSON.parseObject(text);
			return FormApplicationJsonFactory.jsonToObject(jsonObject);
		}
		FormApplication formApplication = formApplicationMapper
				.getFormApplicationByName(name);
		if (SystemConfig.getBoolean("use_query_cache")
				&& formApplication != null) {
			CacheFactory.put(cacheKey, formApplication.toJsonObject()
					.toJSONString());
		}
		return formApplication;
	}

	public Map<String, FormApplication> getFormApplicationMap() {
		Map<String, FormApplication> appMap = new java.util.HashMap<String, FormApplication>();
		FormApplicationQuery query = new FormApplicationQuery();
		List<FormApplication> list = this.formApplicationList(query);
		if (list != null && !list.isEmpty()) {
			for (FormApplication app : list) {
				appMap.put(app.getId(), app);
			}
		}
		return appMap;
	}

	public FormDefinition getFormDefinition(String formDefinitionId) {
		String cacheKey = "form_def_" + formDefinitionId;
		if (SystemConfig.getBoolean("use_query_cache")
				&& CacheFactory.getString(cacheKey) != null) {
			String text = CacheFactory.getString(cacheKey);
			JSONObject jsonObject = JSON.parseObject(text);
			return FormDefinitionJsonFactory.jsonToObject(jsonObject);
		}
		FormDefinition formDefinition = formDefinitionMapper
				.getFormDefinition(formDefinitionId);
		if (SystemConfig.getBoolean("use_query_cache")
				&& formDefinition != null) {
			CacheFactory.put(cacheKey, formDefinition.toJsonObject()
					.toJSONString());
		}
		return formDefinition;
	}

	public List<FormDefinition> getFormDefinitions(String name) {
		FormDefinitionQuery query = new FormDefinitionQuery();
		query.name(name);
		return this.formDefinitionList(query);
	}

	public FormDefinition getLatestFormDefinition(String name) {
		String cacheKey = "form_def_" + name;
		if (SystemConfig.getBoolean("use_query_cache")
				&& CacheFactory.getString(cacheKey) != null) {
			String text = CacheFactory.getString(cacheKey);
			JSONObject jsonObject = JSON.parseObject(text);
			return FormDefinitionJsonFactory.jsonToObject(jsonObject);
		}
		FormDefinition formDefinition = formDefinitionMapper
				.getLatestFormDefinitionByName(name);
		if (SystemConfig.getBoolean("use_query_cache")
				&& formDefinition != null) {
			CacheFactory.put(cacheKey, formDefinition.toJsonObject()
					.toJSONString());
		}
		return formDefinition;
	}

	public List<FormDefinition> getLatestFormDefinitions(
			FormDefinitionQuery query) {
		return formDefinitionMapper.getLatestFormDefinitions(query);
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

		Map<String, Object> paramMap = new java.util.HashMap<String, Object>();
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
				List<String> processInstanceIds = new java.util.ArrayList<String>();
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
		List<Object> rows = new java.util.ArrayList<Object>();

		int total = dataModelService.getDataModelCount(query);
		if (total > 0) {
			page.setTotal(total);
			List<DataModel> list = dataModelService.list(query.getPageNo(),
					query.getPageSize(), query);
			if (list != null && !list.isEmpty()) {
				for (DataModel m : list) {
					rows.add(m);
				}
			}
		}

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
		Map<String, Object> dataMap = formContext.getDataMap();
		FormDefinition formDefinition = formContext.getFormDefinition();
		FormApplication formApplication = this.getFormApplication(appId);
		String tableName = formApplication.getTableName();
		List<ColumnDefinition> columns = tableDefinitionService
				.getColumnDefinitionsByTableName(tableName);
		Map<String, ColumnDefinition> columnMap = new java.util.HashMap<String, ColumnDefinition>();
		for (ColumnDefinition column : columns) {
			columnMap.put(column.getColumnName(), column);
			columnMap.put(column.getColumnName().toLowerCase(), column);
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd",
				Locale.getDefault());
		String ret = formatter.format(new Date());

		Long sid = System.currentTimeMillis();
		String serialNumber = ret + StringTools.getDigit4Id(sid.intValue());
		dataMap.put("serialNumber", serialNumber);

		DataModelEntity dataModelEntity = new DataModelEntity();

		try {
			Tools.populate(dataModelEntity, dataMap);
		} catch (Exception ex) {
			logger.error(ex);
		}
		dataModelEntity.setDataMap(dataMap);
		dataModelEntity.setId(idGenerator.nextId());
		dataModelEntity.setBusinessKey(UUID32.getUUID());
		dataModelEntity.setCreateBy(formContext.getActorId());
		dataModelEntity.setDeleteFlag(0);
		dataModelEntity.setServiceKey(formApplication.getId());
		dataModelEntity.setFormName(formDefinition.getName());
		dataModelEntity.setStatus(0);

		Set<Entry<String, Object>> entrySet = dataMap.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String name = entry.getKey();
			Object value = entry.getValue();
			if (value != null) {
				if (columnMap.containsKey(name.toLowerCase())) {
					ColumnModel field = new ColumnModel();
					field.setName(name);
					field.setValue(value);
					dataModelEntity.addField(field);
				}
			}
		}

		dataModelService.insert(dataModelEntity);

		blobService.makeMark(formContext.getActorId(),
				formApplication.getId(), dataModelEntity.getBusinessKey());

		FormHistoryInstance historyInstance = new FormHistoryInstance();
		historyInstance.setActorId(formContext.getActorId());
		historyInstance.setContent(dataModelEntity.toJsonObject()
				.toJSONString());
		historyInstance.setCreateDate(new Date());
		historyInstance.setId(formApplication.getId() + "_"
				+ idGenerator.nextId());
		historyInstance.setVersionNo(System.currentTimeMillis());
		historyInstance.setRefId(dataModelEntity.getId());
		historyInstance.setNodeId(formContext.getString("nodeId"));
		historyInstance.setObjectId(formContext.getString("objectId"));
		historyInstance.setObjectValue(formContext.getString("objectValue"));

		formHistoryInstanceMapper.insertFormHistoryInstance(historyInstance);
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
		} else {
			formApplicationMapper.updateFormApplication(formApplication);
			CacheFactory.remove("form_app_" + formApplication.getId());
			CacheFactory.remove("form_app_" + formApplication.getId());
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
		formDefinitionMapper.lockOldVersionFormDefinitions(formDefinition
				.getName());
		if (StringUtils.isEmpty(formDefinition.getId())) {
			formDefinition.setId(idGenerator.getNextId());
		}
		FormDefinition model = this.getLatestFormDefinition(formDefinition
				.getName());
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
		CacheFactory.remove("form_def_" + formDefinition.getName());

		if (formDefinition.getTemplateData() != null) {
			DataFile dataFile = new BlobItemEntity();
			dataFile.setBusinessKey(formDefinition.getFormDefinitionId());
			dataFile.setCreateBy(formDefinition.getCreateBy());
			dataFile.setCreateDate(new Date());
			dataFile.setData(formDefinition.getTemplateData());
			dataFile.setFilename(formDefinition.getTemplateName());
			dataFile.setName(formDefinition.getName());
			dataFile.setServiceKey("FormDefinition");
			dataFile.setType(formDefinition.getTemplateType());
			dataFile.setFileId(formDefinition.getFormDefinitionId());
			dataFile.setStatus(9);
			dataFile.setLastModified(System.currentTimeMillis());
			blobService.insertBlob(dataFile);
		}
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
	public void setFormHistoryInstanceMapper(
			FormHistoryInstanceMapper formHistoryInstanceMapper) {
		this.formHistoryInstanceMapper = formHistoryInstanceMapper;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@javax.annotation.Resource
	public void setTableDefinitionService(
			ITableDefinitionService tableDefinitionService) {
		this.tableDefinitionService = tableDefinitionService;
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
		Map<String, Object> dataMap = formContext.getDataMap();

		FormDefinition formDefinition = formContext.getFormDefinition();
		FormApplication formApplication = this.getFormApplication(appId);
		String tableName = formApplication.getTableName();
		List<ColumnDefinition> columns = tableDefinitionService
				.getColumnDefinitionsByTableName(tableName);
		Map<String, ColumnDefinition> columnMap = new java.util.HashMap<String, ColumnDefinition>();
		for (ColumnDefinition column : columns) {
			columnMap.put(column.getColumnName(), column);
			columnMap.put(column.getColumnName().toLowerCase(), column);
		}

		DataModelEntity dataModelEntity = new DataModelEntity();
		try {
			Tools.populate(dataModelEntity, dataMap);
		} catch (Exception ex) {
			logger.error(ex);
		}
		dataModelEntity.setDataMap(dataMap);
		String id = formContext.getString("id");
		String businessKey = formContext.getString("businessKey");
		String subject = formContext.getString("subject");
		dataModelEntity.setId(Long.parseLong(id));
		dataModelEntity.setSubject(subject);
		dataModelEntity.setBusinessKey(businessKey);
		dataModelEntity.setServiceKey(formApplication.getId());
		dataModelEntity.setFormName(formDefinition.getName());
		dataModelEntity.setUpdateBy(formContext.getActorId());
		dataModelEntity.setUpdateDate(new Date());

		blobService.makeMark(formContext.getActorId(),
				formApplication.getId(), dataModelEntity.getBusinessKey());

		Set<Entry<String, Object>> entrySet = dataMap.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String name = entry.getKey();
			Object value = entry.getValue();
			if (value != null) {
				if (columnMap.containsKey(name.toLowerCase())) {
					ColumnModel field = new ColumnModel();
					field.setName(name);
					field.setValue(value);
					dataModelEntity.addField(field);
				}
			}
		}

		dataModelService.update(dataModelEntity);

		FormHistoryInstance historyInstance = new FormHistoryInstance();
		historyInstance.setActorId(formContext.getActorId());
		historyInstance.setContent(dataModelEntity.toJsonObject()
				.toJSONString());
		historyInstance.setCreateDate(new Date());
		historyInstance.setId(formApplication.getId() + "_"
				+ idGenerator.nextId());
		historyInstance.setVersionNo(System.currentTimeMillis());
		historyInstance.setRefId(dataModelEntity.getId());
		historyInstance.setNodeId(formContext.getString("nodeId"));
		historyInstance.setObjectId(formContext.getString("objectId"));
		historyInstance.setObjectValue(formContext.getString("objectValue"));
		historyInstance.setProcessInstanceId(formContext
				.getString("processInstanceId"));
		historyInstance.setTaskInstanceId(formContext
				.getString("taskInstanceId"));

		formHistoryInstanceMapper.insertFormHistoryInstance(historyInstance);
	}

}