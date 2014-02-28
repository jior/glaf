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

package com.glaf.jbpm.deploy;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.taskmgmt.def.Task;

import com.glaf.core.config.CustomProperties;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.config.SystemProperties;

import com.glaf.core.util.Dom4jUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.LogUtils;
import com.glaf.core.util.ZipUtils;
import com.glaf.jbpm.config.JbpmExtensionReader;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.model.Extension;
import com.glaf.jbpm.manager.JbpmExtensionManager;

public class MxJbpmProcessDeployer {

	protected final static Log logger = LogFactory
			.getLog(MxJbpmProcessDeployer.class);

	public MxJbpmProcessDeployer() {

	}

	public ProcessDefinition deploy(JbpmContext jbpmContext, byte[] zipBytes) {
		SAXReader xmlReader = new SAXReader();
		Document doc = null;
		InputStream inputStream = null;
		ZipInputStream zipInputStream = null;
		ProcessDefinition processDefinition = null;
		Map<String, byte[]> zipMap = null;
		try {
			zipInputStream = new ZipInputStream(new ByteArrayInputStream(
					zipBytes));
			zipMap = ZipUtils.getZipBytesMap(zipInputStream);
			zipInputStream.close();
			zipInputStream = null;
			if (zipMap != null) {
				if (zipMap.get("processdefinition.xml") != null) {
					byte[] bytes = zipMap.get("processdefinition.xml");
					inputStream = new ByteArrayInputStream(bytes);
					doc = xmlReader.read(inputStream);
					this.reconfigProcessDefinition(jbpmContext, doc);
					Element root = doc.getRootElement();
					String encoding = doc.getXMLEncoding();
					String processName = root.attributeValue("name");

					try {
						SimpleDateFormat formatter = new SimpleDateFormat(
								"yyyyMMddHHmmss", Locale.getDefault());
						String ret = formatter.format(new Date());
						String filename = SystemConfig.getConfigRootPath()
								+ "/deploy/" + processName + ret + ".zip";
						// 保存原始文件
						FileUtils.save(filename, zipBytes);
					} catch (Exception ex) {
						if (LogUtils.isDebug()) {
							logger.debug(ex);
							ex.printStackTrace();
						}
					}

					byte[] pdBytes = Dom4jUtils.getBytesFromPrettyDocument(doc,
							encoding);
					zipMap.put("processdefinition.xml", pdBytes);

					// 重新打包修改过的流程定义文件
					byte[] newZipBytes = ZipUtils.toZipBytes(zipMap);
					zipInputStream = new ZipInputStream(
							new ByteArrayInputStream(newZipBytes));
					processDefinition = ProcessDefinition
							.parseParZipInputStream(zipInputStream);
					jbpmContext.deployProcessDefinition(processDefinition);
					zipInputStream.close();
					zipInputStream = null;

					String processDefinitionId = String
							.valueOf(processDefinition.getId());

					logger.debug("processDefinitionId:" + processDefinitionId);
					logger.debug("processName:" + processName);
					Map<String, Task> taskMap = processDefinition
							.getTaskMgmtDefinition().getTasks();
					if (taskMap != null && taskMap.size() > 0) {
						Iterator<String> iter = taskMap.keySet().iterator();
						while (iter.hasNext()) {
							String taskName = iter.next();
							logger.debug("taskName:" + taskName);
						}
					}

					try {
						SimpleDateFormat formatter = new SimpleDateFormat(
								"yyyyMMddHHmmss", Locale.getDefault());
						String ret = formatter.format(new Date());
						String filename = SystemConfig.getConfigRootPath()
								+ "/deploy/" + processName + ret
								+ "_repack.zip";
						// 保存修改过的流程定义文件
						FileUtils.save(filename, newZipBytes);
					} catch (Exception ex) {
						if (LogUtils.isDebug()) {
							logger.debug(ex);
						}
					}
				}

				if (zipMap.get("process.cfg.xml") != null) {
					byte[] x_bytes = zipMap.get("process.cfg.xml");
					if (x_bytes != null) {
						JbpmExtensionReader reader = new JbpmExtensionReader();
						List<Extension> extensions = reader
								.readTasks(new ByteArrayInputStream(x_bytes));
						if (extensions != null && extensions.size() > 0) {

							JbpmExtensionManager jbpmExtensionManager = ProcessContainer
									.getContainer().getJbpmExtensionManager();
							jbpmExtensionManager.reconfig(jbpmContext,
									extensions);
						}
					}
				}
			}
			return processDefinition;
		} catch (Throwable ex) {
			if (LogUtils.isDebug()) {
				logger.debug(ex);
				ex.printStackTrace();
			}
			throw new JbpmException(ex);
		} finally {
			try {
				if (zipInputStream != null) {
					zipInputStream.close();
					zipInputStream = null;
				}
			} catch (Exception ex) {
				if (LogUtils.isDebug()) {
					logger.debug(ex);
				}
			}
			try {
				if (inputStream != null) {
					inputStream.close();
					inputStream = null;
				}
			} catch (Exception ex) {
				if (LogUtils.isDebug()) {
					logger.debug(ex);
				}
			}
		}
	}

	public void reconfigProcessDefinition(JbpmContext jbpmContext, Document doc) {
		Element root = doc.getRootElement();
		String processName = root.attributeValue("name");
		String className = null;
		Map<String, String> actionMap = new java.util.concurrent.ConcurrentHashMap<String, String>();
		List<?> actionList = root.elements("action");
		if (actionList != null && actionList.size() > 0) {
			Iterator<?> iterator = actionList.iterator();
			while (iterator.hasNext()) {
				Element element = (Element) iterator.next();
				String name = element.attributeValue("name");
				String clazz = element.attributeValue("class");
				actionMap.put(name, clazz);
			}
		}

		if (StringUtils.isEmpty(className)) {
			className = CustomProperties.getString(processName
					+ ".jbpm.action.class");
		}

		if (StringUtils.isEmpty(className)) {
			className = CustomProperties.getString("jbpm.action.class");
		}

		if (StringUtils.isEmpty(className)) {
			className = SystemProperties.getString("jbpm.action.class");
		}

		List<?> taskNodeList = root.elements("task-node");
		if (taskNodeList == null || taskNodeList.size() == 0) {
			return;
		}

		Iterator<?> iterator = taskNodeList.iterator();
		while (iterator.hasNext()) {
			Element element = (Element) iterator.next();
			Map<String, String> taskMap = new java.util.concurrent.ConcurrentHashMap<String, String>();
			List<?> taskList = element.elements("task");
			if (taskList != null && taskList.size() > 0) {
				Iterator<?> iter = taskList.iterator();
				while (iter.hasNext()) {
					Element elem = (Element) iter.next();
					taskMap.put(elem.attributeValue("name"),
							elem.attributeValue("description"));
				}
			}

			List<?> eventList = element.elements("event");
			if (eventList != null && eventList.size() > 0) {
				Iterator<?> iter = eventList.iterator();
				while (iter.hasNext()) {
					Element elem = (Element) iter.next();
					String eventType = elem.attributeValue("type");
					if (StringUtils.equals(eventType, "node-enter")) {
						List<?> actionRefList = elem.elements("action");
						if (actionRefList != null && actionRefList.size() > 0) {
							Iterator<?> it = actionRefList.iterator();
							while (it.hasNext()) {
								Element e = (Element) it.next();
								String ref = e.attributeValue("ref-name");
								if (StringUtils.isNotEmpty(ref)) {
									if (!actionMap.containsKey(ref)) {
										Element newAction = root
												.addElement("action");
										newAction.addAttribute("name", ref);
										newAction.addAttribute("class",
												className);
										newAction.addElement("extensionName")
												.setText(ref);
									}
								}
							}
						}
					}
				}
			}
		}
	}

}