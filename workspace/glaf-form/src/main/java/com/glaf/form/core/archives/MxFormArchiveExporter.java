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
import java.util.*;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.jbpm.file.def.FileDefinition;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.taskmgmt.def.Task;
import com.alibaba.fastjson.JSONObject;

import com.glaf.core.base.*;

import com.glaf.core.context.ContextFactory;

import com.glaf.core.identity.User;

import com.glaf.core.query.DataModelQuery;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;

import com.glaf.core.todo.Todo;
import com.glaf.core.util.*;

import com.glaf.jbpm.config.JbpmExtensionWriter;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.Context;
import com.glaf.jbpm.model.Extension;

import com.glaf.form.core.container.MxFormContainer;
import com.glaf.form.core.context.FormContext;
import com.glaf.form.core.domain.FormApplication;
import com.glaf.form.core.domain.FormDefinition;

import com.glaf.form.core.model.FormDefinitionType;
import com.glaf.form.core.query.FormApplicationQuery;
import com.glaf.form.core.service.FormDataService;
import com.glaf.form.core.util.*;
import com.glaf.form.core.xml.*;

public class MxFormArchiveExporter {

	/**
	 * 导出全部应用包
	 * 
	 * @return
	 * @throws Exception
	 */
	public byte[] zipAll() throws Exception {
		FormDataService formDataService = (FormDataService) ContextFactory
				.getBean("formDataService");
		FormApplicationQuery query = new FormApplicationQuery();
		Paging page = formDataService.getPageApplication(query);
		List<Object> rows = page.getRows();
		Map<String, InputStream> zipMap = new java.util.concurrent.ConcurrentHashMap<String, InputStream>();
		for (Object object : rows) {
			FormApplication app = (FormApplication) object;
			byte[] bytes = this.zip(app.getName(), false);
			InputStream inputStream = new BufferedInputStream(
					new ByteArrayInputStream(bytes));
			zipMap.put(app.getName() + ".zip", inputStream);
		}
		return AntUtils.getZipStream(zipMap);
	}

	@SuppressWarnings("unchecked")
	public byte[] zip(String app_name, boolean dataFlag) throws Exception {
		byte[] bytes = null;
		Document doc = null;

		FormDataService formDataService = ContextFactory
				.getBean("formDataService");

		FormContext formContext = new FormContext();
		FormApplication formApplication = formDataService
				.getFormApplicationByName(app_name);
		if (formApplication != null) {
			String encoding = "GBK";
			Map<String, InputStream> zipMap = new java.util.concurrent.ConcurrentHashMap<String, InputStream>();
			FormDefinition formDefinition = formDataService
					.getLatestFormDefinition(formApplication.getFormName());
			formContext.setFormDefinition(formDefinition);
			if (formDefinition != null) {
				FormDefinitionType fdt = FdlConverter
						.toFormDefinitionType(formDefinition);
				bytes = FdlConverter.toXml(fdt, encoding);
				InputStream inputStream2 = new BufferedInputStream(
						new ByteArrayInputStream(bytes));
				zipMap.put(formDefinition.getName() + ".fdl.xml", inputStream2);

				formContext.setFormDefinition(formDefinition);
			}

			if (StringUtils.isNotEmpty(formApplication.getProcessName())) {
				JbpmContext jbpmContext = null;

				try {

					jbpmContext = ProcessContainer.getContainer()
							.createJbpmContext();
					ProcessDefinition processDefinition = jbpmContext
							.getGraphSession().findLatestProcessDefinition(
									formApplication.getProcessName());
					if (processDefinition != null
							&& processDefinition.getFileDefinition() != null) {
						FileDefinition fileDefinition = processDefinition
								.getFileDefinition();
						if (fileDefinition.getInputStreamMap() != null) {
							zipMap.putAll(fileDefinition.getInputStreamMap());
							byte[] gpdBytes = fileDefinition
									.getBytes("gpd.xml");
							if (gpdBytes != null) {
								try {
									Document doc2 = Dom4jUtils
											.toDocument(fileDefinition
													.getInputStream("gpd.xml"));
									if (doc2 != null) {
									}
								} catch (Exception ex) {
									try {
										String xml = new String(gpdBytes);
										xml = StringTools.replaceIgnoreCase(
												xml, "UTF-8", "GBK");
										InputStream inputStream = new ByteArrayInputStream(
												xml.getBytes("GBK"));
										Document doc2x = Dom4jUtils
												.toDocument(inputStream);
										inputStream.close();
										inputStream = new ByteArrayInputStream(
												Dom4jUtils
														.getBytesFromPrettyDocument(
																doc2x, "GBK"));
										zipMap.put("gpd.xml", inputStream);
									} catch (Exception ex2) {
										throw new RuntimeException(ex2);
									}
								}
							}
						}
					}
					List<Extension> extensions = ProcessContainer
							.getContainer().getExtensions(jbpmContext,
									formApplication.getProcessName());
					if (extensions != null && extensions.size() > 0) {
						JbpmExtensionWriter writer = new JbpmExtensionWriter();
						doc = writer.write(extensions);
						byte[] cfgBytes = Dom4jUtils
								.getBytesFromPrettyDocument(doc, encoding);
						InputStream cfgStream = new BufferedInputStream(
								new ByteArrayInputStream(cfgBytes));
						zipMap.put("process.cfg.xml", cfgStream);
					}
				} catch (JbpmException ex) {
					throw ex;
				} finally {
					Context.close(jbpmContext);
				}
			}

			FormApplicationWriter formApplicationWriter = new FormApplicationWriter();
			doc = formApplicationWriter.write(formApplication);
			byte[] appBytes = Dom4jUtils.getBytesFromPrettyDocument(doc,
					encoding);
			InputStream appStream = new BufferedInputStream(
					new ByteArrayInputStream(appBytes));
			zipMap.put("application.xml", appStream);

			if (StringUtils.isNotEmpty(formApplication.getProcessName())) {

				List<Todo> todoList = new java.util.concurrent.CopyOnWriteArrayList<Todo>();
				ProcessDefinition processDefinition = null;
				JbpmContext jbpmContext = null;
				try {
					jbpmContext = ProcessContainer.getContainer()
							.createJbpmContext();
					processDefinition = jbpmContext.getGraphSession()
							.findLatestProcessDefinition(
									formApplication.getProcessName());
					Map<String, Task> tasks = processDefinition
							.getTaskMgmtDefinition().getTasks();
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
								subject = subject + task.getDescription()
										+ "尚未完成！";
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
							todo.setProcessName(formApplication
									.getProcessName());
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
							todo.setLinkType(formApplication.getProcessName()
									+ "_" + task.getName());
							todo.setModuleId(todo.getId());
							todo.setModuleName(formApplication.getTitle());
							todoList.add(todo);
						}
					}
				} catch (Exception ex) {
					throw new JbpmException(ex);
				} finally {
					Context.close(jbpmContext);
				}

				if (todoList.size() > 0) {
					long id = System.currentTimeMillis();
					doc = DocumentHelper.createDocument();
					Element root = doc.addElement("rows");
					for (Todo todo : todoList) {
						Element element = root.addElement("row");
						element.addAttribute("id", String.valueOf(id++));
						Map<String, Object> todoMap = Tools.getDataMap(todo);
						todoMap.remove("id");
						Set<Entry<String, Object>> entrySet = todoMap
								.entrySet();
						for (Entry<String, Object> entry : entrySet) {
							String name = entry.getKey();
							Object value = entry.getValue();
							if (value != null) {
								Element elem = element.addElement("property");
								elem.addAttribute("name", name);
								elem.addAttribute("value", value.toString());
							}
						}
					}
					byte[] dataBytes = Dom4jUtils.getBytesFromPrettyDocument(
							doc, encoding);
					doc = null;
					root = null;

					InputStream inputStream = new BufferedInputStream(
							new ByteArrayInputStream(dataBytes));
					zipMap.put(formApplication.getProcessName() + "_todo.xml",
							inputStream);
				}

			}

			if (dataFlag) {
				LoginContext loginContext = new LoginContext();
				DataModelQuery query = new DataModelQuery();
				query.setPageNo(1);
				query.setPageSize(50000);
				query.setLoginContext(loginContext);
				formContext.setFormApplication(formApplication);

				Paging jpage = MxFormContainer.getContainer().getPageDataModel(
						formApplication.getId(), query);
				List<Object> rows = jpage.getRows();
				if (rows != null && rows.size() > 0) {
					List<Object> list = new java.util.concurrent.CopyOnWriteArrayList<Object>();
					doc = DocumentHelper.createDocument();
					Element root = doc.addElement("rows");
					root.addAttribute("total", String.valueOf(rows.size()));
					Map<String, User> userMap = IdentityFactory.getUserMap();
					Iterator<Object> iterator = rows.iterator();
					while (iterator.hasNext()) {
						Object object = iterator.next();
						Map<String, Object> dataMap = null;
						if (object instanceof DataModel) {
							DataModel dataModel = (DataModel) object;
							dataMap = dataModel.getDataMap();
						} else {
							dataMap = Tools.getDataMap(object);
						}
						if (dataMap != null) {
							dataMap.remove("$type$");
							String actorIdx = ParamUtils.getString(dataMap,
									"actorId");
							User userProfile = userMap.get(actorIdx);
							if (userProfile != null) {
								dataMap.put("applyUser", userProfile.getName());
							}
							Element elem = root.addElement("row");
							if (dataMap.get("id") != null) {
								elem.addAttribute("id", dataMap.get("id")
										.toString());
							}
							if (dataMap.get("businessKey") != null) {
								elem.addAttribute("businessKey",
										dataMap.get("businessKey").toString());
							}
							if (dataMap.get("resourceId") != null) {
								elem.addAttribute("resourceId",
										dataMap.get("resourceId").toString());
							}
							list.add(dataMap);

							Set<Entry<String, Object>> entrySet = dataMap
									.entrySet();
							for (Entry<String, Object> entry : entrySet) {
								String name = entry.getKey();
								Object value = entry.getValue();
								if (name != null && value != null) {
									Element e = elem.addElement("field");
									e.addAttribute("name", name);
									if (value instanceof Date) {
										e.setText(DateUtils
												.getDateTime((Date) value));
									} else {
										e.addText(value.toString());
									}
								}
							}
						}
					}

					byte[] dataBytes = Dom4jUtils.getBytesFromPrettyDocument(
							doc, encoding);
					doc = null;
					root = null;

					InputStream inputStream = new BufferedInputStream(
							new ByteArrayInputStream(dataBytes));
					zipMap.put("formdata.xml", inputStream);

					Map<String, Object> pageInfo = new java.util.concurrent.ConcurrentHashMap<String, Object>();

					pageInfo.put("total", list.size());
					pageInfo.put("records", list);
					JSONObject object = new JSONObject(pageInfo);
					String content = object.toString();

					InputStream inputStream2 = new BufferedInputStream(
							new ByteArrayInputStream(content.getBytes()));
					zipMap.put("formdata.json", inputStream2);

					content = null;

				}
			}

			bytes = AntUtils.getZipStream(zipMap);
			zipMap.clear();
			zipMap = null;
		}
		return bytes;
	}
}