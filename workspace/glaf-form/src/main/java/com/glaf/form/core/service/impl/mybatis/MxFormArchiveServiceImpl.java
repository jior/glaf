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

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.base.DataFile;
import com.glaf.core.base.DataModel;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IBlobService;
import com.glaf.core.service.ISysLogService;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.Dom4jUtils;
import com.glaf.form.core.container.MxFormContainer;
import com.glaf.form.core.context.FormContext;
import com.glaf.form.core.domain.FormApplication;
import com.glaf.form.core.domain.FormDefinition;
import com.glaf.form.core.export.xml.FormXmlExporter;
import com.glaf.form.core.mapper.FormDefinitionMapper;
import com.glaf.form.core.service.FormArchiveService;
import com.glaf.form.core.service.FormDataService;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.model.ActivityInstance;

@Service("formArchiveService")
@Transactional
public class MxFormArchiveServiceImpl implements FormArchiveService {
	protected final static Log logger = LogFactory
			.getLog(MxFormArchiveServiceImpl.class);

	protected IBlobService blobService;

	protected FormDataService formDataService;

	protected FormDefinitionMapper formDefinitionMapper;

	protected SqlSession sqlSession;

	protected ISysLogService sysLogService;

	@Transactional
	public void archives(FormApplication formApplication, String businessKey,
			Map<String, Object> dataMap) {
		DataModel dataModel = null;
		FormDefinition formDefinition = null;
		FormContext formContext = new FormContext();
		formContext.setDataMap(dataMap);
		LoginContext loginContext = (LoginContext) dataMap.get("loginContext");
		if (loginContext != null) {
			formContext.setLoginContext(loginContext);
		}
		Collection<DataFile> dataFiles = new java.util.ArrayList<DataFile>();
		Map<String, InputStream> zipMap = new java.util.HashMap<String, InputStream>();

		formContext.setFormApplication(formApplication);
		formDefinition = formDataService
				.getLatestFormDefinition(formApplication.getFormName());
		formContext.setFormDefinition(formDefinition);

		if (StringUtils.isNotEmpty(businessKey)) {
			dataModel = MxFormContainer.getContainer()
					.getDataModelByBusinessKey(formApplication.getId(),
							businessKey);
		}

		if (dataModel != null
				&& StringUtils.isNotEmpty(dataModel.getBusinessKey())) {
			List<DataFile> list = blobService.getBlobList(dataModel
					.getBusinessKey());
			if (list != null) {
				Iterator<DataFile> iterator = list.iterator();
				while (iterator.hasNext()) {
					DataFile dataFile = iterator.next();
					dataFiles.add(dataFile);
				}
			}

			DataFile blob = blobService.getMaxBlob(dataModel.getBusinessKey());
			if (blob != null && blob.getData() != null) {
				InputStream is = new BufferedInputStream(
						new ByteArrayInputStream(blob.getData()));
				zipMap.put(blob.getFilename(), is);
			}
		}

		if (dataModel != null
				&& StringUtils.isNotEmpty(dataModel.getProcessInstanceId())) {
			ProcessContainer container = ProcessContainer.getContainer();
			List<ActivityInstance> list = container.getActivityInstances(Long
					.parseLong(dataModel.getProcessInstanceId()));
			if (list != null && list.size() > 0) {
				Document doc = DocumentHelper.createDocument();
				Element root = doc.addElement("HistoryActivityInstances");
				Iterator<ActivityInstance> iter01 = list.iterator();
				while (iter01.hasNext()) {
					ActivityInstance s = iter01.next();
					Element element = root
							.addElement("HistoryActivityInstance");
					element.addAttribute("Id",
							String.valueOf(s.getTaskInstanceId()));
					element.addElement("ActorId").setText(s.getActorId());
					element.addElement("ActorName").setText(s.getActorName());
					if (s.getIsAgree() != null) {
						element.addElement("IsAgree").setText(s.getIsAgree());
					}
					element.addElement("ProcessInstanceId").setText(
							String.valueOf(s.getProcessInstanceId()));
					element.addElement("TaskName").setText(s.getTaskName());
					element.addElement("TaskInstanceId").setText(
							String.valueOf(s.getTaskInstanceId()));
					element.addElement("TaskDescription").setText(
							s.getTaskDescription());
					element.addElement("RowId").setText(s.getRowId());
					if (s.getContent() != null) {
						element.addElement("Content").setText(s.getContent());
					}
					element.addElement("Date").setText(
							DateUtils.getDateTime(s.getDate()));
				}
				byte[] bytes = Dom4jUtils.getBytesFromPrettyDocument(doc);
				InputStream is = new BufferedInputStream(
						new ByteArrayInputStream(bytes));
				zipMap.put("workflow.xml", is);
			}

			if (dataFiles != null && dataFiles.size() > 0) {
				Iterator<DataFile> iter01 = dataFiles.iterator();
				while (iter01.hasNext()) {
					DataFile dataFile = iter01.next();
					InputStream is = blobService
							.getInputStreamByFileId(dataFile.getFileId());
					if (is != null) {
						zipMap.put(dataFile.getFilename(), is);
					}
				}
			}

			FormXmlExporter exporter = new FormXmlExporter();
			Document doc = exporter.export(dataModel, formContext);
			byte[] bytes2 = Dom4jUtils.getBytesFromPrettyDocument(doc);
			InputStream is2 = new BufferedInputStream(new ByteArrayInputStream(
					bytes2));
			zipMap.put("form.xml", is2);

			logger.debug("zip keys:" + zipMap.keySet());

			zipMap.clear();
			zipMap = null;

			Map<String, Object> parameter = new java.util.HashMap<String, Object>();

			parameter.put("archivesFlag", "Y");
			parameter.put("id", businessKey);
			formDefinitionMapper.updateFormArchivesFlag(parameter);
		}
	}

	@Transactional
	public void archives(String appId, String businessKey,
			Map<String, Object> dataMap) {
		FormApplication formApplication = formDataService
				.getFormApplication(appId);
		this.archives(formApplication, businessKey, dataMap);
	}

	@javax.annotation.Resource
	public void setBlobService(IBlobService blobService) {
		this.blobService = blobService;
	}

	@javax.annotation.Resource
	public void setFormDataService(FormDataService formDataService) {
		this.formDataService = formDataService;
	}

	@javax.annotation.Resource
	public void setFormDefinitionMapper(
			FormDefinitionMapper formDefinitionMapper) {
		this.formDefinitionMapper = formDefinitionMapper;
	}

	@javax.annotation.Resource
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@javax.annotation.Resource
	public void setSysLogService(ISysLogService sysLogService) {
		this.sysLogService = sysLogService;
	}

}