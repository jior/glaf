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
package com.glaf.form.core.archives;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.zip.*;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.taskmgmt.def.Task;
 
import com.glaf.form.core.container.MxFormContainer;
import com.glaf.form.core.context.FormContext;
import com.glaf.form.core.dataimport.MxFormDataImport;
import com.glaf.form.core.dataimport.MxFormDataImportFactory;
import com.glaf.form.core.graph.def.FormApplication;
import com.glaf.form.core.graph.def.FormDefinition;
import com.glaf.form.core.model.FormDefinitionType;
import com.glaf.form.core.service.FormDataService;
import com.glaf.form.core.util.FdlConverter;
import com.glaf.form.core.util.FormTools;
import com.glaf.form.core.xml.FormApplicationReader;

import com.glaf.core.base.DataModel;
import com.glaf.core.base.DataModelEntity;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.todo.Todo;
import com.glaf.core.todo.service.ISysTodoService;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.LogUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.ZipUtils;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.Context;
import com.glaf.jbpm.deploy.MxJbpmProcessDeployer;

public class MxFormArchiveImporter {
	protected final static Log logger = LogFactory
			.getLog(MxFormArchiveImporter.class);

	/**
	 * 部署业务包
	 * 
	 * @param loginContext
	 * @param zipInputStream
	 */
	public void deploy(LoginContext loginContext, byte[] zipBytes,
			Map<String, Object> paramMap) {
		Map<String, byte[]> zipMap = null;
		FormApplication formApplication = null;
		ZipInputStream zipInputStream = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss",
					Locale.getDefault());
			String ret = formatter.format(new Date());
			String filename = SystemProperties.getConfigRootPath() + "/deploy/"
					+ ret + ".jar";
			FileUtils.save(filename, zipBytes);
		} catch (Exception ex) {
			if (LogUtils.isDebug()) {
				logger.debug(ex);
				ex.printStackTrace();
			}
		}
		try {
			zipInputStream = new ZipInputStream(new ByteArrayInputStream(
					zipBytes));
			zipMap = ZipUtils.getZipBytesMap(zipInputStream);
			zipInputStream.close();
			zipInputStream = null;
			if (zipMap != null) {
				logger.debug(zipMap.keySet());
			}
		} catch (Exception ex) {
			if (LogUtils.isDebug()) {
				logger.debug(ex);
				ex.printStackTrace();
			}
			throw new RuntimeException(ex);
		} finally {
			try {
				if (zipInputStream != null) {
					zipInputStream.close();
					zipInputStream = null;
				}
			} catch (Exception ex) {
			}
		}

		try {
			logger.info("start deploy forms...");
			this.deployForms(loginContext, zipBytes, paramMap);
			logger.info("deploy new forms ok.");
		} catch (Exception ex) {
			if (LogUtils.isDebug()) {
				logger.debug(ex);
				ex.printStackTrace();
			}
			throw new RuntimeException(ex);
		}

		try {
			if (zipMap != null && zipMap.get("form.cfg.xml") != null) {
				logger.info("start deploy form access...");
				this.deployFormAccess(loginContext, zipBytes, paramMap);
				logger.info("deploy form access ok.");
			}
		} catch (Exception ex) {
			if (LogUtils.isDebug()) {
				logger.debug(ex);
				ex.printStackTrace();
			}
			throw new RuntimeException(ex);
		}

		try {
			if (zipMap != null && zipMap.get("application.xml") != null) {
				logger.info("start deploy application...");
				formApplication = this.deployApplication(loginContext,
						zipBytes, paramMap);
				logger.info("deploy application ok.");
			}
		} catch (Exception ex) {
			if (LogUtils.isDebug()) {
				logger.debug(ex);
				ex.printStackTrace();
			}
			throw new RuntimeException(ex);
		}

		try {
			if (zipMap != null && zipMap.get("processdefinition.xml") != null) {
				logger.info("start deploy jpdl...");
				this.deployJpdl(loginContext, zipBytes, paramMap);
				logger.info("deploy jpdl ok.");
			}
		} catch (Exception ex) {
			if (LogUtils.isDebug()) {
				logger.debug(ex);
				ex.printStackTrace();
			}
			throw new RuntimeException(ex);
		}

		if (formApplication != null && formApplication.getProcessName() != null) {
			this.deployTodo(formApplication);
		}

		try {
			if (zipMap != null && zipMap.get("formdata.xml") != null) {
				logger.info("start install form data...");
				this.installFormData(loginContext, formApplication, zipBytes);
				logger.info("install form data ok.");
			}
		} catch (Exception ex) {
			if (LogUtils.isDebug()) {
				logger.debug(ex);
				ex.printStackTrace();
			}
			throw new RuntimeException(ex);
		}

	}

	public FormApplication deployApplication(LoginContext loginContext,
			byte[] zipBytes, Map<String, Object> paramMap) {
		FormApplication formApplication = null;
		FormContext formContext = new FormContext();
		formContext.setLoginContext(loginContext);
		ZipInputStream zipInputStream = null;
		try {
			zipInputStream = new ZipInputStream(new ByteArrayInputStream(
					zipBytes));
			byte[] bytes = ZipUtils.getBytes(zipInputStream, "application.xml");
			if (bytes != null) {
				FormApplicationReader reader = new FormApplicationReader();
				SAXReader xmlReader = new SAXReader();

				Document doc = xmlReader.read(new ByteArrayInputStream(bytes));
				Element root = doc.getRootElement();
				formApplication = reader.parse(root);
				formApplication = this.deployApplication(loginContext,
						formApplication, paramMap);
			}
		} catch (Exception ex) {
			if (LogUtils.isDebug()) {
				logger.debug(ex);
				ex.printStackTrace();
			}
			throw new RuntimeException(ex);
		} finally {
			try {
				zipInputStream.close();
				zipInputStream = null;
			} catch (Exception ex) {
			}
		}
		return formApplication;
	}

	public FormApplication deployApplication(LoginContext loginContext,
			FormApplication formApplication, Map<String, Object> paramMap) {
		FormContext formContext = new FormContext();
		formContext.setLoginContext(loginContext);
		FormDataService formDataService = (FormDataService) ContextFactory
				.getBean("formDataService");
		String nodeId = ParamUtils.getString(paramMap, "nodeId");
		if (StringUtils.isNotEmpty(nodeId)) {
			formApplication.setNodeId(Long.parseLong(nodeId));
		}
		FormApplication model = formDataService
				.getFormApplicationByName(formApplication.getName());
		if (model != null) {
			formApplication.setId(model.getId());
			formApplication.setCreateBy(model.getCreateBy());
			formApplication.setCreateDate(model.getCreateDate());
		}
		FormDefinition formDefinition = formDataService
				.getLatestFormDefinitionReference(formApplication.getFormName());
		if (formDefinition != null) {
			formApplication.setFormName(formDefinition.getName());
		}
		formDataService.saveFormApplication(formApplication);
		return formApplication;
	}

	public void deployForms(LoginContext loginContext, byte[] zipBytes,
			Map<String, Object> paramMap) {
		ZipInputStream zipInputStream = null;
		InputStream inputStream = null;
		try {
			zipInputStream = new ZipInputStream(new ByteArrayInputStream(
					zipBytes));
			Map<String, byte[]> zipMap = ZipUtils
					.getZipBytesMap(zipInputStream);
			logger.debug(zipMap);
			if (zipMap != null && zipMap.size() > 0) {
				Set<Entry<String, byte[]>> entrySet = zipMap.entrySet();
				for (Entry<String, byte[]> entry : entrySet) {
					String name = entry.getKey();
					byte[] bytes = entry.getValue();
					MxFormDataImport dx = null;
					String templateType = null;
					FormDefinition formDefinition = null;

					if (StringUtils.endsWith(name, ".xls")) {
						templateType = "xls";
						dx = MxFormDataImportFactory
								.getDataImport(templateType);
					}

					if (StringUtils.endsWith(name, ".fdl.xml")) {
						templateType = "fdl";
						dx = MxFormDataImportFactory
								.getDataImport(templateType);
					}

					if (dx != null) {
						logger.debug("templateType:" + templateType);
						inputStream = new ByteArrayInputStream(bytes);
						FormDefinitionType formDefinitionType = dx
								.read(inputStream);
						formDefinition = FdlConverter
								.toFormDefinition(formDefinitionType);
						inputStream.close();
						inputStream = null;
					}

					if (formDefinition != null) {
						logger.debug("==================DEPLOY FORM============");
						logger.debug("deploy form:" + formDefinition.getTitle());
						formDefinition.setTemplateName(name);
						formDefinition.setTemplateData(bytes);
						formDefinition.setTemplateType(templateType);

						FormContext formContext = new FormContext();
						formContext.setFormDefinition(formDefinition);
						formDefinition.setFormContext(formContext);
						FormDataService formDataService = (FormDataService) ContextFactory
								.getBean("formDataService");
						formDataService.saveFormDefinition(formDefinition);
					}
				}

			}
		} catch (Exception ex) {
			logger.error(ex);
			throw new RuntimeException(ex);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
					inputStream = null;
				} catch (Exception ex) {
				}
			}
			if (zipInputStream != null) {
				try {
					zipInputStream.close();
					zipInputStream = null;
				} catch (Exception ex) {
				}
			}
		}
	}

	public void deployFormAccess(LoginContext loginContext, byte[] zipBytes,
			Map<String, Object> paramMap) {

		ZipInputStream zipInputStream = null;
		try {
			zipInputStream = new ZipInputStream(new ByteArrayInputStream(
					zipBytes));
			byte[] bytes = ZipUtils.getBytes(zipInputStream, "form.cfg.xml");
			if (bytes != null) {

			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				zipInputStream.close();
				zipInputStream = null;
			} catch (Exception ex) {
			}
		}

	}

	/**
	 * 部署流程包
	 * 
	 * @param loginContext
	 *            用户上下文
	 * @param zipInputStream
	 *            压缩输入流
	 * @return
	 */
	public ProcessDefinition deployJpdl(LoginContext loginContext,
			byte[] zipBytes, Map<String, Object> paramMap) {
		ProcessDefinition processDefinition = null;
		JbpmContext jbpmContext = null;
		MxJbpmProcessDeployer deployer = new MxJbpmProcessDeployer();
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			if (jbpmContext != null && jbpmContext.getSession() != null) {
				processDefinition = deployer.deploy(jbpmContext, zipBytes);
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			throw new JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}

		return processDefinition;
	}

	public void deployTodo(FormApplication formApplication) {
		Map<String, Todo> todoMap = new HashMap<String, Todo>();
		ProcessDefinition processDefinition = null;
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			processDefinition = jbpmContext.getGraphSession()
					.findLatestProcessDefinition(
							formApplication.getProcessName());
			Map<String, Task> tasks = processDefinition.getTaskMgmtDefinition()
					.getTasks();
			if (tasks != null && tasks.size() > 0) {
				Iterator<Task> iterator = tasks.values().iterator();
				while (iterator.hasNext()) {
					Task task = iterator.next();
					if (task.getName().startsWith("task55")) {
						continue;
					}
					String subject = "";
					if (processDefinition.getDescription() != null) {
						subject = processDefinition.getDescription();
					} else {
						processDefinition.getName();
					}
					if (task.getDescription() != null) {
						subject = subject + task.getDescription() + "尚未完成！";
					} else {
						subject = subject + task.getName() + "尚未完成！";
					}
					Todo todo = new Todo();
					todo.setTaskName(task.getName());
					todo.setTitle(subject);
					todo.setContent(subject);
					todo.setLimitDay(2);
					todo.setXa(6);
					todo.setXb(6);
					todo.setProcessName(formApplication.getProcessName());
					todo.setProvider("jbpm");
					todo.setLink("/mx/form?businessKey=${businessKey}&x_method=view&app_name="
							+ formApplication.getName());
					todo.setListLink("/mx/form/formList?taskType=running&app_name="
							+ formApplication.getName()
							+ "&taskName="
							+ task.getName());
					todo.setAllListLink("/mx/form/formList?taskType=all&app_name="
							+ formApplication.getName());
					todo.setCode(formApplication.getProcessName() + "_"
							+ task.getName());
					todo.setLinkType(formApplication.getProcessName() + "_"
							+ task.getName());
					// todo.setModuleId();
					todo.setModuleName(formApplication.getTitle());
					todoMap.put(task.getName(), todo);
				}
			}
		} catch (Exception ex) {
			throw new JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}

		ISysTodoService sysTodoService = ContextFactory
				.getBean("sysTodoService");
		if (todoMap.values().size() > 0) {
			sysTodoService.saveAll(todoMap.values());
		}

	}

	public void installFormData(LoginContext loginContext,
			FormApplication formApplication, byte[] zipBytes) {
		FormDataService formDataService = (FormDataService) ContextFactory
				.getBean("formDataService");
		FormContext formContext = new FormContext();
		formContext.setFormApplication(formApplication);
		ZipInputStream zipInputStream = null;
		FormDefinition formDefinition = null;

		formDefinition = formDataService
				.getLatestFormDefinition(formApplication.getFormName());
		formContext.setFormDefinition(formDefinition);
		zipInputStream = new ZipInputStream(new ByteArrayInputStream(zipBytes));
		byte[] bytes = ZipUtils.getBytes(zipInputStream, "formdata.xml");
		if (bytes != null) {
			SAXReader xmlReader = new SAXReader();
			Document doc = null;
			try {
				doc = xmlReader.read(new ByteArrayInputStream(bytes));
			} catch (DocumentException ex) {
				throw new RuntimeException(ex);
			}
			if (doc != null) {
				Element root = doc.getRootElement();
				List<?> rows = root.elements();
				if (rows != null && rows.size() > 0) {
					Iterator<?> iterator = rows.iterator();
					while (iterator.hasNext()) {
						Element element = (Element) iterator.next();
						String businessKey = element
								.attributeValue("businessKey");
						if (businessKey != null) {
							if (MxFormContainer.getContainer()
									.getDataModelByBusinessKey(
											formApplication.getId(),
											businessKey) != null) {
								continue;
							}
							DataModel dataModel = new DataModelEntity();
							Map<String, String> params = new HashMap<String, String>();
							List<?> fields = element.elements();
							if (fields != null && fields.size() > 0) {
								Iterator<?> iter = fields.iterator();
								while (iter.hasNext()) {
									Element elem = (Element) iter.next();
									String name = elem.attributeValue("name");
									String value = elem.getStringValue();
									if (name != null && value != null) {
										params.put(name, value);
									}
								}
							}
							params.put("businessKey", businessKey);

							Map<String, Object> dataMap = FormTools.distill(
									params, formDefinition, dataModel);

							if (LogUtils.isDebug()) {
								logger.debug("dataMap:" + dataMap);
							}
							dataModel.setBusinessKey(businessKey);

							if (params.get("createBy") != null) {
								dataModel.setCreateBy(params.get("createBy")
										.toString());
							}
							if (params.get("status") != null) {
								dataModel.setStatus(Integer.valueOf(params.get(
										"status").toString()));
							}
							if (params.get("wfStatus") != null) {
								dataModel.setStatus(Integer.valueOf(params.get(
										"wfStatus").toString()));
							}

							formContext.setDataModel(dataModel);
							formContext.setFormDefinition(formDefinition);
							formContext.getDataMap().putAll(dataMap);

							MxFormContainer.getContainer().saveDataModel(
									formApplication.getId(), formContext);

						}
					}
				}
			}
		}
	}

}